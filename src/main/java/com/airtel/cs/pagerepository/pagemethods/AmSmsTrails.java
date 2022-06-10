package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.model.cs.response.am.SmsLogsResponse;
import com.airtel.cs.pagerepository.pageelements.AmSmsTrailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class AmSmsTrails extends BasePage {
    AmSmsTrailsPage pageElements;

    public AmSmsTrails(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmSmsTrailsPage.class);
    }

    /**
     * This method is used to check AM Transactions Widget visible or not
     *
     * @return
     */
    public boolean isAmTransactionsWidgetVisible() {
        boolean status = isElementVisible(pageElements.amTransactionsWidget);
        commonLib.info("Is AM Transactions Widget visible : " + status);
        return status;
    }

    /**
     * This method is used to check more icon visible or not
     *
     * @return
     */
    public boolean isMoreIconVisible() {
        boolean status = isElementVisible(pageElements.moreIcon);
        commonLib.info("Is more icon  visible : " + status);
        return status;
    }

    /**
     * This method is used to check AM Profile Details widget  visible or not
     *
     * @return
     */
    public boolean isAmProfileDetailsDetailWidgetVisible() {
        boolean status = isElementVisible(pageElements.amProfileDetailsWidget);
        commonLib.info("Is Am Profile Details Widget visible : " + status);
        return status;
    }

    /**
     * This method is used to click SMS Logs tab
     *
     * @return
     */
    public void clickSmsLogs() {
        commonLib.info("Going to click SMS Logs tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.smsLogs);
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
     *
     * @return
     */
    public void clickDateFilter() {
        commonLib.info("Going to click date filter");
        clickWithoutLoader(pageElements.dateFilter);
    }

    /**
     * This method is used to click cancel button of date filter
     *
     * @return
     */
    public void clickCancelOfDate() {
        commonLib.info("Going to click cancel button of date filter");
        clickWithoutLoader(pageElements.cancelButton);
    }

    /**
     * This method is used to check Sms Logs tab visible or not
     *
     * @return
     */
    public boolean isSmsLogVisible() {
        boolean status = isElementVisible(pageElements.smsLogs);
        commonLib.info("Is SMS LOGS tab visible : " + status);
        return status;
    }

    /**
     * This method is used to check Timestamp label visible or not
     *
     * @return
     */
    public boolean isTimeStampVisible() {
        boolean status = isElementVisible(pageElements.timestamp);
        commonLib.info("Is Timestamp visible : " + status);
        return status;
    }

    /**
     * This method is used to check sms body visible or not
     *
     * @return
     */
    public boolean isSmsBodyVisible() {
        boolean status = isElementVisible(pageElements.smsBody);
        commonLib.info("Is SMS Body visible : " + status);
        return status;
    }

    /**
     * This method is used to check Sms Id visible or not
     *
     * @return
     */
    public boolean isSmsIdVisible() {
        boolean status = isElementVisible(pageElements.smsId);
        commonLib.info("Is SMS Id visible : " + status);
        return status;
    }

    /**
     * This method is used to check Transaction Id visible or not
     *
     * @return
     */
    public boolean isTransactionIdVisible() {
        boolean status = isElementVisible(pageElements.transactionId);
        commonLib.info("Is Transaction Id visible : " + status);
        return status;
    }

    /**
     * This method is used to check date filter visible or not
     *
     * @return
     */
    public boolean isDateFilterVisible() {
        boolean status = isElementVisible(pageElements.dateFilter);
        commonLib.info("Is date filter visible : " + status);
        return status;
    }

    /**
     * This method is used to check calendar visible or not
     *
     * @return
     */
    public boolean isCalendarVisible() {
        boolean status = isElementVisible(pageElements.calendar);
        commonLib.info("Is Calendar visible after clicking date filter: " + status);
        return status;
    }

    /**
     * This method is used to get Footer auuid of SMS Logs Widget
     *
     * @return
     */
    public String getFooterAuuid() {
        String result = getText(pageElements.footerAuuid);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is used to get Middle auuid of SMS Logs Widget
     *
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
        Boolean status = (isElementVisible(pageElements.widgetErrorMessage) || isElementVisible(pageElements.unableToFetchDataMessage));
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
     *
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
     *
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
     * This method will give the no. of rows
     *
     * @return
     */
    public int checkRowSize() {
        final List<WebElement> rows = getElementsListFromBy(pageElements.resendSms);
        return rows.size();
    }

    /**
     * This method will be used to test resend SMS
     *
     * @param smsLogs the sms log api response
     * @return it will return assertCheck value
     */
    public StringBuilder sendSMS(SmsLogsResponse smsLogs) {
        int pageCount = getNumberOfPages(pageElements.totalRows,5);
        boolean rowFound = false;
        for (int x = 0; x < pageCount; x++) {
            for (int i = 0; i < checkRowSize(); i++) {
                final List<WebElement> rows = getElementsListFromBy(pageElements.resendSms);
                for (WebElement webElement : rows) {
                    String transactionType = smsLogs.getResult().get(i).getTransactionId();
                    if (transactionType.contains("APC")) {
                        rowFound = true;
                        webElement.click();
                        assertCheck.append(actions.assertEqualStringType(getSendSmsHeader(), "Send SMS", "Send SMS Header is visible", "Send SMS header is NOT visible"));
                        assertCheck.append(actions.assertEqualStringType(getIssueDetail(), "Issue Detail:", "Issue Detail is visible", "Issue Detail is NOT visible"));
                        assertCheck.append(actions.assertEqualStringType(getEnterCommentHeader(), "Enter Comment", "Enter Comment is visible", "Enter Comment is not Visible"));
                        assertCheck.append(actions.assertEqualStringType(getSmsSelectReason(), "Select Reason *", "Select Reason is Visible", "Select Reason is not visible"));
                        assertCheck.append(actions.assertEqualBoolean(isSubmitBtnDisabled(), true, "Select Reason is Visible", "Select Reason is not visible"));
                        assertCheck.append(actions.assertEqualBoolean(isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is not visible"));
                        performResendSms();
                        assertCheck.append(actions.assertEqualBoolean(isSuccessPopUpVisible(), true, "Success Popup is visible after performing Submit action", "Success Popup is not visible after performing Submit action"));
                        assertCheck.append(actions.assertEqualStringType(getSuccessText(), constants.getValue("ngpsb.send.sms.success"), "Success text is displayed as expected", "Success text is not displayed as expected"));
                        clickCrossIcon();
                        break;
                    } else
                        commonLib.info("No records found with Txn id as APC for row no : " + (i + 1));

                }
                if (!rowFound) {
                    commonLib.warning("No records found with Txn id as APC on page no : " + (x + 1));
                    clickAndWaitForLoaderToBeRemoved(pageElements.nextPage);
                } else {
                    break;
                }
            }
        }
        return assertCheck;
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

    /**
     * This method is used to click resend sms
     */
    public void clickResendSms() {
        commonLib.info("Going to click resend SMS ");
        clickWithoutLoader(pageElements.resendSms);
    }

    /**
     * This method is used to check for resend sms Header
     */
    public String getSendSmsHeader() {
        final String text = getText(pageElements.smsHeader);
        commonLib.info("Validating SMS Header is visible : " + text);
        return text;
    }


    /**
     * This method use to check Issue Detail text is displayed or not
     */
    public String getIssueDetail() {
        final String text = getText(pageElements.smsIssueDetail);
        commonLib.info("Validating Issue Detail is visible : " + text);
        return text;
    }

    /**
     * This method use to check Enter Comment is displayed or not
     *
     * @return Text
     */
    public String getEnterCommentHeader() {
        final String text = getText(pageElements.enterComment);
        commonLib.info("Validating Enter Comment Header is visible : " + text);
        return text;
    }

    /**
     * This method use to check Enter Comment is displayed or not
     *
     * @return Text
     */
    public String getSmsSelectReason() {
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
     * This method is used to  Perform ResendSMS by selecting reason and comment
     */
    public void performResendSms() {
        commonLib.info("Going to perform Resend SMS Action");
        clickOnSmsSelectReason();
        selectRequestFromDropdown();
        enterComment(COMMENT);
        clickOnSubmitButton();
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
        if (isVisible(pageElements.selectArrow))
            clickWithoutLoader((pageElements.selectArrow));
    }

    /**
     * This method is used to click on select Customer Request
     */
    public void selectRequestFromDropdown() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectCustomerRequestFromDropdown))
            clickWithoutLoader((pageElements.selectCustomerRequestFromDropdown));
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
            clickAndWaitForLoaderToBeRemoved(pageElements.submitSms);

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
        if (isVisible(pageElements.crossIcon))
            clickWithoutLoader(pageElements.crossIcon);
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

    public Boolean isResendSmsVisible() {
        final boolean state = isElementVisible(pageElements.reSendSmsLink);
        commonLib.info("Is Resend SMS visible" + state);
        return state;
    }

    /**
     * This method is used to get total no. of rows
     */
    public int getNoOfRows() {
        if (isVisibleContinueExecution(pageElements.totalRows)) {
            List<WebElement> list = returnListOfElement(pageElements.totalRows);
            return list.size();
        } else {
            commonLib.warning("No Data is available under Sms Logs Widget");
            return 0;
        }
    }

    /**
     * This method is used to get Customer Msisdn from action trail drop down
     *
     * @return
     */
    public String getCustomerMsisdn() {
        commonLib.info(getText(pageElements.customerMsisdn));
        return getText(pageElements.customerMsisdn);
    }

    /**
     * This method is used to get SMS Date and Time from action trail drop down
     *
     * @return
     */
    public String getSmsDateTime() {
        commonLib.info(getText(pageElements.smsDateTime));
        return getText(pageElements.smsDateTime);
    }

    /**
     * This method is used to get Txn Id from action trail drop down
     *
     * @return
     */
    public String getTxnId() {
        commonLib.info(getText(pageElements.txnId));
        return getText(pageElements.txnId);
    }
}