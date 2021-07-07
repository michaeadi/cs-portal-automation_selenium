package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class MoreRechargeHistoryPage {
    public By widgetName = By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//parent::div[@class='card__card-header']");
    public By datePicker = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//input[@name='dateRange']");
    public By todayDateFilter = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//form/span[1]//mat-radio-button[1]//span[contains(text(),'Today')]");
    public By last7DaysFilter = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//form//mat-radio-group[@role='radiogroup']//span[contains(text(),'seven days')]");
    public By last2DaysFilter = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//form//mat-radio-group[@role='radiogroup']//span[contains(text(),'two days')]");
    public By listOfRecharge = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted']");

    public By pagination = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//pagination-template//ul");
    public By errorMessage = By.xpath("//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public String headerName="//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String value="]/span";
    public String subHeaderValue="]/span[2]";
    public String columnName="]//div[";
    public String columnRow="//div[data-csautomation-key='INTERNET_BUNDLES_HISTORY']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted'][";
}
