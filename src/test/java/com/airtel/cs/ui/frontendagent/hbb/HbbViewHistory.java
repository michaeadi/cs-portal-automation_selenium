package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.actionconfig.MetaInfo;
import com.airtel.cs.model.response.actiontrail.ActionTrail;
import com.airtel.cs.model.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.model.response.adjustmenthistory.AdjustmentResult;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HbbViewHistory extends Driver {

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
            customerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
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
    public void validateActionTrailTab() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible,Validate column's value are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            ActionTrail actionTrailAPI = api.getEventHistory(customerNumber, "ACTION");
            final int statusCode = actionTrailAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Action Trail API success and status code is :" + statusCode, "Action Trail API got failed and status code is :" + statusCode, false, true));
            if (statusCode == 200) {
                int size = Math.min(actionTrailAPI.getTotalCount(), 10);
                for (int i = 0; i < size; i++) {
                    commonLib.info("Printing Info for Row Number " + i + 1);
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 1), actionTrailAPI.getResult().get(i).getActionType(), "Action Type Column value displayed Correctly", "Action Type Column Value does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 2), UtilsMethods.getDateFromEpoch(Long.valueOf(actionTrailAPI.getResult().get(i).getCreatedOn()), constants.getValue(CommonConstants.APPLICATION_UI_TIME_FORMAT)), "Date & Time Column displayed Correctly", "Date & Time Column does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 3), actionTrailAPI.getResult().get(i).getReason(), "Reason Column displayed Correctly", "Reason Column does not displayed Correctly"));
                    assertCheck.append(actions.assertEqualStringType(pages.getActionTrailPage().getValue(i + 1, 4), actionTrailAPI.getResult().get(i).getAgentId(), "Agent Id Column displayed Correctly", "Agent Id Column does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 5), actionTrailAPI.getResult().get(i).getAgentName(), "Agent name Column displayed Correctly", "Agent name Column does not displayed in Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 6), actionTrailAPI.getResult().get(i).getComments(), "Comments Column displayed Correctly", "Comments Column does not displayed in Correctly"));
                    if (actionTrailAPI.getResult().get(i).getMetaInfo() != null) {
                        pages.getActionTrailPage().clickMetaInfoIcon(i + 1);
                        for (int metaInfoCount = 0; metaInfoCount < actionTrailAPI.getResult().get(i).getMetaInfo().size(); metaInfoCount++) {
                            MetaInfo metaInfo = actionTrailAPI.getResult().get(i).getMetaInfo().get(metaInfoCount);
                            assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getMetaInfoValue(i + 2, metaInfoCount + 1), metaInfo.getValue(), metaInfoCount + " :Meta Info value displayed Correctly", metaInfoCount + " :Meta Info value does not displayed in Correctly"));
                            assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getMetaInfoLabel(i + 2, metaInfoCount + 1), metaInfo.getLabel(), metaInfoCount + " :Meta Info label displayed Correctly", metaInfoCount + " :Meta Info label does not displayed in Correctly"));
                        }
                        pages.getActionTrailPage().clickMetaInfoIcon(i + 1);
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException | IndexOutOfBoundsException e) {
            commonLib.fail("Exception in Method - validateActionTrailValue" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateAdjustmentHistoryTab() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Adjustment History tab is visible,Validate column's value are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnAdjustmentHistory();
            AdjustmentHistory adjustmentHistory = api.getAdjustMentHistory(customerNumber);
            final int statusCode = adjustmentHistory.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode, false, true));
            if (statusCode == 200) {
                int size = Math.min(adjustmentHistory.getTotalCount(), 10);
                for (int i = 0; i < size; i++) {
                    AdjustmentResult result = adjustmentHistory.getResult().get(i);
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 1).toLowerCase().trim(), result.getDate().toLowerCase().trim(), "Date & Time value displayed as expected", "Date & Time value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 2).toLowerCase().trim(), result.getAdjustmentType().toLowerCase().trim(), "Action Type Column displayed as expected", "Action Type value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 3).toLowerCase().trim(), result.getReason().toLowerCase().trim(), "Reason value displayed as expected", "Reason value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 4).toLowerCase().trim(), result.getAccountType().toLowerCase().trim(), "Account Type Id value displayed as expected", "Account Type Id value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 5).trim(), result.getAmount().trim(), "Amount value Column displayed as expected", "Amount value value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 6).trim(), result.getAgentId().trim(), "Agent Id value displayed as expected", "Agent Id value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 7).toLowerCase().trim(), result.getAgentName().toLowerCase().trim(), "Agent name value displayed as expected.", "Agent name value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 8).toLowerCase().trim(), result.getComment().toLowerCase().trim(), "Comments value displayed as expected", "Comments Column value not display as expected"));
                }
            }
        } catch (java.util.NoSuchElementException | TimeoutException | IndexOutOfBoundsException e) {
            commonLib.fail("Exception in Method - validateAdjustmentHistoryTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateViewHistoryOptions() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible,Validate column's value are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isMessageHistoryVisible(), true, "Message History tab is  successfully displayed ", "Message History is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isTicketHistoryVisible(), true, "Ticket History tab successfully displayed ", " Interaction History is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isInteractionHistoryVisible(), true, "Interaction tab is successfully displayed ", "Interaction History is not displayed"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validate " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);

    }
}
