package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class SmartCashTransactionHistoryPage {
    public By smartCashTxnHistoryWidget = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']");
    //    Filter pom
    public By todayFilterSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form//mat-radio-button//span[contains(text(),'Today')]");
    public By dateRangeFilterSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form/span/input");
    public By lastSevenDaysSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form//mat-radio-button//span[contains(text(),'Last seven days')]");
    public By lasTwoDaysSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//form//mat-radio-button//span[contains(text(),'Last two days')]");

    public By searchTxnIdSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//input[@type='search']");
    public By searchBtnSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//parent::div//button[@class='search-icon-btn']");

    public String valueRowSec = "//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted'][";
    public String headerRowSec = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']/div";
    public String valueColumns = "]//div[@class='data-container ng-star-inserted'][";
    public String iconColumn = "]//div[@class='quick-action ng-star-inserted'][";
    public String columnText = "]//span";
    public String resendSmsIcon = "]//span//img[contains(@src,'send-msg.svg')]";
    public String reversalIcon = "]//span//img[contains(@src,'reversal.svg')]";
    public String negSymbol = "]//img[@class='sign-icon-before ng-star-inserted']";
    public String posSymbol = "]//img[@class='sign-icon-before ng-star-inserted']";
    public String headerRow = "//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']/div";

    public By airtelMoneyNoResultFoundSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']");
    public By airtelMoneyNoResultFoundMessageSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public By airtelMoneyErrorSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='image-container']");
    public By titleSec = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[@class='card__card-header--label']");

    public By airtelMoneyNoResultFound = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']");
    public By airtelMoneyNoResultFoundMessage = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']/div[@class='card__content restricted ng-star-inserted']/descendant::div[@class='no-result-found ng-star-inserted']/span/span");
    public By airtelMoneyError = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//div[@class='image-container']");
    public By title = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//span[@class='card__card-header--label']");

    public String detailsRow = "//*[contains(@class,'table-data-wrapper')]/div[";
    public String detailsColumn="]//*[contains(@class,'expansion-block display-block')]//*[@class='card__card-header--card-body--table--data-list']/div[";
    public String detailsColumnText = "]/span";
    public String rowMetaInfo1 = "//*[contains(@class,'table-data-wrapper')]/div[";
    public String rowMetaInfo2 = "]//*[text()=' keyboard_arrow_down']";


    /**
     * Send Notification locators
     */
    public By smsHeader = By.xpath("//div[@class='main-container__header']//span[contains(text(),'Send Notification')]");
    public By smsIssueDetail = By.xpath("//div[contains(text(),'Issue Detail:')]");
    public By enterComment = By.xpath("//div[@class='main-container__body--form-data--issue-comment--label']//span[normalize-space()='Enter Comment']");
    public By smsSelectReason = By.xpath("//label[contains(text(),'Select Reason *')]");
    public By selectArrow = By.xpath("//div[@class='mat-select-arrow-wrapper']//div");
    public By selectCustomerRequestFromDropdown = By.xpath("//span[normalize-space()='Customer Request']");
    public By selectDidNotGetSmsFromDropdown = By.xpath("//span[normalize-space()='Customer did not get SMS']");
    public By selectDeletedTheSmsFromDropdown = By.xpath("//span[normalize-space()='Customer deleted the SMS by mistake']");
    public By smsTextArea = By.xpath("//div[contains(@class,'mat-form-field')]//textarea[@id='interactionComment']");
    public By cancelSms = By.xpath("//span[contains(@class,'buttons')]//span[contains(text(),'Cancel')]");
    public By submitSms = By.xpath("//span[contains(@class,'buttons')]//span[contains(text(),'Submit')]");
    public By successMessage = By.xpath("//div[contains(text(),'Sms has been resent on your device')]");
    public By crossIcon = By.xpath("//div[contains(@class,'main-container__header success')]//mat-icon[contains(text(),'close')]");
    public By demographics = By.xpath("//div[@class='main-container__header success']//span");
    public By smsNotificationIcon = By.xpath("(//div[@class='quick-action ng-star-inserted'])/span/span//img[contains(@src,'send-msg.svg')]");


    /**
     * Action Trail
     */
    public By homePage=By.xpath("//span[contains(text(),'HOME')]");
    public By viewHistory=By.xpath("//span[contains(text(),'VIEW HISTORY')]");
    public By actionTrail=By.xpath("//div[contains(text(),'Action Trail')]");
    public By actionType=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");

}
