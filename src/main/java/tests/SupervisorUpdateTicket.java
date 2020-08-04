package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.TicketStateDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class SupervisorUpdateTicket extends BaseTest {

    String ticketId;

    @Test(priority = 1, description = "Supervisor SKIP Login ", dataProvider = "getTestData", dataProviderClass = DataProviders.class)
    public void agentSkipQueueLogin(Method method, TestDatabean Data) {
        ExtentTestManager.startTest("Supervisor SKIP Queue Login", "Supervisor SKIP Queue Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(),"Agent redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(),"Checking Queue Login Page have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(),"Checking Queue Login Page have Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Update Ticket", dataProvider = "ticketState", dataProviderClass = DataProviders.class)
    public void updateTicket(Method method, TicketStateDataBean ticketState) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Update Ticket", "Update Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
//        ticketListPage.writeTicketId(ticketId);
//        ticketListPage.clickedSearchBtn();
//        Thread.sleep(20000); // Update Particular Ticket
        ticketId = ticketListPage.getTicketIdvalue();
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(),"Verify the searched Ticket fetched Successfully");
        String selectedState = viewTicket.selectState(ticketState.getTicketStateName());
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.changeTicketTypeToClosed();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(ticketListPage.getTicketIdvalue(),ticketId,"Search Ticket Does not Fetched Correctly");
        //softAssert.assertEquals(ticketState.getTicketStateName(),ticketListPage.getStatevalue());
        softAssert.assertEquals(ticketListPage.getStatevalue(),selectedState,"Ticket Does not Updated to Selected State");
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "ALL")
    @Test(priority = 3,dependsOnMethods = "updateTicket", description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
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

    @Test(priority = 4,dependsOnMethods = "openCustomerInteraction", description = "Validate Re-open Icon on Closed Ticket")
    public void validateReopenIcon() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Re-open Icon on Closed Ticket: " + ticketId, "Validate Re-open Icon on Closed Ticket: " + ticketId);
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        FrontendTicketHistory ticketHistory=viewHistory.clickOnTicketHistory();
        ticketHistory.waitTillLoaderGetsRemoved();
        ticketHistory.writeTicketId(ticketId);
        ticketHistory.clickSearchBtn();
        ticketHistory.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        softAssert.assertEquals(ticketHistory.getTicketId(1),ticketId,"Ticket Id does not same as search ticket id.");
        ticketHistory.getTicketState(1);
        softAssert.assertTrue(ticketHistory.checkReopen(1),"Reopen icon does not found on ticket");
        softAssert.assertAll();
    }
}
