package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class ViewTicketPagePOM extends BasePage {

    By ticketIdValue = By.xpath("//span[@class='blueColor ellipsis']");
    By selectState = By.xpath("//body//div//div//div//div//div//div//div//div//div//div//div//div//div//div//div//div//div[2]");
    By submitAs = By.className("submit-btn");
    By submitBtn = By.xpath("//button[@class='sbmit-colse-btn']");
    By stateName = By.xpath("//button[@class='sbmit-colse-btn']//span[2]");
    By addCommentBox=By.xpath("//textarea[@name='commentEntered']");
    By addBtn=By.xpath("//span[contains(text(),'ADD')]");
    By allComment=By.xpath("//table[@class='ng-star-inserted']/tbody/tr");

    public ViewTicketPagePOM(WebDriver driver) {
        super(driver);
    }

    public String getTicketId() {

        log.info("View Ticket: " + readText(ticketIdValue));
        ExtentTestManager.getTest().log(LogStatus.INFO, "View Ticket: " + readText(ticketIdValue));
        return readText(ticketIdValue);
    }

    public String selectState(String state) throws InterruptedException {
        log.info("Selecting State: " + state);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting State: " + state);
        //By stateName= By.xpath("//div[@class=\"ng-tns-c9-325 ng-trigger ng-trigger-transformPanel mat-select-panel mat-primary\"]//span[contains(text(),' "+state+"')]");
        //By stateName=By.xpath("//span[contains(text(),' "+state+"')]");
        scrollToViewElement(submitAs);
        String selectedState = readText(stateName);
        //click(selectState);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Selecting State: " + selectedState);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Submit as " + selectedState);
        click(submitAs);
        return selectedState;
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
        ExtentTestManager.getTest().log(LogStatus.PASS,"Newly added comment does not found on ticket");
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

}
