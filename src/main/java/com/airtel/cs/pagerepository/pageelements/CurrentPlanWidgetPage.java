package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class CurrentPlanWidgetPage {

    public By getTitle=By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']//span[@class='card__card-header--label']");
    public By footerUHWAuuid = By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']//*[@class='auuid-container']");
    public By middleUHWAuuid = By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']");
    public By planNameOnCurrentPlanWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']//*[@class='plan-info-label']");
    public By additionalBundleCountWidget = By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']//*[@class='label-color']//*[@data-csautomation-key='additionalBundles']");

}