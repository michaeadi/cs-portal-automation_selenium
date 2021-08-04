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
    public LoanWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, LoanWidgetPage.class);
    }

    /**
     * This method use to get loan vendor name
     * @return String The value
     */
    public String getVendor() {
        final String text = getText(pageElements.vendor);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    /**
     * This method use to get loan amount from header
     * @return String The value
     */
    public String getLoanAmount() {
        final String text = getText(pageElements.loanAmount);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    /**
     * This method use to loan credit on date from header
     * @return String The value
     */
    public String getCreatedON() {
        final String text = getText(pageElements.createdOn);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    /**
     * This method use to loan outstanding amount from header
     * @return String The value
     */
    public String getCurrentOutstanding() {
        final String text = getText(pageElements.currentOutstanding);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    /**
     * This method use to loan due date from header
     * @return String The value
     */
    public String getDueDate() {
        final String text = getText(pageElements.dueDate);
        commonLib.info(TEXT1 + text);
        return text.trim();
    }

    /**
     * This method use to get currency type from header
     * @return String The value
     */
    public String getCurrencyType() {
        final String text = getText(pageElements.currencyType);
        commonLib.info("Reading Sub header: " + text);
        return text.trim();
    }

    /**
     * This method use to get vendor name based on row number
     * @param i The row number
     * @return String The value
     */
    public String getVendorName(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i + pageElements.vendorName);
        final String text = getText(name);
        commonLib.info("Reading Vendor Name: " + text);
        return text.trim();
    }

    /**
     * This method use to get loan amount value based on row number
     * @param i The row number
     * @return String The value
     */
    public String getLoanAmount(int i) {
        By amount = By.xpath(pageElements.loanServiceValue + i + pageElements.loanAmountValue);
        final String text = getText(amount);
        commonLib.info("Reading Loan Amount: " + text);
        return text.trim();
    }

    /**
     * This method use to get date created on based on row number
     * @param i The row number
     * @return String The value
     */
    public String getDateCreatedOn(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i +pageElements.createdOnDateValue );
        final String text = getText(name);
        commonLib.info("Reading Date Created on: " + text);
        return text.trim();
    }

    /**
     * This method use to get time of created on based on row number
     * @param i The row number
     * @return String The value
     */
    public String getTimeCreatedOn(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i + pageElements.createdOnTimeValue);
        final String text = getText(name);
        commonLib.info("Reading Time Created on: " + text);
        return text.trim();
    }

    /**
     * This method use to get outstanding amount value based on row number
     * @param i The row number
     * @return String The value
     */
    public String getOutstandingAmount(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i + pageElements.outStandingAmountValue);
        final String text = getText(name);
        commonLib.info("Reading Current Outstanding amount: " + text);
        return text.trim();
    }

    /**
     * This method use to get due date value based on row number
     * @param i The row number
     * @return String The value
     */
    public String getDueDate(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i + pageElements.dueDateValue);
        final String text = getText(name);
        commonLib.info("Reading Due Date on: " + text);
        return text.trim();
    }

    /**
     * This method use to get due time value based on row number
     * @param i The row number
     * @return String The value
     */
    public String getTimeDueDate(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i + pageElements.dueTimeValue);
        final String text = getText(name);
        commonLib.info("Reading Due Time on: " + text);
        return text.trim();
    }

    /**
     * This method use to check vendor name display or not based on name
     * @param name The vendor name
     * @return true/false
     */
    public boolean checkVendorNameDisplay(String name) {
        By vendor = By.xpath(pageElements.vendorNameValue + name + "')]");
        final boolean checkState = isEnabled(vendor);
        commonLib.info("Is Vendor name displayed: " + checkState);
        return checkState;
    }

    /**
     * This method use to check  given message display or not
     * @param message The message
     * @return true/false
     */
    public boolean checkMessageDisplay(String message) {
        By vendor = By.xpath(pageElements.message + message + "')]");
        final boolean checkState = isEnabled(vendor);
        commonLib.info("Is message displayed: " + checkState);
        return checkState;
    }

    /**
     * This method use to click vendor name to open loan detail page based on row number
     * @param i The row number
     * @return LoanDetail page
     */
    public LoanDetail clickVendorName(int i) {
        By name = By.xpath(pageElements.loanServiceValue + i + pageElements.vendorName);
        commonLib.info("Clicking Vendor Name");
        clickAndWaitForLoaderToBeRemoved(name);
        return new LoanDetail(driver);
    }

    /**
     * This method use to get total number of vendors
     * @return Integer The count
     */
    public int getSize() {
        vendors = returnListOfElement(pageElements.vendorList);
        return vendors.size();
    }

    /**
     * This method use to get list of all vendor display in loan detail widget page
     * @return List The list of vendors
     */
    public List<String> getVendorNamesList() {
        ArrayList<String> vendorNames = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            vendorNames.add(getVendorName(i + 1).trim());
        }
        return vendorNames;
    }

    /**
     * This Method will let us know Loan Service Widget is visible or not
     * @return Boolean
     */
    public boolean isLoanServiceWidgetVisible() {
        if (isElementVisible(pageElements.loanServiceHeader)) {
            commonLib.info("LoanService Widget is Visible");
            return true;
        }
        return false;
    }
}

