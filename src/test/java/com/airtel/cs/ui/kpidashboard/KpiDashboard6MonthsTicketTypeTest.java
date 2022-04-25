package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;

public class KpiDashboard6MonthsTicketTypeTest extends Driver {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void SixMonthsTicketType() {
        try {
            selUtils.addTestcaseDescription("Six Months Ticket Type", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().is6MonthsTicketTypeLabelVisible(), true, "6 Months Ticket Type Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isFTRIconlVisible(), true, "FTR Icon is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isNFTRIconVisible(), true, "NFTR Icon is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isIssueTypeLabelVisible(), true, "Issue TypeL Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isConnectionLabelVisible(), true, "Connection Label is visible"));
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - SixMonthsTicketType" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void SixMonthsTicketTypeDetails() {
        try {
            selUtils.addTestcaseDescription("Six Months Ticket Type Details", "description");

            pages.getKpiDashboard6MonthsTicketType().clickOn6MonthsTicketDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isTicketTypeDetailsLabelVisible(), true, "Ticket Type Details Labe is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isMonthLabelVisible(), true, "Month Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isFTRLabelVisible(), true, "FTR Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isNFTRLabelVisible(), true, "NFTRLabel is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isFTRPercentageLabelVisible(), true, "FTR %Label is visible"));
            pages.getKpiDashboard6MonthsTicketType().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - SixMonthsTicketTypeDetails" + e.fillInStackTrace(), true);
        }

    }
}
