package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.cs.request.ticketlist.TicketListRequest;
import com.airtel.cs.model.cs.request.ticketlist.TicketListResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.IOException;

public class TicketListingTest extends ApiPrerequisites {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketListingValid() {
        selUtils.addTestcaseDescription("Validate v1/tickets/agent API with Valid data,Check Response and count of the ticket", "description");
        TicketListRequest ticketListRequest = api.ticketListRequest(validHeaderList);
        assertCheck.append(actions.assertEqualIntType(ticketListRequest.getStatusCode(), 200, "Status Code Matched and is -" + ticketListRequest.getStatusCode(), "Status Code NOT Matched"));
        assertCheck.append(actions.assertEqualStringType(ticketListRequest.getStatus(), "SUCCESS", "Status Message Matched", "Status Message Not Matched and is - " + ticketListRequest.getStatus()));
        if (ticketListRequest.getResult() != null && (!ticketListRequest.getResult().isEmpty())) {
            final String sourceApp = ((TicketListResult) ticketListRequest.getResult().get(0)).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "Source App is not null and is -" + sourceApp, "Source App is null in the request and is -" + sourceApp));
        } else {
            commonLib.warning("Result List is Empty or Null. Kindly check this with another test data");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"RegressionTest"})
    public void testTicketListingWithInvalidPageSize() {
        try {
            selUtils.addTestcaseDescription("Validate v1/tickets/agent API with Invalid Page Size, It should be clearly mentioned in the response that you are providing invalid page size", "description");
            TicketListRequest ticketListRequest = api.ticketListRequest(validHeaderList, "0", "25", "");
            final Integer statusCode = ticketListRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 400, "Status Code Matched and is -" + statusCode, "Status Message Not Matched and is - " + statusCode));
            assertCheck.append(actions.assertEqualStringType(ticketListRequest.getStatus(), "Failure", "Status Message Matched", "Status Message Not Matched and is -" + ticketListRequest.getStatus()));
            assertCheck.append(actions.assertEqualStringType(String.valueOf(ticketListRequest.getResult().get(0)), "pageSize: Page size cannot be less than one.", "", ""));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketListingWithInvalidPageSize " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, groups = {"RegressionTest"})
    public void testTicketListingWithInvalidPageNumber() {
        try {
            selUtils.addTestcaseDescription("Validate v1/tickets/agent API with Invalid Page Number, It should be clearly mentioned in the response that you are providing invalid page number", "description");
            TicketListRequest ticketListRequest = api.ticketListRequest(validHeaderList, "25", "-1", "");
            final Integer statusCode = ticketListRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 400, "Status Code Matched and is -" + statusCode, "Status Message Not Matched and is - " + statusCode));
            assertCheck.append(actions.assertEqualStringType(ticketListRequest.getStatus(), "Failure", "Status Message Matched", "Status Message Not Matched and is -" + ticketListRequest.getStatus()));
            assertCheck.append(actions.assertEqualStringType(String.valueOf(ticketListRequest.getResult().get(0)), "pageNumber: Page number cannot be less than zero.", "", ""));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketListingWithInvalidPageNumber " + e.getMessage(), false);
        }

    }

    @Test(priority = 4, groups = {"RegressionTest"})
    public void testTicketListingWithInvalidToken() {
        selUtils.addTestcaseDescription("Validate v1/tickets/agent API with Invalid data,Check Response and count of the ticket", "description");
        TicketListRequest ticketListRequest = api.ticketListRequest(invalidHeaderList);
        assertCheck.append(actions.assertEqualStringType(ticketListRequest.getStatus(), "401", "Status Message Matched", "Status Message Not Matched and is - " + ticketListRequest.getStatus()));
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketListingWithFilter() throws IOException {
        selUtils.addTestcaseDescription("Validate v1/tickets/agent API with filter data,Check Response and count of the ticket", "description");
        TicketListRequest ticketListRequest = api.ticketListRequest(validHeaderList, "25", "0", "1");
        assertCheck.append(actions.assertEqualStringType(ticketListRequest.getStatus(), "SUCCESS", "Status Message Matched", "Status Message Not Matched and is - " + ticketListRequest.getStatus()));
        if (ticketListRequest.getResult() != null && (!ticketListRequest.getResult().isEmpty())) {
            final String sourceApp = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(ticketListRequest.getResult().get(0)), TicketListResult.class).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "Source App is not null and is -" + sourceApp, "Source App is null in the request and is -" + sourceApp));
        } else {
            commonLib.fail("Result List is Empty or Null. Kindly check this with another test data", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}
