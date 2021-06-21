package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TicketEscalationTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"RegressionTest"})
    public void checkExecution() {
        if (!(continueExecutionBS && continueExecutionFA)) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"RegressionTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = {"openSupervisorDashboard"}, groups = {"RegressionTest"})
    public void ticketEscalation() {
        try {
            selUtils.addTestcaseDescription("Validate Ticket Escalation,Apply filter with escalated level,Verify that ticket Escalation Symbol Display", "description");
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            try {
                pages.getSupervisorTicketList().clickFilter();
                pages.getFilterTabPage().openEscalationFilter();
                pages.getFilterTabPage().selectAllLevel1();
                pages.getFilterTabPage().selectAllLevel2();
                pages.getFilterTabPage().selectAllLevel3();
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickApplyFilter();
                if (!pages.getSupervisorTicketList().noTicketFound()) {
                    for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                        String symbol = pages.getSupervisorTicketList().getSymbol(i);
                        assertCheck.append(actions.assertEqual_boolean(symbol.equalsIgnoreCase("!") || symbol.equalsIgnoreCase("!!") || symbol.equalsIgnoreCase("!!!"), true, symbol + " - Ticket Escalation Symbol displayed as ticket escalated.", "Ticket Symbol not displayed correctly"));
                    }
                } else {
                    commonLib.warning("No Ticket Found for Selected Filter");
                }
                pages.getSupervisorTicketList().resetFilter();
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                commonLib.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace(), true);
                pages.getFilterTabPage().clickCloseFilter();
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ticketEscalation" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = {"openSupervisorDashboard"}, groups = {"RegressionTest"})
    public void ticketEscalationBeforeSLA() {
        try {
            selUtils.addTestcaseDescription("Validate the Escalation of Ticket Before the SLA Expiry,Apply filter with first level of escalation ,Validate all ticket in ticket list must display first level of escalation symbol[!]", "description");
            try {
                pages.getSupervisorTicketList().clickFilter();
                pages.getFilterTabPage().openEscalationFilter();
                pages.getFilterTabPage().selectAllLevel1();
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickApplyFilter();
                try {
                    if (!pages.getSupervisorTicketList().noTicketFound()) {
                        for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                            String symbol = pages.getSupervisorTicketList().getSymbol(i);
                            assertCheck.append(actions.assertEqual_boolean(symbol.equalsIgnoreCase("!"), true, symbol + " - Ticket Escalation Symbol displayed as ticket escalated.", "Ticket Symbol not displayed correctly"));
                        }
                    } else {
                        commonLib.warning("No Ticket Found for Selected Filter");
                    }

                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Ticket Escalation Symbol on ticket does not displayed correctly", true);
                    e.printStackTrace();
                }
                pages.getSupervisorTicketList().resetFilter();
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                commonLib.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace(), true);
                pages.getFilterTabPage().clickCloseFilter();
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ticketEscalationBeforeSLA" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, dependsOnMethods = {"openSupervisorDashboard"}, groups = {"RegressionTest"})
    public void ticketEscalationAfterSLA() {
        try {
            selUtils.addTestcaseDescription("Validate the Escalation of Ticket after SLA Expiry,Apply filter with first level of escalation ,Validate all ticket in ticket list must display third level of escalation symbol[!!!]", "description");
            try {
                pages.getSupervisorTicketList().clickFilter();
                pages.getFilterTabPage().openEscalationFilter();
                pages.getFilterTabPage().selectAllLevel3();
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickApplyFilter();
                try {
                    if (!pages.getSupervisorTicketList().noTicketFound()) {
                        for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                            String symbol = pages.getSupervisorTicketList().getSymbol(i);
                            assertCheck.append(actions.assertEqual_boolean(symbol.equalsIgnoreCase("!!!"), true, symbol + " - Ticket Escalation Symbol displayed as ticket escalated.", symbol + " - Ticket Symbol not displayed correctly"));
                        }
                    } else {
                        commonLib.warning("No Ticket Found for Selected Filter");
                    }

                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Ticket Escalation Symbol on ticket not displayed correctly" + e.fillInStackTrace(), true);
                }
                pages.getSupervisorTicketList().resetFilter();
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                commonLib.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace(), true);
                pages.getFilterTabPage().clickCloseFilter();
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ticketEscalationBeforeSLA" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, dependsOnMethods = {"openSupervisorDashboard"}, groups = {"RegressionTest"})
    public void ticketEscalationOnSLA() {
        try {
            selUtils.addTestcaseDescription("Validate the Escalation of Ticket on SLA Expiry,Apply filter with first level of escalation ,Validate all ticket in ticket list must display second level of escalation symbol[!!]", "description");
            try {
                pages.getSupervisorTicketList().clickFilter();

                pages.getFilterTabPage().openEscalationFilter();
                pages.getFilterTabPage().selectAllLevel2();
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickApplyFilter();

                try {
                    if (!pages.getSupervisorTicketList().noTicketFound()) {
                        for (int i = 1; i <= pages.getSupervisorTicketList().getListSize(); i++) {
                            String symbol = pages.getSupervisorTicketList().getSymbol(i);
                            assertCheck.append(actions.assertEqual_boolean(symbol.equalsIgnoreCase("!!"), true, symbol + " - Ticket Escalation Symbol displayed as ticket escalated.", symbol + " - Ticket Symbol not displayed correctly"));
                        }
                    } else {
                        commonLib.warning("No Ticket Found for Selected Filter");
                    }

                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Ticket Escalation Symbol on ticket not displayed correctly" + e.fillInStackTrace(), true);
                }
                pages.getSupervisorTicketList().resetFilter();

            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                commonLib.fail("Not able to apply filter with escalation level :" + e.fillInStackTrace(), true);
                pages.getFilterTabPage().clickCloseFilter();
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ticketEscalationOnSLA" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
