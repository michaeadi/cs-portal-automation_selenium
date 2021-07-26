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

    /**
     * This method use to check crbt widget display or not
     * @return true/false
     */
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

    /**
     * This method use to click ticket icon which present on CRBT widget
     */
    public void clickTicketIcon() {
        commonLib.info("Clicking Ticket Icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
    }

    /**
     * This method is use to check my tune tab display or not
     * @return true/false
     */
    public boolean isMyTuneTabDisplay() {
        final boolean state = isEnabled(pageElements.myTuneTab);
        commonLib.info("Is My Tune Tab Display: " + state);
        return state;
    }

    /**
     * This method is use to check my top 20 tab display or not
     * @return true/false
     */
    public boolean isTop20TuneTabDisplay() {
        final boolean state = isEnabled(pageElements.top20TuneTab);
        commonLib.info("Is Top 20 Tune Tab Display: " + state);
        return state;
    }

    /**
     * This method is use to check search tune tab display or not
     * @return true/false
     */
    public boolean isSearchTuneTabDisplay() {
        final boolean state = isEnabled(pageElements.searchTuneTab);
        commonLib.info("Is Search Tune Tab Display: " + state);
        return state;
    }

    /**
     * This method is use to check tab selected or not
     * @return true/false
     */
    public boolean isTabSelected(int i) {
        By tab = By.xpath(pageElements.tabHeader + i + "]");
        return driver.findElement(tab).getAttribute("aria-selected").equalsIgnoreCase("true");
    }

    /**
     * This method is use to check no result icon displayed or not
     * @return true/false
     */
    public boolean isNoResultImg() {
        final boolean state = isEnabled(pageElements.noResultImg);
        commonLib.info("Is No Result Found Image Displayed: " + state);
        return state;
    }

    /**
     * This method is use to check no result message displayed or not
     * @return true/false
     */
    public boolean isNoResultMessage() {
        final boolean state = isEnabled(pageElements.noResultMessage);
        commonLib.info("Is No Result Found Message Displayed: " + state);
        return state;
    }

    /*
    This Method is used to get No Result Message Displayed on UI
     */
    public String noResultMessage() {
        String result;
        result = getText(pageElements.noResultMessage);
        return result;
    }

    /**
     * This method is use to check widget error icon displayed or not
     * @return true/false
     */
    public boolean isWidgetError() {
        final boolean state = isEnabled(pageElements.widgetError);
        commonLib.info("Is no Widget Error Display: " + state);
        return state;
    }

    /**
     * This method is use to write keyword into search box
     */
    public void searchKeyword(String text) {
        commonLib.info("Writing Keyword in search box: " + text);
        enterText(pageElements.searchBox, text);
    }

    /**
     * This method is use to click search button
     */
    public void clickSearchBtn() {
        commonLib.info("Clicking Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtn);
    }

    /**
     * This method is use to click search by option
     */
    public void clickSearchByOption() {
        commonLib.info("Clicking Search Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchOptionBtn);
    }

    /**
     * This method is use to get first option
     * @return String The String
     */
    public String getOption1() {
        final String text = getText(pageElements.option1);
        commonLib.info("Reading option1: " + text);
        return text;
    }

    /**
     * This method is use to get second option
     * @return String The value
     */
    public String getOption2() {
        final String text = getText(pageElements.option2);
        commonLib.info("Reading option2: " + text);
        return text;
    }


    /**
     * This method is use to get top 20 header based on row number
     * @return String The value
     */
    public String getTop20Header(int i) {
        By text = By.xpath(pageElements.crbtTableHeader + i + pageElements.crbtTableHeader1);
        final String text1 = getText(text);
        commonLib.info("Reading Header Name at POS(" + i + "): " + text1);
        return text1.trim();
    }

    /**
     * This method use to get top 20 ringtone value
     * @param row The row number
     * @param column The column number
     * @return String The value
     */
    public String getValueTop20(int row, int column) {
        By value = By.xpath(pageElements.crbtTableValue + row + pageElements.crbtTableValue1 + column + pageElements.crbtTableHeader1);
        final String text = getText(value);
        commonLib.info("Reading value for '" + getTop20Header(column) + "': " + text);
        return text.trim();
    }

    /**
     * This method use to get search header based on column number
     * @param i The Column number
     * @return String The value
     */
    public String getSearchHeader(int i) {
        By text = By.xpath(pageElements.searchHeader + i + pageElements.headerText);
        final String text1 = getText(text);
        commonLib.info("Reading Header Name at POS(" + i + "): " + text1);
        return text1.trim();
    }

    /**
     * This method use to get searched ringtone value based on row and column
     * @param row The row Number
     * @param column The Column number
     * @return String The value
     */
    public String getValueSearch(int row, int column) {
        By value = By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.crbtTableHeader1);
        final String text = getText(value);
        commonLib.info("Reading value for '" + getSearchHeader(column) + "': " + text);
        return text.trim();
    }

    /**
     * This method is use to click top 20 tab
     */
    public void clickTop20Tab() {
        commonLib.info("Clicking on Top 20 Tab.");
        clickAndWaitForLoaderToBeRemoved(pageElements.top20TuneTab);
    }

    /**
     * This method is use to click Search tune tab
     */
    public void clickSearchTuneTab() {
        commonLib.info("Clicking on Search Tunes.");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchTuneTab);
    }

    /**
     * This method is use to click my tune tab
     */
    public void clickMyTunesTab() {
        commonLib.info("Clicking on My Tunes.");
        clickAndWaitForLoaderToBeRemoved(pageElements.myTuneTab);
    }

    /*
      This Method will give us footer auuid shown in CRBT widget
      CRBT = Caller Ring Back Tune Widget
       */
    public String getFooterAuuidCRBT() {
        String result;
        result = getText(pageElements.footerCRBTAuuid);
        return result;
    }

    /*
    This Method will give us auuid shown in the middle of the CRBT widget
    CRBT = Caller Ring Back Tune Widget
     */
    public String getMiddleAuuidCRBT() {
        String result;
        result = getAttribute(pageElements.middleCRBTAuuid, "data-auuid", false);
        return result;
    }

}
