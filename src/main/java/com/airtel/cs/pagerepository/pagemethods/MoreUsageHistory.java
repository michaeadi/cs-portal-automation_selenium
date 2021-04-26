package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MoreUsageHistoryPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class MoreUsageHistory extends BasePage {

    MoreUsageHistoryPage pageElements;
    List<WebElement> smsHistoryRowsElements = returnListOfElement(pageElements.smsHistoryRows);
    List<WebElement> callHistoryRowsElements = returnListOfElement(pageElements.callHistoryRows);
    List<WebElement> dataHistoryRowsElements = returnListOfElement(pageElements.dataHistoryRows);
    private static final String TEXT1 = "Validating error message when there is no data from com.airtel.cs.API : ";
    private static final String TEXT2 = "Getting header Number ";
    private static final String TEXT3 = "Getting Widget title: ";

    public MoreUsageHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MoreUsageHistoryPage.class);
    }

    public String gettingCallHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.callHistoryNoResultFoundMessage);
        commonLib.info(TEXT1 + text);
        return text;
    }

    public boolean isCallHistoryNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.callHistoryNoResultFound);
        commonLib.info(TEXT1 + elementVisible);
        return elementVisible;
    }

    public String getCallHistoryHeader(int row) {
        String header = getText(By.xpath("//span[contains(text(),\"Call History \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        commonLib.info(TEXT2 + row + " : " + header);
        return header;
    }

    public String getDataHistoryHeader(int row) {
        String header = getText(By.xpath("//span[contains(text(),\"Data History \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        commonLib.info(TEXT2 + row + " : " + header);
        return header;
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath("//span[contains(text(),\"Usage History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        commonLib.info(TEXT2 + row + " : " + header);
        return header;
    }

    public String getSMSHistoryHeader(int row) {
        String header = getText(By.xpath("//span[contains(text(),\"SMS History \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        commonLib.info(TEXT2 + row + " : " + header);
        return header;
    }

    public String gettingSMSHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.smsHistoryNoResultFoundMessage);
        commonLib.info(TEXT1 + text);
        return text;
    }

    public boolean isSMSHistoryNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.smsHistoryNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + elementVisible);
        return elementVisible;
    }

    public String gettingDataHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.dataHistoryNoResultFoundMessage);
        commonLib.info(TEXT1 + text);
        return text;
    }

    public boolean isDataHistoryNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.dataHistoryNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + elementVisible);
        return elementVisible;
    }


    public String getSmsBundleName(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.bundleName).getText();
        commonLib.info("Getting SMS Bundle Name from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getCallBundleName(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.bundleName).getText();
        commonLib.info("Getting Call Bundle Name from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDataBundleName(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.bundleName).getText();
        commonLib.info("Getting Data Bundle Name from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDataTransactionNumber(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.transactionNumber).getText();
        commonLib.info("Getting Data Transaction Number from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getCallTransactionNumber(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.transactionNumber).getText();
        commonLib.info("Getting Call Transaction Number from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getSMSTransactionNumber(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.transactionNumber).getText();
        commonLib.info("Getting SMS Transaction Number from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getSMSDateTime(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.dateTime).getText();
        commonLib.info("Getting SMS Date and Time from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getCallDateTime(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.dateTime).getText();
        commonLib.info("Getting SMS Date and Time from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDataDateTime(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.dateTime).getText();
        commonLib.info("Getting Data Date and Time from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getSMSTo(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.smsTo).getText();
        commonLib.info("Getting SMS To from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getSMSCharges(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.smsCharges).getText();
        commonLib.info("Getting SMS Charges from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getCallCharges(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.callCharges).getText();
        commonLib.info("Getting Call Charges from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getDataCharges(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.dataCharges).getText();
        commonLib.info("Getting Data Charges from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getCallTo(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.callTo).getText();
        commonLib.info("Getting Call To from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getUsedData(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.usedData).getText();
        commonLib.info("Getting Used Data To from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getCallDuration(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        final String text = rowElement.findElement(pageElements.callDuration).getText();
        commonLib.info("Getting Used Data To from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public boolean isSMSDatePickerVisible() {
        commonLib.info("Checking is SMS DatePicker is Enabled");
        return checkState(pageElements.smsDatePicker);
    }

    public boolean isDataDatePickerVisible() {
        commonLib.info("Checking is Data DatePicker is Enabled");
        return checkState(pageElements.dataDatePicker);
    }

    public boolean isCallDatePickerVisible() {
        commonLib.info("Checking is Call DatePicker is Enabled");
        return checkState(pageElements.callDatePicker);
    }

    public int getNumbersOfSMSRows() {
        return smsHistoryRowsElements.size();
    }

    public int getNumbersOfDataRows() {
        return dataHistoryRowsElements.size();
    }

    public int getNumbersOfCallRows() {
        return callHistoryRowsElements.size();
    }

    public String getSMSWidgetTitle() {
        final String text = getText(pageElements.getSMSWidgetTitle);
        log.info(TEXT3 + text);
        return text.toLowerCase();
    }

    public String getCallWidgetTitle() {
        final String text = getText(pageElements.getCallWidgetTitle);
        log.info(TEXT3 + text);
        return text.toLowerCase();
    }

    public String getDataWidgetTitle() {
        final String text = getText(pageElements.getDataWidgetTitle);
        log.info(TEXT3 + text);
        return text.toLowerCase();
    }

    public WidgetInteraction clickSMSTicketIcon() {
        commonLib.info("Clicking on SMS Ticket Icon");
        click(pageElements.ticketSMSIcon);
        return new WidgetInteraction(driver);
    }

    public WidgetInteraction clickDataTicketIcon() {
        commonLib.info("Clicking on Data Ticket Icon");
        click(pageElements.ticketDataIcon);
        return new WidgetInteraction(driver);
    }

    public WidgetInteraction clickCallTicketIcon() {
        commonLib.info("Clicking on Call Ticket Icon");
        click(pageElements.ticketCallIcon);
        return new WidgetInteraction(driver);
    }
}
