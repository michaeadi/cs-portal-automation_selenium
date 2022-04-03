package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboard6MonthsOverviewPage {
    public By serviceLevelTrend=By.xpath("//span[contains(text(),'Service Level Trend')]");
    public By connectionTypeDropDown=By.xpath("//span[@class='mat-select-placeholder ng-tns-c9-123 ng-star-inserted']");
    public By queueTypeDropDown=By.xpath("//span[@class='mat-select-placeholder ng-tns-c9-125 ng-star-inserted']");
    public By issueTypeDropDown=By.xpath("//span[@class='mat-select-placeholder ng-tns-c9-127 ng-star-inserted']");
    public By issueSubTypeDropDown=By.xpath("//span[@class='mat-select-placeholder ng-tns-c9-129 ng-star-inserted']");
    public By downloadReportIcon;
    public By serviceLevelTrendDetailsIcon=By.xpath("//img[contains(@src,'assets/service-request/images/more.svg')]");
    public By queueNameLabel=By.xpath("//span[contains(text(),'Queue Name')]");
    public By totalTicketAllocated;
    public By ticketCancelled;
    public By ticketClosedOutsideSLA;
    public By ticketClosedWithinSLA;
    public By SLAPercentage;
    public By daywise;
    public By date;
    public By month=By.xpath("//span[contains(text(),'Month/Date')]");
    public By monthSelectionBox;
    public By slaPerforrmanceDetailsQueueWiseLabel;
    public By slaPerformanceDdetailsIssueTypeLabel;
    public By issueTypeLabel;
    public By dayWiseInSlaPerformanceDetailsQueueWiseIcon;
    public By backIcon;
    public By dayWiseInSlaPerformanceDetailsIssueTypeIcon;
    public By agentPerormanceIcon;
    public By agentIdLabel;
    public By agentNameLabel;
    public By dayWiseInAgentPerformanceIcon;
}
