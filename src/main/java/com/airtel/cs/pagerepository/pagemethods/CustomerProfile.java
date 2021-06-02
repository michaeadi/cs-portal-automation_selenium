package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.pagerepository.pageelements.CustomerProfilePage;
import com.airtel.cs.pojo.response.MainAccountBalance;
import com.airtel.cs.pojo.response.PlansPOJO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerProfile extends BasePage {

    public CustomerProfilePage pageElements;
    public static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);

    public CustomerProfile(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CustomerProfilePage.class);
    }

    public String getFirstWidgetHeader() {
        final String text = getText(pageElements.firstWidgetHeader);
        commonLib.info("Getting header of 1st Widget : " + text);
        return text;
    }

    public Boolean isSendInternetSettingTitleVisible() {
        final boolean state = isEnabled(pageElements.sendSettingTitle);
        commonLib.info("Is Send Internet Setting Title Display: " + state);
        return state;
    }

    public Boolean isResetME2UPasswordTitle() {
        final boolean state = isEnabled(pageElements.resetME2Title);
        commonLib.info("Is Reset ME2U Password Title Display: " + state);
        waitTillLoaderGetsRemoved();
        return state;
    }

    public String getSecondWidgetHeader() {
        final String text = getText(pageElements.secondWidgetHeader);
        commonLib.info("Getting header of 2nd Widget : " + text);
        return text;
    }

    public String getThirdWidgetHeader() {
        final String text = getText(pageElements.thirdWidgetHeader);
        commonLib.info("Getting header of 3rd Widget : " + text);
        return text;
    }

    public String getFourthWidgetHeader() {
        final String text = getText(pageElements.fourthWidgetHeader);
        commonLib.info("Getting header of 4th Widget : " + text);
        return text;
    }

    public List<String> getPinnedTagTexts() {
        List<String> strings = new ArrayList<>();
        List<WebElement> webElements = returnListOfElement(pageElements.pinTags);
        log.info("Size: " + webElements.size());
        for (int i = 1; i <= webElements.size(); i++) {
            By tagName = By.xpath("//div[@class='sub-header__divide--control']//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"][" + i + "]");
            log.info("Text: " + getText(tagName).toLowerCase().trim());
            commonLib.info("Reading pinned tag name: " + getText(tagName));
            strings.add(getText(tagName).toLowerCase().trim());
        }
        return strings;
    }

    public void clickPinTag(String text) {
        commonLib.info("Clicking " + text + " Pinned Tag");
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        clickAndWaitForLoaderToBeRemoved(tagName);
    }

    /*
    This Method will tell us customer profile page loaded or not?
     */
    public boolean isCustomerProfilePageLoaded() {
        boolean result = false;
        if (isVisible(pageElements.searchNumber)) {
            result = isEnabled(pageElements.searchNumber);
            commonLib.info("Is Customer Profile Page loaded ? " + result);
        }
        return result;
    }

    public void clickOnInteractionIcon() {
        waitTillLoaderGetsRemoved();
        clickAndWaitForLoaderToBeRemoved(pageElements.interactionIcon);
        commonLib.info("Clicking on Interactions Icon");
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

    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        commonLib.info("Checking is " + text + " Pinned Tag Visible");
        return isElementVisible(tagName);
    }

    public DADetails clickOnDADetailsTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.daDetailsTab);
        commonLib.info("Clicking on DA Details Tab");
        return new DADetails(driver);
    }

    public MoreRechargeHistory clickOnRechargeHistoryTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.rechargeHistoryTab);
        commonLib.info("Clicking on Recharge History Tab");
        return new MoreRechargeHistory(driver);
    }

    public MoreUsageHistory clickOnUsageHistoryTab() {
        clickAndWaitForLoaderToBeRemoved(pageElements.usageHistoryTab);
        commonLib.info("Clicking on Usage History Tab");
        return new MoreUsageHistory(driver);
    }

    public void clickOnAction() {
        if (isVisible(pageElements.homeActionBtn)) {
            commonLib.info("Clicking on Action drop down");
            clickAndWaitForLoaderToBeRemoved(pageElements.homeActionBtn);
        } else {
            commonLib.fail("Exception in method - clickOnAction ", true);
        }
    }

    public void openSendSMSTab() {
        commonLib.info("Clicking on Send SMS");
        clickAndWaitForLoaderToBeRemoved(pageElements.sendSMSAction);
    }

    public void clickSendInternetSetting() {
        if (isVisible(pageElements.sendSettings)) {
            commonLib.info("Clicking on Send Internet Setting");
            clickAndWaitForLoaderToBeRemoved(pageElements.sendSettings);
        } else {
            commonLib.fail("Exception in method - clickSendInternetSetting", true);
        }
    }

    public void clickResetME2U() {
        if (isVisible(pageElements.resetME2UPassword)) {
            commonLib.info("Clicking on Reset ME2U Password");
            clickAndWaitForLoaderToBeRemoved(pageElements.resetME2UPassword);
        } else {
            commonLib.fail("Exception in method - clickResetME2U ", true);
        }
    }

    public void clickCancelBtn() {
        if (isVisible(pageElements.cancelBtn)) {
            //waitTillLoaderGetsRemoved();
            commonLib.info("Going to click on Cancel Button");
            clickWithoutLoader(pageElements.cancelBtn);
            clickContinueButton();
        } else {
            commonLib.fail("Exception in method - clickCancelBtn", true);
            commonLib.info("Going to Close Modal through close Button");
            clickWithoutLoader(pageElements.closeBtn);
        }
    }

    public void clickCloseBtn() {
        commonLib.info("Clicking on Close Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeBtn);
    }

    public void openAuthTab() {
        commonLib.info("Opening Authentication tab for : " + getText(pageElements.simBarUnBar));
        clickAndWaitForLoaderToBeRemoved(pageElements.simBarUnBar);
    }

    public boolean isLoanWidgetDisplay() {
        commonLib.info("Checking Loan Widget Displayed");
        return isEnabled(pageElements.loanWidget);
    }

    public boolean isCustomerBirthday() {
        commonLib.info("Checking Customer Birthday or not");
        return isEnabled(pageElements.birthdayIcon);
    }

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
            log.error("ChangeServiceClass is not visible", e);
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
            message = message + "</br>" + e.getMessage();
            commonLib.fail(message, true);
        }
    }

    /*
    This Method will let us know, if Suspend SIM Option is visible under Actions drop down
     */
    public Boolean isSuspendSIMOptionVisible() {
        boolean result = false;
        final String simStatus = pages.getDemoGraphicPage().getGSMStatus();
        if (StringUtils.equalsIgnoreCase(simStatus, "Active")) {
            try {
                result = elementVisibleWithExplictWait(pageElements.suspendSIM);
            } catch (Exception e) {
                log.error("Suspend SIM Option is not visible, Exception in Method -isSuspendSIMOptionVisible", e);
            }
        } else {
            commonLib.error("GSM SIM Status is NOT Active and is - " + simStatus);
        }
        return result;
    }

    /*
   This Method will open Suspend SIM tab from Actions drop down
    */
    public void openSuspendSIMTab() {
        try {
            if (isVisible(pageElements.suspendSIM)) {
                clickAndWaitForLoaderToBeRemoved(pageElements.suspendSIM);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = message + "</br>" + e.getMessage();
            commonLib.fail(message, true);
        }
    }

    /*
    This Method will let us know, if Suspend SIM Option is visible under Actions drop down
     */
    public Boolean isReactivationSIMOptionVisible() {
        boolean result = false;
        final String simStatus = pages.getDemoGraphicPage().getGSMStatus();
        if (StringUtils.equalsIgnoreCase(simStatus, "Suspended")) {
            try {
                result = elementVisibleWithExplictWait(pageElements.reactivationSIM);
            } catch (Exception e) {
                log.error("Reactivation SIM Option is not visible, Exception in Method -isReactivationSIMOptionVisible", e);
            }
        } else {
            commonLib.error("GSM SIM Status is NOT Suspended and is - " + simStatus);
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
            log.error("Suspend SIM Modal is not Visible, Exception in Method - isSuspendSIMModalOpened", e);
        }
        return result;
    }

    /*
    This Method wil return us the main account balance or air time for a MSISDN
     */
    public MainAccountBalance getMainAccountBalance() {
        PlansPOJO plansAPI = api.accountPlansTest(TARIFF_PLAN_TEST_NUMBER);
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

    public void enterCommentIssuePopUp() {
        setTextWithTimeStamp(pageElements.commentBox, "Comment By Automation");
    }

    /*
    This Method will let us know, submit Btn is enabled or not over issue details pop up
     */
    public Boolean isSubmitEnabled() {
        return isEnabled(pageElements.submitBtn);
    }

    public void doSIMBarAction() {
        clickIssueDetails();
        selectReason();
        enterCommentIssuePopUp();
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
}
