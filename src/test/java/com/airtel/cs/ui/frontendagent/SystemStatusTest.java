package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.LoginPOJO;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SystemStatusTest extends Driver {

    @DataProviders.User(userType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 1)
    public void loginAPI(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SoftAssert softAssert = new SoftAssert();
        final String loginAUUID = data.getLoginAUUID();
        LoginPOJO Req = LoginPOJO.loginBody(PassUtils.decodePassword(data.getPassword()), loginAUUID);

        map.clear();
        UtilsMethods.addHeaders("x-app-name", config.getProperty(evnName + "-x-app-name"));
        UtilsMethods.addHeaders("x-service-id", config.getProperty(evnName + "-x-service-id"));
        //map.add(new Header("x-bsy-bn", config.getProperty(Env + "-x-bsy-bn"))); //Comment this line this header removed from MG Opco.
        UtilsMethods.addHeaders("x-app-type", config.getProperty(evnName + "-x-app-type"));
        UtilsMethods.addHeaders("x-client-id", config.getProperty(evnName + "-x-client-id"));
        UtilsMethods.addHeaders("x-api-key", config.getProperty(evnName + "-x-api-key"));
        UtilsMethods.addHeaders("x-login-module", config.getProperty(evnName + "-x-login-module"));
        UtilsMethods.addHeaders("x-channel", config.getProperty(evnName + "-x-channel"));
        UtilsMethods.addHeaders("x-app-version", config.getProperty(evnName + "-x-app-version"));
        UtilsMethods.addHeaders("Opco", OPCO);

        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        try {
            QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
            commonLib.info("Request Headers are  : " + queryable.getHeaders());
            Response response = request.post("/auth/api/user-mngmnt/v2/login");
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
            commonLib.info("Request URL : " + queryable.getURI());
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            if (response.jsonPath().getString("message").equalsIgnoreCase("Failed to authenticate user.")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Please Update Password As soon as possible if required.\nAPI Response Message: " + response.jsonPath().getString("message"));
            } else if (response.jsonPath().getString("message").toLowerCase().contains("something went wrong")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Login API Failed(Marked Build As Failed).\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (!response.jsonPath().getString("message").equalsIgnoreCase("User authenticated successfully")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Please Check the API error Message and make changes if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            }
        } catch (Exception e) {
            continueExecutionAPI = false;
            softAssert.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 2)
    public void testCredOfAdmin(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SoftAssert softAssert = new SoftAssert();
        final String loginAUUID = data.getLoginAUUID();
        LoginPOJO Req = LoginPOJO.loginBody(PassUtils.decodePassword(data.getPassword()), loginAUUID);
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Checking System Status for Admin User,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        try {
            QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
            commonLib.info("Request Headers are  : " + queryable.getHeaders());
            Response response = request.post("/auth/api/user-mngmnt/v2/login");
            commonLib.info("Request URL : " + queryable.getURI());
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            if (response.jsonPath().getString("message").equalsIgnoreCase("Failed to authenticate user.")) {
                continueExecutionFA = false;
                continueExecutionBS = false;
                softAssert.fail("Not able to generate Token. Please Update Password As soon as possible if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (response.jsonPath().getString("message").toLowerCase().contains("something went wrong")) {
                continueExecutionFA = false;
                continueExecutionBS = false;
                softAssert.fail("Not able to generate Token. Login com.airtel.cs.API Failed(Marked Build As Failed).\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (!response.jsonPath().getString("message").equalsIgnoreCase("User authenticated successfully")) {
                continueExecutionFA = false;
                continueExecutionBS = false;
                softAssert.fail("Not able to generate Token. Please Check the com.airtel.cs.API error Message and make changes if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            }
        } catch (Exception e) {
            continueExecutionFA = false;
            continueExecutionBS = false;
            softAssert.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "BA")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 3)
    public void testCredOfBackendAgent(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SoftAssert softAssert = new SoftAssert();
        final String loginAUUID = data.getLoginAUUID();
        LoginPOJO Req = LoginPOJO.loginBody(PassUtils.decodePassword(data.getPassword()), loginAUUID);
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Checking System Status for Backend Agent User,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        try {
            QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
            commonLib.info("Request Headers are  : " + queryable.getHeaders());
            Response response = request.post("/auth/api/user-mngmnt/v2/login");
            commonLib.info("Request URL : " + queryable.getURI());
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            if (response.jsonPath().getString("message").equalsIgnoreCase("Failed to authenticate user.")) {
                continueExecutionBA = false;
                softAssert.fail("Not able to generate Token. Please Update Password As soon as possible if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (response.jsonPath().getString("message").toLowerCase().contains("something went wrong")) {
                continueExecutionBA = false;
                softAssert.fail("Not able to generate Token. Login com.airtel.cs.API Failed(Marked Build As Failed).\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (!response.jsonPath().getString("message").equalsIgnoreCase("User authenticated successfully")) {
                continueExecutionBA = false;
                softAssert.fail("Not able to generate Token. Please Check the com.airtel.cs.API error Message and make changes if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            }
        } catch (Exception e) {
            continueExecutionBA = false;
            softAssert.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
