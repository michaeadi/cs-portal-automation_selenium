package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Log4j2
public class CurrentBalanceWidgetPOM extends BasePage {
    By currentBalanceHeader = By.xpath("//span[@class='card__card-header--label--text' and contains(text(),'Your Current')]");
    By currentBalanceCurrency = By.xpath("//div[@class=\"card__content--top--left ng-star-inserted\"]/span[@class=\"currency ng-star-inserted\"]");
    By mainAccountBalance = By.xpath("//div[@class=\"card__content--top--left ng-star-inserted\"]");
    By currentBalanceLastRecharge = By.xpath("//span[@class=\"label-color\"]/span[@class=\"currency ng-star-inserted\"]");
    By lastRechargeDateTime = By.xpath("//div[@class=\"date_time ng-star-inserted\"]");
    By voiceBalance = By.xpath("//span[@class=\"card__content--bottom--plan ng-star-inserted\"][1]/p[@class=\"balance ng-star-inserted\"]");
    By dataBalance = By.xpath("//span[@class=\"card__content--bottom--plan ng-star-inserted\"][2]/p[@class=\"balance ng-star-inserted\"]");
    By smsBalance = By.xpath("//span[@class=\"card__content--bottom--plan ng-star-inserted\"][3]/p[@class=\"balance ng-star-inserted\"]");
    By voiceExpiryDate = By.xpath("//span[@class=\"card__content--bottom--plan ng-star-inserted\"][1]/p[@class=\"ng-star-inserted\"][2]");
    By dataExpiryDate = By.xpath("//span[@class=\"card__content--bottom--plan ng-star-inserted\"][2]/p[@class=\"ng-star-inserted\"][2]");
    By smsExpiryDate = By.xpath("//span[@class=\"card__content--bottom--plan ng-star-inserted\"][3]/p[@class=\"ng-star-inserted\"][2]");
    By menu = By.xpath("//span[@class='card__card-header--label--text' and contains(text(),'Your Current')]//ancestor::div[1]//span/img[@class='header-action-icon ng-star-inserted']");
    By currentBalanceLastRechargeUnableToFetch = By.xpath("//span[@class=\"label-color\"]/span[@class=\"api-failed-error ng-star-inserted\"]");
    By lastRechargeDateTImeUnableTOFetch = By.xpath("//div[@class=\"api-failed-error ng-star-inserted\"]");
    By ticketIcon = By.xpath("//span[@class='card__card-header--label--text' and contains(text(),'Your Current')]//ancestor::div[1]//span/img[@class='interaction-ticket']");
    By getTitle = By.xpath("//span[contains(text(),'Your Current Plan')]");

    public CurrentBalanceWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isLastRechargeUnableToFetchVisible() {
        UtilsMethods.printInfoLog("Validating is error visible for last recharge balance : " + isElementVisible(currentBalanceLastRechargeUnableToFetch));
        return isElementVisible(currentBalanceLastRechargeUnableToFetch);
    }

    public boolean isLastRechargeDateTImeUnableTOFetch() {
        UtilsMethods.printInfoLog("Validating is error visible for last recharge Date & Time : " + isElementVisible(lastRechargeDateTImeUnableTOFetch));
        return isElementVisible(lastRechargeDateTImeUnableTOFetch);
    }

    public String gettingLastRechargeUnableToFetchVisible() {
        UtilsMethods.printInfoLog("Validating  error message for last recharge balance : " + readText(currentBalanceLastRechargeUnableToFetch));
        return readText(currentBalanceLastRechargeUnableToFetch);
    }

    public String gettingLastRechargeDateTImeUnableTOFetch() {
        UtilsMethods.printInfoLog("Validating  error message for last recharge Date & Time : " + readText(lastRechargeDateTImeUnableTOFetch));
        return readText(lastRechargeDateTImeUnableTOFetch);
    }


    public boolean isCurrentBalanceWidgetMenuVisible() {
        try {
            UtilsMethods.printInfoLog("Checking is Your Current Balance Widget'Menu Visible");
            return isElementVisible(menu);
        } catch (Exception e) {
            throw new NoSuchElementException("Current Plan widget menu option does not display");
        }
    }

    public DADetailsPOM openingDADetails() {
        UtilsMethods.printInfoLog("Clicking Current Balance Widget'Menu Visible");
        click(menu);
        return new DADetailsPOM(driver);
    }

    public String gettingLastRechargeAmount() {
        UtilsMethods.printInfoLog("Getting Main Account Balance from Your Current Balance Widget : " + readText(currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""));
        return readText(currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "").trim();
    }

    public boolean isCurrentBalanceWidgetVisible() {
        UtilsMethods.printInfoLog("Checking is Your Current Balance Widget Visible");
        return isElementVisible(currentBalanceHeader);
    }

    public String gettingCurrentBalanceCurrency() {
        UtilsMethods.printInfoLog("Getting Currency from Your Current Balance Widget : " + readText(currentBalanceCurrency).trim());
        return readText(currentBalanceCurrency).replaceAll("[^a-zA-z]", "");
    }

    public String gettingMainAccountBalance() {
        UtilsMethods.printInfoLog("Getting Main Account Balance from Your Current Balance Widget : " + readText(mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""))
        ;
        return readText(mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
    }

    public String getVoiceExpiryDate() {
        UtilsMethods.printInfoLog("Getting Remaining Voice Expiry Date from Your Current Balance Widget : " + readText(voiceExpiryDate).trim());
        return readText(voiceExpiryDate).trim();
    }

    public String getDataExpiryDate() {
        UtilsMethods.printInfoLog("Getting Remaining Data Expiry Date from Your Current Balance Widget : " + readText(dataExpiryDate).trim());
        return readText(dataExpiryDate).trim();
    }

    public String getSmsExpiryDate() {
        UtilsMethods.printInfoLog("Getting Remaining SMS Expiry Date from Your Current Balance Widget : " + readText(smsExpiryDate).trim());
        return readText(smsExpiryDate).trim();
    }

    public String getVoiceBalance() {
        UtilsMethods.printInfoLog("Getting Remaining Voice Balance from Your Current Balance Widget : " + readText(voiceBalance).trim());
        return readText(voiceBalance).trim();
    }

    public String getDataBalance() {
        UtilsMethods.printInfoLog("Getting Remaining Data Balance from Your Current Balance Widget : " + readText(dataBalance).trim());
        return readText(dataBalance).trim();
    }

    public String getSmsBalance() {
        UtilsMethods.printInfoLog("Getting Remaining SMS Balance from Your Current Balance Widget : " + readText(smsBalance).trim());
        return readText(smsBalance).trim();
    }

    public String getLastRechargeDateTime() {
        UtilsMethods.printInfoLog("Getting Last Recharge Date from Your Current Balance Widget : " + readText(lastRechargeDateTime).trim());
        return readText(lastRechargeDateTime).trim();
    }

    public WidgetInteractionPOM clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(ticketIcon);
            return new WidgetInteractionPOM(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Current Plan History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(getTitle));
        return readText(getTitle).toLowerCase();
    }
}
