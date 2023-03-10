package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class WalletInformationPage {

    public By walletStatus = By.xpath("//span[contains(text(),'Wallet Status')]//following-sibling::span//span");
    public By walletCategory = By.xpath("//span[contains(text(),'Wallet Category')]//following-sibling::span//span");
    public By walletCreatedOn = By.xpath("//span[contains(text(),'Wallet Created on')]//following-sibling::span//span");
    public By walletModifiedOn = By.xpath("//span[contains(text(),'Wallet Modified On')]//following-sibling::span//span");
    public By walletModifiedBy = By.xpath("//td[contains(text(),'Wallet Modified By')]//following-sibling::td//span");
    public By walletCreatedBy = By.xpath("//td[contains(text(),'Wallet Created By')]//following-sibling::td//span");
    public By walletNubanId = By.xpath("//span[contains(text(),'Wallet Nuban Id')]//following-sibling::span//span");
    public By onboardingChannel = By.xpath("//span[contains(text(),'Onboarding Channel')]//following-sibling::span//span");
    public By securityQuestionsSet = By.xpath("//span[contains(text(),'Questions Set')]//following-sibling::span//span");
    public By securityQuestionsConfigured = By.xpath("//span[contains(text(),'Questions Configured')]//following-sibling::span//span");
    public By barringStatus = By.xpath("//span[contains(text(),'Barring Status')]//following-sibling::span//span");
    public By frozenAmount=By.xpath("//td[contains(text(),'Frozen Amount')]//following-sibling::td");
    public By ficAmount=By.xpath("//td[contains(text(),'FIC Amount')]//following-sibling::td");
    public By balanceHoverIcon=By.xpath("//div[contains(@class,'data-smart')] //img[contains(@src,'message.svg')]");
    public By walletCreatedInfoIcon=By.xpath("//span[contains(text(),'Wallet Created')]//following-sibling::span//img[contains(@src,'message.svg')]");
    public By walletModifiedInfoIcon=By.xpath("//span[contains(text(),'Wallet Modified On')]//following-sibling::span//img[contains(@src,'message.svg')]");
    public By barringHoverIcon = By.xpath("//span[contains(text(),'Barring Status')]//following-sibling::span//a");

    public By walletInformationWidget = By.xpath("//div[contains(@data-csautomation-key,'WALLET_INFORMATION')]");
    public  By walletWidgetHeader= By.xpath("//span[contains(text(),'Wallet Information')]");
    public By balanceInfoIcon=By.xpath("//li[contains(text(),'Balance')]//following-sibling::li//a");
    public By balance=By.xpath("//div[contains(@class,'data-smartcash-header')]//li[contains(@class,'text-bold')]");
    public By detailedIcon=By.xpath("//img[contains(@data-csautomation-key,'menuButton')]");
    public By barringInfoIcon=By.xpath("//div[contains(@data-csautomation-key,'WALLET_INFORMATION')]//img[contains(@src,'info-blue-dark.svg')]");
    public By barTypeHeader = By.xpath("//*[contains(text(),'Bar Type')]");
    public By barReasonHeader = By.xpath("//*[contains(text(),'Bar Reason')]");
    public By barredByHeader = By.xpath("//*[contains(text(),'Barred By')]");
    public By barredOnHeader = By.xpath("//*[contains(text(),'Barred On')]");
    public By remarksHeader = By.xpath("//*[contains(text(),'Remarks')]");

    public By barType = By.xpath("//*[contains(text(),'Bar Type')]//following-sibling::td//span");
    public By barReason = By.xpath("//*[contains(text(),'Bar Reason')]//following-sibling::td//span");
    public By barredBy = By.xpath("//*[contains(text(),'Barred By')]//following-sibling::td//span");
    public By barredOn = By.xpath("//*[contains(text(),'Barred On')]//following-sibling::td//span");
    public By remarks = By.xpath("//*[contains(text(),'Remarks')]//following-sibling::td//span");
    /*
    Auuid  Locators
    */
    public By middleAuuid = By.xpath("//div[contains(@data-csautomation-key,'WALLET_INFORMATION')]");
    public By footerAuuid = By.xpath("//div[contains(@data-csautomation-key,'WALLET_INFORMATION')]//div[contains(@class,'auuid-container')]");
}
