package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
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
    By userManagement = By.xpath("//a[contains(text(),'User management') or contains(text(),'User Management')]");
    By profileManagement = By.xpath("//a[contains(text(),'Profile Management')]");
    By customerInteraction = By.xpath("//a[contains(text(),'Customer Interaction')]");
    By templateManagement = By.xpath("//a[contains(text(),'Template Management')]");
    By ticketBulkUpdate = By.xpath("//a[contains(text(),'Bulk')]");
    By supervisorDashboard = By.xpath("//a[contains(text(),'Supervisor Dashboard')]");
    By agentDashboard = By.xpath("//a[contains(text(),'Ticket Dashboard')]");
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
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Clicking on Side Menu");
        click(sideMenuOption);
    }

    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        UtilsMethods.printInfoLog("Wait for Side menu and Home Page to be loaded");

    }

    public boolean isSideMenuVisible() {
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Checking that  Side Menu options are Visible or Not");

        return isElementVisible(sideMenuOption);
    }


    public void clickOnName() {
        log.info("Clicking on User Name");
        click(userName);
    }

    public boolean isAdminSettingVisible() {
        UtilsMethods.printInfoLog("Checking that is Admin Setting  Visible or Not");
        return checkState(adminSettings);
    }

    public boolean isCustomerServicesVisible() {
        UtilsMethods.printInfoLog("Checking that is Customer Service Visible or Not");

        return checkState(customerServices);
    }

    public boolean isCaseManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is Customer Service Visible or Not");
        return checkState(caseManagement);
    }

    public boolean isUserManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is User Management Option Visible or Not");
        hoverAndClick(adminSettings);
        return checkState(userManagement);
    }

    public boolean isProfileManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is Profile Management Option Visible or Not");
        hoverAndClick(adminSettings);
        return checkState(profileManagement);
    }

    public boolean isCustomerInteractionVisible() {
        UtilsMethods.printInfoLog("Checking that is Customer Interaction Option Visible or Not");
        hoverAndClick(customerServices);
        return checkState(customerInteraction);
    }

    public boolean isSupervisorDashboardVisible() {
        UtilsMethods.printInfoLog("Checking that is Supervisor DashBoard Option Visible or Not");
        hoverAndClick(customerServices);
        return checkState(supervisorDashboard);
    }

    public boolean isTicketBulkUpdateVisible() {
        UtilsMethods.printInfoLog("Checking that is Supervisor Ticket Bulk Update Option Visible or Not");
        hoverAndClick(customerServices);
        return checkState(ticketBulkUpdate);
    }

    public boolean isTemplateManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is Admin Template Management Option Visible or Not");
        hoverAndClick(adminSettings);
        return checkState(templateManagement);
    }

    public customerInteractionsSearchPOM openCustomerInteractionPage() {
        UtilsMethods.printInfoLog("Opening Customer Interaction Page");
        hoverAndClick(customerServices);
        click(customerInteraction);
        return new customerInteractionsSearchPOM(driver);
    }

    public TemplateManagementPOM openTemplateManagementPage() {
        UtilsMethods.printInfoLog("Opening Template Management Page");
        hoverAndClick(adminSettings);
        click(templateManagement);
        return new TemplateManagementPOM(driver);
    }

    public userManagementPOM openUserManagementPage() {
        UtilsMethods.printInfoLog("Opening User Management Page");
        hoverAndClick(adminSettings);
        click(userManagement);
        return new userManagementPOM(driver);
    }

    public profileManagementPOM openProfileManagementPage() {
        UtilsMethods.printInfoLog("Opening Profile Management Page");
        hoverAndClick(adminSettings);
        click(profileManagement);
        return new profileManagementPOM(driver);
    }

    public agentLoginPagePOM openSupervisorDashboard() {
        UtilsMethods.printInfoLog("Opening Supervisor Dashboard Page");
        hoverAndClick(customerServices);
        click(supervisorDashboard);
        return new agentLoginPagePOM(driver);
    }

    public agentLoginPagePOM openBackendAgentDashboard() {
        UtilsMethods.printInfoLog("Opening Backend Agent Dashboard Page");
        hoverAndClick(customerServices);
        click(agentDashboard);
        return new agentLoginPagePOM(driver);
    }

    public Boolean isAgentDashboard() {
        UtilsMethods.printInfoLog("Checking Agent Dashboard Visible or not");
        hoverAndClick(customerServices);
        return checkState(agentDashboard);
    }

    public TicketBulkUpdatePOM openTicketBulkUpdateDashboard() {
        UtilsMethods.printInfoLog("Opening Ticket Bulk Update Dashboard Page");
        hoverAndClick(customerServices);
        click(ticketBulkUpdate);
        return new TicketBulkUpdatePOM(driver);
    }

    public loginPagePOM logout() {
        UtilsMethods.printInfoLog("Logging Out");
        click(logout);
        waitTillLoaderGetsRemoved();
        return new loginPagePOM(driver);
    }
}
