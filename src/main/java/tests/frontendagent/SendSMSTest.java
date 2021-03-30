package tests.frontendagent;

import com.airtel.cs.commonutils.DataProviders.DataProviders;
import com.airtel.cs.commonutils.DataProviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.MessageHistoryTabPOM;
import com.airtel.cs.pagerepository.pagemethods.SendSMSPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.customerInteractionPagePOM;
import com.airtel.cs.pagerepository.pagemethods.customerInteractionsSearchPOM;
import com.airtel.cs.pagerepository.pagemethods.viewHistoryPOM;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SendSMSTest extends BaseTest {

    String templateName = null;
    String messageContent = null;
    String customerNumber = null;

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerNumber = Data.getCustomerNumber();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify the fields displayed for SMS channel.", dependsOnMethods = "openCustomerInteraction")
    public void validateSendSMSTab() {
        ExtentTestManager.startTest("Validating the Send SMS Tab ", "Validating the send sms tab");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM homepage = new customerInteractionPagePOM(driver);
        homepage.waitTillLoaderGetsRemoved();
        homepage.clickOnAction();
        try {
            SendSMSPOM smsTab = homepage.openSendSMSTab();
            smsTab.waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(smsTab.isPageLoaded(), "Send SMS tab does not open correctly");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Send SMS tab does not open correctly" + e.getMessage());
            }
            try {
                softAssert.assertTrue(smsTab.isCategory(), "Category field does not displayed");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Category field does not displayed"+e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(smsTab.isCustomerNumber(), "Customer number does not displayed");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Customer number does not displayed"+e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(smsTab.isLanguage(), "Language field does not displayed");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Language field does not displayed"+e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(smsTab.isTemplateName(), "Template name field does not display");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Template name field does not displayed"+e.fillInStackTrace());
            }
            try {
                softAssert.assertTrue(smsTab.isMessageContentEditable(), "Message Content Editable");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Message Content Is Editable"+e.getCause());
            }
            try {
                softAssert.assertTrue(smsTab.isSendBtnDisabled(), "Send SMS button is clickable");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Send SMS button is display on UI"+e.getCause());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Send SMS tab does not open properly. " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }


    @Test(priority = 3, description = "Verify the frontend agent able to send SMS", dependsOnMethods = "validateSendSMSTab")
    public void sendSMS() {
        ExtentTestManager.startTest("Validating the Send SMS Tab ", "Validating the send sms tab");
        SoftAssert softAssert = new SoftAssert();
        SendSMSPOM smsTab = new SendSMSPOM(driver);
        softAssert.assertTrue(smsTab.isPageLoaded(), "Send SMS tab does not open correctly");
        Assert.assertEquals(smsTab.getCustomerNumber(), customerNumber, "Customer Number as not same as whose profile opened");
        try {
            smsTab.selectCategory();
            smsTab.waitTillLoaderGetsRemoved();
            try {
                templateName = smsTab.selectTemplateName();
                smsTab.waitTillLoaderGetsRemoved();
                try {
                    smsTab.selectLanguage();
                    smsTab.waitTillLoaderGetsRemoved();
                    try {
                        softAssert.assertTrue(smsTab.clickSendSMSBtn(), "Send SMS does not enabled");
                        smsTab.waitTillSuccessMessage();
                        smsTab.waitTillLoaderGetsRemoved();
                    } catch (NoSuchElementException | TimeoutException e) {
                        softAssert.fail("Not able to send sms to customer." + e.fillInStackTrace());
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to select Language: " + e.fillInStackTrace());
                    smsTab.clickOutside();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Not able to select Template Name: " + e.fillInStackTrace());
                smsTab.clickOutside();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to select category: " + e.fillInStackTrace());
            smsTab.clickOutside();
        }
        smsTab.clickOutside();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Check Sent SMS display in message history")
    public void checkSendMessageLog() {
        ExtentTestManager.startTest("Check Sent SMS display in message history ", "Check Sent SMS display in message history");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        viewHistory.waitTillLoaderGetsRemoved();
        MessageHistoryTabPOM messageHistory = viewHistory.clickOnMessageHistory();
        messageHistory.waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(messageHistory.isMessageTypeColumn(), "Message Type Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Type Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(messageHistory.isDateSentColumn(), "Date Sent Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Date Sent Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(messageHistory.isTemplateColumn(), "Template/Event Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Template/Event Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(messageHistory.isMessageLanguageColumn(), "Message Language Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Language Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(messageHistory.isMessageTextColumn(), "Message Text Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Text Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertTrue(messageHistory.isMessageTypeColumn(), "Message Type Column does not display on UI");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Type Column does not display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertEquals(messageHistory.messageType(1).toLowerCase().trim(), config.getProperty("manualSMSType").toLowerCase().trim(), "Message Type is not manual");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Type is not Display on UI:" + e.getMessage());
        }
        try {
            softAssert.assertEquals(messageHistory.templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template.");
        } catch (NullPointerException e) {
            softAssert.fail("SMS is not sent, Template name is empty." + e.getMessage());
        }
        try {
            messageContent=messageHistory.messageText(1);
            softAssert.assertNotNull(messageContent, "Message content can not be empty.");
        } catch (NullPointerException e) {
            softAssert.fail("SMS is not sent,Message Content is empty." + e.getMessage());
        }
        try {
            softAssert.assertTrue(messageHistory.isActionBtnEnable(1), "Resend SMS icon does not enable");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Resend SMS Icon does not display on UI.");
        }
        try {
            softAssert.assertTrue(!messageHistory.agentId(1).trim().equalsIgnoreCase("-"), "Agent id must not be empty");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Agent Id does not display on UI.");
        }
        try {
            softAssert.assertTrue(!messageHistory.agentName(1).trim().equalsIgnoreCase("-"), "Agent name must not be empty");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Agent Name does not display on UI.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Re Send SMS using action button in message history")
    public void ReSendMessageLog() {
        ExtentTestManager.startTest("Re Send SMS using action button in message history", "Re Send SMS using action button in message history");
        SoftAssert softAssert = new SoftAssert();
        MessageHistoryTabPOM messageHistory = new MessageHistoryTabPOM(driver);
        messageHistory.waitTillLoaderGetsRemoved();
        try {
            messageHistory.clickActionBtn(1);
            messageHistory.getPopUpTitle();
            Assert.assertTrue(messageHistory.getPopUpMessage().contains(customerNumber), "Pop up Message tab does not contain customer number");
            messageHistory.clickYesBtn();
            messageHistory.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(messageHistory.isMessageTypeColumn(), "Message Type Column does not display on UI");
            try {
                softAssert.assertEquals(messageHistory.messageType(1).toLowerCase().trim(), config.getProperty("manualSMSType").toLowerCase().trim(), "Message Type is not manual");
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Message Type is not Display on UI:" + e.getMessage());
            }
            try {
                softAssert.assertEquals(messageHistory.templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template.");
            } catch (NullPointerException e) {
                softAssert.fail("SMS is not sent, Template name is empty." + e.getMessage());
            }
            try {
                softAssert.assertEquals(messageHistory.messageText(1).toLowerCase().trim(), messageContent.toLowerCase().trim(), "Message content not same as set message content.");
            } catch (NullPointerException e) {
                softAssert.fail("SMS is not sent,Message Content is empty." + e.getMessage());
            }
            softAssert.assertTrue(messageHistory.isActionBtnEnable(1), "Resend SMS icon does not enable");
            softAssert.assertTrue(!messageHistory.agentId(1).trim().equalsIgnoreCase("-"), messageHistory.agentId(1) + " :Agent id must not be empty");
            softAssert.assertTrue(!messageHistory.agentName(1).trim().equalsIgnoreCase("-"), messageHistory.agentName(1) + " :Agent name must not be empty");
        }catch (ElementClickInterceptedException | NoSuchElementException | TimeoutException e){
            softAssert.fail("Not able to resend SMS: "+e.fillInStackTrace());
        }
        softAssert.assertAll();
    }


}
