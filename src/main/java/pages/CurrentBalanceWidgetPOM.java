package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
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

    public CurrentBalanceWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public String gettingLastRechargeAmount() {
        log.info("Getting Main Account Balance from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Main Account Balance from Your Current Balance Widget");
        return readText(currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
    }

    public boolean isCurrentBalanceWidgetVisible() {
        log.info("Checking is Your Current Balance Widget Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Your Current Balance Widget Visible");
        return isElementVisible(currentBalanceHeader);
    }

    public String gettingCurrentBalanceCurrency() {
        log.info("Getting Currency from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Currency from Your Current Balance Widget");
        return readText(currentBalanceCurrency);
    }

    public String gettingMainAccountBalance() {
        log.info("Getting Main Account Balance from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Main Account Balance from Your Current Balance Widget");
        return readText(mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
    }

    public String getVoiceExpiryDate() {
        log.info("Getting Remaining Voice Expiry Date from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Voice Expiry Date from Your Current Balance Widget");
        return readText(voiceExpiryDate).trim();
    }

    public String getDataExpiryDate() {
        log.info("Getting Remaining Data Expiry Date from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Data Expiry Date from Your Current Balance Widget");
        return readText(dataExpiryDate).trim();
    }

    public String getSmsExpiryDate() {
        log.info("Getting Remaining SMS Expiry Date from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining SMS Expiry Date from Your Current Balance Widget");
        return readText(smsExpiryDate).trim();
    }

    public String getVoiceBalance() {
        log.info("Getting Remaining Voice Balance from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Voice Balance from Your Current Balance Widget");
        return readText(voiceBalance).trim();
    }

    public String getDataBalance() {
        log.info("Getting Remaining Data Balance from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining Data Balance from Your Current Balance Widget");
        return readText(dataBalance).trim();
    }

    public String getSmsBalance() {
        log.info("Getting Remaining SMS Balance from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Remaining SMS Balance from Your Current Balance Widget");
        return readText(smsBalance).trim();
    }

    public String getLastRechargeDateTime() {
        log.info("Getting Last Recharge Date from Your Current Balance Widget");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Last Recharge Date from Your Current Balance Widget");
        return readText(lastRechargeDateTime).trim();
    }
}
