package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.SideMenuPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SideMenu extends BasePage {

    SideMenuPage pageElements;

    public SideMenu(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SideMenuPage.class);
    }

    public String getUserName() {
        return getText(pageElements.userName);
    }

    public void clickOnSideMenu() {
        if (isVisible(pageElements.sideMenuOption) && isClickable(pageElements.sideMenuOption)) {
            commonLib.info("Clicking on Side Menu");
            clickWithoutLoader(pageElements.sideMenuOption);
        } else {
            commonLib.error("Side Menu Option is NOT Visible", true);
        }
    }

    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.loader));
        commonLib.info("Wait for Side menu and Home Page to be loaded");

    }

    public boolean isSideMenuVisible() {
        commonLib.info("Checking that Side Menu options are Visible or Not");
        return isElementVisible(pageElements.sideMenuOption);
    }

    public void clickOnUserName() {
        commonLib.info("Clicking on User Name");
        clickWithoutLoader(pageElements.userName);
    }

    public boolean isAdminSettingVisible() {
        commonLib.info("Checking that is Admin Setting  Visible or Not");
        return isEnabled(pageElements.adminSettings);
    }

    public boolean isCustomerServicesVisible() {
        commonLib.info("Checking that is Customer Service Visible or Not");
        return isEnabled(pageElements.customerServices);
    }

    public boolean isCaseManagementVisible() {
        commonLib.info("Checking that is Customer Service Visible or Not");
        return isEnabled(pageElements.caseManagement);
    }

    public boolean isUserManagementVisible() {
        commonLib.info("Checking that is User Management Option Visible or Not");
        hoverOverElement(pageElements.adminSettings);
        return isEnabled(pageElements.userManagement);
    }

    public boolean isProfileManagementVisible() {
        commonLib.info("Checking that is Profile Management Option Visible or Not");
        hoverOverElement(pageElements.adminSettings);
        return isEnabled(pageElements.profileManagement);
    }

    public void hoverOverCustomerService() {
        if (isVisible(pageElements.customerServices))
            hoverOverElement(pageElements.customerServices);
        else
            commonLib.fail("Exception in method - hoverOverCustomerService", true);
    }

    public boolean isCustomerInteractionVisible() {
        commonLib.info("Checking that is Customer Interaction Option Visible or Not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.customerInteraction);
    }

    public boolean isSupervisorDashboardVisible() {
        commonLib.info("Checking that is Supervisor DashBoard Option Visible or Not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.supervisorDashboard);
    }

    public boolean isTicketBulkUpdateVisible() {
        commonLib.info("Checking that is Supervisor Ticket Bulk Update Option Visible or Not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.ticketBulkUpdate);
    }

    public boolean isTemplateManagementVisible() {
        commonLib.info("Checking that is Admin Template Management Option Visible or Not");
        hoverOverElement(pageElements.adminSettings);
        return isEnabled(pageElements.templateManagement);
    }

    public void openCustomerInteractionPage() {
        commonLib.info("Opening Customer Interaction Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.customerInteraction);
    }

    public void openTemplateManagementPage() {
        commonLib.info("Opening Template Management Page");
        hoverOverElement(pageElements.adminSettings);
        clickAndWaitForLoaderToBeRemoved(pageElements.templateManagement);
    }

    public void openUserManagementPage() {
        commonLib.info("Opening User Management Page");
        hoverOverElement(pageElements.adminSettings);
        clickAndWaitForLoaderToBeRemoved(pageElements.userManagement);
    }

    public void openProfileManagementPage() {
        commonLib.info("Opening Profile Management Page");
        hoverOverElement(pageElements.adminSettings);
        clickAndWaitForLoaderToBeRemoved(pageElements.profileManagement);
    }

    public void openSupervisorDashboard() {
        commonLib.info("Opening Supervisor Dashboard Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.supervisorDashboard);
    }

    public void openBackendAgentDashboard() {
        commonLib.info("Opening Backend Agent Dashboard Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.agentDashboard);
    }

    public Boolean isAgentDashboard() {
        commonLib.info("Checking Agent Dashboard Visible or not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.agentDashboard);
    }

    public TicketBulkUpdate openTicketBulkUpdateDashboard() {
        commonLib.info("Opening Ticket Bulk Update Dashboard Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketBulkUpdate);
        return new TicketBulkUpdate(driver);
    }

    public void logout() {
        commonLib.info("Logging Out");
        clickAndWaitForLoaderToBeRemoved(pageElements.logout);
    }
}
