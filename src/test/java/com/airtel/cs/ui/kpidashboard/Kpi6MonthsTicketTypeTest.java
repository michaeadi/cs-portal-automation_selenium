package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;

public class Kpi6MonthsTicketTypeTest extends Driver {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void SixMonthsTicketType() {
        try {
            selUtils.addTestcaseDescription("Six Months Ticket Type", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible", "Dashboard Icon is NOT visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().is6MonthsTicketTypeLabelVisible(), true, "6 Months Ticket Type Label is visible", "6 Months Ticket Type Label is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isFTRIconlVisible(), true, "FTR Icon is visible", "FTR Icon is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isNFTRIconVisible(), true, "NFTR Icon is visible", "NFTR Icon is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isIssueTypeLabelVisible(), true, "Issue TypeL Label is visible", "Issue TypeL Label is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isConnectionLabelVisible(), true, "Connection Label is visible", "Connection Label is NOT visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in method - SixMonthsTicketType" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void SixMonthsTicketTypeDetails() {
        try {
            selUtils.addTestcaseDescription("Six Months Ticket Type Details", "description");
            pages.getKpiDashboard6MonthsTicketType().clickOn6MonthsTicketDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isTicketTypeDetailsLabelVisible(), true, "Ticket Type Details Label is visible", "Ticket Type Details Label is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isMonthLabelVisible(), true, "Month Label is visible", "Month Label is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isFTRLabelVisible(), true, "FTR Label is visible", "FTR Label is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isNFTRLabelVisible(), true, "NFTR Label is visible", "NFTR Label is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsTicketType().isFTRPercentageLabelVisible(), true, "FTR %Label is visible", "FTR %Label is NOT visible"));
            pages.getKpiDashboard6MonthsTicketType().clickOnBackIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in method - SixMonthsTicketTypeDetails" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
