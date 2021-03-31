package tests.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.PassUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.LoginPage;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "ALL")
    @Test(priority = 1, description = "Logging IN", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void loggingIN(Method method, TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Logging Into Portal", "Logging Into Portal with AUUID :  " + data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPagePOM = new LoginPage(driver);
        loginPagePOM.openBaseURL(config.getProperty(BaseTest.Env + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(BaseTest.Env + "-baseurl"), "URl isn't as expected");
        loginPagePOM.waitTillLoaderGetsRemoved();
        loginPagePOM.enterAUUID(data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(PassUtils.decodePassword(data.getPassword()));
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        loginPagePOM.waitTillLoaderGetsRemoved();
        SideMenuPOM sideMenuPOM = new SideMenuPOM(driver);
        Thread.sleep(20000); // wait for 20 Seconds for Dashboard page In case of slow Network slow
        if(sideMenuPOM.isSideMenuVisible()){
            sideMenuPOM.clickOnSideMenu();
            if (!sideMenuPOM.isCustomerServicesVisible()) {
                continueExecutionFA = false;
                softAssert.fail("Customer Service Dashboard does not Assign to User.Please Assign Role to user.");
            } else {
                continueExecutionFA = true;
            }
            sideMenuPOM.clickOnSideMenu();
        }else {
            continueExecutionFA = false;
            softAssert.fail("Customer Service Dashboard does Open with user.Check for the ScreenShot.");
        }
        softAssert.assertAll();
    }
}