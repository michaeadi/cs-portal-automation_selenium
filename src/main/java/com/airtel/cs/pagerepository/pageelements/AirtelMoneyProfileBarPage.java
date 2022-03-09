package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AirtelMoneyProfileBarPage {

    public By barredReason=By.xpath("//span[contains(text(),'Barred Reason')]");
    public By tapToUnlock=By.xpath("//div[contains(text(),'Tap to unlock')]");
    public By authenticate;
    public By barredOn=By.xpath("//span[contains(text(),'Barred On')]");

    public By barIcon;
    public By unBarIcon;
    public By barredReasonText;
    public By barredOnText;
    public By selectReasonLabel=By.xpath("//label[contains(text(),'Select Reason')]");
    public By selectBarTypeLabel=By.xpath("//label[contains(text(),'BAR Type')]");
    public By submitButton=By.xpath("//span[contains(text(),'Submit')]");
    public By commentBox=By.xpath("//div[contains(@class, 'issue-comment')]//following-sibling::mat-form-field");
    public By selectReasonFromDropdown=By.xpath("//span[contains(text(),'Lost sim card')]");
    public By selectBarTypeFromDropdown=By.xpath("//span[contains(text(),'Sender')]");
    public By commentBoxLabel=By.xpath("//span[contains(text(),'Enter Comment')]");
}
