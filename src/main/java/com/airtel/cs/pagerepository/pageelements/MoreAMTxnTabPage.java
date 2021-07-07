package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class MoreAMTxnTabPage {
    public By airtelMoneyNoResultFound = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']");
    public By airtelMoneyNoResultFoundMessage = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public By airtelMoneyError = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//div[@class='image-container']");
    public By title = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//span[@class='card__card-header--label']");
    /*
     * Filter POM
     * */
    public By todayFilter = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//parent::div//form//mat-radio-button//span[contains(text(),'Today')]");
    public By dateRangeFilter = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//parent::div//form/span/input");
    public By lastSevenDays = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//parent::div//form//mat-radio-button//span[contains(text(),'Last seven days')]");
    public By lasTwoDays = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//parent::div//form//mat-radio-button//span[contains(text(),'Last two days')]");
    /*
     * Search Box
     * */
    public By searchTxnId = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//parent::div//input[@type='search']");
    public By searchBtn = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL']//parent::div//button[@class='search-icon-btn']");
    public String valueRow = "//div[data-csautomation-key='AM_TRANSACTION_DETAIL']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list'][";
    public String valueColumns = "]/div[";
    public String columnText = "]//span";
    public String negSymbol = "]//img[@class='sign-icon-before ng-star-inserted']";
    public String posSymbol = "]//img[@class='sign-icon-before ng-star-inserted']";
    public String headerRow = "//div[data-csautomation-key='AM_TRANSACTION_DETAIL']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']/div";
    public String resendSMS = "//div[data-csautomation-key='AM_TRANSACTION_DETAIL']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list row-border'][1]/div[12]//img[1][@class='hide-reversal ng-star-inserted']";

    //Secondary Widget
    public By airtelMoneyNoResultFoundSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']");
    public By airtelMoneyNoResultFoundMessageSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public By airtelMoneyErrorSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//div[@class='image-container']");
    public By titleSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//span[@class='card__card-header--label']");
    //    Filter pom
    public By todayFilterSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//parent::div//form//mat-radio-button//span[contains(text(),'Today')]");
    public By dateRangeFilterSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//parent::div//form/span/input");
    public By lastSevenDaysSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//parent::div//form//mat-radio-button//span[contains(text(),'Last seven days')]");
    public By lasTwoDaysSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//parent::div//form//mat-radio-button//span[contains(text(),'Last two days')]");

    public By searchTxnIdSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//parent::div//input[@type='search']");
    public By searchBtnSec = By.xpath("//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']//parent::div//button[@class='search-icon-btn']");
    public String valueRowSec = "//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list row-border'][";
    public String headerRowSec = "//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']/div";
    public String resendSMSSec = "//div[data-csautomation-key='AM_TRANSACTION_DETAIL_SECONDARY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list row-border'][1]/div[12]//img[1][@class='hide-reversal ng-star-inserted']";

}
