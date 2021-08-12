package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ServiceClassWidgetPage {
    public String widgetIdentifier = "//div[@data-csautomation-key='SERVICE_PROFILE']";
    public String headerRow = "//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public By serviceProfileWidgetHeader = By.xpath("//div[@data-csautomation-key='SERVICE_PROFILE']//span[@class='card__card-header--label']");
    public String offerColumnHeader = "//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public String offerColumnValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    public String headerValue = "]/span";
    public String unableToFetch = "//span[@class='api-failed-error ng-star-inserted']";
    public String noResultFoundIcon = "//*[contains(@class,'no-result-found')]/img";
    public String noResultFoundMessage = "//div[@class='no-result-found ng-star-inserted']//span//span";
    public By commentBox = By.xpath("//textarea[@data-csautomation-key='interactionComment']");
    public By cancelBtn = By.xpath("//button[@class='no-btn']");
    public By submitBtn = By.xpath("//button[@class='submit-btn']");
    public By code = By.xpath("//mat-option[1]//span");
    public By successModal = By.xpath("//*[@class='main-container__body--message ng-star-inserted']");
    public By closeBtn = By.xpath("//following-sibling::mat-icon[contains(text(),'close')]");
    public String serviceStatus = "//div[contains(@class,'table-data-wrapper')]/div[@class='ng-star-inserted'][";
    public String toggleBtn = "]//mat-slide-toggle";
    public String toggleBtnStatus ="]//input";
    /**
     * Pagination previous next button
     */
    public String previousBtnDisable = "//li[@class='pagination-previous disabled ng-star-inserted']";
    public String previousBtnEnable = "//li[@class='pagination-previous ng-star-inserted']";
    public String nextBtnDisable = "//li[@class='pagination-next disabled ng-star-inserted']";
    public String nextBtnEnable = "//li[@class='pagination-next ng-star-inserted']";
    public String paginationCount = "//*[@class='pagination-details']";
    public By servicePagination = By.xpath("//div[@data-csautomation-key='SERVICE_PROFILE']//div[@class='pagination ng-star-inserted']");

}
