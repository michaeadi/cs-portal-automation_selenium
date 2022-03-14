package com.airtel.cs.api.clientconfig;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.model.cs.request.clientconfig.ClientConfigRequest;
import com.airtel.cs.model.cs.response.clientconfig.ConfigResponse;
import org.testng.annotations.Test;

public class ClientConfigTest extends ApiPrerequisites {


    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void getClientConfigTest() {
        selUtils.addTestcaseDescription("Validate Get /v1/clients/config API with valid Request", "Validate Get /v1/clients/config API With valid Request");
        ClientConfigRequest getConfig = api.getClientConfig(validHeaderList);
        assertCheck.append(actions.assertEqualStringType(getConfig.getStatusCode(), "200", "Then API Response Status Code must be 200", "Search Client Config API Response is not expected. Expected 200 and found \" + getConfig.getStatusCode()"));
        for (ConfigResponse result : getConfig.getResult()) {
            assertCheck.append(actions.assertEqualStringType(result.getClient().toUpperCase().trim(), CLIENT.toUpperCase().trim(), "Client name is same as User-Client.", "Client name is not same as User-Client."));
            assertCheck.append(actions.assertEqualStringNotNull(result.getFieldName(), "Field Name Can not be Empty for for any client config", ""));
            assertCheck.append(actions.assertEqualBooleanNotNull(result.getIsActive(), "Is Active Field is not empty", "Is Active Field Can not be Empty for for any client config"));
            assertCheck.append(actions.assertEqualBooleanNotNull(result.getMandatory(), "Mandatory Field is not empty", "Mandatory Field Can not be Empty for for any client config"));
            assertCheck.append(actions.assertEqualBooleanNotNull(result.getSearchable(), "Searchable Field is not empty", "Searchable Field Can not be Empty for for any client config"));
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void getClientConfigInvalidTokenTest() {
        selUtils.addTestcaseDescription("Validate Get /v1/clients/config API with valid Invalid Token", "Validate Get /v1/clients/config API With valid Invalid Token");
        ClientConfigRequest getConfig = api.getClientConfig(restUtils.invalidToken());
        assertCheck.append(actions.assertEqualStringType(getConfig.getStatus(), "401", "Then API Response Status Code must be 401", "Search Client Config API Response is not expected. Expected 401 and found" + getConfig.getStatusCode()));
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
