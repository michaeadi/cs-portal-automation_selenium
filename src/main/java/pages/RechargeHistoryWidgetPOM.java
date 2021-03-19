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

    By rechargeHistoryDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//ancestor::div[@class='card widget ng-star-inserted']//input[@name='dateRange']");
    By rechargeHistoryHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Recharge History \"]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]");
    List<WebElement> as = returnListOfElement(rows);
    By menu = By.xpath("//span[contains(text(),\"Recharge History\")]//ancestor::div[1]//span/img[@class='header-action-icon ng-star-inserted']");
    By more = By.xpath("//button[text()=\"Recharge History\"]");
    By rechargeHistoryNoResultFound = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/img");
    By rechargeHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By rechargeHistoryError = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");
    By ticketIcon = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//parent::div//span[@class=\"card__card-header--icon ng-star-inserted\"]/img");
    By getTitle = By.xpath("//span[contains(text(),'Recharge History')]");
    By voucherBox = By.xpath("//input[@placeholder=\"Voucher ID\"]");
    By voucherBtn = By.xpath("//input[@placeholder=\"Voucher ID\"]//parent::span//button");
    By refillIconDisable=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//parent::div//span[2]/span/img[@class=\"header-action-icon disabled ng-star-inserted\"]");
    By refillIconClickable=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//parent::div//span[2]/span/img[@class=\"header-action-icon ng-star-inserted\"]");
    By popUpMessage=By.xpath("//div[@class=\"confirm-block ng-star-inserted\"]/p");
    By noActionBtn=By.xpath("//div[@class=\"confirm-block ng-star-inserted\"]//button[@class=\"no-btn\"]");

    public RechargeHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isRechargeHistoryErrorVisible() {
        log.info("Validating error is visible when there is Error inAPI : " + isElementVisible(rechargeHistoryError));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is Error in API : " + isElementVisible(rechargeHistoryError));
        return isElementVisible(rechargeHistoryError);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Recharge History\")]//ancestor::div[2]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div["+row+"]/span[1]"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

    public String getSubHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Recharge History\")]//ancestor::div[2]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div["+row+"]/span[2]"));
        log.info("Getting Sub header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Sub Header Number " + row + " : " + header);
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
        click(menu);
        return new MoreRechargeHistoryPOM(driver);
    }

    public void clickingRechargeHistoryWidgetMenu() {
        log.info("Clicking Current Balance Widget's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Current Balance Widget'Menu Visible");
        click(menu);
    }

    public String getRechargeHistoryCharges(int RowNumber) {
        By charges=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][1]//span");
        printInfoLog("Getting Recharge History Charges from Row Number " + RowNumber + " : " + readText(charges));
        return readText(charges);
    }

    public String getRechargeHistoryDateTime(int RowNumber) {
        By date=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][2]//span[1]");
//        By time=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\"][2]//span[@class=\"time ng-star-inserted\"]");
        printInfoLog("Getting Recharge History Date & Time from Row Number " + RowNumber + " : " + readText(date));
        return readText(date);
    }

    public String getRechargeHistoryBundleName(int RowNumber) {
        By bundleName=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][3]//span");
        printInfoLog("Getting Recharge History Bundle Name from Row Number " + RowNumber + " : " + readText(bundleName));
        return readText(bundleName);
    }

    public String getRechargeHistoryBenefits(int RowNumber) {
        By benefits=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]//span");
        printInfoLog("Getting Recharge History Benefits from Row Number " + RowNumber + " : " + readText(benefits));
        return readText(benefits);
    }

    public String getRechargeHistoryStatus(int RowNumber) {
        By status=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][5]//span");
        printInfoLog("Getting Recharge History Status from Row Number " + RowNumber + " : " + readText(status));
        return readText(status);
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

    public void writeVoucherId(String id) throws InterruptedException {
        printInfoLog("Writing voucher id in search box: " + id);
        scrollToViewElement(voucherBox);
        writeText(voucherBox, id);
    }

    public VoucherTabPOM clickSearchBtn() {
        printInfoLog("Clicking Search Button");
        click(voucherBtn);
        return new VoucherTabPOM(driver);
    }

    public Boolean isRefillIconDisable(){
        printInfoLog("Checking Clear refill icon disable :"+checkState(refillIconDisable));
        return checkState(refillIconDisable);
    }

    public Boolean isRefillIconEnable(){
        printInfoLog("Checking Clear refill icon enable :"+checkState(refillIconClickable));
        return checkState(refillIconClickable);
    }

    public void clickRefillIcon(){
        printInfoLog("Clicking Clear refill icon");
        click(refillIconClickable);
    }

    public boolean checkPopDisplay(){
        printInfoLog("Reading Pop up title: "+readText(popUpMessage));
        return checkState(popUpMessage);
    }

    public void clickNoBtn(){
        printInfoLog("Clicking No Button ");
        click(noActionBtn);
    }


}
