package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SupervisorFilterTest extends Driver {


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!(continueExecutionBS && continueExecutionFA)) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void validateOpenFilterTab() {
        try {
            selUtils.addTestcaseDescription("Validate Filter Tab for Supervisor(Open State),Validate filter by created date & filter by SLA due date " +
                    "& filter by category hierarchy & filter by queue & filter assignee name & filter by escalation level & filter by state & " +
                    "filter by priority must be display, validate all value in open state must be displayed,Validate all value in priority must be displayed. ", "description");
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            pages.getSupervisorTicketList().clickFilter();
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isCreatedByFilter(), true, "Filter by created date available", "Filter by created date does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSlaDueDateFilter(), true, "Filter by SLA due date available", "Filter by SLA due date does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isCategoryFilter(), true, "Filter by category available", "Filter by category does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isQueueFilter(), true, "Filter by Queue available", "Filter by Queue does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isTicketByAssigneeFilter(), true, "Filter by Ticket assignee name available", "Filter by Ticket assignee name does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isEscalatedLevelFilter(), true, "Filter by ticket escalation level available", "Filter by ticket escalation level does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isStateFilter(), true, "Filter by State Filter available", "Filter by State Filter does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().validateOpenStateFilter(), true, "Filter by state name display all open state correctly", "Filter by state name does not display all open state correctly"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isPriorityFilter(), true, "Filter by ticket priority available", "Filter by ticket priority does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().validatePriorityFilter(), true, "Filter by priority display all priority correctly", "Filter by priority does not display all priority correctly"));
            pages.getFilterTabPage().clickCloseFilter();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateOpenFilterTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void validateClosedFilterTab() {
        try {
            selUtils.addTestcaseDescription("Validate Filter Tab for Supervisor(Closed State),Validate filter by closure date & filter by SLA due date " +
                    "& filter by category hierarchy & filter by queue & filter assignee name & filter by escalation level & filter by state & " +
                    "filter by priority must be display, validate all value in open state must be displayed,Validate all value in priority must be displayed. ", "description");
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            pages.getSupervisorTicketList().clickFilter();
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isClosureByFilter(), true, "Filter by closure date available", "Filter by closure date does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSlaDueDateFilter(), true, "Filter by SLA due date available", "Filter by SLA due date does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isCategoryFilter(), true, "Filter by category available", "Filter by category does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isQueueFilter(), true, "Filter by Queue available", "Filter by Queue does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isTicketByAssigneeFilter(), true, "Filter by Ticket assignee name available", "Filter by Ticket assignee name does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isEscalatedLevelFilter(), true, "Filter by ticket escalation level available", "Filter by ticket escalation level does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isStateFilter(), true, "Filter by State Filter available", "Filter by State Filter does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().validateCloseStateFilter(), true, "Filter by state name display all open state correctly", "Filter by state name does not display all open state correctly"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isPriorityFilter(), true, "Filter by ticket priority available", "Filter by ticket priority does not available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().validatePriorityFilter(), true, "Filter by priority display all priority correctly", "Filter by priority does not display all priority correctly"));
            pages.getFilterTabPage().clickCloseFilter();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateClosedFilterTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void validateResetFilterButton() {
        try {
            selUtils.addTestcaseDescription("Validate Reset filter button available after applying filter,Apply Filter by Created Date,Validate Reset Filter button displayed or not", "description");
            try {
                pages.getSupervisorTicketList().changeTicketTypeToOpen();
                pages.getSupervisorTicketList().clickFilter();
                pages.getFilterTabPage().clickLast30DaysFilter();
                pages.getFilterTabPage().clickApplyFilter();
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isResetFilter(), true, "Reset Filter button available after applying filter", "Reset Filter button Does Not Available"));
                if (pages.getSupervisorTicketList().isResetFilter()) {
                    pages.getSupervisorTicketList().resetFilter();

                }
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickCloseFilter();
                commonLib.fail("Not able to apply filter " + e.getMessage(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - applyFilterByCreatedDate" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
