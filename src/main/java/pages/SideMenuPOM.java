package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
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
    By templateManagement=By.xpath("//a[contains(text(),'Template Management')]");
    By ticketBulkUpdate=By.xpath("//a[contains(text(),'Bulk')]");
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
        log.info("Clicking on Side Menu");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Side Menu");
        click(sideMenuOption);
    }

    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        log.info("Wait for Side menu and Home Page to be loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Wait for Side menu and Home Page to be loaded");

    }

    public boolean isSideMenuVisible() {
        waitTillLoaderGetsRemoved();
        log.info("Checking that  Side Menu options are Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that  Side Menu options are Visible or Not");

        return isElementVisible(sideMenuOption);
    }


    public void clickOnName() {
        log.info("Clicking on User Name");
        click(userName);
    }

    public boolean isAdminSettingVisible() {
        log.info("Checking is Admin Setting Visible or not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Admin Setting  Visible or Not");
        return checkState(adminSettings);
    }

    public boolean isCustomerServicesVisible() {
        log.info("Checking that is Customer Service Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Customer Service Visible or Not");

        return checkState(customerServices);
    }

    public boolean isCaseManagementVisible() {
        log.info("Checking that is Customer Service Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Customer Service Visible or Not");
        return checkState(caseManagement);
    }

    public boolean isUserManagementVisible() {
        log.info("Checking that is User Management Option Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is User Management Option Visible or Not");
        hoverAndClick(adminSettings);
        return checkState(userManagement);
    }

    public boolean isProfileManagementVisible() {
        log.info("Checking that is Profile Management Option Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Profile Management Option Visible or Not");
        hoverAndClick(adminSettings);
        return checkState(profileManagement);
    }

    public boolean isCustomerInteractionVisible() {
        log.info("Checking that is Customer Interaction Option Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Customer Interaction Option Visible or Not");
        hoverAndClick(customerServices);
        return checkState(customerInteraction);
    }

    public boolean isSupervisorDashboardVisible() {
        log.info("Checking that is Supervisor DashBoard Option Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Supervisor DashBoard Option Visible or Not");
        hoverAndClick(customerServices);
        return checkState(supervisorDashboard);
    }

    public boolean isTicketBulkUpdateVisible() {
        log.info("Checking that is Supervisor Ticket Bulk Update Option Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Supervisor Ticket Bulk Update Option Visible or Not");
        hoverAndClick(customerServices);
        return checkState(ticketBulkUpdate);
    }

    public boolean isTemplateManagementVisible() {
        log.info("Checking that is Admin Template Management Option Visible or Not");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Admin Template Management Option Visible or Not");
        hoverAndClick(adminSettings);
        return checkState(templateManagement);
    }

    public customerInteractionsSearchPOM openCustomerInteractionPage() {
        log.info("Opening Customer Interaction Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Customer Interaction Page");
        hoverAndClick(customerServices);
        click(customerInteraction);
        return new customerInteractionsSearchPOM(driver);
    }

    public TemplateManagementPOM openTemplateManagementPage() {
        log.info("Opening Template Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Template Management Page");
        hoverAndClick(adminSettings);
        click(templateManagement);
        return new TemplateManagementPOM(driver);
    }

    public userManagementPOM openUserManagementPage() {
        log.info("Opening User Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening User Management Page");
        hoverAndClick(adminSettings);
        click(userManagement);
        return new userManagementPOM(driver);
    }

    public profileManagementPOM openProfileManagementPage() {
        log.info("Opening Profile Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Profile Management Page");
        hoverAndClick(adminSettings);
        click(profileManagement);
        return new profileManagementPOM(driver);
    }

    public agentLoginPagePOM openSupervisorDashboard() {
        log.info("Opening Supervisor Dashboard Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Supervisor Dashboard Page");
        hoverAndClick(customerServices);
        click(supervisorDashboard);
        return new agentLoginPagePOM(driver);
    }

    public agentLoginPagePOM openBackendAgentDashboard() {
        log.info("Opening Backend Agent Dashboard Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Backend Agent Dashboard Page");
        hoverAndClick(customerServices);
        click(agentDashboard);
        return new agentLoginPagePOM(driver);
    }

    public loginPagePOM logout() {
        log.info("Logging Out");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Logging Out");
        click(logout);
        waitTillLoaderGetsRemoved();
        return new loginPagePOM(driver);
    }
}
