package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BackendAgentUpdateTicketTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void updateTicket(TicketStateDataBean ticketState) throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Backend Agent Update Ticket", "description");
            commonLib.info("Opening URL");
            String ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
            pages.getSupervisorTicketList().viewTicket();
            assertCheck.append(actions.assertEqual_stringType(ticketId, pages.getViewTicket().getTicketId(), "The searched Ticket fetched Successfully", "The searched Ticket NOT fetched Successfully"));
            DataProviders data = new DataProviders();
            pages.getViewTicket().selectState(data.ticketStateClosed());
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().noTicketFound(), true, "Backend agent NOT able to see closed ticket", "Backend agent able to see closed ticket"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - updateTicket", true);
        }
    }
}
