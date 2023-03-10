package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.rechargehistory.RechargeHistory;
import com.airtel.cs.pagerepository.pagemethods.RechargeHistoryWidget;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Log
public class RechargeHistoryWidgetTest extends Driver {

    public static final String RUN_RECHARGE_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_RECHARGE_WIDGET_TESTCASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkRechargeHistoryFlag() {
        if (!StringUtils.equals(RUN_RECHARGE_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Recharge widget Test Case Flag Value is - " + RUN_RECHARGE_WIDGET_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.RECHARGE_HISTORY_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testHeaderAndAuuid() {
        try {
            selUtils.addTestcaseDescription("Validate is Recharge History Widget Visible,Validate is Recharge History Widget Loaded?,Validate Footer and Middle Auuid", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetIsVisible(), true, "Recharge History Widget is visible", "Recharge History Widget is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetLoaded(), true, "Recharge History Widget Loaded Successfully", "Recharge History NOT Loaded Successfully"));
            assertCheck.append(actions.assertEqualStringType(pages.getRechargeHistoryWidget().getFooterAuuidRHW(), loginAUUID, "Auuid shown at the footer of the Recharge History widget and is correct", "Auuid NOT shown at the footer of Recharge History widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getRechargeHistoryWidget().getMiddleAuuidRHW(), loginAUUID, "Auuid shown at the middle of the Recharge History widget and is correct", "Auuid NOT shown at the middle of Recharge History widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRechargeHistoryDatePickerVisible(), true, "Recharge History Widget's Date Picker is visible", "Recharge History Widget's Date Picker is not visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryWidgetHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "Recharge History")
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void testColumnValueAndAuuid(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Recharge History Widget of User :" + customerNumber + ",Validate Recharge History API Giving Status 200, Validate Recharge History widget header name display correctly as per config,Validate Recharge History widget data value must be same as api response.", "description");
            final RechargeHistoryWidget rechargeHistoryWidget = pages.getRechargeHistoryWidget();
            RechargeHistory rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
            final int statusCode = rechargeHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Recharge History API Status Code Matched and is :" + statusCode, "Recharge History API Status NOT Matched and is :" + statusCode, false));
            if (statusCode != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
                assertCheck.append(actions.assertEqualBoolean(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), true, "CS API and widget both are Giving error", "CS API is Giving error But Widget is not showing error Message on CS API is"));
                commonLib.fail("CS API is unable to give Recharge History ", true);
            } else {
                int size = rechargeHistoryWidget.getNumberOfRows();
                if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                    commonLib.warning("Unable txo get Last Recharge Details from CS API");
                    assertCheck.append(actions.assertEqualBoolean(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaders(0).trim() + " " + rechargeHistoryWidget.getSubHeaders(1).trim().toUpperCase(), data.getHeaderName().get(0).trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                    assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaders(1).trim(), data.getHeaderName().get(1).trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                    assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaders(2).trim(), data.getHeaderName().get(2), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                    assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaders(3).trim() + rechargeHistoryWidget.getSubHeaders(4).trim().replace(" ", "|"), data.getHeaderName().get(3).trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                    assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaders(4).trim(), data.getHeaderName().get(4).trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                    for (int i = 0; i < size; i++) {
                        int row = i + 1;
                        assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaderValue(row, 1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is As received in CS API for row number " + row, "Recharge History Charge is not As received in CS API for row number " + row));
                        String rechargeDate=UtilsMethods.getDateFromEpoch(Long.parseLong(rechargeHistoryAPI.getResult().get(i).getDateTime()), constants.getValue(CommonConstants.UI_RECHARGE_HISTORY_PATTERN));
                        assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaderValue(row, 2).replaceAll("[\\s\\-()]", " "),rechargeDate , "Recharge History Date Time is As received in CS API for row number " + i, "Recharge History Date Time is not As received in CS API for row number " + row));
                        assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaderValue(row, 3), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is As received in CS API for row number " + row, "Recharge History Bundle Name is not As received in CS API for row number " + row));
                        assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaderValue(row, 4).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is As received in CS API for row number " + row, "Recharge History Benefits is not As received in CS API for row number " + row));
                        assertCheck.append(actions.assertEqualStringType(rechargeHistoryWidget.getHeaderValue(row, 5), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is As received in CS API for row number " + row, "Recharge History Status is not As received in CS API for row number " + row));
                        if (i != 0) {
                            final String dateTime = rechargeHistoryWidget.getHeaderValue(row, 2);
                            final String dateTime1 = rechargeHistoryWidget.getHeaderValue(i, 2);
                            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isSortOrderDisplay(dateTime1, dateTime, "dd-MMM-yyy HH:mm"), true, dateTime1 + " displayed before " + dateTime, dateTime1 + " should not display before " + dateTime));
                        }
                    }

                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryWidgetTest" + e.fillInStackTrace(), true);
        }
    }
}
