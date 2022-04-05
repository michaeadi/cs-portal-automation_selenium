package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboardOpenTicketAgeingPage {
    public By lifetimeTillDateLabel=By.xpath("//span[contains(text(),'Lifetime Till Date')]");
    public By openTicketAgeingLabel=By.xpath("//span[contains(text(),'Open Ticket Ageing')]");
    public By monthLabel=By.xpath("//input[contains(@class,'date-input mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-untouched ng-pristine ng-valid')]");
    public By connectionLabel;
    public By issueTypeLabel;
    public By issueSubTypeLabel;
    public By downloadIcon;
    public By openTicketAgeingDetailsIcon;
    public By issueCodeLabel;
    public By queueNameLabel;
    public By beyond30DaysLabe;
    public By threeToTenDaysLabel;
    public By lessThanADayLabel;
    public By twoToThreeDaysLabel;
    public By tenToThirtyDaysLabel;
    public By slabreachTicketAgeingIssueTypeAndQueueLabel;
    public By slaBreachTicketAgeingAgentLabel;
    public By AgentIdLabe;
    public By agentNameLabe;
}
