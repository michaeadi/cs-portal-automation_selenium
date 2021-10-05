package com.airtel.cs.api;

import com.airtel.cs.commonutils.dataproviders.databeans.TestDataBean;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.applicationutils.constants.URIConstants;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.model.request.AccountBalanceRequest;
import com.airtel.cs.model.request.AccountDetailRequest;
import com.airtel.cs.model.request.AccountStatementReq;
import com.airtel.cs.model.request.AccumulatorsRequest;
import com.airtel.cs.model.request.ActionTrailRequest;
import com.airtel.cs.model.request.AgentLimitRequest;
import com.airtel.cs.model.request.ConfigurationRequest;
import com.airtel.cs.model.request.CreateConfigAttributes;
import com.airtel.cs.model.request.FetchTicketPoolRequest;
import com.airtel.cs.model.request.GenericRequest;
import com.airtel.cs.model.request.LimitConfigRequest;
import com.airtel.cs.model.request.LoanRequest;
import com.airtel.cs.model.request.MoreTransactionHistoryRequest;
import com.airtel.cs.model.request.OfferDetailRequest;
import com.airtel.cs.model.request.PaymentRequest;
import com.airtel.cs.model.request.PlanPackRequest;
import com.airtel.cs.model.request.PostpaidAccountDetailRequest;
import com.airtel.cs.model.request.RechargeHistoryRequest;
import com.airtel.cs.model.request.RingtonDetailsRequest;
import com.airtel.cs.model.request.SMSHistoryRequest;
import com.airtel.cs.model.request.SaveAgentLimitRequest;
import com.airtel.cs.model.request.ServiceProfileRequest;
import com.airtel.cs.model.request.TicketSearchCriteria;
import com.airtel.cs.model.request.TicketSearchRequest;
import com.airtel.cs.model.request.TransactionHistoryRequest;
import com.airtel.cs.model.request.UsageHistoryMenuRequest;
import com.airtel.cs.model.request.UsageHistoryRequest;
import com.airtel.cs.model.request.VoucherSearchRequest;
import com.airtel.cs.model.request.categoryhierarchy.CategoryHierarchyRequest;
import com.airtel.cs.model.request.clientconfig.AllConfiguredClientRequest;
import com.airtel.cs.model.request.clientconfig.ClientConfigRequest;
import com.airtel.cs.model.request.clientconfig.ClientDeactivateRequest;
import com.airtel.cs.model.request.createissue.CreateIssueRequest;
import com.airtel.cs.model.request.interaction.InteractionRequest;
import com.airtel.cs.model.request.interactionissue.InteractionIssueRequest;
import com.airtel.cs.model.request.issuehistory.IssueHistoryRequest;
import com.airtel.cs.model.request.layout.IssueLayoutRequest;
import com.airtel.cs.model.request.login.LoginRequest;
import com.airtel.cs.model.request.openapi.category.ChildCategoryOpenApiRequest;
import com.airtel.cs.model.request.openapi.category.FirstLastOpenApiRequest;
import com.airtel.cs.model.request.openapi.category.ParentCategoryOpenApiRequest;
import com.airtel.cs.model.request.openapi.clientconfig.ClientConfigOpenApiRequest;
import com.airtel.cs.model.request.openapi.interactionissue.InteractionIssueOpenApiRequest;
import com.airtel.cs.model.request.openapi.interactionissue.IssueLayoutOpenRequest;
import com.airtel.cs.model.request.openapi.ticket.SearchTicketOpenRequest;
import com.airtel.cs.model.request.openapi.ticket.TicketHistoryLogOpenRequest;
import com.airtel.cs.model.request.openapi.ticket.TicketSearchByTicketIdOpenRequest;
import com.airtel.cs.model.request.ticketdetail.TicketRequest;
import com.airtel.cs.model.request.tickethistory.TicketHistoryRequest;
import com.airtel.cs.model.request.tickethistorylog.TicketHistoryLogRequest;
import com.airtel.cs.model.request.ticketlist.TicketListRequest;
import com.airtel.cs.model.request.ticketreopen.ReopenTicketRequest;
import com.airtel.cs.model.request.ticketstats.TicketStatsRequest;
import com.airtel.cs.model.request.updateticket.CloseTicketRequest;
import com.airtel.cs.model.response.PlanPackResponse;
import com.airtel.cs.model.response.accountinfo.AccountDetails;
import com.airtel.cs.model.response.accounts.AccountsBalance;
import com.airtel.cs.model.response.accumulators.Accumulators;
import com.airtel.cs.model.response.actionconfig.ActionConfigResponse;
import com.airtel.cs.model.response.actionconfig.ActionConfigResult;
import com.airtel.cs.model.response.actiontrail.ActionTrail;
import com.airtel.cs.model.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.model.response.adjustmentreason.AdjustmentReasonRequest;
import com.airtel.cs.model.response.agentlimit.AgentLimit;
import com.airtel.cs.model.response.agentpermission.AgentPermission;
import com.airtel.cs.model.response.agents.AgentDetailAttribute;
import com.airtel.cs.model.response.airtelmoney.AirtelMoney;
import com.airtel.cs.model.response.amprofile.AMProfile;
import com.airtel.cs.model.response.authconfiguration.Configuration;
import com.airtel.cs.model.response.clearrefillstatus.RefillStatus;
import com.airtel.cs.model.response.configurationapi.ConfigurationList;
import com.airtel.cs.model.response.crbt.ActivateRingtone;
import com.airtel.cs.model.response.crbt.Top20Ringtone;
import com.airtel.cs.model.response.filedmasking.FieldMaskConfigReponse;
import com.airtel.cs.model.response.filedmasking.FieldMaskConfigs;
import com.airtel.cs.model.response.friendsfamily.FriendsFamily;
import com.airtel.cs.model.response.hlrservice.HLRService;
import com.airtel.cs.model.response.kycprofile.GsmKyc;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import com.airtel.cs.model.response.kycprofile.Profile;
import com.airtel.cs.model.response.loandetails.Loan;
import com.airtel.cs.model.response.loansummary.Summary;
import com.airtel.cs.model.response.login.Login;
import com.airtel.cs.model.response.offerdetails.OfferDetail;
import com.airtel.cs.model.response.plans.Plans;
import com.airtel.cs.model.response.postpaid.PostpaidAccountDetailResponse;
import com.airtel.cs.model.response.postpaid.enterprise.AccountStatementCSResponse;
import com.airtel.cs.model.response.rechargehistory.RechargeHistory;
import com.airtel.cs.model.response.smshistory.SMSHistory;
import com.airtel.cs.model.response.tariffplan.AvailablePlan;
import com.airtel.cs.model.response.tariffplan.CurrentPlan;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLog;
import com.airtel.cs.model.response.ticketlist.Ticket;
import com.airtel.cs.model.response.transfertoqueue.TransferToQueue;
import com.airtel.cs.model.response.usagehistory.UsageHistory;
import com.airtel.cs.model.response.vendors.VendorNames;
import com.airtel.cs.model.response.voucher.VoucherSearch;
import com.airtel.cs.model.response.parentcategory.Category;
import com.airtel.cs.model.response.parentcategory.ParentCategoryResponse;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class RequestSource extends RestCommonUtils {

    private static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);
    private static final Map<String, Object> queryParam = new HashMap<>();
    public static Integer statusCode = null;
    private ESBRequestSource esbRequestSource = new ESBRequestSource();
    private static final String MSISDN = "msisdn";
    private static final String CS_PORTAL_API_ERROR = "cs.portal.api.error";
    private static final String AM_TRANSACTION_HISTORY_API_URL = "am.transaction.history.api.url";
    private static final String CALLING_ESB_APIS = "Calling ESB APIs";
    private static final String GET_ALL_CONFIGURATION = " - getAllConfiguration ";
    private static RequestSpecification request;
    private static Response response;
    private static QueryableRequestSpecification queryable;
    private static final String CREATED_BY = "API Automation";
    private static final String COMMENT = "Automation Test";
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
    private static final String APPLICATION_JSON = "application/json";
    private static final String FINAL_SUBMIT = "false";
    private static final TestDataBean TEST_DATA_BEAN = new TestDataBean();

    /*
    This Method will hit the Available Plan API and returns the response
     */
    public AvailablePlan availablePlanRequest() {
        AvailablePlan result = null;
        try {
            commonPostMethod(URIConstants.TARIFF_AVAILABLE_PLANS, new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            result = response.as(AvailablePlan.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callAvailableTarrifPlan(new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - availablePlanRequest " + e.getMessage(), false);
            esbRequestSource.callAvailableTarrifPlan(new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
        }
        return result;
    }

    /*
    This Method will hit the Current Plan API and returns the response
     */
    public CurrentPlan currentPlanRequest() {
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
     * This Method will hit the API "/cs-gsm-service/v1/profile" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public Profile profileAPITest(String msisdn) {
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
     * This Method will hit the API "/cs-gsm-service/v1/account/plans" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public Plans accountPlansTest(String msisdn) {
        Plans result = null;
        try {
            commonPostMethod(URIConstants.ACCOUNT_PLAN, new GenericRequest(msisdn));
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
        RechargeHistory result = null;
        try {
            commonPostMethod(URIConstants.RECHARGE_HISTORY, new RechargeHistoryRequest(msisdn, 5, 1, null, null, null));
            result = response.as(RechargeHistory.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callRechargeHistory(msisdn,
                        UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()),
                        UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(14)).getTime()));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - rechargeHistoryAPITest " + e.getMessage(), false);
            esbRequestSource
                    .callRechargeHistory(msisdn, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()),
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
        AirtelMoney result = null;
        try {
            commonPostMethod(URIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
            result = response.as(AirtelMoney.class);
            if (result.getStatusCode() != 200) {
                commonPostMethod(constants.getValue(AM_TRANSACTION_HISTORY_API_URL) + ESBURIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
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
        AccountsBalance result = null;
        try {
            commonPostMethod(URIConstants.ACCOUNT_BALANCE, new AccountBalanceRequest(msisdn, 10, 1));
            result = response.as(AccountsBalance.class);
            if (result.getStatusCode() != 200) {
                commonPostMethod(constants.getValue(AM_TRANSACTION_HISTORY_API_URL) + ESBURIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, null, null));
                commonLib.info(CALLING_ESB_APIS);
                queryParam.put(MSISDN, msisdn);
                commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.QUERY_BALANCE, queryParam);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - balanceAPITest " + e.getMessage(), false);
            commonLib.info(CALLING_ESB_APIS);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.QUERY_BALANCE, queryParam);
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
        Ticket result = null;
        try {
            queryParam.put("id", ticketId);
            commonGetMethodWithQueryParam(URIConstants.SR_FETCH_HISTORY, queryParam);
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
    public VoucherSearch voucherSearchTest(String voucherId) {
        VoucherSearch result = null;
        try {
            commonPostMethod(URIConstants.VOUCHER_DETAILS, new VoucherSearchRequest(voucherId));
            result = response.as(VoucherSearch.class);
            if (result.getStatusCode() != 200 || result.getApiErrors().getVoucherDetail().equalsIgnoreCase(constants.getValue("cs.voucher.detail.downstream.api.error"))) {
                esbRequestSource.callVoucherDetails(voucherId);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - voucherSearchTest " + e.getMessage(), false);
            esbRequestSource.callVoucherDetails(voucherId);
        }
        return result;
    }

    /*
    This Method will hit the API "/cs-vas-service/v1/vendors" and return the response
     */
    public VendorNames vendorsNamesTest() {
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
    public Summary loanSummaryTest(String msisdn, String vendorName) {
        Summary result = null;
        try {
            commonPostMethod(URIConstants.LOAN_SUMMARY, new LoanRequest(msisdn, vendorName));
            result = response.as(Summary.class);
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
        RefillStatus result = null;
        try {
            commonPostMethod(URIConstants.REFILL_STATUS, new GenericRequest(msisdn));
            result = response.as(RefillStatus.class);
            if (!"200".equals(result.getStatusCode())) {
                esbRequestSource.callVoucherRefilBarred(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - clearRefillTest " + e.getMessage(), false);
            esbRequestSource.callVoucherRefilBarred(msisdn);
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
     * This Method will hit the API "/cs-service/api/cs-service/v1/configurations" and return the response
     *
     * @param key The key
     * @return The Response
     */
    public Configuration getConfiguration(String key) {
        Configuration result = null;
        try {
            queryParam.put("keys", key);
            queryParam.put("opco", OPCO);
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
        FriendsFamily result = null;
        try {
            commonPostMethod(URIConstants.FRIENDS_FAMILY, new GenericRequest(msisdn));
            result = response.as(FriendsFamily.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callFriensFamilyAPI(new GenericRequest(msisdn));
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - friendsAndFamilyAPITest " + e.getMessage(), false);
            esbRequestSource.callFriensFamilyAPI(new GenericRequest(msisdn));
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/agent/permissions" and return the response
     *
     * @return The Response
     */
    public AgentPermission transferToQueuePermissionAPI() {
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
     * This Method will hit the API "/cs-gsm-service/v1/offer/details" and return the response
     *
     * @param ticketId     The ticketId
     * @param isSupervisor The supervisor or not
     * @return The Response
     */
    public TransferToQueue fetchTicketPool(List<String> ticketId, Boolean isSupervisor) {
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
     * @param headers The headers contain auth token including common headers
     * @return The Response
     */
    public AgentDetailAttribute getAgentDetail(Headers headers) {
        AgentDetailAttribute result = null;
        try {
            commonGetMethod(URIConstants.AGENT_DETAILS, headers);
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
    public ActionTrail getEventHistory(String msisdn, String eventType) {
        ActionTrail result = null;
        try {
            commonPostMethod(URIConstants.EVENTS_LOG, new ActionTrailRequest(msisdn, eventType, 10, 0));
            result = response.as(ActionTrail.class);
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
     * @param headers    The headers contain auth token including common headers
     * @param actionName The action tag name
     * @return The Response
     */
    public ActionConfigResult getActionConfig(Headers headers, String actionName) {
        ActionConfigResponse actionConfigResponse;
        ActionConfigResult actionConfigResult = null;
        try {
            commonGetMethod(URIConstants.ACTION_CONFIG, headers);
            actionConfigResponse = response.as(ActionConfigResponse.class);
            if (ObjectUtils.isNotEmpty(actionConfigResponse)) {
                statusCode = actionConfigResponse.getStatusCode();
            }
            if (statusCode == 200 && ObjectUtils.isNotEmpty(actionConfigResponse.getResult())) {
                List<ActionConfigResult> actionConfigResultList = actionConfigResponse.getResult();
                Optional<ActionConfigResult> actionConfigResultop = actionConfigResultList.stream()
                        .filter(result -> actionName.equals(result.getActionKey())).findFirst();
                if (actionConfigResultop.isPresent()) {
                    actionConfigResult = actionConfigResultop.get();
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
        AgentLimit result = null;
        try {
            LimitConfigRequest limitConfig = new LimitConfigRequest(featureKey, dailyLimit, monthlyLimit, transactionLimit);
            List<LimitConfigRequest> request = new ArrayList<>();
            request.add(limitConfig);
            commonPostMethod(URIConstants.SAVE_AGENT_LIMIT_API, new SaveAgentLimitRequest(roleId, request));
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
    public TreeMap<String, List<Category>> getParentCategory(Long id) {
        ParentCategoryResponse parentCategoryResponse = null;
        TreeMap<String, List<Category>> result = null;
        try {
            queryParam.put("id", id);
            commonGetMethodWithQueryParam(URIConstants.GET_PARENT_CATEGORY_V1, queryParam);
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
        TicketHistoryLog result = null;
        try {
            queryParam.put("id", ticketId);
            commonGetMethodWithQueryParam(URIConstants.TICKET_HISTORY_LOG, queryParam);
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
     * This Method will hit the API "/sr/api/sr-service/v1/tickets" and return the response
     *
     * @param msisdn The MSISDN
     * @return The Response
     */
    public Integer getTicketHistoryStatusCode(String msisdn) {
        Integer result = null;
        try {
            Map<String, String> clientInfo = new HashMap<>();
            clientInfo.put(MSISDN, msisdn);
            TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(new TicketSearchCriteria(clientInfo));
            commonPostMethod(URIConstants.GET_TICKET_HISTORY_V1, ticketSearchRequest);
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
        ConfigurationList result = null;
        try {
            commonPostMethod(URIConstants.UPDATE_CONFIGURATION_API, new CreateConfigAttributes(OPCO, key, value));
            result = response.as(ConfigurationList.class);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + GET_ALL_CONFIGURATION + e.getMessage(), false);
        }
        return result;
    }

    public TicketStatsRequest ticketStatsRequest(String clientConfig, List<Header> map) {
        body = "{\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "}}}";
        commonPostMethod(URIConstants.TICKET_STATS, map, body);
        return response.as(TicketStatsRequest.class);
    }

    public TicketStatsRequest ticketStatsWithFilterRequest(String clientConfig, List<Header> map) {
        body = "{\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "},\"fromDate\":null,\"toDate\":null,\"ticketId\":null,\"days\":null,\"stateIds\":[1,2,3,4,5]}}";
        commonPostMethod(URIConstants.TICKET_STATS, map, body);
        return response.as(TicketStatsRequest.class);
    }

    public InteractionIssueRequest createInteractionIssue(List<Header> map, String clientConfig, String issueDetails, String categoryIds) {
        body = "{\"interaction\":{\"createdBy\":\"" + CREATED_BY + "\",\"finalSubmit\":false,\"clientInfo\":{" + clientConfig + "}},\"issues\":[{\"comment\":\"" + COMMENT + "\",\"createdBy\":\"" + CREATED_BY + "\",\"issueDetails\":[" + issueDetails + "],\"categoryHierarchy\":[" + categoryIds + "]}]}";
        commonPostMethod(URIConstants.INTERACTION_ISSUE, map, body);
        return response.as(InteractionIssueRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/interactions/issue" API and get the response
     */
    public InteractionIssueOpenApiRequest interactionIssueOpenApiRequest(List<Header> map, String clientConfig, String issueDetails, String categoryIds) {
        body = "{\"interaction\":{\"createdBy\":\"" + CREATED_BY + "\",\"finalSubmit\":false,\"clientInfo\":{" + clientConfig + "}},\"issues\":[{\"comment\":\"" + COMMENT + "\",\"createdBy\":\"" + CREATED_BY + "\",\"issueDetails\":[" + issueDetails + "],\"categoryHierarchy\":[" + categoryIds + "]}]}";
        commonPostMethod(URIConstants.OPEN_API_INTERACTION_ISSUE, map, body);
        return response.as(InteractionIssueOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/clients/config" API and get the response
     */
    public ClientConfigOpenApiRequest clientWithoutUMRequest(List<Header> map) {
        commonGetMethod(URIConstants.OPEN_API_CLIENT_CONFIG,map);
        return response.as(ClientConfigOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/fetch/ticket" API and get the response
     */
    public TicketSearchByTicketIdOpenRequest ticketSearchByTicketIdOpenRequest(List<Header> map, String ticketId) {
        return ticketSearchByTicketIdOpenRequest(map, ticketId, 200);
    }

    public TicketSearchByTicketIdOpenRequest ticketSearchByTicketIdOpenRequest(List<Header> map, String ticketId, Integer statusCode) {
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.OPEN_API_FETCH_TICKET, queryParam);
        return response.as(TicketSearchByTicketIdOpenRequest.class);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/openapi/fetch/ticket/history/log" API and get the response
     */
    public TicketHistoryLogOpenRequest ticketHistoryLogOpenRequest(List<Header> map, String ticketId) {
        return ticketHistoryLogOpenRequest(map, ticketId, 200);
    }

    public TicketHistoryLogOpenRequest ticketHistoryLogOpenRequest(List<Header> map, String ticketId, Integer statusCode) {
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.OPEN_API_FETCH_TICKET_HISTORY_LOG, queryParam);
        return response.as(TicketHistoryLogOpenRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/tickets" API and get the response
     */
    public SearchTicketOpenRequest searchTicketOpenRequest(List<Header> map, String clientConfig) {
        body = "{\"pageNumber\":0,\"pageSize\":10,\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "}}}";
        commonPostMethod(URIConstants.OPEN_API_SEARCH_TICKET, map, body);
        return response.as(SearchTicketOpenRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/layout" API and get the response
     */
    public IssueLayoutOpenRequest issueLayoutOpenRequest(List<Header> map, String categoryId) {
        body = "{\"layoutConfigType\":\"Issue\",\"categoryId\":" + categoryId + "}";
        commonPostMethod(URIConstants.OPEN_API_ISSUE_LAYOUT, map, body);
        return response.as(IssueLayoutOpenRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/child/categories" and get the response
     */
    public ChildCategoryOpenApiRequest childCategoryOpenApiRequest(List<Header> map, Integer categoryId) {
        body = "{\"id\":" + categoryId + "}";
        commonPostMethod(URIConstants.OPEN_API_CHILD_CATEGORY, map, body);
        return response.as(ChildCategoryOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/parent/categories" the API and get the response
     */
    public ParentCategoryOpenApiRequest parentCategoryOpenApiRequest(List<Header> map, String categoryId) {
        queryParam.put("id", categoryId);
        commonGetMethodWithQueryParam(URIConstants.OPEN_API_PARENT_CATEGORY, queryParam);
        return response.as(ParentCategoryOpenApiRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/openapi/firstlast/categories" API and get the response
     */
    public FirstLastOpenApiRequest firstLastOpenApiRequest(List<Header> map) {
        commonGetMethod(URIConstants.OPEN_API_FIRST_LAST,map);
        return response.as(FirstLastOpenApiRequest.class);
    }

    public ReopenTicketRequest reopenTicket(List<Header> map, String ticketIds) {
        body = "{ \"agentId\": " + AGENT_ID + ", \"agentName\": \"" + AGENT_NAME + "\", \"comment\": \"" + COMMENT + "\", \"ticketIdList\": [\"" + ticketIds + "\"], \"ticketPoolId\": " + TICKET_POOL_ID + " }";
        commonPostMethod(URIConstants.REOPEN_TICKET, map, body);
        return response.as(ReopenTicketRequest.class);
    }

    public TicketRequest getTicketDetailById(List<Header> map, String ticketId) {
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.FETCH_TICKET, queryParam);
        return response.as(TicketRequest.class);
    }

    public IssueHistoryRequest getIssueHistory(List<Header> map, String clientConfig, String ticketId) {
        body = "{\"pageNumber\":0,\"ticketId\":\"" + ticketId + "\",\"pageSize\":10,\"clientInfo\":{" + clientConfig + "}}";
        commonPostMethod(URIConstants.ISSUE_HISTORY, map, body);
        return response.as(IssueHistoryRequest.class);
    }

    public CreateIssueRequest createIssue(List<Header> map, String interactionId, String issueDetails, String categoryIds) {
        body = "{\"interactionId\":\"" + interactionId + "\",\"comment\":\"" + COMMENT + "\",\"createdBy\":\"" + CREATED_BY + "\",\"issueDetails\":[" + issueDetails + "],\"categoryHierarchy\":[" + categoryIds + "]}";
        commonPostMethod(URIConstants.CREATE_ISSUE, map, body);
        return response.as(CreateIssueRequest.class);
    }

    public TicketHistoryLogRequest getTicketHistoryLog(List<Header> map, String ticketId) {
        return getTicketHistoryLog(map, ticketId, 200);
    }

    public TicketHistoryLogRequest getTicketHistoryLog(List<Header> map, String ticketId, Integer statusCode) {
        queryParam.put("id", ticketId);
        commonGetMethodWithQueryParam(URIConstants.FETCH_TICKET_HISTORY_LOG, queryParam);
        return response.as(TicketHistoryLogRequest.class);
    }

    public ClientDeactivateRequest deActivateClientConfig(List<Header> map, Integer id) {
        body = "{\"id\":" + id + "}";
        commonPostMethod(URIConstants.DEACTIVATE_CLIENT_CONFIG, map, body);

        return response.as(ClientDeactivateRequest.class);
    }

    public ClientConfigRequest createClientConfig(List<Header> map, String clientConfig) {
        body = "{\"clientConfig\":[" + clientConfig + "]}";
        commonPostMethod(URIConstants.CLIENT_CONFIG, map, body);
        return response.as(ClientConfigRequest.class);
    }

    public InteractionRequest createInteraction(List<Header> map, String clientConfig) {
        body = "{\"createdBy\": \"" + CREATED_BY + "\",\"finalSubmit\": " + FINAL_SUBMIT + ",\"clientInfo\":{" + clientConfig + "}}";
        commonPostMethod(URIConstants.CREATE_INTERACTION, map, body);
        return response.as(InteractionRequest.class);
    }

    public ClientConfigRequest getClientConfig(List<Header> map) {
        commonGetMethod(URIConstants.CLIENT_CONFIG, map);
        return response.as(ClientConfigRequest.class);
    }

    public IssueLayoutRequest getLayoutConfiguration(List<Header> map, Integer categoryId) {
        body = "{\"layoutConfigType\":\"Issue\",\"categoryId\":" + categoryId + "}";
        commonPostMethod(URIConstants.ISSUE_LAYOUT, map, body);
        return response.as(IssueLayoutRequest.class);
    }

    public CategoryHierarchyRequest getParentCategoryId(List<Header> map, Integer categoryId, Integer statusCode) {
        queryParam.put("id", String.valueOf(categoryId));
        commonGetMethodWithQueryParam(URIConstants.PARENT_CATEGORY, queryParam);
        return response.as(CategoryHierarchyRequest.class);
    }

    public CategoryHierarchyRequest getParentCategoryId(List<Header> map, Integer categoryId) {
        return getParentCategoryId(map, categoryId, 200);
    }

    public CategoryHierarchyRequest firstLastCategoryHierarchyTest(List<Header> map) {
        commonGetMethod(URIConstants.FIRST_LAST, map);
        return response.as(CategoryHierarchyRequest.class);
    }

    /**
     * This Method is used to hit the "/api/sr-service/v1/client/fields" API and get the response
     *
     * @return response of the API
     */
    public AllConfiguredClientRequest allConfiguredClientRequest(List<Header> map) {
        commonGetMethod(URIConstants.CONFIGURED_CLIENTS, map);
        return response.as(AllConfiguredClientRequest.class);
    }

    /*
    This Method is used to hit the "/api/user-mngmnt/v2/login" and get the response
     */
    public LoginRequest loginRequest(List<Header> map, String body) {
        baseURI = umBaseUrl;
        Headers headers = new Headers(map);
        request = given()
                .headers(headers)
                .body(body)
                .contentType(APPLICATION_JSON);
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
        commonPostMethod(URIConstants.CONFIGURED_CLIENTS, map, "");
        return response.getStatusCode();
    }

    public CloseTicketRequest closeTicket(List<Header> map, String ticketId, String interactionId) {
        body = "{\"agentId\":" + AGENT_ID + ",\"updatedBy\":" + UPDATED_BY + ",\"stateId\":" + STATE_ID + ",\"ticketId\":\"" + ticketId + "\",\"comment\":[{\"ticketPoolId\":" + TICKET_POOL_ID + ",\"agentName\":\"" + AGENT_NAME + "\",\"comment\":\"" + CLOSURE_COMMENT + "\",\"commentType\":\"\",\"agentId\":" + AGENT_ID + ",\"interactionId\":" + interactionId + "}],\"ticketPoolId\":" + TICKET_POOL_ID + "}";
        commonPostMethod(URIConstants.UPDATE_TICKET, map, body);
        return response.as(CloseTicketRequest.class);
    }

    public TicketListRequest ticketListRequest(List<Header> map) {
        return ticketListRequest(map, PAGE_SIZE, PAGE_NUMBER, STATE_ID);
    }

    public TicketListRequest ticketListRequest(List<Header> map, String pageSize, String pageNumber, String stateId) {
        body = "{\"agentId\":" + AGENT_ID + ",\"pageNumber\":" + pageNumber + ",\"pageSize\":" + pageSize + ",\"ticketPoolIds\":[" + TICKET_POOL_IDS + "],\"fromDate\":null,\"toDate\":null,\"slaFromDate\":null,\"slaToDate\":null,\"ticketAssigneeId\":null,\"stateIds\":[" + stateId + "],\"priorityIds\":[],\"workGroupEscalationIds\":null,\"categoryIds\":null}";
        commonPostMethod(URIConstants.TICKETS_BY_AGENT, map, body);
        return response.as(TicketListRequest.class);
    }

    public TicketHistoryRequest ticketHistoryRequest(List<Header> map, String body) {
        commonPostMethod(URIConstants.TICKET_HISTORY, map, body);
        return response.as(TicketHistoryRequest.class);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/tickets" with filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithFilterRequest(List<Header> map, String clientConfig, String ticketId) {
        body = "{\"pageNumber\":" + PAGE_NUMBER + ",\"pageSize\":" + PAGE_SIZE + ",\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "},\"fromDate\":null,\"toDate\":null,\"stateIds\":[" + EXTERNAL_STATE_IDS + "],\"categoryIds\":null,\"days\":10}}";
        return ticketHistoryRequest(map, body);
    }

    /*
    This Method is used to hit the "/api/sr-service/v1/tickets" without filters and get the response
     */
    public TicketHistoryRequest ticketHistoryWithoutFilter(List<Header> map, String clientConfig) {
        body = "{\"pageNumber\":" + PAGE_NUMBER + ",\"pageSize\":" + PAGE_SIZE + ",\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "}}}";
        return ticketHistoryRequest(map, body);
    }

    /*
    This Methos is used to hit the "/api/sr-service/v1/tickets" with category filter and get the response
     */
    public TicketHistoryRequest ticketHistoryWithCategoryFilter(List<Header> map, String clientConfig, String categoryIds) {
        body = "{\"pageNumber\":" + PAGE_NUMBER + ",\"pageSize\":" + PAGE_SIZE + ",\"ticketSearchCriteria\":{\"clientInfo\":{" + clientConfig + "},\"categoryIds\":[" + categoryIds + "]}}";
        return ticketHistoryRequest(map, body);
    }
}
