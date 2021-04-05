package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.UserManagementElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class UserManagementPage extends BasePage {
    public UserManagementElements userManagementElements = null;

    /*
    This Method will initialize all the elements of UserManagementElements class
     */
    public UserManagementPage(WebDriver driver) {
        super(driver);
        userManagementElements = PageFactory.initElements(driver, UserManagementElements.class);
    }

    /*
    This Method will open the role list from Role Management Page
     */
    public void openRoleList() {
        if (isVisible(userManagementElements.roles)) {
            click(userManagementElements.roles);
        }
    }

    /*
    This Method will click on edit for a particular user role. @Note: For now this is implemented for one user role
     */
    public void editRole() {
        if (isVisible(userManagementElements.editCSBetaUserRole)) {
            click(userManagementElements.editCSBetaUserRole);
            waitTillLoaderGetsRemoved();
        }
    }

    /**
     * This Method will check, uncheck the checkbox for view permission under permissions over UM
     * @param permissionName permissionName
     */
    public void addRemoveViewTariffPlanPermission(String permissionName) {
        By modifiedXpath = By.xpath("//*[text()='" + permissionName + "']/../input");
        if (isVisible(modifiedXpath)) {
            clickByJavascriptExecutor(modifiedXpath);
        } else {
            commonLib.error("Element is NOT Visible");
        }
    }

    /*
    This Method will click on Submit(Update Role Btn)
     */
    public void updateRolePermission() {
        if (isVisible(userManagementElements.updateRoleBtn)) {
            click(userManagementElements.updateRoleBtn);
            waitTillLoaderGetsRemoved();
        }
    }

    /*
    This Method will remove the view permission of tariff plan feature from UM
     */
    public void removeOrAddPermission(String permissionName) {
        openRoleList();
        editRole();
        addRemoveViewTariffPlanPermission(permissionName);
        updateRolePermission();
        waitTillLoaderGetsRemoved();
    }

    /*
    This Method will logout and close the temp browser
     */
    public void destroyTempBrowser() {
        UtilsMethods.printInfoLog("Logging Out");
        click(userManagementElements.logoutUMBtn);
        waitTillLoaderGetsRemoved();
        default_Driver.close();
    }
}
