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

    /**
     * This method is use to check send sms page load or not
     *
     * @return true/false
     */
    public boolean isPageLoaded() {
        commonLib.info("Checking is Send SMS Page Loaded");
        return isEnabled(pageElements.sendSMSTitle);
    }

    /**
     * This method use to get customer number
     *
     * @return String The value
     */
    public String getCustomerNumber() {
        waitTillLoaderGetsRemoved();
        String text = getText(pageElements.customerNumberText);
        commonLib.info("Reading Customer Number: " + text);
        return text.split("-")[0].trim();
    }

    /**
     * This method use to select category
     */
    public void selectCategory() {
        clickWithoutLoader(pageElements.openCategory);
        final String text = getText(pageElements.selectOption1);
        commonLib.info("Searching Category with name: " + text);
        enterText(pageElements.searchCategory, text);
        commonLib.info(TEXT + text);
        clickAndWaitForLoaderToBeRemoved(pageElements.selectOption1);
    }

    /**
     * This method use to select template name and return the template name
     *
     * @return String The template name
     */
    public String selectTemplateName() {
        clickWithoutLoader(pageElements.openTemplates);
        final String text = getText(pageElements.selectOption1);
        commonLib.info(TEXT + text);
        clickAndWaitForLoaderToBeRemoved(pageElements.selectOption1);
        return text;
    }

    /**
     * This method use to select language
     */
    public void selectLanguage() {
        clickWithoutLoader(pageElements.openLanguage);
        commonLib.info(TEXT + getText(pageElements.selectOption1));
        clickAndWaitForLoaderToBeRemoved(pageElements.selectOption1);
    }

    /**
     * This method use to get message content
     *
     * @return String The message content
     */
    public String getMessageContent() {
        final String text = getText(pageElements.messageContent);
        commonLib.info("Get Message Content: " + text);
        return text.trim();
    }

    /**
     * This method use to check customer number field display or not
     *
     * @return true/false
     */
    public boolean isCustomerNumber() {
        final boolean state = isEnabled(pageElements.customerNumber);
        commonLib.info("IS customer number field displayed: " + state);
        return state;
    }

    /**
     * This method use to check category field display or not
     *
     * @return true/false
     */
    public boolean isCategory() {
        final boolean state = isEnabled(pageElements.openCategory);
        commonLib.info("IS category number field displayed: " + state);
        return state;
    }

    /**
     * This method use to check template name field display or not
     *
     * @return true/false
     */
    public boolean isTemplateName() {
        final boolean state = isEnabled(pageElements.openTemplates);
        commonLib.info("IS template name field displayed: " + state);
        return state;
    }

    /**
     * This method use to check language field display or not
     *
     * @return true/false
     */
    public boolean isLanguage() {
        final boolean state = isEnabled(pageElements.openLanguage);
        commonLib.info("IS language field displayed: " + state);
        return state;
    }

    /**
     * This method is use to wait till success message display
     */
    public void waitTillSuccessMessage() {
        commonLib.info("Waiting for SMS Send Success pop up");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pageElements.successMessage));
    }

    /**
     * This method is use to click send sms button and return the status button enable or noy
     *
     * @return true/false
     */
    public boolean clickSendSMSBtn() {
        if (driver.findElement(pageElements.submitBtn).isEnabled()) {
            commonLib.info("Clicking Send button");
            clickWithoutLoader(pageElements.submitBtn);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is use to check send sms button disable or not
     *
     * @return true/false
     */
    public boolean isSendBtnDisabled() {
        return !isEnabled(pageElements.sendBtnDisabled);
    }

    /**
     * This method use to check message content box editable or not
     *
     * @return true/false
     */
    public boolean isMessageContentEditable() {
        return isEnabled(pageElements.messageReadOnly);
    }

    /*
    This Methos id used to get the Send SMS Modal Success/Failure Message
     */
    public String getSMSModalText() {
        String result = "";
        result = getText(pageElements.successMessage);
        return result;
    }

    /*
    This Method is used to get the send SMS Modal Header Text
     */
    public String getSendSMSHeaderText() {
        String result = "";
        result = getText(pageElements.sendSMSHeaderText);
        return result;
    }

}
