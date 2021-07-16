package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.LoanDetail;
import com.airtel.cs.pojo.response.loandetails.*;
import com.airtel.cs.pojo.response.loansummary.Summary;
import com.airtel.cs.pojo.response.vendors.HeaderList;
import com.airtel.cs.pojo.response.vendors.VendorNames;
import com.airtel.cs.pojo.response.vendors.Vendors;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LoanWidgetTest extends Driver {

    static String customerNumber;
    RequestSource api = new RequestSource();
    ArrayList<Vendors> vendors;
    public static final String RUN_LOAN_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_LOAN_WIDGET_TESTCASE);
    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkLoanWidgetFlag() {
        if (!StringUtils.equals(RUN_LOAN_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Loan Widget is NOT Enabled for this Opco= " + OPCO);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate Loan Service Widget Layout
     *
     * @param data
     */
    @DataProviders.Table(name = "Loan Services")
    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanWidgetLayout(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Loan Service Widget layout", "description");
            VendorNames vendorNames = api.vendorsNamesTest();
            vendors = vendorNames.getResult().getVendors();
            for (Vendors v : vendors) {
                commonLib.info("Loan Services: " + v.getVendorName());
            }
            /*
             * Checking com.airtel.cs.API Giving valid Response
             * */
            ArrayList<HeaderList> headers = vendorNames.getResult().getHeaderList();
            assertCheck.append(actions.assertNotEqualStringType(headers.get(0).getHeader().toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header are same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)", "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", data.getRow2().toLowerCase().trim(), "Header are same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)", "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(2).getHeader().toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header are same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)", "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(3).getHeader().toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header are same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)", "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(4).getHeader().toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header are same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)", "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)"));

            /*
             * Checking header displayed on UI
             * */
            assertCheck.append(actions.assertNotEqualStringType(headers.get(0).getHeader().toLowerCase().trim(), pages.getLoanWidget().getVendor().toLowerCase().trim(), "Header are same as expected on UI(com.airtel.cs.API Response Assert with UI)", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", pages.getLoanWidget().getLoanAmount().toLowerCase().trim() + " (" + pages.getLoanWidget().getCurrencyType().toLowerCase().trim() + ")", "Header are same as expected on UI(com.airtel.cs.API Response Assert with UI)", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(2).getHeader().toLowerCase().trim(), pages.getLoanWidget().getCreatedON().toLowerCase().trim(), "Header are same as expected on UI(com.airtel.cs.API Response Assert with UI)", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(3).getHeader().toLowerCase().trim(), pages.getLoanWidget().getCurrentOutstanding().toLowerCase().trim(), "Header are same as expected on UI(com.airtel.cs.API Response Assert with UI)", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)"));
            assertCheck.append(actions.assertNotEqualStringType(headers.get(4).getHeader().toLowerCase().trim(), pages.getLoanWidget().getDueDate().toLowerCase().trim(), "Header are same as expected on UI(com.airtel.cs.API Response Assert with UI)", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateLoanWidgetLayout" + e.fillInStackTrace(), true);
        }

    }


    /**
     * This method is used to validate Loan Service Widget
     *
     * @throws InterruptedException
     * @throws IOException
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateLoanWidget() throws InterruptedException, IOException {
        try {
            selUtils.addTestcaseDescription("Validating Loan Service Widget:" + customerNumber, "description");
            if (pages.getLoanWidget().getSize() > 0) {
                for (int i = 1; i <= pages.getLoanWidget().getSize(); i++) {
                    Summary summary = api.loanSummaryTest(customerNumber, pages.getLoanWidget().getVendorName(i).trim());
                    if (!summary.getStatusCode().equalsIgnoreCase("200") | summary.getStatus().equalsIgnoreCase("Failure")) {
                        assertCheck.append(actions.assertEqualBoolean(pages.getLoanWidget().checkMessageDisplay(summary.getMessage()), true, summary.getMessage() + " :Message does not display"));
                    } else {
                        assertCheck.append(actions.assertNotEqualStringType(pages.getLoanWidget().getLoanAmount(i), UtilsMethods.valueRoundOff(summary.getResult().getLoanAmount()), "Loan amount are same as com.airtel.cs.API Response", "Loan amount not same as com.airtel.cs.API Response"));
                        assertCheck.append(actions.assertNotEqualStringType(pages.getLoanWidget().getOutstandingAmount(i), UtilsMethods.valueRoundOff(summary.getResult().getCurrentOutstanding().getValue()), "Current Outstanding amount is same as com.airtel.cs.API Response", "Current Outstanding amount not same as com.airtel.cs.API Response"));
                    }
                }
            } else {
                commonLib.warning("No Vendor Found in Loan Service Widget");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateLoanWidget" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Validate Loan Detail Widget
     *
     * @param data
     * @throws InterruptedException
     * @throws IOException
     */
    @DataProviders.Table(name = "Loan Details")
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateLoanDetailWidget(HeaderDataBean data) throws InterruptedException, IOException {
        try {
            selUtils.addTestcaseDescription("Validate Loan Detail Widget", "description");
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
                                    assertCheck.append(actions.assertNotEqualStringType(loanDetail.getHeaderName(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header name is same as defined in excel at pos(1)", "Header name not same as defined in excel at pos(1)"));
                                    assertCheck.append(actions.assertNotEqualStringType(loanDetail.getHeaderName(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header name is same as defined in excel pos(2)", "Header name not same as defined in excel pos(2)"));
                                    assertCheck.append(actions.assertNotEqualStringType(loanDetail.getHeaderName(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header name is same as defined in excel pos(3)", "Header name not same as defined in excel pos(3)"));
                                    assertCheck.append(actions.assertNotEqualStringType(loanDetail.getHeaderName(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header name is same as defined in excel pos(4)", "Header name not same as defined in excel pos(4)"));
                                    assertCheck.append(actions.assertNotEqualStringType(loanDetail.getHeaderName(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header name is same as defined in excel pos(5)", "Header name not same as defined in excel pos(5)"));
                                } catch (NoSuchElementException | TimeoutException e) {
                                    commonLib.fail("Header name of detail widget does not display properly: " + e.fillInStackTrace(), true);
                                }
                                /*
                                 * Validating Header name & value displayed on UI with com.airtel.cs.API Response
                                 * */
                                ArrayList<HeaderList> headerList = loanDetails.getResult().getLoanDetails().getHeaderList();
                                LoanDetailList loanDetailValue = loanDetails.getResult().getLoanDetails().getLoanDetailList().get(0);
                                for (int j = 0; j < headerList.size(); j++) {
                                    assertCheck.append(actions.assertNotEqualStringType(loanDetail.getHeaderName(i + 1).toLowerCase().trim(), headerList.get(i).getHeader().toLowerCase().trim(), "Loan Detail Widget Header name at POS(" + (i + 1) + ") is same as in com.airtel.cs.API Response", "Loan Detail Widget Header name at POS(" + (i + 1) + ") not same as in com.airtel.cs.API Response"));


                                }
                                assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToHeader(1).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getTotalLoanEligibility()), "Total Loan Eligibility Value is same as com.airtel.cs.API Response", "Total Loan Eligibility Value not same as com.airtel.cs.API Response"));
                                assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToHeader(2).trim(), String.valueOf(loanDetailValue.getCountOfEvents()), "Number of Loan Taken Value is same as com.airtel.cs.API Response", "Number of Loan Taken Value not same as com.airtel.cs.API Response"));
                                assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToHeader(3).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getTotalLoanAmount()), "Total Loan amount Value is same as com.airtel.cs.API Response", "Total Loan amount Value not same as com.airtel.cs.API Response"));
                                assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToHeader(4).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getLoanPaid()), "Total Loan Paid value is same as com.airtel.cs.API Response", "Total Loan Paid value not same as com.airtel.cs.API Response"));
                                assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToHeader(5).trim(), UtilsMethods.valueRoundOff(loanDetailValue.getRemainingBalance()), "Total Current Outstanding value is same as com.airtel.cs.API Response", "Total Current Outstanding value not same as com.airtel.cs.API Response"));
                            } catch (NoSuchElementException | TimeoutException | AssertionError e) {
                                commonLib.fail("Loan detail Widget does not open properly: " + e.fillInStackTrace(), true);
                                loanDetail.clickCloseTab();
                            }
                        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                            commonLib.fail("Vendor Name not clickable or Present on UI" + e.fillInStackTrace(), true);
                        }
                    }
                }
                assert loanDetail != null;
                loanDetail.clickCloseTab();
            } else {
                commonLib.warning("No Vendor Found in Loan Service Widget");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateLoanDetailWidget" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to Validate Loan History Widget
     *
     * @param data
     * @throws InterruptedException
     * @throws IOException
     */
    @DataProviders.Table(name = "Loan History")
    @Test(priority = 5, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanHistoryWidget(HeaderDataBean data) throws InterruptedException, IOException {
        try {
            selUtils.addTestcaseDescription("Validate Loan History Widget", "description");
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
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Loan History Widget Header Name at POS(1) is same as mentioned in excel", "Loan History Widget Header Name at POS(1) not same as mentioned in excel"));
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Loan History Widget Header Name at POS(2) is same as mentioned in excel", "Loan History Widget Header Name at POS(2) not same as mentioned in excel"));
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Loan History Widget Header Name at POS(3) is same as mentioned in excel", "Loan History Widget Header Name at POS(3) not same as mentioned in excel"));
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Loan History Widget Header Name at POS(4) is same as mentioned in excel", "Loan History Widget Header Name at POS(4) not same as mentioned in excel"));
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Loan History Widget Header Name at POS(5) is same as mentioned in excel", "Loan History Widget Header Name at POS(5) not same as mentioned in excel"));
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Loan History Widget Header Name at POS(6) is same as mentioned in excel", "Loan History Widget Header Name at POS(6) not same as mentioned in excel"));
                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Loan History Widget Header Name at POS(7) is same as mentioned in excel", "Loan History Widget Header Name at POS(7) not same as mentioned in excel"));
                                        LoanHistory loanHistory = loanDetails.getResult().getLoanHistory();
                                        ArrayList<HeaderList> headerList = loanDetails.getResult().getLoanHistory().getHeaderList();
                                        ArrayList<LoanRepaymentList> loanRepaymentList = loanHistory.getLoanRepaymentList();
                                        for (int j = 0; j < headerList.size(); j++) {

                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanHistoryHeaderName(j + 1).toLowerCase().trim(), headerList.get(j).getHeader().toLowerCase().trim(), "Loam History widget Header name at POS(" + (j + 1) + ") is same as in com.airtel.cs.API Response", "Loam History widget Header name at POS(" + (j + 1) + ") not same as in com.airtel.cs.API Response"));
                                        }
                                        int count = 0;
                                        if (loanRepaymentList.size() > 5) {
                                            count = 5;
                                        } else {
                                            count = loanRepaymentList.size();
                                        }
                                        for (int m = 0; m < count; m++) {
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 1).trim(), loanRepaymentList.get(m).getId(), "Loan Transaction id is same as com.airtel.cs.API response in Row" + (m + 1), "Loan Transaction id not same as com.airtel.cs.API response in Row" + (m + 1)));
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 2).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getAmountCredited()), "Loan Amount Credited is same as com.airtel.cs.API response in Row" + (m + 1), "Loan Amount Credited not same as com.airtel.cs.API response in Row" + (m + 1)));
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 3).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getServiceCharge()), "Loan Service charge is same as com.airtel.cs.API response in Row" + (m + 1), "Loan Service charge not same as com.airtel.cs.API response in Row" + (m + 1)));
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 4).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getRecovered()), "Loan Recovered Amount is same as com.airtel.cs.API response in Row" + (m + 1), "Loan Recovered Amount not same as com.airtel.cs.API response in Row" + (m + 1)));
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 5).trim().toLowerCase(), loanRepaymentList.get(m).getLoanChannel().toLowerCase().trim(), "Loan channel is same as com.airtel.cs.API response in Row" + (m + 1), "Loan channel not same as com.airtel.cs.API response in Row" + (m + 1)));
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 6).trim().toLowerCase(), loanRepaymentList.get(m).getLoanType().toLowerCase().trim(), "Loan Type is same as com.airtel.cs.API response in Row" + (m + 1), "Loan Type not same as com.airtel.cs.API response in Row" + (m + 1)));
                                            assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 7).trim(), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(loanRepaymentList.get(m).getDateCreated()), headerList.get(6).getDateFormat() + " " + headerList.get(6).getTimeFormat()), "Loan Date Created is same as com.airtel.cs.API response in Row" + (m + 1), "Loan Date Created not same as com.airtel.cs.API response in Row" + (m + 1)));

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
                                                        commonLib.fail("Not Able to click Transaction id " + e.fillInStackTrace(), true);
                                                    }
                                                    ArrayList<HeaderList> recoveryWidgetHeader = loanRepaymentList.get(i).getLoanRepaymentTransaction().getHeaderList();
                                                    for (int k = 0; k < recoveryWidgetHeader.size(); k++) {
                                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getLoanRecoveryHeaderName(m + 1, k + 1).toLowerCase().trim(), recoveryWidgetHeader.get(k).getHeader().toLowerCase().trim(), "Loan Recovery widget header is same as com.airtel.cs.API response at Pos(" + (m + 1) + ")", "Loan Recovery widget header does not same as com.airtel.cs.API response at Pos(" + (m + 1) + ")"));
                                                    }
                                                    ArrayList<LoanRepaymentDetailList> repaymentList = loanRepaymentList.get(m).getLoanRepaymentTransaction().getLoanRepaymentDetailList();
                                                    for (int l = 0; l < repaymentCount; l++) {
                                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 1).trim(), repaymentList.get(l).getTransactionId(), "Loan Recovery Transaction id column value is same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")", "Loan Recovery Transaction id column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")"));
                                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 2).trim(), UtilsMethods.valueRoundOff(repaymentList.get(l).getAmountRecovered()), "Loan Recovery Amount Recovered column value is same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")", "Loan Recovery Amount Recovered column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")"));
                                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 3).toLowerCase().trim(), repaymentList.get(l).getRecoveryMethod().toLowerCase().trim(), "Loan Recovery method column value is same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")", "Loan Recovery method column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")"));
                                                        assertCheck.append(actions.assertNotEqualStringType(loanDetail.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(repaymentList.get(l).getDateRecovered()), recoveryWidgetHeader.get(3).getDateFormat() + " " + recoveryWidgetHeader.get(3).getTimeFormat()), "Loan Recovery Date Recovered column value is same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")", "Loan Recovery Date Recovered column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")"));
                                                    }

                                                }
                                            } catch (org.openqa.selenium.NoSuchElementException | NullPointerException | TimeoutException e) {
                                                commonLib.fail("Loan Recovery Widget does not display correctly:" + e.fillInStackTrace(), true);
                                            }

                                        }
                                    } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
                                        commonLib.fail("Loan History widget can not be validate due to following error:" + e.fillInStackTrace(), true);
                                    }
                                    loanDetail.clickCloseTab();
                                } catch (AssertionError e) {
                                    loanDetail.clickCloseTab();
                                    commonLib.fail("Loan History Widget does not display Properly:" + e.fillInStackTrace(), true);
                                }
                            } catch (TimeoutException | ElementClickInterceptedException e) {
                                commonLib.fail("Vendor Name does not clickable" + e.fillInStackTrace(), true);
                            }
                        }
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Loan History widget can not be validate due to following error:" + e.fillInStackTrace(), true);
                }
            } else {
                commonLib.warning("No Vendor Found in Loan Service Widget");
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateLoanHistoryWidget" + e.fillInStackTrace(), true);
        }
    }
}
