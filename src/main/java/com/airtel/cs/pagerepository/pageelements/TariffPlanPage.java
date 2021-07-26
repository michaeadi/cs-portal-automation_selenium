package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TariffPlanPage {
    public By serviceClassTab = By.xpath("//*[contains(text(),'SERVICE CLASS')]");
    public By currentPlanName = By.xpath("//*[contains(@class,'current-plan--heading--value')]");
    public By currentPlanDetailsCheckBox = By.xpath("//*[contains(@type,'checkbox')]");
    public By currentPlanDetailsHeaderName = By.xpath("//big[text()]/b");
    public By planDescription = By.xpath(" //small/ul/li[text()]");
    public By dropDownName = By.xpath("//span[contains(@class,'select-tariff-plan--selection--label')]");
    public By dropDown = By.xpath("//mat-select[contains(@class,'tariff-select')]");
    public By dropDownList = By.xpath("//span[@class='mat-option-text']");
    public By selectedDropDownValue = By.xpath("//span[contains(@class,'mat-select-value-text')]/span");
    public By planFootNote = By.xpath("//*[contains(@class,'select-tariff-plan--footnote')]");
    public By migrateButton = By.xpath("//*[contains(@class,'select-tariff-plan--action-button')]");
    public By issueDetailPopUp = By.xpath("//*[contains(@class,'mat-dialog-container')]");
    public By commentBox = By.xpath("//*[contains(@formcontrolname,'comment')]");
    public By issueDetailsReason = By.xpath("//*[contains(@formarrayname,'issueDetails')]");
    public By cancelBtn = By.xpath("//*[contains(text(),'Cancel')]");
    public By submitBtn = By.xpath("//*[contains(@class,'submit-btn')]");
    public By selectReason = By.xpath("//*[contains(@class,'mat-option-text')]");
    public By noteText = By.xpath("//*[@class='extra-info ng-star-inserted']");
    public By successMsg = By.xpath("//*[contains(text(),'Plan Changed Successfully')]");
    public By modalSuccessFailureMsg = By.xpath("//*[contains(@class,'main-container__body--message')]");
}
