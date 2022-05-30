package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;

public class KpiOpenTicketAgeingTest extends Driver {


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void dashboardOpenTicketAgeing() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Ageing", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible", "KPI Dashboard Icon is NOT visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isLifetimeTillDateLabelVisible(), true, "Life time Till Date Label is Visible", "Life time Till Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isOpenTicketAgeingLabelVisible(), true, "Open Ticket Ageing Label is Visible", "Open Ticket Ageing Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isConnectionLabelVisible(), true, "Connection Label is Visible", "Connection Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isMonthLabelVisible(), true, "Month Label is Visible", "Month Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isIssueSubTypeLabelVisible(), true, "Issue Sub Type is Visible", "Issue Sub Type is NOT Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardOpenTicketAgeing" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void dashboardSlaBreachTicketAgeingIssueTypeQueue() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Ageing", "description");
            pages.getKpiDashboardOpenTicketAgeing().clickOpenTicketAgeingDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isSlabreachTicketAgeingIssueTypeAndQueueLabel(), true, "Life time Till Date Label is Visible", "Life time Till Date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isIssueCodeLabellVisible(), true, "IssueCode Label is Visible", "IssueCode Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isQueueNameLabellVisible(), true, "Queue Name Label is Visible", "Queue Name Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isLessThanADayLabellVisible(), true, "Less Than A Day Label is Visible", "Less Than A Day Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTwoToThreeDaysLabellVisible(), true, "Two To Three Days Label is Visible", "Two To Three Days Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isThreeToTenDaysLabellVisible(), true, "Three To Ten Days Label is Visible", "Three To Ten Days Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTenToThrityDaysLabellVisible(), true, "Ten To Thirty Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isBeyond30DaysDaysLabellVisible(), true, "Beyond 30 Days Days Label is Visible", "Beyond 30 Days Days Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isDownLoadIconlVisible(), true, "Download Icon is Visible", "Download Icon is NOT Visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSlaBreachTicketAgeingIssueTypeQueue" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void dashboardSlaBreachTicketAgeingAgent() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Ageing", "description");
            if (!pages.getKpiDashboard6MonthsOverview().checkNoResultMsg()) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isSlaBreachTicketAgeingAgentLabel(), true, "SLA Breach Ticket Ageing - Agent widget is Visible", "SLA Breach Ticket Ageing - Agent widget is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isAgentIdLabellVisible(), true, "Agent Id Label is Visible", "Agent Id Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isAgentNameLabelVisible(), true, "Agent Name Label is Visible", "Agent Name Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isLessThanADayLabellVisible(), true, "Less Than A Day Label is Visible", "Less Than A Day Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTwoToThreeDaysLabellVisible(), true, "Two To Three Days Label is Visible", "Two To Three Days Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isThreeToTenDaysLabellVisible(), true, "Three To Ten Days Label is Visible", "Three To Ten Days Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTenToThrityDaysLabellVisible(), true, "Ten To Thirty Label is Visible", "Ten To Thirty Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isBeyond30DaysDaysLabellVisible(), true, "Beyond 30 Days Days Label is Visible", "Beyond 30 Days Days Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isDownLoadIconlVisible(), true, "Download Icon is Visible", "Download Icon is NOT Visible"));
                pages.getKpiDashboardOpenTicketAgeing().clickOnBackIcon();
            } else {
                assertCheck.append(actions.assertEqualStringType(pages.getKpiDashboard6MonthsOverview().getNoResultText(), "No Result found", "No Result Found Message Matched Successfully", "No Result Found Message Matched NOT Matched"));
                commonLib.warning("No Records for this widget");
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSlaBreachTicketAgeingAgent" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
