package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DemoGraphicPage {
    /*
     * Customer Name & DOB & Refresh Icon Element Locator
     * */
    public By customerNumberSearchBox = By.xpath("//input[@data-csautomation-key='dashBoardSearchBox']");
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
    public By simNumber = By.xpath("//span[contains(text(),'SIM Number')]//following-sibling::span");
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
    public By airtelStatus = By.xpath(
            "//div[@class='customer-details']//div[@class='container-fluid cusomer-profile-detail']/div//div[6]//span[contains(text(),'Tap to unlock')]");
    /*
     * PUK1 & PUK2
     * */
    public By puk1 = By.xpath("//span[contains(text(),'PUK1')]//following-sibling::span");
    public By puk2 = By.xpath("//span[contains(text(),'PUK2')]//following-sibling::span");

    /*
     * Data Manager on & off
     * */
    public By dataManagerStatus = By.xpath(
            "//*[text()='Data Manager']/following-sibling::span//input");
    public By dataManagerText = By.xpath("//span[contains(text(),'Data')]//following-sibling::span");
    /*
     * SIM Status & Airtel Money Status & Service Status
     * */
    public By gsmStatus = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/span");
    public By SIMStatusReason = By.xpath("//span[contains(text(),'GSM Status')]//following-sibling::span/a");
    public By accountStatus = By.xpath("//span[contains(text(),'Account Status')]//following-sibling::span");
    public By serviceStatus = By.xpath("//span[contains(text(),'Service Status')]//following-sibling::span");
    public By walletBalance = By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span");
    public By walletBalance2 = By.xpath("//span[contains(text(),'Wallet Balance')]//following-sibling::span/span/div[2]");
    public By registrationStatus = By.xpath("//span[contains(text(),'Registration Status')]//following-sibling::span");

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
    public By hoverInfoSegment = By.xpath("//span[contains(text(),'Segment')]//following-sibling::span//img");
    public By serviceClass = By.xpath("//span[contains(text(),'Service Class')]//following-sibling::span");
    public By serviceCategory = By.xpath("//*[contains(text(),'Service Category')]//following-sibling::td");
    public By subSegment = By.xpath("//span[contains(text(),'Sub Segment')]//following-sibling::span");
    public By appStatus = By.xpath("//span[contains(text(),'App Status')]//following-sibling::span");
    public By gsmKycStatus = By.xpath("//span[contains(text(),'GSM KYC Status')]//following-sibling::span");

    public By vipFlag = By.id("vip_customer");
    public By customerBirthday = By.id("cust_birthday");
    public By anniversary = By.id("airtel_anniversary");
    public By errorMessage = By.xpath("//p[contains(text(),'Entered customer number is Invalid')]");
    public By clearSearchBox = By.xpath("//div[@class='customer-details']//div[@class='user-left-side']/div/div[1]//span[contains(text(),'X')]");

    /*
    PIN1 and PIN2 available after hovering over SIM Number under demographic widget
     */
    public By pin1 = By.xpath("//*[contains(text(),'PIN1')]//following-sibling::td/span");
    public By pin2 = By.xpath("//*[contains(text(),'PIN2')]//following-sibling::td/span");

    /*
    Auuid shown in the demographic widget
    DGW - Demo Graphic Widget
     */
    public By middleAuuidDGW = By.xpath("//*[@id='GSM_PROFILE']");
    public By footerAuuidDGW = By.xpath("//*[@id='GSM_PROFILE']//*[@class='auuid-container']");

    /*
    Auudi shown in Airtel Money Profile
    AMP - Airtel Money Profile
     */
    public By middleAuuidAMP = By.xpath("//*[@id='AM_PROFILE']");
    public By footerAuuidAMP = By.xpath("//*[@id='AM_PROFILE']//*[contains(@class,'auuid-container')]");

    public By resetPinIcon = By.xpath("//img[@class='header-action-icon cursor-pointer']/ancestor::span[contains(@class,'disabled')]");

    public By dashboardBody = By.xpath("//body//app-dashboard");
}
