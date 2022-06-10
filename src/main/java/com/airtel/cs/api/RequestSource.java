package com.airtel.cs.api;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.applicationutils.constants.URIConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.dataproviders.databeans.SearchType;
import com.airtel.cs.commonutils.dataproviders.databeans.TestDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.model.cs.request.*;
import com.airtel.cs.model.cs.request.am.SmsLogsRequest;
import com.airtel.cs.model.cs.request.am.TcpLimitsRequest;
import com.airtel.cs.model.cs.request.categoryhierarchy.CategoryHierarchyRequest;
import com.airtel.cs.model.cs.request.clientconfig.AllConfiguredClientRequest;
import com.airtel.cs.model.cs.request.clientconfig.ClientConfigResponse;
import com.airtel.cs.model.cs.request.clientconfig.ClientDeactivateRequest;
import com.airtel.cs.model.cs.request.hbb.HbbLinkedAccountsRequest;
import com.airtel.cs.model.cs.request.hbb.NotificationServiceRequest;
import com.airtel.cs.model.cs.request.hbb.ReceiverId;
import com.airtel.cs.model.cs.request.interaction.InteractionRequest;
import com.airtel.cs.model.cs.request.interactionissue.InteractionIssueRequest;
import com.airtel.cs.model.cs.request.issue.CreateIssueRequest;
import com.airtel.cs.model.cs.request.issuehistory.IssueHistoryRequest;
import com.airtel.cs.model.cs.request.layout.AutofillConfigsResponse;
import com.airtel.cs.model.cs.request.psb.cs.BankDetailsRequest;
import com.airtel.cs.model.cs.request.ticketstats.TicketStatsTicketSearchCriteria;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.model.cs.response.psb.cs.bankdetails.BankDetailsResponse;
import com.airtel.cs.model.sr.request.layout.LayoutConfigRequest;
import com.airtel.cs.model.sr.request.ticketsearch.IssueFields;
import com.airtel.cs.model.sr.request.ticketsearch.TicketSearchRequest;
import com.airtel.cs.model.sr.response.layout.IssueLayoutResponse;
import com.airtel.cs.model.cs.request.login.LoginRequest;
import com.airtel.cs.model.cs.request.openapi.category.ChildCategoryOpenApiRequest;
import com.airtel.cs.model.cs.request.openapi.category.FirstLastOpenApiRequest;
import com.airtel.cs.model.cs.request.openapi.category.ParentCategoryOpenApiRequest;
import com.airtel.cs.model.cs.request.openapi.clientconfig.ClientConfigOpenApiRequest;
import com.airtel.cs.model.cs.request.openapi.comment.CommentOpenApiRequest;
import com.airtel.cs.model.cs.request.openapi.interactionissue.InteractionIssueOpenApiRequest;
import com.airtel.cs.model.cs.request.openapi.interactionissue.IssueLayoutOpenRequest;
import com.airtel.cs.model.cs.request.openapi.ticket.SearchTicketOpenRequest;
import com.airtel.cs.model.cs.request.openapi.ticket.TicketHistoryLogOpenRequest;
import com.airtel.cs.model.cs.request.openapi.ticket.TicketSearchByTicketIdOpenRequest;
import com.airtel.cs.model.cs.request.ticketAssign.TicketAssignResponse;
import com.airtel.cs.model.cs.request.ticketdetail.TicketRequest;
import com.airtel.cs.model.sr.response.tickethistory.TicketHistoryRequest;
import com.airtel.cs.model.cs.request.tickethistorylog.TicketHistoryLogRequest;
import com.airtel.cs.model.cs.request.ticketlist.TicketListRequest;
import com.airtel.cs.model.cs.request.ticketreopen.ReopenTicketRequest;
import com.airtel.cs.model.cs.request.ticketstats.TicketStatsRequest;
import com.airtel.cs.model.sr.request.ticketsearch.TicketSearchCriteria;
import com.airtel.cs.model.cs.request.updateticket.CloseTicketRequest;
import com.airtel.cs.model.cs.request.vas.ActiveVasRequest;
import com.airtel.cs.model.cs.response.PlanPackResponse;
import com.airtel.cs.model.cs.response.am.TcpLimitsResponse;
import com.airtel.cs.model.cs.response.hlrservice.HLROrderHistoryRequest;
import com.airtel.cs.model.cs.response.hlrservice.HLROrderHistoryResponse;
import com.airtel.cs.model.cs.response.loansummary.LoanSummaryResponse;
import com.airtel.cs.model.response.fileupload.FileDownloadResponse;
import com.airtel.cs.model.response.fileupload.FileUploadResponse;
import io.restassured.response.Response;
import com.airtel.cs.model.cs.response.accountinfo.AccountDetails;
import com.airtel.cs.model.cs.response.accounts.AccountsBalance;
import com.airtel.cs.model.cs.response.accumulators.Accumulators;
import com.airtel.cs.model.cs.response.actionconfig.ActionConfigResponse;
import com.airtel.cs.model.cs.response.actionconfig.ActionConfigResult;
import com.airtel.cs.model.cs.response.actiontrail.EventLogsResponse;
import com.airtel.cs.model.cs.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.model.cs.response.adjustmentreason.AdjustmentReasonRequest;
import com.airtel.cs.model.cs.response.agentlimit.AgentLimit;
import com.airtel.cs.model.cs.response.agentpermission.AgentPermission;
import com.airtel.cs.model.cs.response.agents.AgentDetailAttribute;
import com.airtel.cs.model.cs.response.airtelmoney.AirtelMoney;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.authconfiguration.Configuration;
import com.airtel.cs.model.cs.response.clearrefillstatus.RefillStatus;
import com.airtel.cs.model.cs.response.configurationapi.ConfigurationList;
import com.airtel.cs.model.cs.response.crbt.ActivateRingtone;
import com.airtel.cs.model.cs.response.crbt.Top20Ringtone;
import com.airtel.cs.model.cs.response.createissue.CreateIssueResponse;
import com.airtel.cs.model.cs.response.enterprise.EnterpriseAccountSearchResponse;
import com.airtel.cs.model.cs.response.filedmasking.FieldMaskConfigReponse;
import com.airtel.cs.model.cs.response.filedmasking.FieldMaskConfigs;
import com.airtel.cs.model.cs.response.friendsfamily.FriendsFamily;
import com.airtel.cs.model.cs.response.hbb.HbbLinkedAccountsResponse;
import com.airtel.cs.model.cs.response.hbb.HbbUserDetailsResponse;
import com.airtel.cs.model.cs.response.hbb.NotificationServiceResponse;
import com.airtel.cs.model.cs.response.hlrservice.HLRService;
import com.airtel.cs.model.cs.response.kycprofile.GsmKyc;
import com.airtel.cs.model.cs.response.kycprofile.KYCProfile;
import com.airtel.cs.model.cs.response.kycprofile.Profile;
import com.airtel.cs.model.cs.response.loandetails.Loan;
import com.airtel.cs.model.cs.response.login.Login;
import com.airtel.cs.model.cs.response.offerdetails.OfferDetail;
import com.airtel.cs.model.cs.response.openapi.comment.CommentOpenApiResponse;
import com.airtel.cs.model.cs.response.parentcategory.Category;
import com.airtel.cs.model.cs.response.parentcategory.ParentCategoryResponse;
import com.airtel.cs.model.cs.response.plans.Plans;
import com.airtel.cs.model.cs.response.postpaid.PostpaidAccountDetailResponse;
import com.airtel.cs.model.cs.response.postpaid.enterprise.AccountStatementCSResponse;
import com.airtel.cs.model.cs.response.rechargehistory.RechargeHistory;
import com.airtel.cs.model.cs.response.smshistory.SMSHistory;
import com.airtel.cs.model.cs.response.tariffplan.AvailablePlan;
import com.airtel.cs.model.cs.response.tariffplan.CurrentPlan;
import com.airtel.cs.model.cs.response.tickethistorylog.TicketHistoryLog;
import com.airtel.cs.model.cs.response.ticketlist.Ticket;
import com.airtel.cs.model.cs.response.ticketstats.TicketStatsResponse;
import com.airtel.cs.model.cs.response.transfertoqueue.TransferToQueue;
import com.airtel.cs.model.cs.response.usagehistory.UsageHistory;
import com.airtel.cs.model.cs.response.vendors.VendorNames;
import com.airtel.cs.model.cs.response.voucher.VoucherSearch;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.airtel.cs.commonutils.applicationutils.constants.URIConstants.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class RequestSource extends RestCommonUtils {

    private static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);
    public static final Map<String, Object> queryParam = new HashMap<>();
    private static final String NOTIFICATION_SERVICE_API = " - notification service for SMS ";
    private Integer statusCode = null;
    private ESBRequestSource esbRequestSource = new ESBRequestSource();
    private static final String MSISDN = "msisdn";
    private static final String MSISDN_CAPS = "MSISDN";
    private static final String TYPE = "type";
    private static final String LAYOUT_CONFIG_TYPE = "layoutConfigType";
    private static final String CATEGORY_ID = "categoryId";
    private static final String INPUT_FIELDS = "inputFields";
    private static final String NUMBER = "number";
    private static final String ACCOUNT_NO = "accountNo";
    private static final String ACCOUNT_ID = "accountId";
    public static final String CS_PORTAL_API_ERROR = "cs.portal.api.error";
    private static final String AM_TRANSACTION_HISTORY_API_URL = "am.transaction.history.api.url";
    private static final String CALLING_ESB_APIS = "Calling ESB APIs";
    private static final String GET_ALL_CONFIGURATION = " - getAllConfiguration ";
    private static RequestSpecification request;
    private static QueryableRequestSpecification queryable;
    private static final String CREATED_BY = "API Automation";
    public static final String COMMENT = "Automation Test";
    public static final String UPDATE_COMMENT = "Automation Test Updated";
    private static final String CLOSURE_COMMENT = "Automation Closure Ticket Test";
    private static final String AGENT_ID = constants.getValue(ApplicationConstants.AGENT_ID);
    private static final String AGENT_NAME = constants.getValue(ApplicationConstants.AGENT_NAME);
    private static final String TICKET_POOL_ID = constants.getValue(ApplicationConstants.TICKET_POOL_ID);
    private static final String UPDATED_BY = constants.getValue(ApplicationConstants.UPDATED_BY);
    private static final String STATE_ID = constants.getValue(ApplicationConstants.EXTERNAL_CLOSED_STATE_ID);
    private static final String EXTERNAL_STATE_IDS = constants.getValue(ApplicationConstants.EXTERNAL_STATE_IDS);
    private static final String PAGE_SIZE = constants.getValue(ApplicationConstants.PAGE_SIZE);
    private static final String PAGE_NUMBER = constants.getValue(ApplicationConstants.PAGE_NUMBER);
    private static final String TICKET_POOL_IDS = constants.getValue(ApplicationConstants.TICKET_POOL_IDS);
    private String body = null;
    private static final String FINAL_SUBMIT = "false";
    private static final TestDataBean TEST_DATA_BEAN = new TestDataBean();
    private static Map<String, String> clientInfo = new HashMap<>();
    private static final TicketStatsTicketSearchCriteria ticketSearchCreteria = new TicketStatsTicketSearchCriteria();
    private static final TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
    private static final IssueFields issueFields = new IssueFields();
    private static final String CHANNEL = "channel";
    public static final String LINKED_ACCOUNT_ORCHESTRATOR = " - linked account orchestrator";
    public static final String CREATE_ISSUE = " - create issue";
    public static final String SR_CLIENT_ID = "sr-client-id";
    private static final String CALLING_CS_API = "cs.api.calling";
    private static final String INGRESS_OPEN_API_BASE_URL_1 = constants.getValue("ingress.open.api.base.url1");
    public static final String INGRESS_OPEN_API_BASE_URL_2 = "." + OPCO.toLowerCase() + "." + evnName.toLowerCase();
    private static final String INGRESS_OPEN_API_BASE_URL_3 = constants.getValue("ingress.open.api.base.url2");
    public static final String INGRESS_OPEN_API_BASE_URL = INGRESS_OPEN_API_BASE_URL_1 + INGRESS_OPEN_API_BASE_URL_2 + INGRESS_OPEN_API_BASE_URL_3;
    public static final String BUNDLE_TYPE = "bundleType";
    public static final String SERVICE_TYPE = "serviceType";


    /*
    This Method will hit the Available Plan API and returns the response
     */
    public AvailablePlan availablePlanRequest() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("tarrif.available.plan"), JavaColors.GREEN, false);
        AvailablePlan result = null;
        try {
            commonPostMethod(URIConstants.TARIFF_AVAILABLE_PLANS, new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            result = response.as(AvailablePlan.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callAvailableTariffPlan(new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - availablePlanRequest " + e.getMessage(), false);
            esbRequestSource.callAvailableTariffPlan(new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
        }
        return result;
    }

    /*
    This Method will hit the Current Plan API and returns the response
     */
    public CurrentPlan currentPlanRequest() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("tarrif.current.plan"), JavaColors.GREEN, false);
        CurrentPlan result = null;
        try {
            commonPostMethod(URIConstants.TARIFF_CURRENT_PLAN, new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            result = response.as(CurrentPlan.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callCurrentTarrifPlan(new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - currentPlanRequest " + e.getMessage(), false);
            esbRequestSource.callCurrentTarrifPlan(new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
        }
        return result;
    }

    /**
     * This Method will hit the Login API "/auth/api/user-mngmnt/v2/login" and return the response
     *
     * @param body body of the API
     * @return response
     */
    public Login loginRequest(String body) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v2.login"), JavaColors.GREEN, false);
        Login result = null;
        try {
            commonLib.info("Logging in Using Login API for getting TOKEN with user");
            commonPostMethod(URIConstants.V2_LOGIN, body);
            result = response.as(Login.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - loginRequest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/kyc/profile" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public KYCProfile kycProfileAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("kyc.profile"), JavaColors.GREEN, false);
        KYCProfile result = null;
        try {
            commonPostMethod(URIConstants.KYC_PROFILE, new GenericRequest(msisdn));
            result = response.as(KYCProfile.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callCustomerProfileV2(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - kycProfileAPITest " + e.getMessage(), false);
            esbRequestSource.callCustomerProfileV2(msisdn);
        }
        return result;
    }

    /**
     * This method will hit CS API /api/cs-gsm-service/v1/search and return the response
     *
     * @param msisdn
     * @return
     */
    public List<String> searchAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("search"), JavaColors.GREEN, false);
        String result;
        List<String> myList = null;
        try {
            queryParam.clear();
            queryParam.put(TYPE, MSISDN_CAPS);
            queryParam.put(NUMBER, msisdn);
            commonGetMethodWithQueryParam(URIConstants.SEARCH, queryParam);
            result = response.print();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callServiceClassRatePlan(new GenericRequest(msisdn));
            }
            myList = new ArrayList<>(Arrays.asList(result.split("data:")));
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - searchAPITest " + e.getMessage(), false);
            esbRequestSource.callServiceClassRatePlan(new GenericRequest(msisdn));
        }
        return myList;
    }

    /**
     * Auto fill api test list.
     *
     * @param layoutConfigType the layout config type
     * @param categoryId       the category id
     * @param inputFields      the input fields
     * @param msisdn
     * @return the list
     */
    public List<String> autoFillAPITest(String layoutConfigType, String categoryId, String inputFields, String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("auto.issue.field"), JavaColors.GREEN, false);
        String result;
        List<String> myList = null;
        try {
            queryParam.put(LAYOUT_CONFIG_TYPE, layoutConfigType);
            queryParam.put(CATEGORY_ID, categoryId);
            queryParam.put(INPUT_FIELDS, inputFields);
            commonGetMethodWithQueryParam(URIConstants.AUTOFILL_ISSUE_FIELD, queryParam, validHeaderList, srBaseUrl);
            result = response.print();
            myList = new ArrayList<>(Arrays.asList(result.split("data:")));
        } catch (Exception e) {
            queryParam.clear();
            queryParam.put(CATEGORY_ID, categoryId);
            commonGetMethodWithQueryParam(URIConstants.AUTOFILL_CONFIGS, queryParam, validHeaderList, srBaseUrl);
            AutofillConfigsResponse autofillConfigsResponse = response.as(AutofillConfigsResponse.class);
            esbRequestSource.callWebhookApisForAutofill(autofillConfigsResponse.getResult(), msisdn);
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - autoFillAPITest " + e.getMessage(), false);
        }
        return myList;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/profile" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public Profile profileAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("gsm.profile"), JavaColors.GREEN, false);
        Profile result = null;
        try {
            commonPostMethod(URIConstants.GSM_PROFILE, new GenericRequest(msisdn));
            result = response.as(Profile.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callProfileESBAPI(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - profileAPITest " + e.getMessage(), false);
            esbRequestSource.callProfileESBAPI(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/profile" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public AMProfile amServiceProfileAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("am.profile"), JavaColors.GREEN, false);
        AMProfile result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(URIConstants.AM_PROFILE, queryParam);
            result = response.as(AMProfile.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callAmServiceProfileESBAPI(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - amServiceProfileAPITest " + e.getMessage(), false);
            esbRequestSource.callAmServiceProfileESBAPI(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/gsm/kyc" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public GsmKyc gsmKYCAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("gsm.kyc"), JavaColors.GREEN, false);
        GsmKyc result = null;
        try {
            commonPostMethod(URIConstants.GSM_KYC, new GenericRequest(msisdn));
            result = response.as(GsmKyc.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callGsmKycESBAPI(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - gsmKYCAPITest " + e.getMessage(), false);
            esbRequestSource.callGsmKycESBAPI(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/user-service/user/v1/details" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public HbbUserDetailsResponse hbbUserDetailsTest(String msisdn, String type) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("hbb.user.details"), JavaColors.GREEN, false);
        HbbUserDetailsResponse result = null;
        try {
            commonPostMethod(HBB_USER, new GenericRequest(msisdn));
            result = response.as(HbbUserDetailsResponse.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.hbbLinkedAccount(msisdn, type);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - gsmKYCAPITest " + e.getMessage(), false);
            esbRequestSource.hbbLinkedAccount(msisdn, type);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/account/plans" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public Plans accountPlansTest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("account.plan"), JavaColors.GREEN, false);
        Plans result = null;
        try {
            commonPostMethod(ACCOUNT_PLAN, new GenericRequest(msisdn));
            result = response.as(Plans.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callAccountPlanESBAPI(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - accountPlansTest " + e.getMessage(), false);
            esbRequestSource.callAccountPlanESBAPI(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/usage/history" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public UsageHistory usageHistoryTest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("usage.history"), JavaColors.GREEN, false);
        UsageHistory result = null;
        try {
            commonPostMethod(URIConstants.USAGE_HISTORY, new UsageHistoryRequest(msisdn, 5, 1, null, null, null, "More"));
            result = response.as(UsageHistory.class);
            if (result.getStatusCode() != 200) {
                if (StringUtils.equals(constants.getValue(ApplicationConstants.TEST_V3_USAGE_HISTORY), "true")) {
                    esbRequestSource.callV3UsageHistory(new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "PAID"));
                } else {
                    esbRequestSource.callUsageHistory(new UsageHistoryRequest(msisdn, 5, 1, null, null, null, "More"));
                }
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - usageHistoryTest " + e.getMessage(), false);
            if (StringUtils.equals(constants.getValue(ApplicationConstants.TEST_V3_USAGE_HISTORY), "true")) {
                esbRequestSource.callV3UsageHistory(new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "PAID"));
            } else {
                esbRequestSource.callUsageHistory(new UsageHistoryRequest(msisdn, 5, 1, null, null, null, "More"));
            }
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/usage/history" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public UsageHistory usageHistoryMenuTest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("usage.history"), JavaColors.GREEN, false);
        UsageHistory result = null;
        try {
            commonPostMethod(URIConstants.USAGE_HISTORY, new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "FREE"));
            result = response.as(UsageHistory.class);
            if (result.getStatusCode() != 200) {
                if (StringUtils.equals(constants.getValue(ApplicationConstants.TEST_V3_USAGE_HISTORY), "false")) {
                    esbRequestSource.callUsageHistory(new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "FREE"));
                } else {
                    esbRequestSource.callV3UsageHistory(new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "FREE"));
                }
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - usageHistoryTest " + e.getMessage(), false);
            if (StringUtils.equals(constants.getValue(ApplicationConstants.TEST_V3_USAGE_HISTORY), "false")) {
                esbRequestSource.callUsageHistory(new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "FREE"));
            } else {
                esbRequestSource.callV3UsageHistory(new UsageHistoryMenuRequest(msisdn, 5, 1, null, null, null, "More", "FREE"));
            }
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/recharge/history" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response,"
     */
    public RechargeHistory rechargeHistoryAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("recharge.history"), JavaColors.GREEN, false);
        RechargeHistory result = null;
        try {
            commonPostMethod(URIConstants.RECHARGE_HISTORY, new RechargeHistoryRequest(msisdn, 5, 1, null, null, null));
            result = response.as(RechargeHistory.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callRechargeHistory(msisdn, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()),
                        UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(14)).getTime()));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - rechargeHistoryAPITest " + e.getMessage(), false);
            esbRequestSource.callRechargeHistory(msisdn, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()),
                    UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(14)).getTime()));
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/transaction/history" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public AirtelMoney transactionHistoryAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("transaction.history"), JavaColors.GREEN, false);
        AirtelMoney result = null;
        try {
            commonPostMethod(URIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
            result = response.as(AirtelMoney.class);
            if (result.getStatusCode() != 200) {
                commonPostMethod(constants.getValue(ESBURIConstants.AM_PROFILE_SERVICE_PROFILE_BASE_URL) + ESBURIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - transactionHistoryAPITest " + e.getMessage(), false);
            commonLib.info(CALLING_ESB_APIS);
            commonPostMethod(constants.getValue(AM_TRANSACTION_HISTORY_API_URL) + ESBURIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/transaction/history" and return the response
     *
     * @param msisdn       The msisdn
     * @param currencyType The currencyType
     * @return The Response
     */
    public AirtelMoney moreTransactionHistoryAPITest(String msisdn, String currencyType) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("transaction.history"), JavaColors.GREEN, false);
        AirtelMoney result = null;
        try {
            commonPostMethod(URIConstants.TRANSACTION_HISTORY, new MoreTransactionHistoryRequest(msisdn, 5, 1, null, null, null, null, currencyType, true));
            result = response.as(AirtelMoney.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - moreTransactionHistoryAPITest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/accounts/balance" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public AccountsBalance balanceAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("accounts.balance"), JavaColors.GREEN, false);
        AccountsBalance result = null;
        try {
            commonPostMethod(URIConstants.ACCOUNT_BALANCE, new AccountBalanceRequest(msisdn, 10, 1,false));
            result = response.as(AccountsBalance.class);
            if (result.getStatusCode() != 200) {
                commonPostMethod(constants.getValue(AM_TRANSACTION_HISTORY_API_URL) + ESBURIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
                commonLib.info(CALLING_ESB_APIS);
                queryParam.put(MSISDN, msisdn);
                commonGetMethodWithQueryParam(constants.getValue("subscriber.profile.base.url") + ESBURIConstants.QUERY_BALANCE, queryParam);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - balanceAPITest " + e.getMessage(), false);
            commonLib.info(CALLING_ESB_APIS);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("subscriber.profile.base.url") + ESBURIConstants.QUERY_BALANCE, queryParam);
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/fetch/ticket" and return the response
     *
     * @param ticketId The ticketId
     * @return The Response
     */
    public Ticket ticketMetaDataTest(String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.ticket"), JavaColors.GREEN, false);
        Ticket result = null;
        try {
            queryParam.put("id", ticketId);
            commonGetMethodWithQueryParam(URIConstants.SR_FETCH_HISTORY, queryParam, map, srBaseUrl);
            result = response.as(Ticket.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - ticketMetaDataTest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-notification-service/v1/fetch/history" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public SMSHistory smsHistoryTest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("notification.fetch.history"), JavaColors.GREEN, false);
        SMSHistory result = null;
        try {
            commonPostMethod(URIConstants.NOTIFICATION_FETCH_HISTORY, new SMSHistoryRequest(msisdn, 10, 0));
            result = response.as(SMSHistory.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - smsHistoryTest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/voucher/detail" and return the response
     *
     * @param voucherId The voucherId
     * @return The Response
     */
    public VoucherSearch voucherDetail(String voucherId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("voucher.detail"), JavaColors.GREEN, false);
        VoucherSearch result = null;
        try {
            commonPostMethod(URIConstants.VOUCHER_DETAILS, new VoucherSearchRequest(voucherId));
            result = response.as(VoucherSearch.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callVoucherDetails(voucherId);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - voucherSearchTest " + e.getMessage(), false);
            esbRequestSource.callVoucherDetails(voucherId);
        }
        return result;
    }

    /**
     * Voucher recharge test response.
     *
     * @param voucherRechargeRequest the voucher recharge request
     * @return the response
     */
    public Response voucherRechargeTest(VoucherRechargeRequest voucherRechargeRequest) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("overscratch.recharge"), JavaColors.GREEN, false);
        try {
            commonPostMethod(URIConstants.OVERSCRATCH_RECHARGE, voucherRechargeRequest);
            if (response.getStatusCode() != 200) {
                esbRequestSource.callOscRefill(voucherRechargeRequest.getVoucherNumber());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - voucherRechargeTest " + e.getMessage(), false);
            esbRequestSource.callOscRefill(voucherRechargeRequest.getVoucherNumber());
        }
        return response;
    }

    /*
    This Method will hit the API "/cs-vas-service/v1/vendors" and return the response
     */
    public VendorNames vendorsNamesTest() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.vendors"), JavaColors.GREEN, false);
        VendorNames result = null;
        try {
            commonGetMethod(URIConstants.VENDORS);
            result = response.as(VendorNames.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callVendors();
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - vendorsNamesTest " + e.getMessage(), false);
            esbRequestSource.callVendors();
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-vas-service/v1/loan/summary" and return the response
     *
     * @param msisdn     The msisdn
     * @param vendorName The vendorName
     * @return The Response
     */
    public LoanSummaryResponse loanSummaryTest(String msisdn, String vendorName) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("loan.summary"), JavaColors.GREEN, false);
        LoanSummaryResponse result = null;
        try {
            commonPostMethod(URIConstants.LOAN_SUMMARY, new LoanRequest(msisdn, vendorName));
            result = response.as(LoanSummaryResponse.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callLoanSummary(new LoanRequest(msisdn, vendorName));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - loanSummaryTest " + e.getMessage(), false);
            esbRequestSource.callLoanSummary(new LoanRequest(msisdn, vendorName));
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-vas-service/v1/loan/details" and return the response
     *
     * @param msisdn     The msisdn
     * @param vendorName The vendorName
     * @return The Response
     */
    public Loan loanDetailsTest(String msisdn, String vendorName) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("loan.details"), JavaColors.GREEN, false);
        Loan result = null;
        try {
            commonPostMethod(URIConstants.LOAN_DETAILS, new LoanRequest(msisdn, vendorName));
            result = response.as(Loan.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callLoanDetails(new LoanRequest(msisdn, vendorName));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - loanDetailsTest " + e.getMessage(), false);
            esbRequestSource.callLoanDetails(new LoanRequest(msisdn, vendorName));
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/refill/status" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public RefillStatus clearRefillTest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("refill.status"), JavaColors.GREEN, false);
        RefillStatus result = null;
        try {
            commonPostMethod(URIConstants.REFILL_STATUS, new GenericRequest(msisdn));
            result = response.as(RefillStatus.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callVoucherRefilLBarred(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - clearRefillTest " + e.getMessage(), false);
            esbRequestSource.callVoucherRefilLBarred(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-vas-service/v1/search/tunes" and return the response
     *
     * @param msisdn     The msisdn
     * @param searchBy   The searchBy
     * @param searchText The searchText
     * @return The Response
     */
    public Top20Ringtone ringtoneDetailTest(String msisdn, String searchBy, String searchText) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("search.tunes"), JavaColors.GREEN, false);
        Top20Ringtone result = null;
        try {
            commonPostMethod(URIConstants.SEARCH_TUNES, new RingtonDetailsRequest(msisdn, searchBy, searchText));
            result = response.as(Top20Ringtone.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callRingtoneDetailsTest(msisdn, searchText);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - ringtoneDetailTest " + e.getMessage(), false);
            esbRequestSource.callRingtoneDetailsTest(msisdn, searchText);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-vas-service/v1/fetch/tunes" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public ActivateRingtone activateRingtone(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.tunes"), JavaColors.GREEN, false);
        ActivateRingtone result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(URIConstants.FETCH_TUNES, queryParam);
            result = response.as(ActivateRingtone.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callActiveRingTone(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - activateRingtone " + e.getMessage(), false);
            esbRequestSource.callActiveRingTone(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/accumulators" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public Accumulators accumulatorsAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("accumulators.v1"), JavaColors.GREEN, false);
        Accumulators result = null;
        try {
            commonPostMethod(URIConstants.ACCUMULATORS, new AccumulatorsRequest(msisdn, 5, 1));
            result = response.as(Accumulators.class);
            if (result.getStatusCode() == 200) {
                esbRequestSource.callAccumulatorAPI(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - accumulatorsAPITest " + e.getMessage(), false);
            esbRequestSource.callAccumulatorAPI(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/hlr/service/profiles" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public HLRService getServiceProfileWidgetInfo(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("hlr.service.profiles"), JavaColors.GREEN, false);
        HLRService result = null;
        try {
            commonPostMethod(URIConstants.SERVICE_PROFILE, new ServiceProfileRequest(msisdn, 5, 1));
            result = response.as(HLRService.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callHLRFetchDetails(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getServiceProfileWidgetInfo " + e.getMessage(), false);
            esbRequestSource.callHLRFetchDetails(msisdn);
        }
        return result;
    }

    /**
     * This method is used to hit the api "/cs-gsm-service/v1/hlr/order/history"
     *
     * @param request the request
     * @return the hlr order history
     */
    public HLROrderHistoryResponse getHLROrderHistory(HLROrderHistoryRequest request) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("hlr.order.history.v1"), JavaColors.GREEN, false);
        HLROrderHistoryResponse result = null;
        try {
            commonPostMethod(URIConstants.HLR_ORDER_HISTORY_V1, request);
            result = response.as(HLROrderHistoryResponse.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callHLROrderHistory(request.getMsisdn());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getHLROrderHistory " + e.getMessage(), false);
            esbRequestSource.callHLROrderHistory(request.getMsisdn());
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-service/api/cs-service/v1/configurations" and return the response
     *
     * @param key      The key
     * @param lineType The lineType
     * @return The Response
     */
    public Configuration getConfiguration(String key, String lineType) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.configurations"), JavaColors.GREEN, false);
        Configuration result = null;
        try {
            queryParam.put("keys", key);
            queryParam.put("opco", OPCO);
            queryParam.put("lineType", lineType);
            commonGetMethodWithQueryParam(URIConstants.CONFIGURATIONS, queryParam);
            result = response.as(Configuration.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getConfiguration " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/offer/details" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public OfferDetail offerDetailAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("offer.details"), JavaColors.GREEN, false);
        OfferDetail result = null;
        try {
            commonPostMethod(URIConstants.OFFER_DETAILS, new OfferDetailRequest(msisdn, true));
            result = response.as(OfferDetail.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callOfferDetailsAPI(new OfferDetailRequest(msisdn, true));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - OfferDetailAPITest " + e.getMessage(), false);
            esbRequestSource.callOfferDetailsAPI(new OfferDetailRequest(msisdn, true));
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/friendsNfamily/details" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public FriendsFamily friendsFamilyAPITest(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("friends.and.family"), JavaColors.GREEN, false);
        FriendsFamily result = null;
        try {
            commonPostMethod(URIConstants.FRIENDS_FAMILY, new GenericRequest(msisdn));
            result = response.as(FriendsFamily.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callFriendsFamilyAPI(new GenericRequest(msisdn));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - friendsAndFamilyAPITest " + e.getMessage(), false);
            esbRequestSource.callFriendsFamilyAPI(new GenericRequest(msisdn));
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/agent/permissions" and return the response
     *
     * @return The Response
     */
    public AgentPermission transferToQueuePermissionAPI() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("agent.permission"), JavaColors.GREEN, false);
        AgentPermission result = null;
        try {
            commonGetMethod(URIConstants.AGENT_PERMISSION);
            result = response.as(AgentPermission.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - transferToQueuePermissionAPI " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/fetch/ticketpool" and return the response
     *
     * @param ticketId     The ticketId
     * @param isSupervisor The supervisor or not
     * @return The Response
     */
    public TransferToQueue fetchTicketPool(List<String> ticketId, Boolean isSupervisor) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.ticket.pool"), JavaColors.GREEN, false);
        TransferToQueue result = null;
        try {
            commonPostMethod(URIConstants.FETCH_TICKET_POOL, new FetchTicketPoolRequest(ticketId, isSupervisor));
            result = response.as(TransferToQueue.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - fetchTicketPoolAPI " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/agents" and return the response
     *
     * @return The Response
     */
    public AgentDetailAttribute getAgentDetail() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("agent.details"), JavaColors.GREEN, false);
        AgentDetailAttribute result = null;
        try {
            commonGetMethod(URIConstants.AGENT_DETAILS);
            result = response.as(AgentDetailAttribute.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getAgentDetail " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "cs-data-service/v1/event/logs" and return the response
     *
     * @param msisdn    The msisdn
     * @param eventType The event type
     * @return The Response
     */
    public EventLogsResponse getEventHistory(String msisdn, String eventType) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("event.logs"), JavaColors.GREEN, false);
        EventLogsResponse result = null;
        try {
            clientInfo.put(MSISDN, msisdn);
            commonPostMethod(URIConstants.EVENTS_LOG, new ActionTrailRequest(msisdn, eventType, 10, 0, clientInfo));
            result = response.as(EventLogsResponse.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEventHistory " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/adjustment/mapping?action=" and return the response
     *
     * @return The Response
     */
    public AdjustmentReasonRequest getAdjustmentReason() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("adjustment.action"), JavaColors.GREEN, false);
        AdjustmentReasonRequest result = null;
        try {
            commonGetMethod(URIConstants.ADJUSTMENT_ACTION);
            result = response.as(AdjustmentReasonRequest.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - AdjustmentReasonRequest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/adjustments" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public AdjustmentHistory getAdjustMentHistory(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("adjustment.history"), JavaColors.GREEN, false);
        AdjustmentHistory result = null;
        try {
            commonPostMethod(URIConstants.ADJUSTMENT_HISTORY, new ServiceProfileRequest(msisdn, 5, 1));
            result = response.as(AdjustmentHistory.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getServiceProfileWidgetInfo " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/postpaid/account/information" and return the response in list
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public List<String> getPostpaidAccountInformation(String msisdn, String customerAccountNumber, PaymentRequest paymentRequest) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("postpaid.account.information"), JavaColors.GREEN, false);
        String result;
        List<String> myList = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(URIConstants.POSTPAID_ACCOUNT_INFORMATION, queryParam);
            result = response.print();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callPostpaidAccountInformation(msisdn);
                esbRequestSource.callPostPaidAPI(customerAccountNumber, paymentRequest);
            }
            myList = new ArrayList<>(Arrays.asList(result.split("data:")));
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEventHistory " + e.getMessage(), false);
            esbRequestSource.callPostpaidAccountInformation(msisdn);
        }
        return myList;
    }


    /**
     * This Method will hit the API "/cs-service/api/cs-service/v1/get/field/mask/config" and return the response
     *
     * @param actionKey The action key
     * @return The Response
     */
    public FieldMaskConfigs getFieldMaskConfigs(String actionKey) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("field.mask.config"), JavaColors.GREEN, false);
        FieldMaskConfigReponse fieldMaskConfigReponse = null;
        try {
            queryParam.clear();
            queryParam.put("actionKey", actionKey);
            commonGetMethodWithQueryParam(URIConstants.GET_FIELD_MASK_CONFIG, queryParam);
            fieldMaskConfigReponse = response.as(FieldMaskConfigReponse.class);
            if ("200".equals(fieldMaskConfigReponse.getStatusCode()) && Objects.nonNull(fieldMaskConfigReponse.getResult())) {
                return fieldMaskConfigReponse.getResult();
            } else {
                commonLib.fail("Unable to fetch the response in getFieldMaskConfigs " + fieldMaskConfigReponse.getStatusCode(), false);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getFieldMaskConfigs " + e.getMessage(), false);
        }
        return new FieldMaskConfigs();
    }

    /**
     * This Method will hit the API "/cs-service/api/cs-service/v1/actions/config" and return the response
     *
     * @param actionKey The action tag name
     * @return The Response
     */
    public ActionConfigResult getActionConfig(String actionKey) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("action.config"), JavaColors.GREEN, false);
        ActionConfigResponse actionConfigResponse;
        ActionConfigResult actionConfigResult = null;
        try {
            commonGetMethod(URIConstants.ACTION_CONFIG);
            actionConfigResponse = response.as(ActionConfigResponse.class);
            if (ObjectUtils.isNotEmpty(actionConfigResponse)) {
                statusCode = actionConfigResponse.getStatusCode();
            }
            if (statusCode == 200 && ObjectUtils.isNotEmpty(actionConfigResponse.getResult())) {
                List<ActionConfigResult> actionConfigResultList = actionConfigResponse.getResult();
                Optional<ActionConfigResult> actionConfigResultTop = actionConfigResultList.stream().filter(result -> actionKey.equals(result.getActionKey())).findFirst();
                if (actionConfigResultTop.isPresent()) {
                    actionConfigResult = actionConfigResultTop.get();
                }
            } else {
                actionConfigResult = new ActionConfigResult();
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getActionConfig " + e.getMessage(), false);
        }
        return actionConfigResult;
    }

    /**
     * This Method will hit the API "/cs-service/api/cs-service/v1/limit/configuration" and return the response
     *
     * @param roleId The role id
     * @return The Response
     */
    public AgentLimit getAgentLimitConfig(String roleId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("limit.config"), JavaColors.GREEN, false);
        AgentLimit result = null;
        try {
            commonPostMethod(URIConstants.AGENT_LIMIT_API, new AgentLimitRequest(roleId));
            result = response.as(AgentLimit.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getAgentLimitConfig " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-service/api/cs-service/v1/limit/save/configuration" and return the response
     *
     * @param roleId The role id
     * @return The Response
     */
    public AgentLimit saveAgentLimit(String roleId, String featureKey, String dailyLimit, String monthlyLimit, String transactionLimit) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("save.config"), JavaColors.GREEN, false);
        AgentLimit result = null;
        try {
            LimitConfigRequest limitConfig = new LimitConfigRequest(featureKey, dailyLimit, monthlyLimit, transactionLimit);
            List<LimitConfigRequest> limitConfigRequests = new ArrayList<>();
            limitConfigRequests.add(limitConfig);
            commonPostMethod(URIConstants.SAVE_AGENT_LIMIT_API, new SaveAgentLimitRequest(roleId, limitConfigRequests));
            result = response.as(AgentLimit.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - saveAgentLimit " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/postpaid/currentplan" and return the response in list
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public List<String> getPostpaidCurrentPlan(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("current.plan.api"), JavaColors.GREEN, false);
        String result;
        List<String> myList = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(URIConstants.CURRENT_PLAN, queryParam);
            result = response.print();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callingPlanAPI(msisdn);
                esbRequestSource.callingPackAPI(msisdn);
            }
            myList = new ArrayList<>(Arrays.asList(result.split("data:")));
        } catch (Exception e) {
            commonLib.fail("Exception in method - getPostpaidCurrentPlan " + e.getMessage(), false);
        }
        return myList;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/postpaid/plan-pack/details" and return the response
     *
     * @param planPackRequest The REQUEST OBJ
     * @return The Response
     */
    public PlanPackResponse getPlanPack(PlanPackRequest planPackRequest, String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("plan.and.pack"), JavaColors.GREEN, false);
        PlanPackResponse result = null;
        try {
            commonPostMethod(URIConstants.PLAN_AND_PACK, planPackRequest);
            result = response.as(PlanPackResponse.class);
            if (response.getStatusCode() != 200) {
                esbRequestSource.callingGetUsageAPI(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - getPlanPack " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/postpaid/account/details" and return the response
     *
     * @param accountNumber The msisdn
     * @return The Response
     */
    public PostpaidAccountDetailResponse accountDetailResponse(String accountNumber) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("postpaid.account.details"), JavaColors.GREEN, false);
        PostpaidAccountDetailResponse result = null;
        try {
            commonPostMethod(URIConstants.POSTPAID_ACCOUNT_DETAILS, new PostpaidAccountDetailRequest(accountNumber, null, null, "1", "5"));
            result = response.as(PostpaidAccountDetailResponse.class);
            if (response.getStatusCode() != 200) {
                esbRequestSource.callingAccountStatementAPI(accountNumber);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - accountDetailResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/parent/categories" and return the response
     *
     * @param id The Id
     * @return The Response
     */
    public SortedMap<String, List<Category>> getParentCategory(Long id) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("parent.categories"), JavaColors.GREEN, false);
        ParentCategoryResponse parentCategoryResponse = null;
        TreeMap<String, List<Category>> result = null;
        try {
            queryParam.put("id", id);
            commonGetMethodWithQueryParam(URIConstants.GET_PARENT_CATEGORY_V1, queryParam, map, srBaseUrl);
            parentCategoryResponse = response.as(ParentCategoryResponse.class);
            if (parentCategoryResponse.getStatusCode() == 200 && ObjectUtils.isNotEmpty(parentCategoryResponse.getResult())) {
                result = parentCategoryResponse.getResult().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> newValue, TreeMap::new));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getParentCategory " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/postpaid/account/details" and return the response in list
     *
     * @param accountNo The Account Number
     * @return The Response
     */
    public AccountDetails getAccountInfoDetail(String accountNo, Integer pageNumber) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("postpaid.account.details"), JavaColors.GREEN, false);
        AccountDetails result = null;
        try {
            commonPostMethod(URIConstants.POSTPAID_ACCOUNT_DETAILS, new AccountDetailRequest(accountNo, pageNumber.toString(), "5"));
            result = response.as(AccountDetails.class);
            if (response.getStatusCode() != 200) {
                esbRequestSource.callingAccountStatementAPI(accountNo);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getAccountInfoDetail " + e.getMessage(), false);
            esbRequestSource.callPostpaidAccountInfoDetails(new AccountDetailRequest(accountNo, pageNumber.toString(), "5"));
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/fetch/ticket/history/log" and return the response
     *
     * @param ticketId The ticket id
     * @return The Response
     */
    public TicketHistoryLog getTicketHistoryLog(String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("ticket.history.log"), JavaColors.GREEN, false);
        TicketHistoryLog result = null;
        try {
            queryParam.put("id", ticketId);
            commonGetMethodWithQueryParam(URIConstants.TICKET_HISTORY_LOG, queryParam, map, srBaseUrl);
            result = response.as(TicketHistoryLog.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getTicketHistoryLog " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/postpaid/msisdn/details" and return the response in list
     *
     * @param accountNo The Account Number
     * @return The Response
     */
    public AccountStatementCSResponse accountStatementCSResponse(String accountNo, Integer pageNumber) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("postpaid.account.msisdn.details"), JavaColors.GREEN, false);
        AccountStatementCSResponse result = null;
        try {
            commonPostMethod(URIConstants.POSTPAID_ACCOUNT_MSISDN_DETAILS, new AccountStatementReq(accountNo, pageNumber.toString(), "5"));
            result = response.as(AccountStatementCSResponse.class);
            if (response.getStatusCode() != 200) {
                esbRequestSource.callingAccountStatementAPI(accountNo);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getAccountStatementDetails " + e.getMessage(), false);
            esbRequestSource.callPostpaidAccountInfoDetails(new AccountDetailRequest(accountNo, pageNumber.toString(), "5"));
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v3/tickets" and return the response
     *
     * @param msisdn The MSISDN
     * @return The Response
     */
    public Integer getTicketHistoryStatusCode(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v3.tickets"), JavaColors.GREEN, false);
        Integer result = null;
        try {
            clientInfo.put(MSISDN, msisdn);
            TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(new TicketSearchCriteria(clientInfo));
            commonPostMethod(URIConstants.GET_TICKET_HISTORY_V3, ticketSearchRequest);
            result = response.getStatusCode();
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getTicketHistoryStatusCode " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/cs-service/v1/config" and return the response in list
     *
     * @return The Response
     */
    public ConfigurationList getAllConfiguration(Integer pageSize, Integer pageNumber) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.configurations"), JavaColors.GREEN, false);
        ConfigurationList result = null;
        try {
            commonPostMethod(URIConstants.GET_CONFIGURATION_API, new ConfigurationRequest(pageNumber, pageSize));
            result = response.as(ConfigurationList.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + GET_ALL_CONFIGURATION + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/cs-service/v1/create/config" and return the response in list
     *
     * @return The Response
     */
    public ConfigurationList createConfig(String key, String value) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("create.configuration"), JavaColors.GREEN, false);
        ConfigurationList result = null;
        try {
            commonPostMethod(URIConstants.CREATE_CONFIGURATION_API, Collections.singletonList(new CreateConfigAttributes(OPCO, key, value)));
            result = response.as(ConfigurationList.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + GET_ALL_CONFIGURATION + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/cs-service/v1/delete/config" and return the response in list
     *
     * @return The Response
     */
    public ConfigurationList deleteConfig(String key) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("delete.configuration"), JavaColors.GREEN, false);
        ConfigurationList result = null;
        try {
            commonPostMethod(URIConstants.DELETE_CONFIGURATION_API, new CreateConfigAttributes(null, key, null));
            result = response.as(ConfigurationList.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + GET_ALL_CONFIGURATION + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/cs-service/v1/update/config" and return the response in list
     *
     * @return The Response
     */
    public ConfigurationList updateConfig(String key, String value) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("update.configuration"), JavaColors.GREEN, false);
        ConfigurationList result = null;
        try {
            commonPostMethod(URIConstants.UPDATE_CONFIGURATION_API, new CreateConfigAttributes(OPCO, key, value));
            result = response.as(ConfigurationList.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + GET_ALL_CONFIGURATION + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/sr-service/v1/ticket/stats" and return the response
     *
     * @param rowKeyword the row to be search in excel sheet
     * @param map        the headers
     * @return the response
     */
    public TicketStatsResponse ticketStatsRequest(String rowKeyword, List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("ticket.stats"), JavaColors.GREEN, false);
        TicketStatsResponse result = null;
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.CLIENT_CONFIG));
        List<String> fromExcelSheetColumnWise = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, rowKeyword, "Field Name", Collections.singletonList("Value"));
        clientInfo.put(MSISDN, fromExcelSheetColumnWise.get(0));
        ticketSearchCreteria.setClientInfo(clientInfo);
        try {
            commonPostMethod(URIConstants.TICKET_STATS, map, new TicketStatsRequest(ticketSearchCreteria), srBaseUrl);
            result = response.as(TicketStatsResponse.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - ticketStatsRequest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/api/sr-service/v1/assign/ticket" and return the response
     *
     * @param map the map
     * @return the result
     */
    public TicketAssignResponse ticketAssignRequest(String agentId, String ticketId, List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("assign.ticket"), JavaColors.GREEN, false);
        body = "{\"agentId\":" + agentId + ",\"ticketId\":[\"" + ticketId + "\"]}";
        commonPostMethod(URIConstants.ASSIGN_TICKET, map, body, srBaseUrl);
        return response.as(TicketAssignResponse.class);
    }


    /**
     * This Method is used to hit the "/api/sr-service/v1/ticket/stats" and get the response
     *
     * @param clientConfig the clientConfig
     * @param map          the map/header
     * @return the response
     */
    public TicketStatsResponse ticketStatsWithFilterRequest(String clientConfig, List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("ticket.stats"), JavaColors.GREEN, false);
        TicketStatsResponse result = null;
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.CLIENT_CONFIG));
        List<String> fromExcelSheetColumnWise = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "msisdn", "Field Name", Collections.singletonList("Value"));
        clientInfo.put(MSISDN, fromExcelSheetColumnWise.get(0));
        ticketSearchCreteria.setClientInfo(clientInfo);
        ticketSearchCreteria.setCategoryLevel(1);
        try {
            commonPostMethod(URIConstants.TICKET_STATS, map, new TicketStatsRequest(ticketSearchCreteria), srBaseUrl);
            result = response.as(TicketStatsResponse.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - ticketStatsWithFilterRequest " + e.getMessage(), false);
        }
        return result;
    }

    public InteractionIssueRequest createInteractionIssue(List<Header> map, String clientConfig, String issueDetails, String categoryIds) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("interaction.issue"), JavaColors.GREEN, false);
        body = "{\"interaction\":{\"createdBy\":\"" + CREATED_BY + "\",\"finalSubmit\":false,\"clientInfo\":{" + clientConfig + "}},\"issues\":[{\"comment\":\"" + COMMENT + "\",\"createdBy\":\"" + CREATED_BY + "\",\"issueDetails\":[" + issueDetails + "],\"categoryHierarchy\":[" + categoryIds + "]}]}";
        commonPostMethod(URIConstants.INTERACTION_ISSUE, map, body, srBaseUrl);
        return response.as(InteractionIssueRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/interactions/issue" API and get the response
     */
    public InteractionIssueOpenApiRequest interactionIssueOpenApiRequest(List<Header> map, String clientConfig, String issueDetails, String categoryIds) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("interaction.issue.open.api"), JavaColors.GREEN, false);
        body = "{\"interaction\":{\"createdBy\":\"" + CREATED_BY + "\",\"finalSubmit\":false,\"clientInfo\":{" + clientConfig + "}},\"issues\":[{\"comment\":\"" + COMMENT + "\",\"createdBy\":\"" + CREATED_BY + "\",\"issueDetails\":[" + issueDetails + "],\"categoryHierarchy\":[" + categoryIds + "]}]}";
        commonPostMethod(URIConstants.OPEN_API_INTERACTION_ISSUE, map, body, srBaseUrl);
        return response.as(InteractionIssueOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/comment" API and get the response
     */
    public CommentOpenApiResponse createCommentOpenApi(String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("create.comment"), JavaColors.GREEN, false);
        CommentOpenApiRequest commentOpenApiRequest = new CommentOpenApiRequest();
        commentOpenApiRequest.setTicketId(ticketId);
        commentOpenApiRequest.setAgentId(Long.parseLong(AGENT_ID));
        commentOpenApiRequest.setAgentName(AGENT_NAME);
        commentOpenApiRequest.setComment(COMMENT);
        commonPostMethod(URIConstants.OPEN_API_CREATE_COMMENT, validHeaderList, commentOpenApiRequest, srBaseUrl);
        return RestCommonUtils.response.as(CommentOpenApiResponse.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/update/comment" API and get the response
     */
    public CommentOpenApiResponse updateCommentOpenApi(Long commentId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("update.comment"), JavaColors.GREEN, false);
        CommentOpenApiRequest commentOpenApiRequest = new CommentOpenApiRequest();
        commentOpenApiRequest.setId(commentId);
        commentOpenApiRequest.setComment(UPDATE_COMMENT);
        commentOpenApiRequest.setAgentId(Long.parseLong(AGENT_ID));
        commonPostMethod(URIConstants.OPEN_API_UPDATE_COMMENT, validHeaderList, commentOpenApiRequest, srBaseUrl);
        return RestCommonUtils.response.as(CommentOpenApiResponse.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/delete/comment" API and get the response
     */
    public CommentOpenApiResponse deleteCommentOpenApi(Long commentId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("delete.comment"), JavaColors.GREEN, false);
        CommentOpenApiRequest commentOpenApiRequest = new CommentOpenApiRequest();
        commentOpenApiRequest.setId(commentId);
        commentOpenApiRequest.setAgentId(Long.parseLong(AGENT_ID));
        commonPostMethod(URIConstants.OPEN_API_DELETE_COMMENT, validHeaderList, commentOpenApiRequest, srBaseUrl);
        return RestCommonUtils.response.as(CommentOpenApiResponse.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/clients/config" API and get the response
     */
    public ClientConfigOpenApiRequest clientWithoutUMRequest(List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("client.config"), JavaColors.GREEN, false);
        commonGetMethod(URIConstants.OPEN_API_CLIENT_CONFIG, map, srBaseUrl);
        return response.as(ClientConfigOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/fetch/ticket" API and get the response
     */
    public TicketSearchByTicketIdOpenRequest ticketSearchByTicketIdOpenRequest(List<Header> map, String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.ticket"), JavaColors.GREEN, false);
        return ticketSearchByTicketIdOpenRequest(map, ticketId, 200);
    }

    public TicketSearchByTicketIdOpenRequest ticketSearchByTicketIdOpenRequest(List<Header> map, String ticketId, Integer statusCode) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.ticket"), JavaColors.GREEN, false);
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.OPEN_API_FETCH_TICKET, queryParam, map, srBaseUrl);
        return response.as(TicketSearchByTicketIdOpenRequest.class);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/openapi/fetch/ticket/history/log" API and get the response
     */
    public TicketHistoryLogOpenRequest ticketHistoryLogOpenRequest(List<Header> map, String ticketId) {
        return ticketHistoryLogOpenRequest(map, ticketId, 200);
    }

    public TicketHistoryLogOpenRequest ticketHistoryLogOpenRequest(List<Header> map, String ticketId, Integer statusCode) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("ticket.history.log"), JavaColors.GREEN, false);
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.OPEN_API_FETCH_TICKET_HISTORY_LOG, queryParam, map, srBaseUrl);
        return response.as(TicketHistoryLogOpenRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/tickets" API and get the response
     */
    public SearchTicketOpenRequest searchTicketOpenRequest(List<Header> map, String clientConfig) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("open.api.tickets"), JavaColors.GREEN, false);
        body = "{\"pageNumber\":0,\"pageSize\":10,\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "}}}";
        commonPostMethod(URIConstants.OPEN_API_SEARCH_TICKET, map, body, srBaseUrl);
        return response.as(SearchTicketOpenRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/layout" API and get the response
     */
    public IssueLayoutOpenRequest issueLayoutOpenRequest(List<Header> map, String categoryId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("open.api.lauout"), JavaColors.GREEN, false);
        body = "{\"layoutConfigType\":\"Issue\",\"categoryId\":" + categoryId + "}";
        commonPostMethod(URIConstants.OPEN_API_ISSUE_LAYOUT, map, body, srBaseUrl);
        return response.as(IssueLayoutOpenRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/child/categories" and get the response
     */
    public ChildCategoryOpenApiRequest childCategoryOpenApiRequest(List<Header> map, Integer categoryId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("open.api.child.categories"), JavaColors.GREEN, false);
        body = "{\"id\":" + categoryId + "}";
        commonPostMethod(URIConstants.OPEN_API_CHILD_CATEGORY, map, body, srBaseUrl);
        return response.as(ChildCategoryOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/parent/categories" the API and get the response
     */
    public ParentCategoryOpenApiRequest parentCategoryOpenApiRequest(List<Header> map, String categoryId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("open.api.parent.categories"), JavaColors.GREEN, false);
        queryParam.put("id", categoryId);
        commonGetMethodWithQueryParam(URIConstants.OPEN_API_PARENT_CATEGORY, queryParam, map, srBaseUrl);
        return response.as(ParentCategoryOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/firstlast/categories" API and get the response
     */
    public FirstLastOpenApiRequest firstLastOpenApiRequest(List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("open.api.first.last.categories"), JavaColors.GREEN, false);
        commonGetMethod(URIConstants.OPEN_API_FIRST_LAST, map, srBaseUrl);
        return response.as(FirstLastOpenApiRequest.class);
    }

    public ReopenTicketRequest reopenTicket(List<Header> map, String ticketIds) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("reopen.ticket"), JavaColors.GREEN, false);
        body = "{ \"agentId\": " + AGENT_ID + ", \"agentName\": \"" + AGENT_NAME + "\", \"comment\": \"" + COMMENT + "\", \"ticketIdList\": [\"" + ticketIds + "\"], \"ticketPoolId\": " + TICKET_POOL_ID + " }";
        commonPostMethod(URIConstants.REOPEN_TICKET, map, body, srBaseUrl);
        return response.as(ReopenTicketRequest.class);
    }

    public TicketRequest getTicketDetailById(List<Header> map, String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.ticket"), JavaColors.GREEN, false);
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.FETCH_TICKET, queryParam, map, srBaseUrl);
        return response.as(TicketRequest.class);
    }

    public IssueHistoryRequest getIssueHistory(List<Header> map, String clientConfig, String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("issue.history"), JavaColors.GREEN, false);
        body = "{\"pageNumber\":0,\"ticketId\":\"" + ticketId + "\",\"pageSize\":10,\"clientInfo\":{" + clientConfig + "}}";
        commonPostMethod(URIConstants.ISSUE_HISTORY, map, body, srBaseUrl);
        return response.as(IssueHistoryRequest.class);
    }

    public CreateIssueResponse createIssue() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("issue.history"), JavaColors.GREEN, false);
        CreateIssueResponse result = null;
        try {
            commonPostMethod(URIConstants.CREATE_ISSUE, validHeaderList, new CreateIssueRequest(), srBaseUrl);
            result = response.as(CreateIssueResponse.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + CREATE_ISSUE + e.getMessage(), false);
        }
        return result;
    }

    public CreateIssueResponse createIssue(List<Header> map, String interactionId, String issueDetails, String categoryIds) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.issue"), JavaColors.GREEN, false);
        body = "{\"interactionId\":\"" + interactionId + "\",\"comment\":\"" + COMMENT + "\",\"createdBy\":\"" + CREATED_BY + "\",\"issueDetails\":[" + issueDetails + "],\"categoryHierarchy\":[" + categoryIds + "] }";
        commonPostMethod(URIConstants.CREATE_ISSUE, map, body, srBaseUrl);
        return response.as(CreateIssueResponse.class);
    }

    public TicketHistoryLogRequest getTicketHistoryLog(List<Header> map, String ticketId) {
        return getTicketHistoryLog(map, ticketId, 200);
    }

    public TicketHistoryLogRequest getTicketHistoryLog(List<Header> map, String ticketId, Integer statusCode) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.issue"), JavaColors.GREEN, false);
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.FETCH_TICKET_HISTORY_LOG, queryParam, map, srBaseUrl);
        return response.as(TicketHistoryLogRequest.class);
    }

    public ClientDeactivateRequest deActivateClientConfig(List<Header> map, Integer id) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("deactivate.client.config"), JavaColors.GREEN, false);
        body = "{\"id\":" + id + "}";
        commonPostMethod(URIConstants.DEACTIVATE_CLIENT_CONFIG, map, body, srBaseUrl);
        return response.as(ClientDeactivateRequest.class);
    }

    public ClientConfigResponse createClientConfig(List<Header> map, String clientConfig) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("client.config"), JavaColors.GREEN, false);
        body = "{\"clientConfig\":[" + clientConfig + "]}";
        commonPostMethod(URIConstants.CLIENT_CONFIG, map, body, srBaseUrl);
        return response.as(ClientConfigResponse.class);
    }

    public InteractionRequest createInteraction(List<Header> map, String clientConfig) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.interactions"), JavaColors.GREEN, false);
        body = "{\"createdBy\": \"" + CREATED_BY + "\",\"finalSubmit\": " + FINAL_SUBMIT + ",\"clientInfo\":{" + clientConfig + "}}";
        commonPostMethod(URIConstants.CREATE_INTERACTION, map, body, srBaseUrl);
        return response.as(InteractionRequest.class);
    }

    public ClientConfigResponse getClientConfig(List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("client.config"), JavaColors.GREEN, false);
        commonGetMethod(URIConstants.CLIENT_CONFIG, map, srBaseUrl);
        return response.as(ClientConfigResponse.class);
    }

    public IssueLayoutResponse getLayoutConfiguration(List<Header> map, Integer categoryId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.layout"), JavaColors.GREEN, false);
        commonPostMethod(URIConstants.ISSUE_LAYOUT, map, new LayoutConfigRequest("Issue", categoryId), srBaseUrl);
        return response.as(IssueLayoutResponse.class);
    }

    public CategoryHierarchyRequest getParentCategoryId(List<Header> map, Integer categoryId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("parent.categories"), JavaColors.GREEN, false);
        queryParam.put("id", String.valueOf(categoryId));
        commonGetMethodWithQueryParam(URIConstants.PARENT_CATEGORY, queryParam, map, srBaseUrl);
        return response.as(CategoryHierarchyRequest.class);
    }

    public CategoryHierarchyRequest firstLastCategoryHierarchyTest(List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("first.last.categories"), JavaColors.GREEN, false);
        commonGetMethod(URIConstants.FIRST_LAST, map, srBaseUrl);
        return response.as(CategoryHierarchyRequest.class);
    }

    /**
     * This Method is used to hit the "/api/sr-service/v1/client/fields" API and get the response
     *
     * @return response of the API
     */
    public AllConfiguredClientRequest allConfiguredClientRequest(List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("client.fields"), JavaColors.GREEN, false);
        commonGetMethod(URIConstants.CONFIGURED_CLIENTS, map, srBaseUrl);
        return response.as(AllConfiguredClientRequest.class);
    }

    /*
    This Method is used to hit the "/api/user-mngmnt/v2/login" and get the response
     */
    public LoginRequest loginRequest(List<Header> map, String body) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v2.login"), JavaColors.GREEN, false);
        baseURI = srUMBaseUrl;
        Headers headers = new Headers(map);
        request = given()
                .headers(headers)
                .body(body)
                .contentType((ApplicationConstants.APPLICATION_JSON));
        response = request.post("/api/user-mngmnt/v2/login");
        return response.as(LoginRequest.class);
    }

    /**
     * This Method is used to hit the "/api/sr-service/v1/client/fields" API and get the response
     *
     * @param map header list
     * @return response of the API
     */
    public Integer allConfigureRequestWithInvalidMethod(List<Header> map) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("client.fields"), JavaColors.GREEN, false);
        commonPostMethod(URIConstants.CONFIGURED_CLIENTS, map, "", srBaseUrl);
        return response.getStatusCode();
    }

    public CloseTicketRequest closeTicket(List<Header> map, String ticketId, String interactionId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("update.tickets"), JavaColors.GREEN, false);
        body = "{\"agentId\":" + AGENT_ID + ",\"updatedBy\":" + UPDATED_BY + ",\"stateId\":" + STATE_ID + ",\"ticketId\":\"" + ticketId + "\",\"comment\":[{\"ticketPoolId\":" + TICKET_POOL_ID + ",\"agentName\":\"" + AGENT_NAME + "\",\"comment\":\"" + CLOSURE_COMMENT + "\",\"commentType\":\"\",\"agentId\":" + AGENT_ID + ",\"interactionId\":" + interactionId + "}],\"ticketPoolId\":" + TICKET_POOL_ID + "}";
        commonPostMethod(URIConstants.UPDATE_TICKET, map, body, srBaseUrl);
        return response.as(CloseTicketRequest.class);
    }

    public TicketListRequest ticketListRequest(List<Header> map) {
        return ticketListRequest(map, PAGE_SIZE, PAGE_NUMBER, STATE_ID);
    }

    public TicketListRequest ticketListRequest(List<Header> map, String pageSize, String pageNumber, String stateId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets.agents"), JavaColors.GREEN, false);
        body = "{\"agentId\":" + AGENT_ID + ",\"pageNumber\":" + pageNumber + ",\"pageSize\":" + pageSize + ",\"ticketPoolIds\":[" + TICKET_POOL_IDS + "],\"fromDate\":null,\"toDate\":null,\"slaFromDate\":null,\"slaToDate\":null,\"ticketAssigneeId\":null,\"stateIds\":[" + stateId + "],\"priorityIds\":[],\"workGroupEscalationIds\":null,\"categoryIds\":null}";
        commonPostMethod(URIConstants.TICKETS_BY_AGENT, map, body, srBaseUrl);
        return response.as(TicketListRequest.class);
    }

    public TicketHistoryRequest getTicketsWithFilter(List<Header> map, String fieldName, String fieldValue) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        List<Object> values = new ArrayList<>();
        Collections.addAll(values, fieldValue);
        ticketSearchCriteria.setIssueFields((List<IssueFields>) issueFields);
        issueFields.setFieldName(fieldName);
        ticketSearchCriteria.setSearchType("CONTAINS");
        commonPostMethod(URIConstants.TICKET_HISTORY, map, new TicketSearchRequest(ticketSearchCriteria), srBaseUrl);
        return response.as(TicketHistoryRequest.class);
    }

    public TicketHistoryRequest ticketHistoryRequest(List<Header> map, TicketSearchRequest body) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        commonPostMethod(URIConstants.TICKET_HISTORY, map, body, srBaseUrl);
        return response.as(TicketHistoryRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/tickets" with filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithFilterRequest(List<Header> map, String clientConfig, String ticketId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchCriteria.setTicketId(ticketId);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/tickets" without filters and get the response
     */
    public TicketHistoryRequest ticketHistoryWithoutFilter(List<Header> map, String clientConfig) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with category filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithCategoryFilter(List<Header> map, String categoryIds) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);

        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        ArrayList<Long> categories = new ArrayList<>();
        for (String s : categoryIds.split(",")) {
            categories.add(Long.valueOf(s));
        }
        ticketSearchCriteria.setCategoryIds(categories);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with category level and value filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithCategoryLevelAndValue(List<Header> map, int categoryLevel, String categoryLevelValues) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();

        ArrayList<String> categories = new ArrayList<>();
        Collections.addAll(categories, categoryLevelValues.split(","));
        ticketSearchCriteria.setCategoryLevel(categoryLevel);
        ticketSearchCriteria.setCategoryLevelValues(categories);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with Assigned Ticket pool filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithAssigenedTicketPool(List<Header> map, String assignedQueues) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();

        ArrayList<String> queues = new ArrayList<>();
        Collections.addAll(queues, assignedQueues.split(","));
        ticketSearchCriteria.setAssignedQueues(queues);

        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with customer sla breached filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithCustomerSLABreached(List<Header> map, boolean customerSLABreached) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        ticketSearchCriteria.setCustomerSlaBreached(customerSLABreached);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with assigneeName filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithAssigneeName(List<Header> map, String assigneeName) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();

        ArrayList<String> names = new ArrayList<>();
        Collections.addAll(names, assigneeName.split(","));
        ticketSearchCriteria.setAssigneeNames(names);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with assigneeId filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithAssigneeId(List<Header> map, String assigneeId) {
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();

        ArrayList<String> ids = new ArrayList<>();
        Collections.addAll(ids, assigneeId.split(","));
        ticketSearchCriteria.setAssigneeIds(ids);

        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with workgroupslaBreached filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithWorkgroupSLABreached(List<Header> map, boolean workgroupSLABreached) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v1.tickets"), JavaColors.GREEN, false);
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(ticketSearchCriteria);
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchCriteria.setWorkgroupSlaBreached(workgroupSLABreached);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }


    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with issue Details filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithIssueDetails(List<Header> map, String fieldName, String fieldValue) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + SEARCH, JavaColors.GREEN, false);
        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest();
        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        IssueFields field = new IssueFields();
        field.setFieldName(fieldName);

        List<Object> values = new ArrayList<>();
        Collections.addAll(values, fieldValue.split(","));

        field.setFieldValues(values);
        field.setSearchType(SearchType.CONTAINS);
        ticketSearchRequest.setTicketSearchCriteria(ticketSearchCriteria);
        return ticketHistoryRequest(map, ticketSearchRequest);
    }


    /**
     * This method is used to get hbb linked accounts response from CS API
     *
     * @param msisdn The msisdn
     * @param type   The Type
     * @return The response
     */

    public HbbLinkedAccountsResponse getLinkedAccountAndUserDetails(String msisdn, String type) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("hbb.linked.accounts"), JavaColors.GREEN, false);
        HbbLinkedAccountsResponse result = null;
        try {
            commonPostMethod(URIConstants.GET_HBB_LINKED_ACCOUNTS_API, new HbbLinkedAccountsRequest(msisdn, false));
            result = response.as(HbbLinkedAccountsResponse.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.hbbLinkedAccount(msisdn, type);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + LINKED_ACCOUNT_ORCHESTRATOR + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method will hit the api /cs-notification-service/v1/send/notification and return the response
     *
     * @param templateIdentifier
     * @param body
     * @param languageCode
     * @param searchId
     * @param sendNotificationType
     * @param templateSourceApp
     * @param templateChannel,
     * @param receiverId
     * @return The response
     */

    public NotificationServiceResponse getNotificationService(String templateIdentifier, String body, String languageCode, String searchId, String sendNotificationType, String templateSourceApp, String templateChannel, List<ReceiverId> receiverId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("notification.service"), JavaColors.GREEN, false);
        NotificationServiceResponse result = null;
        try {
            commonPostMethod(URIConstants.NOTIFICATION_SERVICE_API, new NotificationServiceRequest(templateIdentifier, body, languageCode, searchId, sendNotificationType, templateSourceApp, templateChannel, receiverId));
            result = response.as(NotificationServiceResponse.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + NOTIFICATION_SERVICE_API + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/enterprise/postpaid/account/information" and return the response in list
     *
     * @param accountNo      The msisdn
     * @param paymentRequest the request
     * @return The Response
     */
    public Integer getEnterprisePostpaidAccountInformation(String accountNo, PaymentRequest paymentRequest) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("enterprise.account.information"), JavaColors.GREEN, false);
        try {
            queryParam.put(ACCOUNT_NO, accountNo);
            commonGetMethodWithQueryParam(URIConstants.ENTERPRISE_POSTPAID_ACCOUNT_INFORMATION, queryParam);
            statusCode = response.getStatusCode();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callEnterPrisePostpaidAccountInformation(accountNo, paymentRequest);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterprisePostpaidAccountInformation " + e.getMessage(), false);
            esbRequestSource.callEnterPrisePostpaidAccountInformation(accountNo, paymentRequest);
        }
        return statusCode;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/enterprise/search" and return the response in list
     *
     * @param type the type
     * @param val  the value
     * @return The Response
     */
    public Integer getEnterpriseSearchAccount(String type, String val) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("enterprise.account.search"), JavaColors.GREEN, false);
        EnterpriseAccountSearchResponse result = null;
        try {
            queryParam.put("type", type);
            queryParam.put("val", val);
            commonGetMethodWithQueryParam(URIConstants.ENTERPRISE_ACCOUNT_SEARCH, queryParam);
            statusCode = response.getStatusCode();
            result = response.as(EnterpriseAccountSearchResponse.class);
            if (response.getStatusCode() != 200 || result.getStatusCode() != 200) {
                esbRequestSource.callEnterPriseSearchAccount(type, val);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterpriseSearchAccount " + e.getMessage(), false);
            esbRequestSource.callEnterPriseSearchAccount(type, val);
        }
        return statusCode;
    }


    /**
     * This Method will hit the API "/cs-gsm-service/v1/enterprise/linked/services" and return the response in list
     *
     * @param linkedServiceRequest the CS request
     * @return The Response
     */
    public Integer getEnterpriseLinkedServices(EnterpriseLinkedServiceRequest linkedServiceRequest) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("enterprise.linked.services"), JavaColors.GREEN, false);
        AccountLinesRequest accountLinesRequest = new AccountLinesRequest();
        accountLinesRequest.setAccountNo(linkedServiceRequest.getAccountNo());
        accountLinesRequest.setMsisdn(linkedServiceRequest.getMsisdn());
        accountLinesRequest.setLineType(linkedServiceRequest.getLineType());
        List<String> serviceTypeList = new ArrayList<>();
        if (StringUtils.isNotBlank(linkedServiceRequest.getServiceType()))
            serviceTypeList.add(linkedServiceRequest.getServiceType());
        accountLinesRequest.setServiceTypes(serviceTypeList);
        try {
            commonPostMethod(URIConstants.ENTERPRISE_LINKED_SERVICES, linkedServiceRequest);
            statusCode = response.getStatusCode();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callEnterPrisePostpaidAccountInformation(accountLinesRequest);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterpriseLinkedServices " + e.getMessage(), false);
            esbRequestSource.callEnterPrisePostpaidAccountInformation(accountLinesRequest);
        }
        return statusCode;
    }

    /**
     * This Method will hit the API "cs-data-service/v1/event/logs" in case of Enterprise and return the response
     *
     * @param eventType the event type
     * @param accountNo the account number
     * @return The Response
     */
    public Integer getEnterpriseEventHistory(String eventType, String accountNo) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("event.logs"), JavaColors.GREEN, false);
        try {
            clientInfo.put(ACCOUNT_ID, accountNo);
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.ENTERPRISE_SR_CLIENT_ID));
            commonPostMethod(URIConstants.EVENTS_LOG, new ActionTrailRequest("", eventType, 10, 0, clientInfo));
            statusCode = response.getStatusCode();
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterpriseEventHistory " + e.getMessage(), false);
        } finally {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.SR_CLIENT_ID));
        }
        return statusCode;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/issue/history" in case of Enterprise and return the response
     *
     * @param accountNo the account number
     * @return The Response
     */
    public Integer getEnterpriseInteractionHistory(String accountNo) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("enterprise.interaction.history"), JavaColors.GREEN, false);
        try {
            clientInfo.put(ACCOUNT_ID, accountNo);
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.ENTERPRISE_SR_CLIENT_ID));
            commonPostMethod(URIConstants.ENTERPRISE_INTERACTION_HISTORY, new InteractionHistoryRequest(clientInfo, 0, 10));
            statusCode = response.getStatusCode();
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterpriseInteractionHistory " + e.getMessage(), false);
        } finally {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.SR_CLIENT_ID));
        }
        return statusCode;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v3/tickets" in case of Enterprise and return the response
     *
     * @param accountNo the account number
     * @return The Response
     */
    public Integer getEnterpriseTicketHistory(String accountNo) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v3.tickets"), JavaColors.GREEN, false);
        try {
            clientInfo.put(ACCOUNT_ID, accountNo);
            TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(new TicketSearchCriteria(clientInfo));
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.ENTERPRISE_SR_CLIENT_ID));
            commonPostMethod(URIConstants.GET_TICKET_HISTORY_V3, ticketSearchRequest);
            statusCode = response.getStatusCode();
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterpriseTicketHistory " + e.getMessage(), false);
        } finally {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.SR_CLIENT_ID));
        }
        return statusCode;
    }

    /**
     * This Method will hit the API "/cs-notification-service/v1/fetch/history" in case of Enterprise and return the response
     *
     * @param accountNo the account number
     * @return The Response
     */
    public Integer getEnterpriseMessageHistory(String accountNo) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("notification.fetch.history"), JavaColors.GREEN, false);
        try {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.ENTERPRISE_SR_CLIENT_ID));
            commonPostMethod(URIConstants.NOTIFICATION_FETCH_HISTORY, new SMSHistoryRequest(accountNo, 10, 0));
            statusCode = response.getStatusCode();
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterpriseMessageHistory " + e.getMessage(), false);
        } finally {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.SR_CLIENT_ID));
        }
        return statusCode;
    }

    /**
     * his Method will hit the API "/cs-gsm-service/v1/enterprise/payment/history" in case of Enterprise and return the response
     *
     * @param paymentHistoryRequest    the CS API request
     * @param paymentHistoryESBRequest the ESB request
     * @return status code
     */
    public Integer getEnterprisePaymentHistory(PaymentHistoryRequest paymentHistoryRequest, PaymentHistoryESBRequest paymentHistoryESBRequest) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("enterprise.payment.history"), JavaColors.GREEN, false);
        try {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.ENTERPRISE_SR_CLIENT_ID));
            commonPostMethod(URIConstants.ENTERPRISE_PAYMENT_HISTORY, paymentHistoryRequest);
            statusCode = response.getStatusCode();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callEnterPrisePaymentHistory(paymentHistoryESBRequest);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getEnterprisePaymentHistory " + e.getMessage(), false);
            esbRequestSource.callEnterPrisePaymentHistory(paymentHistoryESBRequest);
        } finally {
            UtilsMethods.replaceHeader(SR_CLIENT_ID, constants.getValue(ApplicationConstants.SR_CLIENT_ID));
        }
        return statusCode;
    }


    /**
     * This method is used to fetch v2 layout configuration based on request
     *
     * @param map  the header
     * @param body the request body
     */
    public IssueLayoutResponse getV2LayoutConfiguration(List<Header> map, Object body) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("v2.layout"), JavaColors.GREEN, false);
        commonPostMethod(V2_LAYOUT_CONFIG, map, body, srBaseUrl);
        return response.as(IssueLayoutResponse.class);
    }


    /**
     * This method is used to hit the API "/cs-vas-service/v1/subscriptions/history" and return the response
     */
    public List<String> getVasSubscriptionHistory(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("active.vas"), JavaColors.GREEN, false);
        String result = null;
        List<String> myList = null;
        try {
            queryParam.put("activeVAS", "true");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(VAS_SUBSCRIPTION_HISTORY, queryParam);
            result = response.print();
            if (response.getStatusCode() != 200) {
                esbRequestSource.callActiveVAS(new ActiveVasRequest(msisdn, true));
            }
            myList = new ArrayList<>(Arrays.asList(result.split("data:")));
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " -getVasSubscriptionHistory " + e.getMessage(), false);
            esbRequestSource.callActiveVAS(new ActiveVasRequest(msisdn, true));
        }
        return myList;

    }

    /**
     * This Method will hit the API "/cs-am-service/v1/tcplimits" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public TcpLimitsResponse getTcpLimits(String msisdn, String tcpId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("tcp.limits"), JavaColors.GREEN, false);
        TcpLimitsResponse result = null;
        try {
            String userType = constants.getValue(CommonConstants.TCP_LIMIT_USER_TYPE);
            String bearer = constants.getValue(CommonConstants.TCP_LIMIT_BEARER);
            commonPostMethod(TCP_LIMITS, new TcpLimitsRequest(msisdn, tcpId, userType, bearer));
            result = response.as(TcpLimitsResponse.class);
            if (response.getStatusCode() != 200) {
                esbRequestSource.callTcpLimits(tcpId);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getTcpLimits " + e.getMessage(), false);
            esbRequestSource.callTcpLimits(tcpId);
        }
        return result;
    }

    /**
     * This method is used to set the id and file description keys in map
     *
     * @return data
     */
    public Map<String, String> metaDataMap() {
        Map<String, String> data = new HashMap<>();
        data.put("identifier", "id");
        data.put("fileDescription", "{\"test.txt\":\"description\"}");
        return data;
    }

    /**
     * This method is used to hit the API "/api/sr-service/v1/files" and return the response
     *
     * @return
     */
    public FileUploadResponse fileUpload() {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + SEARCH, JavaColors.GREEN, false);
        Map<String, String> metaData = metaDataMap();
        commonPostMethodWithMultiPart(URIConstants.FILE_UPLOAD, map, body, srBaseUrl, metaData);
        return response.as(FileUploadResponse.class);
    }

    /**
     * This method is used to hit the API "/api/sr-service/v1/files" and return the response
     *
     * @return
     */
    public FileDownloadResponse fileDownload(String resourceId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + SEARCH, JavaColors.GREEN, false);
        queryParam.put("resourceId", resourceId);
        commonGetMethodWithQueryParam(URIConstants.FILE_DOWNLOAD, queryParam);
        return response.as(FileDownloadResponse.class);
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/sms/trail" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public SmsLogsResponse getSMSLogs(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("sms.trails"), JavaColors.GREEN, false);
        SmsLogsResponse result = null;
        try {
            commonPostMethod(URIConstants.SMS_TRAILS, new SmsLogsRequest(msisdn, null, null, 1, 5));
            result = response.as(SmsLogsResponse.class);
            if (result.getStatusCode() != 200)
                esbRequestSource.callSmsLogs(msisdn);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getSmsLogs " + e.getMessage(), false);
            esbRequestSource.callSmsLogs(msisdn);
        }
        return result;
    }

    /**
     * This Method is used to get the query balance (DA details) for HBB number
     *
     * @param msisdn the HBB number
     * @return the query balance api result
     */
    public AccountsBalance getHBBQueryBalance(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("accounts.balance"), JavaColors.GREEN, false);
        AccountsBalance result = null;
        try {
            commonPostMethod(URIConstants.ACCOUNT_BALANCE, new AccountBalanceRequest(msisdn, 10, 1, false));
            result = response.as(AccountsBalance.class);

            if (result.getStatusCode() != 200) {
                commonLib.info(CALLING_ESB_APIS);
                queryParam.put(BUNDLE_TYPE, "%20");
                queryParam.put(MSISDN, msisdn);
                queryParam.put(SERVICE_TYPE, "%20");
                commonGetMethodWithQueryParam(constants.getValue("subscriber.profile.base.url") + ESBURIConstants.HBB_QUERY_BALANCE, queryParam);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getHBBQueryBalance " + e.getMessage(), false);
            commonLib.info(CALLING_ESB_APIS);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("subscriber.profile.base.url") + ESBURIConstants.HBB_QUERY_BALANCE, queryParam);
        }
        return result;

    }

}