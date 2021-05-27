package com.airtel.cs.pagerepository.pageelements;

public class ServiceClassWidgetPage {
    public String title = "//div[@id='SERVICE_PROFILE']";
    public String headerRow="//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String offerColumnHeader="//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public String offerColumnValue="]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    public String headerValue = "]/span";
    public String unableToFetch = "//span[@class='api-failed-error ng-star-inserted']";
    public String noResultFoundIcon = "//*[contains(@class,'no-result-found')]/img";
    public String noResultFoundMessage = "//div[@class='no-result-found ng-star-inserted']//span//span";
}
