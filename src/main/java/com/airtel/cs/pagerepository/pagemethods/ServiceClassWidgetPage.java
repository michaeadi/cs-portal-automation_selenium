package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.ServiceClassWidgetPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ServiceClassWidgetPage extends BasePage {

    ServiceClassWidgetPageElements pageElements;
    List<WebElement> as;

    public ServiceClassWidgetPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ServiceClassWidgetPageElements.class);
    }

    public boolean isServiceClassWidgetDisplay() {
        UtilsMethods.printInfoLog("Is Service Class Widget Display: " + checkState(pageElements.title));
        return checkState(pageElements.title);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]//span"));
        UtilsMethods.printInfoLog("Getting header Name at Row- " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAccumulator(int row, int column) {
        String value = readText(By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"][" + row + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][" + column + "]//span"));
        UtilsMethods.printInfoLog("Reading '" + getHeaders(column) + "' = " + value);
        return value.trim();
    }

    public Boolean getServiceStatus() {
        By status = By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"][2]//mat-slide-toggle//input");
        return Boolean.valueOf(driver.findElement(status).getAttribute("aria-checked"));
    }

    public int getNumberOfRows() {
        as = returnListOfElement(pageElements.rows);
        return as.size();
    }

    public String gettingServiceProfileNoResultFoundMessage() {
        UtilsMethods.printInfoLog("Validating error message when there is no data from com.airtel.cs.API : " + readText(pageElements.serviceProfileNoResultFoundMessage));
        return readText(pageElements.serviceProfileNoResultFoundMessage);
    }

    public boolean isServiceProfileNoResultFoundVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is no data from com.airtel.cs.API : " + isElementVisible(pageElements.serviceProfileNoResultFound));
        return isElementVisible(pageElements.serviceProfileNoResultFound);
    }

    public boolean isServiceProfileErrorVisible() {
        UtilsMethods.printInfoLog("Validating error is visible when there is Error in com.airtel.cs.API : " + isElementVisible(pageElements.serviceProfileError));
        return isElementVisible(pageElements.serviceProfileError);
    }
}
