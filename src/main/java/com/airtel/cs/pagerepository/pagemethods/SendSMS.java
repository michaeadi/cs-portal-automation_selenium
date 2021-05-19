package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.pagerepository.pageelements.SendSMSPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SendSMS extends BasePage {

    SendSMSPage pageElements;
    private static final String TEXT = "Selecting Category with name: ";

    public SendSMS(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SendSMSPage.class);
    }

    public boolean isPageLoaded() {
        commonLib.info("Checking is Send SMS Page Loaded");
        return isEnabled(pageElements.sendSMSTitle);
    }

    public String getCustomerNumber() {
        String text = getText(pageElements.customerNumberText);
        commonLib.info("Reading Customer Number: " + text);
        return text.split("-")[0].trim();
    }

    public void selectCategory() {
        clickAndWaitForLoaderToBeRemoved(pageElements.openCategory);
        final String text = getText(pageElements.selectOption1);
        commonLib.info("Searching Category with name: " + text);
        enterText(pageElements.searchCategory, text);
        commonLib.info(TEXT + text);
        clickAndWaitForLoaderToBeRemoved(pageElements.selectOption1);
    }

    public String selectTemplateName() {
        clickAndWaitForLoaderToBeRemoved(pageElements.openTemplates);
        final String text = getText(pageElements.selectOption1);
        commonLib.info(TEXT + text);
        clickAndWaitForLoaderToBeRemoved(pageElements.selectOption1);
        return text;
    }

    public void selectLanguage() {
        clickAndWaitForLoaderToBeRemoved(pageElements.openLanguage);
        commonLib.info(TEXT + getText(pageElements.selectOption1));
        clickAndWaitForLoaderToBeRemoved(pageElements.selectOption1);
    }

    public String getMessageContent() {
        final String text = getText(pageElements.messageContent);
        commonLib.info("Get Message Content: " + text);
        return text.trim();
    }

    public boolean isCustomerNumber() {
        final boolean state = isEnabled(pageElements.customerNumber);
        commonLib.info("IS customer number field displayed: " + state);
        return state;
    }

    public boolean isCategory() {
        final boolean state = isEnabled(pageElements.openCategory);
        commonLib.info("IS category number field displayed: " + state);
        return state;
    }

    public boolean isTemplateName() {
        final boolean state = isEnabled(pageElements.openTemplates);
        commonLib.info("IS template name field displayed: " + state);
        return state;
    }

    public boolean isLanguage() {
        final boolean state = isEnabled(pageElements.openLanguage);
        commonLib.info("IS language field displayed: " + state);
        return state;
    }

    public void waitTillSuccessMessage() {
        log.info("Waiting for SMS Send Success pop up");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pageElements.successMessage));
    }

    public boolean clickSendSMSBtn() {
        log.info("Checking Send button");
        if (driver.findElement(pageElements.submitBtn).isEnabled()) {
            commonLib.info("Clicking Send button");
            clickAndWaitForLoaderToBeRemoved(pageElements.submitBtn);
            return true;
        } else {
            return false;
        }
    }

    public boolean isSendBtnDisabled() {
        return !isEnabled(pageElements.sendBtnDisabled);
    }

    public boolean isMessageContentEditable() {
        return isEnabled(pageElements.messageReadOnly);
    }

}
