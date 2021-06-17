package com.airtel.cs.common.requisite;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.extentreports.ExtentReport;
import com.airtel.cs.pojo.response.LoginPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import com.airtel.cs.driver.Driver;
import org.testng.annotations.BeforeSuite;


public class PreRequisites extends Driver {
    public BaseActions actions = new BaseActions();
    ObjectMapper mapper = new ObjectMapper();
    public RequestSource api = new RequestSource();


    @BeforeSuite
    public void doLogin() {
        try {
            ExtentReport.startTest("PreRequisites", "doLogin");
            selUtils.addTestcaseDescription("Logging Into Portal", "Pre-Requisites");
            final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
            loginAUUID = constants.getValue(CommonConstants.ALL_USER_ROLE_AUUID);
            pages.getLoginPage().openBaseURL(value);
            assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), value, "Login URL Opened", "Login URL not Opened"));
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.ALL_USER_ROLE_PASSWORD)));
            assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is not Enabled"));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            commonLib.info("Growl was visible or not?:-" + isGrowlVisible);
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionFA = false;
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getUserManagementPage().isUserManagementPageLoaded(), true, "Customer Dashboard Page Loaded Successfully", "Customer Dashboard page NOT Loaded"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isSideMenuVisible(), true, "Side Menu Visible", "Side Menu Not Visible"));
                pages.getSideMenuPage().clickOnSideMenu();
                assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isCustomerServicesVisible(), true, "Customer Service Visible", "Customer Service Not Visible"));
                actions.assertAllFoundFailedAssert(assertCheck);
            }
        } catch (Exception e) {
            continueExecutionFA = false;
            commonLib.fail("Exception in Method - doLogin" + e.fillInStackTrace(), true);
        }
    }

    @BeforeSuite(dependsOnMethods = "doLogin")
    public void addTokenToHeaderMap() throws JsonProcessingException {
        loginAUUID = constants.getValue(CommonConstants.BETA_USER_AUUID);
        final String password = PassUtils.decodePassword(constants.getValue(CommonConstants.BETA_USER_PASSWORD));
        LoginPOJO req = LoginPOJO.loginBody(loginAUUID, password);
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);
        map.clear();
        pages.getLoginPage().setApiHeader();
        final Response response = pages.getLoginPage().loginAPI(dtoAsString);
        LoginPOJO loginPOJO = response.as(LoginPOJO.class);
        final String accessToken = loginPOJO.getResult().get("accessToken");
        assertCheck.append(actions.assertEqual_stringNotNull(accessToken, "Access Token Fetched Successfully", "Access Token is Null"));
        final String statusCode = loginPOJO.getStatusCode();
        assertCheck.append(actions.assertEqual_stringType(statusCode, "200", "Status Code Matched Successfully", "Status code NOT Matched and is :-" + statusCode));
        String tokenType = loginPOJO.getResult().get("tokenType");
        token = tokenType + " " + accessToken;
        UtilsMethods.addHeaders("Authorization", token);
        ExtentReport.endTest();
    }

    @AfterSuite(alwaysRun = true)
    public void doLogout() {
        commonLib.info("Logging Out Of Portal");
        if (pages.getSideMenuPage().isSideMenuVisible()) {
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().logout();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            assertCheck.append(actions.assertEqual_boolean(isGrowlVisible, true, "Growl Visible Successfully", "Growl Not Visible"));
            final String toastContent = pages.getGrowl().getToastContent();
            assertCheck.append(actions.assertEqual_stringType(toastContent, "You have successfully logged out", "Logout Successfully", "Logout Operation Failed and Message is :-" + toastContent));
            actions.assertAllFoundFailedAssert(assertCheck);
        } else {
            commonLib.fail("Side Menu is NOT Visible", true);
        }
    }

    /*
    This Method is used to Login in CS Portal Again, after changing the permission over UM
     */
    public void loginInCSPortal() throws InterruptedException {
        doLogin();
        pages.getSideMenuPage().openCustomerInteractionPage();
    }

    /*
    This Method will login in CS Portal and will check the Tariff Plan Option visible or not
     */
    public void goAndCheckServiceClassOptionVisible() {
        try {
            loginInCSPortal();
            pages.getMsisdnSearchPage().enterNumber(constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER));
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
        } catch (Exception e) {
            commonLib.error("Exception in Method - goAndCheckServiceClassOptionVisible " + e.getMessage());
        }
    }
}
