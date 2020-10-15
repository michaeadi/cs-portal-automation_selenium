package tests;

import Utils.DataProviders.DataProviders;
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
import java.time.LocalDateTime;

public class SupervisorAddCommentTest extends BaseTest {

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
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(), "Agent redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(), "Checking Queue Login Page have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(), "Checking Queue Login Page have Submit button");
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
        String comment = "Supervisor added comment on ticket using automation";
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(), "Verify the searched Ticket fetched Successfully");
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "addCommentOnTicket", description = "Validate issue comment as supervisor")
    public void validateIssueComment(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Validate issue comment as supervisor", "Validate issue comment [Backend Supervisor]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewTicket.validateCommentType(config.getProperty("issueComment")), "Issue Comment does not found on ticket");
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "addCommentOnTicket", description = "Validate Edit comment as Backend Supervisor")
    public void editComment() throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Validate Edit comment as Backend Supervisor", "Validate Edit comment [Backend Supervisor]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String comment = "Adding updated comment using automation";
        viewTicket.openEditCommentBox();
        viewTicket.clearCommentBox();
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Validate Delete comment as Backend Supervisor")
    public void deleteLastAddedComment() throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Validate Delete comment as Backend Supervisor", "Validate Delete comment [Backend Supervisor]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String comment = "Adding Comment to test Delete comment Flow " + LocalDateTime.now();
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        viewTicket.openDeleteComment();
        viewTicket.clickContinueButton();
        viewTicket.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(viewTicket.isCommentDelete(comment), "Deleted comment found on ticket");
        softAssert.assertAll();
    }
}
