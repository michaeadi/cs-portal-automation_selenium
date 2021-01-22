package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TransferQueueDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class SupervisorFilterTest extends BaseTest {

    @Test(priority = 1, description = "Supervisor SKIP Login ", enabled = true)
    public void agentSkipQueueLogin(Method method) {
        ExtentTestManager.startTest("Supervisor SKIP Queue Login Test", "Supervisor SKIP Queue Login Test");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(), "Agent Does not redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(), "Queue Login Page Does Not have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(), "Queue Login Page have Does Not Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Validate Filter Tab for Supervisor", dataProviderClass = DataProviders.class)
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

    @Test(priority = 3, dependsOnMethods = "agentSkipQueueLogin", description = "Validate Filter Tab for Supervisor", dataProviderClass = DataProviders.class)
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
