package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

public class KpiDashbaordQueueWiseSLAPerformancePageTest extends Driver {
    ObjectMapper mapper = new ObjectMapper();

    @Test(priority = 1, groups = {"RegressionTest", "ProdTest"})
    public void queueWiseSLAPerformance() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueWiseSLAPerformanceLabelVisible(), true, "Queue Wise SLA Performance Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketLabelVisible(), true, "Total Ticket Label "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - queueWiseSLAPerformance" + e.fillInStackTrace(), true);

        }

    }
    @Test(priority = 2, groups = {"RegressionTest", "ProdTest"})
    public void queueWiseSLAPerformanceDetailsQueueWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Queue Wise ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnQueueWiseSLAPerformanceDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSlaPerformanceDetailsQueueWiseLabelVisible(), true, "Queue Wise SLA Performance Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueNameLabelVisible(), true, "Queue name  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseLabelVisible(), true, "Day wise Label "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - queueWiseSLAPerformanceDetailsQueueWise" + e.fillInStackTrace(), true);

        }

    }
    @Test(priority = 3, groups = {"RegressionTest", "ProdTest"})
    public void queueWiseSLAPerformanceDetailsIssueTypeWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Issue Type  Wise ", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSlaPerformanceDetailsIssueTypeWiseLabelVisible(), true, "Queue Wise SLA Performance Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isIssueTypeLabelVisible(), true, "Issue Type  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isQueueNameLabelVisible(), true, "Queue name  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseLabelVisible(), true, "Day wise Label "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - queueWiseSLAPerformanceDetailsIssueTypeWise" + e.fillInStackTrace(), true);

        }

    }
    @Test(priority = 3, groups = {"RegressionTest", "ProdTest"})
    public void agentPerformanceLabelVisible() {
        try {
            selUtils.addTestcaseDescription("Agent PerformanceLabel ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnAgentPerformanceIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentPerformanceLabelVisible(), true, "Queue Wise SLA Performance Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentIdLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentNameLabelVisible(), true, "Agent Name  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseLabelVisible(), true, "Day wise Label "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - agentPerformanceLabelVisible" + e.fillInStackTrace(), true);

        }

    }
    @Test(priority = 4, groups = {"RegressionTest", "ProdTest"})
    public void dayWiseAgentPerformanceDetailsLabelVisible() {
        try {
            selUtils.addTestcaseDescription("Day Wise Agent Performance Details Label Visible ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnDayWiseAgentPerformanceDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseAgentPerformanceLabelVisible(), true, "Queue Wise SLA Performance Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDateLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentIdLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentNameLabelVisible(), true, "Agent Name  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA  Label "));
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnBackIcon();


            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseAgentPerformanceDetailsLabelVisible" + e.fillInStackTrace(), true);

        }

    }
    @Test(priority = 5, groups = {"RegressionTest", "ProdTest"})
    public void dayWiseInSLAPerformanceDetailsQueueWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Queue Wise ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnQueueWiseSLAPerformanceDetailsIcon();
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnDayWiseInSLAPerformanceDetailsQueueWiseIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseInSLAPerformanceDetailsQueueWiseLabelVisible(), true, "Day Wise In SLA Performance Details Queue Wise Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDateLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentIdLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentNameLabelVisible(), true, "Agent Name  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA  Label "));
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnBackIcon();

            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseInSLAPerformanceDetailsQueueWise" + e.fillInStackTrace(), true);

        }

    }
    @Test(priority = 6, groups = {"RegressionTest", "ProdTest"})
    public void dayWiseInSLAPerformanceDetailsIssueTypeWise() {
        try {
            selUtils.addTestcaseDescription("Queue Wise SLA Performance Details Queue Wise ", "description");
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnDayWiseInSLAPerformanceDetailsIssueTypeWiseIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDayWiseInSLAPerformanceDetailsQueueWiseLabelVisible(), true, "Day Wise In SLA Performance Details Issue Type  Wise Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isDateLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentIdLabelVisible(), true, "Agent ID  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isAgentNameLabelVisible(), true, "Agent Name  Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTotalTicketAllocatedLabelVisible(), true, "Total Ticket Allocated Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketCancelledLabelVisible(), true, "Ticket Cancelled Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedOutsideSLALabelVisible(), true, "Ticket Closed Outside SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isTicketClosedWithinSLALabelVisible(), true, "Ticket Closed Within SLA Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardQueueWiseSLAPerformance().isSLALabelVisible(), true, "SLA  Label "));
            pages.getKpiDashboardQueueWiseSLAPerformance().clickOnBackIcon();

            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dayWiseInSLAPerformanceDetailsIssueTypeWise" + e.fillInStackTrace(), true);

        }

    }
}
