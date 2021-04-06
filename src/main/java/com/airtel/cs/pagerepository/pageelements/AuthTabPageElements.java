package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AuthTabPageElements {
    public By authTabTitle = By.xpath("//app-authentication-block-modal//span[@class=\"main-container__header--title\"]");
    public By authCloseBtn = By.xpath("//mat-icon[contains(text(),'close')]");
    public By listOfQuestions = By.xpath("//app-authentication-block-modal//div[1]//div[2]//div[1]//div[@class=\"main-container__body--left--wrapper ng-star-inserted\"]");
    public By authInstruction = By.xpath("//div[@class=\"main-container__body--right--instructions\"]//p");
    public By notAuthBtn = By.xpath("//div[@class=\"main-container__body--right--buttons\"]//button[1]");
    public By authBtn = By.xpath("//div[@class=\"main-container__body--right--buttons\"]//button[2]");

    //SIM Bar/Unbar pop up
    public By simBarTitle = By.xpath("//div[@class=\"main-container__header\"]//span");
    public By simCloseBtn = By.xpath("//div[@class=\"main-container__header\"]//mat-icon");
    public By issueDetails = By.xpath("//div[@formarrayname=\"issueDetails\"]//div[contains(text(),'Issue Detail:')]");
    public By listOfIssue = By.xpath("//div[@formarrayname=\"issueDetails\"]//mat-select");
    public By options = By.xpath("//mat-option");
    public By commentBox = By.xpath("//textarea[@id='interactionComment']");
    public By cancelBtn = By.xpath("//button[@class=\"no-btn\"]");
    public By submitBtn = By.xpath("//button[@class=\"submit-btn\"]");
    public By code = By.xpath("//mat-option[1]//span");
}
