package pages;


import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class SendSMSPOM extends BasePage {

    By sendSMSTitle= By.xpath("//label[contains(text(),'Send Message')]");
    By customerNumber=By.xpath("//div[@class='send-managment__card-list--card--content-area--option-section']//span[@class='ng-tns-c9-24 ng-star-inserted']");
    By openCategory=By.xpath("//mat-label[contains(text(),'Enter Category')]");
    By openTemplates=By.xpath("//span[contains(text(),'Enter Template Name')]");
    By openLanguage=By.xpath("//span[contains(text(),'Enter Language')]");
    By selectOption1=By.xpath("//mat-option[1]");
    By messageContent=By.xpath("//textarea[@formcontrolname=\"messageContent\"]");

    public SendSMSPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded(){
        log.info("Checking is Send SMS page loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Checking is Send SMS Page Loaded");
        return checkState(sendSMSTitle);
    }

    public String getCustomerNumber(){
        String text=readText(customerNumber);
        log.info("Reading Customer Number: "+text);
        ExtentTestManager.getTest().log(LogStatus.INFO,"Reading Customer Number: "+text);
        return text.split("-")[0].trim();
    }

    public void selectCategory(){
        click(openCategory);
        log.info("Selecting Category with name: "+readText(selectOption1));
        ExtentTestManager.getTest().log(LogStatus.INFO,"Selecting Category with name: "+readText(selectOption1));
        click(selectOption1);
    }

    public void selectTemplateName(){
        click(openTemplates);
        log.info("Selecting Template with name: "+readText(selectOption1));
        ExtentTestManager.getTest().log(LogStatus.INFO,"Selecting Category with name: "+readText(selectOption1));
        click(selectOption1);
    }

    public void selectLanguage(){
        click(openCategory);
        log.info("Selecting Language with name: "+readText(selectOption1));
        ExtentTestManager.getTest().log(LogStatus.INFO,"Selecting Category with name: "+readText(selectOption1));
        click(selectOption1);
    }

    public String getMessageContent(){
        log.info("Get Message Content: "+readText(messageContent));
        ExtentTestManager.getTest().log(LogStatus.INFO,"Get Message Content: "+readText(messageContent));
        return readText(messageContent).trim();
    }

}
