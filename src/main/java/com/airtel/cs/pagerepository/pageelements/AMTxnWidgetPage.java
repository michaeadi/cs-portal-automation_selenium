package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AMTxnWidgetPage {
    public By airtelMoneyTransactionHeader = By.xpath("//div[@id='AIRTEL_MONEY']//span[@class='card__card-header--label']");
    public By airtelMoneyBalance = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]//span[@class=\"currency ng-star-inserted\"][1]//span[@class=\"currency-amount\"]");
    public By airtelMoneyBalance2=By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]//span[@class=\"currency ng-star-inserted\"][2]//span[@class=\"currency-amount\"]");
    public By airtelMoneyCurrency = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]//span[@class=\"currency ng-star-inserted\"]");
    public By airtelMoneyDatePicker = By.xpath("//div[@id='AIRTEL_MONEY']//input[@name='dateRange']");
    public By airtelMoneyBalanceUnableToFetch = By.xpath("//div[@id='AIRTEL_MONEY']//span[@class=\"api-failed-error ng-star-inserted\"]");
    public By airtelMoneyNoResultFound = By.xpath("//div[@id='AIRTEL_MONEY']//div[@class=\"no-result-found ng-star-inserted\"]/img");
    public By airtelMoneyNoResultFoundMessage = By.xpath("//div[@id='AIRTEL_MONEY']//div[@class=\"no-result-found ng-star-inserted\"]//span//span");
    public By airtelMoneyError = By.xpath("//div[@id='AIRTEL_MONEY']//div[@class='image-container']");
    public By ticketIcon = By.xpath("//div[@id='AIRTEL_MONEY']//ancestor::div[1]//span/img[@class='interaction-ticket']");
    public By getTitle = By.xpath("//div[@id='AIRTEL_MONEY']//span[@class='card__card-header--label']");
    public By clickMenu = By.xpath("//div[@id='AIRTEL_MONEY']//img[@class=\"header-action-icon ng-star-inserted\"]");
    public By amHistoryTab = By.xpath("//button[contains(text(),\"AM History\")][@role=\"menuitem\"]");
    public By transactionId = By.xpath("//div[@id='AIRTEL_MONEY']//input[@placeholder='Transaction ID']");
    public By transactionSearchBtn = By.xpath("//div[@id='AIRTEL_MONEY']//input[@placeholder='Transaction ID']//following-sibling::button");
    public By headerRows=By.xpath("//div[@id='AIRTEL_MONEY']/descendant::div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div");
    public String valueRows="//div[@id='AIRTEL_MONEY']/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][";
    public String valueColumns="]/div[";
    public String columnText= "]//span";
    public By resendSMSIcon=By.xpath("//div[@id='AIRTEL_MONEY']/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][1]/div[6]//img[1][@class=\"hide-reversal ng-star-inserted\"]");
    public String transactionIdColumn="]/div[4]//span";
}