package com.airtel.cs.api;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.URIConstants;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.model.request.GenericRequest;
import com.airtel.cs.model.request.UsageHistoryMenuRequest;
import com.airtel.cs.model.request.UsageHistoryRequest;
import com.airtel.cs.model.request.RechargeHistoryRequest;
import com.airtel.cs.model.request.TransactionHistoryRequest;
import com.airtel.cs.model.request.MoreTransactionHistoryRequest;
import com.airtel.cs.model.request.AccountBalanceRequest;
import com.airtel.cs.model.request.AccumulatorsRequest;
import com.airtel.cs.model.request.SMSHistoryRequest;
import com.airtel.cs.model.request.VoucherSearchRequest;
import com.airtel.cs.model.request.ActionTrailRequest;
import com.airtel.cs.model.request.FetchTicketPoolRequest;
import com.airtel.cs.model.request.LoanRequest;
import com.airtel.cs.model.request.RingtonDetailsRequest;
import com.airtel.cs.model.request.ServiceProfileRequest;
import com.airtel.cs.model.request.OfferDetailRequest;
import com.airtel.cs.model.response.amprofile.AMProfile;
import com.airtel.cs.model.response.kycprofile.GsmKyc;
import com.airtel.cs.model.response.plans.Plans;
import com.airtel.cs.model.response.usagehistory.UsageHistory;
import com.airtel.cs.model.response.login.Login;
import com.airtel.cs.model.response.kycprofile.Profile;
import com.airtel.cs.model.response.rechargehistory.RechargeHistory;
import com.airtel.cs.model.response.accounts.AccountsBalance;
import com.airtel.cs.model.response.accumulators.Accumulators;
import com.airtel.cs.model.response.actiontrail.ActionTrail;
import com.airtel.cs.model.response.agentpermission.AgentPermission;
import com.airtel.cs.model.response.agents.AgentDetailAttribute;
import com.airtel.cs.model.response.airtelmoney.AirtelMoney;
import com.airtel.cs.model.response.clearrefillstatus.RefillStatus;
import com.airtel.cs.model.response.configuration.Configuration;
import com.airtel.cs.model.response.crbt.ActivateRingtone;
import com.airtel.cs.model.response.crbt.Top20Ringtone;
import com.airtel.cs.model.response.friendsfamily.FriendsFamily;
import com.airtel.cs.model.response.hlrservice.HLRService;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import com.airtel.cs.model.response.loandetails.Loan;
import com.airtel.cs.model.response.loansummary.Summary;
import com.airtel.cs.model.response.offerdetails.OfferDetail;
import com.airtel.cs.model.response.smshistory.SMSHistory;
import com.airtel.cs.model.response.tariffplan.AvailablePlan;
import com.airtel.cs.model.response.tariffplan.CurrentPlan;
import com.airtel.cs.model.response.ticketlist.Ticket;
import com.airtel.cs.model.response.transfertoqueue.TransferToQueue;
import com.airtel.cs.model.response.vendors.VendorNames;
import com.airtel.cs.model.response.voucher.VoucherSearch;
import io.restassured.http.Headers;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class RequestSource extends RestCommonUtils {

    public static Integer statusCode = null;
    private static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);
    private static final Map<String, Object> queryParam = new HashMap<>();

    /*
    This Method will hit the Available Plan API and returns the response
     */
    public AvailablePlan availablePlanPOJO() {
        AvailablePlan result = null;
        try {
            commonPostMethod(URIConstants.TARIFF_AVAILABLE_PLANS, new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            result = response.as(AvailablePlan.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - availablePlanPOJO " + e.getMessage(), false);
        }
        return result;
    }

    /*
    This Method will hit the Current Plan API and returns the response
     */
    public CurrentPlan currentPlanPOJO() {
        CurrentPlan result = null;
        try {
            commonPostMethod(URIConstants.TARIFF_CURRENT_PLAN, new GenericRequest(TARIFF_PLAN_TEST_NUMBER));
            result = response.as(CurrentPlan.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - currentPlanPOJO " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the Login API "/auth/api/user-mngmnt/v2/login" and return the response
     *
     * @param body body of the API
     * @return response
     */
    public Login loginPOJO(String body) {
        Login result = null;
        try {
            commonLib.info("Logging in Using Login API for getting TOKEN with user");
            commonPostMethod(URIConstants.V2_LOGIN, body);
            result = response.as(Login.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - loginPOJO " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - kycProfileAPITest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - profileAPITest " + e.getMessage(), false);
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
            queryParam.put("msisdn", msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(URIConstants.AM_PROFILE, queryParam);
            result = response.as(AMProfile.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - amServiceProfileAPITest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - gsmKYCAPITest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - accountPlansTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - usageHistoryTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - usageHistoryTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - rechargeHistoryAPITest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - transactionHistoryAPITest " + e.getMessage(), false);
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
            commonLib.fail("Exception in method - moreTransactionHistoryAPITest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - balanceAPITest " + e.getMessage(), false);
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
            commonLib.fail("Exception in method - ticketMetaDataTest " + e.getMessage(), false);
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
            commonLib.fail("Exception in method - smsHistoryTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - voucherSearchTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - vendorsNamesTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - loanSummaryTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - loanDetailsTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - clearRefillTest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - ringtoneDetailTest " + e.getMessage(), false);
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
            queryParam.put("msisdn", msisdn);
            commonGetMethodWithQueryParam(URIConstants.FETCH_TUNES, queryParam);
            result = response.as(ActivateRingtone.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - activateRingtone " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - accumulatorsAPITest " + e.getMessage(), false);
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
        } catch (Exception e) {
            commonLib.fail("Exception in method - getServiceProfileWidgetInfo " + e.getMessage(), false);
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
            commonLib.fail("Exception in method - getConfiguration " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/offer/details" and return the response
     *
     * @param msisdn  The msisdn
     * @return The Response
     */
    public OfferDetail offerDetailAPITest(String msisdn) {
        OfferDetail result = null;
        try {
            commonPostMethod(URIConstants.OFFER_DETAILS, new OfferDetailRequest(msisdn,true));
            result = response.as(OfferDetail.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - OfferDetailAPITest " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/friendsNfamily/details" and return the response
     *
     * @param msisdn  The msisdn
     * @return The Response
     */
    public FriendsFamily friendsFamilyAPITest(String msisdn) {
        FriendsFamily result = null;
        try {
            commonPostMethod(URIConstants.FRIENDS_FAMILY, new GenericRequest(msisdn));
            result = response.as(FriendsFamily.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - friendsAndFamilyAPITest " + e.getMessage(), false);
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
            commonLib.fail("Exception in method - transferToQueuePermissionAPI " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-gsm-service/v1/offer/details" and return the response
     *
     * @param ticketId  The ticketId
     * @param isSupervisor The supervisor or not
     * @return The Response
     */
    public TransferToQueue fetchTicketPool(List<String> ticketId, Boolean isSupervisor) {
        TransferToQueue result = null;
        try {
            commonPostMethod(URIConstants.FETCH_TICKET_POOL,new FetchTicketPoolRequest(ticketId,isSupervisor));
            result = response.as(TransferToQueue.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - fetchTicketPoolAPI " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "/sr/api/sr-service/v1/agents" and return the response
     * @param headers The headers contain auth token including common headers
     * @return The Response
     */
    public AgentDetailAttribute getAgentDetail(Headers headers){
        AgentDetailAttribute result = null;
        try {
            commonGetMethod(URIConstants.AGENT_DETAILS,headers);
            result = response.as(AgentDetailAttribute.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - getAgentDetail " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the API "cs-data-service/v1/event/logs" and return the response
     * @param msisdn The msisdn
     * @param eventType The event type
     * @return The Response
     */
    public ActionTrail getEventHistory(String msisdn, String eventType){
        ActionTrail result = null;
        try {
            commonPostMethod(URIConstants.EVENTS_LOG,new ActionTrailRequest(msisdn,eventType,10,0));
            result = response.as(ActionTrail.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - getEventHistory " + e.getMessage(), false);
        }
        return result;
    }


}
