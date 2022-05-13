package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.PinResetPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class PinReset extends BasePage {
    PinResetPage pageElements;

    public PinReset(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, PinResetPage.class);
    }

    /**
     * This method is used to check pin reset icon visible or not
     *
     * @return
     */
    public boolean isPinResetIconVisible() {
        boolean status = isElementVisible(pageElements.resetPinIcon);
        commonLib.info("Is Reset Pin icon visible : " + status);
        return status;
    }

    /**
     * This method is used to check issue pop up  is visible or not
     *
     * @return
     */
    public boolean isIssuePopUpVisible() {
        boolean status = isElementVisible(pageElements.resetPinIssuePopUp);
        commonLib.info("Is Issue pop up visible : " + status);
        return status;
    }

    /**
     * This method is used to check pin reset is visible or not in Issue pop up
     *
     * @return
     */
    public boolean isPinResetPinHeaderVisible() {
        boolean status = isElementVisible(pageElements.resetPin);
        commonLib.info("Is Reset Pin Header visible in Issue pop up  : " + status);
        return status;
    }

    /**
     * This method is used to click on send bill icon
     */
    public void clickPinReset() {
        commonLib.pass("Going to click pin reset icon ");
        if (isVisible(pageElements.resetPinIcon)) ;
        clickAndWaitForLoaderToBeRemoved(pageElements.resetPinIcon);
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
     * This method is used to check Comment Box is visible or not
     */
    public Boolean isCommentBoxVisible() {
        Boolean status = isVisible(pageElements.commentBoxLabel);
        commonLib.pass("Comment box is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Cancel button is visible or not
     */
    public Boolean isCancelButtonVisible() {
        Boolean status = isVisible(pageElements.cancel);
        commonLib.pass("Cancel Button is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Submit button
     */
    public void clickOnSubmitButton() {
        commonLib.info("Going to click submit button");
        if (isVisible(pageElements.submitButton))
            clickAndWaitForLoaderToBeRemoved(pageElements.submitButton);
        else
            commonLib.fail("Submit Button is not visible", true);
    }

    /**
     * This method is used to click on Continue of cancel confirmation message
     */
    public void clickOnContinue() {
        commonLib.info("Clicking on continue button of cancel confirmation message");
        if (isVisible(pageElements.continueButton)) ;
        clickWithoutLoader((pageElements.continueButton));
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
    public void clickSelectReason() {
        commonLib.info("Going to click Select Reason");
        if (isVisible(pageElements.selectReason)) ;
        clickWithoutLoader((pageElements.selectReason));
    }

    /**
     * This method is used to select reason from dropdown
     */
    public void selectReasonFromDropdown() {
        commonLib.info("Going to select Reason from dropdown");
        if (isVisible(pageElements.selectReasonFromDropdown)) ;
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
        Boolean status = isEnabled(pageElements.submitButton);
        commonLib.info("Is submit button disabled " + status);
        return status;
    }

    /**
     * This method is used to click on Cancel button of confirmation pop up
     */
    public void clickOnCancelButton() {
        commonLib.info("Going to click cancel button");
        if (isVisible(pageElements.cancel))
            clickWithoutLoader(pageElements.cancel);
        else
            commonLib.fail("Cancel Button is not visible", true);
    }

    /**
     * This method is used to check enter comment
     *
     * @return true/false
     */
    public Boolean isSuccessPopUpVisible() {
        waitVisibility(pageElements.confirmationPopUp);
        final boolean state = isElementVisible(pageElements.confirmationPopUp);
        commonLib.info("Is confirmation Pop Up visible :" + state);
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
        if (isVisible(pageElements.crossIcon)) ;
        clickWithoutLoader((pageElements.crossIcon));
    }

    /**
     * This method is used to perform pin reset  by selecting reason and comment
     */
    public void performResetPin() {
        commonLib.info("Going to perform pin Reset Action");
        pages.getPinReset().clickPinReset();
        pages.getPinReset().clickSelectReason();
        pages.getPinReset().selectReasonFromDropdown();
        pages.getPinReset().enterComment(COMMENT);
        pages.getPinReset().clickOnSubmitButton();
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
     * This method is used to check demographic widget visible after closing pop up
     *
     * @return true/false
     */
    public Boolean isDemographicWidgetVisible() {
        final boolean state = isElementVisible(pageElements.demographicWidget);
        commonLib.info("Is demographic widget visible" + state);
        return state;

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
     * This method is use to click close tab button
     */
    public void clickCloseBtn() {
        commonLib.info("Clicking on close button");
        clickAndWaitForLoaderToBeRemoved(pageElements.pinRestCloseBtn);
    }
}
