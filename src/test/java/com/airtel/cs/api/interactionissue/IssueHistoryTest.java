package com.airtel.cs.api.interactionissue;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.issuehistory.IssueHistoryRequest;
import org.testng.annotations.Test;

public class IssueHistoryTest extends ApiPrerequisites {

    String validClientInfo = null;

    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void fetchIssueHistoryWithValidTest() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/issue/history API with valid Request", "description");
            IssueHistoryRequest issueHistory = api.getIssueHistory(validHeaderList, getValidClientConfig(MSISDN), "");
            assertCheck.append(actions.assertEqualIntType(issueHistory.getStatusCode(), 200, "Status Code Matched", "API Response as not expected. Expected 200 but found " + issueHistory.getStatusCode()));
            final String sourceApp = issueHistory.getResult().get(0).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "Source App is not null and is -" + sourceApp, "Source App is null in the request and is -" + sourceApp));
            final Integer totalCount = issueHistory.getTotalCount();
            assertCheck.append(actions.assertEqualIntNotNull(totalCount, "Total Number of History found " + totalCount, "No History Available with search values"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - fetchIssueHistoryWithValidTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", groups = {"RegressionTest"})
    public void fetchIssueHistoryWithInValidToken() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/issue/history API with Invalid Token", "description");
            IssueHistoryRequest issueHistory = api.getIssueHistory(restUtils.invalidToken(), getValidClientConfig(MSISDN), "");
            assertCheck.append(actions.assertEqualStringType(issueHistory.getStatus(), "401", "Status Code Matched", "Status Code Not Matched and is - " + issueHistory.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(issueHistory.getMessage(), config.getProperty("unauthorized"), "Response Message Matched", "API Response Message as not expected - " + issueHistory.getMessage()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - fetchIssueHistoryWithInValidToken " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validate API Response Test is Successful", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void fetchIssueHistoryWithValidTicketId(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /v1/issue/history API with valid Ticket Id", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            IssueHistoryRequest issueHistory = api.getIssueHistory(validHeaderList, getValidClientConfig(MSISDN), getTicketId(validCategoryId, clientConfig));
            if (issueHistory.getStatusCode() == 200) {
                if (issueHistory.getTotalCount() <= 0) {
                    restUtils.printWarningLog("No History Available with search values and Ticket Id: 190121000165");
                } else {
                    restUtils.printPassLog("Ticket History found.");
                }
            } else {
                commonLib.fail("API Response as not expected. Expected 200 but found " + issueHistory.getStatusCode(),false);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - fetchIssueHistoryWithValidTicketId " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validate API Response Test is Successful", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void fetchIssueHistoryWithInValidTicketId(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /v1/issue/history API with Invalid Ticket Id", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            IssueHistoryRequest issueHistory = api.getIssueHistory(validHeaderList, getValidClientConfig(MSISDN), getTicketId(validCategoryId, clientConfig));
            if (issueHistory.getStatusCode() == 200) {
                if (issueHistory.getTotalCount() <= 0) {
                    restUtils.printFailLog("Ticket History Not found.");
                } else {
                    restUtils.printPassLog("Ticket History Available with Provided Ticket Id");
                }
            } else {
                commonLib.fail("API Response as not expected. Expected 200 but found " + issueHistory.getStatusCode(),false);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - fetchIssueHistoryWithInValidTicketId " + e.getMessage(), false);
        }
    }
}
