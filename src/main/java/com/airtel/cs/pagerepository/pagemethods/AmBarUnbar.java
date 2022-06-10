package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.BarUnbarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class AmBarUnbar extends BasePage {
    BarUnbarPage pageElements;


    public AmBarUnbar(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, BarUnbarPage.class);
    }

    /**
     * This method is used to  BAR/UNBAR AM Profile by selecting reason and comment
     */
    public void performBarUnbar(String header) {
        commonLib.info("Going to perform Bar/unbar Action");
        try {
            pages.getBarUnbar().clickOnSelectReason();
            pages.getMsisdnSearchPage().selectReasonOrBarTypeFromDropdown(header);
            pages.getBarUnbar().clickOnBarType();
            pages.getMsisdnSearchPage().selectReasonOrBarTypeFromDropdown(header);
            pages.getBarUnbar().enterComment(COMMENT);
            pages.getBarUnbar().clickOnSubmitButton();
        } catch (Exception e) {
            pages.getBarUnbar().clickCrossIcon();
            commonLib.fail("Exception in Method - performBarUnBar" + e.fillInStackTrace(), true);
        }

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
        performBarUnbar("BAR");
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing Bar action", "Success Popup is not visible after performing Bar action"));
        String successText = constants.getValue("cs.am.bar.success.message");
        if (pages.getBarUnbar().getSuccessText().contains(successText))
            commonLib.pass("Success message is same as expected :" + pages.getBarUnbar().getSuccessText());
        else
            commonLib.fail("Success message is not same as expected : " + pages.getBarUnbar().getSuccessText(), true);
        return assertCheck;
    }

    /**
     * This method will be used to test UnBar action
     *
     * @return it will return assertCheck value
     */
    public StringBuilder unBarTest() {
        pages.getBarUnbar().clickUnBarIcon();
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isIssuePopUpVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isBarHeaderVisible(), true, "BAR header is visible in Issue Detail Pop up", "BAR header is visible not Issue Detail Pop up"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is not visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSubmitBtnDisabled(), false, "Submit button is disabled", "Submit button is not disabled"));
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is not visible"));
        performBarUnbar("UNBAR");
        assertCheck.append(actions.assertEqualBoolean(pages.getBarUnbar().isSuccessPopUpVisible(), true, "Success Popup is visible after performing Bar action", "Success Popup is not visible after performing Bar action"));
        String successText = constants.getValue("cs.am.unbar.success.message").trim();
        if (pages.getBarUnbar().getSuccessText().trim().contains(successText))
            commonLib.pass("Success message is same as expected :" + pages.getBarUnbar().getSuccessText());
        else
            commonLib.fail("Success message is not same as expected : " + pages.getBarUnbar().getSuccessText(), true);

        return assertCheck;
    }
}
