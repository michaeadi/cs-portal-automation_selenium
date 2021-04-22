package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SendSMSTest extends Driver {

    String templateName = null;
    String messageContent = null;
    String customerNumber = null;

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
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        this.customerNumber = customerNumber;
        pages.getSideMenu().openCustomerInteractionPage();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        pages.getMsisdnSearchPage().enterNumber(customerNumber);
        pages.getMsisdnSearchPage().clickOnSearch();
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify the fields displayed for SMS channel.", dependsOnMethods = "openCustomerInteraction")
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


    @Test(priority = 3, description = "Verify the frontend agent able to send SMS", dependsOnMethods = "validateSendSMSTab")
    public void sendSMS() {
        selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getSendSMS().isPageLoaded(), "Send SMS tab does not open correctly");
        Assert.assertEquals(pages.getSendSMS().getCustomerNumber(), customerNumber, "Customer Number as not same as whose profile opened");
        try {
            pages.getSendSMS().selectCategory();
            pages.getSendSMS().waitTillLoaderGetsRemoved();
            try {
                templateName = pages.getSendSMS().selectTemplateName();
                pages.getSendSMS().waitTillLoaderGetsRemoved();
                try {
                    pages.getSendSMS().selectLanguage();
                    pages.getSendSMS().waitTillLoaderGetsRemoved();
                    try {
                        softAssert.assertTrue(pages.getSendSMS().clickSendSMSBtn(), "Send SMS does not enabled");
                        pages.getSendSMS().waitTillSuccessMessage();
                        pages.getSendSMS().waitTillLoaderGetsRemoved();
                    } catch (NoSuchElementException | TimeoutException e) {
                        softAssert.fail("Not able to send sms to customer." + e.fillInStackTrace());
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to select Language: " + e.fillInStackTrace());
                    pages.getSendSMS().clickOutside();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Not able to select Template Name: " + e.fillInStackTrace());
                pages.getSendSMS().clickOutside();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to select category: " + e.fillInStackTrace());
            pages.getSendSMS().clickOutside();
        }
        pages.getSendSMS().clickOutside();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Check Sent SMS display in message history",dependsOnMethods = "openCustomerInteraction")
    public void checkSendMessageLog() {
        selUtils.addTestcaseDescription("Check Sent SMS display in message history ", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().goToViewHistory();
        pages.getViewHistory().waitTillLoaderGetsRemoved();
        pages.getViewHistory().clickOnMessageHistory();
        pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTypeColumn(), "Message Type Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Type Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isDateSentColumn(), "Date Sent Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Date Sent Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isTemplateColumn(), "Template/Event Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Template/Event Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isMessageLanguageColumn(), "Message Language Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Language Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTextColumn(), "Message Text Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Text Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTypeColumn(), "Message Type Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Type Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertEquals(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), config.getProperty("manualSMSType").toLowerCase().trim(), "Message Type is not manual");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Type is not Display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertEquals(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template.");
        } catch (NullPointerException e) {
            softAssert.fail("SMS is not sent, Template name is empty." + e.getMessage());
        }
        try {
            messageContent = pages.getMessageHistoryPage().messageText(1);
            softAssert.assertNotNull(messageContent, "Message content can not be empty.");
        } catch (NullPointerException e) {
            softAssert.fail("SMS is not sent,Message Content is empty." + e.getMessage());
        }
        try {
            softAssert.assertTrue(pages.getMessageHistoryPage().isActionBtnEnable(1), "Resend SMS icon does not enable");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Resend SMS Icon does not display on UI.");
        }
        try {
            softAssert.assertTrue(!pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), "Agent id must not be empty");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Agent Id does not display on UI.");
        }
        try {
            softAssert.assertTrue(!pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), "Agent name must not be empty");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Agent Name does not display on UI.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Re Send SMS using action button in message history",dependsOnMethods = "checkSendMessageLog")
    public void reSendMessageLog() {
        selUtils.addTestcaseDescription("Re Send SMS using action button in message history", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
        try {
            pages.getMessageHistoryPage().clickActionBtn(1);
            pages.getMessageHistoryPage().getPopUpTitle();
            Assert.assertTrue(pages.getMessageHistoryPage().getPopUpMessage().contains(customerNumber), "Pop up Message tab does not contain customer number");
            pages.getMessageHistoryPage().clickYesBtn();
            pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTypeColumn(), "Message Type Column does not display on UI");
            try {
                softAssert.assertEquals(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), config.getProperty("manualSMSType").toLowerCase().trim(), "Message Type is not manual");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Message Type is not Display on UI:" + e.getMessage());
            }
            try {
                softAssert.assertEquals(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template.");
            } catch (NullPointerException e) {
                softAssert.fail("SMS is not sent, Template name is empty." + e.getMessage());
            }
            try {
                softAssert.assertEquals(pages.getMessageHistoryPage().messageText(1).toLowerCase().trim(), messageContent.toLowerCase().trim(), "Message content not same as set message content.");
            } catch (NullPointerException e) {
                softAssert.fail("SMS is not sent,Message Content is empty." + e.getMessage());
            }
            softAssert.assertTrue(pages.getMessageHistoryPage().isActionBtnEnable(1), "Resend SMS icon does not enable");
            softAssert.assertTrue(!pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), pages.getMessageHistoryPage().agentId(1) + " :Agent id must not be empty");
            softAssert.assertTrue(!pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), pages.getMessageHistoryPage().agentName(1) + " :Agent name must not be empty");
        } catch (ElementClickInterceptedException | NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to resend SMS: " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
