package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class CustomerInteractionsSearchPOM extends BasePage {
    By numberSearch = By.xpath("//input[@name='search']");
    By searchButton = By.xpath("//button[@class='search-icon-btn']");

    public CustomerInteractionsSearchPOM(WebDriver driver) {
        super(driver);
    }

    public void enterNumber(String number) {
        UtilsMethods.printInfoLog("Entering Number in search box :" + number);
        writeText(numberSearch, number);
    }

    public void clearCustomerNumber() {
        UtilsMethods.printInfoLog("Clearing Customer Number");
        driver.findElement(numberSearch).clear();
    }

    public CustomerProfilePage clickOnSearch() {
        UtilsMethods.printInfoLog("Clicking on Search Button");
        waitTillLoaderGetsRemoved();
        click(searchButton);
        commonLib.hardWait();
        return new CustomerProfilePage(driver);
    }

    public void waitUntilPageIsLoaded() {
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Waiting till the Search Page is Loaded");
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    }
}
