package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.pagerepository.pageelements.AmSmsTrailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AmSmsTrails extends BasePage {
    AmSmsTrailsPage pageElements;

    public AmSmsTrails(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmSmsTrailsPage.class);
    }

    /**
     * This method is used to check AM Transactions Widget visible or not
     * @return
     */
    public boolean isAmTransactionsWidgetVisible() {
        boolean status = isElementVisible(pageElements.amTransactionsWidget);
        commonLib.info("Is AM Transactions Widget visible : " + status);
        return status;
    }

    /**
     * This method is used to check more icon visible or not
     * @return
     */
    public boolean isMoreIconVisible() {
        boolean status = isElementVisible(pageElements.moreIcon);
        commonLib.info("Is more icon  visible : " + status);
        return status;
    }

    /**
     * This method is used to check AM Profile Details widget  visible or not
     * @return
     */
    public boolean isAmProfileDetailsDetailWidgetVisible() {
        boolean status = isElementVisible(pageElements.amProfileDetailsWidget);
        commonLib.info("Is Am Profile Details Widget visible : " + status);
        return status;
    }

    /**
     * This method is used to click SMS Logs tab
     * @return
     */
    public void clickSmsLogs() {
        commonLib.info("Going to click SMS Logs tab");
        clickWithoutLoader(pageElements.smsLogs);
    }

    /**
     * This method is used to click on more icon
     *
     * @return
     */
    public void clickMoreIcon() {
        commonLib.info("Going to click detailed icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.moreIcon);
    }

    /**
     * This method is used to click date filter
     * @return
     */
    public void clickDateFilter() {
        commonLib.info("Going to click date filter");
        clickWithoutLoader(pageElements.dateFilter);
    }

    /**
     * This method is used to click cancel button of date filter
     * @return
     */
    public void clickCancelOfDate() {
        commonLib.info("Going to click cancel button of date filter");
        clickWithoutLoader(pageElements.cancelButton);
    }

    /**
     * This method is used to check Sms Logs tab visible or not
     * @return
     */
    public boolean isSmsLogVisible() {
        boolean status = isElementVisible(pageElements.smsLogs);
        commonLib.info("Is SMS LOGS tab visible : " + status);
        return status;
    }

    /**
     * This method is used to check Timestamp label visible or not
     * @return
     */
    public boolean isTimeStampVisible() {
        boolean status = isElementVisible(pageElements.timestamp);
        commonLib.info("Is Timestamp visible : " + status);
        return status;
    }

    /**
     * This method is used to check sms body visible or not
     * @return
     */
    public boolean isSmsBodyVisible() {
        boolean status = isElementVisible(pageElements.smsBody);
        commonLib.info("Is SMS Body visible : " + status);
        return status;
    }

    /**
     * This method is used to check Sms Id visible or not
     * @return
     */
    public boolean isSmsIdVisible() {
        boolean status = isElementVisible(pageElements.smsId);
        commonLib.info("Is SMS Id visible : " + status);
        return status;
    }

    /**
     * This method is used to check Transaction Id visible or not
     * @return
     */
    public boolean isTransactionIdVisible() {
        boolean status = isElementVisible(pageElements.transactionId);
        commonLib.info("Is Transaction Id visible : " + status);
        return status;
    }

    /**
     * This method is used to check date filter visible or not
     * @return
     */
    public boolean isDateFilterVisible() {
        boolean status = isElementVisible(pageElements.dateFilter);
        commonLib.info("Is date filter visible : " + status);
        return status;
    }

    /**
     * This method is used to check calendar visible or not
     * @return
     */
    public boolean isCalendarVisible() {
        boolean status = isElementVisible(pageElements.calendar);
        commonLib.info("Is Calendar visible after clicking date filter: " + status);
        return status;
    }

    /**
     * This method is used to get Footer auuid of SMS Logs Widget
     * @return
     */
    public String getFooterAuuid() {
        String result = getText(pageElements.footerAuuid);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is used to get Middle auuid of SMS Logs Widget
     * @return
     */
    public String getMiddleAuuid() {
        String result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is use to check no result found icon visible or not
     *
     * @return true/false
     */
    public boolean isNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.noResultFound);
        commonLib.info("Is error icon visible when there is no data available in SMS Trails Widget : " + visible);
        return visible;
    }

    /**
     * This method is used to check error message visible or not
     *
     * @return true/false
     */
    public Boolean isWidgetErrorMessageVisible() {
        Boolean status=(isElementVisible(pageElements.widgetErrorMessage) || isElementVisible(pageElements.unableToFetchDataMessage));
        commonLib.info("Is error message visible when there is widget error :" + status);
        return status;
    }

    /**
     * This method is used to get no result found message
     *
     * @return String The String
     */
    public String getNoResultFoundMessage() {
        final String text = getText(pageElements.noResultFoundMessage);
        commonLib.info("Getting error message when there is no data available in SMS Trails Widget : " + text);
        return text;
    }

    /**
     * This method is used get first header value based on passed row and column
     * @param row
     * @param column
     * @return
     */
    public String getRowValue(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.dataValue));
        commonLib.info("Reading Row(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used get Action value based on passed row and column
     * @param row
     * @param column
     * @return
     */
    public String getAction(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.actionValue));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used to get total no. of rows
     */
    public int getNoOfRows()
    {
        if(isVisibleContinueExecution(pageElements.totalRows))
        {
            List<WebElement> list= returnListOfElement(pageElements.totalRows);
            return list.size();
        }else {
            commonLib.warning("No Data is available under Sms Logs Widget");
            return 0;
        }
    }

    /**
     * This method is used to check whether pagination is displayed or not
     */
    public Boolean isPaginationAvailable() {
        commonLib.info("Checking pagination available or not in SMS Logs");
        return isElementVisible(pageElements.pagination);
    }

    /**
     * This method is used to check previous button in pagination is disabled or not
     */
    public Boolean checkPreviousBtnDisable() {
        commonLib.info("Checking pagination's previous button is disabled or not ");
        return isElementVisible((pageElements.previousBtnDisable));
    }

    /**
     * This method is used to check next button in pagination is enabled or not
     */
    public Boolean checkNextBtnEnable() {
        commonLib.info("Checking pagination's next button is disabled or not");
        return isElementVisible((pageElements.nextBtnEnable));
    }

    /**
     * This method is used to get pagination text display
     * Example : 1 - 6 of 10 Results
     */
    public String getPaginationText() {
        String value = getText(pageElements.paginationCount).trim();
        commonLib.info("Reading Pagination text " + value);
        return value;
    }

    /**
     * This method is used to click next button in pagination
     */
    public void clickNextBtn() {
        commonLib.info("Clicking Next button in pagination");
        clickWithoutLoader(pageElements.nextBtnEnable);
    }

    /**
     * This method is used to click previous button in pagination
     */
    public void clickPreviousBtn() {
        commonLib.info("Clicking Previous button in pagination");
        clickWithoutLoader(pageElements.previousBtnEnable);
    }
}
