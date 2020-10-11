package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

public class SendSMSTest extends BaseTest {

    String templateName=null;
    String messageContent=null;
    String customerNumber=null;

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerNumber=Data.getCustomerNumber();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2,description = "Verify the fields displayed for SMS channel.")
    public void validateSendSMSTab(){
        ExtentTestManager.startTest("Validating the Send SMS Tab ", "Validating the send sms tab");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM homepage=new customerInteractionPagePOM(driver);
        homepage.waitTillLoaderGetsRemoved();
        homepage.clickOnAction();
        SendSMSPOM smsTab=homepage.openSendSMSTab();
        smsTab.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(smsTab.isPageLoaded(),"Send SMS tab does not open correctly");
        softAssert.assertTrue(smsTab.isCategory(),"Category field does not displayed");
        softAssert.assertTrue(smsTab.isCustomerNumber(),"Customer number does not displayed");
        softAssert.assertTrue(smsTab.isLanguage(),"Language field does not displayed");
        softAssert.assertTrue(smsTab.isTemplateName(),"Template name field does not display");
        softAssert.assertTrue(smsTab.isMessageContentEditable(),"Message Content Editable");
        softAssert.assertTrue(smsTab.isSendBtnDisabled(),"Send SMS button is clickable");
        softAssert.assertAll();
    }


    @Test(priority = 3,description = "Verify the frontend agent able to send SMS")
    public void sendSMS(){
        ExtentTestManager.startTest("Validating the Send SMS Tab ", "Validating the send sms tab");
        SoftAssert softAssert = new SoftAssert();
        SendSMSPOM smsTab=new SendSMSPOM(driver);
        softAssert.assertTrue(smsTab.isPageLoaded(),"Send SMS tab does not open correctly");
        Assert.assertEquals(smsTab.getCustomerNumber(),customerNumber,"Customer Number as not same as whose profile opened");
        smsTab.selectCategory();
        smsTab.waitTillLoaderGetsRemoved();
        templateName=smsTab.selectTemplateName();
        smsTab.waitTillLoaderGetsRemoved();
        smsTab.selectLanguage();
        smsTab.waitTillLoaderGetsRemoved();
        messageContent=smsTab.getMessageContent();
        softAssert.assertTrue(smsTab.clickSendSMSBtn(),"Send SMS does not enabled");
        smsTab.waitTillSuccessMessage();
        smsTab.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 4,description = "Check Sent SMS display in message history")
    public void checkSendMessageLog(){
        ExtentTestManager.startTest("Check Sent SMS display in message history ", "Check Sent SMS display in message history");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        viewHistory.waitTillLoaderGetsRemoved();
        MessageHistoryTabPOM messageHistory=viewHistory.clickOnMessageHistory();
        messageHistory.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(messageHistory.isMessageTypeColumn(),"Message Type Column does not display on UI");
        softAssert.assertTrue(messageHistory.isDateSentColumn(),"Date Sent Column does not display on UI");
        softAssert.assertTrue(messageHistory.isTemplateColumn(),"Template/Event Column does not display on UI");
        softAssert.assertTrue(messageHistory.isMessageLanguageColumn(),"Message Language Column does not display on UI");
        softAssert.assertTrue(messageHistory.isMessageTextColumn(),"Message Text Column does not display on UI");
        softAssert.assertTrue(messageHistory.isMessageTypeColumn(),"Message Type Column does not display on UI");
        softAssert.assertEquals(messageHistory.messageType(1).toLowerCase().trim(),config.getProperty("manualSMSType").toLowerCase().trim(),"Message Type is not manual");
        softAssert.assertEquals(messageHistory.templateEvent(1).toLowerCase().trim(),templateName.toLowerCase().trim(),"Template name not same as sent template.");
        softAssert.assertEquals(messageHistory.messageText(1).toLowerCase().trim(),messageContent.toLowerCase().trim(),"Message content not same as set message content.");
        softAssert.assertTrue(messageHistory.isActionBtnEnable(1),"Resend SMS icon does not enable");
        softAssert.assertTrue(!messageHistory.agentId(1).trim().equalsIgnoreCase("-"),messageHistory.agentId(1)+" :Agent id must not be empty");
        softAssert.assertTrue(!messageHistory.agentName(1).trim().equalsIgnoreCase("-"),messageHistory.agentName(1)+" :Agent name must not be empty");
        softAssert.assertAll();
    }


}
