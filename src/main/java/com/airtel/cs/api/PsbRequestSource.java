package com.airtel.cs.api;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.URIConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.restutils.RestCommonUtils;
import com.airtel.cs.model.cs.request.am.SmsLogsRequest;
import com.airtel.cs.model.cs.request.psb.cs.BankDetailsRequest;
import com.airtel.cs.model.cs.request.psb.cs.FetchBalanceRequest;
import com.airtel.cs.model.cs.request.psb.cs.TransactionHistoryRequest;
import com.airtel.cs.model.cs.response.airtelmoney.AirtelMoney;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.psb.cs.bankdetails.BankDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.fetchbalance.FetchBalanceResponse;


import java.util.HashMap;
import java.util.Map;


public class PsbRequestSource extends RestCommonUtils {

    public static final Map<String, Object> queryParam = new HashMap<>();
    private static final String SEARCHID = "searchid";
    private static final String MSISDN = "msisdn";
    private ESBRequestSource esbRequestSource = new ESBRequestSource();
    public static final String CS_PORTAL_API_ERROR = "cs.portal.api.error";
    private static final String CALLING_CS_API = "cs.api.calling";

    /**
     * This Method will hit the  API "/cs-am-service/v1/clm/details" and returns the response
     */
    public CLMDetailsResponse getCLMDetails(String msisdn) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("clm.details"), JavaColors.GREEN, false);
        CLMDetailsResponse result = null;
        try {
            queryParam.clear();
            queryParam.put(SEARCHID, msisdn);
            commonGetMethodWithQueryParam(URIConstants.CLM_DETAILS, queryParam);
            result = response.as(CLMDetailsResponse.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callCLMDetails(msisdn);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getCLMDetails " + e.getMessage(), false);
            esbRequestSource.callCLMDetails(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/transaction/history" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public AirtelMoney getTransactionHistory(String msisdn, String nubanId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("transaction.history"), JavaColors.GREEN, false);
        AirtelMoney result = null;
        String type = constants.getValue(ApplicationConstants.ACCOUNT_TYPE);
        try {
            commonPostMethod(URIConstants.TRANSACTION_HISTORY, new TransactionHistoryRequest(msisdn, 5, 1, nubanId, type));
            result = response.as(AirtelMoney.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callTransactionHistory(msisdn, type);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getTransactionHistory " + e.getMessage(), false);
            esbRequestSource.callTransactionHistory(msisdn, type);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/profile" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public AMProfile getAmProfile(String msisdn, String nubanid,String type) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("am.profile"), JavaColors.GREEN, false);
        AMProfile result = null;
        try {
            queryParam.put(MSISDN, msisdn);
            queryParam.put("walletType", "Main");
            commonGetMethodWithQueryParam(URIConstants.AM_PROFILE, queryParam);
            result = response.as(AMProfile.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callBalanceAPI(nubanid,msisdn, type);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getAmProfile " + e.getMessage(), false);
            esbRequestSource.callBalanceAPI(nubanid,msisdn, lineType);
        }
        return result;
    }


    /**
     * This Method will hit the API "/cs-am-service/v1/bank/details" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public BankDetailsResponse getAmBankDetails(String msisdn, String nubanId) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("bank.details"), JavaColors.GREEN, false);
        BankDetailsResponse result = null;
        String type = constants.getValue(ApplicationConstants.ACCOUNT_TYPE);
        try {
            commonPostMethod(URIConstants.BANK_DETAILS, new BankDetailsRequest(msisdn, nubanId));
            result = response.as(BankDetailsResponse.class);
            if (result.getStatusCode() != 200) {
                esbRequestSource.callBalanceAPI(nubanId,msisdn, type);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getAmBankDetails " + e.getMessage(), false);
            esbRequestSource.callBalanceAPI(nubanId,msisdn, type);
        }
        return result;
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
            commonPostMethod(URIConstants.SMS_TRAILS, new SmsLogsRequest(msisdn,null,null,1,5));
            result = response.as(SmsLogsResponse.class);
            if(result.getStatusCode()!=200)
                esbRequestSource.callSmsSummary(msisdn);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getSmsLogs " + e.getMessage(), false);
            esbRequestSource.callSmsSummary(msisdn);
        }
        return result;
    }

    /**
     * This Method will hit the API "/cs-am-service/v1/fetch/balances" and return the response
     *
     * @param msisdn The msisdn
     * @return The Response
     */
    public FetchBalanceResponse getFetchBalance(String msisdn, String idNumber, String idType) {
        commonLib.infoColored(constants.getValue(CALLING_CS_API) + constants.getValue("fetch.balance"), JavaColors.GREEN, false);
        FetchBalanceResponse result = null;
        try {
            commonPostMethod(URIConstants.FETCH_BALANCE, new FetchBalanceRequest(msisdn,idNumber,idType));
            result = response.as(FetchBalanceResponse.class);
            if(result.getStatusCode()!=200)
                esbRequestSource.callBalanceAPI(msisdn, idNumber,idType);
        } catch (Exception e) {
            commonLib.fail(constants.getValue(CS_PORTAL_API_ERROR) + " - getFetchBalance " + e.getMessage(), false);
            esbRequestSource.callBalanceAPI(msisdn, idNumber,idType);
        }
        return result;
    }
}