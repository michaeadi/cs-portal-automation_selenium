package com.airtel.cs.api;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.model.cs.request.AccountDetailRequest;
import com.airtel.cs.model.cs.request.AccountLineRequest;
import com.airtel.cs.model.cs.request.AccountLinesRequest;
import com.airtel.cs.model.cs.request.EnterpriseAccountRequest;
import com.airtel.cs.model.cs.request.GenericRequest;
import com.airtel.cs.model.cs.request.InvoiceDetailRequest;
import com.airtel.cs.model.cs.request.LoanRequest;
import com.airtel.cs.model.cs.request.OSCRefillRequest;
import com.airtel.cs.model.cs.request.OfferDetailRequest;
import com.airtel.cs.model.cs.request.PaymentHistoryESBRequest;
import com.airtel.cs.model.cs.request.PaymentRequest;
import com.airtel.cs.model.cs.request.StatementRequest;
import com.airtel.cs.model.cs.request.UsageHistoryMenuRequest;
import com.airtel.cs.model.cs.request.UsageHistoryRequest;
import com.airtel.cs.model.cs.request.UsageRequestV3DTO;
import com.airtel.cs.model.cs.request.enterprise.AccountLevelInformationRequest;
import com.airtel.cs.model.cs.request.layout.FieldsConfigDTO;
import com.airtel.cs.model.cs.request.vas.ActiveVasRequest;
import com.airtel.cs.model.cs.response.CreditLimitResponse;
import com.airtel.cs.model.cs.response.InvoiceHistoryResponse;
import com.airtel.cs.model.cs.response.PaymentResponse;
import com.airtel.cs.model.cs.response.PlanPackESBResponse;
import com.airtel.cs.model.cs.response.PostpaidBillDetailsResponse;
import com.airtel.cs.model.cs.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.cs.response.enterprise.AccountLevelInformationResponse;
import com.airtel.cs.model.cs.response.postpaid.AccountStatementResponse;
import com.airtel.cs.model.cs.response.postpaid.enterprise.AccountLinesResponse;
import com.airtel.cs.model.cs.response.serviceclassrateplan.ServiceClassRatePlanResponseDTO;
import com.airtel.cs.model.cs.response.voucher.VoucherDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ESBRequestSource extends RestCommonUtils {

    private static final Map<String, Object> queryParam = new HashMap<>();
    private static final String DOWNSTREAM_API_CALLING = "downstream.api.calling";
    private static final String DOWNSTREAM_API_ERROR = "downstream.api.error";
    private static final String MSISDN = "msisdn";
    private static final String ACCOUNT_NO = "accountNo";
    private static final String END_DATE = "endDate";
    private static final String START_DATE = "startDate";
    private static final String USAGE_HISTORY = " -Usage history ";
    private static final String GSM_KYC = " - gsm kyc";
    private static final String KYC_REQUEST = " - KYC request";
    private static final String SERVICE_CLASS_RATE_PLAN = " - service class rate plan ";
    private static final String QUERY_BALANCE = " - query balance ";
    private static final String RECHARGE_HISTORY = " - recharge history ";
    private static final String VOUCHER_DETAILS = " - voucher details ";
    private static final String OSC_REFILL = " - osc refill ";
    private static final String VOUCHER_REFILL_BARRED = " -voucher refill barred ";
    private static final String TOP_TWENTY_RINGTONE = " -top twenty ringtone ";
    private static final String SEARCH_NAME_TUNE = " -search name tune ";
    private static final String GENERIC_SEARCH_API = " -Generic search api ";
    private static final String RING_BACK_TONE_LIST = " - ring back tone list ";
    private static final String ACCUMULATOR_API = " - accumulatorAPI ";
    private static final String HLR_DETAILS = " - HLR DETAILS ";
    private static final String HLR_ORDER_HISTORY = " - HLR ORDER HISTORY ";
    private static final String OFFER_DETAILS = " - offer details ";
    private static final String FRIENDS_AND_FAMILY_DETAILS = " - friends and family details ";
    private static final String GET_CREDIT_LIMIT = " -get credit limit ";
    private static final String INVOICE_HISTORY = " -invoice history ";
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
    private static final String GSM_KYC_REQUEST = " - GSM KYC request ";
    private static final String SELF_CARE_USER_DETAILS = " - self care user details";
    private static final String DEVICE_INFO = " - Device info";
    private static final String EXCEPTION_IN_METHOD = "Exception in method -";
    public static final String FREE = "FREE";
    public static final String BOTH = "BOTH";
    public static final String USAGE_HISTORY_V3 = " - Usage history V3 ";
    public static final String ENTERPRISE_ACCOUNT_NUMBER = "enterpriseAccountNumber";
    public static final String CORPORATE_CUSTOMER_NUMBER = "corporateCustomerNumber";
    public static final String ENTERPRISE_PAYMENT_HISTORY = "-enterprise payment history";
    public static final String GETTING_SERVICE_CLASS_FROM_ESB = "getting Service Class from ESB";
    public static final String UNABLE_TO_FETCH_SERVICE_CLASS_FROM_ESB = "unable to fetch service class from ESB";
    private static final String INGRESS_DOWNSTREAM_BASE_URL_1 = constants.getValue("ingress.downstream.base.url1");
    public static final String INGRESS_DOWNSTREAM_BASE_URL_2 = "." + OPCO.toLowerCase().substring(0, 2) + "." + evnName.toLowerCase();
    private static final String INGRESS_DOWNSTREAM_BASE_URL_3 = constants.getValue("ingress.downstream.base.url2");
    public static final String INGRESS_DOWNSTREAM_BASE_URL = INGRESS_DOWNSTREAM_BASE_URL_1 + INGRESS_DOWNSTREAM_BASE_URL_2 + INGRESS_DOWNSTREAM_BASE_URL_3;
    private static final String TO_DATE = "to_date";
    private static final String FROM_DATE = "from_date";
    private static final String SOURCE_ID_TYPE = "source_id_type";
    private static final String SOURCE_ID_NUMBER = "source_id_number";
    private static final String NOTIFICATION_TYPE = "notificationType";
    private static final String RECEIVER = "receiver";
    private static final String SMS = "sms";
    private static final String ID_NUMBER = "id_number";
    private static final String ID_TYPE = "id_type";


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
            commonGetMethodWithQueryParam(ESBURIConstants.GSM_KYC_REQUEST, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API GSM KYC request not working with data  ", "Downstream API GSM KYC request working Fine and response is ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + SELF_CARE_USER_DETAILS, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.SELF_CARE_USER_DETAILS, queryParam, map, constants.getValue(ESBURIConstants.GSM_SELFCARE_USER_DETAILS_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API self care user details not working with data ", "Downstream API self care user details working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + DEVICE_INFO, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.DEVICE_INFO, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Device info not working with data \"", "Downstream API Device info working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + DEVICE_INFO + SLASH + SELF_CARE_USER_DETAILS + SLASH + GSM_KYC_REQUEST + exp.getMessage(),
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
            commonGetMethod(ESBURIConstants.AM_PROFILE+msisdn, map, constants.getValue(ESBURIConstants.AM_PROFILE_SERVICE_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API KYC request not working with data ", "Downstream API KYC request working with data ");
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
            commonPostMethod(ESBURIConstants.TOKEN, map, json, constants.getValue(ESBURIConstants.KYC_AUTH_TOKEN_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API gsm kyc not working with data ", "Downstream API gsm kyc working with data ");
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("kyc.profile"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.CUSTOMER_PROFILLE, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API customer profile V2  not working with data ", "Downstream API customer profile V2 working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("customer.profile.v2") + e.getMessage(), false);
        }
    }

    /**
     * Call service class rate plan service class rate plan response dto.
     *
     * @param genericRequest the generic request
     */
    public void callServiceClassRatePlan(GenericRequest genericRequest) {
        ServiceClassRatePlanResponseDTO serviceClassRatePlanResponseDTO = null;
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("service.class.rate.plan"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.SERVICE_CLASS_RATE_PLAN, map, genericRequest, constants.getValue(ESBURIConstants.SUBSCRIBER_PRODUCT_BASE_URL));
            serviceClassRatePlanResponseDTO = response.as(ServiceClassRatePlanResponseDTO.class);
            checkDownstreamAPI(response.getStatusCode(), constants.getValue("service.class.rate.plan"), "Downstream API service class rate plan working with data ");
            if (response.getStatusCode() == 200 && Objects.nonNull(serviceClassRatePlanResponseDTO) && Objects.nonNull(serviceClassRatePlanResponseDTO.getResponse())) {
                if (Boolean.parseBoolean(constants.getValue(ApplicationConstants.IS_SERVICE_CLS_ENABLED))) {
                    assertCheck.append(actions.assertEqualStringNotNull(serviceClassRatePlanResponseDTO.getResponse().getServiceClass(), GETTING_SERVICE_CLASS_FROM_ESB, UNABLE_TO_FETCH_SERVICE_CLASS_FROM_ESB, false));
                    assertCheck.append(actions.assertEqualStringNotNull(serviceClassRatePlanResponseDTO.getResponse().getRatePlanName(), "getting rate plan from ESB", "unable to fetch rate plan from ESB", false));
                } else {
                    if (Boolean.parseBoolean(constants.getValue("service.class.rate.plan")) && "NG".equalsIgnoreCase(OPCO)) {
                        assertCheck.append(actions.assertEqualStringNotNull(serviceClassRatePlanResponseDTO.getResponse().getServiceClass(), GETTING_SERVICE_CLASS_FROM_ESB, UNABLE_TO_FETCH_SERVICE_CLASS_FROM_ESB, false));
                        assertCheck.append(actions.assertEqualStringNotNull(serviceClassRatePlanResponseDTO.getResponse().getRatePlanName(), "getting rate plan from ESB", "unable to fetch rate plan from ESB", false));
                    } else {
                        assertCheck.append(actions.assertEqualStringNotNull(serviceClassRatePlanResponseDTO.getResponse().getServiceClass(), GETTING_SERVICE_CLASS_FROM_ESB, UNABLE_TO_FETCH_SERVICE_CLASS_FROM_ESB, false));
                    }
                }
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + SERVICE_CLASS_RATE_PLAN + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to account plans
     *
     * @param msisdn The msisdn
     */
    public void callAccountPlanESBAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("query.balance"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.QUERY_BALANCE, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API query balance not working with data ", "Downstream API query balance working with data ");
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("recharge.history"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put(END_DATE, endDate);
            queryParam.put(START_DATE, startDate);
            commonGetMethodWithQueryParam(ESBURIConstants.RECHARGE_HISTORY, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API recharge history not  working with data ", "Downstream API recharge history working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + RECHARGE_HISTORY + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API related to voucher details
     *
     * @param voucherId The voucher id
     */
    public VoucherDetail callVoucherDetails(String voucherId) {
        VoucherDetail result = null;
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("voucher.detail"), JavaColors.GREEN, false);
            queryParam.put("serial_number", voucherId);
            commonGetMethodWithQueryParam(ESBURIConstants.VOUCHER_DETAIL, queryParam, map, constants.getValue(ESBURIConstants.VOUCHER_SERVICE_BASE_URL));
            result = response.as(VoucherDetail.class);
            if (!result.getMessage().equalsIgnoreCase("Success"))
                checkDownstreamAPI(response.getStatusCode(), "Downstream API voucher details not working with data ", "Downstream API voucher details working with data ");

        } catch (NullPointerException e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + VOUCHER_DETAILS + e.getMessage(), false);
        }
        return result;
    }

    /**
     * Call osc refill.
     *
     * @param voucherNumber the voucher number
     */
    public void callOscRefill(String voucherNumber) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("osc.refill"), JavaColors.GREEN, false);
            OSCRefillRequest oscRefillRequest = new OSCRefillRequest();
            oscRefillRequest.setVoucherSerialNumber(voucherNumber);
            oscRefillRequest.setMsisdn(constants.getValue(ApplicationConstants.CUSTOMER_MSISDN));
            oscRefillRequest.setIsDamagedPinAvailable(false);
            commonPostMethod(ESBURIConstants.OSC_REFILL, map, oscRefillRequest, constants.getValue(ESBURIConstants.VOUCHER_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API osc refill not working with data", "Downstream API osc refill working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + OSC_REFILL + e.getMessage(), false);
        }
    }

    /**
     * Call webhook apis for autofill.
     *
     * @param fieldsConfigDTOS the fields config dtos
     * @param msisdn           the msisdn
     */
    public void callWebhookApisForAutofill(List<FieldsConfigDTO> fieldsConfigDTOS, String msisdn) {
        try {
            if (CollectionUtils.isNotEmpty(fieldsConfigDTOS)) {
                for (FieldsConfigDTO fieldsConfigDTO : fieldsConfigDTOS) {
                    commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + fieldsConfigDTO.getWebhookUrl(), JavaColors.GREEN, false);
                    if (fieldsConfigDTO.getMethodType().equalsIgnoreCase(ApplicationConstants.POST_METHOD)) {
                        GenericRequest genericRequest = new GenericRequest(msisdn);
                        commonPostMethod(fieldsConfigDTO.getWebhookUrl(), genericRequest);
                    } else if (fieldsConfigDTO.getMethodType().equalsIgnoreCase(ApplicationConstants.GET_METHOD)) {
                        queryParam.clear();
                        queryParam.put(MSISDN, msisdn);
                        commonGetMethodWithQueryParam(fieldsConfigDTO.getWebhookUrl(), queryParam);
                    }
                    checkDownstreamAPI(response.getStatusCode(), "Downstream Webhook API not working with data ", "Downstream Webhook API working with data ");
                }
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to voucher refil barred
     *
     * @param msisdn The msisdn
     */
    public void callVoucherRefilLBarred(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("voucher.refill"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.VOUCHER_REFILL_BARRED, map, new GenericRequest(msisdn), constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API voucher refill barred not working with data ", "Downstream API voucher refill barred working with data ");
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
            commonGetMethod(ESBURIConstants.TOP_TWENTY_RINGBACK_TONE, map, constants.getValue(ESBURIConstants.VAS_SERVICE_TUNE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API top twenty ringtone not working with data ", "Downstream API top twenty ringtone working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + SEARCH_NAME_TUNE, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put("query", searchText);
            commonGetMethodWithQueryParam(ESBURIConstants.SEARCH_NAME_TUNE, queryParam, map, constants.getValue(ESBURIConstants.VAS_SERVICE_TUNE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API search name tune not working with data ", "Downstream API search name tune working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + GENERIC_SEARCH_API, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            queryParam.put("query", searchText);
            commonGetMethodWithQueryParam(ESBURIConstants.GENERIC_SEARCH_API, queryParam, map, constants.getValue(ESBURIConstants.VAS_SERVICE_TUNE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Generic search api not working with data ", "Downstream API Generic search api working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + TOP_TWENTY_RINGTONE + SLASH + SEARCH_NAME_TUNE + SLASH + GENERIC_SEARCH_API + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to ring back tone list
     *
     * @param msisdn The msisdn
     */
    public void callActiveRingTone(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("active.ringtone"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.RING_BACK_TONE_LIST, queryParam, map, constants.getValue(ESBURIConstants.VAS_SERVICE_TUNE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API ring back tone list not working with data ", "Downstream API ring back tone list working with data ");
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
            commonPostMethod(ESBURIConstants.GET_ACCUMULATORS, map, new GenericRequest(msisdn), constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("hlr.fetch.history"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.HLR_FETCH_DETAILS, queryParam, map, constants.getValue(ESBURIConstants.HLR_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API accumulatorAPI not working with data ", "Downstream API accumulatorAPI working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + HLR_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * Call hlr order history.
     *
     * @param msisdn the msisdn
     */
    public void callHLROrderHistory(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("hlr.order.history.v1"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.HLR_ORDER_HISTORY, queryParam, map, constants.getValue(ESBURIConstants.HLR_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API HLR Order History not working with data ", "Downstream API HLR Order History working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + HLR_ORDER_HISTORY + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to available tariff plans
     *
     * @param genericRequest The generic request
     */
    public void callAvailableTariffPlan(GenericRequest genericRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("tarrif.available.plan"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.TARIFF_AVAILABLE_PLANS, map, genericRequest, constants.getValue(ESBURIConstants.BUNDLE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API available tariff plans not working with data ", "Downstream API available tariff plans working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("tarrif.available.plan") + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to current tariff plans
     *
     * @param genericRequest The generic request
     */
    public void callCurrentTarrifPlan(GenericRequest genericRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("tarrif.current.plan"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.TARIFF_CURRENT_PLAN, map, genericRequest, constants.getValue(ESBURIConstants.BUNDLE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API available tariff plans not working with data ", "Downstream API current tariff plans working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("tarrif.current.plan") + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to offer details
     *
     * @param offerDetailRequest The offer detail request
     */
    public void callOfferDetailsAPI(OfferDetailRequest offerDetailRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("my.plans"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.OFFER_DETAILS, map, offerDetailRequest, constants.getValue(ESBURIConstants.SUBSCRIBER_PRODUCT_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API offer details working with data ", "Downstream API offer details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + OFFER_DETAILS + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to friends and family details
     *
     * @param genericRequest The generic request
     */
    public void callFriendsFamilyAPI(GenericRequest genericRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("friends.and.family"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.FRIENDS_FAMILY, map, genericRequest, constants.getValue(ESBURIConstants.SUBSCRIBER_PRODUCT_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API friends and family details not working with data ", "Downstream API friends and family details working with data ");
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
            commonGetMethodWithQueryParam(ESBURIConstants.GET_CREDIT_LIMIT, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), GET_CREDIT_LIMIT, "Downstream API get credit limit working with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + INVOICE_HISTORY, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.INVOICE_HISTORY, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), INVOICE_HISTORY, "Downstream API invoice history working with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + POSTPAID_BILL_DETAILS, JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.POSTPAID_BILL_DETAILS,
                    queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), POSTPAID_BILL_DETAILS, "Downstream API postpaid bill details with data ");

            callCustomerProfileV2(msisdn);
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + GET_CREDIT_LIMIT + SLASH + INVOICE_HISTORY + SLASH + POSTPAID_BILL_DETAILS + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account info
     *
     * @param msisdn The msisdn
     */
    public void callingPlanAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("my.plans"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.MY_PLAN, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API get my-plan not working with data ", "Downstream API get my-plan working with data ");
        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + MY_PLAN + SLASH + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to my pack
     *
     * @param msisdn The msisdn
     */
    public void callingPackAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("my.packs"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.MY_PACK, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API get my-pack not  working with data ", "Downstream API get my-pack working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + MY_PACK + SLASH + exp.getMessage(),
                    false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to get usage
     *
     * @param msisdn The msisdn
     */
    public void callingGetUsageAPI(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("v1.get.usage"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.GET_USAGE, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API get my-pack not working with data ", "Downstream API get my-pack working with data ");
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.payment"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACCOUNT_PAYMENT, map, paymentRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API account payments not working with data ", "Downstream API account payments with data ");

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_LINES, JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.POSTPAID_ACCOUNTS_LINE, map, customerAccountNumber, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API account lines not working with data ", "Downstream API account lines working with data ");

        } catch (Exception exp) {
            commonLib
                    .fail(constants.getValue(DOWNSTREAM_API_ERROR) + ACCOUNT_PAYMENTS + SLASH + ACCOUNT_LINES + exp.getMessage(),
                            false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to postpaid account statement
     *
     * @param accountNumber The account number
     */
    public void callingAccountStatementAPI(String accountNumber) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.statement"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACCOUNT_STATEMENT, map, accountNumber, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API account statement with data", "Downstream API account statement with data ");

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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("v3.usage.history"), JavaColors.GREEN, false);
            queryParam.put(END_DATE, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            queryParam.put(MSISDN, usageHistoryMenuRequest.getMsisdn());
            queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
            queryParam.put(START_DATE, UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals("FREE"))) {
                queryParam.put("cdrType", "BOTH");
            }
            commonGetMethodWithQueryParam(ESBURIConstants.USAGE_HISTORY, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Usage history not working with data ", "Downstream API Usage history working with data ");
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("v3.usage.history"), JavaColors.GREEN, false);
            UsageRequestV3DTO v3RequestDTO = new UsageRequestV3DTO();
            v3RequestDTO.setEndDate(UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            v3RequestDTO.setMsisdn(usageHistoryMenuRequest.getMsisdn());
            v3RequestDTO.setStartDate(UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            v3RequestDTO.setNumberOfRecords(20);
            v3RequestDTO.setOffset(0);
            if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals(FREE))) {
                v3RequestDTO.setCdrType(BOTH);
            }
            commonPostMethod(ESBURIConstants.V3_USAGE_HISTORY, map, v3RequestDTO, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Usage history V3 not working with data", "Downstream API Usage history V3 working with data");
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("usage.history"), JavaColors.GREEN, false);
            queryParam.put(END_DATE, UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
            queryParam.put(MSISDN, usageHistoryRequest.getMsisdn());
            queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
            queryParam.put(START_DATE, UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
            queryParam.put("cdrType", "PAID");
            commonGetMethodWithQueryParam(ESBURIConstants.USAGE_HISTORY, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Usage history not working with data ", "Downstream API Usage history working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + USAGE_HISTORY + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to vendor details
     */
    public void callVendors() {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("v1.vendors"), JavaColors.GREEN, false);
            commonGetMethod(ESBURIConstants.VENDORS, map, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API vendor details not working with data ", "Downstream API vendor details working with data ");
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
        String endPoint = null;
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("loan.summary"), JavaColors.GREEN, false);
            if ((constants.getValue(ApplicationConstants.ESB_LOAN_API_VERSION).equalsIgnoreCase("v2")))
                endPoint = ESBURIConstants.LOAN_SUMMARY_V2;
            else
                endPoint = ESBURIConstants.LOAN_SUMMARY;
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + endPoint, JavaColors.GREEN, false);
            commonPostMethod(endPoint, map, loanRequest, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API loan summary not working with data", "Downstream API loan summary working with data");

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
        String endPoint = null;
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("loan.details"), JavaColors.GREEN, false);
            if ((constants.getValue(ApplicationConstants.ESB_LOAN_API_VERSION).equalsIgnoreCase("v2")))
                endPoint = ESBURIConstants.LOAN_DETAILS_V2;
            else
                endPoint = ESBURIConstants.LOAN_DETAILS;
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + endPoint, JavaColors.GREEN, false);
            commonPostMethod(endPoint, map, loanRequest, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API loan details not working with data ", "Downstream API loan details working with data ");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + LOAN_DETAILS + e.getMessage(), false);
        }
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("customer.profile.v2"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.CUSTOMER_PROFILE_V2, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("invoice.history"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.INVOICE_HISTORY_V1, queryParam, map, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("postpaid.bill.details"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.POSTPAID_BILL_DETAIL_V1, queryParam, map, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("v1.get.usage"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.GET_USAGE, queryParam, map, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.payment.history"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACCOUNT_PAYMENT, map, paymentRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.statement"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACCOUNT_STATEMENT, map, paymentRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            result = response.as(AccountStatementResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "account statement " + e.getMessage(), false);
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("credit.limit"), JavaColors.GREEN, false);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.CREDIT_LIMIT, queryParam, map, constants.getValue(ESBURIConstants.SUBSCRIBER_PROFILE_BASE_URL));
            result = response.as(CreditLimitResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "creditLimitResponse" + e.getMessage(), false);
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.statement"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.POSTPAID_ACCOUNT_DETAILS, map, accountDetailRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API account details not working with data", "Downstream API account details working with data");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_CALLING) + "account lines" + e.getMessage(), false);
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
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.lines"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.POSTPAID_ACCOUNTS_LINE, map, accountLineRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            result = response.as(AccountLinesResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "accountLineResponse " + e.getMessage(), false);
        }
        return result;
    }

    /**
     * This method is used to call downstream api for hbb linked account details
     *
     * @param channel the channel for ex. PORTAL
     * @param msisdn  the msisdn
     */
    public void hbbLinkedAccount(String channel, String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("linked.account.orchestrator"), JavaColors.GREEN, false);
            queryParam.put("channel", channel);
            queryParam.put(MSISDN, msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.HBB_LINKED_ACCOUNT_DETAILS, queryParam, map, constants.getValue(ESBURIConstants.HBB_LINKED_ACCOUNT_ORCHESTRATOR_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API for hbb linked accounts orchestrator working fine with data", "Downstream API for hbb linked accounts orchestrator working fine with data");
        } catch (Exception e) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("linked.account.orchestrator") + e.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to enterprise search account
     *
     * @param type   the number type
     * @param number the number
     */
    public void callEnterPriseSearchAccount(String type, String number) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.search"), JavaColors.GREEN, false);
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
            commonPostMethod(ESBURIConstants.ENTERPRISE_SEARCH_ACCOUNT, map, enterpriseAccountRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Enterprise account not working with data", "Downstream API Enterprise account working with data");

        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("enterprise.account.search") + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to enterprise postpaid account info
     *
     * @param accountNo      The msisdn
     * @param paymentRequest the payment request
     */
    public void callEnterPrisePostpaidAccountInformation(String accountNo, PaymentRequest paymentRequest) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("invoice.history"), JavaColors.GREEN, false);
            queryParam.put(ACCOUNT_NO, accountNo);
            commonPostMethod(ESBURIConstants.ENTERPRISE_INVOICE_HISTORY, map, new InvoiceDetailRequest(accountNo), constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API invoice history not working with data ", "Downstream API invoice history working with data ");
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + ACCOUNT_PAYMENTS, JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACCOUNT_PAYMENT, map, paymentRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API account payments not working with data", "Downstream API account payments wokring with data");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + INVOICE_HISTORY + SLASH + ACCOUNT_PAYMENTS + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream APIs related to enterprise linked services details
     *
     * @param accountLinesRequest the account lines request
     */
    public void callEnterPrisePostpaidAccountInformation(AccountLinesRequest accountLinesRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.account.lines"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ENTERPRISE_ACCOUNT_LINES, map, accountLinesRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API enterprise account lines not working with data", "Downstream API enterprise account lines working with data");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("enterprise.account.lines") + exp.getMessage(), false);
        }
    }


    /**
     * This Method will hit the Downstream API related to enterprise payment history detail
     *
     * @param paymentHistoryESBRequest the payment history esb request
     */
    public void callEnterPrisePaymentHistory(PaymentHistoryESBRequest paymentHistoryESBRequest) {
        try {

            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("enterprise.payment.history"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ENTERPRISE_PAYMENT_HISTORY, map, paymentHistoryESBRequest, constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API enterprise payment history not working with data", "Downstream API enterprise payment history working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + ENTERPRISE_PAYMENT_HISTORY + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API of Active Vas
     *
     * @param activeVasESBRequest the active vas esb request
     */
    public void callActiveVAS(ActiveVasRequest activeVasESBRequest) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("active.vas"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACTIVE_VAS, map, activeVasESBRequest, constants.getValue(ESBURIConstants.VAS_SERVICE_LOAN_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API Active Vas not working with data", "Downstream API Active Vas working with data");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("active.vas") + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API of Tcp Limits
     */
    public void callTcpLimits(String tcpId) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("tcp.limits"), JavaColors.GREEN, false);
            commonGetMethod(ESBURIConstants.TCP_LIMITS + SLASH + tcpId, map, constants.getValue(ESBURIConstants.AM_PROFILE_SERVICE_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API of TCP Limits not working with data ", "Downstream API of TCP Limits working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("tcp.limits") + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API of SMS Logs
     */
    public void callSmsLogs(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("sms.trails"), JavaColors.GREEN, false);
            queryParam.put(TO_DATE, UtilsMethods.getDateInUtc(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime(), END_DATE));
            queryParam.put(FROM_DATE, UtilsMethods.getDateInUtc(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime(), START_DATE));
            commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.SMS_LOGS1 + msisdn + ESBURIConstants.SMS_LOGS2), queryParam, map, constants.getValue(ESBURIConstants.AM_PROFILE_SERVICE_PROFILE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API of SMS Logs not working with data ", "Downstream API of SMS Logs working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("sms.trails") + exp.getMessage(), false);
        }

    }

    /**
     * This Method will hit the Downstream API of CLM Details
     */
    public void callCLMDetails(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("clm.details"), JavaColors.GREEN, false);
            queryParam.put("msisdn", msisdn);
            commonGetMethodWithQueryParam(ESBURIConstants.CLM_DETAILS, queryParam, map, constants.getValue(ESBURIConstants.COLLECTIVE_BASE_URL));
            checkDownstreamAPI(response.getStatusCode(), "Downstream API of CLM Details not working with data ", "Downstream API of CLM Details working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("clm.details") + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API of Psb Transaction History
     */
    public void callTransactionHistory(String nubanId, String type) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("transaction.history"), JavaColors.GREEN, false);
            queryParam.put(SOURCE_ID_TYPE, type);
            queryParam.put(SOURCE_ID_NUMBER, nubanId);
            commonGetMethodWithQueryParam(INGRESS_DOWNSTREAM_BASE_URL + ESBURIConstants.PSB_TRANSCATION_HISTORY, queryParam);
            checkDownstreamAPI(response.getStatusCode(), "Downstream API of Psb Transaction History not working with data ", "Downstream API of Psb Transaction History working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("transaction.history") + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API of Psb Linked Accounts and Walllets
     */
    public void callBalanceAPI(String nubanId, String msisdn, String type) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("get.balance"), JavaColors.GREEN, false);
            queryParam.put(ID_NUMBER, nubanId);
            queryParam.put(ID_TYPE, type);
            commonGetMethodWithQueryParam(INGRESS_DOWNSTREAM_BASE_URL + msisdn + ESBURIConstants.FETCH_BALANCE1 + msisdn + ESBURIConstants.FETCH_BALANCE2, queryParam);
            checkDownstreamAPI(response.getStatusCode(), "Downstream API of Psb Accounts and Walllets  not working with data ", "Downstream API of Psb Accounts and Walllets working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("get.balance") + exp.getMessage(), false);
        }
    }

    /**
     * This Method will hit the Downstream API of Psb Sms Summary
     */
    public void callSmsSummary(String msisdn) {
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("sms.summary"), JavaColors.GREEN, false);
            queryParam.put(NOTIFICATION_TYPE, SMS);
            queryParam.put(RECEIVER, msisdn);
            commonGetMethodWithQueryParam(INGRESS_DOWNSTREAM_BASE_URL + ESBURIConstants.PSB_SMS_SUMMMARY, queryParam);
            checkDownstreamAPI(response.getStatusCode(), "Downstream API of Psb Sms Summary not working with data ", "Downstream API of Psb Sms Summary working with data ");
        } catch (Exception exp) {
            commonLib.fail(constants.getValue(DOWNSTREAM_API_ERROR) + constants.getValue("sms.summary") + exp.getMessage(), false);
        }
    }

    /**
     * This method is used to hit downstream API Account Level Information:  /api/enterprise-service/v1/accounts"
     *
     * @param custMobileNo msisdn
     * @return The result
     */
    public AccountLevelInformationResponse callAccountLevelInfo(String custMobileNo) {
        AccountLevelInformationResponse result = null;
        try {
            commonLib.infoColored(constants.getValue(DOWNSTREAM_API_CALLING) + constants.getValue("v1.account.level.information"), JavaColors.GREEN, false);
            commonPostMethod(ESBURIConstants.ACCOUNT_LEVEL_INFO, map, new AccountLevelInformationRequest(custMobileNo, 10, 0), constants.getValue(ESBURIConstants.ENTERPRISE_SERVICE_BASE_URL));
            result = response.as(AccountLevelInformationResponse.class);
        } catch (Exception e) {
            commonLib.fail(EXCEPTION_IN_METHOD + "callAccountLevelInfo " + e.getMessage(), false);
        }
        return result;
    }
}
