package com.airtel.cs.common.requisite;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pojo.LoginPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.airtel.cs.driver.Driver;

import java.util.Arrays;
import java.util.List;


public class PreRequisites extends Driver {
    public BaseActions actions = new BaseActions();
    ObjectMapper mapper = new ObjectMapper();
    public APIEndPoints api = new APIEndPoints();


    @BeforeClass
    public void doLogin() throws InterruptedException {
        ExtentTestManager.startTest("Logging Into Portal", "Logging Into Portal with AUUID");
        pages.getLoginPage().openBaseURL(config.getProperty(Driver.Env + "-baseurl"));
        assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), config.getProperty(Driver.Env + "-baseurl"), "Login URL Opened", "Login URL not Opened"));
        pages.getLoginPage().waitTillLoaderGetsRemoved();
        pages.getLoginPage().isLoginPageDisplayed();
        pages.getLoginPage().enterAUUID(pages.getLoginPage().getUserNamePassword("auuid"));
        pages.getLoginPage().clickOnSubmitBtn();
        pages.getLoginPage().enterPassword(PassUtils.decodePassword(pages.getLoginPage().getUserNamePassword("password")));
        assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is not Enabled"));
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnLogin();
        pages.getLoginPage().waitTillLoaderGetsRemoved();
        assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPOM().isSideMenuVisible(), true, "Side Menu Visible", "Side Menu Not Visible"));
        pages.getSideMenuPOM().clickOnSideMenu();
        assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPOM().isCustomerServicesVisible(), true, "Customer Service Visible", "Customer Service Not Visible"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @BeforeClass(dependsOnMethods = "doLogin")
    public void addTokenToHeaderMap() throws JsonProcessingException {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.LOGIN_SHEET_NAME));
        List<String> datatPoints = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "API", "userType", Arrays.asList("loginAuuid", "password"));
        LoginPOJO req = LoginPOJO.loginBody(datatPoints.get(0), PassUtils.decodePassword(datatPoints.get(1)));
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);
        map.clear();
        addHeaders();
        LoginPOJO loginPOJO = api.loginPOJO(dtoAsString);
        String accessToken = loginPOJO.getResult().get("accessToken");
        String tokenType = loginPOJO.getResult().get("tokenType");
        Token = tokenType + " " + accessToken;
        UtilsMethods.addHeaders("Authorization", Token);
    }

    @AfterClass(alwaysRun = true)
    public void doLogout() {
        ExtentTestManager.startTest("Logging Out Of Portal", "Logging Out Of Portal");
        if (pages.getSideMenu().isSideMenuVisible()) {
            pages.getSideMenu().clickOnSideMenu();
            pages.getSideMenu().logout();
            try {
                Assert.assertTrue(pages.getLoginPage().isEnterAUUIDFieldVisible());
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                pages.getLoginPage().selectByText("Continue");
            }
        }
        Assert.assertTrue(pages.getLoginPage().isEnterAUUIDFieldVisible());
        pages.getLoginPage().waitTillLoaderGetsRemoved();
    }

    /*
    This Method will add headers in a Map, and then we can use in the test case
     */
    private void addHeaders() {
        UtilsMethods.addHeaders("x-app-name", config.getProperty(Env + "-x-app-name"));
        UtilsMethods.addHeaders("x-service-id", config.getProperty(Env + "-x-service-id"));
        UtilsMethods.addHeaders("x-app-type", config.getProperty(Env + "-x-app-type"));
        UtilsMethods.addHeaders("x-client-id", config.getProperty(Env + "-x-client-id"));
        UtilsMethods.addHeaders("x-api-key", config.getProperty(Env + "-x-api-key"));
        UtilsMethods.addHeaders("x-login-module", config.getProperty(Env + "-x-login-module"));
        UtilsMethods.addHeaders("x-channel", config.getProperty(Env + "-x-channel"));
        UtilsMethods.addHeaders("x-app-version", config.getProperty(Env + "-x-app-version"));
        UtilsMethods.addHeaders("Opco", OPCO);
        UtilsMethods.addHeaders("sr-client-id", constants.getValue(ApplicationConstants.SR_CLIENT_ID));
    }

    /*
    This Method is used to Login in CS Portal Again, after changing the permission over UM
     */
    public void loginInCSPortal() throws InterruptedException {
        doLogin();
        pages.getSideMenuPOM().openCustomerInteractionPage();
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
