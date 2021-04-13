package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.DetailedUsageHistoryPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DetailedUsageHistoryPage extends BasePage {

    DetailedUsageHistoryPageElements pageElements;

    public DetailedUsageHistoryPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailedUsageHistoryPageElements.class);
    }

    public Boolean isWidgetDisplay() {
        UtilsMethods.printInfoLog("Checking Detailed Usage history widget display: " + checkState(pageElements.title));
        return checkState(pageElements.title);
    }

    public Boolean isFreeCDR() {
        UtilsMethods.printInfoLog("Is Free CDR Checkbox displayed: " + checkState(pageElements.freeCDR));
        return checkState(pageElements.freeCDR);
    }

    public Boolean isTypeOfCDR() {
        UtilsMethods.printInfoLog("Is Type of CDR Option displayed: " + checkState(pageElements.typeOfCDR));
        return checkState(pageElements.typeOfCDR);
    }

    public Boolean isTodayDateFilter() {
        UtilsMethods.printInfoLog("Is Today Date Filter Option displayed: " + checkState(pageElements.todayDateFilter));
        return checkState(pageElements.todayDateFilter);
    }

    public Boolean isLast2DayDateFilter() {
        UtilsMethods.printInfoLog("Is last 2 day Date Filter Option displayed: " + checkState(pageElements.last2DayDateFilter));
        return checkState(pageElements.last2DayDateFilter);
    }

    public Boolean isLast7DayDateFilter() {
        UtilsMethods.printInfoLog("Is last 7 day Date Filter Option displayed: " + checkState(pageElements.last7DayDateFilter));
        return checkState(pageElements.last7DayDateFilter);
    }

    public Boolean isDatePickerDisplay() {
        UtilsMethods.printInfoLog("Is Date picker displayed: " + checkState(pageElements.datePicker));
        return checkState(pageElements.datePicker);
    }

    public String getHeaders(int column) {
        By header = By.xpath(pageElements.headerRow + column + pageElements.columnText);
        UtilsMethods.printInfoLog("Reading Header at POS(" + column + "): " + getText(header));
        return getText(header);
    }
    public String getValueCorrespondingToHeader(int row, int column) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.columnValue + column + pageElements.columnText);
        UtilsMethods.printInfoLog("Reading value for Header name '" + getHeaders(column) + "' is: " + getText(value));
        return getText(value);
    }
    public Boolean checkSignDisplay(int row) {
        By value = By.xpath(pageElements.columnRow + row + pageElements.checkSign);
        UtilsMethods.printInfoLog("Checking Negative Sign Display at ROW(" + row + ")");
        return checkState(value);
    }
    public Boolean isPagination() {
        Boolean status = checkState(pageElements.pagination);
        UtilsMethods.printInfoLog("Is pagination displayed: " + status);
        return status;
    }

    public boolean getNoResultFound() {
        UtilsMethods.printInfoLog("Checking No Result Found Message Display");
        return checkState(pageElements.noResultIcon);
    }
}
