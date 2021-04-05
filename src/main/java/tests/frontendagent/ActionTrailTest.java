package tests.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.ActionTrailTabPOM;
import com.airtel.cs.pagerepository.pagemethods.AuthenticationTabPOM;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.CustomerProfilePage;
import com.airtel.cs.pagerepository.pagemethods.CustomerInteractionsSearchPOM;
import com.airtel.cs.pagerepository.pagemethods.ViewHistory;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ActionTrailTest extends BaseTest {

    String reason=null;
    String comments="Adding comment using Automation";
    String agentAuuid=null;

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
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        CustomerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        CustomerProfilePage customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        agentAuuid=Data.getLoginAUUID();
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Action Trail Tab")
    @Test(priority = 3,description = "Validating Action Trail Tab", dataProvider = "HeaderData", dataProviderClass = DataProviders.class,dependsOnMethods = "openCustomerInteraction")
    public void validateActionTrailOpenCorrectly(HeaderDataBean data){
        ExtentTestManager.startTest("Validating the Action Trail Tab Under View History", "Validating the Action Trail Tab Under View History");
        SoftAssert softAssert=new SoftAssert();
        try {
            CustomerProfilePage homepage = new CustomerProfilePage(driver);
            homepage.waitTillLoaderGetsRemoved();
            ViewHistory historyTab = homepage.clickOnViewHistory();
            ActionTrailTabPOM actionTrailTab = historyTab.clickOnActionTrailHistory();
            softAssert.assertEquals(actionTrailTab.getHeaderValue(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Action Type Column does not display in header.");
            softAssert.assertEquals(actionTrailTab.getHeaderValue(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Date & Time Column does not display in header.");
            softAssert.assertEquals(actionTrailTab.getHeaderValue(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Reason Column does not display in header.");
            softAssert.assertEquals(actionTrailTab.getHeaderValue(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Agent Id Column does not display in header.");
            softAssert.assertEquals(actionTrailTab.getHeaderValue(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Agent name Column does not display in header.");
            softAssert.assertEquals(actionTrailTab.getHeaderValue(5).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Comments Column does not display in header.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Not able to validate Action Trail Tab: "+e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify the Send Internet Setting tab",dependsOnMethods = "openCustomerInteraction")
    public void validateSendInternetSetting() {
        ExtentTestManager.startTest("Verify the Send Internet Setting tab", "Verify the Send Internet Setting tab");
        SoftAssert softAssert = new SoftAssert();
        CustomerProfilePage homepage = new CustomerProfilePage(driver);
        AuthenticationTabPOM authTab=new AuthenticationTabPOM(driver);
        homepage.waitTillLoaderGetsRemoved();
        try {
            homepage.clickOnAction();
            homepage.clickSendSetting();
            homepage.waitTillLoaderGetsRemoved();
            homepage.waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(authTab.isIssueDetailTitle(), "Issue Detail does not configured");
                authTab.openSelectPopup();
                reason=authTab.getReason();
                authTab.chooseReason();
                authTab.writeComment(comments);
                softAssert.assertTrue(homepage.isSendInternetSettingTitle(), "Send Internet Setting Tab Does not open after internet setting.");
                authTab.clickSubmitBtn();
                authTab.waitTillLoaderGetsRemoved();
                if(homepage.isSendInternetSettingTitle()){
                    String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                            getScreenshotAs(OutputType.BASE64);
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
                    softAssert.fail("Send Internet setting pop up does not close after submit button: ");
                  authTab.clickCloseBtn();
                }
            } catch (TimeoutException | NoSuchElementException |AssertionError e) {
                softAssert.fail("Not able to close send Internet Setting Tab." + e.fillInStackTrace());
                authTab.clickCloseBtn();
            }
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Send Internet Setting Option does not configure correctly."+e.fillInStackTrace());
            homepage.clickOutside();
        }
        softAssert.assertAll();
    }

    @Test(priority = 4,description = "Validating Action Trail History",dependsOnMethods = "validateSendInternetSetting")
    public void validateActionTrailValue(){
        ExtentTestManager.startTest("Validating Action Trail History", "Validating Action Trail History");
        SoftAssert softAssert=new SoftAssert();
        try {
            CustomerProfilePage homepage = new CustomerProfilePage(driver);
            homepage.waitTillLoaderGetsRemoved();
            ViewHistory historyTab = homepage.clickOnViewHistory();
            ActionTrailTabPOM actionTrailTab = historyTab.clickOnActionTrailHistory();
            softAssert.assertEquals(actionTrailTab.getValue(0,0).toLowerCase().trim(),"send internet settings","Action Type Column Value does not display in Correctly.");
            softAssert.assertNotNull(actionTrailTab.getValue(0,1).toLowerCase().trim(), "Date & Time Column does not display in Correctly.");
            softAssert.assertEquals(actionTrailTab.getValue(0,2).toLowerCase().trim(), reason.toLowerCase().trim(), "Reason Column does not display in Correctly.");
            softAssert.assertEquals(actionTrailTab.getValue(0,3), agentAuuid ,"Agent Id Column does not display in Correctly.");
            softAssert.assertNotNull(actionTrailTab.getValue(0,4).toLowerCase().trim(), "Agent name Column does not display in Correctly.");
            softAssert.assertEquals(actionTrailTab.getValue(0,5).toLowerCase().trim(), comments.toLowerCase().trim(), "Comments Column does not display in Correctly.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Not able to validate Action Trail Tab: "+e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
