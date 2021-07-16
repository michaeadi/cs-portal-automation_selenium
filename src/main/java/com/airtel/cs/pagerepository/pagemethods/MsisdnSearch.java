package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MsisdnSearchPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class MsisdnSearch extends BasePage {

    MsisdnSearchPage pageElements;

    public MsisdnSearch(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MsisdnSearchPage.class);
    }

    /**
     * This method is use to enter number in search field
     * @param number The Number
     */
    public void enterNumber(String number) {
        if (isVisible(pageElements.numberSearch)) {
            enterText(pageElements.numberSearch, number);
        } else {
            commonLib.error("Search box is NOT visible");
        }
    }

    /**
     * This method is use to clear customer number
     */
    public void clearCustomerNumber() {
        commonLib.info("Clearing Customer Number");
        driver.findElement(pageElements.numberSearch).clear();
    }

    /**
     * This method is use to click search button
     */
    public void clickOnSearch() {
        commonLib.info("Clicking on Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchButton);
        if(isVisible(pageElements.errorMessage)){
            highLighterMethod(pageElements.errorMessage);
            commonLib.error(driver.findElement(pageElements.errorMessage).getText());
        }
    }

    /**
     * This method is use to wait until customer dashboard page load
     */
    public void waitUntilPageIsLoaded() {
        waitTillLoaderGetsRemoved();
        commonLib.info("Waiting till the Search Page is Loaded");
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.searchButton));
    }

    /*
    This Method will tell us customer(msisdn) search page loaded or not
     */
    public Boolean isCustomerSearchPageVisible() {
        return isVisible(pageElements.numberSearch);
    }
}
