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

    /**
     * This method is use to check widget error display or not
     * @return true/false
     */
    public boolean isAirtelMoneyErrorVisible() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyError);
        commonLib.info("Validating error is visible when there is Error in API : " + elementVisible);
        return elementVisible;
    }

    /**
     * This method is use to get no result found message
     * @return String The String
     */
    public String gettingAirtelMoneyNoResultFoundMessage() {
        final String text = getText(pageElements.airtelMoneyNoResultFoundMessage);
        commonLib.info("Validating error msg message when there is no data from API : " + text);
        return text;
    }

    /**
     * This method is use to check no result found icon visible or not
     * @return true/false
     */
    public boolean isAirtelMoneyNoResultFoundVisible() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyNoResultFound);
        commonLib.info("Validating 'No Result Found' msg is visible :- " + elementVisible);
        return elementVisible;
    }

    /**
     * This method use to get header name of AM Widget
     * @param column The column number
     * @return String The header name
     * */
    public String getHeaders(int column) {
        String header = readTextOnRows(pageElements.headerRows, column);
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value from AM widget based on row and column
     * @param row The row number
     * @param column The column number
     * @return String The  data value
     * */
    public String getHeaderValue(int row, int column) {
        String result;
        String attribute;
        result = getText(By.xpath(pageElements.dataRow + row + pageElements.valueColumns + column + pageElements.columnText));
        if (column == 1) {
            String sign = null;
            attribute = getAttribute(By.xpath(pageElements.dataRow + row + pageElements.amountSign + column + pageElements.amountImg), "src", false);
            if (attribute.contains("cr.svg"))
                sign = "+ ";
            else if (attribute.contains("dr.svg")) {
                sign = "- ";
            }
            result = sign + result;
        }
        if (column == 3) {
            result = result.replace("\n", " ");
        }
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
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
    This Method will give us footer auuid shown in AM widget
     */
    public String getFooterAuuidAM() {
        String result;
        result = getText(pageElements.footerAMAuuid);
        return result;
    }

    /*
    This Method will give us auuid shown in the middle of the AM modal
     */
    public String getMiddleAuuidAM() {
        String result;
        result = getAttribute(pageElements.middleAMAuuid, "data-auuid", false);
        return result;
    }

    /*
    This Method will get the text of the header
     */
    public String getAirtelMoneyTransactionHeaderText() {
        final String headerText = getText(pageElements.airtelMoneyTransactionHeader);
        commonLib.info("Airtel Money Widget Header Text is:- " + headerText);
        return headerText;
    }

    /**
     * This method is use to check date picker is visible or not
     * @return true/false
     */
    public boolean isAirtelMoneyWidgetDatePickerVisible() {
        final By datePicker = pageElements.airtelMoneyDatePicker;
        commonLib.info("Checking Airtel Money Widget, is Date Picker visible? " + datePicker);
        return isEnabled(datePicker);
    }

    /*
    This Method will give us airtel money currency
     */
    public String gettingAirtelMoneyCurrency() {
        String result;
        result = getText(pageElements.airtelMoneyCurrency);
        result = result.replaceAll("\\s.*", "");
        commonLib.info("Getting Airtel Money Currency from Widget : " + result);
        return result;
    }

    /**
     * This method is use to get the widget error message
     * @return String the value
     */
    public String gettingAMBalanceUnableToFetchMessage() {
        commonLib.info("Getting error Message for unable to fetch AM Money Balance : " + getText(pageElements.airtelMoneyCurrency));
        return getText(pageElements.airtelMoneyBalanceUnableToFetch);
    }

    /**
     * This method is use to check AM balance error visible when api not able to fetch balance
     * @return true/false
     */
    public boolean isAMBalanceUnableToFetch() {
        final boolean elementVisible = isElementVisible(pageElements.airtelMoneyBalanceUnableToFetch);
        commonLib.info("Is error visible on unable to fetch AM Money Balance : " + elementVisible);
        return elementVisible;
    }

    /**
     * This method is use to get airtel money balance
     * @return String The Value
     */
    public String gettingAirtelMoneyBalance() {
        final String text = getText(pageElements.airtelMoneyBalance);
        commonLib.info("Getting Airtel Money Balance from Widget : " + text);
        return text;
    }

    /**
     * This method is use to get secondary AM Wallet balance
     * @return String The String
     */
    public String gettingAirtelMoneyBalance2() {
        final String text = getText(pageElements.airtelMoneyBalance2);
        commonLib.info("Getting Airtel Money 2nd Balance from Widget : " + text);
        return text;
    }

    /**
     * This method is use to click ticket icon present on widget
     */
    public void clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on AM History Widget");
        }
    }

    /**
     * This method use to get title of widget
     * @return String The value
     */
    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        commonLib.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    /*
    This Method will tell us isResendSMS button is enabled or not
     */
    public Boolean isResendSMS(int row) {
        boolean result = false;
        final String attribute = getAttribute(By.xpath(pageElements.resendSMSIcon + row + pageElements.resendSMSIcon2), "class", false);
        commonLib.info(attribute);
        if (!attribute.contains("hide-reversal"))
            result = true;
        return result;
    }

    /**
     * This method use to click menu button
     */
    public void clickMenuOption() {
        commonLib.info("Clicking Menu Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.clickMenu);
    }

    /**
     * This method is use to check transaction search box visible or not
     * @return true/false
     */
    public Boolean isTransactionIdSearchBoxVisible() {
        final boolean enabled = isEnabled(pageElements.transactionId);
        commonLib.info("Checking is Transaction Id Search Box Visible?" + enabled);
        return enabled;
    }

    /**
     * This method is use to write transaction id into transaction id search box
     */
    public void writeTransactionId(String id) {
        enterText(pageElements.transactionId, id);
    }

    /**
     * This method is use to click search button
     */
    public void clickSearchBtn() {
        commonLib.info("Clicking on Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.transactionSearchBtn);
    }

    /**
     * This method used to double click on transaction id and return the transaction id
     * @param row The row number
     * @return String The value
     */
    public String doubleClickOnTransactionId(int row) {
        Actions act = new Actions(driver);
        By id = By.xpath(pageElements.dataRow + row + pageElements.transactionIdColumn);
        act.moveToElement(driver.findElement(id)).doubleClick().build().perform();
        return getText(id);
    }
}
