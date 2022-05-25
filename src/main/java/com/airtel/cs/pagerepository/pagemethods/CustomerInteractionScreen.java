package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.CustomerInteractionScreenPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CustomerInteractionScreen extends BasePage {
    CustomerInteractionScreenPage pageElements;

    public CustomerInteractionScreen(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CustomerInteractionScreenPage.class);
    }

    /**
     * This method use to get Search box Watermark
     *
     * @return true/false
     */
    public String getSearchBoxWatermark() {
        String text = getAttribute(pageElements.searchBox,"placeholder",false);
        commonLib.info("Getting Search box Watermark : " + text);
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

    /**
     * This method use to get text of Customer Dashboard Search box
     */
    public String getCustomerDashboardSearchBoxText() {
        String text = getText(pageElements.customerDashboardSearchBox);
        commonLib.info("Getting Search box Text : " + text);
        return text;
    }

    /**
     * This method is used to search Msisdn/Nuban Id/Customer Id
     */
    public void searchMsisdn(String searchId){
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(searchId);
        pages.getMsisdnSearchPage().clickOnSearch();
    }
}
