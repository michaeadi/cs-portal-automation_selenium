package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.dataproviders.databeans.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.pagerepository.pageelements.AirtelMoneyProfileBarPage;
import com.airtel.cs.pagerepository.pageelements.AirtelMoneyProfileBarPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AirtelMoneyProfileBar extends BasePage {
    AirtelMoneyProfileBarPage pageElements;


    public AirtelMoneyProfileBar(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AirtelMoneyProfileBarPage.class);
    }

    /**
     * This method use Tap To Unlock Airtel Money Profile
     */
    public void clickToUnlock() {
        commonLib.info("Tap To Unlock Airtel Money Profile ");
        clickWithoutLoader(pageElements.tapToUnlock);
    }

    /**
     * This method is use to select minimum number of policy question
     */
    public void selectPolicyQuestion() {
        try {
            DataProviders dataProviders = new DataProviders();
            List<AuthTabDataBeans> list = dataProviders.getPolicy();
            for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                pages.getAuthTabPage().clickCheckBox(i);
            }
        } catch (InterruptedException e) {
            commonLib.fail("Exception in Method - selectPolicyQuestion" + e, true);
        }
    }
    /**
     * This method use Click On Authenticate
     */
    public void clickOnAuthenticate() {
        commonLib.info("Click On Authenticate ");
        clickWithoutLoader(pageElements.authenticate);
    }

    /**
     * This method is used to check Barred Reason Param visible or not
     */
    public boolean isBarredReasonLabelVisible() {
        boolean status = isVisible(pageElements.barredReason);
        commonLib.pass("Barred Reason Param visible : " + status);
        return status;
    }

    /**
     * This method is used to check Barred Reason Value visible or not
     */
    public boolean isBarredReasonTextVisible() {
        boolean status = isVisible(pageElements.barredReasonText);
        commonLib.pass("Barred Reason Value visible : " + status);
        return status;
    }

    /**
     * This method is used to check Barred On Param visible or not
     */
    public boolean isBarredOnLabelVisible() {
        boolean status = isVisible(pageElements.barredOn);
        commonLib.pass("Barred On Param visible : " + status);
        return status;
    }

    /**
     * This method is used to check Barred On Value visible or not
     */
    public boolean isBarredOnTextVisible() {
        boolean status = isVisible(pageElements.barredOnText);
        commonLib.pass("Barred On Value visible : " + status);
        return status;
    }

    /**
     * This method is used to check Bar Icon  visible or not
     */
    public boolean isBarIconVisible() {
        boolean status = isVisible(pageElements.barIcon);
        commonLib.pass("Barred On Icon visible : " + status);
        return status;
    }

    /**
     * This method is used to check UnBar Icon  visible or not
     */
    public boolean isUnBarIconVisible() {
        boolean status = isVisible(pageElements.unBarIcon);
        commonLib.pass("UnBarred On Icon visible : " + status);
        return status;
    }

    /**
     * This method use Click on Bar
     */
    public void clickOnBar() {
        commonLib.info("Click on Bar ");
        clickWithoutLoader(pageElements.barIcon);
    }

    /**
     * This method use Click on UnBar
     */
    public void clickOnUnBar() {
        commonLib.info("Click on UnBar ");
        clickWithoutLoader(pageElements.unBarIcon);
    }

    /**
     * This method is used to check Select Reason visible or not
     */
    public boolean isSelectReasonVisible() {
        boolean status = isVisible(pageElements.selectReasonLabel);
        commonLib.pass("Select Reason is visible : " + status);
        return status;
    }

    /**
     * This method is used to select reason
     */
    public void selectReason() {
        commonLib.info("Going to select reason : Lost sim card");
        if (isVisible(pageElements.selectReasonFromDropdown))
            clickWithoutLoader((pageElements.selectReasonFromDropdown));
    }

    /**
     * This method is used to select Bar Type
     */
    public void selectBarType() {
        commonLib.info("Going to select Bar Type : Sender");
        if (isVisible(pageElements.selectBarTypeFromDropdown))
            clickWithoutLoader((pageElements.selectBarTypeFromDropdown));
    }

    /**
     * This method is used to check Select Bar Type visible or not
     */
    public boolean isSelectBarTypeVisible() {
        boolean status = isVisible(pageElements.selectBarTypeLabel);
        commonLib.pass("Select Bar Type is visible : " + status);
        return status;
    }

    /**
     * This method is used to check SubmitButton Disable or not
     */
    public boolean isSubmitButtonDisabled() {
        boolean status = isVisible(pageElements.submitButton);
        commonLib.info("Is Submit Button disabled \" + status");
        return status;
    }

    /**
     * This method is used to check Comment Box is visible or not
     */

    public boolean isCommentBoxVisible() {
        boolean status = isVisible(pageElements.commentBoxLabel);
        commonLib.info("Is Comment Box Visible  + status");
        return status;
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
}
