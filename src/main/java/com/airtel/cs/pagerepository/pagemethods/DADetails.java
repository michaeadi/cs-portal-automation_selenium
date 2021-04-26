package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DADetailsPage;
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
public class DADetails extends BasePage {

    DADetailsPage pageElements;
    List<WebElement> rows;

    public DADetails(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DADetailsPage.class);
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.headerRow + row + pageElements.headerValue));
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getDAId(int rowNumber) {
        By daId = By.xpath(pageElements.valueRow + rowNumber + pageElements.daID);
        final String text = getText(daId);
        commonLib.info("Getting DA ID from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDADescription(int rowNumber) {
        By daDescription = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDesc);
        final String text = getText(daDescription);
        commonLib.info("Getting DA Description from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getBundleType(int rowNumber) {
        By bundleType = By.xpath(pageElements.valueRow + rowNumber + pageElements.bundleType);
        final String text = getText(bundleType);
        commonLib.info("Getting DA Bundle Type from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDABalance(int rowNumber) {
        By daBalance = By.xpath(pageElements.valueRow + rowNumber + pageElements.daBalance);
        final String text = getText(daBalance);
        commonLib.info("Getting DA Balance from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDADateTime(int rowNumber) {
        By dateTime = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDateTime);
        final String text = getText(dateTime);
        commonLib.info("Getting DA Date and Time from Row Number " + rowNumber + " : " + text);
        return text;
    }


    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.rows);
        return rows.size();
    }

    public WidgetInteraction clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteraction(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on DA History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    public boolean isDAWidgetIsVisible() {
        commonLib.info("Checking is DA Widget Visible");
        return isElementVisible(pageElements.getTitle);
    }

    public String getAccumulatorHeaders(int row) {
        String header = getText(By.xpath(pageElements.accumulatorHeader + row + pageElements.headerValue));
        commonLib.info("Getting Accumulator header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAccumulator(int row, int column) {
        String value = getText(By.xpath(pageElements.accumulatorColumnHeader + row + pageElements.accumulatorColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getAccumulatorHeaders(column) + "' = " + value);
        return value.trim();
    }
}
