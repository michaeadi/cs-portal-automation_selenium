package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ServiceClassWidgetPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ServiceClassWidget extends BasePage {

    ServiceClassWidgetPage pageElements;
    List<WebElement> as;

    public ServiceClassWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ServiceClassWidgetPage.class);
    }

    public boolean isServiceClassWidgetDisplay() {
        final boolean state = isEnabled(By.xpath(pageElements.title));
        commonLib.info("Is Service Class Widget Display: " + state);
        return state;
    }

    public String getHeaders(int row) {
        String header = getText(By.xpath(pageElements.title+pageElements.headerRow + row + pageElements.headerValue));
        commonLib.info("Getting header Name at Row- " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToServiceProfile(int row, int column) {
        String value = getText(By.xpath(pageElements.title+pageElements.offerColumnHeader + row + pageElements.offerColumnValue + column + pageElements.headerValue));
        commonLib.info("Reading '" + getHeaders(column) + "' = " + value);
        return value.trim();
    }

    public Boolean getServiceStatus(int row) {
        By status = By.xpath("//span[contains(text(),\"Service Profile\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"]["+row+"]//mat-slide-toggle//input");
        return Boolean.valueOf(driver.findElement(status).getAttribute("aria-checked"));
    }

    public String gettingServiceProfileNoResultFoundMessage() {
        final String text = getText(By.xpath(pageElements.title+pageElements.noResultFoundMessage));
        commonLib.info("Validating error message when there is no data from com.airtel.cs.API : " + text);
        return text;
    }

    public boolean isServiceProfileNoResultFoundVisible() {
        final boolean visible = isElementVisible(By.xpath(pageElements.title+pageElements.noResultFoundIcon));
        commonLib.info("Validating error is visible when there is no data from com.airtel.cs.API : " + visible);
        return visible;
    }

    public boolean isServiceProfileErrorVisible() {
        final boolean visible = isElementVisible(By.xpath(pageElements.title+pageElements.unableToFetch));
        commonLib.info("Validating error is visible when there is Error in com.airtel.cs.API : " + visible);
        return visible;
    }
}
