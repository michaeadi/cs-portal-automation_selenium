package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BackendAgentUpdateTicketTest extends Driver {


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void updateTicket() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Backend Agent Update Ticket", "description");
            commonLib.info("Opening URL");
            String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            pages.getSupervisorTicketList().viewTicket();
            assertCheck.append(actions.assertEqualStringType(ticketId, pages.getViewTicket().getTicketId(), "The searched Ticket fetched Successfully", "The searched Ticket NOT fetched Successfully"));
            DataProviders data = new DataProviders();
            String selectStateByConfig=data.getState(constants.getValue(CommonConstants.TICKET_CLOSE_STATE)).get(0).getTicketStateName();
            pages.getViewTicket().selectState(selectStateByConfig);
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().noTicketFound(), true, "Backend agent NOT able to see closed ticket", "Backend agent able to see closed ticket"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - updateTicket", true);
        }
    }
}
