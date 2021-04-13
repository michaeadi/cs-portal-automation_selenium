package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.UsageWidgetPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class UsageHistoryWidgetPage extends BasePage {

    UsageWidgetPageElements pageElements;
    List<WebElement> as;

    public UsageHistoryWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, UsageWidgetPageElements.class);
        as = returnListOfElement(pageElements.rows);
    }

    public boolean isUsageHistoryErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error in com.airtel.cs.API : " + isElementVisible(pageElements.usageHistoryError));
        return isElementVisible(pageElements.usageHistoryError);
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.headerRow + row + pageElements.headerText));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingUsageHistoryNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + getText(pageElements.usageHistoryNoResultFoundMessage));
        return getText(pageElements.usageHistoryNoResultFoundMessage);
    }

    public boolean isUsageHistoryNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.usageHistoryNoResultFound));
        return isElementVisible(pageElements.usageHistoryNoResultFound);
    }


    public int getNumberOfRows() {
        return as.size();
    }

    public boolean isUsageHistoryWidgetMenuVisible() {
        UtilsMethods.printInfoLog("Checking is Usage History's Menu Visible");
        return isElementVisible(pageElements.menu);
    }

    public void openingMoreDetails() {
        UtilsMethods.printInfoLog("Opening More under Usage History Widget");
        click(pageElements.menu);
    }


    public String getHistoryEndBalance(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Usage History End Balance from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.endBalance).getText());
        return rowElement.findElement(pageElements.endBalance).getText();
    }

    public String getHistoryStartBalance(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Usage History Start Balance from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.startBalance).getText());
        return rowElement.findElement(pageElements.startBalance).getText();
    }

    public String getHistoryDateTime(int rowNumber) {
        By dateTime = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][" + (rowNumber + 1) + "]/div[3]//span");
        UtilsMethods.printInfoLog("Getting Usage History Date Time from Row Number " + rowNumber + " : " + getText(dateTime));
        return getText(dateTime);
    }

    public String getHistoryCharge(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Usage History Charge from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.charge).getText());
        return rowElement.findElement(pageElements.charge).getText().replace('+', ' ').trim();
    }

    public String getHistoryType(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Usage History Type from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.type).getText());
        return rowElement.findElement(pageElements.type).getText();
    }

    public boolean isUsageHistoryWidgetIsVisible() {
        UtilsMethods.printInfoLog("Checking is Usage History Widget Visible");
        return isElementVisible(pageElements.usageHistoryHeader);
    }

    public boolean isUsageHistoryDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking Usage HistoryWidget Date Picker Visibility ");
        return checkState(pageElements.usageHistoryDatePicker);
    }

    public WidgetInteractionPage clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteractionPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Usage History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + getText(pageElements.getTitle));
        return getText(pageElements.getTitle).toLowerCase();
    }
}
