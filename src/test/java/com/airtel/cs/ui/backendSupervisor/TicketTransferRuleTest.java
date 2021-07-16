package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TicketTransferRuleDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TicketTransferRuleTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionBS) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"RegressionTest"})
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

    @Test(priority = 1, groups = {"RegressionTest"}, dataProvider = "ticketTransferRule", dataProviderClass = DataProviders.class)
    public void ticketTransferRuleCheck(TicketTransferRuleDataBean ruleData) {
        try {
            selUtils.addTestcaseDescription("Ticket Transfer Rule Test" + ruleData.getIssueCode() + " to ticket state " + ruleData.getToQueue() + ",Get the rule config Opco excel sheet,Apply filter with issue category code,View Ticket from ticket list,Choose state name from mapped state " + ruleData.getTicketToState() + " and click on submit button,Validate ticket must be transfer to queue name " + ruleData.getToQueue() + " as per rule file.", "description");
            String ticketId = null;
            try {
                pages.getSupervisorTicketList().clickFilter();
                pages.getFilterTabPage().applyFilterByCategoryCode(ruleData.getIssueCode());
                pages.getFilterTabPage().clickQueueFilter();
                pages.getFilterTabPage().selectQueueByName(ruleData.getFromQueue());
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickApplyFilter();
                try {
                    ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                } catch (NoSuchElementException | TimeoutException e) {
                    pages.getSupervisorTicketList().resetFilter();
                    commonLib.warning("No Ticket Found with Selected Filter ", true);
                }
                pages.getSupervisorTicketList().viewTicket();
                Assert.assertEquals(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully");
                String selectedState = pages.getViewTicket().selectState(ruleData.getTicketToState());
                if (!selectedState.equalsIgnoreCase("Required State not found")) {
                    try {
                        pages.getSupervisorTicketList().writeTicketId(ticketId);
                        pages.getSupervisorTicketList().clickSearchBtn();
                        assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getTicketIdValue(), ticketId, "Search ticket fetched successfully", "Search Ticket Does not Fetched Correctly"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getStatevalue().toLowerCase().trim(), selectedState.toLowerCase().trim(), "Ticket Updated to Selected State " + selectedState, "Ticket Does not Updated to Selected State " + selectedState));
                        assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), ruleData.getToQueue().toLowerCase().trim(), "Ticket updated to correct ticket pool " + ruleData.getToQueue(), "Ticket does not updated to correct ticket pool " + ruleData.getToQueue()));
                    } catch (TimeoutException | NoSuchElementException e) {
                        commonLib.fail("Ticket has been transferred to Selected but not able search ticket." + e.fillInStackTrace(), true);
                    }
                    pages.getSupervisorTicketList().clearInputBox();
                } else {
                    commonLib.fail("Required State does not mapped to ticket queue. Check config in application db or update opco rule file in excel.", true);
                    pages.getViewTicket().clickOutside();
                    pages.getViewTicket().clickBackButton();
                }
            } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
                pages.getFilterTabPage().clickCloseFilter();
                commonLib.fail("Not able to apply filter " + e.getMessage(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ticketTransferRuleCheck" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
