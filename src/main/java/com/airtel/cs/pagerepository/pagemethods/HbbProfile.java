package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.pagerepository.pageelements.CustomerProfilePage;
import com.airtel.cs.model.response.plans.MainAccountBalance;
import com.airtel.cs.model.response.plans.Plans;
import com.airtel.cs.pagerepository.pageelements.HbbProfilePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class HbbProfile extends BasePage{

    public HbbProfilePage pageElements;

    public HbbProfile(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, HbbProfilePage.class);
    }
    /**
     * This method is use to check current plan widget
     *
     * @return true/false
     */
    public Boolean issCurrentPlanVisible() {
        final boolean state = (isElementVisible(pageElements.currentPlan)&&isElementVisible(pageElements.currentPlanMore));
        commonLib.info("Is Current Plan Widget and more icon visible: " + state);
        return state;
    }

    /**
     * This method is use to check Recharge History Widget
     *
     * @return true/false
     */
    public Boolean isRechargeHistoryVisible() {
        final boolean state = (isElementVisible(pageElements.currentPlan)&&isElementVisible(pageElements.rechargeHistoryMore));
        commonLib.info("Is Recharge History Widget and more icon visible: " + state);
        return state;
    }
    /**
     * This method is use to check usage history visible or not
     *
     * @return true/false
     */
    public Boolean isUsageHistoryVisible() {
        final boolean state = (isElementVisible(pageElements.usageHistory)&&isElementVisible(pageElements.usageHistoryMore));
        commonLib.info("Is Usage History Widget visible: " + state);
        return state;
    }
    /**
     * This method is use to check service  profile visible or not
     *
     * @return true/false
     */
    public Boolean isServiceProfileVisible() {
        final boolean state = (isElementVisible(pageElements.serviceProfile));
        commonLib.info("Is Service Profile Widget visible: " + state);
        return state;
    }
    /**
     * This method is use to check hbb profile visible or not
     *
     * @return true/false
     */
   /* public Boolean isCurrentPlanVisible() {
        final boolean state = isElementVisible(pageElements.currentPlan);
        commonLib.info("Is Current Plan Widget visible: " + state);
        return state;
    }
    /**
     * This method is use to check hbb profile visible or not
     *
     * @return true/false
     */
    /*public Boolean isCurrentPlanVisible() {
        final boolean state = isElementVisible(pageElements.currentPlan);
        commonLib.info("Is Current Plan Widget visible: " + state);
        return state;
    }
    */

    /**
     * This method is use to check hbb profile visible or not
     *
     * @return true/false
     */
    public Boolean isHBBProfileVisible() {
        final boolean state = isElementVisible(pageElements.hbbProfile);
        commonLib.info("Is Hbb Profile displayed: " + state);
        return state;
    }

    /**
     * This method is use to check others  visible or not in hbb
     *
     * @return true/false
     */
    public Boolean isHBBOthersVisible() {
        final boolean state = isElementVisible(pageElements.hbbOthers);
        commonLib.info("Is Others displayed in Hbb Profile: " + state);
        return state;
    }
    /**
     * This method is use to check hbb tab visible or not
     *
     * @return true/false
     */
    public Boolean isHBBTabVisible() {
        final boolean state = isElementVisible(pageElements.hbbTab);
        commonLib.info("Is Hbb Tab displayed: " + state);
        return state;
    }

    /**
     * This method is use to check count to hbb linked numbers
     *
     * @return true/false
     */
    public Integer getHbbLinkedNumbers() {
        final Integer size= getSizeOfElement(pageElements.hbbLinkedNumbers);
        commonLib.info("Count of Hbb Linked Numbers : " + size);
        return size;
    }

    /**
     * This method is use to check hbb profile visiblility for non airtel number
     *
     * @return true/false
     */
    public Boolean isHBBNonAirtelPopUpVisible() {
        final boolean state = isElementVisible(pageElements.hbbProfileNonAirtelNo);
        commonLib.info("Is Pop Up visible: " + state);
        return state;
    }

    /**
     * This method is use to check hbb profile visiblility for non airtel number
     *
     * @return true/false
     */
    public Boolean isHBBIconVisible() {
        final boolean state = isElementVisible(pageElements.hbbIcon);
        commonLib.info("Is Pop Up visible: " + state);
        return state;
    }

    /**
     * This method is use to check whether Hbb customer interaction is displayed or not
     *
     * @return true/false
     */
    public Boolean isHBBCustomerInteractionTitleVisible() {
        final boolean state = isElementVisible(pageElements.hbbCustomerInteraction);
        commonLib.info("Is Hbb Customer Interaction Title visible: " + state);
        return state;
    }
    /**
     * This method is use to check whether Non Airtel number is displayed or not
     *
     * @return true/false
     */
    public String getMsisdin() {
        final String text = getText(pageElements.nonAirtelMisdin);
        commonLib.info(" Non Airtel Msisdn visible: " + text);
        return text;
    }

    /**
     * This method is used to check HBB Page for Non Airtel Msisdn
     */

    public boolean ishbbPageForNonAirtelNo()
    {
        final boolean state =isVisible(pageElements.hbbPageNonAirtel);
        commonLib.info("Is Hbb Page loaded for non airtel msisdn: " + state);
        return state;
    }

    /**
     * This method is use to check whether Send sms tab is displayed or not
     *
     * @return true/false
     */
    public Boolean isSendSmsVisible() {
        final Boolean state =isVisible(pageElements.sendSMS);
        commonLib.info("Is SMS tab visible: " + state);
        return state;
    }

    /**
     * This method is use Message History Tab
     *
     * @return true/false
     */
    public Boolean isMessageHistoryVisible() {
        final Boolean state =isVisible(pageElements.messageHistory);
        commonLib.info("Is Message History tab visible: " + state);
        return state;
    }

    /**
     * This method is use Interaction History Tab
     *
     * @return true/false
     */
    public Boolean isInteractionHistoryVisible() {
        final Boolean state =isVisible(pageElements.interactionHistory);
        commonLib.info("Is Interaction History tab visible: " + state);
        return state;
    }
    /**
     * This method is used to check Suspend SIM visibility
     *
     * @return true/false
     */
    public Boolean isSuspendSIMVisible() {
        final Boolean state =isVisible(pageElements.suspendSIM);
        commonLib.info("Is Suspend SIM option visible: " + state);
        return state;
    }
    /**
       * This method is used to check Reactivate SIM Visibility
     *
             * @return true/false
             */
    public Boolean isReactivateSimVisible() {
        final Boolean state =isVisible(pageElements.reactivateSIM);
        commonLib.info("Is Reactivate SIM option visible: " + state);
        return state;
    }
    /**
     * This method is used to check Ticket History
     *
     * @return true/false
     */
    public Boolean isTicketHistoryVisible() {
        final Boolean state =isVisible(pageElements.ticketHistory);
        commonLib.info("Is Ticket History visible: " + state);
        return state;
    }

    /**
     * This method is use to move to HBB Profile from GSM Profile
     *
     *
     * */

    public void moveToHbb()
    {
        Actions actions = new Actions(driver);
        actions.moveToElement((WebElement) pageElements.gsmProfile);
        actions.moveToElement((WebElement) pageElements.hbbProfile);
        actions.click().build().perform();
    }

    /**
     * This method is use to check purple line visibility under hbb tab
     *
     *  @return true/false
     * */
    public Boolean isPurpleLineVisible()
    {
        final boolean state = (isElementVisible(pageElements.hbbPurpleLine));
        commonLib.info("Is purple line visible under HBB tab : " + state);
        return state;

    }

    /**
     * This method is use to click on Hbb Profile
     */
    public void clickOnHbbProfile() {
        if (isVisible(pageElements.hbbProfile) && isClickable(pageElements.hbbProfile)) {
            commonLib.info("Clicking on Hbb Profile");
            clickWithoutLoader(pageElements.hbbProfile);
        } else {
            commonLib.fail("Exception in method - clickOnHbbProfile ", true);
        }
    }

    /**
     * This method is used to click on Hbb Tab
     */
    public void clickOnHbbTab()
    {
        if(isVisible(pageElements.hbbTab))
        commonLib.info("Clicking on Hbb Tab");
        clickWithoutLoader(pageElements.hbbTab);
    }
    /**
     * This method is use to click on Hbb Profile
     */
    public void clickOnCurrentPlan() {
        if (isVisible(pageElements.currentPlan) && isClickable(pageElements.currentPlanMore))
        {commonLib.info("Clicking on Current Plan");
            clickWithoutLoader(pageElements.currentPlanMore);
        } else {
            commonLib.fail("Exception in method - clickOnCurrentPlan ", true);
        }
    }

    /**
     * This method is use to check that hbb tab is displayed next to GSM profile
     *
     *  @return true/false
     * */
    public Boolean isHbbDisplayedNextToGSM()
    {
        final boolean state = (isElementVisible(pageElements.hbbLocation));
        commonLib.info("Is Hbb tab displayed next to GSM : " + state);
        return state;

    }


    /**
     * This method is use to check gsm profile visibility along with am profile
     *
     *  @return true/false
     * */
    public Boolean isGSMAMProfileVisible() {
        final boolean state = (isElementVisible(pageElements.gsmProfile))&& (isElementVisible(pageElements.amProfile));
        commonLib.info("Is GSM Profile visible along with AM Profile : " + state);
        return state;
    }
    /*
      This Method will give us footer auuid shown in Current plan
      Current plan
       */
    public String getFooterAuuid() {
        commonLib.info(getText(pageElements.hbbFoooterAuid));
        return getText(pageElements.hbbMiddleAuid);
    }

    /*
    This Method will give us auuid shown in the middle of the Current plan
     */
    public String getMiddleAuuid() {
        String result;
        result = getAttribute(pageElements.hbbMiddleAuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }

}
