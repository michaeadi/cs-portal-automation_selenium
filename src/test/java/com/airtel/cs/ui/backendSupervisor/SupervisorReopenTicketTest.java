package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SupervisorReopenTicketTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void checkExecution() {
        if (!(continueExecutionBS && continueExecutionFA)) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest","ProdTest"})
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

    @Test(priority = 2, dependsOnMethods = {"openSupervisorDashboard"}, dataProvider = "ticketId", groups = {"SanityTest", "RegressionTest","ProdTest"},dataProviderClass = DataProviders.class)
    public void reopenTicket(NftrDataBeans data) {
        try {
            selUtils.addTestcaseDescription("Validate Reopen Ticket as Supervisor,Select ticket from closed ticket list,validate reopen comment box pop up open,Add reopen ticket and click on submit button,Validate ticket is re-opened successfully.", "description");
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            try {
                pages.getSupervisorTicketList().writeTicketId(data.getTicketNumber());
                pages.getSupervisorTicketList().clickSearchBtn();
                pages.getSupervisorTicketList().clickCheckbox();
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isReopenBtn(), true, "Reopen button available after clicking checkbox", "Reopen button does not available after clicking checkbox"));
                    try {
                        pages.getSupervisorTicketList().clickReopenButton();
                        pages.getSupervisorTicketList().addReopenComment("Reopen Comment Using Automation");
                        pages.getSupervisorTicketList().submitReopenReq();
                        try {
                            pages.getSupervisorTicketList().changeTicketTypeToOpen();
                            pages.getSupervisorTicketList().writeTicketId(data.getTicketNumber());
                            pages.getSupervisorTicketList().clickSearchBtn();
                            assertCheck.append(actions.assertEqual_stringType(pages.getSupervisorTicketList().getStatevalue().toLowerCase().trim(), constants.getValue(CommonConstants.REOPEN_TICKET_STATE_NAME).toLowerCase().trim(), "Ticket Reopen in Correct state", "Ticket Does Not Reopen in Correct state"));
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
                commonLib.warning("No Ticket Found with closed State " + e.fillInStackTrace(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - reopenTicket" + e.fillInStackTrace(), true);
            pages.getSupervisorTicketList().clearInputBox();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
