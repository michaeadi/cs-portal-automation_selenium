package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DetailAccountPromiseToPayPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DetailAccountPromiseToPay extends BasePage {

    DetailAccountPromiseToPayPage pageElements;


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
     * This method is used to check Bill Number is visible or not
     */
    public boolean isBillNumberVisible() {
        Boolean status = isVisible(pageElements.billNumberLabel);
        commonLib.pass("Bill Number is visible : " + status);
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

    public boolean isSubmitBtnDisabled() {
        Boolean status = isVisible(pageElements.submitButton);
        commonLib.info("Is submit button disabled \" + status");
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
     * This method is used to check Comment Box is visible or not
     */

    public boolean isCommentBoxVisible() {
        Boolean status = isVisible(pageElements.commentBox);
        commonLib.info("Is Comment Box Visible  + status");
        return status;
    }

    public boolean isPromiseToPayVisible(int row) {
    }

    public boolean isPromiseToPayDisabled(int row) {
    }

    public String getPromiseToPayIconText(int row) {
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

    public void performPromiseToPay(int row) {
    }
    /**
     * This method is used to click on cross icon of success pop up
     */
    public void clickCrossIcon() {
        commonLib.info("Going to click cross icon");
        if (isVisible(pageElements.crossIcon)) ;
        clickWithoutLoader((pageElements.crossIcon));
    }

    public String getSuccessText() {
    }

    public boolean isSuccessPopUpVisible() {
    }

    public String getBillNumber() {
    }


    public boolean isCancelConfirmMessageVisible() {
    }
}
