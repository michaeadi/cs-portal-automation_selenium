package com.airtel.cs.ui.frontendagent.viewhistorytab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.pojo.response.adjustmenthistory.AdjustmentResult;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

public class AdjustmentHistoryTest extends Driver {
    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    public static final String RUN_ADJUSTMENT_TEST_CASE = constants.getValue(ApplicationConstants.RUN_ADJUSTMENT_TEST_CASE);

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
            commonLib.skip("Adjustment History Tab is NOT Enabled for this Opco "+OPCO);
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
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
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
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Date & Time Column displayed in header", "Date & Time Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Action Type Column displayed in header", "Action Type Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Reason Column displayed in header", "Reason Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Account Type Id Column displayed in header", "Account Type Id Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Amount value Column displayed in header", "Amount value Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Agent Id Column displayed in header", "Agent Id Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(6).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Agent name Column displayed in header.", "Agent name Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getHeaderValue(7).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Comments Column displayed in header", "Comments Column does not display in header"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Exception in Method - validateAdjustmentTabOpenCorrectly" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction","validateAdjustmentTabOpenCorrectly"})
    public void validateAdjustmentHistoryTab() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Adjustment History tab is visible,Validate column's value are visible and correct", "description");
            AdjustmentHistory adjustmentHistory = api.getAdjustMentHistory(customerNumber);
            final int statusCode = adjustmentHistory.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode,true,true));
            if (statusCode == 200) {
                int size=Math.min(adjustmentHistory.getTotalCount(),10);
                for(int i=0;i<size;i++) {
                    AdjustmentResult result=adjustmentHistory.getResult().get(i);
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,1).toLowerCase().trim(), result.getDate().toLowerCase().trim(), "Date & Time value displayed as expected", "Date & Time value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,2).toLowerCase().trim(), result.getAdjustmentType().toLowerCase().trim(), "Action Type Column displayed as expected", "Action Type value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,3).toLowerCase().trim(), result.getReason().toLowerCase().trim(), "Reason value displayed as expected", "Reason value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,4).toLowerCase().trim(), result.getAccountType().toLowerCase().trim(), "Account Type Id value displayed as expected", "Account Type Id value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,5).trim(), result.getAmount().trim(), "Amount value Column displayed as expected", "Amount value value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,6).trim(), result.getAgentId().trim(), "Agent Id value displayed as expected", "Agent Id value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,7).toLowerCase().trim(), result.getAgentName().toLowerCase().trim(), "Agent name value displayed as expected.", "Agent name value does not display as expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentHistoryPage().getValue(i+1,8).toLowerCase().trim(), result.getComment().toLowerCase().trim(), "Comments value displayed as expected", "Comments Column value not display as expected"));
                }
            }
        } catch (NoSuchElementException | TimeoutException | IndexOutOfBoundsException e) {
            commonLib.fail("Exception in Method - validateAdjustmentHistoryTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
