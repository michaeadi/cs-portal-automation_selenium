package tests;

import Utils.DataProviders.DataProviders;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.FilterTabPOM;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

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
    public void validateFilter(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Validate Filter Tab for Supervisor", "Validate Filter Tab for Supervisor");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(filterTab.isCreatedByFilter(), "Filter by created date does not available");
        softAssert.assertTrue(filterTab.isSlaDueDateFilter(), "Filter by SLA due date does not available");
        softAssert.assertTrue(filterTab.isCategoryFilter(), "Filter by category does not available");
        softAssert.assertTrue(filterTab.isQueueFilter(), "Filter by Queue does not available");
        softAssert.assertTrue(filterTab.isTicketByAssigneeFilter(), "Filter by Ticket assignee name does not available");
        softAssert.assertTrue(filterTab.isEscalatedLevelFilter(), "Filter by ticket escalation level does not available");
        softAssert.assertTrue(filterTab.isStateFilter(), "Filter by State Filter does not available");
        softAssert.assertTrue(filterTab.validateOpenStateFilter(), "Filter by state name does not display all open state correctly");
        softAssert.assertTrue(filterTab.isPriorityFilter(), "Filter by ticket priority does not available");
        softAssert.assertTrue(filterTab.validatePriorityFilter(), "Filter by priority does not display all priority correctly");
        filterTab.clickCloseFilter();
        softAssert.assertAll();
    }
}
