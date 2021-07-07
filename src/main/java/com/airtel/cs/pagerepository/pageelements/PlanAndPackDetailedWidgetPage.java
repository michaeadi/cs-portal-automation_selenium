package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class PlanAndPackDetailedWidgetPage {

    /**
     * Account Information Widget
     */
    public By planTitle = By.xpath("//div[data-csautomation-key='PLAN_DETAILS']//span[@class='card__card-header--label']");
    public By packTitle = By.xpath("//div[data-csautomation-key='ADD_ON_BUNDLE_USAGE']//span[@class='card__card-header--label']");
    public By footerPackAuuid = By.xpath("//*[data-csautomation-key='ADD_ON_BUNDLE_USAGE']//*[@class='auuid-container']");
    public By middlePackAuuid = By.xpath("//*[data-csautomation-key='ADD_ON_BUNDLE_USAGE']");
    public By footerPlanAuuid = By.xpath("//*[data-csautomation-key='PLAN_DETAILS']//*[@class='auuid-container']");
    public By middlePlanAuuid = By.xpath("//*[data-csautomation-key='PLAN_DETAILS']");
    public By currentPlanWdiget = By.xpath("//*[text()='Current Plan']");
    public By productName = By.xpath("//*[text()='Product Name']");
    public By category = By.xpath("//*[text()='Category']");
    public By benefit = By.xpath("//*[text()='Benefit']");
    public By used = By.xpath("//*[text()='Used']");
    public By available = By.xpath("//*[text()='Available']");
    public By currentPlanDetailed = By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']//img[@class='header-action-icon ng-star-inserted']");
    // To -do
    public By activePackWidgetTab = By.xpath("");
    public By activePackCheckbox = By.xpath("");
    public By activePackPlanDetails = By.xpath("");
    public By activePackAddOnBundle = By.xpath("");
}