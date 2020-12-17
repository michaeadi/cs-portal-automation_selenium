package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import Utils.PassUtils;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.loginPagePOM;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    @DataProviders.User(UserType = "ALL")
    @Test(priority = 1, description = "Logging IN", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void LoggingIN(Method method, TestDatabean Data) {
        ExtentTestManager.startTest("Logging Into Portal", "Logging Into Portal with AUUID :  " + Data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM loginPagePOM = new loginPagePOM(driver);
        loginPagePOM.openBaseURL(config.getProperty(tests.BaseTest.Env + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(tests.BaseTest.Env + "-baseurl"), "URl isn't as expected");
        loginPagePOM.waitTillLoaderGetsRemoved();
        loginPagePOM.enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(PassUtils.decodePassword(Data.getPassword()));
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        SideMenuPOM sideMenuPOM = new SideMenuPOM(driver);
        sideMenuPOM.waitForHomePage();
        softAssert.assertTrue(sideMenuPOM.isSideMenuVisible());
        softAssert.assertAll();
    }
}