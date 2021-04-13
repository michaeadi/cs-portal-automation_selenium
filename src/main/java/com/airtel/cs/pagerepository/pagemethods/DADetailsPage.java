package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.DADetailsPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class DADetailsPage extends BasePage {

    DADetailsPageElements pageElements;
    List<WebElement> rows;

    public DADetailsPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DADetailsPageElements.class);
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.headerRow + row + pageElements.headerValue));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getDAId(int rowNumber) {
        By daId = By.xpath(pageElements.valueRow + rowNumber + pageElements.daID);
        UtilsMethods.printInfoLog("Getting DA ID from Row Number " + rowNumber + " : " + getText(daId));
        return getText(daId);
    }

    public String getDADescription(int rowNumber) {
        By daDescription = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDesc);
        UtilsMethods.printInfoLog("Getting DA Description from Row Number " + rowNumber + " : " + getText(daDescription));
        return getText(daDescription);
    }

    public String getBundleType(int rowNumber) {
        By bundleType = By.xpath(pageElements.valueRow + rowNumber + pageElements.bundleType);
        UtilsMethods.printInfoLog("Getting DA Bundle Type from Row Number " + rowNumber + " : " + getText(bundleType));
        return getText(bundleType);
    }

    public String getDABalance(int rowNumber) {
        By daBalance = By.xpath(pageElements.valueRow + rowNumber + pageElements.daBalance);
        UtilsMethods.printInfoLog("Getting DA Balance from Row Number " + rowNumber + " : " + getText(daBalance));
        return getText(daBalance);
    }

    public String getDADateTime(int rowNumber) {
        By dateTime = By.xpath(pageElements.valueRow + rowNumber + pageElements.daDateTime);
        UtilsMethods.printInfoLog("Getting DA Date and Time from Row Number " + rowNumber + " : " + getText(dateTime));
        return getText(dateTime);
    }


    public int getNumbersOfRows() {
        rows = returnListOfElement(pageElements.rows);
        return rows.size();
    }

    public WidgetInteractionPage clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteractionPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on DA History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + getText(pageElements.getTitle));
        return getText(pageElements.getTitle).toLowerCase();
    }

    public boolean isDAWidgetIsVisible() {
        UtilsMethods.printInfoLog("Checking is DA Widget Visible");
        return isElementVisible(pageElements.getTitle);
    }

    public String getAccumulatorHeaders(int row) {
        String header = getText(By.xpath(pageElements.accumulatorHeader + row + pageElements.headerValue));
        UtilsMethods.printInfoLog("Getting Accumulator header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAccumulator(int row, int column) {
        String value = getText(By.xpath(pageElements.accumulatorColumnHeader + row + pageElements.accumulatorColumnValue + column + pageElements.headerValue));
        UtilsMethods.printInfoLog("Reading '" + getAccumulatorHeaders(column) + "' = " + value);
        return value.trim();
    }
}
