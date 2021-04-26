package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FrontendAgentTicketTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        final String customerNumber = data.getCustomerNumber();
        selUtils.addTestcaseDescription("Validating the Search forCustomer Interactions :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getMsisdnSearchPage().enterNumber(customerNumber);
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded(), "Dashboard page does not open.");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Ticket Meta Data for Frontend Agent ",dependsOnMethods = "openCustomerInteraction")
    public void validateTicket() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Ticket Meta Data for Frontend Agent", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().goToViewHistory();
        pages.getViewHistory().goToTicketHistoryTab();
        pages.getFrontendTicketHistoryPage().waitTillLoaderGetsRemoved();
        String ticketId = pages.getFrontendTicketHistoryPage().getTicketId(1);
        pages.getFrontendTicketHistoryPage().writeTicketId(ticketId);
        pages.getFrontendTicketHistoryPage().clickSearchBtn();
        pages.getFrontendTicketHistoryPage().waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        softAssert.assertEquals(pages.getFrontendTicketHistoryPage().getTicketId(1), ticketId, "Ticket Id does not same as search ticket id.");
        softAssert.assertNotNull(pages.getFrontendTicketHistoryPage().getTicketPriority(1), "Ticket priority must not be null.");
        softAssert.assertNotNull(pages.getFrontendTicketHistoryPage().getTicketQueue(1), "Ticket Queue must not be null.");
        softAssert.assertNotNull(pages.getFrontendTicketHistoryPage().getSourceApp(1),"Source App can not be empty.");
        pages.getFrontendTicketHistoryPage().clickClearSearchBox();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validate Add to Interaction Icon on Each Ticket", dataProviderClass = DataProviders.class,dependsOnMethods = "openCustomerInteraction")
    public void validateAddToInteraction() {
        selUtils.addTestcaseDescription("Validate Add to Interaction Icon on Each Ticket", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().goToViewHistory();
        pages.getViewHistory().goToTicketHistoryTab();
        pages.getFrontendTicketHistoryPage().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getFrontendTicketHistoryPage().validateAddToInteractionIcon(), "Add to interaction Icon does not found on ticket");
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate NFTR issue have ticket icon", dataProviderClass = DataProviders.class,dependsOnMethods = "openCustomerInteraction")
    public void validateNFTRIssue() {
        selUtils.addTestcaseDescription("Validate NFTR issue have ticket icon", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().goToViewHistory();
        pages.getViewHistory().clickOnInteractionsTab();
        pages.getViewHistory().waitTillLoaderGetsRemoved();
        Assert.assertTrue(pages.getViewHistory().clickOnTicketIcon(), "No NFTR issue found in interaction history tab");
        pages.getViewHistory().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getViewHistory().checkViewTicketPage(), "View ticket page does not open");
        pages.getViewHistory().clickCloseTicketTab();
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Verify the fields displayed for SMS channel.", dependsOnMethods = "openCustomerInteraction")
    public void validateSendSMSTab() {
        selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        pages.getCustomerProfilePage().clickOnAction();
        try {
            pages.getCustomerProfilePage().openSendSMSTab();
            pages.getSendSMS().waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(pages.getSendSMS().isPageLoaded(), "Send SMS tab does not open correctly");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Send SMS tab does not open correctly" + e.getMessage());
            }
            try {
                softAssert.assertTrue(pages.getSendSMS().isCategory(), "Category field does not displayed");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Category field does not displayed" + e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(pages.getSendSMS().isCustomerNumber(), "Customer number does not displayed");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Customer number does not displayed" + e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(pages.getSendSMS().isLanguage(), "Language field does not displayed");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Language field does not displayed" + e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(pages.getSendSMS().isTemplateName(), "Template name field does not display");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Template name field does not displayed" + e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(pages.getSendSMS().isMessageContentEditable(), "Message Content Editable");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Message Content Is Editable" + e.getCause());
            }
            try {
                softAssert.assertTrue(pages.getSendSMS().isSendBtnDisabled(), "Send SMS button is clickable");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Send SMS button is display on UI" + e.getCause());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Send SMS tab does not open properly. " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
