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
     * This method use to get data value from AM widget based on row and column
     * @param row    The row number
     * @param column The column number
     * @return String The  data value
     */
    public String getHeaderValue(int row, int column) {
        String result;
        String attribute;
        result = widgetMethods.getColumnValue(getAMWidgetId(), row, column);
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
     *
     * @return String The Value
     */
    public String gettingAirtelMoneyBalance() {
        final String text = getText(pageElements.airtelMoneyBalance);
        commonLib.info("Getting Airtel Money Balance from Widget : " + text);
        return text;
    }

    /**
     * This method is use to get secondary AM wallet balance
     *
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
     *
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
     * This method is use to double click on transaction id
     * @param row The row Number
     * @return String The value
     */
    public String doubleClickOnTransactionId(int row) {
        Actions act = new Actions(driver);
        By id = By.xpath(pageElements.dataRow + row + pageElements.transactionIdColumn);
        act.moveToElement(driver.findElement(id)).doubleClick().build().perform();
        return getText(id);
    }

    /**
     * This method is use to get Airtel Money transaction widget unique identifier
     * @return String The Value
     */
    public String getAMWidgetId() {
        return pageElements.amWidgetId;
    }

    /**
     * This Method will tell us reverse icon button is enabled or not
     */
    public Boolean isReverseIconEnable(int row) {
        if (!isElementVisible(By.xpath(pageElements.reversalIcon + row + pageElements.reversalIcon2))) {
            return true;
        } else {
            commonLib.info("Reverse Transaction Icon not present");
        }
        return false;
    }

}
