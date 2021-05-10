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
    List<WebElement> list;
    private static final String TEXT1 = "Checking Message Type Column Displayed: ";
    private static final String XPATH = "//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[";
    private static final String TEXT = "No Message Found";

    public MessageHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MessageHistoryPage.class);
    }

    public boolean isMessageTypeColumn() {
        boolean state = isEnabled(pageElements.messageTypeLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isDateSentColumn() {
        boolean state = isEnabled(pageElements.dateSentLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isTemplateColumn() {
        boolean state = isEnabled(pageElements.templateLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isMessageLanguageColumn() {
        boolean state = isEnabled(pageElements.messageLanguageLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isMessageTextColumn() {
        boolean state = isEnabled(pageElements.messageTextLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isAgentIdColumn() {
        boolean state = isEnabled(pageElements.agentIdLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isAgentNameColumn() {
        boolean state = isEnabled(pageElements.agentNameLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public boolean isActionColumn() {
        boolean state = isEnabled(pageElements.actionLabel);
        log.info(TEXT1 + state);
        return state;
    }

    public int getListSize() {
        list = returnListOfElement(pageElements.listOfMessage);
        return getListSize();
    }

    public String messageType(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + "]//td[1]//b"));
            commonLib.info("Message Type: " + type);
            return type;
        }
        return TEXT;
    }

    public String sentDate(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + "]//td[2]//p//span[@class=\"date_time\"]"));
            commonLib.info("Sent Date: " + type);
            return type;
        }
        return TEXT;
    }


    public String templateEvent(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + "]//td[3]//p//b"));
            commonLib.info("Template/Event Name: " + type);
            return type;
        }
        return TEXT;
    }

    public String messageLanguage(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + "]//td[4]//p//b"));
            commonLib.info("Message language: " + type);
            return type;
        }
        return TEXT;
    }

    public String messageText(int i) {
        if (i <= getListSize()) {
            String type = driver.findElement(By.xpath(XPATH + i + "]//td[5]//p//b")).getAttribute("title");
            commonLib.info("Message Text: " + type);
            return type;
        }
        return TEXT;
    }

    public String agentId(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + "]//td[6]//p//b"));
            commonLib.info("Agent Id: " + type);
            return type;
        }
        return TEXT;
    }

    public String agentName(int i) {
        if (i <= getListSize()) {
            String type = getText(By.xpath(XPATH + i + "]//td[7]//p//span"));
            commonLib.info("Agent Name: " + type);
            return type;
        }
        return TEXT;
    }

    public boolean isActionBtnEnable(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath(XPATH + i + "]//td[8]//img");
            return isEnabled(actionEnable);
        }
        return false;
    }

    public boolean isActionBtnDisable(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath(XPATH + i + "]//td[8]//img[@class=\"disabled-icon\"]");
            return isEnabled(actionEnable);
        }
        return false;
    }

    public void clickActionBtn(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath(XPATH + i + "]//td[8]//img");
            click(actionEnable);
        }
    }

    public String getPopUpTitle() {
        final String text = getText(pageElements.popUpTitle);
        commonLib.info("Reading pop title: " + text);
        return text;
    }

    public String getPopUpMessage() {
        final String text = getText(pageElements.popUpMessage);
        commonLib.info("Reading pop message: " + text);
        return text;
    }

    public void clickYesBtn() {
        commonLib.info("Clicking on 'Yes' Button");
        click(pageElements.yesBtn);
    }

    public void clickNoBtn() {
        commonLib.info("Clicking on 'NO' Button");
        click(pageElements.noBtn);
    }


}

