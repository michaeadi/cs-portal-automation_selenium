package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;

public class KpiFeatureUsageActionsTest extends Driver {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testFeatureUsageWidget() {
        try {
            selUtils.addTestcaseDescription("Feature Usage Of Actions", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is Visible", "Dashboard Icon is NOT Visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isFeatureUsageOfActionsLabelVisible(), true, "Feature Usage Of Actions Label is Visible", "Feature Usage Of Actions Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isConnectionLabelVisible(), true, "Connection Label is Visible", "Connection Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isMonthSelectionLabelVisible(), true, "Month Selection label is Visible", "Month Selection label is NOT Visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in method - testFeatureUsageWidget" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testFeatureUsageWidget")
    public void testFeatureUsageDetailedPage() {
        try {
            selUtils.addTestcaseDescription("Feature Usage Of Actions Details Page", "description");
            pages.getKpiDashboardFeatureUsageOfActions().clickOnFeatureUsageOfActionsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().featureUsageDetailsWidgetHeader(), true, "Feature Usage Of Actions Label is Visible", "Feature Usage Of Actions Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isAgentIdLabelVisible(), true, "Agent ID Label is Visible", "Agent ID Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isAgentNameLabelVisible(), true, "Agent Name label is Visible", "Agent Name label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardFeatureUsageOfActions().isMTDLabelVisible(), true, "MTD label is Visible", "MTD label is NOT Visible"));
            pages.getKpiDashboardFeatureUsageOfActions().clickOnBackIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in method - testFeatureUsageDetailedPage" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}