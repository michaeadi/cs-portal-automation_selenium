package com.airtel.cs.pagerepository.pageelements;

import org.openqa.selenium.By;

public class PlanAndPackDetailedWidgetPage {

    /**
     * Plan and pack Widget
     */
    public By planTitle = By.xpath("//div[@data-csautomation-key='PLAN_DETAILS']//span[@class='card__card-header--label']");
    public By packTitle = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//span[@class='card__card-header--label']");
    public By footerPackAuuid = By.xpath("//*[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//*[@class='auuid-container']");
    public By middlePackAuuid = By.xpath("//*[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']");
    public By footerPlanAuuid = By.xpath("//*[@data-csautomation-key='PLAN_DETAILS']//*[@class='auuid-container']");
    public By middlePlanAuuid = By.xpath("//*[@data-csautomation-key='PLAN_DETAILS']");
    public By productName = By.xpath("//*[text()='Product Name']");
    public By category = By.xpath("//*[text()='Category']");
    public By benefit = By.xpath("//*[text()='Benefit']");
    public By used = By.xpath("//*[text()='Used']");
    public By available = By.xpath("//*[text()='Available']");
    public By currentPlanDetailed = By.xpath("//div[@data-csautomation-key='POSTPAID_CURRENT_PLAN']//img[@class='header-action-icon ng-star-inserted']");
    public By activePackWidgetTab = By.xpath("//div[@class='tabs-container']//div[@class='mat-tab-label-container']//div[@class='mat-tab-labels']/div[3]//span[1]");
    public By activePackPlanDetails = By.xpath("//div[@data-csautomation-key='PLAN_DETAILS']//span[1]");
    public By activePackAddOnBundle = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//span[1]");
    public By checkboxInActivePack = By.xpath("//div[@class='container-fluid ng-star-inserted']/div[1]/div[1]/label");
    public By activePackWidgetOnPM = By.xpath("//div[@class='mat-tab-labels']/div/div");
    public By planDetailUnableToFetch = By.xpath("//div[@data-csautomation-key='PLAN_DETAILS']//*[text()='Unable to fetch data']");
    public By packDetailUnableToFetch = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//*[text()='Unable to fetch data']");

    public By packName = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@data-csautomation-key='dataRows']/div[1]");
    public By ctgry = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@data-csautomation-key='dataRows']/div[2]");
    public By bnft = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@data-csautomation-key='dataRows']/div[3]");
    public By usd = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@data-csautomation-key='dataRows']/div[4]");
    public By avl = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@data-csautomation-key='dataRows']/div[5]");

    public String packDetailsHeaderRow = "//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@data-csautomation-key='headerRow']/div[";
    public String packDetailsHeaderValue = "]";
    public By rows = By.xpath("//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted']");
    public String valueRow = "//div[@data-csautomation-key='ADD_ON_BUNDLE_USAGE']//div[@class='table-data-wrapper ng-star-inserted']/div[@class='ng-star-inserted'][";
    public String productNameValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][1]/span";
    public String categoryValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][2]/span";
    public String benefitValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][3]/span";
    public String usedValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][4]/span";
    public String availableValue = "]//div[@class='data-container ng-star-inserted' or @class='slide-toggle red ng-star-inserted'][5]/span";

}