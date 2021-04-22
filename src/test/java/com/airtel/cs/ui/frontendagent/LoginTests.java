package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTests extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Logging IN", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void LoggingIN(TestDatabean Data) throws InterruptedException {
        selUtils.addTestcaseDescription("Logging Into Portal,Logging Into Portal with AUUID :" + Data.getLoginAUUID(), "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getLoginPage().openBaseURL(config.getProperty(evnName + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(evnName + "-baseurl"), "URl isn't as expected");
        pages.getLoginPage().waitTillLoaderGetsRemoved();
        pages.getLoginPage().enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        pages.getLoginPage().clickOnSubmitBtn();
        pages.getLoginPage().enterPassword(PassUtils.decodePassword(Data.getPassword()));
        softAssert.assertTrue(pages.getLoginPage().checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnLogin();
        pages.getLoginPage().waitTillLoaderGetsRemoved();
        Thread.sleep(20000); // wait for 20 Seconds for Dashboard page In case of slow Network
        if (pages.getSideMenu().isSideMenuVisible()) {
            pages.getSideMenu().clickOnSideMenu();
            if (!pages.getSideMenu().isCustomerServicesVisible()) {
                continueExecutionFA = false;
                softAssert.fail("Customer Service Dashboard does not Assign to User.Please Assign Role to user.");
            } else {
                continueExecutionFA = true;
            }
            pages.getSideMenu().clickOnSideMenu();
        } else {
            continueExecutionFA = false;
            softAssert.fail("Customer Service Dashboard does Open with user.Check for the ScreenShot.");
        }
        softAssert.assertAll();
    }
}