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
    By airtelMoneyDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//form/span/input");
    By airtelMoneyBalanceUnableToFetch = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"api-failed-error ng-star-inserted\"]");
    By airtelMoneyNoResultFound = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    By airtelMoneyNoResultFoundMessage = By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By airtelMoneyError = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card ng-star-inserted\"]//div[@class='image-container']");
    By ticketIcon = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//span[@class=\"card__card-header--icon ng-star-inserted\"]");
    By getTitle = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]");
    By clickMenu=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header\"]//span[@class=\"card__card-header--menu ng-star-inserted\"]/img");
    By amHistoryTab=By.xpath("//button[contains(text(),\"AM History\")][@role=\"menuitem\"]");

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
        String header = readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row,int column){
        String value=readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"]["+row+"]/div["+column+"]//span"));
        printInfoLog("Reading Value("+row+"): "+value);
        return value;
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

    public Boolean isResendSMS(){
        By check=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][1]/div[6]//img[1][@class=\"hide-reversal ng-star-inserted\"]");
        return checkState(check);
    }

    public void clickMenuOption(){
        printInfoLog("Clicking Menu Option");
        click(clickMenu);
    }

    public MoreAMTransactionsTabPOM openAMHistoryTab(){
        printInfoLog("Opening AM History Sub Tab");
        click(amHistoryTab);
        return new MoreAMTransactionsTabPOM(driver);
    }
}
