package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.AmTcpLimitsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AmTcpLimits extends BasePage{
    AmTcpLimitsPage pageElements;
    public AmTcpLimits(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, AmTcpLimitsPage.class);
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
     * This method is used to check AM Profile Details tab  visible or not
     * @return
     */
    public boolean isAmProfileDetailsTabVisible() {
        boolean status = isElementVisible(pageElements.amProfileDetailsTab);
        commonLib.info("Is Am Profile Details tab  visible : " + status);
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
     * This method is used to check Wallets visible or not
     * @return
     */
    public boolean isWalletsVisible() {
        boolean status = isElementVisible(pageElements.wallets);
        commonLib.info("Is Wallets visible : " + status);
        return status;
    }

    /**
     * This method is used to check Action visible or not
     * @return
     */
    public boolean isTcpLimitsIconVisible() {
        boolean status = isElementVisible(pageElements.icon);
        commonLib.info("Is icon to open TCP Limits  visible : " + status);
        return status;
    }

    /**
     * This method is used to check TCP Limits Widget visible or not
     * @return
     */
    public boolean isTcpLimitsVisible() {
        boolean status = isElementVisible(pageElements.tcpLimits);
        commonLib.info("Is Action visible : " + status);
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
     * This method is used to click collapsable icon to open tcp limits
     *
     * @return
     */
    public void clickTcpLLimitsIcon() {
        commonLib.info("Going to click collapsable  icon to open tcp limits ");
        clickWithoutLoader(pageElements.icon);
    }


    /**
     * This method is used to get no result found message
     *
     * @return String The String
     */
    public String getNoResultFoundMessage() {
        final String text = getText(pageElements.noResultFoundMessage);
        commonLib.info("Getting error message when there is no data available in Tcp Limits for the msisdn " + text);
        return text;
    }

    /**
     * This method is use to check no result found icon visible or not
     *
     * @return true/false
     */
    public boolean isNoResultFoundVisible() {
        final boolean visible = isElementVisible(pageElements.noResultFound);
        commonLib.info("Is error visible when is no data available in Tcp Limits for the msisdn : " + visible);
        return visible;
    }

    /**
     * This method is used to check error message visible or not
     *
     * @return true/false
     */
    public Boolean isWidgetErrorMessageVisible() {
        boolean status=(isElementVisible(pageElements.widgetErrorMessage) || isElementVisible(pageElements.unableToFetchDataMessage));
        commonLib.info("Is error message visible when there is widget error" + status);
        return status;
    }

    /**
     * This method is used to check Transfer Profile Details visible or not
     * @return
     */
    public boolean isTransferProfileDetailsVisible() {
        boolean status = isElementVisible(pageElements.transferProfileDetails);
        commonLib.info("Is Transfer Profile Details visible : " + status);
        return status;
    }

    /**
     * This method is used to check Payment Method Threshold Details visible or not
     * @return
     */
    public boolean isPaymentMethodThresholdDetailsVisible() {
        boolean status = isElementVisible(pageElements.paymentMethodThresholdDetails);
        commonLib.info("Is Payment Method Threshold Details visible : " + status);
        return status;
    }

    /**
     * This method is used to check profile Id visible or not
     * @return
     */
    public boolean isProfileIdVisible() {
        boolean status = isElementVisible(pageElements.profileId);
        commonLib.info("Is profile Id visible : " + status);
        return status;
    }

    /**
     * This method is used to check currency visible or not
     * @return
     */
    public boolean isCurrencyVisible() {
        boolean status = isElementVisible(pageElements.currency);
        commonLib.info("Is currency  visible : " + status);
        return status;
    }

    /**
     * This method is used to check profile name  visible or not
     * @return
     */
    public boolean isProfileNameVisible() {
        boolean status = isElementVisible(pageElements.profileName);
        commonLib.info("Is profile name visible : " + status);
        return status;
    }

    /**
     * This method is used to check Minimum Residual Balance  visible or not
     * @return
     */
    public boolean isMinimumResidualBalanceVisible() {
        boolean status = isElementVisible(pageElements.minimumResidualBalance);
        commonLib.info("Is Minimum Residual Balance visible : " + status);
        return status;
    }

    /**
     * This method is used to check Minimum Transfer Allowed visible or not
     * @return
     */
    public boolean isMinimumTransferAllowedVisible() {
        boolean status = isElementVisible(pageElements.minimumTransferAllowed);
        commonLib.info("Is Minimum Transfer Allowed  visible : " + status);
        return status;
    }

    /**
     * This method is used to check Maximum Balance  visible or not
     * @return
     */
    public boolean isMaximumBalanceVisible() {
        boolean status = isElementVisible(pageElements.maximumBalance);
        commonLib.info("Is Maximum Balance visible : " + status);
        return status;
    }

    /**
     * This method is used to check Payee Tcp Limits visible or not
     * @return
     */
    public boolean isPayeeTcpLimitsVisible() {
        boolean status = isElementVisible(pageElements.payeeTcpLimits);
        commonLib.info("Is Payee Tcp Limits visible : " + status);
        return status;
    }

    /**
     * This method is used to check Payee visible or not
     * @return
     */
    public boolean isPayeeVisible() {
        boolean status = isElementVisible(pageElements.payee);
        commonLib.info("Is Payee visible : " + status);
        return status;
    }

    /**
     * This method is used to check Minimum Transaction Amount  visible or not
     * @return
     */
    public boolean isMinimumTransactionAmountVisible() {
        boolean status = isElementVisible(pageElements.minimumTransactionAmount);
        commonLib.info("Is Minimum Transaction Amount visible : " + status);
        return status;
    }
    /**
     * This method is used to check Maximum Transaction Amount  visible or not
     * @return
     */
    public boolean isMaximumTransactionAmountVisible() {
        boolean status = isElementVisible(pageElements.maximumTransactionAmount);
        commonLib.info("Is Maximum Transaction Amount visible : " + status);
        return status;
    }

    /**
     * This method is used to check Payer Tcp Limits visible or not
     * @return
     */
    public boolean isPayerTcpLimitsVisible() {
        boolean status = isElementVisible(pageElements.payerTcpLimits);
        commonLib.info("Is Payer Tcp Limits visible : " + status);
        return status;
    }

    /**
     * This method is used to check Payer visible or not
     * @return
     */
    public boolean isPayerVisible() {
        boolean status = isElementVisible(pageElements.payer);
        commonLib.info("Is Payer visible : " + status);
        return status;
    }

    /**
     * This method is used to click Payer toggle button
     *
     * @return
     */
    public void clickPayer() {
        commonLib.info("Going to click payer ");
        clickWithoutLoader(pageElements.payer);
    }

    /**
     * This method is used to click Payee toggle button
     *
     * @return
     */
    public void clickPayee() {
        commonLib.info("Going to click payee ");
        clickWithoutLoader(pageElements.payee);
    }

    /**
     * This method is used get first header value of Threshold Details based on passed row
     * @param row
     * @return
     */
    public String getThresholdDetailsValue(int row) {
        String result;
        result = getText(By.xpath(pageElements.thresholdDetailsRow + row + pageElements.thresholdDetailsColumn));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used get header value of Transfer Details based on passed row
     * @param row
     * @return
     */
    public String getTransferDetailsValue(int row) {
        String result;
        result = getText(By.xpath(pageElements.transferDetailsRow + row + pageElements.transferDetailColumn));
        commonLib.info("Reading Value(" + row + "): " + result);
        return result;
    }

    /**
     * This method is used to check Payer visible or not
     * @return
     */
    public boolean isP2PVisible() {
        boolean status = isElementVisible(pageElements.p2p);
        commonLib.info("Is P2P visible : " + status);
        return status;
    }

    /**
     * This method is used to check USSD visible or not
     * @return
     */
    public boolean isUssdVisible() {
        boolean status = isElementVisible(pageElements.ussd);
        commonLib.info("Is USSD visible : " + status);
        return status;
    }

    /**
     * This method is used to check WEB visible or not
     * @return
     */
    public boolean isWebVisible() {
        boolean status = isElementVisible(pageElements.web);
        commonLib.info("Is WEB visible : " + status);
        return status;
    }

    /**
     * This method is used to check WebService visible or not
     * @return
     */
    public boolean isWebServiceVisible() {
        boolean status = isElementVisible(pageElements.webService);
        commonLib.info("Is WebService visible : " + status);
        return status;
    }

    /**
     * This method is used to check IVR visible or not
     * @return
     */
    public boolean isIvrVisible() {
        boolean status = isElementVisible(pageElements.ivr);
        commonLib.info("Is IVR visible : " + status);
        return status;
    }

    /**
     * This method is used to check ALL visible or not
     * @return
     */
    public boolean isAllVisible() {
        boolean status = isElementVisible(pageElements.all);
        commonLib.info("Is ALL visible : " + status);
        return status;
    }

    /**
     * This method is used to check BANK visible or not
     * @return
     */
    public boolean isBankVisible() {
        boolean status = isElementVisible(pageElements.bank);
        commonLib.info("Is BANK visible : " + status);
        return status;
    }

    /**
     * This method is used to check STK visible or not
     * @return
     */
    public boolean isStkVisible() {
        boolean status = isElementVisible(pageElements.stk);
        commonLib.info("Is STK visible : " + status);
        return status;
    }

    /**
     * This method is used to click on WEB Bearer
     * @return
     */
    public void clickWeb() {
        commonLib.info("Going to click WEB Bearer");
        clickWithoutLoader(pageElements.web);
    }

    /**
     * This method is used to click on STK Bearer
     * @return
     */
    public void clickStk() {
        commonLib.info("Going to click STK Bearer ");
        clickWithoutLoader(pageElements.stk);
    }

    /**
     * This method is used to click on ALL Bearer
     * @return
     */
    public void clickAll() {
        commonLib.info("Going to click ALL Bearer");
        clickWithoutLoader(pageElements.all);
    }

    /**
     * This method is used to click on WebService Bearer
     * @return
     */
    public void clickWebService() {
        commonLib.info("Going to click WebService Bearer");
        clickWithoutLoader(pageElements.web);
    }

    /**
     * This method is used to click on Ivr Bearer
     * @return
     */
    public void clickIvr() {
        commonLib.info("Going to click IVR Bearer");
        clickWithoutLoader(pageElements.ivr);
    }

    /**
     * This method is used to click on Ussd Bearer Type
     * @return
     */
    public void clickUssd() {
        commonLib.info("Going to click USSD Bearer Type ");
        clickWithoutLoader(pageElements.ussd);
    }

    /**
     * This method is used to click on Bank Bearer Type
     * @return
     */
    public void clickBank() {
        commonLib.info("Going to click BANK Bearer Type ");
        clickWithoutLoader(pageElements.bank);
    }

}

