package tests;

import Utils.DataProviders.DataProvider;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.assignToAgentPOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class AssignToAgentTicketTest extends BaseTest {


    @Test(priority = 1, description = "Supervisor SKIP Login ")
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

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Assign Ticket to Agent", dataProviderClass = DataProvider.class)
    public void assignTicketToAgent(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Assign Ticket to Agent");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        String ticketId = ticketListPage.getTicketIdvalue();
        softAssert.assertTrue(ticketListPage.isTicketIdLabel(), "Ticket Meta Data Have Ticket Id");
        softAssert.assertTrue(ticketListPage.isWorkGroupName(), "Ticket Meta Data Have Workgroup");
        softAssert.assertTrue(ticketListPage.isPrioritylabel(), "Ticket Meta Data Have Priority");
        softAssert.assertTrue(ticketListPage.isStateLabel(), "Ticket Meta Data Have State");
        softAssert.assertTrue(ticketListPage.isCreationdateLabel(), "Ticket Meta Data Have Creation Date");
        softAssert.assertTrue(ticketListPage.isCreatedbyLabel(), "Ticket Meta Data Have Created By");
        softAssert.assertTrue(ticketListPage.isQueueLabel(), "Ticket Meta Data Have Queue");
        softAssert.assertTrue(ticketListPage.isIssueLabel(), "Ticket Meta Data Have Issue");
        softAssert.assertTrue(ticketListPage.isIssueTypeLabel(), "Ticket Meta Data Have Issue Type");
        softAssert.assertTrue(ticketListPage.isSubTypeLabel(), "Ticket Meta Data Have Issue Sub Type");
        softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(), "Ticket Meta Data Have Issue Sub Sub Type");
        softAssert.assertTrue(ticketListPage.isCodeLabel(), "Ticket Meta Data Have Code");
        String ticketQueue = ticketListPage.getqueueValue();
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent(), "Assign to Agent Button Available");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(), "Transfer to Queue Button Available");
        ticketListPage.clickAssigntoAgent();
        softAssert.assertTrue(assignTicket.validatePageTitle(), "Assign to Agent tab Open");
        softAssert.assertEquals(assignTicket.getQueueName(), ticketQueue, "Verify Assign to Agent tab Queue");
        int availableCount = assignTicket.getAvailableSlot(); //Before Assignment
        String agentAUUID = assignTicket.getAgentAuuid();
        assignTicket.ClickedAssignBtn();
        assignTicket.getInfoMessage();
        assignTicket.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(assignTicket.getAvailableSlot(), availableCount - 1, "Check Available Slot Decrease");
        softAssert.assertAll();
    }
}
