package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class FrontendTicketHistoryPageElements {
    public By searchBox = By.xpath("//input[@class='search-ticket-id ng-untouched ng-pristine ng-valid']");
    public By searchBtn = By.xpath("//a[@class='search-btn']");
    public By allTicket = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr");
    public By noTicketFound = By.xpath("//div[@class=\"no-result-found ng-star-inserted\"]");
    public By clearTicketId = By.xpath("//a[@class='search-close ng-star-inserted']//img");
}

