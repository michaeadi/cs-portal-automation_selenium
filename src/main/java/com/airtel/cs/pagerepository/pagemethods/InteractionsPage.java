package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.InteractionPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class InteractionsPage extends BasePage {

    InteractionPageElements pageElements;

    public InteractionsPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, InteractionPageElements.class);
    }

    public void clickOnContinueButton() {
        UtilsMethods.printInfoLog("clicking on continue button");
        click(pageElements.continueButton);
    }

    public String isDateFieldAvailable() {
        UtilsMethods.printInfoLog("Checking is Date Field is available at expected place ");
        return readText(pageElements.issueDetails);
    }

    public String isDateFieldAvailableMandatory() {
        UtilsMethods.printInfoLog("Is Date Field mandatory: ");
        return readText(pageElements.issueDetails) + readText(pageElements.issueDetailsMandatory);
    }


    public void setDateFieldAvailable(String date) {
        UtilsMethods.printInfoLog("Writing date to date Field : " + date);
        By issueDetails = By.xpath("//input[@aria-haspopup=\"true\"]");
        writeText(issueDetails, date);
    }

    public String getIssueDetailLabel(String num) {
        UtilsMethods.printInfoLog("Getting the label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + num + "']//following-sibling::span/label");
        return readText(issueDetails);
    }

    public String getIssueDetailLabelDropDown(String num) {
        UtilsMethods.printInfoLog("Getting the label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath("//div[@formarrayname=\"issueDetails\"]//li[" + num + "]//mat-label");
        By mandatory = By.xpath("//div[@formarrayname=\"issueDetails\"]//li[" + num + "]//span");
        return readText(issueDetails) + readText(mandatory);
    }

    public void setIssueDetailInput(String num, String input) {
        UtilsMethods.printInfoLog("Writing " + input + " in label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + num + "']");
        writeText(issueDetails, input);
    }

    public void selectIssueDetailInput(String num) {
        UtilsMethods.printInfoLog("Selecting label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath("//div[@formarrayname=\"issueDetails\"]//li[" + num + "]//mat-select");
        click(issueDetails);
        click(pageElements.option1st);
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
        return isElementVisible(pageElements.search);
    }

    public void searchCode(String code) {
        UtilsMethods.printInfoLog("searching issue code " + code);
        writeText(pageElements.search, code);
    }

    public void selectCode(String code) {
        selectByText(code);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.loader));
        UtilsMethods.printInfoLog("selecting issue code " + code);
    }

    public String getIssue() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        UtilsMethods.printInfoLog("Getting issue " + listOfElements1.get(1).getText());
        return listOfElements1.get(1).getText();

    }

    public String getIssueType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        UtilsMethods.printInfoLog("Getting Issue Type " + listOfElements1.get(2).getText());
        return listOfElements1.get(2).getText();

    }

    public String getIssueSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        UtilsMethods.printInfoLog("Getting Issue Sub Type " + listOfElements1.get(3).getText());
        return listOfElements1.get(3).getText();
    }

    public String getIssueSubSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        UtilsMethods.printInfoLog("Getting issue sub sub type " + listOfElements1.get(4).getText());
        return listOfElements1.get(4).getText();
    }

    public void sendComment(String comment) {
        writeText(pageElements.interactionComment, comment);
        UtilsMethods.printInfoLog("Adding comment -" + comment);

    }

    public void clickOnSave() {
        click(pageElements.saveButton);
        UtilsMethods.printInfoLog("Clicking on save to create Ticket");

    }

    public boolean isSaveEnable() {
        UtilsMethods.printInfoLog("Checking is Save button Enabled");
        if (checkState(pageElements.saveButton)) {
            return true;
        } else {
            throw new ElementClickInterceptedException("Save Button does not enabled");
        }
    }

    public boolean isResolvedFTRDisplayed() {
        waitVisibility(pageElements.resolvedFTR);
        UtilsMethods.printInfoLog("Checking is Ticket Number or Ticket Status is Displayed : " + isElementVisible(pageElements.resolvedFTR));
        return isElementVisible(pageElements.resolvedFTR);
    }

    public String getResolvedFTRDisplayed() {
        waitVisibility(pageElements.resolvedFTR);
        UtilsMethods.printInfoLog("Getting the Ticket Number or Ticket Status  Displayed : " + readText(pageElements.resolvedFTR));
        return readText(pageElements.resolvedFTR);
    }

    public CustomerProfilePage closeInteractions() {
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.closeInteractions));
        click(pageElements.closeInteractions);
        UtilsMethods.printInfoLog("Closing Interaction Screen");
        return new CustomerProfilePage(driver);
    }

    public void clickCommentIcon() {
        log.info("Waiting for Comment Ticket Icon");
        waitVisibility(pageElements.resolvedFTR);
        UtilsMethods.printInfoLog("Click On Ticket Comment Icon");
        click(pageElements.ticketCommentIcon);
    }

    public void openAddedComment() {
        waitVisibility(pageElements.ticketCommentIcon);
        UtilsMethods.printInfoLog("Click On Ticket Comment Icon");
        click(pageElements.ticketCommentIcon);
    }


    public void addInteractionComment(String text) {
        UtilsMethods.printInfoLog("Adding Interaction Ticket Comment: " + text);
        writeText(pageElements.commentBox, text);
    }

    public String getAddedComment() {
        UtilsMethods.printInfoLog("Added Comment Validate Successfully");
        return readText(pageElements.addedComment);
    }

    public void saveInteractionComment() {
        log.info("Clicking Save Comment Button");
        click(pageElements.addCommentBtn);
    }

    public void closeTicketCommentBox() {
        log.info("Closing Ticket Comment Pop up");
        click(pageElements.closeCommentTab);
    }

    public void resetInteractionIssue() {
        UtilsMethods.printInfoLog("Clicking reset issue details Button");
        click(pageElements.resetBtn);
        waitTillLoaderGetsRemoved();
    }

}