package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ViewTicketPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class ViewTicket extends BasePage {

    ViewTicketPage pageElements;
    private static final String READING_COMMENT = "Reading Comment:";

    public ViewTicket(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ViewTicketPage.class);
    }

    /**
     * This method is use to get ticket id which view detail page open
     *
     * @return String The value
     */
    public String getTicketId() {
        final String text = getText(pageElements.ticketIdValue);
        commonLib.info("View Ticket: " + text);
        return text;
    }

    /**
     * This method is use to select the ticket state by state name and return the same state name in-case of state found other-wise return 'Required state not found
     *
     * @return String The value
     */
    public String selectState(String state) throws InterruptedException {
        commonLib.info("Finding State: " + state);
        scrollToViewElement(pageElements.submitAs);
        clickAndWaitForLoaderToBeRemoved(pageElements.arrowIcon);
        try {
            List<WebElement> list = returnListOfElement(pageElements.allTicketState);
            commonLib.info("List Size: " + list.size());
            for (int i = 1; i <= list.size(); i++) {
                By chooseState = By.xpath(pageElements.stateOptions + i + pageElements.stateText);
                commonLib.info("State Read: " + getText(chooseState));
                if (state.equalsIgnoreCase(getText(chooseState).trim())) {
                    commonLib.info("Selecting State: " + state);
                    clickAndWaitForLoaderToBeRemoved(chooseState);
                    String selectedState = getText(pageElements.stateName);
                    commonLib.info("Clicking on Submit as " + selectedState);
                    clickAndWaitForLoaderToBeRemoved(pageElements.submitAs);
                    return selectedState;
                }
            }
            commonLib.fail(state + " State does not mapped to ticket", true);
            return "Required State not found";
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            commonLib.fail("No State does not mapped to ticket " + e.fillInStackTrace(), true);
        }
        return "Required State not found";
    }


    /**
     * This method is use to get ticket state name
     *
     * @return String The value
     */
    public String getStateName() {
        final String text = getText(pageElements.stateName);
        commonLib.info("State: " + text);
        return text;
    }

    /**
     * This method is use to validate comment found in comment section based on comment text
     *
     * @param text The comment text
     */
    public void validateAddedComment(String text) {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allComment);
            for (int i = 1; i <= list.size(); i++) {
                By comment = By.xpath(pageElements.commentSection + i + pageElements.addComment);
                commonLib.info(READING_COMMENT + getText(comment) + " Is:" + getText(comment).trim().equalsIgnoreCase(text));
                if (getText(comment).trim().equalsIgnoreCase(text)) {
                    commonLib.pass("Newly added comment found on ticket");
                    return;
                }
            }
            commonLib.fail("Newly added comment does not found on ticket", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is use to validate comment type  found  or not in comment section based on comment type
     *
     * @param text The comment type
     * @return true/false
     */
    public boolean validateCommentType(String text) {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allComment);
            for (int i = 1; i <= list.size(); i++) {
                By commentType = By.xpath(pageElements.commentSection + i + pageElements.commentType);
                commonLib.info(READING_COMMENT + getText(commentType) + " Is:" + getText(commentType).trim().equalsIgnoreCase(text));
                if (getText(commentType).trim().equalsIgnoreCase(text)) {
                    commonLib.pass("Comment type found on ticket: " + getText(commentType));
                    return true;
                }
            }
            commonLib.warning("No Issue Comment type found on ticket: " + text);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            commonLib.info("Exception Occurred: " + e.fillInStackTrace());
            return false;
        }
    }

    /**
     * This method use to add comment in comment box
     *
     * @param comment The comment
     * @throws InterruptedException in-case scroll interrupt
     */
    public void addComment(String comment) throws InterruptedException {
        scrollToViewElement(pageElements.addCommentBox);
        enterText(pageElements.addCommentBox, comment);
        commonLib.info("Adding comment on ticket:" + comment);
    }

    /**
     * This method use to click add comment icon in comment section
     *
     * @throws InterruptedException in-case scroll interrupt
     */
    public void clickAddButton() throws InterruptedException {
        scrollToViewElement(pageElements.addBtn);
        clickAndWaitForLoaderToBeRemoved(pageElements.addBtn);
        commonLib.info("Clicking on Add comment button");
    }

    /**
     * This method use to click edit comment icon in comment section
     */
    public void openEditCommentBox() {
        commonLib.info("Editing last added comment");
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        By lastAddedComment = By.xpath(pageElements.iconList + list.size() + pageElements.editCommentSection);
        clickAndWaitForLoaderToBeRemoved(lastAddedComment);
    }

    /**
     * This method use to clear comment box
     */
    public void clearCommentBox() {
        commonLib.info("Clearing Comment Box");
        clearInputTag(pageElements.addCommentBox);
    }

    /**
     * This method use to click delete comment icon in comment section
     */
    public void openDeleteComment() {
        commonLib.info("Deleting last added comment");
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        By deleteComment = By.xpath(pageElements.iconList + list.size() + pageElements.deleteIcon);
        clickAndWaitForLoaderToBeRemoved(deleteComment);
    }

    /**
     * This method use to click continue button on overlay open
     */
    public void clickContinueButton() {
        commonLib.info("Clicking on Continue button");
        clickAndWaitForLoaderToBeRemoved(pageElements.continueBtn);
    }

    /**
     * This method validate the comment is found or not in comment section
     *
     * @param text The comment
     * @return true/false
     */
    public boolean isCommentDelete(String text) {
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        for (int i = 1; i <= list.size() - 1; i++) {
            By comment = By.xpath(pageElements.commentSection + i + pageElements.addComment);
            commonLib.info(READING_COMMENT + getText(comment) + " Is:" + getText(comment).trim().equalsIgnoreCase(text));
            if (getText(comment).trim().equalsIgnoreCase(text)) {
                commonLib.info("Latest comment found on ticket: " + comment);
                commonLib.fail("Deleted comment found on ticket", true);
                return false;
            }
        }
        commonLib.info("Deleted comment not found on ticket");
        return true;
    }

    /**
     * This method use to click back button on overlay open
     *
     * @throws InterruptedException in-case of scroll interrupt
     */
    public void clickBackButton() throws InterruptedException {
        commonLib.info("Clicking Back button");
        scrollToViewElement(pageElements.backButton);
        clickAndWaitForLoaderToBeRemoved(pageElements.backButton);
    }

    /**
     * This method use to open ticket history log section
     *
     * @throws InterruptedException in-case of scroll interrupt
     */
    public void clickTicketHistoryLog() throws InterruptedException {
        scrollToViewElement(pageElements.ticketLogTab);
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketLogTab);
    }

    /**
     * This method use to open check ticket bulk update option displayed or not
     *
     * @param state The state name
     * @return true/false
     * @throws InterruptedException in-case of scroll interrupt
     */
    public Boolean checkBulkUpdateLogged(String state) throws InterruptedException {
        By check = By.xpath(pageElements.readLoggedText + state + pageElements.bulkUpdate);
        scrollToViewElement(check);
        return isVisible(check);
    }


}
