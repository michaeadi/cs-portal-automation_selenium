package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.MoreUsageHistoryPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class MoreUsageHistoryPage extends BasePage {

    MoreUsageHistoryPageElements pageElements;
    List<WebElement> smsHistoryRowsElements = returnListOfElement(pageElements.smsHistoryRows);
    List<WebElement> callHistoryRowsElements = returnListOfElement(pageElements.callHistoryRows);
    List<WebElement> dataHistoryRowsElements = returnListOfElement(pageElements.dataHistoryRows);

    public MoreUsageHistoryPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MoreUsageHistoryPageElements.class);
    }

    public String gettingCallHistoryNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.callHistoryNoResultFoundMessage));
        return readText(pageElements.callHistoryNoResultFoundMessage);
    }

    public boolean isCallHistoryNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.callHistoryNoResultFound));
        return isElementVisible(pageElements.callHistoryNoResultFound);
    }

    public String getCallHistoryHeader(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Call History \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getDataHistoryHeader(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Data History \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Usage History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getSMSHistoryHeader(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"SMS History \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingSMSHistoryNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.smsHistoryNoResultFoundMessage));
        return readText(pageElements.smsHistoryNoResultFoundMessage);
    }

    public boolean isSMSHistoryNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.smsHistoryNoResultFound));
        return isElementVisible(pageElements.smsHistoryNoResultFound);
    }

    public String gettingDataHistoryNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.dataHistoryNoResultFoundMessage));
        return readText(pageElements.dataHistoryNoResultFoundMessage);
    }

    public boolean isDataHistoryNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.dataHistoryNoResultFound));
        return isElementVisible(pageElements.dataHistoryNoResultFound);
    }


    public String getSmsBundleName(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting SMS Bundle Name from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.bundleName).getText());
        return rowElement.findElement(pageElements.bundleName).getText();
    }

    public String getCallBundleName(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Call Bundle Name from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.bundleName).getText());
        return rowElement.findElement(pageElements.bundleName).getText();
    }

    public String getDataBundleName(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Data Bundle Name from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.bundleName).getText());
        return rowElement.findElement(pageElements.bundleName).getText();
    }

    public String getDataTransactionNumber(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Data Transaction Number from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.transactionNumber).getText());
        return rowElement.findElement(pageElements.transactionNumber).getText();
    }

    public String getCallTransactionNumber(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Call Transaction Number from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.transactionNumber).getText());
        return rowElement.findElement(pageElements.transactionNumber).getText();
    }

    public String getSMSTransactionNumber(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting SMS Transaction Number from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.transactionNumber).getText());
        return rowElement.findElement(pageElements.transactionNumber).getText();
    }

    public String getSMSDateTime(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting SMS Date and Time from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.dateTime).getText());
        return rowElement.findElement(pageElements.dateTime).getText();
    }

    public String getCallDateTime(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting SMS Date and Time from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.dateTime).getText());
        return rowElement.findElement(pageElements.dateTime).getText();
    }

    public String getDataDateTime(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Data Date and Time from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.dateTime).getText());
        return rowElement.findElement(pageElements.dateTime).getText();
    }

    public String getSMSTo(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting SMS To from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.smsTo).getText());
        return rowElement.findElement(pageElements.smsTo).getText();
    }

    public String getSMSCharges(int rowNumber) {
        WebElement rowElement = smsHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting SMS Charges from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.smsCharges).getText());
        return rowElement.findElement(pageElements.smsCharges).getText();
    }

    public String getCallCharges(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Call Charges from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.callCharges).getText());
        return rowElement.findElement(pageElements.callCharges).getText();
    }

    public String getDataCharges(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Data Charges from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.dataCharges).getText());
        return rowElement.findElement(pageElements.dataCharges).getText();
    }

    public String getCallTo(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Call To from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.callTo).getText());
        return rowElement.findElement(pageElements.callTo).getText();
    }

    public String getUsedData(int rowNumber) {
        WebElement rowElement = dataHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Used Data To from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.usedData).getText());
        return rowElement.findElement(pageElements.usedData).getText();
    }

    public String getCallDuration(int rowNumber) {
        WebElement rowElement = callHistoryRowsElements.get(rowNumber);
        UtilsMethods.printInfoLog("Getting Used Data To from Row Number " + rowNumber + " : " + rowElement.findElement(pageElements.callDuration).getText());
        return rowElement.findElement(pageElements.callDuration).getText();
    }

    public boolean isSMSDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking is SMS DatePicker is Enabled");
        return checkState(pageElements.smsDatePicker);
    }

    public boolean isDataDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking is Data DatePicker is Enabled");
        return checkState(pageElements.dataDatePicker);
    }

    public boolean isCallDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking is Call DatePicker is Enabled");
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
        log.info("Getting Widget title: " + readText(pageElements.getSMSWidgetTitle));
        return readText(pageElements.getSMSWidgetTitle).toLowerCase();
    }

    public String getCallWidgetTitle() {
        log.info("Getting Widget title: " + readText(pageElements.getCallWidgetTitle));
        return readText(pageElements.getCallWidgetTitle).toLowerCase();
    }

    public String getDataWidgetTitle() {
        log.info("Getting Widget title: " + readText(pageElements.getDataWidgetTitle));
        return readText(pageElements.getDataWidgetTitle).toLowerCase();
    }

    public WidgetInteractionPage clickSMSTicketIcon() {
        UtilsMethods.printInfoLog("Clicking on SMS Ticket Icon");
        click(pageElements.ticketSMSIcon);
        return new WidgetInteractionPage(driver);
    }

    public WidgetInteractionPage clickDataTicketIcon() {
        UtilsMethods.printInfoLog("Clicking on Data Ticket Icon");
        click(pageElements.ticketDataIcon);
        return new WidgetInteractionPage(driver);
    }

    public WidgetInteractionPage clickCallTicketIcon() {
        UtilsMethods.printInfoLog("Clicking on Call Ticket Icon");
        click(pageElements.ticketCallIcon);
        return new WidgetInteractionPage(driver);
    }
}
