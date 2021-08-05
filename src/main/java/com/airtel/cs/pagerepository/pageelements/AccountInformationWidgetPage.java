package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AccountInformationWidgetPage {

    /**
     * Account Information Widget
     */
    public By getTitle = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//span[@class='card__card-header--label']");
    public By footerAuuid = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//*[@class='auuid-container']");
    public By middleAuuid = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']");
    public By progressBarForCreditLimit = By.xpath("//div[@class='postpaid-container ng-star-inserted']/div[2]/progress");
    public By accountInfoDetailed = By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFORMATION']//img[@class='header-action-icon ng-star-inserted']");
    public By currentMonthUnbillAmount = By.xpath("//div[text()='Current Month Unbilled']/following-sibling::div/span[2]");
    public By lastMonthBillAmount = By.xpath("//div[text()='Last Month Billed Amount']/following-sibling::div/span[2]");

    public By dueDate = By.xpath("//div[text()='Due Date']/following-sibling::div/span");
    public By lastPaymentMode = By.xpath("//div[contains(text(),'Last Payment & Mode')]/following-sibling::div/div/span");
    public By securityDepositValue = By.xpath("//div[contains(text(),'Security Deposit')]/following-sibling::div/span[2]");
    public By securityDepositCurrency = By.xpath("//div[contains(text(),'Security Deposit')]/following-sibling::div/span[1]");
    public By totalOutstanding = By.xpath("//div[text()='Total Outstanding']/following-sibling::div/span[2]");
    public By accountNumber = By.xpath("//div[contains(text(),'Account No.')]/following-sibling::div/span");
    public By totalCreditLimit = By.xpath("//div[text()='Total Credit Limit']/following-sibling::div/span[2]");
    public By availableCreditLimit = By.xpath("//div[text()='Available Credit Limit']/following-sibling::div/span[2]");
    public By currentCycle = By.xpath("//div[text()='Current Cycle']/following-sibling::div/span");
    public By lastMonthBillDate = By.xpath("//div[text()='Last Month Billed Amount']/following-sibling::div/span[3]//span");
    public By othersTab = By.xpath("//*[contains(text(),'Others')]");
    public By emailId = By.xpath("//span[contains(text(),'Email Id')]//following-sibling::span/span");
    public By unableToFetch = By.xpath("//div[@id='GSM_PROFILE']/div[1]/mat-tab-group/div[1]/mat-tab-body[2]/div[1]/div[1]/div[1]//span[2]");

    public By totalCreditLimitCurrency = By.xpath("//div[contains(text(),'Total Credit Limit')]/following-sibling::div/span[1]");
    public By availCreditLimitCurrency = By.xpath("//div[contains(text(),'Available Credit Limit')]/following-sibling::div/span[1]");
    public By lastMonthBillAmountCurrency = By.xpath("//div[contains(text(),'Last Month Billed Amount')]/following-sibling::div/span[1]");
    public By totalOutstandingCurrency = By.xpath("//div[contains(text(),'Total Outstanding')]/following-sibling::div/span[1]");
    public By lastPaymentModeCurrency = By.xpath("//div[contains(text(),'Last Payment & Mode')]/following-sibling::div/span[1]");
    public By currentMonthUnbillCurrency = By.xpath("//div[contains(text(),'Current Month Unbilled')]/following-sibling::div/span[1]");


}