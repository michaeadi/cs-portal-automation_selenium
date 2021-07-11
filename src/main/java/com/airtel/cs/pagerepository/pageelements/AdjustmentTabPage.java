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
    public By transactionNumber=By.xpath("//input[@formcontrolname='paymentTransactionId']");
    public String chooseOption="//mat-option[";
    public String chooseText="//mat-option//span[";
    public By mainAmount=By.xpath("//input[@formcontrolname='mainAdjustmentAmount']");
    public By errorMsg=By.xpath("//div[@class='error-block']");
    public By openDaId=By.xpath("//mat-select[@formcontrolname='daId']");
    public By openDaUnit=By.xpath("//mat-select[@formcontrolname='daUnit']");
    public By daAmount=By.xpath("//input[@formcontrolname='measure']");
    public By popUpTitle=By.xpath("//span[@class='main-container__header--title']");
    public By yesBtn=By.xpath("//button[@class='yes-btn']");
    public By noBtn=By.xpath("//button[@class='no-btn']");

}
