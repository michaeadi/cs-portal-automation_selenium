package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AssignToAgentPageElements {
    public By pageTitle = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/h4");
    public By searchBox = By.name("searchAgent");
    public By searchBtn = By.xpath("//mat-sidenav-content//mat-sidenav//div//div//div//div//div[1]//button[1]");
    public By queueName = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/div/p");
    public By agentName = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[1]/p/span[1]");
    public By agentAuuid = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[1]/p/span[2]");
    public By assignBtn = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[4]/img");
    public By availableSlot = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[3]/p/span");
    public By assignedSlot = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[2]/div[1]/div[3]/p/text()");
    public By infoMessage = By.xpath("//*[@id=\"assignTicket\"]/app-assign-to-agents/section/div/div[1]/hr");
    public By closeTab = By.xpath("//button[@class=\"mat-button\"]//span[contains(text(),'X')]");

}
