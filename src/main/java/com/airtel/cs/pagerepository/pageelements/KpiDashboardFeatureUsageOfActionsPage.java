package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class KpiDashboardFeatureUsageOfActionsPage {
    public By featureUsageOfActionsLabel = By.xpath("//span[contains(text(),'Feature Usage of Actions (Monthly)')]");
    public By connectionLabel;
    public By monthSelectionLabel;
    public By featureUsageOfActionsDetailsIcon = By.xpath("//span[contains(text(),'Feature Usage of Actions (Monthly)')]/..//img");
    public By agentIdLabel = By.xpath("//span[contains(text(),'Agent Id')]");
    public By agentNameLabel = By.xpath("//span[contains(text(),'Agent Name')]");
    public By MTDLabel = By.xpath("//span[contains(text(),'MTD')]");
    public By backIcon = By.xpath("//img[contains(@src,'assets/service-request/images/icon/back.svg')]");
}
