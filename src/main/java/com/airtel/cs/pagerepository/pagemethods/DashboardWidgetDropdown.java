package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.DashboardWidgetDropdownPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardWidgetDropdown extends BasePage {
    DashboardWidgetDropdownPage pageElements;

    public DashboardWidgetDropdown(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DashboardWidgetDropdownPage.class);
    }

    String openingBracket = "[";
    String closingBracket = "]";

    /**
     * This method is used to check widget dropdown visible or not
     *
     * @return
     */
    public boolean isWidgetDropdownVisible() {
        boolean status = isElementVisible(pageElements.widgetDropdown);
        commonLib.info("Is widget dropdown visible : " + status);
        return status;
    }

    /**
     * This method is used to get Current msisdn value
     *
     * @return
     */
    public String getCurrentMsisdnValue() {
        final String text = getText(pageElements.currentMsisdnValue);
        commonLib.info("Getting  Current msisdn value : " + text);
        return text;
    }

    /**
     * This method is used to click dropdown arrow
     */
    public void clickDropdownArrow() {
        commonLib.info("Going to click dropdown arrow");
        if (isVisible(pageElements.dropdownArrow))
            clickWithoutLoader(pageElements.dropdownArrow);
        else
            commonLib.fail("Dropdown arrow is not visible", true);
    }


    /**
     * This method is used to get linked wallet message
     *
     * @return
     */
    public String getLinkedWalletMessage() {
        final String text = getText(pageElements.walletLinked);
        commonLib.info("Getting linked wallet message : " + text);
        return text;
    }

    /**
     * This method is used to get linked account message
     *
     * @return
     */
    public String getLinkedAccountMessage() {
        final String text = getText(pageElements.accountLinked);
        commonLib.info("Getting linked account message : " + text);
        return text;
    }

    /**
     * This method is used to get currently Selected Msisdn Header
     *
     * @return
     */
    public String getCurrentlySelectedMsisdnHeader() {
        final String text = getText(pageElements.currentlySelectedMsisdnHeader);
        commonLib.info("Getting currently Selected Msisdn Header: " + text);
        return text;
    }


    /**
     * This method is used to get currently Selected Msisdn Value
     *
     * @return
     */
    public String getCurrentlySelectedMsisdnValue() {
        final String text = getText(pageElements.currentlySelectedMsisdnValue);
        commonLib.info("Getting currently Selected Msisdn Value: " + text);
        return text;
    }

    /**
     * This method is used to get Nuban Id from Wallet and Account Information widget
     *
     * @return
     */
    public String getNubanId() {
        final String text = getText(pageElements.nubanId);
        commonLib.info("Getting Nuban id  : " + text);
        return text;
    }


    /**
     * This method is used to get Account Nuban Id from widget dropdown table
     *
     * @return
     */
    public String getAccountNubanId(int row) {
        final String text = getText(By.xpath(pageElements.accountRow + openingBracket + row + pageElements.nubanIdColumn));
        commonLib.info("Getting Account Nuban id for row : " + row);
        return text;
    }

    /**
     * This method is used to get Wallet Nuban Id from widget dropdown table
     *
     * @return
     */
    public String getWalletNubanId(int row) {
        final String text = getText(By.xpath(pageElements.walletRow + openingBracket + row + pageElements.nubanIdColumn));
        commonLib.info("Getting Wallet Nuban id for row : " + row);
        return text;
    }


    /**
     * This method will give the no. of rows for linked Accounts
     *
     * @return
     */
    public int checkAccountRowSize() {
        final List<WebElement> rows = getElementsListFromBy((By.xpath(pageElements.accountRow)));
        return rows.size();
    }

    /**
     * This method will give the no. of rows linked Wallets
     *
     * @return
     */
    public int checkWalletRowSize() {
        final List<WebElement> rows = getElementsListFromBy((By.xpath(pageElements.walletRow)));
        return rows.size();
    }

    /**
     * This method is used to hover over currently selected Account
     */
    public void hoverCurrentlySelectedAccount(int row) {
        commonLib.info("Hovering over currently selected Account");
        hoverOverElement(By.xpath(pageElements.accountRow + openingBracket + row + closingBracket));
    }

    /**
     * This method is used to hover over currently selected Wallet
     */
    public void hoverCurrentlySelectedWallet(int row) {
        commonLib.info("Hovering over currently selected Wallet");
        hoverOverElement(By.xpath(pageElements.walletRow + openingBracket + row + closingBracket));
    }

    /**
     * This method is used to get hover message for currently selected Wallet/Account
     */
    public String getHoverMessage() {
        commonLib.info("Getting message on hovering currently selected Wallet/Account");
        String text = getText(pageElements.hoverMessage);
        return text;
    }

    /**
     * This method is used to get click CTA of Account
     *
     * @return
     */
    public void clickCtaOfAccount(int row) {
        if (isVisible(By.xpath(pageElements.accountRow + openingBracket + row + pageElements.selectRow))) {
            commonLib.info("Clicking Select of Account for row : " + row);
            clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.accountRow + openingBracket + row + pageElements.selectRow));
        } else
            commonLib.fail("CTA for Account is not visible", true);
    }

    /**
     * This method is used to get click CTA of Wallet
     *
     * @return
     */
    public void clickCtaOfWallet(int row) {
        if (isVisible(By.xpath(pageElements.walletRow + openingBracket + row + pageElements.selectRow))) {
            commonLib.info("Clicking Select of Wallet for row : " + row);
            clickAndWaitForLoaderToBeRemoved(By.xpath(pageElements.walletRow + openingBracket + row + pageElements.selectRow));
        } else
            commonLib.fail("CTA for Wallet is not visible", true);
    }
}
