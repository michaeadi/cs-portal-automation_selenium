package com.airtel.cs.pagerepository.pagemethods;

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
public class UserManagement extends BasePage {

    public com.airtel.cs.pagerepository.pageelements.UserManagement pageElements;
    private static final String FINDING="finding ";

    /*
    This Method will initialize all the elements of UserManagementElements class
     */
    public UserManagement(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, com.airtel.cs.pagerepository.pageelements.UserManagement.class);
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
        } else {
            commonLib.error("Automation Beta Role is NOT Created, Kindly Create Automation Beta User Role");
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
        commonLib.info("Logging Out");
        click(pageElements.logoutUMBtn);
        waitTillLoaderGetsRemoved();
        defaultDriver.close();
    }

    public void clickUpdateButton() {
        commonLib.info("Clicking on Update Button");
        click(pageElements.updateButton);
    }

    public void setTicketBucketSize(int size) {
        commonLib.info("Setting Current Ticket size : " + size);
        clearInputTag(pageElements.bucketSize);
        writeText(pageElements.bucketSize, String.valueOf(size));
    }

    public String getCurrentTicketBucketSize() {
        commonLib.info("Getting Current Ticket Size : " + getText(pageElements.ticketBucketSize));
        return getText(pageElements.ticketBucketSize);
    }

    public ArrayList<String> getWorkflows() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.workflowsOptions);
        log.info("total elements " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (WebElement listOfElement : listOfElements) {
            try {
                commonLib.info("Reading Work Group: " + listOfElement.getText().toLowerCase().trim());
                strings.add(listOfElement.getText().toLowerCase().trim());

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
        for (WebElement listOfElement : listOfElements) {
            try {
                commonLib.info("Reading Login Queue: " + listOfElement.getText().toLowerCase().trim());
                strings.add(listOfElement.getText().toLowerCase().trim());
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
        for (WebElement listOfElement : listOfElements) {
            try {
                commonLib.info("Reading Login Channel: " + listOfElement.getText().toLowerCase().trim());
                strings.add(listOfElement.getText().toLowerCase().trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
        }
        return strings;
    }

    public boolean isWorkFlowPresent(String[] strings, String workflow) {
        boolean isThere = false;
        log.info(FINDING + workflow + " in workflow List");
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
        commonLib.info("Opening Work Group Flow List");
        Thread.sleep(1000);

    }

    public void openLoginQueueList() throws InterruptedException {
        List<WebElement> webElements = returnListOfElement(By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"true\"]"));
        webElements.get(1).click();
        commonLib.info("Opening Login Queue Flow List");
        Thread.sleep(1000);
    }


    public boolean isLoginQueuePresent(String[] strings, String workflow) {
        boolean isThere = false;
        commonLib.info(FINDING + workflow + " in Login Queue List");
        for (String a : strings) {
            if (a.equals(workflow)) {
                isThere = true;
                log.info(workflow + " is present in Login Queue List");
                commonLib.info(workflow + " is present in Login Queue List");

            }
        }
        return isThere;
    }

    public void clickViewEditButton() {
        commonLib.info("Clicking View/Edit button");
        click(pageElements.viewEditButton);
    }

    public void waitUntilEditPageIsOpen() {
        commonLib.info("Waiting Until Edit Profile Page is Loaded");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.cancelButton));
    }

    public void waitTillUMPageLoaded() {
        commonLib.info("Waiting Until Profile Management Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.totalUsersHeading));
    }

    public boolean isSearchVisible() {
        final boolean state = checkState(pageElements.searchAuuid);
        commonLib.info("Checking is Search Auuid Text box is Visible : " + state);
        return state;
    }

    public void searchAuuid(String auuid) {
        commonLib.info("Writing AUUID to Search auuid Text box : " + auuid);
        writeText(pageElements.searchAuuid, auuid);
    }

    public void clickSearchButton() {
        commonLib.info("Clicking on Search Button");
        click(pageElements.searchButton);
    }

    public void waitUntilResultPageIsVisible() {
        waitTillLoaderGetsRemoved();
        commonLib.info("Waiting Untill Result Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.searchButton));
    }

    public String resultIsVisible(String auuid) {

        By title = By.xpath("//div[@title='" + auuid + "']");
        final boolean visible = isElementVisible(title);
        commonLib.info("Checking is Search Auuid Text box is Visible : " + visible);
        final String text = getText(title);
        commonLib.info("Getting auuid from result : " + text);
        return text;
    }

    public void openListInteractionChannels() throws InterruptedException {
        commonLib.info("Opening Interaction Channel List");
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
        commonLib.info(FINDING + channel + " in Interaction List");
        for (String a : strings) {
            if (a.equals(channel)) {
                isThere = true;
                log.info(channel + " is present in interaction Channel List");
                commonLib.info(channel + " is present in interaction Channel List");
            }
        }
        return isThere;
    }

    public void clickAddUserBtn() {
        commonLib.info("Clicking on Add to User Button");
        click(pageElements.addUser);
    }

    public void switchFrameToAddUser() {
        commonLib.info("Switching Frame");

    }

    public boolean checkingAddUser() {
        commonLib.info("Checking Add to User Open");
        return checkState(pageElements.addUserPageTitle);
    }
}
