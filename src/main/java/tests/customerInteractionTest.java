package tests;

import API.APITest;
import POJO.GsmKycPOJO;
import POJO.ProfilePOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;

import static Utils.DataProviders.DataProviders.User;

public class customerInteractionTest extends BaseTest {

    String customerNumber;

    @User(UserType = "ALL")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerNumber = Data.getCustomerNumber();
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @User(UserType = "ALL")
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class, enabled = true)
    public void validateDemographicInformation(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        APITest api = new APITest();
        ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
        GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);


        try {
            softAssert.assertEquals(customerInteractionPagePOM.getCustomerName().trim(), gsmKycAPI.getResult().getName(), "Customer Name is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer Name is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getCustomerDOB().trim(), customerInteractionPagePOM.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer DOB is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getActivationDate().trim(), customerInteractionPagePOM.getDateFromEpoch(Long.parseLong(profileAPI.getResult().getActivationDate()), "dd MMM yyyy"), "Customer's Activation Date is not as Expected");

        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Activation Date is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getActivationTime().trim(), customerInteractionPagePOM.getDateFromEpoch(Long.parseLong(profileAPI.getResult().getActivationDate()), "hh: mm aa"), "Customer's Activation Time is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Activation Time is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getSimNumber().trim(), profileAPI.getResult().getSim(), "Customer's SIM Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's SIM Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getSimType().trim(), profileAPI.getResult().getSimType(), "Customer's SIM Type is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's SIM Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getPUK1().trim(), profileAPI.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's PUK1 Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getPUK2().trim(), profileAPI.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's  PUK2 Number is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertEquals(customerInteractionPagePOM.getIdType().trim(), gsmKycAPI.getResult().getIdentificationType(), "Customer's ID Type is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Id Type is not visible", e.getCause());
            e.printStackTrace();
        }
        try {
            softAssert.assertTrue(gsmKycAPI.getResult().getIdentificationNo().contains(customerInteractionPagePOM.getIdNumber().replace("*", "")), "Customer's ID Number is not as Expected");
        } catch (NoSuchElementException e) {
            softAssert.fail("Customer's Id Number is not visible", e.getCause());
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

//    @Table(Name = "Airtel Money")
//    @Test(priority = 3, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void airtelMoneyTransactionWidgetTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating AM Transaction Widget", "Validating AM Transaction Widget of User :" + customerNumber);
//        AMTransactionsWidgetPOM amTransactionsWidget = new AMTransactionsWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Transaction Widget is not visible");
//        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyWidgetDatePickerVisible(), "Airtel Money Transaction Widget's Date Picker is not visible");
//        softAssert.assertEquals(amTransactionsWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
//        softAssert.assertEquals(amTransactionsWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
//        softAssert.assertEquals(amTransactionsWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
//        softAssert.assertEquals(amTransactionsWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
//        softAssert.assertEquals(amTransactionsWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
//        APITest api = new APITest();
//        AMProfilePOJO amServiceProfileAPI = api.amServiceProfileAPITest(customerNumber);
//        AMTransactionHistoryPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
//        if (amServiceProfileAPI.getResult() != null) {
//            softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyBalance(), Integer.parseInt(amServiceProfileAPI.getResult().getWallet().get(0).getBalance())
//                    , "Customer's Airtel Money Balance is not as Expected");
//            softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyCurrency(), amServiceProfileAPI.getResult().getWallet().get(0).getCurrency(),
//                    "Customer's Airtel Money Balance Currency  is not as Expected");
//        } else {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get AM Balance from API");
//            softAssert.assertTrue(amTransactionsWidget.isAMBalanceUnableToFetch(), "No error Message on Unable to get AM Balance For User From API");
//            softAssert.assertEquals(amTransactionsWidget.gettingAMBalanceUnableToFetchMessage(), "Unable to Fetch Data", "Error Message is not as Expected");
//        }
//        if (amTransactionHistoryAPI.getStatusCode() != 200) {
//            softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyErrorVisible(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage());
//        }
//        softAssert.assertAll();
//    }
//
//    //Needs Discussion
//    @Test(priority = 4, description = "Validating Current Balance Widget")
//    public void yourCurrentBalanceWidgetTest() {
//        ExtentTestManager.startTest("Validating Current Balance Transaction Widget", "Validating Current Balance Transaction Widget of User :" + customerNumber);
//        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");
//        APITest api = new APITest();
//        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
//        if (plansAPI.getResult().getMainAccountBalance() != null) {
//            softAssert.assertEquals(Double.parseDouble(currentBalanceWidget.gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()), "Current Balance is not as Received in API ");
//            softAssert.assertEquals(currentBalanceWidget.gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency(), "Current Balance Currency is not as Received in API ");
//        } else {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Main Balance from API");
//
//        }
//        if (plansAPI.getResult().getLastRecharge() != null) {
//            softAssert.assertEquals(Integer.parseInt(currentBalanceWidget.gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()), "Last Recharge is not as Received in API ");
//            String Time = currentBalanceWidget.getTimeFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
//            String Date = currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
//            softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is not as Received in API");
//        } else {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
//            softAssert.assertTrue(currentBalanceWidget.isLastRechargeUnableToFetchVisible(), "Error Message is not Visible");
//            softAssert.assertTrue(currentBalanceWidget.isLastRechargeDateTImeUnableTOFetch(), "Error Message is not Visible");
//            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeUnableToFetchVisible(), "Unable to Fetch Data", "Error Message is not as Expected");
//            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeDateTImeUnableTOFetch(), "Unable to Fetch Data", "Error Message is not as Expected");
//
//        }
//
//        System.out.println(plansAPI.getResult().toString());
//        if (plansAPI.getResult().getVoice() != null) {
//            softAssert.assertEquals(currentBalanceWidget.getVoiceExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Voice Expiry Date is not as Received in API ");
//            softAssert.assertEquals(currentBalanceWidget.getVoiceBalance().replace("-", "null"), plansAPI.getResult().getVoice().getBalance(), "Voice Balance is not as Received in API ");
//        }
//        if (plansAPI.getResult().getData() != null) {
//
//            softAssert.assertEquals(currentBalanceWidget.getDataBalance().replace("-", "null"), plansAPI.getResult().getData().getBalance(), "Data Balance is not as Received in API ");
//            softAssert.assertEquals(currentBalanceWidget.getDataExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "Data Expiry Date is not as Received in API ");
//        }
//        if (plansAPI.getResult().getSms() != null) {
//
//            softAssert.assertEquals(currentBalanceWidget.getSmsExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")), "SMS Expiry Date is not as Received in API ");
//            softAssert.assertEquals(currentBalanceWidget.getSmsBalance().replace("-", "null"), plansAPI.getResult().getSms().getBalance(), "SMS Balance is not as Received in API ");
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Usage History")
//    @Test(priority = 5, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryWidgetTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History Widget", "Validating Usage History Widget of User :" + customerNumber);
//        UsageHistoryWidgetPOM usageHistoryWidget = new UsageHistoryWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible");
//        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryDatePickerVisible(), "Usage History Widget's Date Picker is not visible");
//        softAssert.assertEquals(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
//        softAssert.assertEquals(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
//        softAssert.assertEquals(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
//        softAssert.assertEquals(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
//        softAssert.assertEquals(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
//        APITest api = new APITest();
//        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
//        int size = usageHistoryWidget.getNumberOfRows();
//        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
//            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), "Error Message is not Visible");
//            softAssert.assertEquals(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
//        } else {
//            for (int i = 0; i < size; i++) {
//                softAssert.assertEquals(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is not As received in API for row number " + i);
//                softAssert.assertEquals(usageHistoryWidget.getHistoryCharge(i), usageHistoryAPI.getResult().get(i).getCharges(), "Usage History Charge is not As received in API for row number " + i);
//                softAssert.assertEquals(usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getDateFromEpoch(Long.parseLong(usageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyy HH:mm"), "Usage History Date Time is not As received in API for row number " + i);
//                softAssert.assertEquals(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is not As received in API for row number " + i);
//                softAssert.assertEquals(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is not As received in API for row number " + i);
//            }
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Recharge History")
//    @Test(priority = 6, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void rechargeHistoryWidgetTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Recharge History Widget", "Validating Recharge History Widget of User :" + customerNumber);
//        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
//        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
//        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
//        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
//        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
//        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
//        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
//        APITest api = new APITest();
//        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
//        int size = rechargeHistoryWidget.getNumberOfRows();
//        if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
//            softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), "Error Message is not Visible");
//            softAssert.assertEquals(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
//        } else {
//            for (int i = 0; i < size; i++) {
//                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryCharges(i), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is not As received in API for row number " + i);
//                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getDateFromEpoch(Long.parseLong(rechargeHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm"), "Recharge History Date Time is not As received in API for row number " + i);
//                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBundleName(i), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is not As received in API for row number " + i);
//                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBenefits(i).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is not As received in API for row number " + i);
//                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryStatus(i), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is not As received in API for row number " + i);
//            }
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Da Details")
//    @Test(priority = 7, description = "Validating DA Details", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void daDetailsTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating DA Details", "Validating DA Details of User :" + customerNumber);
//        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
//        currentBalanceWidget.clickingCurrentBalanceWidgetMenu();
//        softAssert.assertTrue(currentBalanceWidget.isDADetailsMenuVisible(), "DA Details Option in  MENU is not visible ");
//        DADetailsPOM daDetails = currentBalanceWidget.openingDADetails();
//        softAssert.assertEquals(daDetails.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
//        softAssert.assertEquals(daDetails.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
//        softAssert.assertEquals(daDetails.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
//        softAssert.assertEquals(daDetails.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
//        softAssert.assertEquals(daDetails.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
//
//        APITest api = new APITest();
//        AccountsBalancePOJO accountsBalanceAPI = api.balanceAPITest(customerNumber);
//        int size = daDetails.getNumbersOfRows();
//        for (int i = 0; i < size; i++) {
//            softAssert.assertEquals(daDetails.getDAId(i), accountsBalanceAPI.getResult().get(i).getDaId(), "DA ID is not as received in API on row " + i);
//            softAssert.assertEquals(daDetails.getDADescription(i), accountsBalanceAPI.getResult().get(i).getDaDesc(), "DA Description is not as received in API on row " + i);
//            softAssert.assertEquals(daDetails.getBundleType(i).replace("-", "null") + " ", accountsBalanceAPI.getResult().get(i).getBundleType() + " ", "DA Bundle Type is not as received in API on row " + i);
//            softAssert.assertEquals(daDetails.getDADateTime(i), daDetails.getTimeFromEpoch(accountsBalanceAPI.getResult().get(i).getExpiryDate(), "dd-MMM-yyyy HH:mm"), "DA Date Time is not as received in API on row " + i);
//            softAssert.assertEquals(daDetails.getDABalance(i), accountsBalanceAPI.getResult().get(i).getCurrentDaBalance(), "DA Current Balance is not as received in API on row " + i);
//        }
//        daDetails.openingCustomerInteractionDashboard();
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "SMS History")
//    @Test(priority = 8, description = "Validating Usage History's more Menu and SMS History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryMenuTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History's more Menu", "Validating Usage History's more Menu of User :" + customerNumber);
//        UsageHistoryWidgetPOM usageHistory = new UsageHistoryWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(usageHistory.isUsageHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
//        usageHistory.clickingUsageHistoryWidgetMenu();
//        softAssert.assertTrue(usageHistory.isMoreMenuVisible(), "More Option in  MENU is not visible ");
//        MoreUsageHistoryPOM moreUsageHistory = usageHistory.openingMoreDetails();
//        softAssert.assertTrue(moreUsageHistory.isSMSDatePickerVisible(), "SMS History Date picker is not visible ");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for SMS Row 1 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for SMS Row 2 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for SMS Row 3 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for SMS Row 4 is not as expected");
//        APITest api = new APITest();
//        UsageHistoryPOJO smsUsageHistoryAPI = api.usageHistoryTest(customerNumber, "SMS_HISTORY");
//        int smsSize = moreUsageHistory.getNumbersOfSMSRows();
//
//        if (smsUsageHistoryAPI.getResult().size() == 0 || smsUsageHistoryAPI.getResult() == null) {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get SMS History Details from API");
//            softAssert.assertTrue(moreUsageHistory.isSMSHistoryNoResultFoundVisible(), "Error Message is not Visible");
//            softAssert.assertEquals(moreUsageHistory.gettingSMSHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
//        } else {
//            for (int i = 0; i < smsSize; i++) {
//                softAssert.assertEquals(moreUsageHistory.getSmsBundleName(i), smsUsageHistoryAPI.getResult().get(i).getBundleName(), "SMS Bundle received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getSMSCharges(i), smsUsageHistoryAPI.getResult().get(i).getCharges(), "SMS Charges received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getSMSDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(smsUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "SMS Date & Time received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getSMSTo(i), smsUsageHistoryAPI.getResult().get(i).getSmsTo(), "SMS To received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getSMSTransactionNumber(i), smsUsageHistoryAPI.getResult().get(i).getTxnNumber(), "SMS Transaction Number received is not as expected on row " + i);
//            }
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Call History")
//    @Test(priority = 9, description = "Validating Usage History's Call History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryCallMenuTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History's Call History", "Validating Usage History's Call History of User :" + customerNumber);
//        MoreUsageHistoryPOM moreUsageHistory = new MoreUsageHistoryPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        APITest api = new APITest();
//        softAssert.assertTrue(moreUsageHistory.isCallDatePickerVisible(), "Call History Date picker is not visible ");
//        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Call Row 1 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Call Row 2 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Call Row 3 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Call Row 4 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Call Row 5 is not as expected");
//        int callSize = moreUsageHistory.getNumbersOfCallRows();
//        UsageHistoryPOJO callUsageHistoryAPI = api.usageHistoryTest(customerNumber, "CALL_HISTORY");
//        if (callUsageHistoryAPI.getResult().size() == 0 || callUsageHistoryAPI.getResult() == null) {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get CALL History Details from API");
//            softAssert.assertTrue(moreUsageHistory.isCallHistoryNoResultFoundVisible(), "Error Message is not Visible");
//            softAssert.assertEquals(moreUsageHistory.gettingCallHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
//        } else {
//            for (int i = 0; i < callSize; i++) {
//                softAssert.assertEquals(moreUsageHistory.getCallBundleName(i), callUsageHistoryAPI.getResult().get(i).getBundleName(), "Call Bundle received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallCharges(i), callUsageHistoryAPI.getResult().get(i).getCharges(), "Call Charges received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(callUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "Call Date & Time received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallTo(i), callUsageHistoryAPI.getResult().get(i).getCallTo(), "Call To received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallTransactionNumber(i), callUsageHistoryAPI.getResult().get(i).getTxnNumber(), "Call Transaction Number received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallDuration(i), callUsageHistoryAPI.getResult().get(i).getCallDuration(), "Call Duration  received is not as expected on row " + i);
//            }
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Data History")
//    @Test(priority = 10, description = "Validating Usage History's Data History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryDataMenuTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History's Data History", "Validating Usage History's Data History of User :" + customerNumber);
//        MoreUsageHistoryPOM moreUsageHistory = new MoreUsageHistoryPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        APITest api = new APITest();
//        int dataSize = moreUsageHistory.getNumbersOfDataRows();
//        softAssert.assertTrue(moreUsageHistory.isDataDatePickerVisible(), "Data History Date picker is not visible ");
//        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Data Row 1 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Data Row 2 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Data Row 3 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Data Row 4 is not as expected");
//        UsageHistoryPOJO dataUsageHistoryAPI = api.usageHistoryTest(customerNumber, "DATA_HISTORY");
//        if (dataUsageHistoryAPI.getResult().size() == 0 || dataUsageHistoryAPI.getResult() == null) {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get DATA History Details from API");
//            softAssert.assertTrue(moreUsageHistory.isDataHistoryNoResultFoundVisible(), "Error Message is not Visible");
//            softAssert.assertEquals(moreUsageHistory.gettingDataHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
//        } else {
//        }
//        moreUsageHistory.openingCustomerInteractionDashboard();
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Bundle Subscription History")
//    @Test(priority = 11, description = "Validating Recharge History's  Menu", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void rechargeHistoryMenuTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Recharge History's  Menu", "Validating Recharge History's  Menu of User :" + customerNumber);
//        RechargeHistoryWidgetPOM rechargeHistory = new RechargeHistoryWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(rechargeHistory.isRechargeHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
//        rechargeHistory.clickingRechargeHistoryWidgetMenu();
//        softAssert.assertTrue(rechargeHistory.isRechargeHistoryMenuVisible(), "More Option in  MENU is not visible ");
//        MoreRechargeHistoryPOM moreRechargeHistory = rechargeHistory.openingRechargeHistoryDetails();
//        softAssert.assertTrue(moreRechargeHistory.isDatePickerVisible(), "Date picker is not visible ");
//
//        APITest api = new APITest();
//        BundleRechargeHistoryPOJO bundleRechargeHistoryAPI = api.bundleRechargeHistoryAPITest(customerNumber);
//        int size = moreRechargeHistory.getNumbersOfRows();
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(6).toLowerCase().trim(), Data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(7).toLowerCase().trim(), Data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
//        softAssert.assertEquals(moreRechargeHistory.getHeaders(8).toLowerCase().trim(), Data.getRow8().toLowerCase().trim(), "Header Name for Row 8 is not as expected");
//        if (bundleRechargeHistoryAPI.getResult().size() == 0 || bundleRechargeHistoryAPI.getResult() == null) {
//            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get DATA History Details from API");
//            softAssert.assertTrue(moreRechargeHistory.isBundleHistoryNoResultFoundVisible(), "Error Message is not Visible");
//            softAssert.assertEquals(moreRechargeHistory.gettingBundleHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
//        } else {
//            for (int i = 0; i < size; i++) {
//                softAssert.assertEquals(moreRechargeHistory.getBundleName(i), bundleRechargeHistoryAPI.getResult().get(i).getBundleName(), " Bundle Name received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getPackageCategory(i), bundleRechargeHistoryAPI.getResult().get(i).getPackageCategory(), "Package Category received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getTransactionNumber(i), bundleRechargeHistoryAPI.getResult().get(i).getTxnNumber(), "Bundle Transaction number received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getStatus(i).toLowerCase(), bundleRechargeHistoryAPI.getResult().get(i).getStatus().toLowerCase(), "Bundle Status received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getSubscriptionDateTime(i), (moreRechargeHistory.getDateFromEpoch(Long.parseLong(bundleRechargeHistoryAPI.getResult().get(i).getSubscriptionDateTime()), "dd-MMM-yyyy HH:mm")), "Bundle Subscription Date & Time received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getExpiresOn(i), (moreRechargeHistory.getDateFromEpoch(Long.parseLong(bundleRechargeHistoryAPI.getResult().get(i).getExpiresOn()), "dd-MMM-yyyy HH:mm")), "Bundle Expiry Date & Time received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getValidity(i), bundleRechargeHistoryAPI.getResult().get(i).getValidity(), "Bundle Validity received is not as expected on row " + i);
//                softAssert.assertEquals(moreRechargeHistory.getBundlePrice(i), bundleRechargeHistoryAPI.getResult().get(i).getBundlePrice(), "Bundle Price received is not as expected on row " + i);
//            }
//        }
//        moreRechargeHistory.openingCustomerInteractionDashboard();
//        softAssert.assertAll();
//    }

}
