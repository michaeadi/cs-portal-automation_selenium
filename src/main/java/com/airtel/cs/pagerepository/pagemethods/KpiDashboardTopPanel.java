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
    public Boolean isDashboardIconVisible() {
        Boolean status = isVisible(pageElements.dashboardIcon);
        commonLib.pass("Dashboard Icon  is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Dashboard Icon
     */
    public void clickOnDashboardIcon() {
        commonLib.info("Going to click Dashboard Icon");
        if (isVisible(pageElements.dashboardIcon))
            clickAndWaitForLoaderToBeRemoved(pageElements.dashboardIcon);

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
    public Boolean isRefreshIcondVisible() {
        Boolean status = isVisible(pageElements.refreshIcon);
        commonLib.pass("Refresh Icon  is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Open Ticket - Overview   is visible or not
     */
    public Boolean isOpenTicketOverviewLableVisible() {
        Boolean status = isVisible(pageElements.openTicketOverviewLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Open Tickets Beyond SLA   is visible or not
     */
    public Boolean isOpenTicketsBeyondSLALableVisible() {
        Boolean status = isVisible(pageElements.openTicketsBeyondSLALabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Open Tickets Under SLA   is visible or not
     */
    public Boolean isOpenTicketsUnderSLALableVisible() {
        Boolean status = isVisible(pageElements.openTicketsUnderSLALabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within 15 Mins   is visible or not
     */
    public Boolean isBreachingWithin15MinsLableVisible() {
        Boolean status = isVisible(pageElements.breachingWithin15MinsLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within 15 - 60 Mins   is visible or not
     */
    public Boolean isBreachingWithin15To60MinsLableVisible() {
        Boolean status = isVisible(pageElements.breachingWithin15To60MinsLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
        return status;
    }
    /**
     * This method is used to check Breaching Within > 60 Mins   is visible or not
     */
    public Boolean isBreachingWithin60MinsLableVisible() {
        Boolean status = isVisible(pageElements.breachingWithin60MinsLabel);
        commonLib.pass("Open Ticket Overview is visible : " + status);
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
}
