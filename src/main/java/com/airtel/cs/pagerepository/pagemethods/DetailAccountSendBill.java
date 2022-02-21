package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountSendBillPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class DetailAccountSendBill extends BasePage {

    DetailAccountSendBillPage pageElements;

    public DetailAccountSendBill(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DetailAccountSendBillPage.class);
    }

    /**
     * This method is used to check whether  send bill icon is visible
     *
     * @return
     */

    public Boolean isSendBillVisible(int row) {
        Boolean status = isElementVisible(By.xpath(pageElements.sendBill1 + row + pageElements.sendBill2)) && isClickable(By.xpath(pageElements.sendBill1 + row + pageElements.sendBill2));
        commonLib.pass("Is Send Bill icon visible at row " + row + ": " + status);
        return status;
    }

    /**
     * This method is used to click on send bill icon
     */
    public void clickOnSendBill(int row) {
        commonLib.pass("Going to click send bill icon ");
        if (isVisible(By.xpath(pageElements.sendBill1 + row + pageElements.sendBill2)))
            clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.sendBill1 + row + pageElements.sendBill2));
    }

    /**
     * This method is used to check Issue Detail Pop up is visible or not
     */
    public Boolean isIssuePopVisible() {
        Boolean status = isVisible(pageElements.issueDetailPopUp);
        commonLib.pass("Issue Detail Pop up is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Select Reason visible or not
     */
    public Boolean isSelectReasonVisible() {
        Boolean status = isVisible(pageElements.selectReasonLabel);
        commonLib.pass("Select Reason is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Bill Number is visible or not
     */
    public Boolean isBillNumberVisible() {
        Boolean status = isVisible(pageElements.billNumberLabel);
        commonLib.pass("Bill Number is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Email ID is visible or not
     */
    public Boolean isEmailIDVisible() {
        Boolean status = isVisible(pageElements.emailIDLabel);
        commonLib.pass("Email ID is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Comment Box is visible or not
     */
    public Boolean isCommentBoxVisible() {
        Boolean status = isVisible(pageElements.commentBoxLabel);
        commonLib.pass("Comment box is visible : " + status);
        return status;
    }

    /**
     * This method is used to get pre-populated bill number
     */
    public String getBillNumber() {
        String billNumber = getText(pageElements.billNumber);
        commonLib.pass("Bill Number : " + billNumber);
        return billNumber;
    }

    /**
     * This method is used to get pre-populated email id
     */
    public String getPrePopulatedEmailId() {
        String emailID = getText(pageElements.prePopulatedEmailID);
        commonLib.pass("Email ID : " + emailID);
        return emailID;

    }

    /**
     * This method is used to split bill number
     */
    public String splitBillNumber(String billNumber) {
        commonLib.info("Splitting Bill Number");
        String arr[] = billNumber.split(":", 2);
        return arr[1];

    }

    /**
     * This method is use to get Email ID from Demographic
     *
     * @return String The value
     */
    public String getCustomerEmail() {
        final String text = getText(pageElements.demographicEmail);
        commonLib.info("Getting email id " + text);
        return text;
    }

    /**
     * This method is used to check Unable To Fetch Error message is visible or not
     */
    public Boolean isErrorMessageVisible() {
        Boolean status = isVisible(pageElements.unableToFetchError);
        commonLib.pass("Error message is visible : " + status);
        return status;
    }

    /**
     * This method is used to check submit button  visible or not
     */
    public Boolean isSubmitButtonVisible() {
        Boolean status = isVisible(pageElements.submitButton);
        commonLib.pass("Submit Button is visible : " + status);
        return status;
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
     * This method is used to click on Cancel button
     */
    public void clickOnCancelButton() {
        commonLib.info("Going to click cancel button");
        if (isVisible(pageElements.cancelButton))
            clickWithoutLoader(pageElements.cancelButton);

    }

    /**
     * This method is used to click on Submit button
     */
    public void clickOnSubmitButton() {
        commonLib.info("Going to click submit button");
        if (isVisible(pageElements.cancelButton))
            clickWithoutLoader(pageElements.cancelButton);

    }

    /**
     * This method is used to click on Continue of cancel confirmation message
     */
    public void clickOnContinue() {
        commonLib.info("Clicking on continue button of cancel confirmation message");
        if (isVisible(pageElements.continueButton));
        clickWithoutLoader((pageElements.continueButton));
    }

    /**
     * This method is used to click on Cancel of cancel confirmation message
     */
    public void clickOnCancel() {
        commonLib.info("Clicking on continue button of cancel confirmation message");
        if (isVisible(pageElements.cancel));
        clickWithoutLoader((pageElements.cancel));
    }

    /**
     * This method is used to check  confirmation message to cancel
     */
    public Boolean isCancelConfirmMessageVisible() {
        Boolean status = isVisible(pageElements.cancelConfirmMessage);
        commonLib.info("Is cancel confirm message visible : " + status);
        return status;
    }

    /**
     * This method is used to click on select reason
     */
    public void clickOnSelectReason() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectReason));
        clickWithoutLoader((pageElements.selectReason));
    }

    /**
     * This method is used to select reason
     */
    public void selectReason() {
        commonLib.info("Going to select reason : Customer Request");
        if (isVisible(pageElements.selectReasonFromDropdown));
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
     * This method is used to check submit button disable or not
     *
     * @return true/false
     */
    public boolean isSubmitBtnDisabled() {
        Boolean status=isEnabled(pageElements.submitButton);
        commonLib.info("Is submit button disabled " + status);
        return status;
    }

    /**
     * This method is used to check enter comment
     *
     * @return true/false
     */
    public Boolean isSuccessPopUpVisible() {
        final boolean state = isElementVisible(pageElements.confirmationPopUp);
        commonLib.info("Is confirmation Pop Up visible" + state);
        return state;

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
     * This method is used to click on cross icon of success pop up
     */
    public void clickCrossIcon() {
        commonLib.info("Going to click cross icon");
        if (isVisible(pageElements.crossIcon));
        clickWithoutLoader((pageElements.crossIcon));
    }

    /**
     * This method route and click Home Tab of Customer Dashboard Page
     */
    public void clickHomeTab() {
        commonLib.info("Going to click Home tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.homePage);
    }

    /**
     * This method will route and click view history
     */
    public void clickViewHistoryTab() {
        commonLib.info("Going to click View History tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.viewHistory);
    }

    /**
     * This method will route and click action trail
     */
    public void clickActionTrailTab() {
        commonLib.info("Going to click Action Trail tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.actionTrail);
    }

    /**
     * This method will click on dropdown icon of action trail tab
     */
    public void clickingOnDropDown() {
        commonLib.info("Going to click Action Trail's dropdown");
        clickAndWaitForLoaderToBeRemoved(pageElements.actionTrailLatestDropdown);
    }

    /**
     * This method is used to get bill number from action trail drop down
     *
     * @return
     */
    public String getBillNo() {
        commonLib.info(getText(pageElements.actionTrailBillNo));
        return getText(pageElements.actionTrailBillNo);
    }

    /**
     * This method is used to get Email Id from action trail drop down
     *
     * @return
     */
    public String getEmailID() {
        commonLib.info(getText(pageElements.actionTrailEmailId));
        return getText(pageElements.actionTrailEmailId);
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

    /**
     * This method is used to check send bill icon disabled or not
     *
     * @return true/false
     */
    public boolean isSendBillDisabled(int row) {
        Boolean status=isEnabled(By.xpath(pageElements.sendBill1 + row + pageElements.sendBill2));
        commonLib.info("Is send bill icon disabled : " + status);
        return status;
    }

    /**
     * This method is used to get text on hovering send bill icon
     *
     * @return text
     */
    public String getSendBillIconText(int row) {
        commonLib.info("Hovering over Send Bill Icon");
        hoverOverElement(By.xpath(pageElements.sendBill1 + row + pageElements.sendBill2));
        return getText(pageElements.hoverSendBill);
    }

    /**
     * This method is used to perform send Bill action by selecting reason and comment
     * @param row
     */
    public void performSendBill(int row) {
        commonLib.info("Going to perform Send Bill Action");
        pages.getDetailAccountSendBill().clickOnSendBill(row);
        pages.getDetailAccountSendBill().selectReason();
        pages.getDetailAccountSendBill().enterComment(COMMENT);
        pages.getDetailAccountSendBill().clickOnSubmitButton();
    }

    /**
     * This method is used to go to Action Trail tab
     */
    public void goToActionTrail() {
        commonLib.info("Going to click Action Trail tab");
        pages.getDetailAccountSendBill().clickHomeTab();
        pages.getDetailAccountSendBill().clickViewHistoryTab();
        pages.getDetailAccountSendBill().clickActionTrailTab();
    }
}
