package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.MsisdnSearchPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class MsisdnSearchPage extends BasePage {

    MsisdnSearchPageElements pageElements;

    public MsisdnSearchPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MsisdnSearchPageElements.class);
    }

    public void enterNumber(String number) {
        UtilsMethods.printInfoLog("Entering Number in search box :" + number);
        writeText(pageElements.numberSearch, number);
    }

    public void clearCustomerNumber() {
        UtilsMethods.printInfoLog("Clearing Customer Number");
        driver.findElement(pageElements.numberSearch).clear();
    }

    public void clickOnSearch() {
        UtilsMethods.printInfoLog("Clicking on Search Button");
        waitTillLoaderGetsRemoved();
        click(pageElements.searchButton);
    }

    public void waitUntilPageIsLoaded() {
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Waiting till the Search Page is Loaded");
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.searchButton));
    }
}
