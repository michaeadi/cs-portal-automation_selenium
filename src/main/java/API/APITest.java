package API;

import POJO.*;
import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import tests.BaseTest;

import static Utils.ExtentReports.ExtentTestManager.getTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class APITest extends BaseTest {
    @DataProvider.User(UserType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProvider.class, priority = 1)
    public void loginAPI(TestDatabean Data) throws JsonProcessingException {
        ExtentTestManager.startTest("Testing Send OTP with Invalid Process Name", "");
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(NON_EMPTY);
//        mapper.setSerializationInclusion(NON_NULL);
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
        System.out.println(dtoAsString);
        getTest().log(LogStatus.INFO, "Sending Request to send OTP with less Than Min Length ReceiverId");
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
        request.body(dtoAsString);
        request.contentType("application/json");
        Response response = request.post("/auth/api/user-mngmnt/v2/login");
        System.out.println(response.asString());
        Token = "Bearer " + response.jsonPath().getString("result.accessToken");
        map.add(new Header("Authorization", Token));
        getTest().log(LogStatus.INFO, response.asString());
//        System.out.println("#$%^&*(*&^%$#$%^&*(*&^%$#@!$%^&89087654323456789");
//        int StatusCode = response.getStatusCode();
//        int ExpectedCode = 200;
//        ProfilePOJO ExtractedResp = response.as(ProfilePOJO.class, ObjectMapperType.JACKSON_2);
//        AE(StatusCode, ExpectedCode);
//        AE(ExtractedResp.getMsgid(), "SPINE_200");
//        getTest().log(LogStatus.PASS, "Testing Send OTP with less Than Min Length ReceiverId");
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public PlansPOJO accountPlansTest(String msisdn) throws JsonProcessingException {
//        System.out.println(Token);
//        System.out.println(map.size());
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
        request.body("{\"msisdn\":\"" + msisdn + "\"}");
        request.contentType("application/json");
        Response response = request.post("/cs-gsm-service/v1/account/plans");
        System.out.println(response.asString());
        return response.as(PlansPOJO.class);
//        System.out.println(ExtractedResp.getResult().getMainAccountBalance().getBalance());
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 3, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public UsageHistoryPOJO usageHistoryTest(String msisdn) throws JsonProcessingException {
//        System.out.println(Token);
//        System.out.println(map.size());
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);

        request.body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null}");
        request.contentType("application/json");
        Response response = request.post("/cs-gsm-service/v1/usage/history");
        System.out.println(response.asString());
        return response.as(UsageHistoryPOJO.class);
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 4, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void amProfileTest(TestDatabean Data) throws JsonProcessingException {
//        System.out.println(Token);
//        System.out.println(map.size());
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
//        request.body("{\"msisdn\":\"" + Data.getCustomerNumber() + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null}");

        request.body("{\"extTxnId\":1234,\"msisdn\":\"735873718\",\"walletType\":\"Main\"}");
        request.contentType("application/json");
        Response response = request.post("/cs-gsm-service/v1/am/profile");
        System.out.println(response.asString());
//        UsageHistoryPOJO ExtractedResp = response.as(UsageHistoryPOJO.class);
//
//        System.out.println(ExtractedResp.getResult().size());
//        System.out.println(ExtractedResp.getResult().get(1).toString());
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 5, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void gsmKYCAPITest(TestDatabean Data) throws JsonProcessingException {
//        System.out.println(Token);
//        System.out.println(map.size());
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
//        request.body("{\"msisdn\":\"" + Data.getCustomerNumber() + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null}");

        request.body("{\"msisdn\":\"731508274\"}");
        request.contentType("application/json");
        Response response = request.post("/cs-gsm-service/v1/gsm/kyc");
        System.out.println(response.asString());
//        UsageHistoryPOJO ExtractedResp = response.as(UsageHistoryPOJO.class);
//
//        System.out.println(ExtractedResp.getResult().size());
//        System.out.println(ExtractedResp.getResult().get(1).toString());
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 6, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void profileAPITest(TestDatabean Data) throws JsonProcessingException {
//        System.out.println(Token);
//        System.out.println(map.size());
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
//        request.body("{\"msisdn\":\"" + Data.getCustomerNumber() + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null}");

        request.body("{\"msisdn\":\"731508274\"}");
        request.contentType("application/json");
        Response response = request.post("/cs-gsm-service/v1/profile");
        System.out.println(response.asString());
        ProfilePOJO ExtractedResp = response.as(ProfilePOJO.class);
//
        System.out.println(ExtractedResp.getResult().getLineType());
//        System.out.println(ExtractedResp.getResult().get(1).toString());
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 7, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public AMProfilePOJO amServiceProfileAPITest(String Msisdn) throws JsonProcessingException {
//        System.out.println(Token);
//        System.out.println(map.size());
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
//        request.body("{\"msisdn\":\"" + Data.getCustomerNumber() + "\",\"pageSize\":5,\"pageNumber\":1,\"type\":null,\"startDate\":null,\"endDate\":null}");

//        request.body("{\"msisdn\":\"731508274\"}");
        request.contentType("application/json");
        request.queryParam("msisdn", Msisdn);
        request.queryParam("walletType", "Main");

        Response response = request.get("/cs-am-service/v1/profile");
        System.out.println(response.asString());

//        System.out.println(ExtractedResp.getResult().getMsisdn());
//        System.out.println(ExtractedResp.toString());
        return response.as(AMProfilePOJO.class);
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 8, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public RechargeHistoryPOJO rechargeHistoryAPITest(String msisdn) throws JsonProcessingException {
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);

        request.body("{\"msisdn\":\"" + msisdn + "\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null,\"rechargeHistoryVoucherSearch\":null}");
        request.contentType("application/json");

        Response response = request.post("/cs-gsm-service/v1/recharge/history");
        System.out.println(response.asString());
        return response.as(RechargeHistoryPOJO.class);
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 9, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void transactionHistoryAPITest(TestDatabean Data) throws JsonProcessingException {
        baseURI = "http://172.23.36.206:30050";
        RequestSpecification request = given();
        Headers headers = new Headers(map);
        request.headers(headers);
        request.body("{\"msisdn\":\"767240995\",\"pageSize\":5,\"pageNumber\":1,\"startDate\":null,\"endDate\":null}");
        request.contentType("application/json");
        Response response = request.post("/cs-am-service/v1/transaction/history");
        System.out.println(response.asString());
//      RechargeHistoryPOJO ExtractedResp = response.as(RechargeHistoryPOJO.class);
//      System.out.println(ExtractedResp.getResult().get(0).getRechargeBenefit().toString());
//      System.out.println(ExtractedResp.toString());
    }
}
