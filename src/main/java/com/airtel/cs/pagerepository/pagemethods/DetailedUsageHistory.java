package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailedUsageHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DetailedUsageHistory extends BasePage {

    DetailedUsageHistoryPage pageElements;

    public DetailedUsageHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailedUsageHistoryPage.class);
    }

    /**
     * This method is use to check detailed usage history widget display or not
     * @return true/false
     */
    public Boolean isWidgetDisplay() {
        final boolean state = isEnabled(pageElements.title);
        commonLib.info("Checking Detailed Usage history widget display: " + state);
        return state;
    }

    /**
     * This method is use to check free cdr checkbox displayed or not
     * @return true/false
     */
    public Boolean isFreeCDR() {
        final boolean state = isEnabled(pageElements.freeCDR);
        commonLib.info("Is Free CDR Checkbox displayed: " + state);
        return state;
    }

    /**
     * This method is use to check type of cdr option displayed or not
     * @return true/false
     */
    public Boolean isTypeOfCDR() {
        final boolean state = isEnabled(pageElements.typeOfCDR);
        commonLib.info("Is Type of CDR Option displayed: " + state);
        return state;
    }

    /**
     * This method is use to check today filter option displayed or not
     * @return true/false
     */
    public Boolean isTodayDateFilter() {
        final boolean state = isEnabled(pageElements.todayDateFilter);
        commonLib.info("Is Today Date Filter Option displayed: " + state);
        return state;
    }

    /**
     * This method is use to check last 2 day filter option displayed or not
     * @return true/false
     */
    public Boolean isLast2DayDateFilter() {
        final boolean state = isEnabled(pageElements.last2DayDateFilter);
        commonLib.info("Is last 2 day Date Filter Option displayed: " + state);
        return state;
    }

    /**
     * This method is use to check last 7 filter option displayed or not
     * @return true/false
     */
    public Boolean isLast7DayDateFilter() {
        final boolean state = isEnabled(pageElements.last7DayDateFilter);
        commonLib.info("Is last 7 day Date Filter Option displayed: " + state);
        return state;
    }

    /**
     * This method is use to check data picker option displayed or not
     * @return true/false
     */
    public Boolean isDatePickerDisplay() {
        final boolean state = isEnabled(pageElements.datePicker);
        commonLib.info("Is Date picker displayed: " + state);
        return state;
    }

    /**
     * This method use to get header name of detailed usage history Widget
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        By header = By.xpath(pageElements.headerRow + column + pageElements.columnText);
        final String text = getText(header);
        commonLib.info("Reading Header at POS(" + column + "): " + text);
        return text;
    }

    /**
     * This method use to get data value from detailed usage history widget based on row and column
     * @param row The row number
     * @param column The column number
     * @return String The  data value
     * */
    public String getValueCorrespondingToHeader(int row, int column) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.columnValue + column + pageElements.columnText);
        final String text = getText(value);
        commonLib.info("Reading value for Header name '" + getHeaders(column) + "' is: " + text);
        return text;
    }

    /**
     * This method use to check negative sign display or not
     * @param row The row number
     * @return true/false
     */
    public Boolean checkSignDisplay(int row) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.checkSign);
        commonLib.info("Checking Negative Sign Display at ROW(" + row + ")");
        return isEnabled(value);
    }

    /**
     * This method use to check pagination display or not
     * @return true/false
     */
    public Boolean isPagination() {
        Boolean status = isEnabled(pageElements.pagination);
        commonLib.info("Is pagination displayed: " + status);
        return status;
    }

    /**
     * This method use to check no result found display or not
     * @return true/false
     */
    public boolean getNoResultFound() {
        commonLib.info("Checking No Result Found Message Display");
        return isEnabled(pageElements.noResultIcon);
    }

    /**
     * This method is use get Widget unique identifier
     * @return The Unique identifier
     */
    public String getWidgetIdentifier(){
        return pageElements.widgetIdentifier;
    }

    /**
     * This method is use to check Usage history error visible when api not able to fetch data
     * @return true/false
     */
    public boolean isUnableToFetch() {
        final boolean elementVisible = isElementVisible(pageElements.unableToFetch);
        commonLib.info("Is error visible on unable to fetch detailed user history : " + elementVisible);
        return elementVisible;
    }


}
