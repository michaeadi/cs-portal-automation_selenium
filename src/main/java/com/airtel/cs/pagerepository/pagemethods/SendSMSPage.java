package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.SendSMSPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class SendSMSPage extends BasePage {

    SendSMSPageElements pageElements;

    public SendSMSPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, SendSMSPageElements.class);
    }

    public boolean isPageLoaded() {
        UtilsMethods.printInfoLog("Checking is Send SMS Page Loaded");
        return checkState(pageElements.sendSMSTitle);
    }

    public String getCustomerNumber() {
        String text = getText(pageElements.customerNumberText);
        UtilsMethods.printInfoLog("Reading Customer Number: " + text);
        return text.split("-")[0].trim();
    }

    public void selectCategory() {
        click(pageElements.openCategory);
        UtilsMethods.printInfoLog("Searching Category with name: " + getText(pageElements.selectOption1));
        writeText(pageElements.searchCategory, getText(pageElements.selectOption1));
        UtilsMethods.printInfoLog("Selecting Category with name: " + getText(pageElements.selectOption1));
        click(pageElements.selectOption1);
    }

    public String selectTemplateName() {
        click(pageElements.openTemplates);
        UtilsMethods.printInfoLog("Selecting Category with name: " + getText(pageElements.selectOption1));
        String text = getText(pageElements.selectOption1);
        click(pageElements.selectOption1);
        return text;
    }

    public void selectLanguage() {
        click(pageElements.openLanguage);
        UtilsMethods.printInfoLog("Selecting Category with name: " + getText(pageElements.selectOption1));
        click(pageElements.selectOption1);
    }

    public String getMessageContent() {
        UtilsMethods.printInfoLog("Get Message Content: " + getText(pageElements.messageContent));
        return getText(pageElements.messageContent).trim();
    }

    public boolean isCustomerNumber() {
        UtilsMethods.printInfoLog("IS customer number field displayed: " + checkState(pageElements.customerNumber));
        return checkState(pageElements.customerNumber);
    }

    public boolean isCategory() {
        UtilsMethods.printInfoLog("IS category number field displayed: " + checkState(pageElements.openCategory));
        return checkState(pageElements.openCategory);
    }

    public boolean isTemplateName() {
        UtilsMethods.printInfoLog("IS template name field displayed: " + checkState(pageElements.openTemplates));
        return checkState(pageElements.openTemplates);
    }

    public boolean isLanguage() {
        UtilsMethods.printInfoLog("IS language field displayed: " + checkState(pageElements.openLanguage));
        return checkState(pageElements.openLanguage);
    }

    public void waitTillSuccessMessage() {
        log.info("Waiting for SMS Send Success pop up");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pageElements.successMessage));
    }

    public boolean clickSendSMSBtn() {
        log.info("Checking Send button");
        if (driver.findElement(pageElements.submitBtn).isEnabled()) {
            UtilsMethods.printInfoLog("Clicking Send button");
            click(pageElements.submitBtn);
            return true;
        } else {
            return false;
        }
    }

    public boolean isSendBtnDisabled() {
        return !checkState(pageElements.sendBtnDisabled);
    }

    public boolean isMessageContentEditable() {
        return checkState(pageElements.messageReadOnly);
    }

}
