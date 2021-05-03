package com.airtel.cs.ui.ticketBulkUpdate;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.FilterTab;
import com.airtel.cs.pagerepository.pagemethods.TicketBulkUpdate;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TicketBulkUpdateTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1, description = "Open Ticket Bulk Update Dashboard")
    public void openTicketBulkUpdate() throws InterruptedException {
        selUtils.addTestcaseDescription("Open Ticket Bulk Update Dashboard", "description");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        TicketBulkUpdate ticketBulkUpdate = pages.getSideMenuPage().openTicketBulkUpdateDashboard();
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(10000);
        ticketBulkUpdate.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketBulkUpdate.isTicketBulkUpdate(), "Ticket Bulk Update page does not open.");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify that max 300 Tickets to be allowed to be bulk updated in one go", dependsOnMethods = "openTicketBulkUpdate")
    public void maxTicketSelectTest() {
        selUtils.addTestcaseDescription("Verify that max 300 Tickets to be allowed to be bulk updated in one go", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            FilterTab filterTab = pages.getTicketBulkUpdate().clickSelectFilter();
            filterTab.waitTillLoaderGetsRemoved();
            filterTab.clickLast30DaysFilter();
            filterTab.clickApplyFilter();
            try {
                String message = pages.getTicketBulkUpdate().getToastMessage();
                if (message.equalsIgnoreCase("Bad Gateway") || message.contains("Unknown Error")) {
                    softAssert.fail("Toast Message Appeared: " + message);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                //continue
            }
            pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(pages.getTicketBulkUpdate().isClearFilterButton(), "Clear Filter button does not display after selecting filter.");
                softAssert.assertEquals(pages.getTicketBulkUpdate().getMaxSelectMessage().replaceAll("[^0-9]+", "").trim(), config.getProperty("maxBulkTicket"), "Max Ticket bulk update message not displayed");
            } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
                String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                        getScreenshotAs(OutputType.BASE64);

                //ExtentReports log and screenshot operations for failed tests.
                commonLib.fail("Test Failed", true);
                softAssert.fail("Max Ticket Message does not display. " + e.fillInStackTrace());
            }
            pages.getTicketBulkUpdate().clickClearFilter();
        } catch (NoSuchElementException | TimeoutException e) {
            pages.getTicketBulkUpdate().clickCloseFilter();
            softAssert.fail("Not able to apply filter: " + e.fillInStackTrace());
        }
        pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Check user able to upload ticket id from excel", dependsOnMethods = "openTicketBulkUpdate")
    public void uploadTicketFromExcelTest() throws IOException, InterruptedException, AWTException {
        selUtils.addTestcaseDescription("Check user able to upload ticket id from excel", "description");
        DataProviders data = new DataProviders();
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(pages.getTicketBulkUpdate().fileDownload(), "Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded");
        Assert.assertTrue(data.writeTicketNumberToExcel(), "No Ticket Found to write in Excel");
        pages.getTicketBulkUpdate().addFile();
        try {
            String message = pages.getTicketBulkUpdate().getToastMessage();
            if (message.equalsIgnoreCase("Bad Gateway") || message.contains("Unknown Error")) {
                softAssert.fail("Toast Message Appeared: " + message);
            }
        } catch (NoSuchElementException | TimeoutException e) {
            //continue
        }
        try {
            pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
            List<String> uploadTickets = data.getTicketNumbers();
            List<String> addedTicket = pages.getTicketBulkUpdate().getTicketList();
            int uploadedSize = uploadTickets.size();
            uploadTickets.removeAll(addedTicket);
            int size = uploadedSize - uploadTickets.size();
            String[] newString = pages.getTicketBulkUpdate().getErrorMessage().replaceAll("[^0-9\\s]+", "").trim().split(" ");
            softAssert.assertEquals(newString[0], String.valueOf(size), "Uploaded ticket miscount");
            softAssert.assertEquals(newString[newString.length - 1], String.valueOf(uploadedSize), "Total number of ticket is not same as expected");
            softAssert.assertTrue(pages.getTicketBulkUpdate().deleteFile(), "File not deleted");
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            softAssert.fail("Not able to validate Upload Ticket id from excel. Due to error: " + e.getMessage());
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate Bulk option test", dependsOnMethods = "uploadTicketFromExcelTest")
    public void bulkOptionTest() {
        selUtils.addTestcaseDescription("Validate Bulk option test", "description");
        DataProviders data = new DataProviders();
        SoftAssert softAssert = new SoftAssert();
        pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
        pages.getTicketBulkUpdate().clickNextBtn();
        pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
        softAssert.assertEquals(pages.getTicketBulkUpdate().getTransferToQueueOption(), "Transfer to Queue", "Transfer to Queue does not present on UI");
        softAssert.assertEquals(pages.getTicketBulkUpdate().getChangeStateOption(), "Change State", "Change State does not present on UI");
        softAssert.assertEquals(pages.getTicketBulkUpdate().getTicketCommentOption(), "Add Ticket comment", "Add Ticket comment does not present on UI");
        softAssert.assertFalse(pages.getTicketBulkUpdate().isNextBtnEnable(), "Next Button enable without choosing operation");
        List<String> loginQueue = data.getLoginQueueData();
        List<String> queues = pages.getTicketBulkUpdate().getQueue();
        List<TicketStateDataBean> openState = data.getState(config.getProperty("openState"));
        List<TicketStateDataBean> closeState = data.getState(config.getProperty("closeState"));
        List<String> states = pages.getTicketBulkUpdate().getState();
        for (String s : queues) {
            if (loginQueue.contains(s)) {
                commonLib.info("Validate " + s + " ticketPool is display correctly");
                loginQueue.remove(s);
            } else {
                commonLib.fail(s + " ticketPool must not display on frontend as tag not mention in config sheet.", true);
                softAssert.fail(s + " ticketPool should not display on UI as ticket pool not mention in config sheet.");
            }
        }
        if (loginQueue.isEmpty()) {
            commonLib.pass("All ticketPool correctly configured and display on UI.");
        } else {
            for (String element : loginQueue) {
                commonLib.fail(element + " ticketPool does not display on UI but present in config sheet.", true);
                softAssert.fail(element + " ticketPool does not display on UI but present in config sheet.");
            }
        }

        for (TicketStateDataBean s : openState) {
            if (!states.contains(s.getTicketStateName().trim().toLowerCase()))
                commonLib.fail(s.getTicketStateName() + " :Ticket State does not display on UI but config in excel", true);
            states.remove(s.getTicketStateName().trim().toLowerCase());
        }
        for (TicketStateDataBean s : closeState) {
            if (!states.contains(s.getTicketStateName().trim().toLowerCase()))
                commonLib.fail(s.getTicketStateName() + " :Ticket State does not display on UI but config in excel", true);
            states.remove(s.getTicketStateName().trim().toLowerCase());
        }
        for (String s : states) {
            commonLib.fail(s + " :Ticket State does not config in excel but display on UI", true);
        }
        pages.getTicketBulkUpdate().clickCancelBtn();
        pages.getTicketBulkUpdate().clickPopUpCancelBtn();
        pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Add comment on ticket using bulk update feature", dependsOnMethods = "uploadTicketFromExcelTest")
    public void bulkAddCommentTest() {
        selUtils.addTestcaseDescription("Add comment on ticket using bulk update feature", "description");
        SoftAssert softAssert = new SoftAssert();
        Integer size = null;
        pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
        pages.getTicketBulkUpdate().clickAddCommentOption();
        pages.getTicketBulkUpdate().addComment("Adding Comment using Automation for Bulk Update option test. Please Ignore this comment");
        pages.getTicketBulkUpdate().clickConfirmAction();
        pages.getTicketBulkUpdate().clickNextBtn();
        softAssert.assertTrue(pages.getTicketBulkUpdate().isStatusBarComplete(), "Status not update for all ticket");
        pages.getTicketBulkUpdate().waitTillLoaderGetsRemoved();
        softAssert.assertEquals(pages.getTicketBulkUpdate().getErrorTicketCount(), Integer.parseInt(pages.getTicketBulkUpdate().getErrorCount()), "Error Ticket count does not displayed correctly");
        try {
            String[] array = pages.getTicketBulkUpdate().getUpdatedMessage().replaceAll("[^0-9\\s]+", "").trim().split(" ");
            size = Integer.parseInt(array[0]) - pages.getTicketBulkUpdate().getErrorTicketCount();
        } catch (NumberFormatException e) {
            softAssert.fail("Not able to read message properly " + e.fillInStackTrace());
        }
        softAssert.assertEquals(pages.getTicketBulkUpdate().getSuccessCount(), size, "Action does not Performed on all uploaded ticket");
        softAssert.assertAll();
    }
}
