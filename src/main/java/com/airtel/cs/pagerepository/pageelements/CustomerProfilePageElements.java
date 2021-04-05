package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CustomerProfilePageElements {
    public By changeServiceClass_btn = new AirtelByWrapper("changeServiceClass_btn -- ActionsDropDown", By.xpath("//button[contains(text(),'Change Service Class')]"));
    public By homePage = new AirtelByWrapper("homePage -- Tab", By.xpath("//*[contains(text(),'HOME')]"));
    public By suspendSIM = new AirtelByWrapper("suspendSIMBtn --ActionsDropDow", By.xpath("//*[contains(text(),'Suspend SIM')]"));
    public By reactivationSIM = new AirtelByWrapper("reactivationSIM --ActionsDropDow", By.xpath("//*[contains(text(),' Reactivate SIM ')]"));
    public By authenticationModal = new AirtelByWrapper("authenticationModal --AuthModal", By.xpath("//*[text()=' Authentication ']"));
    public By authCheckBox = new AirtelByWrapper("authCheckBox --AuthModal", By.xpath("//input[@type='checkbox']"));
    public By authenticateBtn = new AirtelByWrapper("authenticateBtn --AuthModal", By.xpath("//*[text()='Authenticate']/.."));
    public By issueDetailsReason = new AirtelByWrapper("issueDetailsReason", By.xpath("//*[contains(@formarrayname,'issueDetails')]"));
    public By selectReason = new AirtelByWrapper("selectReason", By.xpath("//*[contains(@class,'mat-option-text')]"));
    public By commentBox = new AirtelByWrapper("commentBox -- IssueDetailPopUp", By.xpath("//*[contains(@formcontrolname,'comment')]"));
    public By submitBtn = new AirtelByWrapper("submitBtn -- IssueDetailPopUp", By.xpath("//*[contains(@class,'submit-btn')]"));
    public By modalSuccessFailureMsg = new AirtelByWrapper("modalSuccessFailureMsg --Plan Change", By.xpath("//*[contains(@class,'main-container__body--message')]"));
}
