package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class BasePageElements {
    public By loader = By.xpath("//div[@class='ngx-overlay loading-foreground']");
    public By loader1=By.xpath("//div[@class='ngx-overlay foreground-closing']");
    public By overlay = By.xpath("//mat-dialog-container[@role='dialog']");
    public By timeLine = By.xpath("//app-new-loader[@class='ng-star-inserted']//div[1]");
    public By home = By.xpath("//*[text()='HOME']");
    public By toastMessage = By.xpath("//app-toast-component/p");
}
