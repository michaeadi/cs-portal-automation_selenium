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


    @Test(priority = 1, description = "SideMenu ")
    public void agentSkipQueueLogin(Method method) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSkipBtn();
        Thread.sleep(10000);
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentSkipQueueLogin", description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProvider.class)
    public void SearchTicket(Method method, nftrDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.writeTicketId(Data.getTicketNumber());
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber());
        softAssert.assertTrue(ticketListPage.isTicketIdLabel());
        softAssert.assertTrue(ticketListPage.isWorkGroupName());
        softAssert.assertTrue(ticketListPage.isPrioritylabel());
        softAssert.assertTrue(ticketListPage.isStateLabel());
        softAssert.assertTrue(ticketListPage.isCreationdateLabel());
        softAssert.assertTrue(ticketListPage.isCreatedbyLabel());
        softAssert.assertTrue(ticketListPage.isQueueLabel());
        softAssert.assertTrue(ticketListPage.isIssueLabel());
        softAssert.assertTrue(ticketListPage.isIssueTypeLabel());
        softAssert.assertTrue(ticketListPage.isSubTypeLabel());
        softAssert.assertTrue(ticketListPage.isSubSubTypeLabel());
        softAssert.assertTrue(ticketListPage.isCodeLabel());
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
        softAssert.assertTrue(ticketListPage.checkOpenTicketStateType());
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent());
        softAssert.assertTrue(ticketListPage.isTransferToQueue());
        softAssert.assertAll();
    }


}
