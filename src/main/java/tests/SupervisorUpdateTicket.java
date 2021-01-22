package tests;

import API.APITest;
import POJO.SMSHistory.SMSHistoryList;
import POJO.SMSHistory.SMSHistoryPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class SupervisorUpdateTicket extends BaseTest {

    static String ticketId=null;
    String customerNumber = null;
    APITest api = new APITest();

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 0, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void setCustomerNumber(Method method, TestDatabean Data) {
        customerNumber = Data.getCustomerNumber();
    }

    @Test(priority = 1, description = "Supervisor SKIP Login ", dataProviderClass = DataProviders.class)
    public void agentSkipQueueLogin() {
        ExtentTestManager.startTest("Supervisor SKIP Queue Login", "Supervisor SKIP Queue Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(), "Agent redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(), "Checking Queue Login Page have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(), "Checking Queue Login Page have Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Update Ticket", dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void updateTicket(Method method, nftrDataBeans Data) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Update Ticket: " + Data.getIssueCode(), "Update Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        String selectedState = null;
        try {
            ticketListPage.writeTicketId(Data.getTicketNumber());
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber(), "Search Ticket does not found");
            try {
                ticketListPage.viewTicket();
                ticketListPage.waitTillLoaderGetsRemoved();
                try {
                    selectedState = viewTicket.selectState(data.ticketStateClosed());
                if (selectedState.equalsIgnoreCase(data.ticketStateClosed())) {
                    ticketListPage.waitTillLoaderGetsRemoved();
                    ticketListPage.changeTicketTypeToClosed();
                    ticketListPage.waitTillLoaderGetsRemoved();
                    ticketListPage.writeTicketId(Data.getTicketNumber());
                    ticketListPage.clickSearchBtn();
                    ticketListPage.waitTillLoaderGetsRemoved();
                    softAssert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber(), "Search Ticket Does not Fetched Correctly");
                    Assert.assertEquals(ticketListPage.getStatevalue(), selectedState, "Ticket Does not Updated to Selected State");
                    if(ticketId==null){
                        ticketId=Data.getTicketNumber();
                    }
                    SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
                    SMSHistoryList list = smsHistory.getResult().get(0);
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Message Sent after closure: " + list.getMessageText());
                    softAssert.assertTrue(list.getMessageText().contains(Data.getTicketNumber()), "Message Sent does not send for same ticket id which has been closed");
                    softAssert.assertEquals(list.getSmsType().toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message type is not system");
                    softAssert.assertFalse(list.isAction(), "Action button is not disabled");
                    softAssert.assertEquals(list.getTemplateName().toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
                } else {
                    viewTicket.clickBackButton();
                }
                }catch (TimeoutException | NoSuchElementException | ElementClickInterceptedException e){
                    softAssert.fail("Update Ticket does not complete due to error :" + e.fillInStackTrace());
                    viewTicket.clickBackButton();
                }
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                e.printStackTrace();
                softAssert.fail("Update Ticket does not complete due to error :" + e.fillInStackTrace());
            }
        } catch (TimeoutException | NoSuchElementException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Ticket id search not done due to following error: " + e.getMessage());
        }
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 3, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        if(ticketId==null) {
            SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
            SideMenuPOM.clickOnSideMenu();
            SideMenuPOM.clickOnName();
            customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
            customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
            customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
            softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        }else{
            softAssert.fail("No Ticket Id Closed. SKIP Validate Re-open Icon on Closed Ticket");
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "openCustomerInteraction", description = "Validate Re-open Icon on Closed Ticket")
    public void validateReopenIcon() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Re-open Icon on Closed Ticket: " + ticketId, "Validate Re-open Icon on Closed Ticket: " + ticketId);
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        FrontendTicketHistory ticketHistory = viewHistory.clickOnTicketHistory();
        ticketHistory.waitTillLoaderGetsRemoved();
        ticketHistory.writeTicketId(ticketId);
        ticketHistory.clickSearchBtn();
        ticketHistory.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        softAssert.assertEquals(ticketHistory.getTicketId(1), ticketId, "Ticket Id does not same as search ticket id.");
        ticketHistory.getTicketState(1);
        softAssert.assertTrue(ticketHistory.checkReopen(1), "Reopen icon does not found on ticket");
        softAssert.assertAll();
    }
}
