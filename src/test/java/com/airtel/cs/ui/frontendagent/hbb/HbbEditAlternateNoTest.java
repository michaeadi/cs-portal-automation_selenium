package com.airtel.cs.ui.frontendagent.hbb;
import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.actionconfig.MetaInfo;
import com.airtel.cs.model.response.actiontrail.ActionTrail;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class HbbEditAlternateNoTest extends Driver {

    private static String hbbCustomerNumber, responseMessage,msisdn = null;

    ESBRequestSource apiEsb = new ESBRequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "SmokeTest", "ProdTest"})
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

    /**
     * This method is used to check whether user has permission to edit alternate number
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTestTec"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasEditAlternateMsisdnPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that alternate no. edit icon should be visible to the logged in agent if permission is enabled in UM, Check User has permission to edit alternate no", "description");
            String editIcon_permission = constants.getValue(PermissionConstants.ALTERNATE_NUMBER_EDIT_ICON_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isAlternateNoEditIconVisible(), UtilsMethods.isUserHasPermission(new Headers(map), editIcon_permission), "Edit Icon displayed correctly as per user permission", "Edit Icon does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasEditAlternateMsisdnPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateActionIcon() {
        try {
            selUtils.addTestcaseDescription("Validating call to Action options", "description");
            Boolean profileVisibility = pages.getHbbProfilePage().isHBBProfileVisible();
            if (profileVisibility == true) {
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isCallToActionVisible(), true, "Call to Action icon visible", "Call to Action icon not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isAlternateNoEditIconVisible(), true, "Edit icon visible", "Edit icon not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEmailEditIconVisible(), true, "Edit icon visible", "Edit icon not visible"));
                pages.getHbbProfilePage().clickOnCallToAction();
                msisdn = pages.getHbbProfilePage().getAlternateNumber();
                CustomerProfileResponse customerResponse=apiEsb.customerProfileResponse(msisdn);
                responseMessage=customerResponse.getMessage();
                if (!responseMessage.contains("does not exist in the system"))
                    assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isGSMProfileVisible(), true, "GSM profile is visible when alternate number is airtel number", "GSM profile is not visible when alternate number is airtel number"));
                 else
                    assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHBBNonAirtelPopUpVisible(), true, "Non Airtel Hbb profile is visible when alternate number is non airtel number", "Non Airtel Hbb profile is visible when alternate number is non airtel number"));
            }
            else
                commonLib.fail("Hbb Profile is not visible", true);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateActionIcon" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "isUserHasEditAlternateMsisdnPermission"})
    public void validateEditIcon() {
        try {
            selUtils.addTestcaseDescription("Validating edit option fields ", "description");
            String validNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            Boolean profileVisibility = pages.getHbbProfilePage().isHBBProfileVisible();
            if (profileVisibility == true) {
                pages.getHbbProfilePage().clickOnEditIconAlternateNo();
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEditAlternatePopUpVisible(), true, "Edit Alternate Number Popup visible", "Edit Alternate Number Popup not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEnterAlternateNoVisible(), true, "Enter Alternate no. is visible", "Enter Alternate no is  not visible"));
                pages.getHbbProfilePage().enterMsisdnAlternateNo(validNumber);
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEmailEditIconVisible(), true, "Edit icon visible", "Edit icon not visible"));
                String inValidNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
                pages.getHbbProfilePage().enterMsisdnAlternateNo(inValidNumber);
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isErrorMessageVisible(), true, "Error message is displayed after entering invalid msisdn", "Error message is not displayed after entering invaid msisdn"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSubmitBtnDisabled(), true, "Submit button is disabled", "Submit button is not disabled"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEnterCommentVisible(), true, "Enter comment is visible", "Enter comment is not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSelectReasonVisible(), true, "Select reason is visible", "Select reason is visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getHbbProfilePage().getAlternateNumber(), pages.getHbbProfilePage().getEnterAlternateNoText(), "Alternate number is auto populated in Enter Alternate Number field", "Alternate number is not auto populated in Enter Alternate Number field"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isCancelButtonVisible(), true, "Cancel button is displayed", "Cancel button is not displayed"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSubmitBtnDisabled(), true, "Submit button is disabled", "Submit button is not disabled"));
                pages.getHbbProfilePage().clickCancelButton();
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isEditAlternatePopUpVisible(), false, "Edit Alternate Pop up is not visible after clicking on Cancel button", "Edit Alternate Pop up is still visible after clicking on Cancel button"));
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHBBProfileVisible(), false, "Hbb Panel is visible after clicking Cancel button", "Hbb Panel is not visible after clicking Cancel button"));
            }else{
                commonLib.fail("Hbb Profile is not visible", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateEditIcon" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "isUserHasEditAlternateMsisdnPermission"})
    public void validateEditAlternateNo() {
        try {
            selUtils.addTestcaseDescription("Performing operation on edit icon ", "description");
            String validNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            String successText = "Request for Alternate Number Updating has been successfully submitted. Link has been sent to customer's email id for validation.";
            Boolean profileVisibility = pages.getHbbProfilePage().isHBBProfileVisible();
            if (profileVisibility == true) {
                pages.getHbbProfilePage().clickOnEditIconAlternateNo();
                pages.getHbbProfilePage().enterMsisdnAlternateNo(validNumber);
                pages.getHbbProfilePage().enterComment("Automation Testing");
                pages.getHbbProfilePage().clickOnSubmit();
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSuccessPopUpVisible(), true, "Success Popup visible after editing alternate number", "Success Popup not visible after editing alternate number"));
                assertCheck.append(actions.assertEqualStringType(pages.getHbbProfilePage().getSuccessText(), successText, "Success text is  displayed correctly", "Success text is not displayed correctly"));
                } else{
                commonLib.fail("Hbb Profile is not visible", true);
            }
        actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateEditAlternateNo" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateEntryInActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validate Action Trail column's value are visible and correct ", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            ActionTrail actionTrailAPI = api.getEventHistory(hbbCustomerNumber, "ACTION");
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
}

