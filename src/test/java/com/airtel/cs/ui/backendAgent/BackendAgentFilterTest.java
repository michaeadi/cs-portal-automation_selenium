package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BackendAgentFilterTest extends Driver {

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

    @Test(priority = 1, description = "Backend Agent Queue Login Page")
    public void agentQueueLogin() {
        selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openBackendAgentDashboard();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getAgentLoginPage().isQueueLoginPage());
        softAssert.assertTrue(pages.getAgentLoginPage().checkSubmitButton());
        pages.getAgentLoginPage().clickSelectQueue();
        pages.getAgentLoginPage().selectAllQueue();
        pages.getAgentLoginPage().clickSubmitBtn();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"), "Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentQueueLogin", description = "Validate Filter Tab for Backend Agent")
    public void validateFilter() {
        selUtils.addTestcaseDescription("Validate Filter Tab for Backend Agent", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().clickFilter();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getFilterTabPage().isCreatedByFilter(), "Filter by created date does not available");
        softAssert.assertTrue(pages.getFilterTabPage().isSlaDueDateFilter(), "Filter by SLA due date does not available");
        softAssert.assertTrue(pages.getFilterTabPage().isCategoryFilter(), "Filter by category does not available");
        softAssert.assertTrue(pages.getFilterTabPage().isQueueFilter(), "Filter by Queue does not available");
        softAssert.assertTrue(pages.getFilterTabPage().isEscalatedLevelFilter(), "Filter by ticket escalation level does not available");
        softAssert.assertTrue(pages.getFilterTabPage().isStateFilter(), "Filter by State Filter does not available");
        softAssert.assertTrue(pages.getFilterTabPage().validateOpenStateFilter(), "Filter by state name does not display all open state correctly");
        softAssert.assertTrue(pages.getFilterTabPage().isPriorityFilter(), "Filter by ticket priority does not available");
        softAssert.assertTrue(pages.getFilterTabPage().validatePriorityFilter(), "Filter by priority does not display all priority correctly");
        pages.getFilterTabPage().clickCloseFilter();
        softAssert.assertAll();
    }
}
