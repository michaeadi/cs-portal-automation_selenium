package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AMTxnWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

@Log4j2
public class AMTransactionsWidget extends BasePage {

    AMTxnWidgetPage pageElements;

    public AMTransactionsWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AMTxnWidgetPage.class);
    }

    public boolean isAirtelMoneyErrorVisible() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyError);
        commonLib.info("Validating error is visible when there is Error in API : " + elementVisible);
        return elementVisible;
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        final String text = getText(pageElements.airtelMoneyNoResultFoundMessage);
        commonLib.info("Validating error msg message when there is no data from API : " + text);
        return text;
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyNoResultFound);
        commonLib.info("Validating 'No Result Found' msg is visible :- " + elementVisible);
        return elementVisible;
    }

    public String getHeaders(int row) {
        String header = readTextOnRows(pageElements.headerRows, row);
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRows + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    /*
    This Method will let us know Airtel Money Widget is visible or not
     */
    public boolean isAirtelMoneyTransactionWidgetVisible() {
        boolean elementVisible = false;
        if (isVisible(pageElements.airtelMoneyTransactionHeader)) {
            elementVisible = isElementVisible(pageElements.airtelMoneyTransactionHeader);
            commonLib.info("Checking is Airtel Money Widget Visible? " + elementVisible);
        }
        return elementVisible;
    }

    /*
    This Method will get the text of the header
     */
    public String getAirtelMoneyTransactionHeaderText() {
        final String headerText = getText(pageElements.airtelMoneyTransactionHeader);
        commonLib.info("Airtel Money Widget Header Text is:- " + headerText);
        return headerText;
    }

    public boolean isAirtelMoneyWidgetDatePickerVisible() {
        final By datePicker = pageElements.airtelMoneyDatePicker;
        commonLib.info("Checking Airtel Money Widget, is Date Picker visible? " + datePicker);
        return isEnabled(datePicker);
    }

    public String gettingAirtelMoneyCurrency() {
        final String text = getText(pageElements.airtelMoneyCurrency);
        commonLib.info("Getting Airtel Money Currency from Widget : " + text);
        return text;
    }

    public String gettingAMBalanceUnableToFetchMessage() {
        commonLib.info("Getting error Message for unable to fetch AM Money Balance : " + getText(pageElements.airtelMoneyCurrency));
        return getText(pageElements.airtelMoneyBalanceUnableToFetch);
    }

    public boolean isAMBalanceUnableToFetch() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyBalanceUnableToFetch);
        commonLib.info("Is error visible on unable to fetch AM Money Balance : " + elementVisible);
        return elementVisible;
    }

    public String gettingAirtelMoneyBalance() {
        final String text = getText(pageElements.airtelMoneyBalance);
        commonLib.info("Getting Airtel Money Balance from Widget : " + text);
        return text;
    }

    public String gettingAirtelMoneyBalance2() {
        final String text = getText(pageElements.airtelMoneyBalance2);
        commonLib.info("Getting Airtel Money 2nd Balance from Widget : " + text);
        return text;
    }

    public WidgetInteraction clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteraction(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on AM History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    public Boolean isResendSMS() {
        return isEnabled(pageElements.resendSMSIcon);
    }

    public void clickMenuOption() {
        commonLib.info("Clicking Menu Option");
        click(pageElements.clickMenu);
    }

    public MoreAMTxnTab openAMHistoryTab() {
        commonLib.info("Opening AM History Sub Tab");
        click(pageElements.amHistoryTab);
        return new MoreAMTxnTab(driver);
    }

    public Boolean isTransactionIdSearchBoxVisible() {
        final boolean enabled = isEnabled(pageElements.transactionId);
        commonLib.info("Checking is Transaction Id Search Box Visible?" + enabled);
        return enabled;
    }

    public void writeTransactionId(String id) {
        enterText(pageElements.transactionId, id);
    }

    public void clickSearchBtn() {
        commonLib.info("Clicking on Search Button");
        click(pageElements.transactionSearchBtn);
    }

    public String doubleClickOnTransactionId(int row) {
        Actions act = new Actions(driver);
        By id = By.xpath(pageElements.valueRows + row + pageElements.transactionIdColumn);
        act.moveToElement(driver.findElement(id)).doubleClick().build().perform();
        return getText(id);
    }
}
