package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AmLinkedWalletsPage {
    /**
     * Am widget locators
     */
    public By amTransactionsWidget = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']");
    //public By moreIcon = By.xpath("//img[contains(@src,'more.svg')]");
    public By moreIcon = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//img[contains(@src,'more.svg')]");
    public By amProfileDetailsWidget = By.xpath("//mat-tab-group[@class='mat-tab-group mat-primary ng-star-inserted']");
     /**
     * Header's value locators
     */
    public String dataRows = "//div[contains(@class,'table-data-wrapper')]//div[";
    public String dataColumns = "]//div[@data-csautomation-key='dataRows']//div[";
    public String dataValue = "]//span[@data-csautomation-key='columnValue']";
    public By totalRows = By.xpath("//div[@class='card__content restricted ng-star-inserted']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted']");

    /**
     * Wallets tab locators
     */
    public By smsLogs = By.xpath("//span[contains(text(),'SMS LOGS')]");
    public By wallets = By.xpath("//span[contains(text(),'WALLETS')]");
    public By footerAuuid = By.xpath("//div[@data-csautomation-key='AM_PROFILE_DETAILS']//div[contains(@class,'auuid-container')]");
    public By middleAuuid = By.xpath("//div[@data-csautomation-key='AM_PROFILE_DETAILS']");
    public By noResultFound = By.xpath("//div[@class='no-result-found ng-star-inserted']//img");
    public By noResultFoundMessage = By.xpath("//span[contains(text(),'No Result found')]");
    public By widgetErrorMessage = By.xpath("//div[@data-csautomation-key='widgetErrorMsg']");
    public By unableToFetchDataMessage = By.xpath("//div[@data-csautomation-key='widgetErrorMsg']//following-sibling::h3");
    public By walletType = By.xpath("//div[@data-csautomation-key='AM_PROFILE_DETAILS']//span[contains(text(),'Wallet Type')]");
    public By profileId = By.xpath("//span[contains(text(),'Profile ID')]");
    public By balance = By.xpath("//span[contains(text(),'Balance')]");
    public By frozenAmount = By.xpath("//span[contains(text(),'Frozen')]");
    public By fic = By.xpath("//span[contains(text(),'FIC')]");
    public By primary = By.xpath("//span[contains(text(),'Primary')]");
    public By totalCredit = By.xpath("//span[contains(text(),'Credit')]");
    public By totalDebit = By.xpath("//span[contains(text(),'Debit')]");

}
