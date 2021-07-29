package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DetailAccountInfoViewBillWidgetPage {
    public By getTitle = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public By accountInfoIcon = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//img[@class='header-action-icon ng-star-inserted']");
    public By accountInfoDetailWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public By accountInfo = By.xpath("//div[@class='mat-tab-label-content']//span[contains(text(),'Account Info')]");
    public By pdf = By.xpath("//div[@class='pdf-container ng-star-inserted']//simple-pdf-viewer/div[1]");
    public String viewBillIcon1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[contains(@class,'table-data-wrapper')]/div[";
    public String viewBillIcon2 = "]//img[contains(@src,'view')]";
    public String billNumber1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[2]//div[contains(@class,'table-data-wrapper')]//div[";
    public String billNumber2 = "]//div[4]//span[@data-csautomation-key='columnValue']";
    public String transactionType1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[2]//div[contains(@class,'table-data-wrapper')]//div[";
    public String transactionType2 = "]//div[2]//span[@data-csautomation-key='columnValue']";
    public By clickNext = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//li[contains(@class,'pagination-next')]");
    public By unableToFetchBill = By.xpath("//div[@data-csautomation-key='apiErrorMsg']");
    public By viewBillOpen = By.xpath("//div[@class='textLayer']");
    public By clickPrevPage = By.xpath("//div[@class='pdf-container ng-star-inserted']/div[1]/mat-icon[1]");
    public By clickNextPage = By.xpath("//div[@class='pdf-container ng-star-inserted']/div[1]/mat-icon[2]");
    public By homePage = By.xpath("//*[contains(text(),'HOME')]");
    public By viewHistory = By.xpath("//*[contains(text(),'VIEW HISTORY')]");
    public By actionTrail = By.xpath("//*[contains(text(),'Action Trail')]");
    public By actionTrailLatestDropdown = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[7]/span");
    public By actionTrailBillNo = By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p");
    public By actionType = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");

    public String pdfSubWidget1 = "//*[contains(text(),'Bill: ";
    public String pdfSubWidget2 = "')]";

}