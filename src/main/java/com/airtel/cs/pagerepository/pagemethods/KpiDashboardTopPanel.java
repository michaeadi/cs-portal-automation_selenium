package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboardTopPanelPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KpiDashboardTopPanel extends BasePage {

    KpiDashboardTopPanelPage pageElements;

    public KpiDashboardTopPanel(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, KpiDashboardTopPanelPage.class);
    }


    /**
     * This method is used to check Dashboard Icon is visible or not
     */
    public Boolean isKpiDashboardIconVisible() {
        Boolean status = isVisible(pageElements.kpiDashboardIcon);
        commonLib.info("Dashboard Icon  is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Dashboard Icon
     */
    public void clickOnCsDashboardIcon() {
        commonLib.info("Going to click Dashboard Icon");
        if (isVisible(pageElements.csDashboardIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.csDashboardIcon);
        else {
            commonLib.error(" Dashboard Icon not Visible");
        }

    }

    /**
     * This method is used to check Last Refresh Time  is visible or not
     */
    public Boolean isLastRefreshTimeVisible() {
        Boolean status = isVisible(pageElements.lastRefreshTime);
        commonLib.info(" Last Refresh Time  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Dashboard  is visible or not
     */
    public Boolean isDashboardVisible() {
        Boolean status = isVisible(pageElements.dashboard);
        commonLib.info("Dashboard   is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Refresh Icon  is visible or not
     */
    public Boolean isRefreshIconVisible() {
        Boolean status = isVisible(pageElements.refreshIcon);
        commonLib.info("Refresh Icon  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Ticket - Overview   is visible or not
     */
    public Boolean isOpenTicketOverviewLabelVisible() {
        Boolean status = isVisible(pageElements.openTicketOverviewLabel);
        commonLib.info("Open Ticket Overview is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Tickets Beyond SLA   is visible or not
     */
    public Boolean isOpenTicketsBeyondSLALabelVisible() {
        Boolean status = isVisible(pageElements.openTicketsBeyondSLALabel);
        commonLib.info(" Open Tickets Beyond SLA  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Tickets Under SLA   is visible or not
     */
    public Boolean isOpenTicketsUnderSLALabelVisible() {
        Boolean status = isVisible(pageElements.openTicketsUnderSLALabel);
        commonLib.info("Open Tickets Under SLA is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Breaching Within 15 Mins   is visible or not
     */
    public Boolean isBreachingWithin15MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingWithin15MinsLabel);
        commonLib.info("Breaching Within 15 Mins is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Breaching Within 15 - 60 Mins   is visible or not
     */
    public Boolean isBreachingWithin15To60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingWithin15To60MinsLabel);
        commonLib.info("Breaching Within 15 - 60 Mins is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Breaching Within > 60 Mins   is visible or not
     */
    public Boolean isBreachingGreaterThan60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingWithin60MinsLabel);
        commonLib.info("Breaching Within > 60 Mins visible : " + status);
        return status;
    }

    /**
     * This method is used to click On Open Tickets Beyond SLA Details Icon
     */
    public void clickOnOpenTicketsBeyondSLADetailsIcon() {
        commonLib.info("Click On Open Tickets Beyond SLA Details Icon");
        if (isVisible(pageElements.openTicketsBeyondSLADetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.openTicketsBeyondSLADetailsIcon);
        else {
            commonLib.error("Open Tickets Beyond SLA Details Icon not Visible");
        }
    }

    /**
     * This Method is used to click on detailed icon over top panel if ticket count is more than 0
     *
     * @param element    the element
     * @param infoMsg    the info msg
     * @param warningMsg the warning msg
     * @param errorMsg   the error msg
     * @return ticket found or not?
     */
    public boolean clickDetailIconIfTicketFound(By element, String infoMsg, String warningMsg, String errorMsg) {
        boolean isTicketFound = false;
        commonLib.info(infoMsg);
        if (isVisible(element)) {
            if (!getAttribute(element, "class", false).contains("disabled")) {
                clickAndWaitForLoaderToBeRemoved(element);
                isTicketFound = true;
            } else {
                commonLib.warning(warningMsg);
            }
        } else {
            commonLib.error(errorMsg);
        }
        return isTicketFound;
    }

    /**
     * This method is used to click On Open Tickets Under SLA Details Icon
     */
    public boolean clickOpenTicketsUnderSLAIcon() {
        return clickDetailIconIfTicketFound(pageElements.openTicketsUnderSLALDetailsIcon, "Click On Tickets Under SLA Details Icon", "Open Tickets Under SLA Details Icon not Clickable as ticket Count is 0", "Open Tickets Under SLA Details Icon not Visible");
    }

    /**
     * This method is used to click On Breaching Within 15 Min Details Icon
     */
    public boolean clickBreachWithin15MinIcon() {
        return clickDetailIconIfTicketFound(pageElements.breachingWithin15MinsDetailsIcon, "Click On  Breaching Within 15 Mins Details Icon", "Breaching Within 15 Mins Details Icon not Clickable as ticket Count is 0", "Breaching Within 15 Mins Details Icon not Visible");
    }

    /**
     * This method is used to click On Breaching Within 15 To 60 Min Details Icon
     */
    public boolean clickBreachWithin15To60MinIcon() {
        return clickDetailIconIfTicketFound(pageElements.breachingWithin15To60MinsDetailsIcon, "Click On Breaching Within 15 To 60 Mins Details Icon", "Breaching Within 15 To 60 Min Details Icon not Clickable as ticket Count is 0", "Breaching Within 15 To 60 Min Details Icon not Visible");
    }

    /**
     * This method is used to click On Breaching With in 60 Min Details Icon
     */
    public boolean clickBreachingMoreThan60MinIcon() {
        return clickDetailIconIfTicketFound(pageElements.breachingGreaterThan60MinsDetailsIcon, "Click On Breaching more than 60 Mins Details Icon", "Breaching more than 60 Min Details Icon not Clickable as ticket Count is 0", "Breaching With in 60 Mins Details Icon not Visible");
    }

    /**
     * This method is used to check Ticket ID   is visible or not
     */
    public Boolean isTicketIdLabelVisible() {
        Boolean status = isVisible(pageElements.ticketIdLabel);
        commonLib.info("Ticket ID Lable is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Priority   is visible or not
     */
    public Boolean isPriorityLabelVisible() {
        Boolean status = isVisible(pageElements.priorityLabel);
        commonLib.info("Priority Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check State   is visible or not
     */
    public Boolean isStateLabelVisible() {
        Boolean status = isVisible(pageElements.stateLabel);
        commonLib.info("State Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Creation date   is visible or not
     */
    public Boolean isCreationDateLabelVisible() {
        Boolean status = isVisible(pageElements.creationDateLabel);
        commonLib.info("Creation Date Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Created By  is visible or not
     */
    public Boolean isCreatedByLabelVisible() {
        Boolean status = isVisible(pageElements.createdByLabel);
        commonLib.info("Created By Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Queue   is visible or not
     */
    public Boolean isQueueLabelVisible() {
        Boolean status = isVisible(pageElements.queueLabel);
        commonLib.info("Queue Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Source   is visible or not
     */
    public Boolean isSourceLabelVisible() {
        Boolean status = isVisible(pageElements.sourceLabel);
        commonLib.info("Source Label is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Tickets Beyond SLA   is visible or not
     */
    public Boolean isDetailsOpenTicketsBeyondSLALabelVisible() {
        Boolean status = isVisible(pageElements.detailsOpenTicketsBeyondSLALabel);
        commonLib.info("Open Ticket Overview is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Tickets Under SLA   is visible or not
     */
    public Boolean isDetailsOpenTicketsUnderSLALabelVisible() {
        Boolean status = isVisible(pageElements.detailsOpenTicketsUnderSLALabel);
        boolean clickable = isClickable(pageElements.detailsOpenTicketsUnderSLALabel);
        commonLib.info("Open Ticket Overview is visible : " + status);
        commonLib.info("Open Ticket Overview is clickable" + clickable);
        return status;
    }

    /**
     * This method is used to check Breaching Within 15 Mins   is visible or not
     */
    public Boolean isDetailsBreachingWithin15MinsLabelVisible() {
        Boolean status = isVisible(pageElements.detailsBreachingWithin15MinsLabel);
        commonLib.info("Open Ticket Overview is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Breaching Within 15 - 60 Mins   is visible or not
     */
    public Boolean isDetailsBreachingWithin15To60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.detailsBreachingWithin15To60MinsLabel);
        commonLib.info("Open Ticket Overview is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Breaching Within > 60 Mins   is visible or not
     */
    public Boolean isBreachingMoreThan60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingMoreThan60MinsLabel);
        commonLib.info("Open Ticket Overview is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Back Icon
     */
    public void clickOnBackIcon() {
        commonLib.info("Going to click Back Icon");
        if (isVisible(pageElements.backIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.backIcon);
        else {
            commonLib.error("Back Icon not Visible");
        }

    }

    /**
     * This method is use to hover on Kpi Dashboard
     */
    public void hoverOnKpiDashboardIcon() {
        commonLib.info("Hover KPI dashboard icon");
        hoverOverElement(pageElements.kpiDashboardIcon);
    }

    /**
     * This Method is used to get the ticket count under SLA
     *
     * @return ticket count
     */
    public String ticketCountUnderSLA() {
        commonLib.info("Getting Ticket Count under SLA");
        return getText(pageElements.ticketCountUnderSLA);
    }

    /**
     * This Method is used to get the ticket count within 15 min SLA
     *
     * @return ticket count
     */
    public String ticketCountWithin15Min() {
        commonLib.info("Getting Ticket Count within 15 min SLA");
        return getText(pageElements.ticketCountWithin15Min);
    }

    /**
     * This Method is used to get the ticket count within 15 and 60 min SLA
     *
     * @return ticket count
     */
    public String ticketCountWithin15and60Min() {
        commonLib.info("Getting Ticket Count within 15 and 60 min SLA");
        return getText(pageElements.ticketCountWithin15n60Min);
    }

    /**
     * This Method is used to get the ticket count within 15 and 60 min SLA
     *
     * @return ticket count
     */
    public String ticketCountMoreThan60Min() {
        commonLib.info("Getting Ticket Count more than 60 min SLA");
        return getText(pageElements.ticketCountMoreThan60Min);
    }
}
