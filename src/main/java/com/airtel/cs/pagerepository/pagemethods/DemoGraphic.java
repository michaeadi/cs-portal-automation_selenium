package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.AuthTabDataBeans;
import com.airtel.cs.model.response.authconfiguration.Configuration;
import com.airtel.cs.model.response.authconfiguration.CustomDetails;
import com.airtel.cs.model.response.authconfiguration.CustomerDemographicSection;
import com.airtel.cs.model.response.authconfiguration.Tabs;
import com.airtel.cs.pagerepository.pageelements.DemoGraphicPage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class DemoGraphic extends BasePage {

    DemoGraphicPage pageElements;

    public DemoGraphic(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, DemoGraphicPage.class);
    }

    /**
     * This method is use to get customer name
     *
     * @return String The value
     */
    public String getCustomerName() {
        final String text = getText(pageElements.customerName);
        commonLib.info("Getting Customer Name: " + text);
        return text;
    }


    /**
     * This method is use to get device Compatible
     *
     * @return String The value
     */
    public String getDeviceCompatible() {
        String result = null;
        final String src = getAttribute(pageElements.deviceCompatible, "src", false);
        if (StringUtils.contains(src, "red-mobile.svg"))
            result = "Not 4G Compatible";
        else
            result = "4G Compatible";
        return result;
    }

    /**
     * This Method will be used to get the value for data manager under demographic widget
     */
    public String getDataManagerValue() {
        commonLib.info("Going to get Data Manger value");
        final String text = getText(pageElements.dataManagerText);
        commonLib.info("Data Manager value is -: " + text);
        return text.trim();
    }

    /**
     * This method is use to get customer date of birth
     *
     * @return String The value
     */
    public String getCustomerDOB() {
        final String text = getText(pageElements.customerDOB);
        commonLib.info("Getting Customer DOB " + text);
        return text;
    }

    /**
     * This method is use to get GSM status reason code
     *
     * @return String The value
     */
    public String getGSMStatusReasonCode() {
        final String text = getText(pageElements.reasonCode);
        commonLib.info("Getting SIM Status Reason Code " + text);
        return text;
    }

    /**
     * This method is use to get GSM status modified by
     *
     * @return String The value
     */
    public String getGSMStatusModifiedBy() {
        final String text = getText(pageElements.modifiedBy);
        commonLib.info("Getting SIM Status Modified By " + text);
        return text;
    }

    /**
     * This method is use to get GSM status modified date
     *
     * @return String The value
     */
    public String getGSMStatusModifiedDate() {
        String modifiedDate = null;
        final String date = getText(pageElements.modifiedDate);
        final String time = getText(pageElements.modifiedTime);
        modifiedDate = date.concat(" ") + time;
        commonLib.info("Getting SIM Status Modified Date " + modifiedDate);
        clickFEDashboardOutside();
        return modifiedDate;
    }

    /**
     * This method is use to get GSM activation date
     *
     * @return String The value
     */
    public String getActivationDate() {
        final String text = getText(pageElements.activationDate);
        commonLib.info("Getting Activation Date " + text);
        return text;
    }

    /**
     * This method is use to get SIM Number
     *
     * @return String The value
     */
    public String getSimNumber() {
        final String text = getText(pageElements.simNumber);
        commonLib.info("Getting Sim Number " + text);
        return text;
    }

    /**
     * This method is use to get SIM Type
     *
     * @return String The value
     */
    public String getSimType() {
        String result = null;
        final String src = getAttribute(pageElements.simType, "src", false);
        if (StringUtils.contains(src, "red-sim.svg"))
            result = "3G";
        else
            result = "4G";
        return result;
    }

    /*
    This Method is used to get puk1 details from the demographic widget
     */
    public String getPUK1() {
        String result = null;
        if (isVisible(pageElements.puk1)) {
            commonLib.info("Going to get PUK2");
            result = getText(pageElements.puk1);
            commonLib.info("PUK1 is " + result);
        }
        return result;
    }

    /*
    This Method is used to get puk2 details from the demographic widget
     */
    public String getPUK2() {
        String result = null;
        if (isVisible(pageElements.puk2)) {
            commonLib.info("Going to get PUK1");
            result = getText(pageElements.puk2);
            commonLib.info("PUK2 is " + result);
        }
        return result;
    }

    /**
     * This method is use to get Id type
     *
     * @return String The value
     */
    public String getIdType() {
        final String text = getText(pageElements.idType);
        commonLib.info("Getting ID Type " + text);
        clickFEDashboardOutside();
        return text;
    }

    /**
     * This method is use to get Id number
     *
     * @return String The value
     */
    public String getIdNumber() {
        final String text = getText(pageElements.idNumber);
        commonLib.info("Getting masked ID Number " + text);
        return text;
    }

    /**
     * This method is use to check PUK info locked or not
     *
     * @return true/false
     */
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

    /**
     * This method is use to select minimum number of policy question
     */
    public void selectPolicyQuestion() {
        try {
            DataProviders dataProviders = new DataProviders();
            List<AuthTabDataBeans> list = dataProviders.getPolicy();
            for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
                pages.getAuthTabPage().clickCheckBox(i);
            }
        } catch (InterruptedException e) {
            commonLib.fail("Exception in Method - selectPolicyQuestion" + e, true);
        }
    }

    /**
     * This method is use to check VIP flag display or not
     *
     * @return true/false
     */
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

    /**
     * This method is use to check birthday icon display or not
     *
     * @return true/false
     */
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

    /**
     * This method is use to check Airtel anniversary icon display or not
     *
     * @return true/false
     */
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

    /**
     * This method is use to check Airtel money profile icon locked or not
     *
     * @return true/false
     */
    public boolean isAirtelMoneyProfileLocked() {
        try {
            final boolean visibility = isElementVisible(pageElements.airtelMoneyLock);
            commonLib.info("Is Airtel Money Status Info locked: " + visibility);
            return visibility;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is use to click PUK info locked section
     */
    public void clickPUKToUnlock() {
        commonLib.info("Clicking Tap to unlock on PUK Info");
        clickAndWaitForLoaderToBeRemoved(pageElements.pukLock);
    }

    /**
     * This method is use to click PUK info locked section
     */
    public void clickPUKTAuthenticate() {
        commonLib.info("Clicking on Authenticate tab after selecting minimum number of questions");
        clickAndWaitForLoaderToBeRemoved(pageElements.authenticate);
    }

    /**
     * This method is use to click Airtel money profile info locked section
     */
    public void clickAirtelStatusToUnlock() {
        commonLib.info("Clicking Tap to unlock on Airtel Status Info");
        clickAndWaitForLoaderToBeRemoved(pageElements.airtelMoneyLock);
    }

    /**
     * This method is use to check Airtel money profile widget display
     *
     * @return true/false
     */
    public boolean checkAMProfileWidget() {
        commonLib.info("Checking AM Profile Widget Display");
        return isEnabled(pageElements.amProfileWidget);
    }

    /**
     * This method is use to get data manager status
     *
     * @return String The value
     */
    public String getDataManagerStatus() {
        final String attribute = driver.findElement(pageElements.dataManagerStatus).getAttribute("aria-checked");
        commonLib.info("Status of Data Manager Status+ " + attribute);
        return attribute;
    }

    /**
     * This method is use to get GSM SIM status
     *
     * @return String The value
     */
    public String getGSMStatus() {
        String text = "";
        if (isVisible(pageElements.gsmStatus)) {
            text = getText(pageElements.gsmStatus);
            commonLib.info("Getting SIM Status: " + text);
        }
        return text;
    }

    /**
     * This method is use to get AM Account status
     *
     * @return String The value
     */
    public String getAccountStatus() {
        String result = "";
        if (isVisible(pageElements.accountStatus)) {
            result = getText(pageElements.accountStatus);
            commonLib.info("Getting AM Profile Account Status: " + result);
        }
        return result;
    }

    /**
     * This method is use to check GSM SIM status
     *
     * @return String The value
     */
    public String getServiceStatus() {
        String result = "";
        if (isVisible(pageElements.serviceStatus)) {
            result = getText(pageElements.serviceStatus);
            commonLib.info("Getting Service Status: " + result);
        }
        return result;
    }

    /**
     * This method is use to get AM Primary Wallet balance
     *
     * @return String The value
     */
    public String getWalletBalance() {
        String result = "";
        if (isVisible(pageElements.walletBalance)) {
            result = getText(pageElements.walletBalance);
            commonLib.info("Getting Airtel Money Wallet Balance: " + result);
        }
        return result;
    }

    /**
     * This method is use to get AM Secondary Wallet balance
     *
     * @return String The value
     */
    public String getWalletBalance2() {
        final String text = getText(pageElements.walletBalance2);
        commonLib.info("Getting Airtel Money Wallet Balance: " + text);
        return text;
    }

    /**
     * This method is use to get AM Registration balance
     *
     * @return String The value
     */
    public String getRegistrationStatus() {
        String result = "";
        if (isVisible(pageElements.registrationStatus)) {
            result = getText(pageElements.registrationStatus);
            commonLib.info("Getting Airtel Money Registration Status : " + result);
        }
        return result;
    }

    /**
     * This method is use to get AM Connection Type
     *
     * @return String The value
     */
    public String getConnectionType() {
        final String text = getText(pageElements.connectionType);
        commonLib.info("Getting Connection Type: " + text);
        return text;
    }

    /**
     * This method is use to get Segment
     *
     * @return String The value
     */
    public String getSegment() {
        final String text = getText(pageElements.segment);
        commonLib.info("Getting Segment: " + text);
        return text;
    }

    /**
     * This method is use to get Service class
     *
     * @return String The value
     */
    public String getServiceClass() {
        final String text = getText(pageElements.serviceClass);
        commonLib.info("Getting service class: " + text);
        return text;
    }

    /**
     * This method is use to get Service category
     *
     * @return String The value
     */
    public String getServiceCategory() {
        String result = null;
        try {
            if (isVisible(pageElements.serviceCategory)) {
                result = getText(pageElements.serviceCategory);
                commonLib.info("Getting service Category: " + result);
                clickFEDashboardOutside();
            } else {
                commonLib.fail("Service Category is NOT visible", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - getServiceCategory", true);
        }
        return result;
    }

    /**
     * This method is use to get Sub Segment
     *
     * @return String The value
     */
    public String getSubSegment() {
        String result = null;
        try {
            if (isElementVisible(pageElements.subSegment)) {
                result = getText(pageElements.subSegment);
                commonLib.info("Getting Sub Segment: " + result);
            } else {
                commonLib.fail("Sub Segment is NOT visible", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - getSubSegment", true);
        }
        return result;
    }

    /**
     * This method is use to get self-care API downloaded or not
     *
     * @return String The value
     */
    public String getAppStatus() {
        final String text = getText(pageElements.appStatus);
        commonLib.info("Getting app Status: " + text);
        return text;
    }

    /**
     * This method is use to get GSM KYC status
     *
     * @return String The value
     */
    public String getGsmKycStatus() {
        final String text = getText(pageElements.gsmKycStatus);
        commonLib.info("Getting Gsm Kyc Status: " + text);
        return text;
    }

    /**
     * This method is use to get IMEI number
     *
     * @return String The value
     */
    public String getIMEINumber() {
        final String text = getText(pageElements.IMEINumber);
        commonLib.info("Getting IMEI Number: " + text);
        return text;
    }

    /**
     * This method is use to get device type
     *
     * @return String The value
     */
    public String getDeviceType() {
        final String text = getText(pageElements.type);
        commonLib.info("Getting Device Type: " + text);
        return text;
    }

    /**
     * This method is use to get brand name
     *
     * @return String The value
     */
    public String getBrand() {
        final String text = getText(pageElements.brand);
        commonLib.info("Getting Device Brand: " + text);
        return text;
    }

    /**
     * This method is use to get device model name
     *
     * @return String The value
     */
    public String getDeviceModel() {
        final String text = getText(pageElements.model);
        commonLib.info("Getting Device model: " + text);
        return text;
    }

    /**
     * This method is use to get device os
     *
     * @return String The value
     */
    public String getDeviceOS() {
        final String text = getText(pageElements.os);
        commonLib.info("Getting Device Operating System Number: " + text);
        return text;
    }

    /**
     * This method is use to hover on device info icon
     */
    public void hoverOnDeviceInfoIcon() {
        commonLib.info("Hover on Device Info icon");
        hoverOverElement(pageElements.deviceInfoIcon);
    }

    /**
     * This method is use to hover on SIM Status info icon
     */
    public void hoverOnSIMStatusInfoIcon() {
        commonLib.info("Hover on SIM Status Reason Info icon");
        hoverOverElement(pageElements.SIMStatusReason);
    }

    /**
     * This method is use to hover on Customer info icon
     */
    public void hoverOnCustomerInfoIcon() {
        commonLib.info("Hover on Customer Info icon");
        hoverOverElement(pageElements.customerInfoIcon);
    }

    /**
     * This method is use to hover on customer Segment info icon
     */
    public void hoverOnSegmentInfoIcon() {
        commonLib.info("Hover on Segment Info icon");
        hoverOverElement(pageElements.hoverInfoSegment);
        //commonLib.hardWait(2);
    }

    /**
     * This method is use to get msisdn error message
     *
     * @return String The value
     */
    public String invalidMSISDNError() {
        final String text = getText(pageElements.dashboardSearchErrorMessage);
        commonLib.info("Message is: " + text);
        return text;
    }

    /**
     * This method is use to enter msisdn in search box of the customer dashboard page
     *
     * @param text The text
     */
    public void enterMSISDN(String text) {
        commonLib.info("Writing MSISDN in Search Box: " + text);
        enterText(pageElements.msisdnDashboardSearchBox, text);
        driver.findElement(pageElements.msisdnDashboardSearchBox).sendKeys(Keys.ENTER);
    }

    /**
     * This method is use to click search button on dashboard page
     */
    public void clickOnDashboardSearch() {
        commonLib.info("Clicking on Search Button");
        clickWithoutLoader(pageElements.searchButton);
        final boolean isGrowlVisible = pages.getGrowl().checkIsDashboardGrowlVisible();
        if (!isGrowlVisible) {
            if (isElementVisible(pageElements.dashboardSearchErrorMessage)) {
                highLighterMethod(pageElements.dashboardSearchErrorMessage);
                commonLib.error(driver.findElement(pageElements.dashboardSearchErrorMessage).getText());
            }
        } else {
            commonLib.fail("Growl Message:- " + pages.getGrowl().getDashboardToastMessage(), true);
            throw new SkipException("Growl Message Displayed");
        }
    }

    /**
     * This method is used to hover on SIM Number info icon
     */
    public void hoverOnSIMNumberIcon() {
        commonLib.info("Hover on SIM Number Info icon");
        hoverOverElement(pageElements.simNumberInfoIcon);
    }

    /*
   This method will be used to get PIN1 text from demographic widget after hovering over SIM Number
    */
    public String getPIN1() {
        String result = null;
        result = getText(pageElements.pin1);
        commonLib.info("PIN1 got from UI is - " + result);
        return result;
    }

    /*
   This method will be used to get PIN2 text from demographic widget after hovering over SIM Number
    */
    public String getPIN2() {
        String result = null;
        result = getText(pageElements.pin2);
        commonLib.info("PIN2 got from UI is - " + result);
        clickFEDashboardOutside();
        return result;
    }

    /*
    This Method will give us auuid shown in the middle of the demographic widget
    DGW - Demo Graphic Widget
     */
    public String getMiddleAuuidDGW() {
        String result = null;
        result = getAttribute(pageElements.middleAuuidDGW, "data-auuid", false);
        return result;
    }

    /*
    This Method will give us auuid shown at the footer of the demographic widget
    DGW - Demo Graphic Widget
     */
    public String getFooterAuuidDGW() {
        String result = null;
        result = getText(pageElements.footerAuuidDGW);
        return result;
    }

    /*
    This Method will give us auuid shown in the middle of the demographic widget
    AMP - Airtel Money Profile
     */
    public String getMiddleAuuidAMP() {
        String result = null;
        result = getAttribute(pageElements.middleAuuidAMP, "data-auuid", false);
        return result;
    }

    /*
    This Method will give us auuid shown at the footer of the demographic widget
    AMP - Airtel Money Profile
     */
    public String getFooterAuuidAMP() {
        String result = null;
        result = getText(pageElements.footerAuuidAMP);
        return result;
    }

    /**
     * This Method will tell us reset pin icon button is disabled or not
     */
    public Boolean isResetPinIconDisable() {
        boolean result = false;
        final String attribute = getAttribute(pageElements.resetPinIcon, "class", false);
        commonLib.info(attribute);
        if (attribute.contains("disabled"))
            result = true;
        return result;
    }
    /*
 This Method will click outside over frontend agent dashboard
  */
    public void clickFEDashboardOutside() {
        clickWithoutLoader(pageElements.dashboardBody);
    }

    /**
     * This method is used to click on Others tab
     */
    public void clickOthersTab() {
        commonLib.pass("Going to click on Others Tab");
        clickWithoutLoader(pageElements.othersTab);
    }

    /**
     * This method is used to click on GSM tab
     */
    public void clickGSMTab() {
        commonLib.pass("Going to click on GSM Tab");
        clickWithoutLoader(pageElements.gsmTab);
    }

    public void checkConfiguration(Configuration config, String label) {
        try {
            commonLib.info("Checking configuration");
            List<CustomerDemographicSection> customerDemographic = config.getResult().getCustomerDemographicDetailsWidgets();
            int flag = 0;
            for (int z = 0; z < customerDemographic.size(); z++) {
                List<Tabs> tabsList = customerDemographic.get(z).getWidgetConfig().getTabs();
                for (int i = 0; i < tabsList.size(); i++) {
                    for (int j = 0; j < tabsList.size(); j++) {
                        List<CustomDetails> customDetail = tabsList.get(i).getCustomDetails().get(j);
                        for (int x = 0; x < customDetail.size(); x++) {
                            String displayName = customDetail.get(x).getDisplayName();
                            if (displayName.equalsIgnoreCase(label) && i == 0) {
                                clickGSMTab();
                                flag++;
                                break;
                            } else if (displayName.equalsIgnoreCase(label) && i == 1) {
                                clickOthersTab();
                                flag++;
                                break;
                            }
                        }
                        if (flag > 0)
                            break;
                    }
                    if (flag > 0)
                        break;
                }
                if (flag > 0)
                    break;
            }
        } catch (Exception e) {
            commonLib.fail("Exception in method - " + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is use to get Email ID
     *
     * @return String The value
     */
    public String getEmail() {
        final String text = getText(pageElements.email);
        commonLib.info("Getting email id " + text);
        return text;
    }

    /**
     * This method is use to get Account number
     *
     * @return String The value
     */
    public String getAccountNumber() {
        final String text = getText(pageElements.accountNumber);
        commonLib.info("Getting account number " + text);
        return text;
    }
}
