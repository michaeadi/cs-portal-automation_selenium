package com.airtel.cs.pagerepository.pageelements;
import org.openqa.selenium.By;

public class HbbProfilePage {
    public By hbbProfile = By.xpath("//div[contains(text(),'HBB Profile')]");
    public By hbbPurpleLine =By.xpath("//div[@class='card__content restricted no-header-height ng-star-inserted']//mat-ink-bar[@style='visibility: visible; left: 100px; width: 28px;']");
    public By hbbProfileNonAirtelNo=By.xpath("//div[@class='hbb-profile']");
    public By hbbCustomerInteraction=By.xpath("//div[contains(text(),'HBB Customer Information')]");
    public By nonAirtelMisdin=By.xpath("//span[contains(@class,'search-box ng-star')]");
    public By searchNonAirtel=By.xpath("//input[(@type='search')]");
    public By hbbTab=By.xpath("//div[contains(text(),'HBB')]");
    public By hbbLinkedNumbers=By.xpath("//span[@class='label ng-star-inserted']");
    public By hbbIcon=By.xpath("//span[@class='cursor-pointer ng-star-inserted']");
    public By hbbPageNonAirtel=By.xpath("//div[@class='hbb-profile']");
    public By hbbLocation = By.xpath("//div[@id=\"GSM_PROFILE\"]//div[@style='transform: translateX(0px);']//div[2]");
    public By gsmProfile = By.xpath("//div[@id='GSM_PROFILE']");
    public By amProfile = By.xpath("//div[@id='AM_PROFILE']");
    public By hbbMiddleAuid=By.xpath("//div[@id='HBB_PROFILE']");
    public By hbbFoooterAuid=By.xpath("//div[@id='HBB_PROFILE']//*[@class='auuid-container']");
    public By sendSMS=By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Send SMS')]");
    public By suspendSIM=By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Suspend SIM')]");
    public By reactivateSIM=By.xpath("//div[@class='mat-menu-content']//button[contains(text(),'Reactivate SIM')]");
    public By messageHistory = By.xpath("//div[contains(text(),'Message')]");
    public By ticketHistory = By.xpath("//span[contains(text(),'Ticket')]");
    public By interactionHistory = By.xpath("//div[@class='history-page']//div[contains(text(),'Interaction')]");
    public By nonAirtelMsisdnError=By.xpath("//span[contains(text(),'Invalid Mobile Number')]");




    /*
     * Hbb Customer Name ,Alternate No., Email id and Device Type Element Locator
     * */
    public By hbbUserName = By.xpath("//span[contains(text(),'User Name')]//following-sibling::span/span");
    public By hbbAlternateNumber = By.xpath("//span[contains(text(),'Alternate Number')]//following-sibling::span/span[2]");
    public By hbbEmailId = By.xpath("//span[contains(text(),'Email ID')]//following-sibling::span/span[2]");
    public By deviceInfo = By.xpath("//div[@id='HBB_PROFILE']//span[contains(text(),'Device Type')]//following::span/span[2]");
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

    /*
    Hbb Non Airtel Page
     */

    public By middleName=By.xpath("//div[contains(text(),'Middle Name')]");
    public By lastName=By.xpath("//div[contains(text(),'Last Name')]");
    public By firstName=By.xpath("//div[contains(text(),'First Name')]");
    public By emailId=By.xpath("//div[contains(text(),'Email ID')]");
    public By userName=By.xpath("//div[contains(text(),'User Name')]");
    public By dob=By.xpath("//div[contains(text(),'DOB')]");
}
