package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class UsageWidgetPageElements {
    public By usageHistoryDatePicker = By.xpath("//div[@id='USAGE_HISTORY']//input");
    public By usageHistoryHeader = By.xpath("//div[@id='USAGE_HISTORY']//span[@class='card__card-header--label']");
    public By rows = By.xpath("//div[@id='USAGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]");
    public By type = By.xpath("div[1]/span[@class=\"ng-star-inserted\"]");
    public By charge = By.xpath("div[2]/span[@class=\"ng-star-inserted\"]");
    public By dateTime = By.xpath("div[3]/span[@class=\"date_time ng-star-inserted\"]");
    public By startBalance = By.xpath("div[4]/span[@class=\"ng-star-inserted\"]");
    public By endBalance = By.xpath("div[5]/span[@class=\"ng-star-inserted\"]");
    public By menu = By.xpath("//div[@id='USAGE_HISTORY']//img[@class=\"header-action-icon ng-star-inserted\"]");
    public By usageHistoryNoResultFound = By.xpath("//div[@id='USAGE_HISTORY']//div[@class=\"no-result-found ng-star-inserted\"]//img");
    public By usageHistoryNoResultFoundMessage = By.xpath("//div[@id='USAGE_HISTORY']//span[contains(text(),'No Result found')]");
    public By usageHistoryError = By.xpath("//div[@id='USAGE_HISTORY']/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");
    public By ticketIcon = By.xpath("//div[@id='USAGE_HISTORY']//span/img[@class='interaction-ticket']");
    public By getTitle = By.xpath("//div[@id='USAGE_HISTORY']//span[@class='card__card-header--label']");
    public String headerRow="//div[@id='USAGE_HISTORY']//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[";
    public String headerText="]/span";
}
