package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.VoucherTabPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class VoucherTabPage extends BasePage {

    VoucherTabPageElements pageElements;

    public VoucherTabPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, VoucherTabPageElements.class);
    }

    public boolean isVoucherTabOpen() {
        UtilsMethods.printInfoLog("Checking Voucher tab opened");
        return checkState(pageElements.tabTitle);
    }

    public boolean isSerialLabel() {
        UtilsMethods.printInfoLog("Is Serial label present: " + checkState(pageElements.serialLabel));
        return checkState(pageElements.serialLabel);
    }

    public boolean isStatusLabel() {
        UtilsMethods.printInfoLog("Is Status label present: " + checkState(pageElements.statusLabel));
        return checkState(pageElements.statusLabel);
    }

    public boolean isSubStatus() {
        UtilsMethods.printInfoLog("Is Sub Status label present: " + checkState(pageElements.subStatusLabel));
        return checkState(pageElements.subStatusLabel);
    }

    public boolean isRechargeAmt() {
        UtilsMethods.printInfoLog("Is Recharge Amount label present: " + checkState(pageElements.rechargeAmtLabel));
        return checkState(pageElements.rechargeAmtLabel);
    }

    public boolean isTimeStamp() {
        UtilsMethods.printInfoLog("Is Time Stamp label present: " + checkState(pageElements.timeStampLabel));
        return checkState(pageElements.timeStampLabel);
    }

    public boolean isSubscriberId() {
        UtilsMethods.printInfoLog("Is Subscriber Id label present: " + checkState(pageElements.subscriberIdLabel));
        return checkState(pageElements.subscriberIdLabel);
    }

    public boolean isExpiryDate() {
        UtilsMethods.printInfoLog("Is Expiry Date label present: " + checkState(pageElements.expiryDateLabel));
        return checkState(pageElements.expiryDateLabel);
    }

    public boolean isAgent() {
        UtilsMethods.printInfoLog("Is Agent label present: " + checkState(pageElements.agentLabel));
        return checkState(pageElements.agentLabel);
    }

    public boolean isBatchID() {
        UtilsMethods.printInfoLog("Is Batch ID label present: " + checkState(pageElements.batchIdLabel));
        return checkState(pageElements.batchIdLabel);
    }

    public boolean isVoucherGroup() {
        UtilsMethods.printInfoLog("Is Voucher Group label present: " + checkState(pageElements.voucherGroupLabel));
        return checkState(pageElements.voucherGroupLabel);
    }

    public String getSerialValue() {
        UtilsMethods.printInfoLog("Reading Serial value present: " + readText(pageElements.serialNo));
        return readText(pageElements.serialNo);
    }

    public String getStatusValue() {
        UtilsMethods.printInfoLog("Reading Status value present: " + readText(pageElements.statusValue));
        return readText(pageElements.statusValue);
    }

    public String getSubStatus() {
        UtilsMethods.printInfoLog("Reading Sub Status value present: " + readText(pageElements.subStatusValue));
        return readText(pageElements.subStatusValue);
    }

    public String getRechargeAmt() {
        UtilsMethods.printInfoLog("Reading Recharge Amount value present: " + readText(pageElements.rechargeAmtValue));
        return readText(pageElements.rechargeAmtValue);
    }

    public String getTimeStamp() {
        UtilsMethods.printInfoLog("Reading Time Stamp value present: " + readText(pageElements.timeStampValue));
        return readText(pageElements.timeStampValue);
    }

    public String getSubscriberId() {
        UtilsMethods.printInfoLog("Reading Subscriber Id value present: " + readText(pageElements.subscriberIdValue));
        return readText(pageElements.subscriberIdValue);
    }

    public String getExpiryDate() {
        UtilsMethods.printInfoLog("Reading Expiry Date value present: " + readText(pageElements.expiryDateValue));
        return readText(pageElements.expiryDateValue);
    }

    public String getAgent() {
        UtilsMethods.printInfoLog("Reading Agent value present: " + readText(pageElements.agentValue));
        return readText(pageElements.agentValue);
    }

    public String getBatchID() {
        UtilsMethods.printInfoLog("Reading Batch ID value present: " + readText(pageElements.batchIdValue));
        return readText(pageElements.batchIdValue);
    }

    public String getVoucherGroup() {
        UtilsMethods.printInfoLog("Reading Voucher Group value present: " + readText(pageElements.voucherGroupValue));
        return readText(pageElements.voucherGroupValue);
    }

    public void clickDoneBtn() {
        UtilsMethods.printInfoLog("Clicking done button");
        click(pageElements.doneBtn);
    }
}
