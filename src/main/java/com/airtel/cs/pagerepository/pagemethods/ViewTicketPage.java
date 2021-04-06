package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.ViewTicketPageElements;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class ViewTicketPage extends BasePage {

    ViewTicketPageElements pageElements;

    public ViewTicketPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ViewTicketPageElements.class);
    }

    public String getTicketId() {

        UtilsMethods.printInfoLog("View Ticket: " + readText(pageElements.ticketIdValue));
        return readText(pageElements.ticketIdValue);
    }


    public String selectState(String state) throws InterruptedException {
        log.info("Finding State: " + state);
        scrollToViewElement(pageElements.submitAs);
        click(pageElements.arrowIcon);
        try {
            List<WebElement> list = returnListOfElement(pageElements.allTicketState);
            log.info("List Size: " + list.size());
            for (int i = 1; i <= list.size(); i++) {
                By chooseState = By.xpath("//div[@class='cdk-overlay-pane']//mat-option[" + i + "]//span");
                log.info("State Read: " + readText(chooseState));
                if (state.equalsIgnoreCase(readText(chooseState).trim())) {
                    UtilsMethods.printInfoLog("Selecting State: " + state);
                    click(chooseState);
                    String selectedState = readText(pageElements.stateName);
                    UtilsMethods.printInfoLog("Clicking on Submit as " + selectedState);
                    click(pageElements.submitAs);
                    return selectedState;
                }
            }
            UtilsMethods.printFailLog(state + " State does not mapped to ticket");
            return "Required State not found";
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            UtilsMethods.printFailLog("No State does not mapped to ticket " + e.fillInStackTrace());
        }
        return "Required State not found";
    }


    public String getStateName() {
        log.info("State: " + readText(pageElements.stateName));
        return readText(pageElements.stateName);
    }

    public void validateAddedComment(String text) {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allComment);
            for (int i = 1; i <= list.size(); i++) {
                By comment = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]//p");
                log.info("Reading Comment:" + readText(comment) + " Is:" + readText(comment).trim().equalsIgnoreCase(text));
                if (readText(comment).trim().equalsIgnoreCase(text)) {
                    UtilsMethods.printPassLog("Newly added comment found on ticket");
                    return;
                }
            }
            UtilsMethods.printWarningLog("Newly added comment does not found on ticket");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateCommentType(String text) {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allComment);
            for (int i = 1; i <= list.size(); i++) {
                By commentType = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]/td/span/span[1]");
                log.info("Reading Comment:" + readText(commentType) + " Is:" + readText(commentType).trim().equalsIgnoreCase(text));
                if (readText(commentType).trim().equalsIgnoreCase(text)) {
                    UtilsMethods.printPassLog("Comment type found on ticket: " + readText(commentType));
                    return true;
                }
            }
            ExtentTestManager.getTest().log(LogStatus.WARNING, "No Issue Comment type found on ticket: " + text);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            UtilsMethods.printInfoLog("Exception Occurred: " + e.fillInStackTrace());
            return false;
        }
    }

    public void addComment(String comment) throws InterruptedException {
        log.info("Adding comment on ticket:" + comment);
        scrollToViewElement(pageElements.addCommentBox);
        writeText(pageElements.addCommentBox, comment);
        UtilsMethods.printInfoLog("Adding comment on ticket:" + comment);
    }

    public void clickAddButton() throws InterruptedException {
        log.info("Clicking on Add comment button");
        scrollToViewElement(pageElements.addBtn);
        click(pageElements.addBtn);
        UtilsMethods.printInfoLog("Clicking on Add comment button");
    }

    public void openEditCommentBox() {
        log.info("Editing last added comment");
        UtilsMethods.printInfoLog("Editing last added comment");
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        By lastAddedComment = By.xpath("//table[@class='ng-star-inserted']/tbody//tr[" + list.size() + "]//td[1]//a[1]//img[1]");
        click(lastAddedComment);
    }

    public void clearCommentBox() {
        log.info("Clearing Comment Box");
        clearInputTag(pageElements.addCommentBox);
    }

    public void openDeleteComment() {
        log.info("Delete last added comment");
        UtilsMethods.printInfoLog("Deleting last added comment");
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        By deleteComment = By.xpath("//table[@class='ng-star-inserted']/tbody//tr[" + list.size() + "]//td[1]//a[2]//img[1]");
        click(deleteComment);
    }

    public void clickContinueButton() {
        log.info("Clicking on Continue button");
        UtilsMethods.printInfoLog("Clicking on Continue button");
        click(pageElements.continueBtn);
    }

    public boolean isCommentDelete(String text) {
        List<WebElement> list = returnListOfElement(pageElements.allComment);
        for (int i = 1; i <= list.size() - 1; i++) {
            By comment = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]//p");
            log.info("Reading Comment:" + readText(comment) + " Is:" + readText(comment).trim().equalsIgnoreCase(text));
            if (readText(comment).trim().equalsIgnoreCase(text)) {
                log.info("Latest comment found on ticket: " + comment);
                ExtentTestManager.getTest().log(LogStatus.FAIL, "Deleted comment found on ticket");
                return false;
            }
        }
        ExtentTestManager.getTest().log(LogStatus.PASS, "Deleted comment not found on ticket");
        return true;
    }

    public void clickBackButton() throws InterruptedException {
        log.info("Clicking Back button");
        scrollToViewElement(pageElements.backButton);
        click(pageElements.backButton);
    }
}
