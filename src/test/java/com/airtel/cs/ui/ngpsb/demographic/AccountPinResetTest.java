package com.airtel.cs.ui.ngpsb.demographic;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountPinResetTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String className = this.getClass().getName();


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            clmDetails = api.getCLMDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (!pageLoaded)
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 2, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void pinReset() {
        try {
            selUtils.addTestcaseDescription("Validate Pin Reset", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isPinResetIconVisible(), true, "Pin Reset Icon is visible", "Pin Reset Icon is NOT visible"));
            if (pages.getAccountInformation().getBarringStatus().equals("NO")) {
                pages.getPinReset().clickPinReset();
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isIssuePopUpVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isPinResetPinHeaderVisible(), true, "Pin reset is visible in Issue Detail Pop up", "Pin Reset label is Issue Detail Pop up"));
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isSubmitBtnDisabled(), false, "Submit button is disabled", "Submit button is not disabled"));
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is NOT visible"));
                pages.getPinReset().enterComment(ApplicationConstants.COMMENT);
                pages.getPinReset().clickOnCancelButton();
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isCancelConfirmMessageVisible(), true, "Cancel confirmation message is visible ", "Cancel confirm message  is NOT visible"));
                pages.getPinReset().clickOnContinue();
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isDemographicWidgetVisible(), true, "Demographic Widget is visible after closing the Issue Detail Pop up ", "Demographic Widget is NOT visible after closing the Issue Detail Pop up"));
                pages.getPinReset().performResetPin();
                assertCheck.append(actions.assertEqualBoolean(pages.getPinReset().isSuccessPopUpVisible(), true, "Success Popup visible after resetting pin  ", "Success Popup NOT visible after resetting pin"));
                String successText = "SmartCash Pin Reset is successful";
                assertCheck.append(actions.assertEqualStringType(pages.getPinReset().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
                pages.getPinReset().clickCrossIcon();
            } else {
                commonLib.warning("We can not perform PIN Reset as Wallet is BARRED, Please check this Manually");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - pinReset" + e.fillInStackTrace(), true);
            pages.getPinReset().clickCloseBtn();
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction", "pinReset"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing reset pin", "description");
            pages.getPinReset().goToActionTrail();
            assertCheck.append(actions.assertEqualStringType(pages.getPinReset().getActionType(), "SmartCash PIN Reset", "Action type for pin reset is expected", "Action type for pin reset is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getPinReset().getReason(), "Customer Forgot PIN", "Reason for pin reset is expected", "Reason for pin reset is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getPinReset().getComment(), ApplicationConstants.COMMENT, "Comment for pin reset is expected", "Comment for pin reset is not as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }

}
