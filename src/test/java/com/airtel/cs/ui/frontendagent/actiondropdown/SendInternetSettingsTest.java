package com.airtel.cs.ui.frontendagent.actiondropdown;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SendInternetSettingsTest extends Driver {

    private final BaseActions actions = new BaseActions();
    String comments = "Adding comment using Automation";
    Boolean popup = true;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isSendInternetSettingsEnabled() {
        if (StringUtils.equalsIgnoreCase(constants.getValue(ApplicationConstants.SEND_INTERNET_SETTINGS), "false")) {
            commonLib.skip("Send Internet Settings Feature is NOT Enabled for this Opco=" + OPCO);
            throw new SkipException("Send Internet Settings Feature is NOT Enabled for this Opco=" + OPCO);
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateCancelBtn() {
        boolean modalOpened = false;
        try {
            selUtils.addTestcaseDescription("Open send internet setting modal from actions drop down, Click on cancel button, Modal should be closed", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendInternetSetting();
            modalOpened = true;
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSendInternetSettingTitleVisible(), true, "Send Internet Setting Tab opened", "Send Internet Setting Tab does NOT opened"));
            pages.getCustomerProfilePage().clickCancelBtn();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            if (modalOpened)
                pages.getCustomerProfilePage().clickCloseBtn();
            commonLib.fail("Exception in Method :- validateSendInternetSetting" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest","ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateSendInternetSetting() {
        try {
            selUtils.addTestcaseDescription("Open send internet setting modal from actions drop down,Validate issue detail title visible,Select reason and enter comment and click on submit button, Validate success message", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendInternetSetting();
            popup = !pages.getCustomerProfilePage().isSendInternetSettingConfirmMessageVisible();
            if (popup) {
                assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isIssueDetailTitleVisible(), true, "Issue Detail Configured", "Issue Detail does not configured"));
                pages.getAuthTabPage().clickSelectReasonDropDown();
                reason = pages.getAuthTabPage().getReason();
                pages.getAuthTabPage().chooseReason();
                pages.getAuthTabPage().enterComment(comments);
                pages.getAuthTabPage().clickSubmitBtn();
                final String toastText = pages.getAuthTabPage().getToastText();
                assertCheck.append(actions.assertEqual_stringType(toastText, "Internet Settings has been sent on Customer`s Device.", "Send Internet Settings Message has been sent to customer successfully", "Send Internet Settings Message hasn't been sent to customer ans message is :-" + toastText));
            } else {
                pages.getAuthTabPage().clickYesBtn();
            }
            final String toastText = pages.getAuthTabPage().getToastText();
            assertCheck.append(actions.assertEqual_stringType(toastText, "Internet Settings has been sent on Customer`s Device.", "Send Internet Settings Message has been sent to customer successfully", "Send Internet Settings Message hasn't been sent to customer ans message is :-" + toastText));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - validateSendInternetSetting" + e.fillInStackTrace(), true);
            pages.getCustomerProfilePage().clickOutside();
        }
    }
}
