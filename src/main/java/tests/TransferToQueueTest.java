package tests;

import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class TransferToQueueTest extends BaseTest {

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

    @Test(priority = 2,dependsOnMethods = "LoggingIN", description = "Supervisor Login ", dataProvider = "getTestData",enabled = true)
    public void agentSkipQueueLogin(Method method, TestDatabean Data) throws InterruptedException {
        agentLoginPagePOM AgentLoginPagePOM = new agentLoginPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Supervisor Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(30000);
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        AgentLoginPagePOM.clickSkipBtn();
        Thread.sleep(20000);
        Assert.assertEquals(driver.getTitle(),config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3,dependsOnMethods = "agentSkipQueueLogin",description = "Transfer to queue",enabled = true)
    public void transferToQueue(Method method) throws InterruptedException{
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        transferToQueuePOM transferQueue= new transferToQueuePOM(driver);
        FilterTabPOM filterTab= new FilterTabPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Transfer to queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        Thread.sleep(2000);
        filterTab.scrollToQueueFilter();
        Thread.sleep(2000);
        filterTab.clickQueueFilter();
        filterTab.selectQueueByName(config.getProperty("ticketQueue"));
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        Thread.sleep(20000);
        Assert.assertEquals(ticketListPage.getqueueValue(),config.getProperty("ticketQueue"));
        String ticketId = ticketListPage.getTicketIdvalue();
        ticketListPage.resetFilter();
        Thread.sleep(2000);
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickedSearchBtn();
        Thread.sleep(20000);
        //softAssert.assertTrue(ticketListPage.checkOpenTicketStateType());
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent());
        softAssert.assertTrue(ticketListPage.isTransferToQueue());
        ticketListPage.clickTransfertoQueue();
        softAssert.assertTrue(transferQueue.validatePageTitle());
        transferQueue.clickTransferQueue(config.getProperty("transferQueue"));
        Thread.sleep(20000);
        ticketListPage.writeTicketId(ticketId); //need Method Improvement
        ticketListPage.clickedSearchBtn();
        Thread.sleep(2000);
        Assert.assertEquals(ticketListPage.getqueueValue(),config.getProperty("transferQueue"));
        softAssert.assertAll();

    }
}
