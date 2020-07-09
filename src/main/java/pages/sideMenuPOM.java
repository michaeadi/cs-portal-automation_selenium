package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class sideMenuPOM extends BasePage {


    By sideMenuOption = By.xpath("//mat-toolbar//button");
    By visi = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    By userName = By.xpath("//*[@class=\"ellipsis\"]");
    By adminSettings = By.xpath("//mat-list-item//span[contains(text(),'Admin')]");
    By customerServices = By.xpath("//mat-list-item//span[contains(text(),'Customer')]");
    By caseManagement = By.xpath("//mat-list-item//span[contains(text(),'Case')]");
    By userManagement = By.xpath("//a[contains(text(),'User management')]");
    By profileManagement = By.xpath("//a[contains(text(),'Profile Management')]");
    By customerInteraction = By.xpath("//a[contains(text(),'Customer Interaction')]");
    By supervisorDashboard = By.xpath("//a[contains(text(),'Supervisor Dashboard')]");
    By logout = By.xpath("//*[@class=\"color-name text-center left-sidenav-link ng-tns-c16-21\"]");
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");


    public sideMenuPOM(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {
        return readText(userName);
    }

    public void clickOnSideMenu() {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        click(sideMenuOption);
    }

    public void clickOnName() {
        click(userName);
    }

    public boolean isAdminSettingVisible() {
        return checkState(adminSettings);
    }

    public boolean isCustomerServicesVisible() {
        return checkState(customerServices);
    }

    public boolean isCaseManagementVisible() {
        return checkState(caseManagement);
    }

    public boolean isUserManagementVisible() {
        hoverAndClick(adminSettings);
        return checkState(userManagement);
    }

    public boolean isProfileManagementVisible() {
        hoverAndClick(adminSettings);
        return checkState(profileManagement);
    }

    public boolean isCustomerInteractionVisible() {
        hoverAndClick(customerServices);
        return checkState(customerInteraction);
    }

    public boolean isSupervisorDashboardVisible() {
        hoverAndClick(customerServices);
        return checkState(supervisorDashboard);
    }

    public customerInteractionsSearchPOM openCustomerInteractionPage() {
        hoverAndClick(customerServices);
        click(customerInteraction);
        return new customerInteractionsSearchPOM(driver);
    }
}
