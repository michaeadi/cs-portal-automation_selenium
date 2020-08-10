package tests;


import API.APITest;
import POJO.*;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import static Utils.DataProviders.DataProviders.Table;
import static Utils.DataProviders.DataProviders.User;

public class customerInteractionTest extends BaseTest {

    static String customerNumber;
    APITest api = new APITest();

    @User()
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


    @User()
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void validateDemographicInformation(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();

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


    @Table(Name = "Airtel Money")
    @Test(priority = 3, description = "Validating AM Transaction Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void airtelMoneyTransactionWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating AM Transaction Widget", "Validating AM Transaction Widget of User :" + customerNumber);
        AMTransactionsWidgetPOM amTransactionsWidget = new AMTransactionsWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Transaction Widget is not visible");
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyWidgetDatePickerVisible(), "Airtel Money Transaction Widget's Date Picker is not visible");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
        softAssert.assertEquals(amTransactionsWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");

        AMProfilePOJO amServiceProfileAPI = api.amServiceProfileAPITest(customerNumber);
        AMTransactionHistoryPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
        if (amServiceProfileAPI.getResult() != null) {
            softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyBalance(), Integer.parseInt(amServiceProfileAPI.getResult().getWallet().get(0).getBalance())
                    , "Customer's Airtel Money Balance is not as Expected");
            softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyCurrency(), amServiceProfileAPI.getResult().getWallet().get(0).getCurrency(),
                    "Customer's Airtel Money Balance Currency  is not as Expected");
        } else if (amServiceProfileAPI.getStatusCode() != 200) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get AM Balance from API");
            softAssert.assertTrue(amTransactionsWidget.isAMBalanceUnableToFetch(), "No error Message on Unable to get AM Balance For User From API");
            softAssert.assertEquals(amTransactionsWidget.gettingAMBalanceUnableToFetchMessage(), "Unable to Fetch Data", "Error Message is not as Expected");
            softAssert.fail("API is Unable to Get AM Balance for Customer");
        }
        if (amTransactionHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyErrorVisible(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage());
            softAssert.fail("API is Unable to Get AM Transaction History for Customer");
        }
        softAssert.assertAll();
    }

    //Needs Discussion
    @Test(priority = 4, description = "Validating Current Balance Widget")
    public void yourCurrentBalanceWidgetTest() {
        ExtentTestManager.startTest("Validating Current Balance Transaction Widget", "Validating Current Balance Transaction Widget of User :" + customerNumber);
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");

        PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
        if (plansAPI.getResult().getMainAccountBalance() != null) {
            softAssert.assertEquals(Double.parseDouble(currentBalanceWidget.gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()), "Current Balance is not as Received in API ");
            softAssert.assertEquals(currentBalanceWidget.gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency(), "Current Balance Currency is not as Received in API ");
        } else {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Main Balance from API");

        }
        if (plansAPI.getResult().getLastRecharge() != null) {
            softAssert.assertEquals(Integer.parseInt(currentBalanceWidget.gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()), "Last Recharge is not as Received in API ");
            String Time = currentBalanceWidget.getTimeFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
            String Date = currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
            softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is not as Received in API");
        } else {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
            softAssert.assertTrue(currentBalanceWidget.isLastRechargeUnableToFetchVisible(), "Error Message is not Visible");
            softAssert.assertTrue(currentBalanceWidget.isLastRechargeDateTImeUnableTOFetch(), "Error Message is not Visible");
            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeUnableToFetchVisible(), "Unable to Fetch Data", "Error Message is not as Expected");
            softAssert.assertEquals(currentBalanceWidget.gettingLastRechargeDateTImeUnableTOFetch(), "Unable to Fetch Data", "Error Message is not as Expected");

        }

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
        if (plansAPI.getStatusCode() != 200) {
            softAssert.fail("API unable to get Last recharge and MAINSSS Balance ");
        }
        softAssert.assertAll();
    }


    @Table(Name = "Usage History")
    @Test(priority = 5, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void usageHistoryWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History Widget", "Validating Usage History Widget of User :" + customerNumber);
        UsageHistoryWidgetPOM usageHistoryWidget = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible");
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryDatePickerVisible(), "Usage History Widget's Date Picker is not visible");
        softAssert.assertEquals(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
        softAssert.assertEquals(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
        softAssert.assertEquals(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
        softAssert.assertEquals(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
        softAssert.assertEquals(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
        int size = usageHistoryWidget.getNumberOfRows();
        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryCharge(i), usageHistoryAPI.getResult().get(i).getCharges(), "Usage History Charge is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getDateFromEpoch(Long.parseLong(usageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyy HH:mm"), "Usage History Date Time is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is not As received in API for row number " + i);
                softAssert.assertEquals(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is not As received in API for row number " + i);
            }
        }
        if (usageHistoryAPI.getStatusCode() != 200) {
            softAssert.assertTrue(usageHistoryWidget.isUsageHistoryErrorVisible(), "API is Giving error But Widget is not showing error Message on API is ");
            softAssert.fail("API is unable to give Usage History ");
        }
        softAssert.assertAll();
    }


    @Table(Name = "Recharge History")
    @Test(priority = 6, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void rechargeHistoryWidgetTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Recharge History Widget", "Validating Recharge History Widget of User :" + customerNumber);
        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
        softAssert.assertEquals(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");

        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        int size = rechargeHistoryWidget.getNumberOfRows();
        if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Last Recharge Details from API");
            softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryCharges(i), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getDateFromEpoch(Long.parseLong(rechargeHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm"), "Recharge History Date Time is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBundleName(i), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBenefits(i).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is not As received in API for row number " + i);
                softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryStatus(i), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is not As received in API for row number " + i);
            }

        }
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), "API is Giving error But Widget is not showing error Message on API is ");
            softAssert.fail("API is unable to give Recharge History ");
        }
        softAssert.assertAll();
    }

}
