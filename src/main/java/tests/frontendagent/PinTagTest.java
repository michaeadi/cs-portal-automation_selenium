package tests.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PinnedtagsDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.CustomerProfilePage;
import com.airtel.cs.pagerepository.pagemethods.CustomerInteractionsSearchPOM;
import com.airtel.cs.pagerepository.pagemethods.ViewHistory;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PinTagTest extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }


    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) throws InterruptedException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        CustomerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        CustomerProfilePage customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }


    @Test(priority = 2, description = "Validating Pinned Tags")
    public void checkALLPinnedTag() {
        ExtentTestManager.startTest("Validating Pinned Tag", "Validating Pinned Tag :");
        SoftAssert softAssert = new SoftAssert();
        CustomerProfilePage customerInteractionPage = new CustomerProfilePage(driver);
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
        CustomerProfilePage customerInteractionPage = new CustomerProfilePage(driver);
        try {
            if (customerInteractionPage.isPinTagVisible(Data.getTagName())) {
                CustomerInteractionsSearchPOM customerInteractionsSearch = customerInteractionPage.clickPinTag(Data.getTagName());
                customerInteractionsSearch.waitUntilPageIsLoaded();
                customerInteractionsSearch.enterNumber(Data.getCustomerNumber());
                customerInteractionPage = customerInteractionsSearch.clickOnSearch();
                softAssert.assertTrue(customerInteractionPage.isPageLoaded());
                ViewHistory viewHistory = customerInteractionPage.clickOnViewHistory();
                viewHistory.clickOnInteractionsTab();
                String issueCode = viewHistory.getLastCreatedIssueCode();
                softAssert.assertEquals(issueCode.toLowerCase().trim(), Data.getIssueCode().trim().toLowerCase());
            } else {
                softAssert.fail(Data.getTagName() + " Does not display on UI");
            }
        } catch (NoSuchElementException e) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, Data.getTagName() + " tag does not display on UI but present in config sheet.");
            softAssert.fail(Data.getTagName() + " tag does not display on UI but present in config sheet.\n" + e.fillInStackTrace());
            e.printStackTrace();
        }
        softAssert.assertAll();
    }
}