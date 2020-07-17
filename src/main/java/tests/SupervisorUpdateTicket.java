package tests;

import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import Utils.TicketStateDataBean;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ViewTicketPagePOM;
import pages.agentLoginPagePOM;
import pages.loginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class SupervisorUpdateTicket extends BaseTest{
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
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(),config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3,dependsOnMethods = "agentSkipQueueLogin",description = "Transfer to queue",dataProvider = "ticketState",enabled = true)
    public void updateTicket(Method method, TicketStateDataBean ticketState) throws InterruptedException{
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest(method.getName(), "Update Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
//        ticketListPage.writeTicketId(ticketId);
//        ticketListPage.clickedSearchBtn();
//        Thread.sleep(20000); // Update Particular Ticket
        String ticketId= ticketListPage.getTicketIdvalue();
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId,viewTicket.getTicketId());
        String selectedState=viewTicket.selectState(ticketState.getTicketStateName());
        Thread.sleep(20000);
        ticketListPage.changeTicketTypeToClosed();
        Thread.sleep(20000);
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickedSearchBtn();
        Thread.sleep(20000);
        softAssert.assertEquals(ticketId,ticketListPage.getTicketIdvalue());
        //softAssert.assertEquals(ticketState.getTicketStateName(),ticketListPage.getStatevalue());
        softAssert.assertEquals(selectedState,ticketListPage.getStatevalue());
        ticketListPage.changeTicketTypeToOpen();
        softAssert.assertAll();
    }
}
