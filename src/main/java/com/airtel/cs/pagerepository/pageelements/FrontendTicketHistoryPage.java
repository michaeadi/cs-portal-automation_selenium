package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class FrontendTicketHistoryPage {
    public By searchBox = By.xpath("//input[@class='search-ticket-id ng-untouched ng-pristine ng-valid']");
    public By searchBtn = By.xpath("//a[@class='search-btn']");
    public By allTicket = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr");
    public By noTicketFound = By.xpath("//div[@class='no-result-found ng-star-inserted']");
    public By clearTicketId = By.xpath("//a[@class='search-close ng-star-inserted']//img");
    public By ticketRow = By.xpath("//*[contains(@class,'ticket-history')]/tbody/tr[1]/td");
    public String ticketId = "]//td[1]//p[1]//b[1]";
    public String ticketPriority = "]//td[2]//p[1]//b[1]";
    public String ticketQueue = "]//td[3]//p[1]";
    public String ticketState = "]//td[4]//p[1]";
    public String closeDate = "]//td[5]//p[1]//span[1]";
    public String closeTime = "]//td[5]//p[1]";
    public String ticketCreatedBy = "]//td[6]//p[1]";
    public String ticketCreatedDate = "]//td[7]//p[1]//span[1]";
    public String ticketCreatedTime = "]//td[7]//p[1]";
    public String addToInteraction = "]//td[8]//span[1]//a[1]//img";
    public String reOpenIcon = "]//td[8]//span[1]//a[2]//img";
    public String ticketSourceApp = "]//td[8]/p";
    public By addToInteractionIcon = By.xpath("//*[@title='Add to Interaction']");
    public By unableToFetch = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//div[@data-csautomation-key='widgetErrorMsg']//following-sibling::h3");

}

