package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.LoanDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoanDetail extends BasePage {

    LoanDetailPage pageElements;
    List<WebElement> ls;
    List<WebElement> hls;
    List<WebElement> recoveryWidgetList;
    private static final String TEXT1 = "Reading Header Name: ";
    private static final String TEXT2 = "Reading '";
    private static final String XPATH1 = "//div[@id='LOAN_HISTORY']//div[2]//div[@class='card__card-header--card-body--table--data-list innertable-area ng-star-inserted'][";

    public LoanDetail(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoanDetailPage.class);
    }

    /**
     * This method is use to check loan widget display or not
     * @return true/false
     */
    public boolean isLoanDetailWidgetDisplay() {
        commonLib.info("Reading Widget: " + getText(pageElements.loanDetailWidgetTitle));
        return isEnabled(pageElements.loanDetailWidgetTitle);
    }

    /**
     * This method is use to check loan history widget display or not
     * @return true/false
     */
    public boolean isLoanHistoryWidgetDisplay() {
        commonLib.info("Reading Widget: " + getText(pageElements.loanHistoryWidgetTitle));
        return isEnabled(pageElements.loanHistoryWidgetTitle);
    }

    /**
     * This method is use to get loan widget header size
     * @return Integer The Count
     */
    public int getDetailWidgetHeaderSize() {
        ls = returnListOfElement(pageElements.headerList);
        return ls.size();
    }

    /**
     * This method is use to get loan history widget size 
     * @return Integer The Count
     */
    public int getHistoryWidgetHeaderSize() {
        hls = returnListOfElement(pageElements.historyHeaderList);
        return hls.size();
    }

    /**
     * This method is use to get loan recovery widget size 
     * @return Integer The Count
     */
    public int getRecoveryWidgetHeaderSize() {
        recoveryWidgetList = returnListOfElement(pageElements.recoveryWidgetHeaderList);
        return recoveryWidgetList.size();
    }

    /**
     * This method used to get loan widget header name based on column number
     * @param column The column number
     * @return String The value
     */
    public String getHeaderName(int column) {
        By name = By.xpath( pageElements.widgetHeader+ column + pageElements.value);
        final String text = getText(name);
        commonLib.info(TEXT1 + text);
        return text;
    }

    /**
     * This method used to get loan widget data value based on row number
     * @param row The column number
     * @return String The value
     */
    public String getValueCorrespondingToHeader(int row) {
        By value = By.xpath(pageElements.columnValue + row + pageElements.value);
        final String text = getText(value);
        commonLib.info(TEXT2 + getHeaderName(row) + "' = " + text);
        return text;
    }

    /**
     * This method used to get loan history widget header name based on column number
     * @param i The column number
     * @return String The value
     */
    public String getLoanHistoryHeaderName(int i) {
        By name = By.xpath(pageElements.loanRecoveryWidgetHeader + i + pageElements.value);
        final String text = getText(name);
        commonLib.info(TEXT1 + text);
        return text;
    }

    /**
     * This method used to get loan history widget data value based on row and column number
     * @param row The row number
     * @param column The Column number
     * @return String The value
     */
    public String getValueCorrespondingToLoanHistoryHeader(int row, int column) {
        By value = By.xpath(XPATH1 + row + pageElements.recoveryHeaderValue + column + pageElements.recoveryText);
        final String text = getText(value);
        commonLib.info(TEXT2 + getLoanHistoryHeaderName(column) + "' = " + text);
        return text;
    }

    /**
     * This method is use to click transaction id based on row number
     * @param row The row number
     * @throws InterruptedException in-case of scroll to element interrupt
     */
    public void clickTransactionId(int row) throws InterruptedException {
        By value = By.xpath(XPATH1 + row + pageElements.transactionId);
        commonLib.info("Clicking Transaction Id: " + getText(value));
        scrollToViewElement(value);
        clickAndWaitForLoaderToBeRemoved(value);
    }

    /**
     * This method is use to get loan recovery header name based on transaction id and  row number
     * @param transactionNo The transaction id
     * @param column The column number
     */
    public String getLoanRecoveryHeaderName(int transactionNo, int column) {
        By name = By.xpath(XPATH1 + transactionNo + pageElements.headerValue + column + pageElements.value);
        final String text = getText(name);
        commonLib.info(TEXT1 + text);
        return text;
    }

    /**
     * This method is use to get recovery widget data value based on transaction id and row number and column number
     * @param transactionNo The transaction id
     * @param row The row number
     * @param column The column number
     */
    public String getValueCorrespondingToLoanRecoveryHeader(int transactionNo, int row, int column) {
        By value = By.xpath(XPATH1 + transactionNo + pageElements.recoveryColumnValue + row + pageElements.recoveryColumnText + column + pageElements.value);
        final String text = getText(value);
        commonLib.info(TEXT2 + getLoanRecoveryHeaderName(transactionNo, column) + "' = " + text);
        return text;
    }

    /**
     * This method is use to click loan history sub tab button and click on home button to open home tab
     */
    public void clickCloseTab() {
        commonLib.info("Closing Widget Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTab);
        clickAndWaitForLoaderToBeRemoved(pageElements.homeTab);
    }


}
