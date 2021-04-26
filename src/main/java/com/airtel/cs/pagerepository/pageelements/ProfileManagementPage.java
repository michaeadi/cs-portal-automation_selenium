package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class ProfileManagementPage {
    public By totalProfileHeading = By.xpath("//span[text()=\"Total Profiles\"]");
    public By rows = By.xpath("//tr[@class=\"agent-list-container__agent-list--list-row ng-star-inserted\"]");
    public By pageloadcheck = By.xpath("//div[contains(text(),\"Widget Name\")]");
    public By roleName = By.xpath("td[1]/span");
    public By viewEditButton = By.xpath("td[5]/span/div/div/img");
    public By profileConfigurationStatus = By.xpath("td[2]/span");
    public By widgetName = By.xpath("//span[@class=\"order-no ng-star-inserted\"and contains(text(),\"1\")]/ancestor::div[@class=\"widgetDataTable\"]/div[1]");
    public By widgetsRows = By.xpath("//div[@class=\"data-list ng-star-inserted\"]");
    public By widgetUnCheckbox = By.xpath("//div[@class='mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin']");
    public By submitButton = By.xpath("//button[contains(text(),\"Submit\")]");
    public By configurationCol = By.xpath("//tr//td[2]");
    public By roleStatusCol = By.xpath("//tr//td[3]");
    public By disabledWidget = By.xpath("//div[@class='data-list ng-star-inserted']//div[@class='drag-disabled']");
}
