package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MoreRechargeHistoryPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class MoreRechargeHistory extends BasePage {

    List<WebElement> rows;
    MoreRechargeHistoryPage pageElements;

    public MoreRechargeHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MoreRechargeHistoryPage.class);
    }

    /**
     * This method is use to check widget display or not
     * @return true/false
     */
    public Boolean isWidgetDisplay() {
        commonLib.info("Checking More Recharge History Widget Display");
        return isEnabled(pageElements.widgetName);
    }

    /**
     * This method is use to check pagination display or not
     * @return true/false
     */
    public Boolean isPagination() {
        final boolean state = isEnabled(pageElements.pagination);
        commonLib.info("Is Pagination Available: " + state);
        return state;
    }

    /**
     * This method is use to check date picker display or not
     * @return true/false
     */
    public Boolean isDatePickerDisplay() {
        commonLib.info("Checking More Recharge History Widget Date Picker Display");
        return isEnabled(pageElements.widgetName);
    }


    /**
     * This method use to get header name based on column number
     * @param column The column number
     * @return String The Value
     */
    public String getHeaders(int column) {
        By headers = By.xpath( pageElements.headerName+ column + pageElements.value);
        String header = getText(headers);
        commonLib.info("Reading header Name: " + header);
        return header;
    }

    /**
     * This method use to get sub header name based on column number
     * @param column The column number
     * @return String The Value
     */
    public String getSubHeaders(int column) {
        By headers = By.xpath(pageElements.headerName + column + pageElements.subHeaderValue);
        String header = getText(headers);
        commonLib.info("Reading Sub header Name: " + header);
        return header;
    }

    /**
     * This method use to check no result found message display or not
     * @return true/false
     */
    public Boolean getNoResultFound() {
        final boolean state = isEnabled(pageElements.errorMessage);
        commonLib.info("Is no result found message display: " + state);
        return state;
    }

    /**
     * This method use to get data value based on row and column number
     * @param row The Row number
     * @param column The column number
     * @return String The Value
     */
    public String getValueCorrespondingToRechargeHeader(int row, int column) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.columnName + column + pageElements.value);
        final String text = getText(value);
        commonLib.info("Reading '" + getHeaders(column) + "' = " + text);
        return text;
    }

    /**
     * This method use to check date picker display or not
     * @return true/false
     */
    public boolean isDatePickerVisible() {
        final boolean state = isEnabled(pageElements.datePicker);
        commonLib.info("Is DatePicker available: " + state);
        return state;
    }

    /**
     * This method use to check filter by last 2 day display or not
     * @return true/false
     */
    public boolean isLast2DateVisible() {
        final boolean state = isEnabled(pageElements.last2DaysFilter);
        commonLib.info("Is Last 2 Day Date available: " + state);
        return state;
    }

    /**
     * This method use to check filter by last 7 day display or not
     * @return true/false
     */
    public boolean isLast7DateVisible() {
        final boolean state = isEnabled(pageElements.last7DaysFilter);
        commonLib.info("Is Last 7 Day Date available: " + state);
        return state;
    }

    /**
     * This method use to check filter by today date display or not
     * @return true/false
     */
    public boolean isTodayDateVisible() {
        final boolean state = isEnabled(pageElements.todayDateFilter);
        commonLib.info("Is Today Day Date available: " + state);
        return state;
    }

    /**
     * This method use to get total number of rows
     * @return Integer The size
     */
    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.listOfRecharge);
        return rows.size();
    }

}
