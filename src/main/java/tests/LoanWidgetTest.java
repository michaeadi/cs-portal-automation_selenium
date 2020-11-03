package tests;

import API.APITest;
import POJO.LoanSummary.Summary;
import POJO.Vendors.HeaderList;
import POJO.Vendors.VendorNames;
import POJO.Vendors.Vendors;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoanWidgetPOM;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;

import java.io.IOException;
import java.util.ArrayList;

public class LoanWidgetTest extends BaseTest {

    static String customerNumber;
    APITest api = new APITest();
    ArrayList<Vendors> vendors;

    @DataProviders.User()
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
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @DataProviders.Table(Name = "Loan Services")
    @Test(priority = 2, description = "Validating Loan Widget layout", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateLoanWidgetLayout(HeaderDataBean Data) throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validating Loan Service Widget layout", "Validating Loan Service Widget");
        SoftAssert softAssert = new SoftAssert();
        VendorNames vendorNames=api.vendorsNamesTest();
        LoanWidgetPOM loanWidget=new LoanWidgetPOM(driver);
        vendors=vendorNames.getResult().getVendors();
        for(Vendors v:vendors){
            loanWidget.printInfoLog("Loan Services: "+v.getVendorName());
        }
        /*
        * Checking API Giving valid Response
        * */
        ArrayList<HeaderList> headers=vendorNames.getResult().getHeaderList();
        softAssert.assertEquals(headers.get(0).getHeader().toLowerCase().trim(),Data.getRow1().toLowerCase().trim(),"Header not same as expected in API(API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(1).getHeader().toLowerCase().trim()+" ("+headers.get(1).getSubHeader().toLowerCase().trim()+")",Data.getRow2().toLowerCase().trim(),"Header not same as expected in API(API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(2).getHeader().toLowerCase().trim(),Data.getRow3().toLowerCase().trim(),"Header not same as expected in API(API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(3).getHeader().toLowerCase().trim(),Data.getRow4().toLowerCase().trim(),"Header not same as expected in API(API Response Assert with Excel)");
        softAssert.assertEquals(headers.get(4).getHeader().toLowerCase().trim(),Data.getRow5().toLowerCase().trim(),"Header not same as expected in API(API Response Assert with Excel)");
        /*
         * Checking header displayed on UI
         * */
        softAssert.assertEquals(headers.get(0).getHeader().toLowerCase().trim(),loanWidget.getVendor().toLowerCase().trim(),"Header not same as expected on UI(API Response Assert with UI)");
        softAssert.assertEquals(headers.get(1).getHeader().toLowerCase().trim()+" ("+headers.get(1).getSubHeader().toLowerCase().trim()+")",loanWidget.getLoanAmount().toLowerCase().trim()+" ("+loanWidget.getCurrencyType().toLowerCase().trim()+")","Header not same as expected on UI(API Response Assert with UI)");
        softAssert.assertEquals(headers.get(2).getHeader().toLowerCase().trim(),loanWidget.getCreatedON().toLowerCase().trim(),"Header not same as expected on UI(API Response Assert with UI)");
        softAssert.assertEquals(headers.get(3).getHeader().toLowerCase().trim(),loanWidget.getCurrentOutstanding().toLowerCase().trim(),"Header not same as expected on UI(API Response Assert with UI)");
        softAssert.assertEquals(headers.get(4).getHeader().toLowerCase().trim(),loanWidget.getDueDate().toLowerCase().trim(),"Header not same as expected on UI(API Response Assert with UI)");
        softAssert.assertAll();
    }


    @Test(priority = 3, description = "Validate Loan Widget")
    public void validateLoanWidget() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validating Loan Service Widget:"+customerNumber, "Validating Loan Service Widget");
        SoftAssert softAssert = new SoftAssert();
        LoanWidgetPOM loanWidget = new LoanWidgetPOM(driver);
        for(int i=1;i<=loanWidget.getSize();i++) {
            Summary summary=api.loanSummaryTest(customerNumber,loanWidget.getVendorName(i).trim());
            if(!summary.getStatusCode().equalsIgnoreCase("200") | summary.getStatus().equalsIgnoreCase("Failure")){
                softAssert.assertTrue(loanWidget.checkMessageDisplay(summary.getMessage()),summary.getMessage()+" :Message does not display");
            }else{
                softAssert.assertEquals(loanWidget.getLoanAmount(i),summary.getResult().getLoanAmount(),"Loan amount not same as API Response");
                softAssert.assertEquals(loanWidget.getOutstandingAmount(i),summary.getResult().getCurrentOutstanding().getValue(),"Current Outstanding amount not same as API Response");
                /*
                * Due Date and Created on assertion pending as API not working as expected
                * */
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate Loan Detail Widget")
    public void validateLoanDetailWidget() throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Loan Detail Widget", "Validate Loan Detail Widget");
        SoftAssert softAssert = new SoftAssert();
        VendorNames vendorNames = api.vendorsNamesTest();
        LoanWidgetPOM loanWidget = new LoanWidgetPOM(driver);
        for(int i=1;i<=loanWidget.getSize();i++) {
            Summary summary=api.loanSummaryTest(customerNumber,loanWidget.getVendorName(i).trim());
            if(summary.getStatusCode().equalsIgnoreCase("200")){
                loanWidget.clickVendorName(i);
            }
        }
        softAssert.assertAll();
    }


}
