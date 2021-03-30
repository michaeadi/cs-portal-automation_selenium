package com.airtel.cs.pagerepository.pagemethods;

import org.openqa.selenium.WebDriver;

public class PageCollection {



    /**
     * The driver.
     */
    private WebDriver driver;
    private SideMenuPOM sideMenuPOM;

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
}