package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SupervisorLoginTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionBS) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1,groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2,groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void validateTicketSearchOptions() {
        try {
            selUtils.addTestcaseDescription("Validate Search Ticket Option,Verify there are 2 options displayed to select from in the Search Dropdown :, 1) Ticket Id,  2) Global Search", "description");
            pages.getSupervisorTicketList().clickTicketOption();
            List<String> list = pages.getSupervisorTicketList().getListOfSearchOption();
            assertCheck.append(actions.assertEqual_boolean(list.contains(constants.getValue(CommonConstants.SEARCH_BY_TICKET_ID)), true, constants.getValue(CommonConstants.SEARCH_BY_TICKET_ID) + " Option shows in list as expected.", constants.getValue(CommonConstants.SEARCH_BY_TICKET_ID) + " option does not found in list."));
            assertCheck.append(actions.assertEqual_boolean(list.contains(constants.getValue(CommonConstants.SEARCH_BY_GLOBAL_SEARCH)), true, constants.getValue(CommonConstants.SEARCH_BY_GLOBAL_SEARCH) + " Option shows in list as expected.", constants.getValue(CommonConstants.SEARCH_BY_GLOBAL_SEARCH) + " option does not found in list."));
            pages.getSupervisorTicketList().clickTicketOption();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicketSearchOptions" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3,groups = {"SanityTest", "RegressionTest", "ProdTest"},dependsOnMethods = {"openSupervisorDashboard"})
    public void validateSupervisorTabs() {
        try {
            selUtils.addTestcaseDescription("Validate Supervisor ticket tabs(All Tickets & My Assigned Ticket),Check ALL Tickets Tab Displayed,Check My Assigned Ticket Tab Displayed ", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isMyAssignedTicketTab(), true, "My Assigned Tickets Tab displayed correctly.", "My Assigned Tickets Tab does not displayed correctly."));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isAllTicketTab(), true, "ALL Tickets Tab displayed correctly.", "ALL Tickets Tab does not displayed correctly."));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSupervisorTabs" + e.fillInStackTrace(), true);
        }
    }
}