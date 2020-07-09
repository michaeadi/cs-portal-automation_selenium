package tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.agentLoginPagePOM;
import pages.loginPagePOM;
import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import java.lang.reflect.Method;

public class SupervisorLoginTest extends BaseTest {

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

    @Test(priority = 2, description = "SideMenu ", dataProvider = "getTestData",enabled = false)
    public void agentSkipQueueLogin(Method method, TestDatabean Data) throws InterruptedException {
        agentLoginPagePOM AgentLoginPagePOM = new agentLoginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(30000);
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSkipBtn();
        Thread.sleep(10000);
        Assert.assertEquals(driver.getTitle(),config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "SideMenu ", dataProvider = "getTestData", enabled = true)
    public void agentQueueLogin(Method method, TestDatabean Data) throws InterruptedException {
        agentLoginPagePOM AgentLoginPagePOM = new agentLoginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(30000);
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSelectQueue();
        AgentLoginPagePOM.selectAllQueue();
        AgentLoginPagePOM.clickSubmitBtn();
        Thread.sleep(20000);
        Assert.assertEquals(driver.getTitle(),config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

}
