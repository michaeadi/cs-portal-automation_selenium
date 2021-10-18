package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MsisdnSearchPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;

@Log4j2
public class MsisdnSearch extends BasePage {

    MsisdnSearchPage pageElements;

    public MsisdnSearch(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MsisdnSearchPage.class);
    }

    /**
     * This method is use to enter number in dashboard search box
     *
     * @param number The Number
     */
    public void enterNumberOnDashboardSearch(String number) {
        if (isVisible(pageElements.numberDashboardSearchBox)) {
            enterText(pageElements.numberDashboardSearchBox, number);
        } else {
            commonLib.error("Search box is NOT visible");
        }
    }

    /**
     * This method is use to enter number in search field
     *
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
     * This method is used to click search button
     */
    public void clickOnSearch() {
        commonLib.info("Clicking on Search Button");
        clickWithoutLoader(pageElements.searchButton);
        final boolean isGrowlVisible = pages.getGrowl().checkIsDashboardGrowlVisible();
        if (!isGrowlVisible) {
            if (isElementVisible(pageElements.errorMessage)) {
                highLighterMethod(pageElements.errorMessage);
                commonLib.error(driver.findElement(pageElements.errorMessage).getText());
            }
        } else {
            commonLib.fail("Growl Message:- " + pages.getGrowl().getDashboardToastMessage(), true);
            throw new SkipException("Growl Message Displayed");
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


    /**
     * This method is use to clear searchbox
     */
    public void clearCustomerNumber() {
        commonLib.info("Clearing search box :");
        driver.findElement(pageElements.numberSearch).clear();
    }

}
