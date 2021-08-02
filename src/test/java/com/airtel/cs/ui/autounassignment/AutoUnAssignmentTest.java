package com.airtel.cs.ui.autounassignment;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.agents.AdditionalDetails;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLog;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLogList;
import io.restassured.http.Headers;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutoUnAssignmentTest extends Driver {

    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkToRunUnAssignment() {
        if (!continueUnAssignment) {
            commonLib.skip("Skipping tests because ticket does not assigned to user");
            throw new SkipException("Skipping tests because ticket does not assigned to user");
        }
    }

    @Test(priority = 1,groups ={"SanityTest", "RegressionTest", "ProdTest","SmokeTest"} )
    public void validateTicketAutoUnAssigned(){
        try {
            selUtils.addTestcaseDescription("Validate Agent Login into queue,Validate ticket assigned to login agent", "description");
            String ticketId=constants.getValue(CommonConstants.AUTO_ASSIGNMENT_TICKET_ID);
            TicketHistoryLog ticketHistoryLog=api.getTicketHistoryLog(ticketId);
            final Integer statusCode = ticketHistoryLog.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Ticket History log API Status Code Matched and is :" + statusCode, "Ticket History log Status Code NOT Matched and is :" + statusCode));
            TicketHistoryLogList ticketHistoryLogList=ticketHistoryLog.getResult().getTicketInteractionComments().get(ticketHistoryLog.getResult().getTicketInteractionComments().size()-1);
            AdditionalDetails agentDetails= UtilsMethods.getAgentDetail(new Headers(map)).getAdditionalDetails();
            assertCheck.append(actions.assertEqualIntType(ticketHistoryLogList.getAgentId(),agentDetails.getId(),"Agent id same as expected","Agent id does not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(ticketHistoryLogList.getAssignTo(),agentDetails.getName(),"Agent name same as expected","Agent name does not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(ticketHistoryLogList.getEvent(),constants.getValue(CommonConstants.AUTO_UN_ASSIGNMENT_EVENT_NAME),"Ticket auto un-assigned from user by auto un-assignment event","Ticket does not auto un-assigned from user by auto un-assignment event"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicketAutoUnAssigned" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
