package tests;

import API.APITest;
import POJO.AccountsBalancePOJO;
import POJO.BundleRechargeHistoryPOJO;
import POJO.UsageHistoryPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import static Utils.DataProviders.DataProviders.Table;

public class widgetsOptionsTest extends BaseTest {
    String customerNumber;
    APITest api = new APITest();

    @Table(Name = "Da Details")
    @Test(priority = 1, description = "Validating DA Details", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void daDetailsTest(HeaderDataBean Data) {
        customerNumber = customerInteractionTest.customerNumber;
        ExtentTestManager.startTest("Validating DA Details", "Validating DA Details of User :" + customerNumber);
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        try{
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
        currentBalanceWidget.clickingCurrentBalanceWidgetMenu();
        softAssert.assertTrue(currentBalanceWidget.isDADetailsMenuVisible(), "DA Details Option in  MENU is not visible ");
        DADetailsPOM daDetails = currentBalanceWidget.openingDADetails();
        softAssert.assertEquals(daDetails.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
        softAssert.assertEquals(daDetails.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
        softAssert.assertEquals(daDetails.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
        softAssert.assertEquals(daDetails.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
        softAssert.assertEquals(daDetails.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
        AccountsBalancePOJO accountsBalanceAPI = api.balanceAPITest(customerNumber);
        int size = daDetails.getNumbersOfRows();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(daDetails.getDAId(i), accountsBalanceAPI.getResult().get(i).getDaId(), "DA ID is not as received in API on row " + i);
            softAssert.assertEquals(daDetails.getDADescription(i), accountsBalanceAPI.getResult().get(i).getDaDesc(), "DA Description is not as received in API on row " + i);
            softAssert.assertEquals(daDetails.getBundleType(i).replace("-", "null") + " ", accountsBalanceAPI.getResult().get(i).getBundleType() + " ", "DA Bundle Type is not as received in API on row " + i);
            softAssert.assertEquals(daDetails.getDADateTime(i), daDetails.getTimeFromEpoch(accountsBalanceAPI.getResult().get(i).getExpiryDate(), "dd-MMM-yyyy HH:mm"), "DA Date Time is not as received in API on row " + i);
            softAssert.assertEquals(daDetails.getDABalance(i), accountsBalanceAPI.getResult().get(i).getCurrentDaBalance(), "DA Current Balance is not as received in API on row " + i);
        }
        daDetails.openingCustomerInteractionDashboard();
        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.FAIL,e.fillInStackTrace());
            softAssert.fail("DA details does not display correctly");
        }
        softAssert.assertAll();
    }

    @Table(Name = "SMS History")
    @Test(priority = 2, description = "Validating Usage History's SMS History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void usageHistoryMenuAndSMSHistoryTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History's SMS History", "Validating Usage History's SMS History of User :" + customerNumber);
        UsageHistoryWidgetPOM usageHistory = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(usageHistory.isUsageHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
        usageHistory.clickingUsageHistoryWidgetMenu();
        softAssert.assertTrue(usageHistory.isMoreMenuVisible(), "More Option in  MENU is not visible ");
        MoreUsageHistoryPOM moreUsageHistory = usageHistory.openingMoreDetails();
        softAssert.assertTrue(moreUsageHistory.isSMSDatePickerVisible(), "SMS History Date picker is not visible ");
        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for SMS Row 1 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for SMS Row 2 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for SMS Row 3 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for SMS Row 4 is not as expected");

        UsageHistoryPOJO smsUsageHistoryAPI = api.usageHistoryTest(customerNumber, "SMS_HISTORY");
        int smsSize = moreUsageHistory.getNumbersOfSMSRows();

        if (smsUsageHistoryAPI.getResult().size() == 0 || smsUsageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get SMS History Details from API");
            softAssert.assertTrue(moreUsageHistory.isSMSHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(moreUsageHistory.gettingSMSHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            for (int i = 0; i < smsSize; i++) {
                softAssert.assertEquals(moreUsageHistory.getSmsBundleName(i), smsUsageHistoryAPI.getResult().get(i).getBundleName(), "SMS Bundle received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getSMSCharges(i), smsUsageHistoryAPI.getResult().get(i).getCharges(), "SMS Charges received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getSMSDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(smsUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "SMS Date & Time received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getSMSTo(i), smsUsageHistoryAPI.getResult().get(i).getSmsTo(), "SMS To received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getSMSTransactionNumber(i), smsUsageHistoryAPI.getResult().get(i).getTxnNumber(), "SMS Transaction Number received is not as expected on row " + i);
                if(i!=0){
                    softAssert.assertTrue(moreUsageHistory.isSortOrderDisplay(moreUsageHistory.getSMSDateTime(i),moreUsageHistory.getSMSDateTime(i-1),"dd-MMM-yyy HH:mm"),moreUsageHistory.getSMSDateTime(i)+"should not display before "+moreUsageHistory.getSMSDateTime(i-1));
                }
            }
        }
        softAssert.assertAll();
    }

    @Table(Name = "Call History")
    @Test(priority = 3, description = "Validating Usage History's Call History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void usageHistoryCallMenuTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History's Call History", "Validating Usage History's Call History of User :" + customerNumber);
        MoreUsageHistoryPOM moreUsageHistory = new MoreUsageHistoryPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(moreUsageHistory.isCallDatePickerVisible(), "Call History Date picker is not visible ");
        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Call Row 1 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Call Row 2 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Call Row 3 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Call Row 4 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getCallHistoryHeader(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Call Row 5 is not as expected");
        int callSize = moreUsageHistory.getNumbersOfCallRows();
        UsageHistoryPOJO callUsageHistoryAPI = api.usageHistoryTest(customerNumber, "CALL_HISTORY");
        if (callUsageHistoryAPI.getResult().size() == 0 || callUsageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get CALL History Details from API");
            softAssert.assertTrue(moreUsageHistory.isCallHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(moreUsageHistory.gettingCallHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            for (int i = 0; i < callSize; i++) {
                softAssert.assertEquals(moreUsageHistory.getCallBundleName(i), callUsageHistoryAPI.getResult().get(i).getBundleName(), "Call Bundle received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getCallCharges(i), callUsageHistoryAPI.getResult().get(i).getCharges(), "Call Charges received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getCallDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(callUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "Call Date & Time received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getCallTo(i), callUsageHistoryAPI.getResult().get(i).getCallTo(), "Call To received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallTransactionNumber(i), callUsageHistoryAPI.getResult().get(i).getTxnNumber(), "Call Transaction Number received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getCallDuration(i), callUsageHistoryAPI.getResult().get(i).getCallDuration(), "Call Duration  received is not as expected on row " + i);
                if(i!=0){
                    softAssert.assertTrue(moreUsageHistory.isSortOrderDisplay(moreUsageHistory.getCallDateTime(i),moreUsageHistory.getCallDateTime(i-1),"dd-MMM-yyy HH:mm"),moreUsageHistory.getCallDuration(i)+"should not display before "+moreUsageHistory.getCallDuration(i-1));
                }
            }
        }
        softAssert.assertAll();
    }

    @Table(Name = "Data History")
    @Test(priority = 4, description = "Validating Usage History's Data History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void usageHistoryDataMenuTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History's Data History", "Validating Usage History's Data History of User :" + customerNumber);
        MoreUsageHistoryPOM moreUsageHistory = new MoreUsageHistoryPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        int dataSize = moreUsageHistory.getNumbersOfDataRows();
        softAssert.assertTrue(moreUsageHistory.isDataDatePickerVisible(), "Data History Date picker is not visible ");
        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Data Row 1 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Data Row 2 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Data Row 3 is not as expected");
        softAssert.assertEquals(moreUsageHistory.getDataHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Data Row 4 is not as expected");
        UsageHistoryPOJO dataUsageHistoryAPI = api.usageHistoryTest(customerNumber, "DATA_HISTORY");
        if (dataUsageHistoryAPI.getResult().size() == 0 || dataUsageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get DATA History Details from API");
            softAssert.assertTrue(moreUsageHistory.isDataHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(moreUsageHistory.gettingDataHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            for (int i = 0; i < dataSize; i++) {
                softAssert.assertEquals(moreUsageHistory.getDataBundleName(i), dataUsageHistoryAPI.getResult().get(i).getBundleName(), "Data Bundle received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getDataCharges(i), dataUsageHistoryAPI.getResult().get(i).getCharges(), "Data Charges received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getDataDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(dataUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "Data Date & Time received is not as expected on row " + i);
                softAssert.assertEquals(moreUsageHistory.getUsedData(i), dataUsageHistoryAPI.getResult().get(i).getUsedData(), "Data Used is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getDataTransactionNumber(i), dataUsageHistoryAPI.getResult().get(i).getTxnNumber(), "Data Transaction Number received is not as expected on row " + i);
                if(i!=0){
                    softAssert.assertTrue(moreUsageHistory.isSortOrderDisplay(moreUsageHistory.getDataDateTime(i),moreUsageHistory.getDataDateTime(i-1),"dd-MMM-yyy HH:mm"),moreUsageHistory.getDataDateTime(i)+"should not display before "+moreUsageHistory.getDataDateTime(i-1));
                }
            }
        }
        moreUsageHistory.openingCustomerInteractionDashboard();
        softAssert.assertAll();
    }

    @Table(Name = "Bundle Subscription History")
    @Test(priority = 5, description = "Validating Recharge History's  Menu", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void rechargeHistoryMenuTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Recharge History's  Menu", "Validating Recharge History's  Menu of User :" + customerNumber);
        RechargeHistoryWidgetPOM rechargeHistory = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistory.isRechargeHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
        rechargeHistory.clickingRechargeHistoryWidgetMenu();
        softAssert.assertTrue(rechargeHistory.isRechargeHistoryMenuVisible(), "More Option in  MENU is not visible ");
        MoreRechargeHistoryPOM moreRechargeHistory = rechargeHistory.openingRechargeHistoryDetails();
        softAssert.assertTrue(moreRechargeHistory.isDatePickerVisible(), "Date picker is not visible ");


        BundleRechargeHistoryPOJO bundleRechargeHistoryAPI = api.bundleRechargeHistoryAPITest(customerNumber);
        int size = moreRechargeHistory.getNumbersOfRows();
        softAssert.assertEquals(moreRechargeHistory.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(6).toLowerCase().trim(), Data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(7).toLowerCase().trim(), Data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
        softAssert.assertEquals(moreRechargeHistory.getHeaders(8).toLowerCase().trim(), Data.getRow8().toLowerCase().trim(), "Header Name for Row 8 is not as expected");
        if (bundleRechargeHistoryAPI.getResult().size() == 0 || bundleRechargeHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get DATA History Details from API");
            softAssert.assertTrue(moreRechargeHistory.isBundleHistoryNoResultFoundVisible(), "Error Message is not Visible");
            softAssert.assertEquals(moreRechargeHistory.gettingBundleHistoryNoResultFoundMessage(), "No Result found", "Error Message is not as expected");
        } else {
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(moreRechargeHistory.getBundleName(i), bundleRechargeHistoryAPI.getResult().get(i).getBundleName(), " Bundle Name received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getPackageCategory(i), bundleRechargeHistoryAPI.getResult().get(i).getPackageCategory(), "Package Category received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getTransactionNumber(i), bundleRechargeHistoryAPI.getResult().get(i).getTxnNumber(), "Bundle Transaction number received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getStatus(i).toLowerCase(), bundleRechargeHistoryAPI.getResult().get(i).getStatus().toLowerCase(), "Bundle Status received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getSubscriptionDateTime(i), (moreRechargeHistory.getDateFromEpoch(Long.parseLong(bundleRechargeHistoryAPI.getResult().get(i).getSubscriptionDateTime()), "dd-MMM-yyyy HH:mm")), "Bundle Subscription Date & Time received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getExpiresOn(i), (moreRechargeHistory.getDateFromEpoch(Long.parseLong(bundleRechargeHistoryAPI.getResult().get(i).getExpiresOn()), "dd-MMM-yyyy HH:mm")), "Bundle Expiry Date & Time received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getValidity(i), bundleRechargeHistoryAPI.getResult().get(i).getValidity(), "Bundle Validity received is not as expected on row " + i);
                softAssert.assertEquals(moreRechargeHistory.getBundlePrice(i), bundleRechargeHistoryAPI.getResult().get(i).getBundlePrice(), "Bundle Price received is not as expected on row " + i);
                if(i!=0){
                    softAssert.assertTrue(moreRechargeHistory.isSortOrderDisplay(moreRechargeHistory.getSubscriptionDateTime(i),moreRechargeHistory.getSubscriptionDateTime(i-1),"dd-MMM-yyy HH:mm"),moreRechargeHistory.getSubscriptionDateTime(i)+"should not display before "+moreRechargeHistory.getSubscriptionDateTime(i-1));
                }
            }
        }
        moreRechargeHistory.openingCustomerInteractionDashboard();
        softAssert.assertAll();
    }

}
