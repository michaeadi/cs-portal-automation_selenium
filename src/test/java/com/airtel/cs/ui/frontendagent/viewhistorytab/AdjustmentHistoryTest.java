package com.airtel.cs.ui.frontendagent.viewhistorytab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.model.response.adjustmenthistory.AdjustmentResult;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

public class AdjustmentHistoryTest extends Driver {
    public static final String RUN_ADJUSTMENT_TEST_CASE = constants.getValue(ApplicationConstants.RUN_ADJUSTMENT_TEST_CASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod
    public void checkAdjustmentFlag() {
        if (!StringUtils.equals(RUN_ADJUSTMENT_TEST_CASE, "true")) {
            commonLib.skip("Adjustment History Tab is NOT Enabled for this Opco " + OPCO);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
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

    @DataProviders.Table(name = "Adjustment History Tab")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void validateAdjustmentTabOpenCorrectly(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible and then click on it,Validate column header name are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnAdjustmentHistory();
            for (int i = 0; i < data.getHeaderName().size(); i++) {
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getAdjustmentHistoryPage().getHeaderValue(i), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Exception in Method - validateAdjustmentTabOpenCorrectly" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "validateAdjustmentTabOpenCorrectly"})
    public void validateAdjustmentHistoryTab() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Adjustment History tab is visible,Validate column's value are visible and correct", "description");
            AdjustmentHistory adjustmentHistory = api.getAdjustMentHistory(customerNumber);
            final int statusCode = adjustmentHistory.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode, false, true));
            if (statusCode == 200) {
                int size = Math.min(adjustmentHistory.getTotalCount(), 10);
                for (int i = 0; i < size; i++) {
                    AdjustmentResult result = adjustmentHistory.getResult().get(i);
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 1).toLowerCase().trim(), result.getDate().toLowerCase().trim(), "Date & Time value displayed as expected", "Date & Time value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 2).toLowerCase().trim(), result.getAdjustmentType().toLowerCase().trim(), "Action Type Column displayed as expected", "Action Type value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 3).toLowerCase().trim(), result.getReason().toLowerCase().trim(), "Reason value displayed as expected", "Reason value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 4).toLowerCase().trim(), result.getAccountType().toLowerCase().trim(), "Account Type Id value displayed as expected", "Account Type Id value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 5).trim(), result.getAmount().trim(), "Amount value Column displayed as expected", "Amount value value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 6).trim(), result.getAgentId().trim(), "Agent Id value displayed as expected", "Agent Id value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 7).toLowerCase().trim(), result.getAgentName().toLowerCase().trim(), "Agent name value displayed as expected.", "Agent name value does not display as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentHistoryPage().getValue(i + 1, 8).toLowerCase().trim(), result.getComment().toLowerCase().trim(), "Comments value displayed as expected", "Comments Column value not display as expected"));
                }
            }
        } catch (NoSuchElementException | TimeoutException | IndexOutOfBoundsException e) {
            commonLib.fail("Exception in Method - validateAdjustmentHistoryTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
