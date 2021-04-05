package tests.frontendagent;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.pojo.LoanDetails.*;
import com.airtel.cs.pojo.LoanSummary.Summary;
import com.airtel.cs.pojo.Vendors.HeaderList;
import com.airtel.cs.pojo.Vendors.VendorNames;
import com.airtel.cs.pojo.Vendors.Vendors;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pagemethods.LoanDetailPOM;
import com.airtel.cs.pagerepository.pagemethods.LoanWidgetPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.CustomerProfilePage;
import com.airtel.cs.pagerepository.pagemethods.CustomerInteractionsSearchPOM;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LoanWidgetTest extends BaseTest {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();
    ArrayList<Vendors> vendors;

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        CustomerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerNumber = Data.getCustomerNumber();
        CustomerProfilePage customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Loan Services")
    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", description = "Validating Loan Widget layout", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanWidgetLayout(HeaderDataBean Data) throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validating Loan Service Widget layout", "Validating Loan Service Widget");
        SoftAssert softAssert = new SoftAssert();
        VendorNames vendorNames = api.vendorsNamesTest();
        LoanWidgetPOM loanWidget = new LoanWidgetPOM(driver);
        vendors = vendorNames.getResult().getVendors();
        for (Vendors v : vendors) {
            UtilsMethods.printInfoLog("Loan Services: " + v.getVendorName());
        }
        /*
         * Checking com.airtel.cs.API Giving valid Response
         * */
        ArrayList<HeaderList> headers = vendorNames.getResult().getHeaderList();
        softAssert.assertEquals(headers.get(0).getHeader().toLowerCase().trim(), Data.getRow1().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", Data.getRow2().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(2).getHeader().toLowerCase().trim(), Data.getRow3().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(3).getHeader().toLowerCase().trim(), Data.getRow4().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(4).getHeader().toLowerCase().trim(), Data.getRow5().toLowerCase().trim(), "Header not same as expected in com.airtel.cs.API(com.airtel.cs.API Response Assert with Excel)");
        /*
         * Checking header displayed on UI
         * */
        softAssert.assertEquals(headers.get(0).getHeader().toLowerCase().trim(), loanWidget.getVendor().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(1).getHeader().toLowerCase().trim() + " (" + headers.get(1).getSubHeader().toLowerCase().trim() + ")", loanWidget.getLoanAmount().toLowerCase().trim() + " (" + loanWidget.getCurrencyType().toLowerCase().trim() + ")", "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(2).getHeader().toLowerCase().trim(), loanWidget.getCreatedON().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(3).getHeader().toLowerCase().trim(), loanWidget.getCurrentOutstanding().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertEquals(headers.get(4).getHeader().toLowerCase().trim(), loanWidget.getDueDate().toLowerCase().trim(), "Header not same as expected on UI(com.airtel.cs.API Response Assert with UI)");
        softAssert.assertAll();
    }


    @Test(priority = 3, description = "Validate Loan Widget",dependsOnMethods = "openCustomerInteraction")
    public void validateLoanWidget() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validating Loan Service Widget:" + customerNumber, "Validating Loan Service Widget");
        SoftAssert softAssert = new SoftAssert();
        LoanWidgetPOM loanWidget = new LoanWidgetPOM(driver);
        if(loanWidget.getSize()>0) {
            for (int i = 1; i <= loanWidget.getSize(); i++) {
                Summary summary = api.loanSummaryTest(customerNumber, loanWidget.getVendorName(i).trim());
                if (!summary.getStatusCode().equalsIgnoreCase("200") | summary.getStatus().equalsIgnoreCase("Failure")) {
                    softAssert.assertTrue(loanWidget.checkMessageDisplay(summary.getMessage()), summary.getMessage() + " :Message does not display");
                } else {
                    softAssert.assertEquals(loanWidget.getLoanAmount(i), UtilsMethods.ValueRoundOff(summary.getResult().getLoanAmount()), "Loan amount not same as com.airtel.cs.API Response");
                    softAssert.assertEquals(loanWidget.getOutstandingAmount(i), UtilsMethods.ValueRoundOff(summary.getResult().getCurrentOutstanding().getValue()), "Current Outstanding amount not same as com.airtel.cs.API Response");
                    /*
                     * Due Date and Created on assertion pending as com.airtel.cs.API not working as expected
                     * */
                }
            }
        }else {
            UtilsMethods.printWarningLog("No Vendor Found in Loan Service Widget");
        }
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Loan Details")
    @Test(priority = 4, description = "Validate Loan Detail Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class,dependsOnMethods = "openCustomerInteraction")
    public void validateLoanDetailWidget(HeaderDataBean data) throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Loan Detail Widget", "Validate Loan Detail Widget");
        SoftAssert softAssert = new SoftAssert();
        LoanWidgetPOM loanWidget = new LoanWidgetPOM(driver);
        LoanDetailPOM loanDetailPOM = null;
        List<String> vendorNameList = loanWidget.getVendorNamesList();
        if(vendorNameList.size()>0) {
            for (int i = 1; i <= vendorNameList.size(); i++) {
                String vendorName = vendorNameList.get(i - 1).trim();
                Summary summary = api.loanSummaryTest(customerNumber, vendorName);
                if (summary.getStatusCode().equalsIgnoreCase("200")) {
                    try {
                        loanDetailPOM = loanWidget.clickVendorName(i);
                        loanDetailPOM.waitTillLoaderGetsRemoved();
                        try {
                            Assert.assertTrue(loanDetailPOM.IsLoanDetailWidgetDisplay(), "Loan Detail Widget not display");
                            Loan loanDetails = api.loanDetailsTest(customerNumber, vendorName);
                            /*
                             * Validating Header name displayed on UI with Config present in Excel
                             * */
                            try {
                                softAssert.assertEquals(loanDetailPOM.getHeaderName(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header name not same as defined in excel at pos(1)");
                                softAssert.assertEquals(loanDetailPOM.getHeaderName(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header name not same as defined in excel pos(2)");
                                softAssert.assertEquals(loanDetailPOM.getHeaderName(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header name not same as defined in excel pos(3)");
                                softAssert.assertEquals(loanDetailPOM.getHeaderName(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header name not same as defined in excel pos(4)");
                                softAssert.assertEquals(loanDetailPOM.getHeaderName(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header name not same as defined in excel pos(5)");
                            } catch (NoSuchElementException | TimeoutException e) {
                                softAssert.fail("Header name of detail widget does not display properly: " + e.fillInStackTrace());
                            }
                            /*
                             * Validating Header name & value displayed on UI with com.airtel.cs.API Response
                             * */
                            ArrayList<HeaderList> headerList = loanDetails.getResult().getLoanDetails().getHeaderList();
                            LoanDetailList loanDetailValue = loanDetails.getResult().getLoanDetails().getLoanDetailList().get(0);
                            for (int j = 0; j < headerList.size(); j++) {
                                softAssert.assertEquals(loanDetailPOM.getHeaderName(i + 1).toLowerCase().trim(), headerList.get(i).getHeader().toLowerCase().trim(), "Loan Detail Widget Header name at POS(" + (i + 1) + ") not same as in com.airtel.cs.API Response");
                            }

                            softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToHeader(1).trim(), UtilsMethods.ValueRoundOff(loanDetailValue.getTotalLoanEligibility()), "Total Loan Eligibility Value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToHeader(2).trim(), String.valueOf(loanDetailValue.getCountOfEvents()), "Number of Loan Taken Value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToHeader(3).trim(), UtilsMethods.ValueRoundOff(loanDetailValue.getTotalLoanAmount()), "Total Loan amount Value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToHeader(4).trim(), UtilsMethods.ValueRoundOff(loanDetailValue.getLoanPaid()), "Total Loan Paid value not same as com.airtel.cs.API Response");
                            softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToHeader(5).trim(), UtilsMethods.ValueRoundOff(loanDetailValue.getRemainingBalance()), "Total Current Outstanding value not same as com.airtel.cs.API Response");
                        } catch (NoSuchElementException | TimeoutException | AssertionError e) {
                            softAssert.fail("Loan detail Widget does not open properly: " + e.fillInStackTrace());
                            loanDetailPOM.clickCloseTab();
                        }
                    } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                        softAssert.fail("Vendor Name not clickable or Present on UI" + e.fillInStackTrace());
                    }
                }
            }
            loanDetailPOM.clickCloseTab();
        }else {
            UtilsMethods.printWarningLog("No Vendor Found in Loan Service Widget");
        }
        softAssert.assertAll();
    }


    @DataProviders.Table(name = "Loan History")
    @Test(priority = 5,dependsOnMethods = "openCustomerInteraction", description = "Validate Loan History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanHistoryWidget(HeaderDataBean data) throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Loan History Widget", "Validate Loan History Widget");
        SoftAssert softAssert = new SoftAssert();
        LoanWidgetPOM loanWidget = new LoanWidgetPOM(driver);
        LoanDetailPOM loanDetailPOM = null;
        loanWidget.waitTillLoaderGetsRemoved();
        if(loanWidget.getSize()>0){
        try {
            for (int i = 1; i <= loanWidget.getSize(); i++) {
                String vendorName = loanWidget.getVendorName(i).trim();
                Summary summary = api.loanSummaryTest(customerNumber, vendorName);
                if (summary.getStatusCode().equalsIgnoreCase("200")) {
                    try {
                        loanDetailPOM = loanWidget.clickVendorName(i);
                        loanDetailPOM.waitTillLoaderGetsRemoved();
                        try {
                            Assert.assertTrue(loanDetailPOM.IsLoanHistoryWidgetDisplay(), "Loan History Widget not display");
                            Loan loanDetails = api.loanDetailsTest(customerNumber, vendorName);
                            try {
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Loan History Widget Header Name at POS(1) not same as mentioned in excel");
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Loan History Widget Header Name at POS(2) not same as mentioned in excel");
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Loan History Widget Header Name at POS(3) not same as mentioned in excel");
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Loan History Widget Header Name at POS(4) not same as mentioned in excel");
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Loan History Widget Header Name at POS(5) not same as mentioned in excel");
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(6).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Loan History Widget Header Name at POS(6) not same as mentioned in excel");
                                softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(7).toLowerCase().trim(), data.getRow7().toLowerCase().trim(), "Loan History Widget Header Name at POS(7) not same as mentioned in excel");
                                LoanHistory loanHistory = loanDetails.getResult().getLoanHistory();
                                ArrayList<HeaderList> headerList = loanDetails.getResult().getLoanHistory().getHeaderList();
                                ArrayList<LoanRepaymentList> loanRepaymentList = loanHistory.getLoanRepaymentList();
                                for (int j = 0; j < headerList.size(); j++) {
                                    softAssert.assertEquals(loanDetailPOM.getLoanHistoryHeaderName(j + 1).toLowerCase().trim(), headerList.get(j).getHeader().toLowerCase().trim(), "Loam History widget Header name at POS(" + (j + 1) + ") not same as in com.airtel.cs.API Response");
                                }
                                int count = 0;
                                if (loanRepaymentList.size() > 5) {
                                    count = 5;
                                } else {
                                    count = loanRepaymentList.size();
                                }
                                for (int m = 0; m < count; m++) {
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 1).trim(), loanRepaymentList.get(m).getId(), "Loan Transaction id not same as com.airtel.cs.API response in Row" + (m + 1));
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 2).trim(), UtilsMethods.ValueRoundOff(loanRepaymentList.get(m).getAmountCredited()), "Loan Amount Credited not same as com.airtel.cs.API response in Row" + (m + 1));
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 3).trim(), UtilsMethods.ValueRoundOff(loanRepaymentList.get(m).getServiceCharge()), "Loan Service charge not same as com.airtel.cs.API response in Row" + (m + 1));
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 4).trim(), UtilsMethods.ValueRoundOff(loanRepaymentList.get(m).getRecovered()), "Loan Recovered Amount not same as com.airtel.cs.API response in Row" + (m + 1));
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 5).trim().toLowerCase(), loanRepaymentList.get(m).getLoanChannel().toLowerCase().trim(), "Loan channel not same as com.airtel.cs.API response in Row" + (m + 1));
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 6).trim().toLowerCase(), loanRepaymentList.get(m).getLoanType().toLowerCase().trim(), "Loan Type not same as com.airtel.cs.API response in Row" + (m + 1));
                                    softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanHistoryHeader(m + 1, 7).trim(), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(loanRepaymentList.get(m).getDateCreated()), headerList.get(6).getDateFormat() + " " + headerList.get(6).getTimeFormat()), "Loan Date Created not same as com.airtel.cs.API response in Row" + (m + 1));
                                    /*
                                     * Loan Recovery Widget Opened
                                     * */
                                    try {
                                        int repaymentCount = loanRepaymentList.get(m).getLoanRepaymentTransaction().getRepaymentTransactionCount();
                                        System.out.println("COUNT:" + repaymentCount);
                                        if (repaymentCount > 0) {
                                            try {
                                                loanDetailPOM.clickTransactionId(m + 1);
                                            } catch (InterruptedException | TimeoutException e) {
                                                e.printStackTrace();
                                                softAssert.fail("Not Able to click Transaction id " + e.getMessage());
                                            }
                                            ArrayList<HeaderList> recoveryWidgetHeader = loanRepaymentList.get(i).getLoanRepaymentTransaction().getHeaderList();
                                            for (int k = 0; k < recoveryWidgetHeader.size(); k++) {
                                                softAssert.assertEquals(loanDetailPOM.getLoanRecoveryHeaderName(m + 1, k + 1).toLowerCase().trim(), recoveryWidgetHeader.get(k).getHeader().toLowerCase().trim(), "Loan Recovery widget header does not same as com.airtel.cs.API response at Pos(" + (m + 1) + ")");
                                            }
                                            ArrayList<LoanRepaymentDetailList> repaymentList = loanRepaymentList.get(m).getLoanRepaymentTransaction().getLoanRepaymentDetailList();
                                            for (int l = 0; l < repaymentCount; l++) {
                                                softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 1).trim(), repaymentList.get(l).getTransactionId(), "Loan Recovery Transaction id column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 2).trim(), UtilsMethods.ValueRoundOff(repaymentList.get(l).getAmountRecovered()), "Loan Recovery Amount Recovered column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 3).toLowerCase().trim(), repaymentList.get(l).getRecoveryMethod().toLowerCase().trim(), "Loan Recovery method column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                                softAssert.assertEquals(loanDetailPOM.getValueCorrespondingToLoanRecoveryHeader(m + 1, l + 1, 4), UtilsMethods.getDateFromEpochInUTC(Long.valueOf(repaymentList.get(l).getDateRecovered()), recoveryWidgetHeader.get(3).getDateFormat() + " " + recoveryWidgetHeader.get(3).getTimeFormat()), "Loan Recovery Date Recovered column value does not same as com.airtel.cs.API Response for Transaction No.(" + (m + 1) + ") in row POS(" + (l + 1) + ")");
                                            }

                                        }
                                    } catch (org.openqa.selenium.NoSuchElementException | NullPointerException | TimeoutException e) {
                                        softAssert.fail("Loan Recovery Widget does not display correctly: " + e.fillInStackTrace());
                                    }

                                }
                            } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
                                softAssert.fail("Loan History widget can not be validate due to following error: " + e.getMessage());
                            }
                            loanDetailPOM.clickCloseTab();
                        } catch (AssertionError e) {
                            loanDetailPOM.clickCloseTab();
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
        }else {
            UtilsMethods.printWarningLog("No Vendor Found in Loan Service Widget");
        }
        softAssert.assertAll();
    }
}
