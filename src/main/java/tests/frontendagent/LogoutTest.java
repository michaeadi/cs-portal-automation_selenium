package tests.frontendagent;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.LoginPage;

import java.lang.reflect.Method;

public class LogoutTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }
    @Test
    public void Logout(Method method) {
        SideMenuPOM sideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest("Logging Out Of Portal", "Logging Out Of Portal");
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPagePOM = null;
        if (sideMenuPOM.isSideMenuVisible()) {
            sideMenuPOM.clickOnSideMenu();
            loginPagePOM = sideMenuPOM.logout();
            try {
                Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
            } catch (TimeoutException | NoSuchElementException |AssertionError e) {
                loginPagePOM.selectByText("Continue");
            }
            Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
        } else {
            loginPagePOM = new LoginPage(driver);
            Assert.assertTrue(loginPagePOM.isEnterAUUIDFieldVisible());
        }
        loginPagePOM.waitTillLoaderGetsRemoved();
    }
}
