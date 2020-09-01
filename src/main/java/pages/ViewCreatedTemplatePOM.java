package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ViewCreatedTemplatePOM extends BasePage{

    By template= By.xpath("//label[contains(text(),'Templates')]");
    By searchKeyWord=By.xpath("//input[@placeholder='Search By Name']");
    By agentChannel=By.xpath("//mat-label[contains(text(),'Agents Channel')]//ancestor::div[1]");
    By roles=By.xpath("//mat-label[contains(text(),'Roles')]//ancestor::div[1]");
    By language=By.xpath("//mat-label[contains(text(),'Language')]//ancestor::div[1]");
    By allTemplate=By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"]");
    By searchIcon=By.xpath("//img[@class='search-icn']");
    By allOption=By.xpath("//span[contains(text(),'Select All')]");
    By options=By.xpath("//mat-option[@role=\"option\"]");

    public ViewCreatedTemplatePOM(WebDriver driver) {
        super(driver);
    }

    public boolean isViewCreatedTemplate(){
        log.info("Checking View Created Template Load or Not");
        return isElementVisible(template);
    }

    public void clickAgentChannel(){
        log.info("Clicking on Agent Channel list");
        click(agentChannel);
    }

    public void clickRoles(){
        log.info("Clicking on Roles list");
        click(roles);
    }

    public void clickLanguage(){
        log.info("Clicking on Language list");
        click(language);
    }

    public void WriteSearchKeyword(String text){
        ExtentTestManager.getTest().log(LogStatus.INFO,"Search By Agent Name: "+text);
        writeText(searchKeyWord,text);
    }

    public boolean isSearchByNameAvailable(){
        log.info("Checking Search by template name field Load or Not");
        return isElementVisible(searchKeyWord);
    }

    public boolean isTemplatePresent(String text){
        log.info("Checking Template Present With Name: "+text);
        ExtentTestManager.getTest().log(LogStatus.INFO,"Checking Template Present With Name: "+text);
        List<WebElement> list=driver.findElements(allTemplate);
        for(int i=1;i<=list.size();i++){
            By element=By.xpath("//div[@class=\"sms-managment__card-list--card--sms-template--content--sms-card ng-star-inserted\"]["+i+"]//h6");
            log.info("Reading Template: "+readText(element));
            if(text.equalsIgnoreCase(readText(element).trim())){
                return true;
            }
        }
        return false;
    }

    public void clickSearchIcon(){
        log.info("Clicking on Search Icon");
        click(searchIcon);
    }

    public void selectALLOption(){
        log.info("Clicking on All Select Option");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on All Select Option");
        click(allOption);
    }

    public ArrayList<String> getAllOptions() {
        List<WebElement> listOfElements = driver.findElements(options);
        System.out.println("List Size: "+listOfElements.size());
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < listOfElements.size(); i++) {
            try {
                ExtentTestManager.getTest().log(LogStatus.INFO,"Reading : "+listOfElements.get(i).getText().toLowerCase().trim());
                strings.add(listOfElements.get(i).getText().toLowerCase().trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                break;
            }
        }
        return strings;
    }

}
