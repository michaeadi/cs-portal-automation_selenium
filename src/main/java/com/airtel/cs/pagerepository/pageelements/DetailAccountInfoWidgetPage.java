package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DetailAccountInfoWidgetPage {
    public By getTitle = By.xpath("");
    public By accountInfoIcon = By.xpath("//div@[data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//img[@class='header-action-icon ng-star-inserted']");
    public By accountInfoDetailWidget = By.xpath("//div@[data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public String billDisputIcon = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[contains(@class,'table-data-wrapper')]/div[";
    public String billDisputIcon2 = "]//img[contains(@src,'send')]";
    public By billDisputHeader = By.xpath("");
    public By billNumber = By.xpath("");
    public By accountNumber = By.xpath("");
    public By billDateTime = By.xpath("");
    public By billStatus = By.xpath("");
    public String billNumber1 = "";
    public String billNumber2 = "";
    public String billDateTime1 = "";
    public String billDateTime2 = "";
    public String billStatus1 = "";
    public String billStatus2 = "";
    public String transactionType1 = "";
    public String transactionType2 = "";
}