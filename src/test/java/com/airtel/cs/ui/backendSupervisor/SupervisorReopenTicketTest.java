package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SupervisorReopenTicketTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionBS) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = {"openSupervisorDashboard"}, dataProvider = "ReOpenState", groups = {"SanityTest", "RegressionTest"}, dataProviderClass = DataProviders.class)
    public void reopenTicket(TicketStateDataBean reopen) {
        try {
            selUtils.addTestcaseDescription("Validate Reopen Ticket as Supervisor,Select ticket from closed ticket list,validate reopen comment box pop up open,Add reopen ticket and click on submit button,Validate ticket is re-opened successfully.", "description");
            String ticketId = null;
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            try {
                ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                pages.getSupervisorTicketList().clickCheckbox();
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isReopenBtn(), true, "Reopen button available after clicking checkbox", "Reopen button does not available after clicking checkbox"));
                    try {
                        pages.getSupervisorTicketList().clickReopenButton();
                        pages.getSupervisorTicketList().addReopenComment("Reopen Comment Using Automation");
                        pages.getSupervisorTicketList().submitReopenReq();
                        try {
                            pages.getSupervisorTicketList().changeTicketTypeToOpen();
                            pages.getSupervisorTicketList().writeTicketId(ticketId);
                            pages.getSupervisorTicketList().clickSearchBtn();
                            assertCheck.append(actions.assertEqual_stringType(pages.getSupervisorTicketList().getStatevalue().toLowerCase().trim(), reopen.getTicketStateName().toLowerCase().trim(), "Ticket Reopen in Correct state", "Ticket Does Not Reopen in Correct state"));
                        } catch (NoSuchElementException | TimeoutException e) {
                            commonLib.fail("Ticket does not reopened successfully :" + e.fillInStackTrace(), true);
                        }
                    } catch (NoSuchElementException | TimeoutException e) {
                        commonLib.fail("Not able to add Re open comment " + e.fillInStackTrace(), true);
                        pages.getSupervisorTicketList().closedReopenBox();
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Reopen Ticket action can not complete due to following error " + e.fillInStackTrace(), true);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("No Ticket Found with closed State " + e.fillInStackTrace(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - reopenTicket" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
