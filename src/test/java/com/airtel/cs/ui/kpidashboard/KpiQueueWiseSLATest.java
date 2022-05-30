package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;

public class KpiQueueWiseSLATest extends Driver {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void queueWiseSLAPerformance() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is Visible", "Dashboard Icon is NOT Visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueWiseSLAPerformanceLabelVisible(), true, "Queue Wise SLA Performance Label is Visible", "Queue Wise SLA Performance Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketLabelVisible(), true, "Total Ticket Label is Visible", "Total Ticket Label is NOT Visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in method - queueWiseSLAPerformance" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSLADetailedQueueWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Queue Wise ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnQueueWiseSLAPerformanceDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSlaPerformanceDetailsQueueWiseLabelVisible(), true, "Queue Wise SLA Performance Detailed Page Label is Visible", "Queue Wise SLA Performance Detailed Page Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueNameLabelVisible(), true, "Queue name Label is Visible", "Queue name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseLabelVisible(), true, "Day wise Label is Visible", "Day wise Label is NOT Visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in method - testSLADetailedQueueWise" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSLADetailedQueueWise")
    public void testDayWiseSLADetailedQueueWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Queue Wise ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickDayWiseInSLADetailsQueueWise();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseInSLADetailsQueueWiseLabelVisible(), true, "Day Wise In SLA Performance Details Queue Wise Label is Visible", "Day Wise In SLA Performance Details Queue Wise Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDateLabelVisible(), true, "Date  Label is Visible", "Date  Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueNameLabelVisible(), true, "Queue Name  Label is Visible", "Queue Name  Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLAPercentageLabelVisible(), true, "SLA % Label is Visible", "SLA % Label is NOT Visible"));
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnBackIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in method - testDayWiseSLADetailedQueueWise" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSLADetailedIssueTypeWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Issue Type  Wise ", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSlaDetailsIssueTypeWiseLabelVisible(), true, "Queue Wise SLA Performance Detailed Page Issue Type Label is Visible", "Queue Wise SLA Performance Detailed Page Issue Type Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isIssueTypeLabelVisible(), true, "Issue Type  Label is Visible", "Issue Type  Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueNameLabelVisible(), true, "Queue name Label is Visible", "Queue name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseLabelVisible(), true, "Day wise Label is Visible", "Day wise Label is NOT Visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in method - testSLADetailedIssueTypeWise" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSLADetailedIssueTypeWise")
    public void testDayWiseSLADetailedIssueTypeWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Queue Wise ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickDayWiseInSLADetailsIssueTypeIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseInSLADetailsQueueWiseLabelVisible(), true, "Day Wise In SLA Performance Details Issue Type  Wise Label is Visible", "Day Wise In SLA Performance Details Issue Type  Wise Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDateLabelVisible(), true, "Date Label is Visible", "Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueNameLabelVisible(), true, "Agent Name Label is Visible", "Agent Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLAPercentageLabelVisible(), true, "SLA % Label is Visible", "SLA % Label is NOT Visible"));
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnBackIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in method - testDayWiseSLADetailedIssueTypeWise" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testAgentPerformanceTab() {
        try {
            selUtils.addTestcaseDescription("Agent PerformanceLabel ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnAgentPerformanceIcon();
            if (!pages.getKpiDashboard6MonthsOverview().checkNoResultMsg()) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentPerformanceLabelVisible(), true, "Agent Performance Label is Visible", "Agent Performance Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentIdLabelVisible(), true, "Agent ID Label is Visible", "Agent ID Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentNameLabelVisible(), true, "Agent Name Label is Visible", "Agent Name Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseLabelVisible(), true, "Day wise Label is Visible", "Day wise Label is NOT Visible"));
            } else {
                commonLib.warning("No Records for this widget");
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - testAgentPerformanceTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testAgentPerformanceTab")
    public void testDayWiseAgentPerformanceDetails() {
        try {
            selUtils.addTestcaseDescription("Day Wise Agent Performance Details Label Visible ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnDayWiseAgentPerformanceDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseAgentPerformanceLabelVisible(), true, "Day Wise Agent Performance Label is Visible", "Day Wise Agent Performance Label is Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDateLabelVisible(), true, "Date Label is Visible", "Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentIdLabelVisible(), true, "Agent ID Label is Visible", "Agent ID Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentNameLabelVisible(), true, "Agent Name Label is Visible", "Agent Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label is Visible", "Total Ticket Allocated Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label is Visible", "Ticket Cancelled Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label is Visible", "Ticket Closed Outside SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label is Visible", "Ticket Closed Within SLA Label is NOT Visible"));
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnBackIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in method - testDayWiseAgentPerformanceDetails" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
