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
    private static final String XPATH1 = "//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][";

    public LoanDetail(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoanDetailPage.class);
    }

    public boolean isLoanDetailWidgetDisplay() {
        commonLib.info("Reading Widget: " + getText(pageElements.loanDetailWidgetTitle));
        return isEnabled(pageElements.loanDetailWidgetTitle);
    }

    public boolean isLoanHistoryWidgetDisplay() {
        commonLib.info("Reading Widget: " + getText(pageElements.loanHistoryWidgetTitle));
        return isEnabled(pageElements.loanHistoryWidgetTitle);
    }

    public int getDetailWidgetHeaderSize() {
        ls = returnListOfElement(pageElements.headerList);
        return ls.size();
    }

    public int getHistoryWidgetHeaderSize() {
        hls = returnListOfElement(pageElements.historyHeaderList);
        return hls.size();
    }

    public int getRecoveryWidgetHeaderSize() {
        recoveryWidgetList = returnListOfElement(pageElements.recoveryWidgetHeaderList);
        return recoveryWidgetList.size();
    }

    public String getHeaderName(int i) {
        By name = By.xpath("//div[@id='LOAN_DETAILS']//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div[" + i + "]//span");
        final String text = getText(name);
        commonLib.info(TEXT1 + text);
        return text;
    }

    public String getValueCorrespondingToHeader(int i) {
        By value = By.xpath("//div[@id='LOAN_DETAILS']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"]//div[" + i + "]//span");
        final String text = getText(value);
        commonLib.info(TEXT2 + getHeaderName(i) + "' = " + text);
        return text;
    }

    public String getLoanHistoryHeaderName(int i) {
        By name = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div[" + i + "]//span");
        final String text = getText(name);
        commonLib.info(TEXT1 + text);
        return text;
    }

    public String getValueCorrespondingToLoanHistoryHeader(int row, int column) {
        By value = By.xpath(XPATH1 + row + "]//div[" + column + "]/span");
        final String text = getText(value);
        commonLib.info(TEXT2 + getLoanHistoryHeaderName(column) + "' = " + text);
        return text;
    }

    public void clickTransactionId(int row) throws InterruptedException {
        By value = By.xpath(XPATH1 + row + "]//div[1]//span");
        commonLib.info("Clicking Transaction Id: " + getText(value));
        scrollToViewElement(value);
        clickAndWaitForLoaderToBeRemoved(value);
    }

    public String getLoanRecoveryHeaderName(int transactionNo, int column) {
        By name = By.xpath(XPATH1 + transactionNo + "]//app-loan-recoveries/div/div[1]//div[@class=\"inner-wrapper ng-star-inserted\"][" + column + "]//span");
        final String text = getText(name);
        commonLib.info(TEXT1 + text);
        return text;
    }

    public String getValueCorrespondingToLoanRecoveryHeader(int transactionNo, int row, int column) {
        By value = By.xpath(XPATH1 + transactionNo + "]//app-loan-recoveries//div[@class=\"listing-wrapper ng-star-inserted\"][" + row + "]//div[@class=\"inner-wrapper ng-star-inserted\"][" + column + "]//span");
        final String text = getText(value);
        commonLib.info(TEXT2 + getLoanRecoveryHeaderName(transactionNo, column) + "' = " + text);
        return text;
    }

    public void clickCloseTab() {
        commonLib.info("Closing Widget Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTab);
        waitTillLoaderGetsRemoved();
        clickAndWaitForLoaderToBeRemoved(pageElements.homeTab);
    }


}
