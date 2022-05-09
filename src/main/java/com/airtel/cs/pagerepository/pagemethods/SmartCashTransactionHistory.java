package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.SmartCashTransactionHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class SmartCashTransactionHistory extends BasePage {
    SmartCashTransactionHistoryPage pageElements;

    public SmartCashTransactionHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SmartCashTransactionHistoryPage.class);
    }

    /**
     * This method use to check SmartCash TransactionHistory widget visibility
     *
     * @return true/false
     */
    public boolean isSmartCashTransactionHistoryVisible() {
        boolean status = isEnabled(pageElements.smartCashTxnHistoryWidget);
        commonLib.info("Is SmartCash Transaction History visible: " + status);
        return status;
    }

    /**
     * This method use to check filter by today date display or not
     *
     * @return true/false
     */
    public boolean isTodayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.todayFilterSec);
        commonLib.info("Is Today Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last two day display or not
     *
     * @return true/false
     */
    public boolean isLastTwoDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lasTwoDaysSec);
        commonLib.info("Is Last Two day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check filter by last seven day display or not
     *
     * @return true/false
     */
    public boolean isLastSevenDayFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.lastSevenDaysSec);
        commonLib.info("Is Last Seven day Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check is date range filter display or not
     *
     * @return true/false
     */
    public boolean isDateRangeFilterTabOnSecondWidget() {
        boolean status = isEnabled(pageElements.dateRangeFilterSec);
        commonLib.info("Is Date Range Filter Radio button Display: " + status);
        return status;
    }

    /**
     * This method use to check negative sign display or not
     *
     * @param row The row number
     * @return true/false
     */
    public Boolean isNegSignDisplayOnSecondWidget(int row) {
        By negAmount = By.xpath(pageElements.valueRowSec + row + pageElements.negSymbol);
        return isEnabled(negAmount);
    }

    /**
     * This method use to check positive sign display or not
     *
     * @param row The row number
     * @return true/false
     */
    public Boolean isPosSignDisplayOnSecondWidget(int row) {
        By posAmount = By.xpath(pageElements.valueRowSec + row + pageElements.posSymbol);
        return isEnabled(posAmount);
    }

    /**
     * This method use to check AM menu widget display or not
     *
     * @return true/false
     */
    public boolean isAMMenuHistoryTabDisplayOnSecondWidget() {
        boolean status = isEnabled(pageElements.titleSec);
        commonLib.info("Is AM Menu History Tab Display: " + status);
        return status;
    }


    /**
     * This method use to check is transaction id box display or not
     *
     * @return true/false
     */
    public boolean isSearchTxnIdBoxOnSecondWidget() {
        boolean status = isEnabled(pageElements.searchTxnIdSec);
        commonLib.info("Is Search Transaction Id Box Display: " + status);
        return status;
    }

    /**
     * This method use to write transaction id into search box
     *
     * @param txnId The Transaction Id
     */
    public void searchByTxnIdOnSecondWidget(String txnId) {
        commonLib.info("Search By Transaction Id: " + txnId);
        enterText(pageElements.searchTxnIdSec, txnId);
    }

    /**
     * This method use to click search button
     */
    public void clickSearchBtnOnSecondWidget() {
        commonLib.info("Clicking Search button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtnSec);
    }

    /**
     * This method use to check airtel money error visible
     *
     * @return true/false
     */
    public boolean isAirtelMoneyErrorVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is Error inAPI : " + isElementVisible(pageElements.airtelMoneyError));
        return isElementVisible(pageElements.airtelMoneyErrorSec);
    }

    /**
     * This method use to get no result found message
     *
     * @return String The String
     */
    public String gettingAirtelMoneyNoResultFoundMessageOnSecondWidget() {
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + getText(pageElements.airtelMoneyNoResultFoundMessage));
        return getText(pageElements.airtelMoneyNoResultFoundMessageSec);
    }

    /**
     * This method use to get no result found icon display or not
     *
     * @return true/false
     */
    public boolean isAirtelMoneyNoResultFoundVisibleOnSecondWidget() {
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.airtelMoneyNoResultFound));
        return isElementVisible(pageElements.airtelMoneyNoResultFoundSec);
    }

    /**
     * This method use to get header name based on column number
     *
     * @param column The column number
     * @return String The Value
     */
    public String getHeadersOnSecondWidget(int column) {
        String header = getText(By.xpath(pageElements.headerRowSec + "[" + column + "]"));
        commonLib.info("Getting header Number " + column + " : " + header);
        return header;
    }

    /**
     * This method use to get data value based on row and column number
     *
     * @param row    The Row Number
     * @param column The column number
     * @return String The Value
     */
    public String getValueCorrespondingToHeader(int row, int column) {
        String value = getText(By.xpath(pageElements.valueRowSec + row + pageElements.valueColumns + column + pageElements.columnText));
        commonLib.info("Reading Value(" + row + "): " + value);
        return value;
    }

    /**
     * This method use to check resend sms icon display or not
     *
     * @return true/false
     */
    public Boolean isResendSMSIconVisible(int row, int column) {
        By check = By.xpath(pageElements.valueRowSec + row + pageElements.iconColumn + column + pageElements.resendSmsIcon);
        return isEnabled(check);
    }

    /**
     * This method use to check reversal sms icon display or not
     *
     * @return true/false
     */
    public Boolean isReversalIconVisible(int row, int column) {
        By check = By.xpath(pageElements.valueRowSec + row + pageElements.iconColumn + column + pageElements.reversalIcon);
        return isEnabled(check);
    }

    /**
     * This method use to get data value based on row and column number for Transaction Details
     *
     * @param row    The Row Number
     * @param column The column number
     * @return detail
     */
    public String getValueCorrespondingToTxnHeader(int row, int column) {
        String detail = getText(By.xpath(pageElements.detailsRow + row + pageElements.detailsColumn + column + pageElements.detailsColumnText));
        commonLib.info("Reading Value(" + row + "): " + detail);
        return detail;
    }

    /**
     * This Method is used to click on meta info drop down
     *
     * @param row the row for drop down
     */
    public void clickDropDownForMetaInfo(int row) {
        commonLib.info("Going to click on meta info drop down");
        clickWithoutLoader(By.xpath(pageElements.rowMetaInfo1 + row + pageElements.rowMetaInfo2));
    }

    /**
     * This method use to check SMS Notification icon display or not
     *
     * @return true/false
     */
    public Boolean isSmsNotificationIconVisible(int row, int column) {
        By check = By.xpath(pageElements.valueRowSec + row + pageElements.iconColumn + column + pageElements.resendSmsIcon);
        return isEnabled(check);
    }

    /**
     * This method is used to click SMS Notification Icon
     */

    public void clickSmsNotificationIcon(){
        commonLib.info("Going to click resend SMS ");
        clickWithoutLoader(pageElements.smsNotificationIcon);
    }

    /**
     * This method use to check Send SMS Notification Header is visible
     *
     * @return true/false
     */

    public String isSendNotificationHeaderVisible() {
        final String text = getText(pageElements.smsHeader);
        commonLib.info("Validating Send Notification Header is visible : " + text);
        return text;
    }


    /**
     * This method use to check Issue Detail text is displayed or not
     *
     * @return true/false
     */
    public String isIssueDetailVisible() {
        final String text = getText(pageElements.smsIssueDetail);
        commonLib.info("Validating Issue Detail is visible : " + text);
        return text;
    }

    /**
     * This method use to check Enter Comment is displayed or not
     *
     * @return true/false
     */
    public String isEnterCommentHeaderVisible() {
        final String text = getText(pageElements.enterComment);
        commonLib.info("Validating Enter Comment Header is visible : " + text);
        return text;
    }


    public String isSmsSelectReasonVisible() {
        final String text = getText(pageElements.smsSelectReason);
        commonLib.info("Validating Select Reason Label is visible : " + text);
        return text;
    }

    /**
     * This method is used to check next button in pagination is enabled or not
     */
    public Boolean isSubmitBtnDisabled() {
        commonLib.info("Checking Submit Btn button is disabled or not");
        return isElementVisible((pageElements.submitSms));
    }


    public Boolean isCancelButtonVisible() {
        commonLib.info("Checking Cancel Btn is enabled");
        return isEnabled((pageElements.cancelSms));
    }

    /**
     * This method is used to  Perform SMS Notification by selecting reason and comment
     */
    public void performSmsNotification() {
        commonLib.info("Going to perform Bar/unbar Action");
        pages.getSmartCashTransactionHistory().clickOnSmsSelectReason();
        pages.getSmartCashTransactionHistory().selectRequestFromDropdown();
        pages.getSmartCashTransactionHistory().selectDidNotGetSmsFromDropdown();
        pages.getSmartCashTransactionHistory().selectDeletedTheSmsFromDropdown();
        pages.getSmartCashTransactionHistory().enterComment(COMMENT);
        pages.getSmartCashTransactionHistory().clickOnSubmitButton();
    }

    /**
     * This method is used to go to Action Trail tab
     */
    public void goToActionTrail() {
        commonLib.info("Going to click Action Trail tab");
        pages.getPinReset().clickViewHistoryTab();
        pages.getPinReset().clickActionTrailTab();
    }

    /**
     * This method is used to click on select reason
     */
    public void clickOnSmsSelectReason() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectArrow)) ;
        clickWithoutLoader((pageElements.selectArrow));
    }

    /**
     * This method is used to click on select Customer Request
     */
    public void selectRequestFromDropdown() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectCustomerRequestFromDropdown)) ;
        clickWithoutLoader((pageElements.selectCustomerRequestFromDropdown));
    }

    /**
     * This method is used to click on select Customer did not get SMS
     */
    public void selectDidNotGetSmsFromDropdown() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectDidNotGetSmsFromDropdown)) ;
        clickWithoutLoader((pageElements.selectDidNotGetSmsFromDropdown));
    }

    /**
     * This method is used to click on select Customer deleted the SMS
     */
    public void selectDeletedTheSmsFromDropdown() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectDeletedTheSmsFromDropdown)) ;
        clickWithoutLoader((pageElements.selectDeletedTheSmsFromDropdown));
    }


    /**
     * This method is used to write the comment into comment box
     *
     * @param text The comment
     */
    public void enterComment(String text) {
        commonLib.info("Writing comment into comment box: " + text);
        enterText(pageElements.smsTextArea, text);
    }

    /**
     * This method is used to click on Submit button
     */
    public void clickOnSubmitButton() {
        commonLib.info("Going to click submit button");
        if (isVisible(pageElements.submitSms))
            clickWithoutLoader(pageElements.submitSms);

    }

    /**
     * This method is used to check demographic widget visible after closing pop up
     *
     * @return true/false
     */
    public Boolean isSuccessPopUpVisible() {
        final boolean state = isElementVisible(pageElements.demographics);
        commonLib.info("Is demographic widget visible" + state);
        return state;

    }

    /**
     * This method is used to get text of success message
     *
     * @return text
     */
    public String getSuccessText() {
        final String text = getText(pageElements.successMessage);
        commonLib.info("Getting success pop up text :" + text);
        return text;
    }

    /**
     * This method is used to click on cross icon of success pop up
     */
    public void clickCrossIcon() {
        commonLib.info("Going to click cross icon");
        if (isVisible(pageElements.crossIcon));
        clickWithoutLoader((pageElements.crossIcon));
    }

    /**
     * This method is used to get action type form action trail
     *
     * @return
     */
    public String getActionType() {
        commonLib.info(getText(pageElements.actionType));
        return getText(pageElements.actionType);
    }

    /**
     * This method is used to get reason form action trail
     *
     * @return
     */
    public String getReason() {
        commonLib.info(getText(pageElements.reason));
        return getText(pageElements.reason);
    }

    /**
     * This method is used to get comment form action trail
     *
     * @return
     */
    public String getComment() {
        commonLib.info(getText(pageElements.comment));
        return getText(pageElements.comment);
    }


}
