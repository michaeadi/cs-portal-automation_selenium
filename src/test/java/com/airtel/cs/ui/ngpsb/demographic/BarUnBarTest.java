package com.airtel.cs.ui.ngpsb.demographic;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class BarUnBarTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    boolean barIconVisible, unbarIconVisible = false;


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
                boolean pageLoaded = pages.getDemographicWidget().isPageLoaded(clmDetails);
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
    public void barUnbar() {
        try {
            selUtils.addTestcaseDescription("Validate Bar Unbar Action", "description");
            barIconVisible = pages.getBarUnbar().isBarIconVisible();
            unbarIconVisible = pages.getBarUnbar().isUnBarIconVisible();
            if (barIconVisible == true) {
                pages.getBarUnbar().clickBarIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isIssuePopUpVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isBarHeaderVisible(), true, "BAR header is visible in Issue Detail Pop up", "BAR header is visible not Issue Detail Pop up"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSubmitBtnDisabled(), false, "Submit button is disabled", "Submit button is not disabled"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is not visible"));
                pages.getBarUnbar().performBarUnBar();
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing Bar action", "Success Popup is not visible after performing Bar action"));
                String successText = "SmartCash has been barred successfully";
                assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
            } else if (unbarIconVisible == true) {
                pages.getBarUnbar().clickUnBarIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isIssuePopUpVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isUnBarHeaderVisible(), true, "UNBAR header is visible in Issue Detail Pop up", "UNBAR header label is Issue Detail Pop up"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSubmitBtnDisabled(), false, "Submit button is disabled", "Submit button is not disabled"));
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is NOT visible"));
                pages.getBarUnbar().performBarUnBar();
                assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing UnBar action", "Success Popup is visible not after performing UnBar action"));
                String successText = "SmartCash has been unbarred successfully";
                assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
            }
            pages.getBarUnbar().clickCrossIcon();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - barUnbar" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction","barUnbar"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing bar/unbar action", "description");
            pages.getBarUnbar().goToActionTrail();
            if (barIconVisible == true)
                assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getActionType(), "SmartCash BARRED", "Action type for Bar is expected", "Action type for bar is not as expected"));
            if (unbarIconVisible == true)
                assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getActionType(), "SmartCash UNBARRED", "Action type for UnBar is expected", "Action type for unbar is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getReason(), "Lost Sim", "Reason for bar/unbar is expected", "Reason for bar/unbar is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getComment(), COMMENT, "Comment for bar/unbar is expected", "Comment for bar/unbar is not as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }
}
