package com.airtel.cs.api;

import com.airtel.cs.commonutils.UtilsMethods;
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
  private final String ESB_API_CALLING = "Calling ESB APIs";

  /**
   * This Method will hit the ESB APIs related to profile api
   *
   * @param msisdn The msisdn
   */
  public void callprofileESBAPI(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      queryParam.put("walletType", "Main");
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.GSM_KYC_REQUEST), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -GSM KYC request" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API GSM KYC request working with data " + response.getBody().prettyPrint());
      }

      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.SELF_CARE_USER_DETAILS), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - self care user details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API self care user details working with data " + response.getBody().prettyPrint());
      }

      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.DEVICE_INFO), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - Device info" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Device info working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - Device info/self care user details/GSM KYC request " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to am service profile
   *
   * @param msisdn The msisdn
   */
  public void callAmServiceProfileESBAPI(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      queryParam.put("walletType", "Main");
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.KYC_REQUEST), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - KYC request" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API KYC request working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - recharge history/customer profile " + exp.getMessage(), false);
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

      JSONObject json = new JSONObject();
      json.put("clientId", "d0f8fa8e-b7ea-48b0-8308-ce42897c451f");
      json.put("clientSecret", "0408e8f2-a3ed-470b-8214-536d21b2c45a");
      commonPostMethod(constants.getValue(ESBURIConstants.TOKEN), json);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -  gsm kyc " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API gsm kyc working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - recharge history/customer profile " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to customer profile V2
   *
   * @param msisdn The msisdn
   */
  public void callCustomerProfileV2(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.CUSTOMER_PROFILLE), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - customer profile V2" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API customer profile V2 working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -customer profile V2 " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to account plans
   *
   * @param msisdn The msisdn
   */
  public void callAccoountPlanESBAPI(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.QUERY_BALANCE), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - query balance " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API query balance working with data " + response.getBody().prettyPrint());
      }
      callRechargeHistory(msisdn, Timestamp.valueOf(LocalDateTime.now()).toInstant().toEpochMilli(),
          Timestamp.valueOf(LocalDateTime.now().minusDays(60).with(LocalTime.of(0, 0, 0))).toInstant().toEpochMilli());

    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - query balance/recharge history " + exp.getMessage(), false);
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
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(14)).getTime()));
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.RECHARGE_HISTORY), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - recharge history " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API recharge history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -recharge history " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher details
   *
   * @param voucherId
   */
  public void callVoucherDetails(String voucherId) {
    try {
      commonLib.info(ESB_API_CALLING);
      if (StringUtils.isNotBlank(voucherId)) {
        queryParam.put("serial_number", voucherId);
        commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.VOUCHER_DETAIL), queryParam);
      }
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - voucher details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API voucher details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -voucher details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher refil barred
   *
   * @param msisdn
   */
  public void callVoucherRefilBarred(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.VOUCHER_REFILL_BARRED), new GenericRequest(msisdn));
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - voucher refil barred " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API voucher refil barred working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -voucher refil barred " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to voucher refil barred
   *
   * @param msisdn
   */
  public void callRingtoneDetailsTest(String msisdn, String searchText) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonGetMethod(constants.getValue(ESBURIConstants.TOP_TWENTY_RINGBACK_TONE));
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -top twenty ringtone" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API top twenty ringtone working with data " + response.getBody().prettyPrint());
      }

      queryParam.put("msisdn", msisdn);
      queryParam.put("query", searchText);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.SEARCH_NAME_TUNE), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - search name tune" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API search name tune working with data " + response.getBody().prettyPrint());
      }

      queryParam.put("msisdn", msisdn);
      queryParam.put("query", searchText);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.GENERIC_SEARCH_API), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - Generic search api" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Generic search api working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - top twenty ringtone/search name tune/Generic search api " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to ring back tone list
   *
   * @param msisdn
   */
  public void callActiveRingTone(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.RING_BACK_TONE_LIST), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - ring back tone list " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API ring back tone list working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -ring back tone list " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to accumulatorAPI
   *
   * @param msisdn
   */
  public void callAccumulatorAPI(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.GET_ACCUMULATORS), new GenericRequest(msisdn));
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - accumulatorAPI " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API accumulatorAPI working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -accumulatorAPI " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to accumulatorAPI
   *
   * @param msisdn
   */
  public void callHLRFetchDetails(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.HLR_FETCH_DETAILS), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - accumulatorAPI " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API accumulatorAPI working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -accumulatorAPI " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to available tariff plans
   *
   * @param genericRequest
   */
  public void callAvailableTarrifPlan(GenericRequest genericRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.TARIFF_AVAILABLE_PLANS), genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - available tariff plans " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API available tariff plans working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -available tariff plans " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to current tariff plans
   *
   * @param genericRequest
   */
  public void callCurrentTarrifPlan(GenericRequest genericRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.TARIFF_CURRENT_PLAN), genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - current tariff plans " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API current tariff plans working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -current tariff plans " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to offer details
   *
   * @param offerDetailRequest
   */
  public void callOfferDetailsAPI(OfferDetailRequest offerDetailRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.OFFER_DETAILS), offerDetailRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - offer details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API offer details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -offer details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to friends and family details
   *
   * @param genericRequest
   */
  public void callFriensFamilyAPI(GenericRequest genericRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.FRIENDS_FAMILY), genericRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - friends and family details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API friends and family details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -friends and family details " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to postpaid account info
   *
   * @param msisdn
   */
  public void callPostpaidAccountInformation(String msisdn) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.GET_CREDIT_LIMIT), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -get credit limit" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API get credit limit working with data " + response.getBody().prettyPrint());
      }

      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.INVOICE_HISTORY), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - invoice history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API invoice history working with data " + response.getBody().prettyPrint());
      }

      queryParam.put("msisdn", msisdn);
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.POSTPAID_BILL_DETAILS), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - postpaid bill details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API postpaid bill details with data " + response.getBody().prettyPrint());
      }

      callCustomerProfileV2(msisdn);
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - get credit limit/invoice history/postpaid bill details " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to Usage history
   *
   * @param usageHistoryMenuRequest
   */
  public void callUsageHistory(UsageHistoryMenuRequest usageHistoryMenuRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("msisdn", usageHistoryMenuRequest.getMsisdn());
      queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
      if (!StringUtils.isEmpty(usageHistoryMenuRequest.getCdrTypeFilter()) && (usageHistoryMenuRequest.getCdrTypeFilter().equals("FREE"))) {
        queryParam.put("cdrType", "BOTH");
      }
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.USAGE_HISTORY), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -Usage history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Usage history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - Usage history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to Usage history
   *
   * @param usageHistoryRequest
   */
  public void callUsageHistory(UsageHistoryRequest usageHistoryRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      queryParam.put("endDate", UtilsMethods.getUTCEndDate(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MAX)).getTime()));
      queryParam.put("msisdn", usageHistoryRequest.getMsisdn());
      queryParam.put("sortingOrder", "GSM_USAGE_HISTORY DESC");
      queryParam.put("startDate", UtilsMethods.getUTCStartDate(Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(3)).getTime()));
      queryParam.put("cdrType", "PAID");
      commonGetMethodWithQueryParam(constants.getValue(ESBURIConstants.USAGE_HISTORY), queryParam);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -Usage history" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API Usage history working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - Usage history " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to vendor details
   *
   */
  public void callVendors() {
    try {
      commonLib.info(ESB_API_CALLING);
      commonGetMethod(constants.getValue(ESBURIConstants.VENDORS));
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API -vendor details" + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API vendor details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception exp) {
      commonLib.fail("Exception in ESB API - vendor details " + exp.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to loan summary
   *
   * @param loanRequest
   */
  public void callLoanSummary(LoanRequest loanRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.LOAN_SUMMARY), loanRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - loan summary " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API loan summary working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -loan summary " + e.getMessage(), false);
    }
  }

  /**
   * This Method will hit the ESB APIs related to loan details
   *
   * @param loanRequest
   */
  public void callLoanDetails(LoanRequest loanRequest) {
    try {
      commonLib.info(ESB_API_CALLING);
      commonPostMethod(constants.getValue(ESBURIConstants.LOAN_DETAILS), loanRequest);
      if (response.getStatusCode() != 200) {
        commonLib.fail("Exception in ESB API - loan details " + response.getStatusCode(), false);
      } else {
        commonLib.pass("ESB API loan details working with data " + response.getBody().prettyPrint());
      }
    } catch (Exception e) {
      commonLib.fail("Exception in ESB API -loan details " + e.getMessage(), false);
    }
  }
}
