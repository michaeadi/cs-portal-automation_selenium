package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.pagerepository.pageelements.CustomerProfilePage;
import com.airtel.cs.model.cs.response.plans.MainAccountBalance;
import com.airtel.cs.model.cs.response.plans.Plans;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

@Log4j2
public class CustomerProfile extends BasePage {

    public CustomerProfilePage pageElements;
    public static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);

    public CustomerProfile(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CustomerProfilePage.class);
    }

    public static int suspendSim;
    public static int reactivateSim;

    /**
     * This method is used to get first widget title
     *
     * @return String The value
     */
    public String getFirstWidgetHeader() {
        final String text = getText(pageElements.firstWidgetHeader);
        commonLib.info("Getting header of 1st Widget : " + text);
        return text;
    }

    /**
     * This method is use to check send internet setting action visible or not
     *
     * @return true/false
     */
    public Boolean isSendInternetSettingTitleVisible() {
        final boolean state = isEnabled(pageElements.sendSettingTitle);
        commonLib.info("Is Send Internet Setting Title Display: " + state);
        return state;
    }

    /**
     * This method use to Open Adjustment tab
     */
    public void clickAdjustment() {
        commonLib.info("Opening Adjustment tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.adjustmentAction);
    }

    /**
     * This method is use to check Adjustment action visible or not
     */
    public Boolean checkAdjustmentAction() {
        commonLib.info("Checking Adjustment action display or not");
        return isEnabled(pageElements.adjustmentAction);
    }

    /**
     * This method is use to check send internet setting confirm message visible or not
     *
     * @return true/false
     */
    public Boolean isSendInternetSettingConfirmMessageVisible() {
        final boolean state = isElementVisible(pageElements.sendSettingConfirm);
        commonLib.info("Is Send Internet Setting Confirm message Display: " + state);
        return state;
    }

    /**
     * This method is use to check reset me2u password action visible or not
     *
     * @return true/false
     */
    public Boolean isResetME2UPasswordTitle() {
        final boolean state = isEnabled(pageElements.resetME2Title);
        commonLib.info("Is Reset ME2U Password Title Display: " + state);
        waitTillLoaderGetsRemoved();
        return state;
    }

    /**
     * This method is use to check send internet setting confirm message visible or not
     *
     * @return true/false
     */
    public Boolean isResetME2UPasswordConfirmMessageVisible() {
        final boolean state = isEnabled(pageElements.resetME2Confirm);
        commonLib.info("Is Send Internet Setting Confirm message Display: " + state);
        return state;
    }

    /**
     * This method is used to get Second widget title
     *
     * @return String The value
     */
    public String getSecondWidgetHeader() {
        final String text = getText(pageElements.secondWidgetHeader);
        commonLib.info("Getting header of 2nd Widget : " + text);
        return text;
    }

    /**
     * This method is used to get Third widget title
     *
     * @return String The value
     */
    public String getThirdWidgetHeader() {
        final String text = getText(pageElements.thirdWidgetHeader);
        commonLib.info("Getting header of 3rd Widget : " + text);
        return text;
    }

    /**
     * This method is used to get Fourth widget title
     *
     * @return String The value
     */
    public String getFourthWidgetHeader() {
        final String text = getText(pageElements.fourthWidgetHeader);
        commonLib.info("Getting header of 4th Widget : " + text);
        return text;
    }

    /**
     * This method is use to get list of pin tag text
     *
     * @return List The list of value
     */
    public List<String> getPinnedTagTexts() {
        List<String> strings = new ArrayList<>();
        List<WebElement> webElements = returnListOfElement(pageElements.pinTags);
        for (int i = 1; i <= webElements.size(); i++) {
            By tagName = By.xpath(pageElements.pinTagText + i + "]");
            commonLib.info("Reading pinned tag name: " + getText(tagName));
            strings.add(getText(tagName).toLowerCase().trim());
        }
        return strings;
    }

    /**
     * This method is use to click pin tag by name
     *
     * @param text The text name
     */
    public void clickPinTag(String text) {
        commonLib.info("Clicking " + text + " Pinned Tag");
        By tagName = By.xpath(pageElements.pinTagByName + text + "')]");
        clickWithoutLoader(tagName);
        String message = getText(pageElements.popupMessage);
        commonLib.info("Response is " + message);
    }

    /*
    This Method will tell us customer profile page loaded or not?
     */
    public boolean isCustomerProfilePageLoaded() {
        waitTillLoaderGetsRemoved();
        boolean result = false;
        if (isVisible(pageElements.homeTabWidget)) {
            result = isEnabled(pageElements.homeTabWidget);
            commonLib.info("Is Customer Profile Page loaded ? " + result);
        }
        return result;
    }

    /*
    This Method is used to open the interaction page
     */
    public void clickOnInteractionIcon() {
        if (isVisible(pageElements.interactionIcon)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.interactionIcon);
            commonLib.info("Clicking on Interaction Icon");
        } else {
            commonLib.fail("Interaction Icon is NOT Visible", true);
        }
    }

    /*
    With this Method we will route to the View History tab adjacent to Home tab
     */
    public void goToViewHistory() {
        if (isVisible(pageElements.viewHistory)) {
            commonLib.info("Clicking on View History");
            clickAndWaitForLoaderToBeRemoved(pageElements.viewHistory);
        } else {
            commonLib.fail("Exception in method - goToViewHistory ", true);
        }
    }

    /**
     * This method is use to check pin tag visible or not
     *
     * @param text The text
     * @return true/false
     */
    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath(pageElements.pinTagByName + text + "')]");
        commonLib.info("Checking is " + text + " Pinned Tag Visible");
        return isElementVisible(tagName);
    }

    /**
     * This method is use to click on DA details tab
     */
    public void clickOnDADetailsTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.daDetailsTab);
        commonLib.info("Clicking on DA Details Tab");
    }

    /**
     * This method is use to click on more recharge history tab
     */
    public void clickOnRechargeHistoryTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.rechargeHistoryTab);
        commonLib.info("Clicking on Recharge History Tab");
    }

    /**
     * This method is use to click on more usage history tab
     */
    public void clickOnUsageHistoryTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.usageHistoryTab);
        commonLib.info("Clicking on Usage History Tab");
    }

    /**
     * This method is use to click on action
     */
    public void clickOnAction() {
        if (isVisible(pageElements.homeActionBtn) && isClickable(pageElements.homeActionBtn)) {
            commonLib.info("Clicking on Action drop down");
            clickWithoutLoader(pageElements.homeActionBtn);
        } else {
            commonLib.fail("Exception in method - clickOnAction ", true);
        }
    }

    /**
     * This method is use to click open send sms tab
     */
    public void openSendSMSTab() {
        commonLib.info("Clicking on Send SMS");
        clickAndWaitForLoaderToBeRemoved(pageElements.sendSMSAction);
        //Hard wait is added here because API to get templates details is not getting hit as we come on the tab
        commonLib.hardWait(10);
    }

    /**
     * This method is use to click send internet setting action
     */
    public void clickSendInternetSetting() {
        if (isVisible(pageElements.sendSettings)) {
            commonLib.info("Clicking on Send Internet Setting");
            clickAndWaitForLoaderToBeRemoved(pageElements.sendSettings);
        } else {
            commonLib.fail("Exception in method - clickSendInternetSetting", true);
        }
    }

    /**
     * This method is use to click reset me2u action
     */
    public void clickResetME2U() {
        if (isVisible(pageElements.resetME2UPassword)) {
            commonLib.info("Clicking on Reset ME2U Password");
            clickAndWaitForLoaderToBeRemoved(pageElements.resetME2UPassword);
        } else {
            commonLib.fail("Exception in method - clickResetME2U ", true);
        }
    }

    /**
     * This method is use to click cancel button
     */
    public void clickCancelBtn() {
        if (isVisible(pageElements.cancelBtn)) {
            commonLib.info("Going to click on Cancel Button");
            clickWithoutLoader(pageElements.cancelBtn);
            clickContinueButton();
        } else {
            commonLib.fail("Exception in method - clickCancelBtn", true);
            commonLib.info("Going to Close Modal through close Button");
            clickWithoutLoader(pageElements.closeBtn);
        }
    }

    /**
     * This method is use to click cancel button without continue button
     */
    public void clickCancelBtnWithOutContinue() {
        commonLib.info("Clicking on Close Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.cancelBtn);
    }

    /**
     * This method is use to click close button
     */
    public void clickCloseBtn() {
        commonLib.info("Clicking on Close Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeBtn);
    }

    /**
     * This method is use to open authenticate tab
     */
    public void openAuthTab() {
        commonLib.info("Opening Authentication tab for : " + getText(pageElements.simBarUnBar));
        clickAndWaitForLoaderToBeRemoved(pageElements.simBarUnBar);
    }

    /**
     * This method is use to check loan widget display or not
     *
     * @return true/false
     */
    public boolean isLoanWidgetDisplay() {
        commonLib.info("Checking Loan Widget Displayed");
        return isEnabled(pageElements.loanWidget);
    }

    /**
     * This method is use to check customer birthday icon display or not
     *
     * @return true/false
     */
    public boolean isCustomerBirthday() {
        commonLib.info("Checking Customer Birthday or not");
        return isEnabled(pageElements.birthdayIcon);
    }

    /**
     * This method is use to click continue button
     */
    public void clickContinueButton() {
        commonLib.info("Clicking on Continue button");
        if (isVisible(pageElements.continueBtn)) {
            clickWithoutLoader(pageElements.continueBtn);
        } else {
            commonLib.fail("Exception in method - clickContinueButton", true);
        }
    }

    /*
    This Method will let us know, if Change Service Class Option is visible under Actions drop down
     */
    public Boolean isChangeServiceClassOptionVisible() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(pageElements.changeServiceClass_btn);
        } catch (Exception e) {
            commonLib.error("ChangeServiceClass is not visible" + e.getMessage(), true);
        }
        return result;
    }

    /*
    This Method will open change service class tab from Actions drop down
     */
    public void openChangeServiceClassTab() {
        try {
            if (isVisible(pageElements.changeServiceClass_btn)) {
                clickAndWaitForLoaderToBeRemoved(pageElements.changeServiceClass_btn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = message  + e.getMessage();
            commonLib.fail(message, true);
        }
    }

    /*
    This Method will let us know, if Suspend SIM Option is visible under Actions drop down
     */
    public Boolean isSuspendSIMOptionVisible() {
        boolean result = false;
        try {
            result = isElementVisible(pageElements.suspendSIM);
        } catch (Exception e) {
            commonLib.error("Exception in Method -isSuspendSIMOptionVisible" + e.getMessage(), true);
        }
        return result;
    }

    /*
   This Method will let us know, if Reactivate SIM Option is visible under Actions drop down
    */
    public Boolean isReactivateSIMOptionVisible() {
        boolean result = false;
        try {
            result = isElementVisible(pageElements.reactivateSIM);
        } catch (Exception e) {
            commonLib.error("Exception in Method -isReactivateSIMOptionVisible" + e.getMessage(), true);
        }
        return result;
    }

    /**
     * This method is used to check suspend sim confirm message visible or not
     *
     * @return true/false
     */
    public Boolean isSuspendSIMConfirmMessageVisible() {
        final boolean state = isEnabled(pageElements.suspendSimConfirm);
        commonLib.info("Is SIM Suspend Confirm message Display: " + state);
        return state;
    }

    /**
     * This method is used to check reactivate sim confirm message visible or not
     *
     * @return true/false
     */
    public Boolean isReactivateSIMConfirmMessageVisible() {
        final boolean state = isElementVisible(pageElements.reActivateSimConfirm);
        commonLib.info("Is SIM Activate Confirm message Display: " + state);
        return state;
    }

    /*
   This Method will open Suspend SIM tab from Actions drop down
    */
    public void openSuspendSimTab() {
        try {
            if (isVisible(pageElements.suspendSIM)) {
                clickAndWaitForLoaderToBeRemoved(pageElements.suspendSIM);
            }
        } catch (Exception e) {
            commonLib.fail(message + e.getMessage(), true);
        }
    }

    /*
   This Method will open Reactivate SIM tab from Actions drop down
    */
    public void openReactivateSimTab() {
        try {
            if (isVisible(pageElements.reactivateSIM)) {
                clickAndWaitForLoaderToBeRemoved(pageElements.reactivateSIM);
            }
        } catch (Exception e) {
            commonLib.fail(message + e.getMessage(), true);
        }
    }

    /*
    This Method will let us know, if Suspend SIM Option is visible under Actions drop down
     */
    public Boolean isReactivationSIMOptionVisible() {
        boolean result = false;
        try {
            result = isElementVisible(pageElements.reactivateSIM);
        } catch (Exception e) {
            commonLib.error("Exception in Method -isReactivationSIMOptionVisible" + e.getMessage(), true);
        }
        return result;
    }

    /*
    This Method will verify if Service Class Tab opened or not
    */
    public Boolean isSuspendSIMModalOpened() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(pageElements.suspendSIM);
        } catch (Exception e) {
            commonLib.error("Suspend SIM Modal is not Visible, Exception in Method - isSuspendSIMModalOpened" + e.getMessage(), true);
        }
        return result;
    }

    /*
    This Method wil return us the main account balance or air time for a MSISDN
     */
    public MainAccountBalance getMainAccountBalance() {
        Plans plansAPI = api.accountPlansTest(TARIFF_PLAN_TEST_NUMBER);
        return plansAPI.getResult().getMainAccountBalance();
    }

    /*
    This Method will return last created interaction issue code from view history << interaction tab
     */
    public String goAndCheckFTRCreatedorNot() {
        goToViewHistory();
        ViewHistory history = new ViewHistory(driver);
        history.clickOnInteractionsTab();
        return history.getLastCreatedIssueCode();
    }

    /*
    This will will route you to Home Tab over customer profile page
     */
    public void goToHomeTab() {
        if (isVisible(pageElements.homePage)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.homePage);
        }
    }

    /**
     * This method is use to check auth tab opened or not
     *
     * @return true/false
     */
    public Boolean isAuthTabOpenedDoAction() {
        boolean tabOpened = false;
        if (isVisible(pageElements.authenticationModal)) {
            tabOpened = true;
            selectAuthCheckBox();
            clickAndWaitForLoaderToBeRemoved(pageElements.authenticateBtn);
            waitTillLoaderGetsRemoved();
        }
        return tabOpened;
    }

    /**
     * This method is use to select auth tab check box
     */
    public void selectAuthCheckBox() {
        int count = 0;
        final List<WebElement> elementsListfromBy = getElementsListFromBy(pageElements.authCheckBox);
        for (WebElement authQuestionList : elementsListfromBy) {
            clickAndWaitForLoaderToBeRemoved(pageElements.authCheckBox);
            count++;
            if (count == 3) {
                break;
            }
        }
    }

    /*
   This Method will click over issue details List
    */
    public void clickIssueDetails() {
        clickAndWaitForLoaderToBeRemoved(pageElements.issueDetailsReason);
    }

    /*
    This Method will select the Reason over issue details pop up
     */
    public void selectReason() {
        final List<WebElement> elementsListfromBy = getElementsListFromBy(pageElements.selectReason);
        for (WebElement dropDownList : elementsListfromBy) {
            if (dropDownList.getSize() != null) {
                clickAndWaitForLoaderToBeRemoved(pageElements.selectReason);
                break;
            }
        }
    }

    /**
     * This method is use to enter comment in issue pop up
     */
    public void enterCommentIssuePopUp() {
        setTextWithTimeStamp(pageElements.commentBox, "Comment By Automation");
    }

    /*
    This Method will let us know, submit Btn is enabled or not over issue details pop up
     */
    public Boolean isSubmitEnabled() {
        return isEnabled(pageElements.submitBtn);
    }

    public void performBarUnbarAction() {
        commonLib.info("Going to perform Sim Bar/Unbar");
        pages.getAuthTabPage().clickSelectReason();
        pages.getCustomerProfilePage().selectReasonFromDropdown();
        pages.getPinReset().enterComment(COMMENT);
        if (Boolean.TRUE.equals(isSubmitEnabled())) {
            clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
        }
    }

    /*
  This Method will provide us the text present over the Success or Failure modal
   */
    public String getModalText() {
        return getText(pageElements.modalSuccessFailureMsg);
    }

    /**
     * This method is used to click the cross icon of confirmation message window
     */
    public void clickCrossIcon() {
        commonLib.info("Clicking on Cross Icon");
        if (isVisible(pageElements.crossIcon)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.crossIcon);
        } else {
            commonLib.fail("Exception in method - clickCrossIcon", true);
        }
    }

    /**
     * This method is use to click Airtel money profile info locked section
     */
    public void clickAirtelStatusToUnlock() {
        commonLib.info("Clicking Tap to unlock on Airtel Status Info");
        clickAndWaitForLoaderToBeRemoved(pageElements.airtelMoneyLock);
    }

    /**
     * This method is used to select reason from dropdown while doing sim bar/unbar
     */
    public void selectReasonFromDropdown() {
        commonLib.info("Going to select Reason from dropdown");
        if (isVisible(pageElements.selectReasonFromDropdown)) ;
        clickWithoutLoader((pageElements.selectReasonFromDropdown));
    }

    /**
     * This method is used to open Sim Suspend/Reactivate option from Actions dropdown
     */
    public void openSimSuspendReactivateFromActions() {
        pages.getCustomerProfilePage().clickOnAction();
        if (isSuspendSIMOptionVisible()) {
            clickAndWaitForLoaderToBeRemoved(pageElements.suspendSIM);
            suspendSim++;
        }
        if (isReactivateSIMOptionVisible()) {
            clickAndWaitForLoaderToBeRemoved(pageElements.reactivateSIM);
            reactivateSim++;
        }
        pages.getDemoGraphicPage().selectPolicyQuestion();
        pages.getAuthTabPage().clickAuthBtn();
    }
}
