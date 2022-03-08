package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.loandetails.Loan;
import com.airtel.cs.model.response.loandetails.LoanDetailList;
import com.airtel.cs.model.response.loandetails.LoanHistory;
import com.airtel.cs.model.response.loandetails.LoanRepaymentList;
import com.airtel.cs.model.response.loansummary.LoanSummaryResponse;
import com.airtel.cs.model.response.loansummary.Summary;
import com.airtel.cs.model.response.vendors.HeaderList;
import com.airtel.cs.model.response.vendors.VendorNames;
import com.airtel.cs.model.response.vendors.Vendors;
import com.airtel.cs.pagerepository.pagemethods.LoanDetail;
import io.restassured.http.Headers;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class LoanWidgetTest extends Driver {

    public static final String RUN_LOAN_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_LOAN_WIDGET_TESTCASE);
     String customerNumber;
    RequestSource api = new RequestSource();
    List<Vendors> vendors;
    LoanSummaryResponse summary;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkLoanWidgetFlag() {
        if (!StringUtils.equals(RUN_LOAN_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Loan Widget is NOT Enabled for this Opco= " + OPCO);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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
     * This method is used to validate UM permission for loan serviceCurrentBalanceWidgetTest
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void isUserHasLoanWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that user has loan service permission is enabled in UM, Check User has permission to view loan service Widget Permission", "description");
            String loanServicePermission = constants.getValue(PermissionConstants.LOAN_SERVICE_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getLoanWidget().isLoanServiceWidgetVisible(), UtilsMethods.isUserHasPermission(loanServicePermission), "Loan service Widget displayed correctly as per user permission", "Loan service Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasLoanWidgetPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate Loan Service Widget Layout
     *
     * @param data
     */
    @DataProviders.Table(name = "Loan Services")
    @Test(priority = 3, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanWidgetLayout(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Loan Service Widget layout", "description");
            VendorNames vendorNames = api.vendorsNamesTest();
            /*
             * Checking API Giving valid Response
             */
            List<HeaderList> headers = vendorNames.getResult().getHeaderList();
            for (int i = 0; i < data.getHeaderName().size(); i++) {
                String headerName = i == 1 ? headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")" : headers.get(i).getHeader();
                assertCheck.append(actions.matchUiAndAPIResponse(headerName, data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
            }

            /*
             * Checking header displayed on UI
             */
            assertCheck.append(actions.assertEqualStringType(headers.get(0).getHeader().toLowerCase().trim(), pages.getLoanWidget().getVendor().toLowerCase().trim(), "Header is same as expected on UI", "Header is  not same as expected on UI"));
            assertCheck.append(actions.assertEqualStringType(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", pages.getLoanWidget().getLoanAmount().toLowerCase().trim() + " (" + pages.getLoanWidget().getCurrencyType().toLowerCase().trim() + ")", "Header is same as expected on UI", "Header is not same as expected on UI"));
            assertCheck.append(actions.assertEqualStringType(headers.get(2).getHeader().toLowerCase().trim(), pages.getLoanWidget().getCreditedOn().toLowerCase().trim(), "Header is same as expected on UI", "Header is not same as expected on UI"));
            assertCheck.append(actions.assertEqualStringType(headers.get(3).getHeader().toLowerCase().trim(), pages.getLoanWidget().getCurrentOutstanding().toLowerCase().trim(), "Header is same as expected on UI", "Header is not same as expected on UI"));
            assertCheck.append(actions.assertEqualStringType(headers.get(4).getHeader().toLowerCase().trim(), pages.getLoanWidget().getDueDate().toLowerCase().trim(), "Header is same as expected on UI", "Header is not same as expected on UI"));
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
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateLoanWidget() throws InterruptedException, IOException {
        try {
            selUtils.addTestcaseDescription("Validating Loan Service Widget:" + customerNumber, "description");
            if (pages.getLoanWidget().getSize() > 0) {
                for (int i = 1; i <= pages.getLoanWidget().getSize(); i++) {
                    summary = api.loanSummaryTest(customerNumber, pages.getLoanWidget().getVendorName(i).trim());
                    if (!summary.getStatus().equalsIgnoreCase("SUCCESS") && summary.getResult().getMessage().equalsIgnoreCase("Customer has no transactions")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getLoanWidget().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                    } else {
                        for (int j = 0; j < summary.getResult().getSummary().size(); j++) {
                            assertCheck.append(actions.assertNotEqualStringType(pages.getLoanWidget().getLoanAmount(i), summary.getResult().getSummary().get(j).getLoanAmount(), "Loan amount are same as API Response", "Loan amount not same as API Response"));
                            assertCheck.append(actions.assertNotEqualStringType(pages.getLoanWidget().getOutstandingAmount(i), summary.getResult().getSummary().get(j).getCurrentOutstanding().getValue(), "Current Outstanding amount is same as API Response", "Current Outstanding amount not same as API Response"));

                        }
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
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateLoanDetailWidget(HeaderDataBean data) throws InterruptedException, IOException {
        try {
            selUtils.addTestcaseDescription("Validate Loan Detail Widget", "description");
            LoanDetail loanDetail = null;
            List<String> vendorNameList = pages.getLoanWidget().getVendorNamesList();
            if (pages.getLoanWidget().getSize() > 0) {
                for (int i = 1; i <= pages.getLoanWidget().getSize(); i++) {
                    String vendorName = pages.getLoanWidget().getVendorName(i).trim();
                    summary = api.loanSummaryTest(customerNumber, vendorName);
                    if (summary.getStatusCode().equalsIgnoreCase("200")) {
                        loanDetail = pages.getLoanWidget().clickVendorName(i);
                        loanDetail.waitTillLoaderGetsRemoved();
                        try {
                            Assert.assertTrue(loanDetail.isLoanDetailWidgetDisplay(), "Loan Detail Widget  displayed");
                            Loan loanDetails = api.loanDetailsTest(customerNumber, vendorName);
                            /*
                             * Validating Header name displayed on UI with Config present in Excel
                             * */
                            for (int startIndex = 0; startIndex < data.getHeaderName().size(); startIndex++) {
                                assertCheck.append(actions.matchUiAndAPIResponse(loanDetail.getHeaderName(startIndex+1).toLowerCase().trim() , data.getHeaderName().get(startIndex).toLowerCase().trim(), "Header Name for Row " + (startIndex + 1) + " is as expected", "Header Name for Row " + (startIndex + 1) + " is not as expected"));
                            }
                            /*
                             * Validating Header name & value displayed on UI with com.airtel.cs.API Response
                             * */
                            List<HeaderList> headerList = loanDetails.getResult().getLoanDetails().getHeaderList();
                            LoanDetailList loanDetailValue = loanDetails.getResult().getLoanDetails().getLoanDetailList().get(0);
                            for (int j = 0; j < headerList.size(); j++) {
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getHeaderName(j + 1).toLowerCase().trim(), headerList.get(j).getHeader().toLowerCase().trim(), "Loan Detail Widget Header name at POS(" + (j + 1) + ") is same as in com.airtel.cs.API Response", "Loan Detail Widget Header name at POS(" + (j + 1) + ") not same as in com.airtel.cs.API Response"));
                            }
                            assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToHeader(1).trim(), loanDetailValue.getLoanId(), "Loan Id  Value is same as com.airtel.cs.API Response", "Loan Id Value not same as com.airtel.cs.API Response"));
                            if(loanDetailValue.getTotalLoanEligibility()==null)
                            assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToHeader(2).trim(), "-", "Total Loan Eligibility Value is same as API Response", "Total Loan Eligibility Value not same as API Response"));
                            assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToHeader(3).trim(), String.valueOf(loanDetailValue.getCountOfEvents()), "Number of Loan Taken Value is same as API Response", "Number of Loan Taken Value not same as API Response"));
                            assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToHeader(4).trim(), UtilsMethods.valueRoundOff(Double.parseDouble(loanDetailValue.getTotalLoanAmount())), "Total Loan amount Value is same as API Response", "Total Loan amount Value not same as API Response"));
                            assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToHeader(5).trim(), UtilsMethods.valueRoundOff(Double.parseDouble(loanDetailValue.getLoanPaid())), "Total Loan Paid value is same as API Response", "Total Loan Paid value not same as API Response"));
                            assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToHeader(6).trim(), UtilsMethods.valueRoundOff(Double.parseDouble(loanDetailValue.getRemainingBalance())), "Total Remaining Balance value is same as API Response", "Total Remaining Balance value not same as API Response"));
                        } catch (NoSuchElementException | TimeoutException | AssertionError e) {
                            commonLib.fail("Loan detail Widget does not open properly: " + e.fillInStackTrace(), true);
                            loanDetail.clickCloseTab();
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
    @Test(priority = 6, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanHistoryWidget(HeaderDataBean data) throws InterruptedException, IOException {
        try {
            selUtils.addTestcaseDescription("Validate Loan History Widget", "description");
            LoanDetail loanDetail = null;
            pages.getLoanWidget().waitTillLoaderGetsRemoved();
            if (pages.getLoanWidget().getSize() > 0) {
                for (int i = 1; i <= pages.getLoanWidget().getSize(); i++) {
                    String vendorName = pages.getLoanWidget().getVendorName(i).trim();
                   summary = api.loanSummaryTest(customerNumber, vendorName);
                    if (summary.getStatusCode().equalsIgnoreCase("200")) {
                        loanDetail = pages.getLoanWidget().clickVendorName(i);
                        loanDetail.waitTillLoaderGetsRemoved();
                        try {
                            Assert.assertTrue(loanDetail.isLoanHistoryWidgetDisplay(), "Loan History Widget not display");
                            Loan loanDetails = api.loanDetailsTest(customerNumber, vendorName);
                            pages.getLoanDetailPage().validateLoanHistoryHeaderName(data);
                            LoanHistory loanHistory = loanDetails.getResult().getLoanHistory();
                            List<HeaderList> headerList = loanDetails.getResult().getLoanHistory().getHeaderList();
                            List<LoanRepaymentList> loanRepaymentList = loanHistory.getLoanRepaymentList();
                            for (int j = 0; j < headerList.size(); j++) {
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getLoanHistoryHeaderName(j + 1).toLowerCase().trim(), headerList.get(j).getHeader().toLowerCase().trim(), "Loam History widget Header name at POS(" + (j + 1) + ") is same as in com.airtel.cs.API Response", "Loam History widget Header name at POS(" + (j + 1) + ") not same as in com.airtel.cs.API Response"));
                            }
                            int count = Math.min(loanRepaymentList.size(),5);
                            for (int m = 0; m < count; m++) {
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 1).trim(), loanRepaymentList.get(m).getId(), "Loan Transaction id is same as API response in Row" + (m + 1), "Loan Transaction id not same as API response in Row" + (m + 1)));
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 2).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getAmountCredited()), "Loan Amount Credited is same as API response in Row" + (m + 1), "Loan Amount Credited not same as API response in Row" + (m + 1)));
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 3).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getServiceCharge()), "Loan Service charge is same as API response in Row" + (m + 1), "Loan Service charge not same as API response in Row" + (m + 1)));
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 4).trim(), UtilsMethods.valueRoundOff(loanRepaymentList.get(m).getRecovered()), "Loan Recovered Amount is same as API response in Row" + (m + 1), "Loan Recovered Amount not same as API response in Row" + (m + 1)));
                                if(loanRepaymentList.get(m).getLoanChannel()==null)
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 5).trim().toLowerCase(), "-", "Loan channel is same as API response in Row" + (m + 1), "Loan channel not same as API response in Row" + (m + 1)));
                                if(loanRepaymentList.get(m).getLoanType()==null)
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 6).trim().toLowerCase(), "-", "Loan Type is same as API response in Row" + (m + 1), "Loan Type not same as API response in Row" + (m + 1)));
                                assertCheck.append(actions.assertEqualStringType(loanDetail.getValueCorrespondingToLoanHistoryHeader(m + 1, 7).trim(), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(loanRepaymentList.get(m).getDateCreated()), headerList.get(6).getDateFormat() + " " + headerList.get(6).getTimeFormat()), "Loan Date Created is same as API response in Row" + (m + 1), "Loan Date Created not same as API response in Row" + (m + 1)));
                                /*
                                 * Loan Recovery Widget Opened
                                 * */
                                int repaymentCount = loanRepaymentList.get(m).getLoanRepaymentTransaction().getRepaymentTransactionCount();
                                pages.getLoanDetailPage().validateLoanRecoveryWidget(repaymentCount,loanRepaymentList,m,i);
                            }
                            loanDetail.clickCloseTab();
                        } catch (AssertionError e) {
                            loanDetail.clickCloseTab();
                            commonLib.fail("Loan History Widget does not display Properly:" + e.fillInStackTrace(), true);
                        }
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateLoanHistoryWidget" + e.fillInStackTrace(), true);
        }
    }
}
