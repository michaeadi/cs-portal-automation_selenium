package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.RechargeHistoryWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class RechargeHistoryWidget extends BasePage {

    RechargeHistoryWidgetPage pageElements;
    List<WebElement> as;
    private static final String XPATH = "//div[@id='RECHARGE_HISTORY']//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"][";

    public RechargeHistoryWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, RechargeHistoryWidgetPage.class);
    }

    public boolean isRechargeHistoryErrorVisible() {
        final boolean visible = isElementVisible(pageElements.rechargeHistoryError);
        commonLib.info("Validating error is visible when there is Error in com.airtel.cs.API : " + visible);
        return visible;
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath("//div[@id='RECHARGE_HISTORY']//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]/span[1]"));
        commonLib.info("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getSubHeaders(int row) {
        String header = getText(By.xpath("//div[@id='RECHARGE_HISTORY']//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]/span[2]"));
        commonLib.info("Getting Sub Header Number " + row + " : " + header);
        return header;
    }

    public String gettingRechargeHistoryNoResultFoundMessage() {
        final String text = getText(pageElements.rechargeHistoryNoResultFoundMessage);
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }

    public boolean isRechargeHistoryNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.rechargeHistoryNoResultFound);
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + visible);
        return visible;
    }


    public int getNumberOfRows() {
        as = returnListOfElement(pageElements.rows);
        return as.size();
    }

    public boolean isRechargeHistoryWidgetMenuVisible() {
        commonLib.info("Checking is Recharge History's Menu Visible");
        return isElementVisible(pageElements.menu);
    }

    public boolean isRechargeHistoryMenuVisible() {
        commonLib.info("Checking is More Option Visible");
        return isEnabled(pageElements.more);
    }

    public void openingRechargeHistoryDetails() {
        commonLib.info("Opening RechargeHistory under Recharge History Widget");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }

    public void clickingRechargeHistoryWidgetMenu() {
        commonLib.info("Clicking Current Balance Widget'Menu Visible");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }

    public String getRechargeHistoryCharges(int rowNumber) {
        By charges = By.xpath(XPATH + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][1]//span");
        final String text = getText(charges);
        commonLib.info("Getting Recharge History Charges from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getRechargeHistoryDateTime(int rowNumber) {
        By date = By.xpath(XPATH + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][2]//span[1]");
        final String text = getText(date);
        commonLib.info("Getting Recharge History Date & Time from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getRechargeHistoryBundleName(int rowNumber) {
        By bundleName = By.xpath(XPATH + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][3]//span");
        final String text = getText(bundleName);
        commonLib.info("Getting Recharge History Bundle Name from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getRechargeHistoryBenefits(int rowNumber) {
        By benefits = By.xpath(XPATH + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]//span");
        final String text = getText(benefits);
        commonLib.info("Getting Recharge History Benefits from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public String getRechargeHistoryStatus(int rowNumber) {
        By status = By.xpath(XPATH + rowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][5]//span");
        final String text = getText(status);
        commonLib.info("Getting Recharge History Status from Row Number " + rowNumber + " : " + text);
        return text;
    }

    public boolean isRechargeHistoryWidgetIsVisible() {
        commonLib.info("Checking is Recharge History Widget Visible");
        return isElementVisible(pageElements.rechargeHistoryHeader);
    }

    public boolean isRechargeHistoryDatePickerVisible() {
        commonLib.info("Checking Recharge HistoryWidget Date Picker Visibility ");
        return isEnabled(pageElements.rechargeHistoryDatePicker);
    }

    public WidgetInteraction clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
            return new WidgetInteraction(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Recharge History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    public void writeVoucherId(String id) throws InterruptedException {
        commonLib.info("Writing voucher id in search box: " + id);
        scrollToViewElement(pageElements.voucherBox);
        enterText(pageElements.voucherBox, id);
    }

    public void clickSearchBtn() {
        commonLib.info("Clicking Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.voucherBtn);
    }

    public Boolean isRefillIconDisable() {
        final boolean state = isEnabled(pageElements.refillIconDisable);
        commonLib.info("Checking Clear refill icon disable :" + state);
        return state;
    }

    public Boolean isRefillIconEnable() {
        final boolean state = isEnabled(pageElements.refillIconClickable);
        commonLib.info("Checking Clear refill icon enable :" + state);
        return state;
    }

    public void clickRefillIcon() {
        commonLib.info("Clicking Clear refill icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.refillIconClickable);
    }

    public boolean checkPopDisplay() {
        commonLib.info("Reading Pop up title: " + getText(pageElements.popUpMessage));
        return isEnabled(pageElements.popUpMessage);
    }

    public void clickNoBtn() {
        commonLib.info("Clicking No Button ");
        clickAndWaitForLoaderToBeRemoved(pageElements.noActionBtn);
    }
}
