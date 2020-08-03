package tests;

import API.APITest;
import POJO.*;
import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;

public class customerInteractionTest extends BaseTest {
    @DataProvider.User(UserType = "ALL")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class, enabled = false)
    public void validateDemographicInformation(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getCustomerName().trim(), Data.getCustomerName(), "Customer Name is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer Name is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getCustomerDOB().trim(), Data.getCustomerDOB(), "Customer DOB is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer DOB is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getActivationDate().trim(), Data.getActivationDate(), "Customer's Activation Date is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Activation Date is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getActivationTime().trim(), Data.getActivationTime().trim(), "Customer's Activation Time is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Activation Time is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getSimNumber().trim(), Data.getSimNumber(), "Customer's SIM Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's SIM Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getSimType().trim(), Data.getSimType(), "Customer's SIM Type is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's SIM Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getPUK1().trim(), Data.getPUK1(), "Customer's PUK1 Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's PUK1 Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getPUK2().trim(), Data.getPUK2(), "Customer's PUK2 Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's  PUK2 Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getIdType().trim(), Data.getIdType(), "Customer's ID Type is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Id Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getIdNumber().trim(), Data.getIdNumber(), "Customer's ID Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Id Number is not visible", e.getCause());
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 3, description = "Validating AM Transaction Widget", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void airtelMoneyTransactionWidgetTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating AM Transaction Widget", "Validating AM Transaction Widget of User :" + Data.getCustomerNumber());
        AMTransactionsWidgetPOM amTransactionsWidget = new AMTransactionsWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Transaction Widget is not visible");
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyWidgetDatePickerVisible(), "Airtel Money Transaction Widget's Date Picker is not visible");
        APITest api = new APITest();
        AMProfilePOJO amServiceProfileAPI = api.amServiceProfileAPITest(Data.getCustomerNumber());
        softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyBalance(), Integer.parseInt(amServiceProfileAPI.getResult().getWallet().get(0).getBalance())
                , "Customer's Airtel Money Balance is not as Expected");
        softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyCurrency(), amServiceProfileAPI.getResult().getWallet().get(0).getCurrency(),
                "Customer's Airtel Money Balance Currency  is not as Expected");
        softAssert.assertAll();
    }


    @DataProvider.User(UserType = "ALL")
    //Needs Discussion
    @Test(priority = 4, description = "Validating Current Balance Widget", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void yourCurrentBalanceWidgetTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating Current Balance Transaction Widget", "Validating Current Balance Transaction Widget of User :" + Data.getCustomerNumber());
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");
        APITest api = new APITest();
        PlansPOJO plansAPI = api.accountPlansTest(Data.getCustomerNumber());
        softAssert.assertEquals(Double.parseDouble(currentBalanceWidget.gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()), "Current Balance is not as Received in API ");
        softAssert.assertEquals(currentBalanceWidget.gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency(), "Current Balance Currency is not as Received in API ");
        softAssert.assertEquals(Integer.parseInt(currentBalanceWidget.gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()), "Last Recharge is not as Received in API ");
        String Time = currentBalanceWidget.getTimeFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
        String Date = currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
        softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is not as Received in API");
        System.out.println(plansAPI.getResult().toString());
        if (plansAPI.getResult().getVoice() != null) {
            softAssert.assertEquals(currentBalanceWidget.getVoiceExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.getVoiceBalance().replace("-", "null"), plansAPI.getResult().getVoice().getBalance(), "Voice Balance is not as Received in API ");
        }
        if (plansAPI.getResult().getData() != null) {

            softAssert.assertEquals(currentBalanceWidget.getDataBalance().replace("-", "null"), plansAPI.getResult().getData().getBalance(), "Data Balance is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.getDataExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is not as Received in API ");
        }
        if (plansAPI.getResult().getSms() != null) {

            softAssert.assertEquals(currentBalanceWidget.getSmsExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.getSmsBalance().replace("-", "null"), plansAPI.getResult().getSms().getBalance(), "SMS Balance is not as Received in API ");
        }
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 5, description = "Validating Usage History Widget", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void usageHistoryWidgetTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating Usage History Widget", "Validating Usage History Widget of User :" + Data.getCustomerNumber());
        UsageHistoryWidgetPOM usageHistoryWidget = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible");
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryDatePickerVisible(), "Usage History Widget's Date Picker is not visible");
        APITest api = new APITest();
        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(Data.getCustomerNumber());
        int size = usageHistoryWidget.getNumberOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is not As received in API for row number " +i);
            softAssert.assertEquals(usageHistoryWidget.getHistoryCharge(i), usageHistoryAPI.getResult().get(i).getCharges(), "Usage History Charge is not As received in API for row number " +i);
            softAssert.assertEquals(usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getDateFromEpoch(Long.parseLong(usageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyy HH:mm"), "Usage History Date Time is not As received in API for row number " +i);
            softAssert.assertEquals(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is not As received in API for row number " +i);
            softAssert.assertEquals(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is not As received in API for row number " +i);
        }
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 6, description = "Validating Recharge History Widget", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void rechargeHistoryWidgetTest(TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating Recharge History Widget", "Validating Recharge History Widget of User :" + Data.getCustomerNumber());
        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        APITest api = new APITest();
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(Data.getCustomerNumber());
        int size = rechargeHistoryWidget.getNumberOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryCharges(i), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is not As received in API for row number " +i);
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getDateFromEpoch(Long.parseLong(rechargeHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm"), "Recharge History Date Time is not As received in API for row number " +i);
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBundleName(i), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is not As received in API for row number " +i);
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBenefits(i).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is not As received in API for row number " +i);
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryStatus(i), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is not As received in API for row number " +i);
        }
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 7, description = "Validating DA Details", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void daDetailsTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating DA Details", "Validating DA Details of User :" + Data.getCustomerNumber());
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
        currentBalanceWidget.clickingCurrentBalanceWidgetMenu();
        softAssert.assertTrue(currentBalanceWidget.isDADetailsMenuVisible(), "DA Details Option in  MENU is not visible ");
        DADetailsPOM daDetails = currentBalanceWidget.openingDADetails();
        daDetails.waitTillLoaderGetsRemoved();

        APITest api = new APITest();
        AccountsBalancePOJO accountsBalanceAPI = api.balanceAPITest(Data.getCustomerNumber());
        int size = daDetails.getNumbersOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(daDetails.getDAId(i), accountsBalanceAPI.getResult().get(i).getDaId(), "DA ID is not as received in API on row " +i);
            softAssert.assertEquals(daDetails.getDADescription(i), accountsBalanceAPI.getResult().get(i).getDaDesc(), "DA Description is not as received in API on row " +i);
            softAssert.assertEquals(daDetails.getBundleType(i).replace("-", "null") + " ", accountsBalanceAPI.getResult().get(i).getBundleType() + " ", "DA Bundle Type is not as received in API on row " +i);
            softAssert.assertEquals(daDetails.getDADateTime(i), daDetails.getTimeFromEpoch(accountsBalanceAPI.getResult().get(i).getExpiryDate(), "dd-MMM-yyyy HH:mm"), "DA Date Time is not as received in API on row " +i);
            softAssert.assertEquals(daDetails.getDABalance(i), accountsBalanceAPI.getResult().get(i).getCurrentDaBalance(), "DA Current Balance is not as received in API on row " +i);
        }
        daDetails.openingCustomerInteractionDashboard();
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 8, description = "Validating Usage History's more Menu", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void usageHistoryMenuTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating Usage History's more Menu", "Validating Usage History's more Menu of User :" + Data.getCustomerNumber());
        UsageHistoryWidgetPOM usageHistory = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistory.isUsageHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
        usageHistory.clickingUsageHistoryWidgetMenu();
        softAssert.assertTrue(usageHistory.isMoreMenuVisible(), "More Option in  MENU is not visible ");
        MoreUsageHistoryPOM moreUsageHistory = usageHistory.openingMoreDetails();
        moreUsageHistory.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(moreUsageHistory.isSMSDatePickerVisible(), "SMS History Date picker is not visible ");
        softAssert.assertTrue(moreUsageHistory.isDataDatePickerVisible(), "Data History Date picker is not visible ");
        softAssert.assertTrue(moreUsageHistory.isCallDatePickerVisible(), "Call History Date picker is not visible ");

        APITest api = new APITest();
        UsageHistoryPOJO smsUsageHistoryAPI = api.usageHistoryTest(Data.getCustomerNumber(), "SMS_HISTORY");
        int size = moreUsageHistory.getNumbersOfSMSRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(moreUsageHistory.getSmsBundleName(i), smsUsageHistoryAPI.getResult().get(i).getBundleName(), "SMS Bundle received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getSMSCharges(i), smsUsageHistoryAPI.getResult().get(i).getCharges(), "SMS Charges received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getSMSDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(smsUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "SMS Date & Time received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getSMSTo(i), smsUsageHistoryAPI.getResult().get(i).getSmsTo(), "SMS To received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getSMSTransactionNumber(i), smsUsageHistoryAPI.getResult().get(i).getTxnNumber(), "SMS Transaction Number received is not as expected on row " +i);
        }
        UsageHistoryPOJO callUsageHistoryAPI = api.usageHistoryTest(Data.getCustomerNumber(), "CALL_HISTORY");
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(moreUsageHistory.getCallBundleName(i), callUsageHistoryAPI.getResult().get(i).getBundleName(), "Call Bundle received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getCallCharges(i), callUsageHistoryAPI.getResult().get(i).getCharges(), "Call Charges received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getCallDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(callUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "Call Date & Time received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getCallTo(i), callUsageHistoryAPI.getResult().get(i).getCallTo(), "Call To received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getCallTransactionNumber(i), callUsageHistoryAPI.getResult().get(i).getTxnNumber(), "Call Transaction Number received is not as expected on row " +i);
            softAssert.assertEquals(moreUsageHistory.getCallDuration(i), callUsageHistoryAPI.getResult().get(i).getCallDuration(), "Call Duration  received is not as expected on row " +i);
        }
        moreUsageHistory.openingCustomerInteractionDashboard();
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 9, description = "Validating Recharge History's  Menu", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void rechargeHistoryMenuTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating Recharge History's  Menu", "Validating Recharge History's  Menu of User :" + Data.getCustomerNumber());
        RechargeHistoryWidgetPOM rechargeHistory = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistory.isRechargeHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
        rechargeHistory.clickingRechargeHistoryWidgetMenu();
        softAssert.assertTrue(rechargeHistory.isRechargeHistoryMenuVisible(), "More Option in  MENU is not visible ");
        MoreRechargeHistoryPOM moreRechargeHistory = rechargeHistory.openingRechargeHistoryDetails();
        moreRechargeHistory.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(moreRechargeHistory.isDatePickerVisible(), "Date picker is not visible ");

        APITest api = new APITest();
        BundleRechargeHistoryPOJO bundleRechargeHistoryAPI = api.bundleRechargeHistoryAPITest(Data.getCustomerNumber());
        int size = moreRechargeHistory.getNumbersOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(moreRechargeHistory.getBundleName(i), bundleRechargeHistoryAPI.getResult().get(i).getBundleName(), " Bundle Name received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getPackageCategory(i), bundleRechargeHistoryAPI.getResult().get(i).getPackageCategory(), "Package Category received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getTransactionNumber(i), bundleRechargeHistoryAPI.getResult().get(i).getTxnNumber(), "Bundle Transaction number received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getStatus(i).toLowerCase(), bundleRechargeHistoryAPI.getResult().get(i).getStatus().toLowerCase(), "Bundle Status received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getSubscriptionDateTime(i), (moreRechargeHistory.getDateFromEpoch(Long.parseLong(bundleRechargeHistoryAPI.getResult().get(i).getSubscriptionDateTime()), "dd-MMM-yyyy HH:mm")), "Bundle Subscription Date & Time received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getExpiresOn(i), (moreRechargeHistory.getDateFromEpoch(Long.parseLong(bundleRechargeHistoryAPI.getResult().get(i).getExpiresOn()), "dd-MMM-yyyy HH:mm")), "Bundle Expiry Date & Time received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getValidity(i), bundleRechargeHistoryAPI.getResult().get(i).getValidity(), "Bundle Validity received is not as expected on row " +i);
            softAssert.assertEquals(moreRechargeHistory.getBundlePrice(i), bundleRechargeHistoryAPI.getResult().get(i).getBundlePrice(), "Bundle Price received is not as expected on row " +i);
        }
        moreRechargeHistory.openingCustomerInteractionDashboard();
        softAssert.assertAll();
    }

}
