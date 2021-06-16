package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.MyPlanWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

@Log4j2
public class CurrentBalanceWidget extends BasePage {

    MyPlanWidgetPage pageElements;

    public CurrentBalanceWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, MyPlanWidgetPage.class);
    }

    /**
     * This method is use to check last recharge unable to fetch error visible or not
     * @return true/false
     */
    public boolean isLastRechargeUnableToFetchVisible() {
        final boolean elementVisible = isElementVisible(pageElements.currentBalanceLastRechargeUnableToFetch);
        commonLib.info("Validating is error visible for last recharge balance : " + elementVisible);
        return elementVisible;
    }

    /**
     * This method is use to check last recharge data and time unable to fetch error visible or not
     * @return true/false
     */
    public boolean isLastRechargeDateTImeUnableTOFetch() {
        final boolean elementVisible = isElementVisible(pageElements.lastRechargeDateTImeUnableTOFetch);
        commonLib.info("Validating is error visible for last recharge Date & Time : " + elementVisible);
        return elementVisible;
    }

    /**
     * This method is use to check last recharge error message value
     * @return String The value
     */
    public String gettingLastRechargeUnableToFetchVisible() {
        final String text = getText(pageElements.currentBalanceLastRechargeUnableToFetch);
        commonLib.info("Validating  error message for last recharge balance : " + text);
        return text;
    }

    /**
     * This method is use to check last recharge date & time error message value
     * @return String The value
     */
    public String gettingLastRechargeDateTImeUnableTOFetch() {
        final String text = getText(pageElements.lastRechargeDateTImeUnableTOFetch);
        commonLib.info("Validating  error message for last recharge Date & Time : " + text);
        return text;
    }

    /**
     * This method is use to check current balance widget menu icon visible or not
     * @return true/false
     */
    public boolean isCurrentBalanceWidgetMenuVisible() {
        try {
            commonLib.info("Checking is Your Current Balance Widget'Menu Visible");
            waitVisibility(pageElements.menu);
            return isElementVisible(pageElements.menu);
        } catch (Exception e) {
            throw new NoSuchElementException("Current Plan widget menu option does not display");
        }
    }

    /**
     * This method is use to click menu button
     */
    public void openingDADetails() {
        commonLib.info("Clicking Current Balance WidgetMenu Visible");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }

    /**
     * This method is use to get last recharge amount
     * @return String The value
     */
    public String gettingLastRechargeAmount() {
        final String text = getText(pageElements.currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
        commonLib.info("Getting Main Account Balance from Your Current Balance Widget : " + text);
        return text.trim();
    }

    /**
     * This method is use to check current balance widget visible or not
     * @return true/false
     */
    public boolean isCurrentBalanceWidgetVisible() {
        commonLib.info("Checking is Your Current Balance Widget Visible");
        return isElementVisible(pageElements.currentBalanceHeader);
    }

    /*
   This Method will get the text of the header
    */
    public String getCurrentPlanHeaderText() {
        final String headerText = getText(pageElements.currentBalanceHeader);
        commonLib.info("Airtel Money Widget Header Text is:- " + headerText);
        return headerText;
    }

    /**
     * This method use to get current balance currency
     * @return String The value
     */
    public String gettingCurrentBalanceCurrency() {
        final String text = getText(pageElements.currentBalanceCurrency);
        commonLib.info("Getting Currency from Your Current Balance Widget : " + text.trim());
        return text.replaceAll("[^a-zA-z]", "");
    }

    /**
     * This method use to get main account balance
     * @return String The value
     */
    public String gettingMainAccountBalance() {
        String result = null;
        if (isVisible(pageElements.mainAccountBalance)) {
            result = getText(pageElements.mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
            commonLib.info("Getting Main Account Balance from Your Current Balance Widget : " + result);
        }
        return result;
    }

    /**
     * This method is use to get voice type expiry date
     * @return String The value
     */
    public String getVoiceExpiryDate() {
        final String text = getText(pageElements.voiceExpiryDate);
        commonLib.info("Getting Remaining Voice Expiry Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get data type expiry date
     * @return String The value
     */
    public String getDataExpiryDate() {
        final String text = getText(pageElements.dataExpiryDate);
        commonLib.info("Getting Remaining Data Expiry Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get SMS type expiry date
     * @return String The value
     */
    public String getSmsExpiryDate() {
        final String text = getText(pageElements.smsExpiryDate);
        commonLib.info("Getting Remaining SMS Expiry Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get voice type balance
     * @return String The value
     */
    public String getVoiceBalance() {
        final String text = getText(pageElements.voiceBalance);
        commonLib.info("Getting Remaining Voice Balance from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get data type balance
     * @return String The value
     */
    public String getDataBalance() {
        final String text = getText(pageElements.dataBalance);
        commonLib.info("Getting Remaining Data Balance from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get sms type balance
     * @return String The value
     */
    public String getSmsBalance() {
        final String text = getText(pageElements.smsBalance);
        commonLib.info("Getting Remaining SMS Balance from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get last recharge date & time
     * @return String The value
     */
    public String getLastRechargeDateTime() {
        final String text = getText(pageElements.lastRechargeDateTime);
        commonLib.info("Getting Last Recharge Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to click ticket icon on current balance widget
     * */
    public void clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Current Plan History Widget");
        }
    }

    /**
     * This method is used to get widget title
     * @return String The value
     */
    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }

    /*
       This Method will give us footer auuid shown in YCP widget
       YCP = Your Current Plan
        */
    public String getFooterAuuidYCP() {
        String result = null;
        result = getText(pageElements.footerYCPAuuid);
        return result;
    }

    /*
    This Method will give us auuid shown in the middle of the YCP modal
    YCP = Your Current Plan
     */
    public String getMiddleAuuidTYP() {
        String result = null;
        result = getAttribute(pageElements.middleYCPAuuid, "data-auuid", false);
        return result;
    }
}
