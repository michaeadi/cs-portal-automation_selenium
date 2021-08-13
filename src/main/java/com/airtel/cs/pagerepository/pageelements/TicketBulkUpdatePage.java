package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TicketBulkUpdatePage {
    public By uploadFile = By.xpath("//input[@id='file']");
    public By downloadFile = By.xpath("//a[@class='download-template']");
    public By errorMessage = By.xpath("//div[@class='error-container']");
    public By pageTitle = By.xpath("//span[contains(text(),'Bulk Update')]");
    public By selectFilter = By.xpath("//span[@class='filter']");
    public By ticketList = By.xpath("//div[@class='container-fluid table-card ng-star-inserted']");
    public By maxSelectMessage = By.xpath("//div[@class='negative-applied-filter ng-star-inserted']//p");
    public By clearFilter = By.xpath("//button[@class='mat-button']//span[contains(text(),'Clear Filter')]");
    public By nextBtn = By.xpath("//button[@class='next']");
    public By cancelBtn = By.xpath("//button[@class='cancel']");
    public By transferToQueue = By.xpath("//form//div//div[1]//div[@class='label customer-checkbox']//label");
    public By selectTransferToQueue = By.xpath("//form//div[@class='choose-operation__form-section--form--input ng-star-inserted'][1]//mat-form-field//mat-select");
    public By selectChangeState = By.xpath("//form//div[@class='choose-operation__form-section--form--input ng-star-inserted'][2]//mat-form-field//mat-select");
    public By changeState = By.xpath("//form//div//div[2]//div[@class='label customer-checkbox']//label");
    public By ticketComment = By.xpath("//form//div//div[3]//div[@class='label customer-checkbox']//label");
    public By backBtn = By.xpath("//button[contains(text(),'BACK')]");
    public By options = By.xpath("//span[@class='mat-option-text']");
    public By popUpCancelBtn = By.xpath("//div[@class='deactivate-popup__button-section mat-dialog-actions']//button[1]");
    public By popUpContinueBtn = By.xpath("//span[contains(text(),'Continue')]/..");
    public By commentBox = By.xpath("//textarea[@type='textarea']");
    public By confirmAction = By.xpath("//div[@class='tnc customer-checkbox ng-star-inserted']//input");
    public By statueBar = By.xpath("//span[@class='bar-fill-stripes']");
    public By updateMessage = By.xpath("//div[@class='bar-status']");
    public By successTicketId = By.xpath("//li[@class='id-section successful']");
    public By errorTicketId = By.xpath("//li[@class='id-section error-bg']//span[2]");
    public By errorTicketMessage = By.xpath("//div[@class='bar-status']//span");
    public By closeFilter = By.xpath("//span[@class='close-button']");
    public String option = "//mat-option[";
    public String getText = "]//span";
    public By okButton = By.xpath("//div[@class='action']/button");
    public String ticketRows = "//div[@class='container-fluid table-card ng-star-inserted'][";
    public String ticketIds = "]//ul[1]//li[1]//span[2]";
    public By sourceTitleTicketRowTicketListing = By.xpath("//*[@class='data-area-full']//span[contains(text(),'Source')]");

    public By issueDropDownCategory = By.xpath("//div[@formarrayname='category']/div[3]//div[@class='mat-select-value']/span");
    public By searchButtonDropDownCategory = By.xpath("//input[@placeholder='Search']");
    public By issueType = By.xpath("//div[@formarrayname='category']/div[4]//div[@class='mat-select-value']/span");
    public By dateDurationCD = By.xpath("//mat-radio-group[@formcontrolname='days']/mat-radio-button[3]/label/div[2]/span[2]");
    public By startDateCD = By.xpath("//input[@formcontrolname='startDate']");
    public By endDateCD = By.xpath("//input[@formcontrolname='endDate']");
    public By resetFilter = By.xpath("//span[contains(text(),' Reset Filters ')]");
    public By endDateNotLessThanStartDate = By.xpath("//div[@class='filter-box']//mat-error");
    public By applyFilter = By.xpath("//button[@class='filter-button mat-button']");

}
