package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.pagerepository.pageelements.CustomerProfilePageElements;
import com.airtel.cs.pojo.PlansPOJO;
import com.airtel.cs.pojo.MainAccountBalance;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerProfilePage extends BasePage {

    public CustomerProfilePageElements pageElements;
    public static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);

    public CustomerProfilePage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CustomerProfilePageElements.class);
    }

    public String getFirstWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 1st Widget : " + readText(pageElements.firstWidgetHeader));
        return readText(pageElements.firstWidgetHeader);
    }

    public Boolean isSendInternetSettingTitle() {
        UtilsMethods.printInfoLog("Is Send Internet Setting Title Display: " + checkState(pageElements.sendSettingTitle));
        return checkState(pageElements.sendSettingTitle);
    }

    public Boolean isResetME2UPasswordTitle() {
        UtilsMethods.printInfoLog("Is Reset ME2U Password Title Display: " + checkState(pageElements.resetME2Title));
        return checkState(pageElements.resetME2Title);
    }

    public String getSecondWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 2nd Widget : " + readText(pageElements.secondWidgetHeader));
        return readText(pageElements.secondWidgetHeader);
    }

    public String getThirdWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 3rd Widget : " + readText(pageElements.thirdWidgetHeader));
        return readText(pageElements.thirdWidgetHeader);
    }

    public String getFourthWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 4th Widget : " + readText(pageElements.fourthWidgetHeader));
        return readText(pageElements.fourthWidgetHeader);
    }

    public List<String> getPinnedTagTexts() {
        List<String> strings = new ArrayList<String>();
        List<WebElement> webElements = returnListOfElement(pageElements.pinTags);
        log.info("Size: " + webElements.size());
        for (int i = 1; i <= webElements.size(); i++) {
            By tagName = By.xpath("//div[@class='sub-header__divide--control']//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"][" + i + "]");
            log.info("Text: " + readText(tagName).toLowerCase().trim());
            UtilsMethods.printInfoLog("Reading pinned tag name: " + readText(tagName));
            strings.add(readText(tagName).toLowerCase().trim());
        }
        return strings;
    }

    public void clickPinTag(String text) {
        UtilsMethods.printInfoLog("Clicking " + text + " Pinned Tag");
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        click(tagName);
    }


    public boolean isPageLoaded() {
        boolean check = checkState(pageElements.searchNumber);
        UtilsMethods.printInfoLog("Checking that is Customer Interaction Page is loaded : " + check);
        return check;
    }

    public void clickOnInteractionIcon() {
        waitTillLoaderGetsRemoved();
        click(pageElements.interactionIcon);
        UtilsMethods.printInfoLog("Clicking on Interactions Icon");
    }

    /*
    With this Method we will route to the View History tab adjacent to Home tab
     */
    public void clickOnViewHistory() {
        click(pageElements.viewHistory);
        UtilsMethods.printInfoLog("Clicking on View History");
    }


    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        UtilsMethods.printInfoLog("Checking is " + text + " Pinned Tag Visible");
        return isElementVisible(tagName);
    }

    public DADetailsPage clickOnDADetailsTab() {
        click(pageElements.daDetailsTab);
        UtilsMethods.printInfoLog("Clicking on DA Details Tab");
        return new DADetailsPage(driver);
    }

    public MoreRechargeHistoryPage clickOnRechargeHistoryTab() {
        click(pageElements.rechargeHistoryTab);
        UtilsMethods.printInfoLog("Clicking on Recharge History Tab");
        return new MoreRechargeHistoryPage(driver);
    }

    public MoreUsageHistoryPage clickOnUsageHistoryTab() {
        click(pageElements.usageHistoryTab);
        UtilsMethods.printInfoLog("Clicking on Usage History Tab");
        return new MoreUsageHistoryPage(driver);
    }

    public void clickOnAction() {
        UtilsMethods.printInfoLog("Clicking on Home Action button");
        click(pageElements.homeActionBtn);
    }

    public void openSendSMSTab() {
        UtilsMethods.printInfoLog("Clicking on Send SMS");
        click(pageElements.sendSMSAction);
    }

    public void clickSendSetting() {
        UtilsMethods.printInfoLog("Clicking on Send SMS Setting");
        click(pageElements.sendSettings);
    }

    public void clickResetME2U() {
        UtilsMethods.printInfoLog("Clicking on Reset ME2U Password");
        click(pageElements.resetME2UPassword);
    }

    public void clickNoBtn() {
        UtilsMethods.printInfoLog("Clicking on No Button");
        click(pageElements.noBtn);
    }

    public void clickCloseBtn() {
        UtilsMethods.printInfoLog("Clicking on Close Button");
        click(pageElements.closeBtn);
    }

    public AuthTabPage openAuthTab() {
        UtilsMethods.printInfoLog("Opening Authentication tab for : " + readText(pageElements.simBarUnBar));
        click(pageElements.simBarUnBar);
        return new AuthTabPage(driver);
    }

    public boolean isLoanWidgetDisplay() {
        UtilsMethods.printInfoLog("Checking Loan Widget Displayed");
        return checkState(pageElements.loanWidget);
    }

    public boolean isCustomerBirthday() {
        UtilsMethods.printInfoLog("Checking Customer Birthday or not");
        return checkState(pageElements.birthdayIcon);
    }

    public void clickContinueButton() {
        log.info("Clicking on Continue button");
        UtilsMethods.printInfoLog("Clicking on Continue button");
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
            Message = Message + "</br>" + e.getMessage();
            commonLib.fail(Message, true);
        }
    }

    /*
    This Method will let us know, if Suspend SIM Option is visible under Actions drop down
     */
    public Boolean isSuspendSIMOptionVisible() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(pageElements.suspendSIM);
        } catch (Exception e) {
            log.error("Suspend SIM Option is not visible, Exception in Method -isSuspendSIMOptionVisible", e);
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
            Message = Message + "</br>" + e.getMessage();
            commonLib.fail(Message, true);
        }
    }

    /*
    This Method will let us know, if Suspend SIM Option is visible under Actions drop down
     */
    public Boolean isReactivationSIMOptionVisible() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(pageElements.reactivationSIM);
        } catch (Exception e) {
            log.error("Reactivation SIM Option is not visible, Exception in Method -isReactivationSIMOptionVisible", e);
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
        clickOnViewHistory();
        ViewHistoryPage history = new ViewHistoryPage(driver);
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
        return readText(pageElements.modalSuccessFailureMsg);
    }
}
