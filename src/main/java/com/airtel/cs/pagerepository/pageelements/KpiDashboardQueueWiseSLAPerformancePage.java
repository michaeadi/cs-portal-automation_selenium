package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboardQueueWiseSLAPerformancePage {
    public By queueWiseSLAPerformanceLabel = By.xpath("//span[contains(text(),'Queue Wise SLA Performance')]");
    public By totalTicketLabel = By.xpath("//span[contains(text(),' Total Ticket ')]");
    public By queueWiseSLAPerformanceDetailsIcon = By.xpath("//span[contains(text(),'Queue Wise SLA Performance')]/..//img");
    public By slaPerformanceDetailsQueueWiseLabel = By.xpath("//span[contains(text(),'SLA Performance Details: Queue Wise ')]");
    public By queueNameLabel = By.xpath("//span[contains(text(),'Queue Name')]");
    public By totalTicketAllocatedLabel = By.xpath("//span[contains(text(),'Total Ticket Allocated')]");
    public By ticketCancelledLabel = By.xpath("//span[contains(text(),'Ticket Cancelled')]");
    public By ticketClosedOutsideSLALabel = By.xpath("//span[contains(text(),'Ticket Closed Outside SLA')]");
    public By ticketClosedWithinSLALabel = By.xpath("//span[contains(text(),'Ticket Closed Within SLA')]");
    public By slaPercentageLabel = By.xpath("//span[contains(text(),'SLA %')]");
    public By slaPerformanceDetailsIssueTypeWiseLabel = By.xpath("//span[contains(text(),'SLA Performance Details: Issue Type Wise ')]");
    public By dayWise = By.xpath("//img[contains(@src,'assets/kpi-dashboard/day_wise.svg')]");
    public By dayWiseInSLAPerformanceDetailsQueueWiseIcon = By.xpath("//img[contains(@src,'assets/kpi-dashboard/day_wise.svg')]");
    public By dayWiseInSLAPerformanceDetailsIssueTypeWiseIcon = By.xpath("//img[contains(@src,'assets/kpi-dashboard/day_wise.svg')]");
    public By date = By.xpath("//span[contains(text(),'Date')]");
    public By backIcon = By.xpath("//img[contains(@src,'assets/service-request/images/icon/back.svg')]");
    public By agentPerformanceIcon = By.xpath("//span[contains(text(),'Agent Performance Details')]");
    public By agentPerformanceLabel = By.xpath("//div[contains(text(),'AGENT PERFORMANCE')]");
    public By agentNameLabel = By.xpath("//span[contains(text(),'Agent Name')]");
    public By agentIdeLabel = By.xpath("//span[contains(text(),'Agent Id')]");
    public By issueTypeLabel = By.xpath("//span[contains(text(),'Issue Type')]");
    public By dayWiseAgentPerformanceDetailsIcon = By.xpath("//img[contains(@src,'assets/kpi-dashboard/day_wise.svg')]");
    public By dayWiseAgentPerformanceLabel = By.xpath("//span[contains(text(),'Agent Performance Details ')]");
    public By dayWiseInSLAPerformanceDetailsQueueWiseLabel = By.xpath("//span[contains(text(),'SLA Performance Details: Queue Wise ')]");
    public By dayWiseInSLAPerformanceDetailsIssueTypeWiseLabel = By.xpath("//span[contains(text(),'SLA Performance Details: Issue Type Wise ')]");
}
