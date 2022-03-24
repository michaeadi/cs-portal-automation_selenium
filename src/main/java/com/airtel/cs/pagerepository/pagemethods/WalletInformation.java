package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.WalletInformationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

    public class WalletInformation extends BasePage{
        WalletInformationPage pageElements;

        public WalletInformation(WebDriver driver) {
            super(driver);
            pageElements = PageFactory.initElements(driver, WalletInformationPage.class);
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
         * This method is used to check Wallet Information widget  header visible or not
         * @return
         */
        public boolean isWalletInformationHeaderVisible() {
            boolean status = isElementVisible(pageElements.walletWidgetHeader);
            commonLib.info("Is Account Information widget  header visible : " + status);
            return status;
        }

        /**
         * This method is used to check Wallet Information widget visible or not
         * @return
         */
        public boolean isWalletInformationWidgetVisible() {
            boolean status = isElementVisible(pageElements.walletInformationWidget);
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
            boolean status = isElementVisible(pageElements.barringInfoIcon);
            commonLib.info("Is Barring Status info  icon visible : " + status);
            return status;
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
         * This method is used to get Wallet Status
         * @return
         */
        public String getWalletStatus() {
            final String text = getText(pageElements.walletStatus);
            commonLib.info("Getting Wallet status: " + text);
            return text;
        }

        /**
         * This method is used to get Account Created By
         * @return
         */
        public String getWalletCreatedBy() {
            final String text = getText(pageElements.walletCreatedOn);
            commonLib.info("Getting Wallet Created By: " + text);
            return text;
        }

        /**
         * This method is used to get Wallet Category
         * @return
         */
        public String getWalletCategory() {
            final String text = getText(pageElements.walletCategory);
            commonLib.info("Getting Wallet category: " + text);
            return text;
        }

        /**
         * This method is used to get Wallet Modified by
         * @return
         */
        public String getWalletModifiedBy() {
            final String text = getText(pageElements.walletModifiedBy);
            commonLib.info("Getting Wallet Modified By: " + text);
            return text;
        }

        /**
         * This method is used to get Account Modified On
         * @return
         */
        public String getWalletModifiedOn() {
            final String text = getText(pageElements.walletModifiedOn);
            commonLib.info("Getting Wallet Modified On: " + text);
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
        public String getWalletNubanId() {
            final String text = getText(pageElements.walletNubanId);
            commonLib.info("Getting Wallet Nuban Id: " + text);
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
         * This method is used to get FIC Amount
         * @return
         */
        public String getFicAmount() {
            final String text = getText(pageElements.ficAmount);
            commonLib.info("Getting FIC Amount: " + text);
            return text;
        }
}
