package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.SmartCashTransactionHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SmartCashTransactionHistory extends BasePage {
    SmartCashTransactionHistoryPage pageElements;

    public SmartCashTransactionHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SmartCashTransactionHistoryPage.class);
    }

    /**
     * This method use to check SmartCash TransactionHistory widget visibility
     *
     * @return true/false
     */
    public boolean isSmartCashTransactionHistoryVisible() {
        boolean status = isEnabled(pageElements.smartCashTxnHistoryWidget);
        commonLib.info("Is SmartCash Transaction History visible: " + status);
        return status;
    }

    /**
     * This method use to check filter by today date display or not
     *
     * @return true/false
     */
    public boolean isTodayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.todayFilterSec);
        commonLib.info("Is Today Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last two day display or not
     *
     * @return true/false
     */
    public boolean isLastTwoDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lasTwoDaysSec);
        commonLib.info("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last seven day display or not
     *
     * @return true/false
     */
    public boolean isLastSevenDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lastSevenDaysSec);
        commonLib.info("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check is date range filter display or not
     *
     * @return true/false
     */
    public boolean isDateRangeFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.dateRangeFilterSec);
        commonLib.info("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check negative sign display or not
     *
     * @param row The row number
     * @return true/false
     */
    public Boolean isNegSignDisplayOnSecondWidget(int row) {
        By negAmount = By.xpath(pageElements.valueRowSec + row + pageElements.negSymbol);
        return isEnabled(negAmount);
    }

    /**
     * This method use to check positive sign display or not
     *
     * @param row The row number
     * @return true/false
     */
    public Boolean isPosSignDisplayOnSecondWidget(int row) {
        By posAmount = By.xpath(pageElements.valueRowSec + row + pageElements.posSymbol);
        return isEnabled(posAmount);
    }

    /**
     * This method use to check AM menu widget display or not
     *
     * @return true/false
     */
    public boolean isAMMenuHistoryTabDisplayOnSecondWidget() {
        boolean status = isEnabled(pageElements.titleSec);
        commonLib.info("Is AM Menu History Tab Display: " + status);
        return status;
    }


    /**
     * This method use to check is transaction id box display or not
     *
     * @return true/false
     */
    public boolean isSearchTxnIdBoxOnSecondWidget() {
        boolean status = isEnabled(pageElements.searchTxnIdSec);
        commonLib.info("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    /**
     * This method use to write transaction id into search box
     *
     * @param txnId The Transaction Id
     */
    public void searchByTxnIdOnSecondWidget(String txnId) {
        commonLib.info("Search By Transaction Id: " + txnId);
        enterText(pageElements.searchTxnIdSec, txnId);
    }

    /**
     * This method use to click search button
     */
    public void clickSearchBtnOnSecondWidget() {
        commonLib.info("Clicking Search button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtnSec);
    }

    /**
     * This method use to check airtel money error visible
     *
     * @return true/false
     */
    public boolean isAirtelMoneyErrorVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyErrorSec);
    }

    /**
     * This method use to get no result found message
     *
     * @return String The String
     */
    public String gettingAirtelMoneyNoResultFoundMessageOnSecondWidget() {
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + getText(pageElements.airtelMoneyNoResultFoundMessage));
        return getText(pageElements.airtelMoneyNoResultFoundMessageSec);
    }

    /**
     * This method use to get no result found icon display or not
     *
     * @return true/false
     */
    public boolean isAirtelMoneyNoResultFoundVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFoundSec);
    }

    /**
     * This method use to get header name based on column number
     *
     * @param column The column number
     * @return String The Value
     */
    public String getHeadersOnSecondWidget(int column) {
        String header = getText(By.xpath(pageElements.headerRowSec + "[" + column + "]"));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value based on row and column number
     *
     * @param row    The Row Number
     * @param column The column number
     * @return String The Value
     */
    public String getValueCorrespondingToHeader(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRowSec + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    /**
     * This method use to check resend sms icon display or not
     *
     * @return true/false
     */
    public Boolean isResendSMSIconVisible(int row, int column) {
        By check = By.xpath(pageElements.valueRowSec + row + pageElements.iconColumn + column + pageElements.resendSmsIcon);
        return isEnabled(check);
    }

    /**
     * This method use to check reversal sms icon display or not
     *
     * @return true/false
     */
    public Boolean isReversalIconVisible(int row, int column) {
        By check = By.xpath(pageElements.valueRowSec + row + pageElements.iconColumn + column + pageElements.reversalIcon);
        return isEnabled(check);
    }

    /**
     * This method use to get data value based on row and column number for Transaction Details
     *
     * @param row    The Row Number
     * @param column The column number
     * @return detail
     */
    public String getValueCorrespondingToTxnHeader(int row, int column) {
        String detail = getText(By.xpath(pageElements.detailsRow + row + pageElements.detailsColumn + column + pageElements.detailsColumnText));
        commonLib.info("Reading Value(" + row + "): " + detail);
        return detail;
    }

    /**
     * This Method is used to click on meta info drop down
     *
     * @param row the row for drop down
     */
    public void clickDropDownForMetaInfo(int row) {
        commonLib.info("Going to click on meta info drop down");
        clickWithoutLoader(By.xpath(pageElements.rowMetaInfo1 + row + pageElements.rowMetaInfo2));
    }

}
