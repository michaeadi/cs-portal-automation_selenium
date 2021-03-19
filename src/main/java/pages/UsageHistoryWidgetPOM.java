package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class UsageHistoryWidgetPOM extends BasePage {

    public UsageHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    By usageHistoryDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]//ancestor::div[@class='card widget ng-star-inserted']//input");
    By usageHistoryHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Usage History \"]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Usage\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]");
    List<WebElement> as = returnListOfElement(rows);
    By type = By.xpath("div[1]/span[@class=\"ng-star-inserted\"]");
    By charge = By.xpath("div[2]/span[@class=\"ng-star-inserted\"]");
    By dateTime = By.xpath("div[3]/span[@class=\"date_time ng-star-inserted\"]");
    By startBalance = By.xpath("div[4]/span[@class=\"ng-star-inserted\"]");
    By endBalance = By.xpath("div[5]/span[@class=\"ng-star-inserted\"]");
    By menu = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]//ancestor::div[1]//span/img[@class='header-action-icon ng-star-inserted']");
    By usageHistoryNoResultFound = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]//ancestor::div[2]//div[@class=\"no-result-found ng-star-inserted\"]//img");
    By usageHistoryNoResultFoundMessage = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]//ancestor::div[2]//span[contains(text(),'No Result found')]");
    By usageHistoryError = By.xpath("//span[contains(text(),\"Usage History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");
    By ticketIcon = By.xpath("//span[contains(text(),'Usage History')]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    By getTitle = By.xpath("//span[contains(text(),'Usage History')]");

    public boolean isUsageHistoryErrorVisible() {
        printInfoLog("Validating error is visible when there is Error in API : " + isElementVisible(usageHistoryError));
        return isElementVisible(usageHistoryError);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]//ancestor::div[2]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div["+row+"]/span"));
        printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingUsageHistoryNoResultFoundMessage() {
        printInfoLog("Validating error message when there is no data from API : " + readText(usageHistoryNoResultFoundMessage));
        return readText(usageHistoryNoResultFoundMessage);
    }

    public boolean isUsageHistoryNoResultFoundVisible() {
        printInfoLog("Validating error is visible when there is no data from API : " + isElementVisible(usageHistoryNoResultFound));
        return isElementVisible(usageHistoryNoResultFound);
    }


    public int getNumberOfRows() {
        return as.size();
    }

    public boolean isUsageHistoryWidgetMenuVisible() {
        printInfoLog("Checking is Usage History's Menu Visible");
        return isElementVisible(menu);
    }

    public DetailedUsageHistoryPOM openingMoreDetails() {
        printInfoLog("Opening More under Usage History Widget");
        click(menu);
        return new DetailedUsageHistoryPOM(driver);
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
        By dateTime=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Usage History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"]["+(RowNumber+1)+"]/div[3]//span");
        printInfoLog("Getting Usage History Date Time from Row Number " + RowNumber + " : " + readText(dateTime));
        return readText(dateTime);
    }

    public String getHistoryCharge(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History Charge from Row Number " + RowNumber + " : " + rowElement.findElement(charge).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History Charge from Row Number " + RowNumber + " : " + rowElement.findElement(charge).getText());
        return rowElement.findElement(charge).getText().replace('+',' ').trim();
    }

    public String getHistoryType(int RowNumber) {
        WebElement rowElement = as.get(RowNumber);
        log.info("Getting Usage History Type from Row Number " + RowNumber + " : " + rowElement.findElement(type).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Usage History Type from Row Number " + RowNumber + " : " + rowElement.findElement(type).getText());
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

    public WidgetInteractionPOM clickTicketIcon() {
        log.info("Clicking on Ticket Icon");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Ticket Icon");
        click(ticketIcon);
        return new WidgetInteractionPOM(driver);
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(getTitle));
        return readText(getTitle).toLowerCase();
    }

}
