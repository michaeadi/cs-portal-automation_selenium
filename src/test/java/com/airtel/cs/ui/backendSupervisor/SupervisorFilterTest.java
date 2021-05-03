package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SupervisorFilterTest extends Driver {

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

    @Test(priority = 1, description = "Validate Filter Tab for Supervisor")
    public void validateOpenFilterTab() {
        selUtils.addTestcaseDescription("Validate Filter Tab for Supervisor(Open State)", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().changeTicketTypeToOpen();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        pages.getSupervisorTicketList().clickFilter();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isCreatedByFilter(), "Filter by created date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by created date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isSlaDueDateFilter(), "Filter by SLA due date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by SLA due date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isCategoryFilter(), "Filter by category does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by category does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isQueueFilter(), "Filter by Queue does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Queue does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isTicketByAssigneeFilter(), "Filter by Ticket assignee name does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Assignee name does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isEscalatedLevelFilter(), "Filter by ticket escalation level does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket escalation level does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isStateFilter(), "Filter by State Filter does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by state filter does not available" + e.getCause());
        }
        softAssert.assertTrue(pages.getFilterTabPage().validateOpenStateFilter(), "Filter by state name does not display all open state correctly");
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isPriorityFilter(), "Filter by ticket priority does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket priority does not available" + e.getCause());
        }
        softAssert.assertTrue(pages.getFilterTabPage().validatePriorityFilter(), "Filter by priority does not display all priority correctly");
        pages.getFilterTabPage().clickCloseFilter();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Filter Tab for Supervisor")
    public void validateClosedFilterTab() {
        selUtils.addTestcaseDescription("Validate Filter Tab for Supervisor(Closed State)", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().changeTicketTypeToClosed();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        pages.getSupervisorTicketList().clickFilter();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isClosureByFilter(), "Filter by closure date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by closure date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isSlaDueDateFilter(), "Filter by SLA due date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by SLA due date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isCategoryFilter(), "Filter by category does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by category does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isQueueFilter(), "Filter by Queue does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Queue does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isTicketByAssigneeFilter(), "Filter by Ticket assignee name does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Assignee name does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isEscalatedLevelFilter(), "Filter by ticket escalation level does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket escalation level does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isStateFilter(), "Filter by State Filter does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by state filter does not available" + e.getCause());
        }
        softAssert.assertTrue(pages.getFilterTabPage().validateCloseStateFilter(), "Filter by state name does not display all Close state correctly");
        try {
            softAssert.assertTrue(pages.getFilterTabPage().isPriorityFilter(), "Filter by ticket priority does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket priority does not available" + e.getCause());
        }
        softAssert.assertTrue(pages.getFilterTabPage().validatePriorityFilter(), "Filter by priority does not display all priority correctly");
        pages.getFilterTabPage().clickCloseFilter();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Apply Filter by Created Date")
    public void applyFilterByCreatedDate() {
        selUtils.addTestcaseDescription("Apply Filter by Created Date", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getFilterTabPage().clickLast30DaysFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getSupervisorTicketList().isResetFilter(), "Reset Filter button Does Not Available");
            if (pages.getSupervisorTicketList().isResetFilter()) {
                pages.getSupervisorTicketList().resetFilter();
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            }
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            e.printStackTrace();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
