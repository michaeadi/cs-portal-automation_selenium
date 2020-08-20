package tests;

import Utils.DataProviders.DataProviders;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class AssignToAgentTicketTest extends BaseTest {


    @Test(priority = 1, description = "Supervisor SKIP Login ")
    public void agentSkipQueueLogin(Method method){
        ExtentTestManager.startTest("Supervisor SKIP Queue Login Test", "Supervisor SKIP Queue Login Test");
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

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Assign Ticket to Agent", dataProviderClass = DataProviders.class)
    public void assignTicketToAgent(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Assign Ticket to Agent", "Supervisor Perform Assign Ticket to Agent");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
//        ticketListPage.clickFilter();
//        ticketListPage.waitTillLoaderGetsRemoved();
//        filterTab.clickUnAssignedFilter();
//        filterTab.clickApplyFilter();
//        ticketListPage.waitTillLoaderGetsRemoved();
//        String ticketId= ticketListPage.getTicketIdvalue();
//        ticketListPage.resetFilter();
//        ticketListPage.waitTillLoaderGetsRemoved();
//        ticketListPage.writeTicketId(ticketId);
//        ticketListPage.clickSearchBtn();
//        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketListPage.isTicketIdLabel(),"Ticket Meta Data Does Not Have Ticket Id");
        softAssert.assertTrue(ticketListPage.isWorkGroupName(),"Ticket Meta Data Does Not  Have Workgroup");
        softAssert.assertTrue(ticketListPage.isPrioritylabel(),"Ticket Meta Data  Does Not  Have Priority");
        softAssert.assertTrue(ticketListPage.isStateLabel(),"Ticket Meta Data Does Not  Have State");
        softAssert.assertTrue(ticketListPage.isCreationdateLabel(),"Ticket Meta Data Does Not Have Creation Date");
        softAssert.assertTrue(ticketListPage.isCreatedbyLabel(),"Ticket Meta Data Does Not Have Created By");
        softAssert.assertTrue(ticketListPage.isQueueLabel(),"Ticket Meta Data Have Does Not Queue");
        softAssert.assertTrue(ticketListPage.isIssueLabel(),"Ticket Meta Data Have Does Not Issue");
        softAssert.assertTrue(ticketListPage.isIssueTypeLabel(),"Ticket Meta Data Does Not Have Issue Type");
        softAssert.assertTrue(ticketListPage.isSubTypeLabel(),"Ticket Meta Data Does Not Have Issue Sub Type");
        softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(),"Ticket Meta Data Does Not Have Issue Sub Sub Type");
        softAssert.assertTrue(ticketListPage.isCodeLabel(),"Ticket Meta Data Does Not Have Code");
        String ticketQueue = ticketListPage.getqueueValue();
        String assigneeAUUID=ticketListPage.getAssigneeAUUID();
        String ticketId=ticketListPage.getTicketIdvalue();
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent(),"Assign to Agent Button  Does Not Available");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(),"Transfer to Queue Button  Does Not Available");
        ticketListPage.clickAssigntoAgent();
        softAssert.assertTrue(assignTicket.validatePageTitle(),"Assign to Agent tab Does Not Open");
        softAssert.assertEquals(assignTicket.getQueueName(), ticketQueue,"Assign to Agent tab Queue does not Open Correctly");
        String auuid=assignTicket.ticketAssignedToAgent(assigneeAUUID).trim();
        ticketListPage.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        ticketListPage.writeTicketIdSecond(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(ticketListPage.getAssigneeAUUID().trim(),auuid,"Ticket does not assigned to agent");
        if(ticketListPage.getAssigneeAUUID().trim().equalsIgnoreCase(auuid)) {
            ExtentTestManager.getTest().log(LogStatus.INFO, "Ticket unassigned from <" + assigneeAUUID + "> and Ticket Assigned to <" + auuid + ">");
            ExtentTestManager.getTest().log(LogStatus.INFO, "Validated Ticket is Assigned to User Successfully");
        }else{
            ExtentTestManager.getTest().log(LogStatus.INFO, "Ticket unassigned from <" + assigneeAUUID + "> and Ticket Assigned to <" + auuid + ">");
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Ticket does not Assigned to User Correctly");
        }
        softAssert.assertAll();
    }
}
