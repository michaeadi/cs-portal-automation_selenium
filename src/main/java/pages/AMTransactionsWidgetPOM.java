package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class AMTransactionsWidgetPOM extends BasePage {
    By airtelMoneyTransactionHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Airtel Money Transactions \"]");
    By airtelMoneyBalance = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"ng-star-inserted\"]");
    By airtelMoneyCurrency = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"currency ng-star-inserted\"]");
    By airtelMoneyDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//following-sibling::form//child::input[@name=\"dateRange\"]");

    public AMTransactionsWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isAirtelMoneyTransactionWidgetIsVisible() {
        log.info("Checking is Airtel Money Widget Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Airtel Money Widget Visible");
        return isElementVisible(airtelMoneyTransactionHeader);
    }

    public boolean isAirtelMoneyWidgetDatePickerVisible() {
        log.info("Checking Airtel Money Widget Date Picker Visibility ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking Airtel Money Widget Date Picker Visibility ");
        return checkState(airtelMoneyDatePicker);
    }

    public String gettingAirtelMoneyCurrency() {
        log.info("Getting Airtel Money Currency  from Widget : " + readText(airtelMoneyCurrency));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Airtel Money Currency from Widget : " + readText(airtelMoneyCurrency));
        return readText(airtelMoneyCurrency);
    }

    public int gettingAirtelMoneyBalance() {
        log.info("Getting Airtel Money Balance from Widget : " + readText(airtelMoneyBalance));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Airtel Money Balance from Widget : " + readText(airtelMoneyBalance));
        return Integer.parseInt(readText(airtelMoneyBalance));
    }
}
