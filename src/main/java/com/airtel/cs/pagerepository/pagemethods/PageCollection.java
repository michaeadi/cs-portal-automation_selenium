package com.airtel.cs.pagerepository.pagemethods;

import org.openqa.selenium.WebDriver;

public class PageCollection {


    /**
     * The driver.
     */
    private final WebDriver driver;
    private SideMenuPOM sideMenuPOM;
    private LoginPage loginPage;
    private CustomerProfilePage customerProfilePage;
    private TariffPlanPage tariffPlanPage;
    private CustomerInteractionsSearchPOM customerInteractionsSearchPOM;
    private ViewHistory viewHistory;
    private UserManagementPage userManagementPage;

    /**
     * Instantiates a new page collection.
     *
     * @param driver the driver
     */
    public PageCollection(WebDriver driver) {
        this.driver = driver;

    }

    public SideMenuPOM getSideMenuPOM() {
        return (sideMenuPOM == null) ? sideMenuPOM = new SideMenuPOM(driver) : sideMenuPOM;
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }

    public CustomerProfilePage getCustomerProfilePage() {
        return (customerProfilePage == null) ? customerProfilePage = new CustomerProfilePage(driver) : customerProfilePage;
    }

    public TariffPlanPage getTariffPlanPage() {
        return (tariffPlanPage == null) ? tariffPlanPage = new TariffPlanPage(driver) : tariffPlanPage;
    }

    public CustomerInteractionsSearchPOM getCustomerInteractionsSearchPOM() {
        return (customerInteractionsSearchPOM == null) ? customerInteractionsSearchPOM = new CustomerInteractionsSearchPOM(driver) : customerInteractionsSearchPOM;
    }

    public ViewHistory getViewHistoryPOM() {
        return (viewHistory == null) ? viewHistory = new ViewHistory(driver) : viewHistory;
    }

    public UserManagementPage getUserManagementPage() {
        return (userManagementPage == null) ? userManagementPage = new UserManagementPage(driver) : userManagementPage;
    }
}