package com.airtel.cs.pagerepository.pagemethods;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UserManagement extends BasePage {

    public com.airtel.cs.pagerepository.pageelements.UserManagement pageElements;
    private static final String FINDING = "finding ";

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
            clickAndWaitForLoaderToBeRemoved(pageElements.roles);
        }
    }

    /*
    This Method will click on edit for a particular user role. @Note: For now this is implemented for one user role
     */
    public void editRole() {
        if (isVisible(pageElements.editCSBetaUserRole)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.editCSBetaUserRole);
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
            clickAndWaitForLoaderToBeRemoved(pageElements.updateRoleBtn);
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
        clickAndWaitForLoaderToBeRemoved(pageElements.logoutUMBtn);
        waitTillLoaderGetsRemoved();
        defaultDriver.close();
    }

    /**
     * This method use to click update button
     */
    public void clickUpdateButton() {
        commonLib.info("Clicking on Update Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.updateButton);
    }

    /**
     * This method use to write bucket size in bucket size field
     * @param size The bucket size
     */
    public void setTicketBucketSize(int size) {
        commonLib.info("Setting Current Ticket size : " + size);
        clearInputTag(pageElements.bucketSize);
        enterText(pageElements.bucketSize, String.valueOf(size));
    }

    /**
     * This method use to get current bucket size of an agent
     * @return String The Value
     */
    public String getCurrentTicketBucketSize() {
        commonLib.info("Getting Current Ticket Size : " + getText(pageElements.ticketBucketSize));
        return getText(pageElements.ticketBucketSize);
    }

    /**
     * This method is use to get all workgroup which is present on UI
     * @return List The list of workgroup name
     */
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

    /**
     * This method is use to get all queue which is present on UI
     * @return List The list of queue name
     */
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

    /**
     * This method is use to get all interaction channel which is present on UI
     * @return List The list of interaction channel name
     */
    public List<String> getInteractionChannels() {
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

    /**
     * This method is use to open workgroup list
     * @throws InterruptedException throw InterruptedException
     */
    public void openWorkgroupList() throws InterruptedException {
        List<WebElement> webElements = returnListOfElement(By.xpath(pageElements.list));
        webElements.get(0).click();
        commonLib.info("Opening Work Group Flow List");
        Thread.sleep(1000);

    }

    /**
     * This method is use to open queue list
     * @throws InterruptedException throw InterruptedException
     */
    public void openLoginQueueList() throws InterruptedException {
        List<WebElement> webElements = returnListOfElement(By.xpath(pageElements.list));
        webElements.get(1).click();
        commonLib.info("Opening Login Queue Flow List");
        Thread.sleep(1000);
    }


    /**
     * This method is use to click edit button
     */
    public void clickViewEditButton() {
        commonLib.info("Clicking View/Edit button");
        clickAndWaitForLoaderToBeRemoved(pageElements.viewEditButton);
    }

    /**
     * This method is use to wait until edit page open
     */
    public void waitUntilEditPageIsOpen() {
        commonLib.info("Waiting Until Edit Profile Page is Loaded");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.cancelButton));
    }

    /**
     * This method is use to wait until UM page open
     */
    public void waitTillUMPageLoaded() {
        commonLib.info("Waiting Until Profile Management Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.totalUsersHeading));
    }

    /**
     * This method use to check is search by auuid field visible or not
     * @return true/false
     */
    public boolean isSearchVisible() {
        final boolean state = isEnabled(pageElements.searchAuuid);
        commonLib.info("Checking is Search Auuid Text box is Visible : " + state);
        return state;
    }

    /**
     * This method is use to search auuid by agent auuid
     * @param auuid The auuid
     */
    public void searchAuuid(String auuid) {
        commonLib.info("Writing AUUID to Search auuid Text box : " + auuid);
        enterText(pageElements.searchAuuid, auuid);
    }

    /**
     * This method is use to click search button
     */
    public void clickSearchButton() {
        commonLib.info("Clicking on Search Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchButton);
    }

    /**
     * This method is use to wait until search result page open
     */
    public void waitUntilResultPageIsVisible() {
        waitTillLoaderGetsRemoved();
        commonLib.info("Waiting Until Result Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageElements.searchButton));
    }

    /**
     * This method is used to check searched auuid visible in list
     * @param auuid The auuid
     * @return String The value
     */
    public String resultIsVisible(String auuid) {

        By title = By.xpath(pageElements.auuid + auuid + "']");
        final boolean visible = isElementVisible(title);
        commonLib.info("Checking is Search Auuid Text box is Visible : " + visible);
        final String text = getText(title);
        commonLib.info("Getting auuid from result : " + text);
        return text;
    }

    /**
     * This method use to open list of interaction channel
     */
    public void openListInteractionChannels() throws InterruptedException {
        commonLib.info("Opening Interaction Channel List");
        clickAndWaitForLoaderToBeRemoved(pageElements.interactionChannel);
        Thread.sleep(1000);
    }


    /**
     * This method is use to close options overlay
     * @throws InterruptedException Throw interrupt exception
     */
    public void pressESC() throws InterruptedException {
        driver.findElement(By.xpath(pageElements.backButton)).click();
        Thread.sleep(2000);
    }


    /**
     * This method is use to click add to user button
     */
    public void clickAddUserBtn() {
        commonLib.info("Clicking on Add to User Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.addUser);
    }

    /**
     * This method is use to switch add to user page frame
     */
    public void switchFrameToAddUser() {
        commonLib.info("Switching Frame");

    }

    /**
     * This method is use to check add user page open or not
     * @return true/false
     */
    public boolean checkingAddUser() {
        commonLib.info("Checking Add to User Open");
        return isEnabled(pageElements.addUserPageTitle);
    }

    /*
    This Method will tell us user management page loaded or not
     */
    public Boolean isUserManagementPageLoaded() {
        waitTillLoaderGetsRemoved();
        return isVisible(pageElements.userManagementPage);
    }
}
