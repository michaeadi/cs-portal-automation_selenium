package tests;

import Utils.DataProviders.nftrDataBeans;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.agentLoginPagePOM;
import pages.loginPagePOM;
import pages.supervisorTicketListPagePOM;
import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import java.lang.reflect.Method;

public class SupervisorSearchTicket extends BaseTest {

    @Test(priority = 1, description = "Logging IN ", dataProvider = "getTestData")
    public void LoggingIN(Method method, TestDatabean Data) {
        loginPagePOM loginPagePOM = new loginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM.openBaseURL(config.getProperty("baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty("baseurl"), "URl isn't as expected");
        loginPagePOM.enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(Data.getPassword());
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Supervisor Login ", dataProvider = "getTestData",enabled = true)
    public void agentSkipQueueLogin(Method method, TestDatabean Data) throws InterruptedException {
        agentLoginPagePOM AgentLoginPagePOM = new agentLoginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Supervisor Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(),config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3,dependsOnMethods = "agentSkipQueueLogin", description = "Ticket Search ", dataProvider = "ticketId",enabled = true)
    public void SearchTicket(Method method, nftrDataBeans Data) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage= new supervisorTicketListPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.writeTicketId(Data.getTicketNumber());
        ticketListPage.clickedSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        Assert.assertEquals(ticketListPage.getTicketIdvalue(),Data.getTicketNumber());
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
        softAssert.assertEquals(ticketListPage.getIssueValue(),Data.getIssue());
        softAssert.assertEquals(ticketListPage.getIssueTypeValue(),Data.getIssueType());
        softAssert.assertEquals(ticketListPage.getSubTypeValue(),Data.getIssueSubType());
        softAssert.assertEquals(ticketListPage.getsubSubTypeValue(),Data.getIssueSubSubType());
        softAssert.assertEquals(ticketListPage.getCodeValue(),Data.getIssueCode());
        softAssert.assertEquals(ticketListPage.getWorkGroupName(),Data.getWorkgroup());
        softAssert.assertEquals(ticketListPage.getqueueValue(),Data.getAssignmentQueue());
        softAssert.assertEquals(ticketListPage.getPriorityValue(),Data.getPriority());
        softAssert.assertEquals(ticketListPage.getPriorityValue(),Data.getPriority());
        softAssert.assertAll();
        Thread.sleep(30000);
    }

    @Test(priority = 4,dependsOnMethods = "SearchTicket", description = "Validate Assign to Agent and Transfer to Queue Option",enabled = true)
    public void validateCheckBox(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Validate Assign to Agent and Transfer to Queue Option om Open Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(ticketListPage.checkOpenTicketStateType());
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent());
        softAssert.assertTrue(ticketListPage.isTransferToQueue());
        softAssert.assertAll();
    }


}
