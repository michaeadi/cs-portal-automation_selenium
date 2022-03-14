package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.response.ticketstats.TicketStatsResponse;
import org.testng.annotations.Test;

public class TicketStatsTest extends ApiPrerequisites {

    @Test(priority = 1, description = "Validating Ticket Stats API", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketStatsWithValidTokenMsisdn() {
        try {
            selUtils.addTestcaseDescription("Validate v1/ticket/stats with valid Token and Msisdn", "description");
            TicketStatsResponse ticketStatsRequest = api.ticketStatsRequest(MSISDN, validHeaderList);
            assertCheck.append(actions.assertEqualStringType(ticketStatsRequest.getMessage(), "Ticket statistics fetched successfully", "Ticket statistics fetched successfully", "Ticket Response Not Fetched - " + ticketStatsRequest.getMessage()));
            assertCheck.append(actions.assertEqualIntType(ticketStatsRequest.getStatusCode(), 200, "Status Code Mathced", "Status Code Not Matched - " + ticketStatsRequest.getStatusCode()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketStatsWithValidTokenMsisdn " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validating Ticket Stats API With Invalid Token", groups = {"RegressionTest"})
    public void testTicketStatsWithInvalidToken() {
        try {
            selUtils.addTestcaseDescription("Validate v1/ticket/stats with invalid Token", "description");
            TicketStatsResponse ticketStatsRequest = api.ticketStatsRequest(MSISDN, restUtils.invalidToken());
            assertCheck.append(actions.assertEqualStringType(ticketStatsRequest.getMessage(), "Unauthorized", "Ticket Response Message Matched successfully", "Ticket Response Not Fetched - " + ticketStatsRequest.getMessage()));
            assertCheck.append(actions.assertEqualStringType(ticketStatsRequest.getStatus(), "401", "Status Code Mathced", "Status Code Not Matched and is - " + ticketStatsRequest.getStatus()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketStatsWithInvalidToken " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validating Ticket Stats API With Invalid Msisdn", groups = {"RegressionTest"})
    public void testTicketStatsWithInvalidMsisdn() {
        try {
            selUtils.addTestcaseDescription("Validate v1/ticket/stats with invalid Msisdn and valid Token", "description");
            TicketStatsResponse ticketStatsRequest = api.ticketStatsRequest(INVALID_MSISDN, validHeaderList);
            assertCheck.append(actions.assertEqualStringType(ticketStatsRequest.getMessage(), "Tickets not found for this customer id.", "Ticket Response Message Matched successfully", "Ticket Response Not Fetched - " + ticketStatsRequest.getMessage()));
            assertCheck.append(actions.assertEqualStringType(ticketStatsRequest.getStatus(), "Failure", "Status Code Matched", "Status Code Not Matched and is - " + ticketStatsRequest.getStatus()));
            assertCheck.append(actions.assertEqualIntType(ticketStatsRequest.getStatusCode(), 5015, "Status Code Matched", "Status Code Not Matched - " + ticketStatsRequest.getStatusCode()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketStatsWithInvalidMsisdn " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validating Ticket Stats API With Filter", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketStatsWithFilter() {
        try {
            selUtils.addTestcaseDescription("Validate v1/ticket/stats with Filter", "description");
            TicketStatsResponse ticketStatsRequest = api.ticketStatsWithFilterRequest(getValidClientConfig(MSISDN), validHeaderList);
            assertCheck.append(actions.assertEqualStringType(ticketStatsRequest.getMessage(), "Ticket statistics fetched successfully", "Ticket statistics fetched successfully", "Ticket Response Not Fetched - " + ticketStatsRequest.getMessage()));
            assertCheck.append(actions.assertEqualIntType(ticketStatsRequest.getStatusCode(), 200, "Status Code Mathced", "Status Code Not Matched - " + ticketStatsRequest.getStatusCode()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketStatsWithFilter " + e.getMessage(), false);
        }
    }

}
