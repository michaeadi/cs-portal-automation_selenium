package com.airtel.cs.ui.frontendagent;


import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.clearrefillstatus.RefillStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ClearRefillErrorTest extends Driver {

    static String customerNumber;
    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "API")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        customerNumber = data.getCustomerNumber();
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openCustomerInteractionAPI", description = "Validating Clear Refill Clear")
    public void clearRefillTest() {
        ExtentTestManager.startTest("Validating Clear Refill Clear: " + customerNumber, "Validating Clear Refill Clear :" + customerNumber);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible");
        softAssert.assertTrue(pages.getRechargeHistoryWidget().isRechargeHistoryDatePickerVisible(), "Recharge History Widget's Date Picker is not visible");
        RefillStatus refillStatus = api.clearRefillTest(customerNumber);
        boolean status = refillStatus.getResponse().getRefilledBarred();
        if (status) {
            Assert.assertTrue(pages.getRechargeHistoryWidget().isRefillIconEnable(), "Clear Refill Icon does not enable when user bar.");
            try {
                pages.getRechargeHistoryWidget().clickRefillIcon();
                Assert.assertTrue(pages.getRechargeHistoryWidget().checkPopDisplay(), "Confirmation window does not display after clicking on clear Refill Icon.");
                pages.getRechargeHistoryWidget().clickNoBtn();
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Not able to perform Clear refill action due to error: " + e.fillInStackTrace());
            }
        } else {
            Assert.assertTrue(pages.getRechargeHistoryWidget().isRefillIconDisable(), "Clear refill icon does not disable when user not barred.");
        }
        softAssert.assertAll();
    }
}
