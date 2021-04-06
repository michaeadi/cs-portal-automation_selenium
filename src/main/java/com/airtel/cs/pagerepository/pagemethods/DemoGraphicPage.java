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
        UtilsMethods.printInfoLog("Getting Customer Name " + readText(pageElements.customerName));

        return readText(pageElements.customerName);
    }

    public String getDeviceCompatible() {
        UtilsMethods.printInfoLog("Getting Device Compatible: " + readText(pageElements.deviceCompatible));
        return readText(pageElements.deviceCompatible).trim();
    }


    public String getCustomerDOB() {
        UtilsMethods.printInfoLog("Getting Customer DOB " + readText(pageElements.customerDOB));
        return readText(pageElements.customerDOB);
    }

    public String getSIMStatusReasonCode() {
        UtilsMethods.printInfoLog("Getting SIM Status Reason Code " + readText(pageElements.reasonCode));
        return readText(pageElements.reasonCode);
    }

    public String getSIMStatusModifiedBy() {
        UtilsMethods.printInfoLog("Getting SIM Status Modified By " + readText(pageElements.modifiedBy));
        return readText(pageElements.modifiedBy);
    }

    public String getSIMStatusModifiedDate() {
        UtilsMethods.printInfoLog("Getting SIM Status Modified Date " + readText(pageElements.modifiedDate));
        return readText(pageElements.modifiedDate);
    }

    public String getActivationDate() {
        UtilsMethods.printInfoLog("Getting Activation Date " + readText(pageElements.activationDate));
        return readText(pageElements.activationDate);
    }

    public String getSimNumber() {
        UtilsMethods.printInfoLog("Getting Sim Number " + readText(pageElements.simNumber));
        return readText(pageElements.simNumber);
    }

    public String getSimType() {
        UtilsMethods.printInfoLog("Getting Sim Type " + readText(pageElements.simType));
        return readText(pageElements.simType);
    }

    public String getPUK1() {

        UtilsMethods.printInfoLog("Getting PUK1 " + readText(pageElements.PUK1));
        return readText(pageElements.PUK1);
    }

    public String getPUK2() {
        UtilsMethods.printInfoLog("Getting PUK2 " + readText(pageElements.PUK2));
        return readText(pageElements.PUK2);
    }

    public String getIdType() {
        UtilsMethods.printInfoLog("Getting ID Type " + readText(pageElements.idType));
        return readText(pageElements.idType);
    }

    public String getIdNumber() {
        UtilsMethods.printInfoLog("Getting masked ID Number " + readText(pageElements.idNumber));
        return readText(pageElements.idNumber);
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
        UtilsMethods.printInfoLog("Getting SIM Status: " + readText(pageElements.SIMStatus));
        return readText(pageElements.SIMStatus);
    }

    public String getAirtelMoneyStatus() {
        UtilsMethods.printInfoLog("Getting Airtel Money Status: " + readText(pageElements.airtelMoneyStatus));
        return readText(pageElements.airtelMoneyStatus);
    }

    public String getServiceStatus() {
        UtilsMethods.printInfoLog("Getting Service Status: " + readText(pageElements.serviceStatus));
        return readText(pageElements.serviceStatus);
    }

    public String getWalletBalance() {
        UtilsMethods.printInfoLog("Getting Airtel Money Wallet Balance: " + readText(pageElements.walletBalance));
        return readText(pageElements.walletBalance);
    }

    public String getWalletBalance2() {
        UtilsMethods.printInfoLog("Getting Airtel Money Wallet Balance: " + readText(pageElements.walletBalance));
        return readText(pageElements.walletBalance2);
    }

    public String getRegistrationStatus() {
        UtilsMethods.printInfoLog("Getting Airtel Money Registration Status : " + readText(pageElements.registrationStatus));
        return readText(pageElements.registrationStatus);
    }

    public String getLineType() {
        UtilsMethods.printInfoLog("Getting Line Type: " + readText(pageElements.lineType));
        return readText(pageElements.lineType);
    }

    public String getSegment() {
        UtilsMethods.printInfoLog("Getting Segment: " + readText(pageElements.segment));
        return readText(pageElements.segment);
    }

    public String getServiceClass() {
        UtilsMethods.printInfoLog("Getting service class: " + readText(pageElements.serviceClass));
        return readText(pageElements.serviceClass);
    }

    public String getServiceCategory() {
        UtilsMethods.printInfoLog("Getting service Category: " + readText(pageElements.serviceCategory));
        return readText(pageElements.serviceCategory);
    }

    public String getAppStatus() {
        UtilsMethods.printInfoLog("Getting app Status: " + readText(pageElements.appStatus));
        return readText(pageElements.appStatus);
    }

    public String getGsmKycStatus() {
        UtilsMethods.printInfoLog("Getting Gsm Kyc Status: " + readText(pageElements.gsmKycStatus));
        return readText(pageElements.gsmKycStatus);
    }

    public String getIMEINumber() {
        UtilsMethods.printInfoLog("Getting IMEI Number: " + readText(pageElements.IMEINumber));
        return readText(pageElements.IMEINumber);
    }

    public String getDeviceType() {
        UtilsMethods.printInfoLog("Getting Device Type: " + readText(pageElements.type));
        return readText(pageElements.type);
    }

    public String getBrand() {
        UtilsMethods.printInfoLog("Getting Device Brand: " + readText(pageElements.brand));
        return readText(pageElements.brand);
    }

    public String getDeviceModel() {
        UtilsMethods.printInfoLog("Getting Device model: " + readText(pageElements.model));
        return readText(pageElements.model);
    }

    public String getDeviceOS() {
        UtilsMethods.printInfoLog("Getting Device Operating System Number: " + readText(pageElements.os));
        return readText(pageElements.os);
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
        UtilsMethods.printInfoLog("Reading Error Message: " + readText(pageElements.errorMessage));
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
