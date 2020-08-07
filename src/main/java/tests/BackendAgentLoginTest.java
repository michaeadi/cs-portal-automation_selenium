package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import Utils.PassUtils;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BackendAgentTicketListPOM;
import pages.agentLoginPagePOM;
import pages.loginPagePOM;

import java.lang.reflect.Method;

public class BackendAgentLoginTest extends BaseTest {
    @DataProviders.User(UserType = "BA")
    @Test(priority = 1, description = "Logging IN ", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void LoggingIN(Method method, TestDatabean Data) {
        ExtentTestManager.startTest("Logging Into Portal", "Logging Into Portal with AUUID :  " + Data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM loginPagePOM = new loginPagePOM(driver);
        loginPagePOM.openBaseURL(config.getProperty(tests.BaseTest.Env + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(tests.BaseTest.Env + "-baseurl"), "URl isn't as expected");
        loginPagePOM.enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(PassUtils.decodePassword(Data.getPassword()));
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Backend Agent Queue Login Page")
    public void agentQueueLogin(Method method) {
        ExtentTestManager.startTest("Backend Agent Login into Queue", "Backend Agent Login into Queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        agentLoginPagePOM AgentLoginPagePOM = new agentLoginPagePOM(driver);
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSelectQueue();
        AgentLoginPagePOM.selectAllQueue();
        AgentLoginPagePOM.clickSubmitBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"),"Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentQueueLogin", description = "Ticket Search ")
    public void ValidateTicket(Method method) {
        ExtentTestManager.startTest("Backend Agent Validate Ticket List Page", "Validate the Backend Agent View Ticket List page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        BackendAgentTicketListPOM ticketListPage = new BackendAgentTicketListPOM(driver);
        ticketListPage.waitTillLoaderGetsRemoved();
        /*ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickedSearchBtn();
        Thread.sleep(20000);
        Assert.assertEquals(ticketListPage.getTicketIdvalue(),ticketId);*/
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
        softAssert.assertAll();
    }
}
