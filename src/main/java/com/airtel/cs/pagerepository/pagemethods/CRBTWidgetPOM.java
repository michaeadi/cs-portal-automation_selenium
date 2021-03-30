package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CRBTWidgetPOM extends BasePage {

    By titleCRBT = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]");
    By ticketIcon = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//child::span/img");
    By myTuneTab = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][1]/div");
    By top20TuneTab = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][2]/div");
    By searchTuneTab = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][3]/div");
    By noResultImg = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//img[@class=\"ng-star-inserted\"]");
    By noResultMessage = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//span/span");
    By widgetError = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//app-data-loading-error//div[@class='image-container']//img");
    By searchBox = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//input");
    By searchBtn = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//input//following-sibling::img");
    By searchOptionBtn = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"search-area\"]//span");
    By option1 = By.xpath("//div[@class=\"mat-menu-content\"]/button[1]/span");
    By option2 = By.xpath("//div[@class=\"mat-menu-content\"]/button[2]/span");
    /*
     *Top 20 Element Locator
     * By header=By.xpath("//span[contains(text(),"RING BACK TUNE ") and @class="card__card-header--label"]//ancestor::div[@class="card widget ng-star-inserted"]//div[@class="mat-tab-body-wrapper"]//div[@class="card__card-header--card-body--table--list-heading ng-star-inserted"]/div[1]/span");
     * */


    public CRBTWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isCRBTWidgetDisplay() {
        UtilsMethods.printInfoLog("Is CRBT Widget Display: " + checkState(titleCRBT));
        return checkState(titleCRBT);
    }

    public void clickTicketIcon() {
        UtilsMethods.printInfoLog("Clicking Ticket Icon");
        click(ticketIcon);
    }

    public boolean isMyTuneTabDisplay() {
        UtilsMethods.printInfoLog("Is My Tune Tab Display: " + checkState(myTuneTab));
        return checkState(myTuneTab);
    }

    public boolean isTop20TuneTabDisplay() {
        UtilsMethods.printInfoLog("Is Top 20 Tune Tab Display: " + checkState(top20TuneTab));
        return checkState(top20TuneTab);
    }

    public boolean isSearchTuneTabDisplay() {
        UtilsMethods.printInfoLog("Is Search Tune Tab Display: " + checkState(searchTuneTab));
        return checkState(searchTuneTab);
    }

    public boolean isTabSelected(int i) {
        By tab = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@role='tab'][" + i + "]");
        return driver.findElement(tab).getAttribute("aria-selected") == "true" ? true : false;
    }

    public boolean isNoResultImg() {
        UtilsMethods.printInfoLog("Is No Result Found Image Displayed: " + checkState(noResultImg));
        return checkState(noResultImg);
    }

    public boolean isNoResultMessage() {
        UtilsMethods.printInfoLog("Is No Result Found Message Displayed: " + checkState(noResultMessage));
        return checkState(noResultMessage);
    }

    public boolean isWidgetError() {
        UtilsMethods.printInfoLog("Is no Widget Error Display: " + checkState(widgetError));
        return checkState(widgetError);
    }

    public void searchKeyword(String text) {
        UtilsMethods.printInfoLog("Writing Keyword in search box: " + text);
        writeText(searchBox, text);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking Search Button");
        click(searchBtn);
    }

    public void clickSearchOption() {
        UtilsMethods.printInfoLog("Clicking Search Option");
        click(searchOptionBtn);
    }

    public String getOption1() {
        UtilsMethods.printInfoLog("Reading option1: " + readText(option1));
        return readText(option1);
    }

    public String getOption2() {
        UtilsMethods.printInfoLog("Reading option2: " + readText(option2));
        return readText(option2);
    }

    public String getTop20Header(int i) {
        By text = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + i + "]/span");
        UtilsMethods.printInfoLog("Reading Header Name at POS(" + i + "): " + readText(text));
        return readText(text).trim();
    }

    public String getValueTop20(int row, int column) {
        By value = By.xpath("//span[contains(text(),\"RING BACK TUNE\") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[" + row + "]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading value for '" + getTop20Header(column) + "': " + readText(value));
        return readText(value).trim();
    }

    public String getSearchHeader(int i) {
        By text = By.xpath("//span[contains(text(),\"RING BACK TUNE \") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + i + "]/span");
        UtilsMethods.printInfoLog("Reading Header Name at POS(" + i + "): " + readText(text));
        return readText(text).trim();
    }

    public String getValueSearch(int row, int column) {
        By value = By.xpath("//span[contains(text(),\"RING BACK TUNE\") and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"mat-tab-body-wrapper\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[" + row + "]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading value for '" + getSearchHeader(column) + "': " + readText(value));
        return readText(value).trim();
    }

    public void clickTop20Tab() {
        UtilsMethods.printInfoLog("Clicking on Top 20 Tab.");
        click(top20TuneTab);
    }

    public void clickSearchTuneTab() {
        UtilsMethods.printInfoLog("Clicking on Search Tunes.");
        click(searchTuneTab);
    }

    public void clickMyTunesTab() {
        UtilsMethods.printInfoLog("Clicking on My Tunes.");
        click(myTuneTab);
    }

}
