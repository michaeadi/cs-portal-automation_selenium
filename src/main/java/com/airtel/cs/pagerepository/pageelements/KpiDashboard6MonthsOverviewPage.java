package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboard6MonthsOverviewPage {
    public By serviceLevelTrend = By.xpath("//span[contains(text(),'Service Level Trend')]");
    public By connectionTypeDropDown = By.xpath("//*[@data-csautomation-key='SERVICE_LEVEL_TREND']//*[text()='Connection Type']");
    public By queueTypeDropDown = By.xpath("//*[@data-csautomation-key='SERVICE_LEVEL_TREND']//*[text()='Queue']");
    public By issueTypeDropDown = By.xpath("//*[@data-csautomation-key='SERVICE_LEVEL_TREND']//*[text()='Issue Type']");
    public By issueSubTypeDropDown = By.xpath("//*[@data-csautomation-key='SERVICE_LEVEL_TREND']//*[text()='Issue Sub Type']");
    public By downloadReportIcon = By.xpath("//img[contains(@src,'./assets/kpi-dashboard/download.svg')]");
    public By serviceLevelTrendDetailsIcon = By.xpath("//span[contains(text(),'Service Level Trend')]/..//img");
    public By queueNameLabel = By.xpath("//span[contains(text(),'Queue Name')]");
    public By totalTicketAllocated = By.xpath("//span[contains(text(),'Total Ticket Allocated')]");
    public By ticketCancelled = By.xpath("//span[contains(text(),'Ticket Cancelled')]");
    public By ticketClosedOutsideSLA = By.xpath("//span[contains(text(),'Ticket Closed Outside SLA')]");
    public By ticketClosedWithinSLA = By.xpath("//span[contains(text(),'Ticket Closed Within SLA')]");
    public By SLAPercentage = By.xpath("//span[contains(text(),'SLA %')]");
    public By dayWiseQueueWiseWidget = By.xpath("//*[@class='queue-wise']//span[contains(text(),'Day wise')]");
    public By dayWiseIssueTypeWiseWidget = By.xpath("//*[@class='issue-wise']//span[contains(text(),'Daywise')]");
    public By dayWise = By.xpath("//*[@class='queue-wise']//img[contains(@src,'day_wise.svg')]");
    public By date = By.xpath("//span[contains(text(),'date')]");
    public By month = By.xpath("//span[contains(text(),'month')]");
    public By monthSelectionBox = By.xpath("//input[contains(@class,'date-input mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-untouched ng-pristine ng-valid')]");
    public By slaPerformanceDetailsQueueWiseLabel = By.xpath("//span[contains(text(),'SLA Performance Details: Queue Wise ')]");
    public By slaPerformanceDetailsIssueTypeLabel = By.xpath("//span[contains(text(),'SLA Performance Details: Issue Type Wise ')]");
    public By issueTypeLabel = By.xpath("//span[contains(text(),'Issue Type')]");
    public By dayWiseInSlaPerformanceDetailsQueueWiseIcon = By.xpath("//*[@class='queue-wise']//img[contains(@src,'day_wise.svg')]");
    public By backIcon = By.xpath("//img[contains(@src,'assets/service-request/images/icon/back.svg')]");
    public By dayWiseInSlaPerformanceDetailsIssueTypeIcon = By.xpath("//*[@class='issue-wise']//img[contains(@src,'day_wise.svg')]");
    public By agentPerormanceIcon = By.xpath("//div[contains(text(),'AGENT PERFORMANCE')]");
    public By agentIdLabel = By.xpath("//span[contains(text(),'Agent Id')]");
    public By agentNameLabel = By.xpath("//span[contains(text(),'Agent Name')]");
    public By dayWiseInAgentPerformanceIcon = By.xpath("//img[contains(@src,'assets/kpi-dashboard/day_wise.svg')]");
}
