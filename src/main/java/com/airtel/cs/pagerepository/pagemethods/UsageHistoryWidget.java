package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.UsageWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class UsageHistoryWidget extends BasePage {

    UsageWidgetPage pageElements;
    List<WebElement> as;

    public UsageHistoryWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, UsageWidgetPage.class);
        as = returnListOfElement(pageElements.rows);
    }

    public boolean isUsageHistoryErrorVisible() {
        final boolean visible = isElementVisible(pageElements.usageHistoryError);
        commonLib.info("Validating error is visible when there is Error in com.airtel.cs.API : " + visible);
        return visible;
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.headerRow + row + pageElements.headerText));
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingUsageHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.usageHistoryNoResultFoundMessage);
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }

    public boolean isUsageHistoryNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.usageHistoryNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + visible);
        return visible;
    }


    public int getNumberOfRows() {
        return as.size();
    }

    public boolean isUsageHistoryWidgetMenuVisible() {
        commonLib.info("Checking is Usage History's Menu Visible");
        return isElementVisible(pageElements.menu);
    }

    public void openingMoreDetails() {
        commonLib.info("Opening More under Usage History Widget");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }


    public String getHistoryEndBalance(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        final String text = rowElement.findElement(pageElements.endBalance).getText();
        commonLib.info("Getting Usage History End Balance from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getHistoryStartBalance(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        final String text = rowElement.findElement(pageElements.startBalance).getText();
        commonLib.info("Getting Usage History Start Balance from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getHistoryDateTime(int rowNumber) {
        By dateTime = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][" + (rowNumber + 1) + "]/div[3]//span");
        final String text = getText(dateTime);
        commonLib.info("Getting Usage History Date Time from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getHistoryCharge(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        final String text = rowElement.findElement(pageElements.charge).getText();
        commonLib.info("Getting Usage History Charge from Row Number " + rowNumber + " : " + text);
        return text.replace('+', ' ').trim();
    }

    public String getHistoryType(int rowNumber) {
        WebElement rowElement = as.get(rowNumber);
        final String text = rowElement.findElement(pageElements.type).getText();
        commonLib.info("Getting Usage History Type from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public boolean isUsageHistoryWidgetIsVisible() {
        commonLib.info("Checking is Usage History Widget Visible");
        return isElementVisible(pageElements.usageHistoryHeader);
    }

    public boolean isUsageHistoryDatePickerVisible() {
        commonLib.info("Checking Usage HistoryWidget Date Picker Visibility ");
        return isEnabled(pageElements.usageHistoryDatePicker);
    }

    public WidgetInteraction clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
            return new WidgetInteraction(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Usage History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }
}
