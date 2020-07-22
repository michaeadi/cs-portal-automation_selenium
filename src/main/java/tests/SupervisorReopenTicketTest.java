package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TicketStateDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class SupervisorReopenTicketTest extends BaseTest {

    @Test(priority = 1, description = "Supervisor SKIP Login ", enabled = true)
    public void agentSkipQueueLogin(Method method) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Supervisor SKIP Queue Login Test");
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

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", dataProvider = "ReOpenState", description = "Supervisor Reopen Ticket", dataProviderClass = DataProvider.class)
    public void ReopenTicket(Method method, TicketStateDataBean reopen) throws InterruptedException {
        ExtentTestManager.startTest("Supervisor Reopen Ticket", "Reopen Ticket as Supervisor");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToClosed();
        ticketListPage.waitTillLoaderGetsRemoved();
        String ticketId = ticketListPage.getTicketIdvalue();
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isReopenBtn(), "Reopen Button Available");
        ticketListPage.ClickReopenButton();
        ticketListPage.addReopenComment("Reopen Comment Using Automation");
        ticketListPage.submitReopenReq();
        Thread.sleep(3000);
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        Assert.assertEquals(reopen.getTicketStateName().toLowerCase().trim(), ticketListPage.getStatevalue().toLowerCase().trim(), "Validate Ticket Reopen in Correct state");
        softAssert.assertAll();
    }
}
