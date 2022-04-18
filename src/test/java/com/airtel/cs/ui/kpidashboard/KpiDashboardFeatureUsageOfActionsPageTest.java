package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import static com.airtel.cs.driver.Driver.assertCheck;
import static com.airtel.cs.driver.Driver.selUtils;

public class KpiDashboardFeatureUsageOfActionsPageTest extends Driver {
    ObjectMapper mapper = new ObjectMapper();


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void FeatureUsageOfActions() {
        try {
            selUtils.addTestcaseDescription("Feature Usage Of Actions", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isFeatureUsageOfActionsLabelVisible(), true, "Feature Usage Of Actions Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isConnectionLabelVisible(), true, "Connection Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isMonthSelectionLabelVisible(), true, "Month Selection label is visible"));
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - FeatureUsageOfActions" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void FeatureUsageOfActionsDetailsPage() {
        try {
            selUtils.addTestcaseDescription("Feature Usage Of Actions Details Page", "description");
            pages.getKpiDashboardFeatureUsageOfActions().clickOnFeatureUsageOfActionsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isFeatureUsageOfActionsLabelVisible(), true, "Feature Usage Of Actions Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isAgentIdLabelVisible(), true, "Agent ID Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isAgentNameLabelVisible(), true, "Agent Name label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isMTDLabelVisible(), true, "MTD label is visible"));

            pages.getKpiDashboardFeatureUsageOfActions().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - FeatureUsageOfActionsDetailsPage" + e.fillInStackTrace(), true);

        }
    }
}