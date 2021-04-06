package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class TransferToQueuePageElements {
    public By pageTitle = By.xpath("//*[@id=\"assignQueue\"]/app-assign-to-queue/section/div/div[1]/h4");
    public By transferQueue;
    public By closeTab = By.xpath("//button[@class=\"mat-button\"]//span[contains(text(),'X')]");
}
