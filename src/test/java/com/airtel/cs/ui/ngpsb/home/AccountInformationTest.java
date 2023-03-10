package com.airtel.cs.ui.ngpsb.home;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.actionconfig.MetaInfo;
import com.airtel.cs.model.cs.response.actiontrail.EventLogsResponse;
import com.airtel.cs.model.cs.response.actiontrail.EventResult;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.model.cs.response.psb.cs.bankdetails.BankDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.fetchbalance.FetchBalanceResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class AccountInformationTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource psbApi = new PsbRequestSource();
    RequestSource api = new RequestSource();
    CLMDetailsResponse clmDetails;
    SmsLogsResponse smsLogs;
    String barringStatus;
    String className = this.getClass().getName();


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkAccountsSize() {
        customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER3_MSISDN);
        clmDetails = psbApi.getCLMDetails(customerNumber);
        if (clmDetails.getResult().getDetails().get(0).getAccounts().size() == 0) {
            commonLib.skip("Skipping because there are no accounts linked with the msisdn ");
            throw new SkipException("Skipping because this feature is not applicable when there are no accounts linked with the msisdn");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
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


    @Test(priority = 2, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testAccountInformationWidget() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isAccountInformationWidgetVisible(), true, "Account Information widget is visible", "Account Information widget is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isAccountInformationHeaderVisible(), true, "Account Information header is visible", "Account Information  header is NOT visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getMiddleAuuid(), loginAUUID, "Auuid is visible at the middle of the Account Information Widget and is correct", "Auuid is NOT visible at the middle of the Account Information Widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getFooterAuuid(), loginAUUID, "Auuid is visible at the footer of the Account Information Widget and is correct", "Auuid is NOT visible at the footer of the Account Information Widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isDetailedIconVisible(), true, "Detailed icon is visible", "Detailed icon is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isBalanceInfoIconVisible(), true, "Balance info icon is visible", "Balance info icon  is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountInformationWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testAccountInformationWidgetData() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget data", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountStatus().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getStatus()), "Account status is same as Expected", "Account status  is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountCategory().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCategory()), "Account Category is same as Expected", "Account Category  is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountCreatedBy().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCreatedBy()), "Account Created By is same as Expected", "Account Created By is not same as Expected"));
            String createdOnDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCreatedOn()), constants.getValue(CommonConstants.NGPSB_ACCOUNT_CREATED_DATE_PATTERN));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountCreatedOn().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(createdOnDate), "Account Created On is same as Expected", "Account Created On is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountModifiedBy().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getModifiedBy()), "Account Modified By is same as Expected", "Account Modified By is not same as Expected"));
            String modifiedDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getModifiedOn()), constants.getValue(CommonConstants.NGPSB_ACCOUNT_CREATED_DATE_PATTERN));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountModifiedOn().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(modifiedDate), "Account Modified On is same as Expected", "Account Modified On is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getOnboardingChannel().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getChannel()), "Onboarding Channel is same as Expected", "Onboarding Channel is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getAccountNubanId().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId()), "Account Nuban id is same as Expected", "Account nuban id is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getSecurityQuestionsSet().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getIsSecurityQuestionSet()), "Security Question Set is same as Expected", "Security Question Set is not same as Expected"));
            String securityQuestionConfigured = pages.getAccountInformation().getSecurityQuestionsConfigured();
            assertCheck.append(actions.assertEqualStringType(securityQuestionConfigured.toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(String.valueOf(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getSecurityQuestionsConfigured())), "Security Question Configured is same as Expected", "Security Question Configured is not same as Expected"));
            if (securityQuestionConfigured.equalsIgnoreCase("YES"))
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getSecurityQuestionsSetColour(), "#33a833", "Colour of Security Questions Set is same as expected", "Colour of Security Questions Set is NOT same as expected"));
            else if (securityQuestionConfigured.equalsIgnoreCase("NO"))
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getBarringStatusColour(), "#e4000e", "Colour of Security Questions Set is same as expected", "Colour of Security Questions Set is NOT same as expected"));
            barringStatus = pages.getAccountInformation().getBarringStatus();
            assertCheck.append(actions.assertEqualStringType(barringStatus.toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getBarred()), "Barring status is same as Expected", "Barring status  is not same as Expected"));
            if (barringStatus.equalsIgnoreCase("YES"))
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getBarringStatusColour(), "#33a833", "Colour of Barring Status is same as expected", "Colour of Barring Status is NOT same as expected"));
            else if (barringStatus.equalsIgnoreCase("NO"))
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getBarringStatusColour(), "#e4000e", "Colour of Barring Status is same as expected", "Colour of Barring Status is NOT same as expected"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountInformationWidgetData" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testBarringStatus() {
        try {
            selUtils.addTestcaseDescription("Validate Barring Status widget data", "description");
            if (barringStatus.equalsIgnoreCase("NO")) {
                commonLib.warning("Barring Status is N0, Unable to display barring details");
            } else if (barringStatus.equalsIgnoreCase("YES")) {
                pages.getWalletInformation().hoverOnBarringInfoIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarTypeHeaderVisible(), true, "Bar type header is visible", "Bar Type header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarReasonHeaderVisible(), true, "Bar Reason header is visible", "Bar Reason header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarredByHeaderVisible(), true, "Bar By header is visible", "Bar By header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarredOnHeaderVisible(), true, "Bar On is visible", "Bar On header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isRemarksHeaderVisible(), true, "Remarks header is visible", "Remarks header is NOT visible"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarType(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getBarDetails().getBarType()), "Bar Type status is same as Expected", "Bar Type  is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarReason(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getBarDetails().getBarReason()), "Bar Reason is same as Expected", "Bar Reason is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarredBy(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getBarDetails().getBarredBy()), "Barred By is same as Expected", "Barred By is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarredOn(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getBarDetails().getBarredOn()), "Barred On is same as Expected", "Barred On is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getRemarks(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getBarDetails().getRemarks()), "Remarks is same as Expected", "Remarks is not same as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } else {
                commonLib.fail("Not able to get barring status", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBarringStatus" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check Accounts balance
     */
    @Test(priority = 5, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testAccountsBalance() {
        try {
            selUtils.addTestcaseDescription("Validate Accounts balance", "description");
            String nubanId = clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId();
            String type = constants.getValue(ApplicationConstants.ACCOUNT_TYPE);
            FetchBalanceResponse balance = psbApi.getFetchBalance(customerNumber, nubanId, type);
            String currency = balance.getResult().currency;
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getBalance().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getBalance()), "Balance is same as Expected", "Balance is not same as Expected"));
            if (pages.getAccountInformation().getBalance().trim().equalsIgnoreCase("- -"))
                commonLib.warning("Balance is not available so unable to display Frozen Amount");
            else {
                pages.getAccountInformation().hoverOnBalanceInfoIcon();
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getFrozenAmount().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getFrozenAmt()), "Frozen Amount is same as Expected", "Frozen Amount is not same as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountsBalance" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 6, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testBankAccountsTabs() {
        try {
            selUtils.addTestcaseDescription("Validate Bank Accounts tab data", "description");
            pages.getAmLinkedWallets().clickMoreIcon();
            String nubanId = clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId();
            BankDetailsResponse bankDetails = psbApi.getAmBankDetails(customerNumber, nubanId);
            assertCheck.append(actions.assertEqualIntType(bankDetails.getStatusCode(), 200, "Bank Details API Status Code Matched and is :" + bankDetails.getStatusCode(), "Bank Details API Status Code NOT Matched and is :" + bankDetails.getStatusCode(), false));
            if (bankDetails.getStatusCode() == 200 && bankDetails.getResult().size() == 0) {
                commonLib.warning("Bank Accounts data is not available for the test msisdn");
            } else if (bankDetails.getStatusCode() == 2500 && bankDetails.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Bank Accounts", true);
            } else {
                int size = pages.getAmSmsTrails().checkRowSize();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 1), bankDetails.getResult().get(i).getAccountNumber(), "Account No. is same as expected ", "Account No.is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 2), bankDetails.getResult().get(i).getCustomerNo(), "Customer No. is same as expected ", "Account No.is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 3), bankDetails.getResult().get(i).getOpeningBalance(), "Opening Balance is same as expected ", "Opening Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 4), bankDetails.getResult().get(i).getAvailableBalance(), "Available Balance is same as expected ", "Available Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 5), bankDetails.getResult().get(i).getFrozen(), "Frozen Balance is same as expected ", "Frozen Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 6), bankDetails.getResult().get(i).getCurrentBalance(), "Current Balance is same as expected ", "Current Balance is NOT same as expected"));
                    String accountStatus = pages.getBankAccount().getHeaderValue(row, 7);
                    assertCheck.append(actions.assertEqualStringType(accountStatus, bankDetails.getResult().get(i).getStatus(), "Account status is same as expected ", "Account status is NOT same as expected"));
                    if (accountStatus.equalsIgnoreCase("ACTIVE"))
                        assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValueColor(row, 7), "#33a833", "Colour of Account Status is same as expected", "Colour of Account Status is NOT same as expected"));
                    else if (accountStatus.equalsIgnoreCase("INACTIVE"))
                        assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValueColor(row, 7), "#e4000e", "Colour of Account Status is same as expected", "Colour of Account Status is NOT same as expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBankAccountsTabs" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 7, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testSmsLogsTabs() {
        try {
            selUtils.addTestcaseDescription("Validate Wallets tab data", "description");
            pages.getAmLinkedWallets().clickSmsLogsTab();
            smsLogs = psbApi.getSMSLogs(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "Sms Logs API Status Code Matched and is :" + clmDetails.getStatusCode(), "Sms Logs API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (smsLogs.getStatusCode() == 200 && smsLogs.getResult().size() == 0) {
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                int size = pages.getAmSmsTrails().checkRowSize();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 1), smsLogs.getResult().get(i).getSmsDate(), "Timestamp is same as expected ", "Timestamp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 2), smsLogs.getResult().get(i).getTransactionId(), "Transaction Id is same as expected ", "Transaction Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 3), smsLogs.getResult().get(i).getSmsId(), "Sms Id is same as expected ", "Sms Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 4), smsLogs.getResult().get(i).getSmsBody(), "Sms Body is same as expected ", "Sms Body is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getAction(row, 5), "Resend SMS", "Resend SMS is visible in Action", "Resend SMS is NOT visible in Action"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSmsLogsTabs" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"testSmsLogsTabs"})
    public void testResendSms() {
        try {
            selUtils.addTestcaseDescription("Validate Resend SMS", "description");
            smsLogs = psbApi.getSMSLogs(customerNumber);
            assertCheck.append(actions.assertEqualIntType(smsLogs.getStatusCode(), 200, "Sms Logs API Status Code Matched and is :" + smsLogs.getStatusCode(), "Sms Logs API Status Code NOT Matched and is :" + smsLogs.getStatusCode(), false));
            if (smsLogs.getStatusCode() != 200) {
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                assertCheck = pages.getAmSmsTrails().sendSMS(smsLogs);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getAmSmsTrails().clickCrossIcon();
            commonLib.fail("Exception in Method - ResendSms" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 9, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"testResendSms"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing ResendSMS action", "description");
            pages.getAmSmsTrails().goToActionTrail();
            EventLogsResponse eventLogs = api.getEventHistory(customerNumber, "ACTION");
            int statusCode = eventLogs.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Event Logs API success and status code is :" + statusCode, "Event Logs API got failed and status code is :" + statusCode, false, true));
            EventResult eventResult = eventLogs.getResult().get(0);
            if (statusCode == 200) {
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getActionType(), eventResult.getActionType(), "Action type for Resend SMS is expected", "Action type for Resend SME is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getReason(), eventResult.getReason(), "Reason for Resend SMS is as expected", "Reason for Resend SMS not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getComment(), eventResult.getComments(), "Comment for Resend SMS is expected", "Comment for Resend SMS is not as expected"));
                pages.getOscRecharge().clickingOnDropDown();
                List<MetaInfo> metaInfo = eventResult.getMetaInfo();
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getCustomerMsisdn().trim(), pages.getDemoGraphicPage().getKeyValueAPI(metaInfo.get(0).getValue()), "Customer Msisdn rendered as expected in action trail's meta info", "Customer Msisdn rendered as expected in action trail's meta info's meta info"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getSmsDateTime().trim(), pages.getDemoGraphicPage().getKeyValueAPI(metaInfo.get(1).getValue()), "Sms Date and Time rendered as expected in action trail's meta info", "Sms Date and Time rendered as expected in action trail's meta info"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getTxnId().trim(), pages.getDemoGraphicPage().getKeyValueAPI(metaInfo.get(2).getValue()), "SmartCash Txn Id rendered as expected in action trail's meta info", "SmartCash Txn Id rendered as expected in action trail's meta info"));
            } else
                commonLib.fail("Not able to fetch action trail event log using API as its status code is :" + statusCode, true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }

}
