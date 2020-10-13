package tests;

import Utils.DataProviders.AuthTabDataBeans;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AuthenticationTabPOM;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;
import java.util.List;
import java.util.Map;

public class AuthTabTest extends BaseTest {

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
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

    @Test(priority = 2, description = "Verify the Authentication tab")
    public void validateAuthTab() {
        ExtentTestManager.startTest("Verify the Authentication tab", "Verify the Authentication tab");
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM homepage = new customerInteractionPagePOM(driver);
        homepage.waitTillLoaderGetsRemoved();
        homepage.clickOnAction();
        AuthenticationTabPOM authTab = homepage.openAuthTab();
        DataProviders data = new DataProviders();
        authTab.waitTillLoaderGetsRemoved();
        Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
        Map<String, String> questionList = authTab.getQuestionAnswer();
        List<AuthTabDataBeans> list = data.getPolicy();
        List<String> questions = data.getPolicyQuestion();
        softAssert.assertEquals(authTab.getAuthInstruction().toLowerCase().trim(), list.get(0).getPolicyMessage().toLowerCase().trim(), "Policy Message not same as configured");
        for (String s : questions) {
            String trim = s.replaceAll("[^a-zA-Z]+", "").toLowerCase().trim();
            if (!questionList.containsKey(trim)) {
                softAssert.assertTrue(false, s + " :Question must configured on UI as present in config sheet");
            }
            questionList.remove(trim);
        }
        if (questionList.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All Questions correctly configured and display on UI.");
        } else {
            for (Map.Entry<String, String> mapElement : questionList.entrySet()) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " Question Display on UI but does not present in config sheet.");
                softAssert.fail(mapElement.getKey() + " :Question Display on UI but does not present in config sheet.");
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Verify the Authentication tab Minimum question Configured correctly")
    public void validateAuthTabMinQuestion() throws InterruptedException {
        ExtentTestManager.startTest("Verify the Authentication tab Minimum question Configured correctly", "Verify the Authentication tab Minimum question Configured correctly");
        SoftAssert softAssert = new SoftAssert();
        AuthenticationTabPOM authTab = new AuthenticationTabPOM(driver);
        DataProviders data = new DataProviders();
        authTab.waitTillLoaderGetsRemoved();
        Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
        List<AuthTabDataBeans> list = data.getPolicy();
        for(int i=1;i<=Integer.parseInt(list.get(0).getMinAnswer());i++){
            authTab.clickCheckBox(i);
        }
        softAssert.assertTrue(authTab.isAuthBtnEnable(),"Authenticate Button does not enable after choose minimum number of question");
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Authenticate User")
    public void authCustomer() throws InterruptedException {
        ExtentTestManager.startTest("Authenticate User", "Authenticate User");
        SoftAssert softAssert = new SoftAssert();
        AuthenticationTabPOM authTab = new AuthenticationTabPOM(driver);
        DataProviders data = new DataProviders();
        authTab.waitTillLoaderGetsRemoved();
        Assert.assertTrue(authTab.isAuthTabLoad(), "Authentication tab does not load correctly");
        authTab.clickAuthBtn();
        authTab.waitTillLoaderGetsRemoved();
        Assert.assertTrue(authTab.isSIMBarPopup(),"SIM Bar/unbar popup does not open");
        softAssert.assertTrue(authTab.isIssueDetailTitle(),"Issue Detail does not configured");
        softAssert.assertFalse(authTab.isSubmitBtnEnable(),"Submit button enable without adding comment");
        authTab.openSelectPopup();
        List<String> reason=authTab.getReasonConfig();
        List<String> configReason=data.issueDetailReason("SIM Bar Unbar");
        for(String s:reason){
            if(!configReason.contains(s)){
                softAssert.assertFalse(true,s+": Must not configured on UI as not mentioned in config.");
            }
            configReason.remove(s);
        }

        for(String s: configReason){
            softAssert.assertFalse(true,s+": Must configured on UI as mentioned in config.");
        }
        authTab.chooseReason();
        authTab.writeComment("Adding comment using Automation");
        softAssert.assertTrue(authTab.isSubmitBtnEnable(),"Submit button does not enabled after adding comment");
        authTab.closeSIMBarPopup();
        softAssert.assertAll();
    }

}
