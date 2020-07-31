package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.PinnedtagsDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;
import pages.viewHistoryPOM;

import java.lang.reflect.Method;

public class PinnedTagTest extends BaseTest {

    @DataProvider.RowNumber(rowNumber = "1")
    @Test(priority = 1, description = "Validating Blank Call ", dataProvider = "getSingleRow", dataProviderClass = DataProvider.class)
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
    @Test(priority = 2, description = "Validating Call Drop ", dataProvider = "getSingleRow", dataProviderClass = DataProvider.class)
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
    @Test(priority = 3, description = "SideMenu ", dataProvider = "getSingleRow", dataProviderClass = DataProvider.class)
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
