package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.ViewTicketPagePOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class SupervisorAddCommentTest extends BaseTest {

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard(Method method) {
        ExtentTestManager.startTest("Open Supervisor Dashboard", "Open Supervisor Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Supervisor Add Comment on Ticket")
    public void addCommentOnTicket(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Supervisor Add Comment on Ticket", "Add new Comment[Backend Supervisor]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        try {
            String ticketId = ticketListPage.getTicketIdvalue();
            String comment = "Supervisor added comment on ticket using automation";
            try {
                ticketListPage.viewTicket();
                viewTicket.waitTillLoaderGetsRemoved();
                Assert.assertEquals(ticketId, viewTicket.getTicketId(), "Verify the searched Ticket fetched Successfully");
                try {
                    viewTicket.addComment(comment);
                    viewTicket.clickAddButton();
                    viewTicket.waitTillLoaderGetsRemoved();
                    viewTicket.validateAddedComment(comment);
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to add comment on ticket :" + e.fillInStackTrace());
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Not able to view ticket properly: " + e.fillInStackTrace());
                viewTicket.clickBackButton();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("No Ticket Found in open state " + e.fillInStackTrace());
        }
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

    @Test(priority = 5,dependsOnMethods = "addCommentOnTicket", description = "Validate Delete comment as Backend Supervisor")
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
