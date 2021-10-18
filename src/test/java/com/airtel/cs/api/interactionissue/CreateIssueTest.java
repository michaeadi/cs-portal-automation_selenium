package com.airtel.cs.api.interactionissue;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.createissue.CreateIssueRequest;
import com.airtel.cs.model.request.interaction.InteractionRequest;
import org.testng.annotations.Test;

public class CreateIssueTest extends ApiPrerequisites {

    Integer validCategoryId = 0;


    @Test(priority = 1, description = "Validate Create Interaction API", groups = {"SanityTest", "RegressionTest"})
    public void createInteraction() {
        try {
            selUtils.addTestcaseDescription("Create Interaction using /v1/interactions API with valid Request", "description");
            InteractionRequest interaction = api.createInteraction(validHeaderList, getValidClientConfig(MSISDN));
            assertCheck.append(actions.assertEqualStringType(interaction.getStatusCode(), "200", "Status Code Matched", "Status Code not Matched and is - " + interaction.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(interaction.getMessage(), config.getProperty("interactionCreated"), "Response Message Matched Successfully", "Response Message Not Matched and is - " + interaction.getMessage()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - createInteraction " + e.getMessage(), false);
        }
    }

    /*@Test(priority = 2, description = "Validate API Response Test is Successful", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest"})
    public void createNFTRIssueWithValidTest(NftrDataBeans list) {
        selUtils.addTestcaseDescription("Validate Create issue /v1/issue API With Category Id: " + getClientCode(list), "description");
        setLastCategoryIntoMap();
        validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        CreateIssueRequest interactionIssue = api.createIssue(validHeaderList, getInteractionId(), getIssueDetails(validCategoryId), getCategoryHierarchy(clientConfig, validCategoryId) ,);
        assertCheck.append(actions.assertEqualIntType(interactionIssue.getStatusCode(), 200, "Status Code Matched", "Status Code Not Matched and is - " + interactionIssue.getStatusCode()));
        assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), config.getProperty("interactionCreated"), "API message Matched", "API message is not as expected"));
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", dataProvider = "FTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest"})
    public void createFTRIssueWithValidTest(FtrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate Create issue /v1/issue API With Category Id: " + getClientCode(list), "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            CreateIssueRequest interactionIssue = api.createIssue(validHeaderList, getInteractionId(), getIssueDetails(validCategoryId), getCategoryHierarchy(clientConfig, validCategoryId) ,isHBBProfile);
            assertCheck.append(actions.assertEqualIntType(interactionIssue.getStatusCode(), 200, "Status Code Matched", "Status Code Not Matched and is - " + interactionIssue.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), config.getProperty("interactionCreated"), "API message Matched", "API message is not as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - createFTRIssueWithValidTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validate API Response Test is Successful", dataProvider = "FTRIssue", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void createFTRIssueWithInValidTokenTest(FtrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate Create issue /v1/issue API With Invalid Token Test: ", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            CreateIssueRequest interactionIssue = api.createIssue(restUtils.invalidToken(), getInteractionId(), getIssueDetails(validCategoryId), getCategoryHierarchy(clientConfig, validCategoryId));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getStatus(), "401", "Status Matched Successfully", "Status Not Matched and is - " + interactionIssue.getStatus()));
            assertCheck.append(actions.assertEqualStringType(interactionIssue.getMessage(), config.getProperty("unauthorized"), "API Response Message Matched", "API Response Message as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - createFTRIssueWithInValidTokenTest " + e.getMessage(), false);
        }
    }*/
}
