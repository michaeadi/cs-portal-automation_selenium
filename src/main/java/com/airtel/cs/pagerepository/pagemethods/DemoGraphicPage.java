package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.commonutils.UtilsMethods;
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
        UtilsMethods.printInfoLog("Getting Customer Name " + getText(pageElements.customerName));

        return getText(pageElements.customerName);
    }

    public String getDeviceCompatible() {
        UtilsMethods.printInfoLog("Getting Device Compatible: " + getText(pageElements.deviceCompatible));
        return getText(pageElements.deviceCompatible).trim();
    }


    public String getCustomerDOB() {
        UtilsMethods.printInfoLog("Getting Customer DOB " + getText(pageElements.customerDOB));
        return getText(pageElements.customerDOB);
    }

    public String getSIMStatusReasonCode() {
        UtilsMethods.printInfoLog("Getting SIM Status Reason Code " + getText(pageElements.reasonCode));
        return getText(pageElements.reasonCode);
    }

    public String getSIMStatusModifiedBy() {
        UtilsMethods.printInfoLog("Getting SIM Status Modified By " + getText(pageElements.modifiedBy));
        return getText(pageElements.modifiedBy);
    }

    public String getSIMStatusModifiedDate() {
        UtilsMethods.printInfoLog("Getting SIM Status Modified Date " + getText(pageElements.modifiedDate));
        return getText(pageElements.modifiedDate);
    }

    public String getActivationDate() {
        UtilsMethods.printInfoLog("Getting Activation Date " + getText(pageElements.activationDate));
        return getText(pageElements.activationDate);
    }

    public String getSimNumber() {
        UtilsMethods.printInfoLog("Getting Sim Number " + getText(pageElements.simNumber));
        return getText(pageElements.simNumber);
    }

    public String getSimType() {
        UtilsMethods.printInfoLog("Getting Sim Type " + getText(pageElements.simType));
        return getText(pageElements.simType);
    }

    public String getPUK1() {

        UtilsMethods.printInfoLog("Getting PUK1 " + getText(pageElements.PUK1));
        return getText(pageElements.PUK1);
    }

    public String getPUK2() {
        UtilsMethods.printInfoLog("Getting PUK2 " + getText(pageElements.PUK2));
        return getText(pageElements.PUK2);
    }

    public String getIdType() {
        UtilsMethods.printInfoLog("Getting ID Type " + getText(pageElements.idType));
        return getText(pageElements.idType);
    }

    public String getIdNumber() {
        UtilsMethods.printInfoLog("Getting masked ID Number " + getText(pageElements.idNumber));
        return getText(pageElements.idNumber);
    }

    public boolean isPUKInfoLock() {
        try {
            UtilsMethods.printInfoLog("Is PUK Info locked: " + checkState(pageElements.pukLock));
            return checkState(pageElements.pukLock);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVIP() {
        try {
            Boolean check = checkState(pageElements.vipFlag);
            UtilsMethods.printInfoLog("Is Customer VIP: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBirthday() {
        try {
            Boolean check = checkState(pageElements.customerBirthday);
            UtilsMethods.printInfoLog("Is Customer Birthday Today: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelAnniversary() {
        try {
            Boolean check = checkState(pageElements.customerBirthday);
            UtilsMethods.printInfoLog("Is Customer Birthday Today: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelMoneyStatusLock() {
        try {
            UtilsMethods.printInfoLog("Is Airtel Money Status Info locked: " + checkState(pageElements.airtelMoneyLock));
            return checkState(pageElements.airtelMoneyLock);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickPUKToUnlock() {
        UtilsMethods.printInfoLog("Clicking Tap to unlock on PUK Info");
        click(pageElements.pukLock);
    }

    public void clickAirtelStatusToUnlock() {
        UtilsMethods.printInfoLog("Clicking Tap to unlock on Airtel Status Info");
        click(pageElements.airtelMoneyLock);
    }

    public boolean checkAMProfileWidget() {
        UtilsMethods.printInfoLog("Checking AM Profile Widget Display");
        return checkState(pageElements.amProfileWidget);
    }


    public String getDataManagerStatus() {
        UtilsMethods.printInfoLog("Status of Data Manager Status+ " + driver.findElement(pageElements.dataManagerStatus).getAttribute("aria-checked"));
        return driver.findElement(pageElements.dataManagerStatus).getAttribute("aria-checked");
    }

    public String getSIMStatus() {
        UtilsMethods.printInfoLog("Getting SIM Status: " + getText(pageElements.SIMStatus));
        return getText(pageElements.SIMStatus);
    }

    public String getAirtelMoneyStatus() {
        UtilsMethods.printInfoLog("Getting Airtel Money Status: " + getText(pageElements.airtelMoneyStatus));
        return getText(pageElements.airtelMoneyStatus);
    }

    public String getServiceStatus() {
        UtilsMethods.printInfoLog("Getting Service Status: " + getText(pageElements.serviceStatus));
        return getText(pageElements.serviceStatus);
    }

    public String getWalletBalance() {
        UtilsMethods.printInfoLog("Getting Airtel Money Wallet Balance: " + getText(pageElements.walletBalance));
        return getText(pageElements.walletBalance);
    }

    public String getWalletBalance2() {
        UtilsMethods.printInfoLog("Getting Airtel Money Wallet Balance: " + getText(pageElements.walletBalance));
        return getText(pageElements.walletBalance2);
    }

    public String getRegistrationStatus() {
        UtilsMethods.printInfoLog("Getting Airtel Money Registration Status : " + getText(pageElements.registrationStatus));
        return getText(pageElements.registrationStatus);
    }

    public String getLineType() {
        UtilsMethods.printInfoLog("Getting Line Type: " + getText(pageElements.lineType));
        return getText(pageElements.lineType);
    }

    public String getSegment() {
        UtilsMethods.printInfoLog("Getting Segment: " + getText(pageElements.segment));
        return getText(pageElements.segment);
    }

    public String getServiceClass() {
        UtilsMethods.printInfoLog("Getting service class: " + getText(pageElements.serviceClass));
        return getText(pageElements.serviceClass);
    }

    public String getServiceCategory() {
        UtilsMethods.printInfoLog("Getting service Category: " + getText(pageElements.serviceCategory));
        return getText(pageElements.serviceCategory);
    }

    public String getAppStatus() {
        UtilsMethods.printInfoLog("Getting app Status: " + getText(pageElements.appStatus));
        return getText(pageElements.appStatus);
    }

    public String getGsmKycStatus() {
        UtilsMethods.printInfoLog("Getting Gsm Kyc Status: " + getText(pageElements.gsmKycStatus));
        return getText(pageElements.gsmKycStatus);
    }

    public String getIMEINumber() {
        UtilsMethods.printInfoLog("Getting IMEI Number: " + getText(pageElements.IMEINumber));
        return getText(pageElements.IMEINumber);
    }

    public String getDeviceType() {
        UtilsMethods.printInfoLog("Getting Device Type: " + getText(pageElements.type));
        return getText(pageElements.type);
    }

    public String getBrand() {
        UtilsMethods.printInfoLog("Getting Device Brand: " + getText(pageElements.brand));
        return getText(pageElements.brand);
    }

    public String getDeviceModel() {
        UtilsMethods.printInfoLog("Getting Device model: " + getText(pageElements.model));
        return getText(pageElements.model);
    }

    public String getDeviceOS() {
        UtilsMethods.printInfoLog("Getting Device Operating System Number: " + getText(pageElements.os));
        return getText(pageElements.os);
    }

    public void hoverOnDeviceInfoIcon() {
        UtilsMethods.printInfoLog("Hover on Device Info icon");
        hoverAndClick(pageElements.deviceInfoIcon);
    }

    public void hoverOnSIMStatusInfoIcon() {
        UtilsMethods.printInfoLog("Hover on SIM Status Reason Info icon");
        hoverAndClick(pageElements.SIMStatusReason);
    }

    public void hoverOnCustomerInfoIcon() {
        UtilsMethods.printInfoLog("Hover on Customer Info icon");
        hoverAndClick(pageElements.customerInfoIcon);
    }

    public void hoverOnSegmentInfoIcon() {
        UtilsMethods.printInfoLog("Hover on Segment Info icon");
        hoverAndClick(pageElements.hoverInfoSegment);
    }

    public boolean invalidMSISDNError() {
        UtilsMethods.printInfoLog("Reading Error Message: " + getText(pageElements.errorMessage));
        return checkState(pageElements.errorMessage);
    }

    public void enterMSISDN(String text) {
        UtilsMethods.printInfoLog("Writing MSISDN in Search Box: " + text);
        writeText(pageElements.customerNumberSearchBox, text);
        driver.findElement(pageElements.customerNumberSearchBox).sendKeys(Keys.ENTER);
    }

    public void clearSearchBox(int size) {
        UtilsMethods.printInfoLog("Clearing Search box");
        for (int i = 0; i < size; i++) {
            driver.findElement(pageElements.customerNumberSearchBox).sendKeys(Keys.BACK_SPACE);
        }
    }


    public void hoverOnSIMNumberIcon() {
        UtilsMethods.printInfoLog("Hover on SIM Number Info icon");
        hoverAndClick(pageElements.simNumberInfoIcon);
    }
}
