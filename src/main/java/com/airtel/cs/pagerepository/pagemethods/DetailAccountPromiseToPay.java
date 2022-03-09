package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountPromiseToPayPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class DetailAccountPromiseToPay extends BasePage {

    DetailAccountPromiseToPayPage pageElements;
    static String nextDate = "null";
    static String splitNextdate = "null";


    public DetailAccountPromiseToPay(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailAccountPromiseToPayPage.class);
    }

    /**
     * This method is used to check Unable To Fetch Error message is visible or not
     */
    public boolean isErrorMessageVisible() {
        Boolean status = isVisible(pageElements.unableToFetchError);
        commonLib.pass("Error message is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on promise to payl icon
     */
    public void clickOnPromiseToPay(int row) {
        commonLib.pass("Going to click Promise To Payicon ");
        if (isVisible(By.xpath(pageElements.promiseToPay1 + row + pageElements.promiseToPay2)))
            clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.promiseToPay1 + row + pageElements.promiseToPay2));
    }

    /**
     * This method is used to check Issue Detail Pop up is visible or not
     */
    public boolean isIssuePopVisible() {
        Boolean status = isVisible(pageElements.issueDetailPopUp);
        commonLib.pass("Issue Detail Pop up is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Select Reason visible or not
     */
    public boolean isSelectReasonVisible() {
        Boolean status = isVisible(pageElements.selectReasonLabel);
        commonLib.pass("Select Reason is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Cancel button
     */
    public void clickOnCancelButton() {
        commonLib.info("Going to click cancel button");
        if (isVisible(pageElements.cancelButton))
            clickWithoutLoader(pageElements.cancelButton);

    }

    /**
     * This method is used to check  Raise Sr Button Disable or not
     */
    public boolean isRaiseSrButtonDisabled() {
        Boolean status = isVisible(pageElements.raiseSrButton);
        commonLib.info("Is Raise SR Button disabled \" + status");
        return status;
    }

    /**
     * This method is used to check  Raise Sr Button enable or not
     */
    public boolean isRaiseSrButtonEnable() {
        Boolean status = isVisible(pageElements.raiseSrButton);
        commonLib.info("Is Raise SR Button enable \" + status");
        return status;
    }

    /**
     * This method is used to click on Raise Sr Button
     */
    public void clickOnRaiseSrButton() {
        commonLib.info("Going to click Raise Sr Button");
        if (isVisible(pageElements.raiseSrButton))
            clickWithoutLoader(pageElements.raiseSrButton);

    }

    /**
     * This method is used to check Cancel button is visible or not
     */
    public Boolean isCancelButtonVisible() {
        Boolean status = isVisible(pageElements.cancelButton);
        commonLib.pass("Cancel Button is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Comment Box is visible or not
     */

    public boolean isCommentBoxVisible() {
        boolean status = isVisible(pageElements.commentBox);
        commonLib.info("Is Comment Box Visible  + status");
        return status;
    }

    public boolean isPromiseToPayVisible(int row) {
        return false;
    }

    /**
     * This method is used to check Promise To Pay icon disabled or not
     *
     * @return true/false
     */
    public boolean isPromiseToPayDisabled(int row) {
        boolean status = isEnabled(By.xpath(pageElements.promiseToPay1 + row + pageElements.promiseToPay2));
        commonLib.info("Is Promise To Pay  icon disabled : " + status);
        return status;
    }

    public String getPromiseToPayIconText(int row) {
        return null;
    }

    /**
     * This method is used to click on Cancel of cancel confirmation message
     */
    public void clickOnCancel() {
        commonLib.info("Clicking on continue button of cancel confirmation message");
        if (isVisible(pageElements.cancel)) ;
        clickWithoutLoader((pageElements.cancel));
    }

    public void clickOnContinue() {
        commonLib.info("Clicking on continue button of cancel confirmation message");
        if (isVisible(pageElements.continueButton)) ;
        clickWithoutLoader((pageElements.continueButton));
    }

    /**
     * This method is used to perform Promise To Pay action by selecting reason and comment
     *
     * @param row Widget row where P2P icon is visible
     */
    public void performPromiseToPay(int row) {
        String date;
        commonLib.info("Going to perform Promise To Pay Action");
        pages.getDetailAccountPromiseToPay().clickOnPromiseToPay(row);
        pages.getDetailAccountPromiseToPay().selectReason();
        date = getNextDate();
        if (date.equals("01")) {
            pages.getDetailAccountPromiseToPay().clickNextButton();
            pages.getDetailAccountPromiseToPay().clickFinalDate();
        } else
            pages.getDetailAccountPromiseToPay().clickFinalDate();
        pages.getDetailAccountPromiseToPay().enterComment(COMMENT);
        pages.getDetailAccountPromiseToPay().clickOnRaiseSrButton();

    }

    /**
     * This method is used to select reason
     */
    public void selectReason() {
        commonLib.info("Going to select reason : Customer Request");
        if (isVisible(pageElements.selectReasonFromDropdown))
            clickWithoutLoader((pageElements.selectReasonFromDropdown));
    }

    /**
     * This method is used to write the comment into comment box
     *
     * @param text The comment
     */
    public void enterComment(String text) {
        commonLib.info("Writing comment into comment box: " + text);
        enterText(pageElements.commentBox, text);
    }

    /**
     * This method is used to click on cross icon of success pop up
     */
    public void clickCrossIcon() {
        commonLib.info("Going to click cross icon");
        if (isVisible(pageElements.crossIcon)) {
            clickWithoutLoader((pageElements.crossIcon));
        }
    }

    /**
     * This method is used to get text of success message
     *
     * @return text
     */
    public String getSuccessText() {
        final String text = getText(pageElements.successMessage);
        commonLib.info("Getting success pop up text" + text);
        return text;
    }

    /**
     * This method is used to check iSuccess PopUp Visible
     *
     * @return true/false
     */
    public boolean isSuccessPopUpVisible() {
        final boolean state = isElementVisible(pageElements.confirmationPopUp);
        commonLib.info("Is confirmation Pop Up visible" + state);
        return state;
    }

    /**
     * This method is used to check  confirmation message to cancel
     */
    public boolean isCancelConfirmMessageVisible() {
        boolean status = isVisible(pageElements.cancelConfirmMessage);
        commonLib.info("Is cancel confirm message visible : " + status);
        return status;
    }

    /**
     * This method is used to check  Total Outstanding (TZS) Visible
     */
    public boolean isTotalOutstandingVisible() {
        boolean status = isVisible(pageElements.totalOutstanding);
        commonLib.info("Is Total Outstanding (TZS) visible : " + status);
        return status;
    }

    /**
     * This method is used to check  Last Paid Bill Amount (TZS) Visible
     */
    public boolean isLastPaidBillAmountVisible() {
        boolean status = isVisible(pageElements.lastPaidBillAmount);
        commonLib.info("Is Last Paid Bill Amount visible : " + status);
        return status;
    }

    /**
     * This method is used to check Bill Date and Time Visible
     */
    public boolean isBillDateAndTimeVisible() {
        boolean status = isVisible(pageElements.billDateAndTime);
        commonLib.info("Is  Bill Date and Time visible : " + status);
        return status;
    }

    /**
     * This method is used to check Promise to Pay Date Visible
     */
    public boolean isPromiseToPayDateVisible() {
        boolean status = isVisible(pageElements.promiseToPayDate);
        commonLib.info("Is  Promise to Pay Date visible : " + status);
        return status;
    }

    /**
     * This method is used to check Last Bill Number Visible
     */
    public boolean isLastBillNumberVisible() {
        boolean status = isVisible(pageElements.lastBillNumber);
        commonLib.info("Is  Last Bill Number visible : " + status);
        return status;

    }

    /**
     * This method is used to check Successfull Message  Visible after Raise SR
     */
    public boolean isSuccessfullMessageVisible() {
        boolean status = isVisible(pageElements.successfullMessage);
        commonLib.info("Is  Successfull Message visible : " + status);
        return status;
    }

    /**
     * This method is used to get Current Date
     */

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * This method is used to get Next Date
     */
    public static String getNextDate() {

        try {
            String currentDate = getCurrentDate();
            Calendar today = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(currentDate);
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }

    /**
     * This method is used to split Date
     */
    public String splitBillNumber(String nextDate) {
        commonLib.info("Splitting Date ");
        String arr[] = nextDate.split("/", 2);
        splitNextdate = arr[0];
        return splitNextdate;
    }

    /**
     * This method is used to click on Next Calendar Months Icon
     */
    public void clickNextButton() {
        commonLib.info("Going to click Next Button");
        if (splitNextdate.equals("01")) {
            clickWithoutLoader(pageElements.nextButton);
        }
    }

    /**
     * This method is used to click on Final Date
     */
    public void clickFinalDate() {
        commonLib.pass("Going to click Final Date ");
        if (isVisible(By.xpath(pageElements.finalDate1 + nextDate + pageElements.finalDate2)))
            clickWithoutLoader(By.xpath(pageElements.finalDate1 + nextDate + pageElements.finalDate2));
    }

}

