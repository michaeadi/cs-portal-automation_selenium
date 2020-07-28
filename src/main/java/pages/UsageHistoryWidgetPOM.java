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
    By usageHistoryHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Usage History \"]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Usage\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    List<WebElement> as = driver.findElements(rows);
    By type = By.xpath("div[1]/span[@class=\"ng-star-inserted\"]");
    By charge = By.xpath("div[2]/span[@class=\"ng-star-inserted\"]");
    By dateTime = By.xpath("div[3]/span[@class=\"date_time ng-star-inserted\"]");
    By startBalance = By.xpath("div[4]/span[@class=\"ng-star-inserted\"]");
    By endBalance = By.xpath("div[5]/span[@class=\"ng-star-inserted\"]");


    public UsageHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public int getNumberOfRows() {
        return as.size();
    }

    public String getHistoryEndBalance(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        return rowElement.findElement(endBalance).getText();
    }

    public String getHistoryStartBalance(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        return rowElement.findElement(startBalance).getText();
    }

    public String getHistoryDateTime(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        return rowElement.findElement(dateTime).getText();
    }

    public String getHistoryCharge(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        return rowElement.findElement(charge).getText();
    }

    public String getHistoryType(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
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
