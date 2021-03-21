package pages;

import Utils.ExtentReports.ExtentTestManager;
import Utils.UtilsMethods;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ServiceClassWidgetPOM extends BasePage {

    By title= By.xpath("//span[contains(text(),'Service Profile')]");
    By rows = By.xpath("//div[@class=\"card__card-header\"]/span[contains(text(),\"Service Profile\")]//parent::div//following-sibling::div[@class=\"card__content restricted ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--data-list row-border\"]");
    List<WebElement> as = returnListOfElement(rows);
    By serviceProfileNoResultFound = By.xpath("//span[contains(text(),\"Service Profile\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/img");
    By serviceProfileNoResultFoundMessage = By.xpath("//span[contains(text(),\"Service Profile\")]/ancestor::div[@class=\"card widget ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"no-result-found ng-star-inserted\"]/span/span");
    By serviceProfileError = By.xpath("//span[contains(text(),\"Service Profile\")]/ancestor::div[@class=\"card ng-star-inserted\"]/div[@class=\"card__content restricted ng-star-inserted\"]/descendant::div[@class=\"widget-error apiMsgBlock ng-star-inserted\"][1]");

    public ServiceClassWidgetPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isServiceClassWidgetDisplay(){
        UtilsMethods.printInfoLog("Is CRBT Widget Display: "+checkState(title));
        return checkState(title);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div["+row+"]//span"));
        UtilsMethods.printInfoLog("Getting header Name at Row- " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAccumulator(int row,int column){
        String value=readText(By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"]["+row+"]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"]["+column+"]//span"));
        UtilsMethods.printInfoLog("Reading '"+getHeaders(column)+"' = "+value);
        return value.trim();
    }

    public Boolean getServiceStatus(){
        By status=By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"][2]//mat-slide-toggle//input");
        return Boolean.valueOf(driver.findElement(status).getAttribute("aria-checked"));
    }

    public int getNumberOfRows() {
        return as.size();
    }

    public String gettingServiceProfileNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from API : " + readText(serviceProfileNoResultFoundMessage));
        return readText(serviceProfileNoResultFoundMessage);
    }

    public boolean isServiceProfileNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from API : " + isElementVisible(serviceProfileNoResultFound));
        return isElementVisible(serviceProfileNoResultFound);
    }

    public boolean isServiceProfileErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error in API : " + isElementVisible(serviceProfileError));
        return isElementVisible(serviceProfileError);
    }
}
