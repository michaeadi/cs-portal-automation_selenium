package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class ViewTicketPagePOM extends BasePage {

    By ticketIdValue = By.xpath("//span[@class='blueColor ellipsis']");
    By arrowIcon = By.xpath("//div[@class='mat-form-field-infix']//div[@class='mat-select-arrow-wrapper']");
    By submitAs = By.className("submit-btn");
    By stateName = By.xpath("//button[@class='sbmit-colse-btn']//span[2]");
    By addCommentBox=By.xpath("//textarea[@placeholder='Add Comment...']");
    By addBtn=By.xpath("//button[@class='add-button']//span");
    By allComment=By.xpath("//table[@class='ng-star-inserted']/tbody/tr");
    By allTicketState=By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    By continueBtn=By.xpath("//span[contains(text(),'continue')]");
    By cancelBtn=By.xpath("//button[@class='no-btn mat-button']");

    public ViewTicketPagePOM(WebDriver driver) {
        super(driver);
    }

    public String getTicketId() {

        log.info("View Ticket: " + readText(ticketIdValue));
        ExtentTestManager.getTest().log(LogStatus.INFO, "View Ticket: " + readText(ticketIdValue));
        return readText(ticketIdValue);
    }

//    public String selectState(String state) throws InterruptedException {
//        log.info("Selecting State: " + state);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting State: " + state);
//        //By stateName= By.xpath("//div[@class=\"ng-tns-c9-325 ng-trigger ng-trigger-transformPanel mat-select-panel mat-primary\"]//span[contains(text(),' "+state+"')]");
//        //By stateName=By.xpath("//span[contains(text(),' "+state+"')]");
//        scrollToViewElement(submitAs);
//        String selectedState = readText(stateName);
//        //click(selectState);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting State: " + selectedState);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Submit as " + selectedState);
//        click(submitAs);
//        return selectedState;
//    }

    public String selectState(String state) throws InterruptedException {
        log.info("Finding State: " + state);
        scrollToViewElement(submitAs);
        click(arrowIcon);
        try{
            List<WebElement> list= driver.findElements(allTicketState);
            System.out.println("List Size: "+list.size());
            for(int i=1;i<=list.size();i++){
                By chooseState=By.xpath("//div[@class='cdk-overlay-pane']//mat-option["+i+"]//span");
                System.out.println("State Read: "+readText(chooseState));
                if(state.equalsIgnoreCase(readText(chooseState).trim())){
                    log.info("Clicking State: " + state);
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting State: " + state);
                    click(chooseState);
                    String selectedState = readText(stateName);
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Submit as " + selectedState);
                    click(submitAs);
                    return selectedState;
                }
            }
            log.info(state+" State does not mapped to ticket");
            ExtentTestManager.getTest().log(LogStatus.WARNING, state+" State does not mapped to ticket");
            return "Required State not found";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("No State does not mapped to ticket");
            ExtentTestManager.getTest().log(LogStatus.FAIL, "No State does not mapped to ticket");
        }
        return "Required State not found";
    }


    public String getStatename() {
        log.info("State: " + readText(stateName));
        return readText(stateName);
    }

    public void validateAddedComment(String text){
        try{
        List<WebElement> list=driver.findElements(allComment);
        for(int i=1;i<=list.size();i++){
            By comment=By.xpath("//table[@class='ng-star-inserted']//tbody//tr["+i+"]//p");
            System.out.println("Reading Comment:"+readText(comment)+" Is:"+readText(comment).trim().equalsIgnoreCase(text));
            if(readText(comment).trim().equalsIgnoreCase(text)) {
                log.info("Latest comment found on ticket: " + comment);
                ExtentTestManager.getTest().log(LogStatus.PASS,"Newly added comment found on ticket");
                return ;
            }
        }
        ExtentTestManager.getTest().log(LogStatus.WARNING,"Newly added comment does not found on ticket");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateCommentType(String text){
        try{
            List<WebElement> list=driver.findElements(allComment);
            for(int i=1;i<=list.size();i++){
                By commentType=By.xpath("//table[@class='ng-star-inserted']//tbody//tr["+i+"]/td/span/span[1]");
                System.out.println("Reading Comment:"+readText(commentType)+" Is:"+readText(commentType).trim().equalsIgnoreCase(text));
                if(readText(commentType).trim().equalsIgnoreCase(text)) {
                    log.info("Comment type found on ticket: " + readText(commentType));
                    ExtentTestManager.getTest().log(LogStatus.PASS,"Comment type found on ticket: " + readText(commentType));
                    return true;
                }
            }
            ExtentTestManager.getTest().log(LogStatus.WARNING,"No Issue Comment type found on ticket: " +text);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.INFO,e.fillInStackTrace());
            return false;
        }
    }

    public void addComment(String comment) throws InterruptedException {
        log.info("Adding comment on ticket:"+comment);
        scrollToViewElement(addCommentBox);
        writeText(addCommentBox,comment);
        ExtentTestManager.getTest().log(LogStatus.INFO,"Adding comment on ticket:"+comment);
    }

    public void clickAddButton() throws InterruptedException {
        log.info("Clicking on Add comment button");
        scrollToViewElement(addBtn);
        click(addBtn);
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on Add comment button");
    }

    public void openEditCommentBox(){
        log.info("Editing last added comment");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Editing last added comment");
        List<WebElement> list=driver.findElements(allComment);
        By lastAddedComment=By.xpath("//table[@class='ng-star-inserted']/tbody//tr["+list.size()+"]//td[1]//a[1]//img[1]");
        click(lastAddedComment);
    }

    public void clearCommentBox(){
        log.info("Clearing Comment Box");
        clearInputTag(addCommentBox);
    }

    public void openDeleteComment(){
        log.info("Delete last added comment");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Deleting last added comment");
        List<WebElement> list=driver.findElements(allComment);
        By deleteComment=By.xpath("//table[@class='ng-star-inserted']/tbody//tr["+list.size()+"]//td[1]//a[2]//img[1]");
        click(deleteComment);
    }

    public void clickContinueButton(){
        log.info("Clicking on Continue button");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on Continue button");
        click(continueBtn);
    }

    public boolean isCommentDelete(String text){
            List<WebElement> list=driver.findElements(allComment);
            for(int i=1;i<=list.size()-1;i++){
                By comment=By.xpath("//table[@class='ng-star-inserted']//tbody//tr["+i+"]//p");
                System.out.println("Reading Comment:"+readText(comment)+" Is:"+readText(comment).trim().equalsIgnoreCase(text));
                if(readText(comment).trim().equalsIgnoreCase(text)) {
                    log.info("Latest comment found on ticket: " + comment);
                    ExtentTestManager.getTest().log(LogStatus.FAIL,"Deleted comment found on ticket");
                    return false;
                }
            }
            ExtentTestManager.getTest().log(LogStatus.PASS,"Deleted comment not found on ticket");
            return true;
    }


}
