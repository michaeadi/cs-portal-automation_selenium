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
    By searchNumber = By.xpath("//input[@placeholder=\"Search\"]");
    By customerName = By.xpath("//p[@class=\"fw-bold name ng-star-inserted\"]");
    By customerDOB = By.xpath("//span[@class=\"fw-normal dob ng-star-inserted\"]/span[@class=\"ng-star-inserted\"]");
    By activationDate = By.xpath("//div[@class=\"col-md-4 border-right1 ng-star-inserted\"][1]//child::span[1]");
    //    By activationDate = By.xpath("//div[@class=\"col-md-4 border-right1 ng-star-inserted\"][1]//child::span");
    By activationTime = By.xpath("//span[@class='sp-block ng-star-inserted']");
    By simNumber = By.xpath("//div[@class=\"col-md-4 border-right1 ng-star-inserted\"][2]//child::p[1]/span");
    By simType = By.xpath("//p[@class=\"user-sim-device ng-star-inserted\"]/span[@class=\"ng-star-inserted\"]");
    By PUK1 = By.xpath("//p[@class=\"puk-show\"][1]/span[contains(text(),\"PUK1\")]//following-sibling::span");
    By PUK2 = By.xpath("//p[@class=\"puk-show\"][1]/span[contains(text(),\"PUK2\")]//following-sibling::span");
    By idType = By.xpath("//div[@class=\"col-md-3 border-right1 ng-star-inserted\"][4]//child::p[@class=\"user-title\"]");
    By idNumber = By.xpath("//div[@class=\"col-md-3 border-right1 ng-star-inserted\"][4]//child::p[@class=\"user-details-para\"]/span");
    By interactionIcon = By.xpath("//div[@class='sub-header__divide--control--tab']");
    By actions = By.xpath("//span[@class=\"action-placeholder\"]");
    By simBar = By.xpath("//button[@class=\"db-action-menu-item mat-menu-item ng-star-inserted\"]");
    By pinTags = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"]");
    By viewHistory = By.xpath("//div[@class=\"mat-tab-label-content\" and contains(text(),\"VIEW HISTORY\")]");
    By firstWidgetHeader = By.xpath("//div[@class=\"home-tab-container__left-widgets--widgets ng-star-inserted\"][1]//child::span[@class=\"card__card-header--label\"]");
    By thirdWidgetHeader = By.xpath("//div[@class=\"home-tab-container__left-widgets--widgets ng-star-inserted\"][2]//child::span[@class=\"card__card-header--label\"]");
    By secondWidgetHeader = By.xpath("//div[@class=\"home-tab-container__right-widgets--widgets ng-star-inserted\"][1]//child::span[@class=\"card__card-header--label\"]");
    By fourthWidgetHeader = By.xpath("//div[@class=\"home-tab-container__right-widgets--widgets ng-star-inserted\"][2]//child::span[@class=\"card__card-header--label\"]");
    By daDetailsTab=By.xpath("//div[contains(text(),'DA DETAILS')]");
    By usageHistoryTab=By.xpath("//div[contains(text(),'USAGE HISTORY')]");
    By rechargeHistoryTab=By.xpath("//div[contains(text(),'RECHARGE HISTORY')]");
    By homeActionBtn=By.xpath("//span[@class='action-placeholder']");
    By sendSMSAction=By.xpath("//div[@class=\"mat-menu-content\"]//button[contains(text(),'Send SMS')]");

    public customerInteractionPagePOM(WebDriver driver) {
        super(driver);
    }

    public String getFirstWidgetHeader() {
        log.info("Getting header of 1st Widget : " + readText(firstWidgetHeader));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header of 1st Widget : " + readText(firstWidgetHeader));
        return readText(firstWidgetHeader);
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
        List<String> strings=new ArrayList<String>();
        List<WebElement> webElements = driver.findElements(pinTags);
        System.out.println("Size: "+webElements.size());
        for (int i = 1; i <= webElements.size(); i++) {
            By tagName=By.xpath("//div[@class='sub-header__divide--control']//div[@class=\"sub-header__divide--control--tab ng-star-inserted\"]["+i+"]");
            System.out.println("Text: "+readText(tagName).toLowerCase().trim());
            log.info("Reading pinned tag name: "+readText(tagName));
            ExtentTestManager.getTest().log(LogStatus.INFO,"Reading pinned tag name: "+readText(tagName));
            strings.add(readText(tagName).toLowerCase().trim());
        }
        return strings;
    }

    public customerInteractionsSearchPOM clickPinTag(String text) {
        log.info("Clicking on "+text+" Pinned Tag");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking "+text+" Pinned Tag");
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\""+text+"\")]");
        click(tagName);
        return new customerInteractionsSearchPOM(driver);
    }


    public boolean isPageLoaded() {
        waitVisibility(searchNumber);
        log.info("Checking that is Customer Interaction Page is loaded : " + checkState(searchNumber));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Customer Interaction Page is loaded : " + checkState(searchNumber));
        return checkState(searchNumber);
    }

    public String getCustomerName() {
        log.info("Getting Customer Name " + readText(customerName));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Customer Name " + readText(customerName));

        return readText(customerName);
    }

    public String getCustomerDOB() {
        log.info("Getting Customer DOB " + readText(customerDOB));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Customer DOB " + readText(customerDOB));
        return readText(customerDOB);
    }

    public String getActivationDate() {
        log.info("Getting Activation Date " + readText(activationDate));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Activation Date " + readText(activationDate));
        return readText(activationDate);
    }

    public String getActivationTime() {
        log.info("Getting Activation Time " + readText(activationTime));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Activation Time " + readText(activationTime));
        return readText(activationTime);
    }

    public String getSimNumber() {
        log.info("Getting Sim Number " + readText(simNumber));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Sim Number " + readText(simNumber));
        return readText(simNumber);
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

    public String getSimType() {
        log.info("Getting Sim Type " + readText(simType));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Sim Type " + readText(simType));
        return readText(simType);
    }

    public String getPUK1() {

        log.info("Getting PUK1 " + readText(PUK1));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting PUK1 " + readText(PUK1));
        return readText(PUK1);
    }

    public String getPUK2() {
        log.info("Getting PUK2 " + readText(PUK2));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting PUK2 " + readText(PUK2));
        return readText(PUK2);
    }

    public String getIdType() {
        log.info("Getting ID Type " + readText(idType));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting ID Type " + readText(idType));
        return readText(idType);
    }

    public String getIdNumber() {
        log.info("Getting masked ID Number " + readText(idNumber));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting masked ID Number " + readText(idNumber));
        return readText(idNumber);
    }

    public boolean isPinTagVisible(String text) {
        By tagName = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\""+text+"\")]");
        log.info("Checking is "+text+" Pinned Tag Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is "+text+" Pinned Tag Visible");
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

    public SendSMSPOM openSendSMSTab(){
        log.info("Clicking on Send SMS");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on Send SMS");
        click(sendSMSAction);
        return new SendSMSPOM(driver);
    }

}
