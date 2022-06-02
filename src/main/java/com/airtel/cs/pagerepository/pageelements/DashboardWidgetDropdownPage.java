package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class DashboardWidgetDropdownPage {
    public By widgetDropdown = By.xpath("//*[contains(@class,'info-container')]");
    public By currentMsisdnValue = By.xpath("//*[contains(@class,'info-container')]//*[contains(text(),'Current')]");
    public By dropdownArrow = By.xpath("//*[contains(@class,'info-container')]//mat-icon");
    public By msisdn = By.xpath("//li[contains(text(),'MSISDN')]");
    public By msisdnValue = By.xpath("//li[contains(text(),'MSISDN')]//following-sibling::li");
    public By accountLinked = By.xpath("//*[contains(@class,'account-section')]//span[@class='data-text']");
    public By walletLinked = By.xpath("//*[contains(@class,'wallet-section')]//span[@class='data-text']");
    public By currentlySelectedMsisdnHeader = By.xpath("//div[contains(@class,'overlay-content tab-group-box box-styling')]//div[contains(@class,'mat-tab-label-active')]//li[contains(text(),'MSISDN')]");
    public By nubanId = By.xpath("//span[contains(text(),'Nuban Id')]//following-sibling::span//span");
    public By currentlySelectedMsisdnValue = By.xpath("//div[contains(@class,'overlay-content tab-group-box box-styling')]//div[contains(@class,'mat-tab-label-active')]//li[contains(text(),'MSISDN')]//following-sibling::li");
    public String accountRow = ("//div[contains(@class,'account-section')]//tr");
    public String walletRow = ("//div[contains(@class,'wallet-section')]//tr");
    public String nubanIdColumn = ("]//td[1]//span");
    public String selectRow = ("]//img");
    public By hoverMessage = By.xpath("//div[contains(@class,'mat-tooltip-panel')]//div");
}
