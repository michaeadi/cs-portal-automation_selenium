package tests.backendAgent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.PassUtils;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.agentLoginPagePOM;
import com.airtel.cs.pagerepository.pagemethods.LoginPage;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;

public class BackendAgentLoginTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBA){
           softAssert.fail("Terminate Execution as Backend Agent not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }
    @DataProviders.User(userType = "BA")
    @Test(priority = 1, description = "Logging IN ", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void LoggingIN(Method method, TestDatabean Data) throws InterruptedException {
        ExtentTestManager.startTest("Backend Agent Login Into Portal", "Logging Into Portal with AUUID :  " + Data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPagePOM = new LoginPage(driver);
        loginPagePOM.openBaseURL(config.getProperty(BaseTest.Env + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(BaseTest.Env + "-baseurl"), "URl isn't as expected");
        loginPagePOM.waitTillLoaderGetsRemoved();
        loginPagePOM.enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(PassUtils.decodePassword(Data.getPassword()));
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        Thread.sleep(20000); // wait for 20 Seconds for Dashboard page In case of slow Network slow
        if(sideMenu.isSideMenuVisible()){
            sideMenu.clickOnSideMenu();
            if (!sideMenu.isAgentDashboard()) {
                continueExecutionBA = false;
                softAssert.fail("Agent Dashboard does not Assign to User Visible.Please Assign Role to user.");
            } else {
                continueExecutionBA = true;
            }
            sideMenu.clickOnSideMenu();
        }else {
            continueExecutionBA = false;
            softAssert.fail("Agent Dashboard does Open with user.Check for the ScreenShot.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Backend Agent Login into Queue", dependsOnMethods = "LoggingIN")
    public void agentQueueLogin(Method method) {
        ExtentTestManager.startTest("Backend Agent Login into Queue", "Backend Agent Login into Queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(sideMenu.isSideMenuVisible());
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openBackendAgentDashboard();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSelectQueue();
        AgentLoginPagePOM.selectAllQueue();
        AgentLoginPagePOM.clickSubmitBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"), "Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentQueueLogin", description = "Ticket Search ")
    public void ValidateTicket(Method method) {
        ExtentTestManager.startTest("Backend Agent Validate Ticket List Page", "Validate the Backend Agent View Ticket List page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
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
        softAssert.assertFalse(ticketListPage.getMSISDN().isEmpty(), "MSISDN Can not be empty");
        softAssert.assertAll();
    }
}
