package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class LinkedMsisdnToAccountNoPage {
    public By getTitle=By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//span[@class='card__card-header--label']");
    public By footerAuuid=By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//*[@class='auuid-container']");
    public By middleAuuid=By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']");
    public By getDetailAccInfoWidget=By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//span[@class='card__card-header--label']");
    public By accountInfoIcon = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//img[@class='header-action-icon ng-star-inserted']");
    public By accountInfo = By.xpath("//div[@class='tabs-container']//div[@class='mat-tab-labels']/div[3]/div[1]//span");
    public By accountInfoTab = By.xpath("//div[@class='mat-tab-label-content']//span[contains(text(),'Account Info')]");
    public By linkedMsisdnWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//span[@class='card__card-header--label']");

    public By msisdnCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[1]//span");
    public By simNumberCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[2]//span");
    public By gsmSerActDetailCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[3]//span");
    public By lineTypeCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[4]//span");
    public By paymentLvlCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[5]//span");
    public By gsmStsCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[6]//span");
    public By currentUsgLmtCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[7]//span");
    public By vipCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[8]//span");
    public By cugCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[9]//span");
    public By dndCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[10]//span");
    public By securityDepCol = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[2]/div[1]/div[1]/div[11]//span");
    public By linkedMsisdnPagination = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//div[@data-csautomation-key='dataRows']");
    public By checkboxAccountInfo = By.xpath("//div[@class='container-fluid ng-star-inserted']/div[1]/div[1]/label[1]/input");
    public By checkboxLinkedMsisdn = By.xpath("//div[@class='container-fluid ng-star-inserted']/div[1]/div[1]/label[2]/input");
    public String packDetailsHeaderRow = "//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//div[@data-csautomation-key='headerRow']/div[";
    public String packDetailsHeaderValue = "]";

    public String valueRow = "//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'][";
    public String msisdnValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][1]/span";
    public String simValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][2]/span";
    public String lineTypeValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][4]/span";
    public String paymentLvlValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][5]/span";
    public String gsmStatusValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][6]/span";
    public String currentUsageLmtValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][7]/span";
    public String vipValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][8]/span";
    public String cugValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][9]/span";
    public String dndValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][10]/span";
    public String securityDepValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][11]/span";
    public By pagination = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//*[@class='pagination-details']");
    public By searchMsisdnInLinkedMsisdnWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']/div[2]/div[1]//span[1]//span/input");
    public By searchBtn = By.xpath("//button[@data-csautomation-key='searchBtn']");
    public By invalidNumber = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//span[contains(text(),'Invalid Mobile Number')]");

    public By prevPageArrow = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//li[@class='pagination-previous disabled ng-star-inserted']");
    public By nextPageArrow = By.xpath("//div[@data-csautomation-key='POSTPAID_LINKED_MSISDN']//li[@class='pagination-next disabled ng-star-inserted']");
    public By activePackWidgetOnPM = By.xpath("//div[@class='mat-tab-labels']/div/div");

}