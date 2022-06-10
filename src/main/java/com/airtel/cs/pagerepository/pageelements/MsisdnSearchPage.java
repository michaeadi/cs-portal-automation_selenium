package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class MsisdnSearchPage {
    public By numberSearch = By.xpath("//input[@data-csautomation-key='msisdnSearchBox']");
    public By searchButton = By.xpath("//button[@data-csautomation-key='searchButton']");
    public By errorMessage = By.xpath("//div[@class='error top-space ng-star-inserted']");
    public By numberDashboardSearchBox = By.xpath("//input[@data-csautomation-key='dashBoardSearchBox']");

    /*
    Locators for selecting Reason and Bartype from dropdown in Issue details pop up
     */
    public String headerType=("//*[contains(text(),'");
    public String selectReasonOrBarTypeFromDropdown =("')]/ancestor::div/following-sibling::div//mat-option[1]");
}
