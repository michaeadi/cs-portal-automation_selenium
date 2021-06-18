package com.airtel.cs.pagerepository.pageelements;


import org.openqa.selenium.By;

public class InteractionPage {
    public By code = By.xpath("//span[contains(@class,'mat-select-placeholder ng-tns-c9-')]");
    public By issues = By.xpath("//span[starts-with(@class,'ng-tns-c9-')]");
    public By search = By.xpath("//input[@placeholder='Search' and @class='search-box mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-star-inserted']");
    public By interactionComment = By.xpath("//textarea[@id='interactionComment']");
    public By saveButton = By.xpath("//button[@class='btn btn-save ng-star-inserted']");
    public By ticketIdOnHeader = By.xpath("//span[@class='ticket-id-color']");
    public By closeInteractions = By.xpath("//mat-icon[@class='tab-close mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted']");
    public By addInteractions = By.xpath("//a[@class='issue-add']");
    public By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    public By issueDetailHeading = By.xpath("//h3[text()='Issue Detail']");
    public By continueButton = By.xpath("//span[contains(text(),'continue')]");
    public By issueDetails = By.xpath("//input[@aria-haspopup='true']//following-sibling::span/label//mat-label");
    public By issueDetailsMandatory = By.xpath("//input[@aria-haspopup='true']//following-sibling::span/label//span");
    public By ticketCommentIcon = By.className("comment-text");
    public By commentBox = By.xpath("//textarea[@placeholder='Add Comment...']");
    public By addCommentBtn = By.xpath("//div[@class='footer']/button");
    public By addedComment = By.xpath("//div[@class='comment-detail ng-star-inserted']");
    public By closeCommentTab = By.xpath("//div[@class='header-close']");
    public By resetBtn = By.xpath("//button[@class='btn btn-reset ng-star-inserted']");
    public By option1st = By.xpath("//mat-option[1]");
    public By noResultFound = By.xpath("//span[text()='No Result Found']");
    public By clickCodeDropDown=By.xpath("//div[@class='mat-select-value']");
    public String issueField=" //input[@name='q";
    public String fieldLabel="']//following-sibling::span/label";
    public String dropDown="]//mat-label";
    public String mandatorySign="]//span";
    public String selectDropDown="]//mat-select";
}
