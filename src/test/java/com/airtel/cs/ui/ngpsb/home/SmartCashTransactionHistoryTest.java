package com.airtel.cs.ui.ngpsb.home;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.airtelmoney.AirtelMoney;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.pagerepository.pagemethods.PsbDemographicWidget;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SmartCashTransactionHistoryTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    AirtelMoney amTransactionHistoryAPI;
    String nubanId;
    String className = this.getClass().getName();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            clmDetails = api.getCLMDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (!pageLoaded)
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "SmartCash Transaction History")
    @Test(priority = 2, groups = {"ProdTest", "SmokeTest", "SanityTest", "RegressionTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void transactionHistoryWidgetLayoutTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Smart Cash Transaction History's Header Name ,Validating all the filter displayed as per config,Validate search by transaction id box displayed as per config.", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isSmartCashTransactionHistoryVisible(), true, "SmartCash transaction history widget is visible.", "SmartCash transaction history widget is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isTodayFilterTabOnSecondWidget(), true, "Today Filter is displayed ", "Today Filter is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isTodayFilterTabOnSecondWidget(), true, "Last Two Days Filter is displayed", "Last Two Days Filter is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isTodayFilterTabOnSecondWidget(), true, "Last Seven Days Filter is displayed", "Last Seven Days Filter is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isTodayFilterTabOnSecondWidget(), true, "Date Range Filter is displayed", "Date Range Filter is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isSearchTxnIdBoxOnSecondWidget(), true, "TXN ID Box is displayed", "TXN ID Box is not displayed"));
            PsbDemographicWidget widget = new PsbDemographicWidget(driver);
            if (widget.walletFlag > 0)
                nubanId = clmDetails.getResult().getDetails().get(0).getWallets().get(0).getId();
            if (widget.accountFlag > 0)
                nubanId = clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId();
            amTransactionHistoryAPI = api.getTransactionHistory(customerNumber, nubanId);
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Transaction History  API success and status code is :" + statusCode, "Transaction History API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isAirtelMoneyErrorVisibleOnSecondWidget(), true, "API is Giving error and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get Transaction History for Customer", true);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isAirtelMoneyNoResultFoundVisibleOnSecondWidget(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));

            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 10);
                if (count > 0) {
                    for (int i = 0; i < data.getHeaderName().size(); i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getSmartCashTransactionHistory().getHeadersOnSecondWidget(i + 1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                    }
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - transactionHistoryWidgetLayoutTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "SmartCash Transaction History")
    @Test(priority = 3, groups = {"ProdTest", "SanityTest", "RegressionTest"}, dependsOnMethods = {"transactionHistoryWidgetLayoutTest", "openCustomerInteraction"})
    public void transactionHistoryWidgetDataTest() {
        try {
            selUtils.addTestcaseDescription("Validate all the row data display on UI as per api response.", "description");
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisibleOnSecondWidget(), true, "API is Giving and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get Smart Cash Transaction History for Customer", false);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isAirtelMoneyNoResultFoundVisibleOnSecondWidget(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 1);
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        if (amTransactionHistoryAPI.getResult().getData().get(i).getAmount().charAt(0) == '+') {
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isPosSignDisplayOnSecondWidget(i + 1), true, (i + 1) + "th Positive Sign is displayed  in case of Amount Credited.", (i + 1) + "th Positive Sign is not displayed in case of Amount Credited."));
                        } else {
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isNegSignDisplayOnSecondWidget(i + 1), true, (i + 1) + "th Negative Sign is displayed in case of Amount Debited.", (i + 1) + "th Negative Sign is not displayed in case of Amount Debited."));
                        }
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 2).replaceAll("\\R", " "), UtilsMethods.getDateFromEpoch(Long.parseLong(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate().toLowerCase()), "dd-MMM-yyy hh:mm aa").replace("am", "AM").replace("pm", "PM"), i + "th Date is matched Successfully", i + "th Date is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 3), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionType(), (i + 1) + "th Transaction Type is same as expected in API response.", (i + 1) + "th Transaction Type is NOT Matched"));
                        assertCheck.append(actions.assertEqualBoolean(amTransactionHistoryAPI.getResult().getData().get(i).getSource().contains(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 4).replaceAll("\\*", "")), true, (i + 1) + "th Sender MSISDN is matched Successfully", (i + 1) + "th Sender MSISDN is NOT Matched"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn()).contains(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 5).replaceAll("\\*", "")), true, (i + 1) + "th Receiver MSISDN is matched Successfully", (i + 1) + "th Receiver MSISDN is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 6).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getSecondPartyName()), (i + 1) + "th Beneficiary name is matched Successfully", (i + 1) + "th Beneficiary name is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 7), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), (i + 1) + "th Transaction Id is matched Successfully", (i + 1) + "th Transaction Id is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 8), amTransactionHistoryAPI.getResult().getData().get(i).getTxnChannel(), (i + 1) + "th Transaction Channel is matched Successfully", (i + 1) + "th Transaction Channel is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 9), amTransactionHistoryAPI.getResult().getData().get(i).getServiceCharge(), (i + 1) + "th Service Charge is matched Successfully", (i + 1) + "th Service Charge is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 10), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getBalanceBefore()), (i + 1) + "th Pre-balance is matched Successfully", (i + 1) + "th Pre-balance is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 11), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getBalanceAfter()), (i + 1) + "th Post-balance is matched Successfully", (i + 1) + "th Post-balance is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToHeader(i + 1, 12), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), (i + 1) + "th Status is matched Successfully", (i + 1) + "th Status is NOT Matched"));
                        if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isResendSMSIconVisible(i + 1, 1), true, "Resend SMS Icon is enabled as mentioned in API Response.", "Resend SMS Icon is not enabled as mentioned in API Response."));
                        }
                        if (amTransactionHistoryAPI.getResult().getData().get(i).getIsReversal()) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isReversalIconVisible(i + 1, 1), true, "Reversal Icon is enabled as mentioned in API Response.", "Reversal SMS Icon is not enabled as mentioned in API Response."));
                        }
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - transactionHistoryWidgetDataTest" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "More Airtel Money History")
    @Test(priority = 4, groups = {"ProdTest", "RegressionTest"}, dependsOnMethods = {"transactionHistoryWidgetLayoutTest", "openCustomerInteraction", "transactionHistoryWidgetDataTest"})
    public void txnHistoryMetadataTest() {
        try {
            selUtils.addTestcaseDescription("Validate all the transaction Detail display on UI as per api response", "description");
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisibleOnSecondWidget(), true, "API is Giving and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get Smart Cash Transaction History for Customer", false);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isAirtelMoneyNoResultFoundVisibleOnSecondWidget(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 10);
                if (count > 5) {
                    count = 5;
                }
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        pages.getSmartCashTransactionHistory().clickDropDownForMetaInfo(i + 1);
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToTxnHeader(i + 1, 1).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getService()), i + "1" + "th Service is matched Successfully", i + "th Service is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToTxnHeader(i + 1, 2).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getExternalTransactionId()), i + "th External Transaction Id is is matched Successfully", i + "th External Transaction Id is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToTxnHeader(i + 1, 3).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getInstrumentTransactionId()), i + "th Instrument Transaction Id is matched Successfully", i + "th Instrument Transaction Id  is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToTxnHeader(i + 1, 4).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getPaymentMode()), i + "th Payment Mode is matched Successfully", i + "th Payment Mode is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToTxnHeader(i + 1, 5).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getCategoryCode()), i + "th Category Code is matched Successfully", i + "th Category Code is NOT Matched"));
                        assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getValueCorrespondingToTxnHeader(i + 1, 6).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(amTransactionHistoryAPI.getResult().getData().get(i).getGradeCode()), i + "th Grade Code is matched Successfully", i + "th Grade Code is NOT Matched"));
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - txnHistoryMetadataTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"ProdTest", "RegressionTest"}, dependsOnMethods = {"transactionHistoryWidgetLayoutTest", "openCustomerInteraction", "txnHistoryMetadataTest"})
    public void sendNotificationSmsTest() {
        try {
            selUtils.addTestcaseDescription("Validate all the Send Notification transaction are displayed on UI as per api response", "description");
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisibleOnSecondWidget(), true, "API is Giving and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get Smart Cash Transaction History for Customer", false);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isAirtelMoneyNoResultFoundVisibleOnSecondWidget(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
            } else {
                int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 1);
                if (count > 0) {
                    for (int i = 0; i < count; i++) {

                        if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isResendSMSIconVisible(i + 1, 1), true, "SMS Notification Icon is enabled as mentioned in API Response.", "SMS Notification Icon is not enabled as mentioned in API Response."));
                            pages.getSmartCashTransactionHistory().clickSmsNotificationIcon();
                            assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().isSendNotificationHeaderVisible(), "Send Notification", "Send SMS Header is visible", "Send SMS header is NOT visible"));
                            assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().isIssueDetailVisible(), "Issue Detail:", "Issue Detail is visible", "Issue Detail is NOT visible"));
                            assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().isEnterCommentHeaderVisible(), "Enter Comment", "Enter Comment is visible", "Enter Comment is not Visible"));
                            assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().isSmsSelectReasonVisible(), "Select Reason *", "Select Reason is Visible", "Select Reason is not visible"));
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isSubmitBtnDisabled(), true, "Select Reason is Visible", "Select Reason is not visible"));
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is not visible"));
                            pages.getSmartCashTransactionHistory().performSmsNotification();
                            assertCheck.append(actions.assertEqualBoolean(pages.getSmartCashTransactionHistory().isSuccessPopUpVisible(), true, "Success Popup is visible after performing Submit action", "Success Popup is not visible after performing Submit action"));
                            String successText = "SMS has being resent on your device";
                            assertCheck.append(actions.assertEqualStringType(pages.getSmartCashTransactionHistory().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
                        }
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        }catch(Exception e){
            commonLib.fail("Exception in Method - txnHistoryMetadataTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"sendNotificationSmsTest"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing ResendSMS action", "description");
            pages.getAmSmsTrails().goToActionTrail();
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getActionType(), "SmartCash SMS Logs - Resend SMS", "Action type for Resend SMS is expected", "Action type for Resend SME is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getReason(), "\n" +
                    "Customer Request", "Reason for Resend SMS is as expected", "Reason for Resend SMS not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getComment(), constants.getValue(ApplicationConstants.COMMENT), "Comment for Resend SMS is expected", "Comment for Resend SMS is not as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }


}

