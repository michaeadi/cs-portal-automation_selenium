package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class SendSMSPage {
    public By sendSMSTitle = By.xpath("//label[contains(text(),'Send Message')]");
    public By customerNumber = By.xpath("//div[@class=\"send-managment__card-list--card--content-area--option-section--form\"][1]//div[@class=\"mat-select-value\"]");
    public By openCategory = By.xpath("//app-custom-mat-select[@formcontrolname=\"categorySelect\"]//mat-select");
    public By openTemplates = By.xpath("//div[@class=\"send-managment__card-list--card--content-area--option-section--form--options\"][2]//mat-select");
    public By openLanguage = By.xpath("//div[@class=\"send-managment__card-list--card--content-area--option-section--form--options\"][3]//mat-select");
    public By selectOption1 = By.xpath("//mat-option[1]");
    public By messageContent = By.xpath("//textarea[@formcontrolname=\"messageContent\"]");
    public By successMessage = By.xpath("//p[contains(text(),'Message sent successfully to')]");
    public By cancelBtn = By.xpath("//div[@class='action-btn']//span[contains(text(),'CANCEL')]");
    public By submitBtn = By.xpath("//div[@class='action-btn']//span[contains(text(),'SEND')]");
    public By searchCategory = By.xpath("//div[@class='input-search ng-star-inserted']//input[@placeholder='Search']");
    public By messageReadOnly = By.xpath("//textarea[@readonly=\"true\"]");
    public By sendBtnDisabled = By.xpath("//button[@class='disabled-send-button']");
    public By customerNumberText = By.xpath("//span[contains(text(),'- Primary Number')]");

}
