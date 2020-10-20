package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TicketStateDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.FilterTabPOM;
import pages.SideMenuPOM;
import pages.TicketBulkUpdatePOM;
import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TicketBulkUpdateTest extends BaseTest {

    @Test(priority = 1, description = "Open Ticket Bulk Update Dashboard")
    public void openTicketBulkUpdate(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Open Ticket Bulk Update Dashboard", "Open Ticket Bulk Update Dashboard");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        TicketBulkUpdatePOM ticketBulkUpdatePOM = sideMenu.openTicketBulkUpdateDashboard();
        SoftAssert softAssert = new SoftAssert();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.isTicketBulkUpdate();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify that max 300 Tickets to be allowed to be bulk updated in one go",dependsOnMethods = "openTicketBulkUpdate")
    public void maxTicketSelectTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Verify that max 300 Tickets to be allowed to be bulk updated in one go", "Verify that max 300 Tickets to be allowed to be bulk updated in one go");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        SoftAssert softAssert=new SoftAssert();
        FilterTabPOM filterTab=ticketBulkUpdatePOM.clickSelectFilter();
        filterTab.waitTillLoaderGetsRemoved();
        filterTab.clickLast30DaysFilter();
        filterTab.clickApplyFilter();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        try {
            softAssert.assertEquals(ticketBulkUpdatePOM.getMaxSelectMessage().replaceAll("[^0-9]+", "").trim(), config.getProperty("maxBulkTicket"), "Max Ticket bulk update message not displayed");
        }catch (NullPointerException | TimeoutException e){
            e.printStackTrace();
        }
        ticketBulkUpdatePOM.clickClearFilter();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Check user able to upload ticket id from excel",dependsOnMethods = "openTicketBulkUpdate")
    public void uploadTicketFromExcelTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Check user able to upload ticket id from excel", "Check user able to upload ticket id from excel");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        DataProviders data=new DataProviders();
        SoftAssert softAssert=new SoftAssert();
        Assert.assertTrue(ticketBulkUpdatePOM.fileDownload(),"Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded");
        Assert.assertTrue(data.writeTicketNumberToExcel(),"No Ticket Found to write in Excel");
        ticketBulkUpdatePOM.addFile();
        List<String> uploadTickets=data.getTicketNumbers();
        List<String> addedTicket=ticketBulkUpdatePOM.getTicketList();
        int uploadedSize=uploadTickets.size();
        uploadTickets.removeAll(addedTicket);
        int size=uploadedSize-uploadTickets.size();
        String newString=ticketBulkUpdatePOM.getErrorMessage().replaceAll("[^a-zA-Z0-9]+", "");
        softAssert.assertEquals(String.valueOf(newString.charAt(0)),String.valueOf(size),"Uploaded ticket miscount");
        softAssert.assertEquals(String.valueOf(newString.charAt(newString.length()-1)),String.valueOf(uploadedSize),"Total number of ticket is not same as expected");
        softAssert.assertTrue(ticketBulkUpdatePOM.deleteFile(),"File not deleted");
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate Bulk option test",dependsOnMethods = "openTicketBulkUpdate")
    public void bulkOptionTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Validate Bulk option test", "Validate Bulk option test");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        DataProviders data=new DataProviders();
        SoftAssert softAssert=new SoftAssert();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.clickNextBtn();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(ticketBulkUpdatePOM.getTransferToQueueOption(),"Transfer to Queue","Transfer to Queue does not present on UI");
        softAssert.assertEquals(ticketBulkUpdatePOM.getChangeStateOption(),"Change State","Change State does not present on UI");
        softAssert.assertEquals(ticketBulkUpdatePOM.getTicketCommentOption(),"Add Ticket comment","Add Ticket comment does not present on UI");
        softAssert.assertFalse(ticketBulkUpdatePOM.isNextBtnEnable(),"Next Button enable without choosing operation");
        ArrayList<String> loginQueue = data.getLoginQueueData();
        List<String> queues= ticketBulkUpdatePOM.getQueue();
        List<TicketStateDataBean> openState=data.getState(config.getProperty("openState"));
        List<TicketStateDataBean> closeState=data.getState(config.getProperty("closeState"));
        List<String> states=ticketBulkUpdatePOM.getState();
        for (String s : queues) {
            if (loginQueue.contains(s)) {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " ticketPool is display correctly");
                loginQueue.remove(s);
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " ticketPool must not display on frontend as tag not mention in config sheet.");
                softAssert.fail(s + " ticketPool should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (loginQueue.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All ticketPool correctly configured and display on UI.");
        } else {
            for (String element : loginQueue) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, element + " ticketPool does not display on UI but present in config sheet.");
                softAssert.fail(element + " ticketPool does not display on UI but present in config sheet.");
            }
        }

        for(TicketStateDataBean s:openState){
            if(!states.contains(s.getTicketStateName().trim()))
            ExtentTestManager.getTest().log(LogStatus.FAIL,s.getTicketStateName()+" :Ticket State does not display on UI but config in excel");
            states.remove(s.getTicketStateName().trim());
        }
        for(TicketStateDataBean s:closeState){
            if(!states.contains(s.getTicketStateName().trim()))
                ExtentTestManager.getTest().log(LogStatus.FAIL,s.getTicketStateName()+" :Ticket State does not display on UI but config in excel");
            states.remove(s.getTicketStateName().trim());
        }
        for(String s:states){
            ExtentTestManager.getTest().log(LogStatus.FAIL,s+" :Ticket State does not config in excel but display on UI");
        }
        ticketBulkUpdatePOM.clickCancelBtn();
        ticketBulkUpdatePOM.clickPopUpContinueBtn();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketBulkUpdatePOM.isSelectFilter(),"Filter option does not available");
        softAssert.assertAll();
    }
}
