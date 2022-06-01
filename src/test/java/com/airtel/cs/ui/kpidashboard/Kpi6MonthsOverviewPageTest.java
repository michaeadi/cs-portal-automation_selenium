package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;


public class Kpi6MonthsOverviewPageTest extends Driver {


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void serviceLevelTrend() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is Visible","Dashboard Icon is NOT Visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isServiceLevelTrendVisible(), true, "Service Level Trend Graph is Visible", "Service Level Trend Graph is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isConnectionTypeDropDownVisible(), true, "Connection Type Drop Down is Visible", "Connection Type Drop Down is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isQueueTypeDropDownVisible(), true, "Queue Type Drop Down is Visible", "Queue Type Drop Down is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isIssueTypeDropDownVisible(), true, "Issue Type Drop Down is Visible", "Issue Type Drop Down is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isIssueSubTypeDropDownVisible(), true, "Issue Sub Type Drop Down is Visible", "Issue Sub Type Drop Down is NOT Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - serviceLevelTrend" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "serviceLevelTrend")
    public void slaPerformanceDetailsQueueWise() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");
            pages.getKpiDashboard6MonthsOverview().clickOnServiceLevelTrendDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSlaPerforrmanceDetailsQueueWiseLabelVisible(), true, "Sla Performance Details Queue Wise Label is Visible", "Sla Performance Details Queue Wise Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isQueueNameLabelVisible(), true, "Queue Name Label is Visible", "Queue Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label is Visible", "SLA Percentage Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Day wise Label is Visible", "Day wise Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Label is Visible", "Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isMonthLabelVisible(), true, "Month Label is Visible", "Month Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isMonthSelectionBoxVisible(), true, "Month Selection Box is Visible", "Month Selection Box is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDownloadReportIconVisible(), true, "Download Report Icon is Visible", "Download Report Icon is NOT Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - slaPerformanceDetailsQueueWise" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "serviceLevelTrend")
    public void slaPerformanceDetailsIssueTypeWise() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPerformanceDetailsIssueTypeLabelVisible(), true, "Sla Performance Details Queue Wise Label is Visible", "Sla Performance Details Queue Wise Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isIssueTypeLabelVisible(), true, "Issue Type Label is Visible", "Issue Type Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label is Visible", "SLA Percentage Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisibleIssueTypeWise(), true, "Day wise Label is Visible", "Day wise Label is NOT Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSLAPerformanceDetailsIssueTypeWise" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"serviceLevelTrend", "slaPerformanceDetailsQueueWise"})
    public void dayWiseInSlaPerformanceDetailsQueueWise() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");
            pages.getKpiDashboard6MonthsOverview().clickOnDayWiseInSlaPerformanceDetailsQueueWiseIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Label is Visible", "Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isQueueNameLabelVisible(), true, "Queue Name Label is Visible", "Queue Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label is Visible", "SLA Percentage Label is NOT Visible"));
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseInSlaPerformanceDetailsQueueWise" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"serviceLevelTrend", "slaPerformanceDetailsIssueTypeWise"})
    public void dayWiseInSlaPerformanceDetailsIssueType() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");
            pages.getKpiDashboard6MonthsOverview().clickOnDayWiseInSlaPerformanceDetailsIssueTypeIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Label is Visible", "Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isQueueNameLabelVisible(), true, "Queue Name Label is Visible", "Queue Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label is Visible", "SLA Percentage Label is NOT Visible"));
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseInSlaPerformanceDetailsIssueType" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "serviceLevelTrend")
    public void AgentPerformanceDetails() {
        try {
            selUtils.addTestcaseDescription("Agent Performance Details", "description");
            pages.getKpiDashboard6MonthsOverview().clickOnAgentPerformanceIcon();
            if (!pages.getKpiDashboard6MonthsOverview().checkNoResultMsg()) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentIdLabelVisible(), true, "Agent Id Label is Visible", "Agent Id Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentNameLabelVisible(), true, "Agent Name Label is Visible", "Agent Name Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label is Visible", "SLA Percentage Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDaywiseLabelVisible(), true, "Day wise Label is Visible", "Day wise Label is NOT Visible"));
            } else {
                commonLib.warning("No Records for this widget");
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - AgentPerformanceDetails" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"AgentPerformanceDetails"})
    public void DayWiseAgentPerformanceDetails() {
        try {
            selUtils.addTestcaseDescription("Day Wise Agent Performance Details", "description");
            pages.getKpiDashboard6MonthsOverview().clickOnDayWiseInAgentPerformanceIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentIdLabelVisible(), true, "Agent Id Label is Visible", "Agent Id Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isDateLabelVisible(), true, "Date Label is Visible", "Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isAgentNameLabelVisible(), true, "Agent Name Label is Visible", "Agent Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboard6MonthsOverview().isSLAPercentageLabelVisible(), true, "SLA Percentage Label is Visible", "SLA Percentage Label is NOT Visible"));
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
            pages.getKpiDashboard6MonthsOverview().clickOnBackIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in method - DayWiseAgentPerformanceDetails" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}

