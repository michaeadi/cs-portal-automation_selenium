package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.CRBTWidgetPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CRBTWidgetPage extends BasePage {

    CRBTWidgetPageElements pageElements;

    public CRBTWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CRBTWidgetPageElements.class);
    }

    public boolean isCRBTWidgetDisplay() {
        UtilsMethods.printInfoLog("Is CRBT Widget Display: " + checkState(pageElements.titleCRBT));
        return checkState(pageElements.titleCRBT);
    }

    public void clickTicketIcon() {
        UtilsMethods.printInfoLog("Clicking Ticket Icon");
        click(pageElements.ticketIcon);
    }

    public boolean isMyTuneTabDisplay() {
        UtilsMethods.printInfoLog("Is My Tune Tab Display: " + checkState(pageElements.myTuneTab));
        return checkState(pageElements.myTuneTab);
    }

    public boolean isTop20TuneTabDisplay() {
        UtilsMethods.printInfoLog("Is Top 20 Tune Tab Display: " + checkState(pageElements.top20TuneTab));
        return checkState(pageElements.top20TuneTab);
    }

    public boolean isSearchTuneTabDisplay() {
        UtilsMethods.printInfoLog("Is Search Tune Tab Display: " + checkState(pageElements.searchTuneTab));
        return checkState(pageElements.searchTuneTab);
    }

    public boolean isTabSelected(int i) {
        By tab = By.xpath("//div[@id='RING_BACK_TUNE']//div[@role='tab'][" + i + "]");
        return driver.findElement(tab).getAttribute("aria-selected").equalsIgnoreCase("true");
    }

    public boolean isNoResultImg() {
        UtilsMethods.printInfoLog("Is No Result Found Image Displayed: " + checkState(pageElements.noResultImg));
        return checkState(pageElements.noResultImg);
    }

    public boolean isNoResultMessage() {
        UtilsMethods.printInfoLog("Is No Result Found Message Displayed: " + checkState(pageElements.noResultMessage));
        return checkState(pageElements.noResultMessage);
    }

    public boolean isWidgetError() {
        UtilsMethods.printInfoLog("Is no Widget Error Display: " + checkState(pageElements.widgetError));
        return checkState(pageElements.widgetError);
    }

    public void searchKeyword(String text) {
        UtilsMethods.printInfoLog("Writing Keyword in search box: " + text);
        writeText(pageElements.searchBox, text);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking Search Button");
        click(pageElements.searchBtn);
    }

    public void clickSearchOption() {
        UtilsMethods.printInfoLog("Clicking Search Option");
        click(pageElements.searchOptionBtn);
    }

    public String getOption1() {
        UtilsMethods.printInfoLog("Reading option1: " + readText(pageElements.option1));
        return readText(pageElements.option1);
    }

    public String getOption2() {
        UtilsMethods.printInfoLog("Reading option2: " + readText(pageElements.option2));
        return readText(pageElements.option2);
    }

    public String getTop20Header(int i) {
        By text = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + i + "]/span");
        UtilsMethods.printInfoLog("Reading Header Name at POS(" + i + "): " + readText(text));
        return readText(text).trim();
    }

    public String getValueTop20(int row, int column) {
        By value = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[" + row + "]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading value for '" + getTop20Header(column) + "': " + readText(value));
        return readText(value).trim();
    }

    public String getSearchHeader(int i) {
        By text = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + i + "]/span");
        UtilsMethods.printInfoLog("Reading Header Name at POS(" + i + "): " + readText(text));
        return readText(text).trim();
    }

    public String getValueSearch(int row, int column) {
        By value = By.xpath("//div[@id='RING_BACK_TUNE']//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[" + row + "]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading value for '" + getSearchHeader(column) + "': " + readText(value));
        return readText(value).trim();
    }

    public void clickTop20Tab() {
        UtilsMethods.printInfoLog("Clicking on Top 20 Tab.");
        click(pageElements.top20TuneTab);
    }

    public void clickSearchTuneTab() {
        UtilsMethods.printInfoLog("Clicking on Search Tunes.");
        click(pageElements.searchTuneTab);
    }

    public void clickMyTunesTab() {
        UtilsMethods.printInfoLog("Clicking on My Tunes.");
        click(pageElements.myTuneTab);
    }

}
