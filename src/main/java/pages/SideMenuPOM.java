package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SideMenuPOM extends BasePage {


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
    By logout = By.xpath("//span[@class=\"logout-icon\"]");
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");


    public SideMenuPOM(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {
        return readText(userName);
    }

    public void clickOnSideMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        click(sideMenuOption);
    }

    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public boolean isSideMenuVisible() {
        waitTillLoaderGetsRemoved();
        return isElementVisible(sideMenuOption);
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

    public userManagementPOM openUserManagementPage() {
        hoverAndClick(adminSettings);
        click(userManagement);
        return new userManagementPOM(driver);
    }

    public profileManagementPOM openProfileManagementPage() {
        hoverAndClick(adminSettings);
        click(profileManagement);
        return new profileManagementPOM(driver);
    }

    public agentLoginPagePOM openSupervisorDashboard() {
        hoverAndClick(customerServices);
        click(supervisorDashboard);
        return new agentLoginPagePOM(driver);
    }

    public loginPagePOM logout() {
        click(logout);
        waitTillLoaderGetsRemoved();
        return new loginPagePOM(driver);
    }
}
