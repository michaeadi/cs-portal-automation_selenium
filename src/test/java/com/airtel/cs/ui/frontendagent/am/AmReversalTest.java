package com.airtel.cs.ui.frontendagent.am;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.actiontrail.EventLogsResponse;
import com.airtel.cs.model.cs.response.actiontrail.EventResult;
import com.airtel.cs.model.cs.response.airtelmoney.AirtelMoney;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AmReversalTest extends Driver {

    RequestSource api = new RequestSource();
    String customerNumber;
    private AirtelMoney amTransactionHistoryAPI;
    public static final String RUN_AIRTEL_MONEY_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_AIRTEL_MONEY_WIDGET_TESTCASE);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest"})
    public void checkAirtelMoneyFlag() {
        if (!StringUtils.equals(RUN_AIRTEL_MONEY_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Airtel Money widget Test Case Flag Value is - " + RUN_AIRTEL_MONEY_WIDGET_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"}, enabled = false)
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.AM_CUSTOMER_MSISDN);
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"}, enabled = false)
    public void testAmReversal() {
        try {
            selUtils.addTestcaseDescription("Verify Airtel Money Txn Reversal", "description");
            amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
            final int statusCode = amTransactionHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "AM Txn History API success and status code is :" + statusCode, "AM Txn History got failed and status code is :" + statusCode));
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyErrorVisible(), true, "API is Giving error and Widget is showing error Message on API is " + amTransactionHistoryAPI.getMessage(), "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", true);
            } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null || amTransactionHistoryAPI.getResult().getTotalCount() == 0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getMoreAMTxnTabPage().isAirtelMoneyNoResultFoundVisible(), true, "No Result Found Icon does display on UI.", "No Result Found Icon does not display on UI."));
            } else {
                assertCheck = pages.getAmReversal().reversal(amTransactionHistoryAPI);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testAmReversal" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"testAmReversal"}, enabled = false)
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing Am Reversal action", "description");
            pages.getAmSmsTrails().goToActionTrail();
            EventLogsResponse eventLogs = api.getEventHistory(customerNumber, "ACTION");
            int statusCode = eventLogs.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Event Logs API success and status code is :" + statusCode, "Event Logs API got failed and status code is :" + statusCode, false, true));
            EventResult eventResult = eventLogs.getResult().get(0);
            if (statusCode == 200) {
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getActionType(), eventResult.getActionType(), "Action type for Reversal is expected", "Action type for Reversal is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getReason(), eventResult.getReason(), "Reason for Reversal is as expected", "Reason for Reversal not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmSmsTrails().getComment(), eventResult.getComments(), "Comment for Reversal is expected", "Comment for Reversal is not as expected"));
            } else
                commonLib.fail("Not able to fetch action trail as event log API's status code is :" + statusCode, true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }
}
