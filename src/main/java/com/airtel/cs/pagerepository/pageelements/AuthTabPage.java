package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AuthTabPage {
    public By authTabTitle = By.xpath("//app-authentication-block-modal//span[@class='main-container__header--title']");
    public By authCloseBtn = By.xpath("//mat-icon[contains(text(),'close')]");
    public By listOfQuestions = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class='main-container__body--left--wrapper ng-star-inserted']");
    public By authInstruction = By.xpath("//div[@class='main-container__body--right--instructions']//p");
    public By notAuthBtn = By.xpath("//div[@class='main-container__body--right--buttons']//button[1]");
    public By authBtn = By.xpath("//div[@class='main-container__body--right--buttons']//button[2]");
    public By widgetUnlockMsg = By.xpath("//*[@class='wait']/following-sibling::div");

    //SIM Bar/Unbar pop up
    public By simBarTitle = By.xpath("//div[@class='main-container__header']//span");
    public By simCloseBtn = By.xpath("//div[@class='main-container__header']//mat-icon");
    public By issueDetails = By.xpath("//div[contains(text(),'Issue Detail:')]");
    public By listOfIssue = By.xpath("//div[@formarrayname='issueDetails']//mat-select");
    public By listOfFields=By.xpath("//div[@formarrayname='issueDetails']//input");
    public String questionField="//div[@formarrayname='issueDetails']//input[@name='q";
    public By options = By.xpath("//mat-option");
    public By commentBox = By.xpath("//textarea[@id='interactionComment']");
    public By cancelBtn = By.xpath("//button[@class='no-btn']");
    public By submitBtn = By.xpath("//button[@class='submit-btn']");
    public By code = By.xpath("//mat-option[1]//span");
    public By toastModal = By.xpath("//div[contains(@class,'main-container__body--message')]");
    public By closeBtn = By.xpath("//following-sibling::mat-icon[contains(text(),'close')]");
    public By errorMessage=By.xpath("//span[@class='message-container']/p[@class='error-message ng-star-inserted']");
    public String authTabQuestion="//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class='main-container__body--left--wrapper ng-star-inserted'][";
    public String question="]//span[1]";
    public String answer="]//span[2]";
    public String authTabCheckBox="//*[@class='main-container__body--left--wrapper ng-star-inserted'][";
    public String checkBox="]//mat-checkbox";
    public By submitYesBtn = By.xpath("//button[@class='yes-btn']");

    public By confirmationPopUp = By.xpath("//div[@class='main-container']");
    public By successMessage = By.xpath("//div[contains(@class,'main-container__body--message')]");
    public By crossIcon = By.xpath("//following-sibling::mat-icon[contains(@class,'cursor-pointer')]");
    public By selectReasonFromDropdown = By.xpath("//span[contains(text(),'Customer not')]");
    public By selectReason = By.xpath("//div[contains(@class,'sim-suspend')] //div[@class='mat-select-value']");

}
