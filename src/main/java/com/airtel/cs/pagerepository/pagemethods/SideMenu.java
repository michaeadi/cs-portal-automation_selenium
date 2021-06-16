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

    /**
     * This method is use to click on side menu icon
     */
    public void clickOnSideMenu() {
        if (isVisible(pageElements.sideMenuOption) && isClickable(pageElements.sideMenuOption)) {
            commonLib.info("Clicking on Side Menu");
            clickWithoutLoader(pageElements.sideMenuOption);
        } else {
            commonLib.error("Side Menu Option is NOT Visible", true);
        }
    }

    /**
     * This method is use to wait till home page load
     */
    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.sideMenuOption));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.loader));
        commonLib.info("Wait for Side menu and Home Page to be loaded");

    }

    /**
     * This method is use to check side menu visible or not
     * @return true/false
     */
    public boolean isSideMenuVisible() {
        commonLib.info("Checking that Side Menu options are Visible or Not");
        return isElementVisible(pageElements.sideMenuOption);
    }

    /**
     * This method is use to click on user name
     */
    public void clickOnUserName() {
        commonLib.info("Clicking on User Name");
        clickWithoutLoader(pageElements.userName);
    }

    /**
     * This method use to check admin module display or not
     * @return true/false
     */
    public boolean isAdminSettingVisible() {
        commonLib.info("Checking that is Admin Setting  Visible or Not");
        return isEnabled(pageElements.adminSettings);
    }

    /**
     * This method use to check customer service module display or not
     * @return true/false
     */
    public boolean isCustomerServicesVisible() {
        commonLib.info("Checking that is Customer Service Visible or Not");
        return isEnabled(pageElements.customerServices);
    }

    /**
     * This method use to check case management module display or not
     * @return true/false
     */
    public boolean isCaseManagementVisible() {
        commonLib.info("Checking that is Customer Service Visible or Not");
        return isEnabled(pageElements.caseManagement);
    }

    /**
     * This method use to check user management module display or not
     * @return true/false
     */
    public boolean isUserManagementVisible() {
        commonLib.info("Checking that is User Management Option Visible or Not");
        hoverOverElement(pageElements.adminSettings);
        return isEnabled(pageElements.userManagement);
    }

    /**
     * This method use to check profile management module display or not
     * @return true/false
     */
    public boolean isProfileManagementVisible() {
        commonLib.info("Checking that is Profile Management Option Visible or Not");
        hoverOverElement(pageElements.adminSettings);
        return isEnabled(pageElements.profileManagement);
    }

    /**
     * This method use to hover on custome service module
     */
    public void hoverOverCustomerService() {
        if (isVisible(pageElements.customerServices))
            hoverOverElement(pageElements.customerServices);
        else
            commonLib.fail("Exception in method - hoverOverCustomerService", true);
    }

    /**
     * This method use to check customer interaction option display or not
     * @return true/false
     */
    public boolean isCustomerInteractionVisible() {
        commonLib.info("Checking that is Customer Interaction Option Visible or Not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.customerInteraction);
    }

    /**
     * This method use to check Supervisor dashboard option display or not
     * @return true/false
     */
    public boolean isSupervisorDashboardVisible() {
        commonLib.info("Checking that is Supervisor DashBoard Option Visible or Not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.supervisorDashboard);
    }

    /**
     * This method use to check ticket bulk update option display or not
     * @return true/false
     */
    public boolean isTicketBulkUpdateVisible() {
        commonLib.info("Checking that is Supervisor Ticket Bulk Update Option Visible or Not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.ticketBulkUpdate);
    }

    /**
     * This method use to check Template Management option display or not
     * @return true/false
     */
    public boolean isTemplateManagementVisible() {
        commonLib.info("Checking that is Admin Template Management Option Visible or Not");
        hoverOverElement(pageElements.adminSettings);
        return isEnabled(pageElements.templateManagement);
    }

    /**
     * This method is use to open customer interaction page
     */
    public void openCustomerInteractionPage() {
        commonLib.info("Opening Customer Interaction Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.customerInteraction);
    }

    /**
     * This method is use to open template management page
     */
    public void openTemplateManagementPage() {
        commonLib.info("Opening Template Management Page");
        hoverOverElement(pageElements.adminSettings);
        clickAndWaitForLoaderToBeRemoved(pageElements.templateManagement);
    }

    /**
     * This method is use to open user management page
     */
    public void openUserManagementPage() {
        commonLib.info("Opening User Management Page");
        hoverOverElement(pageElements.adminSettings);
        clickAndWaitForLoaderToBeRemoved(pageElements.userManagement);
    }

    /**
     * This method is use to open profile management page
     */
    public void openProfileManagementPage() {
        commonLib.info("Opening Profile Management Page");
        hoverOverElement(pageElements.adminSettings);
        clickAndWaitForLoaderToBeRemoved(pageElements.profileManagement);
    }

    /**
     * This method is use to open supervisor dashboard page
     */
    public void openSupervisorDashboard() {
        commonLib.info("Opening Supervisor Dashboard Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.supervisorDashboard);
    }

    /**
     * This method is use to open backend agent dashboard page
     */
    public void openBackendAgentDashboard() {
        commonLib.info("Opening Backend Agent Dashboard Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.agentDashboard);
    }

    /**
     * This method use to check is backend agent dahsboard option display or not
     * @return true/false
     */
    public Boolean isAgentDashboard() {
        commonLib.info("Checking Agent Dashboard Visible or not");
        hoverOverElement(pageElements.customerServices);
        return isEnabled(pageElements.agentDashboard);
    }

    /**
     * This method is use to open ticket bulk update page
     */
    public void openTicketBulkUpdateDashboard() {
        commonLib.info("Opening Ticket Bulk Update Dashboard Page");
        hoverOverElement(pageElements.customerServices);
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketBulkUpdate);
    }

    /**
     * This method is use to click logout button
     */
    public void logout() {
        commonLib.info("Logging Out");
        clickAndWaitForLoaderToBeRemoved(pageElements.logout);
    }
}
