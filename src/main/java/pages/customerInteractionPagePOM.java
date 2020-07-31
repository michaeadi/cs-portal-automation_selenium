package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class customerInteractionPagePOM extends BasePage {
    By searchNumber = By.xpath("//input[@placeholder=\"Search\"]");
    By customerName = By.xpath("//p[@class=\"fw-bold name ng-star-inserted\"]");
    By customerDOB = By.xpath("//span[@class=\"fw-normal dob ng-star-inserted\"]/span[@class=\"ng-star-inserted\"]");
    By activationDate = By.xpath("//div[@class=\"col-md-4 border-right1 ng-star-inserted\"][1]//child::span[1]");
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
    By blankCallBtn = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"lank\")]");
    By callDropBtn = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"rop\")]");
    By noiseCallBtn = By.xpath("//div[@class=\"sub-header__divide--control--tab ng-star-inserted\" and contains(text(),\"on\")]");

    public customerInteractionPagePOM(WebDriver driver) {
        super(driver);
    }


    public String[] getPinnedTagTexts() {
        String[] strings = null;
        List<WebElement> webElements = driver.findElements(pinTags);
        for (int i = 0; i < webElements.size(); i++) {
            strings[i] = webElements.get(i).getText();
        }
        return strings;
    }


    public boolean isBlankCallTagVisible() {
        log.info("Checking is Blank Call Pinned Tag Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Blank Call Pinned Tag Visible");
        return isElementVisible(blankCallBtn);
    }

    public boolean isCallDropTagVisible() {
        log.info("Checking is Call Drop Pinned Tag Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Call Drop Pinned Tag Visible");

        return isElementVisible(callDropBtn);
    }

    public boolean isNoiseCallTagVisible() {
        log.info("Checking is Blank Call Pinned Tag Visible");
        return isElementVisible(noiseCallBtn);
    }

    public customerInteractionsSearchPOM clickOnCallDrop() {
        log.info("Clicking on Call Drop Pinned Tag");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Call Drop Pinned Tag");
        click(callDropBtn);
        return new customerInteractionsSearchPOM(driver);
    }

    public customerInteractionsSearchPOM clickOnNoiseCall() {
        log.info("Clicking on Noise on Call Pinned Tag");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Noise on Call Pinned Tag");
        click(noiseCallBtn);
        return new customerInteractionsSearchPOM(driver);
    }

    public customerInteractionsSearchPOM clickOnBlankCall() {
        log.info("Clicking on Blank Call Pinned Tag");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Blank Call Pinned Tag");
        click(blankCallBtn);
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
}
