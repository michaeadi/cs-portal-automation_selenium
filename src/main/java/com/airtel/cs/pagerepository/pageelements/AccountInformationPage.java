package com.airtel.cs.pagerepository.pageelements;
import org.openqa.selenium.By;

public class AccountInformationPage {

    public By accountStatus = By.xpath("//span[contains(text(),'Account Status')]//following-sibling::span//span");
    public By accountCategory = By.xpath("//span[contains(text(),'Account Category')]//following-sibling::span//span");
    public By accountCreatedOn = By.xpath("//span[contains(text(),'Account Created on')]//following-sibling::span//span");
    public By accountCreatedBy = By.xpath("//span[contains(text(),'Account Created By')]//following-sibling::span//span");
    public By accountModifiedOn = By.xpath("//span[contains(text(),'Account Modified On')]//following-sibling::span//span");
    public By accountModifiedBy = By.xpath("//span[contains(text(),'Account Modified By')]//following-sibling::span//span");
    public By accountNubanId = By.xpath("//span[contains(text(),'Account Nuban Id')]//following-sibling::span//span");
    public By onboardingChannel = By.xpath("//span[contains(text(),'Onboarding Channel')]//following-sibling::span//span");
    public By securityQuestionsSet = By.xpath("//span[contains(text(),'Questions Set')]//following-sibling::span//span");
    public By securityQuestionsConfigured = By.xpath("//span[contains(text(),'Questions Configured')]//following-sibling::span//span");
    public By barringStatus = By.xpath("//span[contains(text(),'Barring Status')]//following-sibling::span//span");
    public By frozenAmount=By.xpath("//td[contains(text(),'Frozen Amount')]//following-sibling::td");

    public By accountInformationWidget = By.xpath("//div[contains(@data-csautomation-key,'ACCOUNT_INFORMATION')]");
    public  By accountWidgetHeader= By.xpath("//span[contains(text(),'Account Information')]");
    public By balanceInfoIcon=By.xpath("//li[contains(text(),'Balance')]//following-sibling::li//a");
    public By balance=By.xpath("//div[contains(@class,'data-smartcash-header')]//li[contains(@class,'text-bold')]");
    public By detailedIcon=By.xpath("//img[contains(@data-csautomation-key,'menuButton')]");
    public By barringInfoIcon=By.xpath("//div[contains(@data-csautomation-key,'ACCOUNT_INFORMATION')]//img[contains(@src,'info-blue-dark.svg')]");

    /*
    Auuid  Locators
    */
    public By middleAuuid = By.xpath("//div[contains(@data-csautomation-key,'ACCOUNT_INFORMATION')]");
    public By footerAuuid = By.xpath("//div[contains(@data-csautomation-key,'ACCOUNT_INFORMATION')]//div[contains(@class,'auuid-container')]");


}
