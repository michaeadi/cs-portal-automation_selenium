package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FrontendAgentTicketTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     * @param data
     */
    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"} ,dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
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

    /**
     * This method is used to Validate Ticket Meta Data for Frontend Agent
     * @throws InterruptedException
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"} ,dependsOnMethods = "openCustomerInteraction")
    public void validateTicket() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Validate Ticket Meta Data for Frontend Agent", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().goToTicketHistoryTab();
            pages.getFrontendTicketHistoryPage().waitTillLoaderGetsRemoved();
            String ticketId = pages.getFrontendTicketHistoryPage().getTicketId(1);
            pages.getFrontendTicketHistoryPage().writeTicketId(ticketId);
            pages.getFrontendTicketHistoryPage().clickSearchBtn();
            pages.getFrontendTicketHistoryPage().waitTillLoaderGetsRemoved();
            Thread.sleep(3000);
            assertCheck.append(actions.assertEqual_stringType(pages.getFrontendTicketHistoryPage().getTicketId(1), ticketId, "Ticket Id same as search ticket id.", "Ticket Id does not same as search ticket id."));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getFrontendTicketHistoryPage().getTicketPriority(1), "Ticket priority is not null.", "Ticket priority must not be null."));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getFrontendTicketHistoryPage().getTicketQueue(1), "Ticket Queue is not null.", "Ticket Queue must not be null."));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getFrontendTicketHistoryPage().getSourceApp(1), "Source App is not empty.", "Source App can not be empty."));
            if (ticketId != null)
                actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicket()" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to Validate Add to Interaction Icon on Each Ticket
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"},dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateAddToInteraction() {
        try {
            selUtils.addTestcaseDescription("Validate Add to Interaction Icon on Each Ticket", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().goToTicketHistoryTab();
            pages.getFrontendTicketHistoryPage();
            final boolean interactionIcon = pages.getFrontendTicketHistoryPage().validateAddToInteractionIcon();
            assertCheck.append(actions.assertEqual_boolean(interactionIcon, true, "Add to interaction Icon found on ticket", "Add to interaction Icon does not found on ticket"));
            if (!interactionIcon) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAddToInteraction()" + e.fillInStackTrace(), true);
        }
        /*softAssert.assertTrue(pages.getFrontendTicketHistoryPage().validateAddToInteractionIcon(), "Add to interaction Icon does not found on ticket");
        softAssert.assertAll();*/
    }

    /**
     * This method is used to Validate NFTR issue have ticket icon
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"},dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateNFTRIssue() {
        try {
            selUtils.addTestcaseDescription("Validate NFTR issue have ticket icon", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnInteractionsTab();
            pages.getViewHistory();
            final boolean viewTicketPage = pages.getViewHistory().checkViewTicketPage();
            final boolean clickOnTicketIcon = pages.getViewHistory().clickOnTicketIcon();
            assertCheck.append(actions.assertEqual_boolean(clickOnTicketIcon, true, "NFTR issue found in interaction history tab", "No NFTR issue found in interaction history tab"));
            pages.getViewHistory().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(viewTicketPage, true, "View ticket page open", "View ticket page does not open"));
            pages.getViewHistory().clickCloseTicketTab();
            if(!clickOnTicketIcon || !viewTicketPage) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateNFTRIssue()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate send SMS tab
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"},dependsOnMethods = "openCustomerInteraction")
    public void validateSendSMSTab() {
        selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        pages.getCustomerProfilePage().clickOnAction();
        try {
            pages.getCustomerProfilePage().openSendSMSTab();
            pages.getSendSMS().waitTillLoaderGetsRemoved();
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isPageLoaded(), true, "Send SMS tab open correctly", "Send SMS tab does not open correctly"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Send SMS tab does not open correctly" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isCategory(), true, "Category field displayed", "Category field does not displayed"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Category field does not displayed" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isCustomerNumber(), true,"Customer number displayed", "Customer number does not displayed" ));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Customer number does not displayed" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isLanguage(), true, "Language field displayed", "Language field does not displayed"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Language field does not displayed" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isTemplateName(), true, "Template name field display", "Template name field does not display"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Template name field does not displayed" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isMessageContentEditable(), true, "Message Content Editable", "Message Content is not Editable"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Content Is Editable" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isSendBtnDisabled(), true, "Send SMS button is clickable","Send SMS button is not clickable"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Send SMS button is display on UI" + e.fillInStackTrace(), true);
            }
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Send SMS tab does not open properly" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
