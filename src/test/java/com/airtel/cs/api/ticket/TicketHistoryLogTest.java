package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.cs.request.tickethistorylog.TicketHistoryLogRequest;
import org.testng.annotations.Test;

import java.util.Arrays;

public class TicketHistoryLogTest extends ApiPrerequisites {

    String validTicketId = null;

    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void getTicketHistoryLogTest() {
        try {
            if (data.getSingleTicketId() != null) {
                selUtils.addTestcaseDescription("Validate Get v1/fetch/ticket/history/log API with valid Request", "description");
                validTicketId = "\"" + Arrays.deepToString(data.getSingleTicketId()) + "\"";
                TicketHistoryLogRequest historyLog = api.getTicketHistoryLog(validHeaderList, "\"" + Arrays.deepToString(data.getSingleTicketId()) + "\"");
                assertCheck.append(actions.assertEqualIntType(historyLog.getStatusCode(), 200, "Status Code Matched", "Status Code not Matched and is - " + historyLog.getStatusCode()));
                assertCheck.append(actions.assertEqualStringType(historyLog.getResult().getTicketInteractionComments().get(0).getType().toLowerCase(), "interaction", "First event during ticket creation is interaction", "First event during ticket creation is not interaction"));
                assertCheck.append(actions.assertEqualStringType(historyLog.getResult().getTicketInteractionComments().get(1).getType().toLowerCase(), "issue", "Second event during ticket creation is Issue", "Second event during ticket creation is not Issue"));
                assertCheck.append(actions.assertEqualStringType(historyLog.getResult().getTicketInteractionComments().get(2).getType().toLowerCase(), "ticket", "Second event during ticket creation is Issue", "Second event during ticket creation is not Issue"));
                assertCheck.append(actions.assertEqualStringType(historyLog.getMessage(), constants.getValue("fetchedHistoryLog"), "API response message is same as expected", "API response message is not same as expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getTicketHistoryLogTest " + e.getMessage(), false);
        }

    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void getHistoryLogInvalidTokenTest() {
        try {
            selUtils.addTestcaseDescription("Validate Get /v1/clients/config API with Valid Ticket number Invalid Token", "description");
            TicketHistoryLogRequest historyLog = api.getTicketHistoryLog(restUtils.invalidToken(), validTicketId, 401);
            assertCheck.append(actions.assertEqualStringType(historyLog.getStatus(), "401", "API Response Status Code as Expected - 401", "Get Ticket History Log API Response is not expected. Expected 401 and found " + historyLog.getStatusCode()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getHistoryLogInvalidTokenTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void getHistoryLogInvalidTicketNumberTest() {
        try {
            selUtils.addTestcaseDescription("Validate Get /v1/clients/config API with invalid Ticket Number and valid Token", "description");
            TicketHistoryLogRequest historyLog = api.getTicketHistoryLog(validHeaderList, "98102000001");
            assertCheck.append(actions.assertEqualIntType(historyLog.getStatusCode(), 5006, "API Response Status Code as Expected - 5006", "Get Ticket History Log API Response is not expected. Expected 5006 and found " + historyLog.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(historyLog.getMessage(), constants.getValue("noTicketFound"), "API response message is same as expected", "API response message is not same as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getHistoryLogInvalidTicketNumberTest " + e.getMessage(), false);
        }
    }
}
