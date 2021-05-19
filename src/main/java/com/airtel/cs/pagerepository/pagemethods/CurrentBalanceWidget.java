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

    public boolean isLastRechargeUnableToFetchVisible() {
        final boolean elementVisible = isElementVisible(pageElements.currentBalanceLastRechargeUnableToFetch);
        commonLib.info("Validating is error visible for last recharge balance : " + elementVisible);
        return elementVisible;
    }

    public boolean isLastRechargeDateTImeUnableTOFetch() {
        final boolean elementVisible = isElementVisible(pageElements.lastRechargeDateTImeUnableTOFetch);
        commonLib.info("Validating is error visible for last recharge Date & Time : " + elementVisible);
        return elementVisible;
    }

    public String gettingLastRechargeUnableToFetchVisible() {
        final String text = getText(pageElements.currentBalanceLastRechargeUnableToFetch);
        commonLib.info("Validating  error message for last recharge balance : " + text);
        return text;
    }

    public String gettingLastRechargeDateTImeUnableTOFetch() {
        final String text = getText(pageElements.lastRechargeDateTImeUnableTOFetch);
        commonLib.info("Validating  error message for last recharge Date & Time : " + text);
        return text;
    }


    public boolean isCurrentBalanceWidgetMenuVisible() {
        try {
            commonLib.info("Checking is Your Current Balance Widget'Menu Visible");
            return isElementVisible(pageElements.menu);
        } catch (Exception e) {
            throw new NoSuchElementException("Current Plan widget menu option does not display");
        }
    }

    public void openingDADetails() {
        commonLib.info("Clicking Current Balance WidgetMenu Visible");
        clickAndWaitForLoaderToBeRemoved(pageElements.menu);
    }

    public String gettingLastRechargeAmount() {
        final String text = getText(pageElements.currentBalanceLastRecharge).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
        commonLib.info("Getting Main Account Balance from Your Current Balance Widget : " + text);
        return text.trim();
    }

    public boolean isCurrentBalanceWidgetVisible() {
        commonLib.info("Checking is Your Current Balance Widget Visible");
        return isElementVisible(pageElements.currentBalanceHeader);
    }

    /*
   This Method will get the text of the header
    */
    public String getCurrentPlanHeaderText(){
        final String headerText = getText(pageElements.currentBalanceHeader);
        commonLib.info("Airtel Money Widget Header Text is:- " + headerText);
        return headerText;
    }

    public String gettingCurrentBalanceCurrency() {
        final String text = getText(pageElements.currentBalanceCurrency);
        commonLib.info("Getting Currency from Your Current Balance Widget : " + text.trim());
        return text.replaceAll("[^a-zA-z]", "");
    }

    public String gettingMainAccountBalance() {
        final String text = getText(pageElements.mainAccountBalance).replaceAll("\\s", "").replaceAll("[A-Z,a-z]", "");
        commonLib.info("Getting Main Account Balance from Your Current Balance Widget : " + text);
        return text;
    }

    public String getVoiceExpiryDate() {
        final String text = getText(pageElements.voiceExpiryDate);
        commonLib.info("Getting Remaining Voice Expiry Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public String getDataExpiryDate() {
        final String text = getText(pageElements.dataExpiryDate);
        commonLib.info("Getting Remaining Data Expiry Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public String getSmsExpiryDate() {
        final String text = getText(pageElements.smsExpiryDate);
        commonLib.info("Getting Remaining SMS Expiry Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public String getVoiceBalance() {
        final String text = getText(pageElements.voiceBalance);
        commonLib.info("Getting Remaining Voice Balance from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public String getDataBalance() {
        final String text = getText(pageElements.dataBalance);
        commonLib.info("Getting Remaining Data Balance from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public String getSmsBalance() {
        final String text = getText(pageElements.smsBalance);
        commonLib.info("Getting Remaining SMS Balance from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public String getLastRechargeDateTime() {
        final String text = getText(pageElements.lastRechargeDateTime);
        commonLib.info("Getting Last Recharge Date from Your Current Balance Widget : " + text.trim());
        return text.trim();
    }

    public WidgetInteraction clickTicketIcon() {
        try {
            commonLib.info("Clicking on Ticket Icon");
            clickAndWaitForLoaderToBeRemoved(pageElements.ticketIcon);
            return new WidgetInteraction(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on Current Plan History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        final String text = getText(pageElements.getTitle);
        log.info("Getting Widget title: " + text);
        return text.toLowerCase();
    }
}
