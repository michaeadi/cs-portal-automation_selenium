package tests.ticketBulkUpdate;

import com.airtel.cs.commonutils.DataProviders.DataProviders;
import com.airtel.cs.commonutils.DataProviders.TicketStateDataBean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.FilterTabPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.TicketBulkUpdatePOM;
import tests.frontendagent.BaseTest;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TicketBulkUpdateTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Open Ticket Bulk Update Dashboard")
    public void openTicketBulkUpdate(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Open Ticket Bulk Update Dashboard", "Open Ticket Bulk Update Dashboard");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        TicketBulkUpdatePOM ticketBulkUpdatePOM = sideMenu.openTicketBulkUpdateDashboard();
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(10000);
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.isTicketBulkUpdate();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify that max 300 Tickets to be allowed to be bulk updated in one go", dependsOnMethods = "openTicketBulkUpdate")
    public void maxTicketSelectTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Verify that max 300 Tickets to be allowed to be bulk updated in one go", "Verify that max 300 Tickets to be allowed to be bulk updated in one go");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        try {
            FilterTabPOM filterTab = ticketBulkUpdatePOM.clickSelectFilter();
            filterTab.waitTillLoaderGetsRemoved();
            filterTab.clickLast30DaysFilter();
            filterTab.clickApplyFilter();
            try {
                String message=ticketBulkUpdatePOM.getToastMessage();
                if(message.equalsIgnoreCase("Bad Gateway") | message.contains("Unknown Error")){
                    softAssert.fail("Toast Message Appeared: "+message);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                //continue
            }
            ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(ticketBulkUpdatePOM.isClearFilterButton(),"Clear Filter button does not display after selecting filter.");
                softAssert.assertEquals(ticketBulkUpdatePOM.getMaxSelectMessage().replaceAll("[^0-9]+", "").trim(), config.getProperty("maxBulkTicket"), "Max Ticket bulk update message not displayed");
            } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
                String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                        getScreenshotAs(OutputType.BASE64);

                //ExtentReports log and screenshot operations for failed tests.
                ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
                softAssert.fail("Max Ticket Message does not display. " + e.fillInStackTrace());
            }
            ticketBulkUpdatePOM.clickClearFilter();
        } catch (NoSuchElementException | TimeoutException e) {
            ticketBulkUpdatePOM.clickCloseFilter();
            softAssert.fail("Not able to apply filter: " + e.fillInStackTrace());
        }
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Check user able to upload ticket id from excel", dependsOnMethods = "openTicketBulkUpdate")
    public void uploadTicketFromExcelTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Check user able to upload ticket id from excel", "Check user able to upload ticket id from excel");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        DataProviders data = new DataProviders();
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(ticketBulkUpdatePOM.fileDownload(), "Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded");
        Assert.assertTrue(data.writeTicketNumberToExcel(), "No Ticket Found to write in Excel");
        ticketBulkUpdatePOM.addFile();
        try {
            String message=ticketBulkUpdatePOM.getToastMessage();
            if(message.equalsIgnoreCase("Bad Gateway") | message.contains("Unknown Error")){
                softAssert.fail("Toast Message Appeared: "+message);
            }
        } catch (NoSuchElementException | TimeoutException e) {
            //continue
        }
        try {
            ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
            List<String> uploadTickets = data.getTicketNumbers();
            List<String> addedTicket = ticketBulkUpdatePOM.getTicketList();
            int uploadedSize = uploadTickets.size();
            uploadTickets.removeAll(addedTicket);
            int size = uploadedSize - uploadTickets.size();
            String[] newString = ticketBulkUpdatePOM.getErrorMessage().replaceAll("[^0-9\\s]+","").trim().split(" ");
            softAssert.assertEquals(newString[0], String.valueOf(size), "Uploaded ticket miscount");
            softAssert.assertEquals(newString[newString.length - 1], String.valueOf(uploadedSize), "Total number of ticket is not same as expected");
            softAssert.assertTrue(ticketBulkUpdatePOM.deleteFile(), "File not deleted");
        }catch (NoSuchElementException | TimeoutException e){
            e.printStackTrace();
            softAssert.fail("Not able to validate Upload Ticket id from excel. Due to error: "+e.getMessage());
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate Bulk option test", dependsOnMethods = "uploadTicketFromExcelTest")
    public void bulkOptionTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Validate Bulk option test", "Validate Bulk option test");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        DataProviders data = new DataProviders();
        SoftAssert softAssert = new SoftAssert();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.clickNextBtn();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(ticketBulkUpdatePOM.getTransferToQueueOption(), "Transfer to Queue", "Transfer to Queue does not present on UI");
        softAssert.assertEquals(ticketBulkUpdatePOM.getChangeStateOption(), "Change State", "Change State does not present on UI");
        softAssert.assertEquals(ticketBulkUpdatePOM.getTicketCommentOption(), "Add Ticket comment", "Add Ticket comment does not present on UI");
        softAssert.assertFalse(ticketBulkUpdatePOM.isNextBtnEnable(), "Next Button enable without choosing operation");
        ArrayList<String> loginQueue = data.getLoginQueueData();
        List<String> queues = ticketBulkUpdatePOM.getQueue();
        List<TicketStateDataBean> openState = data.getState(config.getProperty("openState"));
        List<TicketStateDataBean> closeState = data.getState(config.getProperty("closeState"));
        List<String> states = ticketBulkUpdatePOM.getState();
        for (String s : queues) {
            if (loginQueue.contains(s)) {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " ticketPool is display correctly");
                loginQueue.remove(s);
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " ticketPool must not display on frontend as tag not mention in config sheet.");
                softAssert.fail(s + " ticketPool should not display on UI as ticket pool not mention in config sheet.");
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

        for (TicketStateDataBean s : openState) {
            if (!states.contains(s.getTicketStateName().trim().toLowerCase()))
                ExtentTestManager.getTest().log(LogStatus.FAIL, s.getTicketStateName() + " :Ticket State does not display on UI but config in excel");
            states.remove(s.getTicketStateName().trim().toLowerCase());
        }
        for (TicketStateDataBean s : closeState) {
            if (!states.contains(s.getTicketStateName().trim().toLowerCase()))
                ExtentTestManager.getTest().log(LogStatus.FAIL, s.getTicketStateName() + " :Ticket State does not display on UI but config in excel");
            states.remove(s.getTicketStateName().trim().toLowerCase());
        }
        for (String s : states) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :Ticket State does not config in excel but display on UI");
        }
        ticketBulkUpdatePOM.clickCancelBtn();
        ticketBulkUpdatePOM.clickPopUpCancelBtn();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
//        softAssert.assertTrue(ticketBulkUpdatePOM.isSelectFilter(),"Filter option does not available");
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Add comment on ticket using bulk update feature", dependsOnMethods = "uploadTicketFromExcelTest")
    public void bulkAddCommentTest(Method method) {
        ExtentTestManager.startTest("Add comment on ticket using bulk update feature", "Add comment on ticket using bulk update feature");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        DataProviders data = new DataProviders();
        SoftAssert softAssert = new SoftAssert();
        Integer size = null;
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.clickAddCommentOption();
        ticketBulkUpdatePOM.addComment("Adding Comment using Automation for Bulk Update option test. Please Ignore this comment");
        ticketBulkUpdatePOM.clickConfirmAction();
        ticketBulkUpdatePOM.clickNextBtn();
        softAssert.assertTrue(ticketBulkUpdatePOM.isStatusBarComplete(), "Status not update for all ticket");
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        softAssert.assertEquals(ticketBulkUpdatePOM.getErrorTicketCount(), Integer.parseInt(ticketBulkUpdatePOM.getErrorCount()), "Error Ticket count does not displayed correctly");
        try {
            String[] array=ticketBulkUpdatePOM.getUpdatedMessage().replaceAll("[^0-9\\s]+","").trim().split(" ");
            size = Integer.parseInt(array[0]) - ticketBulkUpdatePOM.getErrorTicketCount();
        } catch (NumberFormatException e) {
            softAssert.fail("Not able to read message properly " + e.fillInStackTrace());
        }
        softAssert.assertEquals(ticketBulkUpdatePOM.getSuccessCount(), size, "Action does not Performed on all uploaded ticket");
        softAssert.assertAll();
    }
}
