package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class AdjustmentTabPage {
    public By openAdjustmentReason= By.xpath("//mat-select[@formcontrolname='adjustmentActionReason']");
    public By allOption=By.xpath("//mat-option");
    public By openAdjustmentType=By.xpath("//mat-select[@formcontrolname='adjustmentType']");
    public By serviceNumber=By.xpath("//input[@formcontrolname='msisdn']");
    public By openPrepaidAccountType=By.xpath("//mat-select[@formcontrolname='daType']");
    public By adjustmentCurrency=By.xpath("//input[@formcontrolname='mainAdjustmentAmount']");
    public By comments=By.xpath("//textarea[@formcontrolname='description']");
    public By submitBtn=By.xpath("//button[contains(text(),'Submit')]");
    public String adjustmentWidgetTitle="//div[@data-csautomation-key='ADJUSTMENTS_WIDGET']";
}
