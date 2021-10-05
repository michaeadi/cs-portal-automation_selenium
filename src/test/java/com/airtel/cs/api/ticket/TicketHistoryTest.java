package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.tickethistory.TicketHistoryRequest;
import org.testng.annotations.Test;

public class TicketHistoryTest extends ApiPrerequisites {

    @Test(priority = 1, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryValid(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets, Verify the response", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            TicketHistoryRequest ticketHistoryWithFilterRequest = api.ticketHistoryWithFilterRequest(validHeaderList, getValidClientConfig(MSISDN), getTicketId(validCategoryId, clientConfig));
            final Integer statusCode = ticketHistoryWithFilterRequest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));
            final String sourceApp = ticketHistoryWithFilterRequest.getResult().get(0).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "SourceApp is not null and is -" + sourceApp, "SourceApp is null and is -" + sourceApp));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryValid " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithoutFilter() {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets without filter, Verify the response", "description");
            final TicketHistoryRequest ticketHistoryWithoutFilter = api.ticketHistoryWithoutFilter(validHeaderList, getValidClientConfig(MSISDN));
            final Integer statusCode = ticketHistoryWithoutFilter.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));
            final String sourceApp = ticketHistoryWithoutFilter.getResult().get(0).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "SourceApp is not null and is -" + sourceApp, "SourceApp is null and is -" + sourceApp));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithoutFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithCategoryFilter(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Category Filter, Verify the response", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            final TicketHistoryRequest ticketHistoryWithCategoryFilter = api.ticketHistoryWithCategoryFilter(validHeaderList, getValidClientConfig(MSISDN), getLastCategoryId(validCategoryId).split(":")[1].replaceAll("[(){}]",""));
            final Integer statusCode = ticketHistoryWithCategoryFilter.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));
            final String sourceApp = ticketHistoryWithCategoryFilter.getResult().get(0).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "SourceApp is not null and is -" + sourceApp, "SourceApp is null and is -" + sourceApp));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }
}
