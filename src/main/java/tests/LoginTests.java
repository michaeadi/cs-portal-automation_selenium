package tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;
import pages.loginPagePOM;
import pages.sideMenuPOM;
import utils.ExtentReports.ExtentTestManager;
import utils.TestDatabean;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {

    @Test(priority = 1, description = "Logging IN ", dataProvider = "getTestData")
    public void LoggingIN(Method method, TestDatabean Data) {
        loginPagePOM loginPagePOM = new loginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM.openBaseURL(config.getProperty("baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty("baseurl"), "URl isn't as expected");
        loginPagePOM.enterAUUID(Data.getLoginAUUID());//*[@id="mat-input-7"]
        loginPagePOM.clickOnSubmitBtn();
        loginPagePOM.enterPassword(Data.getPassword());
        softAssert.assertTrue(loginPagePOM.checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnVisibleButton();
        loginPagePOM.clickOnLogin();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "SideMenu ", dataProvider = "getTestData")
    public void sideMenuAssert(Method method, TestDatabean Data) throws InterruptedException {
        sideMenuPOM SideMenuPOM = new sideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(30000);
        SideMenuPOM.clickOnSideMenu();
        softAssert.assertTrue(SideMenuPOM.isAdminSettingVisible());
        softAssert.assertTrue(SideMenuPOM.isCustomerServicesVisible());
        softAssert.assertTrue(SideMenuPOM.isCaseManagementVisible());
        softAssert.assertTrue(SideMenuPOM.isUserManagementVisible());
        softAssert.assertTrue(SideMenuPOM.isProfileManagementVisible());
        SideMenuPOM.clickOnName();
//        SideMenuPOM.clickOnSideMenu();
        softAssert.assertTrue(SideMenuPOM.isCustomerInteractionVisible());
        softAssert.assertTrue(SideMenuPOM.isSupervisorDashboardVisible());
        SideMenuPOM.clickOnSideMenu();
        softAssert.assertAll();

    }

    @Test(priority = 3, description = "SideMenu ", dataProvider = "getTestData")
    public void openCustomerInteraction(Method method, TestDatabean Data) throws InterruptedException {
        sideMenuPOM SideMenuPOM = new sideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber("735873718");
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        System.out.println(customerInteractionPagePOM.getCustomerName());
        System.out.println(customerInteractionPagePOM.getCustomerDOB());
        System.out.println(customerInteractionPagePOM.getActivationDate());
        System.out.println(customerInteractionPagePOM.getActivationTime());
        System.out.println(customerInteractionPagePOM.getSimNumber());
        System.out.println(customerInteractionPagePOM.getSimType());
        System.out.println(customerInteractionPagePOM.getPUK1());
        System.out.println(customerInteractionPagePOM.getPUK2());
        System.out.println(customerInteractionPagePOM.getIdType());
        System.out.println(customerInteractionPagePOM.getIdNumber());
        softAssert.assertAll();

    }
}