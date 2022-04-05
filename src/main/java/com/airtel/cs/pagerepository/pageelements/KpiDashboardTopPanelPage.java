package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboardTopPanelPage {
    public By lastRefreshTime=By.xpath("//span[contains(text(),'Last Refresh')]");
    public By dashboard=By.xpath("//div[contains(text(),' Dashboard ')]");
    public By refreshIcon=By.xpath("//img[contains(@src,'assets/service-request/icons/kpi_refresh.svg')]");
    public By openTicketOverviewLabel=By.xpath("//span[contains(text(),'Open Ticket - Overview')]");
    public By openTicketsBeyondSLALabel=By.xpath("//span[contains(text(),'Open Tickets Beyond SLA')]");
    public By openTicketsUnderSLALabel=By.xpath("//span[contains(text(),'Open Tickets Under SLA ')]");
    public By breachingWithin15MinsLabel=By.xpath("//span[contains(text(),'Breaching Within 15 Mins ')]");
    public By breachingWithin15To60MinsLabel=By.xpath("//span[contains(text(),'Breaching Within 15 - 60 Mins ')]");
    public By breachingWithin60MinsLabel=By.xpath("//span[contains(text(),'Breaching Within > 60 Mins ')]");
    public By openTicketsBeyondSLADetailsIcon=By.xpath("//span[contains(text(),'Open Tickets Beyond SLA')]//following-sibling::img");
    public By openTicketsUnderSLALDetailsIcon=By.xpath("//span[contains(text(),'Open Tickets Under SLA')]//following-sibling::img");
    public By breachingWithin15MinsDetailsIcon=By.xpath("//span[contains(text(),'Breaching Within 15 Mins ')]//following-sibling::img");
    public By breachingWithin15To60MinsDetailsIcon=By.xpath("//span[contains(text(),'Breaching Within > 60 Mins ')]//following-sibling::img");
    public By breachingGreaterThan60MinsDetailsIcon=By.xpath("//span[contains(text(),'Breaching Within > 60 Mins ')]//following-sibling::img");
    public By ticketIdLabel=By.xpath("//span[contains(@class,'data-title')]");
    public By priorityLabel=By.xpath("//span[contains(text(),'Priority')]");
    public By stateLabel=By.xpath("//span[contains(text(),'State')]");
    public By creationDateLabel=By.xpath("//span[contains(text(),'Creation Date')]");
    public By createdByLabel=By.xpath("//span[contains(text(),'Created By')]");
    public By queueLabel=By.xpath("//span[contains(text(),'Queue')]");
    public By sourceLabel=By.xpath("//span[contains(text(),'Source')]");
    public By detailsOpenTicketsBeyondSLALabel=By.xpath("//div[contains(text(),' Open Tickets beyond SLA ')]");
    public By detailsOpenTicketsUnderSLALabel=By.xpath("//div[contains(text(),' Open Tickets Under SLA ')]");
    public By detailsBreachingWithin15MinsLabel=By.xpath("//div[contains(text(),' Breaching Within 15 Mins ')]");
    public By detailsBreachingWithin15To60MinsLabel=By.xpath("//div[contains(text(),' Breaching Within 15 - 60 Mins ')]");
    public By detailsBreachingWithin60MinsLabel=By.xpath("//div[contains(text(),' Breaching Within > 60 Mins ')]");
    public By backIcon=By.xpath("//img[contains(@src,'assets/service-request/images/icon/back.svg')]");
    public By kpiDashboardIcon = By.xpath("//img[contains(@src,'assets/service-request/icons/kpi_dashboard.svg')]");
    public By csDashboardIcon=By.xpath("//*[@id=\"left-side\"]/div/div/mat-nav-list/mat-list-item[4]/div/div[3]/mat-nav-list/mat-list-item/div/a");
}
