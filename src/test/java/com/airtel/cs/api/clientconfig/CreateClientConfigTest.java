package com.airtel.cs.api.clientconfig;

import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.cs.request.clientconfig.ClientConfigRequest;
import com.airtel.cs.model.cs.response.clientconfig.ConfigResponse;
import org.testng.annotations.Test;

import java.util.List;

public class CreateClientConfigTest extends ApiPrerequisites {
    String validClientInfo = null;

    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void createClientConfigTest() {
        selUtils.addTestcaseDescription("Validate Create /clients/config API with valid Request", "Validate Create /clients/config API With valid Request");
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "{\"fieldName\":\"" + clientConfig.get(i).getFieldName() + "\",\"mandatory\":\"" + clientConfig.get(i).getIsMandatory() + "\",\"searchable\":\"" + clientConfig.get(i).getIsSearchAble() + "\",\"isActive\":\"" + clientConfig.get(i).getIsActive() + "\"}";
            } else {
                validClientInfo += "," + "{\"fieldName\":\"" + clientConfig.get(i).getFieldName() + "\",\"mandatory\":\"" + clientConfig.get(i).getIsMandatory() + "\",\"searchable\":\"" + clientConfig.get(i).getIsSearchAble() + "\",\"isActive\":\"" + clientConfig.get(i).getIsActive() + "\"}";
            }
        }
        ClientConfigRequest createConfig = api.createClientConfig(validHeaderList, validClientInfo);
        if (!createConfig.getStatusCode().equalsIgnoreCase("200")) {
            commonLib.fail(" Create Client Config API Response is not expected. Expected 200 but found " + createConfig.getStatusCode(), false);
        } else {
            for (ConfigResponse clientConfigResult : createConfig.getResult()) {
                assertCheck.append(actions.assertEqualStringType(clientConfigResult.getClient().toUpperCase().trim(), CLIENT.toUpperCase().trim(), "Config created for same client","Config does not created for same client"));
                assertCheck.append(actions.assertEqualBooleanNotNull(clientConfigResult.getFieldName() != null, "Field name can be empty","Field name can not be empty"));
            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void createClientConfigWithInvalidTokenTest() {
        selUtils.addTestcaseDescription("Validate Create /clients/config API with valid Request and Invalid Token", "Validate Create /clients/config API With valid Request and Invalid Token");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "{\"fieldName\":\"" + clientConfig.get(i).getFieldName() + "\",\"mandatory\":\"" + clientConfig.get(i).getIsMandatory() + "\",\"searchable\":\"" + clientConfig.get(i).getIsSearchAble() + "\",\"isActive\":\"" + clientConfig.get(i).getIsActive() + "\"}";
            } else {
                validClientInfo += "," + "{\"fieldName\":\"" + clientConfig.get(i).getFieldName() + "\",\"mandatory\":\"" + clientConfig.get(i).getIsMandatory() + "\",\"searchable\":\"" + clientConfig.get(i).getIsSearchAble() + "\",\"isActive\":\"" + clientConfig.get(i).getIsActive() + "\"}";
            }
        }
        ClientConfigRequest createConfig = api.createClientConfig(restUtils.invalidToken(), validClientInfo);
        if (Integer.parseInt(createConfig.getStatus()) == 401) {
            assertCheck.append(actions.assertEqualStringType(createConfig.getMessage(), constants.getValue("unauthorized"), "API Response Message is as expected", "API Response Message is as NOT expected"));
        } else {
            commonLib.fail(" API Response is not 401 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void createClientConfigWithInvalidRequestTest() {
        selUtils.addTestcaseDescription("Validate Create /clients/config API with valid Invalid Request(Field Name Param Missing) and valid Token", "Validate Create /clients/config API With Invalid Request and valid Token");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "{\"fieldName\":\"" + clientConfig.get(i).getFieldName() + "\",\"mandatory\":\"" + clientConfig.get(i).getIsMandatory() + "\",\"searchable\":\"" + clientConfig.get(i).getIsSearchAble() + "\",\"isActive\":\"" + clientConfig.get(i).getIsActive() + "\"}";
            } else {
                validClientInfo += "," + "{\"fieldName\":\"" + clientConfig.get(i).getFieldName() + "\",\"mandatory\":\"" + clientConfig.get(i).getIsMandatory() + "\",\"searchable\":\"" + clientConfig.get(i).getIsSearchAble() + "\",\"isActive\":\"" + clientConfig.get(i).getIsActive() + "\"}";
            }
        }
        String invalidClientInfo = validClientInfo + "," + "{\"mandatory\":\"" + false + "\",\"searchable\":" + true + ",\"isActive\":" + true + "}";
        ClientConfigRequest createConfig = api.createClientConfig(validHeaderList, invalidClientInfo);
        restUtils.printInfoLog("And Request API with Invalid Request(Field Name Parameter Missing)");
        restUtils.printFailLog("Then API Response Status Code is not 400");
        commonLib.fail(" API Response is not 400 as expected.", false);
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void createClientConfigWithBlankRequestTest() {
        selUtils.addTestcaseDescription("Validate Create /clients/config API with valid Invalid Request(Field Name Blank) and valid Token", "Validate Create /clients/config API With Invalid Request and valid Token");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        String validClientInfo = "{\"fieldName\":" + null + ",\"mandatory\":" + null + ",\"searchable\":" + null + ",\"isActive\":" + null + "}";
        ClientConfigRequest createConfig = api.createClientConfig(validHeaderList, validClientInfo);
        restUtils.printInfoLog("And Request API with Invalid Request(Field Name Parameter Missing)");
        if (createConfig.getStatus().equalsIgnoreCase("2902")) {
            restUtils.printInfoLog("Then API Response Status Code must be 2902");
            assertCheck.append(actions.assertEqualStringType(createConfig.getMessage(), constants.getValue("invalidRequestWithClient"), "API Response Message is as expected","API Response Message NOT as expected"));
        } else {
            restUtils.printFailLog("Then API Response Status Code is not 2902 as expected. API Response is " + createConfig.getStatus());
            commonLib.fail(" API Response is not 2902 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
