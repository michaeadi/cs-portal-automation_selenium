package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.driver.Driver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BackendAgentUpdateTicketTest extends Driver {

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

    @Test(priority = 1, description = "Backend Agent Update Ticket", dataProvider = "ticketState", dataProviderClass = DataProviders.class)
    public void updateTicket(TicketStateDataBean ticketState) throws InterruptedException {
        selUtils.addTestcaseDescription("Backend Agent Update Ticket", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
        pages.getSupervisorTicketList().viewTicket();
        Assert.assertEquals(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully");
        String selectedState = pages.getViewTicket().selectState(ticketState.getTicketStateName());
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        pages.getSupervisorTicketList().writeTicketId(ticketId);
        pages.getSupervisorTicketList().clickSearchBtn();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getSupervisorTicketList().noTicketFound(), "Backend agent able to see closed ticket");
        softAssert.assertAll();
    }
}
