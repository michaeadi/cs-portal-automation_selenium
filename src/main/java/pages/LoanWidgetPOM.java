package pages;

import Utils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LoanWidgetPOM extends BasePage {
    /*
     * Header Labels
     * */
    By vendor = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading\"]//div[1]//span");
    By loanAmount = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading\"]//div[2]//span[1]");
    By createdOn = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading\"]//div[3]//span");
    By currentOutstanding = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading\"]//div[4]//span");
    By dueDate = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading\"]//div[5]//span");
    /*
     * Sub Header Loan Amount Currency Type
     * */
    By currencyType = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading\"]//div[2]//span[2]");

    /*
     *Vendors List & Details
     * */
    By vendorList = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    List<WebElement> vendors;

    public LoanWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public String getVendor() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(vendor));
        return readText(vendor).trim();
    }

    public String getLoanAmount() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(loanAmount));
        return readText(loanAmount).trim();
    }

    public String getCreatedON() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(createdOn));
        return readText(createdOn).trim();
    }

    public String getCurrentOutstanding() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(currentOutstanding));
        return readText(currentOutstanding).trim();
    }

    public String getDueDate() {
        UtilsMethods.printInfoLog("Reading Header: " + readText(dueDate));
        return readText(dueDate).trim();
    }

    public String getCurrencyType() {
        UtilsMethods.printInfoLog("Reading Sub header: " + readText(currencyType));
        return readText(currencyType).trim();
    }

    public String getVendorName(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][1]//span[1]");
        UtilsMethods.printInfoLog("Reading Vendor Name: " + readText(name));
        return readText(name).trim();
    }

    public String getLoanAmount(int i) {
        By amount = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][2]//span");
        UtilsMethods.printInfoLog("Reading Loan Amount: " + readText(amount));
        return readText(amount).trim();
    }

    public String getDateCreatedOn(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][3]//span[@class=\"date_time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Date Created on: " + readText(name));
        return readText(name).trim();
    }

    public String getTimeCreatedOn(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][3]//span[@class=\"time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Time Created on: " + readText(name));
        return readText(name).trim();
    }

    public String getOutstandingAmount(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][4]//div[@class=\"widget-section\"]");
        UtilsMethods.printInfoLog("Reading Current Outstanding amount: " + readText(name));
        return readText(name).trim();
    }

    public String getDueDate(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][5]//span[@class=\"date_time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Due Date on: " + readText(name));
        return readText(name).trim();
    }

    public String getTimeDueDate(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][5]//span[@class=\"time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Reading Due Time on: " + readText(name));
        return readText(name).trim();
    }

    public boolean checkVendorNameDisplay(String name) {
        By vendor = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//span[contains(text(),'" + name + "')]");
        UtilsMethods.printInfoLog("Is Vendor name displayed: " + checkState(vendor));
        return checkState(vendor);
    }

    public boolean checkMessageDisplay(String message) {
        By vendor = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//span[contains(text(),'" + message + "')]");
        UtilsMethods.printInfoLog("Is message displayed: " + checkState(vendor));
        return checkState(vendor);
    }

    public LoanDetailPOM clickVendorName(int i) {
        By name = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"][" + i + "]//div[@class=\"show-error-message ng-star-inserted\"][1]//span[1]");
        UtilsMethods.printInfoLog("Clicking Vendor Name");
        click(name);
        return new LoanDetailPOM(driver);
    }

    public int getSize() {
        vendors = returnListOfElement(vendorList);
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
