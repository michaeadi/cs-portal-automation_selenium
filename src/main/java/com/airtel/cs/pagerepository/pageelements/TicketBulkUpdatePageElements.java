package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TicketBulkUpdatePageElements {
    public By uploadFile = By.xpath("//input[@id='file']");
    public By downloadFile = By.xpath("//a[@class=\"download-template\"]");
    public By errorMessage = By.xpath("//div[@class=\"error-container\"]");
    public By pageTitle = By.xpath("//span[contains(text(),'Bulk Update')]");
    public By selectFilter = By.xpath("//span[@class=\"filter\"]");
    public By ticketList = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"]");
    public By maxSelectMessage = By.xpath("//div[@class=\"negative-applied-filter ng-star-inserted\"]//p");
    public By clearFilter = By.xpath("//button[@class=\"mat-button\"]//span[contains(text(),'Clear Filter')]");
    public By nextBtn = By.xpath("//button[@class=\"next\"]");
    public By cancelBtn = By.xpath("//button[@class=\"cancel\"]");
    public By transferToQueue = By.xpath("//form//div//div[1]//div[@class=\"label customer-checkbox\"]//label");
    public By selectTransferToQueue = By.xpath("//form//div[@class=\"choose-operation__form-section--form--input ng-star-inserted\"][1]//mat-form-field//mat-select");
    public By selectChangeState = By.xpath("//form//div[@class=\"choose-operation__form-section--form--input ng-star-inserted\"][2]//mat-form-field//mat-select");
    public By changeState = By.xpath("//form//div//div[2]//div[@class=\"label customer-checkbox\"]//label");
    public By ticketComment = By.xpath("//form//div//div[3]//div[@class=\"label customer-checkbox\"]//label");
    public By backBtn = By.xpath("//button[contains(text(),'BACK')]");
    public By options = By.xpath("//span[@class=\"mat-option-text\"]");
    public By popUpCancelBtn = By.xpath("//div[@class=\"deactivate-popup__button-section mat-dialog-actions\"]//button[1]");
    public By popUpContinueBtn = By.xpath("//div[@class=\"deactivate-popup__button-section mat-dialog-actions\"]//button[2]");
    public By commentBox = By.xpath("//textarea[@type='textarea']");
    public By confirmAction = By.xpath("//div[@class=\"tnc customer-checkbox ng-star-inserted\"]//input");
    public By statueBar = By.xpath("//span[@class=\"bar-fill-stripes\"]");
    public By updateMessage = By.xpath("//div[@class=\"bar-status\"]");
    public By successTicketId = By.xpath("//li[@class=\"id-section successful\"]");
    public By errorTicketId = By.xpath("//li[@class=\"id-section error-bg\"]//span[2]");
    public By errorTicketMessage = By.xpath("//div[@class=\"bar-status\"]//span");
    public By closeFilter = By.xpath("//span[@class='close-button']");
}
