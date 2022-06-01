package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboardFeatureUsageOfActionsPage {
    public By featureUsageOfActionsLabel = By.xpath("//span[contains(text(),'Feature Usage of Actions (Monthly)')]");
    public By connectionLabel = By.xpath("//span[contains(text(),'Feature Usage of Actions (Monthly)')]/../..//*[contains(text(),'Connection')]");
    public By monthSelectionLabel = By.xpath("//span[contains(text(),'Feature Usage of Actions (Monthly)')]/../..//*[@placeholder='Month']");
    public By featureUsageOfActionsDetailsIcon = By.xpath("//span[contains(text(),'Feature Usage of Actions (Monthly)')]/..//img");
    public By agentIdLabel = By.xpath("//span[contains(text(),'Agent Id')]");
    public By agentNameLabel = By.xpath("//span[contains(text(),'Agent Name')]");
    public By mtdLabel = By.xpath("//span[contains(text(),'MTD')]");
    public By backIcon = By.xpath("//img[contains(@src,'assets/service-request/images/icon/back.svg')]");
    public By featureUsageDetailedWidget = By.xpath("//*[contains(text(),'Feature Usage of Actions ')]");
}
