package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DADetailsPage {
    /**
     * Page Elements of DA Detail Widget
     * */
    public By rows = By.xpath("//div[@id='DA_ACCOUNTS']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted']");
    public String daID = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][1]/span";
    public String bundleType = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][3]/span";
    public String daBalance = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][4]/span";
    public String daDateTime = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][5]/span";
    public By ticketIcon = By.xpath("//div[@id='DA_ACCOUNTS']//span/img[@class='interaction-ticket']");
    public By getTitle = By.xpath("//div[@id='DA_ACCOUNTS']//span[@class='card__card-header--label']");
    public String daDesc = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][2]/span";
    public String headerRow = "//span[contains(text(),'DA Details')]//ancestor::div[@class='card widget ng-star-inserted']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String headerValue = "]/span";
    public String valueRow = "//div[@id='DA_ACCOUNTS']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'][";
    /**
     * Page Elements of Accumulator Widget
     * */
    public String accumulatorHeader="//div[@id='ACCUMULATORS']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String accumulatorColumnHeader="//div[@id='ACCUMULATORS']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public String accumulatorColumnValue="]//div[@class='ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    /**
     * Page Elements of UC & UT Offer Widget
     * */
    public String offerHeader="//div[@id='DISPLAY_OFFERS']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String offerColumnHeader="//div[@id='DISPLAY_OFFERS']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public String offerColumnValue="]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    public String moreIcon="]//mat-icon";
    public String displayOfferTitle="//div[@id='DISPLAY_OFFERS']";
    public String moreHeaderRow="]//div[@class='expansion-block display-block ng-star-inserted']//div[@class='header-data-container ng-star-inserted'][";
    public String moreColumnHeader="]//div[@class='expansion-block display-block ng-star-inserted']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public By offerPagination=By.xpath("//div[@id='DISPLAY_OFFERS']//div[@class='pagination ng-star-inserted']");
    /**
     * Page Elements of Friends & Family Detail Widget
     * */
    public String fnfTitle="//div[@id='FRIENDS_AND_FAMILY']";
    public String fnfHeader="//div[@id='FRIENDS_AND_FAMILY']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String fnfColumnHeader="//div[@id='FRIENDS_AND_FAMILY']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public String fnfColumnValue="]//div[@class='ng-star-inserted' or @class='slide-toggle red ng-star-inserted' or @class='data-container ng-star-inserted'][";
    public String actionIcon="]//img";
    public By addMemberIcon=By.xpath("//div[@id='FRIENDS_AND_FAMILY']//span[contains(text(),'Add')]");
    public By popUpTitle=By.xpath("//div[@class='main-container__header']//span");
    public By popUpCloseBtn = By.xpath("//div[@class='main-container__header']//mat-icon");
    public By addNumber=By.xpath("//input[@name='q1']");
    /**
     * Widget No Result/widget error elements
     * */
    public By unableToFetch = By.xpath("//span[@class='api-failed-error ng-star-inserted']");
    public By noResultFoundIcon = By.xpath("//*[contains(@class,'no-result-found')]/img");
    public By noResultFoundMessage = By.xpath("//div[@class='no-result-found ng-star-inserted']//span//span");

    /**
     * Associated DA Widget
     * */
    public String associatedWidgetTitle="//span[contains(text(),'Associated DAs')]//ancestor::div[@class='card widget ng-star-inserted']";
    public String associateWidgetHeader="//span[@class='header-data ']";
    public By associateWidgetRowValue=By.xpath("//div[@class='card__card-header--card-body--table--data-list row-border']");
    public By associateWidgetColumnValue=By.xpath("//span[@class='data ng-star-inserted']");

    /**
     * Pagination previous next button
     * */
    public String previousBtnDisable="//li[@class='pagination-previous disabled ng-star-inserted']";
    public String previousBtnEnable="//li[@class='pagination-previous ng-star-inserted']";
    public String nextBtnDisable="//li[@class='pagination-next disabled ng-star-inserted']";
    public String nextBtnEnable="//li[@class='pagination-next ng-star-inserted']";
    public String paginationCount="//*[@class='pagination-details']";
}
