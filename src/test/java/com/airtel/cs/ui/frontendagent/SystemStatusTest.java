package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.LoginPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

public class SystemStatusTest extends Driver {

    @DataProviders.User(userType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 1)
    public void loginAPI(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SoftAssert softAssert = new SoftAssert();
        final String loginAUUID = data.getLoginAUUID();
        LoginPOJO Req = LoginPOJO.loginBody(loginAUUID, PassUtils.decodePassword(data.getPassword()));
        selUtils.addTestcaseDescription("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        map.clear();
        pages.getLoginPage().setApiHeader();
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        try {
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
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
        LoginPOJO Req = LoginPOJO.loginBody(loginAUUID, PassUtils.decodePassword(data.getPassword()));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Checking System Status for Admin User,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        try {
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
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
        LoginPOJO Req = LoginPOJO.loginBody(loginAUUID, PassUtils.decodePassword(data.getPassword()));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Checking System Status for Backend Agent User,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        try {
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
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
