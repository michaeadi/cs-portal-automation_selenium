package com.airtel.cs.api;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.model.request.AccountDetailRequest;
import com.airtel.cs.model.request.AccountLineRequest;
import com.airtel.cs.model.request.GenericRequest;
import com.airtel.cs.model.request.LoanRequest;
import com.airtel.cs.model.request.OfferDetailRequest;
import com.airtel.cs.model.request.PaymentRequest;
import com.airtel.cs.model.request.StatementRequest;
import com.airtel.cs.model.request.UsageHistoryMenuRequest;
import com.airtel.cs.model.request.UsageHistoryRequest;
import com.airtel.cs.model.response.CreditLimitResponse;
import com.airtel.cs.model.response.InvoiceHistoryResponse;
import com.airtel.cs.model.response.PaymentResponse;
import com.airtel.cs.model.response.PlanPackESBResponse;
import com.airtel.cs.model.response.PostpaidBillDetailsResponse;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.postpaid.AccountStatementResponse;
import com.airtel.cs.model.response.postpaid.enterprise.AccountLinesResponse;
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
    private static final String CALLING_DOWNSTREAM_API = "downstream.api.calling";
    private static final String DOWNSTREAM_API_ERROR = "downstream.api.error";
    private static final String GSM_CUSTOMER_PROFILE_BASE_URL = "gsm.customer.profile.base.url";
    private static final String MSISDN = "msisdn";
    private static final String END_DATE = "endDate";
    private static final String START_DATE = "startDate";
    private static final String VAS_SERVICE_TUNE_BASE_URL = "vas.service.tune.base.url";
    private static final String API_ENTERPRISE_SERVICE_BASE_URL = "api.enterprise.service.base.url";
    private static final String USAGE_HISTORY = " -Usage history";
    private static final String VAS_SERVICE_LOAN_BASE_URL="vas.service.loan.base.url";


    /**
     * This Method will hit the Downstream APIs related to profile api
     *
     * @param msisdn The msisdn
     */
    public void callprofileESBAPI(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - GSM KYC request");
            queryParam.put(MSISDN, msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - GSM KYC request" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API GSM KYC request working Fine and response is: " + response.getBody().prettyPrint());
            }

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - self care user details");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("gsm.self.care.user.details.api.url") + ESBURIConstants.SELF_CARE_USER_DETAILS,
                    queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - self care user details" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API self care user details working Fine and response is: " + response.getBody().prettyPrint());
            }

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - Device info");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.DEVICE_INFO, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - Device info" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API Device info working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - Device info/self care user details/GSM KYC request " + exp.getMessage(),
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
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - KYC request");
            queryParam.put(MSISDN, msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - KYC request" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API KYC request working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - recharge history/customer profile " + exp.getMessage(), false);
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

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - gsm kyc");
            JSONObject json = new JSONObject();
            json.put("clientId", constants.getValue("kyc.client.id"));
            json.put("clientSecret", constants.getValue("kyc.client.secret"));
            commonPostMethod(constants.getValue("api.kyc.auth.token.url") + ESBURIConstants.TOKEN, json);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -  gsm kyc " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API 'gsm kyc' working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - recharge history/customer profile " + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to customer profile V2
     *
     * @param msisdn The msisdn
     */
    public void callCustomerProfileV2(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - customer profile V2");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.CUSTOMER_PROFILLE, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - customer profile V2" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API 'customer profile V2' working Fine and response is:" + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -customer profile V2 " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to account plans
     *
     * @param msisdn The msisdn
     */
    public void callAccoountPlanESBAPI(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - query balance ");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.QUERY_BALANCE, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - query balance " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API query balance working Fine and response is: " + response.getBody().prettyPrint());
            }
            callRechargeHistory(msisdn, Timestamp.valueOf(LocalDateTime.now()).toInstant().toEpochMilli(),
                    Timestamp.valueOf(LocalDateTime.now().minusDays(60).with(LocalTime.of(0, 0, 0))).toInstant().toEpochMilli());

        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - query balance/recharge history " + exp.getMessage(), false);
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
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - recharge history ");
            queryParam.put(MSISDN, msisdn);
            queryParam.put(END_DATE, endDate);
            queryParam.put(START_DATE, startDate);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.RECHARGE_HISTORY, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - recharge history " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API recharge history working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -recharge history " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher details
     *
     * @param voucherId The Voucher Id
     */
    public void callVoucherDetails(String voucherId) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - voucher details ");
            if (StringUtils.isNotBlank(voucherId)) {
                queryParam.put("serial_number", voucherId);
                commonGetMethodWithQueryParam(constants.getValue("voucher.service.base.url") + ESBURIConstants.VOUCHER_DETAIL, queryParam);
            }
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - voucher details " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API voucher details working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -voucher details " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher refil barred
     *
     * @param msisdn The MSISDN
     */
    public void callVoucherRefilBarred(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - voucher refil barred ");
            commonPostMethod(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.VOUCHER_REFILL_BARRED,
                    new GenericRequest(msisdn));
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - voucher refil barred " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API voucher refil barred working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -voucher refil barred " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher refil barred
     *
     * @param msisdn The MSISDN
     */
    public void callRingtoneDetailsTest(String msisdn, String searchText) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -top twenty ringtone");
            commonGetMethod(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.TOP_TWENTY_RINGBACK_TONE);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -top twenty ringtone" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API top twenty ringtone working Fine and response is: " + response.getBody().prettyPrint());
            }

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -search name tune");
            queryParam.put(MSISDN, msisdn);
            queryParam.put("query", searchText);
            commonGetMethodWithQueryParam(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.SEARCH_NAME_TUNE, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - search name tune" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API search name tune working Fine and response is: " + response.getBody().prettyPrint());
            }

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -Generic search api");
            queryParam.put(MSISDN, msisdn);
            queryParam.put("query", searchText);
            commonGetMethodWithQueryParam(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.GENERIC_SEARCH_API, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - Generic search api" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API Generic search api working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib.fail(
                    constants.getValue(DOWNSTREAM_API_ERROR) + " - top twenty ringtone/search name tune/Generic search api " + exp.getMessage(),
                    false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to ring back tone list
     *
     * @param msisdn The MSISDN
     */
    public void callActiveRingTone(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - ring back tone list ");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(VAS_SERVICE_TUNE_BASE_URL) + ESBURIConstants.RING_BACK_TONE_LIST, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - ring back tone list " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API ring back tone list working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -ring back tone list " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to accumulatorAPI
     *
     * @param msisdn The MSISDN
     */
    public void callAccumulatorAPI(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - accumulatorAPI ");
            commonPostMethod(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_ACCUMULATORS, new GenericRequest(msisdn));
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - accumulatorAPI " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API accumulatorAPI working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -accumulatorAPI " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to HLR DETAILS
     *
     * @param msisdn The MSISDN
     */
    public void callHLRFetchDetails(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - HLR DETAILS ");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue("hlr.services.profile.url") + ESBURIConstants.HLR_FETCH_DETAILS, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - HLR DETAILS " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API accumulatorAPI working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -HLR DETAILS " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to available tariff plans
     *
     * @param genericRequest The Generic Request
     */
    public void callAvailableTarrifPlan(GenericRequest genericRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - available tariff plans ");
            commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_AVAILABLE_PLANS, genericRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - available tariff plans " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API available tariff plans working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -available tariff plans " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to current tariff plans
     *
     * @param genericRequest The Generic Request
     */
    public void callCurrentTarrifPlan(GenericRequest genericRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - current tariff plans ");
            commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_CURRENT_PLAN, genericRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - current tariff plans " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API current tariff plans working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -current tariff plans " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to offer details
     *
     * @param offerDetailRequest THe Offer Detail Request
     */
    public void callOfferDetailsAPI(OfferDetailRequest offerDetailRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - offer details ");
            commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.OFFER_DETAILS, offerDetailRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - offer details " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API offer details working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -offer details " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to friends and family details
     *
     * @param genericRequest The Generic Request
     */
    public void callFriensFamilyAPI(GenericRequest genericRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - friends and family details ");
            commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.FRIENDS_FAMILY, genericRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - friends and family details " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API friends and family details working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -friends and family details " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param msisdn The MSISDN
     */
    public void callPostpaidAccountInformation(String msisdn) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -get credit limit");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_CREDIT_LIMIT, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -get credit limit" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API get credit limit working Fine and response is: " + response.getBody().prettyPrint());
            }

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -invoice history");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.INVOICE_HISTORY, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - invoice history" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API invoice history working Fine and response is: " + response.getBody().prettyPrint());
            }

            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -postpaid bill details");
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.POSTPAID_BILL_DETAILS,
                    queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - postpaid bill details" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API postpaid bill details with data " + response.getBody().prettyPrint());
            }

            callCustomerProfileV2(msisdn);
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - get credit limit/invoice history/postpaid bill details " + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to Usage history
     *
     * @param usageHistoryMenuRequest The Usage History Menu Request
     */
    public void callUsageHistory(UsageHistoryMenuRequest usageHistoryMenuRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + USAGE_HISTORY);
            queryParam.put(END_DATE, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            queryParam.put(MSISDN, usageHistoryMenuRequest.getMsisdn());
            queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
            queryParam.put(START_DATE, UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals("FREE"))) {
                queryParam.put("cdrType", "BOTH");
            }
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.USAGE_HISTORY, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + USAGE_HISTORY + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API Usage history working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - Usage history " + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to Usage history
     *
     * @param usageHistoryRequest The Usage History Menu Request
     */
    public void callUsageHistory(UsageHistoryRequest usageHistoryRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + USAGE_HISTORY);
            queryParam.put(END_DATE, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            queryParam.put(MSISDN, usageHistoryRequest.getMsisdn());
            queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
            queryParam.put(START_DATE, UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            queryParam.put("cdrType", "PAID");
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.USAGE_HISTORY, queryParam);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + USAGE_HISTORY + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API Usage history working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - Usage history " + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to vendor details
     */
    public void callVendors() {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " -vendor details");
            commonGetMethod(constants.getValue(VAS_SERVICE_LOAN_BASE_URL) + ESBURIConstants.VENDORS);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -vendor details" + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API vendor details working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - vendor details " + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to loan summary
     *
     * @param loanRequest The Loan Request
     */
    public void callLoanSummary(LoanRequest loanRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - loan summary ");
            commonPostMethod(constants.getValue(VAS_SERVICE_LOAN_BASE_URL) + ESBURIConstants.LOAN_SUMMARY, loanRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - loan summary " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API loan summary working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -loan summary " + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to loan details
     *
     * @param loanRequest The Loan Request
     */
    public void callLoanDetails(LoanRequest loanRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - loan details ");
            commonPostMethod(constants.getValue(VAS_SERVICE_LOAN_BASE_URL) + ESBURIConstants.LOAN_DETAILS, loanRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " - loan details " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API loan details working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + " -loan details " + e.getMessage(), false);
        }
    }


    /**
     * This method is used to call customer profile v2 api "/api/subscriber-profile/v2/customer-profile"
     *
     * @param msisdn The MSISDN
     * @return The Result
     */
    public CustomerProfileResponse customerProfileResponse(String msisdn) {
        CustomerProfileResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.CUSTOMER_PROFILE_V2, queryParam);
            result = response.as(CustomerProfileResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - customerProfileResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method is used to call invoice history v2 api "/api/subscriber-profile/v1/invoice-history"
     *
     * @param msisdn The MSISDN
     * @return The Result
     */
    public InvoiceHistoryResponse invoiceHistoryResponse(String msisdn) {
        InvoiceHistoryResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.INVOICE_HISTORY_V1, queryParam);
            result = response.as(InvoiceHistoryResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - invoiceHistoryResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call invoice history v2 api "/api/subscriber-profile/v1/postpaid-bill-details"
     *
     * @param msisdn The MSISDN
     * @return The Result
     */
    public PostpaidBillDetailsResponse postpaidBillDetailsResponse(String msisdn) {
        PostpaidBillDetailsResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.POSTPAID_BILL_DETAIL_V1, queryParam);
            result = response.as(PostpaidBillDetailsResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - postpaidBillDetailsResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method is used to call get usage api "/api/subscriber-profile/v1/get-usage"
     *
     * @param msisdn The MSISDN
     * @return The Result
     */
    public PlanPackESBResponse planPackResponse(String msisdn) {
        PlanPackESBResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.GET_USAGE, queryParam);
            result = response.as(PlanPackESBResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - planPackResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call account payment api "/api/enterprise-service/v1/accounts/payments"
     *
     * @param paymentRequest The Payment Request
     * @return The Result
     */
    public PaymentResponse paymentResponse(PaymentRequest paymentRequest) {
        PaymentResponse result = null;
        try {
            commonPostMethod(constants.getValue(API_ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.ACCOUNT_PAYMENT, paymentRequest);
            result = response.as(PaymentResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - paymentResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method is used to call account payment api "/api/enterprise-service/v1/accounts/statement"
     *
     * @param paymentRequest The Payment Request
     * @return The Result
     */
    public AccountStatementResponse accountStatementResponse(StatementRequest paymentRequest) {
        AccountStatementResponse result = null;
        try {
            commonPostMethod(constants.getValue(API_ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.ACCOUNT_STATEMENT, paymentRequest);
            result = response.as(AccountStatementResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - paymentResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This method is used to call credit limit api api "/api/subscriber-profile/v1/get-credit-limit"
     *
     * @param msisdn The MSISDN
     * @return The Result
     */
    public CreditLimitResponse creditLimitResponse(String msisdn) {
        CreditLimitResponse result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(constants.getValue(GSM_CUSTOMER_PROFILE_BASE_URL) + ESBURIConstants.CREDIT_LIMIT, queryParam);
            result = response.as(CreditLimitResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - creditLimitResponse " + e.getMessage(), false);
        }
        return result;
    }


    /**
     * This Method will hit the Downstream APIs related to postpaid account info details
     *
     * @param accountDetailRequest The Account Detail Request
     */
    public void callPostpaidAccountInfoDetails(AccountDetailRequest accountDetailRequest) {
        try {
            commonLib.info(constants.getValue(CALLING_DOWNSTREAM_API) + " - account details ");
            commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.POSTPAID_ACCOUNT_DETAILS, accountDetailRequest);
            if (response.getStatusCode() != 200) {
                commonLib.fail(constants.getValue(CALLING_DOWNSTREAM_API) + " - account details " + response.getStatusCode(), false);
            } else {
                commonLib.pass("Downstream API account details working Fine and response is: " + response.getBody().prettyPrint());
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CALLING_DOWNSTREAM_API) + " -account details " + e.getMessage(), false);
        }
    }

    /**
     * This method is used to call account payment api "/api/enterprise-service/v1/accounts/lines"
     *
     * @param accountLineRequest The Account Line Request
     * @return The Result
     */
    public AccountLinesResponse accountLinesResponse(AccountLineRequest accountLineRequest) {
        AccountLinesResponse result = null;
        try {
            commonPostMethod(constants.getValue(API_ENTERPRISE_SERVICE_BASE_URL) + ESBURIConstants.POSTPAID_ACCOUNTS_LINE, accountLineRequest);
            result = response.as(AccountLinesResponse.class);
        } catch (Exception e) {
            commonLib.fail("Exception in method - accountLineResponse " + e.getMessage(), false);
        }
        return result;
    }

}