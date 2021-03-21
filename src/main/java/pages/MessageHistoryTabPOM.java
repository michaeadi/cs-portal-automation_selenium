package pages;

import Utils.UtilsMethods;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class MessageHistoryTabPOM extends BasePage {

    By messageTypeLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[1]//b");
    By dateSentLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[2]//b");
    By templateLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[3]//b");
    By messageLanguageLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[4]//b");
    By messageTextLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[5]//b");
    By agentIdLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[6]//b");
    By agentNameLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[7]//b");
    By actionLabel = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//thead/tr[1]/th[8]//b");
    By listOfMessage = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr");
    List<WebElement> list = returnListOfElement(listOfMessage);
    By popUpTitle = By.xpath("//h1[contains(text(),'Resend Message')]");
    By popUpMessage = By.xpath("//p[@class=\"error\"]");
    By yesBtn = By.xpath("//div[@class=\"deactivate-popup__content mat-dialog-content\"]//button[2]");
    By noBtn = By.xpath("//div[@class=\"deactivate-popup__content mat-dialog-content\"]//button[1]");
    public MessageHistoryTabPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isMessageTypeColumn() {
        boolean state = checkState(messageTypeLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isDateSentColumn() {
        boolean state = checkState(dateSentLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isTemplateColumn() {
        boolean state = checkState(templateLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isMessageLanguageColumn() {
        boolean state = checkState(messageLanguageLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isMessageTextColumn() {
        boolean state = checkState(messageTextLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isAgentIdColumn() {
        boolean state = checkState(agentIdLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isAgentNameColumn() {
        boolean state = checkState(agentNameLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public boolean isActionColumn() {
        boolean state = checkState(actionLabel);
        log.info("Checking Message Type Column Displayed: " + state);
        return state;
    }

    public String messageType(int i) {
        if (i <= list.size()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[1]//b"));
            UtilsMethods.printInfoLog("Message Type: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String sentDate(int i) {
        if (i <= list.size()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[2]//p//span[@class=\"date_time\"]"));
            UtilsMethods.printInfoLog("Sent Date: " + type);
            return type;
        }
        return "No Message Found";
    }


    public String templateEvent(int i) {
        if (i <= list.size()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[3]//p//b"));
            UtilsMethods.printInfoLog("Template/Event Name: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String messageLanguage(int i) {
        if (i <= list.size()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[4]//p//b"));
            UtilsMethods.printInfoLog("Message language: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String messageText(int i) {
        if (i <= list.size()) {
            String type = driver.findElement(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[5]//p//b")).getAttribute("title");
            UtilsMethods.printInfoLog("Message Text: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String agentId(int i) {
        if (i <= list.size()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[6]//p//b"));
            UtilsMethods.printInfoLog("Agent Id: " + type);
            return type;
        }
        return "No Message Found";
    }

    public String agentName(int i) {
        if (i <= list.size()) {
            String type = readText(By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[7]//p//span"));
            UtilsMethods.printInfoLog("Agent Name: " + type);
            return type;
        }
        return "No Message Found";
    }

    public boolean isActionBtnEnable(int i) {
        if (i <= list.size()) {
            By actionEnable = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[8]//img");
            return checkState(actionEnable);
        }
        return false;
    }

    public boolean isActionBtnDisable(int i) {
        if (i <= list.size()) {
            By actionEnable = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[8]//img[@class=\"disabled-icon\"]");
            return checkState(actionEnable);
        }
        return false;
    }

    public void clickActionBtn(int i) {
        if (i <= list.size()) {
            By actionEnable = By.xpath("//table[@id=\"fetchTicketByCustomer\"]//tbody/tr[" + i + "]//td[8]//img");
            click(actionEnable);
        }
    }

    public String getPopUpTitle() {
        UtilsMethods.printInfoLog("Reading pop title: " + readText(popUpTitle));
        return readText(popUpTitle);
    }

    public String getPopUpMessage() {
        UtilsMethods.printInfoLog("Reading pop message: " + readText(popUpMessage));
        return readText(popUpMessage);
    }

    public void clickYesBtn() {
        UtilsMethods.printInfoLog("Clicking on 'Yes' Button");
        click(yesBtn);
    }

    public void clickNoBtn() {
        UtilsMethods.printInfoLog("Clicking on 'NO' Button");
        click(noBtn);
    }


}

