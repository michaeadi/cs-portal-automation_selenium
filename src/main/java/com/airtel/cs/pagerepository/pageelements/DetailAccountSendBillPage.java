package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DetailAccountSendBillPage {

    public String sendBill1 = "//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//div[contains(@class,'table-data-wrapper')]/div[";
    public String sendBill2 = "]//img[contains(@src,'send')]";
    public By unableToFetchError=By.xpath("//div[@data-csautomation-key='POSTPAID_ACCOUNT_INFO_DETAIL']//h3[contains(text(),'Unable to Fetch Data')]");
    public By demographicEmail=By.xpath("//span[contains(text(),'Email Id')]//following-sibling::span");

    /*
    Issue Detail Pop up
     */
    public By issueDetailPopUp=By.xpath("//span[contains(text(),'Send Postpaid')]");
    public By billNumberLabel=By.xpath("//label[contains(text(),'Bill Number *')]");
    public By emailIDLabel=By.xpath("//label[contains(text(),'Email ID *')]");
    public By billNumber=By.xpath("//label[contains(text(),'Bill Number *')]//following-sibling::input");
    public By prePopulatedEmailID =By.xpath("//label[contains(text(),'Email ID *')]//following-sibling::input");
    public By commentBoxLabel=By.xpath("//div[contains(@class, 'issue-comment--label')]");
    public By selectReasonLabel=By.xpath("//label[contains(text(),'Select Reason *')]");
    public By submitButton =By.xpath("//button[contains(text(),'Submit')]");
    public By cancelButton =By.xpath("//button[contains(text(),'Cancel')]");
    public By continueButton=By.xpath("//span[contains(text(),'continue')]");
    public By cancel=By.xpath("//span[contains(text(),'cancel')]");
    public By cancelConfirmMessage = By.xpath("//p[contains(text(),'You have unsaved data')]");
    public By selectReason=By.xpath("//div[@class='mat-select-value']");
    public By selectReasonFromDropdown=By.xpath("//span[contains(text(),'Customer Request')]");
    public By commentBox=By.xpath("//div[contains(@class, 'issue-comment')]//following-sibling::mat-form-field");
    public By confirmationPopUp=By.xpath("//div[@class='main-container']");
    public By successMessage=By.xpath("//div[contains(@class,'main-container__body--message')]");
    public By crossIcon=By.xpath("//mat-icon[contains(@class,'cursor-pointer')]");
    public By hoverSendBill=By.xpath("//div[contains(@class,'ng-trigger-state')]");

    /*
    Action Trail tab
     */
    public By homePage=By.xpath("//span[contains(text(),'HOME')]");
    public By viewHistory=By.xpath("//span[contains(text(),'VIEW HISTORY')]");
    public By actionTrail=By.xpath("//div[contains(text(),'Action Trail')]");
    public By actionTrailLatestDropdown=By.xpath("//span[contains(@class,'cursor')]");
    public By actionTrailBillNo=By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p");
    public By actionTrailEmailId=By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p[2]");
    public By actionType=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");
}
