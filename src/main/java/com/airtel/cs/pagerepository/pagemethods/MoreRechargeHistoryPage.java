package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.MoreRechargeHistoryPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class MoreRechargeHistoryPage extends BasePage {

    List<WebElement> rows;
    MoreRechargeHistoryPageElements pageElements;

    public MoreRechargeHistoryPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MoreRechargeHistoryPageElements.class);
    }

    public Boolean isWidgetDisplay() {
        UtilsMethods.printInfoLog("Checking More Recharge History Widget Display");
        return checkState(pageElements.widgetName);
    }

    public Boolean isPagination() {
        UtilsMethods.printInfoLog("Is Pagination Available: " + checkState(pageElements.pagination));
        return checkState(pageElements.pagination);
    }

    public Boolean isDatePickerDisplay() {
        UtilsMethods.printInfoLog("Checking More Recharge History Widget Date Picker Display");
        return checkState(pageElements.widgetName);
    }


    public String getHeaders(int row) {
        By headers = By.xpath("//div[@id='INTERNET_BUNDLES_HISTORY']/div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]/span");
        String header = readText(headers);
        UtilsMethods.printInfoLog("Reading header Name: " + header);
        return header;
    }

    public String getSubHeaders(int row) {
        By headers = By.xpath("//div[@id='INTERNET_BUNDLES_HISTORY']/div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]/span[2]");
        String header = readText(headers);
        UtilsMethods.printInfoLog("Reading Sub header Name: " + header);
        return header;
    }

    public Boolean getNoResultFound() {
        UtilsMethods.printInfoLog("Is no result found message display: " + checkState(pageElements.errorMessage));
        return checkState(pageElements.errorMessage);
    }

    public String getValueCorrespondingToRechargeHeader(int row, int column) {
        By value = By.xpath("//div[@id='INTERNET_BUNDLES_HISTORY']/div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + row + "]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading '" + getHeaders(column) + "' = " + readText(value));
        return readText(value);
    }

    public boolean isDatePickerVisible() {
        UtilsMethods.printInfoLog("Is DatePicker available: " + checkState(pageElements.datePicker));
        return checkState(pageElements.datePicker);
    }

    public boolean isLast2DateVisible() {
        UtilsMethods.printInfoLog("Is Last 2 Day Date available: " + checkState(pageElements.last2DaysFilter));
        return checkState(pageElements.last2DaysFilter);
    }

    public boolean isLast7DateVisible() {
        UtilsMethods.printInfoLog("Is Last 7 Day Date available: " + checkState(pageElements.last7DaysFilter));
        return checkState(pageElements.last7DaysFilter);
    }

    public boolean isTodayDateVisible() {
        UtilsMethods.printInfoLog("Is Today Day Date available: " + checkState(pageElements.todayDateFilter));
        return checkState(pageElements.todayDateFilter);
    }

    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.listOfRecharge);
        return rows.size();
    }

}
