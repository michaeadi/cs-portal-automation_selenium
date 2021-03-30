package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VoucherTabPOM extends BasePage {

    By tabTitle = By.xpath("//span[contains(text(),'Voucher Details')]");
    By serialLabel = By.xpath("//div[@class=\"serialno\"]//span[1]");
    By serialNo = By.xpath("//div[@class=\"serialno\"]//span[2]");
    By statusLabel = By.xpath("//div[@class=\"pop-up-block\"]//div[2]//span[1]");
    By statusValue = By.xpath("//div[@class=\"pop-up-block\"]//div[2]//span[2]");
    By subStatusLabel = By.xpath("//div[@class=\"pop-up-block\"]//div[3]//span[1]");
    By subStatusValue = By.xpath("//div[@class=\"pop-up-block\"]//div[3]//span[2]");
    By rechargeAmtLabel = By.xpath("//div[@class=\"pop-up-block\"]//div[4]//span[1]");
    By rechargeAmtValue = By.xpath("//div[@class=\"pop-up-block\"]//div[4]//span[2]");
    By timeStampLabel = By.xpath("//div[@class=\"pop-up-block\"]//div[5]//span[1]");
    By timeStampValue = By.xpath("//div[@class=\"pop-up-block\"]//div[5]//span[2]");
    By subscriberIdLabel = By.xpath("//div[@class=\"pop-up-block\"]//div[6]//span[1]");
    By subscriberIdValue = By.xpath("//div[@class=\"pop-up-block\"]//div[6]//span[2]");
    By expiryDateLabel = By.xpath("//div[@class=\"pop-up-block\"]//div[7]//span[1]");
    By expiryDateValue = By.xpath("//div[@class=\"pop-up-block\"]//div[7]//span[2]");
    By agentLabel = By.xpath("//div[@class=\"agent\"]//div[1]//span[1]");
    By agentValue = By.xpath("//div[@class=\"agent\"]//div[1]//span[2]");
    By batchIdLabel = By.xpath("//div[@class=\"agent\"]//div[2]//span[1]");
    By batchIdValue = By.xpath("//div[@class=\"agent\"]//div[2]//span[2]");
    By voucherGroupLabel = By.xpath("//div[@class=\"agent\"]//div[3]//span[1]");
    By voucherGroupValue = By.xpath("//div[@class=\"agent\"]//div[3]//span[2]");
    By doneBtn = By.xpath("//button//span[contains(text(),'Done')]");

    public VoucherTabPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isVoucherTabOpen() {
        UtilsMethods.printInfoLog("Checking Voucher tab opened");
        return checkState(tabTitle);
    }

    public boolean isSerialLabel() {
        UtilsMethods.printInfoLog("Is Serial label present: " + checkState(serialLabel));
        return checkState(serialLabel);
    }

    public boolean isStatusLabel() {
        UtilsMethods.printInfoLog("Is Status label present: " + checkState(statusLabel));
        return checkState(statusLabel);
    }

    public boolean isSubStatus() {
        UtilsMethods.printInfoLog("Is Sub Status label present: " + checkState(subStatusLabel));
        return checkState(subStatusLabel);
    }

    public boolean isRechargeAmt() {
        UtilsMethods.printInfoLog("Is Recharge Amount label present: " + checkState(rechargeAmtLabel));
        return checkState(rechargeAmtLabel);
    }

    public boolean isTimeStamp() {
        UtilsMethods.printInfoLog("Is Time Stamp label present: " + checkState(timeStampLabel));
        return checkState(timeStampLabel);
    }

    public boolean isSubscriberId() {
        UtilsMethods.printInfoLog("Is Subscriber Id label present: " + checkState(subscriberIdLabel));
        return checkState(subscriberIdLabel);
    }

    public boolean isExpiryDate() {
        UtilsMethods.printInfoLog("Is Expiry Date label present: " + checkState(expiryDateLabel));
        return checkState(expiryDateLabel);
    }

    public boolean isAgent() {
        UtilsMethods.printInfoLog("Is Agent label present: " + checkState(agentLabel));
        return checkState(agentLabel);
    }

    public boolean isBatchID() {
        UtilsMethods.printInfoLog("Is Batch ID label present: " + checkState(batchIdLabel));
        return checkState(batchIdLabel);
    }

    public boolean isVoucherGroup() {
        UtilsMethods.printInfoLog("Is Voucher Group label present: " + checkState(voucherGroupLabel));
        return checkState(voucherGroupLabel);
    }

    public String getSerialValue() {
        UtilsMethods.printInfoLog("Reading Serial value present: " + readText(serialNo));
        return readText(serialNo);
    }

    public String getStatusValue() {
        UtilsMethods.printInfoLog("Reading Status value present: " + readText(statusValue));
        return readText(statusValue);
    }

    public String getSubStatus() {
        UtilsMethods.printInfoLog("Reading Sub Status value present: " + readText(subStatusValue));
        return readText(subStatusValue);
    }

    public String getRechargeAmt() {
        UtilsMethods.printInfoLog("Reading Recharge Amount value present: " + readText(rechargeAmtValue));
        return readText(rechargeAmtValue);
    }

    public String getTimeStamp() {
        UtilsMethods.printInfoLog("Reading Time Stamp value present: " + readText(timeStampValue));
        return readText(timeStampValue);
    }

    public String getSubscriberId() {
        UtilsMethods.printInfoLog("Reading Subscriber Id value present: " + readText(subscriberIdValue));
        return readText(subscriberIdValue);
    }

    public String getExpiryDate() {
        UtilsMethods.printInfoLog("Reading Expiry Date value present: " + readText(expiryDateValue));
        return readText(expiryDateValue);
    }

    public String getAgent() {
        UtilsMethods.printInfoLog("Reading Agent value present: " + readText(agentValue));
        return readText(agentValue);
    }

    public String getBatchID() {
        UtilsMethods.printInfoLog("Reading Batch ID value present: " + readText(batchIdValue));
        return readText(batchIdValue);
    }

    public String getVoucherGroup() {
        UtilsMethods.printInfoLog("Reading Voucher Group value present: " + readText(voucherGroupValue));
        return readText(voucherGroupValue);
    }

    public void clickDoneBtn() {
        UtilsMethods.printInfoLog("Clicking done button");
        click(doneBtn);
    }
}
