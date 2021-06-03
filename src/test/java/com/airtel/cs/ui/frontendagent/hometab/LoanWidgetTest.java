package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.LoanDetail;
import com.airtel.cs.pojo.response.loandetails.Loan;
import com.airtel.cs.pojo.response.loandetails.LoanDetailList;
import com.airtel.cs.pojo.response.loandetails.LoanHistory;
import com.airtel.cs.pojo.response.loandetails.LoanRepaymentDetailList;
import com.airtel.cs.pojo.response.loandetails.LoanRepaymentList;
import com.airtel.cs.pojo.response.loansummary.Summary;
import com.airtel.cs.pojo.response.vendors.HeaderList;
import com.airtel.cs.pojo.response.vendors.VendorNames;
import com.airtel.cs.pojo.response.vendors.Vendors;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LoanWidgetTest extends Driver {

    static String customerNumber;
    RequestSource api = new RequestSource();
    ArrayList<Vendors> vendors;
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
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

    @DataProviders.Table(name = "Loan Services")
    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", description = "Validating Loan Widget layout", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanWidgetLayout(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating Loan Service Widget layout", "description");
        SoftAssert softAssert = new SoftAssert();
        VendorNames vendorNames = api.vendorsNamesTest();
        vendors = vendorNames.getResult().getVendors();
        for (Vendors v : vendors) {
            commonLib.info("Loan Services: " + v.getVendorName());
        }
        /*
         * Checking com.airtel.cs.API Giving valid Response
         * */
        ArrayList<HeaderList> headers = vendorNames.getResult().getHeaderList();
        softAssert.assertEquals(headers.get(0).getHeader().toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", data.getRow2().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(2).getHeader().toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(3).getHeader().toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(4).getHeader().toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        /*
         * Checking header displayed on UI
         * */
        softAssert.assertEquals(headers.get(0).getHeader().toLowerCase().trim(), pages.getLoanWidget().getVendor().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", pages.getLoanWidget().getLoanAmount().toLowerCase().trim() + " (" + pages.getLoanWidget().getCurrencyType().toLowerCase().trim() + ")", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(2).getHeader().toLowerCase().trim(), pages.getLoanWidget().getCreatedON().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(3).getHeader().toLowerCase().trim(), pages.getLoanWidget().getCurrentOutstanding().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(4).getHeader().toLowerCase().trim(), pages.getLoanWidget().getDueDate().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertAll();
    }


    @Test(priority = 3, description = "Validate Loan Widget", dependsOnMethods = "openCustomerInteraction")
    public void validateLoanWidget() throws InterruptedException, IOException {
        selUtils.addTestcaseDescription("Validating Loan Service Widget:" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        if (pages.getLoanWidget().getSize() > 0) {
            for (int i = 1; i <= pages.getLoanWidget().getSize(); i++) {
                Summary summary = api.loanSummaryTest(customerNumber, pages.getLoanWidget().getVendorName(i).trim());
                if (!summary.getStatusCode().equalsIgnoreCase("200") | summary.getStatus().equalsIgnoreCase("Failure")) {
                    softAssert.assertTrue(pages.getLoanWidget().checkMessageDisplay(summary.getMessage()), summary.getMessage() + " :Message does not display");
                } else {
                    softAssert.assertEquals(pages.getLoanWidget().getLoanAmount(i), UtilsMethods.valueRoundOff(summary.getResult().getLoanAmount()), "Loan amount not same as com.airtel.cs.API Response");
                    softAssert.assertEquals(pages.getLoanWidget().getOutstandingAmount(i), UtilsMethods.valueRoundOff(summary.getResult().getCurrentOutstanding().getValue()), "Current Outstanding amount not same as com.airtel.cs.API Response");
                    /*
                     * Due Date and Created on assertion pending as com.airtel.cs.API not working as expected
                     * */
                }
            }
        } else {
            commonLib.warning("No Vendor Found in Loan Service Widget");
        }
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Loan Details")
    @Test(priority = 4, description = "Validate Loan Detail Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateLoanDetailWidget(HeaderDataBean data) throws InterruptedException, IOException {
        selUtils.addTestcaseDescription("Validate Loan Detail Widget", "description");
        SoftAssert softAssert = new SoftAssert();
        LoanDetail loanDetail = null;
        List<String> vendorNameList = pages.getLoanWidget().getVendorNamesList();
        if (vendorNameList.size() > 0) {
            for (int i = 1; i <= vendorNameList.size(); i++) {
                String vendorName = vendorNameList.get(i - 1).trim();
                Summary summary = api.loanSummaryTest(customerNumber, vendorName);
                if (summary.getStatusCode().equalsIgnoreCase("200")) {
                    try {
                        loanDetail = pages.getLoanWidget().clickVendorName(i);
                        loanDetail.waitTillLoaderGetsRemoved();
                        try {
                            Assert.assertTrue(loanDetail.isLoanDetailWidgetDisplay(), "Loan Detail Widget not display");
                            Loan loanDetails = api.loanDetailsTest(customerNumber, vendorName);
                            /*
                             * Validating Header name displayed on UI with Config present in Excel
                             * */
                            try {
                                softAssert.assertEquals(loanDetail.getHeaderName(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header name not same as defined in excel at pos(1)");
                                softAssert.assertEquals(loanDetail.getHeaderName(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header name not same as defined in excel pos(2)");
                                softAssert.assertEquals(loanDetail.getHeaderName(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header name not same as defined in excel pos(3)");
                                softAssert.assertEquals(loanDetail.getHeaderName(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header name not same as defined in excel pos(4)");
                                softAssert.assertEquals(loanDetail.getHeaderName(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header name not same as defined in excel pos(5)");
                            } catch (NoSuchElementException | TimeoutException e) {
                                softAssert.fail("Header name of detail widget does not display properly: " + e.fillInStackTrace());
                            }
                            /*
                             * Validating Header name & value displayed on UI with com.airtel.cs.API Response
                             * */
                            ArrayList<HeaderList> headerList = loanDetails.getResult().getLoanDetails().getHeaderList();
                            LoanDetailList loanDetailValue = loanDetails.getResult().getLoanDetails().getLoanDetailList().get(0);
                            for (int j = 0; j < headerList.size(); j++) {
                                softAssert.assertEquals(loanDetail.getHeaderName(i + 1).toLowerCase().trim(), headerList.get(i).getHeader().toLowerCase().trim(), "Loan Detail Widget Header name at POS(" + (i + 1) + ") not same as in com.airtel.cs.API Response");
                            }

                            softAssert.assertEquals(loanDetail.getValueCorrespondingToHeader(1).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getTotalLoanEligibility()), "Total Loan Eligibility Value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetail.getValueCorrespondingToHeader(2).trim(), String.valueOf(loanDetailValue.getCountOfEvents()), "Number of Loan Taken Value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetail.getValueCorrespondingToHeader(3).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getTotalLoanAmount()), "Total Loan amount Value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetail.getValueCorrespondingToHeader(4).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getLoanPaid()), "Total Loan Paid value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetail.getValueCorrespondingToHeader(5).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getRemainingBalance()), "Total Current Outstanding value not same as com.airtel.cs.API Response");
                        } catch (NoSuchElementException | TimeoutException | AssertionError e) {
                            softAssert.fail("Loan detail Widget does not open properly: " + e.fillInStackTrace());
                            loanDetail.clickCloseTab();
                        }
                    } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                        softAssert.fail("Vendor Name not clickable or Present on UI" + e.fillInStackTrace());
                    }
                }
            }
            assert loanDetail != null;
            loanDetail.clickCloseTab();
        } else {
            commonLib.warning("No Vendor Found in Loan Service Widget");
        }
        softAssert.assertAll();
    }


    @DataProviders.Table(name = "Loan History")
    @Test(priority = 5, dependsOnMethods = "openCustomerInteraction", description = "Validate Loan History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanHistoryWidget(HeaderDataBean data) throws InterruptedException, IOException {
        selUtils.addTestcaseDescription("Validate Loan History Widget", "description");
        SoftAssert softAssert = new SoftAssert();
        LoanDetail loanDetail = null;
        pages.getLoanWidget().waitTillLoaderGetsRemoved();
        if (pages.getLoanWidget().getSize() > 0) {
            try {
                for (int i = 1; i <= pages.getLoanWidget().getSize(); i++) {
                    String vendorName = pages.getLoanWidget().getVendorName(i).trim();
                    Summary summary = api.loanSummaryTest(customerNumber, vendorName);
                    if (summary.getStatusCode().equalsIgnoreCase("200")) {
                        try {
                            loanDetail = pages.getLoanWidget().clickVendorName(i);
                            loanDetail.waitTillLoaderGetsRemoved();
                            try {
                                Assert.assertTrue(loanDetail.isLoanHistoryWidgetDisplay(), "Loan History Widget not display");
                                Loan loanDetails = api.loanDetailsTest(customerNumber, vendorName);
                                try {
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Loan History Widget Header Name at POS(1) not same as mentioned in excel");
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Loan History Widget Header Name at POS(2) not same as mentioned in excel");
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Loan History Widget Header Name at POS(3) not same as mentioned in excel");
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Loan History Widget Header Name at POS(4) not same as mentioned in excel");
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Loan History Widget Header Name at POS(5) not same as mentioned in excel");
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Loan History Widget Header Name at POS(6) not same as mentioned in excel");
                                    softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Loan History Widget Header Name at POS(7) not same as mentioned in excel");
                                    LoanHistory loanHistory = loanDetails.getResult().getLoanHistory();
                                    ArrayList<HeaderList> headerList = loanDetails.getResult().getLoanHistory().getHeaderList();
                                    ArrayList<LoanRepaymentList> loanRepaymentList = loanHistory.getLoanRepaymentList();
                                    for (int j = 0; j < headerList.size(); j++) {
                                        softAssert.assertEquals(loanDetail.getLoanHistoryHeaderName(j + 1).toLowerCase().trim(), headerList.get(j).getHeader().toLowerCase().trim(), "Loam History widget Header name at POS(" + (j + 1) + ") not same as in com.airtel.cs.API Response");
                                    }
                                    int count = 0;
                                    if (loanRepaymentList.size() > 5) {
                                        count = 5;
                                    } else {
                                        count = loanRepaymentList.size();
                                    }
                                    for (int m = 0; m < count; m++) {
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 1).trim(), loanRepaymentList.get(m).getId(), "Loan Transaction id not same as com.airtel.cs.API response in Row" + (m + 1));
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 2).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getAmountCredited()), "Loan Amount Credited not same as com.airtel.cs.API response in Row" + (m + 1));
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 3).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getServiceCharge()), "Loan Service charge not same as com.airtel.cs.API response in Row" + (m + 1));
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 4).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getRecovered()), "Loan Recovered Amount not same as com.airtel.cs.API response in Row" + (m + 1));
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 5).trim().toLowerCase(), loanRepaymentList.get(m).getLoanChannel().toLowerCase().trim(), "Loan channel not same as com.airtel.cs.API response in Row" + (m + 1));
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 6).trim().toLowerCase(), loanRepaymentList.get(m).getLoanType().toLowerCase().trim(), "Loan Type not same as com.airtel.cs.API response in Row" + (m + 1));
                                        softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 7).trim(), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(loanRepaymentList.get(m).getDateCreated()), headerList.get(6).getDateFormat() + " " + headerList.get(6).getTimeFormat()), "Loan Date Created not same as com.airtel.cs.API response in Row" + (m + 1));
                                        /*
                                         * Loan Recovery Widget Opened
                                         * */
                                        try {
                                            int repaymentCount = loanRepaymentList.get(m).getLoanRepaymentTransaction().getRepaymentTransactionCount();
                                            System.out.println("COUNT:" + repaymentCount);
                                            if (repaymentCount > 0) {
                                                try {
                                                    loanDetail.clickTransactionId(m + 1);
                                                } catch (InterruptedException | TimeoutException e) {
                                                    e.printStackTrace();
                                                    softAssert.fail("Not Able to click Transaction id " + e.getMessage());
                                                }
                                                ArrayList<HeaderList> recoveryWidgetHeader = loanRepaymentList.get(i).getLoanRepaymentTransaction().getHeaderList();
                                                for (int k = 0; k < recoveryWidgetHeader.size(); k++) {
                                                    softAssert.assertEquals(loanDetail.getLoanRecoveryHeaderName(m + 1, k + 1).toLowerCase().trim(), recoveryWidgetHeader.get(k).getHeader().toLowerCase().trim(), "Loan Recovery widget header does not same as com.airtel.cs.API response at Pos(" + (m + 1) + ")");
                                                }
                                                ArrayList<LoanRepaymentDetailList> repaymentList = loanRepaymentList.get(m).getLoanRepaymentTransaction().getLoanRepaymentDetailList();
                                                for (int l = 0; l < repaymentCount; l++) {
                                                    softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 1).trim(), repaymentList.get(l).getTransactionId(), "Loan Recovery Transaction id column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                    softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 2).trim(), UtilsMethods.valueRoundOff(repaymentList.get(l).getAmountRecovered()), "Loan Recovery Amount Recovered column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                    softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 3).toLowerCase().trim(), repaymentList.get(l).getRecoveryMethod().toLowerCase().trim(), "Loan Recovery method column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                    softAssert.assertEquals(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(repaymentList.get(l).getDateRecovered()), recoveryWidgetHeader.get(3).getDateFormat() + " " + recoveryWidgetHeader.get(3).getTimeFormat()), "Loan Recovery Date Recovered column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                }

                                            }
                                        } catch (org.openqa.selenium.NoSuchElementException | NullPointerException | TimeoutException e) {
                                            softAssert.fail("Loan Recovery Widget does not display correctly: " + e.fillInStackTrace());
                                        }

                                    }
                                } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
                                    softAssert.fail("Loan History widget can not be validate due to following error: " + e.getMessage());
                                }
                                loanDetail.clickCloseTab();
                            } catch (AssertionError e) {
                                loanDetail.clickCloseTab();
                                softAssert.fail("Loan History Widget does not display Properly: " + e.getMessage());
                            }
                        } catch (TimeoutException | ElementClickInterceptedException e) {
                            softAssert.fail("Vendor Name does not clickable" + e.getMessage());
                        }
                    }
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Loan History widget can not be validate due to following error: " + e.fillInStackTrace());
            }
        } else {
            commonLib.warning("No Vendor Found in Loan Service Widget");
        }
        softAssert.assertAll();
    }
}
