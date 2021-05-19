package com.airtel.cs.ui.frontendagent.hometab;


import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.clearrefillstatus.RefillStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ClearRefillErrorTest extends Driver {

    static String customerNumber;
    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @DataProviders.User(userType = "API")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteractionAPI(TestDatabean data) {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, dependsOnMethods = "openCustomerInteractionAPI", description = "Validating Clear Refill Clear")
    public void clearRefillTest() {
        selUtils.addTestcaseDescription("Validating Clear Refill Clear: " + customerNumber, "description");
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
