package com.airtel.cs.api.openapi;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.cs.request.openapi.clientconfig.ClientConfigOpenApiRequest;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

public class OpenApiWithCSTokenTest extends ApiPrerequisites {
    /*
    CWUM = Client without UM
     */
    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void getClientConfigCWUMWithCSUser() {
        try {
            selUtils.addTestcaseDescription("API is: - v1/openapi/clients/config, This API will return Client details like client Name and field Name", "description");
            ClientConfigOpenApiRequest clientConfigOpenApiRequest = api.clientWithoutUMRequest(validHeaderList);
            String message = clientConfigOpenApiRequest.getMessage();
            assertCheck.append(actions.assertEqualStringStatusCode(clientConfigOpenApiRequest.getStatus(), "401", "Response Matched", "Response not matched. Status is:- " + clientConfigOpenApiRequest.getStatus() + " and Message is - " + message));
            if (StringUtils.equals(message, "No message available") || StringUtils.equals(message, "Cannot call sendError() after the response has been committed")) {
                isSelfcareNOTConfigured = true;
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getClientConfigCWUMWithCSUser " + e.getMessage(), false);
        }
    }
}
