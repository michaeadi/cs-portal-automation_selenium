package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AMTxnWidgetPage {
    public By airtelMoneyTransactionHeader = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[contains(@class,'widget_header_label')]");
    public By airtelMoneyBalance = By.xpath("//*[@data-csautomation-key='AIRTEL_MONEY']//span[@class='currency-amount']");
    public By airtelMoneyBalance2 = By.xpath("//div[@class='card__content--money-balance ng-star-inserted']//span[@class='currency ng-star-inserted'][2]//span[@class='currency-amount']");
    public By airtelMoneyCurrency = By.xpath("//*[@data-csautomation-key='AIRTEL_MONEY']//span[contains(@class,'currency')]");
    public By airtelMoneyDatePicker = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//input[@name='dateRange']");
    public By airtelMoneyBalanceUnableToFetch = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[@class='api-failed-error ng-star-inserted']");
    public By airtelMoneyNoResultFound = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//*[contains(@class,'no-result-found')]/img");
    public By airtelMoneyNoResultFoundMessage = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='no-result-found ng-star-inserted']//span//span");
    public By airtelMoneyError = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='image-container']");
    public By ticketIcon = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//ancestor::div[1]//span/img[@class='interaction-ticket']");
    public By getTitle = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[@class='card__card-header--label']");
    public By clickMenu = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//img[contains(@src,'history.svg')]");
    public By amHistoryTab = By.xpath("//button[contains(text(),'AM History')][@role='menuitem']");
    public By transactionId = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//input[@placeholder='Transaction ID']");
    public By transactionSearchBtn = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//input[@placeholder='Transaction ID']//following-sibling::button");
    public By headerRows = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[contains(@class,'card-header--card-body--table--list')]/div");
    public String dataRow = "//div[@data-csautomation-key='AIRTEL_MONEY']//div[contains(@class,'table-data-wrapper')]/div[";
    public String valueColumns = "]/div/div[";
    public String columnText = "]//span";
    public String amountSign = "]/div[";
    public String amountImg = "]//img";
    public String resendSMSIcon = "//div[@data-csautomation-key='AIRTEL_MONEY']//div[contains(@class,'table-data-wrapper')]/div[";
    public String resendSMSIcon2 = "]//img[contains(@src,'send')]";
    public String transactionIdColumn = "]/div/div[4]//span";
    public By footerAMAuuid = By.xpath("//*[@id='AIRTEL_MONEY']//*[@class='auuid-container']");
    public By middleAMAuuid = By.xpath("//*[@id='AIRTEL_MONEY']");

    public String amWidgetId="//div[@data-csautomation-key='AIRTEL_MONEY']";
  	public By reverseLabel = By.xpath("//*[contains(text(),'ID No.')]//following-sibling::td/span");
    public String reversalIcon="//div[@data-csautomation-key='AIRTEL_MONEY']//div[contains(@class,'table-data-wrapper')]/div[";
    public String reversalIcon2="]//span[contains(@class,'disabled')]";
}