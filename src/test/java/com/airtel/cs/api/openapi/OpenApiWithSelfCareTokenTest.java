package com.airtel.cs.api.openapi;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.prerequisite.OpenAPIPrerequisites;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.AssertionMessageConstants;
import com.airtel.cs.model.request.openapi.category.ChildCategoryOpenApiRequest;
import com.airtel.cs.model.request.openapi.category.FirstLastOpenApiRequest;
import com.airtel.cs.model.request.openapi.category.ParentCategoryOpenApiRequest;
import com.airtel.cs.model.request.openapi.clientconfig.ClientConfigOpenApiRequest;
import com.airtel.cs.model.request.openapi.interactionissue.InteractionIssueOpenApiRequest;
import com.airtel.cs.model.request.openapi.interactionissue.IssueLayoutOpenRequest;
import com.airtel.cs.model.request.openapi.ticket.SearchTicketOpenRequest;
import com.airtel.cs.model.request.openapi.ticket.TicketHistoryLogOpenRequest;
import com.airtel.cs.model.request.openapi.ticket.TicketSearchByTicketIdOpenRequest;
import com.airtel.cs.model.response.openapi.comment.CommentOpenApiResponse;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class OpenApiWithSelfCareTokenTest extends OpenAPIPrerequisites {


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSelfCareConfigured() {
        if (isSelfcareNOTConfigured) {
            commonLib.skip("Skipping tests because SelfCare is not Configured");
            assertCheck.append(actions.assertEqualBoolean(isSelfcareNOTConfigured, true, "SelfCare is NOT Configured, So Skipping Self care Test Case"));
            throw new SkipException("Skipping tests because SelfCare is not Configured");
        } else {
            commonLib.info("Selfcare is Configured and proceeding for selfcare test cases");
            assertCheck.append(actions.assertEqualBoolean(isSelfcareNOTConfigured, false, "SelfCare is Configured, So Proceeding for Self care Test Case"));
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testClientConfigWithValidToken() {
        try {
            selUtils.addTestcaseDescription("API is: - v1/openapi/clients/config, This API will return Client details like client Name and field Name", "description");
            ClientConfigOpenApiRequest clientConfigOpenApiPOJO = api.clientWithoutUMRequest(validHeaderList);
            assertCheck.append(actions.assertEqualIntergerStatusCode(clientConfigOpenApiPOJO.getStatusCode(), 200, "Status Code Matched and is -" + clientConfigOpenApiPOJO.getStatusCode(), "Response not matched and statusCode is:- " + clientConfigOpenApiPOJO.getStatusCode()));
            final String client = clientConfigOpenApiPOJO.getResult().get(0).getClient();
            assertCheck.append(actions.assertEqualStringType(client, constants.getValue(AssertionMessageConstants.OPEN_API_CLIENT_NAME), "Client Name Matched and is -" + client, "Client Name not matched and client is:-" + client));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testClientConfigWithValidToken " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, groups = {"RegressionTest","ProdTest"}, dependsOnMethods = {"testSelfCareConfigured"})
    public void testClientConfigWithInvalidToken() {
        try {
            String SELFCARE = "Selfcare";
            selUtils.addTestcaseDescription("API is: - v1/openapi/clients/config, This API will return Client details like client Name and field Name", "description");
            ClientConfigOpenApiRequest clientConfigOpenApiPOJO = api.clientWithoutUMRequest(invalidHeaderList);
            final String status = clientConfigOpenApiPOJO.getStatus();
            assertCheck.append(actions.assertEqualStringStatusCode(status, "401", "Status Code Matched and is -" + status, "Response not matched and statusCode is:- " + status));
            assertCheck.append(actions.assertEqualStringType(clientConfigOpenApiPOJO.getMessage(), "Unauthorized", "Api Failed Successfully with Invalid Token", "Response not matched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testClientConfigWithInvalidToken " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testFirstLastCategories() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/firstlast/categories, This API will return the first last category", "description");
            FirstLastOpenApiRequest firstLastOpenApiPOJO = api.firstLastOpenApiRequest(validHeaderList);
            final Integer statusCode = firstLastOpenApiPOJO.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is -" + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final int size = firstLastOpenApiPOJO.getResult().size();
            assertCheck.append(actions.assertEqualIntType(size, 5, size + " Level Fetched Successfully", "Levels not fetched and is -" + size));
            assertCheck.append(actions.assertEqualBoolean(firstLastOpenApiPOJO.getResult().isEmpty(), false, "Response is NOT NULL", "Response is NULL"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testFirstLastCategories " + e.getMessage(), false);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testParentCategory() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/parent/categories, This API will return Category Level for Particular Category Id", "description");
            ParentCategoryOpenApiRequest parentCategoryOpenApiRequest = api.parentCategoryOpenApiRequest(validHeaderList, constants.getValue(ApplicationConstants.OPEN_API_CATEGORY_ID));
            final Integer statusCode = parentCategoryOpenApiRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is -" + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String status = parentCategoryOpenApiRequest.getStatus();
            assertCheck.append(actions.assertEqualStringType(status, "SUCCESS", "Status Matched Successfully", "Status not matched and is -" + status));
            final int size = parentCategoryOpenApiRequest.getResult().size();
            assertCheck.append(actions.assertEqualIntType(size, 5, size + " Level Fetched Successfully", "Levels not fetched and is -" + size));
            assertCheck.append(actions.assertEqualBoolean(parentCategoryOpenApiRequest.getResult().isEmpty(), false, "Response is Not Null", "Response is null"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testParentCategory " + e.getMessage(), false);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testChildCategory() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/child/categories, This API will return child categories", "description");
            ChildCategoryOpenApiRequest childCategoryOpenApiRequest = api.childCategoryOpenApiRequest(validHeaderList, Integer.parseInt(constants.getValue(ApplicationConstants.OPEN_API_CHILD_CATEGORY_ID)));
            final Integer statusCode = childCategoryOpenApiRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Response Matched", "Response not matched and statusCode is:- " + statusCode));
            final String status = childCategoryOpenApiRequest.getStatus();
            assertCheck.append(actions.assertEqualStringType(status, "SUCCESS", "Status Matched Successfully", "Status not matched and is -" + status));
            assertCheck.append(actions.assertEqualBoolean(childCategoryOpenApiRequest.getResult().isEmpty(), false, "Response is Not Null", "Response is null"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testChildCategory " + e.getMessage(), false);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest","ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testInteractionIssue() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/interactions/issue, This API will create the Interaction Issue on single Click", "description");
            InteractionIssueOpenApiRequest interactionIssueOpenApiRequest = api.interactionIssueOpenApiRequest(validHeaderList, getOpenAPIClientConfig(), getOpenAPIIssueDetails(), getOpenAPICategoryHierarchy());
            final Integer statusCode = interactionIssueOpenApiRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = interactionIssueOpenApiRequest.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Interaction created successfully.", message, "Interaction not created or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testInteractionIssue " + e.getMessage(), false);
        }
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testSearchTicket() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/tickets, This API will Search the Ticket", "description");
            SearchTicketOpenRequest searchTicketOpenRequest = api.searchTicketOpenRequest(validHeaderList, getOpenAPIClientConfig());
            final Integer statusCode = searchTicketOpenRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is - " + statusCode, "Response not matched and statusCode is - " + statusCode));
            final String status = searchTicketOpenRequest.getStatus();
            assertCheck.append(actions.assertEqualStringType(status, "SUCCESS", "Status Matched and is - " + status, "Status Not Matched and is - " + status));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSearchTicket " + e.getMessage(), false);
        }
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest","ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testTicketHistoryLog() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/fetch/ticket/history/log, This API will fetch the Ticket History Log", "description");
            TicketHistoryLogOpenRequest ticketHistoryLogOpenRequest = api.ticketHistoryLogOpenRequest(validHeaderList, getOpenApiTicketId());
            final Integer statusCode = ticketHistoryLogOpenRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Matched and is - " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = ticketHistoryLogOpenRequest.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Tickets History fetched successfully.", message, "Ticket History Not Fetched or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryLog " + e.getMessage(), false);
        }
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest","ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testSearchTicketByTicketId() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/fetch/ticket?id, This API will provides Capability to Search Ticket by Ticket Id", "description");
            TicketSearchByTicketIdOpenRequest ticketSearchByTicketIdOpenRequest = api.ticketSearchByTicketIdOpenRequest(validHeaderList, getOpenApiTicketId());
            final Integer statusCode = ticketSearchByTicketIdOpenRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Matched and is - " + statusCode, "Response not matched and statusCode is:-" + statusCode));
            final String message = ticketSearchByTicketIdOpenRequest.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Tickets fetched successfully.", message, "Tickets Not Fetched or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testLayoutHistory " + e.getMessage(), false);
        }
    }


    @Test(priority = 11, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testLayoutHistory() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/layout, This API will fetch Layout History", "description");
            IssueLayoutOpenRequest issueLayoutOpenRequest = api.issueLayoutOpenRequest(validHeaderList, constants.getValue(ApplicationConstants.OPEN_API_CATEGORY_ID));
            final Integer statusCode = issueLayoutOpenRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Matched and is - " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = issueLayoutOpenRequest.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Layout fetched successfully.", message, "Layout Not Fetched or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testLayoutHistory " + e.getMessage(), false);
        }
    }

    @Test(priority = 12, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testCreateComment() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/comment, This API will create the comment on ticket", "description");
            CommentOpenApiResponse commentOpenApiResponse = api.createCommentOpenApi(getOpenApiTicketId());
            final Integer statusCode = commentOpenApiResponse.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = commentOpenApiResponse.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Comment added successfully", message, "Comment not created or Some Assertion Failed"));
            final String comment = commentOpenApiResponse.getResult().getComment();
            assertCheck.append(actions.assertEqualStringType(comment, RequestSource.COMMENT, message, "Comment not matched or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testCreateComment " + e.getMessage(), false);
        }
    }

    @Test(priority = 13, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testUpdateComment() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/update/comment, This API will update the comment on ticket", "description");
            CommentOpenApiResponse commentOpenApiResponse = api.updateCommentOpenApi(getOpenApiCommentId());
            final Integer statusCode = commentOpenApiResponse.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = commentOpenApiResponse.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Comment updated successfully", message, "Comment not updated or Some Assertion Failed"));
            final String comment = commentOpenApiResponse.getResult().getComment();
            assertCheck.append(actions.assertEqualStringType(comment, RequestSource.UPDATE_COMMENT, message, "Comment not matched or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testUpdateComment " + e.getMessage(), false);
        }
    }

    @Test(priority = 14, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testDeleteComment() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/delete/comment, This API will delete the comment from ticket", "description");
            CommentOpenApiResponse commentOpenApiResponse = api.deleteCommentOpenApi(getOpenApiCommentId());
            final Integer statusCode = commentOpenApiResponse.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = commentOpenApiResponse.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Comment deleted successfully", message, "Comment not deleted or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testDeleteComment " + e.getMessage(), false);
        }
    }

    @Test(priority = 15, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelfCareConfigured")
    public void testCreateCommentWithOpcoAndLocaleHeader() {
        try {
            selUtils.addTestcaseDescription("API is - v1/openapi/comment, This API will create the comment on ticket", "description");
            CommentOpenApiResponse commentOpenApiResponse = api.createCommentOpenApi(getOpenApiTicketId());
            final Integer statusCode = commentOpenApiResponse.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "Status Code Matched and is " + statusCode, "Response not matched and statusCode is:- " + statusCode));
            final String message = commentOpenApiResponse.getMessage();
            assertCheck.append(actions.assertEqualStringType(message, "Comment added successfully", message, "Comment not created or Some Assertion Failed"));
            final String comment = commentOpenApiResponse.getResult().getComment();
            assertCheck.append(actions.assertEqualStringType(comment, RequestSource.COMMENT, message, "Comment not matched or Some Assertion Failed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testCreateComment " + e.getMessage(), false);
        }
    }
}
