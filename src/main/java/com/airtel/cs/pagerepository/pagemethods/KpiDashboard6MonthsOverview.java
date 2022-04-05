package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboard6MonthsOverviewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashboard6MonthsOverview  extends BasePage {
    KpiDashboard6MonthsOverviewPage pageElements;
    public KpiDashboard6MonthsOverview(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboard6MonthsOverviewPage.class);
    }
    /**
     * This method is used to check Service Level Trend  is visible or not
     */
    public Boolean isServiceLevelTrendVisible() {
        Boolean status = isVisible(pageElements.serviceLevelTrend);
        commonLib.pass("Service Level Trend  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Connection Type Drop Down  is visible or not
     */
    public Boolean isConnectionTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.connectionTypeDropDown);
        commonLib.pass("Connection Type Drop Down  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Queue Type Drop Down is visible or not
     */
    public Boolean isQueueTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.queueTypeDropDown);
        commonLib.pass("Queue Type Drop Down is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Issue Type Drop Down  is visible or not
     */
    public Boolean isIssueTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.issueTypeDropDown);
        commonLib.pass("Issue Type Drop Down is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Issue sub Type Drop Down  is visible or not
     */
    public Boolean isIssueSubTypeDropDownVisible() {
        Boolean status = isVisible(pageElements.issueSubTypeDropDown);
        commonLib.pass("Issue sub Type Drop Down  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Download Report Icon  is visible or not
     */
    public Boolean isDownloadReportIconVisible() {
        Boolean status = isVisible(pageElements.downloadReportIcon);
        commonLib.pass("Download Report Icon  is visible : " + status);
        return status;
    }
    /**
     * This method is used to click On Service Level Trend Details Icon
     */
    public void clickOnServiceLevelTrendDetailsIcon() {
        commonLib.pass("click On Service Level Trend Details Icon");
        if (isVisible(pageElements.serviceLevelTrendDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.serviceLevelTrendDetailsIcon);
    }
    /**
     * This method is used to check Queue Name Label  is visible or not
     */
    public Boolean isQueueNameLabelVisible() {
        Boolean status = isVisible(pageElements.queueNameLabel);
        commonLib.pass("Queue Name Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Total Ticket Allocated Label  is visible or not
     */
    public Boolean isTotalTicketAllocatedLabelVisible() {
        Boolean status = isVisible(pageElements.totalTicketAllocated);
        commonLib.pass("Total Ticket Allocated Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Ticket Cancelled Label  is visible or not
     */
    public Boolean isTicketCancelledLabelVisible() {
        Boolean status = isVisible(pageElements.ticketCancelled);
        commonLib.pass("Ticket Cancelled Allocated Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Ticket Closed Outside SLA Label  is visible or not
     */
    public Boolean isTicketClosedOutsideSLALabelVisible() {
        Boolean status = isVisible(pageElements.ticketClosedOutsideSLA);
        commonLib.pass("Ticket Closed Outside SLA Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Ticket Closed Within SLA Label  is visible or not
     */
    public Boolean isTicketClosedWithinSLALabelVisible() {
        Boolean status = isVisible(pageElements.ticketClosedWithinSLA);
        commonLib.pass("Ticket Closed Within SLA Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check SLA % Label  is visible or not
     */
    public Boolean isSLAPercentageLabelVisible() {
        Boolean status = isVisible(pageElements.SLAPercentage);
        commonLib.pass("SLA % Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Day wise Label  is visible or not
     */
    public Boolean isDaywiseLabelVisible() {
        Boolean status = isVisible(pageElements.daywise);
        commonLib.pass("Day wise Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Month Label  is visible or not
     */
    public Boolean isMonthLabelVisible() {
        Boolean status = isVisible(pageElements.month);
        commonLib.pass("Month Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Day Label  is visible or not
     */
    public Boolean isDateLabelVisible() {
        Boolean status = isVisible(pageElements.date);
        commonLib.pass("Date  Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Month Selection Box Label  is visible or not
     */
    public Boolean isMonthSelectionBoxVisible() {
        Boolean status = isVisible(pageElements.monthSelectionBox);
        commonLib.pass("Month Selection Box  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Sla Performance Details: Queue Wise Label  is visible or not
     */
    public Boolean isSlaPerforrmanceDetailsQueueWiseLabelVisible() {
        Boolean status = isVisible(pageElements.slaPerforrmanceDetailsQueueWiseLabel);
        commonLib.pass("Sla Performance Details Queue Wise Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check SLA PERFORMANCE DETAILS: ISSUE TYPE WISE Label  is visible or not
     */
    public Boolean isSLAPerformanceDetailsIssueTypeLabelVisible() {
        Boolean status = isVisible(pageElements.slaPerformanceDetailsIssueTypeLabel);
        commonLib.pass("SLA PERFORMANCE DETAILS: ISSUE TYPE WISE Label  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Issue Type  Label  is visible or not
     */
    public Boolean isIssueTypeLabelVisible() {
        Boolean status = isVisible(pageElements.issueTypeLabel);
        commonLib.pass("Issue Type   is visible : " + status);
        return status;
    }
    /**
     * This method is used to click On Day WiseIn Sla Performance Details Queue Wise Icon
     */
    public void clickOnDayWiseInSlaPerformanceDetailsQueueWiseIcon() {
        commonLib.pass("click On Service Level Trend Details Icon");
        if (isVisible(pageElements.dayWiseInSlaPerformanceDetailsQueueWiseIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.dayWiseInSlaPerformanceDetailsQueueWiseIcon);
    }
    /**
     * This method is used to click on Back Icon
     */
    public void clickOnBackIcon() {
        commonLib.info("Going to click Back Icon");
        if (isVisible(pageElements.backIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.backIcon);

    }
    /**
     * This method is used to click On Day WiseIn Sla Performance Details Issue Type Icon
     */
    public void clickOnDayWiseInSlaPerformanceDetailsIssueTypeIcon() {
        commonLib.pass("click On Day WiseIn Sla Performance Details Issue Type Icon");
        if (isVisible(pageElements.dayWiseInSlaPerformanceDetailsIssueTypeIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.dayWiseInSlaPerformanceDetailsIssueTypeIcon);
    }
    /**
     * This method is used to click Agent Performance Details Queue Wise Icon
     */
    public void clickOnAgentPerormanceIcon() {
        commonLib.pass("click On Agent performance Icon");
        if (isVisible(pageElements.agentPerormanceIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.agentPerormanceIcon);
    }
    /**
     * This method is used to check Agent Id Label  is visible or not
     */
    public Boolean isAgentIdLabelVisible() {
        Boolean status = isVisible(pageElements.agentIdLabel);
        commonLib.pass("Agent ID   is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Agent Name Label  is visible or not
     */
    public Boolean isAgentNameLabelVisible() {
        Boolean status = isVisible(pageElements.agentNameLabel);
        commonLib.pass("Agent Name   is visible : " + status);
        return status;
    }
    /**
     * This method is used to click On Day WiseIn Agent Performance Icon
     */
    public void clickOnDayWiseInAgentPerformanceIcon() {
        commonLib.pass("click On Day WiseIn Agent Performance Icon");
        if (isVisible(pageElements.dayWiseInAgentPerformanceIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.dayWiseInAgentPerformanceIcon);
    }


}
