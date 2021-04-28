package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
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

import java.util.concurrent.TimeUnit;

public class SystemStatusTest extends Driver {

    private final BaseActions actions = new BaseActions();


    @DataProviders.User(userType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 1)
    public void loginAPI(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
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
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqual_stringType(message, "User authenticated successfully", "User authenticated successfully", message));
        } catch (Exception e) {
            continueExecutionAPI = false;
            commonLib.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User()
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 2)
    public void testCredOfAdmin(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        final String loginAUUID = data.getLoginAUUID();
        LoginPOJO Req = LoginPOJO.loginBody(loginAUUID, PassUtils.decodePassword(data.getPassword()));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Checking System Status for Admin User,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        try {
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqual_stringType(message,"User authenticated successfully","User authenticated successfully", message));
        } catch (Exception e) {
            continueExecutionFA = false;
            continueExecutionBS = false;
            commonLib.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User(userType = "BA")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 3)
    public void testCredOfBackendAgent(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        final String loginAUUID = data.getLoginAUUID();
        LoginPOJO Req = LoginPOJO.loginBody(loginAUUID, PassUtils.decodePassword(data.getPassword()));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        selUtils.addTestcaseDescription("Checking System Status for Backend Agent User,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID, "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + loginAUUID);
        try {
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqual_stringType(message,"User authenticated successfully","User authenticated successfully", message));
        } catch (Exception e) {
            continueExecutionBA = false;
            commonLib.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
