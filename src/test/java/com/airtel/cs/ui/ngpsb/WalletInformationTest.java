package com.airtel.cs.ui.ngpsb;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.model.cs.response.psb.cs.fetchbalance.FetchBalanceResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WalletInformationTest extends Driver {
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
    public void testWalletInformationWidget() {
        try {
            selUtils.addTestcaseDescription("Validate Wallet Information widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isWalletInformationWidgetVisible(), true, "Wallet Information widget is visible", "Wallet Information widget is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isWalletInformationHeaderVisible(), true, "Wallet Information header is visible", "Wallet Information  header is NOT visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getMiddleAuuid(), loginAUUID, "Auuid is visible at the middle of Wallet Information Widget and is correct", "Auuid is NOT visible at the middle of the Wallet Information Widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getFooterAuuid(), loginAUUID, "Auuid is visible at the footer of the Wallet Information Widget and is correct", "Auuid is NOT visible at the footer of the Wallet Information Widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isDetailedIconVisible(), true, "Detailed icon is visible", "Detailed icon is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBalanceInfoIconVisible(), true, "Balance info icon is visible", "Balance info icon  is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletInformationWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testWalletInformationWidgetData() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget data", "description");
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletStatus(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getStatus(), "Wallet status is same as Expected", "Wallet status  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletCategory(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getCategory(), "Wallet status is same as Expected", "Wallet status  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletCreatedBy(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getCreatedBy(), "Wallet Created By is same as Expected", "Wallet Created By is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletCreatedBy(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getCreatedOn(), "Wallet Created On is same as Expected", "Wallet Created On is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletModifiedBy(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getModifiedBy(), "Wallet Modified By is same as Expected", "Wallet Modified By is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletModifiedOn(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getModifiedOn(), "Wallet Modified On is same as Expected", "Wallet Modified On is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getOnboardingChannel(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getChannel(), "Onboarding Channel is same as Expected", "Onboarding Channel is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletNubanId(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getId(), "Wallet Nuban id is same as Expected", "Account nuban id is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getSecurityQuestionsSet(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getIsSecurityQuestionSet(), "Security Question Set is same as Expected", "Security Question Set is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getSecurityQuestionsConfigured(), clmDetails.getResult().getDetails().get(0).getIsSecurityQuestionSet().toString(), "Security Question Configured is same as Expected", "Security Question Configured  is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarringStatus(), clmDetails.getResult().getDetails().get(0).getUserBarred(),"Barring status is same as Expected", "Barring status  is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarringInfoIconVisible(), true, "Barring status info icon is visible", "Barring status info icon is NOT visible"));
            String nubanId = clmDetails.getResult().getDetails().get(0).getWallets().get(0).getId();
            String type = constants.getValue(ApplicationConstants.WALLET_TYPE);
            FetchBalanceResponse balance = api.getFetchBalance(customerNumber, nubanId, type);
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBalance(), balance.getResult().getBalance(), "Balance is same as Expected", "Balance is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getFrozenAmount(), balance.getResult().getFrozenAmt(), "Frozen Amount is same as Expected", "Frozen Amount is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getFicAmount(), balance.getResult().getFundsInClearance(), "FIC Amount is same as Expected", "FIC Amount is not same as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletInformationWidgetData" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check Wallets data
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testWalletsTab() {
        try {
            selUtils.addTestcaseDescription("Validate data of all the fields of Wallets tab", "description");
            String type = constants.getValue(ApplicationConstants.WALLET_TYPE);
            AMProfile amProfile = api.getAmProfile(customerNumber, type);
            if (amProfile.getStatusCode() == 200 && amProfile.getResult().getWallets().size() == 0) {
                commonLib.warning("Linked Wallets data is not available for the punched msisdn");
            } else if (amProfile.getStatusCode() == 3007 && amProfile.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Linked Wallets data ", true);
            } else {
                int size = pages.getAmLinkedWallets().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    String currency = amProfile.getResult().getWallets().get(i).getCurrency();
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 1), amProfile.getResult().getWallets().get(i).getWalletType(), "Wallet Type is same as expected ", "Wallet Type is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 2), amProfile.getResult().getWallets().get(i).getNubanId(), "Wallet Id is same as expected ", "Wallet Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 3), amProfile.getResult().getWallets().get(i).getTcpId(), "Profile Id is same as expected ", "Profile Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 4), currency + " " + amProfile.getResult().getWallets().get(i).getBalance(), "Balance is same as expected ", "Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 5), currency + " " + amProfile.getResult().getWallets().get(i).getFrozen(), "Frozen Amount is same as expected ", "Frozen Amount is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 6), currency + " " + amProfile.getResult().getWallets().get(i).getFundsInClearance(), "FIC is same as expected ", "FIC is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 7), amProfile.getResult().getWallets().get(i).getPrimary(), "Primary Value is same as expected ", "Primary Value is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValue(row, 8), amProfile.getResult().getWallets().get(i).getStatus(), "Wallet Status is same as expected ", "Wallet Status is NOT same as expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletsTab" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testSMSLogsTab() {
        try {
            selUtils.addTestcaseDescription("Validate Wallets tab data", "description");
            pages.getAmLinkedWallets().clickMoreIcon();
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
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 1), smsLogs.getResult().get(i).getSmsDate(), "Timestamp is same as expected ", "Timestamp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 2), smsLogs.getResult().get(i).getTransactionId(), "Transaction Id is same as expected ", "Transaction Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 3), smsLogs.getResult().get(i).getSmsId(), "Sms Id is same as expected ", "Sms Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getHeaderValue(row, 4), smsLogs.getResult().get(i).getSmsBody(), "Sms Body is same as expected ", "Sms Body is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getAction(row, 5), "Resend SMS", "Resend SMS is visible in Action", "Resend SMS is NOT visible in Action"));
                }
            }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSMSLogsTab" + e.fillInStackTrace(), true);
        }
    }


}
