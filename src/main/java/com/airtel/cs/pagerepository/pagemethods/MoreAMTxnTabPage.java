package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.MoreAMTxnTabPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class MoreAMTxnTabPage extends BasePage {

    MoreAMTxnTabPageElements pageElements;

    public MoreAMTxnTabPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MoreAMTxnTabPageElements.class);
    }

    public Boolean isNegSignDisplay(int row) {
        By negAmount = By.xpath(pageElements.valueRow + row + pageElements.negSymbol);
        return checkState(negAmount);
    }

    public Boolean isPosSignDisplay(int row) {
        By posAmount = By.xpath(pageElements.valueRow + row + pageElements.posSymbol);
        return checkState(posAmount);
    }

    public boolean isAMMenuHistoryTabDisplay() {
        boolean status = checkState(pageElements.title);
        UtilsMethods.printInfoLog("Is AM Menu History Tab Display: " + status);
        return status;
    }

    public boolean isTodayFilterTab() {
        boolean status = checkState(pageElements.todayFilter);
        UtilsMethods.printInfoLog("Is Today Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastTwoDayFilterTab() {
        boolean status = checkState(pageElements.lasTwoDays);
        UtilsMethods.printInfoLog("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastSevenDayFilterTab() {
        boolean status = checkState(pageElements.lastSevenDays);
        UtilsMethods.printInfoLog("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isDateRangeFilterTab() {
        boolean status = checkState(pageElements.dateRangeFilter);
        UtilsMethods.printInfoLog("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    public boolean isSearchTxnIdBox() {
        boolean status = checkState(pageElements.searchTxnId);
        UtilsMethods.printInfoLog("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    public void searchByTxnId(String txnId) {
        UtilsMethods.printInfoLog("Search By Transaction Id: " + txnId);
        writeText(pageElements.searchTxnId, txnId);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking Search button");
        click(pageElements.searchBtn);
    }


    public boolean isAirtelMoneyErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyError);
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.airtelMoneyNoResultFoundMessage));
        return readText(pageElements.airtelMoneyNoResultFoundMessage);
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFound);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath(pageElements.headerRow + "[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row, int column) {
        String value = readText(By.xpath(pageElements.valueRow + row + pageElements.valueColumns + column + pageElements.columnText));
        UtilsMethods.printInfoLog("Reading Value(" + row + "): " + value);
        return value;
    }

    public Boolean isResendSMS() {
        By check = By.xpath(pageElements.resendSMS);
        return checkState(check);
    }

    //Secondary Widget

    public Boolean isNegSignDisplayOnSecondWidget(int row) {
        By negAmount = By.xpath(pageElements.valueRowSec + row + pageElements.negSymbol);
        return checkState(negAmount);
    }

    public Boolean isPosSignDisplayOnSecondWidget(int row) {
        By posAmount = By.xpath(pageElements.valueRowSec + row + pageElements.posSymbol);
        return checkState(posAmount);
    }

    public boolean isAMMenuHistoryTabDisplayOnSecondWidget() {
        boolean status = checkState(pageElements.titleSec);
        UtilsMethods.printInfoLog("Is AM Menu History Tab Display: " + status);
        return status;
    }

    public boolean isTodayFilterTabOnSecondWidget() {
        boolean status = checkState(pageElements.todayFilterSec);
        UtilsMethods.printInfoLog("Is Today Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastTwoDayFilterTabOnSecondWidget() {
        boolean status = checkState(pageElements.lasTwoDaysSec);
        UtilsMethods.printInfoLog("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastSevenDayFilterTabOnSecondWidget() {
        boolean status = checkState(pageElements.lastSevenDaysSec);
        UtilsMethods.printInfoLog("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isDateRangeFilterTabOnSecondWidget() {
        boolean status = checkState(pageElements.dateRangeFilterSec);
        UtilsMethods.printInfoLog("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    public boolean isSearchTxnIdBoxOnSecondWidget() {
        boolean status = checkState(pageElements.searchTxnIdSec);
        UtilsMethods.printInfoLog("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    public void searchByTxnIdOnSecondWidget(String txnId) {
        UtilsMethods.printInfoLog("Search By Transaction Id: " + txnId);
        writeText(pageElements.searchTxnIdSec, txnId);
    }

    public void clickSearchBtnOnSecondWidget() {
        UtilsMethods.printInfoLog("Clicking Search button");
        click(pageElements.searchBtnSec);
    }


    public boolean isAirtelMoneyErrorVisibleOnSecondWidget() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyErrorSec);
    }

    public String gettingAirtelMoneyNoResultFoundMessageOnSecondWidget() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.airtelMoneyNoResultFoundMessage));
        return readText(pageElements.airtelMoneyNoResultFoundMessageSec);
    }

    public boolean isAirtelMoneyNoResultFoundVisibleOnSecondWidget() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFoundSec);
    }

    public String getHeadersOnSecondWidget(int row) {
        String header = readText(By.xpath(pageElements.headerRowSec + "[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeaderOnSecondWidget(int row, int column) {
        String value = readText(By.xpath(pageElements.valueRowSec + row + pageElements.valueColumns + column + pageElements.columnText));
        UtilsMethods.printInfoLog("Reading Value(" + row + "): " + value);
        return value;
    }

    public Boolean isResendSMSOnSecondWidget() {
        By check = By.xpath(pageElements.resendSMSSec);
        return checkState(check);
    }
}
