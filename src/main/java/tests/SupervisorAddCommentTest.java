package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.ViewTicketPagePOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class SupervisorAddCommentTest extends BaseTest {

    @Test(priority = 1, description = "Supervisor SKIP Login ", dataProvider = "getTestData", dataProviderClass = DataProvider.class)
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

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Supervisor Add Comment on Ticket")
    public void addCommentOnTicket(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Supervisor Add Comment on Ticket", "Add new Comment[Backend Supervisor]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
//        ticketListPage.writeTicketId(ticketId);
//        ticketListPage.clickedSearchBtn();
//        Thread.sleep(20000); // Add comment on Particular Ticket
        String ticketId = ticketListPage.getTicketIdvalue();
        String comment="Supervisor added comment on ticket using automation";
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(),"Verify the searched Ticket fetched Successfully");
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        softAssert.assertAll();
    }
}
