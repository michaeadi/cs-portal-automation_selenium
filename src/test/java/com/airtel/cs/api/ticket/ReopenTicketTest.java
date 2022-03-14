package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.cs.request.ticketreopen.ReopenTicketRequest;
import com.airtel.cs.model.cs.request.updateticket.CloseTicketRequest;
import org.testng.annotations.Test;

public class ReopenTicketTest extends ApiPrerequisites {

    String validTicketId = null;
    String ticketId;
    String interactionId;

    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testReopenTicketWithOpenStateTicket() {
        try {
            if (data.getSingleTicketId() != null) {
                selUtils.addTestcaseDescription("Validate Reopen Ticket API with Single Ticket(Re-open State) valid Request", "description");
                validTicketId = "\"" + data.getSingleTicketId() + "\"";
                ReopenTicketRequest reopenTicket = api.reopenTicket(validHeaderList, validTicketId);
                assertCheck.append(actions.assertEqualIntType(reopenTicket.getStatusCode(), 1500, "API Response Code as Expected and is 1500", "Ticket Can not be reopened. API Response is not expected. Expected 1500 and found " + reopenTicket.getStatusCode()));
                final String status = reopenTicket.getStatus();
                assertCheck.append(actions.assertEqualStringType(status, "PARTIAL_SUCCESS", "Status Matched Successfully and is -" + status, "Status Not Matched and is -" + status));
                assertCheck.append(actions.assertEqualStringType(reopenTicket.getResult().getReopenedTicketList().get(0).getMessage(), "Ticket could not be re-opened.", "Response Message is as Expected and is - " + reopenTicket.getResult().getReopenedTicketList().get(0).getMessage(), "Response Message is not as Expected and is - " + reopenTicket.getResult().getReopenedTicketList().get(0).getMessage()));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testReopenTicketWithOpenStateTicket " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest"})
    public void testReopenTicketValid(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate Reopen Ticket API with Single Ticket valid Request", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            ticketId = getTicketId(validCategoryId, clientConfig);
            interactionId = getInteractionId();
            CloseTicketRequest closeTicketPOJO = api.closeTicket(validHeaderList, ticketId, interactionId);
            assertCheck.append(actions.assertEqualStringType(closeTicketPOJO.getMessage(), "ticket update successfully", "Message Matched Successfully", "Message Not Matched"));
            ReopenTicketRequest reopenTicket = api.reopenTicket(validHeaderList, ticketId);
            assertCheck.append(actions.assertEqualIntType(reopenTicket.getStatusCode(), 200, "API Response Code as Expected and is 200", "Ticket Can not be reopened. API Response is not expected. Expected 200 and found " + reopenTicket.getStatusCode()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testReopenTicketValid " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void reopenSingleTicketInvalidTokenTest() {
        try {
            if (data.getSingleTicketId() != null) {
                selUtils.addTestcaseDescription("Validate Reopen Ticket API with Single Ticket & Invalid Token", "description");
                validTicketId = "\"" + data.getSingleTicketId() + "\"";
                ReopenTicketRequest reopenTicket = api.reopenTicket(restUtils.invalidToken(), validTicketId);
                assertCheck.append(actions.assertEqualStringType(reopenTicket.getStatus(), "401", "API Response Code as Expected and is 401", "API Response is not expected. Expected 401 and found " + reopenTicket.getStatusCode()));
                actions.assertAllFoundFailedAssert(assertCheck);
            }
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - reopenSingleTicketInvalidTokenTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void reopenInvalidTicketTest() {
        try {
            if (data.getSingleTicketId() != null) {
                selUtils.addTestcaseDescription("Validate Reopen Ticket API with Invalid Ticket Id", "description");
                validTicketId = "\"910121001031\"";
                ReopenTicketRequest reopenTicket = api.reopenTicket(validHeaderList, validTicketId);
                assertCheck.append(actions.assertEqualIntType(reopenTicket.getStatusCode(), 1500, "API Response Code as Expected and is 1500", "Ticket Can not be reopened. API Response is not expected. Expected 1500 and found " + reopenTicket.getStatusCode()));
                assertCheck.append(actions.assertEqualStringType(reopenTicket.getResult().getReopenedTicketList().get(0).getMessage(), constants.getValue("noTicketFound"), "API Message is As Expected", "API message is not as expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - reopenInvalidTicketTest " + e.getMessage(), false);
        }
    }


}
