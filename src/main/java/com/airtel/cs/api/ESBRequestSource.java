package com.airtel.cs.api;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.model.request.GenericRequest;
import com.airtel.cs.model.request.OfferDetailRequest;
import com.airtel.cs.model.request.UsageHistoryMenuRequest;
import com.airtel.cs.model.request.UsageHistoryRequest;
import com.airtel.cs.model.request.LoanRequest;
import com.airtel.cs.model.request.StatementRequest;
import com.airtel.cs.model.request.PaymentRequest;
import com.airtel.cs.model.request.AccountDetailRequest;
import com.airtel.cs.model.response.CreditLimitResponse;
import com.airtel.cs.model.response.PostpaidBillDetailsResponse;
import com.airtel.cs.model.response.PlanPackESBResponse;
import com.airtel.cs.model.response.InvoiceHistoryResponse;
import com.airtel.cs.model.response.PaymentResponse;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.postpaid.AccountStatementResponse;
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

  /**
   * This Method will hit the ESB APIs related to profile api
   *
   * @param msisdn The msisdn
   */
  public void callprofileESBAPI(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - GSM KYC request");
      queryParam.put("msisdn", msisdn);
      queryParam.put("walletType", "Main");
      commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - GSM KYC request" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API GSM KYC request working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(constants.getValue("downstream.api.calling") + " - self care user details");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.self.care.user.details.api.url") + ESBURIConstants.SELF_CARE_USER_DETAILS,
              queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - self care user details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API self care user details working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(constants.getValue("downstream.api.calling") + " - Device info");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.DEVICE_INFO, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - Device info" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Device info working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib
              .fail(constants.getValue("downstream.api.error") + " - Device info/self care user details/GSM KYC request " + exp.getMessage(),
                      false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to am service profile
   *
   * @param msisdn The msisdn
   */
  public void callAmServiceProfileESBAPI(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - KYC request");
      queryParam.put("msisdn", msisdn);
      queryParam.put("walletType", "Main");
      commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - KYC request" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API KYC request working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(constants.getValue("downstream.api.error") + " - recharge history/customer profile " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the  ESB APIs related to gsm kyc
   *
   * @param msisdn The msisdn
   */
  public void callGsmKycESBAPI(String msisdn) {
    try {
      callCustomerProfileV2(msisdn);

      commonLib.info(constants.getValue("downstream.api.calling") + " - gsm kyc");
      JSONObject json = new JSONObject();
      json.put("clientId", constants.getValue("kyc.client.id"));
      json.put("clientSecret", constants.getValue("kyc.client.secret"));
      commonPostMethod(constants.getValue("api.kyc.auth.token.url") + ESBURIConstants.TOKEN, json);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " -  gsm kyc " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API gsm kyc working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(constants.getValue("downstream.api.error") + " - recharge history/customer profile " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to customer profile V2
   *
   * @param msisdn The msisdn
   */
  public void callCustomerProfileV2(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - customer profile V2");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.CUSTOMER_PROFILLE, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - customer profile V2" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API customer profile V2 working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -customer profile V2 " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to account plans
   *
   * @param msisdn The msisdn
   */
  public void callAccoountPlanESBAPI(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - query balance ");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.QUERY_BALANCE, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - query balance " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API query balance working with data " + response.getBody().prettyPrint());
      }
      callRechargeHistory(msisdn, Timestamp.valueOf(LocalDateTime.now()).toInstant().toEpochMilli(),
              Timestamp.valueOf(LocalDateTime.now().minusDays(60).with(LocalTime.of(0, 0, 0))).toInstant().toEpochMilli());

    } catch (Exception exp) {
      commonLib.fail(constants.getValue("downstream.api.error") + " - query balance/recharge history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to recharge history
   *
   * @param msisdn The msisdn
   * @param endDate
   * @param startDate
   */
  public void callRechargeHistory(String msisdn, Long endDate, Long startDate) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - recharge history ");
      queryParam.put("msisdn", msisdn);
      queryParam.put("endDate", endDate);
      queryParam.put("startDate", startDate);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.RECHARGE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - recharge history " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API recharge history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -recharge history " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher details
   *
   * @param voucherId
   */
  public void callVoucherDetails(String voucherId) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - voucher details ");
      if (StringUtils.isNotBlank(voucherId)) {
        queryParam.put("serial_number", voucherId);
        commonGetMethodWithQueryParam(constants.getValue("voucher.service.base.url") + ESBURIConstants.VOUCHER_DETAIL, queryParam);
      }
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - voucher details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API voucher details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -voucher details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher refil barred
   *
   * @param msisdn
   */
  public void callVoucherRefilBarred(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - voucher refil barred ");
      commonPostMethod(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.VOUCHER_REFILL_BARRED,
              new GenericRequest(msisdn));
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - voucher refil barred " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API voucher refil barred working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -voucher refil barred " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher refil barred
   *
   * @param msisdn
   */
  public void callRingtoneDetailsTest(String msisdn, String searchText) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " -top twenty ringtone");
      commonGetMethod(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.TOP_TWENTY_RINGBACK_TONE);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " -top twenty ringtone" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API top twenty ringtone working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(constants.getValue("downstream.api.calling") + " -search name tune");
      queryParam.put("msisdn", msisdn);
      queryParam.put("query", searchText);
      commonGetMethodWithQueryParam(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.SEARCH_NAME_TUNE, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - search name tune" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API search name tune working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(constants.getValue("downstream.api.calling") + " -Generic search api");
      queryParam.put("msisdn", msisdn);
      queryParam.put("query", searchText);
      commonGetMethodWithQueryParam(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.GENERIC_SEARCH_API, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - Generic search api" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Generic search api working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(
              constants.getValue("downstream.api.error") + " - top twenty ringtone/search name tune/Generic search api " + exp.getMessage(),
              false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to ring back tone list
   *
   * @param msisdn
   */
  public void callActiveRingTone(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - ring back tone list ");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.RING_BACK_TONE_LIST, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - ring back tone list " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API ring back tone list working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -ring back tone list " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to accumulatorAPI
   *
   * @param msisdn
   */
  public void callAccumulatorAPI(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - accumulatorAPI ");
      commonPostMethod(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.GET_ACCUMULATORS, new GenericRequest(msisdn));
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - accumulatorAPI " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API accumulatorAPI working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -accumulatorAPI " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to HLR DETAILS
   *
   * @param msisdn
   */
  public void callHLRFetchDetails(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - HLR DETAILS ");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("hlr.services.profile.url") + ESBURIConstants.HLR_FETCH_DETAILS, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - HLR DETAILS " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API accumulatorAPI working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -HLR DETAILS " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to available tariff plans
   *
   * @param genericRequest
   */
  public void callAvailableTarrifPlan(GenericRequest genericRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - available tariff plans ");
      commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_AVAILABLE_PLANS, genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - available tariff plans " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API available tariff plans working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -available tariff plans " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to current tariff plans
   *
   * @param genericRequest
   */
  public void callCurrentTarrifPlan(GenericRequest genericRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling")+ " - current tariff plans ");
      commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_CURRENT_PLAN, genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - current tariff plans " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API current tariff plans working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -current tariff plans " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to offer details
   *
   * @param offerDetailRequest
   */
  public void callOfferDetailsAPI(OfferDetailRequest offerDetailRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - offer details ");
      commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.OFFER_DETAILS, offerDetailRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - offer details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API offer details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -offer details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to friends and family details
   *
   * @param genericRequest
   */
  public void callFriensFamilyAPI(GenericRequest genericRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - friends and family details ");
      commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.FRIENDS_FAMILY, genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - friends and family details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API friends and family details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -friends and family details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to postpaid account info
   *
   * @param msisdn
   */
  public void callPostpaidAccountInformation(String msisdn) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " -get credit limit");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.GET_CREDIT_LIMIT, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " -get credit limit" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API get credit limit working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(constants.getValue("downstream.api.calling") + " -invoice history");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.INVOICE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - invoice history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API invoice history working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(constants.getValue("downstream.api.calling") + " -postpaid bill details");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.POSTPAID_BILL_DETAILS,
              queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - postpaid bill details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API postpaid bill details with data " + response.getBody().prettyPrint());
      }

      callCustomerProfileV2(msisdn);
    } catch (Exception exp) {
      commonLib
              .fail(constants.getValue("downstream.api.error") + " - get credit limit/invoice history/postpaid bill details " + exp.getMessage(),
                      false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to Usage history
   *
   * @param usageHistoryMenuRequest
   */
  public void callUsageHistory(UsageHistoryMenuRequest usageHistoryMenuRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " -Usage history");
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("msisdn", usageHistoryMenuRequest.getMsisdn());
      queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
      if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals("FREE"))) {
        queryParam.put("cdrType", "BOTH");
      }
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.USAGE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " -Usage history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Usage history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(constants.getValue("downstream.api.error") + " - Usage history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to Usage history
   *
   * @param usageHistoryRequest
   */
  public void callUsageHistory(UsageHistoryRequest usageHistoryRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " -Usage history");
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("msisdn", usageHistoryRequest.getMsisdn());
      queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
      queryParam.put("cdrType", "PAID");
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.USAGE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " -Usage history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Usage history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(constants.getValue("downstream.api.error") + " - Usage history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to vendor details
   *
   */
  public void callVendors() {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " -vendor details");
      commonGetMethod(constants.getValue("vas.service.loan.base.url") + ESBURIConstants.VENDORS);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " -vendor details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API vendor details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(constants.getValue("downstream.api.error") + " - vendor details " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to loan summary
   *
   * @param loanRequest
   */
  public void callLoanSummary(LoanRequest loanRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - loan summary ");
      commonPostMethod(constants.getValue("vas.service.loan.base.url") + ESBURIConstants.LOAN_SUMMARY, loanRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - loan summary " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API loan summary working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -loan summary " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to loan details
   *
   * @param loanRequest
   */
  public void callLoanDetails(LoanRequest loanRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - loan details ");
      commonPostMethod(constants.getValue("vas.service.loan.base.url") + ESBURIConstants.LOAN_DETAILS, loanRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.error") + " - loan details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API loan details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.error") + " -loan details " + e.getMessage(), false);
    }
  }


  /**
   * This method is used to call customer profile v2 api "/api/subscriber-profile/v2/customer-profile"
   * @param msisdn
   * @return
   */
  public CustomerProfileResponse customerProfileResponse(String msisdn){
    CustomerProfileResponse result = null;
    try{
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url")+ESBURIConstants.CUSTOMER_PROFILE_V2, queryParam);
      result = response.as(CustomerProfileResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - customerProfileResponse " + e.getMessage(), false);
    }
    return result;
  }

  /**
   * This method is used to call invoice history v2 api "/api/subscriber-profile/v1/invoice-history"
   * @param msisdn
   * @return
   */
  public InvoiceHistoryResponse invoiceHistoryResponse(String msisdn){
    InvoiceHistoryResponse result = null;
    try{
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url")+ESBURIConstants.INVOICE_HISTORY_V1, queryParam);
      result = response.as(InvoiceHistoryResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - invoiceHistoryResponse " + e.getMessage(), false);
    }
    return result;
  }


  /**
   * This method is used to call invoice history v2 api "/api/subscriber-profile/v1/postpaid-bill-details"
   * @param msisdn
   * @return
   */
  public PostpaidBillDetailsResponse postpaidBillDetailsResponse(String msisdn){
    PostpaidBillDetailsResponse result = null;
    try{
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url")+ESBURIConstants.POSTPAID_BILL_DETAIL_V1, queryParam);
      result = response.as(PostpaidBillDetailsResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - postpaidBillDetailsResponse " + e.getMessage(), false);
    }
    return result;
  }




  /**
   * This method is used to call get usage api "/api/subscriber-profile/v1/get-usage"
   * @param msisdn
   * @return
   */
  public PlanPackESBResponse planPackResponse(String msisdn){
    PlanPackESBResponse result = null;
    try{
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url")+ESBURIConstants.GET_USAGE, queryParam);
      result = response.as(PlanPackESBResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - planPackResponse " + e.getMessage(), false);
    }
    return result;
  }


  /**
   * This method is used to call account payment api "/api/enterprise-service/v1/accounts/payments"
   * @param paymentRequest
   * @return
   */
  public PaymentResponse paymentResponse(PaymentRequest paymentRequest){
    PaymentResponse result = null;
    try{
      commonPostMethod(constants.getValue("api.enterprise.service.base.url")+ESBURIConstants.ACCOUNT_PAYMENT, paymentRequest);
      result = response.as(PaymentResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - paymentResponse " + e.getMessage(), false);
    }
    return result;
  }

  /**
   * This method is used to call account payment api "/api/enterprise-service/v1/accounts/statement"
   * @param paymentRequest
   * @return
   */
  public AccountStatementResponse accountStatementResponse(StatementRequest paymentRequest){
    AccountStatementResponse result = null;
    try{
      commonPostMethod(constants.getValue("api.enterprise.service.base.url")+ESBURIConstants.ACCOUNT_STATEMENT, paymentRequest);
      result = response.as(AccountStatementResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - paymentResponse " + e.getMessage(), false);
    }
    return result;
  }


  /**
   * This method is used to call credit limit api api "/api/subscriber-profile/v1/get-credit-limit"
   * @param msisdn
   * @return
   */
  public CreditLimitResponse creditLimitResponse(String msisdn){
    CreditLimitResponse result = null;
    try{
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url")+ESBURIConstants.CREDIT_LIMIT, queryParam);
      result = response.as(CreditLimitResponse.class);
    } catch (Exception e) {
      commonLib.fail("Exception in method - creditLimitResponse " + e.getMessage(), false);
    }
    return result;
  }


  /**
   * This Method will hit the ESB APIs related to postpaid account info details
   *
   * @param accountDetailRequest
   */
  public void callPostpaidAccountInfoDetails(AccountDetailRequest accountDetailRequest) {
    try {
      commonLib.info(constants.getValue("downstream.api.calling") + " - account details ");
      commonPostMethod(constants.getValue("postpaid.enterprise.serice.base.url") + ESBURIConstants.POSTPAID_ACCOUNT_DETAILS, accountDetailRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(constants.getValue("downstream.api.calling") + " - account details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API account details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(constants.getValue("downstream.api.calling") + " -account details " + e.getMessage(), false);
    }
  }
}