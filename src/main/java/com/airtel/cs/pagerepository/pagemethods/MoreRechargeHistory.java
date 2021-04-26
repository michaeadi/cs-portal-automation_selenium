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

    public Boolean isWidgetDisplay() {
        commonLib.info("Checking More Recharge History Widget Display");
        return checkState(pageElements.widgetName);
    }

    public Boolean isPagination() {
        final boolean state = checkState(pageElements.pagination);
        commonLib.info("Is Pagination Available: " + state);
        return state;
    }

    public Boolean isDatePickerDisplay() {
        commonLib.info("Checking More Recharge History Widget Date Picker Display");
        return checkState(pageElements.widgetName);
    }


    public String getHeaders(int row) {
        By headers = By.xpath("//div[@id='INTERNET_BUNDLES_HISTORY']/div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]/span");
        String header = getText(headers);
        commonLib.info("Reading header Name: " + header);
        return header;
    }

    public String getSubHeaders(int row) {
        By headers = By.xpath("//div[@id='INTERNET_BUNDLES_HISTORY']/div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]/span[2]");
        String header = getText(headers);
        commonLib.info("Reading Sub header Name: " + header);
        return header;
    }

    public Boolean getNoResultFound() {
        final boolean state = checkState(pageElements.errorMessage);
        commonLib.info("Is no result found message display: " + state);
        return state;
    }

    public String getValueCorrespondingToRechargeHeader(int row, int column) {
        By value = By.xpath("//div[@id='INTERNET_BUNDLES_HISTORY']/div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + row + "]//div[" + column + "]/span");
        final String text = getText(value);
        commonLib.info("Reading '" + getHeaders(column) + "' = " + text);
        return text;
    }

    public boolean isDatePickerVisible() {
        final boolean state = checkState(pageElements.datePicker);
        commonLib.info("Is DatePicker available: " + state);
        return state;
    }

    public boolean isLast2DateVisible() {
        final boolean state = checkState(pageElements.last2DaysFilter);
        commonLib.info("Is Last 2 Day Date available: " + state);
        return state;
    }

    public boolean isLast7DateVisible() {
        final boolean state = checkState(pageElements.last7DaysFilter);
        commonLib.info("Is Last 7 Day Date available: " + state);
        return state;
    }

    public boolean isTodayDateVisible() {
        final boolean state = checkState(pageElements.todayDateFilter);
        commonLib.info("Is Today Day Date available: " + state);
        return state;
    }

    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.listOfRecharge);
        return rows.size();
    }

}
