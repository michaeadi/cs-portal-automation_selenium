package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.extentreports.ExtentReport;
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

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class Login extends BasePage {

    public LoginPage pageElements;
    private static final String PASSWORD = "password";

    public Login(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoginPage.class);
    }

    /**
     * This method use to check enter auuid field visible or not
     *
     * @return true/false
     */
    public boolean isEnterAUUIDFieldVisible() {
        commonLib.info("Checking is Enter AUUID field is Visible");
        return isElementVisible(pageElements.enterAUUID);
    }

    /**
     * This method use to open url
     *
     * @param baseURL The URL
     */
    public void openBaseURL(String baseURL) {
        driver.get(baseURL);
        commonLib.info("Opening URL:- " + baseURL);
    }

    /**
     * This Method will enter the AUUID once AUUID field is visible
     *
     * @param auuid The AUUID
     */
    public void enterAUUID(String auuid) {
        if (isVisible(pageElements.enterAUUID) && isClickable(pageElements.enterAUUID)) {
            commonLib.info("Going to enter auuid : " + auuid + " in username");
            enterText(pageElements.enterAUUID, auuid);
        } else {
            commonLib.fail("Exception Caught in Method - enterAUUID", true);
        }
    }


    /**
     * This method use to click submit button
     */
    public void clickOnSubmitBtn() {
        commonLib.info("Clicking on Submit button");
        clickAndWaitForLoaderToBeRemoved(pageElements.submitButton);
    }

    /**
     * This Method will enter the password once password field is visible
     *
     * @param password The password
     */
    public void enterPassword(String password) {
        if (isVisible(pageElements.enterPassword) && isClickable(pageElements.enterPassword)) {
            commonLib.info("Going to enter password to Password field");
            enterText(pageElements.enterPassword, password);
        } else {
            commonLib.fail("Exception Caught in Method - enterPassword", true);
        }
    }

    /**
     * This method use to check login button enable or not
     *
     * @return true/false
     */
    public boolean checkLoginButton() {
        commonLib.info("checking login button is enabled or not");
        return isEnabled(pageElements.submitButton);
    }

    /**
     * This method is use to click login button
     */
    public void clickOnLogin() {
        commonLib.info("Going to click on Login button");
        clickWithoutLoader(pageElements.submitButton);
    }

    /**
     * This method is use to click hide/show icon in password field
     */
    public void clickOnVisibleButton() {
        commonLib.info("Going to click on Visible Password Button");
        if (isVisible(pageElements.visiblePassword))
            clickWithoutLoader(pageElements.visiblePassword);
        else
            commonLib.fail("Exception in method - clickOnVisibleButton", true);
    }

    /**
     * This method is use to click back button
     */
    public void clickBackButton() {
        commonLib.info("Clicking on back button");
        clickAndWaitForLoaderToBeRemoved(pageElements.backButton);
    }

    /**
     * This method is use to check login page display or not
     *
     * @return true/false
     */
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
            //for ESB downstream APIs
            UtilsMethods.addHeaders("ASP-OPCO", OPCO);
            UtilsMethods.addHeaders("ASP-API-Key", constants.getValue("asp.api.key"));
            UtilsMethods.addHeaders("ASP-Consumer-Id", constants.getValue("asp.consumer.id"));
            UtilsMethods.addHeaders("Host", constants.getValue("asp.host") + OPCO.toLowerCase());
            UtilsMethods.addHeaders("ASP-Locale", constants.getValue("asp.locale"));
            UtilsMethods.addHeaders("ASP-Req-Timestamp", String.valueOf(System.currentTimeMillis()));
            UtilsMethods.addHeaders("X-Api-Client", constants.getValue("hlr.x.api.client"));
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

    /*
   This Method is used to Login in CS Portal Again, after changing the permission over UM
    */
    public void loginInCSPortal() throws InterruptedException {
        doLoginInCSPortal();
        pages.getSideMenuPage().openCustomerInteractionPage();
    }

    public void doLoginInCSPortal() {
        try {
            ExtentReport.startTest("PreRequisites", "doLogin");
            selUtils.addTestcaseDescription("Logging Into Portal", "Pre-Requisites");
            final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
            loginAUUID = constants.getValue(CommonConstants.ALL_USER_ROLE_AUUID);
            pages.getLoginPage().openBaseURL(value);
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.ALL_USER_ROLE_PASSWORD)));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            commonLib.info("Growl was visible or not?:-" + isGrowlVisible);
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionFA = false;
            } else {
                pages.getSideMenuPage().clickOnSideMenu();
            }
        } catch (Exception e) {
            continueExecutionFA = false;
            commonLib.fail("Exception in Method - doLogin" + e.fillInStackTrace(), true);
        }
    }
}

