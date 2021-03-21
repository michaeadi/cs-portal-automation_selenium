package pages;

import Utils.ExtentReports.ExtentTestManager;
import Utils.UtilsMethods;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;

import java.util.List;

@Log4j2
public class ViewTicketPagePOM extends BasePage {

    By ticketIdValue = By.xpath("//span[@class='blueColor ellipsis']");
    By arrowIcon = By.xpath("//div[@class='mat-form-field-infix']//div[@class='mat-select-arrow-wrapper']");
    By submitAs = By.className("submit-btn");
    By stateName = By.xpath("//button[@class='sbmit-colse-btn']//span[2]");
    By addCommentBox = By.xpath("//textarea[@placeholder='Add Comment...']");
    By addBtn = By.xpath("//button[@class='add-button']//span");
    By allComment = By.xpath("//table[@class='ng-star-inserted']/tbody/tr");
    By allTicketState = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    By continueBtn = By.xpath("//span[contains(text(),'continue')]");
    By cancelBtn = By.xpath("//button[@class='no-btn mat-button']");
    By backButton = By.xpath("//button[@class=\"back mat-button\"]");

    public ViewTicketPagePOM(WebDriver driver) {
        super(driver);
    }

    public String getTicketId() {

        UtilsMethods.printInfoLog("View Ticket: " + readText(ticketIdValue));
        return readText(ticketIdValue);
    }

//    public String selectState(String state) throws InterruptedException {
//        log.info("Selecting State: " + state);
//        UtilsMethods.printInfoLog("Selecting State: " + state);
//        //By stateName= By.xpath("//div[@class=\"ng-tns-c9-325 ng-trigger ng-trigger-transformPanel mat-select-panel mat-primary\"]//span[contains(text(),' "+state+"')]");
//        //By stateName=By.xpath("//span[contains(text(),' "+state+"')]");
//        scrollToViewElement(submitAs);
//        String selectedState = readText(stateName);
//        //click(selectState);
//        UtilsMethods.printInfoLog("Selecting State: " + selectedState);
//        UtilsMethods.printInfoLog("Clicking on Submit as " + selectedState);
//        click(submitAs);
//        return selectedState;
//    }

    public String selectState(String state) throws InterruptedException {
        log.info("Finding State: " + state);
        scrollToViewElement(submitAs);
        click(arrowIcon);
        try {
            List<WebElement> list = returnListOfElement(allTicketState);
            log.info("List Size: " + list.size());
            for (int i = 1; i <= list.size(); i++) {
                By chooseState = By.xpath("//div[@class='cdk-overlay-pane']//mat-option[" + i + "]//span");
                log.info("State Read: " + readText(chooseState));
                if (state.equalsIgnoreCase(readText(chooseState).trim())) {
                    UtilsMethods.printInfoLog("Selecting State: " + state);
                    click(chooseState);
                    String selectedState = readText(stateName);
                    UtilsMethods.printInfoLog("Clicking on Submit as " + selectedState);
                    click(submitAs);
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


    public String getStatename() {
        log.info("State: " + readText(stateName));
        return readText(stateName);
    }

    public void validateAddedComment(String text) {
        try {
            List<WebElement> list = returnListOfElement(allComment);
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
            List<WebElement> list = returnListOfElement(allComment);
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
        scrollToViewElement(addCommentBox);
        writeText(addCommentBox, comment);
        UtilsMethods.printInfoLog("Adding comment on ticket:" + comment);
    }

    public void clickAddButton() throws InterruptedException {
        log.info("Clicking on Add comment button");
        scrollToViewElement(addBtn);
        click(addBtn);
        UtilsMethods.printInfoLog("Clicking on Add comment button");
    }

    public void openEditCommentBox() {
        log.info("Editing last added comment");
        UtilsMethods.printInfoLog("Editing last added comment");
        List<WebElement> list = returnListOfElement(allComment);
        By lastAddedComment = By.xpath("//table[@class='ng-star-inserted']/tbody//tr[" + list.size() + "]//td[1]//a[1]//img[1]");
        click(lastAddedComment);
    }

    public void clearCommentBox() {
        log.info("Clearing Comment Box");
        clearInputTag(addCommentBox);
    }

    public void openDeleteComment() {
        log.info("Delete last added comment");
        UtilsMethods.printInfoLog("Deleting last added comment");
        List<WebElement> list = returnListOfElement(allComment);
        By deleteComment = By.xpath("//table[@class='ng-star-inserted']/tbody//tr[" + list.size() + "]//td[1]//a[2]//img[1]");
        click(deleteComment);
    }

    public void clickContinueButton() {
        log.info("Clicking on Continue button");
        UtilsMethods.printInfoLog("Clicking on Continue button");
        click(continueBtn);
    }

    public boolean isCommentDelete(String text) {
        List<WebElement> list = returnListOfElement(allComment);
        for (int i = 1; i <= list.size() - 1; i++) {
            By comment = By.xpath("//table[@class='ng-star-inserted']//tbody//tr[" + i + "]//p");
            System.out.println("Reading Comment:" + readText(comment) + " Is:" + readText(comment).trim().equalsIgnoreCase(text));
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
        scrollToViewElement(backButton);
        click(backButton);
    }

}
