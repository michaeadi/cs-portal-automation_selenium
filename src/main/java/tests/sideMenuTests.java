package tests;

import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.sideMenuPOM;

import java.lang.reflect.Method;

public class sideMenuTests extends BaseTest {

    @Test(priority = 1, description = "SideMenu ", dataProvider = "getTestData")
    public void sideMenuAssert(Method method, TestDatabean Data) throws InterruptedException {
        sideMenuPOM SideMenuPOM = new sideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "SideMenu Assert");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting Side Menu Options");
        SoftAssert softAssert = new SoftAssert();
//        Thread.sleep(30000);
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
}