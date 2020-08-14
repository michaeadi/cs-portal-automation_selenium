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
    By airtelMoneyBalanceUnableToFetch = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"api-failed-error ng-star-inserted\"]");
    By airtelMoneyNoResultFound = By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    By airtelMoneyNoResultFoundMessage = By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By airtelMoneyError = By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]/ancestor::div[@class=\"card ng-star-inserted\"]//div[@class='image-container']");
    By ticketIcon=By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    By getTitle=By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]");

    public boolean isAirtelMoneyErrorVisible() {
        log.info("Validating error is visible when there is Error inAPI : " + isElementVisible(airtelMoneyError));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is Error inAPI : " + isElementVisible(airtelMoneyError));
        return isElementVisible(airtelMoneyError);
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        log.info("Validating error message when there is no data from API : " + readText(airtelMoneyNoResultFoundMessage));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error message when there is no data from API : " + readText(airtelMoneyNoResultFoundMessage));
        return readText(airtelMoneyNoResultFoundMessage);
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        log.info("Validating error is visible when there is no data from API : " + isElementVisible(airtelMoneyNoResultFound));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating error is visible when there is no data from API : " + isElementVisible(airtelMoneyNoResultFound));
        return isElementVisible(airtelMoneyNoResultFound);
    }


    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading\"]/div[" + row + "]"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

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

    public String gettingAMBalanceUnableToFetchMessage() {
        log.info("Getting error Message for unable to fetch AM Money Balance : " + readText(airtelMoneyBalanceUnableToFetch));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting error Message for unable to fetch AM Money Balance : " + readText(airtelMoneyCurrency));
        return readText(airtelMoneyBalanceUnableToFetch);
    }

    public boolean isAMBalanceUnableToFetch() {
        log.info("Is error visible on unable to fetch AM Money Balance : " + isElementVisible(airtelMoneyBalanceUnableToFetch));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Is error visible on unable to fetch AM Money Balance : " + isElementVisible(airtelMoneyBalanceUnableToFetch));
        return isElementVisible(airtelMoneyBalanceUnableToFetch);
    }

    public double gettingAirtelMoneyBalance() {
        log.info("Getting Airtel Money Balance from Widget : " + readText(airtelMoneyBalance));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Airtel Money Balance from Widget : " + readText(airtelMoneyBalance));
        return Double.parseDouble(readText(airtelMoneyBalance));
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
