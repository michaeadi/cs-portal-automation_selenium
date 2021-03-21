package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class RechargeHistoryWidgetPOM extends BasePage {

    By rechargeHistoryDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//ancestor::div[@class='card widget ng-star-inserted']//input[@name='dateRange']");
    By rechargeHistoryHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Recharge History \"]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]");
    List<WebElement> as;
    By menu = By.xpath("//span[contains(text(),\"Recharge History\")]//ancestor::div[1]//span/img[@class='header-action-icon ng-star-inserted']");
    By more = By.xpath("//button[text()=\"Recharge History\"]");
    By rechargeHistoryNoResultFound = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/img");
    By rechargeHistoryNoResultFoundMessage = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By rechargeHistoryError = By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");
    By ticketIcon = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),'Recharge History')]//ancestor::div[1]//span/img[@class='interaction-ticket']");
    By getTitle = By.xpath("//span[contains(text(),'Recharge History')]");
    By voucherBox = By.xpath("//input[@placeholder=\"Voucher ID\"]");
    By voucherBtn = By.xpath("//input[@placeholder=\"Voucher ID\"]//parent::span//button");
    By refillIconDisable = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//parent::div//span[2]/span/img[@class=\"header-action-icon disabled ng-star-inserted\"]");
    By refillIconClickable = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//parent::div//span[2]/span/img[@class=\"header-action-icon ng-star-inserted\"]");
    By popUpMessage = By.xpath("//div[@class=\"confirm-block ng-star-inserted\"]/p");
    By noActionBtn = By.xpath("//div[@class=\"confirm-block ng-star-inserted\"]//button[@class=\"no-btn\"]");

    public RechargeHistoryWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isRechargeHistoryErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error in API : " + isElementVisible(rechargeHistoryError));
        return isElementVisible(rechargeHistoryError);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Recharge History\")]//ancestor::div[2]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]/span[1]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getSubHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Recharge History\")]//ancestor::div[2]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]/span[2]"));
        UtilsMethods.printInfoLog("Getting Sub Header Number " + row + " : " + header);
        return header;
    }

    public String gettingRechargeHistoryNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from API : " + readText(rechargeHistoryNoResultFoundMessage));
        return readText(rechargeHistoryNoResultFoundMessage);
    }

    public boolean isRechargeHistoryNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from API : " + isElementVisible(rechargeHistoryNoResultFound));
        return isElementVisible(rechargeHistoryNoResultFound);
    }


    public int getNumberOfRows() {
        as = returnListOfElement(rows);
        return as.size();
    }

    public boolean isRechargeHistoryWidgetMenuVisible() {
        UtilsMethods.printInfoLog("Checking is Recharge History's Menu Visible");
        return isElementVisible(menu);
    }

    public boolean isRechargeHistoryMenuVisible() {
        UtilsMethods.printInfoLog("Checking is More Option Visible");
        return checkState(more);
    }

    public MoreRechargeHistoryPOM openingRechargeHistoryDetails() {
        UtilsMethods.printInfoLog("Opening RechargeHistory under Recharge History Widget");
        click(menu);
        return new MoreRechargeHistoryPOM(driver);
    }

    public void clickingRechargeHistoryWidgetMenu() {
        UtilsMethods.printInfoLog("Clicking Current Balance Widget'Menu Visible");
        click(menu);
    }

    public String getRechargeHistoryCharges(int RowNumber) {
        By charges = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][1]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Charges from Row Number " + RowNumber + " : " + readText(charges));
        return readText(charges);
    }

    public String getRechargeHistoryDateTime(int RowNumber) {
        By date = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][2]//span[1]");
//        By time=By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+RowNumber+"]//div[@class=\"ng-star-inserted\"][2]//span[@class=\"time ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Getting Recharge History Date & Time from Row Number " + RowNumber + " : " + readText(date));
        return readText(date);
    }

    public String getRechargeHistoryBundleName(int RowNumber) {
        By bundleName = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][3]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Bundle Name from Row Number " + RowNumber + " : " + readText(bundleName));
        return readText(bundleName);
    }

    public String getRechargeHistoryBenefits(int RowNumber) {
        By benefits = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Benefits from Row Number " + RowNumber + " : " + readText(benefits));
        return readText(benefits);
    }

    public String getRechargeHistoryStatus(int RowNumber) {
        By status = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Recharge\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][5]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Status from Row Number " + RowNumber + " : " + readText(status));
        return readText(status);
    }

    public boolean isRechargeHistoryWidgetIsVisible() {
        UtilsMethods.printInfoLog("Checking is Recharge History Widget Visible");
        return isElementVisible(rechargeHistoryHeader);
    }

    public boolean isRechargeHistoryDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking Recharge HistoryWidget Date Picker Visibility ");
        return checkState(rechargeHistoryDatePicker);
    }

    public WidgetInteractionPOM clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(ticketIcon);
            return new WidgetInteractionPOM(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Recharge History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(getTitle));
        return readText(getTitle).toLowerCase();
    }

    public void writeVoucherId(String id) throws InterruptedException {
        UtilsMethods.printInfoLog("Writing voucher id in search box: " + id);
        scrollToViewElement(voucherBox);
        writeText(voucherBox, id);
    }

    public VoucherTabPOM clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking Search Button");
        click(voucherBtn);
        return new VoucherTabPOM(driver);
    }

    public Boolean isRefillIconDisable() {
        UtilsMethods.printInfoLog("Checking Clear refill icon disable :" + checkState(refillIconDisable));
        return checkState(refillIconDisable);
    }

    public Boolean isRefillIconEnable() {
        UtilsMethods.printInfoLog("Checking Clear refill icon enable :" + checkState(refillIconClickable));
        return checkState(refillIconClickable);
    }

    public void clickRefillIcon() {
        UtilsMethods.printInfoLog("Clicking Clear refill icon");
        click(refillIconClickable);
    }

    public boolean checkPopDisplay() {
        UtilsMethods.printInfoLog("Reading Pop up title: " + readText(popUpMessage));
        return checkState(popUpMessage);
    }

    public void clickNoBtn() {
        UtilsMethods.printInfoLog("Clicking No Button ");
        click(noActionBtn);
    }


}
