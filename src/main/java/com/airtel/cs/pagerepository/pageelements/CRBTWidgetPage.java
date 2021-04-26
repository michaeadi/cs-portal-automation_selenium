package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CRBTWidgetPage {

    public By titleCRBT = By.xpath("//div[@id='RING_BACK_TUNE']//span[@class=\"card__card-header--label\"]");
    public By ticketIcon = By.xpath("//div[@id='RING_BACK_TUNE']//span/img[@class='interaction-ticket']");
    public By myTuneTab = By.xpath("//div[@id='RING_BACK_TUNE']//div[@role='tab'][1]/div");
    public By top20TuneTab = By.xpath("//div[@id='RING_BACK_TUNE']//div[@role='tab'][2]/div");
    public By searchTuneTab = By.xpath("//div[@id='RING_BACK_TUNE']//div[@role='tab'][3]/div");
    public By noResultImg = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//img[@class=\"ng-star-inserted\"]");
    public By noResultMessage = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//span/span");
    public By widgetError = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//div[@class='image-container']//img");
    public By searchBox = By.xpath("//div[@id='RING_BACK_TUNE']//input");
    public By searchBtn = By.xpath("//div[@id='RING_BACK_TUNE']//input//following-sibling::img");
    public By searchOptionBtn = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"search-area\"]//span");
    public By option1 = By.xpath("//div[@class=\"mat-menu-content\"]/button[1]/span");
    public By option2 = By.xpath("//div[@class=\"mat-menu-content\"]/button[2]/span");
    /*
     *Top 20 Element Locator
     * public By header=By.xpath("//span[contains(text(),"RING BACK TUNE ") and @class="card__card-header--label"]//ancestor::div[@class="card widget ng-star-inserted"]//div[@class="mat-tab-body-wrapper"]//div[@class="card__card-header--card-body--table--list-heading ng-star-inserted"]/div[1]/span");
     * */
}
