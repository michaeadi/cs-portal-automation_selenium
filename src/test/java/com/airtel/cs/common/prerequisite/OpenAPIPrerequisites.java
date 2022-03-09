package com.airtel.cs.common.prerequisite;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.issue.IssueDetails;
import com.airtel.cs.model.request.openapi.category.ParentCategoryOpenApiRequest;
import com.airtel.cs.model.request.openapi.interactionissue.InteractionIssueOpenApiRequest;
import com.airtel.cs.model.request.openapi.interactionissue.IssueLayoutOpenRequest;
import com.airtel.cs.model.response.openapi.comment.CommentOpenApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;

public class OpenAPIPrerequisites extends Driver {
    public BaseActions actions = new BaseActions();
    DataProviders data = new DataProviders();
    String openApiValidClientInfo = null;
    String validCategoryId = null;
    public static Map<String, Integer> ids = new HashMap<>();
    private static String Token;
    private static final String OPCO = System.getProperty("Opco").toUpperCase();
    private static final String SR_CLIENT_ID =  constants.getValue(ApplicationConstants.SELFCARE_SR_CLIENT_ID);
    private static final String LOCALE = constants.getValue(ApplicationConstants.LOCALE);


    /*
    This Method is used to get the valid token for open API
     */
    @BeforeClass(alwaysRun = true)
    public void getValidTokenForOpenAPI() {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.OPEN_API_LOGIN_SHEET));
        List<String> datatPoints = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "OpenAPIValidToken", "API Type", Collections.singletonList("Token"));
        validHeaderList.clear();
        baseURI = umBaseUrl;
        Token = "Bearer " + datatPoints.get(0);
        restUtils.clearValidHeaderMap();
        map.clear();
        restUtils.addHeaders("Opco", OPCO);
        restUtils.addHeaders("Authorization", Token);
        restUtils.addHeaders("sr-client-id", SR_CLIENT_ID);
        restUtils.addHeaders("locale", LOCALE);
    }

    /*
    This Method is used to get the invalid token for open API
     */
    @BeforeMethod(firstTimeOnly = true)
    public void getInvalidTokenForOpenAPI() {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.OPEN_API_LOGIN_SHEET));
        List<String> datatPoints = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "OpenApiInvalidToken", "API Type", Collections.singletonList("Token"));
        invalidHeaderList.clear();
        baseURI = umBaseUrl;
        Token = "Bearer " + datatPoints.get(0);
        restUtils.clearInvalidHeaderMap();
        restUtils.addHeaders("Opco", OPCO);
        restUtils.addHeaders("Authorization", Token);
    }

    /*
    This Method is used to get the client config for Open API
     */
    public String getOpenAPIClientConfig() {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.OPEN_API_CLIENT_CONFIG));
        List<String> scenarioDetails_fromExcelSheetColumnWise = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "customerMsisdn", "Field Name", Collections.singletonList("Value"));
        openApiValidClientInfo = "\"customerMsisdn\":\"" + scenarioDetails_fromExcelSheetColumnWise.get(0) + "\"";
        return openApiValidClientInfo;
    }

    /*
    This Method is used to get the issue details for Open API
     */
    public String getOpenAPIIssueDetails() {
        validCategoryId = constants.getValue(ApplicationConstants.OPEN_API_CATEGORY_ID);
        StringBuilder issueDetails = null;
        if (validCategoryId != null) {
            IssueLayoutOpenRequest layoutConfiguration = api.issueLayoutOpenRequest(validHeaderList, validCategoryId);
            if (layoutConfiguration.getStatusCode() == 200) {
                if (!(layoutConfiguration.getResult() == null)) {
                    if (!(layoutConfiguration.getResult().isEmpty())) {
                        for (IssueDetails s : layoutConfiguration.getResult()) {
                            String value;
                            if (StringUtils.equalsIgnoreCase(s.getFieldType(), "text") && (Objects.nonNull(s.getPattern()) && s.getPattern().contains("/"))) {
                                value = "1111";
                            } else if ("text".equalsIgnoreCase(s.getFieldType()) && StringUtils.isBlank(s.getPattern())) {
                                value = "test";
                            } else if ("number".equalsIgnoreCase(s.getFieldType())) {
                                value = "1001";
                            } else if ("select".equalsIgnoreCase(s.getFieldType())) {
                                value = s.getFieldOptions().get(0);
                            } else {
                                value = "test";
                            }
                            if (issueDetails == null) {
                                issueDetails = new StringBuilder("{\"fieldName\":\"" + s.getFieldName() + "\",\"fieldType\":\"" + s.getFieldType() + "\",\"mandatory\":\"" + s.getMandatory() + "\",\"fieldValue\":\"" + value + "\",\"placeHolder\":\"" + s.getPlaceHolder() + "\"}");
                            } else {
                                issueDetails.append(",{\"fieldName\":\"").append(s.getFieldName()).append("\",\"fieldType\":\"").append(s.getFieldType()).append("\",\"mandatory\":\"").append(s.getMandatory()).append("\",\"fieldValue\":\"").append(value).append("\",\"placeHolder\":\"").append(s.getPlaceHolder()).append("\"}");
                            }
                        }
                    } else {
                        issueDetails = new StringBuilder();
                    }
                } else {
                    issueDetails = new StringBuilder();
                }
            }
        }
        assert issueDetails != null;
        return issueDetails.toString();
    }

    /*
    This Method is used to get the category hierarchy for Open API
     */
    public String getOpenAPICategoryHierarchy() {
        StringBuilder categoryHierarchy = null;
        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        validCategoryId = constants.getValue(ApplicationConstants.OPEN_API_CATEGORY_ID);
        ParentCategoryOpenApiRequest parentCategoryOpenApiRequest = api.parentCategoryOpenApiRequest(validHeaderList, validCategoryId);
        if (parentCategoryOpenApiRequest.getStatusCode() == 200) {
            for (int i = Integer.parseInt(clientConfig.getFirstCategoryLevel()); i <= Integer.parseInt(clientConfig.getLastCategoryLevel()); i++) {
                Integer id = parentCategoryOpenApiRequest.getResult().get(String.valueOf(i)).get(0).getId();
                if (categoryHierarchy == null) {
                    categoryHierarchy = new StringBuilder("{\"id\":" + id + "}");
                } else {
                    categoryHierarchy.append(",{\"id\":").append(id).append("}");
                }
            }
        }
        assert categoryHierarchy != null;
        return categoryHierarchy.toString();
    }

    /*
    This Method is used to get the ticket id for Open API by hitting the interaction issue open API
     */
    public String getOpenApiTicketId() {
        String ticketId = null;
        InteractionIssueOpenApiRequest interactionIssueOpenApiRequest = api.interactionIssueOpenApiRequest(validHeaderList, getOpenAPIClientConfig(), getOpenAPIIssueDetails(), getOpenAPICategoryHierarchy());
        if (interactionIssueOpenApiRequest.getStatusCode() == 200) {
            ticketId = interactionIssueOpenApiRequest.getResult().getIssues().get(0).getTicket().getTicketId();
        }
        return ticketId;
    }

    /*
    This Method is used to get the comment id for Open API by hitting the create comment open API
     */
    public Long getOpenApiCommentId() {
        Long commentId = null;
        CommentOpenApiResponse commentOpenApiResponse = api.createCommentOpenApi(getOpenApiTicketId());
        if (commentOpenApiResponse.getStatusCode() == 200) {
            commentId = commentOpenApiResponse.getResult().getId();
        }
        return commentId;
    }

    /*
     This Method is used to get the interaction id for Open API by hitting the interaction issue open API
     */
    public String getOpenAPIInteractionId(Integer validCategoryId) {
        String interactionId = null;
        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        InteractionIssueOpenApiRequest interactionIssueOpenApiRequest = api.interactionIssueOpenApiRequest(validHeaderList, getOpenAPIClientConfig(), getOpenAPIIssueDetails(), getOpenAPICategoryHierarchy());
        if (interactionIssueOpenApiRequest.getStatusCode() == 200) {
            interactionId = interactionIssueOpenApiRequest.getResult().getInteraction().getInteractionId();
        }
        return interactionId;
    }

}
