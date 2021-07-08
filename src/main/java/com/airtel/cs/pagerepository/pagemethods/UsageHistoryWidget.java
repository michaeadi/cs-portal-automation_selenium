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
    }

    /**
     * This method is used to check usage history widget error display or noy
     *
     * @return true/false
     */
    public boolean isUsageHistoryErrorVisible() {
        final boolean visible = isElementVisible(pageElements.usageHistoryError);
        commonLib.info("Validating error is visible when there is Error in com.airtel.cs.API : " + visible);
        return visible;
    }

    /**
     * This method use to get header name based on column number
     *
     * @param column The column number
     * @return String The value
     */
    public String getHeaders(int column) {
        String header = getText(By.xpath(pageElements.headerRow + column + pageElements.headerText));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method is use to get no result found message
     *
     * @return String The String
     */
    public String gettingUsageHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.usageHistoryNoResultFoundMessage);
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }

    /**
     * This method is use to check no result found icon visible or not
     *
     * @return true/false
     */
    public boolean isUsageHistoryNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.usageHistoryNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + visible);
        return visible;
    }


    /**
     * This method is use to get number of data rows display on UI
     *
     * @return Integer the count
     */
    public int getNumberOfRows() {
        try {
            if (isVisibleContinueExecution(pageElements.rows)) {
                as = returnListOfElement(pageElements.rows);
                return as.size();
            }
        } catch (Exception e) {
            commonLib.warning("No Data is available under Usage History Widget over CS Portal");
        }
        return 0;
    }

    /**
     * This method is use to check widget menu icon visible or not
     *
     * @return true/false
     */
    public boolean isUsageHistoryWidgetMenuVisible() {
        commonLib.info("Checking is Usage History's Menu Visible");
        return isElementVisible(pageElements.menu);
    }

    /**
     * This method use to click menu button for opening sub tab
     */
    public void openingMoreDetails() {
        commonLib.info("Opening More under Usage History Widget");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }

    /*
    This Method will give us the header value
     */
    public String getHeaderValue(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRow + row + pageElements.valueColumns + column + "]"));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method use to check widget display or not
     *
     * @return true/false
     */
    public boolean isUsageHistoryWidgetIsVisible() {
        commonLib.info("Checking is Usage History Widget Visible");
        return isElementVisible(pageElements.usageHistoryHeader);
    }

    /**
     * This method use to check date picker display or not
     *
     * @return true/false
     */
    public boolean isUsageHistoryDatePickerVisible() {
        commonLib.info("Checking Usage HistoryWidget Date Picker Visibility ");
        return isEnabled(pageElements.usageHistoryDatePicker);
    }

    /**
     * This method is use to click ticket icon
     */
    public void clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Usage History Widget");
        }
    }

    /**
     * This method is use to get widget name
     *
     * @return String The value
     */
    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        commonLib.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    /*
       This Method will give us footer auuid shown in UHW widget
       UHW = Usage History Widget
        */
    public String getFooterAuuidUHW() {
        return getText(pageElements.footerUHWAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the UHW modal
    UHW = Usage History Widget
     */
    public String getMiddleAuuidUHW() {
        String result;
        result = getAttribute(pageElements.middleUHWAuuid, "data-auuid", false);
        return result;
    }

    /*
   This Method will get the text of the usage history widget header
    */
    public String getUsageHistoryHeaderText() {
        final String headerText = getText(pageElements.usageHistoryHeader);
        commonLib.info("Usage History Widget Header Text is:- " + headerText);
        return headerText;
    }


    public String getHistoryDateTime(int rowNumber) {
        By dateTime = By.xpath(pageElements.dataRow + rowNumber + pageElements.valueColumns + "3]");
        final String text = getText(dateTime);
        commonLib.info("Getting Usage History Date Time from Row Number " + rowNumber + " : " + text);
        return text;
    }
}
