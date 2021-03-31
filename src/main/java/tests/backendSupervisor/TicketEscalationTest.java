package tests.backendSupervisor;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.FilterTabPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.agentLoginPagePOM;
import com.airtel.cs.pagerepository.pagemethods.assignToAgentPOM;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;

public class TicketEscalationTest extends BaseTest {

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

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Validate Ticket Escalation Symbol", dataProviderClass = DataProviders.class)
    public void ticketEscalation(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate Ticket Escalation", "Verify that ticket Escalation Symbol Display");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            ticketListPage.clickFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.OpenEscalationFilter();
            filterTab.selectAllLevel1();
            filterTab.selectAllLevel2();
            filterTab.selectAllLevel3();
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            try {
                if (!ticketListPage.noTicketFound()) {
                    for (int i = 1; i <= ticketListPage.getListSize(); i++) {
                        String symbol = ticketListPage.getSymbol(i);
                        softAssert.assertTrue(symbol.equalsIgnoreCase("!") || symbol.equalsIgnoreCase("!!") || symbol.equalsIgnoreCase("!!!"), "Ticket Symbol not displayed correctly");
                    }
                } else {
                    System.out.println("No Ticket Found for Selected Filter");
                    ExtentTestManager.getTest().log(LogStatus.WARNING, "No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                ExtentTestManager.getTest().log(LogStatus.ERROR, e.fillInStackTrace());
            }
            ticketListPage.resetFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "openSupervisorDashboard", description = "Validate the Escalation of Ticket Before the SLA Expiry", dataProviderClass = DataProviders.class)
    public void ticketEscalationBeforeSLA(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate the Escalation of Ticket Before the SLA Expiry", "Validate the Escalation of Ticket Before the SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            ticketListPage.clickFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.OpenEscalationFilter();
            filterTab.selectAllLevel1();
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            try {
                if (!ticketListPage.noTicketFound()) {
                    for (int i = 1; i <= ticketListPage.getListSize(); i++) {
                        String symbol = ticketListPage.getSymbol(i);
                        softAssert.assertTrue(symbol.equalsIgnoreCase("!"), "Ticket Symbol not displayed correctly");
                    }
                } else {
                    ExtentTestManager.getTest().log(LogStatus.WARNING, "No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                ExtentTestManager.getTest().log(LogStatus.ERROR, e.fillInStackTrace());
                e.printStackTrace();
            }
            ticketListPage.resetFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "openSupervisorDashboard", description = "Validate the Escalation of Ticket after SLA Expiry", dataProviderClass = DataProviders.class)
    public void ticketEscalationAfterSLA(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate the Escalation of Ticket after SLA Expiry", "Validate the Escalation of Ticket after SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            ticketListPage.clickFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.OpenEscalationFilter();
            filterTab.selectAllLevel3();
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            try {
                if (!ticketListPage.noTicketFound()) {
                    for (int i = 1; i <= ticketListPage.getListSize(); i++) {
                        String symbol = ticketListPage.getSymbol(i);
                        softAssert.assertTrue(symbol.equalsIgnoreCase("!!!"), "Ticket Symbol not displayed correctly");
                    }
                } else {
                    ExtentTestManager.getTest().log(LogStatus.WARNING, "No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                ExtentTestManager.getTest().log(LogStatus.ERROR, e.fillInStackTrace());
            }
            ticketListPage.resetFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :"+e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = "openSupervisorDashboard", description = "Validate the Escalation of Ticket on SLA Expiry", dataProviderClass = DataProviders.class)
    public void ticketEscalationOnSLA(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate the Escalation of Ticket on SLA Expiry", "Validate the Escalation of Ticket on SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            ticketListPage.clickFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.OpenEscalationFilter();
            filterTab.selectAllLevel2();
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            try {
                if (!ticketListPage.noTicketFound()) {
                    for (int i = 1; i <= ticketListPage.getListSize(); i++) {
                        String symbol = ticketListPage.getSymbol(i);
                        softAssert.assertTrue(symbol.equalsIgnoreCase("!!"), "Ticket Symbol not displayed correctly");
                    }
                } else {
                    ExtentTestManager.getTest().log(LogStatus.WARNING, "No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                ExtentTestManager.getTest().log(LogStatus.ERROR, e.fillInStackTrace());
            }
            ticketListPage.resetFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :"+e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

}
