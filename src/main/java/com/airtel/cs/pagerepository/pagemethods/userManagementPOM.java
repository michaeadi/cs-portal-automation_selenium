package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class userManagementPOM extends BasePage {
    By totalUsersHeading = By.xpath("//span[text()=\"Total users\"]");
    By interactionChannel = By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"false\"]");
    By searchAuuid = By.xpath("//input[@placeholder=\"UserId/name\"]");
    By searchButton = By.xpath("//img[@class=\"filter-container__filter--form--section--search-icon\"]");
    By viewEditButton = By.xpath("//div[@class=\"agent-list-container__agent-list--list-row--value--cta--action-button--action-label\"]");
    By updateButton = By.xpath("//button[@class=\"new-user-CTA__submit mat-button\"]");
    By cancelButton = By.xpath("//button[@class=\"new-user-CTA__cancel mat-button\"]");
    By channelsOptions = By.xpath("//mat-option[@role=\"option\"]/span");
    By workflowsOptions = By.xpath("//span[@class='mat-option-text']");
    By ticketBucketSize = By.xpath("//tr[@class=\"agent-list-container__agent-list--list-row ng-star-inserted\"]/td[7]/div");
    By bucketSize = By.xpath("//input[@formcontrolname=\"bucketSize\"]");

    public userManagementPOM(WebDriver driver) {
        super(driver);
    }

    public void clickUpdateButton() {
        UtilsMethods.printInfoLog("Clicking on Update Button");
        click(updateButton);
    }

    public void setTicketBucketSize(int Size) {
        UtilsMethods.printInfoLog("Setting Current Ticket Size : " + Size);
        clearInputTag(bucketSize);
        writeText(bucketSize, String.valueOf(Size));
    }

    public String getCurrentTicketBucketSize() {
        UtilsMethods.printInfoLog("Getting Current Ticket Size : " + readText(ticketBucketSize));
        return readText(ticketBucketSize);
    }

    public ArrayList<String> getWorkflows() {
        List<WebElement> listOfElements = returnListOfElement(workflowsOptions);
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
        List<WebElement> listOfElements = returnListOfElement(workflowsOptions);
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
        List<WebElement> listOfElements = returnListOfElement(channelsOptions);
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
        click(viewEditButton);
    }

    public void waitUntilEditPageIsOpen() {
        UtilsMethods.printInfoLog("Waiting Until Edit Profile Page is Loaded");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
    }

    public void waitTillUMPageLoaded() {
        UtilsMethods.printInfoLog("Waiting Until Profile Management Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalUsersHeading));
    }

    public boolean isSearchVisible() {
        UtilsMethods.printInfoLog("Checking is Search Auuid Text box is Visible : " + checkState(searchAuuid));
        return checkState(searchAuuid);
    }

    public void searchAuuid(String Auuid) {
        UtilsMethods.printInfoLog("Writing AUUID to Search Auuid Text box : " + Auuid);
        writeText(searchAuuid, Auuid);
    }

    public void clickSearchButton() {
        UtilsMethods.printInfoLog("Clicking on Search Button");
        click(searchButton);
    }

    public void waitUntilResultPageIsVisible() {
        waitTillLoaderGetsRemoved();
        UtilsMethods.printInfoLog("Waiting Untill Result Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
    }

    public String resultIsVisible(String AUUID) {

        By title = By.xpath("//div[@title='" + AUUID + "']");
        UtilsMethods.printInfoLog("Checking is Search Auuid Text box is Visible : " + isElementVisible(title));
        UtilsMethods.printInfoLog("Getting AUUID from result : " + readText(title));
        isElementVisible(title);
        return readText(title);
    }

    public void openListInteractionChannels() throws InterruptedException {
        UtilsMethods.printInfoLog("Opening Interaction Channel List");
        click(interactionChannel);
        Thread.sleep(1000);
    }

    public void pressESCWorkflow() {
        List<WebElement> webElements = returnListOfElement(By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"true\"]"));
        webElements.get(0).sendKeys(Keys.ESCAPE);
    }

    public void pressESC() throws InterruptedException {
//        driver.findElement(interactionChannel).sendKeys(Keys.ESCAPE);
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


}