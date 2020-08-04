package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.PinnedtagsDataBeans;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;
import pages.viewHistoryPOM;

import java.io.IOException;
import java.lang.reflect.Method;

public class customerInteractionTest extends BaseTest {
    @DataProvider.User(UserType = "ALL")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
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
    @Test(priority = 2, description = "Validating Demographic Info", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void validateDemographicInformation(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Demographic Information of User :" + Data.getCustomerNumber(), "Validating the Demographic Information of User :" + Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        System.out.println(customerInteractionPagePOM.getCustomerName());
        softAssert.assertEquals(customerInteractionPagePOM.getCustomerName().trim(), Data.getCustomerName());
        System.out.println(customerInteractionPagePOM.getCustomerDOB());
        softAssert.assertEquals(customerInteractionPagePOM.getCustomerDOB().trim(), Data.getCustomerDOB());
        System.out.println(customerInteractionPagePOM.getActivationDate());
        softAssert.assertEquals(customerInteractionPagePOM.getActivationDate().trim(), Data.getActivationDate());
        System.out.println(customerInteractionPagePOM.getActivationTime());
        softAssert.assertEquals(customerInteractionPagePOM.getActivationTime().trim(), Data.getActivationTime().trim());
        System.out.println(customerInteractionPagePOM.getSimNumber());
        softAssert.assertEquals(customerInteractionPagePOM.getSimNumber().trim(), Data.getSimNumber());
        System.out.println(customerInteractionPagePOM.getSimType());
        softAssert.assertEquals(customerInteractionPagePOM.getSimType().trim(), Data.getSimType());
        System.out.println(customerInteractionPagePOM.getPUK1());
        softAssert.assertEquals(customerInteractionPagePOM.getPUK1().trim(), Data.getPUK1());
        System.out.println(customerInteractionPagePOM.getPUK2());
        softAssert.assertEquals(customerInteractionPagePOM.getPUK2().trim(), Data.getPUK2());
        System.out.println(customerInteractionPagePOM.getIdType());
        softAssert.assertEquals(customerInteractionPagePOM.getIdType().trim(), Data.getIdType());
        System.out.println(customerInteractionPagePOM.getIdNumber());
        softAssert.assertEquals(customerInteractionPagePOM.getIdNumber().trim(), Data.getIdNumber());
        softAssert.assertAll();
    }

    @DataProvider.RowNumber(rowNumber = "1")
    @Test(priority = 3, description = "Validating Blank Call ", dataProvider = "getSingleRow", dataProviderClass = DataProvider.class)
    public void blankCallTest(Method method, PinnedtagsDataBeans Data) {
        ExtentTestManager.startTest("Validating Pinned Tag : Blank call", "Validating Pinned Tag : Blank call Tag and Ticket creation by tag");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        if (Data.getAvailable().trim().toLowerCase().equals("yes")) {
            customerInteractionsSearchPOM customerInteractionsSearch = customerInteractionPage.clickOnBlankCall();
            customerInteractionsSearch.waitUntilPageIsLoaded();
            customerInteractionsSearch.enterNumber(Data.getCustomerNumber());
            customerInteractionPage = customerInteractionsSearch.clickOnSearch();
            softAssert.assertTrue(customerInteractionPage.isPageLoaded());
            viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
            viewHistory.clickOnInteractionsTab();
            String issueCode = viewHistory.getLastCreatedIssueCode();
            softAssert.assertEquals(issueCode.toLowerCase().trim(), Data.getIssueCode().trim().toLowerCase());

        } else {
            if (customerInteractionPage.isBlankCallTagVisible())
                softAssert.fail("Blank Call shouldn't be visible but it is");
            else {
                softAssert.assertTrue(true);
                ExtentTestManager.getTest().log(LogStatus.INFO, "Blank Call Tag is not Present");

            }
        }
        softAssert.assertAll();
    }

    @DataProvider.RowNumber(rowNumber = "2")
    @Test(priority = 4, description = "Validating Call Drop ", dataProvider = "getSingleRow", dataProviderClass = DataProvider.class)
    public void callDropTest(Method method, PinnedtagsDataBeans Data) {
        ExtentTestManager.startTest("Validating Pinned Tag : Call Drop", "Validating Pinned Tag : Call Drop Tag and Ticket creation by tag");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        if (Data.getAvailable().trim().toLowerCase().equals("yes")) {
            customerInteractionsSearchPOM customerInteractionsSearch = customerInteractionPage.clickOnCallDrop();
            customerInteractionsSearch.waitUntilPageIsLoaded();
            customerInteractionsSearch.enterNumber(Data.getCustomerNumber());
            customerInteractionPage = customerInteractionsSearch.clickOnSearch();
            softAssert.assertTrue(customerInteractionPage.isPageLoaded());
            viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
            viewHistory.clickOnInteractionsTab();
            String issueCode = viewHistory.getLastCreatedIssueCode();
            softAssert.assertEquals(issueCode.toLowerCase().trim(), Data.getIssueCode().trim().toLowerCase());

        } else {
            if (customerInteractionPage.isCallDropTagVisible())
                softAssert.fail(" Call Drop Tag shouldn't be visible but it is");
            else {
                softAssert.assertTrue(true);
                ExtentTestManager.getTest().log(LogStatus.INFO, "Call Drop Tag is not Present");

            }
        }
        softAssert.assertAll();
    }

    @DataProvider.RowNumber(rowNumber = "3")
    @Test(priority = 5, description = "SideMenu ", dataProvider = "getSingleRow", dataProviderClass = DataProvider.class)
    public void noiseOnCallTest(Method method, PinnedtagsDataBeans Data) {
        ExtentTestManager.startTest("Validating Pinned Tag : Noise On Call ", "Validating Pinned Tag : Noise On Call  Tag and Ticket creation by tag");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        if (Data.getAvailable().trim().toLowerCase().equals("yes")) {
            customerInteractionsSearchPOM customerInteractionsSearch = customerInteractionPage.clickOnNoiseCall();
            customerInteractionsSearch.waitUntilPageIsLoaded();
            customerInteractionsSearch.enterNumber(Data.getCustomerNumber());
            customerInteractionPage = customerInteractionsSearch.clickOnSearch();
            softAssert.assertTrue(customerInteractionPage.isPageLoaded());
            viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
            viewHistory.clickOnInteractionsTab();
            String issueCode = viewHistory.getLastCreatedIssueCode();
            softAssert.assertEquals(issueCode.toLowerCase().trim(), Data.getIssueCode().trim().toLowerCase());

        } else {
            if (customerInteractionPage.isNoiseCallTagVisible())
                softAssert.fail(" Noise On Call Tag shouldn't be visible but it is");
            else {
                softAssert.assertTrue(true);
                ExtentTestManager.getTest().log(LogStatus.INFO, "Noise on Call Tag  is not Present");
            }
        }
        softAssert.assertAll();
    }
}
