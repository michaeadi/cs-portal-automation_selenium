package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class customerInteractionPagePOM extends BasePage {
    By searchNumber = By.xpath("//input[@placeholder=\"Search\"]");
    By customerName = By.xpath("//p[@class=\"fw-bold name ng-star-inserted\"]");
    By customerDOB = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-sr-dashboard/div/div[1]/app-sr-customer-details/div/div/div/div[1]/div/div/div[1]/div[2]/ul/li[2]/span/span[2]");
    By activationDate = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[2]/p[1]/span[1]");
    By activationTime = By.xpath("//span[@class='sp-block ng-star-inserted']");
    By simNumber = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/ul[1]/li[2]/p[1]/span[1]");
    By simType = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/ul[1]/li[2]/p[2]/span[1]");
    By PUK1 = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[1]/li[1]/div[1]/div[1]/p[1]/span[2]");
    By PUK2 = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[1]/li[1]/div[1]/div[2]/p[1]/span[2]");
    By idType = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[4]/div[1]/ul[1]/li[1]/p[1]");
    By idNumber = By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[2]/app-admin-panel[1]/div[1]/div[1]/app-sidenav-bar[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/app-service-request[1]/div[1]/app-sr-dashboard[1]/div[1]/div[1]/app-sr-customer-details[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[4]/div[1]/ul[1]/li[2]/li[1]/p[1]/span[1]");

    public customerInteractionPagePOM(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        waitVisibility(searchNumber);
        return checkState(searchNumber);
    }

    public String getCustomerName() {
        return readText(customerName);
    }

    public String getCustomerDOB() {
        return readText(customerDOB);
    }

    public String getActivationDate() {
        return readText(activationDate);
    }

    public String getActivationTime() {
        return readText(activationTime);
    }

    public String getSimNumber() {
        return readText(simNumber);
    }

    public String getSimType() {
        return readText(simType);
    }

    public String getPUK1() {
        return readText(PUK1);
    }

    public String getPUK2() {
        return readText(PUK2);
    }

    public String getIdType() {
        return readText(idType);
    }

    public String getIdNumber() {
        return readText(idNumber);
    }
}
