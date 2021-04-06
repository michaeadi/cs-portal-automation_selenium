package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.SideMenuPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SideMenuPage extends BasePage {

    SideMenuPageElements pageElements;

    public SideMenuPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SideMenuPageElements.class);
    }

    public String getUserName() {
        return readText(pageElements.userName);
    }

    public void clickOnSideMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.sideMenuOption));
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Clicking on Side Menu");
        click(pageElements.sideMenuOption);
    }

    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.loader));
        UtilsMethods.printInfoLog("Wait for Side menu and Home Page to be loaded");

    }

    public boolean isSideMenuVisible() {
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Checking that  Side Menu options are Visible or Not");

        return isElementVisible(pageElements.sideMenuOption);
    }


    public void clickOnName() {
        log.info("Clicking on User Name");
        click(pageElements.userName);
    }

    public boolean isAdminSettingVisible() {
        UtilsMethods.printInfoLog("Checking that is Admin Setting  Visible or Not");
        return checkState(pageElements.adminSettings);
    }

    public boolean isCustomerServicesVisible() {
        UtilsMethods.printInfoLog("Checking that is Customer Service Visible or Not");

        return checkState(pageElements.customerServices);
    }

    public boolean isCaseManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is Customer Service Visible or Not");
        return checkState(pageElements.caseManagement);
    }

    public boolean isUserManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is User Management Option Visible or Not");
        hoverAndClick(pageElements.adminSettings);
        return checkState(pageElements.userManagement);
    }

    public boolean isProfileManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is Profile Management Option Visible or Not");
        hoverAndClick(pageElements.adminSettings);
        return checkState(pageElements.profileManagement);
    }

    public boolean isCustomerInteractionVisible() {
        UtilsMethods.printInfoLog("Checking that is Customer Interaction Option Visible or Not");
        hoverAndClick(pageElements.customerServices);
        return checkState(pageElements.customerInteraction);
    }

    public boolean isSupervisorDashboardVisible() {
        UtilsMethods.printInfoLog("Checking that is Supervisor DashBoard Option Visible or Not");
        hoverAndClick(pageElements.customerServices);
        return checkState(pageElements.supervisorDashboard);
    }

    public boolean isTicketBulkUpdateVisible() {
        UtilsMethods.printInfoLog("Checking that is Supervisor Ticket Bulk Update Option Visible or Not");
        hoverAndClick(pageElements.customerServices);
        return checkState(pageElements.ticketBulkUpdate);
    }

    public boolean isTemplateManagementVisible() {
        UtilsMethods.printInfoLog("Checking that is Admin Template Management Option Visible or Not");
        hoverAndClick(pageElements.adminSettings);
        return checkState(pageElements.templateManagement);
    }

    public void openCustomerInteractionPage() {
        UtilsMethods.printInfoLog("Opening Customer Interaction Page");
        hoverAndClick(pageElements.customerServices);
        click(pageElements.customerInteraction);
    }

    public void openTemplateManagementPage() {
        UtilsMethods.printInfoLog("Opening Template Management Page");
        hoverAndClick(pageElements.adminSettings);
        click(pageElements.templateManagement);
    }

    public void openUserManagementPage() {
        UtilsMethods.printInfoLog("Opening User Management Page");
        hoverAndClick(pageElements.adminSettings);
        click(pageElements.userManagement);
    }

    public void openProfileManagementPage() {
        UtilsMethods.printInfoLog("Opening Profile Management Page");
        hoverAndClick(pageElements.adminSettings);
        click(pageElements.profileManagement);
    }

    public void openSupervisorDashboard() {
        UtilsMethods.printInfoLog("Opening Supervisor Dashboard Page");
        hoverAndClick(pageElements.customerServices);
        click(pageElements.supervisorDashboard);
    }

    public void openBackendAgentDashboard() {
        UtilsMethods.printInfoLog("Opening Backend Agent Dashboard Page");
        hoverAndClick(pageElements.customerServices);
        click(pageElements.agentDashboard);
    }

    public Boolean isAgentDashboard() {
        UtilsMethods.printInfoLog("Checking Agent Dashboard Visible or not");
        hoverAndClick(pageElements.customerServices);
        return checkState(pageElements.agentDashboard);
    }

    public TicketBulkUpdatePage openTicketBulkUpdateDashboard() {
        UtilsMethods.printInfoLog("Opening Ticket Bulk Update Dashboard Page");
        hoverAndClick(pageElements.customerServices);
        click(pageElements.ticketBulkUpdate);
        return new TicketBulkUpdatePage(driver);
    }

    public void logout() {
        UtilsMethods.printInfoLog("Logging Out");
        click(pageElements.logout);
        waitTillLoaderGetsRemoved();
    }
}
