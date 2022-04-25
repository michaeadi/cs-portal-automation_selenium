package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AccountInformationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AccountInformation extends BasePage{
    AccountInformationPage pageElements;

    public AccountInformation(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver,AccountInformationPage.class);
    }

    /**
     * This method is used to check Account Information widget  header visible or not
     * @return
     */
    public boolean isAccountInformationHeaderVisible() {
        boolean status = isElementVisible(pageElements.accountWidgetHeader);
        commonLib.info("Is Account Information widget  header visible : " + status);
        return status;
    }


    /**
     * This method is used to check Account Information widget visible or not
     * @return
     */
    public boolean isAccountInformationWidgetVisible() {
        boolean status = isElementVisible(pageElements.accountInformationWidget);
        commonLib.info("Is Account Information widget  visible : " + status);
        return status;
    }

    /**
     * This method is used to check Detailed Account visible or not
     * @return
     */
    public boolean isDetailedIconVisible() {
        boolean status = isElementVisible(pageElements.detailedIcon);
        commonLib.info("Is Detailed icon visible : " + status);
        return status;
    }

    /**
     * This method is used to check Barring Status Info icon visible or not
     * @return
     */
    public boolean isBarringInfoIconVisible() {
        boolean status = isElementVisible(pageElements.barringStatus);
        commonLib.info("Is Barring Status info  icon visible : " + status);
        return status;
    }

    /**
     * This method is used to check footer auuid visible or not
     * @return
     */
    public String getFooterAuuid() {
        String result = null;
        result = getText(pageElements.footerAuuid);
        return result;
    }

    /**
     * This method is used to check middle auuid  visible or not
     * @return
     */
    public String getMiddleAuuid() {
        String result = null;
        result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        return result;
    }

    /**
     * This method is used to check balance info icon visible or not
     * @return
     */
    public boolean isBalanceInfoIconVisible() {
        boolean status = isElementVisible(pageElements.balanceInfoIcon);
        commonLib.info("Is Balance info icon visible : " + status);
        return status;
    }

    /**
     * This method is used to get Account Status
     * @return
     */
    public String getAccountStatus() {
        final String text = getText(pageElements.accountStatus);
        commonLib.info("Getting Account status: " + text);
        return text;
    }

    /**
     * This method is used to get Account Created By
     * @return
     */
    public String getAccountCreatedOn() {
        final String text = getText(pageElements.accountCreatedOn);
        commonLib.info("Getting Account Created By: " + text);
        return text;
    }

    /**
     * This method is used to get Account Created By
     * @return
     */
    public String getAccountCreatedBy() {
        final String text = getText(pageElements.accountCreatedBy);
        commonLib.info("Getting Account Created By: " + text);
        return text;
    }

    /**
     * This method is used to get Account Category
     * @return
     */
    public String getAccountCategory() {
        final String text = getText(pageElements.accountCategory);
        commonLib.info("Getting Account category: " + text);
        return text;
    }

    /**
     * This method is used to get Account Modified by
     * @return
     */
    public String getAccountModifiedBy() {
        final String text = getText(pageElements.accountModifiedBy);
        commonLib.info("Getting Account Modified By: " + text);
        return text;
    }

    /**
     * This method is used to get Account Modified On
     * @return
     */
    public String getAccountModifiedOn() {
        final String text = getText(pageElements.accountModifiedOn);
        commonLib.info("Getting Account Modified On: " + text);
        return text;
    }

    /**
     * This method is used to get Onboarding Channel
     * @return
     */
    public String getOnboardingChannel() {
        final String text = getText(pageElements.onboardingChannel);
        commonLib.info("Getting Onboarding Channel: " + text);
        return text;
    }

    /**
     * This method is used to get Account Nuabn Id
     * @return
     */
    public String getAccountNubanId() {
        final String text = getText(pageElements.accountNubanId);
        commonLib.info("Getting Nuban Id: " + text);
        return text;
    }

    /**
     * This method is used to get Barring Status
     * @return
     */
    public String getBarringStatus() {
        final String text = getText(pageElements.barringStatus);
        commonLib.info("Getting Barring status: " + text);
        return text;
    }

    /**
     * This method is used to get Security Questions Set
     * @return
     */
    public String getSecurityQuestionsSet() {
        final String text = getText(pageElements.securityQuestionsSet);
        commonLib.info("Getting Security Questions Set: " + text);
        return text;
    }

    /**
     * This method is used to get Security Questions Configured
     * @return
     */
    public String getSecurityQuestionsConfigured() {
        final String text = getText(pageElements.securityQuestionsConfigured);
        commonLib.info("Getting Security Questions Configured: " + text);
        return text;
    }

    /**
     * This method is used to get Balance
     * @return
     */
    public String getBalance() {
        final String text = getText(pageElements.balance);
        commonLib.info("Getting Balance: " + text);
        return text;
    }

    /**
     * This method is used to get Frozen Amount
     * @return
     */
    public String getFrozenAmount() {
        final String text = getText(pageElements.frozenAmount);
        commonLib.info("Getting Frozen Amount: " + text);
        return text;
    }

    /**
     * This method is use to hover on Balance info icon
     */
    public void hoverOnBalanceInfoIcon() {
        commonLib.info("Hover on Balance Info icon");
        hoverOverElement(pageElements.balanceHoverIcon);
    }
}
