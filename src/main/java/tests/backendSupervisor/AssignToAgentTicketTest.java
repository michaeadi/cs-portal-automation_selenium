package tests.backendSupervisor;

import API.APIEndPoints;
import POJO.TicketList.TicketPOJO;
import Utils.DataProviders.DataProviders;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.FilterTabPOM;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;
import pages.assignToAgentPOM;
import tests.frontendAgent.BaseTest;

import java.lang.reflect.Method;

public class AssignToAgentTicketTest extends BaseTest {

    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

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

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Assign Ticket to Agent", dataProviderClass = DataProviders.class)
    public void assignTicketToAgent(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Assign Ticket to Agent", "Supervisor Perform Assign Ticket to Agent");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String auuid=null;
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketListPage.isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
        softAssert.assertTrue(ticketListPage.isWorkGroupName(), "Ticket Meta Data Does Not  Have Workgroup");
        softAssert.assertTrue(ticketListPage.isPrioritylabel(), "Ticket Meta Data  Does Not  Have Priority");
        softAssert.assertTrue(ticketListPage.isStateLabel(), "Ticket Meta Data Does Not  Have State");
        softAssert.assertTrue(ticketListPage.isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
        softAssert.assertTrue(ticketListPage.isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
        softAssert.assertTrue(ticketListPage.isQueueLabel(), "Ticket Meta Data Have Does Not Queue");
        softAssert.assertTrue(ticketListPage.isIssueLabel(), "Ticket Meta Data Have Does Not Issue");
        softAssert.assertTrue(ticketListPage.isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
        softAssert.assertTrue(ticketListPage.isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
        softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
        softAssert.assertTrue(ticketListPage.isCodeLabel(), "Ticket Meta Data Does Not Have Code");
        String ticketQueue = ticketListPage.getqueueValue();
        String assigneeAUUID = ticketListPage.getAssigneeAUUID();
        String ticketId = ticketListPage.getTicketIdvalue();
        TicketPOJO ticketAPI=api.ticketMetaDataTest(ticketId);
        if(ticketAPI.getResult().getAssignee()==null){
            softAssert.assertTrue(ticketListPage.isNotAssigneeDisplay(),"Not Assigned does not display correctly");
        }else {
            softAssert.assertEquals(ticketListPage.getAssigneeName().toLowerCase().trim(), ticketAPI.getResult().getAssignee().toLowerCase().trim(), "Assignee pan does not display on UI Correctly.");
        }
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent(), "Assign to Agent Button  Does Not Available");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(), "Transfer to Queue Button  Does Not Available");
        ticketListPage.clickAssigntoAgent();
        softAssert.assertTrue(assignTicket.validatePageTitle(), "Assign to Agent tab Does Not Open");
        softAssert.assertEquals(assignTicket.getQueueName(), ticketQueue, "Assign to Agent tab Queue does not Open Correctly");
        try {
            auuid = assignTicket.ticketAssignedToAgent(assigneeAUUID).trim();
        }
        catch (ElementClickInterceptedException | NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to assign ticket to agent " + e.fillInStackTrace());
            assignTicket.closeAssignTab();
            ticketListPage.clickCheckbox();
        }
        ticketListPage.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        ticketListPage.writeTicketIdSecond(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(ticketListPage.getAssigneeAUUID().trim(), auuid, "Ticket does not assigned to agent");
        if (ticketListPage.getAssigneeAUUID().trim().equalsIgnoreCase(auuid)) {
            ExtentTestManager.getTest().log(LogStatus.INFO, "Ticket unassigned from '" + assigneeAUUID + "' and Ticket Assigned to '" + auuid + "'");
            ExtentTestManager.getTest().log(LogStatus.INFO, "Validated Ticket is Assigned to User Successfully");
        } else {
            ExtentTestManager.getTest().log(LogStatus.INFO, "Ticket unassigned from '" + assigneeAUUID + "' and Ticket Assigned to '" + auuid + "'");
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Ticket does not Assigned to User Correctly");
        }
        softAssert.assertAll();
    }
}
