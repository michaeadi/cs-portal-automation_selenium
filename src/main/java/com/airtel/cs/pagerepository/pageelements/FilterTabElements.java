package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class FilterTabElements {
    public By applyFilter = By.xpath("//button[@class=\"filter-button mat-button\"]");
    public By closeFilter = By.xpath("//span[@class='close-button']");

    //Filter By Created date
    public By filterCreatedByLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By Created Date')]");
    public By filterClosureByLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter by Closure Date')]");
    public By last7DaysCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[1]/label/div[2]/span[2]");
    public By last30DaysCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[2]/label/div[2]/span[2]");
    public By dateDurationCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[3]/label/div[2]/span[2]");
    public By startDateCD = By.xpath("//input[@formcontrolname=\"startDate\"]");
    public By endDateCD = By.xpath("//input[@formcontrolname=\"endDate\"]");

    //Filter By SLA Date
    public By sLADueDateLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By SLA Due Date')]");
    public By last7DaysSD = By.xpath("//mat-radio-group[@formcontrolname='slaDays']/mat-radio-button[1]/label/div[2]/span[2]");
    public By last30DaysSD = By.xpath("//mat-radio-group[@formcontrolname='slaDays']/mat-radio-button[2]/label/div[2]/span[2]");
    public By dateDurationSD = By.xpath("//mat-radio-group[@formcontrolname='slaDays']/mat-radio-button[3]/label/div[2]/span[2]");
    public By slaStartDate = By.xpath("//input[@formcontrolname=\"slaStartDate\"]");
    public By slaEndDate = By.xpath("//input[@formcontrolname=\"slaEndDate\"]");

    //Filter By Category
    public By categoryLabel = By.xpath("//div[@class=\"mat-drawer-inner-container\"]//span[contains(text(),'Filter By Category')]");
    public By byCode = By.xpath("//div[@formarrayname=\"category\"]//div[2]//div[@class=\"mat-select-value\"]");
    public By byIssue = By.xpath("//label[@class='mat-form-field-label ng-tns-c6-98 mat-empty mat-form-field-empty ng-star-inserted']//mat-label[@class='ng-star-inserted'][contains(text(),'Issue')]");
    public By byIssueType = By.xpath("//mat-label[contains(text(),'Issue Type')]");
    public By byIssueSubType = By.xpath("//mat-label[contains(text(),'Issue sub type')]");
    public By byIssueSubSubType = By.xpath("//mat-label[contains(text(),'Issue sub sub type')]");
    public By searchBox = By.xpath("//input[@placeholder=\"Search\"]");

    //Filter By Queue
    public By queueLabel = By.xpath("//span[contains(text(),'Filter By Queue')]");
    public By showQueueFilter = By.xpath("//mat-label[contains(text(),'Select Queue')]");
    public By openQueueList = By.xpath("//app-custom-mat-select[@formcontrolname=\"selectedFilterQueue\"]");

    //Filter By Ticket Assignee
    public By ticketAssigneeLabel = By.xpath("//span[contains(text(),'Filter By Ticket Assignee')]");
    public By unAssigned = By.xpath("//span[contains(text(),'Unassigned')]");
    public By assigned = By.xpath("//span[contains(text(),'Assigned')]");
    public By assigneeList = By.xpath("//mat-select[@formcontrolname=\"selectedAssigneeName\"]");

    //Filter By Ticket Escalated level
    public By escalatedLevelLabel = By.xpath("//span[contains(text(),'Filter By Escalated Level')]");
    public By openEscalationFilter = By.xpath("//*[@formcontrolname=\"selectedEscalatedLevel\"]/div/div[2]");
    public By level1Escalation = By.xpath("//span[contains(text(),' L1 ')]");
    public By level2Escalation = By.xpath("//span[contains(text(),' L2 ')]");
    public By level3Escalation = By.xpath("//span[contains(text(),' L3 ')]");

    //Filter By State
    public By stateLabel = By.xpath("//span[contains(text(),'Filter By State')]");

    //Filter By Priority
    public By priorityLabel = By.xpath("//span[contains(text(),'By Priority')]");
}
