package tests.backendSupervisor;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import Utils.PassUtils;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.loginPagePOM;
import pages.supervisorTicketListPagePOM;
import tests.frontendAgent.BaseTest;

import java.lang.reflect.Method;
import java.util.List;

public class SupervisorLoginTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "ALL")
    @Test(priority = 1, description = "Logging IN", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void LoggingIN(Method method, TestDatabean Data) {
        ExtentTestManager.startTest("Logging Into Portal", "Logging Into Portal with AUUID :  " + Data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM loginPagePOM = new loginPagePOM(driver);
        loginPagePOM.openBaseURL(config.getProperty(BaseTest.Env + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(BaseTest.Env + "-baseurl"), "URl isn't as expected");
        loginPagePOM.enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(PassUtils.decodePassword(Data.getPassword()));
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Supervisor Dashboard Login ")
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

    @Test(priority = 3, description = "Verify there are Searchable fields options displayed to select from in the Search Dropdown : 1) Ticket Id & 2) MSISDN", dataProviderClass = DataProviders.class)
    public void validateTicketSearchOptions(Method method) {
        ExtentTestManager.startTest("Validate Search Ticket Option", "Verify there are 2 options displayed to select from in the Search Dropdown : 1) Ticket Id & 2) MSISDN");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickTicketOption();
        List<String> list=ticketListPage.getListOfSearchOption();
        softAssert.assertTrue(list.contains(config.getProperty("ticketOption")),config.getProperty("ticketOption")+" option does not found in list.");
        softAssert.assertTrue(list.contains(config.getProperty("msisdnOption")),config.getProperty("msisdnOption")+" option does not found in list.");
        ticketListPage.clickTicketOption();
        softAssert.assertAll();
    }

    @Test(priority = 4,description = "Validate Supervisor ticket tabs ALL Tickets & My Assigned Tab")
    public void validateSupervisorTabs(){
        ExtentTestManager.startTest("Validate Supervisor ticket tabs(All Tickets & My Assigned Ticket) ", "Validate Supervisor ticket tabs(All Tickets & My Assigned Ticket)");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(ticketListPage.isMyAssignedTicketTab(),"My Assigned Tickets Tab does not displayed correctly.");
        softAssert.assertTrue(ticketListPage.isAllTicketTab(),"ALL Tickets Tab does not displayed correctly.");
        softAssert.assertAll();
    }

}