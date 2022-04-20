package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.pagerepository.pageelements.AmLinkedWalletsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class AmLinkedWallets extends BasePage {
    AmLinkedWalletsPage pageElements;

    public AmLinkedWallets(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmLinkedWalletsPage.class);
    }

    /**
     * This method is used to check AM Transactions Widget visible or not
     * @return
     */
    public boolean isAmTransactionsWidgetVisible() {
        boolean status = isElementVisible(pageElements.amTransactionsWidget);
        commonLib.info("Is AM Transactions Widget visible : " + status);
        return status;
    }

    /**
     * This method is used to check more icon visible or not
     * @return
     */
    public boolean isMoreIconVisible() {
        boolean status = isElementVisible(pageElements.moreIcon);
        commonLib.info("Is more icon  visible : " + status);
        return status;
    }

    /**
     * This method is used to check AM Profile Details widget  visible or not
     * @return
     */
    public boolean isAmProfileDetailsDetailWidgetVisible() {
        boolean status = isElementVisible(pageElements.amProfileDetailsWidget);
        commonLib.info("Is Am Profile Details Widget visible : " + status);
        return status;
    }

    /**
     * This method is used to click on more icon
     *
     * @return
     */
    public void clickMoreIcon() {
        commonLib.info("Going to click detailed icon");
        clickAndWaitForLoaderToBeRemoved(pageElements.moreIcon);
    }

    /**
     * This method is used to get Footer auuid of Wallets widget
     * @return
     */
    public String getFooterAuuid() {
        String result = getText(pageElements.footerAuuid);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is used to get Middle auuid of Wallets widget
     * @return
     */
    public String getMiddleAuuid() {
        String result = getAttribute(pageElements.middleAuuid, "data-auuid", false);
        commonLib.info("Getting footer auuid :" + result);
        return result;
    }

    /**
     * This method is use to check no result found icon visible or not
     *
     * @return true/false
     */
    public boolean isNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.noResultFound);
        commonLib.info("Is error icon visible when there is no data available in Linked Wallets Widget : " + visible);
        return visible;
    }

    /**
     * This method is used to check error message visible or not
     *
     * @return true/false
     */
    public Boolean isWidgetErrorMessageVisible() {
        Boolean status=(isElementVisible(pageElements.widgetErrorMessage) || isElementVisible(pageElements.unableToFetchDataMessage));
        commonLib.info("Is error message visible when there is widget error :" + status);
        return status;
    }

    /**
     * This method is used to get no result found message
     *
     * @return String The String
     */
    public String getNoResultFoundMessage() {
        final String text = getText(pageElements.noResultFoundMessage);
        commonLib.info("Getting error message when there is no data available in Linked Wallets Widget : " + text);
        return text;
    }

    /**
     * This method is used get first header value based on passed row and column
     * @param row
     * @param column
     * @return
     */
    public String getRowValue(int row, int column) {
        String result;
        result = getText(By.xpath(pageElements.dataRows + row + pageElements.dataColumns + column + pageElements.dataValue));
        commonLib.info("Reading Row(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used to check Wallet Type label visible or not
     * @return
     */
    public boolean isWalletTypeVisible() {
        boolean status = isElementVisible(pageElements.walletType);
        commonLib.info("Is Wallet Type visible : " + status);
        return status;
    }

    /**
     * This method is used to check Profile Id label visible or not
     * @return
     */
    public boolean isProfileIdVisible() {
        boolean status = isElementVisible(pageElements.profileId);
        commonLib.info("Is Profile Id visible : " + status);
        return status;
    }

    /**
     * This method is used to check Balance label visible or not
     * @return
     */
    public boolean isBalanceVisible() {
        boolean status = isElementVisible(pageElements.balance);
        commonLib.info("Is Balance visible : " + status);
        return status;
    }

    /**
     * This method is used to check FIC label visible or not
     * @return
     */
    public boolean isFicVisible() {
        boolean status = isElementVisible(pageElements.fic);
        commonLib.info("Is FIC visible : " + status);
        return status;
    }

    /**
     * This method is used to check Primary label visible or not
     * @return
     */
    public boolean isPrimaryVisible() {
        boolean status = isElementVisible(pageElements.primary);
        commonLib.info("Is Primary visible : " + status);
        return status;
    }

    /**
     * This method is used to check TotalCredit label visible or not
     * @return
     */
    public boolean isTotalCreditVisible() {
        boolean status = isElementVisible(pageElements.totalCredit);
        commonLib.info("Is TotalCredit visible : " + status);
        return status;
    }

    /**
     * This method is used to check Total Debit label visible or not
     * @return
     */
    public boolean isTotalDebitVisible() {
        boolean status = isElementVisible(pageElements.totalDebit);
        commonLib.info("Is Total Debit visible : " + status);
        return status;
    }

    /**
     * This method is used to check Frozen Amount label visible or not
     * @return
     */
    public boolean isFrozenAmountVisible() {
        boolean status = isElementVisible(pageElements.frozenAmount);
        commonLib.info("Is Frozen Amount visible : " + status);
        return status;
    }

    /**
     * This method is used to check Wallets tab visible or not
     * @return
     */
    public boolean isWalletsVisible() {
        boolean status = isElementVisible(pageElements.wallets);
        commonLib.info("Is Wallets visible : " + status);
        return status;
    }

    /**
     * This method is used to get total no. of rows
     */
    public int getNoOfRows()
    {
        if(isVisibleContinueExecution(pageElements.totalRows))
        {
            List<WebElement> list= returnListOfElement(pageElements.totalRows);
            return list.size();
        }else {
            commonLib.warning("No Data is available under Wallets  Widget");
            return 0;
        }
    }

    /**
     * This method is used to click sms logs tab
     * @return
     */
    public void clickSmsLogsTab() {
        commonLib.info("Going to click SMS Logs tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.smsLogs);
    }
}

