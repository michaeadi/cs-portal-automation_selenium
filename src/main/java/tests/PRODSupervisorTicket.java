package tests;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.ViewTicketPagePOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

public class PRODSupervisorTicket extends BaseTest {

    @Test(priority = 1, description = "Supervisor SKIP Login ", enabled = true)
    public void agentSkipQueueLogin() {
        ExtentTestManager.startTest("Supervisor SKIP Queue Login Test", "Supervisor SKIP Queue Login Test");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(), "Agent Does not redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(), "Queue Login Page Does Not have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(), "Queue Login Page have Does Not Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Supervisor Search Ticket", enabled = true)
    public void supervisorSearchTicket() {
        ExtentTestManager.startTest("Supervisor Search Ticket", "Supervisor Search Ticket");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        String ticketId = ticketListPage.getTicketIdvalue();
        try {

            ticketListPage.writeTicketId(ticketId);
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getTicketIdvalue(), ticketId, "Search Ticket does not found");

            try {
                softAssert.assertTrue(ticketListPage.isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
                softAssert.assertTrue(ticketListPage.isWorkGroupName(), "Ticket Meta Data Does Not Have Workgroup");
                softAssert.assertTrue(ticketListPage.isPrioritylabel(), "Ticket Meta Data Does Not Have Priority");
                softAssert.assertTrue(ticketListPage.isStateLabel(), "Ticket Meta Data Does Not Have State");
                softAssert.assertTrue(ticketListPage.isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
                softAssert.assertTrue(ticketListPage.isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
                softAssert.assertTrue(ticketListPage.isQueueLabel(), "Ticket Meta Data Does Not Have Queue");
                softAssert.assertTrue(ticketListPage.isIssueLabel(), "Ticket Meta Data Does Not Have Issue");
                softAssert.assertTrue(ticketListPage.isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
                softAssert.assertTrue(ticketListPage.isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
                softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
                softAssert.assertTrue(ticketListPage.isCodeLabel(), "Ticket Meta Data Does Not Have Code");
            } catch (NullPointerException | TimeoutException | NoSuchElementException e) {
                e.printStackTrace();
                softAssert.fail("Ticket meta data Assertion failed: " + e.getMessage());
            }
        } catch (TimeoutException | NoSuchElementException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Ticket id search not done for following error: " + e.getMessage());
        }
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Supervisor View Ticket", enabled = true)
    public void supervisorViewTicket() {
        ExtentTestManager.startTest("Supervisor View Ticket", "Supervisor View Ticket");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        String ticketId = ticketListPage.getTicketIdvalue();
        ViewTicketPagePOM viewTicketPagePOM=new ViewTicketPagePOM(driver);
        try {
            ticketListPage.writeTicketId(ticketId);
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getTicketIdvalue(), ticketId, "Search Ticket does not found");
            ticketListPage.viewTicket();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(viewTicketPagePOM.getTicketId(), ticketId, "View Ticket does not Same as Open.");
            viewTicketPagePOM.clickBackButton();
        } catch (TimeoutException | NoSuchElementException | AssertionError | InterruptedException e) {
            e.printStackTrace();
            softAssert.fail("Ticket id search not done for following error: " + e.getMessage());
        }
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }


}
