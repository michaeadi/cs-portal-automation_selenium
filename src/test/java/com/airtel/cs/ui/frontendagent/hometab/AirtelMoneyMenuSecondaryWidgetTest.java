package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.airtelmoney.AirtelMoney;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AirtelMoneyMenuSecondaryWidgetTest extends Driver {

    public static final String MULTI_AM_WALLET = constants.getValue(ApplicationConstants.MULTI_WALLET_BALANCE);
    public static final String RUN_AIRTEL_MONEY_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_AIRTEL_MONEY_WIDGET_TESTCASE);
    String customerNumber;
    RequestSource api = new RequestSource();
    private AirtelMoney amTransactionHistoryAPI;

    @BeforeMethod(groups = {"ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"ProdTest"})
    public void checkAirtelMoneyFlag() {
        if (!StringUtils.equals(RUN_AIRTEL_MONEY_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Multiple Wallet not enabled for this " + OPCO);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @BeforeMethod(groups = {"ProdTest"})
    public void checkMultiWalletFlag() {
        if (!StringUtils.equals(MULTI_AM_WALLET, "true")) {
            commonLib.skip("Skipping because Airtel Multi-Wallet Test Case Flag Value is - " + MULTI_AM_WALLET);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.AM_CUSTOMER_MSISDN);
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

    @DataProviders.Table(name = "More Airtel Money Secondary History")
    @Test(priority = 2, groups = {"ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void amDetailedHistorySecondaryWidgetHeaderTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Airtel Money History's Header Name  Menu of User :" + customerNumber + ",Validating all the filter display as per config,Validate search by transaction id box displayed as per config.", "description");
            pages.getAmTxnWidgetPage().clickMenuOption();
            assertCheck.append(actions.assertEqualStringType(pages.getMoreAMTxnTabPage().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of AM Transaction History widget and is correct", "Auuid NOT shown at the footer of AM Transaction History widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getMoreAMTxnTabPage().getFooterAuuid(), loginAUUID, "Auuid shown at the middle of AM Transaction History widget and is correct", "Auuid NOT shown at the middle of AM Transaction History widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAMMenuHistoryTabDisplayOnSecondWidget(), true, "AM More Secondary transaction widget display as expected", "AM More Secondary transaction widget does not display as expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), true, "Today Filter does display on UI.", "Today Filter does not display."));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), true, "Last Two Days Filter does display on UI.", "Last Two Days Filter does not display."));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), true, "Last Seven Days Filter does display on UI.", "Last Seven Days Filter does not display."));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), true, "Date Range Filter does display on UI.", "Date Range Filter does not display."));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isSearchTxnIdBoxOnSecondWidget(), true, "TXN ID Box does display on UI.", "TXN ID Box does not display."));
            amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, constants.getValue(ApplicationConstants.SECOND_AM_CURRENCY));
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Airtel Widget API success and status code is :" + statusCode, "Airtel Widget API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisibleOnSecondWidget(), true, "API is Giving error and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", true);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisibleOnSecondWidget(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));

            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 10);
                if (count > 0) {
                    for (int i = 0; i < data.getHeaderName().size(); i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeadersOnSecondWidget(i + 1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                    }
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - airtelMoneyHistoryMenuSecondaryWidgetHeaderTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "More Airtel Money History")
    @Test(priority = 3, groups = {"ProdTest"}, dependsOnMethods = {"amDetailedHistorySecondaryWidgetHeaderTest", "openCustomerInteraction"})
    public void amHistoryMenuSecondaryValueTest() {
        try {
            selUtils.addTestcaseDescription("Validating Airtel Money History's  Menu Secondary Widget of User :" + customerNumber + "Validate all the row data display on UI as per api response.", "description");
            amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, constants.getValue(ApplicationConstants.SECOND_AM_CURRENCY));
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Airtel Widget API success and status code is :" + statusCode, "Airtel Widget API got failed and status code is :" + statusCode));
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisibleOnSecondWidget(), true, "API is Giving and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", false);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisibleOnSecondWidget(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 10);
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        if (amTransactionHistoryAPI.getResult().getData().get(i).getAmount().charAt(0) == '+') {
                            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isPosSignDisplayOnSecondWidget(i + 1), true, i + "th Positive Sign does display in case of Amount Credited.", i + "th Positive Sign does not display in case of Amount Credited."));
                        } else {
                            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isNegSignDisplayOnSecondWidget(i + 1), true, i + "th Negative Sign does display in case of Amount Debited.", i + "th Negative Sign does not display in case of Amount Debited."));
                        }
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 2).replaceAll("\\R", " "), UtilsMethods.getDateFromEpoch(new Long(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), constants.getValue(CommonConstants.AM_HISTORY_TIME_FORMAT)), i + "th Date is expected as API response.", i + "th Date is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 3), amTransactionHistoryAPI.getResult().getData().get(i).getService(), i + "th Service name is same as expected in API response.", i + "th Service name is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getSource(), i + "th Sender MSISDN is expected as API response.", i + "th Sender MSISDN is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), i + "th Receiver MSISDN is expected as API response.", i + "th Receiver MSISDN is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 6), amTransactionHistoryAPI.getResult().getData().get(i).getSecondPartyName(), i + "th Beneficiary name is expected as API response.", i + "th Beneficiary name is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 7), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), i + "th Transaction Id is expected as API response.", i + "th Transaction Id is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 8), amTransactionHistoryAPI.getResult().getData().get(i).getServiceCharge(), i + "th Service Charge is expected as API response.", i + "th Service Charge is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 9), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceBefore(), i + "th Pre-balance is expected as API response.", i + "th Pre-balance is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 10), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceAfter(), i + "th Post-balance is expected as API response.", i + "th Post-balance is not expected as API response."));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeaderOnSecondWidget(i + 1, 11), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), i + "th Status is expected as API response.", i + "th Status is not expected as API response."));
                        if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isResendSMSOnSecondWidget(), true, "Resend SMS Icon does enable as mentioned in API Response.", "Resend SMS Icon does not enable as mentioned in API Response."));
                        }
                    }
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - airtelMoneyHistoryMenuSecondaryTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
