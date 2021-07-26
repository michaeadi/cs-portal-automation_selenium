package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AgentLoginPage {
    public By loginQueueTitle = By.xpath("//h2[contains(text(),'Login Queue')]");
    public By selectQueue = By.xpath("//span[contains(text(),'Select Sub Workgroup (Queue)')]");
    public By skipBtn = By.xpath("//span[contains(text(),'Skip > >')]");
    public By submitBtn = By.xpath("//span[contains(text(),'Submit')]");
    public By allQueueOption = By.xpath("//span[@class='mat-checkbox-label']");
}
