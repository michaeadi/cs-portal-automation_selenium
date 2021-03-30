package tests.backendSupervisor;

import com.airtel.cs.commonutils.DataProviders.DataProviders;
import com.airtel.cs.commonutils.DataProviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.agentLoginPagePOM;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;

public class SupervisorGlobalSearchTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS){
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

    @Test(priority = 2, description = "Verify Global Search By Valid Ticket Id")
    public void globalSearchTestByTicket() {
        ExtentTestManager.startTest("Verify Global Search By Valid Ticket Id", "Verify Global Search By Valid Ticket Id");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        try {
            ticketListPage.waitTillLoaderGetsRemoved();
            String ticketId = ticketListPage.getTicketIdvalue();
            ticketListPage.writeTicketId(ticketId);
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            softAssert.assertEquals(ticketListPage.getTicketIdvalue(), ticketId, "Search Ticket does not found with Ticket Number: " + ticketId);
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("No Ticket Id Found.");
        }
        ticketListPage.clearInputBox();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Verify Global Search By Invalid Ticket Id")
    public void globalSearchTestByInvalidTicket() {
        ExtentTestManager.startTest("Verify Global Search By Invalid Ticket Id", "Verify Global Search By Invalid Ticket Id");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.waitTillLoaderGetsRemoved();
        String ticketId = "987654321012";
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketListPage.noTicketFound(),"No Result found Page does not display for Ticket Number: " + ticketId);
        ticketListPage.clearInputBox();
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 4,description = "Verify Global Search By Global Search Option",dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void globalSearchTestBy(TestDatabean data){
        ExtentTestManager.startTest("Verify Global Search By Global Search Option", "Verify Global Search By Global Search Option");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            String msisdn = ticketListPage.getMSISDN();
            ticketListPage.clickTicketOption();
            ticketListPage.clickSearchOptionByTextNoIgnoreCase(config.getProperty("msisdnOption"));
            ticketListPage.writeTicketId(msisdn);
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            softAssert.assertEquals(ticketListPage.getMSISDN(), msisdn, "Ticket does not found By searched MSISDN.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Not able to validate Global search by Global Search Option.");
        }
        ticketListPage.clearInputBox();
        softAssert.assertAll();
    }
}
