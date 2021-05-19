package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DADetailsPage {
    public By rows = By.xpath("//div[@id='DA_ACCOUNTS']//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"]");
    public String daID = "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][1]/span";
    public String bundleType = "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][3]/span";
    public String daBalance = "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]/span";
    public String daDateTime = "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][5]/span";
    public By ticketIcon = By.xpath("//div[@id='DA_ACCOUNTS']//span/img[@class='interaction-ticket']");
    public By getTitle = By.xpath("//div[@id='DA_ACCOUNTS']//span[@class='card__card-header--label']");
    public String daDesc = "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][2]/span";
    public String headerRow = "//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[";
    public String headerValue = "]/span";
    public String valueRow = "//div[@id='DA_ACCOUNTS']//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][";
    public String accumulatorHeader="//div[@id='ACCUMULATORS']//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[";
    public String accumulatorColumnHeader="//div[@id='ACCUMULATORS']//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"][";
    public String accumulatorColumnValue="]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][";
    public String offerHeader="//div[@id='DISPLAY_OFFERS']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String offerColumnHeader="//div[@id='DISPLAY_OFFERS']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
    public String offerColumnValue="]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][";
    public String moreIcon="]//mat-icon";
    public By displayOfferTitle=By.id("DISPLAY_OFFERS");
    public String moreHeaderRow="]//div[@class=\"expansion-block display-block ng-star-inserted\"]//div[@class=\"header-data-container ng-star-inserted\"][";
    public String moreColumnHeader="]//div[@class=\"expansion-block display-block ng-star-inserted\"]//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'  or @class='slide-toggle red ng-star-inserted'][";
}
