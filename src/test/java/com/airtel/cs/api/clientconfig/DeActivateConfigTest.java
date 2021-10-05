package com.airtel.cs.api.clientconfig;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.clientconfig.ClientDeactivateRequest;
import com.airtel.cs.model.response.clientconfig.ConfigResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;


class DeActivateConfigTest extends ApiPrerequisites {

    Integer id = null;

    @Test(priority = 1, description = "Validate API Response Test is Successful", dataProvider = "deActivateClientConfig", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void deActivateClientConfigTest(ClientConfigDataBean data) {
        selUtils.addTestcaseDescription("Validate Deactivate /clients/config API with valid Request " + data.getFieldName(), "Validate Create /clients/config API With valid Request");
        ArrayList<ConfigResponse> searchConfig = api.getClientConfig(validHeaderList).getResult();
        ClientDeactivateRequest deActivateConfig = null;
        for (ConfigResponse clientConfig : searchConfig) {
            if (clientConfig.getFieldName().equalsIgnoreCase(data.getFieldName())) {
                id = clientConfig.getId();
                restUtils.printInfoLog("Client Config Found and ready to deactivate");
                break;
            } else {
                Assert.fail("Client Config does not found in API Response");
            }
        }
        if (id != null) {
            deActivateConfig = api.deActivateClientConfig(validHeaderList, id);
        }

        if (!deActivateConfig.getStatusCode().equalsIgnoreCase("200")) {
            commonLib.fail("Deactivate Client Config API Response is not expected. Expected 200 but found " + deActivateConfig.getStatusCode(), false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void deActivateClientConfigWithInvalidTokenTest() {
        selUtils.addTestcaseDescription("Validate DeActivate /clients/config API with valid Request and Invalid Token", "Validate DeActivate /clients/config API With valid Request and Invalid Token");
        ClientDeactivateRequest deActivateConfig = null;
        if (id != null) {
            deActivateConfig = api.deActivateClientConfig(restUtils.invalidToken(), id);
        } else {
            Assert.fail("Client Config does not found in API Response");
        }
        if (Integer.parseInt(deActivateConfig.getStatus()) == 401) {
            assertCheck.append(actions.assertEqualStringType(deActivateConfig.getMessage(), config.getProperty("unauthorized"), "API Response Message is as expected", "API Response Message NOT as expected"));
        } else {
            commonLib.fail("API Response is not 401 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void deActivateClientConfigWithInvalidIdTest() {
        selUtils.addTestcaseDescription("Validate DeActivate /clients/config API with invalid id and valid Token", "Validate DeActivate /clients/config API with invalid id and valid Token");
        ClientDeactivateRequest deActivateConfig = null;
        deActivateConfig = api.deActivateClientConfig(validHeaderList, 9999);
        if (Integer.parseInt(deActivateConfig.getStatusCode()) == 2900) {
            assertCheck.append(actions.assertEqualStringType(deActivateConfig.getMessage(), config.getProperty("configNotPresent"), "API Response Message is as expected", "API Response Message NOT as expected"));
        } else {
            commonLib.fail("API Response is not 401 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}