package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.pagerepository.pageelements.CustomerProfilePage;
import com.airtel.cs.pojo.MainAccountBalance;
import com.airtel.cs.pojo.PlansPOJO;
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

    public Boolean isSendInternetSettingTitle() {
        final boolean state = checkState(pageElements.sendSettingTitle);
        commonLib.info("Is Send Internet Setting Title Display: " + state);
        return state;
    }

    public Boolean isResetME2UPasswordTitle() {
        final boolean state = checkState(pageElements.resetME2Title);
        commonLib.info("Is Reset ME2U Password Title Display: " + state);
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
        click(tagName);
    }


    public boolean isPageLoaded() {
        boolean check = checkState(pageElements.searchNumber);
        commonLib.info("Checking that is Customer Interaction Page is loaded : " + check);
        return check;
    }

    public void clickOnInteractionIcon() {
        waitTillLoaderGetsRemoved();
        click(pageElements.interactionIcon);
        commonLib.info("Clicking on Interactions Icon");
    }

    /*
    With this Method we will route to the View History tab adjacent to Home tab
     */
    public void goToViewHistory() {
        click(pageElements.viewHistory);
        commonLib.info("Clicking on View History");
    }

    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        commonLib.info("Checking is " + text + " Pinned Tag Visible");
        return isElementVisible(tagName);
    }

    public DADetails clickOnDADetailsTab() {
        click(pageElements.daDetailsTab);
        commonLib.info("Clicking on DA Details Tab");
        return new DADetails(driver);
    }

    public MoreRechargeHistory clickOnRechargeHistoryTab() {
        click(pageElements.rechargeHistoryTab);
        commonLib.info("Clicking on Recharge History Tab");
        return new MoreRechargeHistory(driver);
    }

    public MoreUsageHistory clickOnUsageHistoryTab() {
        click(pageElements.usageHistoryTab);
        commonLib.info("Clicking on Usage History Tab");
        return new MoreUsageHistory(driver);
    }

    public void clickOnAction() {
        commonLib.info("Clicking on Home Action button");
        click(pageElements.homeActionBtn);
    }

    public void openSendSMSTab() {
        commonLib.info("Clicking on Send SMS");
        click(pageElements.sendSMSAction);
    }

    public void clickSendSetting() {
        commonLib.info("Clicking on Send SMS Setting");
        click(pageElements.sendSettings);
    }

    public void clickResetME2U() {
        commonLib.info("Clicking on Reset ME2U Password");
        click(pageElements.resetME2UPassword);
    }

    public void clickNoBtn() {
        commonLib.info("Clicking on No Button");
        click(pageElements.noBtn);
    }

    public void clickCloseBtn() {
        commonLib.info("Clicking on Close Button");
        click(pageElements.closeBtn);
    }

    public AuthTab openAuthTab() {
        commonLib.info("Opening Authentication tab for : " + getText(pageElements.simBarUnBar));
        click(pageElements.simBarUnBar);
        return new AuthTab(driver);
    }

    public boolean isLoanWidgetDisplay() {
        commonLib.info("Checking Loan Widget Displayed");
        return checkState(pageElements.loanWidget);
    }

    public boolean isCustomerBirthday() {
        commonLib.info("Checking Customer Birthday or not");
        return checkState(pageElements.birthdayIcon);
    }

    public void clickContinueButton() {
        log.info("Clicking on Continue button");
        commonLib.info("Clicking on Continue button");
        click(pageElements.continueBtn);
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
                click(pageElements.changeServiceClass_btn);
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
        final String simStatus = pages.getDemoGraphicPage().getSIMStatus();
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
                click(pageElements.suspendSIM);
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
        final String simStatus = pages.getDemoGraphicPage().getSIMStatus();
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
            click(pageElements.homePage);
        }
    }

    public Boolean isAuthTabOpenedDoAction() {
        boolean tabOpened = false;
        if (isVisible(pageElements.authenticationModal)) {
            tabOpened = true;
            selectAuthCheckBox();
            click(pageElements.authenticateBtn);
            waitTillLoaderGetsRemoved();
        }
        return tabOpened;
    }

    public void selectAuthCheckBox() {
        int count = 0;
        final List<WebElement> elementsListfromBy = getElementsListFromBy(pageElements.authCheckBox);
        for (WebElement authQuestionList : elementsListfromBy) {
            click(pageElements.authCheckBox);
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
        click(pageElements.issueDetailsReason);
    }

    /*
    This Method will select the Reason over issue details pop up
     */
    public void selectReason() {
        final List<WebElement> elementsListfromBy = getElementsListFromBy(pageElements.selectReason);
        for (WebElement dropDownList : elementsListfromBy) {
            if (dropDownList.getSize() != null) {
                click(pageElements.selectReason);
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
        return checkState(pageElements.submitBtn);
    }

    public void doSIMBarAction() {
        clickIssueDetails();
        selectReason();
        enterCommentIssuePopUp();
        if (Boolean.TRUE.equals(isSubmitEnabled())) {
            click(pageElements.submitBtn);
        }
    }

    /*
  This Method will provide us the text present over the Success or Failure modal
   */
    public String getModalText() {
        return getText(pageElements.modalSuccessFailureMsg);
    }
}
