package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.RechargeHistoryWidgetPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class RechargeHistoryWidgetPage extends BasePage {

    RechargeHistoryWidgetPageElements pageElements;
    List<WebElement> as;

    public RechargeHistoryWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, RechargeHistoryWidgetPageElements.class);
    }

    public boolean isRechargeHistoryErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error in com.airtel.cs.API : " + isElementVisible(pageElements.rechargeHistoryError));
        return isElementVisible(pageElements.rechargeHistoryError);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//div[@id='RECHARGE_HISTORY']//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]/span[1]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getSubHeaders(int row) {
        String header = readText(By.xpath("//div[@id='RECHARGE_HISTORY']//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]/span[2]"));
        UtilsMethods.printInfoLog("Getting Sub Header Number " + row + " : " + header);
        return header;
    }

    public String gettingRechargeHistoryNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.rechargeHistoryNoResultFoundMessage));
        return readText(pageElements.rechargeHistoryNoResultFoundMessage);
    }

    public boolean isRechargeHistoryNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.rechargeHistoryNoResultFound));
        return isElementVisible(pageElements.rechargeHistoryNoResultFound);
    }


    public int getNumberOfRows() {
        as = returnListOfElement(pageElements.rows);
        return as.size();
    }

    public boolean isRechargeHistoryWidgetMenuVisible() {
        UtilsMethods.printInfoLog("Checking is Recharge History's Menu Visible");
        return isElementVisible(pageElements.menu);
    }

    public boolean isRechargeHistoryMenuVisible() {
        UtilsMethods.printInfoLog("Checking is More Option Visible");
        return checkState(pageElements.more);
    }

    public void openingRechargeHistoryDetails() {
        UtilsMethods.printInfoLog("Opening RechargeHistory under Recharge History Widget");
        click(pageElements.menu);
    }

    public void clickingRechargeHistoryWidgetMenu() {
        UtilsMethods.printInfoLog("Clicking Current Balance Widget'Menu Visible");
        click(pageElements.menu);
    }

    public String getRechargeHistoryCharges(int rowNumber) {
        By charges = By.xpath("//div[@id='RECHARGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][1]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Charges from Row Number " + rowNumber + " : " + readText(charges));
        return readText(charges);
    }

    public String getRechargeHistoryDateTime(int rowNumber) {
        By date = By.xpath("//div[@id='RECHARGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][2]//span[1]");
        UtilsMethods.printInfoLog("Getting Recharge History Date & Time from Row Number " + rowNumber + " : " + readText(date));
        return readText(date);
    }

    public String getRechargeHistoryBundleName(int rowNumber) {
        By bundleName = By.xpath("//div[@id='RECHARGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][3]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Bundle Name from Row Number " + rowNumber + " : " + readText(bundleName));
        return readText(bundleName);
    }

    public String getRechargeHistoryBenefits(int rowNumber) {
        By benefits = By.xpath("//div[@id='RECHARGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Benefits from Row Number " + rowNumber + " : " + readText(benefits));
        return readText(benefits);
    }

    public String getRechargeHistoryStatus(int rowNumber) {
        By status = By.xpath("//div[@id='RECHARGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][" + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][5]//span");
        UtilsMethods.printInfoLog("Getting Recharge History Status from Row Number " + rowNumber + " : " + readText(status));
        return readText(status);
    }

    public boolean isRechargeHistoryWidgetIsVisible() {
        UtilsMethods.printInfoLog("Checking is Recharge History Widget Visible");
        return isElementVisible(pageElements.rechargeHistoryHeader);
    }

    public boolean isRechargeHistoryDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking Recharge HistoryWidget Date Picker Visibility ");
        return checkState(pageElements.rechargeHistoryDatePicker);
    }

    public WidgetInteractionPage clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteractionPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Recharge History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(pageElements.getTitle));
        return readText(pageElements.getTitle).toLowerCase();
    }

    public void writeVoucherId(String id) throws InterruptedException {
        UtilsMethods.printInfoLog("Writing voucher id in search box: " + id);
        scrollToViewElement(pageElements.voucherBox);
        writeText(pageElements.voucherBox, id);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking Search Button");
        click(pageElements.voucherBtn);
    }

    public Boolean isRefillIconDisable() {
        UtilsMethods.printInfoLog("Checking Clear refill icon disable :" + checkState(pageElements.refillIconDisable));
        return checkState(pageElements.refillIconDisable);
    }

    public Boolean isRefillIconEnable() {
        UtilsMethods.printInfoLog("Checking Clear refill icon enable :" + checkState(pageElements.refillIconClickable));
        return checkState(pageElements.refillIconClickable);
    }

    public void clickRefillIcon() {
        UtilsMethods.printInfoLog("Clicking Clear refill icon");
        click(pageElements.refillIconClickable);
    }

    public boolean checkPopDisplay() {
        UtilsMethods.printInfoLog("Reading Pop up title: " + readText(pageElements.popUpMessage));
        return checkState(pageElements.popUpMessage);
    }

    public void clickNoBtn() {
        UtilsMethods.printInfoLog("Clicking No Button ");
        click(pageElements.noActionBtn);
    }
}
