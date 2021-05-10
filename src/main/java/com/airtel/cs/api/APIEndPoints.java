package com.airtel.cs.api;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.URIConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.AMHandSetProfilePOJO;
import com.airtel.cs.pojo.AMProfilePOJO;
import com.airtel.cs.pojo.AccountsBalancePOJO;
import com.airtel.cs.pojo.BundleRechargeHistoryPOJO;
import com.airtel.cs.pojo.GsmKycPOJO;
import com.airtel.cs.pojo.LoginPOJO;
import com.airtel.cs.pojo.PlansPOJO;
import com.airtel.cs.pojo.ProfilePOJO;
import com.airtel.cs.pojo.RechargeHistoryPOJO;
import com.airtel.cs.pojo.UsageHistoryPOJO;
import com.airtel.cs.pojo.accumulators.AccumulatorsPOJO;
import com.airtel.cs.pojo.airtelmoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.clearrefillstatus.RefillStatus;
import com.airtel.cs.pojo.configuration.ConfigurationPOJO;
import com.airtel.cs.pojo.crbt.ActivateRingtone;
import com.airtel.cs.pojo.crbt.Top20Ringtone;
import com.airtel.cs.pojo.hlrservice.HLRServicePOJO;
import com.airtel.cs.pojo.kycprofile.KYCProfile;
import com.airtel.cs.pojo.loandetails.Loan;
import com.airtel.cs.pojo.loansummary.Summary;
import com.airtel.cs.pojo.smshistory.SMSHistoryPOJO;
import com.airtel.cs.pojo.tariffplan.AvailablePlanPOJO;
import com.airtel.cs.pojo.tariffplan.CurrentPlanPOJO;
import com.airtel.cs.pojo.ticketlist.TicketPOJO;
import com.airtel.cs.pojo.vendors.VendorNames;
import com.airtel.cs.pojo.voucher.VoucherSearchPOJO;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class APIEndPoints extends Driver {

    public static Integer statusCode = null;
    private static final String APPLICATION_JSON = "application/json";
    private static Response response;
    private static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);

    public PlansPOJO accountPlansTest(String msisdn) {
        commonLib.info("Using Account Plans com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.ACCOUNT_PLAN);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(PlansPOJO.class);
    }

    public UsageHistoryPOJO usageHistoryTest(String msisdn) {
        commonLib.info("Using Usage History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null,\"action\": \"More\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.USAGE_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(UsageHistoryPOJO.class);
    }

    public UsageHistoryPOJO usageHistoryMenuTest(String msisdn) {
        commonLib.info("Using Usage History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"typeFilter\":null,\"startDate\":null,\"endDate\":null,\"action\":\"More\",\"cdrTypeFilter\":\"FREE\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.USAGE_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(UsageHistoryPOJO.class);
    }

    public AMHandSetProfilePOJO amProfileTest(String msisdn) {
        commonLib.info("Using AM Profile com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"extTxnId\":1234,\"msisdn\":\"" + msisdn + "\",\"walletType\":\"Main\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.GSM_AM_PROFILE);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AMHandSetProfilePOJO.class);

    }

    public GsmKycPOJO gsmKYCAPITest(String msisdn) {
        commonLib.info("Using GSM KYC com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.GSM_KYC);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(GsmKycPOJO.class);

    }

    public ProfilePOJO profileAPITest(String msisdn) {
        commonLib.info("Using /cs-gsm-service/v1/profile api for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.GSM_PROFILE);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(ProfilePOJO.class);
    }

    public KYCProfile KYCProfileAPITest(String msisdn) {
        commonLib.info("Using /cs-gsm-service/v1/profile api for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.KYC_PROFILE);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(KYCProfile.class);
    }

    public AMProfilePOJO amServiceProfileAPITest(String Msisdn) {
        commonLib.info("Using AM Service Profile com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .contentType("application/json")
                .queryParam("msisdn", Msisdn)
                .queryParam("walletType", "Main");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        commonLib.info("Request Body is  : " + queryable.getQueryParams().toString());
        log.info("Request Body is  : " + queryable.getQueryParams().toString());
        response = request.get(URIConstants.AM_PROFILE);
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AMProfilePOJO.class);
    }

    public RechargeHistoryPOJO rechargeHistoryAPITest(String msisdn) {
        commonLib.info("Using Recharge History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null,\"rechargeHistoryVoucherSearch\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.RECHARGE_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(RechargeHistoryPOJO.class);
    }

    public BundleRechargeHistoryPOJO bundleRechargeHistoryAPITest(String msisdn) {
        commonLib.info("Using Recharge History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.BUNDLE_RECHARGE_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(BundleRechargeHistoryPOJO.class);
    }

    public AirtelMoneyPOJO transactionHistoryAPITest(String msisdn) {
        commonLib.info("Using Transaction History API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.TRANSACTION_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AirtelMoneyPOJO.class);
    }

    public AirtelMoneyPOJO moreTransactionHistoryAPITest(String msisdn, String currencyType) {
        commonLib.info("Using Transaction History API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"daysFilter\":null,\"startDate\":null,\"endDate\":null,\"airtelMoneyTransactionIdSearch\":null,\"currencyType\":\"" + currencyType + "\",\"amHistory\":true}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.TRANSACTION_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AirtelMoneyPOJO.class);
    }

    public AccountsBalancePOJO balanceAPITest(String msisdn) {
        commonLib.info("Using Balance (accounts) com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\": \"" + msisdn + "\",\"pageSize\": 10,\"pageNumber\": 1}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.ACCOUNT_BALANCE);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AccountsBalancePOJO.class);

    }

    public UsageHistoryPOJO usageHistoryTest(String msisdn, String type) {
        commonLib.info("Using Usage History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null,\"type\":\"" + type + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.USAGE_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(UsageHistoryPOJO.class);
    }

    public TicketPOJO ticketMetaDataTest(String ticketId) {
        commonLib.info("Using fetch ticket details using ticket Id to validate ticket meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).param("id", ticketId).contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        response = request.get(URIConstants.SR_FETCH_HISTORY);
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(TicketPOJO.class);
    }

    public SMSHistoryPOJO smsHistoryTest(String msisdn) {
        commonLib.info("Using fetch ticket details using ticket Id to validate ticket meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"receiverId\":" + msisdn + ",\"pageNumber\":0,\"pageSize\":10}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.NOTIFICATION_FETCH_HISTORY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(SMSHistoryPOJO.class);
    }

    public VoucherSearchPOJO voucherSearchTest(String voucherId) {
        commonLib.info("Using fetch Voucher details using voucher Id to validate Voucher meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"voucherId\":\"" + voucherId + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.VOUCHER_DETAILS);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(VoucherSearchPOJO.class);
    }

    public VendorNames vendorsNamesTest() {
        commonLib.info("Using fetch vendor name");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers);
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        response = request.get(URIConstants.VENDORS);
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(VendorNames.class);
    }

    public Summary loanSummaryTest(String msisdn, String vendorName) {
        commonLib.info("Using fetch Voucher details using voucher Id to validate Voucher meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"vendorName\":\"" + vendorName + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.LOAN_SUMMARY);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(Summary.class);
    }

    public Loan loanDetailsTest(String msisdn, String vendorName) {
        commonLib.info("Using Loan details com.airtel.cs.API to validate Loan Detail and Loan history widget");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"vendorName\":\"" + vendorName + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.LOAN_DETAILS);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(Loan.class);
    }

    public RefillStatus clearRefillTest(String msisdn) {
        commonLib.info("Using fetch Voucher details using voucher Id to validate Voucher meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.REFILL_STATUS);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(RefillStatus.class);
    }

    public Top20Ringtone ringtoneDetailTest(String msisdn, String searchBy, String searchText) {
        commonLib.info("Using fetch Ringtone details using MSISDN");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"searchBy\":\"" + searchBy + "\",\"searchText\":\"" + searchText + "\",\"msisdn\":\"" + msisdn + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.SEARCH_TUNES);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(Top20Ringtone.class);
    }

    public ActivateRingtone activateRingtone(String msisdn) {
        commonLib.info("Using fetch ticket details using ticket Id to validate ticket meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).param("msisdn", msisdn).contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.get(URIConstants.FETCH_TUNES);
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(ActivateRingtone.class);
    }

    public AccumulatorsPOJO accumulatorsAPITest(String msisdn) {
        commonLib.info("Using fetch Ringtone details using MSISDN");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.ACCUMULATORS);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AccumulatorsPOJO.class);
    }

    public HLRServicePOJO getServiceProfileWidgetInfo(String msisdn) {
        commonLib.info("Using fetch Ringtone details using MSISDN");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.post(URIConstants.SERVICE_PROFILE);
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(HLRServicePOJO.class);
    }

    public void getAuthTabAnswer(String key, String msisdn) {
        commonLib.info("Fetch Answer using auth user api");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).param("key", key).param("msisdn", msisdn).contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        response = request.get(URIConstants.AUTH_USER);
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        UtilsMethods.printInfoLog(response.prettyPrint());
        UtilsMethods.printInfoLog(response.peek().toString());
        statusCode = response.getStatusCode();
    }

    public ConfigurationPOJO getConfiguration(String key) {
        commonLib.info("Using Configuration API to validate meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).queryParam("keys", key).queryParam("opco", OPCO).contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        response = request.get(URIConstants.CONFIGURATIONS);
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(ConfigurationPOJO.class);
    }

    /**
     * This Method will hit the API with POST Method
     *
     * @param endPoint endpoint
     * @param body     body of the api
     */
    public static void commonPostMethod(String endPoint, String body) {
        commonPostMethod(endPoint, body, baseUrl);
    }

    /**
     * This Method will hit the API with POST Method
     *
     * @param endPoint endpoint
     * @param body     body of the api
     * @param url      http url
     */
    public static void commonPostMethod(String endPoint, String body, String url) {
        try {
            commonLib.info("Using " + endPoint + " API for Testing");
            baseURI = url;
            Headers headers = new Headers(map);
            RequestSpecification request = given()
                    .headers(headers)
                    .body(body)
                    .contentType(APPLICATION_JSON);
            response = request.post(endPoint);
            QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
            UtilsMethods.printPostRequestDetail(queryable);
            UtilsMethods.printResponseDetail(response);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - commonPostMethod " + e.getMessage(), false);
        }
    }

    /*
    This Method will hit the Available Plan API and returns the response
     */
    public AvailablePlanPOJO availablePlanPOJO() {
        String body = "{ \"msisdn\":\"" + TARIFF_PLAN_TEST_NUMBER + "\"}";
        commonPostMethod(URIConstants.TARIFF_AVAILABLE_PLANS, body);
        return response.as(AvailablePlanPOJO.class);
    }

    /*
    This Method will hit the Current Plan API and returns the response
     */
    public CurrentPlanPOJO currentPlanPOJO() {
        String body = "{ \"msisdn\":\"" + TARIFF_PLAN_TEST_NUMBER + "\"}";
        commonPostMethod(URIConstants.TARIFF_CURRENT_PLAN, body);
        return response.as(CurrentPlanPOJO.class);
    }

    /**
     * This Method will hit the Login API "/auth/api/user-mngmnt/v2/login" and return the response
     *
     * @param body body of the API
     * @return response
     */
    public LoginPOJO loginPOJO(String body) {
        commonLib.info("Logging in Using Login API for getting TOKEN with user");
        commonPostMethod(URIConstants.V2_LOGIN, body);
        return response.as(LoginPOJO.class);
    }

}
