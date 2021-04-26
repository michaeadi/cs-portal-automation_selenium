package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AgentLoginPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class AgentLogin extends BasePage {
    AgentLoginPage pageElements;

    public AgentLogin(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AgentLoginPage.class);
    }


    public boolean checkSkipButton() {
        commonLib.info("Checking Agent Login Page SKIP Button");
        return checkState(pageElements.skipBtn);
    }

    public boolean checkSubmitButton() {
        commonLib.info("Checking Agent Login Page Submit Button");
        return checkState(pageElements.submitBtn);
    }

    public boolean isQueueLoginPage() {
        commonLib.info("Supervisor Login Page");
        return checkState(pageElements.loginQueueTitle);
    }

    public void clickSelectQueue() {
        commonLib.info("Selecting Login Queue");
        click(pageElements.selectQueue);
    }

    public void clickSkipBtn() {
        commonLib.info("Clicking on SKIP Button");
        click(pageElements.skipBtn);
    }

    public void clickSubmitBtn() {
        commonLib.info("Clicking on SUBMIT Button");
        clickOutside();
        click(pageElements.submitBtn);
    }

    public void byQueueName(String queueName) throws InterruptedException {
        log.info("Selecting Queue : " + queueName);
        selectByText(queueName);
    }

    public void selectAllQueue() {
        commonLib.info("Selecting ALL Queue");
        click(pageElements.allQueueOption);
    }
}
