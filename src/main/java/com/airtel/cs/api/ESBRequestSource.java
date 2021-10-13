package com.airtel.cs.api;

import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.model.request.AccountDetailRequest;
import com.airtel.cs.model.request.AccountLineRequest;
import com.airtel.cs.model.request.AccountLinesRequest;
import com.airtel.cs.model.request.EnterpriseAccountRequest;
import com.airtel.cs.model.request.GenericRequest;
import com.airtel.cs.model.request.InvoiceDetailRequest;
import com.airtel.cs.model.request.LoanRequest;
import com.airtel.cs.model.request.OfferDetailRequest;
import com.airtel.cs.model.request.PaymentHistoryESBRequest;
import com.airtel.cs.model.request.PaymentRequest;
import com.airtel.cs.model.request.StatementRequest;
import com.airtel.cs.model.request.UsageHistoryMenuRequest;
import com.airtel.cs.model.request.UsageHistoryRequest;
import com.airtel.cs.model.request.UsageRequestV3DTO;
import com.airtel.cs.model.response.CreditLimitResponse;
import com.airtel.cs.model.response.InvoiceHistoryResponse;
import com.airtel.cs.model.response.PaymentResponse;
import com.airtel.cs.model.response.PlanPackESBResponse;
import com.airtel.cs.model.response.PostpaidBillDetailsResponse;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.postpaid.AccountStatementResponse;
import com.airtel.cs.model.response.postpaid.enterprise.AccountLinesResponse;
import com.airtel.cs.model.response.serviceclassrateplan.ServiceClassRatePlanResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ESBRequestSource extends RestCommonUtils {

    private static final Map<String, Object> queryParam = new HashMap<>();
    private static final String DOWNSTREAM_API_CALLING = "downstream.api.calling";
    private static final String DOWNSTREAM_API_ERROR = "downstream.api.error";
    private static final String MSISDN = "msisdn";
    private static final String ACCOUNT_NO = "accountNo";
    private static final String GSM_CUSTOMER_PROFILE_BASE_URL = "gsm.customer.profile.base.url";
    private static final String SUBS_TRANSACTION_SERVICE_BASE_URL = "subscriber.transaction.base.url";
    private static final String END_DATE = "endDate";
    private static final String START_DATE = "startDate";
    private static final String VAS_SERVICE_TUNE_BASE_URL = "vas.service.tune.base.url";
    private static final String API_ENTERPRISE_SERVICE_BASE_URL = "api.enterprise.service.base.url";
    private static final String VAS_SERVICE_LOAN_BASE_URL = "vas.service.loan.base.url";
    private static final String SUBSCRIBER_PRODUCT_BASE_URL = "subscriber.product.base.url";
    private static final String USAGE_HISTORY = " -Usage history ";
    private static final String ENTERPRISE_SERVICE_BASE_URL = "enterprise.service.base.url";
    private static final String GSM_KYC = " - gsm kyc";
    private static final String KYC_REQUEST = " - KYC request";
    private static final String CUSTOMER_PROFILE_V2 = " - customer profile V2 ";
    private static final String QUERY_BALANCE = " - query balance ";
    private static final String RECHARGE_HISTORY = " - recharge history ";
    private static final String VOUCHER_DETAILS = " - voucher details ";
    private static final String VOUCHER_REFILL_BARRED = " -voucher refill barred ";
    private static final String TOP_TWENTY_RINGTONE = " -top twenty ringtone ";
    private static final String SEARCH_NAME_TUNE = " -search name tune ";
    private static final String GENERIC_SEARCH_API = " -Generic search api ";
    private static final String RING_BACK_TONE_LIST = " - ring back tone list ";
    private static final String ACCUMULATOR_API = " - accumulatorAPI ";
    private static final String HLR_DETAILS = " - HLR DETAILS ";
    private static final String AVAILABLE_TARIFF_PLANS = " - available tariff plans ";
    private static final String CURRENT_TARIFF_PLANS = " - current tariff plans ";
    private static final String OFFER_DETAILS = " - offer details ";
    private static final String FRIENDS_AND_FAMILY_DETAILS = " - friends and family details ";
    private static final String GET_CREDIT_LIMIT = " -get credit limit ";
    private static final String INVOICE_HISTORY = " -invoice history ";
    private static final String ENTERPRISE_ACCOUNT_LINES = " -enterprise account lines ";
    private static final String POSTPAID_BILL_DETAILS = " -postpaid bill details ";
    private static final String MY_PLAN = " - my-plan ";
    private static final String MY_PACK = " - my-pack";
    private static final String GET_USAGE = " - get-usage";
    private static final String ACCOUNT_PAYMENTS = " - account payments";
    private static final String ACCOUNT_LINES = " - account lines";
    private static final String SLASH = "/";
    private static final String ACCOUNT_STATEMENT = " - account statement ";
    private static final String VENDOR_DETAILS = " -vendor details";
    private static final String LOAN_SUMMARY = " - loan summary ";
    private static final String LOAN_DETAILS = " - loan details ";
    private static final String ACCOUNT_DETAILS = " - account details ";
    private static final String GSM_KYC_REQUEST = " - GSM KYC request ";
    private static final String SELF_CARE_USER_DETAILS = " - self care user details";
    private static final String DEVICE_INFO = " - Device info";
    private static final String EXCEPTION_IN_METHOD = "Exception in method -";
    public static final String FREE = "FREE";
    public static final String BOTH = "BOTH";
    public static final String USAGE_HISTORY_V3 = " - Usage history V3 ";
    private static final String ENTERPRISE_SEARCH = " -enterprise account search ";
    public static final String ENTERPRISE_ACCOUNT_NUMBER = "enterpriseAccountNumber";
    public static final String CORPORATE_CUSTOMER_NUMBER = "corporateCustomerNumber";
    public static final String AM_PROFILE_DETAILS = " -am profile and wallet deatils";
    public static final String ENTERPRISE_PAYMENT_HISTORY = "-enterprise payment history";


    /**
     * This method is used to test the downstream API
     *
     * @param statusCode  the status code
     * @param failMessage the fail message
     * @param passMessage the pass message
     */
    public void checkDownstreamAPI(int statusCode, String failMessage, String passMessage) {
        if (statusCode != 200) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + failMessage + response.getStatusCode(), false);
        } else {
            commonLib.pass(passMessage + response.getBody().prettyPrint());
        }
    }

    /**
     * This Method will hit the Downstream APIs related to profile api
     *
     * @param msisdn The msisdn
     */
    public void callProfileESBAPI(String msisdn) {
        try {
            commonLib.info(constants.getValue(DOWNSTREAM_API_CALLING) + GSM_KYC_REQUEST);
            queryParam.put(MSISDN, msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
            checkDownstreamAPI(response.getStatusCode(), GSM_KYC_REQUEST, "Downstream API GSM KYC request working Fine and response is: ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + SELF_CARE_USER_DETAILS, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("gsm.self.care.user.details.api.url") + ESBURIConstants.SELF_CARE_USER_DETAILS,
                    queryParam);
            checkDownstreamAPI(response.getStatusCode(), SELF_CARE_USER_DETAILS, "Downstream API self care user details working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + DEVICE_INFO, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.DEVICE_INFO, queryParam);
            checkDownstreamAPI(response.getStatusCode(), DEVICE_INFO, "Downstream API Device info working with data ");
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + DEVICE_INFO + SLASH + SELF_CARE_USER_DETAILS + SLASH + GSM_KYC_REQUEST + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to am service profile
     *
     * @param msisdn The msisdn
     */
    public void callAmServiceProfileESBAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + KYC_REQUEST, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
            checkDownstreamAPI(response.getStatusCode(), KYC_REQUEST, "Downstream API KYC request working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + KYC_REQUEST + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the  Downstream APIs related to gsm kyc
     *
     * @param msisdn The msisdn
     */
    public void callGsmKycESBAPI(String msisdn) {
        try {
            callCustomerProfileV2(msisdn);

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + GSM_KYC, JavaColors.GREEN, false);
            JSONObject json = new JSONObject();
            json.put("clientId", constants.getValue("kyc.client.id"));
            json.put("clientSecret", constants.getValue("kyc.client.secret"));
            commonPostMethod(constants.getValue("api.kyc.auth.token.url") + ESBURIConstants.TOKEN, json);
            checkDownstreamAPI(response.getStatusCode(), GSM_KYC, "Downstream API gsm kyc working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + GSM_KYC + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to customer profile V2
     *
     * @param msisdn The msisdn
     */
    public void callCustomerProfileV2(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + CUSTOMER_PROFILE_V2, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.CUSTOMER_PROFILLE, queryParam);
            checkDownstreamAPI(response.getStatusCode(), CUSTOMER_PROFILE_V2, "Downstream API customer profile V2 working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + CUSTOMER_PROFILE_V2 + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to account plans
     *
     * @param msisdn The msisdn
     */
    public void callAccountPlanESBAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + QUERY_BALANCE, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.QUERY_BALANCE, queryParam);
            checkDownstreamAPI(response.getStatusCode(), QUERY_BALANCE, "Downstream API query balance working with data ");
            callRechargeHistory(msisdn, Timestamp.valueOf(LocalDateTime.now()).toInstant().toEpochMilli(),
                    Timestamp.valueOf(LocalDateTime.now().minusDays(60).with(LocalTime.of(0, 0, 0))).toInstant().toEpochMilli());
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + QUERY_BALANCE + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to recharge history
     *
     * @param msisdn    The msisdn
     * @param endDate   The end date
     * @param startDate The start date
     */
    public void callRechargeHistory(String msisdn, Long endDate, Long startDate) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + RECHARGE_HISTORY, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put(END_DATE, endDate);
            queryParam.put(START_DATE, startDate);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.RECHARGE_HISTORY, queryParam);
            checkDownstreamAPI(response.getStatusCode(), RECHARGE_HISTORY, "Downstream API recharge history working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + RECHARGE_HISTORY + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher details
     *
     * @param voucherId The voucher id
     */
    public void callVoucherDetails(String voucherId) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + VOUCHER_DETAILS, JavaColors.GREEN, false);
            if (StringUtils.isNotBlank(voucherId)) {
                queryParam.put("serial_number", voucherId);
                commonGetMethodWithQueryParam(constants.getValue("voucher.service.base.url") + ESBURIConstants.VOUCHER_DETAIL, queryParam);
            }
            checkDownstreamAPI(response.getStatusCode(), VOUCHER_DETAILS, "Downstream API voucher details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + VOUCHER_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher refil barred
     *
     * @param msisdn The msisdn
     */
    public void callVoucherRefilBarred(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + VOUCHER_REFILL_BARRED, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.VOUCHER_REFILL_BARRED,
                    new GenericRequest(msisdn));
            checkDownstreamAPI(response.getStatusCode(), VOUCHER_REFILL_BARRED, "Downstream API voucher refill barred working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + VOUCHER_REFILL_BARRED + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher refil barred
     *
     * @param msisdn The msisdn
     */
    public void callRingtoneDetailsTest(String msisdn, String searchText) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + TOP_TWENTY_RINGTONE, JavaColors.GREEN, false);
            commonGetMethod(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.TOP_TWENTY_RINGBACK_TONE);
            checkDownstreamAPI(response.getStatusCode(), TOP_TWENTY_RINGTONE, "Downstream API top twenty ringtone working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + SEARCH_NAME_TUNE, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put("query", searchText);
            commonGetMethodWithQueryParam(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.SEARCH_NAME_TUNE, queryParam);
            checkDownstreamAPI(response.getStatusCode(), SEARCH_NAME_TUNE, "Downstream API search name tune working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + GENERIC_SEARCH_API, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put("query", searchText);
            commonGetMethodWithQueryParam(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.GENERIC_SEARCH_API, queryParam);
            checkDownstreamAPI(response.getStatusCode(), GENERIC_SEARCH_API, "Downstream API Generic search api working with data ");
        } catch (Exception exp) {
            commonLib.fail(
                    constants.getValue(DOWNSTREAM_API_ERROR) + TOP_TWENTY_RINGTONE + SLASH + SEARCH_NAME_TUNE + SLASH + GENERIC_SEARCH_API + exp.getMessage(),
                    false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to ring back tone list
     *
     * @param msisdn The msisdn
     */
    public void callActiveRingTone(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + RING_BACK_TONE_LIST, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.RING_BACK_TONE_LIST, queryParam);
            checkDownstreamAPI(response.getStatusCode(), RING_BACK_TONE_LIST, "Downstream API ring back tone list working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + RING_BACK_TONE_LIST + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to accumulatorAPI
     *
     * @param msisdn The msisdn
     */
    public void callAccumulatorAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCUMULATOR_API, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_ACCUMULATORS, new GenericRequest(msisdn));
            checkDownstreamAPI(response.getStatusCode(), ACCUMULATOR_API, "Downstream API accumulatorAPI working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + ACCUMULATOR_API + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to HLR DETAILS
     *
     * @param msisdn The msisdn
     */
    public void callHLRFetchDetails(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + HLR_DETAILS, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("hlr.services.profile.url") + ESBURIConstants.HLR_FETCH_DETAILS, queryParam);
            checkDownstreamAPI(response.getStatusCode(), HLR_DETAILS, "Downstream API accumulatorAPI working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + HLR_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to available tariff plans
     *
     * @param genericRequest The generic request
     */
    public void callAvailableTarrifPlan(GenericRequest genericRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + AVAILABLE_TARIFF_PLANS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_AVAILABLE_PLANS, genericRequest);
            checkDownstreamAPI(response.getStatusCode(), AVAILABLE_TARIFF_PLANS, "Downstream API available tariff plans working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + AVAILABLE_TARIFF_PLANS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to current tariff plans
     *
     * @param genericRequest The generic request
     */
    public void callCurrentTarrifPlan(GenericRequest genericRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + CURRENT_TARIFF_PLANS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_CURRENT_PLAN, genericRequest);
            checkDownstreamAPI(response.getStatusCode(), CURRENT_TARIFF_PLANS, "Downstream API current tariff plans working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + CURRENT_TARIFF_PLANS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to offer details
     *
     * @param offerDetailRequest The offer detail request
     */
    public void callOfferDetailsAPI(OfferDetailRequest offerDetailRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + OFFER_DETAILS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.OFFER_DETAILS, offerDetailRequest);
            checkDownstreamAPI(response.getStatusCode(), OFFER_DETAILS, "Downstream API offer details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + OFFER_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to friends and family details
     *
     * @param genericRequest The generic request
     */
    public void callFriensFamilyAPI(GenericRequest genericRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + FRIENDS_AND_FAMILY_DETAILS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.FRIENDS_FAMILY, genericRequest);
            checkDownstreamAPI(response.getStatusCode(), FRIENDS_AND_FAMILY_DETAILS, "Downstream API friends and family details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + FRIENDS_AND_FAMILY_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param msisdn The msisdn
     */
    public void callPostpaidAccountInformation(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + GET_CREDIT_LIMIT, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_CREDIT_LIMIT, queryParam);
            checkDownstreamAPI(response.getStatusCode(), GET_CREDIT_LIMIT, "Downstream API get credit limit working with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + INVOICE_HISTORY, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.INVOICE_HISTORY, queryParam);
            checkDownstreamAPI(response.getStatusCode(), INVOICE_HISTORY, "Downstream API invoice history working with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + POSTPAID_BILL_DETAILS, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.POSTPAID_BILL_DETAILS,
                    queryParam);
            checkDownstreamAPI(response.getStatusCode(), POSTPAID_BILL_DETAILS, "Downstream API postpaid bill details with data ");

            callCustomerProfileV2(msisdn);
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + GET_CREDIT_LIMIT + SLASH + INVOICE_HISTORY + SLASH + POSTPAID_BILL_DETAILS + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param msisdn The msisdn
     */
    public void callingPlanAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + MY_PLAN, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.MY_PLAN, queryParam);
            checkDownstreamAPI(response.getStatusCode(), MY_PLAN, "Downstream API get my-plan working with data ");
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + MY_PLAN + SLASH + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param msisdn The msisdn
     */
    public void callingPackAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + MY_PACK, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.MY_PACK, queryParam);
            checkDownstreamAPI(response.getStatusCode(), MY_PACK, "Downstream API get my-pack working with data ");
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + MY_PACK + SLASH + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param msisdn The msisdn
     */
    public void callingGetUsageAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + GET_USAGE, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_USAGE, queryParam);
            checkDownstreamAPI(response.getStatusCode(), GET_USAGE, "Downstream API get my-pack working with data ");
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + GET_USAGE + SLASH + exp.getMessage(),
                            false);
        }
    }


    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param customerAccountNumber The customer account number
     */
    public void callPostPaidAPI(String customerAccountNumber, PaymentRequest paymentRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_PAYMENTS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.ACCOUNT_PAYMENT,
                    paymentRequest);
            checkDownstreamAPI(response.getStatusCode(), ACCOUNT_PAYMENTS, "Downstream API account payments with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_LINES, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.POSTPAID_ACCOUNTS_LINE,
                    customerAccountNumber);
            checkDownstreamAPI(response.getStatusCode(), ACCOUNT_LINES, "Downstream API account lines with data ");

        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + ACCOUNT_PAYMENTS + SLASH + ACCOUNT_LINES + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param accountNumber The account number
     */
    public void callingAccountStatementAPI(String accountNumber) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_STATEMENT, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.ACCOUNT_STATEMENT,
                    accountNumber);
            checkDownstreamAPI(response.getStatusCode(), ACCOUNT_STATEMENT, "Downstream API account statement with data ");

        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + ACCOUNT_STATEMENT + SLASH + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to Usage history
     *
     * @param usageHistoryMenuRequest The Usage history request
     */
    public void callUsageHistory(UsageHistoryMenuRequest usageHistoryMenuRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + USAGE_HISTORY, JavaColors.GREEN, false);
            queryParam.put(END_DATE, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            queryParam.put(MSISDN, usageHistoryMenuRequest.getMsisdn());
            queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
            queryParam.put(START_DATE, UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals("FREE"))) {
                queryParam.put("cdrType", "BOTH");
            }
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.USAGE_HISTORY, queryParam);
            checkDownstreamAPI(response.getStatusCode(), USAGE_HISTORY, "Downstream API Usage history working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + USAGE_HISTORY + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the V3 Downstream APIs related to Usage history
     *
     * @param usageHistoryMenuRequest The Usage history request
     */
    public void callV3UsageHistory(UsageHistoryMenuRequest usageHistoryMenuRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + USAGE_HISTORY, JavaColors.GREEN, false);
            UsageRequestV3DTO v3RequestDTO = new UsageRequestV3DTO();
            v3RequestDTO.setEndDate(UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            v3RequestDTO.setMsisdn(usageHistoryMenuRequest.getMsisdn());
            v3RequestDTO.setStartDate(UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            v3RequestDTO.setNumberOfRecords(20);
            v3RequestDTO.setOffset(0);
            if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals(FREE))) {
                v3RequestDTO.setCdrType(BOTH);
            }
            commonPostMethod(constants.getValue(SUBS_TRANSACTION_SERVICE_BASE_URL) + ESBURIConstants.V3_USAGE_HISTORY, v3RequestDTO);
            checkDownstreamAPI(response.getStatusCode(), USAGE_HISTORY, "Downstream API Usage history V3 working with data");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + USAGE_HISTORY_V3 + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to Usage history
     *
     * @param usageHistoryRequest The Usage history request
     */
    public void callUsageHistory(UsageHistoryRequest usageHistoryRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + USAGE_HISTORY, JavaColors.GREEN, false);
            queryParam.put(END_DATE, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            queryParam.put(MSISDN, usageHistoryRequest.getMsisdn());
            queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
            queryParam.put(START_DATE, UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            queryParam.put("cdrType", "PAID");
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.USAGE_HISTORY, queryParam);
            checkDownstreamAPI(response.getStatusCode(), USAGE_HISTORY, "Downstream API Usage history working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + USAGE_HISTORY + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to vendor details
     */
    public void callVendors() {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + VENDOR_DETAILS, JavaColors.GREEN, false);
            commonGetMethod(constants.getValue(VAS_SERVICE_LOAN_BASE_URL) + ESBURIConstants.VENDORS);
            checkDownstreamAPI(response.getStatusCode(), VENDOR_DETAILS, "Downstream API vendor details working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + VENDOR_DETAILS + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to loan summary
     *
     * @param loanRequest The loan request
     */
    public void callLoanSummary(LoanRequest loanRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + LOAN_SUMMARY, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(VAS_SERVICE_LOAN_BASE_URL) + ESBURIConstants.LOAN_SUMMARY, loanRequest);
            checkDownstreamAPI(response.getStatusCode(), LOAN_SUMMARY, "Downstream API loan summary working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + LOAN_SUMMARY + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to loan details
     *
     * @param loanRequest The loan request
     */
    public void callLoanDetails(LoanRequest loanRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + LOAN_DETAILS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue(VAS_SERVICE_LOAN_BASE_URL) + ESBURIConstants.LOAN_DETAILS, loanRequest);
            checkDownstreamAPI(response.getStatusCode(), LOAN_DETAILS, "Downstream API loan details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + LOAN_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * Call service class rate plan service class rate plan response dto.
     *
     * @param genericRequest the generic request
     * @return the service class rate plan response dto
     */
    public ServiceClassRatePlanResponseDTO callServiceClassRatePlan(GenericRequest genericRequest) {
        ServiceClassRatePlanResponseDTO serviceClassRatePlanResponseDTO = null;
        try {
            commonPostMethod(constants.getValue(SUBSCRIBER_PRODUCT_BASE_URL) + ESBURIConstants.SERVICE_CLASS_RATE_PLAN, genericRequest);
            serviceClassRatePlanResponseDTO = response.as(ServiceClassRatePlanResponseDTO.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + " serviceClassRatePlanResponse " + e.getMessage(), false);
        }
        return serviceClassRatePlanResponseDTO;
    }


    /**
     * This method is used to call customer profile v2 api "/api/subscriber-profile/v2/customer-profile"
     *
     * @param msisdn The msisdn
     * @return The result
     */
    public CustomerProfileResponse customerProfileResponse(String msisdn) {
        CustomerProfileResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.CUSTOMER_PROFILE_V2, queryParam);
            result = response.as(CustomerProfileResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "customerProfileResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method is used to call invoice history v2 api "/api/subscriber-profile/v1/invoice-history"
     *
     * @param msisdn The msisdn
     * @return The result
     */
    public InvoiceHistoryResponse invoiceHistoryResponse(String msisdn) {
        InvoiceHistoryResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.INVOICE_HISTORY_V1, queryParam);
            result = response.as(InvoiceHistoryResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "invoiceHistoryResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call invoice history v2 api "/api/subscriber-profile/v1/postpaid-bill-details"
     *
     * @param msisdn The msisdn
     * @return The result
     */
    public PostpaidBillDetailsResponse postpaidBillDetailsResponse(String msisdn) {
        PostpaidBillDetailsResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.POSTPAID_BILL_DETAIL_V1, queryParam);
            result = response.as(PostpaidBillDetailsResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "postpaidBillDetailsResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call get usage api "/api/subscriber-profile/v1/get-usage"
     *
     * @param msisdn The msisdn
     * @return The result
     */
    public PlanPackESBResponse planPackResponse(String msisdn) {
        PlanPackESBResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_USAGE, queryParam);
            result = response.as(PlanPackESBResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "planPackResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call account payment api "/api/enterprise-service/v1/accounts/payments"
     *
     * @param paymentRequest The payment request
     * @return The result
     */
    public PaymentResponse paymentResponse(PaymentRequest paymentRequest) {
        PaymentResponse result = null;
        try {
            commonPostMethod(constants.getValue(API_ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.ACCOUNT_PAYMENT, paymentRequest);
            result = response.as(PaymentResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "paymentResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method is used to call account payment api "/api/enterprise-service/v1/accounts/statement"
     *
     * @param paymentRequest The payment request
     * @return The result
     */
    public AccountStatementResponse accountStatementResponse(StatementRequest paymentRequest) {
        AccountStatementResponse result = null;
        try {
            commonPostMethod(constants.getValue(API_ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.ACCOUNT_STATEMENT, paymentRequest);
            result = response.as(AccountStatementResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "paymentResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call credit limit api api "/api/subscriber-profile/v1/get-credit-limit"
     *
     * @param msisdn The msisdn
     * @return The result
     */
    public CreditLimitResponse creditLimitResponse(String msisdn) {
        CreditLimitResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.CREDIT_LIMIT, queryParam);
            result = response.as(CreditLimitResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "creditLimitResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This Method will hit the Downstream APIs related to postpaid account info details
     *
     * @param accountDetailRequest The account details request
     */
    public void callPostpaidAccountInfoDetails(AccountDetailRequest accountDetailRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_DETAILS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.POSTPAID_ACCOUNT_DETAILS, accountDetailRequest);
            checkDownstreamAPI(response.getStatusCode(), ACCOUNT_DETAILS, "Downstream API account details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * This method is used to call account payment api "/api/enterprise-service/v1/accounts/lines"
     *
     * @param accountLineRequest The account line request
     * @return The result
     */
    public AccountLinesResponse accountLinesResponse(AccountLineRequest accountLineRequest) {
        AccountLinesResponse result = null;
        try {
            commonPostMethod(constants.getValue(API_ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.POSTPAID_ACCOUNTS_LINE, accountLineRequest);
            result = response.as(AccountLinesResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "accountLineResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This Method will hit the Downstream APIs related to enterprise search account
     *
     * @param type
     * @param number
     */
    public void callEnterPriseSearchAccount(String type, String number) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ENTERPRISE_SEARCH, JavaColors.GREEN, false);
            EnterpriseAccountRequest enterpriseAccountRequest = new EnterpriseAccountRequest();
            enterpriseAccountRequest.setAccountNo(number);
            switch (type) {
                case ENTERPRISE_ACCOUNT_NUMBER:
                    enterpriseAccountRequest.setAccountNo(number);
                    break;
                case CORPORATE_CUSTOMER_NUMBER:
                    enterpriseAccountRequest.setCustMobileNo(number);
                    break;
                default:
                    enterpriseAccountRequest.setAccountNo(number);
            }
            commonPostMethod(constants.getValue("api.enterprise.service.base.url.mocked") + ESBURIConstants.ENTERPRISE_SEARCH_ACCOUNT,
                enterpriseAccountRequest);
            checkDownstreamAPI(response.getStatusCode(), ENTERPRISE_SEARCH, "Downstream API Enterprise account working with data ");

        } catch (Exception exp) {
            commonLib
                .fail(constants.getValue(DOWNSTREAM_API_ERROR) + ENTERPRISE_SEARCH  + exp.getMessage(),
                    false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to enterprise postpaid account info
     *
     * @param accountNo The msisdn
     * @param paymentRequest
     */
    public void callEnterPrisePostpaidAccountInformation(String accountNo, PaymentRequest paymentRequest) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + INVOICE_HISTORY, JavaColors.GREEN, false);
            queryParam.put(ACCOUNT_NO, accountNo);
            commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.ENTERPRISE_INVOICE_HISTORY,
                new InvoiceDetailRequest(accountNo));
            checkDownstreamAPI(response.getStatusCode(), INVOICE_HISTORY, "Downstream API invoice history working with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_PAYMENTS, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.ACCOUNT_PAYMENT,
                paymentRequest);
            checkDownstreamAPI(response.getStatusCode(), ACCOUNT_PAYMENTS, "Downstream API account payments with data ");

        } catch (Exception exp) {
            commonLib
                .fail(constants.getValue(DOWNSTREAM_API_ERROR) + INVOICE_HISTORY + SLASH + ACCOUNT_PAYMENTS + exp.getMessage(),
                    false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to enterprise linked services details
     *
     * @param accountLinesRequest
     */
    public void callEnterPrisePostpaidAccountInformation(AccountLinesRequest accountLinesRequest) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ENTERPRISE_ACCOUNT_LINES, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.ENTERPRISE_ACCOUNT_LINES,
                accountLinesRequest);
            checkDownstreamAPI(response.getStatusCode(), ENTERPRISE_ACCOUNT_LINES,
                "Downstream API enterprise account lines working with data ");

        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + ENTERPRISE_ACCOUNT_LINES + exp.getMessage(), false);
        }
    }


    /**
     * This Method will hit the Downstream APIs related to enterprise payment history detail
     *
     * @param paymentHistoryESBRequest
     */
    public void callEnterPrisePaymentHistory(PaymentHistoryESBRequest paymentHistoryESBRequest) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ENTERPRISE_PAYMENT_HISTORY, JavaColors.GREEN, false);
            commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.ENTERPRISE_PAYMENT_HISTORY,
                paymentHistoryESBRequest);
            checkDownstreamAPI(response.getStatusCode(), ENTERPRISE_ACCOUNT_LINES,
                "Downstream API enterprise payment history working with data ");

        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + ENTERPRISE_ACCOUNT_LINES + exp.getMessage(), false);
        }
    }

}