package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboard6MonthsTicketTypePage {
    public By sixMonthsTicketTypeLabel = By.xpath("//span[contains(text(),'6 Months Ticket Type')]");
    public By ftrIcon = By.xpath("//span[contains(text(),' FTR ')]");
    public By connectionLabel;
    public By nftrIcon = By.xpath("//span[contains(text(),' NFTR ')]");
    public By issueTypelLabel;
    public By sixMonthsTicketDetailsIcon = By.xpath("//span[contains(text(),'6 Months Ticket Type')]/..//img");
    public By ticketTypeDetailsLabel = By.xpath("//span[contains(text(),'Ticket Type Details ')]");
    public By monthLabel = By.xpath("//span[contains(text(),'Month')]");
    public By ftrPercentageLabel = By.xpath("//span[contains(text(),'FTR %')]");
    public By ftrLabel = By.xpath("//span[contains(text(),'FTR')]");
    public By nftrLabel = By.xpath("//span[contains(text(),'NFTR')]");
    public By backIcon = By.xpath("//img[contains(@src,'assets/service-request/images/icon/back.svg')]");
}
