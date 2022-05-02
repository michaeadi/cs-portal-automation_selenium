package com.airtel.cs.ui.frontendagent.am;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AmSMSTrailsTest extends Driver {
    String customerNumber = null;
    SmsLogsResponse smsLogs;
    int size;
    Boolean isPermissionEnable = false;

    public static final String RUN_AM_SMS_TRAILS = constants.getValue(ApplicationConstants.RUN_AM_SMS_TRAILS);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }


    @BeforeMethod(groups = {"ProdTest", "SanityTest", "RegressionTest"})
    public void checkLinkedWalletFlag() {
        if (!StringUtils.equals(RUN_AM_SMS_TRAILS, "true")) {
            commonLib.skip("Skipping because Run Airtel Money SMS Trails Test Case Flag Value is - " + RUN_AM_SMS_TRAILS);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasPermission() {
        try {
            selUtils.addTestcaseDescription("Validate whether user has AM Profile Details Permission ", "description");
            String permission = constants.getValue(PermissionConstants.AM_PROFILE_DETAILS_PERMISSION);
            isPermissionEnable = UtilsMethods.isUserHasPermission(permission);
            assertCheck.append(actions.assertEqualBoolean(isPermissionEnable, true, "Logged in user has Am Profile Details permission", "Logged in user doesn't has Am Profile Details permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermission " + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Open SMS Logs tab
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void openSmsLogs() {
        try {
            selUtils.addTestcaseDescription("Validate AM Transactions Widget visible or not ,Open detailed page of Am Transactions widget , Open Sms Logs,Validate auuid at the footer and middle of the widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isAmTransactionsWidgetVisible(), true, "Am Transaction Widget is visible", "Am Transaction Widget is NOT visible"));
            if (isPermissionEnable) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isMoreIconVisible(), true, "AM Transaction Widget detailed icon  is visible", "AM Transaction Widget detailed icon is NOT visible"));
                pages.getAmSmsTrails().clickMoreIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isAmProfileDetailsDetailWidgetVisible(), true, "Am Profile Details widget is visible", "Am Profile Details widget is NOT visible"));
                pages.getAmSmsTrails().clickSmsLogs();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isSmsLogVisible(), true, "SMS Logs tab is visible", "SMS Logs tab is NOT visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of SMS Logs widget and is expected ", "Auuid NOT shown at the footer of SMS Logs widget "));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of SMS Logs widget and is expected", "Auuid NOT shown at the middle of SMS Logs widget "));
            } else
                commonLib.fail("Am Profile Details widget is not visible as user has not permission to view it", true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSmsLogs" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to test Sms Logs tab Layout
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testSmsLogsLayout() {
        try {
            selUtils.addTestcaseDescription("Validate all the fields are visible or not in SMS Logs", "description");
            smsLogs = api.getSMSLogs(customerNumber);
            assertCheck.append(actions.assertEqualIntType(smsLogs.getStatusCode(), 200, "Sms Trails API Status Code Matched and is :" + smsLogs.getStatusCode(), "Sms Trails Status Code NOT Matched and is :" + smsLogs.getStatusCode(), false));
            if (smsLogs.getStatusCode() == 200 && smsLogs.getResult().size() == 0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isWidgetErrorMessageVisible(), true, "CS API and widget both are giving error", "CS API is giving error but widget is not showing error message"));
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isTimeStampVisible(), true, "Timestamp is visible", "Timestamp is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isTransactionIdVisible(), true, "Transaction Id is visible", "Transaction Id is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isSmsBodyVisible(), true, "Sms Body is visible", "Sms Body is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isSmsIdVisible(), true, "SMS Id is visible", "SMS Id is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isDateFilterVisible(), true, "Date filter is visible", "Date filter is NOT visible"));
                pages.getAmSmsTrails().clickDateFilter();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().isCalendarVisible(), true, "Calendar is visible", "Calendar is NOT visible"));
                pages.getAmSmsTrails().clickCancelOfDate();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSmsLogsLayout" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check Sms Logs data
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testSmsLogsData() {
        try {
            selUtils.addTestcaseDescription("Validate all the fields are visible or not in SMS Logs", "description");
            if (smsLogs.getStatusCode() == 200 && smsLogs.getResult().size() == 0) {
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                size = pages.getAmSmsTrails().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 1), smsLogs.getResult().get(i).getSmsDate(), "Timestamp is same as expected ", "Timestamp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 2), smsLogs.getResult().get(i).getTransactionId(), "Transaction Id is same as expected ", "Transaction Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 3), smsLogs.getResult().get(i).getSmsId(), "Sms Id is same as expected ", "Sms Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 4), smsLogs.getResult().get(i).getSmsBody(), "Sms Body is same as expected ", "Sms Body is NOT same as expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSmsLogsData" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 6, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction","testSmsLogsData"})
    public void checkPaginationForSmsLogs() {
        selUtils.addTestcaseDescription("Validate pagination and pagination count ,Validate user should be able to click next button if rows > 5 ,After navigating to next page user should be able to navigate back using previous button ", "description");
        try {
            String paginationResult = "1 - " + size + " of " + smsLogs.getResult().size() + " Results";
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getPaginationText(), paginationResult, "Pagination Count is as expected", "Pagination count is Not as  expected"));
            if (smsLogs.getResult().size() > 5) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().checkNextBtnEnable(), true, "In pagination next button is enable as result is greater than 5", "In Pagination next button is not enable but result is greater than 5"));
                pages.getAmSmsTrails().clickNextBtn();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().checkPreviousBtnDisable(), false, "In pagination Previous button is enable", "In Pagination previous button is not enable"));
                pages.getAmSmsTrails().clickPreviousBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getPaginationText(), paginationResult, "Pagination Count as expected", "Pagination count as not expected"));
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmSmsTrails().checkNextBtnEnable(), false, "In pagination next button is disable as result is <= 5", "In Pagination next button is not disable but result is <= 5"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkPaginationForSmsLogs" + e.fillInStackTrace(), true);
        }
    }
}


