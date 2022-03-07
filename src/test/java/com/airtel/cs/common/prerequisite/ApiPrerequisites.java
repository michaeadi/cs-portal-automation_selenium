package com.airtel.cs.common.prerequisite;


import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.utils.PassUtils;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.categoryhierarchy.CategoryHierarchyRequest;
import com.airtel.cs.model.request.interaction.InteractionRequest;
import com.airtel.cs.model.request.interactionissue.InteractionIssueRequest;
import com.airtel.cs.model.request.issue.CategoryHierarchy;
import com.airtel.cs.model.request.issue.IssueDetails;
import com.airtel.cs.model.request.layout.IssueLayoutRequest;
import com.airtel.cs.model.request.login.LoginRequest;
import com.airtel.cs.model.response.login.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApiPrerequisites extends Driver {

    /**
     * The actions.
     */
    public BaseActions actions = new BaseActions();
    public DataProviders data = new DataProviders();
    private static final String OPCO = System.getProperty("Opco").toUpperCase();
    String validClientInfo = null;
    public static Integer validCategoryId = null;
    String categoryHierarchy = null;
    public NftrDataBeans nftr = new NftrDataBeans();
    public static Map<String, Integer> ids = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    private static final String CLIENT_ID = constants.getValue(ApplicationConstants.X_CLIENT_ID);
    private static final String CHANNEL = constants.getValue(ApplicationConstants.X_CHANNEL);
    public static final String BASE_LOGIN_URL = constants.getValue(ApplicationConstants.BASE_LOGIN_URL);
    private static String Token;
    public static final String MSISDN = "msisdn";
    public static final String CLIENT = "CS";


    /*
    This Method will hit the login API and will set the headers to the map
     */
    @BeforeClass
    public void loginAPI() throws Exception {
        test = extent.createTest("ApiPrerequisites" + " :: " + "loginAPI");
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.LOGIN_SHEET_NAME));
        final String password = PassUtils.decodePassword(constants.getValue(CommonConstants.ADVISOR_USER_ROLE_PASSWORD));
        loginAUUID = constants.getValue(CommonConstants.ADVISOR_USER_ROLE_AUUID);
        Login req = Login.loginBody(loginAUUID, password);
        validHeaderList.clear();
        restUtils.addHeaders("x-client-id", CLIENT_ID);
        restUtils.addHeaders("x-channel", CHANNEL);
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);
        LoginRequest loginPOJO = api.loginRequest(validHeaderList, dtoAsString);
        String accessToken = loginPOJO.getResult().get("accessToken");
        String tokenType = loginPOJO.getResult().get("tokenType");
        Token = tokenType + " " + accessToken;
        restUtils.clearValidHeaderMap();
        restUtils.addHeaders("Opco", OPCO);
        restUtils.addHeaders("Authorization", Token);
        restUtils.addHeaders("sr-client-id", constants.getValue(CommonConstants.CS_SR_CLIENT_ID));
    }

    /*
    This Method will hit the First Last Category API and extract the Last category Level and then will put under a map
     */
    public void setLastCategoryIntoMap() {
        CategoryHierarchyRequest firstLastCategory = api.firstLastCategoryHierarchyTest(validHeaderList);
        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        ArrayList<CategoryHierarchy> lastLevelCategories = firstLastCategory.getResult().get(clientConfig.getLastCategoryLevel());
        lastLevelCategories.forEach(level -> ids.put(level.getCategoryName().toLowerCase().trim(), level.getId()));
    }

    /*
    This Method will hit the create interaction API and will return the interaction id
     */
    public String getInteractionId() {
        String interactionId = null;
        InteractionRequest interaction = api.createInteraction(validHeaderList, getValidClientConfig(MSISDN));
        if (interaction.getStatusCode().equalsIgnoreCase("200")) {
            interactionId = interaction.getResult().getInteractionId();
        }
        return interactionId;
    }

    /*
    This Method will hit the create interaction issue API and will return the ticket id
     */
    public String getTicketId(Integer validCategoryId, ClientConfigDataBean clientConfig) {
        String ticketId = null;
        InteractionIssueRequest interactionIssue = api.createInteractionIssue(validHeaderList, getValidClientConfig(MSISDN), getIssueDetails(validCategoryId), getCategoryHierarchy(clientConfig, validCategoryId));
        if (interactionIssue.getStatusCode() == 200) {
            ticketId = interactionIssue.getResult().getIssues().get(0).getTicket().getTicketId();
        }
        return ticketId;
    }

    /*
    This Method will hit the parent category API and will return the category hierarchy
     */
    public String getCategoryHierarchy(ClientConfigDataBean clientConfig, Integer validCategoryId) {
        CategoryHierarchyRequest parentCategoryId = api.getParentCategoryId(validHeaderList, validCategoryId);
        if (parentCategoryId.getStatusCode() == 200) {
            for (int i = Integer.parseInt(clientConfig.getFirstCategoryLevel()); i <= Integer.parseInt(clientConfig.getLastCategoryLevel()); i++) {
                Integer id = parentCategoryId.getResult().get(String.valueOf(i)).get(0).getId();
                if (categoryHierarchy == null) {
                    categoryHierarchy = "{\"id\":" + id + "}";
                } else {
                    categoryHierarchy += ",{\"id\":" + id + "}";
                }
            }
        }
        return categoryHierarchy;
    }

    /**
     * This Method will extract the last category id from parent category API
     *
     * @param validCategoryId category id
     * @return category id
     */
    public String getLastCategoryId(Integer validCategoryId) {
        String lastCategoryId;
        Integer id = getLastCategory(validCategoryId).getId();
        lastCategoryId = "{\"id\":" + id + "}";
        return lastCategoryId;
    }

    /**
     * This Method will extract the last category from parent category API
     *
     * @param validCategoryId category id
     * @return category id
     */
    public CategoryHierarchy getLastCategory(Integer validCategoryId) {

        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        CategoryHierarchyRequest parentCategoryId = api.getParentCategoryId(validHeaderList, validCategoryId);
        assertCheck.append(actions.assertEqualIntType(parentCategoryId.getStatusCode(), 200));
        CategoryHierarchy category = parentCategoryId.getResult().get(String.valueOf(clientConfig.getLastCategoryLevel())).get(0);

        return category;
    }

    /**
     * This Method is used get issue details for a particular category id
     *
     * @param validCategoryId category id
     * @return issue details
     */
    public String getIssueDetails(Integer validCategoryId) {
        StringBuilder issueDetails = null;
        IssueLayoutRequest layoutConfiguration = api.getLayoutConfiguration(validHeaderList, validCategoryId);
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
        } else {
            commonLib.fail("v1/layout API Response is not 200 and is -" + layoutConfiguration.getStatusCode(), false);
        }
        assert issueDetails != null;
        return issueDetails.toString();
    }

    /**
     * THis Method is used to get value of "Field Name" column under the excel sheet with sheet name client config
     *
     * @param rowKeyword name of the cell, for which you want to extract the value
     * @return value from Value column
     */
    public String getValidClientConfig(String rowKeyword) {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.CLIENT_CONFIG));
        List<String> scenarioDetails_fromExcelSheetColumnWise = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, rowKeyword, "Field Name", Collections.singletonList("Value"));
        validClientInfo = "\"msisdn\":\"" + scenarioDetails_fromExcelSheetColumnWise.get(0) + "\"";
        return validClientInfo;
    }

    /*
    This Method is used to get invalid client config
     */
    public String getInvalidClientConfig() {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.CLIENT_CONFIG));
        List<String> scenarioDetails_fromExcelSheetColumnWise = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "invalidMsisdn", "Field Name", Collections.singletonList("Value"));
        validClientInfo = "\"msisdn\":\"" + scenarioDetails_fromExcelSheetColumnWise.get(0) + "\"";
        return validClientInfo;
    }

    public String getClientCode(NftrDataBeans list) {
        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        String code = null;
        if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 1) {
            code = list.getIssue();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 2) {
            code = list.getIssueType();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 3) {
            code = list.getIssueSubType();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 4) {
            code = list.getIssueSubSubType();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 5) {
            code = list.getIssueCode();
        }
        return code;
    }

    public String getClientCode(FtrDataBeans list) {
        ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
        String code = null;
        if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 1) {
            code = list.getIssue();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 2) {
            code = list.getIssueType();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 3) {
            code = list.getIssueSubType();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 4) {
            code = list.getIssueSubSubType();
        } else if (Integer.parseInt(clientConfig.getLastCategoryLevel()) == 5) {
            code = list.getIssueCode();
        }
        return code;
    }

    /*
    This Method is used to set the header map with backend agent credentials.
     */
    public void getTokenForBackendAgent() throws Exception {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.LOGIN_SHEET_NAME));
        List<String> datatPoints = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "Backend", "userType", Arrays.asList("loginAuuid", "password"));
        LoginRequest req = LoginRequest.loginBody(datatPoints.get(0), PassUtils.decodePassword(datatPoints.get(1)));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);
        validHeaderList.clear();
        restUtils.addHeaders("x-client-id", CLIENT_ID);
        restUtils.addHeaders("x-channel", CHANNEL);
        LoginRequest loginPOJO = api.loginRequest(validHeaderList, dtoAsString);
        String accessToken = loginPOJO.getResult().get("accessToken");
        String tokenType = loginPOJO.getResult().get("tokenType");
        Token = tokenType + " " + accessToken;
        restUtils.clearValidHeaderMap();
        restUtils.addHeaders("Opco", OPCO);
        restUtils.addHeaders("Authorization", Token);
    }

    /**
     * This method is used to generate Field value and field name for Issue details.
     *
     * @param layoutConfiguration
     * @param fieldName
     * @param fieldValue
     */
    public void getFieldValueAndName(IssueLayoutRequest layoutConfiguration, StringBuilder fieldName, StringBuilder fieldValue) {
        String value = "";
        String Name = "";
        if (layoutConfiguration.getStatusCode() == 200) {
            if (!(layoutConfiguration.getResult() == null)) {
                if (!(layoutConfiguration.getResult().isEmpty())) {
                    for (IssueDetails s : layoutConfiguration.getResult()) {

                        if (StringUtils.equalsIgnoreCase(s.getFieldType(), "text")
                                && (Objects.nonNull(s.getPattern()) && s.getPattern().contains("/"))) {
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
                        Name = s.getPlaceHolder();
                        break;

                    }
                }
            }
        } else {
            commonLib.fail("v1/layout API Response is not 200 and is -" + layoutConfiguration.getStatusCode(), false);
        }

        fieldName.append(Name);
        fieldValue.append(value);
    }
}