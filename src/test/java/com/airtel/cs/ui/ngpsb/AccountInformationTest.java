package com.airtel.cs.ui.ngpsb;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.fetchbalance.FetchBalanceResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AccountInformationTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;


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
                boolean pageLoaded = pages.getDemographicWidget().isPageLoaded(clmDetails);
                if (!pageLoaded)
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
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

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testAccountInformationWidgetData() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget data", "description");
           assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountStatus(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getStatus(), "Account status is same as Expected", "Account status  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountCategory(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCategory(), "Account status is same as Expected", "Account status  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountCreatedBy(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCreatedBy(), "Account Created By is same as Expected", "Account Created By is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountCreatedOn(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getCreatedOn(), "Account Created On is same as Expected", "Account Created On is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountModifiedBy(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getModifiedBy(), "Account Modified By is same as Expected", "Account Modified By is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountModifiedOn(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getModifiedOn(), "Account Modified On is same as Expected", "Account Modified On is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getOnboardingChannel(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getChannel(), "Onboarding Channel is same as Expected", "Onboarding Channel is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getAccountNubanId(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId(), "Account Nuban id is same as Expected", "Account nuban id is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getSecurityQuestionsSet(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getIsSecurityQuestionSet(), "Security Question Set is same as Expected", "Security Question Set is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getSecurityQuestionsConfigured(), clmDetails.getResult().getDetails().get(0).getIsSecurityQuestionSet().toString(), "Security Question Configured is same as Expected", "Security Question Configured  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getBarringStatus(), clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getBarred(), "Barring status is same as Expected", "Barring status  is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarringInfoIconVisible(), true, "Barring status info icon is visible", "Barring status info icon is NOT visible"));
            String nubanId=clmDetails.getResult().getDetails().get(0).getAccounts().get(0).getId();
            String type=constants.getValue(ApplicationConstants.ACCOUNT_TYPE);
            FetchBalanceResponse balance=api.getFetchBalance(customerNumber,nubanId,type);
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getBalance(),balance.getResult().getBalance() , "Balance is same as Expected", "Balance is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getAccountInformation().getFrozenAmount(),balance.getResult().getFrozenAmt() , "Frozen Amount is same as Expected", "Frozen Amount is not same as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAccountInformationWidgetData" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testWalletTabs() {
        try {
            selUtils.addTestcaseDescription("Validate Wallets tab data", "description");
            pages.getAmLinkedWallets().clickMoreIcon();
            SmsLogsResponse smsLogs=api.getSMSLogs(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "Sms Logs API Status Code Matched and is :" + clmDetails.getStatusCode(), "Sms Logs API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (smsLogs.getStatusCode() == 200 && smsLogs.getResult().size()==0) {
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                int size = pages.getAmSmsTrails().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 1), smsLogs.getResult().get(i).getSmsDate(), "Timestamp is same as expected ", "Timestamp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 2), smsLogs.getResult().get(i).getTransactionId(), "Transaction Id is same as expected ", "Transaction Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 3), smsLogs.getResult().get(i).getSmsId(), "Sms Id is same as expected ", "Sms Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 4), smsLogs.getResult().get(i).getSmsBody(), "Sms Body is same as expected ", "Sms Body is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getAction(row,5), "Resend SMS", "Resend SMS is visible in Action", "Resend SMS is NOT visible in Action"));
                }
            }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletTabs" + e.fillInStackTrace(), true);
        }
    }

}
