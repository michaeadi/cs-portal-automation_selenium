package com.airtel.cs.api.interactionissue;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.interaction.InteractionRequest;
import org.testng.annotations.Test;

import java.util.List;

public class InteractionCreateTest extends ApiPrerequisites {
    String validClientInfo = null;

    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest"})
    public void createInteractionWithValidTest() {
        selUtils.addTestcaseDescription("Validate /v1/interactions API with valid Request", "Validate /v1/interactions API With valid Request");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            } else if (clientConfig.get(i).getIsDeActivate().equalsIgnoreCase("no")) {
                validClientInfo += "," + "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            }
        }
        InteractionRequest interaction = api.createInteraction(validHeaderList, validClientInfo);
        if (Integer.parseInt(interaction.getStatusCode()) != 200) {
            commonLib.fail("Create Interaction API Response is not expected. Expected 200 and found " + interaction.getStatusCode(), false);
        } else {
            assertCheck.append(actions.assertEqualStringType(interaction.getMessage(), constants.getValue("interactionCreated"), "API Response Message is as expected", "API Response Message NOT as expected"));
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void createInteractionWithInvalidTest() {
        selUtils.addTestcaseDescription("Validate /v1/interactions API with Invalid Request", "Validate /v1/interactions API With Invalid Request");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            } else {
                validClientInfo += "," + "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            }
        }
        String invalidClientInfo = validClientInfo + "," + "\"invalid\": \"invalid\"";
        InteractionRequest interaction = api.createInteraction(validHeaderList, invalidClientInfo);
        if (Integer.parseInt(interaction.getStatusCode()) == 2902) {
            restUtils.printInfoLog("Then API Response Status Code must be 2902");
            assertCheck.append(actions.assertEqualStringType(interaction.getMessage(), constants.getValue("invalidRequestWithClient"), "API Response Message is as expected", "API Response Message NOT as expected"));
        } else {
            restUtils.printFailLog("Then API Response Status Code is not 2902 as expected and found " + interaction.getStatusCode());
            commonLib.fail("API Response is not 2902 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest"})
    public void createInteractionWithInValidTokenTest() {
        selUtils.addTestcaseDescription("Validate /v1/interactions API with Invalid Token", "Validate /v1/interactions API With Invalid Token");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            } else {
                validClientInfo += "," + "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            }
        }
        InteractionRequest interaction = api.createInteraction(restUtils.invalidToken(), validClientInfo);
        if (Integer.parseInt(interaction.getStatus()) == 401) {
            restUtils.printInfoLog("Then API Response Status Code must be 401");
            assertCheck.append(actions.assertEqualStringType(interaction.getMessage(), constants.getValue("unauthorized"), "API Response Message is as expected", "API Response Message NOT as expected"));
        } else {
            restUtils.printFailLog("Then API Response Status Code is not 401 as expected and found " + interaction.getStatus());
            commonLib.fail("API Response is not 401 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void createInteractionWithMandatoryInfoBlank() {
        selUtils.addTestcaseDescription("Validate /v1/interactions API with Mandatory Client Config Param Blank", "Validate /v1/interactions API With Invalid Token");
        DataProviders data = new DataProviders();
        List<ClientConfigDataBean> clientConfig = data.getClientConfig();
        for (int i = 0; i < clientConfig.size(); i++) {
            if (i == 0) {
                validClientInfo = "\"" + clientConfig.get(i).getFieldName() + "\": \"" + clientConfig.get(i).getValue() + "\"";
            } else if (clientConfig.get(i).getIsMandatory().equalsIgnoreCase("true")) {
                continue;
            }
        }
        InteractionRequest interaction = api.createInteraction(validHeaderList, validClientInfo);
        if (Integer.parseInt(interaction.getStatusCode()) == 2901) {
            restUtils.printInfoLog("Then API Response Status Code must be 2901");
            assertCheck.append(actions.assertEqualStringType(interaction.getMessage(), constants.getValue("mandatoryFieldMissing"), "API Response Message is as expected", "API Response Message NOT as expected"));
        } else {
            restUtils.printFailLog("Then API Response Status Code is not 2901 as expected and found " + interaction.getStatusCode());
            commonLib.fail("API Response is not 2902 as expected.", false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
