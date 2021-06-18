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


    /**
     * This method is use to check skip button visible or not
     * @return true/false
     */
    public boolean checkSkipButton() {
        commonLib.info("Checking Agent Login Page SKIP Button");
        return isEnabled(pageElements.skipBtn);
    }

    /**
     * This method is use to check submit button visible or not
     * @return true/false
     */
    public boolean checkSubmitButton() {
        commonLib.info("Checking Agent Login Page Submit Button");
        return isEnabled(pageElements.submitBtn);
    }

    /**
     * This method is use to check queue login page displayed or not
     * @return true/false
     */
    public boolean isQueueLoginPage() {
        commonLib.info("Supervisor Login Page");
        return isEnabled(pageElements.loginQueueTitle);
    }

    /**
     * This method used to click open select queue option list
     */
    public void clickSelectQueue() {
        commonLib.info("Selecting Login Queue");
        clickWithoutLoader(pageElements.selectQueue);
    }

    /**
     * This method use to click skip button
     */
    public void clickSkipBtn() {
        commonLib.info("Clicking on SKIP Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.skipBtn);
    }

    /**
     * This method use to click submit  button
     */
    public void clickSubmitBtn() {
        commonLib.info("Clicking on SUBMIT Button");
        clickOutside();
        clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
    }

    /**
     * This method use to select queue based on queue name
     * @param queueName The Queue Name
     * @throws InterruptedException
     */
    public void byQueueName(String queueName) throws InterruptedException {
        commonLib.info("Selecting Queue : " + queueName);
        selectByText(queueName);
    }

    /**
     * This method used to select all queue
     */
    public void selectAllQueue() {
        commonLib.info("Selecting ALL Queue");
        clickWithoutLoader(pageElements.allQueueOption);
    }
}
