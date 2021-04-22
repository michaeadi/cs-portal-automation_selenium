package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.pagerepository.pageelements.DemoGraphicPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.NoSuchElementException;

@Log4j2
public class DemoGraphicPage extends BasePage {

    DemoGraphicPageElements pageElements;

    public DemoGraphicPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DemoGraphicPageElements.class);
    }

    public String getCustomerName() {
        commonLib.info("Getting Customer Name " + getText(pageElements.customerName));

        return getText(pageElements.customerName);
    }

    public String getDeviceCompatible() {
        commonLib.info("Getting Device Compatible: " + getText(pageElements.deviceCompatible));
        return getText(pageElements.deviceCompatible).trim();
    }


    public String getCustomerDOB() {
        commonLib.info("Getting Customer DOB " + getText(pageElements.customerDOB));
        return getText(pageElements.customerDOB);
    }

    public String getSIMStatusReasonCode() {
        commonLib.info("Getting SIM Status Reason Code " + getText(pageElements.reasonCode));
        return getText(pageElements.reasonCode);
    }

    public String getSIMStatusModifiedBy() {
        commonLib.info("Getting SIM Status Modified By " + getText(pageElements.modifiedBy));
        return getText(pageElements.modifiedBy);
    }

    public String getSIMStatusModifiedDate() {
        commonLib.info("Getting SIM Status Modified Date " + getText(pageElements.modifiedDate));
        return getText(pageElements.modifiedDate);
    }

    public String getActivationDate() {
        commonLib.info("Getting Activation Date " + getText(pageElements.activationDate));
        return getText(pageElements.activationDate);
    }

    public String getSimNumber() {
        commonLib.info("Getting Sim Number " + getText(pageElements.simNumber));
        return getText(pageElements.simNumber);
    }

    public String getSimType() {
        commonLib.info("Getting Sim Type " + getText(pageElements.simType));
        return getText(pageElements.simType);
    }

    public String getPUK1() {

        commonLib.info("Getting PUK1 " + getText(pageElements.PUK1));
        return getText(pageElements.PUK1);
    }

    public String getPUK2() {
        commonLib.info("Getting PUK2 " + getText(pageElements.PUK2));
        return getText(pageElements.PUK2);
    }

    public String getIdType() {
        commonLib.info("Getting ID Type " + getText(pageElements.idType));
        return getText(pageElements.idType);
    }

    public String getIdNumber() {
        commonLib.info("Getting masked ID Number " + getText(pageElements.idNumber));
        return getText(pageElements.idNumber);
    }

    public boolean isPUKInfoLock() {
        try {
            commonLib.info("Is PUK Info locked: " + checkState(pageElements.pukLock));
            return checkState(pageElements.pukLock);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVIP() {
        try {
            boolean check = checkState(pageElements.vipFlag);
            commonLib.info("Is Customer VIP: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBirthday() {
        try {
            boolean check = checkState(pageElements.customerBirthday);
            commonLib.info("Is Customer Birthday Today: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelAnniversary() {
        try {
            boolean check = checkState(pageElements.customerBirthday);
            commonLib.info("Is Customer Birthday Today: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelMoneyStatusLock() {
        try {
            commonLib.info("Is Airtel Money Status Info locked: " + checkState(pageElements.airtelMoneyLock));
            return checkState(pageElements.airtelMoneyLock);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickPUKToUnlock() {
        commonLib.info("Clicking Tap to unlock on PUK Info");
        click(pageElements.pukLock);
    }

    public void clickAirtelStatusToUnlock() {
        commonLib.info("Clicking Tap to unlock on Airtel Status Info");
        click(pageElements.airtelMoneyLock);
    }

    public boolean checkAMProfileWidget() {
        commonLib.info("Checking AM Profile Widget Display");
        return checkState(pageElements.amProfileWidget);
    }


    public String getDataManagerStatus() {
        commonLib.info("Status of Data Manager Status+ " + driver.findElement(pageElements.dataManagerStatus).getAttribute("aria-checked"));
        return driver.findElement(pageElements.dataManagerStatus).getAttribute("aria-checked");
    }

    public String getSIMStatus() {
        commonLib.info("Getting SIM Status: " + getText(pageElements.SIMStatus));
        return getText(pageElements.SIMStatus);
    }

    public String getAirtelMoneyStatus() {
        commonLib.info("Getting Airtel Money Status: " + getText(pageElements.airtelMoneyStatus));
        return getText(pageElements.airtelMoneyStatus);
    }

    public String getServiceStatus() {
        commonLib.info("Getting Service Status: " + getText(pageElements.serviceStatus));
        return getText(pageElements.serviceStatus);
    }

    public String getWalletBalance() {
        commonLib.info("Getting Airtel Money Wallet Balance: " + getText(pageElements.walletBalance));
        return getText(pageElements.walletBalance);
    }

    public String getWalletBalance2() {
        commonLib.info("Getting Airtel Money Wallet Balance: " + getText(pageElements.walletBalance));
        return getText(pageElements.walletBalance2);
    }

    public String getRegistrationStatus() {
        commonLib.info("Getting Airtel Money Registration Status : " + getText(pageElements.registrationStatus));
        return getText(pageElements.registrationStatus);
    }

    public String getLineType() {
        commonLib.info("Getting Line Type: " + getText(pageElements.lineType));
        return getText(pageElements.lineType);
    }

    public String getSegment() {
        commonLib.info("Getting Segment: " + getText(pageElements.segment));
        return getText(pageElements.segment);
    }

    public String getServiceClass() {
        commonLib.info("Getting service class: " + getText(pageElements.serviceClass));
        return getText(pageElements.serviceClass);
    }

    public String getServiceCategory() {
        commonLib.info("Getting service Category: " + getText(pageElements.serviceCategory));
        return getText(pageElements.serviceCategory);
    }

    public String getAppStatus() {
        commonLib.info("Getting app Status: " + getText(pageElements.appStatus));
        return getText(pageElements.appStatus);
    }

    public String getGsmKycStatus() {
        commonLib.info("Getting Gsm Kyc Status: " + getText(pageElements.gsmKycStatus));
        return getText(pageElements.gsmKycStatus);
    }

    public String getIMEINumber() {
        commonLib.info("Getting IMEI Number: " + getText(pageElements.IMEINumber));
        return getText(pageElements.IMEINumber);
    }

    public String getDeviceType() {
        commonLib.info("Getting Device Type: " + getText(pageElements.type));
        return getText(pageElements.type);
    }

    public String getBrand() {
        commonLib.info("Getting Device Brand: " + getText(pageElements.brand));
        return getText(pageElements.brand);
    }

    public String getDeviceModel() {
        commonLib.info("Getting Device model: " + getText(pageElements.model));
        return getText(pageElements.model);
    }

    public String getDeviceOS() {
        commonLib.info("Getting Device Operating System Number: " + getText(pageElements.os));
        return getText(pageElements.os);
    }

    public void hoverOnDeviceInfoIcon() {
        commonLib.info("Hover on Device Info icon");
        hoverAndClick(pageElements.deviceInfoIcon);
    }

    public void hoverOnSIMStatusInfoIcon() {
        commonLib.info("Hover on SIM Status Reason Info icon");
        hoverAndClick(pageElements.SIMStatusReason);
    }

    public void hoverOnCustomerInfoIcon() {
        commonLib.info("Hover on Customer Info icon");
        hoverAndClick(pageElements.customerInfoIcon);
    }

    public void hoverOnSegmentInfoIcon() {
        commonLib.info("Hover on Segment Info icon");
        hoverAndClick(pageElements.hoverInfoSegment);
    }

    public boolean invalidMSISDNError() {
        commonLib.info("Reading Error Message: " + getText(pageElements.errorMessage));
        return checkState(pageElements.errorMessage);
    }

    public void enterMSISDN(String text) {
        commonLib.info("Writing MSISDN in Search Box: " + text);
        writeText(pageElements.customerNumberSearchBox, text);
        driver.findElement(pageElements.customerNumberSearchBox).sendKeys(Keys.ENTER);
    }

    public void clearSearchBox(int size) {
        commonLib.info("Clearing Search box");
        for (int i = 0; i < size; i++) {
            driver.findElement(pageElements.customerNumberSearchBox).sendKeys(Keys.BACK_SPACE);
        }
    }


    public void hoverOnSIMNumberIcon() {
        commonLib.info("Hover on SIM Number Info icon");
        hoverAndClick(pageElements.simNumberInfoIcon);
    }
}
