package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DetailAccountInfoWidgetPage {
    public By getTitle = By.xpath("");
    public By accountInfoIcon = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//img[@class='header-action-icon ng-star-inserted']");
    public By accountInfoDetailWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public String billDisputIcon = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[contains(@class,'table-data-wrapper')]/div[";
    public String billDisputIcon2 = "]//img[contains(@src,'dispute')]";
    public By billDisputHeader = By.xpath("//span[contains(text(),'Raise Dispute')]");
    public By billNumber = By.xpath("//label[contains(text(),'Bill Number *')]//following-sibling::input");
    public By accountNumber = By.xpath("//label[contains(text(),'Account Number *')]//following-sibling::input");
    public By billDateTime = By.xpath("//label[contains(text(),'Bill Date and Time *')]//following-sibling::input");
    public By billStatus = By.xpath("//label[contains(text(),'Bill Status *')]//following-sibling::input");
    public String billNumber1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[2]//div[contains(@class,'table-data-wrapper')]//div[";
    public String billNumber2 = "]//div[4]//span[@data-csautomation-key='columnValue']";
    public String billDateTime1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[2]//div[contains(@class,'table-data-wrapper')]//div[";
    public String billDateTime2 = "]//div[1]//span[@data-csautomation-key='columnValue']";
    public String billStatus1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[2]//div[contains(@class,'table-data-wrapper')]//div[";
    public String billStatus2 = "]//div[3]//span[@data-csautomation-key='columnValue']";
    public String transactionType1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[2]//div[contains(@class,'table-data-wrapper')]//div[";
    public String transactionType2 = "]//div[2]//span[@data-csautomation-key='columnValue']";
    public By clickNext = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//li[contains(@class,'pagination-next')]");
    public By raiseDisputeCloseButton = By.xpath("//app-modal-success-failure//following-sibling::mat-icon[contains(text(),'close')]");
    public By toastModal = By.xpath("//div[contains(@class,'main-container__body--message')]");
}