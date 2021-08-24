package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ProfileManagementPage {
    public By totalProfileHeading = By.xpath("//span[text()='Total Profiles']");
    public By rows = By.xpath("//tr[@class='agent-list-container__agent-list--list-row ng-star-inserted']");
    public By pageloadcheck = By.xpath("//div[contains(text(),'Widget Name')]");
    public By roleName = By.xpath("td[1]/span");
    public By viewEditButton = By.xpath("td[5]/span/div/div/img");
    public By profileConfigurationStatus = By.xpath("td[2]/span");
    public By widgetName = By.xpath("//span[@class='order-no ng-star-inserted'and contains(text(),'1')]/ancestor::div[@class='widgetDataTable']/div[1]");
    public By widgetsRows = By.xpath("//div[@class='data-list ng-star-inserted']");
    public String widgetUnCheckbox = "//div[@class='mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin']";
    public By submitButton = By.xpath("//button[contains(text(),'Submit')]");
    public By configurationCol = By.xpath("//tr//td[2]");
    public By roleStatusCol = By.xpath("//tr//td[3]");
    public By disabledWidget = By.xpath("//div[@class='data-list ng-star-inserted']//div[@class='drag-disabled']");
    public String widgetOrder="//span[@class='order-no ng-star-inserted'and contains(text(),'";
    public String widgetOrderName="')]/ancestor::div[@class='widgetDataTable']/div[1]";
    public String moveUp= "')]/ancestor::div[@class='widgetDataTable']/div[3]//span[2]";
    public String moveDown= "')]/ancestor::div[@class='widgetDataTable']/div[3]//span[2]";
    public String selectValue="//div[@class='mat-select-value']";
    public String columnNumber="//th//span";
    public String optionText="//span[@class='mat-option-text' and text()=' ";
    public String checkbox = "//input";
    public By activePackWidgetOnPM = By.xpath("//div[@class='mat-tab-labels']/div/div");
    public By activePackTab = By.xpath("//div[@class='mat-tab-labels']/div/div[text()=' ACTIVE PACKS ']");
    public By activePackChkBoxRows = By.xpath("//div[@class='widgetDataTable']/div[1]");
    public String checkBoxInActivePack = "/div[1]/mat-checkbox";
    public By accountInfoTab = By.xpath("//div[@class='mat-tab-labels']/div/div[text()=' Account Info ']");
    public By planDetailCheckBox = By.xpath("//div[@class='widgetDataTable']//div[text()=' PLAN DETAILS ']/mat-checkbox/label/div");
    public By packDetailCheckBox = By.xpath("//div[@class='widgetDataTable']//div[text()=' ADD-ON BUNDLE USAGE ']/mat-checkbox/label/div");
    public By activePackWidgetName = By.xpath("//div[@class='widgetDataTable']/div[1]");
    public By accountInfoDetailChkBox = By.xpath("//div[@class='widgetDataTable']//div[text()=' POSTPAID ACCOUNT INFO DETAIL ']/mat-checkbox/label/div");
    public By linkedMsisdnChkBox = By.xpath("//div[@class='widgetDataTable']//div[text()=' POSTPAID LINKED MSISDN ']/mat-checkbox/label/div");
    public By accountInfoUp = By.xpath("//div[@class='widgetDataTable']//div[text()=' POSTPAID ACCOUNT INFO DETAIL ']/ancestor::div[@class='widgetDataTable']/div[3]//span[2]");
    public By accountInfoDown = By.xpath("//div[@class='widgetDataTable']//div[text()=' POSTPAID ACCOUNT INFO DETAIL ']/ancestor::div[@class='widgetDataTable']/div[3]//span[3]");
    public By linkedMsisdnUp = By.xpath("//div[@class='widgetDataTable']//div[text()=' POSTPAID LINKED MSISDN ']/ancestor::div[@class='widgetDataTable']/div[3]//span[2]");
    public By linkedMsisdnDown = By.xpath("//div[@class='widgetDataTable']//div[text()=' POSTPAID LINKED MSISDN ']/ancestor::div[@class='widgetDataTable']/div[3]//span[3]");
}