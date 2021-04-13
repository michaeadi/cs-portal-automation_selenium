package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pageelements.AssignToAgentPageElements;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class AssignToAgentPage extends BasePage {

    AssignToAgentPageElements pageElements;

    public AssignToAgentPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AssignToAgentPageElements.class);
    }

    public boolean validatePageTitle() {
        UtilsMethods.printInfoLog("Validating Assign to agent Page");
        return checkState(pageElements.pageTitle);
    }

    public String getQueueName() {
        log.info("Queue Name: " + getText(pageElements.queueName));
        return getText(pageElements.queueName);
    }

    public String getAgentName() {
        UtilsMethods.printInfoLog("Assigning Ticket to Agent Name: " + getText(pageElements.agentName));
        return getText(pageElements.agentName);
    }

    public String getAgentAuuid() {
        UtilsMethods.printInfoLog("Assigning Ticket to Agent AUUID: " + getText(pageElements.agentAuuid));
        return getText(pageElements.agentAuuid);
    }

    public int getAvailableSlot(By element) {
        log.info("Agent Available Slot: " + getText(element));
        return Integer.parseInt(getText(element));
    }

    public String getAssignedSlot() {
        log.info("Agent Assigned Slot: " + getText((pageElements.assignedSlot)));
        return getText(pageElements.assignedSlot);
    }

    public void closeAssignTab() {
        UtilsMethods.printInfoLog("Clicking on Close Assign Button");
        click(pageElements.closeTab);
    }

    public String getInfoMessage() {
        log.info("Reading Info Message: " + getText(pageElements.infoMessage));
        return getText(pageElements.infoMessage);
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
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Found Agent with Available Slot");
                    By clickAssignBtn = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]/div[4]/img[1]");
                    scrollToViewElement(clickAssignBtn);
                    click(clickAssignBtn);
                    UtilsMethods.printInfoLog("Click on Assign to Agent Button");
                    return auuid.split("-")[1];
                }
            }
        }
        UtilsMethods.printWarningLog("No User have Available Slot");
        closeAssignTab();
        return "No Agent Available";
    }

}
