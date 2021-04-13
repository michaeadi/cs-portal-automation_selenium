package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.LoanDetailPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoanDetailPage extends BasePage {

    LoanDetailPageElements pageElements;
    List<WebElement> ls;
    List<WebElement> hls;
    List<WebElement> recoveryWidgetList;

    public LoanDetailPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoanDetailPageElements.class);
    }

    public boolean isLoanDetailWidgetDisplay() {
        UtilsMethods.printInfoLog("Reading Widget: " + getText(pageElements.loanDetailWidgetTitle));
        return checkState(pageElements.loanDetailWidgetTitle);
    }

    public boolean isLoanHistoryWidgetDisplay() {
        UtilsMethods.printInfoLog("Reading Widget: " + getText(pageElements.loanHistoryWidgetTitle));
        return checkState(pageElements.loanHistoryWidgetTitle);
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
        UtilsMethods.printInfoLog("Reading Header Name: " + getText(name));
        return getText(name);
    }

    public String getValueCorrespondingToHeader(int i) {
        By value = By.xpath("//div[@id='LOAN_DETAILS']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"]//div[" + i + "]//span");
        UtilsMethods.printInfoLog("Reading '" + getHeaderName(i) + "' = " + getText(value));
        return getText(value);
    }

    public String getLoanHistoryHeaderName(int i) {
        By name = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div[" + i + "]//span");
        UtilsMethods.printInfoLog("Reading Header Name: " + getText(name));
        return getText(name);
    }

    public String getValueCorrespondingToLoanHistoryHeader(int row, int column) {
        By value = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + row + "]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading '" + getLoanHistoryHeaderName(column) + "' = " + getText(value));
        return getText(value);
    }

    //    public String getDateTime(int row){
//        By date=By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"]["+row+"]//div[7]//span[1]");
//       UtilsMethods.printInfoLog("Reading '"+getLoanHistoryHeaderName(column)+"' = "+readText(value));
//        return readText(value);
//    }
    public void clickTransactionId(int row) throws InterruptedException {
        By value = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + row + "]//div[1]//span");
        UtilsMethods.printInfoLog("Clicking Transaction Id: " + getText(value));
        scrollToViewElement(value);
        click(value);
    }

    public String getLoanRecoveryHeaderName(int transactionNo, int column) {
        By name = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + transactionNo + "]//app-loan-recoveries/div/div[1]//div[@class=\"inner-wrapper ng-star-inserted\"][" + column + "]//span");
        UtilsMethods.printInfoLog("Reading Header Name: " + getText(name));
        return getText(name);
    }

    public String getValueCorrespondingToLoanRecoveryHeader(int transactionNo, int row, int column) {
        By value = By.xpath("//div[@id='LOAN_HISTORY']//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + transactionNo + "]//app-loan-recoveries//div[@class=\"listing-wrapper ng-star-inserted\"][" + row + "]//div[@class=\"inner-wrapper ng-star-inserted\"][" + column + "]//span");
        UtilsMethods.printInfoLog("Reading '" + getLoanRecoveryHeaderName(transactionNo, column) + "' = " + getText(value));
        return getText(value);
    }

    public void clickCloseTab() {
        UtilsMethods.printInfoLog("Closing Widget Tab");
        click(pageElements.closeTab);
        waitTillLoaderGetsRemoved();
        click(pageElements.homeTab);
    }


}
