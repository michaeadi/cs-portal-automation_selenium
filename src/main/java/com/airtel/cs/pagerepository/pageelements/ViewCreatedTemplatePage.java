package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ViewCreatedTemplatePage {
    public By template = By.xpath("//label[contains(text(),'Templates')]");
    public By searchKeyWord = By.xpath("//input[@placeholder='Search By Name']");
    public By agentChannel = By.xpath("//mat-label[contains(text(),'Agents Channel')]//ancestor::div[1]");
    public By roles = By.xpath("//mat-label[contains(text(),'Roles')]//ancestor::div[1]");
    public By language = By.xpath("//mat-label[contains(text(),'Language')]//ancestor::div[1]");
    public By allActiveTemplate = By.xpath("//div[@class='sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted']");
    public By searchIcon = By.xpath("//img[@class='search-icn']");
    public By allOption = By.xpath("//span[contains(text(),'Select All')]");
    public By options = By.xpath("//mat-option[@role='option']");
    public By allDeActiveTemplate = By.xpath("//div[@class='sms-managment__card-list--card--sms-template--content--sms-card deactivate-card ng-star-inserted']");
    public By pagination = By.xpath("//div[@class='pagination-details']");

    //DeActive Pop up
    public By deActivePopUpTitle = By.xpath("//h1[@class='deactivate-popup__heading red mat-dialog-title']");
    public By popUpMessage = By.xpath("//p[@class='error']");
    public By noBtn = By.xpath("//div[@class='cdk-overlay-container']//button[1]");
    public By yesBtn = By.xpath("//div[@class='cdk-overlay-container']//button[2]");
    public String templateRow="//div[@class='sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted'][";
    public String deleteIcon="]//div//img[@title='delete']";
    public String templateName="]//h6";
    public String templateCategoryName="]//h5";
    public String templateActiveStatus="]//div//p[1]";
    public String templateDeActiveStatue="]//div//p[2]";
    public String editIcon="]//div//img[@title='EDIT']";
    public String commentIcon="]//div//div//img";
    public String templateLanguage="]//div[2]//span";
}
