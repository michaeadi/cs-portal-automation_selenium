package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.airtelmoney.AirtelMoneyPOJO;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AirtelMoneyMenuPrimaryWidgetTest extends Driver {
    public static final String MULTI_AM_WALLET = constants.getValue(ApplicationConstants.MULTI_WALLET_BALANCE);
    private final BaseActions actions = new BaseActions();
    String customerNumber;
    RequestSource api = new RequestSource();
    private AirtelMoneyPOJO amTransactionHistoryAPI;
    public static final String RUN_AIRTEL_MONEY_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_AIRTEL_MONEY_WIDGET_TESTCASE);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkAirtelMoneyFlag() {
        if (!StringUtils.equals(RUN_AIRTEL_MONEY_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Airtel Money widget Test Case Flag Value is - " + RUN_AIRTEL_MONEY_WIDGET_TEST_CASE);
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
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "More Airtel Money History")
    @Test(priority = 2, groups = {"ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void airtelMoneyHistoryMenuHeaderTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Airtel Money History's Header Name  Menu of User :" + customerNumber + ",Validating all the filter display as per config,Validate search by transaction id box displayed as per config.", "description");
            pages.getAmTxnWidgetPage().clickMenuOption();
            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isAMMenuHistoryTabDisplay(), true, "AM More Primary transaction widget display as expected", "AM More Primary transaction widget does not display as expected"));
            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isTodayFilterTab(), true, "Today Filter does display on UI.", "Today Filter does not display."));
            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isTodayFilterTab(), true, "Last Two Days Filter does display on UI.", "Last Two Days Filter does not display."));
            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isTodayFilterTab(), true, "Last Seven Days Filter does display on UI.", "Last Seven Days Filter does not display."));
            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isTodayFilterTab(), true, "Date Range Filter does display on UI.", "Date Range Filter does not display."));
            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isSearchTxnIdBox(), true, "TXN ID Box does display on UI.", "TXN ID Box does not display."));
            if (StringUtils.equals(MULTI_AM_WALLET, "true")) {
                amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, constants.getValue(ApplicationConstants.FIRST_AM_CURRENCY));
            } else {
                amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, null);
            }
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Airtel Widget API success and status code is :" + statusCode, "Airtel Widget API got failed and status code is :" + statusCode));
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisible(), true, "API is Giving error and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", true);
            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 10);
                if (count > 0) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(1), data.getRow1(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(2), data.getRow2(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(3), data.getRow3(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(4), data.getRow4(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(5), data.getRow5(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(6), data.getRow6(), "Header Name for Row 6 is as expected", "Header Name for Row 6 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(7), data.getRow7(), "Header Name for Row 7 is as expected", "Header Name for Row 7 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(8), data.getRow8(), "Header Name for Row 8 is as expected", "Header Name for Row 8 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(9), data.getRow9(), "Header Name for Row 9 is as expected", "Header Name for Row 9 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(10), data.getRow10(), "Header Name for Row 10 is as expected", "Header Name for Row 10 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(11), data.getRow11(), "Header Name for Row 11 is as expected", "Header Name for Row 11 is not as expected"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getHeaders(12), data.getRow12(), "Header Name for Row 12 is as expected", "Header Name for Row 12 is not as expected"));
                } else {
                    assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisible(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - airtelMoneyHistoryMenuHeaderTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "airtelMoneyHistoryMenuHeaderTest"})
    public void airtelMoneyHistoryMenuTest() {
        try {
            selUtils.addTestcaseDescription("Validating Airtel Money History's  Menu of User :" + customerNumber+"Validate all the data rows displayed as per api response,In case of No result data rows found validate no result found icon displayed,Validate resend sms icon displayed as per api response.", "description");
            try {
                try {
                    if (StringUtils.equals(MULTI_AM_WALLET, "true")) {
                        amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, constants.getValue(ApplicationConstants.FIRST_AM_CURRENCY));
                    } else {
                        amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, null);
                    }
                    final int statusCode = amTransactionHistoryAPI.getStatusCode();
                    assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Airtel Widget API success and status code is :" + statusCode, "Airtel Widget API got failed and status code is :" + statusCode));
                    if (statusCode != 200) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisible(), true, "API is Giving error and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                        commonLib.fail("API is Unable to Get AM Transaction History for Customer", true);
                    } else {
                        int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 10);
                        if (count > 0) {
                            for (int i = 0; i < count; i++) {
                                if (amTransactionHistoryAPI.getResult().getData().get(i).getAmount().charAt(0) == '+') {
                                    assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isPosSignDisplay(i + 1), true, i + "th Positive Sign does display in case of Amount Credited.", i + "th Positive Sign does not display in case of Amount Credited."));
                                } else {
                                    assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isNegSignDisplay(i + 1), true, i + "th Negative Sign does display in case of Amount Debited.", i + "th Negative Sign does not display in case of Amount Debited."));
                                }
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 2).replace("\n"," "), UtilsMethods.getDateFromEpochInUTC(new Long(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), constants.getValue(CommonConstants.AM_HISTORY_TIME_FORMAT)), i + "th Date is expected as API response.", i + "th Date is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 3), amTransactionHistoryAPI.getResult().getData().get(i).getService(), i + "th Service name is not expected as API response.", i + "th Service name is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getSource(), i + "th Sender MSISDN is expected as API response.", i + "th Sender MSISDN is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), i + "th Receiver MSISDN is expected as API response.", i + "th Receiver MSISDN is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 6), amTransactionHistoryAPI.getResult().getData().get(i).getSecondPartyName(), i + "th Beneficiary name is expected as API response.", i + "th Beneficiary name is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 7), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), i + "th Transaction Id is expected as API response.", i + "th Transaction Id is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 8), amTransactionHistoryAPI.getResult().getData().get(i).getServiceCharge(), i + "th Service Charge is expected as API response.", i + "th Service Charge is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 9), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceBefore(), i + "th Pre-balance is expected as API response.", i + "th Pre-balance is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 10), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceAfter(), i + "th Post-balance is expected as API response.", i + "th Post-balance is not expected as API response."));
                                assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 11), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), i + "th Status is expected as API response.", i + "th Status is not expected as API response."));
                                if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                                    assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isResendSMS(), true, "Resend SMS Icon does enable as mentioned in API Response.", "Resend SMS Icon does not enable as mentioned in API Response."));
                                }
                            }
                        } else {
                            assertCheck.append(actions.assertEqual_boolean(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisible(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
                        }
                    }
                } catch (NullPointerException | NoSuchElementException | TimeoutException f) {
                    commonLib.fail("Not able to validate airtel menu widget: " + f.fillInStackTrace(), true);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to Open Airtel Money History Sub Tab " + e.fillInStackTrace(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - airtelMoneyHistoryMenuTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
