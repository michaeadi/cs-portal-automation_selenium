package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CustomerInteractionScreenPage {

    public By searchBox = By.xpath("//input[@data-csautomation-key='msisdnSearchBox']");
    public By suggestions=By.xpath("//div[contains(text(),'Suggestions')]");
    public By msisdnRegex = By.xpath("//input[@data-csautomation-key='msisdnSearchBox']");
    public By nubanIdRegex=By.xpath("//li[contains(text(),'NUBAN ID')]");
    public By customerIdRegex = By.xpath("//li[contains(text(),'Customer ID')]");
    public By customerDashboardSearchBox = By.xpath("//input[@data-csautomation-key='dashBoardSearchBox']");
}
