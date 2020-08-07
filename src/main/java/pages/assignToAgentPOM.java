package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class assignToAgentPOM extends BasePage {

    By pageTitle = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/h4");
    By searchBox = By.name("searchAgent");
    By searchBtn = By.xpath("//mat-sidenav-content//mat-sidenav//div//div//div//div//div[1]//button[1]");
    By queueName = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/div/p");
    By agentName = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[1]/p/span[1]");
    By agentAuuid = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[1]/p/span[2]");
    By assignBtn = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[4]/img");
    By availableSlot = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[3]/p/span");
    By assignedSlot = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[3]/p/text()");
    By infoMessage = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/hr");
    public assignToAgentPOM(WebDriver driver) {
        super(driver);
    }

    public boolean validatePageTitle() {
        log.info("Validating Assign to agent Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating Assign to agent Page");
        return checkState(pageTitle);
    }

    public String getQueueName() {
        log.info("Queue Name: " + readText(queueName));
        return readText(queueName);
    }

    public String getAgentName() {
        log.info("Agent Name: " + readText(agentName));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Assigning Ticket to Agent Name: "+readText(agentName));
        return readText(agentName);
    }

    public String getAgentAuuid() {
        log.info("Agent AUUID: " + readText(agentAuuid));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Assigning Ticket to Agent AUUID: "+readText(agentAuuid));
        return readText(agentAuuid);
    }

    public int getAvailableSlot(By element) {
        log.info("Agent Available Slot: " + readText(element));
        return Integer.parseInt(readText(element));
    }

    public String getAssignedSlot() {
        log.info("Agent Assigned Slot: " + readText((assignedSlot)));
        return readText(assignedSlot);
    }

//    public void ClickedAssignBtn() {
//        log.info("Clicking on Assign Button");
//        if (getAvailableSlot() > 0) {
//            ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Assign Button");
//            click(assignBtn);
//        }
//    }

    public String getInfoMessage() {
        log.info("Reading Info Message: " + readText(infoMessage));
        return readText(infoMessage);
    }

    public void getAvailableSlotAll(String assigneeAUUID) throws InterruptedException {
        int slot;
        By list=By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div");
        List<WebElement> agentList=driver.findElements(list);
        for(int i=1;i<=agentList.size();i++) {
            By agentAUUID = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]//span[@class=\"auuid yellow\"]");
            String auuid=readText(agentAUUID);
            System.out.println("Agent AUUID: "+readText(agentAUUID));
            System.out.println("Check state: "+readText(agentAUUID).contains(assigneeAUUID));
            if (!readText(agentAUUID).contains(assigneeAUUID)) {
                By allSlot = By.xpath("//div[@class=\"pannel-content-area ng-star-inserted\"]/div[" + i + "]//span[@class=\"slot-count orange\"]");
                System.out.println(readText(allSlot));
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
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Click on Assign to Agent Button");
                    waitTillLoaderGetsRemoved();
                    Assert.assertEquals( slot - 1, getAvailableSlot(allSlot),"Agent Available Slot does not Decrease");
                    ExtentTestManager.getTest().log(LogStatus.INFO,"Ticket unassigned from <"+assigneeAUUID+">");
                    ExtentTestManager.getTest().log(LogStatus.INFO,"Ticket Assigned to <"+auuid+">");
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Validated Ticket is Assigned to User Successfully");
                    return;
                }
            }
        }
        ExtentTestManager.getTest().log(LogStatus.WARNING, "No User have Available Slot");
    }

}
