package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SupervisorGlobalSearchTest extends Driver {

    private final BaseActions actions = new BaseActions();

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
            assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void globalSearchTestByTicket() {
        try {
            selUtils.addTestcaseDescription("Verify Global Search By Valid Ticket Id,Search By ticket id", "description");
            try {
                String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                pages.getSupervisorTicketList().writeTicketId(ticketId);
                pages.getSupervisorTicketList().clickSearchBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getTicketIdValue(), ticketId, "Search Ticket found.", "Search Ticket does not found with Ticket Number: " + ticketId));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.warning("No Ticket Id Found.", true);
            }
            pages.getSupervisorTicketList().clearInputBox();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - globalSearchTestByTicket" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"RegressionTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void globalSearchTestByInvalidTicket() {
        try {
            selUtils.addTestcaseDescription("Verify Global Search By Invalid Ticket Id,Search with invalid ticket id,validate no result must be displayed.", "description");
            String ticketId = constants.getValue(CommonConstants.INVALID_TICKET_ID);
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().noTicketFound(), true, "No Result found Page display for Ticket Number: " + ticketId, "No Result page does not displayed with invalid ticket number: " + ticketId));
            pages.getSupervisorTicketList().clearInputBox();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - globalSearchTestByInvalidTicket" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void globalSearchTestBy() {
        try {
            selUtils.addTestcaseDescription("Verify Global Search By Global Search Option, Search using customer msisdn,validate Result page show ticket with searched msisdn", "description");
            try {
                String msisdn = pages.getSupervisorTicketList().getMSISDN();
                pages.getSupervisorTicketList().clickTicketOption();
                pages.getSupervisorTicketList().clickSearchOptionByTextNoIgnoreCase(constants.getValue(CommonConstants.SEARCH_BY_GLOBAL_SEARCH));
                pages.getSupervisorTicketList().writeTicketId(msisdn);
                pages.getSupervisorTicketList().clickSearchBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getMSISDN(), msisdn, "Ticket found with searched MSISDN", "Ticket does not found By searched MSISDN."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to validate Global search by Global Search Option.", true);
            }
            pages.getSupervisorTicketList().clearInputBox();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - globalSearchTestBy" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
