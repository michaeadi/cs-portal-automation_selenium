package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.BarUnbarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class BarUnbar extends BasePage{
    BarUnbarPage pageElements;

    public BarUnbar(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, BarUnbarPage.class);
    }

    /**
     * This method is used to check BAR icon visible or not
     * @return
     */
    public boolean isBarIconVisible() {
        boolean status = isElementVisible(pageElements.barIcon);
        commonLib.info("Is Bar icon visible : " + status);
        return status;
    }

    /**
     * This method is used to check UnBAR icon visible or not
     * @return
     */
    public boolean isUnBarIconVisible() {
        boolean status = isElementVisible(pageElements.unBarIcon);
        commonLib.info("Is UnBar icon visible : " + status);
        return status;
    }

    /**
     * This method is used to check issue pop up  is visible or not
     * @return
     */
    public boolean isIssuePopUpVisible() {
        boolean status = isElementVisible(pageElements.barUnbarIssuePopUp);
        commonLib.info("Is Issue pop up visible : " + status);
        return status;
    }

    /**
     * This method is used to check BAR header is visible or not in Issue pop up
     * @return
     */
    public boolean isBarHeaderVisible() {
        boolean status = isElementVisible(pageElements.barHeader);
        commonLib.info("Is BAR Header visible in Issue pop up  : " + status);
        return status;
    }

    /**
     * This method is used to check UNBAR header is visible or not in Issue pop up
     * @return
     */
    public boolean isUnBarHeaderVisible() {
        boolean status = isElementVisible(pageElements.unbarHeader);
        commonLib.info("Is UNBAR Header visible in Issue pop up  : " + status);
        return status;
    }

    /**
     * This method is used to click on bar  icon
     */
    public void clickBarIcon() {
        commonLib.pass("Going to click bar icon ");
        if (isVisible(pageElements.barIcon));
        clickAndWaitForLoaderToBeRemoved(pageElements.barIcon);
    }
    /**
     * This method is used to click on unbar  icon
     */
    public void clickUnBarIcon() {
        commonLib.pass("Going to click unbar icon ");
        if (isVisible(pageElements.unBarIcon));
        clickAndWaitForLoaderToBeRemoved(pageElements.unBarIcon);
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
     * This method is used to check BAR Type visible or not
     */
    public Boolean isBarTypeVisible() {
        Boolean status = isVisible(pageElements.selectBarTpe);
        commonLib.pass("BAR Type is visible : " + status);
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
        Boolean status = isVisible(pageElements.cancelButton);
        commonLib.pass("Cancel Button is visible : " + status);
        return status;
    }

    /**
     * This method is used to click on Submit button
     */
    public void clickOnSubmitButton() {
        commonLib.info("Going to click submit button");
        if (isVisible(pageElements.submitButton))
            clickWithoutLoader(pageElements.submitButton);

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
     * This method is used to select reason from dropdown
     */
    public void selectReasonFromDropdown() {
        commonLib.info("Going to Select Reason from dropdown");
        if (isVisible(pageElements.selectReasonFromDropdown));
        clickWithoutLoader((pageElements.selectReasonFromDropdown));
    }

    /**
     * This method is used to click on select BAR Type
     */
    public void clickOnBarType() {
        commonLib.info("Going to click BAR Type");
        if (isVisible(pageElements.selectBarTpe));
        clickWithoutLoader((pageElements.selectBarTpe));
    }

    /**
     * This method is used to select Bar Type
     */
    public void selectBarTypeFromDropdown() {
        commonLib.info("Going to select BAR Type from dropdown");
        if (isVisible(pageElements.selectBarTypeFromDropdown));
        clickWithoutLoader((pageElements.selectBarTypeFromDropdown));
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
     * This method route and click Home Tab of Customer Dashboard Page
     */
    public void clickHomeTab() {
        commonLib.info("Going to click Home tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.homePage);
    }

    /**
     * This method is used to  BAR/UNBAR smartcash profile by selecting reason and comment
     */
    public void performBarUnBar( ) {
        commonLib.info("Going to perform Bar/unbar Action");
        pages.getBarUnbar().clickOnSelectReason();
        pages.getBarUnbar().selectReasonFromDropdown();
        pages.getBarUnbar().clickOnBarType();
        pages.getBarUnbar().selectBarTypeFromDropdown();
        pages.getBarUnbar().enterComment(COMMENT);
        pages.getBarUnbar().clickOnSubmitButton();
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
     * This method is used to check demographic widget visible after closing pop up
     *
     * @return true/false
     */
    public Boolean isSuccessPopUpVisible() {
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
}
