package tests;

import Utils.DataProviders.DataProviders;
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
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PinTagTest extends BaseTest {


    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) throws InterruptedException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
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


    @Test(priority = 2, description = "Validating Pinned Tags")
    public void checkALLPinnedTag() {
        ExtentTestManager.startTest("Validating Pinned Tag", "Validating Pinned Tag :");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        DataProviders data = new DataProviders();
        Map<String, Boolean> tags = data.getALLPinnedTags();
        List<String> availableTags = customerInteractionPage.getPinnedTagTexts();
        try {
            for (String s : availableTags) {
                if (tags.containsKey(s)) {
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " pinned tag is display correctly");
                    tags.remove(s);
                } else {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, s + " tag must not display on frontend as tag not mention in config sheet.");
                    softAssert.fail(s + " tag should not display on UI as tagged not mention in config sheet.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tags.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All pinned tagged correctly configured and display on UI.");
        } else {
            for (Map.Entry mapElement : tags.entrySet()) {
                String key = (String) mapElement.getKey();
                ExtentTestManager.getTest().log(LogStatus.FAIL, key + " tag does not display on UI but present in config sheet.");
                softAssert.fail(key + " tag does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "SideMenu ", dataProvider = "pinTag", dataProviderClass = DataProviders.class)
    public void checkIssueCodeForPinTag(PinnedtagsDataBeans Data) {
        ExtentTestManager.startTest("Validating Pinned Tag : " + Data.getTagName(), "Validating Pinned Tag : " + Data.getTagName() + "  Tag and Issue creation by tag");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        try {
            if (customerInteractionPage.isPinTagVisible(Data.getTagName())) {
                customerInteractionsSearchPOM customerInteractionsSearch = customerInteractionPage.clickPinTag(Data.getTagName());
                customerInteractionsSearch.waitUntilPageIsLoaded();
                customerInteractionsSearch.enterNumber(Data.getCustomerNumber());
                customerInteractionPage = customerInteractionsSearch.clickOnSearch();
                softAssert.assertTrue(customerInteractionPage.isPageLoaded());
                viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
                viewHistory.clickOnInteractionsTab();
                String issueCode = viewHistory.getLastCreatedIssueCode();
                softAssert.assertEquals(issueCode.toLowerCase().trim(), Data.getIssueCode().trim().toLowerCase());
            }else{
                softAssert.fail(Data.getTagName()+" Does not display on UI");
            }
        } catch (NoSuchElementException e) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, Data.getTagName() + " tag does not display on UI but present in config sheet.");
            softAssert.fail(Data.getTagName() + " tag does not display on UI but present in config sheet.\n"+e.fillInStackTrace());
            e.printStackTrace();
        }
        softAssert.assertAll();
    }
}