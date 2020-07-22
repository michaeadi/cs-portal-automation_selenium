package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class SupervisorSearchTicket extends BaseTest {


    @Test(priority = 1, description = "Supervisor SKIP Login ")
    public void agentSkipQueueLogin(Method method) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Supervisor SKIP Queue Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(),"Agent redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(),"Checking Queue Login Page have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(),"Checking Queue Login Page have Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        Thread.sleep(10000);
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentSkipQueueLogin", description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProvider.class)
    public void SearchTicket(Method method, nftrDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Search Ticket & Validate Ticket Meta Data");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.writeTicketId(Data.getTicketNumber());
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber());
        softAssert.assertTrue(ticketListPage.isTicketIdLabel(),"Ticket Meta Data Have Ticket Id");
        softAssert.assertTrue(ticketListPage.isWorkGroupName(),"Ticket Meta Data Have Workgroup");
        softAssert.assertTrue(ticketListPage.isPrioritylabel(),"Ticket Meta Data Have Priority");
        softAssert.assertTrue(ticketListPage.isStateLabel(),"Ticket Meta Data Have State");
        softAssert.assertTrue(ticketListPage.isCreationdateLabel(),"Ticket Meta Data Have Creation Date");
        softAssert.assertTrue(ticketListPage.isCreatedbyLabel(),"Ticket Meta Data Have Created By");
        softAssert.assertTrue(ticketListPage.isQueueLabel(),"Ticket Meta Data Have Queue");
        softAssert.assertTrue(ticketListPage.isIssueLabel(),"Ticket Meta Data Have Issue");
        softAssert.assertTrue(ticketListPage.isIssueTypeLabel(),"Ticket Meta Data Have Issue Type");
        softAssert.assertTrue(ticketListPage.isSubTypeLabel(),"Ticket Meta Data Have Issue Sub Type");
        softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(),"Ticket Meta Data Have Issue Sub Sub Type");
        softAssert.assertTrue(ticketListPage.isCodeLabel(),"Ticket Meta Data Have Code");
        softAssert.assertEquals(ticketListPage.getIssueValue().toLowerCase().trim(), Data.getIssue().toLowerCase().trim(),
                "Issue Validated");
        softAssert.assertEquals(ticketListPage.getIssueTypeValue().toLowerCase().trim(), Data.getIssueType().toLowerCase().trim(),
                "Issue Type Validated");
        softAssert.assertEquals(ticketListPage.getSubTypeValue().toLowerCase().trim(), Data.getIssueSubType().toLowerCase().trim(),
                "Issue Sub Type Validated");
        softAssert.assertEquals(ticketListPage.getsubSubTypeValue().toLowerCase().trim(), Data.getIssueSubSubType().toLowerCase().trim(),
                "Issue Sub Sub Type Validated");
        softAssert.assertEquals(ticketListPage.getCodeValue().toLowerCase().trim(), Data.getIssueCode().toLowerCase().trim(),
                "Issue Code Validated");
        softAssert.assertEquals(ticketListPage.getWorkGroupName().toLowerCase().trim(), Data.getWorkgroup().toLowerCase().trim(),
                "Ticket WorkGroup Validated");
        softAssert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), Data.getAssignmentQueue().toLowerCase().trim(),
                "Ticket Queue Validated");
        softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                "Ticket Priority Validated");
        softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                "Issue Type Validated");
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate Assign to Agent and Transfer to Queue Option", dataProviderClass = DataProvider.class)
    public void validateCheckBox(Method method) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Validate Assign to Agent and Transfer to Queue Option om Open Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(ticketListPage.checkOpenTicketStateType(),"Check User on Open Ticket State Type");
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent(),"Check User have Option to Assign to Agent");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(),"Check User have Option to Transfer to Queue");
        softAssert.assertAll();
    }


}
