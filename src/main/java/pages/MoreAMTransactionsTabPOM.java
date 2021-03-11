package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class MoreAMTransactionsTabPOM extends BasePage {

    By airtelMoneyNoResultFound = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]");
    By airtelMoneyNoResultFoundMessage = By.xpath("//span[contains(text(),\"Airtel Money Transactions \")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By airtelMoneyError = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card ng-star-inserted\"]//div[@class='image-container']");
    By title= By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]");
    /*
    * Filter POM
    * */
    By todayFilter=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//form//mat-radio-button//span[contains(text(),'Today')]");
    By dateRangeFilter=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//form/span/input");
    By lastSevenDays=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//form//mat-radio-button//span[contains(text(),'Last seven days')]");
    By lasTwoDays=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//form//mat-radio-button//span[contains(text(),'Last two days')]");
    /*
    * Search Box
    * */
    By searchTxnId=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//input[@type='search']");
    By searchBtn=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]//parent::div//button[@class=\"search-icon-btn\"]");


    public MoreAMTransactionsTabPOM(WebDriver driver) {
        super(driver);
    }

    public Boolean isNegSignDisplay(int row){
        By negAmount=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"]["+row+"]//span[@class=\"amount-sign db ng-star-inserted\"]");
        return checkState(negAmount);
    }

    public Boolean isPosSignDisplay(int row){
        By posAmount=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"]["+row+"]//span[@class=\"amount-sign cr ng-star-inserted\"]");
        return checkState(posAmount);
    }

    public boolean isAMMenuHistoryTabDisplay(){
        boolean status=checkState(title);
        printInfoLog("Is AM Menu History Tab Display: "+status);
        return status;
    }

    public boolean isTodayFilterTab(){
        boolean status=checkState(todayFilter);
        printInfoLog("Is Today Filter Radio button Display: "+status);
        return status;
    }

    public boolean isLastTwoDayFilterTab(){
        boolean status=checkState(lasTwoDays);
        printInfoLog("Is Last Two day Filter Radio button Display: "+status);
        return status;
    }

    public boolean isLastSevenDayFilterTab(){
        boolean status=checkState(lastSevenDays);
        printInfoLog("Is Last Seven day Filter Radio button Display: "+status);
        return status;
    }

    public boolean isDateRangeFilterTab(){
        boolean status=checkState(dateRangeFilter);
        printInfoLog("Is Date Range Filter Radio button Display: "+status);
        return status;
    }

    public boolean isSearchTxnIdBox(){
        boolean status=checkState(searchTxnId);
        printInfoLog("Is Search Transaction Id Box Display: "+status);
        return status;
    }

    public void searchByTxnId(String txnId){
        printInfoLog("Search By Transaction Id: "+txnId);
        writeText(searchTxnId,txnId);
    }

    public void clickSearchBtn(){
        printInfoLog("Clicking Search button");
        click(searchBtn);
    }


    public boolean isAirtelMoneyErrorVisible() {
        printInfoLog("Validating error is visible when there is Error inAPI : " + isElementVisible(airtelMoneyError));
        return isElementVisible(airtelMoneyError);
    }

    public String gettingAirtelMoneyNoResultFoundMessage() {
        printInfoLog("Validating error message when there is no data from API : " + readText(airtelMoneyNoResultFoundMessage));
        return readText(airtelMoneyNoResultFoundMessage);
    }

    public boolean isAirtelMoneyNoResultFoundVisible() {
        printInfoLog("Validating error is visible when there is no data from API : " + isElementVisible(airtelMoneyNoResultFound));
        return isElementVisible(airtelMoneyNoResultFound);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]/div[" + row + "]"));
        printInfoLog("Getting header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToHeader(int row,int column){
        String value=readText(By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"]["+row+"]/div["+column+"]//span"));
        printInfoLog("Reading Value("+row+"): "+value);
        return value;
    }

    public Boolean isResendSMS(){
        By check=By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),\"Airtel Money Transactions\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"card__card-header--card-body--table--data-list row-border\"][1]/div[12]//img[1][@class=\"hide-reversal ng-star-inserted\"]");
        return checkState(check);
    }
}
