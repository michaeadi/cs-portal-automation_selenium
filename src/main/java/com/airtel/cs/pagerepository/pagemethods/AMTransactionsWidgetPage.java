package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.AMTxnWidgetPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

@Log4j2
public class AMTransactionsWidgetPage extends BasePage {

    AMTxnWidgetPageElements pageElements;

    public AMTransactionsWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AMTxnWidgetPageElements.class);
    }

    public boolean isAirtelMoneyErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyError);
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.airtelMoneyNoResultFoundMessage));
        return readText(pageElements.airtelMoneyNoResultFoundMessage);
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFound);
    }

    public String getHeaders(int row) {
        String header = readTextOnRows(pageElements.headerRows, row);
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row, int column) {
        String value = readText(By.xpath(pageElements.valueRows + row + pageElements.valueColumns + column + pageElements.columnText));

        UtilsMethods.printInfoLog("Reading Value(" + row + "): " + value);
        return value;
    }

    public boolean isAirtelMoneyTransactionWidgetIsVisible() {
        UtilsMethods.printInfoLog("Checking is Airtel Money Widget Visible");
        return isElementVisible(pageElements.airtelMoneyTransactionHeader);
    }

    public boolean isAirtelMoneyWidgetDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking Airtel Money Widget Date Picker Visibility ");
        return checkState(pageElements.airtelMoneyDatePicker);
    }

    public String gettingAirtelMoneyCurrency() {
        UtilsMethods.printInfoLog("Getting Airtel Money Currency from Widget : " + readText(pageElements.airtelMoneyCurrency));
        return readText(pageElements.airtelMoneyCurrency);
    }

    public String gettingAMBalanceUnableToFetchMessage() {
        UtilsMethods.printInfoLog("Getting error Message for unable to fetch AM Money Balance : " + readText(pageElements.airtelMoneyCurrency));
        return readText(pageElements.airtelMoneyBalanceUnableToFetch);
    }

    public boolean isAMBalanceUnableToFetch() {
        UtilsMethods.printInfoLog("Is error visible on unable to fetch AM Money Balance : " + isElementVisible(pageElements.airtelMoneyBalanceUnableToFetch));
        return isElementVisible(pageElements.airtelMoneyBalanceUnableToFetch);
    }

    public double gettingAirtelMoneyBalance() {
        UtilsMethods.printInfoLog("Getting Airtel Money Balance from Widget : " + readText(pageElements.airtelMoneyBalance));
        return Double.parseDouble(readText(pageElements.airtelMoneyBalance));
    }

    public double gettingAirtelMoneyBalance2() {
        UtilsMethods.printInfoLog("Getting Airtel Money 2nd Balance from Widget : " + readText(pageElements.airtelMoneyBalance2));
        return Double.parseDouble(readText(pageElements.airtelMoneyBalance2));
    }

    public WidgetInteractionPage clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteractionPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on AM History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(pageElements.getTitle));
        return readText(pageElements.getTitle).toLowerCase();
    }

    public Boolean isResendSMS() {
        return checkState(pageElements.resendSMSIcon);
    }

    public void clickMenuOption() {
        UtilsMethods.printInfoLog("Clicking Menu Option");
        click(pageElements.clickMenu);
    }

    public MoreAMTxnTabPage openAMHistoryTab() {
        UtilsMethods.printInfoLog("Opening AM History Sub Tab");
        click(pageElements.amHistoryTab);
        return new MoreAMTxnTabPage(driver);
    }

    public Boolean isTransactionId() {
        UtilsMethods.printInfoLog("Checking is Transaction Id Box Displayed");
        return checkState(pageElements.transactionId);
    }

    public void writeTransactionId(String id) {
        writeText(pageElements.transactionId, id);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking on Search Button");
        click(pageElements.transactionSearchBtn);
    }

    public String doubleClickOnTransactionId(int row) {
        Actions act = new Actions(driver);
        By id = By.xpath(pageElements.valueRows + row + pageElements.transactionIdColumn);
        act.moveToElement(driver.findElement(id)).doubleClick().build().perform();
        return readText(id);
    }
}
