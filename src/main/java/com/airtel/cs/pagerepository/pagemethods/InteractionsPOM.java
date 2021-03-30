package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class InteractionsPOM extends BasePage {

    By code = By.xpath("//span[contains(@class,'mat-select-placeholder ng-tns-c9-')]");
    By issues = By.xpath("//span[starts-with(@class,'ng-tns-c9-')]");
    By search = By.xpath("//input[@placeholder='Search' and @class='search-box mat-input-element mat-form-field-autofill-control cdk-text-field-autofill-monitored ng-star-inserted']");
    By interactionComment = By.xpath("//textarea[@id='interactionComment']");
    By saveButton = By.xpath("//button[@class='btn btn-save ng-star-inserted']");
    By resolvedFTR = By.xpath("//span[@class='ticket-id-color']");
    By closeInteractions = By.xpath("//mat-icon[@class=\"tab-close mat-icon notranslate material-icons mat-icon-no-color ng-star-inserted\"]");
    By addInteractions = By.xpath("//a[@class='issue-add']");
    By loader = By.xpath("/html/body/app-root/ngx-ui-loader/div[2]");
    By issueDetailHeading = By.xpath("//h3[text()=\"Issue Detail\"]");
    By continueButton = By.xpath("//button[@class=\"yes-btn mat-button\"]");
    By issueDetails = By.xpath("//input[@aria-haspopup=\"true\"]//following-sibling::span/label//mat-label");
    By issueDetailsMandatory = By.xpath("//input[@aria-haspopup=\"true\"]//following-sibling::span/label//span");
    By ticketCommentIcon = By.className("comment-text");
    By commentBox = By.xpath("//textarea[@placeholder='Add Comment...']");
    By addCommentBtn = By.xpath("//div[@class='footer']/button");
    By addedComment = By.xpath("//div[@class='comment-detail ng-star-inserted']");
    By closeCommentTab = By.xpath("//div[@class='header-close']");
    By resetBtn = By.xpath("//button[@class='btn btn-reset ng-star-inserted']");
    By option1st = By.xpath("//mat-option[1]");

    public InteractionsPOM(WebDriver driver) {
        super(driver);
    }

    public void clickOnContinueButton() {
        UtilsMethods.printInfoLog("clicking on continue button");
        click(continueButton);
    }

    public String isDateFieldAvailable() {
        UtilsMethods.printInfoLog("Checking is Date Field is available at expected place ");
        return readText(issueDetails);
    }

    public String isDateFieldAvailableMandatory() {
        UtilsMethods.printInfoLog("Is Date Field mandatory: ");
        return readText(issueDetails) + readText(issueDetailsMandatory);
    }


    public void setDateFieldAvailable(String Date) {
        UtilsMethods.printInfoLog("Writing Date to Date Field : " + Date);
        By issueDetails = By.xpath("//input[@aria-haspopup=\"true\"]");
        writeText(issueDetails, Date);
    }

    public String getIssueDetailLabel(String Num) {
        UtilsMethods.printInfoLog("Getting the label for issue detail field situated at Position : " + Num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + Num + "']//following-sibling::span/label");
        return readText(issueDetails);
    }

    public String getIssueDetailLabelDropDown(String Num) {
        UtilsMethods.printInfoLog("Getting the label for issue detail field situated at Position : " + Num);
        By issueDetails = By.xpath("//div[@formarrayname=\"issueDetails\"]//li[" + Num + "]//mat-label");
        By mandatory = By.xpath("//div[@formarrayname=\"issueDetails\"]//li[" + Num + "]//span");
        return readText(issueDetails) + readText(mandatory);
    }

    public void setIssueDetailInput(String Num, String Input) {
        UtilsMethods.printInfoLog("Writing " + Input + " in label for issue detail field situated at Position : " + Num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + Num + "']");
        writeText(issueDetails, Input);
    }

    public void selectIssueDetailInput(String Num) {
        UtilsMethods.printInfoLog("Selecting label for issue detail field situated at Position : " + Num);
        By issueDetails = By.xpath("//div[@formarrayname=\"issueDetails\"]//li[" + Num + "]//mat-select");
        click(issueDetails);
        click(option1st);
    }

    public void clickOnCode() throws InterruptedException {
        waitTillLoaderGetsRemoved();
        Thread.sleep(1000);
        List<WebElement> listOfElements = returnListOfElement(By.xpath("//div[@class=\"mat-select-value\"]"));
        listOfElements.get(0).click();
        UtilsMethods.printInfoLog("clicking on issue code field");
    }

    public boolean isSearchVisible() {
        UtilsMethods.printInfoLog("Checking is search Visible");
        return isElementVisible(search);
    }

    public void searchCode(String code) {
        UtilsMethods.printInfoLog("searching issue code " + code);
        writeText(search, code);
    }

    public void selectCode(String code) throws InterruptedException {
        selectByText(code);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        UtilsMethods.printInfoLog("selecting issue code " + code);
    }

    public String getIssue() {
        List<WebElement> listOfElements1 = returnListOfElement(issues);
        UtilsMethods.printInfoLog("Getting issue " + listOfElements1.get(1).getText());
        return listOfElements1.get(1).getText();

    }

    public String getIssueType() {
        List<WebElement> listOfElements1 = returnListOfElement(issues);
        UtilsMethods.printInfoLog("Getting Issue Type " + listOfElements1.get(2).getText());
        return listOfElements1.get(2).getText();

    }

    public String getIssueSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(issues);
        UtilsMethods.printInfoLog("Getting Issue Sub Type " + listOfElements1.get(3).getText());
        return listOfElements1.get(3).getText();
    }

    public String getIssueSubSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(issues);
        UtilsMethods.printInfoLog("Getting issue sub sub type " + listOfElements1.get(4).getText());
        return listOfElements1.get(4).getText();
    }

    public void sendComment(String Comment) {
        writeText(interactionComment, Comment);
        UtilsMethods.printInfoLog("Adding comment -" + Comment);

    }

    public void clickOnSave() {
        click(saveButton);
        UtilsMethods.printInfoLog("Clicking on save to create Ticket");

    }

    public boolean isSaveEnable() {
        UtilsMethods.printInfoLog("Checking is Save button Enabled");
        if (checkState(saveButton)) {
            return true;
        } else {
            throw new ElementClickInterceptedException("Save Button does not enabled");
        }
    }

    public boolean isResolvedFTRDisplayed() {
        waitVisibility(resolvedFTR);
        UtilsMethods.printInfoLog("Checking is Ticket Number or Ticket Status is Displayed : " + isElementVisible(resolvedFTR));
        return isElementVisible(resolvedFTR);
    }

    public String getResolvedFTRDisplayed() {
        waitVisibility(resolvedFTR);
        UtilsMethods.printInfoLog("Getting the Ticket Number or Ticket Status  Displayed : " + readText(resolvedFTR));
        return readText(resolvedFTR);
    }

    public customerInteractionPagePOM closeInteractions() {
        wait.until(ExpectedConditions.elementToBeClickable(closeInteractions));
        click(closeInteractions);
        UtilsMethods.printInfoLog("Closing Interaction Screen");
        return new customerInteractionPagePOM(driver);
    }

    public void clickCommentIcon() {
        log.info("Waiting for Comment Ticket Icon");
        waitVisibility(resolvedFTR);
        UtilsMethods.printInfoLog("Click On Ticket Comment Icon");
        click(ticketCommentIcon);
    }

    public void openAddedComment() {
        waitVisibility(ticketCommentIcon);
        UtilsMethods.printInfoLog("Click On Ticket Comment Icon");
        click(ticketCommentIcon);
    }


    public void addInteractionComment(String text) {
        UtilsMethods.printInfoLog("Adding Interaction Ticket Comment: " + text);
        writeText(commentBox, text);
    }

    public String getAddedComment() {
        UtilsMethods.printInfoLog("Added Comment Validate Successfully");
        return readText(addedComment);
    }

    public void saveInteractionComment() {
        log.info("Clicking Save Comment Button");
        click(addCommentBtn);
    }

    public void closeTicketCommentBox() {
        log.info("Closing Ticket Comment Pop up");
        click(closeCommentTab);
    }

    public void resetInteractionIssue() {
        UtilsMethods.printInfoLog("Clicking reset issue details Button");
        click(resetBtn);
        waitTillLoaderGetsRemoved();
    }

}