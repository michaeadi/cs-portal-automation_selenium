package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TicketEscalationTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        ExtentTestManager.startTest("Open Supervisor Dashboard", "Open Supervisor Dashboard");
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Validate Ticket Escalation Symbol")
    public void ticketEscalation() throws InterruptedException {
        ExtentTestManager.startTest("Validate Ticket Escalation", "Verify that ticket Escalation Symbol Display");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().changeTicketTypeToOpen();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().OpenEscalationFilter();
            pages.getFilterTabPage().selectAllLevel1();
            pages.getFilterTabPage().selectAllLevel2();
            pages.getFilterTabPage().selectAllLevel3();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            try {
                if (!pages.getSupervisorTicketList().noTicketFound()) {
                    for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                        String symbol = pages.getSupervisorTicketList().getSymbol(i);
                        softAssert.assertTrue(symbol.equalsIgnoreCase("!") || symbol.equalsIgnoreCase("!!") || symbol.equalsIgnoreCase("!!!"), "Ticket Symbol not displayed correctly");
                    }
                } else {
                    ExtentTestManager.getTest().log(LogStatus.WARNING, "No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                ExtentTestManager.getTest().log(LogStatus.ERROR, e.fillInStackTrace());
            }
            pages.getSupervisorTicketList().resetFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace());
            pages.getFilterTabPage().clickCloseFilter();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "openSupervisorDashboard", description = "Validate the Escalation of Ticket Before the SLA Expiry")
    public void ticketEscalationBeforeSLA() throws InterruptedException {
        ExtentTestManager.startTest("Validate the Escalation of Ticket Before the SLA Expiry", "Validate the Escalation of Ticket Before the SLA Expiry");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().OpenEscalationFilter();
            pages.getFilterTabPage().selectAllLevel1();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            try {
                if (!pages.getSupervisorTicketList().noTicketFound()) {
                    for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                        String symbol = pages.getSupervisorTicketList().getSymbol(i);
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
            pages.getSupervisorTicketList().resetFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace());
            pages.getFilterTabPage().clickCloseFilter();
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "openSupervisorDashboard", description = "Validate the Escalation of Ticket after SLA Expiry")
    public void ticketEscalationAfterSLA() throws InterruptedException {
        ExtentTestManager.startTest("Validate the Escalation of Ticket after SLA Expiry", "Validate the Escalation of Ticket after SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().OpenEscalationFilter();
            pages.getFilterTabPage().selectAllLevel3();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            try {
                if (!pages.getSupervisorTicketList().noTicketFound()) {
                    for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                        String symbol = pages.getSupervisorTicketList().getSymbol(i);
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
            pages.getSupervisorTicketList().resetFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace());
            pages.getFilterTabPage().clickCloseFilter();
        }
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = "openSupervisorDashboard", description = "Validate the Escalation of Ticket on SLA Expiry")
    public void ticketEscalationOnSLA() throws InterruptedException {
        ExtentTestManager.startTest("Validate the Escalation of Ticket on SLA Expiry", "Validate the Escalation of Ticket on SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().OpenEscalationFilter();
            pages.getFilterTabPage().selectAllLevel2();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            try {
                if (!pages.getSupervisorTicketList().noTicketFound()) {
                    for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                        String symbol = pages.getSupervisorTicketList().getSymbol(i);
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
            pages.getSupervisorTicketList().resetFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            softAssert.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace());
            pages.getFilterTabPage().clickCloseFilter();
        }
        softAssert.assertAll();
    }
}
