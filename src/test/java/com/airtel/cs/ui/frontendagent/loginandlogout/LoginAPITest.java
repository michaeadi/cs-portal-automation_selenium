package com.airtel.cs.ui.frontendagent.loginandlogout;

import com.airtel.cs.commonutils.utils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.login.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginAPITest extends Driver {

    ObjectMapper mapper = new ObjectMapper();

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testLoginAPIWithAdvisorSupervisor() {
        try {
            selUtils.addTestcaseDescription("Validate the Login API with Beta user,Hit the Login API -/auth/api/user-mngmnt/v2/login with valid headers and credentials,Validating Success Message from response", "description");
            final String loginAUUID = constants.getValue(CommonConstants.ADVISOR_USER_ROLE_AUUID);
            Login Req = Login.loginBody(loginAUUID, PassUtils.decodePassword(constants.getValue(CommonConstants.ADVISOR_USER_ROLE_PASSWORD)));
            map.clear();
            pages.getLoginPage().setCsAndDownstreamApiHeader();
            String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
            commonLib.info("Validating login api with user : " + loginAUUID);
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqualStringType(message, "User authenticated successfully", "User authenticated successfully", message, false));
        } catch (Exception e) {
            continueExecutionAPI = false;
            commonLib.fail("Exception in Method :- testLoginAPIWithAdvisorSupervisor " + e.fillInStackTrace(), false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testLoginApiWithBetaUser() {
        try {
            selUtils.addTestcaseDescription("Validate the Login API with User Having all the roles,Hit the Login API -/auth/api/user-mngmnt/v2/login with valid headers and credentials,Validating Success Message from response", "description");
            final String loginAUUID = constants.getValue(CommonConstants.BETA_USER_ROLE_AUUID);
            Login Req = Login.loginBody(loginAUUID, PassUtils.decodePassword(constants.getValue(CommonConstants.BETA_USER_ROLE_PASSWORD)));
            String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
            commonLib.info("Validating login api with user : " + loginAUUID);
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqualStringType(message, "User authenticated successfully", "User authenticated successfully", message, false));
        } catch (Exception e) {
            continueExecutionFA = false;
            continueExecutionBS = false;
            commonLib.fail("Exception in Method :- testLoginApiWithBetaUser " + e.fillInStackTrace(), false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testLoginApiWithBackendAgent() {
        try {
            selUtils.addTestcaseDescription("Validate the Login API with Backend Agent valid credentials,Hit the Login API -/auth/api/user-mngmnt/v2/login with valid headers and credentials,Validating Success Message from response", "description");
            final String loginAUUID = constants.getValue(CommonConstants.BA_USER_AUUID);
            Login Req = Login.loginBody(loginAUUID, PassUtils.decodePassword(constants.getValue(CommonConstants.BA_USER_PASSWORD)));
            String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
            commonLib.info("Validating login api with user : " + loginAUUID);
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqualStringType(message, "User authenticated successfully", "User authenticated successfully", message, false));
        } catch (Exception e) {
            continueExecutionBA = false;
            commonLib.fail("Exception in Method :- testLoginApiWithBackendAgent " + e.fillInStackTrace(), false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
