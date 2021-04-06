package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class LoanDetailPageElements {
    /*
     * Loan Detail Widget Element Locator
     * */
    public By loanDetailWidgetTitle = By.xpath("//div[@id='LOAN_DETAILS']//span[@class='card__card-header--label']");
    public By headerList = By.xpath("//div[@id='LOAN_DETAILS']//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div");


    /*
     * Loan History Widget Element Locator
     * */
    public By loanHistoryWidgetTitle = By.xpath("//div[@id='LOAN_HISTORY']//span[@class='card__card-header--label']");
    public By historyHeaderList = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div");

    /*
     * Loan Recoveries Widget Element Locator
     * */
    public By recoveryWidgetHeaderList = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][1]//app-loan-recoveries/div/div[1]//div[@class=\"inner-wrapper ng-star-inserted\"]");
    public By closeTab = By.xpath("//mat-icon[contains(text(),'close')]");
    public By homeTab = By.xpath("//*[contains(text(),'HOME')]");
}

