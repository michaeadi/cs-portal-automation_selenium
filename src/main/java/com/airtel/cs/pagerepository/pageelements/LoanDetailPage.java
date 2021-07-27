package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class LoanDetailPage {
    /*
     * Loan Detail Widget Element Locator
     * */
    public By loanDetailWidgetTitle = By.xpath("//div[@data-csautomation-key='LOAN_DETAILS']//span[@class='card__card-header--label']");
    public By headerList = By.xpath("//div[@data-csautomation-key='LOAN_DETAILS']//div[2]//div[@class='card__card-header--card-body--table--list-heading left-spacing']//div");


    /*
     * Loan History Widget Element Locator
     * */
    public By loanHistoryWidgetTitle = By.xpath("//div[@data-csautomation-key='LOAN_HISTORY']//span[@class='card__card-header--label']");
    public By historyHeaderList = By.xpath("//div[@data-csautomation-key='LOAN_HISTORY']//div[2]//div[@class='card__card-header--card-body--table--list-heading left-spacing']//div");

    /*
     * Loan Recoveries Widget Element Locator
     * */
    public By recoveryWidgetHeaderList = By.xpath("//div[@data-csautomation-key='LOAN_HISTORY']//div[2]//div[@class='card__card-header--card-body--table--data-list innertable-area ng-star-inserted'][1]//app-loan-recoveries/div/div[1]//div[@class='inner-wrapper ng-star-inserted']");
    public By closeTab = By.xpath("//mat-icon[contains(text(),'close')]");
    public By homeTab = By.xpath("//*[contains(text(),'HOME')]");
    
    public String widgetHeader="//div[@data-csautomation-key='LOAN_DETAILS']//div[2]//div[@class='card__card-header--card-body--table--list-heading left-spacing']//div[";
    public String value="]//span";
    public String columnValue="//div[@data-csautomation-key='LOAN_DETAILS']//div[2]//div[@class='card__card-header--card-body--table--data-list innertable-area ng-star-inserted']//div[";
    public String loanRecoveryWidgetHeader="//div[@data-csautomation-key='LOAN_HISTORY']//div[2]//div[@class='card__card-header--card-body--table--list-heading left-spacing']//div[";
    public String recoveryHeaderValue="]//div[";
    public String recoveryText="]/span";
    public String transactionId="]//div[1]//span";
    public String headerValue="]//app-loan-recoveries/div/div[1]//div[@class='inner-wrapper ng-star-inserted'][";
    public String recoveryColumnValue="]//app-loan-recoveries//div[@class='listing-wrapper ng-star-inserted'][";
    public String recoveryColumnText="]//div[@class='inner-wrapper ng-star-inserted'][";

}

