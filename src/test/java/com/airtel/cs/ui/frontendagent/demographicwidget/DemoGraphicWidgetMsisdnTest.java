package com.airtel.cs.ui.frontendagent.demographicwidget;


import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AuthTab;
import com.airtel.cs.pagerepository.pagemethods.DemoGraphic;
import com.airtel.cs.pojo.AMProfilePOJO;
import com.airtel.cs.pojo.GsmKycPOJO;
import com.airtel.cs.pojo.PlansPOJO;
import com.airtel.cs.pojo.ProfilePOJO;
import com.airtel.cs.pojo.kycprofile.KYCProfile;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Log4j2
public class DemoGraphicWidgetMsisdnTest extends Driver {

    APIEndPoints api = new APIEndPoints();
    private final BaseActions actions = new BaseActions();
    private static String customerNumber = null;

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, description = "Validate PUK Details under Demo Graphic Widget", dependsOnMethods = {"openCustomerInteraction"})
    public void testPukDetails() {
        try {
            selUtils.addTestcaseDescription("Verify PUK is locked or unlocked, If Locked then verify data, else unlock PUK details, Validate PUK1 and PUK2", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            if (!(kycProfile == null)) {
                if (pages.getDemoGraphicPage().isPUKInfoLock()) {
                    pages.getDemoGraphicPage().clickPUKToUnlock();
                    assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                    pages.getDemoGraphicPage().checkPolicyQuestion();
                    assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                    pages.getAuthTabPage().clickAuthBtn();
                }
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is as Expected", "Customer's PUK1 Number is not as Expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is as Expected", "Customer's PUK2 Number is not as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } else {
                commonLib.fail("kycProfile is NULL", false);
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException | InterruptedException e) {
            pages.getAuthTabPage().clickCloseBtn();
            commonLib.fail("Exception in method - testPukDetails " + e, true);
        }
    }

    @Test(priority = 3, description = "Validate Airtel Money Profile Widget under Demo Graphic Widget", dependsOnMethods = {"openCustomerInteraction"})
    public void testAirtelMoneyProfile() {
        try {
            selUtils.addTestcaseDescription("Verify Airtel Money Profile is locked or unlocked, if locked then verify data, else unlock Airtel Money Profile", "description");
            if (StringUtils.equalsIgnoreCase(constants.getValue(ApplicationConstants.AIRTEL_MONEY_PROFILE), "true")) {
                ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
                AMProfilePOJO amProfileAPI = api.amServiceProfileAPITest(customerNumber);
                if (!(profileAPI == null && amProfileAPI == null)) {
                    if (pages.getDemoGraphicPage().isAirtelMoneyStatusLock()) {
                        pages.getDemoGraphicPage().clickAirtelStatusToUnlock();
                        assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                        pages.getDemoGraphicPage().checkPolicyQuestion();
                        assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                        pages.getAuthTabPage().clickAuthBtn();
                    }
                    assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getAirtelMoneyStatus().toLowerCase().trim(), profileAPI.getResult().getAirtelMoneyStatus().toLowerCase().trim(), "Customer's Airtel Money Status is as Expected", "Customer's Airtel Money Status is not as Expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getServiceStatus().toLowerCase().trim(), profileAPI.getResult().getServiceStatus().toLowerCase().trim(), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getWalletBalance().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
                    if (StringUtils.equalsIgnoreCase(constants.getValue(ApplicationConstants.MULTI_WALLET_BALANCE), "true")) {
                        try {
                            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getWalletBalance2().toUpperCase().trim(), amProfileAPI.getResult().getWallet().get(1).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallet().get(1).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
                        } catch (NoSuchElementException | TimeoutException e) {
                            commonLib.fail("Customer's Airtel Money 2nd Wallet Balance is not visible" + e.getCause(), true);
                        }
                    }
                    assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getRegistrationStatus().toLowerCase().trim(), amProfileAPI.getResult().getRegStatus().toLowerCase().trim(), "Customer's Airtel Money Registration Status as Expected", "Customer's Airtel Money Registration Status not same not as Expected"));
                    actions.assertAllFoundFailedAssert(assertCheck);
                } else {
                    commonLib.fail("profileAPI or amProfileAPI  is NULL", false);
                }
            } else {
                commonLib.info("Airtel Money Profile is Not for Opco=" + OPCO);
            }

        } catch (NoSuchElementException | TimeoutException | NullPointerException | InterruptedException e) {
            pages.getAuthTabPage().clickCloseBtn();
            commonLib.fail("Exception in method - testAirtelMoneyProfile " + e, true);
        }

    }

    @Test(priority = 4, description = "Validate Customer Information under Demo Graphic Widget", dependsOnMethods = {"openCustomerInteraction"})
    public void testCustomerInfo() {
        try {
            selUtils.addTestcaseDescription("Validate Customer Name,Validate Customer DOB,Validate if Customer has Birthday or Anniversary with Airtel", "description");
            GsmKycPOJO gsmKycAPI = api.gsmKYCAPITest(customerNumber);
            if (!(gsmKycAPI == null)) {
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getCustomerName().trim(), gsmKycAPI.getResult().getName().trim(), "Customer Name is as Expected", "Customer Name is not as Expected"));
                pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
                final String customerDOB = pages.getDemoGraphicPage().getCustomerDOB();
                assertCheck.append(actions.assertEqual_stringType(customerDOB.trim(), UtilsMethods.getDateFromEpoch(gsmKycAPI.getResult().getDob(), "dd-MMM-yyyy"), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
                if (UtilsMethods.isCustomerBirthday(customerDOB.trim(), "dd-MMM-yyyy")) {
                    commonLib.pass("Today is Customer Birthday");
                    final boolean birthday = pages.getDemoGraphicPage().isBirthday();
                    assertCheck.append(actions.assertEqual_boolean(birthday, true, "Today is customer birthday and birthday icon displayed", "Today is customer birthday but birthday Icon NOT displayed"));
                }
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getIdType().trim(), gsmKycAPI.getResult().getIdentificationType() == null || gsmKycAPI.getResult().getIdentificationType().equals("") ? "-" : gsmKycAPI.getResult().getIdentificationType().toLowerCase().trim(), "Customer's ID Type is as Expected", "Customer's ID Type is not as Expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getIdNumber().replace("*", ""), gsmKycAPI.getResult().getIdentificationNo() == null || gsmKycAPI.getResult().getIdentificationNo().equals("") ? "-" : gsmKycAPI.getResult().getIdentificationNo().toLowerCase().trim(), "Customer's ID Number is as Expected", "Customer's ID Number is not as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } else {
                commonLib.fail("gsmKycAPI is NULL", false);
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testCustomerInfo " + e, true);
        }
    }

    @Test(priority = 5, description = "Validate SIM Number and GSM Status Module under Demo Graphic Widget", dependsOnMethods = {"openCustomerInteraction"})
    public void testSIMNumberAndGSMStatus() {
        try {
            selUtils.addTestcaseDescription("Validate SIM Number,Validate Activation date after hovering over SIM Number,Validate GSM Status, Validate data after hovering the GSM status", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            if (!(kycProfile == null)) {
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getSimNumber().trim(), kycProfile.getResult().getSim(), "Customer's SIM Number is as Expected", "Customer's SIM Number is not as Expected"));
                pages.getDemoGraphicPage().hoverOnSIMNumberIcon();
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getActivationDate().trim(), UtilsMethods.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd-MMM-yyy"), "Customer's Activation Date is as Expected", "Customer's Activation Date is not as Expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getGSMStatus().toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is as Expected", "Customer's SIM Status is not as Expected"));
                pages.getDemoGraphicPage().hoverOnSIMStatusInfoIcon();
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getGSMStatusReasonCode().trim().toLowerCase(), kycProfile.getResult().getReason() == null || kycProfile.getResult().getReason().equals("") ? "-" : kycProfile.getResult().getReason().toLowerCase().trim(), "Customer SIM Status Reason is as Expected", "Customer SIM Status Reason is not as Expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getGSMStatusModifiedBy().trim().toLowerCase(), kycProfile.getResult().getModifiedBy().trim().toLowerCase(), "Customer SIM Status Modified By is as Expected", "Customer SIM Status Modified By is not as Expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getGSMStatusModifiedDate().trim(), UtilsMethods.getDateFromString(kycProfile.getResult().getModifiedDate(), "dd-MMM-yyy hh:mm aa", "dd-MMM-yyyy hh:mm aa").replace("am", "AM").replace("pm", "PM"), "Customer SIM Status Modified Date is as Expected", "Customer SIM Status Modified Date is not as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } else {
                commonLib.fail("kycProfile is NULL", false);
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testSIMNumberAndGSMStatus " + e, true);
        }
    }

    @Test(priority = 6, description = "Validate Data Manager and Device Compatible", dependsOnMethods = {"openCustomerInteraction"})
    public void testDataManagerAndDeviceCompatible() {
        try {
            selUtils.addTestcaseDescription("Validate Data Manager Status,Validate Device Compatible", "description");
            PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
            if (StringUtils.equalsIgnoreCase(constants.getValue(ApplicationConstants.DATA_MANAGER_TOGGLEABLE), "True"))
                if (pages.getDemoGraphicPage().getDataManagerStatus().equalsIgnoreCase("true"))
                    assertCheck.append(actions.assertEqual_stringType("on", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is as Expected", "Customer's Data Manager Status is not as Expected"));
                else
                    assertCheck.append(actions.assertEqual_stringType("off", plansAPI.getResult().getDataManager().toLowerCase().trim(), "Customer's Data Manager Status is as Expected", "Customer's Data Manager Status is not as Expected"));
            else
                assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getDataManagerValue(), plansAPI.getResult().getDataManager() == null || plansAPI.getResult().getDataManager().equals("") ? "-" : plansAPI.getResult().getDataManager().toLowerCase().trim(), "Data Manager Value is as Expected", "Data Manager Value is NOT as Expected"));
            final String deviceType = profileAPI.getResult().getDeviceType();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getDeviceCompatible(), deviceType, "Customer's Device Type is as Expected and is - " + deviceType, "Customer's Device Type is not as Expected"));
            final String simType = kycProfile.getResult().getSimType();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getSimType().trim(), simType, "Customer's SIM Type is as Expected and is -" + simType, "Customer's SIM Type is not as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testDataManagerAndDeviceCompatible " + e, true);
        }
    }

    @Test(priority = 7, description = "Validate Connection type,Service Category and Segment", dependsOnMethods = {"openCustomerInteraction"})
    public void testConnectionTypeServiceCategorySegment() {
        selUtils.addTestcaseDescription("Validate Connection Type, Validate Service Category,Validate Segment, Validate Service Class", "description");
        KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
        try {
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(), kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type as expected", "Customer Connection Type as not expected"));
            pages.getDemoGraphicPage().hoverOnSegmentInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getServiceCategory().toLowerCase().trim(), kycProfile.getResult().getServiceCategory().toLowerCase().trim(), "Customer Service Category as expected", "Customer Service Category as not expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getSegment().toLowerCase().trim(), kycProfile.getResult().getSegment().toLowerCase().trim(), "Customer Segment as expected", "Customer Segment as not expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getServiceClass().toLowerCase().trim(), kycProfile.getResult().getServiceClass().toLowerCase().trim(), "Customer Service Class as expected", "Customer Service Class as not expected"));
            if (kycProfile.getResult().getVip()) {
                assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().isVIP(), true, "Customer is VIP but Icon displayed as expected", "Customer is VIP but Icon does not display as expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testConnectionTypeServiceCategorySegment " + e, true);
        }

    }

    @Test(priority = 8, description = "Validate Device info", dependsOnMethods = {"openCustomerInteraction"})
    public void testDeviceInfo() {
        try {
            selUtils.addTestcaseDescription("Validate Device Info like IMEI Number,Validate data on hover like Device Type Brand Device Model and Device OS", "description");
            ProfilePOJO profileAPI = api.profileAPITest(customerNumber);
            pages.getDemoGraphicPage().hoverOnDeviceInfoIcon();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getIMEINumber().trim(), profileAPI.getResult().getImeiNumber(), "Customer device IMEI number as expected", "Customer device IMEI number as not expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getDeviceType().toLowerCase().trim(), profileAPI.getResult().getDeviceType().toLowerCase().trim(), "Customer device type as expected", "Customer device type as not expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getBrand().toLowerCase().trim(), profileAPI.getResult().getBrand().toLowerCase().trim(), "Customer device Brand as expected", "Customer device Brand as not expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getDeviceModel().toLowerCase().trim(), profileAPI.getResult().getModel().toLowerCase().trim(), "Customer device model as expected", "Customer device model as not expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getDeviceOS().toLowerCase().trim(), profileAPI.getResult().getOs().toLowerCase().trim(), "Customer device OS as expected", "Customer device OS as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testDeviceInfo " + e, true);
        }
    }

    @Test(priority = 9, description = "As an agent I want capability to check if an MSISDN is valid or invalid")
    public void invalidMSISDNTest() {
        try {
            selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
            pages.getDemoGraphicPage().enterMSISDN("123456789");
            pages.getDemoGraphicPage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Entered customer number is Invalid", "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
        }
    }


}
