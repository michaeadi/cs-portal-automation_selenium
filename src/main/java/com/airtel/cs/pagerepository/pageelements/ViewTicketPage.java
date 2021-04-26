package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ViewTicketPage {
    public By ticketIdValue = By.xpath("//span[@class='blueColor ellipsis']");
    public By arrowIcon = By.xpath("//div[@class='mat-form-field-infix']//div[@class='mat-select-arrow-wrapper']");
    public By submitAs = By.className("submit-btn");
    public By stateName = By.xpath("//button[@class='sbmit-colse-btn']//span[2]");
    public By addCommentBox = By.xpath("//textarea[@placeholder='Add Comment...']");
    public By addBtn = By.xpath("//button[@class='add-button']//span");
    public By allComment = By.xpath("//table[@class='ng-star-inserted']/tbody/tr");
    public By allTicketState = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public By continueBtn = By.xpath("//span[contains(text(),'continue')]");
    public By cancelBtn = By.xpath("//button[@class='no-btn mat-button']");
    public By backButton = By.xpath("//button[@class=\"back mat-button\"]");
}
