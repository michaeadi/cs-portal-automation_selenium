package com.airtel.cs.ui.ticketBulkUpdate;

import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TicketStateDataBean;
import com.airtel.cs.driver.Driver;
import io.restassured.http.Headers;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TicketBulkUpdateTest extends Driver {

    public static final String BULK_UPDATE_TRANSFER_QUEUE_STATUS = constants.getValue(ApplicationConstants.BULK_UPDATE_TRANSFER_TO_QUEUE_STATUS);
    public static boolean continueExecTicketBulkUpdateTC = true;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!(continueExecutionFA && continueExecutionBU)) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkBulkUpdateExecution() {
        if (!continueExecTicketBulkUpdateTC) {
            commonLib.skip("Skipping tests because there are some errors at bulk update page");
            throw new SkipException("Skipping tests because there are some errors at bulk update page");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasTicketBulkUpdatePermission() {
        try {
            selUtils.addTestcaseDescription("Validate user has permission to perform validate Ticket Bulk Update operation", "description");
            String ticket_bulk_permission = constants.getValue(PermissionConstants.TICKET_BULK_UPDATE_ACTION_PERMISSION);
            pages.getSideMenuPage().clickOnSideMenu();
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isTicketBulkUpdateVisible(), UtilsMethods.isUserHasPermission(ticket_bulk_permission), "User has permission to perform ticket bulk update as expected", "User does not has permission to perform ticket bulk update as expected"));
            pages.getSideMenuPage().clickOnSideMenu();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasTicketBulkUpdatePermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasTicketBulkUpdatePermission"})
    public void openTicketBulkUpdate() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Bulk Update Dashboard,Validate Ticket Bulk Update Dashboard Opened", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openTicketBulkUpdateDashboard();
            final boolean pageLoaded = pages.getTicketBulkUpdate().isTicketBulkUpdate();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Ticket bulk update Page Loaded Successfully", "Ticket bulk update Page NOT Loaded"));
            if (!pageLoaded) continueExecTicketBulkUpdateTC = false;
            final Boolean filterVisible = pages.getTicketBulkUpdate().isFilterVisible();
            assertCheck.append(actions.assertEqualBoolean(filterVisible, true, "Filters are available over bulk update screen", "Filters are NOT available over bulk update screen"));
            if (filterVisible) continueExecTicketBulkUpdateTC = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openTicketBulkUpdate" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void validateIssueTypeOnCategoryDropDown() {
        try {
            selUtils.addTestcaseDescription("validate issue type on category drop down is visisble or not", "description");
            pages.getTicketBulkUpdate().clickSelectFilter();
            pages.getTicketBulkUpdate().clickIssueOnCatDropDown();
            pages.getTicketBulkUpdate().enterIssue(constants.getValue(CommonConstants.ISSUE));
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().clickingOnIssueType(), true, "Issue type visible as expected", "Issue type not visible as expected"));
            pages.getTicketBulkUpdate().clickResetFilterButton();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateStartDateEndDate" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void validateStartEndSameDate() {
        try {
            selUtils.addTestcaseDescription("validate same start date and end date in date duration", "description");
            String start_date = pages.getTicketBulkUpdate().getCurrentDate();
            String end_date = pages.getTicketBulkUpdate().getCurrentDate();
            pages.getTicketBulkUpdate().enterCurrentStartDate(start_date);
            pages.getTicketBulkUpdate().enterCurrentEndDate(end_date);
            assertCheck.append(actions.assertEqualBoolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), true, "Filter button enabled", "Filter button not enabled"));
            pages.getTicketBulkUpdate().clickResetFilterButton();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateStartEndSameDate" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void validateDateRange() {
        try {
            selUtils.addTestcaseDescription("validating date range end date can not be previous date", "description");
            String start_date = pages.getTicketBulkUpdate().getCurrentDate();
            String end_dt = pages.getTicketBulkUpdate().getPreviousDayDate();
            pages.getTicketBulkUpdate().enterCurrentStartDate(start_date);
            pages.getTicketBulkUpdate().enterCurrentEndDate(end_dt);
            pages.getTicketBulkUpdate().clickApplyFilterButton();
            assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getErrorMsg(), "* Start date is required field, it can't be Null, Blank, or Future Date", "End date can not be less than start date error message displays as expected", "End date can not be less than start date error message is not displays as expected"));
            pages.getTicketBulkUpdate().clickCloseFilter();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateStartEndSameDate" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void maxTicketSelectTest() {
        try {
            selUtils.addTestcaseDescription("Verify that max" + constants.getValue(CommonConstants.TICKET_BULK_UPDATE_MAX_COUNT) + "Tickets to be allowed to be bulk updated in one go,Select Filter with date duration last 30 days and apply filter,Validate the max ticket message display or not.", "description");
            try {
                pages.getTicketBulkUpdate().clickSelectFilter();
                pages.getFilterTabPage().clickLast30DaysFilter();
                pages.getFilterTabPage().clickApplyFilter();
                final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
                if (isGrowlVisible) {
                    String message = pages.getGrowl().getToastContent();
                    if (message.equalsIgnoreCase("Bad Gateway") || message.contains("Unknown Error") || message.equalsIgnoreCase("Something went wrong")) {
                        commonLib.fail("Toast Message Appeared as api failed: " + message, true);
                    }
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to apply filter: " + e.fillInStackTrace(), true);
                pages.getTicketBulkUpdate().clickCloseFilter();
            }
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().isClearFilterButton(), true, "Clear Filter button display after selecting filter.", "Clear Filter button does not display after selecting filter."));
            assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getMaxSelectMessage().replaceAll("[^0-9]+", "").trim(), constants.getValue(CommonConstants.TICKET_BULK_UPDATE_MAX_COUNT), "Max Ticket bulk update message displayed", "Max Ticket bulk update message not displayed"));
            pages.getTicketBulkUpdate().clickClearFilter();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - maxTicketSelectTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void uploadTicketFromExcelTest() {
        try {
            selUtils.addTestcaseDescription("Check user able to upload ticket id from excel,Validate total number of tickets updated in list must be same as total number of ticket upload.", "description");
            DataProviders data = new DataProviders();
            Assert.assertTrue(pages.getTicketBulkUpdate().fileDownload(), "Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded");
            Assert.assertTrue(data.writeTicketNumberToExcel(5), "No Ticket Found to write in Excel");
            pages.getTicketBulkUpdate().addFile();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            if (isGrowlVisible) {
                String message = pages.getGrowl().getToastContent();
                if (message.equalsIgnoreCase("Bad Gateway") || message.contains("Unknown Error")) {
                    commonLib.fail("Toast Message Appeared as api failed: " + message, true);
                }
            }
            List<String> uploadTickets = data.getTicketNumbers();
            List<String> addedTicket = pages.getTicketBulkUpdate().getTicketList();
            int uploadedSize = uploadTickets.size();
            uploadTickets.removeAll(addedTicket);
            int size = uploadedSize - uploadTickets.size();
            String[] newString = pages.getTicketBulkUpdate().getErrorMessage().replaceAll("[^0-9\\s]+", "").trim().split(" ");
            assertCheck.append(actions.assertEqualStringType(newString[0], String.valueOf(size), "Uploaded ticket count as expected", "Uploaded ticket miscount"));
            assertCheck.append(actions.assertEqualStringType(newString[newString.length - 1], String.valueOf(uploadedSize), "Total number of ticket is same as expected", "Total number of ticket is not same as expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().deleteFile(), true, "File deleted", "File not deleted"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - uploadTicketFromExcelTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "uploadTicketFromExcelTest")
    public void bulkOptionTest() {
        try {
            selUtils.addTestcaseDescription("Validate Bulk option test,Validate transfer to queue,Change state,Add comment Action field must be displayed,Validate all the queue displayed as per config,Validate all the Open & closed state displayed.", "description");
            DataProviders data = new DataProviders();
            pages.getTicketBulkUpdate().clickNextBtn();
            assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getTransferToQueueOption(), constants.getValue(CommonConstants.TICKET_BULK_UPDATE_TRANSFER_TO_QUEUE_ACTION), "Transfer to Queue action present on UI", "Transfer to Queue does not present on UI"));
            assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getChangeStateOption(), constants.getValue(CommonConstants.TICKET_BULK_UPDATE_CHANGE_STATE_ACTION), "Change State action present on UI", "Change State does not present on UI"));
            assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getTicketCommentOption(), constants.getValue(CommonConstants.TICKET_BULK_UPDATE_ADD_TICKET_COMMENT_ACTION), "Add Ticket comment action present on UI", "Add Ticket comment does not present on UI"));
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().isNextBtnEnable(), false, "Next Button does not enable before choosing operation", "Next Button enable without choosing operation"));
            List<String> loginQueue = data.getLoginQueueData();
            List<String> queues = pages.getTicketBulkUpdate().getQueue();
            List<TicketStateDataBean> openState = data.getState(constants.getValue(CommonConstants.TICKET_OPEN_STATE));
            List<TicketStateDataBean> closeState = data.getState(constants.getValue(CommonConstants.TICKET_CLOSE_STATE));
            List<String> states = pages.getTicketBulkUpdate().getState();
            for (String s : queues) {
                if (loginQueue.contains(s)) {
                    commonLib.info("Validate " + s + " ticketPool is display correctly");
                    loginQueue.remove(s);
                } else {
                    commonLib.fail(s + " ticketPool must not display on frontend as tag not mention in config sheet.", false);
                }
            }
            if (loginQueue.isEmpty()) {
                commonLib.pass("All ticketPool correctly configured and display on UI.");
            } else {
                for (String element : loginQueue) {
                    commonLib.fail(element + " ticketPool does not display on UI but present in config sheet.", false);
                }
            }

            for (TicketStateDataBean s : openState) {
                if (!states.contains(s.getTicketStateName().trim().toLowerCase()) && !s.getTicketStateName().equalsIgnoreCase(constants.getValue(CommonConstants.REOPEN_TICKET_STATE_NAME)))
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
        } catch (Exception e) {
            commonLib.fail("Exception in Method - bulkOptionTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "uploadTicketFromExcelTest")
    public void bulkAddCommentTest() {
        try {
            selUtils.addTestcaseDescription("Add comment on ticket using bulk update feature,Select add comment option,Add new comment,Click on confirm action,Click on next button and validate all the ticket  update correctly.", "description");
            int size = -1;
            pages.getTicketBulkUpdate().clickAddCommentOption();
            pages.getTicketBulkUpdate().addComment("Adding Comment using Automation for Bulk Update option test. Please Ignore this comment");
            pages.getTicketBulkUpdate().clickConfirmAction();
            pages.getTicketBulkUpdate().clickNextBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().isStatusBarComplete(), true, "Status update for all ticket", "Status not update for all ticket"));
            assertCheck.append(actions.assertEqualIntType(pages.getTicketBulkUpdate().getErrorTicketCount(), Integer.parseInt(pages.getTicketBulkUpdate().getErrorCount()), "Error Ticket count does displayed correctly", "Error Ticket count does not displayed correctly"));
            String[] array = pages.getTicketBulkUpdate().getUpdatedMessage().replaceAll("[^0-9\\s]+", "").trim().split(" ");
            size = Integer.parseInt(array[0]) - pages.getTicketBulkUpdate().getErrorTicketCount();
            assertCheck.append(actions.assertEqualIntType(pages.getTicketBulkUpdate().getSuccessCount(), size, "Action does Performed on all uploaded ticket", "Action does not Performed on all uploaded ticket"));
            pages.getTicketBulkUpdate().clickOkButton();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - bulkAddCommentTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void uploadTicketFromExcelAndTransferToQueueTest() {
        if (!StringUtils.equals(BULK_UPDATE_TRANSFER_QUEUE_STATUS, "true")) {
            commonLib.skip("Skipping because Bulk Operation not allowing while performing transfer to queue action as no two or more queue lies with in same workgroup - " + BULK_UPDATE_TRANSFER_QUEUE_STATUS);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        } else {
            try {
                selUtils.addTestcaseDescription("Check user able to upload ticket id from excel,Validate ticket upload from excel is complete,Click on next button and choose transfer to queue operation,Choose queue from the list and confirm the info,Click on submit button and validate ticket transfer to selected queue,Validate Ticket history log logged this entry as ticket update by bulk update.", "description");
                DataProviders data = new DataProviders();
                int noOfTicket = 1;
                Assert.assertTrue(pages.getTicketBulkUpdate().fileDownload(), "Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded");
                Assert.assertTrue(data.writeTicketNumberToExcel(noOfTicket), "No Ticket Found to write in Excel");
                pages.getTicketBulkUpdate().addFile();
                final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
                if (isGrowlVisible) {
                    String message = pages.getGrowl().getToastContent();
                    if (message.equalsIgnoreCase("Bad Gateway") || message.contains("Unknown Error")) {
                        commonLib.fail("Toast Message Appeared as api failed: " + message, true);
                    }
                }
                pages.getTicketBulkUpdate().clickNextBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getTransferToQueueOption(), constants.getValue(CommonConstants.TICKET_BULK_UPDATE_TRANSFER_TO_QUEUE_ACTION), "Transfer to Queue action present on UI", "Transfer to Queue does not present on UI"));
                pages.getTicketBulkUpdate().clickTransferToQueueOption();
                pages.getTicketBulkUpdate().clickSelectTransferToQueue();
                pages.getTicketBulkUpdate().selectByText(constants.getValue(ApplicationConstants.BULK_UPDATE_TO_QUEUE_NAME));
                pages.getTicketBulkUpdate().clickOutside();
                pages.getTicketBulkUpdate().clickConfirmAction();
                pages.getTicketBulkUpdate().clickNextBtn();
                assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().isStatusBarComplete(), true, "Status update for all ticket", "Status not update for all ticket"));
                assertCheck.append(actions.assertEqualIntType(pages.getTicketBulkUpdate().getSuccessCount(), noOfTicket, "Action does Performed on all uploaded ticket", "Action does not Performed on all uploaded ticket"));
                List<String> ticketId = pages.getTicketBulkUpdate().getTicketList();
                if (pages.getTicketBulkUpdate().getSuccessCount() > 0) {
                    pages.getTicketBulkUpdate().clickOkButton();
                    pages.getSideMenuPage().clickOnSideMenu();
                    pages.getSideMenuPage().openSupervisorDashboard();
                    assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
                    for (String ticket : ticketId) {
                        pages.getSupervisorTicketList().changeTicketTypeToClosed();
                        pages.getSupervisorTicketList().writeTicketId(ticket);
                        pages.getSupervisorTicketList().clickSearchBtn();
                        Assert.assertEquals(pages.getSupervisorTicketList().getTicketIdValue(), ticket, "Search Ticket does not found");
                        pages.getSupervisorTicketList().viewTicket();
                        pages.getViewTicket().clickTicketHistoryLog();
                        assertCheck.append(actions.assertEqualBoolean(pages.getViewTicket().checkBulkUpdateLogged("Transferred"), true, "Bulk update action logged in ticket history log with ticket id: " + ticket, "Bulk update action does not logged in ticket history log with ticket id: " + ticket));
                        pages.getViewTicket().clickBackButton();
                    }
                } else {
                    commonLib.fail("No Tickets update through bulk update", true);
                    pages.getTicketBulkUpdate().clickOkButton();
                }
                assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().deleteFile(), true, "File deleted", "File not deleted"));
            } catch (Exception e) {
                commonLib.fail("Exception in Method - uploadTicketFromExcelAndTransferToQueueTest" + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        }
    }

    @Test(priority = 11, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openTicketBulkUpdate")
    public void uploadTicketFromExcelAndChangeStateTest() {
        try {
            selUtils.addTestcaseDescription("Check user able to upload ticket id from excel,Validate ticket upload from excel is complete,Click on next button and choose Change state operation,Choose State from the list and confirm the info,Click on submit button and validate ticket transfer to selected state,Validate Ticket history log logged this entry as ticket update by bulk update.", "description");
            DataProviders data = new DataProviders();
            int noOfTicket = 1;
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().fileDownload(),true, "Ticket Upload Template file download.Please check Excels/BulkUploadTemplate.xlsx downloaded","Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded",true));
            assertCheck.append(actions.assertEqualBoolean(data.writeTicketNumberToExcel(noOfTicket),true, "Ticket Found to write in Excel","No Ticket Found to write in Excel",true));
            pages.getTicketBulkUpdate().addFile();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            if (isGrowlVisible) {
                String message = pages.getGrowl().getToastContent();
                if (message.equalsIgnoreCase("Bad Gateway") || message.contains("Unknown Error")) {
                    commonLib.fail("Toast Message Appeared as api failed: " + message, true);
                }
            }
            pages.getTicketBulkUpdate().clickNextBtn();
            assertCheck.append(actions.assertEqualStringType(pages.getTicketBulkUpdate().getTransferToQueueOption(), constants.getValue(CommonConstants.TICKET_BULK_UPDATE_TRANSFER_TO_QUEUE_ACTION), "Transfer to Queue action present on UI", "Transfer to Queue does not present on UI"));
            pages.getTicketBulkUpdate().clickTicketStateOption();
            pages.getTicketBulkUpdate().clickSelectStateOption();
            pages.getTicketBulkUpdate().selectOptionByName("Closed");
            pages.getTicketBulkUpdate().clickOutside();
            pages.getTicketBulkUpdate().clickConfirmAction();
            pages.getTicketBulkUpdate().clickNextBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().isStatusBarComplete(), true, "Status update for all ticket", "Status not update for all ticket"));
            assertCheck.append(actions.assertEqualIntType(pages.getTicketBulkUpdate().getSuccessCount(), noOfTicket, "Action does Performed on all uploaded ticket", "Action does not Performed on all uploaded ticket"));
            List<String> ticketId = pages.getTicketBulkUpdate().getTicketList();
            if (pages.getTicketBulkUpdate().getSuccessCount() > 0) {
                pages.getTicketBulkUpdate().clickOkButton();
                pages.getSideMenuPage().clickOnSideMenu();
                pages.getSideMenuPage().openSupervisorDashboard();
                assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
                for (String ticket : ticketId) {
                    pages.getSupervisorTicketList().changeTicketTypeToClosed();
                    pages.getSupervisorTicketList().writeTicketId(ticket);
                    pages.getSupervisorTicketList().clickSearchBtn();
                    Assert.assertEquals(pages.getSupervisorTicketList().getTicketIdValue(), ticket, "Search Ticket does not found");
                    pages.getSupervisorTicketList().viewTicket();
                    pages.getViewTicket().clickTicketHistoryLog();
                    assertCheck.append(actions.assertEqualBoolean(pages.getViewTicket().checkBulkUpdateLogged("Closed"), true, "Bulk update action logged in ticket history log with ticket id: " + ticket, "Bulk update action does not logged in ticket history log with ticket id: " + ticket));
                    pages.getViewTicket().clickBackButton();
                }
            } else {
                commonLib.fail("No Tickets update through bulk update", true);
                pages.getTicketBulkUpdate().clickOkButton();
            }
            assertCheck.append(actions.assertEqualBoolean(pages.getTicketBulkUpdate().deleteFile(), true, "File deleted", "File not deleted"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - uploadTicketFromExcelAndChangeStateTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
