package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CustomerProfilePage {

    public By searchNumber = By.xpath("//input[@data-csautomation-key='dashBoardSearchBox']");
    public By interactionIcon = By.xpath("//div[@class='sub-header__divide--control--tab']");
    public By actions = By.xpath("//span[@class='action-placeholder']");
    public By simBar = By.xpath("//button[@class='db-action-menu-item mat-menu-item ng-star-inserted']");
    public By pinTags = By.xpath("//div[@class='sub-header__divide--control--tab ng-star-inserted']");
    public By viewHistory = By.xpath("//*[contains(text(),'VIEW HISTORY')]");
    public By firstWidgetHeader = By.xpath("//div[@class='home-tab-container__left-widgets--widgets ng-star-inserted'][1]//child::span[@class='card__card-header--label']");
    public By thirdWidgetHeader = By.xpath("//div[@class='home-tab-container__left-widgets--widgets ng-star-inserted'][2]//child::span[@class='card__card-header--label']");
    public By secondWidgetHeader = By.xpath("//div[@class='home-tab-container__right-widgets--widgets ng-star-inserted'][1]//child::span[@class='card__card-header--label']");
    public By fourthWidgetHeader = By.xpath("//div[@class='home-tab-container__right-widgets--widgets ng-star-inserted'][2]//child::span[@class='card__card-header--label']");
    public By daDetailsTab = By.xpath("//div[contains(text(),'DA DETAILS')]");
    public By usageHistoryTab = By.xpath("//div[contains(text(),'USAGE HISTORY')]");
    public By rechargeHistoryTab = By.xpath("//div[contains(text(),'RECHARGE HISTORY')]");
    public By homeActionBtn = By.xpath("//div[@class='sub-header__divide--control--action']");
    public By loanWidget = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class='card widget ng-star-inserted']");
    public By sendSMSAction = By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Send SMS')]");
    public By simBarUnBar = By.xpath("//div[@class='mat-menu-content']//button[1]");
    public By sendSettings = By.xpath("//button[text()=' Send Internet Settings ']");
    public By resetME2UPassword = By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Reset Me2U Password')]");
    public By sendSettingTitle = By.xpath("//span[contains(text(),'Send Internet Settings')]");
    public By resetME2Title = By.xpath("//span[contains(text(),'Reset Me2U Password')]");
    public By cancelBtn = By.xpath("//button[@class='no-btn']");
    public By closeBtn = By.xpath("//following-sibling::mat-icon[contains(text(),'close')]");
    public By birthdayIcon = By.xpath("//span[@class='customer-icon-block']/img");
    public By continueBtn = By.xpath("//span[contains(text(),'continue')]");
    public By changeServiceClass_btn = By.xpath("//button[contains(text(),'Change Service Class')]");
    public By homePage = By.xpath("//*[contains(text(),'HOME')]");
    public By suspendSIM = By.xpath("//*[contains(text(),'Suspend SIM')]");
    public By reactivateSIM = By.xpath("//*[contains(text(),' Reactivate SIM ')]");
    public By authenticationModal = By.xpath("//*[text()=' Authentication ']");
    public By authCheckBox = By.xpath("//input[@type='checkbox']");
    public By authenticateBtn = By.xpath("//*[text()='Authenticate']/..");
    public By issueDetailsReason = By.xpath("//*[contains(@formarrayname,'issueDetails')]");
    public By selectReason = By.xpath("//*[contains(@class,'mat-option-text')]");
    public By commentBox = By.xpath("//*[contains(@formcontrolname,'comment')]");
    public By submitBtn = By.xpath("//*[contains(@class,'submit-btn')]");
    public By modalSuccessFailureMsg = By.xpath("//*[contains(@class,'main-container__body--message')]");
    public String pinTagText = "//div[@class='sub-header__divide--control']//div[@class='sub-header__divide--control--tab ng-star-inserted'][";
    public String pinTagByName = "//div[@class='sub-header__divide--control--tab ng-star-inserted' and contains(text(),'";
    public By demographichWidget = By.xpath("//*[@class='home-tab-container__short-widgets']");
    public By adjustmentAction=By.xpath("//button[contains(text(),'Adjustments')]");
    public By homeTabWidget = By.xpath("//*[contains(@class,'sr-navigation')]//*[@class='mat-tab-body-wrapper']");
    public By popupMessage = By.xpath("//div[@class='tagging-popup ng-star-inserted']/p");
    public By sendSettingConfirm = By.xpath("//div[contains(@class,'main-container__body')]/p[contains(text(),'Do you wish to send internet settings?')]");
    public By resetME2Confirm = By.xpath("//div[contains(@class,'main-container__body')]/p[contains(text(),'Do you wish to reset Me2U password?')]");
    public By suspendSimConfirm = By.xpath("//div[contains(@class,'main-container__body')]/p[contains(text(),'Do you wish to suspend the SIM?')]");
    public By reActivateSimConfirm = By.xpath("//div[contains(@class,'main-container__body')]/p[contains(text(),'Do you wish to reactivate the SIM?')]");
    public By crossIcon=By.xpath("//mat-icon[contains(text(),'close')]");
    public By airtelMoneyLock = By.xpath("//div[@id='AM_PROFILE']//div[contains(text(),'Tap to unlock')]");
    public By selectReasonFromDropdown = By.xpath("//span[contains(text(),'Suspecting Fraud')]");
}
