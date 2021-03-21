package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class MoreRechargeHistoryPOM extends BasePage {

    By widgetName=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//parent::div[@class=\"card__card-header\"]");
    By datePicker=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Recharge History\")]//ancestor::div[@class='card widget ng-star-inserted']//input[@name='dateRange']");
    By todayDateFilter=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//parent::div[@class=\"card__card-header\"]//span[@class=\"filter-object\"]//form//mat-radio-group[@role=\"radiogroup\"]//span[contains(text(),'Today')]");
    By last7DaysFilter=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//parent::div[@class=\"card__card-header\"]//span[@class=\"filter-object\"]//form//mat-radio-group[@role=\"radiogroup\"]//span[contains(text(),'seven days')]");
    By last2DaysFilter=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//parent::div[@class=\"card__card-header\"]//span[@class=\"filter-object\"]//form//mat-radio-group[@role=\"radiogroup\"]//span[contains(text(),'two days')]");
    By listOfRecharge=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"]");
    List<WebElement> rows=returnListOfElement(listOfRecharge);
    By pagination=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//pagination-template//ul");
    By errorMessage=By.xpath("//span[contains(text(),\"Recharge History\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");

    public Boolean isWidgetDisplay(){
        UtilsMethods.printInfoLog("Checking More Recharge History Widget Display");
        return checkState(widgetName);
    }

    public Boolean IsPagination(){
        UtilsMethods.printInfoLog("Is Pagination Available: "+checkState(pagination));
        return checkState(pagination);
    }

    public Boolean isDatePickerDisplay(){
        UtilsMethods.printInfoLog("Checking More Recharge History Widget Date Picker Display");
        return checkState(widgetName);
    }


    public String getHeaders(int row) {
        By headers=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div["+row+"]/span");
        String header = readText(headers);
        UtilsMethods.printInfoLog("Reading header Name: "+ header);
        return header;
    }

    public String getSubHeaders(int row) {
        By headers=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div["+row+"]/span[2]");
        String header = readText(headers);
        UtilsMethods.printInfoLog("Reading Sub header Name: "+ header);
        return header;
    }

    public Boolean getNoResultFound(){
        UtilsMethods.printInfoLog("Is no result found message display: "+checkState(errorMessage));
        return checkState(errorMessage);
    }

    public String getValueCorrespondingToRechargeHeader(int row,int column){
        By value=By.xpath("//span[contains(text(),'RECHARGE HISTORY')]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"ng-star-inserted\"]["+row+"]//div["+column+"]/span");
        UtilsMethods.printInfoLog("Reading '"+getHeaders(column)+"' = "+readText(value));
        return readText(value);
    }


    public MoreRechargeHistoryPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isDatePickerVisible() {
        UtilsMethods.printInfoLog("Is DatePicker available: "+checkState(datePicker));
        return checkState(datePicker);
    }

    public boolean isLast2DateVisible() {
        UtilsMethods.printInfoLog("Is Last 2 Day Date available: "+checkState(last2DaysFilter));
        return checkState(last2DaysFilter);
    }

    public boolean isLast7DateVisible() {
        UtilsMethods.printInfoLog("Is Last 7 Day Date available: "+checkState(last7DaysFilter));
        return checkState(last7DaysFilter);
    }

    public boolean isTodayDateVisible() {
        UtilsMethods.printInfoLog("Is Today Day Date available: "+checkState(todayDateFilter));
        return checkState(todayDateFilter);
    }

    public int getNumbersOfRows() {
        return rows.size();
    }

}
