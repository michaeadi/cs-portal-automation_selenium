package com.airtel.cs.api.clientconfig;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.request.clientconfig.AllConfiguredClientRequest;
import org.testng.annotations.Test;

public class SearchableFieldsClientConfigTest extends ApiPrerequisites {

    @Test(priority = 1, description = "Validate Api to fetch all the searchable fields for all the client", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testConfiguredClientsWithSupervisorToken() {
        selUtils.addTestcaseDescription("API is - v1/client/fields with Supervisor Token, This API will return all the configured clients", "description");
        AllConfiguredClientRequest allConfiguredClientPOJO = api.allConfiguredClientRequest(validHeaderList);
        assertCheck.append(actions.assertEqualStringType(allConfiguredClientPOJO.getStatusCode(), "200", "Status Code Matched", "Status Code Not Matched and is - " + allConfiguredClientPOJO.getStatusCode()));
        assertCheck.append(actions.assertEqualStringType(allConfiguredClientPOJO.getResult().get(0).getClient(), "Selfcare", "Client Name Matched", "Client Name not Matched and is - " + allConfiguredClientPOJO.getResult().get(0).getClient()));
        assertCheck.append(actions.assertEqualStringType(allConfiguredClientPOJO.getResult().get(0).getLabel().toUpperCase(), "CUSTOMER MSISDN(SELFCARE)", "Label Matched Successfully", "Label Not Matched Successfully - " + allConfiguredClientPOJO.getResult().get(0).getLabel()));
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, description = "Validate Api to fetch all the searchable fields for all the client with Invalid Method", groups = {"RegressionTest"})
    public void testConfiguredClientAPIWithWrongMethod() {
        selUtils.addTestcaseDescription("API is - v1/client/fields with Supervisor Token but Invalid method, This API will not return clients information", "description");
        final Integer status = api.allConfigureRequestWithInvalidMethod(validHeaderList);
        assertCheck.append(actions.assertEqualIntType(status, 405, "Success Message:- Method Not Allowed", "Status Message Not Matched and is - " + status));
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /*@NOTE
    Please keep this test case in the end of this class, as this is resetting the header list and put token for Frontend agent
     */
    @Test(priority = 3, description = "Validate Api with Frontend Agent Token", groups = {"RegressionTest"})
    public void testConfiguredClientWithFrontendAgent() throws Exception {
        selUtils.addTestcaseDescription("API is - v1/client/fields with Frontend Agent Token, This API will return all the configured clients", "description");
        getTokenForBackendAgent();
        AllConfiguredClientRequest allConfiguredClientPOJO = api.allConfiguredClientRequest(validHeaderList);
        assertCheck.append(actions.assertEqualStringType(allConfiguredClientPOJO.getMessage(), "Access Denied", "Response Message Matched", "Response Message Not Matched and is - " + allConfiguredClientPOJO.getMessage()));
        assertCheck.append(actions.assertEqualStringType(allConfiguredClientPOJO.getStatus(), "200", "Status Matched", "Status Not Matched and is - " + allConfiguredClientPOJO.getStatus()));
        assertCheck.append(actions.assertEqualStringType(allConfiguredClientPOJO.getStatusCode(), "4054", "Status Code Matched", "Status Code Not Matched and is - " + allConfiguredClientPOJO.getStatusCode()));
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
