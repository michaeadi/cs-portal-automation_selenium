package API;

import POJO.*;
import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import tests.BaseTest;

import static Utils.ExtentReports.ExtentTestManager.getTest;
import static Utils.ExtentReports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class APITest extends BaseTest {
    @DataProvider.User(UserType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProvider.class, priority = 1)
    public void loginAPI(TestDatabean Data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LoginPOJO Req = LoginPOJO.loginBody(Data.getPassword(), Data.getLoginAUUID());
        System.out.println(config.getProperty(Env + "-x-app-name"));
        map.add(new Header("x-app-name", config.getProperty(Env + "-x-app-name")));
        map.add(new Header("x-service-id", config.getProperty(Env + "-x-service-id")));
        map.add(new Header("x-bsy-bn", config.getProperty(Env + "-x-bsy-bn")));
        map.add(new Header("x-app-type", config.getProperty(Env + "-x-app-type")));
        map.add(new Header("x-client-id", config.getProperty(Env + "-x-client-id")));
        map.add(new Header("x-api-key", config.getProperty(Env + "-x-api-key")));
        map.add(new Header("x-login-module", config.getProperty(Env + "-x-login-module")));
        map.add(new Header("x-channel", config.getProperty(Env + "-x-channel")));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        startTest("LOGIN API TEST ", "Logging in Using Login API for getting TOKEN with user : " + Data.getLoginAUUID());
        getTest().log(LogStatus.INFO, "Logging in Using Login API for getting TOKEN with user : " + Data.getLoginAUUID());
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        Response response = request.post("/auth/api/user-mngmnt/v2/login");
        Token = "Bearer " + response.jsonPath().getString("result.accessToken");
        map.add(new Header("Authorization", Token));
        getTest().log(LogStatus.INFO, "Response : " + response.asString());
        log.info("Response : " + response.asString());
    }

    public PlansPOJO accountPlansTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Account Plans API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/account/plans");
        getTest().log(LogStatus.INFO, "Response : " + response.asString());
        log.info("Response : " + response.asString());
        System.out.println(response.asString());
        return response.as(PlansPOJO.class);
    }

    public UsageHistoryPOJO usageHistoryTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Usage History API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/usage/history");
        System.out.println(response.asString());
        log.info("Response : " + response.asString());
        return response.as(UsageHistoryPOJO.class);
    }

    public void amProfileTest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using AM Profile API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"extTxnId\":1234,\"msisdn\":\"" + msisdn + "\",\"walletType\":\"Main\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/am/profile");
        System.out.println(response.asString());
        log.info("Response : " + response.asString());
    }

    public void gsmKYCAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using GSM KYC API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/gsm/kyc");
        System.out.println(response.asString());
        log.info("Response : " + response.asString());

    }

    public ProfilePOJO profileAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using KYC Profile API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\"}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/profile");
        System.out.println(response.asString());
        log.info("Response : " + response.asString());

        return response.as(ProfilePOJO.class);
    }

    public AMProfilePOJO amServiceProfileAPITest(String Msisdn) {
        getTest().log(LogStatus.INFO, "Using AM Service Profile API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .contentType("application/json")
                .queryParam("msisdn", Msisdn)
                .queryParam("walletType", "Main");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getQueryParams().toString());
        log.info("Request Body is  : " + queryable.getQueryParams().toString());
        Response response = request.get("/cs-am-service/v1/profile");
        System.out.println(response.asString());
        log.info("Response : " + response.asString());

        return response.as(AMProfilePOJO.class);
    }

    public RechargeHistoryPOJO rechargeHistoryAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Recharge History API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null,\"rechargeHistoryVoucherSearch\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/recharge/history");
        System.out.println(response.asString());
        log.info("Response : " + response.asString());

        return response.as(RechargeHistoryPOJO.class);
    }

    public void transactionHistoryAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Transaction History API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-am-service/v1/transaction/history");
        log.info("Response : " + response.asString());
        System.out.println(response.asString());
    }

    public AccountsBalancePOJO balanceAPITest(String msisdn) {
        getTest().log(LogStatus.INFO, "Using Balance (accounts) API for Getting expected data for UI");
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body("{\"msisdn\": \"" + msisdn + "\",\"pageSize\": 10,\"pageNumber\": 1}")
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        log.info("Request Headers are  : " + queryable.getHeaders());
        getTest().log(LogStatus.INFO, "Request Body is  : " + queryable.getBody().toString());
        log.info("Request Body is  : " + queryable.getBody().toString());
        Response response = request.post("/cs-gsm-service/v1/accounts/balance");
        log.info("Response : " + response.asString());
        System.out.println(response.asString());
        return response.as(AccountsBalancePOJO.class);

    }
}
