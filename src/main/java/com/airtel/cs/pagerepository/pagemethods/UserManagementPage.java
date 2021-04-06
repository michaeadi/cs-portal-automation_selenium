package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.UserManagementElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UserManagementPage extends BasePage {

    public UserManagementElements pageElements;

    /*
    This Method will initialize all the elements of UserManagementElements class
     */
    public UserManagementPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, UserManagementElements.class);
    }

    /*
    This Method will open the role list from Role Management Page
     */
    public void openRoleList() {
        if (isVisible(pageElements.roles)) {
            click(pageElements.roles);
        }
    }

    /*
    This Method will click on edit for a particular user role. @Note: For now this is implemented for one user role
     */
    public void editRole() {
        if (isVisible(pageElements.editCSBetaUserRole)) {
            click(pageElements.editCSBetaUserRole);
            waitTillLoaderGetsRemoved();
        }
    }

    /**
     * This Method will check, uncheck the checkbox for view permission under permissions over UM
     *
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
        if (isVisible(pageElements.updateRoleBtn)) {
            click(pageElements.updateRoleBtn);
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
        click(pageElements.logoutUMBtn);
        waitTillLoaderGetsRemoved();
        default_Driver.close();
    }

    public void clickUpdateButton() {
        UtilsMethods.printInfoLog("Clicking on Update Button");
        click(pageElements.updateButton);
    }

    public void setTicketBucketSize(int size) {
        UtilsMethods.printInfoLog("Setting Current Ticket size : " + size);
        clearInputTag(pageElements.bucketSize);
        writeText(pageElements.bucketSize, String.valueOf(size));
    }

    public String getCurrentTicketBucketSize() {
        UtilsMethods.printInfoLog("Getting Current Ticket Size : " + readText(pageElements.ticketBucketSize));
        return readText(pageElements.ticketBucketSize);
    }

    public ArrayList<String> getWorkflows() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.workflowsOptions);
        log.info("total elements " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                UtilsMethods.printInfoLog("Reading Work Group: " + listOfElements.get(i).getText().toLowerCase().trim());
                strings.add(listOfElements.get(i).getText().toLowerCase().trim());

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
        }
        return strings;
    }

    public ArrayList<String> getLoginQueues() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.workflowsOptions);
        log.info("total elements " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                UtilsMethods.printInfoLog("Reading Login Queue: " + listOfElements.get(i).getText().toLowerCase().trim());
                strings.add(listOfElements.get(i).getText().toLowerCase().trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
        }
        return strings;
    }

    public ArrayList<String> getInteractionChannels() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.channelsOptions);
        log.info("List Size: " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                UtilsMethods.printInfoLog("Reading Login Channel: " + listOfElements.get(i).getText().toLowerCase().trim());
                strings.add(listOfElements.get(i).getText().toLowerCase().trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
        }
        return strings;
    }

    public boolean isWorkFlowPresent(String[] strings, String workflow) {
        boolean isThere = false;
        log.info("finding " + workflow + " in workflow List");
        for (String a : strings) {
            if (a.equals(workflow)) {
                isThere = true;
                log.info(workflow + " is present in work flow List");
            }
        }
        return isThere;
    }


    public void openWorkgroupList() throws InterruptedException {
        List<WebElement> webElements = returnListOfElement(By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"true\"]"));
        webElements.get(0).click();
        UtilsMethods.printInfoLog("Opening Work Group Flow List");
        Thread.sleep(1000);

    }

    public void openLoginQueueList() throws InterruptedException {
        List<WebElement> webElements = returnListOfElement(By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"true\"]"));
        webElements.get(1).click();
        UtilsMethods.printInfoLog("Opening Login Queue Flow List");
        Thread.sleep(1000);
    }


    public boolean isLoginQueuePresent(String[] strings, String workflow) {
        boolean isThere = false;
        UtilsMethods.printInfoLog("finding " + workflow + " in Login Queue List");
        for (String a : strings) {
            if (a.equals(workflow)) {
                isThere = true;
                log.info(workflow + " is present in Login Queue List");
                UtilsMethods.printInfoLog(workflow + " is present in Login Queue List");

            }
        }
        return isThere;
    }

    public void clickViewEditButton() {
        UtilsMethods.printInfoLog("Clicking View/Edit button");
        click(pageElements.viewEditButton);
    }

    public void waitUntilEditPageIsOpen() {
        UtilsMethods.printInfoLog("Waiting Until Edit Profile Page is Loaded");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.cancelButton));
    }

    public void waitTillUMPageLoaded() {
        UtilsMethods.printInfoLog("Waiting Until Profile Management Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.totalUsersHeading));
    }

    public boolean isSearchVisible() {
        UtilsMethods.printInfoLog("Checking is Search Auuid Text box is Visible : " + checkState(pageElements.searchAuuid));
        return checkState(pageElements.searchAuuid);
    }

    public void searchAuuid(String auuid) {
        UtilsMethods.printInfoLog("Writing AUUID to Search auuid Text box : " + auuid);
        writeText(pageElements.searchAuuid, auuid);
    }

    public void clickSearchButton() {
        UtilsMethods.printInfoLog("Clicking on Search Button");
        click(pageElements.searchButton);
    }

    public void waitUntilResultPageIsVisible() {
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Waiting Untill Result Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.searchButton));
    }

    public String resultIsVisible(String auuid) {

        By title = By.xpath("//div[@title='" + auuid + "']");
        UtilsMethods.printInfoLog("Checking is Search Auuid Text box is Visible : " + isElementVisible(title));
        UtilsMethods.printInfoLog("Getting auuid from result : " + readText(title));
        isElementVisible(title);
        return readText(title);
    }

    public void openListInteractionChannels() throws InterruptedException {
        UtilsMethods.printInfoLog("Opening Interaction Channel List");
        click(pageElements.interactionChannel);
        Thread.sleep(1000);
    }

    public void pressESCWorkflow() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"true\"]"));
        webElements.get(0).sendKeys(Keys.ESCAPE);
    }

    public void pressESC() throws InterruptedException {
        driver.findElement(By.xpath("//div[@class=\"cdk-overlay-backdrop cdk-overlay-transparent-backdrop cdk-overlay-backdrop-showing\"]")).click();
        Thread.sleep(2000);
    }


    public boolean isInteractionChannelPresent(String[] strings, String channel) {
        boolean isThere = false;
        UtilsMethods.printInfoLog("finding " + channel + " in Interaction List");
        for (String a : strings) {
            if (a.equals(channel)) {
                isThere = true;
                log.info(channel + " is present in interaction Channel List");
                UtilsMethods.printInfoLog(channel + " is present in interaction Channel List");
            }
        }
        return isThere;
    }

    public void clickAddUserBtn() {
        UtilsMethods.printInfoLog("Clicking on Add to User Button");
        click(pageElements.addUser);
    }

    public void switchFrameToAddUser() {
        UtilsMethods.printInfoLog("Switching Frame");

    }

    public boolean checkingAddUser() {
        UtilsMethods.printInfoLog("Checking Add to User Open");
        return checkState(pageElements.addUserPageTitle);
    }
}
