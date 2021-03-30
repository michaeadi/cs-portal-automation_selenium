package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoanDetailPOM extends BasePage {

    /*
     * Loan Detail Widget Element Locator
     * */
    By loanDetailWidgetTitle = By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]");
    By headerList = By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div");
    List<WebElement> ls = returnListOfElement(headerList);

    /*
     * Loan History Widget Element Locator
     * */
    By loanHistoryWidgetTitle = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]");
    By historyHeaderList = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div");
    List<WebElement> Hls = returnListOfElement(historyHeaderList);

    /*
     * Loan Recoveries Widget Element Locator
     * */
    By recoveryWidgetHeaderList = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][1]//app-loan-recoveries/div/div[1]//div[@class=\"inner-wrapper ng-star-inserted\"]");
    List<WebElement> recoveryWidgetList = returnListOfElement(recoveryWidgetHeaderList);
    By closeTab = By.xpath("//mat-icon[contains(text(),'close')]");
    By homeTab = By.xpath("//*[contains(text(),'HOME')]");

    public LoanDetailPOM(WebDriver driver) {
        super(driver);
    }

    public boolean IsLoanDetailWidgetDisplay() {
        UtilsMethods.printInfoLog("Reading Widget: " + readText(loanDetailWidgetTitle));
        return checkState(loanDetailWidgetTitle);
    }

    public boolean IsLoanHistoryWidgetDisplay() {
        UtilsMethods.printInfoLog("Reading Widget: " + readText(loanHistoryWidgetTitle));
        return checkState(loanHistoryWidgetTitle);
    }

    public int getDetailWidgetHeaderSize() {
        return ls.size();
    }

    public int getHistoryWidgetHeaderSize() {
        return Hls.size();
    }

    public int getRecoveryWidgetHeaderSize() {
        return recoveryWidgetList.size();
    }

    public String getHeaderName(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div[" + i + "]//span");
        UtilsMethods.printInfoLog("Reading Header Name: " + readText(name));
        return readText(name);
    }

    public String getValueCorrespondingToHeader(int i) {
        By value = By.xpath("//span[contains(text(),'LOAN DETAILS') and @class=\"card__card-header--label\"]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"]//div[" + i + "]//span");
        UtilsMethods.printInfoLog("Reading '" + getHeaderName(i) + "' = " + readText(value));
        return readText(value);
    }

    public String getLoanHistoryHeaderName(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--list-heading left-spacing\"]//div[" + i + "]//span");
        UtilsMethods.printInfoLog("Reading Header Name: " + readText(name));
        return readText(name);
    }

    public String getValueCorrespondingToLoanHistoryHeader(int row, int column) {
        By value = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + row + "]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading '" + getLoanHistoryHeaderName(column) + "' = " + readText(value));
        return readText(value);
    }

//    public String getDateTime(int row){
//        By date=By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"]["+row+"]//div[7]//span[1]");
//       UtilsMethods.printInfoLog("Reading '"+getLoanHistoryHeaderName(column)+"' = "+readText(value));
//        return readText(value);
//    }

    public void clickTransactionId(int row) throws InterruptedException {
        By value = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + row + "]//div[1]//span");
        UtilsMethods.printInfoLog("Clicking Transaction Id: " + readText(value));
        scrollToViewElement(value);
        click(value);
    }

    public String getLoanRecoveryHeaderName(int transactionNo, int column) {
        By name = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + transactionNo + "]//app-loan-recoveries/div/div[1]//div[@class=\"inner-wrapper ng-star-inserted\"][" + column + "]//span");
        UtilsMethods.printInfoLog("Reading Header Name: " + readText(name));
        return readText(name);
    }

    public String getValueCorrespondingToLoanRecoveryHeader(int transactionNo, int row, int column) {
        By value = By.xpath("//span[contains(text(),'LOAN HISTORY') or class=\"card__card-header--label\"]//ancestor::div[@class=\"custom-widget ng-star-inserted\"]//div[2]//div[@class=\"card__card-header--card-body--table--data-list innertable-area ng-star-inserted\"][" + transactionNo + "]//app-loan-recoveries//div[@class=\"listing-wrapper ng-star-inserted\"][" + row + "]//div[@class=\"inner-wrapper ng-star-inserted\"][" + column + "]//span");
        UtilsMethods.printInfoLog("Reading '" + getLoanRecoveryHeaderName(transactionNo, column) + "' = " + readText(value));
        return readText(value);
    }

    public void clickCloseTab() {
        UtilsMethods.printInfoLog("Closing Widget Tab");
        click(closeTab);
        waitTillLoaderGetsRemoved();
        click(homeTab);
    }


}
