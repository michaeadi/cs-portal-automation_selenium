package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.VoucherTabPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class VoucherTab extends BasePage {

    VoucherTabPage pageElements;

    public VoucherTab(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, VoucherTabPage.class);
    }

    public boolean isVoucherTabOpen() {
        commonLib.info("Checking Voucher tab opened");
        return isEnabled(pageElements.tabTitle);
    }

    public boolean isSerialLabel() {
        final boolean state = isEnabled(pageElements.serialLabel);
        commonLib.info("Is Serial label present: " + state);
        return state;
    }

    public boolean isStatusLabel() {
        final boolean state = isEnabled(pageElements.statusLabel);
        commonLib.info("Is Status label present: " + state);
        return state;
    }

    public boolean isSubStatus() {
        final boolean state = isEnabled(pageElements.subStatusLabel);
        commonLib.info("Is Sub Status label present: " + state);
        return state;
    }

    public boolean isRechargeAmt() {
        final boolean state = isEnabled(pageElements.rechargeAmtLabel);
        commonLib.info("Is Recharge Amount label present: " + state);
        return state;
    }

    public boolean isTimeStamp() {
        final boolean state = isEnabled(pageElements.timeStampLabel);
        commonLib.info("Is Time Stamp label present: " + state);
        return state;
    }

    public boolean isSubscriberId() {
        final boolean state = isEnabled(pageElements.subscriberIdLabel);
        commonLib.info("Is Subscriber Id label present: " + state);
        return state;
    }

    public boolean isExpiryDate() {
        final boolean state = isEnabled(pageElements.expiryDateLabel);
        commonLib.info("Is Expiry Date label present: " + state);
        return state;
    }

    public boolean isAgent() {
        final boolean state = isEnabled(pageElements.agentLabel);
        commonLib.info("Is Agent label present: " + state);
        return state;
    }

    public boolean isBatchID() {
        final boolean state = isEnabled(pageElements.batchIdLabel);
        commonLib.info("Is Batch ID label present: " + state);
        return state;
    }

    public boolean isVoucherGroup() {
        final boolean state = isEnabled(pageElements.voucherGroupLabel);
        commonLib.info("Is Voucher Group label present: " + state);
        return state;
    }

    public String getSerialValue() {
        final String text = getText(pageElements.serialNo);
        commonLib.info("Reading Serial value present: " + text);
        return text;
    }

    public String getStatusValue() {
        final String text = getText(pageElements.statusValue);
        commonLib.info("Reading Status value present: " + text);
        return text;
    }

    public String getSubStatus() {
        final String text = getText(pageElements.subStatusValue);
        commonLib.info("Reading Sub Status value present: " + text);
        return text;
    }

    public String getRechargeAmt() {
        final String text = getText(pageElements.rechargeAmtValue);
        commonLib.info("Reading Recharge Amount value present: " + text);
        return text;
    }

    public String getTimeStamp() {
        final String text = getText(pageElements.timeStampValue);
        commonLib.info("Reading Time Stamp value present: " + text);
        return text;
    }

    public String getSubscriberId() {
        final String text = getText(pageElements.subscriberIdValue);
        commonLib.info("Reading Subscriber Id value present: " + text);
        return text;
    }

    public String getExpiryDate() {
        final String text = getText(pageElements.expiryDateValue);
        commonLib.info("Reading Expiry Date value present: " + text);
        return text;
    }

    public String getAgent() {
        final String text = getText(pageElements.agentValue);
        commonLib.info("Reading Agent value present: " + text);
        return text;
    }

    public String getBatchID() {
        final String text = getText(pageElements.batchIdValue);
        commonLib.info("Reading Batch ID value present: " + text);
        return text;
    }

    public String getVoucherGroup() {
        final String text = getText(pageElements.voucherGroupValue);
        commonLib.info("Reading Voucher Group value present: " + text);
        return text;
    }

    public void clickDoneBtn() {
        commonLib.info("Clicking done button");
        click(pageElements.doneBtn);
    }
}
