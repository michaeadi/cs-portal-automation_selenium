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

    /**
     * This method use to check negative sign display or not(Primary Widget)
     * @param row The row number
     * @return true/false
     */
    public Boolean isNegSignDisplay(int row) {
        By negAmount = By.xpath(pageElements.valueRow + row + pageElements.negSymbol);
        return getAttribute(negAmount,"src",false).contains("dr.svg");
    }

    /**
     * This method use to check positive sign display or not(Primary Widget)
     * @param row The row number
     * @return true/false
     */
    public Boolean isPosSignDisplay(int row) {
        By posAmount = By.xpath(pageElements.valueRow + row + pageElements.posSymbol);
        return getAttribute(posAmount,"src",false).contains("cr.svg");
    }

    /**
     * This method use to check AM menu widget display or not(Primary Widget)
     * @return true/false
     */
    public boolean isAMMenuHistoryTabDisplay() {
        boolean status = isEnabled(pageElements.title);
        commonLib.info("Is AM Menu History Tab Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by today date display or not(Primary Widget)
     * @return true/false
     */
    public boolean isTodayFilterTab() {
        boolean status = isEnabled(pageElements.todayFilter);
        commonLib.info("Is Today Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last two day display or not(Primary Widget)
     * @return true/false
     */
    public boolean isLastTwoDayFilterTab() {
        boolean status = isEnabled(pageElements.lasTwoDays);
        commonLib.info("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last seven day display or not(Primary Widget)
     * @return true/false
     */
    public boolean isLastSevenDayFilterTab() {
        boolean status = isEnabled(pageElements.lastSevenDays);
        commonLib.info("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check is date range filter display or not(Primary Widget)
     * @return true/false
     */
    public boolean isDateRangeFilterTab() {
        boolean status = isEnabled(pageElements.dateRangeFilter);
        commonLib.info("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check is transaction id box display or not(Primary Widget)
     * @return true/false
     */
    public boolean isSearchTxnIdBox() {
        boolean status = isEnabled(pageElements.searchTxnId);
        commonLib.info("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    /**
     * This method use to write transaction id into search box(Primary Widget)
     * @param txnId The Transaction Id
     */
    public void searchByTxnId(String txnId) {
        commonLib.info("Search By Transaction Id: " + txnId);
        enterText(pageElements.searchTxnId, txnId);
    }

    /**
     * This method use to click search button(Primary Widget)
     */
    public void clickSearchBtn() {
        commonLib.info("Clicking Search button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtn);
    }


    /**
     * This method use to check airtel money error visible(Primary Widget)
     * @return true/false
     */
    public boolean isAirtelMoneyErrorVisible() {
        final boolean visible = isElementVisible(pageElements.airtelMoneyError);
        commonLib.info("Validating error is visible when there is Error in CS API : " + visible);
        return visible;
    }

    /**
     * This method use to get no result found message(Primary Widget)
     * @return String The String
     */
    public String gettingAirtelMoneyNoResultFoundMessage() {
        final String text = getText(pageElements.airtelMoneyNoResultFoundMessage);
        commonLib.info("Validating error message when there is no data from CS API : " + text);
        return text;
    }

    /**
     * This method use to get no result found icon display or not(Primary Widget)
     * @return true/false
     */
    public boolean isAirtelMoneyNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyNoResultFound);
        commonLib.info("Validating error is visible when there is no data from CS API : " + elementVisible);
        return elementVisible;
    }

    /**
     * This method use to get header name based on column number(Primary Widget)
     * @param column The column number
     * @return String The Value
     */
    public String getHeaders(int column) {
        String header = getText(By.xpath(pageElements.headerRow + "[" + column + "]"));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value based on row and column number(Primary Widget)
     * @param row The Row Number
     * @param column The column number
     * @return String The Value
     */
    public String getValueCorrespondingToHeader(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRow + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    /**
     * This method use to check resend sms icon display or not(Primary Widget)
     * @return true/false
     */
    public Boolean isResendSMS() {
        By check = By.xpath(pageElements.resendSMS);
        return isEnabled(check);
    }

    //Secondary Widget

    /**
     * This method use to check negative sign display or not(Secondary Widget)
     * @param row The row number
     * @return true/false
     */
    public Boolean isNegSignDisplayOnSecondWidget(int row) {
        By negAmount = By.xpath(pageElements.valueRowSec + row + pageElements.negSymbol);
        return isEnabled(negAmount);
    }

    /**
     * This method use to check positive sign display or not(Secondary Widget)
     * @param row The row number
     * @return true/false
     */
    public Boolean isPosSignDisplayOnSecondWidget(int row) {
        By posAmount = By.xpath(pageElements.valueRowSec + row + pageElements.posSymbol);
        return isEnabled(posAmount);
    }

    /**
     * This method use to check AM menu widget display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isAMMenuHistoryTabDisplayOnSecondWidget() {
        boolean status = isEnabled(pageElements.titleSec);
        commonLib.info("Is AM Menu History Tab Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by today date display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isTodayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.todayFilterSec);
        commonLib.info("Is Today Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last two day display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isLastTwoDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lasTwoDaysSec);
        commonLib.info("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last seven day display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isLastSevenDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lastSevenDaysSec);
        commonLib.info("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check is date range filter display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isDateRangeFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.dateRangeFilterSec);
        commonLib.info("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check is transaction id box display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isSearchTxnIdBoxOnSecondWidget() {
        boolean status = isEnabled(pageElements.searchTxnIdSec);
        commonLib.info("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    /**
     * This method use to write transaction id into search box(Secondary Widget)
     * @param txnId The Transaction Id
     */
    public void searchByTxnIdOnSecondWidget(String txnId) {
        commonLib.info("Search By Transaction Id: " + txnId);
        enterText(pageElements.searchTxnIdSec, txnId);
    }

    /**
     * This method use to click search button(Secondary Widget)
     */
    public void clickSearchBtnOnSecondWidget() {
        commonLib.info("Clicking Search button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtnSec);
    }

    /**
     * This method use to check airtel money error visible(Secondary Widget)
     * @return true/false
     */
    public boolean isAirtelMoneyErrorVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyErrorSec);
    }

    /**
     * This method use to get no result found message(Secondary Widget)
     * @return String The String
     */
    public String gettingAirtelMoneyNoResultFoundMessageOnSecondWidget() {
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + getText(pageElements.airtelMoneyNoResultFoundMessage));
        return getText(pageElements.airtelMoneyNoResultFoundMessageSec);
    }

    /**
     * This method use to get no result found icon display or not(Secondary Widget)
     * @return true/false
     */
    public boolean isAirtelMoneyNoResultFoundVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFoundSec);
    }

    /**
     * This method use to get header name based on column number(Secondary Widget)
     * @param column The column number
     * @return String The Value
     */
    public String getHeadersOnSecondWidget(int column) {
        String header = getText(By.xpath(pageElements.headerRowSec + "[" + column + "]"));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value based on row and column number(Secondary Widget)
     * @param row The Row Number
     * @param column The column number
     * @return String The Value
     */
    public String getValueCorrespondingToHeaderOnSecondWidget(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRowSec + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    /**
     * This method use to check resend sms icon display or not(Secondary Widget)
     * @return true/false
     */
    public Boolean isResendSMSOnSecondWidget() {
        By check = By.xpath(pageElements.resendSMSSec);
        return isEnabled(check);
    }
}
