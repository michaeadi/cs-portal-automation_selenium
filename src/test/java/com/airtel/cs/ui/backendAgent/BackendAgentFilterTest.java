package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BackendAgentFilterTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void agentQueueLogin() {
        try {
            selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openBackendAgentDashboard();
            assertCheck.append(actions.assertEqualBoolean(pages.getAgentLoginPage().isQueueLoginPage(), true, "Queue Login Page is Visible", "Queue Login Page is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAgentLoginPage().checkSubmitButton(), true, "Submit button for login is Enabled", "Submit button for login is NOT Enabled"));
            pages.getAgentLoginPage().clickSelectQueue();
            pages.getAgentLoginPage().selectAllQueue();
            pages.getAgentLoginPage().clickSubmitBtn();
            Assert.assertEquals(driver.getTitle(), constants.getValue(CommonConstants.BACKEND_AGENT_TICKET_LIST_PAGE), "Backend Agent Does not Redirect to Ticket List Page");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - agentQueueLogin", true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest","ProdTest"}, dependsOnMethods = {"agentQueueLogin"})
    public void validateFilter() {
        try {
            selUtils.addTestcaseDescription("Validate Filter Tab for Backend Agent", "description");
            pages.getSupervisorTicketList().clickFilter();
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isCreatedByFilter(), true, "Filter by created date is available", "Filter by created date does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isSlaDueDateFilter(), true, "Filter by SLA due date is available", "Filter by SLA due date does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isCategoryFilter(), true, "Filter by category is available", "Filter by category does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isQueueFilter(), true, "Filter by Queue is available", "Filter by Queue does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isEscalatedLevelFilter(), true, "Filter by ticket escalation level is available", "Filter by ticket escalation level does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isStateFilter(), true, "Filter by State Filter is available", "Filter by State Filter does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().validateOpenStateFilter(), true, "Filter by state name is display all open state correctly", "Filter by state name does NOT display all open state correctly"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isPriorityFilter(), true, "Filter by ticket priority is available", "Filter by ticket priority does NOT available"));
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().validatePriorityFilter(), true, "Filter by priority is display all priority correctly", "Filter by priority does NOT display all priority correctly"));
            pages.getFilterTabPage().clickCloseFilter();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - validateFilter", true);
        }
    }
}
