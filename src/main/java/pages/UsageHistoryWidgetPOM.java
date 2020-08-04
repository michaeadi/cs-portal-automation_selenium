package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class UsageHistoryWidgetPOM extends BasePage {

    By usageHistoryDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]//following-sibling::form//child::input[@name=\"dateRange\"]");
    By usageHistoryHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Usage History\"]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Usage\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    List<WebElement> as = driver.findElements(rows);
    By type = By.xpath("div[1]/span[@class=\"ng-star-inserted\"]");
    By charge = By.xpath("div[2]/span[@class=\"ng-star-inserted\"]");
    By dateTime = By.xpath("div[3]/span[@class=\"date_time ng-star-inserted\"]");
    By startBalance = By.xpath("div[4]/span[@class=\"ng-star-inserted\"]");
    By endBalance = By.xpath("div[5]/span[@class=\"ng-star-inserted\"]");
    By menu = By.xpath("//span[contains(text(),\"Usage History \")]//parent::div/span[@class=\"card__card-header--menu ng-star-inserted\"]/img");
    By more = By.xpath("//button[text()=\"More\"]");
    By usageHistoryNoResultFound = By.xpath("//span[contains(text(),\"Usage History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    By usageHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"Usage History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Usage History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingUsageHistoryNoResultFoundMessage() {
        log.info("Validating error message when there is no data from API : " + readText(usageHistoryNoResultFoundMessage));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error message when there is no data from API : " + readText(usageHistoryNoResultFoundMessage));
        return readText(usageHistoryNoResultFoundMessage);
    }

    public boolean isUsageHistoryNoResultFoundVisible() {
        log.info("Validating error is visible when there is no data from API : " + isElementVisible(usageHistoryNoResultFound));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is no data from API : " + isElementVisible(usageHistoryNoResultFound));
        return isElementVisible(usageHistoryNoResultFound);
    }

    public UsageHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public int getNumberOfRows() {
        return as.size();
    }

    public boolean isUsageHistoryWidgetMenuVisible() {
        log.info("Checking is Usage History's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Usage History's Menu Visible");
        return isElementVisible(menu);
    }

    public boolean isMoreMenuVisible() {
        log.info("Checking is More Option Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is More Option Visible");
        return checkState(more);
    }

    public MoreUsageHistoryPOM openingMoreDetails() {
        log.info("Opening More under Usage History Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening More under Usage History Widget");
        click(more);
        return new MoreUsageHistoryPOM(driver);
    }

    public void clickingUsageHistoryWidgetMenu() {
        log.info("Clicking Current Balance Widget's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Current Balance Widget'Menu Visible");
        click(menu);
    }

    public String getHistoryEndBalance(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History End Balacne  from Row Number " + RowNumber + " : " + rowElement.findElement(endBalance).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History End Balance from Row Number " + RowNumber + " : " + rowElement.findElement(endBalance).getText());
        return rowElement.findElement(endBalance).getText();
    }

    public String getHistoryStartBalance(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History Start Balance from Row Number " + RowNumber + " : " + rowElement.findElement(startBalance).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History Start Balance from Row Number " + RowNumber + " : " + rowElement.findElement(startBalance).getText());
        return rowElement.findElement(startBalance).getText();
    }

    public String getHistoryDateTime(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History Date Time from Row Number " +  RowNumber + " : " + rowElement.findElement(dateTime).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History Date Time from Row Number " +  RowNumber + " : " + rowElement.findElement(dateTime).getText());
        return rowElement.findElement(dateTime).getText();
    }

    public String getHistoryCharge(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History Charge from Row Number " +  RowNumber + " : " + rowElement.findElement(charge).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History Charge from Row Number " +  RowNumber + " : " + rowElement.findElement(charge).getText());
        return rowElement.findElement(charge).getText();
    }

    public String getHistoryType(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History Type from Row Number " +  RowNumber + " : " + rowElement.findElement(type).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History Type from Row Number " +  RowNumber + " : " + rowElement.findElement(type).getText());
        return rowElement.findElement(type).getText();
    }

    public boolean isUsageHistoryWidgetIsVisible() {
        log.info("Checking is Usage History Widget Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Usage History Widget Visible");
        return isElementVisible(usageHistoryHeader);
    }

    public boolean isUsageHistoryDatePickerVisible() {
        log.info("Checking Usage History Widget Date Picker Visibility ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking Usage HistoryWidget Date Picker Visibility ");
        return checkState(usageHistoryDatePicker);
    }
}
