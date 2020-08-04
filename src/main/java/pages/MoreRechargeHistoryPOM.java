package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class MoreRechargeHistoryPOM extends BasePage {
    By rows = By.xpath("//span[contains(text(),\"BUNDLE SUBSCRIPTION HISTORY \")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    List<WebElement> rowsElements = driver.findElements(rows);
    By bundleName = By.xpath("div[1]/span[@class=\"ng-star-inserted\"]");
    By packageCategory = By.xpath("div[2]/span[@class=\"ng-star-inserted\"]");
    By transactionNumber = By.xpath("div[3]/span[@class=\"ng-star-inserted\"]");
    By status = By.xpath("div[4]/span[@class=\"background-badge ng-star-inserted\"]");
    By subscriptionDateTime = By.xpath("div[5]/span[@class=\"date_time ng-star-inserted\"]");
    By expiresOn = By.xpath("div[6]/span[@class=\"date_time ng-star-inserted\"]");
    By validity = By.xpath("div[7]/span[@class=\"ng-star-inserted\"]");
    By bundlePrice = By.xpath("div[8]/span[@class=\"ng-star-inserted\"]");
    By DatePicker = By.xpath("//span[contains(text(),\"BUNDLE SUBSCRIPTION HISTORY \")]//following-sibling::form/span[@class=\"datepicker-transaction ng-star-inserted\"]");
    By bundleHistoryNoResultFound = By.xpath("//span[contains(text(),\"BUNDLE SUBSCRIPTION HISTORY \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    By bundleHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"BUNDLE SUBSCRIPTION HISTORY \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");


    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"BUNDLE SUBSCRIPTION HISTORY\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingBundleHistoryNoResultFoundMessage() {
        log.info("Validating error message when there is no data from API : " + readText(bundleHistoryNoResultFoundMessage));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error message when there is no data from API : " + readText(bundleHistoryNoResultFoundMessage));
        return readText(bundleHistoryNoResultFoundMessage);
    }

    public boolean isBundleHistoryNoResultFoundVisible() {
        log.info("Validating error is visible when there is no data from API : " + isElementVisible(bundleHistoryNoResultFound));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is no data from API : " + isElementVisible(bundleHistoryNoResultFound));
        return isElementVisible(bundleHistoryNoResultFound);
    }

    public MoreRechargeHistoryPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isDatePickerVisible() {
        log.info("Checking is DatePicker is Enabled");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is DatePicker is Enabled");
        return checkState(DatePicker);
    }

    public String getBundleName(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting  Bundle Name from Row Number " + RowNumber + " : " + rowElement.findElement(bundleName).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting  Bundle Name from Row Number " + RowNumber + " : " + rowElement.findElement(bundleName).getText());
        return rowElement.findElement(bundleName).getText();
    }

    public String getTransactionNumber(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Transaction Number from Row Number " + RowNumber + " : " + rowElement.findElement(transactionNumber).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Transaction Number from Row Number " + RowNumber + " : " + rowElement.findElement(transactionNumber).getText());
        return rowElement.findElement(transactionNumber).getText();
    }

    public String getPackageCategory(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Package Category from Row Number " + RowNumber + " : " + rowElement.findElement(packageCategory).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Package Category from Row Number " + RowNumber + " : " + rowElement.findElement(packageCategory).getText());
        return rowElement.findElement(packageCategory).getText();
    }

    public String getStatus(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Status from Row Number " + RowNumber + " : " + rowElement.findElement(status).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Status from Row Number " + RowNumber + " : " + rowElement.findElement(status).getText());
        return rowElement.findElement(status).getText();
    }

    public String getSubscriptionDateTime(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Subscription Date & Time from Row Number " + RowNumber + " : " + rowElement.findElement(subscriptionDateTime).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Subscription Date & Time from Row Number " + RowNumber + " : " + rowElement.findElement(subscriptionDateTime).getText());
        return rowElement.findElement(subscriptionDateTime).getText();
    }

    public String getExpiresOn(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Expires On Date & Time from Row Number " + RowNumber + " : " + rowElement.findElement(expiresOn).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Expires On Date & Time from Row Number " + RowNumber + " : " + rowElement.findElement(expiresOn).getText());
        return rowElement.findElement(expiresOn).getText();
    }

    public String getValidity(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Validity from Row Number " + RowNumber + " : " + rowElement.findElement(validity).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Validity from Row Number " + RowNumber + " : " + rowElement.findElement(validity).getText());
        return rowElement.findElement(validity).getText();
    }

    public String getBundlePrice(int RowNumber) {
        WebElement rowElement = rowsElements.get(RowNumber);
        log.info("Getting Bundle Price from Row Number " + RowNumber + " : " + rowElement.findElement(bundlePrice).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Bundle Price from Row Number " + RowNumber + " : " + rowElement.findElement(bundlePrice).getText());
        return rowElement.findElement(bundlePrice).getText();
    }

    public int getNumbersOfRows() {
        return rowsElements.size();
    }

}
