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
    By customerDOB = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-sr-dashboard/div/div[1]/app-sr-customer-details/div/div/div/div[1]/div/div/div[1]/div[2]/ul/li[2]/span/span[2]");
    By activationDate = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[2]/p[1]/span[1]");
    By activationTime = By.xpath("//span[@class='sp-block ng-star-inserted']");
    By simNumber = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/ul[1]/li[2]/p[1]/span[1]");
    By simType = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/ul[1]/li[2]/p[2]/span[1]");
    By PUK1 = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[1]/li[1]/div[1]/div[1]/p[1]/span[2]");
    By PUK2 = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[1]/li[1]/div[1]/div[2]/p[1]/span[2]");
    By idType = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[4]/div[1]/ul[1]/li[1]/p[1]");
    By idNumber = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[4]/div[1]/ul[1]/li[2]/li[1]/p[1]/span[1]");
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
