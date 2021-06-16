package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.RechargeHistoryWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class RechargeHistoryWidget extends BasePage {

    RechargeHistoryWidgetPage pageElements;
    List<WebElement> as;

    public RechargeHistoryWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, RechargeHistoryWidgetPage.class);
    }

    /**
     * This method use to check widget error display or not
     * @return true/false
     */
    public boolean isRechargeHistoryErrorVisible() {
        final boolean visible = isElementVisible(pageElements.rechargeHistoryError);
        commonLib.info("Validating error is visible when there is Error in com.airtel.cs.API : " + visible);
        return visible;
    }

    /**
     * This method use to get header name based on column number
     * @param column The column number
     * @return String The value
     */
    public String getHeaders(int column) {
        String header = getText(By.xpath(pageElements.headerRow + column + pageElements.headerName));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get sub header name based on column number
     * @param column The column number
     * @return String The value
     */
    public String getSubHeaders(int column) {
        String header = getText(By.xpath(pageElements.headerRow + column + pageElements.subHeaderName));
        commonLib.info("Getting Sub Header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method is use to get no result found message
     * @return String The String
     */
    public String gettingRechargeHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.rechargeHistoryNoResultFoundMessage);
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }

    /**
     * This method is use to check no result found icon visible or not
     * @return true/false
     */
    public boolean isRechargeHistoryNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.rechargeHistoryNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + visible);
        return visible;
    }


    /**
     * This method is use to get number of data rows display on UI
     * @return Integer the count
     */
    public int getNumberOfRows() {
        as = returnListOfElement(pageElements.rows);
        return as.size();
    }

    /**
     * This method is use to check widget menu icon visible or not
     * @return true/false
     */
    public boolean isRechargeHistoryWidgetMenuVisible() {
        commonLib.info("Checking is Recharge History's Menu Visible");
        return isElementVisible(pageElements.menu);
    }

    /**
     * This method use to click menu button for opening sub tab
     */
    public void openingRechargeHistoryDetails() {
        commonLib.info("Opening RechargeHistory under Recharge History Widget");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }

    /*
   This Method will give us the header value
    */
    public String getHeaderValue(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRow + row + pageElements.valueColumns + column +pageElements.columnValue));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method use to check widget display or not
     * @return true/false
     */
    public boolean isRechargeHistoryWidgetIsVisible() {
        commonLib.info("Checking is Recharge History Widget Visible");
        return isElementVisible(pageElements.rechargeHistoryHeader);
    }

    /*
    This Method will let us know is Recharge History Widget Loaded Successfully or not
     */
    public boolean isRechargeHistoryWidgetLoaded() {
        boolean result = false;
        if (isElementVisible(pageElements.widgetLoader)) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.widgetLoader));
            result = true;
        }
        return result;
    }

    /**
     * This method use to check date picker display or not
     * @return true/false
     */
    public boolean isRechargeHistoryDatePickerVisible() {
        commonLib.info("Checking Recharge HistoryWidget Date Picker Visibility ");
        return isEnabled(pageElements.rechargeHistoryDatePicker);
    }

    /**
     * This method is use to click ticket icon
     */
    public void clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Recharge History Widget");
        }
    }

    /**
     * This method is use to get widget name
     * @return String The value
     */
    public String getWidgetTitle() {
        final String text = getText(pageElements.rechargeHistoryHeader);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    /**
     * This method is use to write voucher id in voucher id search box
     * @param id The voucher id
     * @throws InterruptedException in-case scroll interrupt
     */
    public void writeVoucherId(String id) throws InterruptedException {
        commonLib.info("Writing voucher id in search box: " + id);
        scrollToViewElement(pageElements.voucherBox);
        enterText(pageElements.voucherBox, id);
    }

    /**
     * This method is use to click search button
     */
    public void clickSearchBtn() {
        commonLib.info("Clicking Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.voucherBtn);
    }

    /**
     * This method is use to check refill icon disable or not
     * @return true/false
     */
    public Boolean isRefillIconDisable() {
        final boolean state = isEnabled(pageElements.refillIconDisable);
        commonLib.info("Checking Clear refill icon disable :" + state);
        return state;
    }

    /**
     * This method is use to check refill icon enable or not
     * @return true/false
     */
    public Boolean isRefillIconEnable() {
        final boolean state = isEnabled(pageElements.refillIconClickable);
        commonLib.info("Checking Clear refill icon enable :" + state);
        return state;
    }

    /**
     * This method use to click clear refill icon
     */
    public void clickRefillIcon() {
        commonLib.info("Clicking Clear refill icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.refillIconClickable);
    }

    /**
     * This method is use to check clear refill pop up display or not
     * @return true/false
     */
    public boolean checkPopDisplay() {
        commonLib.info("Reading Pop up title: " + getText(pageElements.popUpMessage));
        return isEnabled(pageElements.popUpMessage);
    }

    /**
     * This method is use to click no button
     */
    public void clickNoBtn() {
        commonLib.info("Clicking No Button ");
        clickAndWaitForLoaderToBeRemoved(pageElements.noActionBtn);
    }

    /*
       This Method will give us footer auuid shown in RCW widget
       RHW = Recharge History Widget
        */
    public String getFooterAuuidRHW() {
       return getText(pageElements.footerRHWAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the RCW widget
    RHW = Recharge History Widget
     */
    public String getMiddleAuuidRHW() {
        String result;
        result = getAttribute(pageElements.middleRHWAuuid, "data-auuid", false);
        return result;
    }
}
