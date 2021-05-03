package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CustomerProfilePage {

    public By searchNumber = By.xpath("//input[@type='text' and @placeholder='Search']");
    public By interactionIcon = By.xpath("//div[@class='sub-header__divide--control--tab']");
    public By actions = By.xpath("//span[@class=\"action-placeholder\"]");
    public By simBar = By.xpath("//button[@class=\"db-action-menu-item mat-menu-item ng-star-inserted\"]");
    public By pinTags = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"]");
    public By viewHistory = By.xpath("//*[contains(text(),\"VIEW HISTORY\")]");
    public By firstWidgetHeader = By.xpath("//div[@class=\"home-tab-container__left-widgets--widgets ng-star-inserted\"][1]//child::span[@class=\"card__card-header--label\"]");
    public By thirdWidgetHeader = By.xpath("//div[@class=\"home-tab-container__left-widgets--widgets ng-star-inserted\"][2]//child::span[@class=\"card__card-header--label\"]");
    public By secondWidgetHeader = By.xpath("//div[@class=\"home-tab-container__right-widgets--widgets ng-star-inserted\"][1]//child::span[@class=\"card__card-header--label\"]");
    public By fourthWidgetHeader = By.xpath("//div[@class=\"home-tab-container__right-widgets--widgets ng-star-inserted\"][2]//child::span[@class=\"card__card-header--label\"]");
    public By daDetailsTab = By.xpath("//div[contains(text(),'DA DETAILS')]");
    public By usageHistoryTab = By.xpath("//div[contains(text(),'USAGE HISTORY')]");
    public By rechargeHistoryTab = By.xpath("//div[contains(text(),'RECHARGE HISTORY')]");
    public By homeActionBtn = By.xpath("//span[@class='action-placeholder']");
    public By loanWidget = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]");
    public By sendSMSAction = By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Send SMS')]");
    public By simBarUnBar = By.xpath("//div[@class=\"mat-menu-content\"]//button[1]");
    public By sendSettings = By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Send Internet Settings')]");
    public By resetME2UPassword = By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Reset Me2U Password')]");
    public By sendSettingTitle = By.xpath("//span[contains(text(),'Send Internet Settings')]");
    public By resetME2Title = By.xpath("//span[contains(text(),'Reset Me2U Password')]");
    public By noBtn = By.xpath("//button[@class=\"no-btn\"]");
    public By closeBtn = By.xpath("//span[contains(text(),'Send Internet Settings')]//following-sibling::mat-icon[contains(text(),'close')]");
    public By birthdayIcon = By.xpath("//span[@class='customer-icon-block']/img");
    public By continueBtn = By.xpath("//span[contains(text(),'continue')]");

    public By changeServiceClass_btn = By.xpath("//button[contains(text(),'Change Service Class')]");
    public By homePage = By.xpath("//*[contains(text(),'HOME')]");
    public By suspendSIM = By.xpath("//*[contains(text(),'Suspend SIM')]");
    public By reactivationSIM = By.xpath("//*[contains(text(),' Reactivate SIM ')]");
    public By authenticationModal = By.xpath("//*[text()=' Authentication ']");
    public By authCheckBox = By.xpath("//input[@type='checkbox']");
    public By authenticateBtn = By.xpath("//*[text()='Authenticate']/..");
    public By issueDetailsReason = By.xpath("//*[contains(@formarrayname,'issueDetails')]");
    public By selectReason = By.xpath("//*[contains(@class,'mat-option-text')]");
    public By commentBox = By.xpath("//*[contains(@formcontrolname,'comment')]");
    public By submitBtn = By.xpath("//*[contains(@class,'submit-btn')]");
    public By modalSuccessFailureMsg = By.xpath("//*[contains(@class,'main-container__body--message')]");
}
