package tests.backendSupervisor;

import com.airtel.cs.commonutils.DataProviders.DataProviders;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.FilterTabPOM;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;

public class SupervisorFilterTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Validate Filter Tab for Supervisor", dataProviderClass = DataProviders.class)
    public void validateOpenFilterTab(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Validate Filter Tab for Supervisor(Open State)", "Validate Filter Tab for Supervisor(Open State)");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(filterTab.isCreatedByFilter(), "Filter by created date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by created date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isSlaDueDateFilter(), "Filter by SLA due date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by SLA due date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isCategoryFilter(), "Filter by category does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by category does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isQueueFilter(), "Filter by Queue does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Queue does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isTicketByAssigneeFilter(), "Filter by Ticket assignee name does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Assignee name does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isEscalatedLevelFilter(), "Filter by ticket escalation level does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket escalation level does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isStateFilter(), "Filter by State Filter does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by state filter does not available" + e.getCause());
        }
        softAssert.assertTrue(filterTab.validateOpenStateFilter(), "Filter by state name does not display all open state correctly");
        try {
            softAssert.assertTrue(filterTab.isPriorityFilter(), "Filter by ticket priority does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket priority does not available" + e.getCause());
        }
        softAssert.assertTrue(filterTab.validatePriorityFilter(), "Filter by priority does not display all priority correctly");
        filterTab.clickCloseFilter();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Filter Tab for Supervisor", dataProviderClass = DataProviders.class)
    public void validateClosedFilterTab(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Validate Filter Tab for Supervisor(Closed State)", "Validate Filter Tab for Supervisor(Closed State)");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToClosed();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            softAssert.assertTrue(filterTab.isClosureByFilter(), "Filter by closure date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by closure date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isSlaDueDateFilter(), "Filter by SLA due date does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by SLA due date does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isCategoryFilter(), "Filter by category does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by category does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isQueueFilter(), "Filter by Queue does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Queue does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isTicketByAssigneeFilter(), "Filter by Ticket assignee name does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by Assignee name does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isEscalatedLevelFilter(), "Filter by ticket escalation level does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket escalation level does not available" + e.getCause());
        }
        try {
            softAssert.assertTrue(filterTab.isStateFilter(), "Filter by State Filter does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by state filter does not available" + e.getCause());
        }
        softAssert.assertTrue(filterTab.validateCloseStateFilter(), "Filter by state name does not display all Close state correctly");
        try {
            softAssert.assertTrue(filterTab.isPriorityFilter(), "Filter by ticket priority does not available");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Filter by ticket priority does not available" + e.getCause());
        }
        softAssert.assertTrue(filterTab.validatePriorityFilter(), "Filter by priority does not display all priority correctly");
        filterTab.clickCloseFilter();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Apply Filter by Created Date", enabled = true)
    public void applyFilterByCreatedDate() throws InterruptedException {
        ExtentTestManager.startTest("Apply Filter by Created Date", "Apply Filter by Created Date");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        try {
            ticketListPage.clickFilter();
            filterTab.clickLast30DaysFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(ticketListPage.isResetFilter(), "Reset Filter button Does Not Available");
            if(ticketListPage.isResetFilter()){
                ticketListPage.resetFilter();
                ticketListPage.waitTillLoaderGetsRemoved();
            }
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            e.printStackTrace();
            filterTab.clickOutsideFilter();
            filterTab.clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
