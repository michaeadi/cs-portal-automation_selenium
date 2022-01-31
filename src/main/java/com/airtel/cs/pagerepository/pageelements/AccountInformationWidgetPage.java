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
    public By currentMonthUnbillAmount = By.xpath("//span[@id='currentMonthUnbilledAmount']");
    public By lastMonthBillAmount = By.xpath("//span[@id='lastMonthBilledAmount']");

    public By dueDate = By.xpath("//span[@id='dueDate']");
    public By lastPaymentMode = By.xpath("//div[contains(text(),'Last Payment & Mode')]/following-sibling::div/div/span");
    public By securityDepositValue = By.xpath("//div[contains(text(),'Security Deposit')]/following-sibling::div/span[2]");
    public By securityDepositCurrency = By.xpath("//div[contains(text(),'Security Deposit')]/following-sibling::div/span[1]");
    public By totalOutstanding = By.xpath("//span[@id='totalOutstandingAmount']");
    public By accountNumber = By.xpath("//div[contains(text(),'Account No.')]/following-sibling::div/span");
    public By totalCreditLimit = By.xpath("//span[@id='totalCreditLimit']");
    public By availableCreditLimit = By.xpath("//span[@id='availableCreditLimit']");
    public By currentCycle = By.xpath("//span[@id='currentCycle']");
    public By lastMonthBillDate = By.xpath("//div[contains(text(),'Last Month Billed Amount')]//following-sibling::div/span[3]/span");
    public By othersTab = By.xpath("//*[contains(text(),'Others')]");
    public By emailId = By.xpath("//span[contains(text(),'Email Id')]//following-sibling::span/span");
    public By unableToFetch = By.xpath("//div[@id='GSM_PROFILE']/div[1]/mat-tab-group/div[1]/mat-tab-body[2]/div[1]/div[1]/div[1]//span[2]");
    public By tempCreditCurrency = By.xpath("//div[text()='Temp Credit Limit ']/following-sibling::div/span[1]");
    public By tempCreditLimit = By.xpath("//div[text()='Temp Credit Limit ']/following-sibling::div/span[2]");
    public By tempCreditLimitInfoIcon = By.xpath("//div[text()='Temp Credit Limit ']/following-sibling::div/span[3]");
    public By validTillDate = By.xpath("//div[contains(@class, 'mat-tooltip') and contains(text(),'Valid Till')]");
    public By currentCycleEndDate = By.xpath("//div[text()='Current Cycle ']/following-sibling::div/span[1]");
    public By raiseSRIcon=By.xpath("//div[contains(text(),'Temp Credit Limit')]//img");
    public By lastPaymentDate = By.xpath("//div[contains(text(),'Last Payment & Mode')]/following-sibling::div/span[3]/span");
    public By lastPaymentAmount = By.xpath("//span[@id='lastPaymentAmount']");

    /**
     * Issue Detail Pop up
     */
    public By popupTitle=By.xpath("//span[contains(text(),'Set Temporary Limit')]");
    public String successMessage="//div[@class='main-container__body--message ng-star-inserted']";
    public By closePopup=By.xpath("//mat-icon[contains(text(),'close')]");
    public By ticketId=By.xpath(successMessage+"/b[1]");
    public By expectedClosureDate=By.xpath(successMessage+"/b[2]");
    public By accountNumberInput=By.xpath("//label[contains(text(),'Account No')]//following-sibling::input");
    public By amountField=By.xpath("//label[contains(text(),'Amount')]//following-sibling::input");


    public By totalCreditLimitCurrency = By.xpath("//div[contains(text(),'Total Credit Limit')]/following-sibling::div/span[1]");
    public By availCreditLimitCurrency = By.xpath("//div[contains(text(),'Available Credit Limit')]/following-sibling::div/span[1]");
    public By lastMonthBillAmountCurrency = By.xpath("//div[contains(text(),'Last Month Billed Amount')]/following-sibling::div/span[1]");
    public By totalOutstandingCurrency = By.xpath("//div[contains(text(),'Total Outstanding')]/following-sibling::div/span[1]");
    public By lastPaymentModeCurrency = By.xpath("//div[contains(text(),'Last Payment & Mode')]/following-sibling::div/span[1]");
    public By currentMonthUnbillCurrency = By.xpath("//div[contains(text(),'Current Month Unbilled')]/following-sibling::div/span[1]");


}