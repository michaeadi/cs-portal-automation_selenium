package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class agentLoginPagePOM extends BasePage {

    By loginQueueTitle = By.xpath("//h2[contains(text(),'Login Queue')]");
    By selectQueue = By.xpath("//span[contains(text(),'Select Sub Workgroup (Queue)')]");
    By skipBtn = By.xpath("//span[contains(text(),'Skip > >')]");
    By submitBtn = By.xpath("//span[contains(text(),'Submit')]");
    By allQueueOption = By.xpath("//body/div/div/div/div/div/app-select-all/mat-checkbox/label/span[1]");


    public agentLoginPagePOM(WebDriver driver) {
        super(driver);
    }


    public boolean checkSkipButton() {
        UtilsMethods.printInfoLog("Checking Agent Login Page SKIP Button");
        return checkState(skipBtn);
    }

    public boolean checkSubmitButton() {
        UtilsMethods.printInfoLog("Checking Agent Login Page Submit Button");
        return checkState(submitBtn);
    }

    public boolean isQueueLoginPage() {
        UtilsMethods.printInfoLog("Supervisor Login Page");
        return checkState(loginQueueTitle);
    }

    public void clickSelectQueue() {
        UtilsMethods.printInfoLog("Selecting Login Queue");
        click(selectQueue);
    }

    public void clickSkipBtn() {
        UtilsMethods.printInfoLog("Clicking on SKIP Button");
        click(skipBtn);
    }

    public void clickSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking on SUBMIT Button");
        clickOutside();
        click(submitBtn);
    }

    public void byQueueName(String queueName) throws InterruptedException {
        log.info("Selecting Queue : " + queueName);
        selectByText(queueName);
    }

    public void selectAllQueue() {
        UtilsMethods.printInfoLog("Selecting ALL Queue");
        click(allQueueOption);
    }
}
