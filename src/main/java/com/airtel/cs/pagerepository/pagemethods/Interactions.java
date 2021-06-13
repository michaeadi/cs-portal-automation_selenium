package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.InteractionPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class Interactions extends BasePage {

    InteractionPage pageElements;
    private static final String XPATH = "//div[@formarrayname='issueDetails']//li[";

    public Interactions(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, InteractionPage.class);
    }

    public void clickOnContinueButton() {
        commonLib.info("clicking on continue button");
        clickWithoutLoader(pageElements.continueButton);
    }

    public String getAvailableDateField() {
        commonLib.info("Checking is Date Field is available at expected place");
        return getText(pageElements.issueDetails);
    }

    public String isDateFieldAvailableMandatory() {
        commonLib.info("Is Date Field mandatory: ");
        return getText(pageElements.issueDetails) + getText(pageElements.issueDetailsMandatory);
    }


    public void setDateFieldAvailable(String date) {
        commonLib.info("Writing date to date Field : " + date);
        By issueDetails = By.xpath("//input[@aria-haspopup='true']");
        enterText(issueDetails, date);
    }

    public String getIssueDetailLabel(String num) {
        commonLib.info("Getting the label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + num + "']//following-sibling::span/label");
        return getText(issueDetails);
    }

    public String getIssueDetailLabelDropDown(String num) {
        commonLib.info("Getting the label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(XPATH + num + "]//mat-label");
        By mandatory = By.xpath(XPATH + num + "]//span");
        return getText(issueDetails) + getText(mandatory);
    }

    public void setIssueDetailInput(String num, String input) {
        commonLib.info("Writing " + input + " in label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(" //input[@name=" + "'q" + num + "']");
        enterText(issueDetails, input);
    }

    public void selectIssueDetailInput(String num) {
        commonLib.info("Selecting label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(XPATH + num + "]//mat-select");
        clickAndWaitForLoaderToBeRemoved(issueDetails);
        clickAndWaitForLoaderToBeRemoved(pageElements.option1st);
    }

    /*
    This Method will open the issue code drop down while creating interaction
     */
    public void clickOnCode() {
        List<WebElement> listOfElements = returnListOfElement(pageElements.clickCodeDropDown);
        listOfElements.get(0).click();
        commonLib.info("clicking on issue code field");
    }

    public boolean isSearchVisible() {
        commonLib.info("Checking is search Visible");
        return isElementVisible(pageElements.search);
    }

    public void searchCode(String code) {
        commonLib.info("searching issue code " + code);
        enterText(pageElements.search, code);
    }

    /*
    This Method is used to select issue code
     */
    public void selectCode(String code) {
        selectByText(code);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.loader));
        commonLib.info("selecting issue code " + code);
    }

    public String getIssue() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(1).getText();
        commonLib.info("Getting issue " + text);
        return text;
    }

    public String getIssueType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(2).getText();
        commonLib.info("Getting Issue Type " + text);
        return text;

    }

    public String getIssueSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(3).getText();
        commonLib.info("Getting Issue Sub Type " + text);
        return text;
    }

    public String getIssueSubSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(4).getText();
        commonLib.info("Getting issue sub sub type " + text);
        return text;
    }

    public void sendComment(String comment) {
        enterText(pageElements.interactionComment, comment);
        commonLib.info("Adding comment -" + comment);

    }

    public void clickOnSave() {
        clickAndWaitForLoaderToBeRemoved(pageElements.saveButton);
        commonLib.info("Clicking on save to create Ticket");
    }

    public boolean isSaveEnable() {
        commonLib.info("Checking is Save button Enabled");
        if (isEnabled(pageElements.saveButton)) {
            return true;
        } else {
            throw new ElementClickInterceptedException("Save Button does not enabled");
        }
    }

    public boolean isTicketIdVisible() {
        final boolean visible = isVisible(pageElements.ticketIdOnHeader);
        commonLib.info("Checking is Ticket Number or Ticket Status is Displayed : " + visible);
        return visible;
    }

    /*
    This Method is used to get the Ticket Number or Ticket Status from header of created ticket
     */
    public String getResolvedFTRDisplayed() {
        String result = "";
        if (isVisible(pageElements.ticketIdOnHeader)) {
            result = getText(pageElements.ticketIdOnHeader);
            commonLib.info("Getting the Ticket Number or Ticket Status  Displayed : " + result);
        } else {
            commonLib.fail("Ticket Number or Ticket Status  NOT Displayed", true);
        }
        return result;
    }

    public CustomerProfile closeInteractions() {
        if (isClickable(pageElements.closeInteractions)) {
            clickWithoutLoader(pageElements.closeInteractions);
            commonLib.info("Closing Interaction Screen");
        }
        return new CustomerProfile(driver);
    }

    /*
    This Methos is used to open the comment box
     */
    public void clickCommentIcon() {
        if (isVisible(pageElements.ticketCommentIcon)) {
            commonLib.info("Click On Ticket Comment Icon");
            clickWithoutLoader(pageElements.ticketCommentIcon);
        } else {
            commonLib.fail("Ticket Comment Icon is NOT Visible", true);
        }
    }

    /*
    This Method is used to open comment tab from interaction creation tab
     */
    public void openAddedComment() {
        if (isVisible(pageElements.ticketCommentIcon)) {
            commonLib.info("Click On Ticket Comment Icon");
            clickWithoutLoader(pageElements.ticketCommentIcon);
        } else {
            commonLib.fail("Ticket Comment Icon is NOT Visible", true);
        }
    }

    /*
    This Method is used to add interaction comment in an interaction
     */
    public String addInteractionComment() {
        String text = "Adding Interaction Comment Using Automation";
        commonLib.info("Going to add Interaction Ticket Comment: " + text);
        if (isVisible(pageElements.commentBox))
            enterText(pageElements.commentBox, text);
        else
            commonLib.fail("Comment Box is NOT Visible", true);
        return text;
    }

    public String getAddedComment() {
        String result = "";
        if (isVisible(pageElements.addCommentBtn)) {
            commonLib.info("Added Comment Validate Successfully");
            result = getText(pageElements.addedComment);
        } else {
            commonLib.fail("Added Comment is NOT Visibel", true);
        }
        return result;
    }

    public void saveInteractionComment() {
        commonLib.info("Clicking Save Comment Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.addCommentBtn);
    }

    public void closeTicketCommentBox() {
        commonLib.info("Closing Ticket Comment Pop up");
        clickWithoutLoader(pageElements.closeCommentTab);
    }

    public void resetInteractionIssue() {
        commonLib.info("Clicking reset issue details Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.resetBtn);
        waitTillLoaderGetsRemoved();
    }

}