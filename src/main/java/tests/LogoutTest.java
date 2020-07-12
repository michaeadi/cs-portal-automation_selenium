package tests;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.loginPagePOM;

import java.lang.reflect.Method;

public class LogoutTest extends BaseTest {
    @Test
    public void Logout(Method method) {
        SideMenuPOM sideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "SideMenu Assert");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting Side Menu Options");
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM loginPagePOM;
        if (sideMenuPOM.isSideMenuVisible()) {
            sideMenuPOM.clickOnSideMenu();
            loginPagePOM = sideMenuPOM.logout();
            Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
        } else {
            loginPagePOM = new loginPagePOM(driver);
            Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
        }
    }
}
