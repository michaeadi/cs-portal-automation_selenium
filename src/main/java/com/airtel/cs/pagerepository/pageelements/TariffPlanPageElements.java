package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TariffPlanPageElements {
    public By serviceClassTab = new AirtelByWrapper("serviceClassTab --TabList ", By.xpath("//*[contains(text(),'SERVICE CLASS')]"));
    public By currentPlanName = new AirtelByWrapper("currentPlanName -- ServiceClassTab", By.xpath("//*[contains(@class,'current-plan--heading--value')]"));
    public By currentPlanDetailsCheckBox = new AirtelByWrapper("currentPlanDetailsCheckBox -- CheckBox", By.xpath("//*[contains(@type,'checkbox')]"));
    public By currentPlanDetailsHeaderName = new AirtelByWrapper("currentPlanDetailsHeaderName -- PlanDetails", By.xpath("//big[text()]/b"));
    public By planDescription = new AirtelByWrapper("planDescription --PlanDetails", By.xpath(" //small/ul/li[text()]"));
    public By dropDownName = new AirtelByWrapper("dropDownName -- PlanDetails", By.xpath("//span[contains(@class,'select-tariff-plan--selection--label')]"));
    public By dropDown = new AirtelByWrapper("dropDown -- PlanDetails", By.xpath("//mat-select[contains(@class,'tariff-select')]"));
    public By dropDownList = new AirtelByWrapper("dropDownList -- PlanDetails", By.xpath("//span[@class='mat-option-text']"));
    public By selectedDropDownValue = new AirtelByWrapper("selectedDropDownValue -- PlanDetails", By.xpath("//span[contains(@class,'mat-select-value-text')]/span"));
    public By planFootNote = new AirtelByWrapper("planFootNote -- PlanDetails", By.xpath("//*[contains(@class,'select-tariff-plan--footnote')]"));
    public By migrateButton = new AirtelByWrapper("migrateButton -- PlanDetails", By.xpath("//*[contains(@class,'select-tariff-plan--action-button')]"));
    public By issueDetailPopUp = new AirtelByWrapper("issueDetailPopUp -IssueDetailPopUp",By.xpath("//*[contains(@class,'mat-dialog-container')]"));
    public By commentBox = new AirtelByWrapper("commentBox -- IssueDetailPopUp",By.xpath("//*[contains(@formcontrolname,'comment')]"));
    public By issueDetailsReason = new AirtelByWrapper("issueDetailsReason -- IssueDetailPopUp",By.xpath("//*[contains(@formarrayname,'issueDetails')]"));
    public By cancelBtn = new AirtelByWrapper("cancelBtn -- IssueDetailPopUp",By.xpath("//*[contains(text(),'Cancel')]"));
    public By submitBtn = new AirtelByWrapper("submitBtn -- IssueDetailPopUp",By.xpath("//*[contains(@class,'submit-btn')]"));
    public By selectReason = new AirtelByWrapper("selectReason -- IssueDetailPopUp",By.xpath("//*[contains(@class,'mat-option-text')]"));
    public By noteText = new AirtelByWrapper("noteText -- IssueDetailPopUp",By.xpath("//*[@class='extra-info ng-star-inserted']"));
    public By successMsg = new AirtelByWrapper("successMsg -- PlanChange",By.xpath("//*[contains(text(),'Plan Changed Successfully')]"));
    public By modalSuccessFailureMsg = new AirtelByWrapper("modalSuccessFailureMsg --Plan Change",By.xpath("//*[contains(@class,'main-container__body--message')]"));
}
