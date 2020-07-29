package tests;

import Utils.DataProviders.DataProvider;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class BackendAgentFilterTest extends BaseTest{

    @Test(priority = 1, description = "Backend Agent Queue Login Page")
    public void agentQueueLogin(Method method) {
        ExtentTestManager.startTest("Backend Agent Login into Queue", "Backend Agent Login into Queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openBackendAgentDashboard();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSelectQueue();
        AgentLoginPagePOM.selectAllQueue();
        AgentLoginPagePOM.clickSubmitBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"),"Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentQueueLogin", description = "Validate Filter Tab for Backend Agent", dataProviderClass = DataProvider.class)
    public void validateFilter(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Validate Filter Tab for Backend Agent", "Validate Filter Tab for Backend Agent");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        BackendAgentTicketListPOM ticketListPage = new BackendAgentTicketListPOM(driver);
        FilterTabPOM filterTab=new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(filterTab.isCreatedByFilter(),"Filter by created date does not available");
        softAssert.assertTrue(filterTab.isSlaDueDateFilter(),"Filter by SLA due date does not available");
        softAssert.assertTrue(filterTab.isCategoryFilter(),"Filter by category does not available");
        softAssert.assertTrue(filterTab.isQueueFilter(),"Filter by Queue does not available");
        //softAssert.assertTrue(filterTab.isTicketByAssigneeFilter(),"Filter by Ticket assignee name does not available");
        softAssert.assertTrue(filterTab.isEscalatedLevelFilter(),"Filter by ticket escalation level does not available");
        softAssert.assertTrue(filterTab.isStateFilter(),"Filter by State Filter does not available");
        softAssert.assertTrue(filterTab.validateOpenStateFilter(),"Filter by state name does not display all open state correctly");
        softAssert.assertTrue(filterTab.isPriorityFilter(),"Filter by ticket priority does not available");
        softAssert.assertTrue(filterTab.validatePriorityFilter(),"Filter by priority does not display all priority correctly");
        filterTab.clickCloseFilter();
        softAssert.assertAll();
    }
}
