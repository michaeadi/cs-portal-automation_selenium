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

    public Boolean isWidgetDisplay() {
        final boolean state = isEnabled(pageElements.title);
        commonLib.info("Checking Detailed Usage history widget display: " + state);
        return state;
    }

    public Boolean isFreeCDR() {
        final boolean state = isEnabled(pageElements.freeCDR);
        commonLib.info("Is Free CDR Checkbox displayed: " + state);
        return state;
    }

    public Boolean isTypeOfCDR() {
        final boolean state = isEnabled(pageElements.typeOfCDR);
        commonLib.info("Is Type of CDR Option displayed: " + state);
        return state;
    }

    public Boolean isTodayDateFilter() {
        final boolean state = isEnabled(pageElements.todayDateFilter);
        commonLib.info("Is Today Date Filter Option displayed: " + state);
        return state;
    }

    public Boolean isLast2DayDateFilter() {
        final boolean state = isEnabled(pageElements.last2DayDateFilter);
        commonLib.info("Is last 2 day Date Filter Option displayed: " + state);
        return state;
    }

    public Boolean isLast7DayDateFilter() {
        final boolean state = isEnabled(pageElements.last7DayDateFilter);
        commonLib.info("Is last 7 day Date Filter Option displayed: " + state);
        return state;
    }

    public Boolean isDatePickerDisplay() {
        final boolean state = isEnabled(pageElements.datePicker);
        commonLib.info("Is Date picker displayed: " + state);
        return state;
    }

    public String getHeaders(int column) {
        By header = By.xpath(pageElements.headerRow + column + pageElements.columnText);
        final String text = getText(header);
        commonLib.info("Reading Header at POS(" + column + "): " + text);
        return text;
    }
    public String getValueCorrespondingToHeader(int row, int column) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.columnValue + column + pageElements.columnText);
        final String text = getText(value);
        commonLib.info("Reading value for Header name '" + getHeaders(column) + "' is: " + text);
        return text;
    }
    public Boolean checkSignDisplay(int row) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.checkSign);
        commonLib.info("Checking Negative Sign Display at ROW(" + row + ")");
        return isEnabled(value);
    }
    public Boolean isPagination() {
        Boolean status = isEnabled(pageElements.pagination);
        commonLib.info("Is pagination displayed: " + status);
        return status;
    }

    public boolean getNoResultFound() {
        commonLib.info("Checking No Result Found Message Display");
        return isEnabled(pageElements.noResultIcon);
    }
}
