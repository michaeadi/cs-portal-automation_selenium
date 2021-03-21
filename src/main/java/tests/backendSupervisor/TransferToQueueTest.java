package tests.backendSupervisor;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TransferQueueDataBean;
import Utils.ExtentReports.ExtentTestManager;
import Utils.UtilsMethods;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.frontendAgent.BaseTest;

import java.lang.reflect.Method;

public class TransferToQueueTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard(Method method) {
        ExtentTestManager.startTest("Open Supervisor Dashboard", "Open Supervisor Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dataProvider = "TransferQueue", description = "Transfer to queue", enabled = true, dataProviderClass = DataProviders.class)
    public void transferToQueue(Method method, TransferQueueDataBean data) throws InterruptedException {
        ExtentTestManager.startTest("Transfer to queue", "Transfer to queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        transferToQueuePOM transferQueue = new transferToQueuePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        try {
            ticketListPage.clickFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.scrollToQueueFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.clickQueueFilter();
            filterTab.selectQueueByName(data.getFromQueue());
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getqueueValue().trim().toLowerCase(), data.getFromQueue().toLowerCase().trim(), "Ticket Does not found with Selected State");
            softAssert.assertTrue(ticketListPage.validateQueueFilter(data.getFromQueue()), "Queue Filter Does Applied Correctly");
            try {
                ticketId = ticketListPage.getTicketIdvalue();
                ticketListPage.resetFilter();
                ticketListPage.waitTillLoaderGetsRemoved();
                ticketListPage.writeTicketId(ticketId);
                ticketListPage.clickSearchBtn();
                ticketListPage.waitTillLoaderGetsRemoved();
                ticketListPage.clickCheckbox();
                try {
                    softAssert.assertTrue(ticketListPage.isAssignToAgent(), "Assign to Agent Button Does Not Available");
                    softAssert.assertTrue(ticketListPage.isTransferToQueue(), "Transfer to Queue Button Does Not Available");
                    ticketListPage.clickTransfertoQueue();
                    softAssert.assertTrue(transferQueue.validatePageTitle(), "Page Title Does not Display");
                    transferQueue.clickTransferQueue(data.getToQueue());
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace());
                    transferQueue.clickCloseTab();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("No Ticket Found in Selected Queue to perform transfer to queue action" + e.fillInStackTrace());
            }
            ticketListPage.waitTillLoaderGetsRemoved();
            try {
                Assert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                ticketListPage.clearInputBox();
            } catch (AssertionError f) {
                f.printStackTrace();
                UtilsMethods.printInfoLog("Not able to perform transfer to Queue action: " + ticketListPage.getTransferErrorMessage());
                softAssert.assertTrue(ticketListPage.isCancelBtn(),"Cancel Button does not display.");
                if (data.getTransferAnyway().equalsIgnoreCase("true")) {
                    softAssert.assertTrue(ticketListPage.isTransferAnyWayBtn(), "Transfer Any button does not displayed.");
                    try {
                        ticketListPage.clickTransferAnyWayBtn();
                        ticketListPage.writeTicketId(ticketId);
                        ticketListPage.clickSearchBtn();
                        ticketListPage.waitTillLoaderGetsRemoved();
                        Assert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                    } catch (NoSuchElementException | TimeoutException g) {
                        softAssert.fail("Transfer Anyway does not display in case of ticket does not transfer to selected queue.");
                        ticketListPage.clickCancelBtn();
                    }
                } else {
                    ticketListPage.clickCancelBtn();
                    softAssert.fail("Transfer to queue does not Perform as per config sheet both queue belong to same workgroup.");
                }
            }
        } catch (InterruptedException | NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            e.printStackTrace();
            filterTab.clickOutsideFilter();
            filterTab.clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        ticketListPage.clearInputBox();
        softAssert.assertAll();
    }
}
