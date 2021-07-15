package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DetailedUsageHistoryPage {
    public By title = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//span[@class='card__card-header--label']");

    /*
     * Filter Headers
     * */
    public By freeCDR = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//form/span[1]//label/span");
    public By typeOfCDR = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//form/span[2]");
    public By todayDateFilter = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//*//span[contains(text(),'Today')]");
    public By last2DayDateFilter = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//*//span[contains(text(),'two days')]");
    public By last7DayDateFilter = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']*//span[contains(text(),'seven days')]");
    public By datePicker = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//form/span[4]//input");

    /*
     * Header Names
     * */
    public By headers = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div");
    public String headerRow="//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String columnText="]/span";
    public String columnRow="//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    public String columnValue="]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    public String checkSign="]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][4]/img[@class='sign-icon-before ng-star-inserted']";

    //Pagination Details
    public By pagination = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='pagination ng-star-inserted']/div[1]");
    public By noResultIcon = By.xpath("//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']//div[@class='no-result-found ng-star-inserted']/img");
    public String widgetIdentifier="//div[@data-csautomation-key='DETAILED_USAGE_HISTORY']";

}
