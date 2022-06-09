package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.actionconfig.MetaInfo;
import com.airtel.cs.model.cs.response.actiontrail.EventLogsResponse;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HbbEditEmailTest extends Driver {


    private String hbbCustomerNumber = null;
    Boolean profileVisibility;

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
            hbbCustomerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page loaded successfully", "Customer Profile Page not loaded successfully"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasEditEmailPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that email. edit icon should be visible to the logged in agent if permission is enabled in UM, Check User has permission to edit email id", "description");
            String editEmail_permission = constants.getValue(PermissionConstants.EMAIL_EDIT_ICON_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEmailEditIconVisible(), UtilsMethods.isUserHasPermission(editEmail_permission), "Edit email icon  displayed correctly as per user permission", "Edit email Icon does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasEditEmailPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasEditEmailPermission"})
    public void testEditEmailIcon() {
        try {
            if (profileVisibility) {
                selUtils.addTestcaseDescription("Validate Edit icon is visible after Alternate number,Validate Edit icon is visible after Email", "description");
                pages.getHbbProfilePage().clickOnEditAlternateNo();
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEditAlternatePopUpVisible(), true, "Edit Alternate Email Popup visible", "Edit Alternate Email Popup NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEnterEmailIdVisible(), true, "Enter Alternate no. is visible", "Enter Alternate no is  not visible"));
                String inValidEmail = "abcgmail.com";
                pages.getHbbProfilePage().enterMsisdnAlternateNo(inValidEmail);
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isRedLineVisible(), true, "Red line is displayed after entering invalid email", "Red line is not displayed after entering invalid email"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSubmitBtnDisabled(), true, "Submit button is disabled", "Submit button is not disabled"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEnterCommentVisible(), true, "Enter comment is visible", "Enter comment is not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isAsteriskVisible(), true, "Enter comment is mandatory field", "Enter comment is not mandatory field"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSelectReasonVisible(), true, "Select reason is visible", "Select reason is visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isCancelButtonVisible(), true, "Cancel button is displayed", "Cancel button is not displayed"));
            } else
                commonLib.fail("Hbb Profile is not visible", true);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testEditEmailIcon" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    /*@Test(priority = 5, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateEntryInActionTrail() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible,Validate column's value are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            EventLogsResponse actionTrailAPI = api.getEventHistory(hbbCustomerNumber, "ACTION");
            final int statusCode = actionTrailAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Action Trail API success and status code is :" + statusCode, "Action Trail API got failed and status code is :" + statusCode, false, true));
            if (statusCode == 200) {
                int size = Math.min(actionTrailAPI.getTotalCount(), 10);
                for (int i = 0; i < size; i++) {
                    commonLib.info("Printing Info for Row Number " + i + 1);
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 1), actionTrailAPI.getResult().get(i).getActionType(), "Action Type Column value displayed Correctly", "Action Type Column Value does not displayed Correctly"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getActionTrailPage().getValue(i + 1, 2), UtilsMethods.getDateFromEpoch(Long.parseLong(actionTrailAPI.getResult().get(i).getCreatedOn()), constants.getValue(CommonConstants.APPLICATION_UI_TIME_FORMAT)), "Date & Time Column displayed Correctly", "Date & Time Column does not displayed Correctly"));
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
            commonLib.fail("Exception in Method - ValidateEntryInActionTrail" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }*/


}
