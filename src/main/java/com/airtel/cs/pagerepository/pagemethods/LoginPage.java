package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.enums.ReportInfoMessageColorList;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.pagerepository.pageelements.LoginPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class LoginPage extends BasePage {

    public LoginPageElements pageElements = null;

    public LoginPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoginPageElements.class);
    }

    public boolean isEnterAUUIDFieldVisible() {
        UtilsMethods.printInfoLog("Checking is Enter AUUID field is Visible");
        return isElementVisible(pageElements.enterAUUID);
    }

    public void openBaseURL(String baseURL) {
        driver.get(baseURL);
        UtilsMethods.printInfoLog("Opening URL:-" + baseURL);
    }

    public void enterAUUID(String auuid) {
        UtilsMethods.printInfoLog("Entering auuid :" + auuid + "In username");
        writeText(pageElements.enterAUUID, auuid);

    }

    public void clickOnSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking on Submit button");
        click(pageElements.submitButton);
    }

    public void enterPassword(String password) {
        UtilsMethods.printInfoLog("Send password to Password field");
        writeText(pageElements.enterPassword, password);
    }

    public boolean checkLoginButton() {
        UtilsMethods.printInfoLog("checking login button is enabled or not");
        return checkState(pageElements.submitButton);
    }

    public void clickOnLogin() {
        UtilsMethods.printInfoLog("Clicking on Login button");
        click(pageElements.submitButton);
    }

    public void clickOnVisibleButton() {
        UtilsMethods.printInfoLog("Clicking on Visible Password Button");
        click(pageElements.visiblePassword);
    }

    public String getPasswordText() {
        UtilsMethods.printInfoLog("getting text from Password field ");
        return readText(pageElements.enterPassword);
    }

    public void clickBackButton() {
        UtilsMethods.printInfoLog("Clicking on back button");
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
        default_Driver = driver;
        commonLib.openNewTemporaryBrowser2Nd();
        loginIntoUM();
        waitTillLoaderGetsRemoved();
    }

    /*
    With This method we can login into UM
     */
    public boolean loginIntoUM() {
        boolean isLoginSuccessful = false;
        try {
            gotoURL(loginURL);
            pages.getLoginPage().waitTillLoaderGetsRemoved();
            pages.getLoginPage().isLoginPageDisplayed();
            pages.getLoginPage().enterAUUID(getUserNamePassword("auuid"));
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(getUserNamePassword("password")));
            pages.getLoginPage().clickOnLogin();
            pages.getLoginPage().waitTillLoaderGetsRemoved();
            isLoginSuccessful = true;

        } catch (Exception e) {
            e.getStackTrace();
            commonLib.warning("Exception in method - | loginIntoUM | " + "</br>" + "Exception Message - "
                    + e.getMessage());
        }
        return isLoginSuccessful;
    }

    /*
    This Method will return us the username and password mentioned in the excel file
     */
    public String getUserNamePassword(String usernameOrPassword) {
        recordset = DataProviders.readExcelSheet(excelPath, constants.getValue(ApplicationConstants.LOGIN_SHEET_NAME));
        List<String> datatPoints = DataProviders.getScenarioDetailsFromExcelSheetColumnWise(recordset, "Beta", "userType", Arrays.asList("loginAuuid", "password"));
        if ("auuid".equalsIgnoreCase(usernameOrPassword)) usernameOrPassword = datatPoints.get(0);
        else if ("password".equalsIgnoreCase(usernameOrPassword)) usernameOrPassword = datatPoints.get(1);
        return usernameOrPassword;
    }

    /*
    This Method will logout and close the default browser
     */
    public void destroyDefaultBrowser() {
        pages.getSideMenuPOM().logout();
        driver.close();
    }
}

