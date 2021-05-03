package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SupervisorReopenTicketTest extends Driver {

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

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        selUtils.addTestcaseDescription("Open Supervisor Dashboard", "description");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", dataProvider = "ReOpenState", description = "Supervisor Reopen Ticket", dataProviderClass = DataProviders.class)
    public void reopenTicket(TicketStateDataBean reopen) {
        selUtils.addTestcaseDescription("Validate Reopen Ticket as Supervisor", "description");
        SoftAssert softAssert = new SoftAssert();
        String ticketId;
        pages.getSupervisorTicketList().changeTicketTypeToClosed();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
            pages.getSupervisorTicketList().clickCheckbox();
            try {
                softAssert.assertTrue(pages.getSupervisorTicketList().isReopenBtn(), "Reopen Button Available");
                try {
                    pages.getSupervisorTicketList().clickReopenButton();
                    pages.getSupervisorTicketList().addReopenComment("Reopen Comment Using Automation");
                    pages.getSupervisorTicketList().submitReopenReq();
                    pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                    try {
                        pages.getSupervisorTicketList().changeTicketTypeToOpen();
                        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                        pages.getSupervisorTicketList().writeTicketId(ticketId);
                        pages.getSupervisorTicketList().clickSearchBtn();
                        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                        Assert.assertEquals(pages.getSupervisorTicketList().getStatevalue().toLowerCase().trim(), reopen.getTicketStateName().toLowerCase().trim(), "Ticket Does Not Reopen in Correct state");
                    } catch (NoSuchElementException | TimeoutException e) {
                        softAssert.fail("Ticket does not reopened successfully :" + e.fillInStackTrace());
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to add Re open comment " + e.fillInStackTrace());
                    pages.getSupervisorTicketList().closedReopenBox();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Reopen Ticket action can not complete due to following error " + e.fillInStackTrace());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("No Ticket Found with closed State " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
