package com.airtel.cs.ui.frontendagent.demographicwidget;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.actionconfig.ActionConfigResult;
import com.airtel.cs.model.cs.response.actionconfig.Condition;
import com.airtel.cs.model.cs.response.agents.RoleDetails;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.authconfiguration.Configuration;
import com.airtel.cs.model.cs.response.filedmasking.FieldMaskConfigs;
import com.airtel.cs.model.cs.response.kycprofile.GsmKyc;
import com.airtel.cs.model.cs.response.kycprofile.KYCProfile;
import com.airtel.cs.model.cs.response.kycprofile.Profile;
import com.airtel.cs.model.cs.response.plans.Plans;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

import static com.airtel.cs.commonutils.utils.UtilsMethods.stringNotNull;


@Log4j2
public class DemoGraphicWidgetPrepaidMsisdnTest extends Driver {
    private static String customerNumber = null;
    private String customerName = null;
    RequestSource api = new RequestSource();
    ESBRequestSource esbRequestSource = new ESBRequestSource();
    KYCProfile kycProfile;
    Configuration config;
    GsmKyc gsmKycAPI;


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_PREPAID_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded)
                continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void getConnectionType() {
        try {
            selUtils.addTestcaseDescription("Validate KYC Profile , Getting Connection Type ", "description");
            kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            lineType = kycProfile.getResult().getLineType().toLowerCase().trim();
            config = api.getConfiguration("customerDemographicDetailsWidgets", lineType);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - getConnectionType" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
    public void testCustomerNameAndGsmKYCStatus() {
        try {
            selUtils.addTestcaseDescription("Validate Customer Name, Validate GSM KYC Status", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getMiddleAuuidDGW(), loginAUUID, "Auuid is visible at the middle of the Demo Graphic Widget and is correct", "Auuid is NOT visible at the middle of the Demo Graphic Widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getFooterAuuidDGW(), loginAUUID, "Auuid is visible at the footer of the Demo Graphic Widget and is correct", "Auuid is NOT visible at the footer of the Demo Graphic Widget"));
            pages.getDemoGraphicPage().checkConfiguration(config, "Name");
            gsmKycAPI = api.gsmKYCAPITest(customerNumber);
            final int statusCode = gsmKycAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "GSM KYC API Status Code Matched and is :" + statusCode, "GSM KYC API Status Code NOT Matched and is :" + statusCode, false));
            final String customerName = pages.getDemoGraphicPage().getCustomerName();
            assertCheck.append(actions.matchUiAndAPIResponse(customerName, gsmKycAPI.getResult().getName(), "Customer Name is as Expected", "Customer Name is not as Expected"));
            pages.getDemoGraphicPage().checkConfiguration(config, "GSM KYC Status");
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGsmKycStatus().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(gsmKycAPI.getResult().getGsm()), "Customer's GSM KYC Status is as Expected", "Customer's GSM KYC Status is not as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testCustomerNameAndGsmKYCStatus " + e, true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
    public void testCustomerDetails() {
        try {
            selUtils.addTestcaseDescription("Validate Customer Name,Validate Customer DOB,Validate if Customer has Birthday or Anniversary with Airtel", "description");
            customerName = pages.getDemoGraphicPage().getCustomerName();
            if (customerName.contains("Unable to fetch data") || gsmKycAPI.getResult().getName().isEmpty())
                commonLib.warning("Customer Name is unavailable so customer details are not displayed");
            else {
                pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
                final String customerDOB = pages.getDemoGraphicPage().getCustomerDOB().toLowerCase();
                final String customerIdNumber = pages.getDemoGraphicPage().getIdNumber().replace("*", "");
                final String idType = stringNotNull(pages.getDemoGraphicPage().getIdType()).toLowerCase().trim();
                final String dob = gsmKycAPI.getResult().getDob().substring(0, 11);
                if (!Objects.isNull(dob))
                    assertCheck.append(actions.assertEqualStringType(customerDOB.trim(), pages.getDemoGraphicPage().getKeyValueAPI(dob), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
                else
                    assertCheck.append(actions.assertEqualStringType(customerDOB.trim(), pages.getDemoGraphicPage().getKeyValueAPI(String.valueOf(dob)), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
                if (!customerDOB.equals("-"))
                    if (UtilsMethods.isCustomerBirthday(customerDOB.trim(), "dd-MMM-yyyy")) {
                        commonLib.pass("Today is Customer Birthday");
                        final boolean birthday = pages.getDemoGraphicPage().isBirthday();
                        assertCheck.append(actions.assertEqualBoolean(birthday, true, "Today is customer birthday and birthday icon displayed", "Today is customer birthday but birthday Icon NOT displayed"));
                    }
                assertCheck.append(actions.assertEqualStringType(idType, pages.getDemoGraphicPage().getKeyValueAPI(gsmKycAPI.getResult().getIdentificationType()), "Customer's ID Type is as Expected", "Customer's ID Type is not as Expected"));
                FieldMaskConfigs nationalIdfieldMaskConfigs = api.getFieldMaskConfigs("nationalId");
                List<RoleDetails> agentRoles = UtilsMethods.getAgentDetail().getUserDetails().getUserDetails().getRole();
                boolean hasRole = ObjectUtils.isNotEmpty(nationalIdfieldMaskConfigs.getRoles()) && agentRoles.stream().anyMatch(roleName -> nationalIdfieldMaskConfigs.getRoles().contains(roleName.getRoleName()));
                if (hasRole) {
                    assertCheck.append(actions.assertEqualBoolean(customerIdNumber.length() == nationalIdfieldMaskConfigs.getDigitsVisible(), true, "National Id masking is correct as per user role", "National Id masking is not correct as per user role"));
                } else {
                    pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
                    assertCheck.append(actions.assertEqualBoolean(StringUtils.contains(gsmKycAPI.getResult().getIdentificationNo(), customerIdNumber), true,
                            "Customer's ID Number is as Expected", "Customer's ID Number is not as Expected and Expected was :" + customerIdNumber));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - testCustomerDetails" + e.fillInStackTrace(), true);
        }
    }


        @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testPukDetails () {
            try {
                selUtils.addTestcaseDescription("Verify Auuid shown in middle and at the footer of the demographic widget, Verify PUK is locked or unlocked, If Locked then verify data, else unlock PUK details, Validate PUK1 and PUK2", "description");
                config = api.getConfiguration("customerDemographicDetailsWidgets", lineType);
                pages.getDemoGraphicPage().checkConfiguration(config, "PUK");
                if (pages.getDemoGraphicPage().isPUKInfoLocked()) {
                    pages.getDemoGraphicPage().clickPukUnlock();
                    assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
                    pages.getDemoGraphicPage().selectPolicyQuestion();
                    assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
                    pages.getAuthTabPage().clickAuthBtn();
                    assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getWidgetUnlockMessage(), "Unlocking the widget", "Unlock Widget, Successfully", "Unlock Widget, Un-Successful"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getToastMessage(), "Customer response saved successfully", "Toast Message Matched Successfully", "Toast Message NOT Matched"));
                }
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(), "Customer's PUK1 Number is as Expected", "Customer's PUK1 Number is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(), "Customer's PUK2 Number is as Expected", "Customer's PUK2 Number is not as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testPukDetails " + e, true);
                pages.getAuthTabPage().clickCloseBtn();
            }
        }

        @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testGSMStatus () {
            try {
                selUtils.addTestcaseDescription("Validate GSM Status, Validate data after hovering the GSM status", "description");
                pages.getDemoGraphicPage().checkConfiguration(config, "GSM Status");
                final String gsmStatus = pages.getDemoGraphicPage().getGSMStatus();
                assertCheck.append(actions.assertEqualStringType(gsmStatus.toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's GSM Status is as Expected", "Customer's GSM Status is not as Expected"));
                if (!gsmStatus.contains("Unable to fetch data")) {
                    pages.getDemoGraphicPage().hoverOnSIMStatusInfoIcon();
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getActivationDate().trim(), kycProfile.getResult().getActivationDate(), "Activation Date is as Expected", "Activation Date is not as Expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGSMStatusReasonCode().trim().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getReason()), "Reason is as Expected", "Reason is not as Expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGSMStatusModifiedBy().trim().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getModifiedBy().trim().toLowerCase()), "Modified By is as Expected", "Modified By is not as Expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGSMStatusModifiedDate().trim(), kycProfile.getResult().getModifiedDate(), "Modified Date is as Expected", "Modified Date is not as Expected"));
                }
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testGSMStatus " + e, true);
            }
        }


        @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testSimNumber () {
            try {
                selUtils.addTestcaseDescription("Validate SIM Number,Validate Pin1 and Pin2 after hovering over SIM Number", "description");
                Configuration config = api.getConfiguration("customerDemographicDetailsWidgets", lineType);
                pages.getDemoGraphicPage().checkConfiguration(config, "SIM Number");
                final String simNumber = pages.getDemoGraphicPage().getSimNumber();
                assertCheck.append(actions.assertEqualStringType(simNumber.trim(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSim()), "Customer's SIM Number is as Expected", "Customer's SIM Number is not as Expected"));
                if (!simNumber.equalsIgnoreCase("Unable to Fetch Data")) {
                    pages.getDemoGraphicPage().hoverOnSIMNumberIcon();
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getPIN1(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getPin1()), "PIN1 Matched Successfully", "PIN1 NOT Matched"));
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getPIN2(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getPin2()), "PIN2 Matched Successfully", "PIN2 NOT Matched"));
                }
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (Exception e) {
                commonLib.fail("Exception in Method - testSimNumber" + e.fillInStackTrace(), true);
            }
        }

        @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testDeviceCompatible () {
            try {
                selUtils.addTestcaseDescription("Validate Device Compatibility and SIM type", "description");
                Profile profileAPI = api.profileAPITest(customerNumber);
                final int profileAPIStatusCode = profileAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(profileAPIStatusCode, 200, "Profile API Status Code Matched and is :" + profileAPIStatusCode, "Profile API Status Code NOT Matched and is :" + profileAPIStatusCode, false));
                pages.getDemoGraphicPage().checkConfiguration(config, "Device Type");
                final String simType = pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSimType());
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getSimType().trim(), simType.toUpperCase(), "Customer's SIM Type is as Expected and is -" + simType, "Customer's SIM Type is not as Expected"));
                pages.getDemoGraphicPage().checkConfiguration(config, "4G Compatibility");
                final String deviceType = pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getDeviceType());
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDeviceCompatible().toLowerCase(), deviceType, "Customer's Device Type is as Expected and is - " + deviceType, "Customer's Device Type is not as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testDeviceCompatible " + e, true);
            }
        }

        @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testDeviceInfo () {
            try {
                selUtils.addTestcaseDescription("Validate Device Info on hovering over Device Info icon", "description");
                Profile profileAPI = api.profileAPITest(customerNumber);
                final int profileAPIStatusCode = profileAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(profileAPIStatusCode, 200, "Profile API Status Code Matched and is :" + profileAPIStatusCode, "Profile API Status Code NOT Matched and is :" + profileAPIStatusCode, false));
                pages.getDemoGraphicPage().hoverOnDeviceInfoIcon();
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getIMEINumber().trim(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getImeiNumber()), "Customer device IMEI number as expected", "Customer device IMEI number as not expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDeviceType().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getDeviceType().toLowerCase().trim()), "Customer device type as expected", "Customer device type as not expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getBrand().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getBrand().toLowerCase().trim()), "Customer device Brand as expected", "Customer device Brand as not expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDeviceModel().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getModel().toLowerCase().trim()), "Customer device model as expected", "Customer device model as not expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemoGraphicPage().getDeviceOS(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getOs()), "Customer device OS as expected", "Customer device OS as not expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testDeviceInfo " + e, true);
            }
        }

        @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testConnectionTypeAndSegment () {
            try {
                selUtils.addTestcaseDescription("Validate Connection Type, Validate Segment , Validate Subsegment and SubCategory on hovering over Segment icon", "description");
                pages.getDemoGraphicPage().checkConfiguration(config, "Connection Type");
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getLineType().toLowerCase().trim()), "Customer Connection Type is as expected", "Customer Connection Type as not expected"));
                pages.getDemoGraphicPage().checkConfiguration(config, "Segment");
                final String segment = pages.getDemoGraphicPage().getSegment();
                assertCheck.append(actions.assertEqualStringType(segment.toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSegment().toLowerCase().trim()), "Customer Segment as expected", "Customer Segment as not expected"));
                if (!segment.contains("Unable to fetch data")) {
                    pages.getDemoGraphicPage().hoverOnSegmentInfoIcon();
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getSubSegment().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSubSegment().toLowerCase().trim()), "Customer Sub Segment as expected", "Customer Sub Segment as not expected"));
                    final String serviceCategory = pages.getDemoGraphicPage().getServiceCategory();
                    assertCheck.append(actions.assertEqualStringType(serviceCategory.toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getServiceCategory()), "Customer Service Category as expected", "Customer Service Category as not expected and is: " + serviceCategory));
                }
                if (kycProfile.getResult().getVip()) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getDemoGraphicPage().isVIP(), true, "Customer is VIP and Icon displayed as expected", "Customer is VIP but Icon does not display as expected"));
                }
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testConnectionTypeAndSegment" + e, true);
            }

        }

        @Test(priority = 11, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testServiceClassAndAppStatus () {
            try {
                selUtils.addTestcaseDescription("Validate Service Class, Validate App Status", "description");
                Profile profileAPI = api.profileAPITest(customerNumber);
                pages.getDemoGraphicPage().checkConfiguration(config, "Service Class");
                final String serviceClass = kycProfile.getResult().getServiceClass();
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getServiceClass().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(String.valueOf(serviceClass)), "Customer Service Class as expected", "Customer Service Class as not expected"));
                pages.getDemoGraphicPage().checkConfiguration(config, "App Status");
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getAppStatus().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getAppStatus()), "App Status as expected", "App Status as not expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testServiceClassAndAppStatus " + e, true);
            }
        }

        @Test(priority = 12, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testDataManager () {
            try {
                selUtils.addTestcaseDescription("Validate Data Manager,Validate Data Manager Status", "description");
                pages.getDemoGraphicPage().checkConfiguration(config, "Data Manager");
                Plans plansAPI = api.accountPlansTest(customerNumber);
                final int statusCode = plansAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Plan API Status Code Matched and is :" + statusCode, "Plan API Status Code NOT Matched and is :" + statusCode, false));
                final String dataManager = pages.getDemoGraphicPage().getKeyValueAPI(String.valueOf(plansAPI.getResult().getDataManager()));
                if (StringUtils.equalsIgnoreCase(constants.getValue(ApplicationConstants.DATA_MANAGER_TOGGLEABLE), "True"))
                    if (pages.getDemoGraphicPage().getDataManagerStatus().equalsIgnoreCase("true"))
                        assertCheck.append(actions.assertEqualStringType("on", dataManager.toLowerCase().trim(), "Customer's Data Manager Status is as Expected", "Customer's Data Manager Status is not as Expected"));
                    else
                        assertCheck.append(actions.assertEqualStringType("off", dataManager.toLowerCase().trim(), "Customer's Data Manager Status is as Expected", "Customer's Data Manager Status is not as Expected"));
                else
                    assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDataManagerValue(), dataManager, "Data Manager Value is as Expected", "Data Manager Value is NOT as Expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
                commonLib.fail("Exception in method - testDataManager " + e, true);
            }

        }

        @Test(priority = 13, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "getConnectionType"})
        public void testEmailIdAndAccountNumber () {
            try {
                selUtils.addTestcaseDescription("Validate Email Id ", "description");
                pages.getDemoGraphicPage().checkConfiguration(config, "Email Id");
                List<String> customerDetails = api.searchAPITest(customerNumber);
                final String emailId = pages.getAccountInformationWidget().getValue(customerDetails, "KYC", "email");
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getEmail(), emailId, "Customer's Email ID is as expected", "Customer's Email ID is not as expected"));
                pages.getDemoGraphicPage().checkConfiguration(config, "Account Number");
                final String accountNumber = pages.getAccountInformationWidget().getValue(customerDetails, "KYC", "customerAccountNumber");
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getAccountNumber(), accountNumber, "Customer's Account Number  is as expected", "Customer's Account number is not as expected"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (Exception e) {
                commonLib.fail("Exception in method - testEmailIdAndAccountNumber" + e.fillInStackTrace(), true);
            }
        }

        @Test(priority = 14, groups = {"SanityTest", "RegressionTest", "ProdTest"})
        public void testServiceClassRatePlanAPI () {
            try {
                selUtils.addTestcaseDescription("Validate Service Class and Rate Plan", "description");
                final String msisdn = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
                List<String> customerDetails = api.searchAPITest(msisdn);
                assertCheck.append(actions.assertEqualStringNotNull(pages.getAccountInformationWidget().getValue(customerDetails, "KYC", "serviceClass"), "Service Class and Rate Plan test case pass", "Service Class and Rate Plan test case fail", false));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (Exception e) {
                commonLib.fail("Exception in method - testServiceClassRatePlanAPI " + e.fillInStackTrace(), true);
            }
        }

        @Test(priority = 15, groups = {"RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
        public void invalidMSISDNTest () {
            try {
                selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
                pages.getDemoGraphicPage().enterMSISDN(constants.getValue("cs.invalid.msisdn"));
                pages.getDemoGraphicPage().waitTillLoaderGetsRemoved();
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Entered customer number is Invalid", "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (Exception e) {
                commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
            }
        }


    }
