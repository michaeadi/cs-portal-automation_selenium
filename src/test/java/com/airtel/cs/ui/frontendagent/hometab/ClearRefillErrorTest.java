package com.airtel.cs.ui.frontendagent.hometab;


import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.clearrefillstatus.RefillStatus;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClearRefillErrorTest extends Driver {

    static String customerNumber;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate clear refill
     */
    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest"})
    public void clearRefillTest() {
        try {
            selUtils.addTestcaseDescription("Validating Clear Refill Test: " + customerNumber, "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetIsVisible(), true, "Recharge History Widget is visible", "Recharge History Widget is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRechargeHistoryDatePickerVisible(), true, "Recharge History Widget's Date Picker is visible", "Recharge History Widget's Date Picker is not visible"));
            RefillStatus refillStatus = api.clearRefillTest(customerNumber);
            boolean status = refillStatus.getResponse().getRefilledBarred();
            if (status) {
                assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRefillIconEnable(), true, "Clear Refill Icon enable when user bar.", "Clear Refill Icon does not enable when user bar."));
                pages.getRechargeHistoryWidget().clickRefillIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().checkPopDisplay(), true, "Confirmation window display after clicking on clear Refill Icon.", "Confirmation window does not display after clicking on clear Refill Icon."));
                pages.getRechargeHistoryWidget().clickNoBtn();
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRefillIconDisable(), true, "Clear refill icon disable when user not barred.", "`Clear refill icon does not disable when user not barred`."));
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - clearRefillTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
