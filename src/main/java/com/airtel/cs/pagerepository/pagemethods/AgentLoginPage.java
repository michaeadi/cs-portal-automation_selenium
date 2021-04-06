package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.AgentLoginPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class AgentLoginPage extends BasePage {
    AgentLoginPageElements pageElements;

    public AgentLoginPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AgentLoginPageElements.class);
    }


    public boolean checkSkipButton() {
        UtilsMethods.printInfoLog("Checking Agent Login Page SKIP Button");
        return checkState(pageElements.skipBtn);
    }

    public boolean checkSubmitButton() {
        UtilsMethods.printInfoLog("Checking Agent Login Page Submit Button");
        return checkState(pageElements.submitBtn);
    }

    public boolean isQueueLoginPage() {
        UtilsMethods.printInfoLog("Supervisor Login Page");
        return checkState(pageElements.loginQueueTitle);
    }

    public void clickSelectQueue() {
        UtilsMethods.printInfoLog("Selecting Login Queue");
        click(pageElements.selectQueue);
    }

    public void clickSkipBtn() {
        UtilsMethods.printInfoLog("Clicking on SKIP Button");
        click(pageElements.skipBtn);
    }

    public void clickSubmitBtn() {
        UtilsMethods.printInfoLog("Clicking on SUBMIT Button");
        clickOutside();
        click(pageElements.submitBtn);
    }

    public void byQueueName(String queueName) throws InterruptedException {
        log.info("Selecting Queue : " + queueName);
        selectByText(queueName);
    }

    public void selectAllQueue() {
        UtilsMethods.printInfoLog("Selecting ALL Queue");
        click(pageElements.allQueueOption);
    }
}
