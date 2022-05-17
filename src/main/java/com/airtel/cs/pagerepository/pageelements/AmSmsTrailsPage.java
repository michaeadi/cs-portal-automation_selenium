package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AmSmsTrailsPage {
    /**
     * AM widget locators
     */
    public By amTransactionsWidget = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']");
    public By moreIcon = By.xpath("//div[@data-csautomation-key='AIRTEL_MONEY']//img[contains(@src,'more.svg')]");
    public By amProfileDetailsWidget = By.xpath("//mat-tab-group[@class='mat-tab-group mat-primary ng-star-inserted']");
    public By demographics = By.xpath("//div[@class='main-container__header success']//span");

    /**
     * SMS Logs widget locators
     */
    public By smsLogs = By.xpath("//span[contains(text(),'SMS LOGS')]");
    public By timestamp = By.xpath("//span[contains(text(),'Timestamp')]");
    public By transactionId = By.xpath("//span[contains(text(),'Transaction ID')]");
    public By smsBody = By.xpath("//span[contains(text(),'SMS Body')]");
    public By smsId = By.xpath("//span[contains(text(),'SMS ID')]");
    public By dateFilter = By.xpath("//form[contains(@name,'filter')]");
    public By cancelButton = By.xpath("//button[contains(@class,'btn btn-default')]");
    public By calendar = By.xpath("//div[contains(@class,'md-drppicker')]");
    public By footerAuuid = By.xpath("//div[@data-csautomation-key='AM_PROFILE_DETAILS']//div[contains(@class,'auuid-container')]");
    public By middleAuuid = By.xpath("//div[@data-csautomation-key='AM_PROFILE_DETAILS']");
    public By noResultFound = By.xpath("//div[@class='no-result-found ng-star-inserted']//img");
    public By noResultFoundMessage = By.xpath("//span[contains(text(),'No Result found')]");
    public By widgetErrorMessage = By.xpath("//div[@data-csautomation-key='widgetErrorMsg']");
    public By unableToFetchDataMessage = By.xpath("//div[@data-csautomation-key='widgetErrorMsg']//following-sibling::h3");

    /**
     * Header's value locators
     */
    public String dataRows = "//div[contains(@class,'table-data-wrapper')]//div[";
    public String dataColumns = "]//div[@data-csautomation-key='dataRows']//div[";
    public String dataValue = "]//span[@data-csautomation-key='columnValue']";
    public String actionValue = "]//span";
    public By totalRows = By.xpath("//div[@class='card__content restricted ng-star-inserted']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted']");
    public By totalSMSRow = By.xpath("//div[@data-csautomation-key='paginationResult']");
    public By nextPage = By.xpath("//*[contains(@class,'pagination-next')]/a");

    /**
     * Pagination locators
     */
    public By previousBtnDisable = By.xpath("//li[@class='pagination-previous disabled ng-star-inserted']");
    public By previousBtnEnable = By.xpath("//li[@class='pagination-previous ng-star-inserted']");
    public By nextBtnDisable = By.xpath("//li[@class='pagination-next disabled ng-star-inserted']");
    public By nextBtnEnable = By.xpath("//li[@class='pagination-next ng-star-inserted']");
    public By paginationCount = By.xpath("//*[@class='pagination-details']");
    public By pagination = By.xpath("//div[@class='pagination ng-star-inserted']");


    /**
     * Resend SMS locators
     */

    public By reSendSmsLink = By.xpath("//span[contains(text(),'Resend')]");
    public By resendSms = By.xpath("//div[@class='quick-action ng-star-inserted']//span[contains(text(),'Resend SMS')]");
    public By smsHeader = By.xpath("//div[@class='main-container__header']//span[contains(text(),'Send SMS')]");
    public By smsIssueDetail = By.xpath("//div[contains(text(),'Issue Detail:')]");
    public By enterComment = By.xpath("//div[@class='main-container__body--form-data--issue-comment--label']//span[normalize-space()='Enter Comment']");
    public By smsSelectReason = By.xpath("//label[contains(text(),'Select Reason *')]");
    public By selectArrow = By.xpath("//div[@class='mat-select-arrow-wrapper']");
    public By selectCustomerRequestFromDropdown = By.xpath("//span[normalize-space()='Customer Request']");
    public By smsTextArea = By.xpath("//div[contains(@class,'mat-form-field')]//textarea[@id='interactionComment']");
    public By cancelSms = By.xpath("//span[contains(@class,'buttons')]//span[contains(text(),'Cancel')]");
    public By submitSms = By.xpath("//span[contains(@class,'buttons')]//span[contains(text(),'Submit')]");
    public By successMessage = By.xpath("//div[contains(text(),'Sms has been resent on your device')]");
    public By crossIcon = By.xpath("//div[contains(@class,'main-container__header success')]//mat-icon[contains(text(),'close')]");

    /**
     * Action Trail tab
     */
    public By homePage = By.xpath("//span[contains(text(),'HOME')]");
    public By viewHistory = By.xpath("//span[contains(text(),'VIEW HISTORY')]");
    public By actionTrail = By.xpath("//div[contains(text(),'Action Trail')]");
    public By actionType = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment = By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");


}