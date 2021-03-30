package com.airtel.cs.api;

import com.airtel.cs.pojo.*;
import com.airtel.cs.pojo.Accumulators.AccumulatorsPOJO;
import com.airtel.cs.pojo.AirtelMoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.CRBT.ActivateRingtone;
import com.airtel.cs.pojo.CRBT.Top20Ringtone;
import com.airtel.cs.pojo.ClearRefillStatus.RefillStatus;
import com.airtel.cs.pojo.HLRService.HLRServicePOJO;
import com.airtel.cs.pojo.KYCProfile.KYCProfile;
import com.airtel.cs.pojo.LoanDetails.Loan;
import com.airtel.cs.pojo.LoanSummary.Summary;
import com.airtel.cs.pojo.SMSHistory.SMSHistoryPOJO;
import com.airtel.cs.pojo.TicketList.TicketPOJO;
import com.airtel.cs.pojo.Vendors.VendorNames;
import com.airtel.cs.pojo.Voucher.VoucherSearchPOJO;
import com.airtel.cs.commonutils.UtilsMethods;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.log4j.Log4j2;
import tests.frontendagent.BaseTest;

import static com.airtel.cs.commonutils.extentreports.ExtentTestManager.getTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class APIEndPoints extends BaseTest {

    public static Integer statusCode = null;

    public PlansPOJO accountPlansTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Account Plans com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/account/plans");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(PlansPOJO.class);
    }

    public UsageHistoryPOJO usageHistoryTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Usage History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null,\"action\": \"More\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/usage/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(UsageHistoryPOJO.class);
    }

    public UsageHistoryPOJO usageHistoryMenuTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Usage History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"typeFilter\":null,\"startDate\":null,\"endDate\":null,\"action\":\"More\",\"cdrTypeFilter\":\"FREE\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/usage/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(UsageHistoryPOJO.class);
    }

    public AMHandSetProfilePOJO amProfileTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using AM Profile com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"extTxnId\":1234,\"msisdn\":\"" + msisdn + "\",\"walletType\":\"Main\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/am/profile");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AMHandSetProfilePOJO.class);

    }

    public GsmKycPOJO gsmKYCAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using GSM KYC com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
//                .header("Opco", Opco)
//                .header("Host", "172.23.36.206:30050")
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/gsm/kyc");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(GsmKycPOJO.class);

    }

    public ProfilePOJO profileAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using /cs-gsm-service/v1/profile api for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/profile");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(ProfilePOJO.class);
    }

    public KYCProfile KYCProfileAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using /cs-gsm-service/v1/profile api for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/kyc/profile");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(KYCProfile.class);
    }

    public AMProfilePOJO amServiceProfileAPITest(String Msisdn) {
        getTest().log(LogStatus.INFO, "Using AM Service Profile com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .contentType("application/json")
                .queryParam("msisdn", Msisdn)
                .queryParam("walletType", "Main");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getQueryParams().toString());
        log.info("Request Body is  : " + queryable.getQueryParams().toString());
        Response response = request.get("/cs-am-service/v1/profile");
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AMProfilePOJO.class);
    }

    public RechargeHistoryPOJO rechargeHistoryAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Recharge History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null,\"rechargeHistoryVoucherSearch\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/recharge/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(RechargeHistoryPOJO.class);
    }

    public BundleRechargeHistoryPOJO bundleRechargeHistoryAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Recharge History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/bundle/recharge/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(BundleRechargeHistoryPOJO.class);
    }

    public AirtelMoneyPOJO transactionHistoryAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Transaction History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-am-service/v1/transaction/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AirtelMoneyPOJO.class);
    }

    public AccountsBalancePOJO balanceAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Balance (accounts) com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\": \"" + msisdn + "\",\"pageSize\": 10,\"pageNumber\": 1}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/accounts/balance");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AccountsBalancePOJO.class);

    }

    public UsageHistoryPOJO usageHistoryTest(String msisdn, String Type) {
        getTest().log(LogStatus.INFO, "Using Usage History com.airtel.cs.API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null,\"type\":\"" + Type + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/usage/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(UsageHistoryPOJO.class);
    }

    public TicketPOJO ticketMetaDataTest(String ticketId) {
        getTest().log(LogStatus.INFO, "Using fetch ticket details using ticket Id to validate ticket meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).param("id", ticketId).contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        Response response = request.get("/sr/api/sr-service/v1/fetch/ticket");
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(TicketPOJO.class);
    }

    public SMSHistoryPOJO smsHistoryTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using fetch ticket details using ticket Id to validate ticket meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"receiverId\":" + msisdn + ",\"pageNumber\":0,\"pageSize\":10}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-notification-service/v1/fetch/history");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(SMSHistoryPOJO.class);
    }

    public VoucherSearchPOJO voucherSearchTest(String voucherId) {
        getTest().log(LogStatus.INFO, "Using fetch Voucher details using voucher Id to validate Voucher meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"voucherId\":\"" + voucherId + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/voucher/detail");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(VoucherSearchPOJO.class);
    }

    public VendorNames vendorsNamesTest() {
        getTest().log(LogStatus.INFO, "Using fetch vendor name");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers);
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        log.info("Request Headers are  : " + queryable.getHeaders());
        Response response = request.get("/cs-vas-service/v1/vendors");
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(VendorNames.class);
    }

    public Summary loanSummaryTest(String msisdn, String vendorName) {
        getTest().log(LogStatus.INFO, "Using fetch Voucher details using voucher Id to validate Voucher meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"vendorName\":\"" + vendorName + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-vas-service/v1/loan/summary");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(Summary.class);
    }

    public Loan loanDetailsTest(String msisdn, String vendorName) {
        getTest().log(LogStatus.INFO, "Using Loan details com.airtel.cs.API to validate Loan Detail and Loan history widget");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"vendorName\":\"" + vendorName + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-vas-service/v1/loan/details");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(Loan.class);
    }

    public RefillStatus clearRefillTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using fetch Voucher details using voucher Id to validate Voucher meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/refill/status");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(RefillStatus.class);
    }

    public Top20Ringtone ringtoneDetailTest(String msisdn, String searchBy, String searchText) {
        getTest().log(LogStatus.INFO, "Using fetch Ringtone details using MSISDN");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"searchBy\":\"" + searchBy + "\",\"searchText\":\"" + searchText + "\",\"msisdn\":\"" + msisdn + "\"}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-vas-service/v1/search/tunes");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(Top20Ringtone.class);
    }

    public ActivateRingtone activateRingtone(String msisdn) {
        getTest().log(LogStatus.INFO, "Using fetch ticket details using ticket Id to validate ticket meta data");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).param("msisdn", msisdn).contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.get("/cs-vas-service/v1/fetch/tunes");
        UtilsMethods.printGetRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(ActivateRingtone.class);
    }

    public AccumulatorsPOJO accumulatorsAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using fetch Ringtone details using MSISDN");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/accumulators");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(AccumulatorsPOJO.class);
    }

    public HLRServicePOJO getServiceProfileWidgetInfo(String msisdn) {
        getTest().log(LogStatus.INFO, "Using fetch Ringtone details using MSISDN");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers).body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1}").contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        Response response = request.post("/cs-gsm-service/v1/hlr/service/profiles");
        UtilsMethods.printPostRequestDetail(queryable);
        UtilsMethods.printResponseDetail(response);
        statusCode = response.getStatusCode();
        return response.as(HLRServicePOJO.class);
    }

}
