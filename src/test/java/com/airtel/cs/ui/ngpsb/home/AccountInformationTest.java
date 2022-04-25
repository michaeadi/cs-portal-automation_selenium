package com.airtel.cs.ui.ngpsb.home;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.model.cs.response.psb.cs.bankdetails.BankDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.fetchbalance.FetchBalanceResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AccountInformationTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String barringStatus;


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkAccountsSize() {
        customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
        clmDetails = api.getCLMDetails(customerNumber);
        if (clmDetails.getResult().getDetails().get(0).getAccounts().size() == 0) {
            commonLib.skip("Skipping because there are no accounts linked with the msisdn ");
            throw new SkipException("Skipping because this feature is not applicable when there are no accounts linked with the msisdn");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails);
                if (!pageLoaded)
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testAccountInformationWidget() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isAccountInformationWidgetVisible(), true, "Account Information widget is visible", "Account Information widget is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isAccountInformationHeaderVisible(), true, "Account Information header is visible", "Account Information  header is NOT visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getMiddleAuuid(), loginAUUID, "Auuid is visible at the middle of the Demo Graphic Widget and is correct", "Auuid is NOT visible at the middle of the Demo Graphic Widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getFooterAuuid(), loginAUUID, "Auuid is visible at the footer of the Demo Graphic Widget and is correct", "Auuid is NOT visible at the footer of the Demo Graphic Widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isDetailedIconVisible(), true, "Detailed icon is visible", "Detailed icon is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isBalanceInfoIconVisible(), true, "Balance info icon is visible", "Balance info icon  is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountInformationWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testAccountInformationWidgetData() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget data", "description");
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountStatus(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getStatus(), "Account status is same as Expected", "Account status  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountCategory(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCategory(), "Account Category is same as Expected", "Account Category  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountCreatedBy(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCreatedBy(), "Account Created By is same as Expected", "Account Created By is not same as Expected"));
            String createdOnDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCreatedOn()), constants.getValue(CommonConstants.NGPSB_ACCOUNT_CREATED_DATE_PATTERN));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountCreatedOn(), createdOnDate, "Account Created On is same as Expected", "Account Created On is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountModifiedBy(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getModifiedBy(), "Account Modified By is same as Expected", "Account Modified By is not same as Expected"));
            String modifiedDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getModifiedOn()), constants.getValue(CommonConstants.NGPSB_ACCOUNT_CREATED_DATE_PATTERN));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountModifiedOn(), modifiedDate, "Account Modified On is same as Expected", "Account Modified On is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getOnboardingChannel(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getChannel(), "Onboarding Channel is same as Expected", "Onboarding Channel is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountNubanId(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId(), "Account Nuban id is same as Expected", "Account nuban id is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getSecurityQuestionsSet(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getIsSecurityQuestionSet(), "Security Question Set is same as Expected", "Security Question Set is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getSecurityQuestionsConfigured(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getSecurityQuestionsConfigured(), "Security Question Configured is same as Expected", "Security Question Configured is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getBarringStatus(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getBarred(), "Barring status is same as Expected", "Barring status  is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarringInfoIconVisible(), true, "Barring status info icon is visible", "Barring status info icon is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getSecurityQuestionsConfigured(), clmDetails.getResult().getDetails().get(0).getIsSecurityQuestionSet().toString(), "Security Question Configured is same as Expected", "Security Question Configured  is not same as Expected"));
            barringStatus = pages.getAccountInformation().getBarringStatus();
            assertCheck.append(actions.matchUiAndAPIResponse(barringStatus,clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getBarred(), "Barring status is same as Expected", "Barring status  is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarringInfoIconVisible(), true, "Barring status info icon is visible", "Barring status info icon is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountInformationWidgetData" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testBarringStatus() {
        try {
            selUtils.addTestcaseDescription("Validate Barring Status widget data", "description");
            pages.getWalletInformation().hoverOnBarringInfoIcon();
            if (barringStatus.equalsIgnoreCase("NO"))
                commonLib.info("Barring Status is N0, Unable to display barring details");
            else if (barringStatus.equalsIgnoreCase("YES")) {
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarTypeHeaderVisible(), true, "Bar type header is visible", "Bar Type header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarReasonHeaderVisible(), true, "Bar Reason header is visible", "Bar Reason header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarredByHeaderVisible(), true, "Bar By header is visible", "Bar By header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarredOnHeaderVisible(), true, "Bar On is visible", "Bar On header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isRemarksHeaderVisible(), true, "Remarks header is visible", "Remarks header is NOT visible"));

                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarType(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarType(), "Bar Type status is same as Expected", "Bar Type  is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarReason(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarReason(), "Bar Reason is same as Expected", "Bar Reason is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarredBy(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarredBy(), "Barred By is same as Expected", "Barred By is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarredOn(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarredOn(), "Barred On is same as Expected", "Barred On is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getRemarks(), clmDetails.getResult().getDetails().get(0).getBarDetails().getRemarks(), "Remarks is same as Expected", "Remarks is not same as Expected"));

            }else
                commonLib.fail("Not able to get barring status", true);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBarringStatus" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check Accounts balance
     */
    @Test(priority = 5, groups = {"SanityTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testAccountsBalance() {
        try {
            selUtils.addTestcaseDescription("Validate Accounts balance", "description");
            String nubanId = clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId();
            String type = constants.getValue(ApplicationConstants.ACCOUNT_TYPE);
            FetchBalanceResponse balance = api.getFetchBalance(customerNumber, nubanId, type);
            String currency = balance.getResult().currency;
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getBalance(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getBalance()), "Balance is same as Expected", "Balance is not same as Expected"));
            if (pages.getAccountInformation().getBalance().trim().equalsIgnoreCase("- -"))
                commonLib.warning("Balance is not available so unable to display Frozen and FIC Amount");
            else {
                pages.getAccountInformation().hoverOnBalanceInfoIcon();
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformation().getFrozenAmount(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getFrozenAmt()), "Frozen Amount is same as Expected", "Frozen Amount is not same as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getFicAmount(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getFundsInClearance()), "FIC Amount is same as Expected", "FIC Amount is not same as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountsBalance" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 6, groups = {"SanityTest","ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testBankAccountsTabs() {
        try {
            selUtils.addTestcaseDescription("Validate Bank Accounts tab data", "description");
            pages.getAmLinkedWallets().clickMoreIcon();
            String nubanId = clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId();
            BankDetailsResponse bankDetails = api.getAmBankDetails(customerNumber, nubanId);
            assertCheck.append(actions.assertEqualIntType(bankDetails.getStatusCode(), 200, "Bank Details API Status Code Matched and is :" + bankDetails.getStatusCode(), "Bank Details API Status Code NOT Matched and is :" + bankDetails.getStatusCode(), false));
            if (bankDetails.getStatusCode() == 200 && bankDetails.getResult().size() == 0) {
                commonLib.warning("Bank Accounts data is not available for the test msisdn");
            } else if (bankDetails.getStatusCode() == 2500 && bankDetails.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Bank Accounts", true);
            } else {
                int size = pages.getAmSmsTrails().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 1), bankDetails.getResult().get(i).getAccountNumber(), "Account No. is same as expected ", "Account No.is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 2), bankDetails.getResult().get(i).getCustomerNo(), "Customer No. is same as expected ", "Account No.is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 3), bankDetails.getResult().get(i).getOpeningBalance(), "Opening Balance is same as expected ", "Opening Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 4), bankDetails.getResult().get(i).getAvailableBalance(), "Available Balance is same as expected ", "Available Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 4), bankDetails.getResult().get(i).getFrozen(), "Frozen Balance is same as expected ", "Frozen Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 4), bankDetails.getResult().get(i).getCurrentBalance(), "Current Balance is same as expected ", "Current Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 4), bankDetails.getResult().get(i).getStatus(), "Account status is same as expected ", "Account status is NOT same as expected"));

                }
            }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBankAccountsTabs" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest","ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})

    public void testSmsLogsTabs() {
        try {
            selUtils.addTestcaseDescription("Validate Wallets tab data", "description");
            pages.getAmLinkedWallets().clickSmsLogsTab();
            SmsLogsResponse smsLogs = api.getSMSLogs(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "Sms Logs API Status Code Matched and is :" + clmDetails.getStatusCode(), "Sms Logs API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (smsLogs.getStatusCode() == 200 && smsLogs.getResult().size() == 0) {
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                int size = pages.getAmSmsTrails().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 1), smsLogs.getResult().get(i).getSmsDate(), "Timestamp is same as expected ", "Timestamp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 2), smsLogs.getResult().get(i).getTransactionId(), "Transaction Id is same as expected ", "Transaction Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 3), smsLogs.getResult().get(i).getSmsId(), "Sms Id is same as expected ", "Sms Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 4), smsLogs.getResult().get(i).getSmsBody(), "Sms Body is same as expected ", "Sms Body is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getAction(row, 5), "Resend SMS", "Resend SMS is visible in Action", "Resend SMS is NOT visible in Action"));
                }
            }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSmsLogsTabs" + e.fillInStackTrace(), true);
        }
    }

}

