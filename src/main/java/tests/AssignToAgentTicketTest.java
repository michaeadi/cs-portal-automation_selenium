package tests;

import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.agentLoginPagePOM;
import pages.assignToAgentPOM;
import pages.loginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class AssignToAgentTicketTest extends BaseTest {

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

    @Test(priority = 2,dependsOnMethods = "LoggingIN", description = "Supervisor Login ", dataProvider = "getTestData",enabled = true)
    public void agentSkipQueueLogin(Method method, TestDatabean Data) throws InterruptedException {
        agentLoginPagePOM AgentLoginPagePOM = new agentLoginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Supervisor Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(30000);
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        AgentLoginPagePOM.clickSkipBtn();
        Thread.sleep(20000);
        Assert.assertEquals(driver.getTitle(),config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3,dependsOnMethods = "agentSkipQueueLogin",description = "Assign Ticket to Agent",enabled = true)
    public void assignTicketToAgent(Method method) throws InterruptedException{
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Assign Ticket to Agent");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.getTicketIdvalue();
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
        String ticketQueue = ticketListPage.getqueueValue();
        softAssert.assertTrue(ticketListPage.checkOpenTicketStateType());
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent());
        softAssert.assertTrue(ticketListPage.isTransferToQueue());
        ticketListPage.clickAssigntoAgent();
        softAssert.assertTrue(assignTicket.validatePageTitle());
        softAssert.assertEquals(assignTicket.getQueueName(),ticketQueue);
        assignTicket.ClickedAssignBtn();
        //Thread.sleep(2000);
        assignTicket.getInfoMessage();
        Thread.sleep(20000);
        softAssert.assertAll();

    }
}
