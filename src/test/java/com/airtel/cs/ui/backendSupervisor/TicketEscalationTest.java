package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TicketEscalationTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        selUtils.addTestcaseDescription("Open Supervisor Dashboard", "description");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Validate Ticket Escalation Symbol")
    public void ticketEscalation() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Ticket Escalation,Verify that ticket Escalation Symbol Display", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().changeTicketTypeToOpen();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().openEscalationFilter();
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
                    commonLib.warning("No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                commonLib.error("", true);
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
        selUtils.addTestcaseDescription("Validate the Escalation of Ticket Before the SLA Expiry", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().openEscalationFilter();
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
                    commonLib.warning("No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                commonLib.error("", true);
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
        selUtils.addTestcaseDescription("Validate the Escalation of Ticket after SLA Expiry", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().openEscalationFilter();
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
                    commonLib.warning("No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                commonLib.error("", true);
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
        selUtils.addTestcaseDescription("Validate the Escalation of Ticket on SLA Expiry", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().openEscalationFilter();
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
                    commonLib.warning("No Ticket Found for Selected Filter");
                }

            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
                softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
                commonLib.error("Ticket Escalation Symbol on ticket not displayed correctly", true);
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
