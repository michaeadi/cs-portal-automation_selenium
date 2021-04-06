package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DemoGraphicPageElements {
    /*
     * Customer Name & DOB & Refresh Icon Element Locator
     * */
    public By customerNumberSearchBox = By.xpath("//input[@type='search' and @placeholder='Search']");
    public By customerName = By.xpath("//span[@class='customer-name ng-star-inserted']/span[1]");
    public By customerDOB = By.xpath("//span[contains(text(),'DOB')]//following-sibling::span/span");
    public By customerInfoIcon = By.xpath("//span[@class='customer-name ng-star-inserted']/a");
    public By refreshIcon = By.xpath("//img[@class='refresh-button cursor-pointer']");

    /*
     * Activation Date & Time No longer required in newer version
     * */
    public By activationDate = By.xpath("//span[contains(text(),'Customer Activation Date')]//following-sibling::span/span");

    /*
     * SIM Number & Device Info
     * */
    public By simNumber = By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span/span");
    public By simNumberInfoIcon = By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span/a");
    public By simType = By.xpath("//span[contains(text(),'SIM Type')]//following-sibling::span/span");
    public By deviceCompatible = By.xpath("//span[contains(text(),'Device Type')]//following-sibling::span/span");

    /*
     * Device Info
     * */
    public By deviceInfoIcon = By.xpath("//span[contains(text(),'Device Type')]//following-sibling::span/a");
    public By IMEINumber = By.xpath("//span[contains(text(),'IMEI')]//following-sibling::span");
    public By type = By.xpath("//li//span[contains(text(),'Device Type')]//following-sibling::span");
    public By brand = By.xpath("//span[contains(text(),'Brand')]//following-sibling::span");
    public By model = By.xpath("//span[contains(text(),'Model')]//following-sibling::span");
    public By os = By.xpath("//span[contains(text(),'OS Type')]//following-sibling::span");

    /*
     * PUK Tap to unlock & Airtel Money Status Lock
     * */
    public By pukLock = By.xpath("//span[contains(text(),'PUK')]//parent::div//span[contains(text(),'Tap to unlock')]");
    public By airtelMoneyLock = By.xpath("//div[@id='AM_PROFILE']//div[contains(text(),'Tap to unlock')]");
    public By amProfileWidget=By.xpath("//div[@id='AM_PROFILE']");
    public By airtelStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"container-fluid cusomer-profile-detail\"]/div//div[6]//span[contains(text(),'Tap to unlock')]");
    /*
     * PUK1 & PUK2
     * */
    public By PUK1 = By.xpath("//p[@class=\"puk-show\"][1]/span[contains(text(),\"PUK1\")]//following-sibling::span");
    public By PUK2 = By.xpath("//p[@class=\"puk-show\"][1]/span[contains(text(),\"PUK2\")]//following-sibling::span");

    /*
     * Data Manager on & off
     * */
    public By dataManagerStatus = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"top col-md-7\"]/div/div[1]//div[@class=\"mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin\"]//input");
    /*
     * SIM Status & Airtel Money Status & Service Status
     * */
    public By SIMStatus = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/span");
    public By SIMStatusReason = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/a");
    public By airtelMoneyStatus = By.xpath("//span[contains(text(),'Account Status')]//following-sibling::span/span");
    public By serviceStatus = By.xpath("//span[contains(text(),'Service Status')]//following-sibling::span/span");
    public By walletBalance = By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span/span/div[1]");
    public By walletBalance2= By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span/span/div[2]");
    public By registrationStatus = By.xpath("//span[contains(text(),'Registration Status')]//following-sibling::span/span");

    /*
     * SIM Status Reason
     * */
    public By reasonCode = By.xpath("//span[contains(text(),'Reason Code')]//following-sibling::span");
    public By modifiedBy = By.xpath("//span[contains(text(),'Modified By')]//following-sibling::span");
    public By modifiedDate = By.xpath("//span[contains(text(),'Modified Date')]//following-sibling::span");
    /*
     * ID Type & ID Number
     * */
    public By idType = By.xpath("//span[contains(text(),'ID Type')]//following-sibling::span");
    public By idNumber = By.xpath("//span[contains(text(),'ID No.')]//following-sibling::span");

    public By lineType = By.xpath("//span[contains(text(),'Connection Type')]//following-sibling::span");
    public By segment = By.xpath("//span[contains(text(),'Segment')]//following-sibling::span");
    public By hoverInfoSegment = By.xpath("//span[contains(text(),'Segment')]//following-sibling::span/a");
    public By serviceClass = By.xpath("//span[contains(text(),'Service Class')]//following-sibling::span");
    public By serviceCategory = By.xpath("//span[contains(text(),'Service Category')]//following-sibling::span");
    public By subSegment = By.xpath("//span[contains(text(),'Sub Segment')]//following-sibling::span");
    public By appStatus = By.xpath("//span[contains(text(),'App Status')]//following-sibling::span");
    public By gsmKycStatus = By.xpath("//span[contains(text(),'GSM KYC Status')]//following-sibling::span");

    public By vipFlag = By.id("vip_customer");
    public By customerBirthday = By.id("cust_birthday");
    public By anniversary = By.id("airtel_anniversary");
    public By errorMessage = By.xpath("//p[contains(text(),'Entered customer number is Invalid')]");
    public By clearSearchBox = By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]//span[contains(text(),'X')]");
}
