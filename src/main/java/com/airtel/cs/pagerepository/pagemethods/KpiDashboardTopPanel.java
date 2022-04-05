package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.KpiDashboardTopPanelPage;
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
        commonLib.pass("Dashboard Icon  is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Dashboard Icon
     */
    public void clickOnCsDashboardIcon() {
        commonLib.info("Going to click Dashboard Icon");
        if (isVisible(pageElements.csDashboardIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.csDashboardIcon);

    }
    /**
     * This method is used to check Last Refresh Time  is visible or not
     */
    public Boolean isLastRefreshTimeVisible() {
        Boolean status = isVisible(pageElements.lastRefreshTime);
        commonLib.pass(" Last Refresh Time  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Dashboard  is visible or not
     */
    public Boolean isDashboardVisible() {
        Boolean status = isVisible(pageElements.dashboard);
        commonLib.pass("Dashboard   is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Refresh Icon  is visible or not
     */
    public Boolean isRefreshIconVisible() {
        Boolean status = isVisible(pageElements.refreshIcon);
        commonLib.pass("Refresh Icon  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Ticket - Overview   is visible or not
     */
    public Boolean isOpenTicketOverviewLabelVisible() {
        Boolean status = isVisible(pageElements.openTicketOverviewLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Open Tickets Beyond SLA   is visible or not
     */
    public Boolean isOpenTicketsBeyondSLALabelVisible() {
        Boolean status = isVisible(pageElements.openTicketsBeyondSLALabel);
        commonLib.pass(" Open Tickets Beyond SLA  is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Open Tickets Under SLA   is visible or not
     */
    public Boolean isOpenTicketsUnderSLALabelVisible() {
        Boolean status = isVisible(pageElements.openTicketsUnderSLALabel);
        commonLib.pass("Open Tickets Under SLA is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within 15 Mins   is visible or not
     */
    public Boolean isBreachingWithin15MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingWithin15MinsLabel);
        commonLib.pass("Breaching Within 15 Mins is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within 15 - 60 Mins   is visible or not
     */
    public Boolean isBreachingWithin15To60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingWithin15To60MinsLabel);
        commonLib.pass("Breaching Within 15 - 60 Mins is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within > 60 Mins   is visible or not
     */
    public Boolean isBreachingGreaterThan60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.breachingWithin60MinsLabel);
        commonLib.pass("Breaching Within > 60 Mins visible : " + status);
        return status;
    }

    /**
     * This method is used to click On Open Tickets Beyond SLA Details Icon
     */
    public void clickOnOpenTicketsBeyondSLADetailsIcon() {
        commonLib.pass("Click On Open Tickets Beyond SLA Details Icon");
        if (isVisible(pageElements.openTicketsBeyondSLADetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.openTicketsBeyondSLADetailsIcon);
    }
    /**
     * This method is used to click On Open Tickets Under SLA Details Icon
     */
    public void clickOnOpenTicketsUnderSLALDetailsIcon() {
        commonLib.pass("Click On Tickets Under SLA Details Icon");
        if (isVisible(pageElements.openTicketsUnderSLALDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.openTicketsUnderSLALDetailsIcon);
    }
    /**
     * This method is used to click On Breaching Within 15 Mins Details Icon
     */
    public void clickOnBreachingWithin15MinsDetailsIcon() {
        commonLib.pass("Click On  Breaching Within 15 Mins Details Icon");
        if (isVisible(pageElements.breachingWithin15MinsDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.breachingWithin15MinsDetailsIcon);
    }
    /**
     * This method is used to click On Breaching Within 15 To 60 Mins Details Icon
     */
    public void clickOnBreachingWithin15To60MinsDetailsIcon() {
        commonLib.pass("Click On Breaching Within 15 To 60 Mins Details Icon");
        if (isVisible(pageElements.breachingWithin15To60MinsDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.breachingWithin15To60MinsDetailsIcon);
    }
    /**
     * This method is used to click On Breaching With in 60 Mins Details Icon
     */
    public void clickOnBreachingGreaterThan60MinsDetailsIcon() {
        commonLib.pass("Click On Breaching With in 60 Mins Details Icon");
        if (isVisible(pageElements.breachingGreaterThan60MinsDetailsIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.breachingGreaterThan60MinsDetailsIcon);
    }
    /**
     * This method is used to check Ticket ID   is visible or not
     */
    public Boolean isTicketIdLableVisible() {
        Boolean status = isVisible(pageElements.ticketIdLabel);
        commonLib.pass("Ticket ID Lable is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Priority   is visible or not
     */
    public Boolean isPriorityLableVisible() {
        Boolean status = isVisible(pageElements.priorityLabel);
        commonLib.pass("Priority Label is visible : " + status);
        return status;
    }
    /**
     * This method is used to check State   is visible or not
     */
    public Boolean isStateLableVisible() {
        Boolean status = isVisible(pageElements.stateLabel);
        commonLib.pass("State Label is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Creation date   is visible or not
     */
    public Boolean isCreationDateLableVisible() {
        Boolean status = isVisible(pageElements.creationDateLabel);
        commonLib.pass("Creation Date Label is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Created By  is visible or not
     */
    public Boolean isCreatedByLableVisible() {
        Boolean status = isVisible(pageElements.createdByLabel);
        commonLib.pass("Created By Label is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Queue   is visible or not
     */
    public Boolean isQueueLableVisible() {
        Boolean status = isVisible(pageElements.queueLabel);
        commonLib.pass("Queue Label is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Source   is visible or not
     */
    public Boolean isSourceLabelVisible() {
        Boolean status = isVisible(pageElements.sourceLabel);
        commonLib.pass("Source Label is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Open Tickets Beyond SLA   is visible or not
     */
    public Boolean isDetailsOpenTicketsBeyondSLALabelVisible() {
        Boolean status = isVisible(pageElements.detailsOpenTicketsBeyondSLALabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Open Tickets Under SLA   is visible or not
     */
    public Boolean isDetailsOpenTicketsUnderSLALabelVisible() {
        Boolean status = isVisible(pageElements.detailsOpenTicketsUnderSLALabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within 15 Mins   is visible or not
     */
    public Boolean isDetailsBreachingWithin15MinsLabelVisible() {
        Boolean status = isVisible(pageElements.detailsBreachingWithin15MinsLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within 15 - 60 Mins   is visible or not
     */
    public Boolean isDetailsBreachingWithin15To60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.detailsBreachingWithin15To60MinsLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within > 60 Mins   is visible or not
     */
    public Boolean isDetailsBreachingGreaterThan60MinsLabelVisible() {
        Boolean status = isVisible(pageElements.detailsBreachingWithin60MinsLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
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
     * This method is use to hover on Kpi Dashboard
     */
    public void hoverOnKpiDashboardIcon() {
        commonLib.info("Hover KPI dashboard icon");
        hoverOverElement(pageElements.kpiDashboardIcon);
    }
}
