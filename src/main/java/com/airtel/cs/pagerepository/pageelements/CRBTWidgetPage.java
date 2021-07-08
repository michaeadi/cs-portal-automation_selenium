package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CRBTWidgetPage {

    public By titleCRBT = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//span[@class='card__card-header--label']");
    public By ticketIcon = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//span/img[@class='interaction-ticket']");
    public By myTuneTab = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//*[text()='My Tunes']");
    public By top20TuneTab = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//*[text()='Top 20 Tunes']");
    public By searchTuneTab = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//*[text()='Search Tunes']");
    public By noResultImg = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//app-data-loading-error//img");
    public By noResultMessage = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//div[contains(@class,'no-result-found')]/span");
    public By widgetError = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//app-data-loading-error//img");
    public By searchBox = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//input");
    public By searchBtn = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//input/following-sibling::img");
    public By searchOptionBtn = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//div[@class='search-area']//span");
    public By option1 = By.xpath("//div[@class='mat-menu-content']/button[1]/span");
    public By option2 = By.xpath("//div[@class='mat-menu-content']/button[2]/span");
    public By widgetLoader = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//div[contains(@class,'animated-background')]");
    public By footerCRBTAuuid = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']//div[contains(@class,'auuid-container')]");
    public By middleCRBTAuuid = By.xpath("//div[@data-csautomation-key='RING_BACK_TUNE']");
    public String crbtTableHeader = "//div[@data-csautomation-key='RING_BACK_TUNE']//div[contains(@class,'table--list-heading')]/div[";
    public String crbtTableHeader1 = "]/span";
    public String crbtTableValue = "//div[@data-csautomation-key='RING_BACK_TUNE']//div[contains(@class,'table-data-wrapper')]//div[";
    public String crbtTableValue1 = "]//div[contains(@class,'table--data-list row-border')]//div[";
    public String tabHeader="//div[@data-csautomation-key='RING_BACK_TUNE']//div[@role='tab'][";
    public String searchHeader="//div[@data-csautomation-key='RING_BACK_TUNE']//div[@class='mat-tab-body-wrapper']//div[@class='card__card-header--card-body--table']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[";
    public String headerText="]/span";
    public String dataRows="//div[@data-csautomation-key='RING_BACK_TUNE']//div[@class='mat-tab-body-wrapper']//div[@class='table-data-wrapper ng-star-inserted']//div[";
    public String dataColumns="]//div[@class='card__card-header--card-body--table--data-list row-border']//div[";

}
