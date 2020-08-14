package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

@Log4j2
public class CurrentBalanceWidgetPOM extends BasePage {
    By currentBalanceHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Your Current Plan \"]");
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
    By menu = By.xpath("//span[contains(text(),\"Your Current Plan \")]//parent::div/span[@class=\"card__card-header--menu ng-star-inserted\"]/img");
    By currentBalanceLastRechargeUnableToFetch = By.xpath("//span[@class=\"label-color\"]/span[@class=\"api-failed-error ng-star-inserted\"]");
    By lastRechargeDateTImeUnableTOFetch = By.xpath("//div[@class=\"api-failed-error ng-star-inserted\"]");
    By daDetails = By.xpath("//button[text()=\"DA Details\"]");
    By ticketIcon=By.xpath("//span[contains(text(),'Your Current Plan')]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    By getTitle=By.xpath("//span[contains(text(),'Your Current Plan')]");

    public CurrentBalanceWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isLastRechargeUnableToFetchVisible() {
        log.info("Validating is error visible for last recharge balance : " + isElementVisible(currentBalanceLastRechargeUnableToFetch));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating is error visible for last recharge balance : " + isElementVisible(currentBalanceLastRechargeUnableToFetch));
        return isElementVisible(currentBalanceLastRechargeUnableToFetch);
    }

    public boolean isLastRechargeDateTImeUnableTOFetch() {
        log.info("Validating is error visible for last recharge Date & Time : " + isElementVisible(lastRechargeDateTImeUnableTOFetch));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating is error visible for last recharge Date & Time : " + isElementVisible(lastRechargeDateTImeUnableTOFetch));
        return isElementVisible(lastRechargeDateTImeUnableTOFetch);
    }

    public String gettingLastRechargeUnableToFetchVisible() {
        log.info("Validating  error message for last recharge balance : " + readText(currentBalanceLastRechargeUnableToFetch));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating  error message for last recharge balance : " + readText(currentBalanceLastRechargeUnableToFetch));
        return readText(currentBalanceLastRechargeUnableToFetch);
    }

    public String gettingLastRechargeDateTImeUnableTOFetch() {
        log.info("Validating  error message for last recharge Date & Time : " + readText(lastRechargeDateTImeUnableTOFetch));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating  error message for last recharge Date & Time : " + readText(lastRechargeDateTImeUnableTOFetch));
        return readText(lastRechargeDateTImeUnableTOFetch);
    }


    public boolean isCurrentBalanceWidgetMenuVisible() {
        try{
        log.info("Checking is Your Current Balance Widget's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Your Current Balance Widget'Menu Visible");
        return isElementVisible(menu);
        } catch (Exception e) {
            throw new NoSuchElementException("Current Plan widget menu option does not display");
        }
    }

    public boolean isDADetailsMenuVisible() {
        log.info("Checking is DA Details Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is DA Details Visible");
        return checkState(daDetails);
    }

    public DADetailsPOM openingDADetails() {
        log.info("Clicking Current Balance Widget's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Current Balance Widget'Menu Visible");
        click(daDetails);
        return new DADetailsPOM(driver);
    }

    public void clickingCurrentBalanceWidgetMenu() {
        log.info("Clicking Current Balance Widget's Menu Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Current Balance Widget'Menu Visible");
        click(menu);
    }

    public String gettingLastRechargeAmount() {
        log.info("Getting Main Account Balance from Your Current Balance Widget : " + readText(currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Main Account Balance from Your Current Balance Widget : " + readText(currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""));
        return readText(currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
    }

    public boolean isCurrentBalanceWidgetVisible() {
        log.info("Checking is Your Current Balance Widget Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Your Current Balance Widget Visible");
        return isElementVisible(currentBalanceHeader);
    }

    public String gettingCurrentBalanceCurrency() {
        log.info("Getting Currency from Your Current Balance Widget : " + readText(currentBalanceCurrency).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Currency from Your Current Balance Widget : " + readText(currentBalanceCurrency).trim());
        return readText(currentBalanceCurrency);
    }

    public String gettingMainAccountBalance() {
        log.info("Getting Main Account Balance from Your Current Balance Widget : " + readText(mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""))
        ;
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Main Account Balance from Your Current Balance Widget : " + readText(mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", ""))
        ;
        return readText(mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
    }

    public String getVoiceExpiryDate() {
        log.info("Getting Remaining Voice Expiry Date from Your Current Balance Widget : " + readText(voiceExpiryDate).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Voice Expiry Date from Your Current Balance Widget : " + readText(voiceExpiryDate).trim());
        return readText(voiceExpiryDate).trim();
    }

    public String getDataExpiryDate() {
        log.info("Getting Remaining Data Expiry Date from Your Current Balance Widget : " + readText(dataExpiryDate).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Data Expiry Date from Your Current Balance Widget : " + readText(dataExpiryDate).trim());
        return readText(dataExpiryDate).trim();
    }

    public String getSmsExpiryDate() {
        log.info("Getting Remaining SMS Expiry Date from Your Current Balance Widget : " + readText(smsExpiryDate).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining SMS Expiry Date from Your Current Balance Widget : " + readText(smsExpiryDate).trim());
        return readText(smsExpiryDate).trim();
    }

    public String getVoiceBalance() {
        log.info("Getting Remaining Voice Balance from Your Current Balance Widget : " + readText(voiceBalance).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Voice Balance from Your Current Balance Widget : " + readText(voiceBalance).trim());
        return readText(voiceBalance).trim();
    }

    public String getDataBalance() {
        log.info("Getting Remaining Data Balance from Your Current Balance Widget : " + readText(dataBalance).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Data Balance from Your Current Balance Widget : " + readText(dataBalance).trim());
        return readText(dataBalance).trim();
    }

    public String getSmsBalance() {
        log.info("Getting Remaining SMS Balance from Your Current Balance Widget : " + readText(smsBalance).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining SMS Balance from Your Current Balance Widget : " + readText(smsBalance).trim());
        return readText(smsBalance).trim();
    }

    public String getLastRechargeDateTime() {
        log.info("Getting Last Recharge Date from Your Current Balance Widget : " + readText(lastRechargeDateTime).trim());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Last Recharge Date from Your Current Balance Widget : " + readText(lastRechargeDateTime).trim());
        return readText(lastRechargeDateTime).trim();
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
