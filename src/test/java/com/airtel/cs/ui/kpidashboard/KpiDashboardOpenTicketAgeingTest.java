package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.pagerepository.pagemethods.BasePage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class KpiDashboardOpenTicketAgeingTest extends BasePage {


    public KpiDashboardOpenTicketAgeingTest(WebDriver driver) {
        super(driver);
    }

    ObjectMapper mapper = new ObjectMapper();


    @Test(priority = 1, groups = {"RegressionTest", "ProdTest"})
    public void dashboardOpenTicketAgeing() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Ageing", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isLifetimeTillDateLabelVisible(), true, "Life time Till Date Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isOpenTicketAgeingLabelVisible(), true, "Open Ticket Ageing Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isConnectionLabelVisible(), true, "Connection Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isMonthLabelVisible(), true, "Month Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isIssueTypeLabelVisible(), true, "Issue Type"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isIssueSubTypeLabelVisible(), true, "Issue Sub Type"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isDownLoadIconlVisible(), true, "Download Icon "));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardOpenTicketAgeing" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 2, groups = {"RegressionTest", "ProdTest"})
    public void dashboardSlaBreachTicketAgeingIssueTypeQueue() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Ageing", "description");
            pages.getKpiDashboardOpenTicketAgeing().clickOpenTicketAgeingDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isSlabreachTicketAgeingIssueTypeAndQueueLabel(), true, "Life time Till Date Labe "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isIssueCodeLabellVisible(), true, "IssueCode Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isQueueNameLabellVisible(), true, "Queue Name Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isLessThanADayLabellVisible(), true, "Less Than A Day Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTwoToThreeDaysLabellVisible(), true, "Two To Three Days Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isThreeToTenDaysLabellVisible(), true, "Three To Ten Days Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTenToThrityDaysLabellVisible(), true, "Ten To Thrity Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isBeyond30DaysDaysLabellVisible(), true, "Beyond 30 Days Days Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isDownLoadIconlVisible(), true, "Download Icon "));
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSlaBreachTicketAgeingIssueTypeQueue" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 3, groups = {"RegressionTest", "ProdTest"})
    public void dashboardSlaBreachTicketAgeingNGAgent() {
        try {
            selUtils.addTestcaseDescription("Open Ticket Ageing", "description");
            pages.getKpiDashboardOpenTicketAgeing().clickOpenTicketAgeingDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isSlaBreachTicketAgeingAgentLabel(), true, "Life time Till Date Labe "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isAgentIdLabellVisible(), true, "IssueCode Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isAgentNameLabellVisible(), true, "Queue Name Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isLessThanADayLabellVisible(), true, "Less Than A Day Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTwoToThreeDaysLabellVisible(), true, "Two To Three Days Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isThreeToTenDaysLabellVisible(), true, "Three To Ten Days Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isTenToThrityDaysLabellVisible(), true, "Ten To Thirty Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isBeyond30DaysDaysLabellVisible(), true, "Beyond 30 Days Days Label "));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardOpenTicketAgeing().isDownLoadIconlVisible(), true, "Download Icon "));
            pages.getKpiDashboardOpenTicketAgeing().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardSlaBreachTicketAgeingNGAgent" + e.fillInStackTrace(), true);

        }
    }
}
