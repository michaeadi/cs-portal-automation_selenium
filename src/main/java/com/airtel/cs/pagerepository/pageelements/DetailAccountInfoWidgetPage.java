package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DetailAccountInfoWidgetPage {
    public By getTitle = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public By accountInfoIcon = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//img[@class='header-action-icon ng-star-inserted']");
    public By accountInfoDetailWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public By accountInfoTab = By.xpath("//div[@class='mat-tab-labels']/div/div");
    public By accountInfo = By.xpath("//div[@class='mat-tab-label-content']//span[contains(text(),'Account Info')]");
    public By checkboxInAccountInfo = By.xpath("//div[@class='container-fluid ng-star-inserted']/div[1]/div[1]/label");
    public By footerAuuid = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//*[@class='auuid-container']");
    public By middleAuuid = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']");
    public By txnType = By.xpath("//div[@data-csautomation-key='dataRows']/div[2]//span//span");
    public By paginationDetailInfo = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']/div[2]/div[2]/div[1]/div[2]/div[5]/div");
    public By paginationLeftMove = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']/div[2]/div[2]/div[1]/div[2]/div[5]/div[2]/pagination-controls/pagination-template/ul/li[1]");
    public By paginationRightMove = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']/div[2]/div[2]/div[1]/div[2]/div[5]/div[2]/pagination-controls/pagination-template/ul/li[3]");
    public By dateRangeIcon = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']/div[2]/div/form/span/input");
    public By dateRangeDoneButton = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']/div[2]/div/form/span/ngx-daterangepicker-material/div/div[4]/div/button");

    public String packDetailsHeaderRow = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[@data-csautomation-key='headerRow']/div[";
    public String packDetailsHeaderValue = "]";
    public By rows = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted']");
    public String valueRow = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'][";
    public String dateTimeValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][1]/span";
    public String typeValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][2]/span";
    public String statusValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][3]/span";
    public String refNoValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][4]/span";
    public String billAmountValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][5]/span";
    public String amountRecValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][6]/span";
}