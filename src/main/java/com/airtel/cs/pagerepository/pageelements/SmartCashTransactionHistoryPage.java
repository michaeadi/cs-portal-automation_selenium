package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class SmartCashTransactionHistoryPage {
    public By smartCashTxnHistoryWidget=By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']");
    //    Filter pom
    public By todayFilterSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form//mat-radio-button//span[contains(text(),'Today')]");
    public By dateRangeFilterSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form/span/input");
    public By lastSevenDaysSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form//mat-radio-button//span[contains(text(),'Last seven days')]");
    public By lasTwoDaysSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form//mat-radio-button//span[contains(text(),'Last two days')]");

    public By searchTxnIdSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//input[@type='search']");
    public By searchBtnSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//button[@class='search-icon-btn']");

    public String valueRowSec = "//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted'][";
    public String headerRowSec = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']/div";
    public String resendSMSSec = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list row-border'][1]/div[12]//img[1][@class='hide-reversal ng-star-inserted']";
    public String valueColumns = "//div[@class='data-container ng-star-inserted'][";
    public String iconColumn = "//div[@class='quick-action ng-star-inserted'][";
    public String columnText = "]//span//span";
    public String resendSmsIcon="]//span//img[contains(@src,'send-msg.svg')]";
    public String reversalIcon="]//span//img[contains(@src,'reversal.svg')]";
    public String negSymbol = "]//img[@class='sign-icon-before ng-star-inserted']";
    public String posSymbol = "]//img[@class='sign-icon-before ng-star-inserted']";
    public String headerRow = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']/div";
    public String resendSMS = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--data-list row-border'][1]/div[12]//img[1][@class='hide-reversal ng-star-inserted']";

    public By airtelMoneyNoResultFoundSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']");
    public By airtelMoneyNoResultFoundMessageSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public By airtelMoneyErrorSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='image-container']");
    public By titleSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[@class='card__card-header--label']");

    public By airtelMoneyNoResultFound = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']");
    public By airtelMoneyNoResultFoundMessage = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public By airtelMoneyError = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='image-container']");
    public By title = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[@class='card__card-header--label']");

}
