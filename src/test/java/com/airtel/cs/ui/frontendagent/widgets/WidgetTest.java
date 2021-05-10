package com.airtel.cs.ui.frontendagent.widgets;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AMTransactionsWidget;
import com.airtel.cs.pagerepository.pagemethods.CurrentBalanceWidget;
import com.airtel.cs.pagerepository.pagemethods.RechargeHistoryWidget;
import com.airtel.cs.pagerepository.pagemethods.ServiceClassWidget;
import com.airtel.cs.pagerepository.pagemethods.UsageHistoryWidget;
import com.airtel.cs.pojo.AMProfilePOJO;
import com.airtel.cs.pojo.PlansPOJO;
import com.airtel.cs.pojo.PlansResultPOJO;
import com.airtel.cs.pojo.RechargeHistoryPOJO;
import com.airtel.cs.pojo.UsageHistoryPOJO;
import com.airtel.cs.pojo.airtelmoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.hlrservice.HLRServicePOJO;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

@Log4j2
public class WidgetTest extends Driver {

    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    APIEndPoints api = new APIEndPoints();

    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteractionAPI() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
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

    @DataProviders.Table(name = "Airtel Money")
    @Test(priority = 2, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void airtelMoneyTransactionWidgetTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating AM Transaction Widget of User :" + customerNumber, "description");
            final AMTransactionsWidget amTxnWidgetPage = pages.getAmTxnWidgetPage();
            assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyTransactionWidgetIsVisible(), true, "Airtel Money Transaction Widget is visible", "Airtel Money Transaction Widget is not visible"));
            assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyWidgetDatePickerVisible(), true, "Airtel Money Transaction Widget's Date Picker is visible", "Airtel Money Transaction Widget's Date Picker is not visible"));
            AirtelMoneyPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
            AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);
            if (amProfileAPI.getStatusCode() == 200) {
                assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.gettingAirtelMoneyBalance(), amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
                if (OPCO.equalsIgnoreCase("CD")) {
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.gettingAirtelMoneyBalance2(), amProfileAPI.getResult().getWallet().get(1).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
                }
            } else {
                commonLib.fail("API is Unable to Get AM Profile for Customer", true);
            }
            if (amTransactionHistoryAPI.getStatusCode() != 200) {
                assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyErrorVisible(), true, "API and Widget both are showing error message", "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", true);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
                assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isAirtelMoneyNoResultFoundVisible(), true, "No Result Found Icon displayed on UI", "No Result Found Icon does not display on UI"));
            } else {
                int count = amTransactionHistoryAPI.getResult().getTotalCount();
                if (count > 0) {
                    if (count > 5) {
                        count = 5;
                    }
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getHeaders(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                    assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isTransactionId(), true, "Transaction Id Search Box displayed on UI", "Transaction Id Search Box does not displayed on UI"));
                    for (int i = 0; i < count; i++) {
                        assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 1), amTransactionHistoryAPI.getResult().getData().get(i).getAmount(), "Amount is as expected as com.airtel.cs.API response", "Amount is not expected as com.airtel.cs.API response"));
                        assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 2), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), "Receiver MSISDN is as expected as com.airtel.cs.API response", "Receiver MSISDN is not expected as com.airtel.cs.API response"));
                        assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 3), UtilsMethods.getDateFromEpochInUTC(Long.parseLong(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), config.getProperty("AMHistoryTimeFormat")), "Date is as expected as com.airtel.cs.API response", "Date is not expected as com.airtel.cs.API response"));
                        assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), "Transaction Id is as expected as com.airtel.cs.API response", "Transaction Id is not expected as com.airtel.cs.API response"));
                        assertCheck.append(actions.assertEqual_stringType(amTxnWidgetPage.getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), "Status is as expected as com.airtel.cs.API response", "Status is not expected as com.airtel.cs.API response"));
                        if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                            assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isResendSMS(), true, "Resend SMS Icon is enabled as mentioned in com.airtel.cs.API Response", "Resend SMS Icon does not enable as mentioned in com.airtel.cs.API Response"));
                        }
                        String id = amTxnWidgetPage.doubleClickOnTransactionId(i + 1);
                        String clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        commonLib.info("Reading Clipboard copied text: " + clipboardText);
                        assertCheck.append(actions.assertEqual_stringType(id, clipboardText, "Transaction id copied to clipboard successfully", "Transaction id does not copy to clipboard"));
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - airtelMoneyTransactionWidgetTest" + e.fillInStackTrace(), true);
        }
    }

    //Needs Discussion
    @Test(priority = 3, description = "Validating Current Balance Widget", dependsOnMethods = "openCustomerInteractionAPI")
    public void yourCurrentBalanceWidgetTest() {
        try {
            selUtils.addTestcaseDescription("Validating Current Balance Transaction Widget of User :" + customerNumber, "description");
            final CurrentBalanceWidget currentBalanceWidgetPage = pages.getCurrentBalanceWidgetPage();
            assertCheck.append(actions.assertEqual_boolean(currentBalanceWidgetPage.isCurrentBalanceWidgetVisible(), true, "Current Balance Widget is visible", "Current Balance Widget is not visible"));

            PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
            final PlansResultPOJO result = plansAPI.getResult();
            if (result.getMainAccountBalance() != null) {
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingMainAccountBalance(), result.getMainAccountBalance().getBalance(), "Current Balance is as Received in com.airtel.cs.API", "Current Balance is not as Received in com.airtel.cs.API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingCurrentBalanceCurrency(), result.getMainAccountBalance().getCurrency(), "Current Balance Currency is as Received in com.airtel.cs.API", "Current Balance Currency is not as Received in com.airtel.cs.API"));
            } else {
                commonLib.warning("Unable to get Main Balance from com.airtel.cs.API");

            }
            if (result.getLastRecharge() != null) {
                try {
                    assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingLastRechargeAmount(), result.getLastRecharge().getAmount(), "Last Recharge is as Received in com.airtel.cs.API", "Last Recharge is not as Received in com.airtel.cs.API"));
                } catch (NumberFormatException e) {
                    commonLib.fail("Last Recharge is not in expected format", true);
                    commonLib.fail("Last Recharge is not in expected format", true);
                }
                String Time = UtilsMethods.getDateFromEpochInUTC(result.getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
                String Date = UtilsMethods.getDateFromEpochInUTC(result.getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is as Received in com.airtel.cs.API", "Last Recharge Date and Time is not as Received in com.airtel.cs.API"));
            } else {
                commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.gettingLastRechargeAmount().replace('-', ' ').trim(), "", "Last Recharge Amount is as expected", "Last Recharge Amount is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getLastRechargeDateTime(), "- -", "Last Recharge Date & Time is as expected", "Last Recharge Date & Time is not as expected"));
            }

            log.info(result.toString());
            if (result.getVoice() != null) {
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getVoiceExpiryDate(), UtilsMethods.getDateFromEpoch(result.getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is as Received in com.airtel.cs.API", "Voice Expiry Date is not as Received in com.airtel.cs.API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getVoiceBalance().replace("-", "null"), result.getVoice().getBalance(), "Voice Balance is as Received in com.airtel.cs.API", "Voice Balance is not as Received in com.airtel.cs.API"));
            }
            if (result.getData() != null) {
                try {
                    double amount = Double.parseDouble(result.getData().getBalance().split(" ")[0]);
                    if (amount > 0) {
                        String unit = result.getData().getBalance().split(" ")[1];
                        if (unit.equalsIgnoreCase("MB") && amount > 1024) {
                            commonLib.fail("MB to GB conversion does not done Correctly. Data Balance" + currentBalanceWidgetPage.getDataBalance(), true);
                        } else {
                            commonLib.pass("MB to GB Conversion Verified. Balance " + currentBalanceWidgetPage.getDataBalance());
                        }
                    }
                } catch (NumberFormatException ns) {
                    commonLib.info("Not able to fetch amount" + ns.fillInStackTrace());
                }
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getDataBalance().replace("-", "null"), result.getData().getBalance(), "Data Balance is as Received in com.airtel.cs.API", "Data Balance is not as Received in com.airtel.cs.API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getDataExpiryDate(), UtilsMethods.getDateFromEpoch(result.getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is as Received in com.airtel.cs.API", "Data Expiry Date is not as Received in com.airtel.cs.API"));
            }
            if (result.getSms() != null) {

                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getSmsExpiryDate(), UtilsMethods.getDateFromEpoch(result.getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is as Received in com.airtel.cs.API", "SMS Expiry Date is not as Received in com.airtel.cs.API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getSmsBalance().replace("-", "null"), result.getSms().getBalance(), "SMS Balance is as Received in com.airtel.cs.API", "SMS Balance is not as Received in com.airtel.cs.API"));
            }
            if (plansAPI.getStatusCode() != 200) {
                commonLib.fail("com.airtel.cs.API unable to get Last recharge and MAIN Balance ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - yourCurrentBalanceWidgetTest" + e.fillInStackTrace(), true);
        }
    }


    @DataProviders.Table(name = "Usage History")
    @Test(priority = 4, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void usageHistoryWidgetTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Usage History Widget of User :" + customerNumber, "description");
            final UsageHistoryWidget usageHistoryWidget = pages.getUsageHistoryWidget();
            assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryDatePickerVisible(), true, "Usage History Widget's Date Picker is visible", "Usage History Widget's Date Picker is not visible"));
            UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
            int size = usageHistoryWidget.getNumberOfRows();
            if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
                assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is As received in com.airtel.cs.API for row number " + i, "Usage History Type is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryCharge(i).replaceAll("[^0-9]", "").trim(), usageHistoryAPI.getResult().get(i).getCharges().replaceAll("[^0-9]", ""), "Usage History Charge is As received in com.airtel.cs.API for row number " + i, "Usage History Charge is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryDateTime(i), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Usage History Date Time is As received in com.airtel.cs.API for row number " + i, "Usage History Date Time is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is As received in com.airtel.cs.API for row number " + i, "Usage History Start Balance  is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is As received in com.airtel.cs.API for row number " + i, "Usage History End Balance is not As received in com.airtel.cs.API for row number " + i));
                    if (i != 0) {
                        assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isSortOrderDisplay(usageHistoryWidget.getHistoryDateTime(i).replace("\n", " "), usageHistoryWidget.getHistoryDateTime(i - 1).replace("\n", " "), "EEE dd MMM yyy hh:mm:ss aa"), true, usageHistoryWidget.getHistoryDateTime(i - 1) + "displayed before " + usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getHistoryDateTime(i - 1) + "should not display before " + usageHistoryWidget.getHistoryDateTime(i)));
                    }
                }
            }
            if (usageHistoryAPI.getStatusCode() != 200) {
                assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryErrorVisible(), true, "API and widget both are giving error message", "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is "));
                commonLib.fail("com.airtel.cs.API is unable to give Usage History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryWidgetTest" + e.fillInStackTrace(), true);
        }
    }


    @DataProviders.Table(name = "Recharge History")
    @Test(priority = 5, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void rechargeHistoryWidgetTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Recharge History Widget of User :" + customerNumber, "description");
            final RechargeHistoryWidget rechargeHistoryWidget = pages.getRechargeHistoryWidget();
            assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), true, "Recharge History Widget is visible", "Recharge History Widget is not visible"));
            assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), true, "Recharge History Widget's Date Picker is visible", "Recharge History Widget's Date Picker is not visible"));
            RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
            if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
                assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), true, "com.airtel.cs.API and widget both are Giving error", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("com.airtel.cs.API is unable to give Recharge History ", true);
            } else {
                int size = rechargeHistoryWidget.getNumberOfRows();
                if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                    commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
                    assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim() + " " + rechargeHistoryWidget.getSubHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim() + rechargeHistoryWidget.getSubHeaders(4).toLowerCase().trim().replace("|", ""), data.getRow4().toLowerCase().replace("|", "").trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryCharges(i + 1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is As received in com.airtel.cs.API for row number " + i, "Recharge History Charge is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), config.getProperty("UIRechargeHistoryTimeFormat"), config.getProperty("APIRechargeHistoryTimeFormat")), "Recharge History Date Time is As received in com.airtel.cs.API for row number " + i, "Recharge History Date Time is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryBundleName(i + 1), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is As received in com.airtel.cs.API for row number " + i, "Recharge History Bundle Name is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryBenefits(i + 1).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is As received in com.airtel.cs.API for row number " + i, "Recharge History Benefits is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryStatus(i + 1), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is As received in com.airtel.cs.API for row number " + i, "Recharge History Status is not As received in com.airtel.cs.API for row number " + i));
                        if (i != 0) {
                            assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isSortOrderDisplay(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), rechargeHistoryWidget.getRechargeHistoryDateTime(i), "dd-MMM-yyy HH:mm"), true, rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "displayed before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "should not display before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i)));
                        }
                    }

                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryWidgetTest" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "Service Profile")
    @Test(priority = 6, description = "Verify Service Profile Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void validateServiceProfileWidget(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify Service Profile Widget: " + customerNumber, "description");
            commonLib.info("Opening URL");
            final ServiceClassWidget serviceClassWidget = pages.getServiceClassWidget();
            assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.isServiceClassWidgetDisplay(), true, "Service Profile Widget displayed correctly", "Service Profile Widget does not display correctly"));
            HLRServicePOJO hlrService = api.getServiceProfileWidgetInfo(customerNumber);
            int size = serviceClassWidget.getNumberOfRows();
            if (Integer.parseInt(hlrService.getStatusCode()) == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from com.airtel.cs.API");
                    assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header Name at Row(1) is as expected", "Header Name at Row(1) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header Name at Row(2) is as expected", "Header Name at Row(2) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header Name at Row(3) is as expected", "Header Name at Row(3) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(4).trim().toLowerCase(), data.getRow4().trim().toLowerCase(), "Header Name at Row(4) is as expected", "Header Name at Row(4) is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getHeaders(5).trim().toLowerCase(), data.getRow5().trim().toLowerCase(), "Header Name at Row(5) is as expected", "Header Name at Row(5) is not as expected"));
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 1), hlrService.getResult().get(i).getServiceName(), "Service Name is As received in API for row number " + i, "Service Name is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 2), hlrService.getResult().get(i).getServiceDesc(), "Service desc is As received in API for row number " + i, "Service desc is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 3), hlrService.getResult().get(i).getHlrCodes().get(0), "HLR Code is As received in API for row number " + i, "HLR Code is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(serviceClassWidget.getValueCorrespondingToAccumulator(i + 1, 4), hlrService.getResult().get(i).getHlrCodeDetails().get(0), "HLR code details is As received in API for row number " + i, "HLR code details is not As received in API for row number " + i));
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.getServiceStatus(), true, "Service Status is as expected", "Service Status is not as expected"));
                            } else {
                                assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.getServiceStatus(), false, "Service Status is as expected", "Service Status is not as expected"));
                            }
                        }
                    }
                }

            } else {
                assertCheck.append(actions.assertEqual_boolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "com.airtel.cs.API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("com.airtel.cs.API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateServiceProfileWidget" + e.fillInStackTrace(), true);
        }
    }
}
