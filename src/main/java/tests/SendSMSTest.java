package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SendSMSPOM;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;

public class SendSMSTest extends BaseTest {

    String templateName=null;
    String messageContent=null;

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
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

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 3,description = "Verify the frontend agent able to send SMS",dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void sendSMS(TestDatabean Data){
        ExtentTestManager.startTest("Validating the Send SMS Tab ", "Validating the send sms tab");
        SoftAssert softAssert = new SoftAssert();
        SendSMSPOM smsTab=new SendSMSPOM(driver);
        softAssert.assertTrue(smsTab.isPageLoaded(),"Send SMS tab does not open correctly");
        Assert.assertEquals(smsTab.getCustomerNumber(),Data.getCustomerNumber(),"Customer Number as not same as whose profile opened");
        smsTab.selectCategory();
        smsTab.waitTillLoaderGetsRemoved();
        templateName=smsTab.selectTemplateName();
        smsTab.waitTillLoaderGetsRemoved();
        smsTab.selectLanguage();
        smsTab.waitTillLoaderGetsRemoved();
        messageContent=smsTab.getMessageContent();
        softAssert.assertTrue(smsTab.clickSendSMSBtn(),"Send SMS does not enabled");
        smsTab.waitTillSuccessMessage();
        softAssert.assertAll();
    }



}
