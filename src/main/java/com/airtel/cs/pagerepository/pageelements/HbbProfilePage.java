package com.airtel.cs.pagerepository.pageelements;
import org.openqa.selenium.By;

public class HbbProfilePage {
    public By hbbProfile = By.xpath("//div[contains(text(),'HBB Profile')]");
    public By hbbPurpleLine =By.xpath("//div[@class='card__content restricted no-header-height ng-star-inserted']//mat-ink-bar[@style='visibility: visible; left: 100px; width: 28px;']");
    public By hbbProfileNonAirtelNo=By.xpath("//div[@class='hbb-profile']");
    public By hbbCustomerInteraction=By.xpath("//div[contains(text(),'HBB Customer Information')]");
    public By nonAirtelMisdin=By.xpath("//span[contains(@class,'search-box ng-star')]");
    public By hbbTab=By.xpath("//div[contains(text(),'HBB')]");
    public By hbbLinkedNumbers=By.xpath("//span[@class='label ng-star-inserted']");
    public By hbbIcon=By.xpath("//span[@class='cursor-pointer ng-star-inserted']");
    public By hbbPageNonAirtel=By.xpath("//div[@class='hbb-profile']");
    public By hbbLocation = By.xpath("//div[@id='mat-tab-label-11-1']");
    public By gsmProfile = By.xpath("//div[contains(text(),'GSM Profile')]");
    public By amProfile = By.xpath("//span[contains(text(),'Airtel Money Profile')]");
    public By hbbMiddleAuid=By.xpath("//div[@id='HBB_PROFILE']");
    public By hbbFoooterAuid=By.xpath("//div[@id='HBB_PROFILE']//*[@class='auuid-container']");
    public By sendSMS=By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Send SMS')]");
    public By suspendSIM=By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Suspend SIM')]");
    public By reactivateSIM=By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Reactivate SIM')]");
    public By messageHistory = By.xpath("//div[contains(text(),'Message')]");
    public By ticketHistory = By.xpath("//span[contains(text(),'Ticket')]");
    public By interactionHistory = By.xpath("//div[@class='history-page']//div[contains(text(),'Interaction')]");


    /*
     * Hbb Customer Name ,Alternate No., Email id and Device Type Element Locator
     * */
    public By userName = By.xpath("//span[contains(text(),'User Name')]//following-sibling::span");
    public By alternateNumber = By.xpath("//input[@data-csautomation-key='msisdnSearchBox']");
    public By emailId = By.xpath("//span[@class='customer-name ng-star-inserted']/span[1]");
    public By deviceInfo = By.xpath("//*[contains(text(),'DOB')]//following-sibling::td/span/span");
    public By hbbOthers = By.xpath("//div[@id='HBB_PROFILE'] //div[contains(text(),'Others')]");

    /*
    Widgets visibility check
     */
    public By currentPlan=By.xpath("//div[@data-csautomation-key='CURRENT_PLAN']");
    public By rechargeHistory=By.xpath("//div[@data-csautomation-key='RECHARGE_HISTORY']");
    public By usageHistory=By.xpath("//div[@data-csautomation-key='USAGE_HISTORY']");
    public By serviceProfile=By.xpath("//div[@data-csautomation-key='SERVICE_PROFILE']");
    public By rechargeHistoryMore=By.xpath("//div[@data-csautomation-key='RECHARGE_HISTORY'] //img[@data-csautomation-key='menuButton']");
    public By usageHistoryMore=By.xpath("//div[@data-csautomation-key='USAGE_HISTORY'] //img[@data-csautomation-key='menuButton']");
    public By currentPlanMore=By.xpath("//div[@data-csautomation-key='CURRENT_PLAN'] //img[@data-csautomation-key='menuButton']");

}
