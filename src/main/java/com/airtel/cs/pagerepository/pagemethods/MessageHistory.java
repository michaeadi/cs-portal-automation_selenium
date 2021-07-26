package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MessageHistoryPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class MessageHistory extends BasePage {

    MessageHistoryPage pageElements;
    private static final String TEXT1 = "Checking Message Type Column Displayed: ";
    private static final String XPATH = "//table[@id='fetchTicketByCustomer']//tbody/tr[";
    private static final String TEXT = "No Message Found";

    public MessageHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MessageHistoryPage.class);
    }

    /**
     * This method is use to check message type column display or not
     * @return true/false
     */
    public boolean isMessageTypeColumn() {
        boolean state = isEnabled(pageElements.messageTypeLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check message sent date column display or not
     * @return true/false
     */
    public boolean isDateSentColumn() {
        boolean state = isEnabled(pageElements.dateSentLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check template column display or not
     * @return true/false
     */
    public boolean isTemplateColumn() {
        boolean state = isEnabled(pageElements.templateLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check message language column display or not
     * @return true/false
     */
    public boolean isMessageLanguageColumn() {
        boolean state = isEnabled(pageElements.messageLanguageLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check message text column display or not
     * @return true/false
     */
    public boolean isMessageTextColumn() {
        boolean state = isEnabled(pageElements.messageTextLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check agent id column display or not
     * @return true/false
     */
    public boolean isAgentIdColumn() {
        boolean state = isEnabled(pageElements.agentIdLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check agent name column display or not
     * @return true/false
     */
    public boolean isAgentNameColumn() {
        boolean state = isEnabled(pageElements.agentNameLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method is use to check action column display or not
     * @return true/false
     */
    public boolean isActionColumn() {
        boolean state = isEnabled(pageElements.actionLabel);
        commonLib.info(TEXT1 + state);
        return state;
    }

    /**
     * This method use to get total number of message display
     * @return Integer The count
     */
    public int getListSize() {
        final List<WebElement> elementsList = getElementsListFromBy(pageElements.listOfMessage);
        return elementsList.size();
    }

    /**
     * This method use to get message type column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String messageType(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + pageElements.messageTypeValue));
            commonLib.info("Message Type: " + type);
            return type;
        }
        return TEXT;
    }

    /**
     * This method use to get message sent date column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String sentDate(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + pageElements.messageSentDate));
            commonLib.info("Sent Date: " + type);
            return type;
        }
        return TEXT;
    }


    /**
     * This method use to get message template event column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String templateEvent(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + pageElements.templateEvent));
            commonLib.info("Template/Event Name: " + type);
            return type;
        }
        return TEXT;
    }

    /**
     * This method use to get message language column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String messageLanguage(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + pageElements.messageLanguageValue));
            commonLib.info("Message language: " + type);
            return type;
        }
        return TEXT;
    }

    /**
     * This method use to get message text column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String messageText(int i) {
        if (i <= getListSize()) {
            String type = driver.findElement(By.xpath(XPATH + i + pageElements.messageText)).getAttribute("title");
            commonLib.info("Message Text: " + type);
            return type;
        }
        return TEXT;
    }

    /**
     * This method use to get agent id column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String agentId(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + pageElements.agentIdValue));
            commonLib.info("Agent Id: " + type);
            return type;
        }
        return TEXT;
    }

    /**
     * This method use to get agent name column value based on row number
     * @param i The row number
     * @return String The value
     */
    public String agentName(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + pageElements.agentNameValue));
            commonLib.info("Agent Name: " + type);
            return type;
        }
        return TEXT;
    }

    /**
     * This method use check action button enable or not
     * @return true/false
     */
    public boolean isActionBtnEnable(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath(XPATH + i + pageElements.actionBtnEnable);
            return isEnabled(actionEnable);
        }
        return false;
    }

    /**
     * This method use check action button disable or not
     * @return true/false
     */
    public boolean isActionBtnDisable(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath(XPATH + i + pageElements.actionButtonDisable);
            return isEnabled(actionEnable);
        }
        return false;
    }

    /**
     * This method use click action button
     */
    public void clickActionBtn(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath(XPATH + i + pageElements.actionBtnEnable);
            clickAndWaitForLoaderToBeRemoved(actionEnable);
        }
    }

    /**
     * This method is use to get pop up title
     */
    public String getPopUpTitle() {
        final String text = getText(pageElements.popUpTitle);
        commonLib.info("Reading pop title: " + text);
        return text;
    }

    /**
     * This method is use to get pop up message
     * @return String The value
     */
    public String getPopUpMessage() {
        final String text = getText(pageElements.popUpMessage);
        commonLib.info("Reading pop message: " + text);
        return text;
    }

    /**
     * This method is use to click yes btn over pop up tab
     */
    public void clickYesBtn() {
        commonLib.info("Clicking on 'Yes' Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.yesBtn);
        waitTillLoaderGetsRemoved();
    }

    /**
     * This method is use to click no btn over pop up tab
     */
    public void clickNoBtn() {
        commonLib.info("Clicking on 'NO' Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.noBtn);
    }


}

