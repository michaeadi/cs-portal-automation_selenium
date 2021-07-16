package com.airtel.cs.api;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.ESBURIConstants;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.pojo.request.*;
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
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - GSM KYC request");
      queryParam.put("msisdn", msisdn);
      queryParam.put("walletType", "Main");
      commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - GSM KYC request" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API GSM KYC request working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - self care user details");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.self.care.user.details.api.url") + ESBURIConstants.SELF_CARE_USER_DETAILS,
          queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - self care user details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API self care user details working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - Device info");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.DEVICE_INFO, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - Device info" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Device info working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib
          .fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - Device info/self care user details/GSM KYC request " + exp.getMessage(),
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
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - KYC request");
      queryParam.put("msisdn", msisdn);
      queryParam.put("walletType", "Main");
      commonGetMethodWithQueryParam(constants.getValue("am.profile.service.base.url") + ESBURIConstants.GSM_KYC_REQUEST, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - KYC request" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API KYC request working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - recharge history/customer profile " + exp.getMessage(), false);
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

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - gsm kyc");
      JSONObject json = new JSONObject();
      json.put("clientId", constants.getValue("kyc.client.id"));
      json.put("clientSecret", constants.getValue("kyc.client.secret"));
      commonPostMethod(constants.getValue("api.kyc.auth.token.url") + ESBURIConstants.TOKEN, json);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -  gsm kyc " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API gsm kyc working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - recharge history/customer profile " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to customer profile V2
   *
   * @param msisdn The msisdn
   */
  public void callCustomerProfileV2(String msisdn) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - customer profile V2");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.CUSTOMER_PROFILLE, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - customer profile V2" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API customer profile V2 working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -customer profile V2 " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to account plans
   *
   * @param msisdn The msisdn
   */
  public void callAccoountPlanESBAPI(String msisdn) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - query balance ");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.QUERY_BALANCE, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - query balance " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API query balance working with data " + response.getBody().prettyPrint());
      }
      callRechargeHistory(msisdn, Timestamp.valueOf(LocalDateTime.now()).toInstant().toEpochMilli(),
          Timestamp.valueOf(LocalDateTime.now().minusDays(60).with(LocalTime.of(0, 0, 0))).toInstant().toEpochMilli());

    } catch (Exception exp) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - query balance/recharge history " + exp.getMessage(), false);
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
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - recharge history ");
      queryParam.put("msisdn", msisdn);
      queryParam.put("endDate", endDate);
      queryParam.put("startDate", startDate);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.RECHARGE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - recharge history " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API recharge history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -recharge history " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher details
   *
   * @param voucherId
   */
  public void callVoucherDetails(String voucherId) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - voucher details ");
      if (StringUtils.isNotBlank(voucherId)) {
        queryParam.put("serial_number", voucherId);
        commonGetMethodWithQueryParam(constants.getValue("voucher.service.base.url") + ESBURIConstants.VOUCHER_DETAIL, queryParam);
      }
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - voucher details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API voucher details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -voucher details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher refil barred
   *
   * @param msisdn
   */
  public void callVoucherRefilBarred(String msisdn) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - voucher refil barred ");
      commonPostMethod(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.VOUCHER_REFILL_BARRED,
          new GenericRequest(msisdn));
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - voucher refil barred " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API voucher refil barred working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -voucher refil barred " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher refil barred
   *
   * @param msisdn
   */
  public void callRingtoneDetailsTest(String msisdn, String searchText) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -top twenty ringtone");
      commonGetMethod(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.TOP_TWENTY_RINGBACK_TONE);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -top twenty ringtone" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API top twenty ringtone working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -search name tune");
      queryParam.put("msisdn", msisdn);
      queryParam.put("query", searchText);
      commonGetMethodWithQueryParam(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.SEARCH_NAME_TUNE, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - search name tune" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API search name tune working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -Generic search api");
      queryParam.put("msisdn", msisdn);
      queryParam.put("query", searchText);
      commonGetMethodWithQueryParam(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.GENERIC_SEARCH_API, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - Generic search api" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Generic search api working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(
          ApplicationConstants.DOWNSTREAM_API_ERROR + " - top twenty ringtone/search name tune/Generic search api " + exp.getMessage(),
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
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - ring back tone list ");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("vas.service.tune.base.url") + ESBURIConstants.RING_BACK_TONE_LIST, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - ring back tone list " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API ring back tone list working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -ring back tone list " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to accumulatorAPI
   *
   * @param msisdn
   */
  public void callAccumulatorAPI(String msisdn) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - accumulatorAPI ");
      commonPostMethod(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.GET_ACCUMULATORS, new GenericRequest(msisdn));
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - accumulatorAPI " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API accumulatorAPI working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -accumulatorAPI " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to HLR DETAILS
   *
   * @param msisdn
   */
  public void callHLRFetchDetails(String msisdn) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - HLR DETAILS ");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("hlr.services.profile.url") + ESBURIConstants.HLR_FETCH_DETAILS, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - HLR DETAILS " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API accumulatorAPI working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -HLR DETAILS " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to available tariff plans
   *
   * @param genericRequest
   */
  public void callAvailableTarrifPlan(GenericRequest genericRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - available tariff plans ");
      commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_AVAILABLE_PLANS, genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - available tariff plans " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API available tariff plans working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -available tariff plans " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to current tariff plans
   *
   * @param genericRequest
   */
  public void callCurrentTarrifPlan(GenericRequest genericRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING+ " - current tariff plans ");
      commonPostMethod(constants.getValue("product.catalog.service.base.url") + ESBURIConstants.TARIFF_CURRENT_PLAN, genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - current tariff plans " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API current tariff plans working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -current tariff plans " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to offer details
   *
   * @param offerDetailRequest
   */
  public void callOfferDetailsAPI(OfferDetailRequest offerDetailRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - offer details ");
      commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.OFFER_DETAILS, offerDetailRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - offer details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API offer details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -offer details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to friends and family details
   *
   * @param genericRequest
   */
  public void callFriensFamilyAPI(GenericRequest genericRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - friends and family details ");
      commonPostMethod(constants.getValue("subscriber.product.base.url") + ESBURIConstants.FRIENDS_FAMILY, genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - friends and family details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API friends and family details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -friends and family details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to postpaid account info
   *
   * @param msisdn
   */
  public void callPostpaidAccountInformation(String msisdn) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -get credit limit");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.GET_CREDIT_LIMIT, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -get credit limit" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API get credit limit working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -invoice history");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.INVOICE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - invoice history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API invoice history working with data " + response.getBody().prettyPrint());
      }

      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -postpaid bill details");
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.POSTPAID_BILL_DETAILS,
          queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - postpaid bill details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API postpaid bill details with data " + response.getBody().prettyPrint());
      }

      callCustomerProfileV2(msisdn);
    } catch (Exception exp) {
      commonLib
          .fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - get credit limit/invoice history/postpaid bill details " + exp.getMessage(),
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
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -Usage history");
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("msisdn", usageHistoryMenuRequest.getMsisdn());
      queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
      if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals("FREE"))) {
        queryParam.put("cdrType", "BOTH");
      }
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.USAGE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -Usage history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Usage history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - Usage history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to Usage history
   *
   * @param usageHistoryRequest
   */
  public void callUsageHistory(UsageHistoryRequest usageHistoryRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -Usage history");
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("msisdn", usageHistoryRequest.getMsisdn());
      queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
      queryParam.put("cdrType", "PAID");
      commonGetMethodWithQueryParam(constants.getValue("gsm.customer.profile.base.url") + ESBURIConstants.USAGE_HISTORY, queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -Usage history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Usage history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - Usage history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to vendor details
   *
   */
  public void callVendors() {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " -vendor details");
      commonGetMethod(constants.getValue("vas.service.loan.base.url") + ESBURIConstants.VENDORS);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -vendor details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API vendor details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - vendor details " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to loan summary
   *
   * @param loanRequest
   */
  public void callLoanSummary(LoanRequest loanRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - loan summary ");
      commonPostMethod(constants.getValue("vas.service.loan.base.url") + ESBURIConstants.LOAN_SUMMARY, loanRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - loan summary " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API loan summary working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -loan summary " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to loan details
   *
   * @param loanRequest
   */
  public void callLoanDetails(LoanRequest loanRequest) {
    try {
      commonLib.info(ApplicationConstants.DOWNSTREAM_API_CALLING + " - loan details ");
      commonPostMethod(constants.getValue("vas.service.loan.base.url") + ESBURIConstants.LOAN_DETAILS, loanRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " - loan details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API loan details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail(ApplicationConstants.DOWNSTREAM_API_ERROR + " -loan details " + e.getMessage(), false);
    }
  }
}
