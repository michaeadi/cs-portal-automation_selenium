package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class OscRechargePage {
    public By tabTitle = By.xpath("//span[contains(text(),'Voucher Details')]");
    public By serialLabel = By.xpath("//div[@class='serialno']//span[1]");
    public By serialNo = By.xpath("//div[@class='serialno']//span[2]");
    public By statusLabel = By.xpath("//div[@class='pop-up-block']//div[2]//span[1]");
    public By statusValue = By.xpath("//div[@class='pop-up-block']//div[2]//span[2]");
    public By subStatusLabel = By.xpath("//div[@class='pop-up-block']//div[3]//span[1]");
    public By subStatusValue = By.xpath("//div[@class='pop-up-block']//div[3]//span[2]");
    public By rechargeAmtLabel = By.xpath("//div[@class='pop-up-block']//div[4]//span[1]");
    public By rechargeAmtValue = By.xpath("//div[@class='pop-up-block']//div[4]//span[2]");
    public By timeStampLabel = By.xpath("//div[@class='pop-up-block']//div[5]//span[1]");
    public By timeStampValue = By.xpath("//div[@class='pop-up-block']//div[5]//span[2]");
    public By subscriberIdLabel = By.xpath("//div[@class='pop-up-block']//div[6]//span[1]");
    public By subscriberIdValue = By.xpath("//div[@class='pop-up-block']//div[6]//span[2]");
    public By expiryDateLabel = By.xpath("//div[@class='pop-up-block']//div[7]//span[1]");
    public By expiryDateValue = By.xpath("//div[@class='pop-up-block']//div[7]//span[2]");
    public By agentLabel = By.xpath("//div[@class='agent']//div[1]//span[1]");
    public By agentValue = By.xpath("//div[@class='agent']//div[1]//span[2]");
    public By batchIdLabel = By.xpath("//div[@class='agent']//div[2]//span[1]");
    public By batchIdValue = By.xpath("//div[@class='agent']//div[2]//span[2]");
    public By voucherGroupLabel = By.xpath("//div[@class='agent']//div[3]//span[1]");
    public By voucherGroupValue = By.xpath("//div[@class='agent']//div[3]//span[2]");
    public By doneBtn = By.xpath("//div[@class='action-btn-area']//button[contains(text(),'Done')]");
    public By overscratchButton = By.xpath("//button[contains(text(),'Overscratch')]");
    public By msisdnCopyIcon = By.xpath("//img[contains(@class,'copy-msisdn')]");
    public By enterPin = By.xpath("//div[contains(@class,'enter-pin')]");
    public By rechargeButton = By.xpath("//button[contains(text(),'Recharge')]");
    public By confirmationPopUp=By.xpath("//div[@class='main-container']");
    public By cancelBtn = By.xpath("//div[@class='action-btn-area']//button[contains(text(),'Cancel')]");




    /*
Action Trail tab
 */
    public By homePage=By.xpath("//span[contains(text(),'HOME')]");
    public By viewHistory=By.xpath("//span[contains(text(),'VIEW HISTORY')]");
    public By actionTrail=By.xpath("//div[contains(text(),'Action Trail')]");
    public By actionTrailLatestDropdown=By.xpath("//span[contains(@class,'cursor')]");
    public By rechargeAmount =By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p[1]");
    public By rechargedMsisdn =By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p[2]");
    public By voucherNumber=By.xpath("//div[@id='collapseTwo']/div/form/ul/li[2]/p[3]");
    public By actionType=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[1]");
    public By reason=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[3]");
    public By comment=By.xpath("//div[@class='agent-list-container']/table/tbody/tr/td[6]");
    public By successMessage=By.xpath("//div[contains(@class,'main-container__body--message')]");
    public By crossIcon=By.xpath("//mat-icon[contains(@class,'cursor-pointer')]");

}
