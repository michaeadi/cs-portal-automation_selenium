package tests.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.FrontendTicketHistory;
import com.airtel.cs.pagerepository.pagemethods.SendSMSPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.CustomerProfilePage;
import com.airtel.cs.pagerepository.pagemethods.CustomerInteractionsSearchPOM;
import com.airtel.cs.pagerepository.pagemethods.ViewHistory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Method;

public class FrontendAgentTicketTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        CustomerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        CustomerProfilePage customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded(),"Dashboard page does not open.");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Ticket Meta Data for Frontend Agent ")
    public void validateTicket() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Ticket Meta Data for Frontend Agent", "Validate Ticket Meta Data for Frontend Agent ");
        SoftAssert softAssert = new SoftAssert();
        CustomerProfilePage customerInteractionPage = new CustomerProfilePage(driver);
        ViewHistory viewHistory = customerInteractionPage.clickOnViewHistory();
        FrontendTicketHistory ticketHistory = viewHistory.clickOnTicketHistory();
        ticketHistory.waitTillLoaderGetsRemoved();
        String ticketId=ticketHistory.getTicketId(1);
        ticketHistory.writeTicketId(ticketId);
        ticketHistory.clickSearchBtn();
        ticketHistory.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        softAssert.assertEquals(ticketHistory.getTicketId(1), ticketId, "Ticket Id does not same as search ticket id.");
        softAssert.assertNotNull(ticketHistory.getTicketPriority(1), "Ticket priority must not be null.");
        softAssert.assertNotNull(ticketHistory.getTicketQueue(1), "Ticket Queue must not be null.");
        ticketHistory.clickClearSearchBox();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validate Add to Interaction Icon on Each Ticket", dataProviderClass = DataProviders.class)
    public void validateAddToInteraction() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Add to Interaction Icon on Each Ticket", "Validate Add to Interaction Icon on Each Ticket");
        SoftAssert softAssert = new SoftAssert();
        CustomerProfilePage customerInteractionPage = new CustomerProfilePage(driver);
        ViewHistory viewHistory = customerInteractionPage.clickOnViewHistory();
        FrontendTicketHistory ticketHistory = viewHistory.clickOnTicketHistory();
        ticketHistory.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketHistory.validateAddToInteractionIcon(), "Add to interaction Icon does not found on ticket");
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate NFTR issue have ticket icon", dataProviderClass = DataProviders.class)
    public void validateNFTRIssue() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate NFTR issue have ticket icon", "Validate NFTR issue have ticket icon");
        SoftAssert softAssert = new SoftAssert();
        CustomerProfilePage customerInteractionPage = new CustomerProfilePage(driver);
        ViewHistory viewHistory = customerInteractionPage.clickOnViewHistory();
        viewHistory.clickOnInteractionsTab();
        viewHistory.waitTillLoaderGetsRemoved();
        Assert.assertTrue(viewHistory.clickOnTicketIcon(), "No NFTR issue found in interaction history tab");
        viewHistory.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(viewHistory.checkViewTicketPage(), "View ticket page does not open");
        viewHistory.clickCloseTicketTab();
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Verify the fields displayed for SMS channel.", dependsOnMethods = "openCustomerInteraction")
    public void validateSendSMSTab() {
        ExtentTestManager.startTest("Validating the Send SMS Tab ", "Validating the send sms tab");
        SoftAssert softAssert = new SoftAssert();
        CustomerProfilePage homepage = new CustomerProfilePage(driver);
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
}
