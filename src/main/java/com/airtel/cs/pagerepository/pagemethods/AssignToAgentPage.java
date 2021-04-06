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
        log.info("Queue Name: " + readText(pageElements.queueName));
        return readText(pageElements.queueName);
    }

    public String getAgentName() {
        UtilsMethods.printInfoLog("Assigning Ticket to Agent Name: " + readText(pageElements.agentName));
        return readText(pageElements.agentName);
    }

    public String getAgentAuuid() {
        UtilsMethods.printInfoLog("Assigning Ticket to Agent AUUID: " + readText(pageElements.agentAuuid));
        return readText(pageElements.agentAuuid);
    }

    public int getAvailableSlot(By element) {
        log.info("Agent Available Slot: " + readText(element));
        return Integer.parseInt(readText(element));
    }

    public String getAssignedSlot() {
        log.info("Agent Assigned Slot: " + readText((pageElements.assignedSlot)));
        return readText(pageElements.assignedSlot);
    }

    public void closeAssignTab() {
        UtilsMethods.printInfoLog("Clicking on Close Assign Button");
        click(pageElements.closeTab);
    }

    public String getInfoMessage() {
        log.info("Reading Info Message: " + readText(pageElements.infoMessage));
        return readText(pageElements.infoMessage);
    }

    public String ticketAssignedToAgent(String assigneeAUUID) throws InterruptedException {
        int slot;
        By list = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div");
        List<WebElement> agentList = returnListOfElement(list);
        for (int i = 1; i <= agentList.size(); i++) {
            By agentAUUID = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]//span[@class=\"auuid yellow\"]");
            String auuid = readText(agentAUUID);
            log.info("Agent AUUID: " + readText(agentAUUID));
            log.info("Check state: " + readText(agentAUUID).contains(assigneeAUUID));
            if (!readText(agentAUUID).contains(assigneeAUUID)) {
                By allSlot = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]//span[@class=\"slot-count orange\"]");
                log.info(readText(allSlot));
                try {
                    slot = Integer.parseInt(readText(allSlot));
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
