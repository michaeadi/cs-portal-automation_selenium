package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.LoanWidgetPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class LoanWidgetPage extends BasePage {

    LoanWidgetPageElements pageElements;
    List<WebElement> vendors;

    public LoanWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoanWidgetPageElements.class);
    }

    public String getVendor() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(pageElements.vendor));
        return readText(pageElements.vendor).trim();
    }

    public String getLoanAmount() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(pageElements.loanAmount));
        return readText(pageElements.loanAmount).trim();
    }

    public String getCreatedON() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(pageElements.createdOn));
        return readText(pageElements.createdOn).trim();
    }

    public String getCurrentOutstanding() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(pageElements.currentOutstanding));
        return readText(pageElements.currentOutstanding).trim();
    }

    public String getDueDate() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(pageElements.dueDate));
        return readText(pageElements.dueDate).trim();
    }

    public String getCurrencyType() {
        UtilsMethods.printInfoLog("Reading Sub header: " + readText(pageElements.currencyType));
        return readText(pageElements.currencyType).trim();
    }

    public String getVendorName(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][1]//span[1]");
        UtilsMethods.printInfoLog("Reading Vendor Name: " + readText(name));
        return readText(name).trim();
    }

    public String getLoanAmount(int i) {
        By amount = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][2]//span");
        UtilsMethods.printInfoLog("Reading Loan Amount: " + readText(amount));
        return readText(amount).trim();
    }

    public String getDateCreatedOn(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][3]//span[@class=\"date_time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Date Created on: " + readText(name));
        return readText(name).trim();
    }

    public String getTimeCreatedOn(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][3]//span[@class=\"time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Time Created on: " + readText(name));
        return readText(name).trim();
    }

    public String getOutstandingAmount(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][4]//div[@class=\"widget-section\"]");
        UtilsMethods.printInfoLog("Reading Current Outstanding amount: " + readText(name));
        return readText(name).trim();
    }

    public String getDueDate(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][5]//span[@class=\"date_time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Due Date on: " + readText(name));
        return readText(name).trim();
    }

    public String getTimeDueDate(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][5]//span[@class=\"time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Due Time on: " + readText(name));
        return readText(name).trim();
    }

    public boolean checkVendorNameDisplay(String name) {
        By vendor = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//span[contains(text(),'" + name + "')]");
        UtilsMethods.printInfoLog("Is Vendor name displayed: " + checkState(vendor));
        return checkState(vendor);
    }

    public boolean checkMessageDisplay(String message) {
        By vendor = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//span[contains(text(),'" + message + "')]");
        UtilsMethods.printInfoLog("Is message displayed: " + checkState(vendor));
        return checkState(vendor);
    }

    public LoanDetailPage clickVendorName(int i) {
        By name = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][1]//span[1]");
        UtilsMethods.printInfoLog("Clicking Vendor Name");
        click(name);
        return new LoanDetailPage(driver);
    }

    public int getSize() {
        vendors = returnListOfElement(pageElements.vendorList);
        return vendors.size();
    }

    public List<String> getVendorNamesList() {
        List<String> vendors = new ArrayList();
        for (int i = 0; i < getSize(); i++) {
            vendors.add(getVendorName(i + 1).trim());
        }
        return vendors;
    }
}

