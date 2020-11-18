package tests;

import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
        ExtentTestManager.startTest("Logging Out Of Portal", "Logging Out Of Portal");
        SoftAssert softAssert = new SoftAssert();
        loginPagePOM loginPagePOM = null;
        if (sideMenuPOM.isSideMenuVisible()) {
            sideMenuPOM.clickOnSideMenu();
            loginPagePOM = sideMenuPOM.logout();
            try {
                Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
            } catch (TimeoutException | NoSuchElementException e) {
                loginPagePOM.selectByText("Continue");
            }
            Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
        } else {
            loginPagePOM = new loginPagePOM(driver);
            Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
        }
    }
}
