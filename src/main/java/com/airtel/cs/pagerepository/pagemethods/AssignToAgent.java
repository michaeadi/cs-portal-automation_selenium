package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AssignToAgentPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class AssignToAgent extends BasePage {

    AssignToAgentPage pageElements;

    public AssignToAgent(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AssignToAgentPage.class);
    }

    public boolean validatePageTitle() {
        commonLib.info("Validating Assign to agent Page");
        return checkState(pageElements.pageTitle);
    }

    public String getQueueName() {
        final String text = getText(pageElements.queueName);
        log.info("Queue Name: " + text);
        return text;
    }

    public String getAgentName() {
        final String text = getText(pageElements.agentName);
        commonLib.info("Assigning Ticket to Agent Name: " + text);
        return text;
    }

    public String getAgentAuuid() {
        final String text = getText(pageElements.agentAuuid);
        commonLib.info("Assigning Ticket to Agent AUUID: " + text);
        return text;
    }

    public int getAvailableSlot(By element) {
        final String text = getText(element);
        log.info("Agent Available Slot: " + text);
        return Integer.parseInt(text);
    }

    public String getAssignedSlot() {
        final String text = getText(pageElements.assignedSlot);
        log.info("Agent Assigned Slot: " + text);
        return text;
    }

    public void closeAssignTab() {
        commonLib.info("Clicking on Close Assign Button");
        click(pageElements.closeTab);
    }

    public String getInfoMessage() {
        final String text = getText(pageElements.infoMessage);
        log.info("Reading Info Message: " + text);
        return text;
    }

    public String ticketAssignedToAgent(String assigneeAUUID) throws InterruptedException {
        int slot;
        By list = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div");
        List<WebElement> agentList = returnListOfElement(list);
        for (int i = 1; i <= agentList.size(); i++) {
            By agentAUUID = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]//span[@class=\"auuid yellow\"]");
            String auuid = getText(agentAUUID);
            log.info("Agent AUUID: " + getText(agentAUUID));
            log.info("Check state: " + getText(agentAUUID).contains(assigneeAUUID));
            if (!getText(agentAUUID).contains(assigneeAUUID)) {
                By allSlot = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]//span[@class=\"slot-count orange\"]");
                log.info(getText(allSlot));
                try {
                    slot = Integer.parseInt(getText(allSlot));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    slot = 0;
                }
                if (slot > 0) {
                    commonLib.info("Found Agent with Available Slot");
                    By clickAssignBtn = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]/div[4]/img[1]");
                    scrollToViewElement(clickAssignBtn);
                    click(clickAssignBtn);
                    commonLib.info("Click on Assign to Agent Button");
                    return auuid.split("-")[1];
                }
            }
        }
        commonLib.warning("No User have Available Slot");
        closeAssignTab();
        return "No Agent Available";
    }

}
