package tests.frontendAgent;


import API.APIEndPoints;
import POJO.ClearRefillStatus.RefillStatus;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.RechargeHistoryWidgetPOM;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;

public class ClearRefillErrorTest extends BaseTest {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "API")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean Data) {
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
        softAssert.assertAll();
    }

    @Test(priority = 2,dependsOnMethods = "openCustomerInteractionAPI",description = "Validating Clear Refill Clear")
    public void clearRefillTest() {
        ExtentTestManager.startTest("Validating Clear Refill Clear: "+customerNumber, "Validating Clear Refill Clear :" + customerNumber);
        RechargeHistoryWidgetPOM rechargeHistoryWidget = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        RefillStatus refillStatus=api.clearRefillTest(customerNumber);
        if(refillStatus.getResponse().getRefilledBarred()){
            Assert.assertTrue(rechargeHistoryWidget.isRefillIconEnable(),"Clear Refill Icon does not enable when user bar.");
            try{
                rechargeHistoryWidget.clickRefillIcon();
                Assert.assertTrue(rechargeHistoryWidget.checkPopDisplay(),"Confirmation window does not display after clicking on clear Refill Icon.");
                rechargeHistoryWidget.clickNoBtn();
            }catch (NoSuchElementException | TimeoutException e){
                softAssert.fail("Not able to perform Clear refill action due to error: "+e.fillInStackTrace());
            }
        }else {
            Assert.assertTrue(rechargeHistoryWidget.isRefillIconDisable(),"Clear refill icon does not disable when user not barred.");
        }
        softAssert.assertAll();
    }
}
