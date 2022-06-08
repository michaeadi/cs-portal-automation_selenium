package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.dataproviders.databeans.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.pagerepository.pageelements.AmProfilePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AmProfile extends BasePage {
    AmProfilePage pageElements;


    public AmProfile(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmProfilePage.class);
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
     * This method is used to check Barred Reason Param visible or not
     * @return
     */
    public boolean isBarredReasonLabelVisible() {
        boolean status = isVisible(pageElements.barredReasonLabel);
        commonLib.pass("Barred Reason Param visible : " + status);
        return status;
    }

    /**
     * This method is used to get Barred Reason
     */
    public String getBarredReason() {
        String status = getText(pageElements.barredReason);
        commonLib.info("Getting Barred Reason  : " + status);
        return status;
    }

    /**
     * This method is used to check Barred On Param visible or not
     */
    public boolean isBarredOnLabelVisible() {
        boolean status = isVisible(pageElements.barredOnLabel);
        commonLib.pass("Barred On Param visible : " + status);
        return status;
    }

    /**
     * This method is used to get Barred On
     */
    public String getBarredOn() {
        String status = getText(pageElements.barredOn);
        commonLib.info("Getting Barred On: " + status);
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
     * This method use Click on Submit
     */
    public void clickOnSubmitButton() {
        commonLib.info("Click on Submit Button ");
        clickWithoutLoader(pageElements.submitButton);
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
        commonLib.info("Is Submit Button disabled :" + status);
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

    /**
     * This method is used to check Service status visible or not
     */
    public boolean isServiceStatusLabelVisible() {
        boolean status = isVisible(pageElements.barredOnLabel);
        commonLib.pass("Service status is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Barred By visible or not
     */
    public boolean isBarredByLabelVisible() {
        boolean status = isVisible(pageElements.barredByLabel);
        commonLib.pass("Barred By is visible : " + status);
        return status;
    }

    /**
     * This method is used to check Remarks visible or not
     */
    public boolean isRemarksLabelVisible() {
        boolean status = isVisible(pageElements.remarksLabel);
        commonLib.pass("Remarks is visible : " + status);
        return status;
    }

    /**
     * This method is used to get Barred By
     */
    public String getBarredBy() {
        String status = getText(pageElements.barredBy);
        commonLib.info("Getting Barred By  : " + status);
        return status;
    }

    /**
     * This method is used to get Remarks
     */
    public String getRemarks() {
        String status = getText(pageElements.remarks);
        commonLib.info("Getting Remarks : " + status);
        return status;
    }

    /**
     * This method is used to get Wallet Type
     */
    public String getWalletType() {
        String status = getText(pageElements.walletType);
        commonLib.info("Getting Wallet Type  : " + status);
        return status;
    }

    /**
     * This method is used to get Grade
     */
    public String getGrade() {
        String status = getText(pageElements.grade);
        commonLib.info("Getting Grade : " + status);
        return status;
    }

    /**
     * This method is used to Authenticate Airtel Money Profile
     */
    public StringBuilder amAuthenticate(){
        pages.getDemoGraphicPage().clickAirtelStatusToUnlock();
        assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
        pages.getDemoGraphicPage().selectPolicyQuestion();
        assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
        pages.getAuthTabPage().clickAuthBtn();
        assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getWidgetUnlockMessage(), "Unlocking the widget", "Unlock Widget, Successfully", "Unlock Widget, Un-Successful"));
        assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getToastMessage(), "Customer response saved successfully", "Toast Message Shown Successfully", "Toast Message NOT Successful"));
        return assertCheck;
    }

    /**
     * This method is used to hover on Address info icon
     */
    public void hoverServiceStatusMessageIcon() {
        commonLib.info("Hover over Service Status message icon");
        hoverOverElement(pageElements.messageIcon);
    }
}
