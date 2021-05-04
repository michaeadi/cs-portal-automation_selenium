package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.AccountsBalancePOJO;
import com.airtel.cs.pojo.RechargeHistoryPOJO;
import com.airtel.cs.pojo.UsageHistoryPOJO;
import com.airtel.cs.pojo.accumulators.AccumulatorsPOJO;
import com.airtel.cs.pojo.airtelmoney.AirtelMoneyPOJO;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.airtel.cs.commonutils.dataproviders.DataProviders.Table;

public class WidgetsOptionsTest extends Driver {
    String customerNumber;
    APIEndPoints api = new APIEndPoints();
    private final BaseActions actions = new BaseActions();


    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User(userType = "API")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean data) {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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

    @Table(name = "Da Details")
    @Test(priority = 2, description = "Validating DA Details", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void daDetailsTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating DA Details of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getCurrentBalanceWidgetPage().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            softAssert.assertEquals(pages.getDaDetailsPage().getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
            AccountsBalancePOJO accountsBalanceAPI = api.balanceAPITest(customerNumber);
            if (accountsBalanceAPI.getStatusCode() == 200) {
                int size = pages.getDaDetailsPage().getNumbersOfRows();
                if (size > 10) {
                    size = 10;
                }
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(pages.getDaDetailsPage().getDAId(i + 1), accountsBalanceAPI.getResult().get(i).getDaId(), "DA ID is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getDADescription(i + 1).trim(), accountsBalanceAPI.getResult().get(i).getDaDesc(), "DA Description is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getBundleType(i + 1).replace("-", "null") + " ", accountsBalanceAPI.getResult().get(i).getBundleType() + " ", "DA Bundle Type is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getDADateTime(i + 1), UtilsMethods.getDateFromEpochInUTC(accountsBalanceAPI.getResult().get(i).getExpiryDate(), "dd-MMM-yyyy"), "DA Date Time is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getDABalance(i + 1), accountsBalanceAPI.getResult().get(i).getCurrentDaBalance(), "DA Current Balance is not as received in com.airtel.cs.API on row " + i);
                    if (i != 0) {
                        softAssert.assertTrue(UtilsMethods.isSortOrderDisplay(pages.getDaDetailsPage().getDADateTime(i), pages.getDaDetailsPage().getDADateTime(i + 1), "dd-MMM-yyy"), pages.getDaDetailsPage().getDADateTime(i) + "should not display before " + pages.getDaDetailsPage().getDADateTime(i + 1));
                    }
                }
                pages.getDaDetailsPage().openingCustomerInteractionDashboard();
            } else {
                softAssert.fail("com.airtel.cs.API Response does not 200 :" + accountsBalanceAPI.getMessage());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            commonLib.fail("DA details does not display correctly", true);
            softAssert.fail("DA details does not display correctly");
            pages.getDaDetailsPage().openingCustomerInteractionDashboard();
        }
        softAssert.assertAll();
    }

    @Table(name = "Accumulator")
    @Test(priority = 3, description = "Validating Accumulator Details", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void accumulatorDetailsTest(HeaderDataBean Data) {
        selUtils.addTestcaseDescription("Validating Accumulator Details of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getCurrentBalanceWidgetPage().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            softAssert.assertEquals(pages.getDaDetailsPage().getAccumulatorHeaders(1).toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getAccumulatorHeaders(2).toLowerCase().trim(), Data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getAccumulatorHeaders(3).toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
            softAssert.assertEquals(pages.getDaDetailsPage().getAccumulatorHeaders(4).toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
            AccumulatorsPOJO accumulatorAPI = api.accumulatorsAPITest(customerNumber);
            if (accumulatorAPI.getStatusCode() == 200) {
                int size = accumulatorAPI.getResult().size() > 5 ? 5 : accumulatorAPI.getResult().size();
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(pages.getDaDetailsPage().getValueCorrespondingToAccumulator(i + 1, 1).trim(), accumulatorAPI.getResult().get(i).getId(), "Accumulator ID is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getValueCorrespondingToAccumulator(i + 1, 2).trim(), String.valueOf(accumulatorAPI.getResult().get(i).getValue()), "Accumulator Value is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getValueCorrespondingToAccumulator(i + 1, 3).trim(), accumulatorAPI.getResult().get(i).getStartDate() == null ? "-" : UtilsMethods.getDateFromString(accumulatorAPI.getResult().get(i).getStartDate(), config.getProperty("UIAccumulatorTimeFormat"), config.getProperty("APIAccumulatorTimeFormat")), "Accumulator Start Date is not as received in com.airtel.cs.API on row " + i);
                    softAssert.assertEquals(pages.getDaDetailsPage().getValueCorrespondingToAccumulator(i + 1, 4).trim(), accumulatorAPI.getResult().get(i).getNextResetDate() == null ? "-" : UtilsMethods.getDateFromString(accumulatorAPI.getResult().get(i).getNextResetDate(), config.getProperty("UIAccumulatorTimeFormat"), config.getProperty("APIAccumulatorTimeFormat")), "Accumulator Next Reset Date Time is not as received in com.airtel.cs.API on row " + i);
                }
                pages.getDaDetailsPage().openingCustomerInteractionDashboard();
            } else {
                softAssert.fail("com.airtel.cs.API Response does not 200 :" + accumulatorAPI.getMessage());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            commonLib.fail("Accumulator details does not display correctly", true);
            softAssert.fail("Accumulator details does not display correctly");
            pages.getDaDetailsPage().openingCustomerInteractionDashboard();
        }
        softAssert.assertAll();
    }

    //    @Table(Name = "SMS History")
//    @Test(priority = 4, description = "Validating Usage History's SMS History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryMenuAndSMSHistoryTest(HeaderDataBean Data) {
//        selUtils.addTestcaseDescription();("Validating Usage History's SMS History of User :" + customerNumber, "description");
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
//            commonLib.warning("Unable to get SMS History Details from com.airtel.cs.API");
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
//    @Test(priority = 5, description = "Validating Usage History's Call History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryCallMenuTest(HeaderDataBean Data) {
//        selUtils.addTestcaseDescription();("Validating Usage History's Call History of User :" + customerNumber, "description");
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
//            commonLib.warning("Unable to get CALL History Details from com.airtel.cs.API");
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
//    @Test(priority = 6, description = "Validating Usage History's Data History", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
//    public void usageHistoryDataMenuTest(HeaderDataBean Data) {
//        selUtils.addTestcaseDescription();("Validating Usage History's Data History of User :" + customerNumber, "description");
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
//            commonLib.warning("Unable to get DATA History Details from com.airtel.cs.API");
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
    @Table(name = "More Recharge History")
    @Test(priority = 4, description = "Validating Recharge History's  Menu", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void rechargeHistoryMenuTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Recharge History's  Menu of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetMenuVisible(), "Recharge History's MENU is not visible ");
        pages.getRechargeHistoryWidget().openingRechargeHistoryDetails();
        softAssert.assertTrue(pages.getMoreRechargeHistoryPage().isDatePickerVisible(), "Date picker is not visible ");

        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        int size = pages.getMoreRechargeHistoryPage().getNumbersOfRows();
        try {
            if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get DATA History Details from com.airtel.cs.API");
                softAssert.assertTrue(pages.getMoreRechargeHistoryPage().getNoResultFound(), "No Result Message is not Visible");
            } else {
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(4).toLowerCase().trim() + pages.getMoreRechargeHistoryPage().getSubHeaders(4).toLowerCase().trim().replace("|", ""), data.getRow4().toLowerCase().trim().toLowerCase().trim().replace("|", ""), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(8).toLowerCase().trim(), data.getRow8().toLowerCase().trim(), "Header Name for Row 8 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(9).toLowerCase().trim(), data.getRow9().toLowerCase().trim(), "Header Name for Row 9 is not as expected");
                softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getHeaders(10).toLowerCase().trim(), data.getRow10().toLowerCase().trim(), "Header Name for Row 10 is not as expected");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 1).trim(), rechargeHistoryAPI.getResult().get(i).getCharges().trim(), " Charges received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 2), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), config.getProperty("UIRechargeHistoryTimeFormat"), config.getProperty("APIRechargeHistoryTimeFormat")), "Date & Time received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 3).toLowerCase().trim().replaceAll("[^a-zA-Z]+", ""), rechargeHistoryAPI.getResult().get(i).getBundleName().toLowerCase().trim().replaceAll("[^a-zA-Z]+", ""), "Bundle Name received is not as expected on row " + i);
                    System.out.println("Recharge History Benefits:?? " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS() + " " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA());
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 4).replaceAll("[^a-zA-Z0-9]+", ""), ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() == null) ? "" : rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE()) + ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() == null) ? "" : rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA()) + ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS() == null) ? "" : rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS()), "Recharge Benefits received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 5).toLowerCase().trim(), rechargeHistoryAPI.getResult().get(i).getStatus().trim().toLowerCase(), "Status received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 6), rechargeHistoryAPI.getResult().get(i).getMode(), "Mode of recharge received is not as expected on row " + i);
                    if (rechargeHistoryAPI.getResult().get(i).getMode().equalsIgnoreCase("Voucher")) {
                        softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 7).trim(), rechargeHistoryAPI.getResult().get(i).getSerialNumber().trim(), "Serial Number received is not as expected on row " + i);
                    } else {
                        softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 7), "-", "Serial Number received is not as expected on row " + i);
                    }
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 8), ((rechargeHistoryAPI.getResult().get(i).getExpiryDate() == null) ? "-" : UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getExpiryDate(), "dd-MMM-yyyy hh:mm aa", "dd MMM yyyy hh:mm aa")), "Expiry date received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 9), "-", "Old Expiry date received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 10).trim(), ((rechargeHistoryAPI.getResult().get(i).getValidity() == null) ? "-" : rechargeHistoryAPI.getResult().get(i).getValidity()), "Validity received is not as expected on row " + i);
                    if (i != 0) {
                        softAssert.assertTrue(UtilsMethods.isSortOrderDisplay(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 2), pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i, 2), "dd-MMM-yyy HH:mm"), pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 2) + "should not display before " + pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i, 2));
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            e.printStackTrace();
            softAssert.fail("Not able to validate Correctly more recharge history widget: " + e.fillInStackTrace());
        }
        pages.getMoreRechargeHistoryPage().openingCustomerInteractionDashboard();
        pages.getMoreRechargeHistoryPage().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Table(name = "Detailed Usage History")
    @Test(priority = 5, description = "Validating Usage History's  Menu", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void usageHistoryMenuTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Usage History's  Menu of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUsageHistoryWidget().openingMoreDetails();
        pages.getDetailedUsageHistoryPage().waitTillLoaderGetsRemoved();
        Assert.assertTrue(pages.getDetailedUsageHistoryPage().isWidgetDisplay(), "Detailed Usage History Widget does not visible ");

        try {
            softAssert.assertTrue(pages.getDetailedUsageHistoryPage().isFreeCDR(), "Free CDR Option does not display on UI.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Free CDR Option does not available on UI " + e.getCause());
        }

        try {
            softAssert.assertTrue(pages.getDetailedUsageHistoryPage().isTypeOfCDR(), "Type of CDR Option does not display on UI.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Type of CDR Option does not available on UI " + e.getCause());
        }

        try {
            softAssert.assertTrue(pages.getDetailedUsageHistoryPage().isTodayDateFilter(), "Today date filter Option does not display on UI.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Today date filter Option does not available on UI " + e.getCause());
        }

        try {
            softAssert.assertTrue(pages.getDetailedUsageHistoryPage().isLast2DayDateFilter(), "Last 2 Days date filter Option does not display on UI.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Last 2 Days date filter Option does not available on UI " + e.getCause());
        }

        try {
            softAssert.assertTrue(pages.getDetailedUsageHistoryPage().isLast7DayDateFilter(), "Last 7 Days date filter Option does not display on UI.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Last 7 Days date filter Option does not available on UI " + e.getCause());
        }

        try {
            UsageHistoryPOJO usageHistoryAPI = api.usageHistoryMenuTest(customerNumber);
            int size = usageHistoryAPI.getTotalCount();
            if (size > 20) {
                size = 20;
            }
            if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
                softAssert.assertTrue(pages.getDetailedUsageHistoryPage().getNoResultFound(), "No Result Message is not Visible");
            } else {
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
                softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getHeaders(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
                softAssert.assertTrue(pages.getDetailedUsageHistoryPage().isPagination(), "Pagination does not display on UI");
                for (int i = 0; i < size; i++) {
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 1).toLowerCase().trim(), usageHistoryAPI.getResult().get(i).getType().toLowerCase().trim(), " Type received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 2), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Date & Time received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 3).trim(), usageHistoryAPI.getResult().get(i).getStartBalance().trim(), "Start Balance received is not as expected on row " + i);
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 4).trim(), usageHistoryAPI.getResult().get(i).getCharges().trim(), "Charges received is not as expected on row " + i);
                    if (usageHistoryAPI.getResult().get(i).getCharges().charAt(0) == '-') {
                        softAssert.assertTrue(pages.getDetailedUsageHistoryPage().checkSignDisplay(i + 1), "Red Negative Symbol does not display at row " + i);
                    }
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 5), usageHistoryAPI.getResult().get(i).getEndBalance(), "End balance received is not as expected on row " + i);
                    if (usageHistoryAPI.getResult().get(i).getDescription() == null) {
                        usageHistoryAPI.getResult().get(i).setDescription("-");
                    }
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 6), usageHistoryAPI.getResult().get(i).getDescription(), "Description received is not as expected on row " + i);
                    if (usageHistoryAPI.getResult().get(i).getBundleName() == null) {
                        usageHistoryAPI.getResult().get(i).setBundleName("-");
                    }
                    softAssert.assertEquals(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 7).toLowerCase().trim(), usageHistoryAPI.getResult().get(i).getBundleName().toLowerCase().trim(), "Bundle Name received is not as expected on row " + i);
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            softAssert.fail("Not able to validate Detailed Usage History Completely: " + e.fillInStackTrace());
        }
        pages.getUsageHistoryWidget().openingCustomerInteractionDashboard();
        pages.getUsageHistoryWidget().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Table(name = "More Airtel Money History")
    @Test(priority = 6, description = "Validating Airtel Money History's  Menu", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI", enabled = false)
    public void airtelMoneyHistoryMenuTest(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Airtel Money History's  Menu of User :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getAmTxnWidgetPage().clickMenuOption();
            //pages.getAmTransactionsWidget().openAMHistoryTab();
            pages.getMoreAMTxnTabPage().waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTab(), "Today Filter does not display.");
                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTab(), "Last Two Days Filter does not display.");
                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTab(), "Last Seven Days Filter does not display.");
                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTab(), "Date Range Filter does not display.");
                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isSearchTxnIdBox(), "TXN ID Box does not display.");
                AirtelMoneyPOJO amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, config.getProperty("amCurrency"));
                if (amTransactionHistoryAPI.getStatusCode() != 200) {
                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is " + amTransactionHistoryAPI.getMessage());
                    softAssert.fail("com.airtel.cs.API is Unable to Get AM Transaction History for Customer");
                } else {
                    int count = amTransactionHistoryAPI.getResult().getTotalCount();
                    if (count > 0) {
                        if (count > 10) {
                            count = 10;
                        }
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(8).toLowerCase().trim(), data.getRow8().toLowerCase().trim(), "Header Name for Row 8 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(9).toLowerCase().trim(), data.getRow9().toLowerCase().trim(), "Header Name for Row 9 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(10).toLowerCase().trim(), data.getRow10().toLowerCase().trim(), "Header Name for Row 10 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(11).toLowerCase().trim(), data.getRow11().toLowerCase().trim(), "Header Name for Row 11 is not as expected");
                        softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(12).toLowerCase().trim(), data.getRow12().toLowerCase().trim(), "Header Name for Row 12 is not as expected");
                        for (int i = 0; i < count; i++) {
                            if (amTransactionHistoryAPI.getResult().getData().get(i).getAmount().charAt(0) == '+') {
                                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isPosSignDisplay(i + 1), i + "th Positive Sign does not display in case of Amount Credited.");
                            } else {
                                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isNegSignDisplay(i + 1), i + "th Negative Sign does not display in case of Amount Debited.");
                            }
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 2), UtilsMethods.getDateFromEpoch(new Long(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), config.getProperty("AMHistoryTimeFormat")), i + "th Date is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 3), amTransactionHistoryAPI.getResult().getData().get(i).getService(), i + "th Service name is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getSource(), i + "th Sender MSISDN is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), i + "th Receiver MSISDN is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 6), amTransactionHistoryAPI.getResult().getData().get(i).getSecondPartyName(), i + "th Beneficiary name is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 7), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), i + "th Transaction Id is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 8), amTransactionHistoryAPI.getResult().getData().get(i).getServiceCharge(), i + "th Service Charge is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 9), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceBefore(), i + "th Pre-balance is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 10), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceAfter(), i + "th Post-balance is not expected as com.airtel.cs.API response.");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 11), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), i + "th Status is not expected as com.airtel.cs.API response.");
                            if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                                softAssert.assertTrue(pages.getMoreAMTxnTabPage().isResendSMS(), "Resend SMS Icon does not enable as mentioned in com.airtel.cs.API Response.");
                            }
                        }
                    } else {
                        softAssert.assertTrue(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisible(), "No Result Found Icon does not display on UI.");
                    }
                }
                softAssert.assertAll();
            } catch (NullPointerException | NoSuchElementException | TimeoutException f) {
                softAssert.fail("Not able to validate airtel menu widget: " + f.fillInStackTrace());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to Open Airtel Monet History Sub Tab " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Table(name = "More Airtel Money History")
    @Test(priority = 7, description = "Validating Airtel Money History's  Menu Secondary Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI", enabled = false)
    public void airtelMoneyHistoryMenuSecondaryTest(HeaderDataBean data) {
        if (OPCO.equalsIgnoreCase("CD")) {
            selUtils.addTestcaseDescription("Validating Airtel Money History's  Menu Secondary Widget of User :" + customerNumber, "description");
            SoftAssert softAssert = new SoftAssert();
            try {
                pages.getAmTxnWidgetPage().clickMenuOption();
                //pages.getAmTransactionsWidget().openAMHistoryTab();
                pages.getMoreAMTxnTabPage().waitTillLoaderGetsRemoved();
                try {
                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), "Today Filter does not display.");
                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), "Last Two Days Filter does not display.");
                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), "Last Seven Days Filter does not display.");
                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isTodayFilterTabOnSecondWidget(), "Date Range Filter does not display.");
                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isSearchTxnIdBoxOnSecondWidget(), "TXN ID Box does not display.");
                    AirtelMoneyPOJO amTransactionHistoryAPI = api.moreTransactionHistoryAPITest(customerNumber, config.getProperty("2ndAmCurrency"));
                    if (amTransactionHistoryAPI.getStatusCode() != 200) {
                        softAssert.assertTrue(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisible(), "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is " + amTransactionHistoryAPI.getMessage());
                        softAssert.fail("com.airtel.cs.API is Unable to Get AM Transaction History for Customer");
                    } else {
                        int count = amTransactionHistoryAPI.getResult().getTotalCount();
                        if (count > 0) {
                            if (count > 10) {
                                count = 10;
                            }
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Header Name for Row 6 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Header Name for Row 7 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(8).toLowerCase().trim(), data.getRow8().toLowerCase().trim(), "Header Name for Row 8 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(9).toLowerCase().trim(), data.getRow9().toLowerCase().trim(), "Header Name for Row 9 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(10).toLowerCase().trim(), data.getRow10().toLowerCase().trim(), "Header Name for Row 10 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(11).toLowerCase().trim(), data.getRow11().toLowerCase().trim(), "Header Name for Row 11 is not as expected");
                            softAssert.assertEquals(pages.getMoreAMTxnTabPage().getHeaders(12).toLowerCase().trim(), data.getRow12().toLowerCase().trim(), "Header Name for Row 12 is not as expected");
                            for (int i = 0; i < count; i++) {
                                if (amTransactionHistoryAPI.getResult().getData().get(i).getAmount().charAt(0) == '+') {
                                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isPosSignDisplay(i + 1), i + "th Positive Sign does not display in case of Amount Credited.");
                                } else {
                                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isNegSignDisplay(i + 1), i + "th Negative Sign does not display in case of Amount Debited.");
                                }
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 2), UtilsMethods.getDateFromEpoch(new Long(amTransactionHistoryAPI.getResult().getData().get(i).getTransactionDate()), config.getProperty("AMHistoryTimeFormat")), i + "th Date is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 3), amTransactionHistoryAPI.getResult().getData().get(i).getService(), i + "th Service name is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 4), amTransactionHistoryAPI.getResult().getData().get(i).getSource(), i + "th Sender MSISDN is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 5), amTransactionHistoryAPI.getResult().getData().get(i).getMsisdn(), i + "th Receiver MSISDN is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 6), amTransactionHistoryAPI.getResult().getData().get(i).getSecondPartyName(), i + "th Beneficiary name is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 7), amTransactionHistoryAPI.getResult().getData().get(i).getTransactionId(), i + "th Transaction Id is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 8), amTransactionHistoryAPI.getResult().getData().get(i).getServiceCharge(), i + "th Service Charge is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 9), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceBefore(), i + "th Pre-balance is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 10), amTransactionHistoryAPI.getResult().getData().get(i).getBalanceAfter(), i + "th Post-balance is not expected as com.airtel.cs.API response.");
                                softAssert.assertEquals(pages.getMoreAMTxnTabPage().getValueCorrespondingToHeader(i + 1, 11), amTransactionHistoryAPI.getResult().getData().get(i).getStatus(), i + "th Status is not expected as com.airtel.cs.API response.");
                                if (amTransactionHistoryAPI.getResult().getData().get(i).getEnableResendSms()) {
                                    softAssert.assertTrue(pages.getMoreAMTxnTabPage().isResendSMS(), "Resend SMS Icon does not enable as mentioned in com.airtel.cs.API Response.");
                                }
                            }
                        } else {
                            softAssert.assertTrue(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisible(), "No Result Found Icon does not display on UI.");
                        }
                    }
                    softAssert.assertAll();
                } catch (NullPointerException | NoSuchElementException | TimeoutException f) {
                    softAssert.fail("Not able to validate airtel menu widget: " + f.fillInStackTrace());
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Not able to Open Airtel Monet History Sub Tab " + e.fillInStackTrace());
            }
            softAssert.assertAll();
        }
    }
}
