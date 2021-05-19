package com.airtel.cs.pagerepository.pagemethods;


import com.airtel.cs.commonutils.dataproviders.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.pagerepository.pageelements.DemoGraphicPage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class DemoGraphic extends BasePage {

    DemoGraphicPage pageElements;

    public DemoGraphic(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DemoGraphicPage.class);
    }

    public String getCustomerName() {
        final String text = getText(pageElements.customerName);
        commonLib.info("Getting Customer Name " + text);
        return text;
    }

    public String getDeviceCompatible() {
        String result = null;
        final String src = getAttribute(pageElements.deviceCompatible, "src", false);
        if (StringUtils.contains(src, "red-mobile.svg"))
            result = "Not 4G Compatible";
        else result = "4G Compatible";
        return result;
    }

    /*
    This Method will be used to get the value for data manager under demographic widget
     */
    public String getDataManagerValue() {
        commonLib.info("Going to get Data Manger value");
        final String text = getText(pageElements.dataManagerText);
        commonLib.info("Data Manager value is -: " + text);
        return text.trim();
    }


    public String getCustomerDOB() {
        final String text = getText(pageElements.customerDOB);
        commonLib.info("Getting Customer DOB " + text);
        return text;
    }

    public String getGSMStatusReasonCode() {
        final String text = getText(pageElements.reasonCode);
        commonLib.info("Getting SIM Status Reason Code " + text);
        return text;
    }

    public String getGSMStatusModifiedBy() {
        final String text = getText(pageElements.modifiedBy);
        commonLib.info("Getting SIM Status Modified By " + text);
        return text;
    }

    public String getGSMStatusModifiedDate() {
        String modifiedDate = null;
        final String date = getText(pageElements.modifiedDate);
        final String time = getText(pageElements.modifiedTime);
        modifiedDate = date.concat(" ") + time;
        commonLib.info("Getting SIM Status Modified Date " + modifiedDate);
        return modifiedDate;
    }

    public String getActivationDate() {
        final String text = getText(pageElements.activationDate);
        commonLib.info("Getting Activation Date " + text);
        return text;
    }

    public String getSimNumber() {
        final String text = getText(pageElements.simNumber);
        commonLib.info("Getting Sim Number " + text);
        return text;
    }

    public String getSimType() {
        String result = null;
        final String src = getAttribute(pageElements.simType, "src", false);
        if (StringUtils.contains(src, "red-sim.svg"))
            result = "3G";
        else result = "4G";
        return result;
    }

    public String getPUK1() {

        final String text = getText(pageElements.PUK1);
        commonLib.info("Getting PUK1 " + text);
        return text;
    }

    public String getPUK2() {
        final String text = getText(pageElements.PUK2);
        commonLib.info("Getting PUK2 " + text);
        return text;
    }

    public String getIdType() {
        final String text = getText(pageElements.idType);
        commonLib.info("Getting ID Type " + text);
        return text;
    }

    public String getIdNumber() {
        final String text = getText(pageElements.idNumber);
        commonLib.info("Getting masked ID Number " + text);
        return text;
    }

    public boolean isPUKInfoLocked() {
        try {
            final boolean enabled = isEnabled(pageElements.pukLock);
            commonLib.info("Is PUK Info locked: " + enabled);
            return enabled;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectPolicyQuestion() throws InterruptedException {
        DataProviders dataProviders = new DataProviders();
        List<AuthTabDataBeans> list = dataProviders.getPolicy();
        for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
            pages.getAuthTabPage().clickCheckBox(i);
        }
    }

    public boolean isVIP() {
        try {
            boolean check = isEnabled(pageElements.vipFlag);
            commonLib.info("Is Customer VIP: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBirthday() {
        try {
            boolean check = isEnabled(pageElements.customerBirthday);
            commonLib.info("Is Customer Birthday Today: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelAnniversary() {
        try {
            boolean check = isEnabled(pageElements.customerBirthday);
            commonLib.info("Is Customer Birthday Today: " + check);
            return check;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirtelMoneyProfileLocked() {
        try {
            final boolean enabled = isEnabled(pageElements.airtelMoneyLock);
            commonLib.info("Is Airtel Money Status Info locked: " + enabled);
            return enabled;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickPUKToUnlock() {
        commonLib.info("Clicking Tap to unlock on PUK Info");
        clickAndWaitForLoaderToBeRemoved(pageElements.pukLock);
    }

    public void clickAirtelStatusToUnlock() {
        commonLib.info("Clicking Tap to unlock on Airtel Status Info");
        clickAndWaitForLoaderToBeRemoved(pageElements.airtelMoneyLock);
    }

    public boolean checkAMProfileWidget() {
        commonLib.info("Checking AM Profile Widget Display");
        return isEnabled(pageElements.amProfileWidget);
    }


    public String getDataManagerStatus() {
        final String attribute = driver.findElement(pageElements.dataManagerStatus).getAttribute("aria-checked");
        commonLib.info("Status of Data Manager Status+ " + attribute);
        return attribute;
    }

    public String getGSMStatus() {
        final String text = getText(pageElements.gsmStatus);
        commonLib.info("Getting SIM Status: " + text);
        return text;
    }

    public String getAccountStatus() {
        final String text = getText(pageElements.accountStatus);
        commonLib.info("Getting AM Profile Account Status: " + text);
        return text;
    }

    public String getServiceStatus() {
        final String text = getText(pageElements.serviceStatus);
        commonLib.info("Getting Service Status: " + text);
        return text;
    }

    public String getWalletBalance() {
        final String text = getText(pageElements.walletBalance);
        commonLib.info("Getting Airtel Money Wallet Balance: " + text);
        return text;
    }

    public String getWalletBalance2() {
        final String text = getText(pageElements.walletBalance2);
        commonLib.info("Getting Airtel Money Wallet Balance: " + text);
        return text;
    }

    public String getRegistrationStatus() {
        final String text = getText(pageElements.registrationStatus);
        commonLib.info("Getting Airtel Money Registration Status : " + text);
        return text;
    }

    public String getConnectionType() {
        final String text = getText(pageElements.connectionType);
        commonLib.info("Getting Line Type: " + text);
        return text;
    }

    public String getSegment() {
        final String text = getText(pageElements.segment);
        commonLib.info("Getting Segment: " + text);
        return text;
    }

    public String getServiceClass() {
        final String text = getText(pageElements.serviceClass);
        commonLib.info("Getting service class: " + text);
        return text;
    }

    public String getServiceCategory() {
        final String text = getText(pageElements.serviceCategory);
        commonLib.info("Getting service Category: " + text);
        return text;
    }

    public String getAppStatus() {
        final String text = getText(pageElements.appStatus);
        commonLib.info("Getting app Status: " + text);
        return text;
    }

    public String getGsmKycStatus() {
        final String text = getText(pageElements.gsmKycStatus);
        commonLib.info("Getting Gsm Kyc Status: " + text);
        return text;
    }

    public String getIMEINumber() {
        final String text = getText(pageElements.IMEINumber);
        commonLib.info("Getting IMEI Number: " + text);
        return text;
    }

    public String getDeviceType() {
        final String text = getText(pageElements.type);
        commonLib.info("Getting Device Type: " + text);
        return text;
    }

    public String getBrand() {
        final String text = getText(pageElements.brand);
        commonLib.info("Getting Device Brand: " + text);
        return text;
    }

    public String getDeviceModel() {
        final String text = getText(pageElements.model);
        commonLib.info("Getting Device model: " + text);
        return text;
    }

    public String getDeviceOS() {
        final String text = getText(pageElements.os);
        commonLib.info("Getting Device Operating System Number: " + text);
        return text;
    }

    public void hoverOnDeviceInfoIcon() {
        commonLib.info("Hover on Device Info icon");
        hoverOverElement(pageElements.deviceInfoIcon);
    }

    public void hoverOnSIMStatusInfoIcon() {
        commonLib.info("Hover on SIM Status Reason Info icon");
        hoverOverElement(pageElements.SIMStatusReason);
    }

    public void hoverOnCustomerInfoIcon() {
        commonLib.info("Hover on Customer Info icon");
        hoverOverElement(pageElements.customerInfoIcon);
    }

    public void hoverOnSegmentInfoIcon() {
        commonLib.info("Hover on Segment Info icon");
        hoverOverElement(pageElements.hoverInfoSegment);
    }

    public String invalidMSISDNError() {
        final String text = getText(pageElements.errorMessage);
        commonLib.info("Message is: " + text);
        return text;
    }

    public void enterMSISDN(String text) {
        commonLib.info("Writing MSISDN in Search Box: " + text);
        enterText(pageElements.customerNumberSearchBox, text);
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
        hoverOverElement(pageElements.simNumberInfoIcon);
    }

}
