package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TransferQueueDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class TransferToQueueTest extends BaseTest {

    @Test(priority = 1, dataProvider = "TransferQueue", description = "Transfer to queue", enabled = true, dataProviderClass = DataProviders.class)
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
            softAssert.assertTrue(ticketListPage.validateQueueFilter(data.getFromQueue()), "Queue Filter Does Applied Correctly");
            Assert.assertEquals(ticketListPage.getqueueValue().trim().toLowerCase(), data.getFromQueue().toLowerCase().trim(), "Ticket Does not found with Selected State");
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
                    softAssert.assertTrue(transferQueue.validatePageTitle(),"Page Title Does not Display");
                    transferQueue.clickTransferQueue(data.getToQueue());
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace());
                    transferQueue.clickCloseTab();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("No Ticket Found in Selected Queue to perform transfer to queue action" + e.fillInStackTrace());
            }
            ticketListPage.waitTillLoaderGetsRemoved();
            ticketListPage.writeTicketId(ticketId);
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
        } catch (InterruptedException | NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            e.printStackTrace();
            filterTab.clickOutsideFilter();
            filterTab.clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
