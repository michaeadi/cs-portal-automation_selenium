package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboardQueueWiseSLAPerformancePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashbaordQueueWiseSLAPerformance extends BasePage {
    KpiDashboardQueueWiseSLAPerformancePage pageElements;

    public KpiDashbaordQueueWiseSLAPerformance(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboardQueueWiseSLAPerformancePage.class);

    }

    /**
     * This method is used to check Queue Wise SLA Performance Label is visible or not
     */
    public Boolean isQueueWiseSLAPerformanceLabelVisible() {
        Boolean status = isVisible(pageElements.queueWiseSLAPerformanceLabel);
        commonLib.info("Queue Wise SLA Performance Label is visible : " + status);
        return status;
    }



    /**
     * This method is used to check Total Ticket Label is visible or not
     */
    public Boolean isTotalTicketLabelVisible() {
        Boolean status = isVisible(pageElements.totalTicketLabel);
        commonLib.info("Total Ticket Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Queue Wise SLA Performance Details Icon
     */
    public void clickOnQueueWiseSLAPerformanceDetailsIcon() {
        commonLib.info("Going to click on Queue Wise SLA Performance Details Icon");
        if (isVisible(pageElements.queueWiseSLAPerformanceDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.queueWiseSLAPerformanceDetailsIcon);
        else {
           commonLib.error("Queue Wise SLA Performance Details Icon not Visible");
        }


    }

    /**
     * This method is used to check SLA PERFORMANCE DETAILS: QUEUE WISE Label is visible or not
     */
    public Boolean isSlaPerformanceDetailsQueueWiseLabelVisible() {
        Boolean status = isVisible(pageElements.slaPerformanceDetailsQueueWiseLabel);
        commonLib.info("SLA PERFORMANCE DETAILS: QUEUE WISE Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Queue Name Label is visible or not
     */
    public Boolean isQueueNameLabelVisible() {
        Boolean status = isVisible(pageElements.queueNameLabel);
        commonLib.info("Queue Name Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Total Ticket Allocated Label is visible or not
     */
    public Boolean isTotalTicketAllocatedLabelVisible() {
        Boolean status = isVisible(pageElements.totalTicketAllocatedLabel);
        commonLib.info("Total Ticket Allocated Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Ticket Cancelled Label is visible or not
     */
    public Boolean isTicketCancelledLabelVisible() {
        Boolean status = isVisible(pageElements.ticketCancelledLabel);
        commonLib.info("Ticket Cancelled  Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Ticket Closed Outside SLA Label is visible or not
     */
    public Boolean isTicketClosedOutsideSLALabelVisible() {
        Boolean status = isVisible(pageElements.ticketClosedOutsideSLALabel);
        commonLib.info("Ticket Closed Outside SLA Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Ticket Closed Within SLA Label is visible or not
     */
    public Boolean isTicketClosedWithinSLALabelVisible() {
        Boolean status = isVisible(pageElements.ticketClosedWithinSLALabel);
        commonLib.info("Ticket Closed Within SLA Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check SLA % Label is visible or not
     */
    public Boolean isSLAPercentageLabelVisible() {
        Boolean status = isVisible(pageElements.slaPercentageLabel);
        commonLib.info(" SLA % Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check SLA PERFORMANCE DETAILS: ISSUE TYPE WISE Label is visible or not
     */
    public Boolean isSlaDetailsIssueTypeWiseLabelVisible() {
        Boolean status = isVisible(pageElements.slaDetailsIssueTypeWiseLabel);
        commonLib.info("SLA PERFORMANCE DETAILS: ISSUE TYPE WISE Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Day wise Label  is visible or not
     */
    public Boolean isDayWiseLabelVisible() {
        Boolean status = isVisible(pageElements.dayWise);
        commonLib.info("Day wise Label  is visible : " + status);
        return status;
    }

    /**
     * This method is used to click On Day Wise SLA PERFORMANCE DETAILS: QUEUE WISE
     */
    public void clickDayWiseInSLADetailsQueueWise() {
        commonLib.info("click On Day Wise SLA PERFORMANCE DETAILS: QUEUE WISE Icon");
        if (isVisible(pageElements.dayWiseInSLADetailsQueueWise))
            clickAndWaitForLoaderToBeRemoved(pageElements.dayWiseInSLADetailsQueueWise);
        else {
            commonLib.error("Day Wise SLA PERFORMANCE DETAILS: QUEUE WISE Icon not Visible");
        }
    }

    /**
     * This method is used to check Day Wise In SLAPerformance Details Queue Wise Label  is visible or not
     */
    public Boolean isDayWiseInSLADetailsQueueWiseLabelVisible() {
        Boolean status = isVisible(pageElements.dayWiseInSLADetailsQueueWiseLabel);
        commonLib.info("Day Wise In SLAPerformance Details Queue Wise Label  is visible : " + status);
        return status;
    }

    /**
     * This method is used to click On Day Wise SLA PERFORMANCE DETAILS: ISSUE TYPE WISE
     */
    public void clickDayWiseInSLADetailsIssueTypeIcon() {
        commonLib.info("click On Day Wise SLA PERFORMANCE DETAILS: QUEUE WISE Icon");
        if (isVisible(pageElements.dayWiseInSLADetailsIssueTypeIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.dayWiseInSLADetailsIssueTypeIcon);
        else {
            commonLib.error("Day Wise SLA PERFORMANCE DETAILS: ISSUE TYPE WISE Icon not Visible");
        }
    }

    /**
     * This method is used to check Day Wise In SLAPerformance Details Issue Type  Wise Label  is visible or not
     */
    public Boolean isDayWiseInSLAPerformanceDetailsIssueTypeWiseLabelVisible() {
        Boolean status = isVisible(pageElements.dayWiseInSLAPerformanceDetailsIssueTypeWiseLabel);
        commonLib.info("Day Wise In SLAPerformance Details Issue Type  Wise Label  is visible : " + status);
        return status;
    }

    /**
     * This method is used to click On Day Wise Agent  PERFORMANCE
     */
    public void clickOnDayWiseAgentPerformanceDetailsIcon() {
        commonLib.info("click On Day Wise Agent PERFORMANCE Details Icon");
        if (isVisible(pageElements.dayWiseAgentPerformanceDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.dayWiseAgentPerformanceDetailsIcon);
        else {
            commonLib.error(" Day Wise Agent  PERFORMANCE Icon not Visible");
        }
    }

    /**
     * This method is used to check Date Label  is visible or not
     */
    public Boolean isDateLabelVisible() {
        Boolean status = isVisible(pageElements.date);
        commonLib.info("Date Label  is visible : " + status);
        return status;
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
     * This method is used to click on AGENT PERFORMANCE Icon
     */
    public void clickOnAgentPerformanceIcon() {
        commonLib.info("Going to click AGENT PERFORMANCE Icon");
        if (isVisible(pageElements.agentPerformanceLabel))
            clickAndWaitForLoaderToBeRemoved(pageElements.agentPerformanceLabel);
        else {
            commonLib.error(" AGENT PERFORMANCE Icon Icon not Visible");
        }

    }

    /**
     * This method is used to check AGENT PERFORMANCE Label  is visible or not
     */
    public Boolean isAgentPerformanceLabelVisible() {
        Boolean status = isVisible(pageElements.agentPerformanceIcon);
        commonLib.info("AGENT PERFORMANCE  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check AGENT ID Label  is visible or not
     */
    public Boolean isAgentIdLabelVisible() {
        Boolean status = isVisible(pageElements.agentIdeLabel);
        commonLib.info("AGENT Id  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check AGENT Name Label  is visible or not
     */
    public Boolean isAgentNameLabelVisible() {
        Boolean status = isVisible(pageElements.agentNameLabel);
        commonLib.info("AGENT Name  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Issue Type Label  is visible or not
     */
    public Boolean isIssueTypeLabelVisible() {
        Boolean status = isVisible(pageElements.issueTypeLabel);
        commonLib.info("Issue Type  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Day Wise Agent Performance Label  is visible or not
     */
    public Boolean isDayWiseAgentPerformanceLabelVisible() {
        Boolean status = isVisible(pageElements.dayWiseAgentPerformanceLabel);
        commonLib.info(" Day Wise Agent Performance Label visible : " + status);
        return status;
    }


}
