package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.LoanWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class LoanWidget extends BasePage {

    LoanWidgetPage pageElements;
    List<WebElement> vendors;
    private static final String TEXT1 = "Reading Header: ";
    private static final String XPATH = "//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][";

    public LoanWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoanWidgetPage.class);
    }

    public String getVendor() {
        final String text = getText(pageElements.vendor);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    public String getLoanAmount() {
        final String text = getText(pageElements.loanAmount);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    public String getCreatedON() {
        final String text = getText(pageElements.createdOn);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    public String getCurrentOutstanding() {
        final String text = getText(pageElements.currentOutstanding);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    public String getDueDate() {
        final String text = getText(pageElements.dueDate);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    public String getCurrencyType() {
        final String text = getText(pageElements.currencyType);
        commonLib.info("Reading Sub header: " + text);
        return text.trim();
    }

    public String getVendorName(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][1]//span[1]");
        final String text = getText(name);
        commonLib.info("Reading Vendor Name: " + text);
        return text.trim();
    }

    public String getLoanAmount(int i) {
        By amount = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][2]//span");
        final String text = getText(amount);
        commonLib.info("Reading Loan Amount: " + text);
        return text.trim();
    }

    public String getDateCreatedOn(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][3]//span[@class=\"date_time ng-star-inserted\"]");
        final String text = getText(name);
        commonLib.info("Reading Date Created on: " + text);
        return text.trim();
    }

    public String getTimeCreatedOn(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][3]//span[@class=\"time ng-star-inserted\"]");
        final String text = getText(name);
        commonLib.info("Reading Time Created on: " + text);
        return text.trim();
    }

    public String getOutstandingAmount(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][4]//div[@class=\"widget-section\"]");
        final String text = getText(name);
        commonLib.info("Reading Current Outstanding amount: " + text);
        return text.trim();
    }

    public String getDueDate(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][5]//span[@class=\"date_time ng-star-inserted\"]");
        final String text = getText(name);
        commonLib.info("Reading Due Date on: " + text);
        return text.trim();
    }

    public String getTimeDueDate(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][5]//span[@class=\"time ng-star-inserted\"]");
        final String text = getText(name);
        commonLib.info("Reading Due Time on: " + text);
        return text.trim();
    }

    public boolean checkVendorNameDisplay(String name) {
        By vendor = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//span[contains(text(),'" + name + "')]");
        final boolean checkState = checkState(vendor);
        commonLib.info("Is Vendor name displayed: " + checkState);
        return checkState;
    }

    public boolean checkMessageDisplay(String message) {
        By vendor = By.xpath("//div[@id='LOAN_SERVICES']//div[@class=\"card__card-header--card-body--table\"]//span[contains(text(),'" + message + "')]");
        final boolean checkState = checkState(vendor);
        commonLib.info("Is message displayed: " + checkState);
        return checkState;
    }

    public LoanDetail clickVendorName(int i) {
        By name = By.xpath(XPATH + i + "]//div[@class=\"show-error-message ng-star-inserted\"][1]//span[1]");
        commonLib.info("Clicking Vendor Name");
        click(name);
        return new LoanDetail(driver);
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

