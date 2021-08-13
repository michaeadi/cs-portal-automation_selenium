package com.airtel.cs.ui.frontendagent.viewhistorytab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.actionconfig.MetaInfo;
import com.airtel.cs.model.response.actiontrail.ActionTrail;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ActionTrailTest extends Driver {

    String comments = "Adding comment using Automation";
    RequestSource api = new RequestSource();
    private static String customerNumber = null;



    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasActionTrailPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that Action trail permission should be visible to the logged in agent if action trail permission is enabled in UM, Check User has permission to view Action trail tab", "description");
            String actionTrail = constants.getValue(PermissionConstants.ACTION_TRAIL_TAB_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(true, UtilsMethods.isUserHasPermission(new Headers(map), actionTrail), "Action trail permission displayed correctly as per user permission", "Action trail permission does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasFriendFamilyPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "Action Trail Tab")
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction","isUserHasActionTrailPermission"})
    public void validateActionTrailOpenCorrectly(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible and then click on it,Validate column header name are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            for(int i=0;i<data.getHeaderName().size();i++){
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getHeaderValue(i), data.getHeaderName().get(i), "Header Name for Row "+(i+1)+" is as expected", "Header Name for Row "+(i+1)+" is not as expected"));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Exception in Method - validateActionTrailOpenCorrectly" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"validateActionTrailOpenCorrectly"})
    public void validateActionTrailValue() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible,Validate column's value are visible and correct", "description");
            ActionTrail actionTrailAPI = api.getEventHistory(customerNumber, "ACTION");
            final int statusCode = actionTrailAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Action Trail API success and status code is :" + statusCode, "Action Trail API got failed and status code is :" + statusCode,false,true));
            if (statusCode == 200) {
                int size=Math.min(actionTrailAPI.getTotalCount(),10);
                for(int i=0;i<size;i++) {
                    commonLib.info("Printing Info for Row Number "+i+1);
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i+1, 1), actionTrailAPI.getResult().get(i).getActionType(), "Action Type Column value displayed Correctly", "Action Type Column Value does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i+1, 2), UtilsMethods.getDateFromEpoch(Long.valueOf(actionTrailAPI.getResult().get(i).getCreatedOn()),constants.getValue(CommonConstants.APPLICATION_UI_TIME_FORMAT)),"Date & Time Column displayed Correctly", "Date & Time Column does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i+1, 3) ,actionTrailAPI.getResult().get(i).getReason(), "Reason Column displayed Correctly", "Reason Column does not displayed Correctly"));
                    assertCheck.append(actions.assertEqualStringType(pages.getActionTrailPage().getValue(i+1, 4), actionTrailAPI.getResult().get(i).getAgentId(), "Agent Id Column displayed Correctly", "Agent Id Column does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i+1, 5),actionTrailAPI.getResult().get(i).getAgentName() ,"Agent name Column displayed Correctly", "Agent name Column does not displayed in Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i+1, 6),actionTrailAPI.getResult().get(i).getComments() , "Comments Column displayed Correctly", "Comments Column does not displayed in Correctly"));
                    if(actionTrailAPI.getResult().get(i).getMetaInfo()!=null){
                        pages.getActionTrailPage().clickMetaInfoIcon(i+1);
                        for(int metaInfoCount=0;metaInfoCount<actionTrailAPI.getResult().get(i).getMetaInfo().size();metaInfoCount++) {
                            MetaInfo metaInfo=actionTrailAPI.getResult().get(i).getMetaInfo().get(metaInfoCount);
                            assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getMetaInfoValue(i+2, metaInfoCount+1),metaInfo.getValue() , metaInfoCount+" :Meta Info value displayed Correctly", metaInfoCount+" :Meta Info value does not displayed in Correctly"));
                            assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getMetaInfoLabel(i+2, metaInfoCount+1),metaInfo.getLabel() , metaInfoCount+" :Meta Info label displayed Correctly", metaInfoCount+" :Meta Info label does not displayed in Correctly"));
                        }
                        pages.getActionTrailPage().clickMetaInfoIcon(i+1);
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException | IndexOutOfBoundsException e) {
            commonLib.fail("Exception in Method - validateActionTrailValue" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
