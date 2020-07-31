package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class RechargeHistoryWidgetPOM extends BasePage {

    By rechargeHistoryDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//following-sibling::form//child::input[@name=\"dateRange\"]");
    By rechargeHistoryHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Recharge History \"]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    List<WebElement> as = driver.findElements(rows);
    By charges = By.xpath("div[1]/span[@class=\"ng-star-inserted\"]");
    By dateTime = By.xpath("div[2]/span[@class=\"date_time ng-star-inserted\"]");
    By bundleName = By.xpath("div[3]/span[@class=\"ng-star-inserted\"]");
    By benefit = By.xpath("div[4]/span[@class=\"ng-star-inserted\"]");
    By status = By.xpath("div[5]/span[@class=\"ng-star-inserted\"]");


    public RechargeHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public int getNumberOfRows() {
        return as.size();
    }

    public String getRechargeHistoryCharges(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Recharge History Charges from Row Number " + RowNumber + " : " + rowElement.findElement(charges).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Recharge History Charges from Row Number " + RowNumber + " : " + rowElement.findElement(charges).getText());
        return rowElement.findElement(charges).getText();
    }

    public String getRechargeHistoryDateTime(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Recharge History Date Time from Row Number " + RowNumber + " : " + rowElement.findElement(dateTime).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Recharge History Date Time from Row Number " + RowNumber + " : " + rowElement.findElement(dateTime).getText());
        return rowElement.findElement(dateTime).getText();
    }

    public String getRechargeHistoryBundleName(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Recharge History Bundle Name from Row Number " + RowNumber + " : " + rowElement.findElement(bundleName).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Recharge History Bundle Name from Row Number " + RowNumber + " : " + rowElement.findElement(bundleName).getText());
        return rowElement.findElement(bundleName).getText();
    }

    public String getRechargeHistoryBenefits(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Recharge History Benefits from Row Number " + RowNumber + " : " + rowElement.findElement(benefit).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Recharge History Benefits from Row Number " + RowNumber + " : " + rowElement.findElement(benefit).getText());
        return rowElement.findElement(benefit).getText();
    }

    public String getRechargeHistoryStatus(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Recharge History Status from Row Number " + RowNumber + " : " + rowElement.findElement(status).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Recharge History Status from Row Number " + RowNumber + " : " + rowElement.findElement(status).getText());
        return rowElement.findElement(status).getText();
    }

    public boolean isRechargeHistoryWidgetIsVisible() {
        log.info("Checking is Recharge History Widget Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Recharge History Widget Visible");
        return isElementVisible(rechargeHistoryHeader);
    }

    public boolean isRechargeHistoryDatePickerVisible() {
        log.info("Checking Recharge History Widget Date Picker Visibility ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking Recharge HistoryWidget Date Picker Visibility ");
        return checkState(rechargeHistoryDatePicker);
    }
}
