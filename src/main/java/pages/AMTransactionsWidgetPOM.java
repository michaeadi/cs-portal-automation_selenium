package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

@Log4j2
public class AMTransactionsWidgetPOM extends BasePage {
    By airtelMoneyTransactionHeader = By.xpath("//span[@class=\"card__card-header--label\" and text()=\"Airtel Money Transactions \"]");
    By airtelMoneyBalance = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"ng-star-inserted\"]");
    By airtelMoneyCurrency = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"currency ng-star-inserted\"]");
    By airtelMoneyDatePicker = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//ancestor::div[@class='card widget ng-star-inserted']//input[@name='dateRange']");
    By airtelMoneyBalanceUnableToFetch = By.xpath("//div[@class=\"card__content--money-balance ng-star-inserted\"]/span[@class=\"api-failed-error ng-star-inserted\"]");
    By airtelMoneyNoResultFound = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"no-result-found ng-star-inserted\"]/img");
    By airtelMoneyNoResultFoundMessage = By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By airtelMoneyError = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card ng-star-inserted\"]//div[@class='image-container']");
    By ticketIcon = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),'Airtel')]//ancestor::div[1]//span/img[@class='interaction-ticket']");
    By getTitle = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]");
    By clickMenu = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header\"]//span[@class=\"card__card-header--menu ng-star-inserted\"]/img");
    By amHistoryTab = By.xpath("//button[contains(text(),\"AM History\")][@role=\"menuitem\"]");
    By transactionId = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//ancestor::div[@class='card widget ng-star-inserted']//input[@placeholder='Transaction ID']");
    By transactionSearchBtn = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//ancestor::div[@class='card widget ng-star-inserted']//input[@placeholder='Transaction ID']//following-sibling::button");

    public AMTransactionsWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isAirtelMoneyErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error inAPI : " + isElementVisible(airtelMoneyError));
        return isElementVisible(airtelMoneyError);
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from API : " + readText(airtelMoneyNoResultFoundMessage));
        return readText(airtelMoneyNoResultFoundMessage);
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from API : " + isElementVisible(airtelMoneyNoResultFound));
        return isElementVisible(airtelMoneyNoResultFound);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]"));
        UtilsMethods.printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row, int column) {
        String value = readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][" + row + "]/div[" + column + "]//span"));
        UtilsMethods.printInfoLog("Reading Value(" + row + "): " + value);
        return value;
    }

    public boolean isAirtelMoneyTransactionWidgetIsVisible() {
        UtilsMethods.printInfoLog("Checking is Airtel Money Widget Visible");
        return isElementVisible(airtelMoneyTransactionHeader);
    }

    public boolean isAirtelMoneyWidgetDatePickerVisible() {
        UtilsMethods.printInfoLog("Checking Airtel Money Widget Date Picker Visibility ");
        return checkState(airtelMoneyDatePicker);
    }

    public String gettingAirtelMoneyCurrency() {
        UtilsMethods.printInfoLog("Getting Airtel Money Currency from Widget : " + readText(airtelMoneyCurrency));
        return readText(airtelMoneyCurrency);
    }

    public String gettingAMBalanceUnableToFetchMessage() {
        UtilsMethods.printInfoLog("Getting error Message for unable to fetch AM Money Balance : " + readText(airtelMoneyCurrency));
        return readText(airtelMoneyBalanceUnableToFetch);
    }

    public boolean isAMBalanceUnableToFetch() {
        UtilsMethods.printInfoLog("Is error visible on unable to fetch AM Money Balance : " + isElementVisible(airtelMoneyBalanceUnableToFetch));
        return isElementVisible(airtelMoneyBalanceUnableToFetch);
    }

    public double gettingAirtelMoneyBalance() {
        UtilsMethods.printInfoLog("Getting Airtel Money Balance from Widget : " + readText(airtelMoneyBalance));
        return Double.parseDouble(readText(airtelMoneyBalance));
    }

    public WidgetInteractionPOM clickTicketIcon() {
        try {
            UtilsMethods.printInfoLog("Clicking on Ticket Icon");
            click(ticketIcon);
            return new WidgetInteractionPOM(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on AM History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(getTitle));
        return readText(getTitle).toLowerCase();
    }

    public Boolean isResendSMS() {
        By check = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][1]/div[6]//img[1][@class=\"hide-reversal ng-star-inserted\"]");
        return checkState(check);
    }

    public void clickMenuOption() {
        UtilsMethods.printInfoLog("Clicking Menu Option");
        click(clickMenu);
    }

    public MoreAMTransactionsTabPOM openAMHistoryTab() {
        UtilsMethods.printInfoLog("Opening AM History Sub Tab");
        click(amHistoryTab);
        return new MoreAMTransactionsTabPOM(driver);
    }

    public Boolean isTransactionId() {
        UtilsMethods.printInfoLog("Checking is Transaction Id Box Displayed");
        return checkState(transactionId);
    }

    public void writeTransactionId(String id) {
        writeText(transactionId, id);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking on Search Button");
        click(transactionSearchBtn);
    }

    public String doubleClickOnTransactionId(int row) {
        By id = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][" + row + "]/div[4]//span");
        Actions act = new Actions(driver);
        act.moveToElement(driver.findElement(id)).doubleClick().build().perform();
        return readText(id);
    }
}
