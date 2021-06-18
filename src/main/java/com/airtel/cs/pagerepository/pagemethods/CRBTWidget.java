package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.CRBTWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CRBTWidget extends BasePage {

    CRBTWidgetPage pageElements;

    public CRBTWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CRBTWidgetPage.class);
    }

    public boolean isCRBTWidgetDisplay() {
        final boolean state = isEnabled(pageElements.titleCRBT);
        commonLib.info("Is CRBT Widget Display: " + state);
        return state;
    }

    /*
   This Method will let us know is CRBT Widget Loaded Successfully or not
    */
    public boolean isCRBTHistoryWidgetLoaded() {
        boolean result = false;
        if (isElementVisible(pageElements.widgetLoader)) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.widgetLoader));
            result = true;
        }
        return result;
    }

    public void clickTicketIcon() {
        commonLib.info("Clicking Ticket Icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
    }

    public boolean isMyTuneTabDisplay() {
        final boolean state = isEnabled(pageElements.myTuneTab);
        commonLib.info("Is My Tune Tab Display: " + state);
        return state;
    }

    public boolean isTop20TuneTabDisplay() {
        final boolean state = isEnabled(pageElements.top20TuneTab);
        commonLib.info("Is Top 20 Tune Tab Display: " + state);
        return state;
    }

    public boolean isSearchTuneTabDisplay() {
        final boolean state = isEnabled(pageElements.searchTuneTab);
        commonLib.info("Is Search Tune Tab Display: " + state);
        return state;
    }

    public boolean isTabSelected(int i) {
        By tab = By.xpath("//div[@id='RING_BACK_TUNE']//div[@role='tab'][" + i + "]");
        return driver.findElement(tab).getAttribute("aria-selected").equalsIgnoreCase("true");
    }

    public boolean isNoResultImg() {
        final boolean state = isEnabled(pageElements.noResultImg);
        commonLib.info("Is No Result Found Image Displayed: " + state);
        return state;
    }

    public boolean isNoResultMessage() {
        final boolean state = isEnabled(pageElements.noResultMessage);
        commonLib.info("Is No Result Found Message Displayed: " + state);
        return state;
    }

    /*
    This Method is used to get No Result Message Displayed on UI
     */
    public String noResultMessage() {
        String result = null;
        result = getText(pageElements.noResultMessage);
        return result;
    }

    public boolean isWidgetError() {
        final boolean state = isEnabled(pageElements.widgetError);
        commonLib.info("Is no Widget Error Display: " + state);
        return state;
    }

    public void searchKeyword(String text) {
        commonLib.info("Writing Keyword in search box: " + text);
        enterText(pageElements.searchBox, text);
    }

    public void clickSearchBtn() {
        commonLib.info("Clicking Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtn);
    }

    public void clickSearchByOption() {
        commonLib.info("Clicking Search Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchOptionBtn);
    }

    public String getOption1() {
        final String text = getText(pageElements.option1);
        commonLib.info("Reading option1: " + text);
        return text;
    }

    public String getOption2() {
        final String text = getText(pageElements.option2);
        commonLib.info("Reading option2: " + text);
        return text;
    }

    public String getTop20Header(int i) {
        By text = By.xpath(pageElements.crbtTableHeader + i + pageElements.crbtTableHeader1);
        final String text1 = getText(text);
        commonLib.info("Reading Header Name at POS(" + i + "): " + text1);
        return text1.trim();
    }

    public String getValueTop20(int row, int column) {
        By value = By.xpath(pageElements.crbtTableValue + row + pageElements.crbtTableValue1 + column + pageElements.crbtTableHeader1);
        final String text = getText(value);
        commonLib.info("Reading value for '" + getTop20Header(column) + "': " + text);
        return text.trim();
    }

    public String getSearchHeader(int i) {
        By text = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class='mat-tab-body-wrapper']//div[@class='card__card-header--card-body--table']//div[@class='card__card-header--card-body--table--list-heading ng-star-inserted']//div[" + i + "]/span");
        final String text1 = getText(text);
        commonLib.info("Reading Header Name at POS(" + i + "): " + text1);
        return text1.trim();
    }

    public String getValueSearch(int row, int column) {
        By value = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class='mat-tab-body-wrapper']//div[@class='table-data-wrapper ng-star-inserted']//div[" + row + "]//div[@class='card__card-header--card-body--table--data-list row-border']//div[" + column + "]/span");
        final String text = getText(value);
        commonLib.info("Reading value for '" + getSearchHeader(column) + "': " + text);
        return text.trim();
    }

    public void clickTop20Tab() {
        commonLib.info("Clicking on Top 20 Tab.");
        clickAndWaitForLoaderToBeRemoved(pageElements.top20TuneTab);
    }

    public void clickSearchTuneTab() {
        commonLib.info("Clicking on Search Tunes.");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchTuneTab);
    }

    public void clickMyTunesTab() {
        commonLib.info("Clicking on My Tunes.");
        clickAndWaitForLoaderToBeRemoved(pageElements.myTuneTab);
    }

    /*
      This Method will give us footer auuid shown in CRBT widget
      CRBT = Caller Ring Back Tune Widget
       */
    public String getFooterAuuidCRBT() {
        String result = null;
        result = getText(pageElements.footerCRBTAuuid);
        return result;
    }

    /*
    This Method will give us auuid shown in the middle of the CRBT widget
    CRBT = Caller Ring Back Tune Widget
     */
    public String getMiddleAuuidCRBT() {
        String result = null;
        result = getAttribute(pageElements.middleCRBTAuuid, "data-auuid", false);
        return result;
    }

    public String getCRBTWidgetId(){
        return pageElements.crbtWidgetIdentifier;
    }

}
