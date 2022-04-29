package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TransferToQueuePage {
    public By pageTitle = By.xpath("//*[@id='assignQueue']/app-assign-to-queue/section/div/div[1]/h4");
    public String transferQueueBtn="')]//ancestor::div[1]//following-sibling::div/img";
    public By closeTab = By.xpath("//button[@class='mat-button']//span[contains(text(),'X')]");
    public By firstQueueName=By.xpath("//div[@class='agent-list ng-star-inserted'][1]//span");
    public String option="//span[contains(text(),'";
}
