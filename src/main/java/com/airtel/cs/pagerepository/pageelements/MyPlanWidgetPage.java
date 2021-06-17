package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class MyPlanWidgetPage {
    public By currentBalanceHeader = By.xpath("//*[@data-csautomation-key='CURRENT_PLAN']//*[@data-csautomation-key='widgetName']");
    public By currentBalanceCurrency = By.xpath("//div[@class='card__content--top--left ng-star-inserted']/span[@class='currency ng-star-inserted']");
    public By mainAccountBalance = By.xpath("//div[@class='card__content--top--left ng-star-inserted']");
    public By currentBalanceLastRecharge = By.xpath("//span[@class='label-color']/span[@class='currency ng-star-inserted']");
    public By lastRechargeDateTime = By.xpath("//div[@class='date_time ng-star-inserted']");
    public By voiceBalance = By.xpath("//span[@class='card__content--bottom--plan ng-star-inserted'][1]/p[@class='balance ng-star-inserted']");
    public By dataBalance = By.xpath("//span[@class='card__content--bottom--plan ng-star-inserted'][2]/p[@class='balance ng-star-inserted']");
    public By smsBalance = By.xpath("//span[@class='card__content--bottom--plan ng-star-inserted'][3]/p[@class='balance ng-star-inserted']");
    public By voiceExpiryDate = By.xpath("//span[@class='card__content--bottom--plan ng-star-inserted'][1]/p[@class='ng-star-inserted'][2]");
    public By dataExpiryDate = By.xpath("//span[@class='card__content--bottom--plan ng-star-inserted'][2]/p[@class='ng-star-inserted'][2]");
    public By smsExpiryDate = By.xpath("//span[@class='card__content--bottom--plan ng-star-inserted'][3]/p[@class='ng-star-inserted'][2]");
    public By menu = By.xpath("//div[@data-csautomation-key='CURRENT_PLAN']//img[@data-csautomation-key='menuButton']");
    public By currentBalanceLastRechargeUnableToFetch = By.xpath("//span[@class='label-color']/span[@class='api-failed-error ng-star-inserted']");
    public By lastRechargeDateTImeUnableTOFetch = By.xpath("//div[@class='api-failed-error ng-star-inserted']");
    public By ticketIcon = By.xpath("//div[@id='CURRENT_PLAN']//span/img[@class='interaction-ticket']");
    public By getTitle = By.xpath("//div[@id='CURRENT_PLAN']//span[@class='card__card-header--label--text']");
    public By currentPlanWidgetLoader = By.xpath("//*[@data-csautomation-key='CURRENT_PLAN']//div[@class='animated-background']");
    public By footerYCPAuuid = By.xpath("//*[@data-csautomation-key='CURRENT_PLAN']//*[@class='auuid-container']");
    public By middleYCPAuuid = By.xpath("//*[@data-csautomation-key='CURRENT_PLAN']");

}
