package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;


public class KpiDashboard6MonthsOverviewPageTest extends Driver {


    ObjectMapper mapper = new ObjectMapper();


    @Test(priority = 1, groups = {"RegressionTest", "ProdTest"})
    public void dashboardServiceLevelTrend() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isServiceLevelTrendVisible(), true, "Service Level Trend "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isConnectionTypeDropDownVisible(), true, "Connection Type Drop Down "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isQueueTypeDropDownVisible(), true, "Queue Type Drop Down "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isIssueTypeDropDownVisible(), true, "Issue Type Drop Down "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isIssueSubTypeDropDownVisible(), true, "Issue Sub Type Drop Down "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardServiceLevelTrend" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 2, groups = {"RegressionTest", "ProdTest"})
    public void dashboardSlaPerformanceDetailsQueueWise
            () {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");

            pages.getKpiDashboard6MonthsOverview().clickOnServiceLevelTrendDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSlaPerforrmanceDetailsQueueWiseLabelVisible(), true, "Sla Perforrmance Details Queue Wise Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isQueueNameLabelVisible(), true, "Queue Name Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Daywise Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isMonthLabelVisible(), true, "Month Label Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isMonthSelectionBoxVisible(), true, "Month Selection Box Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDownloadReportIconVisible(), true, "Download Report Icon Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSlaPerformanceDetailsQueueWise" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 4, groups = {"RegressionTest", "ProdTest"})
    public void dashboardSLAPerformanceDetailsIssueTypeWise

            () {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPerformanceDetailsIssueTypeLabelVisible(), true, "Sla Perforrmance Details Queue Wise Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isIssueTypeLabelVisible(), true, "Issue Type Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Daywise Label Visible "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSLAPerformanceDetailsIssueTypeWise" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 5, groups = {"RegressionTest", "ProdTest"})
    public void dayWiseInSlaPerformanceDetailsQueueWise

            () {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");

            pages.getKpiDashboard6MonthsOverview().clickOnDayWiseInSlaPerformanceDetailsQueueWiseIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Daywise Label Visible "));
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseInSlaPerformanceDetailsQueueWise" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 6, groups = {"RegressionTest", "ProdTest"})
    public void dayWiseInSlaPerformanceDetailsIssueType

            () {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");

            pages.getKpiDashboard6MonthsOverview().clickOnDayWiseInSlaPerformanceDetailsIssueTypeIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Daywise Label Visible "));
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseInSlaPerformanceDetailsIssueType" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 7, groups = {"RegressionTest", "ProdTest"})
    public void AgentPerformanceDetails


            () {
        try {
            selUtils.addTestcaseDescription("Agent Performance Details", "description");

            pages.getKpiDashboard6MonthsOverview().clickOnAgentPerormanceIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentIdLabelVisible(), true, "Agent Id Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentNameLabelVisible(), true, "Agent Name Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Daywise Label Visible "));
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - AgentPerformanceDetails" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 8, groups = {"RegressionTest", "ProdTest"})
    public void DayWiseAgentPerformanceDetails


            () {
        try {
            selUtils.addTestcaseDescription("Day Wise Agent Performance Details", "description");

            pages.getKpiDashboard6MonthsOverview().clickOnDayWiseInAgentPerformanceIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentIdLabelVisible(), true, "Agent Id Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Labe Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentNameLabelVisible(), true, "Agent Name Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label Visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label Visible "));
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - DayWiseAgentPerformanceDetails" + e.fillInStackTrace(), true);

        }
    }
}

