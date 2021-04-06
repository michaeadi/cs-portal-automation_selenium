package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.MessageHistoryPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class MessageHistoryPage extends BasePage {

    MessageHistoryPageElements pageElements;
    List<WebElement> list;

    public MessageHistoryPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MessageHistoryPageElements.class);
    }

    public boolean isMessageTypeColumn() {
        boolean state = checkState(pageElements.messageTypeLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isDateSentColumn() {
        boolean state = checkState(pageElements.dateSentLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isTemplateColumn() {
        boolean state = checkState(pageElements.templateLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isMessageLanguageColumn() {
        boolean state = checkState(pageElements.messageLanguageLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isMessageTextColumn() {
        boolean state = checkState(pageElements.messageTextLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isAgentIdColumn() {
        boolean state = checkState(pageElements.agentIdLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isAgentNameColumn() {
        boolean state = checkState(pageElements.agentNameLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isActionColumn() {
        boolean state = checkState(pageElements.actionLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public int getListSize(){
        list = returnListOfElement(pageElements.listOfMessage);
        return getListSize();
    }

    public String messageType(int i) {
        if (i <= getListSize()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[1]//b"));
            UtilsMethods.printInfoLog("Message Type: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String sentDate(int i) {
        if (i <= getListSize()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[2]//p//span[@class=\"date_time\"]"));
            UtilsMethods.printInfoLog("Sent Date: " + type);
            return type;
        }
        return "No Message Found";
    }


    public String templateEvent(int i) {
        if (i <= getListSize()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[3]//p//b"));
            UtilsMethods.printInfoLog("Template/Event Name: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String messageLanguage(int i) {
        if (i <= getListSize()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[4]//p//b"));
            UtilsMethods.printInfoLog("Message language: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String messageText(int i) {
        if (i <= getListSize()) {
            String type = driver.findElement(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[5]//p//b")).getAttribute("title");
            UtilsMethods.printInfoLog("Message Text: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String agentId(int i) {
        if (i <= getListSize()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[6]//p//b"));
            UtilsMethods.printInfoLog("Agent Id: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String agentName(int i) {
        if (i <= getListSize()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[7]//p//span"));
            UtilsMethods.printInfoLog("Agent Name: " + type);
            return type;
        }
        return "No Message Found";
    }

    public boolean isActionBtnEnable(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[8]//img");
            return checkState(actionEnable);
        }
        return false;
    }

    public boolean isActionBtnDisable(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[8]//img[@class=\"disabled-icon\"]");
            return checkState(actionEnable);
        }
        return false;
    }

    public void clickActionBtn(int i) {
        if (i <= getListSize()) {
            By actionEnable = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[8]//img");
            click(actionEnable);
        }
    }

    public String getPopUpTitle() {
        UtilsMethods.printInfoLog("Reading pop title: " + readText(pageElements.popUpTitle));
        return readText(pageElements.popUpTitle);
    }

    public String getPopUpMessage() {
        UtilsMethods.printInfoLog("Reading pop message: " + readText(pageElements.popUpMessage));
        return readText(pageElements.popUpMessage);
    }

    public void clickYesBtn() {
        UtilsMethods.printInfoLog("Clicking on 'Yes' Button");
        click(pageElements.yesBtn);
    }

    public void clickNoBtn() {
        UtilsMethods.printInfoLog("Clicking on 'NO' Button");
        click(pageElements.noBtn);
    }


}

