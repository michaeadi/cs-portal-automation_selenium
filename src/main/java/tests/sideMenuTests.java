package tests;

import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;

public class sideMenuTests extends BaseTest {
    //Pending as per users
    @Test(priority = 1, description = "Validating Side Menu ")
    public void sideMenuAssert() throws InterruptedException {
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest("Validating Side Menu", "Validating Side Menu and It's Options");
        SoftAssert softAssert = new SoftAssert();
//        Thread.sleep(10000);
        SideMenuPOM.clickOnSideMenu();
        softAssert.assertTrue(SideMenuPOM.isAdminSettingVisible());
        softAssert.assertTrue(SideMenuPOM.isCustomerServicesVisible());
//        softAssert.assertTrue(SideMenuPOM.isCaseManagementVisible());
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