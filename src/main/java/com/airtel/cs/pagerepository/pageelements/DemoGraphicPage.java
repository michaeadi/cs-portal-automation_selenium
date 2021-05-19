package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DemoGraphicPage {
    /*
     * Customer Name & DOB & Refresh Icon Element Locator
     * */
    public By customerNumberSearchBox = By.xpath("//input[(@type='text' or @type='search') and @placeholder='Search']");
    public By customerName = By.xpath("//span[@class='customer-name ng-star-inserted']/span[1]");
    public By customerDOB = By.xpath("//*[contains(text(),'DOB')]//following-sibling::td/span/span");
    public By customerInfoIcon = By.xpath("//span[@class='customer-name ng-star-inserted']/a");
    public By refreshIcon = By.xpath("//img[@class='refresh-button cursor-pointer']");

    /*
     * Activation Date & Time No longer required in newer version
     * */
    public By activationDate = By.xpath("//*[contains(text(),'Activation')]//following-sibling::td/span/span");

    /*
     * SIM Number & Device Info
     * */
    public By simNumber = By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span/span");
    public By simNumberInfoIcon = By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span/a");
    public By simType = By.xpath("//*[contains(@class,'simCompatibilityIcon')]");
    public By deviceCompatible = By.xpath("//*[contains(@class,'deviceCompatibilityIcon')]");

    /*
     * Device Info
     * */
    public By deviceInfoIcon = By.xpath("//span[contains(text(),'Device Type')]//following-sibling::span/a");
    public By IMEINumber = By.xpath("//*[contains(text(),'IMEI')]//following-sibling::td/span");
    public By type = By.xpath("//*[contains(text(),'Device Type')]//following-sibling::td/span");
    public By brand = By.xpath("//*[contains(text(),'Brand')]//following-sibling::td/span");
    public By model = By.xpath("//*[contains(text(),'Model')]//following-sibling::td/span");
    public By os = By.xpath("//*[contains(text(),'OS Type')]//following-sibling::td/span");

    /*
     * PUK Tap to unlock & Airtel Money Status Lock
     * */
    public By pukLock = By.xpath("//span[contains(text(),'PUK')]//parent::div//span[contains(text(),'Tap to unlock')]");
    public By airtelMoneyLock = By.xpath("//div[@id='AM_PROFILE']//div[contains(text(),'Tap to unlock')]");
    public By amProfileWidget = By.xpath("//div[@id='AM_PROFILE']");
    public By airtelStatus = By.xpath("//div[@class='customer-details']//div[@class='container-fluid cusomer-profile-detail']/div//div[6]//span[contains(text(),'Tap to unlock')]");
    /*
     * PUK1 & PUK2
     * */
    public By PUK1 = By.xpath("//span[contains(text(),'PUK1')]//following-sibling::span");
    public By PUK2 = By.xpath("//span[contains(text(),'PUK2')]//following-sibling::span");

    /*
     * Data Manager on & off
     * */
    public By dataManagerStatus = By.xpath("//div[@class='customer-details']//div[@class='top col-md-7']/div/div[1]//div[@class='mat-slide-toggle-bar mat-slide-toggle-bar-no-side-margin']//input");
    public By dataManagerText = By.xpath("//span[contains(text(),'Data')]//following-sibling::span");
    /*
     * SIM Status & Airtel Money Status & Service Status
     * */
    public By gsmStatus = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/span");
    public By SIMStatusReason = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/a");
    public By accountStatus = By.xpath("//span[contains(text(),'Account Status')]//following-sibling::span/span");
    public By serviceStatus = By.xpath("//span[contains(text(),'Service Status')]//following-sibling::span/span");
    public By walletBalance = By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span/span/div[1]");
    public By walletBalance2 = By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span/span/div[2]");
    public By registrationStatus = By.xpath("//span[contains(text(),'Registration Status')]//following-sibling::span/span");

    /*
     * SIM Status Reason
     * */
    public By reasonCode = By.xpath("//*[contains(text(),'Reason Code')]//following-sibling::td/span");
    public By modifiedBy = By.xpath("//*[contains(text(),'Modified By')]//following-sibling::td/span");
    public By modifiedDate = By.xpath("//*[contains(text(),'Modified Date')]//following-sibling::td/span/span");
    public By modifiedTime = By.xpath("//*[contains(text(),'Modified Date')]//following-sibling::td/span/span[2]");
    /*
     * ID Type & ID Number
     * */
    public By idType = By.xpath("//*[contains(text(),'ID Type')]//following-sibling::td/span");
    public By idNumber = By.xpath("//*[contains(text(),'ID No.')]//following-sibling::td/span");

    public By connectionType = By.xpath("//span[contains(text(),'Connection Type')]//following-sibling::span");
    public By segment = By.xpath("//span[contains(text(),'Segment')]//following-sibling::span/span");
    public By hoverInfoSegment = By.xpath("//span[contains(text(),'Segment')]//following-sibling::span/a");
    public By serviceClass = By.xpath("//span[contains(text(),'Service Class')]//following-sibling::span");
    public By serviceCategory = By.xpath("//*[contains(text(),'Service Category')]//following-sibling::td/span");
    public By subSegment = By.xpath("//span[contains(text(),'Sub Segment')]//following-sibling::span");
    public By appStatus = By.xpath("//span[contains(text(),'App Status')]//following-sibling::span");
    public By gsmKycStatus = By.xpath("//span[contains(text(),'GSM KYC Status')]//following-sibling::span");

    public By vipFlag = By.id("vip_customer");
    public By customerBirthday = By.id("cust_birthday");
    public By anniversary = By.id("airtel_anniversary");
    public By errorMessage = By.xpath("//p[contains(text(),'Entered customer number is Invalid')]");
    public By clearSearchBox = By.xpath("//div[@class='customer-details']//div[@class='user-left-side']/div/div[1]//span[contains(text(),'X')]");
}
