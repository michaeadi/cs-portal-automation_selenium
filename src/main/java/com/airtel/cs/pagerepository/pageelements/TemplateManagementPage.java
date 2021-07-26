package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TemplateManagementPage {
    public By createdTemplateTab = By.xpath("//div[@role='tab'][1]");
    public By viewCreatedTemplateTab = By.xpath("//div[@role='tab'][2]");
    public By addTemplateBtn = By.xpath("//mat-radio-button[1]");
    public By addTemplateCategoryBtn = By.xpath("//mat-radio-button[2]");
    public By categoryLabel = By.xpath("//label[contains(text(),'Category')]");
    public By messageChannelLabel = By.xpath("//label[contains(text(),'Message Channel')]");
    public By templateNameLabel = By.xpath("//label[contains(text(),'Template Name')]");
    public By roleLabel = By.xpath("//label[contains(text(),'Role')]");
    public By agentChannelLabel = By.xpath("//label[contains(text(),'Agents Channel')]");
    public By smsLanguageLabel = By.xpath("//label[contains(text(),'SMS Language')]");
    public By smsContentLabel = By.xpath("//label[contains(text(),'SMS Content')]");
    public By selectedLanguages = By.xpath("//mat-tab-group[@class='teatarea mat-tab-group mat-primary ng-star-inserted']//div[@class='mat-tab-label-container']//div[@role='tab']");

    //Add template Category Tab
    public By templateCategoryName = By.xpath("//input[@placeholder='Enter Category Name']");
    public By addCategoryBtn = By.xpath("//span[contains(text(),'Add Category')]");
    public By allCategoryList = By.xpath("//div[@class='sms-managment__card-list--card--content-area--content ng-star-inserted']//div[@class='sms-managment__card-list--card--content-area--content--sms-card ng-star-inserted']");

    //Add Template
    public By openCategoryList = By.xpath("//app-custom-mat-select[@formcontrolname='templateCategory']//mat-select[@role='listbox']");
    public By templateNameBox = By.xpath("//input[@formcontrolname='templateName']");
    public By openRoleList = By.xpath("//app-custom-mat-select[@formcontrolname='roles']//mat-select[@role='listbox']");
    public By openChannelList = By.xpath("//app-custom-mat-select[@formcontrolname='channels']//mat-select[@role='listbox']");
    public By openLangList = By.xpath("//mat-select[@formcontrolname='language']");
    public By smsContent = By.xpath("//textarea[@placeholder='Content']");
    public By allOption = By.xpath("//mat-option[@role='option']");
    public By cancelBtn = By.xpath("//button[contains(text(),'Cancel')]");
    public By createBtn = By.xpath("//button[contains(text(),'Create')]");

    public By message = By.xpath("//mat-dialog-container[@role='dialog']//app-template-modal//div//div//p");
    public String categoryName="//div[@class='sms-managment__card-list--card--content-area--content ng-star-inserted']//div[@class=sms-managment__card-list--card--content-area--content--sms-card ng-star-inserted][";
}
