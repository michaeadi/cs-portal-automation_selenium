package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ViewHistoryPage {
    public By firstIssueCode = By.xpath("//tbody/tr[1]/td[7]/p");
    public By interactionsTab = By.xpath("//div[@class='mat-tab-label-content' and contains(text(),'Interaction')]");
    public By ticketHistory = By.xpath("//span[contains(text(),'Ticket')]");
    public By allIssue = By.xpath("//table[@id='fetchInteractionByCustomer']//tbody//tr");
    public By ticketId = By.xpath("//table[@id='fetchInteractionByCustomer']//tbody//tr[1]//td[8]//span[1]//span[1]");
    public By ticketPageTitle = By.xpath("//h2[contains(text(),'View Ticket')]");
    public By closeTicketTab = By.xpath("//button[@class='close-btn']//img");
    public By messageHistory = By.xpath("//div[contains(text(),'Message')]");
    public By actionTrailTab = By.xpath("//div[contains(text(),'Action')]");
    public By paginationDetails = By.xpath("//*[contains(@class,'pagination-details')]");
    public By sourceApp = By.xpath("//*[@class='bksuper hr']//span[contains(text(),'Source')]");
    public By sourceAppValue = By.xpath("//*[@class='bksuper hr']/li[9]/span[2]");
    public By closeBtn = By.xpath("//*[@class='close-btn']/img");

}
