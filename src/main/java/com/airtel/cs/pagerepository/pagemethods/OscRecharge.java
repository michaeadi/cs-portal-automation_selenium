package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.pagerepository.pageelements.OscRechargePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.COMMENT;

public class OscRecharge extends BasePage {

    OscRechargePage pageElements;

    public OscRecharge(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver,OscRechargePage.class);
    }

    /**
     * This method is use to check voucher tab pop up tab display or not
     * @return true/false
     */
    public boolean isVoucherTabOpen() {
        commonLib.info("Checking Voucher tab opened");
        return isEnabled(pageElements.tabTitle);
    }

    /**
     * This method is use to check voucher serial label display or not
     * @return true/false
     */
    public boolean isSerialLabel() {
        final boolean state = isEnabled(pageElements.serialLabel);
        commonLib.info("Is Serial label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher status label display or not
     * @return true/false
     */
    public boolean isStatusLabel() {
        final boolean state = isEnabled(pageElements.statusLabel);
        commonLib.info("Is Status label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher sub status label display or not
     * @return true/false
     */
    public boolean isSubStatus() {
        final boolean state = isEnabled(pageElements.subStatusLabel);
        commonLib.info("Is Sub Status label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher recharge amount label display or not
     * @return true/false
     */
    public boolean isRechargeAmt() {
        final boolean state = isEnabled(pageElements.rechargeAmtLabel);
        commonLib.info("Is Recharge Amount label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher recharge time stamp label display or not
     * @return true/false
     */
    public boolean isTimeStamp() {
        final boolean state = isEnabled(pageElements.timeStampLabel);
        commonLib.info("Is Time Stamp label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher subscriber id label display or not
     * @return true/false
     */
    public boolean isSubscriberId() {
        final boolean state = isEnabled(pageElements.subscriberIdLabel);
        commonLib.info("Is Subscriber Id label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher expiry date label display or not
     * @return true/false
     */
    public boolean isExpiryDate() {
        final boolean state = isEnabled(pageElements.expiryDateLabel);
        commonLib.info("Is Expiry Date label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher used by agent label display or not
     * @return true/false
     */
    public boolean isAgent() {
        final boolean state = isEnabled(pageElements.agentLabel);
        commonLib.info("Is Agent label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher batch id display or not
     * @return true/false
     */
    public boolean isBatchID() {
        final boolean state = isEnabled(pageElements.batchIdLabel);
        commonLib.info("Is Batch ID label present: " + state);
        return state;
    }

    /**
     * This method is use to check voucher group display or not
     * @return true/false
     */
    public boolean isVoucherGroup() {
        final boolean state = isEnabled(pageElements.voucherGroupLabel);
        commonLib.info("Is Voucher Group label present: " + state);
        return state;
    }

    /**
     * This method is use to get voucher serial number
     * @return String The value
     */
    public String getSerialValue() {
        final String text = getText(pageElements.serialNo);
        commonLib.info("Reading Serial value present: " + text);
        return text;
    }

    /**
     * This method is use to get voucher state value
     * @return String The value
     */
    public String getStatusValue() {
        final String text = getText(pageElements.statusValue);
        commonLib.info("Reading Status value present: " + text);
        return text;
    }

    /**
     * This method is use to get voucher sub status value
     * @return String The value
     */
    public String getSubStatus() {
        final String text = getText(pageElements.subStatusValue);
        commonLib.info("Reading Sub Status value present: " + text);
        return text;
    }

    /**
     * This method is use to get recharge amount value
     * @return String The value
     */
    public String getRechargeAmt() {
        final String text = getText(pageElements.rechargeAmtValue);
        commonLib.info("Reading Recharge Amount value present: " + text);
        return text;
    }

    /**
     * This method is use to get recharge date & time value
     * @return String The value
     */
    public String getTimeStamp() {
        final String text = getText(pageElements.timeStampValue);
        commonLib.info("Reading Time Stamp value present: " + text);
        return text;
    }

    /**
     * This method is use to get subscriber id value
     * @return String The value
     */
    public String getSubscriberId() {
        final String text = getText(pageElements.subscriberIdValue);
        commonLib.info("Reading Subscriber Id value present: " + text);
        return text;
    }

    /**
     * This method is use to get voucher expiry date value
     * @return String The value
     */
    public String getExpiryDate() {
        final String text = getText(pageElements.expiryDateValue);
        commonLib.info("Reading Expiry Date value present: " + text);
        return text;
    }

    /**
     * This method is use to get voucher agent id value
     * @return String The value
     */
    public String getAgent() {
        final String text = getText(pageElements.agentValue);
        commonLib.info("Reading Agent value present: " + text);
        return text;
    }

    /**
     * This method is use to get voucher batch id value
     * @return String The value
     */
    public String getBatchID() {
        final String text = getText(pageElements.batchIdValue);
        commonLib.info("Reading Batch ID value present: " + text);
        return text;
    }

    /**
     * This method is use to get voucher group id value
     * @return String The value
     */
    public String getVoucherGroup() {
        final String text = getText(pageElements.voucherGroupValue);
        commonLib.info("Reading Voucher Group value present: " + text);
        return text;
    }

    /**
     * This method is use to click done button
     */
    public void clickDoneBtn() {
        commonLib.info("Clicking done button");
        clickAndWaitForLoaderToBeRemoved(pageElements.doneBtn);
    }

    /**
     * This method is use to click Cancel button
     */
    public void clickCancelBtn() {
        commonLib.info("Clicking Cancel button");
        clickAndWaitForLoaderToBeRemoved(pageElements.cancelBtn);
    }
    /**
     * This method is used to go to Action Trail tab
     */
    public void goToActionTrail() {
        commonLib.info("Going to click Action Trail tab");
        pages.getOscRecharge().clickHomeTab();
        pages.getOscRecharge().clickViewHistoryTab();
        pages.getOscRecharge().clickActionTrailTab();
    }

    /**
     * This will will route you to action trail's meta info
     */
    public void clickingOnDropDown() {
        clickAndWaitForLoaderToBeRemoved(pageElements.actionTrailLatestDropdown);
    }


    /**
     * This method is used to get action type form action trail
     * @return
     */
    public String getActionType() {
        commonLib.info(getText(pageElements.actionType));
        return getText(pageElements.actionType);
    }

    /**
     * This method is used to get reason form action trail
     * @return
     */
    public String getReason() {
        commonLib.info(getText(pageElements.reason));
        return getText(pageElements.reason);
    }

    /**
     * This method is used to get comment form action trail
     * @return
     */
    public String getComment() {
        commonLib.info(getText(pageElements.comment));
        return getText(pageElements.comment);
    }

    /**
     * This method route and click Home Tab of Customer Dashboard Page
     */
    public void clickHomeTab() {
        commonLib.info("Going to click Home tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.homePage);
    }

    /**
     * This method will route and click view history
     */
    public void clickViewHistoryTab() {
        commonLib.info("Going to click View History tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.viewHistory);
    }

    /**
     * This method will route and click action trail
     */
    public void clickActionTrailTab() {
        commonLib.info("Going to click Action Trail tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.actionTrail);
    }

    /**
     * This method is used to get Recharge amount from action trail drop down
     *
     * @return
     */
    public String getRechargeAmount() {
        commonLib.info(getText(pageElements.rechargeAmount));
        return getText(pageElements.rechargeAmount);
    }

    /**
     * This method is used to get Recharge Msisdn from action trail drop down
     *
     * @return
     */
    public String getRechargeMsisdn() {
        commonLib.info(getText(pageElements.rechargedMsisdn));
        return getText(pageElements.rechargedMsisdn);
    }

    /**
     * This method is used to get Voucher Number from action trail drop down
     *
     * @return
     */
    public String getVoucherNumber() {
        commonLib.info(getText(pageElements.voucherNumber));
        return getText(pageElements.voucherNumber);
    }

    /**
     * This method is used to get text of success message
     *
     * @return text
     */
    public String getSuccessText() {
        final String text = getText(pageElements.successMessage);
        commonLib.info("Getting success pop up text :" + text);
        return text;
    }

    /**
     * This method is used to click on cross icon of success pop up
     */
    public void clickCrossIcon() {
        commonLib.info("Going to click cross icon");
        if (isVisible(pageElements.crossIcon));
        clickWithoutLoader((pageElements.crossIcon));
    }

    /**
     * This method is used to click Overscratch Button
     */
    public void clickOverScratchButton() {
        commonLib.info("Going to click Overscratch Recharge button");
        clickAndWaitForLoaderToBeRemoved(pageElements.overscratchButton);
    }

    /**
     * This method is used to click Recharge Button
     */
    public void clickRechargeButton() {
        commonLib.info("Going to click Recharge button");
        clickAndWaitForLoaderToBeRemoved(pageElements.rechargeButton);
    }

    /**
     * This method is used to click copy msisdn icon to copy msisdn
     */
    public void clickCopyMsisdnIcon() {
        commonLib.info("Going to click Copy Msisn icon");
        clickWithoutLoader(pageElements.msisdnCopyIcon);
    }

    /**
     * This method is used to enter pin
     * @param pin The comment
     */
    public void enterPin(String pin) {
        commonLib.info("Entering Pin: " + pin);
        enterText(pageElements.enterPin, pin);
    }

    /**
     * This method is used to perform OSC Recharge by searching voucher , entering msisdn and pin
     */
    public void performOscRecharge(String pin , String voucher ) throws InterruptedException {
        commonLib.info("Going to  enter msisdn , pin and then clicking Recharge Button ");
        pages.getOscRecharge().searchVoucher(voucher);
        pages.getOscRecharge().clickOverScratchButton();
        pages.getOscRecharge().clickCopyMsisdnIcon();
        pages.getOscRecharge().enterPin(pin);
        pages.getOscRecharge().clickRechargeButton();
    }

    /**
     * This method is used to search voucher
     */
    public void searchVoucher(String voucherId ) throws InterruptedException {
        commonLib.info("Going to  search voucher");
        pages.getRechargeHistoryWidget().writeVoucherId(voucherId);
        pages.getRechargeHistoryWidget().clickSearchBtn();
    }

    /**
     * This method is used to perform OSC Recharge by entering msisdn
     */
    public void performOscRechargeWithoutPin(String voucherid) throws InterruptedException {
        commonLib.info("Going to enter msisdn  and clicking Recharge Button");
        pages.getOscRecharge().searchVoucher(voucherid);
        pages.getOscRecharge().clickOverScratchButton();
        pages.getOscRecharge().clickCopyMsisdnIcon();
        pages.getOscRecharge().clickRechargeButton();
    }


    /**
     * This method is used to split activation code
     */
    public String splitActivationCode(String activationCode) {
        commonLib.info("Splitting Activation code");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < activationCode.length()-9; i++) {
            sb.append(activationCode.charAt(i));
        }
        return (sb.toString());
    }

    /**
     * This method is used to check success pop visible or not
     *
     * @return true/false
     */
    public Boolean isSuccessPopUpVisible() {
        waitVisibility(pageElements.successMessage);
        final boolean state = isElementVisible(pageElements.successMessage);
        commonLib.info("Is confirmation Pop Up visible :" + state);
        return state;

    }

}
