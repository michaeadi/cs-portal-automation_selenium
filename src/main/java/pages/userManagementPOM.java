package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
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
        log.info("Clicking on Update Button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Update Button");
        click(updateButton);
    }

    public void setTicketBucketSize(int Size) {
        log.info("Setting Current Ticket Size : " + Size);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Setting Current Ticket Size : " + Size);
        clearInputTag(bucketSize);
        writeText(bucketSize, String.valueOf(Size));
    }

    public String getCurrentTicketBucketSize() {
        log.info("Getting Current Ticket Size : " + readText(ticketBucketSize));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Current Ticket Size : " + readText(ticketBucketSize));
        return readText(ticketBucketSize);
    }

    public ArrayList<String> getWorkflows() {
        List<WebElement> listOfElements = returnListOfElement(workflowsOptions);
        System.out.println("total elements " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Work Group: " + listOfElements.get(i).getText().toLowerCase().trim());
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
        System.out.println("total elements " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Login Queue: " + listOfElements.get(i).getText().toLowerCase().trim());
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
        System.out.println("List Size: " + listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Login Channel: " + listOfElements.get(i).getText().toLowerCase().trim());
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
        log.info("Opening Work Group Flow List");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Work Group Flow List");
        Thread.sleep(1000);

    }

    public void openLoginQueueList() throws InterruptedException {
        List<WebElement> webElements = returnListOfElement(By.xpath("//mat-select[starts-with(@class,'mat-select ng-tns') and @aria-multiselectable=\"true\"]"));
        webElements.get(1).click();
        log.info("Opening Login QueueFlow List");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Login Queue Flow List");
        Thread.sleep(1000);
    }


    public boolean isLoginQueuePresent(String[] strings, String workflow) {
        boolean isThere = false;
        ExtentTestManager.getTest().log(LogStatus.INFO, "finding " + workflow + " in Login Queue List");
        log.info("finding " + workflow + " in Login Queue List");
        for (String a : strings) {
            if (a.equals(workflow)) {
                isThere = true;
                log.info(workflow + " is present in Login Queue List");
                ExtentTestManager.getTest().log(LogStatus.INFO, workflow + " is present in Login Queue List");

            }
        }
        return isThere;
    }

    public void clickViewEditButton() {
        log.info("Clicking View/Edit button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking View/Edit button");
        click(viewEditButton);
    }

    public void waitUntilEditPageIsOpen() {
        log.info("Waiting Until Edit Profile Page is Loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Waiting Until Edit Profile Page is Loaded");
        waitTillLoaderGetsRemoved();
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
    }

    public void waitTillUMPageLoaded() {
        log.info("Waiting Until Profile Management Page is Loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Waiting Until Profile Management Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalUsersHeading));
    }

    public boolean isSearchVisible() {
        log.info("Checking is Search Auuid Text box is Visible : " + checkState(searchAuuid));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Search Auuid Text box is Visible : " + checkState(searchAuuid));
        return checkState(searchAuuid);
    }

    public void searchAuuid(String Auuid) {
        log.info("Writing AUUID to Search Auuid Text box : " + Auuid);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Writing AUUID to Search Auuid Text box : " + Auuid);
        writeText(searchAuuid, Auuid);
    }

    public void clickSearchButton() {
        log.info("Clicking on Search Button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Search Button");
        click(searchButton);
    }

    public void waitUntilResultPageIsVisible() {
        waitTillLoaderGetsRemoved();
        log.info("Waiting Untill Result Page is Loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Waiting Untill Result Page is Loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
    }

    public String resultIsVisible(String AUUID) {

        By title = By.xpath("//div[@title='" + AUUID + "']");
        log.info("Checking is Search Auuid Text box is Visible : " + isElementVisible(title));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Search Auuid Text box is Visible : " + isElementVisible(title));
        log.info("Getting AUUID from result : " + readText(title));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting AUUID from result : " + readText(title));
        isElementVisible(title);
        return readText(title);
    }

    public void openListInteractionChannels() throws InterruptedException {
        log.info("Opening Interaction Channel List");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Interaction Channel List");
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
        log.info("finding " + channel + " in Interaction List");
        ExtentTestManager.getTest().log(LogStatus.INFO, "finding " + channel + " in Interaction List");
        for (String a : strings) {
            if (a.equals(channel)) {
                isThere = true;
                log.info(channel + " is present in interaction Channel List");
                ExtentTestManager.getTest().log(LogStatus.INFO, channel + " is present in interaction Channel List");
            }
        }
        return isThere;
    }


}