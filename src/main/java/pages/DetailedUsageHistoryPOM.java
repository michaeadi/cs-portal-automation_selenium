package pages;

import Utils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetailedUsageHistoryPOM extends BasePage {

    By title = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]");

    /*
     * Filter Headers
     * */
    By freeCDR = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//form/span[1]//label/span");
    By typeOfCDR = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//form/span[2]");
    By todayDateFilter = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//form/span[3]//mat-radio-button[1]//span[contains(text(),'Today')]");
    By last2DayDateFilter = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//form/span[3]//mat-radio-button[2]//span[contains(text(),'two days')]");
    By last7DayDateFilter = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//form/span[3]//mat-radio-button[3]//span[contains(text(),'seven days')]");
    By datePicker = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//form/span[4]//input");

    /*
     * Header Names
     * */
    By headers = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div");

    //Pagination Details
    By pagination = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"pagination ng-star-inserted\"]/div[1]");
    By noResultIcon = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"no-result-found ng-star-inserted\"]/img");


    public DetailedUsageHistoryPOM(WebDriver driver) {
        super(driver);
    }

    public Boolean isWidgetDisplay() {
        UtilsMethods.printInfoLog("Checking Detailed Usage history widget display: " + checkState(title));
        return checkState(title);
    }

    public Boolean isFreeCDR() {
        UtilsMethods.printInfoLog("Is Free CDR Checkbox displayed: " + checkState(freeCDR));
        return checkState(freeCDR);
    }

    public Boolean isTypeOfCDR() {
        UtilsMethods.printInfoLog("Is Type of CDR Option displayed: " + checkState(typeOfCDR));
        return checkState(typeOfCDR);
    }

    public Boolean isTodayDateFilter() {
        UtilsMethods.printInfoLog("Is Today Date Filter Option displayed: " + checkState(todayDateFilter));
        return checkState(todayDateFilter);
    }

    public Boolean isLast2DayDateFilter() {
        UtilsMethods.printInfoLog("Is last 2 day Date Filter Option displayed: " + checkState(last2DayDateFilter));
        return checkState(last2DayDateFilter);
    }

    public Boolean isLast7DayDateFilter() {
        UtilsMethods.printInfoLog("Is last 7 day Date Filter Option displayed: " + checkState(last7DayDateFilter));
        return checkState(last7DayDateFilter);
    }

    public Boolean isDatePickerDisplay() {
        UtilsMethods.printInfoLog("Is Date picker displayed: " + checkState(datePicker));
        return checkState(datePicker);
    }

    public String getHeaders(int column) {
        By header = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + column + "]/span");
        UtilsMethods.printInfoLog("Reading Header at POS(" + column + "): " + readText(header));
        return readText(header);
    }

    public String getValueCorrespondingToHeader(int row, int column) {
        By value = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][" + row + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][" + column + "]/span");
        UtilsMethods.printInfoLog("Reading value for Header name '" + getHeaders(column) + "' is: " + readText(value));
        return readText(value);
    }

    public Boolean checkSignDisplay(int row) {
        By value = By.xpath("//div[@class=\"card__card-header\"]//span[contains(text(),'USAGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][" + row + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]/img[@class=\"sign-icon-before ng-star-inserted\"]");
        UtilsMethods.printInfoLog("Checking Negative Sign Display at ROW(" + row + ")");
        return checkState(value);
    }

    public Boolean isPagination() {
        UtilsMethods.printInfoLog("Is pagination displayed: " + checkState(pagination));
        return checkState(pagination);
    }

    public boolean getNoResultFound() {
        UtilsMethods.printInfoLog("Checking No Result Found Message Display");
        return checkState(noResultIcon);
    }
}
