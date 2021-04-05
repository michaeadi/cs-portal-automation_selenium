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
    By searchNumber = By.xpath("//input[@type='search' and @placeholder='Search']");
    By interactionIcon = By.xpath("//div[@class='sub-header__divide--control--tab']");
    By actions = By.xpath("//span[@class=\"action-placeholder\"]");
    By simBar = By.xpath("//button[@class=\"db-action-menu-item mat-menu-item ng-star-inserted\"]");
    By pinTags = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"]");
    By viewHistory = By.xpath("//*[contains(text(),\"VIEW HISTORY\")]");
    By firstWidgetHeader = By.xpath("//div[@class=\"home-tab-container__left-widgets--widgets ng-star-inserted\"][1]//child::span[@class=\"card__card-header--label\"]");
    By thirdWidgetHeader = By.xpath("//div[@class=\"home-tab-container__left-widgets--widgets ng-star-inserted\"][2]//child::span[@class=\"card__card-header--label\"]");
    By secondWidgetHeader = By.xpath("//div[@class=\"home-tab-container__right-widgets--widgets ng-star-inserted\"][1]//child::span[@class=\"card__card-header--label\"]");
    By fourthWidgetHeader = By.xpath("//div[@class=\"home-tab-container__right-widgets--widgets ng-star-inserted\"][2]//child::span[@class=\"card__card-header--label\"]");
    By daDetailsTab = By.xpath("//div[contains(text(),'DA DETAILS')]");
    By usageHistoryTab = By.xpath("//div[contains(text(),'USAGE HISTORY')]");
    By rechargeHistoryTab = By.xpath("//div[contains(text(),'RECHARGE HISTORY')]");
    By homeActionBtn = By.xpath("//span[@class='action-placeholder']");
    By loanWidget = By.xpath("//span[contains(text(),'LOAN SERVICES')]//ancestor::div[@class=\"card widget ng-star-inserted\"]");
    By sendSMSAction = By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Send SMS')]");
    By simBarUnBar = By.xpath("//div[@class=\"mat-menu-content\"]//button[1]");
    By sendSettings = By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Send Internet Settings')]");
    By resetME2UPassword = By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Reset Me2U Password')]");
    By sendSettingTitle = By.xpath("//span[contains(text(),'Send Internet Settings')]");
    By resetME2Title = By.xpath("//span[contains(text(),'Reset Me2U Password')]");
    By noBtn = By.xpath("//button[@class=\"no-btn\"]");
    By closeBtn = By.xpath("//span[contains(text(),'Send Internet Settings')]//following-sibling::mat-icon[contains(text(),'close')]");
    By birthdayIcon = By.xpath("//span[@class='customer-icon-block']/img");
    By continueBtn = By.xpath("//span[contains(text(),'continue')]");

    public CustomerProfilePageElements customerProfilePageElements;
    public static final String TARIFF_PLAN_TEST_NUMBER = constants.getValue(ApplicationConstants.TARIFF_PLAN_TEST_NUMBER);

    public CustomerProfilePage(WebDriver driver) {
        super(driver);
        customerProfilePageElements = PageFactory.initElements(driver, CustomerProfilePageElements.class);
    }

    public String getFirstWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 1st Widget : " + readText(firstWidgetHeader));
        return readText(firstWidgetHeader);
    }

    public Boolean isSendInternetSettingTitle() {
        UtilsMethods.printInfoLog("Is Send Internet Setting Title Display: " + checkState(sendSettingTitle));
        return checkState(sendSettingTitle);
    }

    public Boolean isResetME2UPasswordTitle() {
        UtilsMethods.printInfoLog("Is Reset ME2U Password Title Display: " + checkState(resetME2Title));
        return checkState(resetME2Title);
    }

    public String getSecondWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 2nd Widget : " + readText(secondWidgetHeader));
        return readText(secondWidgetHeader);
    }

    public String getThirdWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 3rd Widget : " + readText(thirdWidgetHeader));
        return readText(thirdWidgetHeader);
    }

    public String getFourthWidgetHeader() {
        UtilsMethods.printInfoLog("Getting header of 4th Widget : " + readText(fourthWidgetHeader));
        return readText(fourthWidgetHeader);
    }

    public List<String> getPinnedTagTexts() {
        List<String> strings = new ArrayList<String>();
        List<WebElement> webElements = returnListOfElement(pinTags);
        log.info("Size: " + webElements.size());
        for (int i = 1; i <= webElements.size(); i++) {
            By tagName = By.xpath("//div[@class='sub-header__divide--control']//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"][" + i + "]");
            log.info("Text: " + readText(tagName).toLowerCase().trim());
            UtilsMethods.printInfoLog("Reading pinned tag name: " + readText(tagName));
            strings.add(readText(tagName).toLowerCase().trim());
        }
        return strings;
    }

    public CustomerInteractionsSearchPOM clickPinTag(String text) {
        UtilsMethods.printInfoLog("Clicking " + text + " Pinned Tag");
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        click(tagName);
        return new CustomerInteractionsSearchPOM(driver);
    }


    public boolean isPageLoaded() {
        boolean check = checkState(searchNumber);
        UtilsMethods.printInfoLog("Checking that is Customer Interaction Page is loaded : " + check);
        return check;
    }

    public InteractionsPOM clickOnInteractionIcon() {
        waitTillLoaderGetsRemoved();
        click(interactionIcon);
        UtilsMethods.printInfoLog("Clicking on Interactions Icon");
        return new InteractionsPOM(driver);
    }

    /*
    With this Method we will route to the View History tab adjacent to Home tab
     */
    public ViewHistory clickOnViewHistory() {
        click(viewHistory);
        UtilsMethods.printInfoLog("Clicking on View History");
        return new ViewHistory(driver);
    }


    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        UtilsMethods.printInfoLog("Checking is " + text + " Pinned Tag Visible");
        return isElementVisible(tagName);
    }

    public DADetailsPOM clickOnDADetailsTab() {
        click(daDetailsTab);
        UtilsMethods.printInfoLog("Clicking on DA Details Tab");
        return new DADetailsPOM(driver);
    }

    public MoreRechargeHistoryPOM clickOnRechargeHistoryTab() {
        click(rechargeHistoryTab);
        UtilsMethods.printInfoLog("Clicking on Recharge History Tab");
        return new MoreRechargeHistoryPOM(driver);
    }

    public MoreUsageHistoryPOM clickOnUsageHistoryTab() {
        click(usageHistoryTab);
        UtilsMethods.printInfoLog("Clicking on Usage History Tab");
        return new MoreUsageHistoryPOM(driver);
    }

    public void clickOnAction() {
        UtilsMethods.printInfoLog("Clicking on Home Action button");
        click(homeActionBtn);
    }

    public SendSMSPOM openSendSMSTab() {
        UtilsMethods.printInfoLog("Clicking on Send SMS");
        click(sendSMSAction);
        return new SendSMSPOM(driver);
    }

    public void clickSendSetting() {
        UtilsMethods.printInfoLog("Clicking on Send SMS Setting");
        click(sendSettings);
    }

    public void clickResetME2U() {
        UtilsMethods.printInfoLog("Clicking on Reset ME2U Password");
        click(resetME2UPassword);
    }

    public void clickNoBtn() {
        UtilsMethods.printInfoLog("Clicking on No Button");
        click(noBtn);
    }

    public void clickCloseBtn() {
        UtilsMethods.printInfoLog("Clicking on Close Button");
        click(closeBtn);
    }

    public AuthenticationTabPOM openAuthTab() {
        UtilsMethods.printInfoLog("Opening Authentication tab for : " + readText(simBarUnBar));
        click(simBarUnBar);
        return new AuthenticationTabPOM(driver);
    }

    public boolean isLoanWidgetDisplay() {
        UtilsMethods.printInfoLog("Checking Loan Widget Displayed");
        return checkState(loanWidget);
    }

    public boolean isCustomerBirthday() {
        UtilsMethods.printInfoLog("Checking Customer Birthday or not");
        return checkState(birthdayIcon);
    }

    public void clickContinueButton() {
        log.info("Clicking on Continue button");
        UtilsMethods.printInfoLog("Clicking on Continue button");
        click(continueBtn);
    }

    /*
    This Method will let us know, if Change Service Class Option is visible under Actions drop down
     */
    public Boolean isChangeServiceClassOptionVisible() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(customerProfilePageElements.changeServiceClass_btn);
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
            if (isVisible(customerProfilePageElements.changeServiceClass_btn)) {
                click(customerProfilePageElements.changeServiceClass_btn);
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
            result = elementVisibleWithExplictWait(customerProfilePageElements.suspendSIM);
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
            if (isVisible(customerProfilePageElements.suspendSIM)) {
                click(customerProfilePageElements.suspendSIM);
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
            result = elementVisibleWithExplictWait(customerProfilePageElements.reactivationSIM);
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
            result = elementVisibleWithExplictWait(customerProfilePageElements.suspendSIM);
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
        ViewHistory history = new ViewHistory(driver);
        history.clickOnInteractionsTab();
        return history.getLastCreatedIssueCode();
    }

    /*
    This will will route you to Home Tab over customer profile page
     */
    public void goToHomeTab() {
        if (isVisible(customerProfilePageElements.homePage)) {
            click(customerProfilePageElements.homePage);
        }
    }

    public Boolean isAuthTabOpenedDoAction() {
        boolean tabOpened = false;
        if (isVisible(customerProfilePageElements.authenticationModal)) {
            tabOpened = true;
            selectAuthCheckBox();
            click(customerProfilePageElements.authenticateBtn);
            waitTillLoaderGetsRemoved();
        }
        return tabOpened;
    }

    public void selectAuthCheckBox() {
        int count = 0;
        final List<WebElement> elementsListfromBy = getElementsListFromBy(customerProfilePageElements.authCheckBox);
        for (WebElement authQuestionList : elementsListfromBy) {
            click(customerProfilePageElements.authCheckBox);
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
        click(customerProfilePageElements.issueDetailsReason);
    }

    /*
    This Method will select the Reason over issue details pop up
     */
    public void selectReason() {
        final List<WebElement> elementsListfromBy = getElementsListFromBy(customerProfilePageElements.selectReason);
        for (WebElement dropDownList : elementsListfromBy) {
            if (dropDownList.getSize() != null) {
                click(customerProfilePageElements.selectReason);
                break;
            }
        }
    }

    public void enterCommentIssuePopUp() {
        setTextWithTimeStamp(customerProfilePageElements.commentBox, "Comment By Automation");
    }

    /*
    This Method will let us know, submit Btn is enabled or not over issue details pop up
     */
    public Boolean isSubmitEnabled() {
        return checkState(customerProfilePageElements.submitBtn);
    }

    public void doSIMBarAction() {
        clickIssueDetails();
        selectReason();
        enterCommentIssuePopUp();
        if (Boolean.TRUE.equals(isSubmitEnabled())) {
            click(customerProfilePageElements.submitBtn);
        }
    }

    /*
  This Method will provide us the text present over the Success or Failure modal
   */
    public String getModalText() {
        return readText(customerProfilePageElements.modalSuccessFailureMsg);
    }
}
