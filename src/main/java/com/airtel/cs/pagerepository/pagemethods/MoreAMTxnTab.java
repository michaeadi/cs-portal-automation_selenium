package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MoreAMTxnTabPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class MoreAMTxnTab extends BasePage {

    MoreAMTxnTabPage pageElements;

    public MoreAMTxnTab(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MoreAMTxnTabPage.class);
    }

    public Boolean isNegSignDisplay(int row) {
        By negAmount = By.xpath(pageElements.valueRow + row + pageElements.negSymbol);
        return isEnabled(negAmount);
    }

    public Boolean isPosSignDisplay(int row) {
        By posAmount = By.xpath(pageElements.valueRow + row + pageElements.posSymbol);
        return isEnabled(posAmount);
    }

    public boolean isAMMenuHistoryTabDisplay() {
        boolean status = isEnabled(pageElements.title);
        commonLib.info("Is AM Menu History Tab Display: " + status);
        return status;
    }

    public boolean isTodayFilterTab() {
        boolean status = isEnabled(pageElements.todayFilter);
        commonLib.info("Is Today Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastTwoDayFilterTab() {
        boolean status = isEnabled(pageElements.lasTwoDays);
        commonLib.info("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastSevenDayFilterTab() {
        boolean status = isEnabled(pageElements.lastSevenDays);
        commonLib.info("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isDateRangeFilterTab() {
        boolean status = isEnabled(pageElements.dateRangeFilter);
        commonLib.info("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    public boolean isSearchTxnIdBox() {
        boolean status = isEnabled(pageElements.searchTxnId);
        commonLib.info("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    public void searchByTxnId(String txnId) {
        commonLib.info("Search By Transaction Id: " + txnId);
        enterText(pageElements.searchTxnId, txnId);
    }

    public void clickSearchBtn() {
        commonLib.info("Clicking Search button");
        click(pageElements.searchBtn);
    }


    public boolean isAirtelMoneyErrorVisible() {
        final boolean visible = isElementVisible(pageElements.airtelMoneyError);
        commonLib.info("Validating error is visible when there is Error inAPI : " + visible);
        return visible;
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        final String text = getText(pageElements.airtelMoneyNoResultFoundMessage);
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + elementVisible);
        return elementVisible;
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.headerRow + "[" + row + "]"));
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRow + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    public Boolean isResendSMS() {
        By check = By.xpath(pageElements.resendSMS);
        return isEnabled(check);
    }

    //Secondary Widget

    public Boolean isNegSignDisplayOnSecondWidget(int row) {
        By negAmount = By.xpath(pageElements.valueRowSec + row + pageElements.negSymbol);
        return isEnabled(negAmount);
    }

    public Boolean isPosSignDisplayOnSecondWidget(int row) {
        By posAmount = By.xpath(pageElements.valueRowSec + row + pageElements.posSymbol);
        return isEnabled(posAmount);
    }

    public boolean isAMMenuHistoryTabDisplayOnSecondWidget() {
        boolean status = isEnabled(pageElements.titleSec);
        commonLib.info("Is AM Menu History Tab Display: " + status);
        return status;
    }

    public boolean isTodayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.todayFilterSec);
        commonLib.info("Is Today Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastTwoDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lasTwoDaysSec);
        commonLib.info("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isLastSevenDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lastSevenDaysSec);
        commonLib.info("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    public boolean isDateRangeFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.dateRangeFilterSec);
        commonLib.info("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    public boolean isSearchTxnIdBoxOnSecondWidget() {
        boolean status = isEnabled(pageElements.searchTxnIdSec);
        commonLib.info("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    public void searchByTxnIdOnSecondWidget(String txnId) {
        commonLib.info("Search By Transaction Id: " + txnId);
        enterText(pageElements.searchTxnIdSec, txnId);
    }

    public void clickSearchBtnOnSecondWidget() {
        commonLib.info("Clicking Search button");
        click(pageElements.searchBtnSec);
    }


    public boolean isAirtelMoneyErrorVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyErrorSec);
    }

    public String gettingAirtelMoneyNoResultFoundMessageOnSecondWidget() {
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + getText(pageElements.airtelMoneyNoResultFoundMessage));
        return getText(pageElements.airtelMoneyNoResultFoundMessageSec);
    }

    public boolean isAirtelMoneyNoResultFoundVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFoundSec);
    }

    public String getHeadersOnSecondWidget(int row) {
        String header = getText(By.xpath(pageElements.headerRowSec + "[" + row + "]"));
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeaderOnSecondWidget(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRowSec + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    public Boolean isResendSMSOnSecondWidget() {
        By check = By.xpath(pageElements.resendSMSSec);
        return isEnabled(check);
    }
}
