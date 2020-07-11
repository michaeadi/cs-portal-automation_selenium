package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class assignToAgentPOM extends BasePage {

    By pageTitle= By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/h4");
    By searchBox=By.name("searchAgent");
    By searchBtn=By.xpath("//mat-sidenav-content//mat-sidenav//div//div//div//div//div[1]//button[1]");
    By queueName=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/div/p");
    By agentName=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[1]/p/span[1]");
    By agentAuuid=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[1]/p/span[2]");
    By assignBtn=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[4]/img");
    By availableSlot=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[3]/p/span");
    By assignedSlot=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[3]/p/text()");
    By infoMessage=By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/hr");

    public assignToAgentPOM(WebDriver driver) {
        super(driver);
    }

    public boolean validatePageTitle(){
        log.info("Validating Assign to agent Page");
        return checkState(pageTitle);
    }

    public String getQueueName(){
        log.info("Queue Name: "+readText(queueName));
        return readText(queueName);
    }

    public String getAgentName(){
        log.info("Agent Name: "+readText(agentName));
        return readText(agentName);
    }

    public String getAgentAuuid(){
        log.info("Agent AUUID: "+readText(agentAuuid));
        return readText(agentAuuid);
    }

    public int getAvailableSlot(){
        log.info("Agent Available Slot: "+readText(availableSlot));
        return Integer.parseInt(readText(availableSlot));
    }

    public String getAssignedSlot(){
        log.info("Agent Assigned Slot: "+readText((assignedSlot)));
        return readText(assignedSlot);
    }

    public void ClickedAssignBtn(){
        log.info("Clicking on Assign Button");
        if(getAvailableSlot() > 0) {
            click(assignBtn);
        }
    }

    public String getInfoMessage(){
        log.info("Reading Info Message: "+readText(infoMessage));
        return readText(infoMessage);
    }

}
