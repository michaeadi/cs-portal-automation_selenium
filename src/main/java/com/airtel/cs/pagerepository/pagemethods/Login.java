package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.pagerepository.pageelements.LoginPage;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class Login extends BasePage {

    public LoginPage pageElements;
    private static final String PASSWORD = "password";

    public Login(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoginPage.class);
    }

    public boolean isEnterAUUIDFieldVisible() {
        commonLib.info("Checking is Enter AUUID field is Visible");
        return isElementVisible(pageElements.enterAUUID);
    }

    public void openBaseURL(String baseURL) {
        driver.get(baseURL);
        commonLib.info("Opening URL:- " + baseURL);
    }

    /**
     * This Method will enter the AUUID once AUUID field is visible
     *
     * @param auuid      The AUUID
     * @param timeToWait The wait time
     */
    public void enterAUUID(String auuid, int timeToWait) {
        if (isVisible(pageElements.enterAUUID, timeToWait)) {
            commonLib.info("Going to enter auuid : " + auuid + " in username");
            enterText(pageElements.enterAUUID, auuid);
        } else {
            commonLib.fail("Exception Caught in Method - enterAUUID", true);
        }
    }

    public void enterAUUID(String auuid) {
        enterAUUID(auuid, 5);
    }

    public void clickOnSubmitBtn() {
        commonLib.info("Clicking on Submit button");
        clickByJavascriptExecutor(pageElements.submitButton);
    }

    /**
     * This Method will enter the password once password field is visible
     *
     * @param password   The password
     * @param timeToWait The wait time
     */
    public void enterPassword(String password, int timeToWait) {
        if (isVisible(pageElements.enterPassword, timeToWait)) {
            commonLib.info("Going to enter password to Password field");
            enterText(pageElements.enterPassword, password);
        } else {
            commonLib.fail("Exception Caught in Method - enterPassword", true);
        }
    }

    public void enterPassword(String password) {
        enterPassword(password, 5);
    }

    public boolean checkLoginButton() {
        commonLib.info("checking login button is enabled or not");
        return isEnabled(pageElements.submitButton);
    }

    public void clickOnLogin() {
        commonLib.info("Going to click on Login button");
        click(pageElements.submitButton);
    }

    public void clickOnVisibleButton() {
        commonLib.info("Going to click on Visible Password Button");
        click(pageElements.visiblePassword);
    }

    public String getPasswordText() {
        commonLib.info("Getting text from Password field ");
        return getText(pageElements.enterPassword);
    }

    public void clickBackButton() {
        commonLib.info("Clicking on back button");
        click(pageElements.backButton);
    }

    public Boolean isLoginPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.enterAuuid));
        return isElementVisible(pageElements.enterAuuid);
    }

    /*
    This Method is used to Open New Browser to Login user under UM
     */
    public void openNewTempBrowserAndLoginInUM() {
        commonLib.infoHighlight("", "LOGGING-IN USER in 2nd TEMPORARY BROWSER", ReportInfoMessageColorList.BLUE);
        defaultDriver = driver;
        commonLib.openNewTemporaryBrowser2Nd();
        loginIntoUM();
        waitTillLoaderGetsRemoved();
    }

    /*
    With This method we can login into UM
     */
    public void loginIntoUM() {
        try {
            gotoURL(loginURL);
            pages.getLoginPage().waitTillLoaderGetsRemoved();
            pages.getLoginPage().isLoginPageDisplayed();
            pages.getLoginPage().enterAUUID(getUserNamePassword("auuid"));
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(getUserNamePassword(PASSWORD)));
            pages.getLoginPage().clickOnLogin();
            pages.getLoginPage().waitTillLoaderGetsRemoved();
        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning("Exception in method - | loginIntoUM | " + "</br>" + "Exception Message - "
                    + e.getMessage());
        }
    }

    /*
    This Method will return us the username and password mentioned in the excel file
     */
    public String getUserNamePassword(String usernameOrPassword) {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(CommonConstants.LOGIN_SHEET_NAME));
        List<String> datatPoints = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "Beta", "userType", Arrays.asList("loginAuuid", PASSWORD));
        if ("auuid".equalsIgnoreCase(usernameOrPassword)) usernameOrPassword = datatPoints.get(0);
        else if (PASSWORD.equalsIgnoreCase(usernameOrPassword)) usernameOrPassword = datatPoints.get(1);
        return usernameOrPassword;
    }

    /*
    This Method will logout and close the default browser
     */
    public void destroyDefaultBrowser() {
        pages.getSideMenuPage().logout();
        driver.close();
    }

    /*
    This Method is used to set headers
     */
    public void setApiHeader() {
        try {
            UtilsMethods.addHeaders("x-app-name", constants.getValue(ApplicationConstants.APP_NAME));
            UtilsMethods.addHeaders("x-service-id", constants.getValue(ApplicationConstants.SERVICE_ID));
            UtilsMethods.addHeaders("x-app-type", constants.getValue(ApplicationConstants.APP_TYPE));
            UtilsMethods.addHeaders("x-client-id", constants.getValue(ApplicationConstants.CLIENT_ID));
            UtilsMethods.addHeaders("x-api-key", constants.getValue(ApplicationConstants.API_KEY));
            UtilsMethods.addHeaders("x-login-module", constants.getValue(ApplicationConstants.LOGIN_MODULE));
            UtilsMethods.addHeaders("x-channel", constants.getValue(ApplicationConstants.CHANNEL_ID));
            UtilsMethods.addHeaders("x-app-version", constants.getValue(ApplicationConstants.APP_VERSION));
            UtilsMethods.addHeaders("Opco", OPCO);
        } catch (Exception e) {
            commonLib.error("Exception in Method :- setApiHeader" + e.getMessage());
        }
    }

    /*
    This Method is used to hit Login API
     */
    public Response loginAPI(String dtoAsString) {
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        commonLib.info("Request Headers are  : " + queryable.getHeaders());
        commonLib.info("Request URL : " + queryable.getURI());
        return request.post("/auth/api/user-mngmnt/v2/login");
    }
}

