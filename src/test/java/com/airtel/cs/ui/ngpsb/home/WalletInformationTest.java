package com.airtel.cs.ui.ngpsb.home;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
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
    String nubanId, barringStatus;
    String className = this.getClass().getName();
    SmsLogsResponse smsLogs;


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkWalletsSize() {
        customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER3_MSISDN);
        clmDetails = api.getCLMDetails(customerNumber);
        if (clmDetails.getResult().getDetails().get(0).getWallets().size() == 0) {
            commonLib.skip("Skipping because there are no wallets linked with the msisdn ");
            throw new SkipException("Skipping because this feature is not applicable when there are no wallets linked with the msisdn");
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

    @Test(priority = 3, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testWalletInformationWidgetData() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information widget data", "description");
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletStatus(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getStatus(), "Wallet status is same as Expected", "Wallet status  is not same as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getWalletStatusColour(), "#33a833", "Colour of Wallet Status is same as expected", "Colour of Wallet Status is NOT same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletCategory(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getCategory(), "Wallet Category is same as Expected", "Wallet Category  is not same as Expected"));
            String createdOnDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(0).getWallets().get(0).getCreatedOn()), constants.getValue(CommonConstants.NGPSB_WALLET_CREATED_DATE_PATTERN));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletCreatedOn(), createdOnDate, "Wallet Created On is same as Expected", "Wallet Created On is not same as Expected"));
            pages.getWalletInformation().hoverOnWalletCreated();
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletCreatedBy(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getCreatedBy(), "Wallet Created By is same as Expected", "Wallet Created By is not same as Expected"));
            String modifiedOnDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(0).getWallets().get(0).getModifiedOn()), constants.getValue(CommonConstants.NGPSB_WALLET_CREATED_DATE_PATTERN));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletModifiedOn(), modifiedOnDate, "Wallet Modified On is same as Expected", "Wallet Modified On is not same as Expected"));
            pages.getWalletInformation().hoverOnWalletModified();
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletModifiedBy(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getModifiedBy(), "Wallet Modified By is same as Expected", "Wallet Modified By is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getOnboardingChannel(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getWallets().get(0).getChannel()), "Onboarding Channel is same as Expected", "Onboarding Channel is not same as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getWalletNubanId(), clmDetails.getResult().getDetails().get(0).getWallets().get(0).getId(), "Wallet Nuban id is same as Expected", "Account nuban id is not same as Expected"));
            String securityQuestionsSet = pages.getWalletInformation().getSecurityQuestionsSet();
            assertCheck.append(actions.matchUiAndAPIResponse(securityQuestionsSet, clmDetails.getResult().getDetails().get(0).getWallets().get(0).getIsSecurityQuestionSet(), "Security Question Set is same as Expected", "Security Question Set is not same as Expected"));
            if (securityQuestionsSet.equalsIgnoreCase("YES"))
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getSecurityQuestionsSetColour(), "#33a833", "Colour of Security Questions Set is same as expected", "Colour of Security Questions Set is NOT same as expected"));
            else if (securityQuestionsSet.equalsIgnoreCase("NO"))
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getBarringStatusColour(), "#e4000e", "Colour of Security Questions Set is same as expected", "Colour of Security Questions Set is NOT same as expected"));
            final Integer securityQuestionsConfigured = clmDetails.getResult().getDetails().get(0).getWallets().get(0).getSecurityQuestionsConfigured();
            String securityQuestion = null;
            if (securityQuestionsConfigured == null)
                securityQuestion = "";
            else
                securityQuestion = securityQuestionsConfigured.toString();
            assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getSecurityQuestionsConfigured(), pages.getDemoGraphicPage().getKeyValueAPI(securityQuestion), "Security Question Configured is same as Expected", "Security Question Configured  is not same as Expected"));
            barringStatus = pages.getWalletInformation().getBarringStatus();
            assertCheck.append(actions.matchUiAndAPIResponse(barringStatus, clmDetails.getResult().getDetails().get(0).getUserBarred(), "Barring status is same as Expected", "Barring status  is not same as Expected"));
            if (barringStatus.equalsIgnoreCase("YES"))
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getBarringStatusColour(), "#33a833", "Colour of Barring Status is same as expected", "Colour of Barring Status is NOT same as expected"));
            else if (barringStatus.equalsIgnoreCase("NO"))
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getBarringStatusColour(), "#e4000e", "Colour of Barring Status is same as expected", "Colour of Barring Status is NOT same as expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isBarringInfoIconVisible(), true, "Barring status info icon is visible", "Barring status info icon is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletInformationWidgetData" + e.fillInStackTrace(), true);
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

                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarType(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarType(), "Bar Type status is same as Expected", "Bar Type  is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarReason(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarReason(), "Bar Reason is same as Expected", "Bar Reason is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarredBy(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarredBy(), "Barred By is same as Expected", "Barred By is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getBarredOn(), clmDetails.getResult().getDetails().get(0).getBarDetails().getBarredOn(), "Barred On is same as Expected", "Barred On is not same as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getWalletInformation().getRemarks(), clmDetails.getResult().getDetails().get(0).getBarDetails().getRemarks(), "Remarks is same as Expected", "Remarks is not same as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } else {
                commonLib.fail("Not able to get barring status", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBarringStatus" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check Wallets balance
     */
    @Test(priority = 5, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testWalletsBalance() {
        try {
            selUtils.addTestcaseDescription("Validate Wallets balance", "description");
            nubanId = clmDetails.getResult().getDetails().get(0).getWallets().get(0).getId();
            String type = constants.getValue(ApplicationConstants.WALLET_TYPE);
            FetchBalanceResponse balance = api.getFetchBalance(customerNumber, nubanId, type);
            String currency = balance.getResult().currency;
            assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getBalance().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getBalance()), "Balance is same as Expected", "Balance is not same as Expected"));
            if (pages.getWalletInformation().getBalance().trim().equalsIgnoreCase("- -"))
                commonLib.warning("Balance is not available so unable to display Frozen and FIC Amount");
            else {
                pages.getWalletInformation().hoverOnBalanceInfoIcon();
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getFrozenAmount().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getFrozenAmt()), "Frozen Amount is same as Expected", "Frozen Amount is not same as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getWalletInformation().getFicAmount().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(currency + " " + balance.getResult().getFundsInClearance()), "FIC Amount is same as Expected", "FIC Amount is not same as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletsBalance" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check Wallets data
     */
    @Test(priority = 6, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testWalletsTab() {
        try {
            selUtils.addTestcaseDescription("Validate data of all the fields of Wallets tab", "description");
            pages.getAmLinkedWallets().clickMoreIcon();
            String type = constants.getValue(ApplicationConstants.WALLET_TYPE);
            AMProfile amProfile = api.getAmProfile(customerNumber, nubanId, type);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "AM Profile API Status Code Matched and is :" + amProfile.getStatusCode(), "AM Profile API Status Code NOT Matched and is :" + amProfile.getStatusCode(), false));
            if (amProfile.getStatusCode() == 200 && amProfile.getResult().getWallets().size() == 0) {
                commonLib.warning("Linked Wallets data is not available for the msisdn");
            } else if (amProfile.getStatusCode() == 3007 && amProfile.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Linked Wallets data ", true);
            } else {
                int size = pages.getAmLinkedWallets().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    String currency = amProfile.getResult().getWallets().get(i).getCurrency();
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 1), amProfile.getResult().getWallets().get(i).getWalletType(), "Wallet Type is same as expected ", "Wallet Type is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 2), amProfile.getResult().getWallets().get(i).getNubanId(), "Wallet Id is same as expected ", "Wallet Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 3), amProfile.getResult().getWallets().get(i).getTcpId(), "Profile Id is same as expected ", "Profile Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 4), currency + " " + amProfile.getResult().getWallets().get(i).getBalance(), "Balance is same as expected ", "Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 5), currency + " " + amProfile.getResult().getWallets().get(i).getFrozen(), "Frozen Amount is same as expected ", "Frozen Amount is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 6), currency + " " + amProfile.getResult().getWallets().get(i).getFundsInClearance(), "FIC is same as expected ", "FIC is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 7), amProfile.getResult().getWallets().get(i).getPrimary(), "Primary Value is same as expected ", "Primary Value is NOT same as expected"));
                    String walletStatus = pages.getAmLinkedWallets().getRowValue(row, 8);
                    assertCheck.append(actions.assertEqualStringType(walletStatus, amProfile.getResult().getWallets().get(i).getStatus(), "Wallet Status is same as expected ", "Wallet Status is NOT same as expected"));
                    if (walletStatus.equalsIgnoreCase("ACTIVE"))
                        assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValueColor(row, 7), "#33a833", "Colour of Wallet Status is same as expected", "Colour of Wallet Status is NOT same as expected"));
                    else if (walletStatus.equalsIgnoreCase("INACTIVE"))
                        assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getHeaderValueColor(row, 7), "#e4000e", "Colour of Wallet Status is same as expected", "Colour of Wallet Status is NOT same as expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletsTab" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 7, groups = {"SanityTest", "ProdTest","RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testSmsLogsTab() {
        try {
            selUtils.addTestcaseDescription("Validate Wallets tab data", "description");
            pages.getAmLinkedWallets().clickSmsLogsTab();
            smsLogs = api.getSMSLogs(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "Sms Logs API Status Code Matched and is :" + clmDetails.getStatusCode(), "Sms Logs API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (smsLogs.getStatusCode() == 200 && smsLogs.getResult().size() == 0) {
                commonLib.warning("SMS Logs data is not available for the test msisdn");
            } else if (smsLogs.getStatusCode() == 2500 && smsLogs.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Sms Logs data ", true);
            } else {
                int size = pages.getAmSmsTrails().checkRowSize();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 1).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(smsLogs.getResult().get(i).getSmsDate()), "Timestamp is same as expected ", "Timestamp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 2).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(smsLogs.getResult().get(i).getTransactionId()), "Transaction Id is same as expected ", "Transaction Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 3).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(smsLogs.getResult().get(i).getSmsId()), "Sms Id is same as expected ", "Sms Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getRowValue(row, 4).toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(smsLogs.getResult().get(i).getSmsBody()), "Sms Body is same as expected ", "Sms Body is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getAction(row, 5), "Resend SMS", "Resend SMS is visible in Action", "Resend SMS is NOT visible in Action"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSmsLogsTab" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 8, groups = {"SanityTest",  "RegressionTest"}, dependsOnMethods = {"testSmsLogsTab"})
    public void testResendSms() {
        try {
            selUtils.addTestcaseDescription("Validate Resend SMS", "description");
            smsLogs = api.getSMSLogs(customerNumber);
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

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"testSmsLogsTab", "testResendSms"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing ResendSMS action", "description");
            pages.getAmSmsTrails().goToActionTrail();
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getActionType(), "SmartCash SMS Logs - Resend SMS", "Action type for Resend SMS is expected", "Action type for Resend SME is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getReason(), "Customer Request", "Reason for Resend SMS is as expected", "Reason for Resend SMS not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getComment(), ApplicationConstants.COMMENT, "Comment for Resend SMS is expected", "Comment for Resend SMS is not as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }

}
