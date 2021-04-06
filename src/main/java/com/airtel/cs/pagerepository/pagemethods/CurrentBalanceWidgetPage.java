package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.MyPlanWidgetPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

@Log4j2
public class CurrentBalanceWidgetPage extends BasePage {

    MyPlanWidgetPageElements pageElements;

    public CurrentBalanceWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MyPlanWidgetPageElements.class);
    }

    public boolean isLastRechargeUnableToFetchVisible() {
        UtilsMethods.printInfoLog("Validating is error visible for last recharge balance : " + isElementVisible(pageElements.currentBalanceLastRechargeUnableToFetch));
        return isElementVisible(pageElements.currentBalanceLastRechargeUnableToFetch);
    }

    public boolean isLastRechargeDateTImeUnableTOFetch() {
        UtilsMethods.printInfoLog("Validating is error visible for last recharge Date & Time : " + isElementVisible(pageElements.lastRechargeDateTImeUnableTOFetch));
        return isElementVisible(pageElements.lastRechargeDateTImeUnableTOFetch);
    }

    public String gettingLastRechargeUnableToFetchVisible() {
        UtilsMethods.printInfoLog("Validating  error message for last recharge balance : " + readText(pageElements.currentBalanceLastRechargeUnableToFetch));
        return readText(pageElements.currentBalanceLastRechargeUnableToFetch);
    }

    public String gettingLastRechargeDateTImeUnableTOFetch() {
        UtilsMethods.printInfoLog("Validating  error message for last recharge Date & Time : " + readText(pageElements.lastRechargeDateTImeUnableTOFetch));
        return readText(pageElements.lastRechargeDateTImeUnableTOFetch);
    }


    public boolean isCurrentBalanceWidgetMenuVisible() {
        try {
            UtilsMethods.printInfoLog("Checking is Your Current Balance Widget'Menu Visible");
            return isElementVisible(pageElements.menu);
        } catch (Exception e) {
            throw new NoSuchElementException("Current Plan widget menu option does not display");
        }
    }

    public void openingDADetails() {
        UtilsMethods.printInfoLog("Clicking Current Balance WidgetMenu Visible");
        click(pageElements.menu);
    }

    public String gettingLastRechargeAmount() {
        UtilsMethods.printInfoLog("Getting Main Account Balance from Your Current Balance Widget : " + readText(pageElements.currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""));
        return readText(pageElements.currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "").trim();
    }

    public boolean isCurrentBalanceWidgetVisible() {
        UtilsMethods.printInfoLog("Checking is Your Current Balance Widget Visible");
        return isElementVisible(pageElements.currentBalanceHeader);
    }

    public String gettingCurrentBalanceCurrency() {
        UtilsMethods.printInfoLog("Getting Currency from Your Current Balance Widget : " + readText(pageElements.currentBalanceCurrency).trim());
        return readText(pageElements.currentBalanceCurrency).replaceAll("[^a-zA-z]", "");
    }

    public String gettingMainAccountBalance() {
        UtilsMethods.printInfoLog("Getting Main Account Balance from Your Current Balance Widget : " + readText(pageElements.mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""))
        ;
        return readText(pageElements.mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
    }

    public String getVoiceExpiryDate() {
        UtilsMethods.printInfoLog("Getting Remaining Voice Expiry Date from Your Current Balance Widget : " + readText(pageElements.voiceExpiryDate).trim());
        return readText(pageElements.voiceExpiryDate).trim();
    }

    public String getDataExpiryDate() {
        UtilsMethods.printInfoLog("Getting Remaining Data Expiry Date from Your Current Balance Widget : " + readText(pageElements.dataExpiryDate).trim());
        return readText(pageElements.dataExpiryDate).trim();
    }

    public String getSmsExpiryDate() {
        UtilsMethods.printInfoLog("Getting Remaining SMS Expiry Date from Your Current Balance Widget : " + readText(pageElements.smsExpiryDate).trim());
        return readText(pageElements.smsExpiryDate).trim();
    }

    public String getVoiceBalance() {
        UtilsMethods.printInfoLog("Getting Remaining Voice Balance from Your Current Balance Widget : " + readText(pageElements.voiceBalance).trim());
        return readText(pageElements.voiceBalance).trim();
    }

    public String getDataBalance() {
        UtilsMethods.printInfoLog("Getting Remaining Data Balance from Your Current Balance Widget : " + readText(pageElements.dataBalance).trim());
        return readText(pageElements.dataBalance).trim();
    }

    public String getSmsBalance() {
        UtilsMethods.printInfoLog("Getting Remaining SMS Balance from Your Current Balance Widget : " + readText(pageElements.smsBalance).trim());
        return readText(pageElements.smsBalance).trim();
    }

    public String getLastRechargeDateTime() {
        UtilsMethods.printInfoLog("Getting Last Recharge Date from Your Current Balance Widget : " + readText(pageElements.lastRechargeDateTime).trim());
        return readText(pageElements.lastRechargeDateTime).trim();
    }

    public WidgetInteractionPage clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(pageElements.ticketIcon);
            return new WidgetInteractionPage(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Current Plan History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(pageElements.getTitle));
        return readText(pageElements.getTitle).toLowerCase();
    }
}
