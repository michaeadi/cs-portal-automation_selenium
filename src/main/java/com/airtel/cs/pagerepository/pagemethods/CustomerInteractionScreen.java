package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.CustomerInteractionScreenPage;
import com.airtel.cs.pagerepository.pageelements.SmartCashTransactionHistoryPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CustomerInteractionScreen extends BasePage {
    CustomerInteractionScreenPage pageElements;

    public CustomerInteractionScreen(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CustomerInteractionScreenPage.class);
    }

    /**
     * This method use to check Search box text
     *
     * @return true/false
     */
    public String getSearchBoxText() {
        String text = getAttribute(pageElements.searchBox,"placeholder",false);
        commonLib.info("Getting Searchbox Text : " + text);
        return text;
    }

    /**
     * This method is used to check Suggestions is visible or not
     * @return
     */
    public boolean isSuggestionsVisible() {
        boolean status = isElementVisible(pageElements.suggestions);
        commonLib.info("Is Suggestions visible : " + status);
        return status;
    }

    /**
     * This method is used to check Msisdn Regex visible or not
     * @return
     */
    public boolean isMsisdnRegexVisible() {
        boolean status = isElementVisible(pageElements.msisdnRegex);
        commonLib.info("Is Msisdn Regex visible : " + status);
        return status;
    }

    /**
     * This method is used to check Nuban Id Regex visible or not
     * @return
     */
    public boolean isNubanIdRegexVisible() {
        boolean status = isElementVisible(pageElements.nubanIdRegex);
        commonLib.info("Is Nuban Id Regex visible : " + status);
        return status;
    }

    /**
     * This method is used to check Customer Id Regex visible or not
     * @return
     */
    public boolean isCustomerIdRegexVisible() {
        boolean status = isElementVisible(pageElements.customerIdRegex);
        commonLib.info("Is Customer Id Regex visible : " + status);
        return status;
    }
}
