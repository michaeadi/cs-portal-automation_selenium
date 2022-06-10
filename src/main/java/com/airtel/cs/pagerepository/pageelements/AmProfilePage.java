package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AmProfilePage {

    public By barredReasonLabel =By.xpath("//span[contains(text(),'Barred Reason')]");
    public By barredOnLabel =By.xpath("//span[contains(text(),'Barred On')]");
    public By serviceStatusLabel =By.xpath("//span[contains(text(),'Service Status')]");
    public By remarksLabel =By.xpath("//td[contains(text(),'Remarks')]");
    public By barredByLabel =By.xpath("///td[contains(text(),'Barred By')]");

    public By tapToUnlock=By.xpath("//div[contains(text(),'Tap to unlock')]");
    public By barIcon=By.xpath("//img[contains(@src,'airtelmoneybar.svg')]");
    public By unBarIcon=By.xpath("//img[contains(@src,'airtelmoneyunbar.svg')]");

    public By barredReason =By.xpath("//span[contains(text(),'Barred Reason')]//following-sibling::span//span");
    public By barredOn =By.xpath("//span[contains(text(),'Barred On')]//following-sibling::span//span");
    public By selectReasonLabel=By.xpath("//label[contains(text(),'Select Reason')]");
    public By selectBarTypeLabel=By.xpath("//label[contains(text(),'BAR Type')]");
    public By submitButton=By.xpath("//span[contains(text(),'Submit')]");
    public By commentBox=By.xpath("//div[contains(@class, 'issue-comment')]//following-sibling::mat-form-field");
    public By selectReasonFromDropdown=By.xpath("//span[contains(text(),'Lost sim card')]");
    public By selectBarTypeFromDropdown=By.xpath("//span[contains(text(),'Sender')]");
    public By commentBoxLabel=By.xpath("//span[contains(text(),'Enter Comment')]");
    public By remarks =By.xpath("//td[contains(text(),'Remarks')]/../td//span");
    public By barredBy =By.xpath("//td[contains(text(),'Barred By')]/../td//span");
    public By serviceStatus =By.xpath("//span[contains(text(),'Service Status')]/../span//span");
    public By walletType =By.xpath("//span[contains(text(),'Wallet')]//following-sibling::span//span");
    public By grade =By.xpath("//span[contains(text(),'Grade')]//following-sibling::span//span");
    public By messageIcon=By.xpath("//span[contains(text(),'Service Status')]//following-sibling::span//img");
}
