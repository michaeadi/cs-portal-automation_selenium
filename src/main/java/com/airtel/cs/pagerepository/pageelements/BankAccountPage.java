package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class BankAccountPage {
    /**
     * Header's value locators
     */
    public String dataRows = "//div[contains(@class,'table-data-wrapper')]//div[";
    public String dataColumns = "]//div[@data-csautomation-key='dataRows']//div[";
    public String dataValue = "]//span[@data-csautomation-key='columnValue']";
    public String actionValue="]//span";
    public By totalRows = By.xpath("//div[@class='card__content restricted ng-star-inserted']//div[@class='table-data-wrapper ng-star-inserted']//div[@class='ng-star-inserted']");

}
