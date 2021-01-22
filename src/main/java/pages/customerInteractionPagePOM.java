package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class customerInteractionPagePOM extends BasePage {
    By searchNumber=By.xpath("//div[@class=\"customer-details\"]//div[@class=\"user-left-side\"]/div/div[1]//input");
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
    By sendSettings=By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Send Internet Settings')]");
    By resetME2UPassword=By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Reset Me2U Password')]");
    By sendSettingTitle=By.xpath("//span[contains(text(),'Send Internet Settings')]");
    By resetME2Title=By.xpath("//span[contains(text(),'Reset Me2U Password')]");
    By noBtn=By.xpath("//button[@class=\"no-btn\"]");
    By closeBtn=By.xpath("//span[contains(text(),'Send Internet Settings')]//following-sibling::mat-icon[contains(text(),'close')]");

    public customerInteractionPagePOM(WebDriver driver) {
        super(driver);
    }

    public String getFirstWidgetHeader() {
        log.info("Getting header of 1st Widget : " + readText(firstWidgetHeader));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header of 1st Widget : " + readText(firstWidgetHeader));
        return readText(firstWidgetHeader);
    }

    public Boolean isSendInternetSettingTitle(){
        printInfoLog("Is Send Internet Setting Title Display: "+checkState(sendSettingTitle));
        return checkState(sendSettingTitle);
    }

    public Boolean isResetME2UPasswordTitle(){
        printInfoLog("Is Reset ME2U Password Title Display: "+checkState(resetME2Title));
        return checkState(resetME2Title);
    }

    public String getSecondWidgetHeader() {
        log.info("Getting header of 2nd Widget : " + readText(secondWidgetHeader));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header of 2nd Widget : " + readText(secondWidgetHeader));
        return readText(secondWidgetHeader);
    }

    public String getThirdWidgetHeader() {
        log.info("Getting header of 3rd Widget : " + readText(thirdWidgetHeader));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header of 3rd Widget : " + readText(thirdWidgetHeader));
        return readText(thirdWidgetHeader);
    }

    public String getFourthWidgetHeader() {
        log.info("Getting header of 4th Widget : " + readText(fourthWidgetHeader));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header of 4th Widget : " + readText(fourthWidgetHeader));
        return readText(fourthWidgetHeader);
    }

    public List<String> getPinnedTagTexts() {
        List<String> strings = new ArrayList<String>();
        List<WebElement> webElements = returnListOfElement(pinTags);
        System.out.println("Size: " + webElements.size());
        for (int i = 1; i <= webElements.size(); i++) {
            By tagName = By.xpath("//div[@class='sub-header__divide--control']//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"][" + i + "]");
            System.out.println("Text: " + readText(tagName).toLowerCase().trim());
            log.info("Reading pinned tag name: " + readText(tagName));
            ExtentTestManager.getTest().log(LogStatus.INFO, "Reading pinned tag name: " + readText(tagName));
            strings.add(readText(tagName).toLowerCase().trim());
        }
        return strings;
    }

    public customerInteractionsSearchPOM clickPinTag(String text) {
        log.info("Clicking on " + text + " Pinned Tag");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking " + text + " Pinned Tag");
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        click(tagName);
        return new customerInteractionsSearchPOM(driver);
    }


    public boolean isPageLoaded() {
        waitVisibility(searchNumber);
        log.info("Checking that is Customer Interaction Page is loaded : " + checkState(searchNumber));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Customer Interaction Page is loaded : " + checkState(searchNumber));
        return checkState(searchNumber);
    }

    public InteractionsPOM clickOnInteractionIcon() {
        waitTillLoaderGetsRemoved();
        click(interactionIcon);
        log.info("Clicking on Interactions Icon");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Interactions Icon");
        return new InteractionsPOM(driver);
    }

    public viewHistoryPOM clickOnViewHistory() {
        click(viewHistory);
        log.info("Clicking on View History");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on View History");
        return new viewHistoryPOM(driver);
    }


    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"" + text + "\")]");
        log.info("Checking is " + text + " Pinned Tag Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is " + text + " Pinned Tag Visible");
        return isElementVisible(tagName);
    }

    public DADetailsPOM clickOnDADetailsTab() {
        click(daDetailsTab);
        log.info("Clicking on DA Details Tab");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on DA Details Tab");
        return new DADetailsPOM(driver);
    }

    public MoreRechargeHistoryPOM clickOnRechargeHistoryTab() {
        click(rechargeHistoryTab);
        log.info("Clicking on Recharge History Tab");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Recharge History Tab");
        return new MoreRechargeHistoryPOM(driver);
    }

    public MoreUsageHistoryPOM clickOnUsageHistoryTab() {
        click(usageHistoryTab);
        log.info("Clicking on Usage History Tab");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Usage History Tab");
        return new MoreUsageHistoryPOM(driver);
    }

    public void clickOnAction() {
        log.info("Clicking on Home Action button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Home Action button");
        click(homeActionBtn);
    }

    public SendSMSPOM openSendSMSTab() {
        log.info("Clicking on Send SMS");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Send SMS");
        click(sendSMSAction);
        return new SendSMSPOM(driver);
    }

    public void clickSendSetting() {
        printInfoLog("Clicking on Send SMS Setting");
        click(sendSettings);
    }

    public void clickResetME2U() {
        printInfoLog("Clicking on Reset ME2U Password");
        click(resetME2UPassword);
    }

    public void clickNoBtn() {
        printInfoLog("Clicking on No Button");
        click(noBtn);
    }

    public void clickCloseBtn() {
        printInfoLog("Clicking on Close Button");
        click(closeBtn);
    }

    public AuthenticationTabPOM openAuthTab() {
        printInfoLog("Opening Authentication tab for : " + readText(simBarUnBar));
        click(simBarUnBar);
        return new AuthenticationTabPOM(driver);
    }

    public boolean isLoanWidgetDisplay(){
        printInfoLog("Checking Loan Widget Displayed");
        return checkState(loanWidget);
    }


}
