package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AmBarUnbarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class AmBarUnbar extends BasePage {
    AmBarUnbarPage pageElements;


    public AmBarUnbar(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmBarUnbarPage.class);
    }

    /**
     * This method is used to  BAR/UNBAR smartcash profile by selecting reason and comment
     */
    public void performBarUnBar() {
        commonLib.info("Going to perform Bar/unbar Action");
        pages.getBarUnbar().clickOnSelectReason();
        pages.getAmBarUnbar().selectReasonFromDropdown();
        pages.getBarUnbar().clickOnBarType();
        pages.getAmBarUnbar().selectBarTypeFromDropdown();
        pages.getBarUnbar().enterComment(COMMENT);
        pages.getBarUnbar().clickOnSubmitButton();
    }

    /**
     * This method is used to select reason from dropdown
     */
    public void selectReasonFromDropdown() {
        commonLib.info("Going to select reason : Lost sim card");
        if (isVisible(pageElements.selectReasonFromDropdown))
            clickWithoutLoader((pageElements.selectReasonFromDropdown));
    }

    /**
     * This method is used to select Bar Type from dropdown
     */
    public void selectBarTypeFromDropdown() {
        commonLib.info("Going to select Bar Type :Sender ");
        if (isVisible(pageElements.selectBarTypeFromDropdown))
            clickWithoutLoader((pageElements.selectBarTypeFromDropdown));
    }

    /**
     * This method will be used to test Bar action
     *
     * @return it will return assertCheck value
     */
    public StringBuilder barTest() {
        pages.getBarUnbar().clickBarIcon();
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isIssuePopUpVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isBarHeaderVisible(), true, "BAR header is visible in Issue Detail Pop up", "BAR header is visible not Issue Detail Pop up"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is not visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSubmitBtnDisabled(), false, "Submit button is disabled", "Submit button is not disabled"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is not visible"));
        performBarUnBar();
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing Bar action", "Success Popup is not visible after performing Bar action"));
        String successText = "Your Barring request of airtel money is accepted";
        assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
        return assertCheck;
    }

    /**
     * This method will be used to test uNBar action
     *
     * @return it will return assertCheck value
     */
    public StringBuilder unBarTest() {
        pages.getBarUnbar().clickBarIcon();
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isIssuePopUpVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isBarHeaderVisible(), true, "BAR header is visible in Issue Detail Pop up", "BAR header is visible not Issue Detail Pop up"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is not visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSubmitBtnDisabled(), false, "Submit button is disabled", "Submit button is not disabled"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is not visible"));
        performBarUnBar();
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing Bar action", "Success Popup is not visible after performing Bar action"));
        String successText = "Your Un-Barring request of airtel money is accepted";
        assertCheck.append(actions.assertEqualStringType(pages.getBarUnbar().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
        return assertCheck;
    }
}
