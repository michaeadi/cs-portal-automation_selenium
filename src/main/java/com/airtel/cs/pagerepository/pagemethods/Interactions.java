package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.InteractionPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
public class Interactions extends BasePage {

    InteractionPage pageElements;
    private static final String XPATH = "//div[@formarrayname='issueDetails']//li[";

    public Interactions(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, InteractionPage.class);
    }

    /**
     * This method is use to click on continue button
     */
    public void clickOnContinueButton() {
        commonLib.info("clicking on continue button");
        clickWithoutLoader(pageElements.continueButton);
    }

    /**
     * This method is used to get date field label
     *
     * @return String The value
     */
    public String getAvailableDateField() {
        commonLib.info("Checking is Date Field is available at expected place");
        return getText(pageElements.issueDetails);
    }

    /**
     * This method used to get date field label + * sign in case field mandatory
     *
     * @return String The value
     */
    public String isDateFieldAvailableMandatory() {
        commonLib.info("Is Date Field mandatory: ");
        return getText(pageElements.issueDetails) + getText(pageElements.issueDetailsMandatory);
    }


    /**
     * This method used to get write date into date field
     *
     * @param date The date
     */
    public void setDateFieldAvailable(String date) {
        commonLib.info("Writing date to date Field : " + date);
        By issueDetails = By.xpath("//input[@aria-haspopup='true']");
        enterText(issueDetails, date);
    }

    /**
     * This method is used to get issue detail label based on question number
     *
     * @param num The number
     * @return String The value
     */
    public String getIssueDetailLabel(String num) {
        commonLib.info("Getting the label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(pageElements.issueField + num + pageElements.fieldLabel);
        return getText(issueDetails);
    }

    /**
     * This method use to get issue field label of drop down type field based on num
     *
     * @param num The number
     * @return String The value
     */
    public String getIssueDetailLabelDropDown(String num) {
        commonLib.info("Getting the label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(XPATH + num + pageElements.dropDown);
        By mandatory = By.xpath(XPATH + num + pageElements.mandatorySign);
        return getText(issueDetails) + getText(mandatory);
    }

    /**
     * This method is use to write text into input field based on question number
     *
     * @param num   The Number
     * @param input The text
     */
    public void setIssueDetailInput(String num, String input) {
        commonLib.info("Writing " + input + " in label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(pageElements.issueField + num + "']");
        enterText(issueDetails, input);
    }

    /**
     * This method is use to select from dropdown based on option number
     *
     * @param num The Number
     */
    public void selectIssueDetailInput(String num) {
        commonLib.info("Selecting label for issue detail field situated at Position : " + num);
        By issueDetails = By.xpath(XPATH + num + pageElements.selectDropDown);
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

    /**
     * This method is used to check is search visible or not
     *
     * @return true/false
     */
    public boolean isSearchVisible() {
        commonLib.info("Checking is search Visible");
        return isElementVisible(pageElements.search);
    }

    /**
     * This method is used to write code into search box
     */
    public void searchCode(String code) {
        commonLib.info("searching issue code " + code);
        enterText(pageElements.search, code);
    }

    /*
    This Method is used to select issue code
     */
    public void selectCode(String code) {
        try {
            selectByText(code);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pageElements.loader));
            commonLib.info("selecting issue code " + code);
        } catch (Exception e) {
            commonLib.fail("Exception in method - selectCode", true);
            clickOutside();
        }
    }

    /**
     * This method is used to get issue field value
     *
     * @return String The value
     */
    public String getIssue() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(1).getText();
        commonLib.info("Getting issue " + text);
        return text;
    }

    /**
     * This method is used to get issue type field value
     *
     * @return String The value
     */
    public String getIssueType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(2).getText();
        commonLib.info("Getting Issue Type " + text);
        return text;

    }

    /**
     * This method is used to get issue sub type field value
     *
     * @return String The value
     */
    public String getIssueSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(3).getText();
        commonLib.info("Getting Issue Sub Type " + text);
        return text;
    }

    /**
     * This method is used to get issue sub sub type field value
     *
     * @return String The value
     */
    public String getIssueSubSubType() {
        List<WebElement> listOfElements1 = returnListOfElement(pageElements.issues);
        final String text = listOfElements1.get(4).getText();
        commonLib.info("Getting issue sub sub type " + text);
        return text;
    }

    /**
     * This method use to write comment into comment box
     *
     * @param comment The comment
     */
    public void sendComment(String comment) {
        enterText(pageElements.interactionComment, comment);
        commonLib.info("Adding comment -" + comment);
    }

    /**
     * This method use to click on save button
     */
    public void clickOnSave() {
        clickAndWaitForLoaderToBeRemoved(pageElements.saveButton);
        commonLib.info("Clicking on save to create Ticket");
    }

    /**
     * This method use to check save button enable or not
     *
     * @return true/false
     */
    public boolean isSaveEnable() {
        commonLib.info("Checking is Save button Enabled");
        if (isEnabled(pageElements.saveButton)) {
            return true;
        } else {
            throw new ElementClickInterceptedException("Save Button does not enabled");
        }
    }

    /**
     * This method use to check ticket id visible or not
     *
     * @return true/false
     */
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

    /**
     * This method used to close create interaction tab
     */
    public void closeInteractions() {
        if (isClickable(pageElements.closeInteractions)) {
            clickWithoutLoader(pageElements.closeInteractions);
            commonLib.info("Closing Interaction Screen");
        }
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

    /**
     * This method is used to get added comment
     *
     * @return String The value
     */
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

    /**
     * This method is use to click on save interaction comment button
     */
    public void saveInteractionComment() {
        commonLib.info("Clicking Save Comment Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.addCommentBtn);
    }

    /**
     * This method is use to click close comment section button
     */
    public void closeTicketCommentBox() {
        commonLib.info("Closing Ticket Comment Pop up");
        clickWithoutLoader(pageElements.closeCommentTab);
    }

    /**
     * This method is use to click reset interaction issue
     */
    public void resetInteractionIssue() {
        commonLib.info("Clicking reset issue details Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.resetBtn);
        waitTillLoaderGetsRemoved();
    }
    
    public void fillIssueFields(String issueFieldLabel1,String fieldType,String fieldMandatory,String questionNumber){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        if (issueFieldLabel1 != null)
            if (fieldType.equalsIgnoreCase("Text Box") && !issueFieldLabel1.isEmpty()) {
                commonLib.info(pages.getInteractionsPage().getIssueDetailLabel(questionNumber));
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel(questionNumber).replaceAll("[^a-zA-Z]+", "").trim(), (issueFieldLabel1.replaceAll("[^a-zA-Z]+", "").trim()), issueFieldLabel1 + " Label matched", issueFieldLabel1 + " Label does not match"));
                if (fieldMandatory.equalsIgnoreCase("Yes")) {
                    assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel(questionNumber).contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contain '*' "));
                }
                pages.getInteractionsPage().setIssueDetailInput(questionNumber, "012345");
            } else if (fieldType.equalsIgnoreCase("Date") && !issueFieldLabel1.isEmpty()) {
                commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel1, "Label 1 Matched", "Label 1 NOT Matched"));
                if (fieldMandatory.equalsIgnoreCase("Yes")) {
                    assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contain '*' "));
                }
                pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
            } else if (fieldType.equalsIgnoreCase("Drop Down") && !issueFieldLabel1.isEmpty()) {
                commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown(questionNumber));
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown(questionNumber).replace("*", "").trim(), (issueFieldLabel1.replace("*", "").trim()), issueFieldLabel1 + "Label matched", issueFieldLabel1 + "Label does not match"));
                if (fieldMandatory.equalsIgnoreCase("Yes")) {
                    assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown(questionNumber).contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contain '*' "));
                }
                pages.getInteractionsPage().selectIssueDetailInput(questionNumber);
            }
    }

}