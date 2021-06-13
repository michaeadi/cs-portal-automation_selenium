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

    public String getTicketId() {

        final String text = getText(pageElements.ticketIdValue);
        commonLib.info("View Ticket: " + text);
        return text;
    }


    public String selectState(String state) throws InterruptedException {
        log.info("Finding State: " + state);
        scrollToViewElement(pageElements.submitAs);
        clickAndWaitForLoaderToBeRemoved(pageElements.arrowIcon);
        try {
            List<WebElement> list = returnListOfElement(pageElements.allTicketState);
            log.info("List Size: " + list.size());
            for (int i = 1; i <= list.size(); i++) {
                By chooseState = By.xpath("//div[@class='cdk-overlay-pane']//mat-option[" + i + "]//span");
                log.info("State Read: " + getText(chooseState));
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


    public String getStateName() {
        final String text = getText(pageElements.stateName);
        log.info("State: " + text);
        return text;
    }

    public void validateAddedComment(String text) {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allComment);
            for (int i = 1; i <= list.size(); i++) {
                By comment = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]//p");
                commonLib.info(READING_COMMENT + getText(comment) + " Is:" + getText(comment).trim().equalsIgnoreCase(text));
                if (getText(comment).trim().equalsIgnoreCase(text)) {
                    commonLib.pass("Newly added comment found on ticket");
                    return;
                }
            }
            commonLib.fail("Newly added comment does not found on ticket",true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateCommentType(String text) {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allComment);
            for (int i = 1; i <= list.size(); i++) {
                By commentType = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]/td/span/span[1]");
                log.info(READING_COMMENT + getText(commentType) + " Is:" + getText(commentType).trim().equalsIgnoreCase(text));
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

    public void addComment(String comment) throws InterruptedException {
        scrollToViewElement(pageElements.addCommentBox);
        enterText(pageElements.addCommentBox, comment);
        commonLib.info("Adding comment on ticket:" + comment);
    }

    public void clickAddButton() throws InterruptedException {
        scrollToViewElement(pageElements.addBtn);
        clickAndWaitForLoaderToBeRemoved(pageElements.addBtn);
        commonLib.info("Clicking on Add comment button");
    }

    public void openEditCommentBox() {
        log.info("Editing last added comment");
        commonLib.info("Editing last added comment");
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        By lastAddedComment = By.xpath("//table[@class='ng-star-inserted']/tbody//tr[" + list.size() + "]//td[1]//a[1]//img[1]");
        clickAndWaitForLoaderToBeRemoved(lastAddedComment);
    }

    public void clearCommentBox() {
        log.info("Clearing Comment Box");
        clearInputTag(pageElements.addCommentBox);
    }

    public void openDeleteComment() {
        log.info("Delete last added comment");
        commonLib.info("Deleting last added comment");
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        By deleteComment = By.xpath("//table[@class='ng-star-inserted']/tbody//tr[" + list.size() + "]//td[1]//a[2]//img[1]");
        clickAndWaitForLoaderToBeRemoved(deleteComment);
    }

    public void clickContinueButton() {
        log.info("Clicking on Continue button");
        commonLib.info("Clicking on Continue button");
        clickAndWaitForLoaderToBeRemoved(pageElements.continueBtn);
    }

    public boolean isCommentDelete(String text) {
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        for (int i = 1; i <= list.size() - 1; i++) {
            By comment = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]//p");
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

    public void clickBackButton() throws InterruptedException {
        log.info("Clicking Back button");
        scrollToViewElement(pageElements.backButton);
        clickAndWaitForLoaderToBeRemoved(pageElements.backButton);
    }
}
