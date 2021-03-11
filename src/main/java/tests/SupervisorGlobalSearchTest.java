package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class SupervisorGlobalSearchTest extends BaseTest {

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
    @Test(priority = 4,description = "Verify Global Search By MSISDN(Customer Service)",dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void globalSearchTestByMSISDN(TestDatabean data){
        ExtentTestManager.startTest("Verify Global Search By MSISDN(Customer Service)", "Verify Global Search By MSISDN(Customer Service)");
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
            softAssert.fail("Not able to validate Global search by msisdn.");
        }
        ticketListPage.clearInputBox();
        softAssert.assertAll();
    }
}
