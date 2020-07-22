package tests;

import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.profileManagementPOM;

import java.io.IOException;
import java.lang.reflect.Method;

public class profileManagementTest extends BaseTest {
    @Test(priority = 1, description = "Validating Profile Management")
    public void openProfileManagementPage(Method method) throws IOException {
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest("Validating Profile Management", "Validating Profile Management with Validating Filter and Columns Present ");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        profileManagementPOM profileManagement = SideMenuPOM.openProfileManagementPage();
        profileManagement.waitTillPMPageLoads();
        softAssert.assertTrue(profileManagement.isProfileConfigFilterPresent());
        softAssert.assertTrue(profileManagement.isRoleStatusFilterPresent());
        softAssert.assertEquals(profileManagement.getNumberOfColumns(), 5, "Number of columns aren't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(0), "Role Name", "Column 1 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(1), "Profile Configuration Status", "Column 2 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(2), "Role Status", "Column 3 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(3), "Role Description", "Column 4 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(4), "Action", "Column 5 isn't as expected");
        softAssert.assertAll();
    }
}
