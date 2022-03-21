package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.beantoexcel.AgentDataExcelToBeanDao;
import com.airtel.cs.commonutils.dataproviders.databeans.AgentDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.cs.request.interactionissue.InteractionIssueRequest;
import com.airtel.cs.model.cs.request.issue.CategoryHierarchy;
import com.airtel.cs.model.sr.response.layout.IssueLayoutResponse;
import com.airtel.cs.model.cs.request.ticketAssign.TicketAssignResponse;
import com.airtel.cs.model.cs.request.ticketdetail.TicketRequest;
import com.airtel.cs.model.sr.response.tickethistory.TicketHistoryRequest;
import com.airtel.cs.model.cs.request.tickethistory.TicketHistoryResult;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.annotations.Test;

public class TicketHistoryTest extends ApiPrerequisites {

    InteractionIssueRequest interactionIssue;
    CategoryHierarchy category;
    TicketRequest ticketDetails;

    /**
     * This method is used to prepare Ticket related required for test cases.
     *
     * @param list
     */
    public void prepareTicketData(NftrDataBeans list) {

        if (Objects.isNull(interactionIssue)) {
            category = getLastCategory(validCategoryId);
            interactionIssue = api.createInteractionIssue(validHeaderList, getValidClientConfig(MSISDN), getIssueDetails(validCategoryId),
                    "{\"id\":" + category.getId() + "}");

            ticketDetails = api.getTicketDetailById(validHeaderList, interactionIssue.getResult().getIssues().get(0).getTicket().getTicketId());

        }
    }

    @Test(priority = 10, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
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
            final TicketHistoryRequest ticketHistoryWithCategoryFilter = api.ticketHistoryWithCategoryFilter(validHeaderList, getLastCategoryId(validCategoryId).split(":")[1].replaceAll("[(){}]", ""));
            final Integer statusCode = ticketHistoryWithCategoryFilter.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));
            final String sourceApp = ticketHistoryWithCategoryFilter.getResult().get(0).getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "SourceApp is not null and is -" + sourceApp, "SourceApp is null and is -" + sourceApp));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithCategoryLevelAndValue(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Category Level and Value Filter, Verify the response", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            prepareTicketData(list);
            final TicketHistoryRequest ticketHistoryWithCategoryFilter = api.ticketHistoryWithCategoryLevelAndValue(validHeaderList, category.getLevel(), category.getCategoryName());
            final Integer statusCode = ticketHistoryWithCategoryFilter.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

            Boolean isCategorySame = true;

            if (Objects.nonNull(ticketHistoryWithCategoryFilter)
                    && CollectionUtils.isNotEmpty(ticketHistoryWithCategoryFilter.getResult())) {

                for (TicketHistoryResult ticket : ticketHistoryWithCategoryFilter.getResult()) {

                    if (CollectionUtils.isNotEmpty(ticket.getCategoryLevel())) {

                        if (ticket.getCategoryLevel().stream().noneMatch(category -> {
                            return category.getCategoryName().equalsIgnoreCase(category.getCategoryName())
                                    && category.getLevel() == category.getLevel();
                        })) {
                            isCategorySame = false;
                            break;
                        }
                    } else {
                        isCategorySame = false;
                        break;
                    }
                }

            } else {
                isCategorySame = false;
            }

            assertCheck.append(actions.assertEqualBoolean(isCategorySame, Boolean.TRUE, "Tickets Fetched of same category as given in filter", "Tickets Failed to fetched of same category as given in filter"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 5, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithAssigenedTicketPool(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with assigned Ticket pool Filter, Verify the response", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            prepareTicketData(list);

            String ticketPoolName = ticketDetails.getResult().getQueue().getQueueName();
            final TicketHistoryRequest ticketHistory = api.ticketHistoryWithAssigenedTicketPool(validHeaderList, ticketPoolName);

            final Integer statusCode = ticketHistory.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

            Boolean isTicketPoolSame = true;

            if (Objects.nonNull(ticketHistory)
                    && CollectionUtils.isNotEmpty(ticketHistory.getResult())) {

                for (TicketHistoryResult ticket : ticketHistory.getResult()) {

                    if (Objects.nonNull(ticket.getQueue())
                            && !ticketPoolName.equalsIgnoreCase(ticket.getQueue().getQueueName())) {
                        isTicketPoolSame = false;
                    }
                }

            } else {
                isTicketPoolSame = false;
            }

            assertCheck.append(actions.assertEqualBoolean(isTicketPoolSame, Boolean.TRUE, "Tickets Fetched of same ticketpool as given in filter", "Tickets Failed to fetched of same ticketpool as given in filter"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 6, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithCustomerSLABreached(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Customer SLA breached Filter, Verify the response", "description");

            final TicketHistoryRequest ticketHistoryWithFilter = api.ticketHistoryWithCustomerSLABreached(validHeaderList, true);

            final Integer statusCode = ticketHistoryWithFilter.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

            Boolean isCustomerSLABreachedSame = true;

            //checking for customer SLA Breached True

            if (Objects.nonNull(ticketHistoryWithFilter)
                    && CollectionUtils.isNotEmpty(ticketHistoryWithFilter.getResult())) {

                for (TicketHistoryResult ticket : ticketHistoryWithFilter.getResult()) {

                    if (Objects.isNull(ticket.isCustomerSlaBreached()) || Boolean.FALSE.equals(ticket.isCustomerSlaBreached())) {
                        isCustomerSLABreachedSame = false;
                        break;
                    }
                }

            } else {
                isCustomerSLABreachedSame = false;
            }

            final TicketHistoryRequest ticketHistoryWithFilter2 = api.ticketHistoryWithCustomerSLABreached(validHeaderList, false);

            if (Objects.nonNull(ticketHistoryWithFilter2)
                    && CollectionUtils.isNotEmpty(ticketHistoryWithFilter2.getResult())) {

                for (TicketHistoryResult ticket : ticketHistoryWithFilter2.getResult()) {

                    if (Objects.isNull(ticket.isCustomerSlaBreached()) || Boolean.TRUE.equals(ticket.isCustomerSlaBreached())) {
                        isCustomerSLABreachedSame = false;
                        break;
                    }
                }

            } else {
                isCustomerSLABreachedSame = false;
            }

            assertCheck.append(actions.assertEqualBoolean(isCustomerSLABreachedSame, Boolean.TRUE, "Tickets Fetched of same customer SLA Breached as given in filter", "Tickets Failed to fetched of same customer SLA Breached as given in filter"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 7, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithWorkgroupSLABreached(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Workgorup SLA breached Filter, Verify the response", "description");

            final TicketHistoryRequest ticketHistoryWithFilter = api.ticketHistoryWithWorkgroupSLABreached(validHeaderList, true);

            final Integer statusCode = ticketHistoryWithFilter.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

            Boolean isWorkgroupSLABreachedSame = true;

            //checking for workgroup SLA Breached True

            if (Objects.nonNull(ticketHistoryWithFilter)
                    && CollectionUtils.isNotEmpty(ticketHistoryWithFilter.getResult())) {

                for (TicketHistoryResult ticket : ticketHistoryWithFilter.getResult()) {

                    if (Objects.isNull(ticket.isWorkgroupSlaBreached()) || Boolean.FALSE.equals(ticket.isWorkgroupSlaBreached())) {
                        isWorkgroupSLABreachedSame = false;
                        break;
                    }
                }

            } else {
                isWorkgroupSLABreachedSame = false;
            }

            final TicketHistoryRequest ticketHistoryWithFilter2 = api.ticketHistoryWithWorkgroupSLABreached(validHeaderList, false);

            if (Objects.nonNull(ticketHistoryWithFilter2)
                    && CollectionUtils.isNotEmpty(ticketHistoryWithFilter2.getResult())) {

                for (TicketHistoryResult ticket : ticketHistoryWithFilter2.getResult()) {

                    if (Objects.isNull(ticket.isWorkgroupSlaBreached()) || Boolean.TRUE.equals(ticket.isWorkgroupSlaBreached())) {
                        isWorkgroupSLABreachedSame = false;
                        break;
                    }
                }

            } else {
                isWorkgroupSLABreachedSame = false;
            }

            assertCheck.append(actions.assertEqualBoolean(isWorkgroupSLABreachedSame, Boolean.TRUE, "Tickets Fetched of same workgroup SLA Breached as given in filter", "Tickets Failed to fetched of same workgroup SLA Breached as given in filter"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 8, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithAssigneName(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Assignee Name Filter, Verify the response", "description");

            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            prepareTicketData(list);

            AgentDataExcelToBeanDao agentDataExcelToBeanDao = new AgentDataExcelToBeanDao();
            List<AgentDataBeans> agents = agentDataExcelToBeanDao.getData(excelPath, constants.getValue(CommonConstants.AGENT_DETAILS_RULE_SHEET));


            if (CollectionUtils.isNotEmpty(agents)) {

                TicketAssignResponse response = api.ticketAssignRequest(agents.get(0).getAgentId(), ticketDetails.getResult().getTicketId(), validHeaderList);

                String assigneeName = agents.get(0).getAgentName();

                final TicketHistoryRequest ticketHistoryWithFilter = api.ticketHistoryWithAssigneeName(validHeaderList, assigneeName);

                final Integer statusCode = ticketHistoryWithFilter.getStatusCode();
                assertCheck.append(
                        actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

                Boolean isAssigneeNameTicketsfound = true;


                if (Objects.nonNull(ticketHistoryWithFilter) && CollectionUtils.isNotEmpty(ticketHistoryWithFilter.getResult())) {

                    for (TicketHistoryResult ticket : ticketHistoryWithFilter.getResult()) {

                        if (Objects.isNull(ticket.getAssigneeName()) || !assigneeName.equalsIgnoreCase(ticket.getAssigneeName())) {
                            isAssigneeNameTicketsfound = false;
                            break;
                        }
                    }

                } else {
                    isAssigneeNameTicketsfound = false;
                }

                assertCheck.append(actions.assertEqualBoolean(isAssigneeNameTicketsfound, Boolean.TRUE,
                        "Tickets Fetched of same assignee Name as given in filter",
                        "Tickets Failed to fetched of same assignee Name as given in filter"));
            } else {
                throw new Exception("Agent list Empty which is used to assign ticket");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 9, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithAssigneId(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Assignee Id Filter, Verify the response", "description");

            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            prepareTicketData(list);

            AgentDataExcelToBeanDao agentDataExcelToBeanDao = new AgentDataExcelToBeanDao();
            List<AgentDataBeans> agents = agentDataExcelToBeanDao.getData(excelPath, constants.getValue(CommonConstants.AGENT_DETAILS_RULE_SHEET));


            if (CollectionUtils.isNotEmpty(agents)) {

                TicketAssignResponse resposne = api.ticketAssignRequest(agents.get(0).getAgentId(), ticketDetails.getResult().getTicketId(), validHeaderList);

                String assigneeID = agents.get(0).getAgentAuuid();

                final TicketHistoryRequest ticketHistoryWithFilter = api.ticketHistoryWithAssigneeId(validHeaderList, assigneeID);

                final Integer statusCode = ticketHistoryWithFilter.getStatusCode();
                assertCheck.append(
                        actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

                Boolean isAssigneeIdTicketsfound = true;


                if (Objects.nonNull(ticketHistoryWithFilter) && CollectionUtils.isNotEmpty(ticketHistoryWithFilter.getResult())) {

                    for (TicketHistoryResult ticket : ticketHistoryWithFilter.getResult()) {

                        if (Objects.isNull(ticket.getAssigneeName()) || !agents.get(0).getAgentId().equalsIgnoreCase(String.valueOf(ticket.getAgentId()))) {
                            isAssigneeIdTicketsfound = false;
                            break;
                        }
                    }

                } else {
                    isAssigneeIdTicketsfound = false;
                }

                assertCheck.append(actions.assertEqualBoolean(isAssigneeIdTicketsfound, Boolean.TRUE,
                        "Tickets Fetched of same assignee Id as given in filter",
                        "Tickets Failed to fetched of same assignee Id as given in filter"));
            } else {
                throw new Exception("Agent list Empty which is used to assign ticket");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithCategoryFilter " + e.getMessage(), false);
        }
    }

    @Test(priority = 1, dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testTicketHistoryWithIssueField(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /api/sr-service/v1/tickets with Assignee Id Filter, Verify the response", "description");

            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            prepareTicketData(list);

            IssueLayoutResponse layoutConfiguration = api.getLayoutConfiguration(validHeaderList, validCategoryId);
            StringBuilder fieldName = new StringBuilder();
            StringBuilder fieldValue = new StringBuilder();

            getFieldValueAndName(layoutConfiguration, fieldName, fieldValue);

            final String fieldNameFinal = fieldName.toString();
            final String fieldValueFinal = fieldValue.toString();

            final TicketHistoryRequest ticketHistoryWithFilter = api.getTicketsWithFilter(validHeaderList, fieldNameFinal, fieldValueFinal);

            final Integer statusCode = ticketHistoryWithFilter.getStatusCode();
            assertCheck
                    .append(actions.assertEqualIntType(statusCode, 200, "Status Code Matched", "Status Code Not Matched and is -" + statusCode));

            Boolean isIssuefieldTicketfound = true;

            if (Objects.nonNull(ticketHistoryWithFilter) && CollectionUtils.isNotEmpty(ticketHistoryWithFilter.getResult())) {
                for (TicketHistoryResult ticket : ticketHistoryWithFilter.getResult()) {
                    TicketRequest ticketdetails = api.getTicketDetailById(validHeaderList, ticket.getTicketId());
                    if (Objects.isNull(ticketdetails.getResult()) || CollectionUtils.isEmpty(ticketdetails.getResult().getIssueDetails()) || ticketdetails.getResult().getIssueDetails().stream().noneMatch(issueDetail -> issueDetail.getFieldName().equalsIgnoreCase(fieldNameFinal))) {
                        isIssuefieldTicketfound = false;
                    }
                }
            } else {
                isIssuefieldTicketfound = false;
            }
            assertCheck.append(actions.assertEqualBoolean(isIssuefieldTicketfound, Boolean.TRUE, "Tickets Fetched of same issue Field as given in filter", "Tickets Failed to fetched of same issue Field as given in filter"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testTicketHistoryWithIssueField " + e.getMessage(), false);
        }
    }


}
