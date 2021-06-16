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

    /**
     * This method is used to check validate page title displayed
     * @return true/false
     */
    public boolean validatePageTitle() {
        commonLib.info("Validating Assign to agent Page");
        return isEnabled(pageElements.pageTitle);
    }

    /**
     * This method is use to get queue name for which tab is opened
     * @return String The value
     */
    public String getQueueName() {
        final String text = getText(pageElements.queueName);
        log.info("Queue Name: " + text);
        return text;
    }

    /**
     * This method use to get agent name
     * @return String The Value
     */
    public String getAgentName() {
        final String text = getText(pageElements.agentName);
        commonLib.info("Assigning Ticket to Agent Name: " + text);
        return text;
    }

    /**
     * This method use to get agent auuid
     * @return String The Value
     */
    public String getAgentAuuid() {
        final String text = getText(pageElements.agentAuuid);
        commonLib.info("Assigning Ticket to Agent AUUID: " + text);
        return text;
    }

    /**
     * This Method is use to get availability slot
     * @param element The element location
     * @return Integer the count
     */
    public int getAvailableSlot(By element) {
        final String text = getText(element);
        log.info("Agent Available Slot: " + text);
        return Integer.parseInt(text);
    }

    /**
     * This method is used to get assigned slot
     * @return String The Value
     */
    public String getAssignedSlot() {
        final String text = getText(pageElements.assignedSlot);
        log.info("Agent Assigned Slot: " + text);
        return text;
    }

    /**
     * This method is use to close assign tab
     */
    public void closeAssignTab() {
        commonLib.info("Clicking on Close Assign Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTab);
    }

    /**
     * This method is use to get info message once ticket assign
     * @return String The value
     */
    public String getInfoMessage() {
        final String text = getText(pageElements.infoMessage);
        log.info("Reading Info Message: " + text);
        return text;
    }

    /**
     * This method is use to assign ticket to agent based on agent have available slot and tickets not already assigned to same agent
     * @param assigneeAUUID The assignee auuid
     * @return String The ticket new assignee auuid
     * @throws InterruptedException
     */
    public String ticketAssignedToAgent(String assigneeAUUID) throws InterruptedException {
        int slot;
        List<WebElement> agentList = returnListOfElement(pageElements.agentsCount);
        for (int i = 1; i <= agentList.size(); i++) {
            By agentAUUID = By.xpath(pageElements.agentList + i + pageElements.auuidField);
            String auuid = getText(agentAUUID);
            commonLib.info("Agent AUUID: " + getText(agentAUUID));
            commonLib.info("Check state: " + getText(agentAUUID).contains(assigneeAUUID));
            if (!getText(agentAUUID).contains(assigneeAUUID)) {
                By allSlot = By.xpath(pageElements.agentList + i + pageElements.slotCount);
                log.info(getText(allSlot));
                try {
                    slot = Integer.parseInt(getText(allSlot));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    slot = 0;
                }
                if (slot > 0) {
                    commonLib.info("Found Agent with Available Slot");
                    By clickAssignBtn = By.xpath(pageElements.agentList + i + pageElements.tabAssignIcon);
                    scrollToViewElement(clickAssignBtn);
                    clickAndWaitForLoaderToBeRemoved(clickAssignBtn);
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
