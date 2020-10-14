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
    By menu = By.xpath("//span[contains(text(),\"Recharge History\")]//parent::div/span[@class=\"card__card-header--menu ng-star-inserted\"]/img");
    By more = By.xpath("//button[text()=\"Recharge History\"]");
    By rechargeHistoryNoResultFound = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    By rechargeHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By rechargeHistoryError = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");
    By ticketIcon=By.xpath("//span[contains(text(),'Recharge History')]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    By getTitle=By.xpath("//span[contains(text(),'Recharge History')]");

    public RechargeHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isRechargeHistoryErrorVisible() {
        log.info("Validating error is visible when there is Error inAPI : " + isElementVisible(rechargeHistoryError));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is Error in API : " + isElementVisible(rechargeHistoryError));
        return isElementVisible(rechargeHistoryError);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

    public String gettingRechargeHistoryNoResultFoundMessage() {
        log.info("Validating error message when there is no data from API : " + readText(rechargeHistoryNoResultFoundMessage));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error message when there is no data from API : " + readText(rechargeHistoryNoResultFoundMessage));
        return readText(rechargeHistoryNoResultFoundMessage);
    }

    public boolean isRechargeHistoryNoResultFoundVisible() {
        log.info("Validating error is visible when there is no data from API : " + isElementVisible(rechargeHistoryNoResultFound));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is no data from API : " + isElementVisible(rechargeHistoryNoResultFound));
        return isElementVisible(rechargeHistoryNoResultFound);
    }


    public int getNumberOfRows() {
        return as.size();
    }

    public boolean isRechargeHistoryWidgetMenuVisible() {
        log.info("Checking is Recharge History's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Recharge History's Menu Visible");
        return isElementVisible(menu);
    }

    public boolean isRechargeHistoryMenuVisible() {
        log.info("Checking is More Option Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is More Option Visible");
        return checkState(more);
    }

    public MoreRechargeHistoryPOM openingRechargeHistoryDetails() {
        log.info("Opening Recharge History Details under Recharge History Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening RechargeHistory under Recharge History Widget");
        click(more);
        return new MoreRechargeHistoryPOM(driver);
    }

    public void clickingRechargeHistoryWidgetMenu() {
        log.info("Clicking Current Balance Widget's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Current Balance Widget'Menu Visible");
        click(menu);
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

    public WidgetInteractionPOM clickTicketIcon(){
        log.info("Clicking on Ticket Icon");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on Ticket Icon");
        click(ticketIcon);
        return new WidgetInteractionPOM(driver);
    }

    public String getWidgetTitle(){
        log.info("Getting Widget title: "+readText(getTitle));
        return readText(getTitle).toLowerCase();
    }
}
