package tests;

import API.APITest;
import POJO.AMProfilePOJO;
import POJO.PlansPOJO;
import POJO.RechargeHistoryPOJO;
import POJO.UsageHistoryPOJO;
import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class customerInteractionTest extends BaseTest {
    @DataProvider.User(UserType = "ALL")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void openCustomerInteraction(TestDatabean Data) throws IOException {
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
    public void validateDemographicInformation(Method method, TestDatabean Data) throws IOException {
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
    @Test(priority = 3, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void airtelMoneyTransactionWidgetTest(TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        AMTransactionsWidgetPOM amTransactionsWidget = new AMTransactionsWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyTransactionWidgetIsVisible());
        softAssert.assertTrue(amTransactionsWidget.isAirtelMoneyWidgetDatePickerVisible());
        APITest api = new APITest();
        AMProfilePOJO amServiceProfileAPI = api.amServiceProfileAPITest(Data.getCustomerNumber());
        softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyBalance(), Integer.parseInt(amServiceProfileAPI.getResult().getWallet().get(0).getBalance()));
        softAssert.assertEquals(amTransactionsWidget.gettingAirtelMoneyCurrency(), amServiceProfileAPI.getResult().getWallet().get(0).getCurrency());
        softAssert.assertAll();
    }


    @DataProvider.User(UserType = "ALL")
    //Needs Discussion
    @Test(priority = 4, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void yourCurrentBalanceWidgetTest(TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible());
        APITest api = new APITest();
        PlansPOJO plansAPI = api.accountPlansTest(Data.getCustomerNumber());
        softAssert.assertEquals(Double.parseDouble(currentBalanceWidget.gettingMainAccountBalance()), Double.parseDouble(plansAPI.getResult().getMainAccountBalance().getBalance()));
        softAssert.assertEquals(currentBalanceWidget.gettingCurrentBalanceCurrency(), plansAPI.getResult().getMainAccountBalance().getCurrency());
        softAssert.assertEquals(Integer.parseInt(currentBalanceWidget.gettingLastRechargeAmount()), Integer.parseInt(plansAPI.getResult().getLastRecharge().getAmount()));
        String formatted1 = currentBalanceWidget.getTimeFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeTimePattern"));
        String formatted = currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getLastRecharge().getRechargeOn(), config.getProperty("LastRechargeDatePattern"));
        softAssert.assertEquals(currentBalanceWidget.getLastRechargeDateTime(), formatted + " " + formatted1);
        softAssert.assertEquals(currentBalanceWidget.getVoiceExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getVoice().getExpireTime(), config.getProperty("BalanceExpiryPattern")));
        softAssert.assertEquals(currentBalanceWidget.getDataExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getData().getExpireTime(), config.getProperty("BalanceExpiryPattern")));
        softAssert.assertEquals(currentBalanceWidget.getSmsExpiryDate(), currentBalanceWidget.getDateFromEpoch(plansAPI.getResult().getSms().getExpireTime(), config.getProperty("BalanceExpiryPattern")));
        softAssert.assertEquals(currentBalanceWidget.getVoiceBalance(), plansAPI.getResult().getVoice().getBalance());
        softAssert.assertEquals(currentBalanceWidget.getDataBalance(), plansAPI.getResult().getData().getBalance());
        softAssert.assertEquals(currentBalanceWidget.getSmsBalance(), plansAPI.getResult().getSms().getBalance());
        System.out.println(plansAPI.getResult().getVoice().getBalance());
        System.out.println(plansAPI.getResult().getData().getBalance());
        System.out.println(plansAPI.getResult().getSms().getBalance());
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 5, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void usageHistoryWidgetTest(TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        UsageHistoryWidgetPOM usageHistoryWidget = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryWidgetIsVisible());
        softAssert.assertTrue(usageHistoryWidget.isUsageHistoryDatePickerVisible());
        APITest api = new APITest();
        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(Data.getCustomerNumber());
        int size = usageHistoryWidget.getNumberOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType());
            softAssert.assertEquals(usageHistoryWidget.getHistoryCharge(i), usageHistoryAPI.getResult().get(i).getCharges());
            softAssert.assertEquals(usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getDateFromEpoch(Long.parseLong(usageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyy HH:mm"));
            softAssert.assertEquals(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance());
            softAssert.assertEquals(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance());
        }
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 6, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void rechargeHistoryWidgetTest(TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistoryWidget.isUsageHistoryWidgetIsVisible());
        softAssert.assertTrue(rechargeHistoryWidget.isUsageHistoryDatePickerVisible());
        APITest api = new APITest();
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(Data.getCustomerNumber());
        int size = rechargeHistoryWidget.getNumberOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryCharges(i), rechargeHistoryAPI.getResult().get(i).getCharges());
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getDateFromEpoch(Long.parseLong(rechargeHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyy HH:mm"));
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBundleName(i), rechargeHistoryAPI.getResult().get(i).getBundleName());
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryBenefits(i).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS());
            softAssert.assertEquals(rechargeHistoryWidget.getRechargeHistoryStatus(i), rechargeHistoryAPI.getResult().get(i).getStatus());
        }
        softAssert.assertAll();
    }

}
