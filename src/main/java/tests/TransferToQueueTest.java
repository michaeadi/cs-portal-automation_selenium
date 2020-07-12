package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class TransferToQueueTest extends BaseTest {


    @Test(priority = 2, description = "SideMenu ", dataProvider = "getTestData", enabled = true, dataProviderClass = DataProvider.class)
    public void agentSkipQueueLogin(Method method, TestDatabean Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentSkipQueueLogin", description = "Transfer to queue", enabled = true)
    public void transferToQueue(Method method) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Transfer to queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        transferToQueuePOM transferQueue = new transferToQueuePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.scrollToQueueFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.clickQueueFilter();
        filterTab.selectQueueByName(config.getProperty("ticketQueue"));
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketListPage.validateQueueFilter(config.getProperty("ticketQueue")), "Queue Filter Successfully");
        Assert.assertEquals(ticketListPage.getqueueValue(), config.getProperty("ticketQueue"));
        String ticketId = ticketListPage.getTicketIdvalue();
        ticketListPage.resetFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        //softAssert.assertTrue(ticketListPage.checkOpenTicketStateType());
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent());
        softAssert.assertTrue(ticketListPage.isTransferToQueue());
        ticketListPage.clickTransfertoQueue();
        softAssert.assertTrue(transferQueue.validatePageTitle());
        transferQueue.clickTransferQueue(config.getProperty("transferQueue"));
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        Assert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), config.getProperty("transferQueue").toLowerCase().trim());
        softAssert.assertAll();
        //Pick data onlyThrough EXCEL
    }
}
