package tests;

import API.APITest;
import POJO.AccountsBalancePOJO;
import POJO.Accumulators.AccumulatorsPOJO;
import POJO.BundleRechargeHistoryPOJO;
import POJO.RechargeHistoryPOJO;
import POJO.UsageHistoryPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
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
        DADetailsPOM daDetails=null;
        try {
            currentBalanceWidget.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
            daDetails = currentBalanceWidget.openingDADetails();
            softAssert.assertEquals(daDetails.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(daDetails.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(daDetails.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(daDetails.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(daDetails.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            AccountsBalancePOJO accountsBalanceAPI = api.balanceAPITest(customerNumber);
            if (accountsBalanceAPI.getStatusCode() == 200) {
                int size = daDetails.getNumbersOfRows();
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(daDetails.getDAId(i + 1), accountsBalanceAPI.getResult().get(i).getDaId(), "DA ID is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getDADescription(i + 1), accountsBalanceAPI.getResult().get(i).getDaDesc(), "DA Description is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getBundleType(i + 1).replace("-", "null") + " ", accountsBalanceAPI.getResult().get(i).getBundleType() + " ", "DA Bundle Type is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getDADateTime(i + 1), daDetails.getDateFromEpochInUTC(accountsBalanceAPI.getResult().get(i).getExpiryDate(), "dd-MMM-yyyy HH:mm"), "DA Date Time is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getDABalance(i + 1), accountsBalanceAPI.getResult().get(i).getCurrentDaBalance(), "DA Current Balance is not as received in API on row " + i);
                    if (i != 0) {
                        softAssert.assertTrue(daDetails.isSortOrderDisplay(daDetails.getDADateTime(i), daDetails.getDADateTime(i + 1), "dd-MMM-yyy HH:mm"), daDetails.getDADateTime(i) + "should not display before " + daDetails.getDADateTime(i + 1));
                    }
                }
                daDetails.openingCustomerInteractionDashboard();
            }else{
                softAssert.fail("API Response does not 200 :"+accountsBalanceAPI.getMessage());
            }
            } catch(NoSuchElementException | TimeoutException e){
                e.printStackTrace();
                ExtentTestManager.getTest().log(LogStatus.FAIL, e.fillInStackTrace());
                softAssert.fail("DA details does not display correctly");
                daDetails.openingCustomerInteractionDashboard();
            }
        softAssert.assertAll();
    }

    @Table(Name = "Accumulator")
    @Test(priority = 2, description = "Validating Accumulator Details", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void accumulatorDetailsTest(HeaderDataBean Data) {
        customerNumber = customerInteractionTest.customerNumber;
        ExtentTestManager.startTest("Validating Accumulator Details", "Validating Accumulator Details of User :" + customerNumber);
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DADetailsPOM daDetails=null;
        try {
            currentBalanceWidget.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
            daDetails = currentBalanceWidget.openingDADetails();
            softAssert.assertEquals(daDetails.getAccumulatorHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(daDetails.getAccumulatorHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(daDetails.getAccumulatorHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(daDetails.getAccumulatorHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            AccumulatorsPOJO accumulatorAPI = api.accumulatorsAPITest(customerNumber);
            if (accumulatorAPI.getStatusCode() == 200) {
                int size = accumulatorAPI.getResult().size()>5 ?5:accumulatorAPI.getResult().size();
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(daDetails.getValueCorrespondingToAccumulator(i + 1,1).trim(), accumulatorAPI.getResult().get(i).getId(), "Accumulator ID is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getValueCorrespondingToAccumulator(i + 1,2).trim(), String.valueOf(accumulatorAPI.getResult().get(i).getValue()), "Accumulator Value is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getValueCorrespondingToAccumulator(i + 1,3).trim(), accumulatorAPI.getResult().get(i).getStartDate()==null?"-":daDetails.getDateFromString(accumulatorAPI.getResult().get(i).getStartDate(),"dd-MMM-yyyy"), "Accumulator Start Date is not as received in API on row " + i);
                    softAssert.assertEquals(daDetails.getValueCorrespondingToAccumulator(i + 1,4).trim(), accumulatorAPI.getResult().get(i).getNextResetDate()==null?"-":daDetails.getDateFromString(accumulatorAPI.getResult().get(i).getNextResetDate(), "dd-MMM-yyyy"), "Accumulator Next Reset Date Time is not as received in API on row " + i);
                }
                daDetails.openingCustomerInteractionDashboard();
            }else{
                softAssert.fail("API Response does not 200 :"+accumulatorAPI.getMessage());
            }
        } catch(NoSuchElementException | TimeoutException e){
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.FAIL, e.fillInStackTrace());
            softAssert.fail("Accumulator details does not display correctly");
            daDetails.openingCustomerInteractionDashboard();
        }
        softAssert.assertAll();
    }

//    @Table(Name = "SMS History")
//    @Test(priority = 2, description = "Validating Usage History's SMS History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryMenuAndSMSHistoryTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History's SMS History", "Validating Usage History's SMS History of User :" + customerNumber);
//        UsageHistoryWidgetPOM usageHistory = new UsageHistoryWidgetPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertTrue(usageHistory.isUsageHistoryWidgetMenuVisible(), "Usage History's MENU is not visible ");
//        MoreUsageHistoryPOM moreUsageHistory = usageHistory.openingMoreDetails();
//        softAssert.assertTrue(moreUsageHistory.isSMSDatePickerVisible(), "SMS History Date picker is not visible ");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for SMS Row 1 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for SMS Row 2 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for SMS Row 3 is not as expected");
//        softAssert.assertEquals(moreUsageHistory.getSMSHistoryHeader(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for SMS Row 4 is not as expected");
//
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
////                softAssert.assertEquals(moreUsageHistory.getSMSTransactionNumber(i), smsUsageHistoryAPI.getResult().get(i).getTxnNumber(), "SMS Transaction Number received is not as expected on row " + i);
//                if (i != 0) {
//                    softAssert.assertTrue(moreUsageHistory.isSortOrderDisplay(moreUsageHistory.getSMSDateTime(i), moreUsageHistory.getSMSDateTime(i - 1), "dd-MMM-yyy HH:mm"), moreUsageHistory.getSMSDateTime(i) + "should not display before " + moreUsageHistory.getSMSDateTime(i - 1));
//                }
//            }
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Call History")
//    @Test(priority = 3, description = "Validating Usage History's Call History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryCallMenuTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History's Call History", "Validating Usage History's Call History of User :" + customerNumber);
//        MoreUsageHistoryPOM moreUsageHistory = new MoreUsageHistoryPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
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
////                softAssert.assertEquals(moreUsageHistory.getCallTransactionNumber(i), callUsageHistoryAPI.getResult().get(i).getTxnNumber(), "Call Transaction Number received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getCallDuration(i), callUsageHistoryAPI.getResult().get(i).getCallDuration(), "Call Duration  received is not as expected on row " + i);
//                if (i != 0) {
//                    softAssert.assertTrue(moreUsageHistory.isSortOrderDisplay(moreUsageHistory.getCallDateTime(i), moreUsageHistory.getCallDateTime(i - 1), "dd-MMM-yyy HH:mm"), moreUsageHistory.getCallDuration(i) + "should not display before " + moreUsageHistory.getCallDuration(i - 1));
//                }
//            }
//        }
//        softAssert.assertAll();
//    }
//
//    @Table(Name = "Data History")
//    @Test(priority = 4, description = "Validating Usage History's Data History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryDataMenuTest(HeaderDataBean Data) {
//        ExtentTestManager.startTest("Validating Usage History's Data History", "Validating Usage History's Data History of User :" + customerNumber);
//        MoreUsageHistoryPOM moreUsageHistory = new MoreUsageHistoryPOM(driver);
//        SoftAssert softAssert = new SoftAssert();
//
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
//            for (int i = 0; i < dataSize; i++) {
//                softAssert.assertEquals(moreUsageHistory.getDataBundleName(i), dataUsageHistoryAPI.getResult().get(i).getBundleName(), "Data Bundle received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getDataCharges(i), dataUsageHistoryAPI.getResult().get(i).getCharges(), "Data Charges received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getDataDateTime(i), (moreUsageHistory.getDateFromEpoch(Long.parseLong(dataUsageHistoryAPI.getResult().get(i).getDateTime()), "dd-MMM-yyyy HH:mm")), "Data Date & Time received is not as expected on row " + i);
//                softAssert.assertEquals(moreUsageHistory.getUsedData(i), dataUsageHistoryAPI.getResult().get(i).getUsedData(), "Data Used is not as expected on row " + i);
////                softAssert.assertEquals(moreUsageHistory.getDataTransactionNumber(i), dataUsageHistoryAPI.getResult().get(i).getTxnNumber(), "Data Transaction Number received is not as expected on row " + i);
//                if (i != 0) {
//                    softAssert.assertTrue(moreUsageHistory.isSortOrderDisplay(moreUsageHistory.getDataDateTime(i), moreUsageHistory.getDataDateTime(i - 1), "dd-MMM-yyy HH:mm"), moreUsageHistory.getDataDateTime(i) + "should not display before " + moreUsageHistory.getDataDateTime(i - 1));
//                }
//            }
//        }
//        moreUsageHistory.openingCustomerInteractionDashboard();
//        softAssert.assertAll();
//    }
//
    @Table(Name = "More Recharge History")
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

        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        int size = moreRechargeHistory.getNumbersOfRows();
        try {
            if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get DATA History Details from API");
                softAssert.assertTrue(moreRechargeHistory.getNoResultFound(), "No Result Message is not Visible");
            } else {
                softAssert.assertEquals(moreRechargeHistory.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(4).toLowerCase().trim() + moreRechargeHistory.getSubHeaders(4).toLowerCase().trim().replace("|", ""), Data.getRow4().toLowerCase().trim().toLowerCase().trim().replace("|", ""), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(6).toLowerCase().trim(), Data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(7).toLowerCase().trim(), Data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(8).toLowerCase().trim(), Data.getRow8().toLowerCase().trim(), "Header Name for Row 8 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(9).toLowerCase().trim(), Data.getRow9().toLowerCase().trim(), "Header Name for Row 9 is not as expected");
                softAssert.assertEquals(moreRechargeHistory.getHeaders(10).toLowerCase().trim(), Data.getRow10().toLowerCase().trim(), "Header Name for Row 10 is not as expected");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 1).trim(), rechargeHistoryAPI.getResult().get(i).getCharges().trim(), " Charges received is not as expected on row " + i);
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 2), rechargeHistory.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), "dd-MMM-yyyy HH:mm"), "Date & Time received is not as expected on row " + i);
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 3).toLowerCase().trim().replaceAll("[^a-zA-Z]+", ""), rechargeHistoryAPI.getResult().get(i).getBundleName().toLowerCase().trim().replaceAll("[^a-zA-Z]+", ""), "Bundle Name received is not as expected on row " + i);
                    System.out.println("Recharge History Benefits:?? "+rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS()+" "+rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE()+" "+rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA());
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 4).replaceAll("[^a-zA-Z]+", ""), ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE()==null) ? "":rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE()) + ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA()==null) ? "":rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA()) + ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS()==null)?"":rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS()), "Recharge Benefits received is not as expected on row " + i);
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 5).toLowerCase().trim(), rechargeHistoryAPI.getResult().get(i).getStatus().trim().toLowerCase(), "Status received is not as expected on row " + i);
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 6), rechargeHistoryAPI.getResult().get(i).getMode(), "Mode of recharge received is not as expected on row " + i);
                    if(rechargeHistoryAPI.getResult().get(i).getMode().equalsIgnoreCase("Voucher")) {
                        softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 7).trim(), rechargeHistoryAPI.getResult().get(i).getSerialNumber().trim(), "Serial Number received is not as expected on row " + i);
                    }else{
                        softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 7), "-", "Serial Number received is not as expected on row " + i);
                    }
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 8), ((rechargeHistoryAPI.getResult().get(i).getExpiryDate()==null)?"-":rechargeHistory.getDateFromString(rechargeHistoryAPI.getResult().get(i).getExpiryDate(),"dd-MMM-yyyy HH:mm")), "Expiry date received is not as expected on row " + i);
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 9), "-", "Old Expiry date received is not as expected on row " + i);
                    softAssert.assertEquals(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i + 1, 10).trim(), ((rechargeHistoryAPI.getResult().get(i).getValidity()==null)?"-":rechargeHistoryAPI.getResult().get(i).getValidity()), "Validity received is not as expected on row " + i);
                    if (i != 0) {
                        softAssert.assertTrue(moreRechargeHistory.isSortOrderDisplay(moreRechargeHistory.getValueCorrespondingToRechargeHeader(i+1, 2), moreRechargeHistory.getValueCorrespondingToRechargeHeader(i, 2), "dd-MMM-yyy HH:mm"), moreRechargeHistory.getValueCorrespondingToRechargeHeader(i+1, 2) + "should not display before " + moreRechargeHistory.getValueCorrespondingToRechargeHeader(i , 2));
                    }
                }
            }
        }catch (NoSuchElementException | TimeoutException | NullPointerException e){
            e.printStackTrace();
            softAssert.fail("Not able to validate Correctly more recharge history widget: "+e.fillInStackTrace());
        }
        moreRechargeHistory.openingCustomerInteractionDashboard();
        moreRechargeHistory.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Table(Name = "Detailed Usage History")
    @Test(priority = 6, description = "Validating Usage History's  Menu", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void usageHistoryMenuTest(HeaderDataBean Data) {
        ExtentTestManager.startTest("Validating Usage History's  Menu", "Validating Usage History's  Menu of User :" + customerNumber);
        UsageHistoryWidgetPOM usageHistory = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DetailedUsageHistoryPOM detailedUsage=usageHistory.openingMoreDetails();
        detailedUsage.waitTillLoaderGetsRemoved();
        Assert.assertTrue(detailedUsage.isWidgetDisplay(), "Detailed Usage History Widget does not visible ");

        try{
            softAssert.assertTrue(detailedUsage.isFreeCDR(),"Free CDR Option does not display on UI.");
        }catch (NoSuchElementException | TimeoutException e){
                softAssert.fail("Free CDR Option does not available on UI "+e.getCause());
        }

        try{
            softAssert.assertTrue(detailedUsage.isTypeOfCDR(),"Type of CDR Option does not display on UI.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Type of CDR Option does not available on UI "+e.getCause());
        }

        try{
            softAssert.assertTrue(detailedUsage.isTodayDateFilter(),"Today date filter Option does not display on UI.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Today date filter Option does not available on UI "+e.getCause());
        }

        try{
            softAssert.assertTrue(detailedUsage.isLast2DayDateFilter(),"Last 2 Days date filter Option does not display on UI.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Last 2 Days date filter Option does not available on UI "+e.getCause());
        }

        try{
            softAssert.assertTrue(detailedUsage.isLast7DayDateFilter(),"Last 7 Days date filter Option does not display on UI.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Last 7 Days date filter Option does not available on UI "+e.getCause());
        }

        try{
        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryMenuTest(customerNumber);
        int size = usageHistoryAPI.getTotalCount();
        if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            ExtentTestManager.getTest().log(LogStatus.WARNING, "Unable to get Usage History Details from API");
            softAssert.assertTrue(detailedUsage.getNoResultFound(), "No Result Message is not Visible");
        } else {
            softAssert.assertEquals(detailedUsage.getHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(detailedUsage.getHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(detailedUsage.getHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(detailedUsage.getHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(detailedUsage.getHeaders(5).toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            softAssert.assertEquals(detailedUsage.getHeaders(6).toLowerCase().trim(), Data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
            softAssert.assertEquals(detailedUsage.getHeaders(7).toLowerCase().trim(), Data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
            softAssert.assertTrue(detailedUsage.isPagination(),"Pagination does not display on UI");
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,1).toLowerCase().trim(), usageHistoryAPI.getResult().get(i).getType().toLowerCase().trim(), " Type received is not as expected on row " + i);
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,2), usageHistoryAPI.getResult().get(i).getDateTime()+"\n"+usageHistoryAPI.getResult().get(i).getTime(), "Date & Time received is not as expected on row " + i);
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,3).trim(), usageHistoryAPI.getResult().get(i).getStartBalance().trim(), "Start Balance received is not as expected on row " + i);
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,4).trim(), usageHistoryAPI.getResult().get(i).getCharges().trim(), "Charges received is not as expected on row " + i);
                if(usageHistoryAPI.getResult().get(i).getCharges().charAt(0)=='-'){
                    softAssert.assertTrue(detailedUsage.checkSignDisplay(i+1),"Red Negative Symbol does not display at row "+i);
                }
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,5), usageHistoryAPI.getResult().get(i).getEndBalance(), "End balance received is not as expected on row " + i);
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,6), usageHistoryAPI.getResult().get(i).getDescription(), "Description received is not as expected on row " + i);
                softAssert.assertEquals(detailedUsage.getValueCorrespondingToHeader(i+1,7).toLowerCase().trim(), usageHistoryAPI.getResult().get(i).getBundleName().toLowerCase().trim(), "Bundle Name received is not as expected on row " + i);
                if(i !=0){
                    softAssert.assertTrue(detailedUsage.isSortOrderDisplay(detailedUsage.getValueCorrespondingToHeader(i+1, 2).replace("\n"," "), detailedUsage.getValueCorrespondingToHeader(i, 2).replace("\n"," "), "E dd MMM yyyy hh:mm:ss aa"), detailedUsage.getValueCorrespondingToHeader(i+1, 2) + "should not display before " + detailedUsage.getValueCorrespondingToHeader(i, 2));
                }
            }
        }
        }catch (NoSuchElementException | TimeoutException e){
            e.printStackTrace();
            softAssert.fail("Not able to validate Detailed Usage History Completely: "+e.fillInStackTrace());
        }
        usageHistory.openingCustomerInteractionDashboard();
        usageHistory.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

}
