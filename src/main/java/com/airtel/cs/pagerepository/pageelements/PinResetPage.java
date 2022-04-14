package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class PinResetPage {
    public By resetPinIcon=By.xpath("//img[contains(@src,'images/lock')]");
    public By resetPinIssuePopUp=By.xpath("//div[contains(@class,'sim-suspend')]");
    public By resetPin=By.xpath("//span[contains(text(),'Reset PIN')]");
    public By demographicWidget=By.xpath("//div[contains(@id,'AM_PROFILE')]");

    /*
   Issue Detail Pop up
    */

    public By commentBoxLabel=By.xpath("//div[contains(@class, 'issue-comment--label')]");
    public By selectReasonLabel=By.xpath("//label[contains(text(),'Select Reason *')]");
    public By submitButton =By.xpath("//button[contains(@class,'submit-btn')]");
    public By cancelButton =By.xpath("//button[contains(@class,'no-btn mat-button')]");
    public By cancel=By.xpath("//button[contains(@class,'no-btn')]");
    public By continueButton=By.xpath("//button[contains(@class,'yes-btn mat-button')]");
    public By cancelConfirmMessage = By.xpath("//p[contains(text(),'You have unsaved data')]");
    public By selectReason=By.xpath("//div[@class='mat-select-value']");
    public By commentBox=By.xpath("//textarea[contains(@id, 'interactionComment')]");
    public By confirmationPopUp=By.xpath("//div[@class='main-container']");
    public By successMessage=By.xpath("//div[contains(@class,'main-container__body--message')]");
    public By crossIcon=By.xpath("//span[contains(text(),'Reset PIN')] //following-sibling::mat-icon[contains(@class,'cursor-pointer')]");
    public By selectReasonFromDropdown=By.xpath("//span[contains(text(),'Customer forgot PIN')]");

    /*
    Action Trail tab
     */
    public By viewHistory=By.xpath("//span[contains(text(),'VIEW HISTORY')]");
    public By actionTrail=By.xpath("//div[contains(text(),'Action Trail')]");
    public By actionType=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");
}


