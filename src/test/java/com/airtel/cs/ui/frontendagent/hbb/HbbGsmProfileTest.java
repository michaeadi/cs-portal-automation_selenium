package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.agents.RoleDetails;
import com.airtel.cs.model.response.filedmasking.FieldMaskConfigs;
import com.airtel.cs.model.response.kycprofile.GsmKyc;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import com.airtel.cs.model.response.kycprofile.Profile;
import io.restassured.http.Headers;
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

public class HbbGsmProfileTest extends Driver {

    private static String customerNumber = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_PREPAID_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"}, enabled = false)
    public void testPukDetails() {
        try {
            selUtils.addTestcaseDescription(
                    "Verify PUK is locked or unlocked, If Locked then verify data, else unlock PUK details, Validate PUK1 and PUK2", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            if (pages.getDemoGraphicPage().isPUKInfoLocked()) {
                pages.getDemoGraphicPage().clickPUKToUnlock();
                assertCheck.append(actions
                        .assertEqualBoolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly",
                                "Authentication tab does not load correctly"));
                pages.getDemoGraphicPage().selectPolicyQuestion();
                assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthBtnEnable(), true,
                        "Authenticate Button enabled after minimum number of question chosen",
                        "Authenticate Button does not enable after choose minimum number of question"));
                pages.getAuthTabPage().clickAuthBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getWidgetUnlockMessage(), "Unlocking the widget", "Unlock Widget, Successfully", "Unlock Widget, Un-Successful"));
                assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getToastMessage(), "Customer response saved successfully", "Toast Message Matched Successfully", "Toast Message NOT Matched"));

            }
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getPUK1().trim(), kycProfile.getResult().getPuk().get(0).getValue(),
                    "Customer's PUK1 Number is as Expected", "Customer's PUK1 Number is not as Expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getPUK2().trim(), kycProfile.getResult().getPuk().get(1).getValue(),
                    "Customer's PUK2 Number is as Expected", "Customer's PUK2 Number is not as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testPukDetails " + e, true);
            pages.getAuthTabPage().clickCloseBtn();
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testCustomerInfoAndGSMKYCStatus() {
        try {
            selUtils.addTestcaseDescription(
                    "Validate Customer Name,Validate Customer DOB,Validate if Customer has Birthday or Anniversary with Airtel, Validate GSM KYC Status", "description");
            GsmKyc gsmKycAPI = api.gsmKYCAPITest(customerNumber);
            final int statusCode = gsmKycAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "GSM KYC API Status Code Matched and is :" + statusCode, "GSM KYC API Status Code NOT Matched and is :" + statusCode, false));
            final String customerName = pages.getDemoGraphicPage().getCustomerName();
            assertCheck.append(actions.matchUiAndAPIResponse(customerName, gsmKycAPI.getResult().getName(),
                    "Customer Name is as Expected", "Customer Name is not as Expected"));
            if (!customerName.contains("Unable to fetch data")) {
                pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
                final String customerDOB = pages.getDemoGraphicPage().getCustomerDOB().toLowerCase();
                final String customerIdNumber = pages.getDemoGraphicPage().getIdNumber().replace("*", "");
                final String idType = stringNotNull(pages.getDemoGraphicPage().getIdType()).toLowerCase().trim();
                final String dob = gsmKycAPI.getResult().getDob().substring(0, 11);
                if (!Objects.isNull(dob))
                    assertCheck.append(actions
                            .assertEqualStringType(customerDOB.trim(), pages.getDemoGraphicPage().getKeyValueAPI(dob), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
                else
                    assertCheck.append(actions
                            .assertEqualStringType(customerDOB.trim(), pages.getDemoGraphicPage().getKeyValueAPI(String.valueOf(dob)), "Customer DOB is as Expected", "Customer DOB is not as Expected"));
                if (!customerDOB.equals("-"))
                    if (UtilsMethods.isCustomerBirthday(customerDOB.trim(), "dd-MMM-yyyy")) {
                        commonLib.pass("Today is Customer Birthday");
                        final boolean birthday = pages.getDemoGraphicPage().isBirthday();
                        assertCheck.append(actions.assertEqualBoolean(birthday, true, "Today is customer birthday and birthday icon displayed",
                                "Today is customer birthday but birthday Icon NOT displayed"));
                    }
                assertCheck.append(actions.assertEqualStringType(idType, pages.getDemoGraphicPage().getKeyValueAPI(gsmKycAPI.getResult().getIdentificationType()),
                        "Customer's ID Type is as Expected", "Customer's ID Type is not as Expected"));

                FieldMaskConfigs nationalIdfieldMaskConfigs = api.getFieldMaskConfigs("nationalId");
                List<RoleDetails> agentRoles = UtilsMethods.getAgentDetail(new Headers(map)).getUserDetails().getUserDetails()
                        .getRole();
                boolean hasRole = ObjectUtils.isNotEmpty(nationalIdfieldMaskConfigs.getRoles()) && agentRoles.stream().anyMatch(roleName -> nationalIdfieldMaskConfigs.getRoles().contains(roleName.getRoleName()));
                if (hasRole) {
                    assertCheck.append(actions.assertEqualBoolean(customerIdNumber.length() == nationalIdfieldMaskConfigs.getDigitsVisible(), true, "National Id masking is correct as per user role", "National Id masking is not correct as per user role"));
                } else {
                    pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
                    assertCheck.append(actions.assertEqualBoolean(StringUtils.contains(gsmKycAPI.getResult().getIdentificationNo(), customerIdNumber), true,
                            "Customer's ID Number is as Expected", "Customer's ID Number is not as Expected and Expected was :" + customerIdNumber));
                }
            }
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGsmKycStatus().toLowerCase(), pages.getDemoGraphicPage().getKeyValueAPI(gsmKycAPI.getResult().getGsm()),
                    "Customer's GSM KYC Status is as Expected", "Customer's GSM KYC Status is not as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testCustomerInfo " + e, true);
        }
    }
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testGSMStatusAndSIMNumber() {
        try {
            selUtils.addTestcaseDescription(
                    "Validate SIM Number,Validate Activation date after hovering over SIM Number,Validate GSM Status, Validate data after hovering the GSM status",
                    "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            final String gsmStatus = pages.getDemoGraphicPage().getGSMStatus();
            assertCheck.append(actions.assertEqualStringType(gsmStatus.toLowerCase().trim(), kycProfile.getResult().getStatus().toLowerCase().trim(), "Customer's SIM Status is as Expected", "Customer's SIM Status is not as Expected"));
            if (!gsmStatus.contains("Unable to fetch data")) {
                pages.getDemoGraphicPage().hoverOnSIMStatusInfoIcon();
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getActivationDate().trim(), UtilsMethods.getDateFromEpoch(Long.parseLong(kycProfile.getResult().getActivationDate()), "dd-MMM-yyy"),
                        "Customer's Activation Date is as Expected", "Customer's Activation Date is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGSMStatusReasonCode().trim().toLowerCase(),
                        pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getReason()), "Customer SIM Status Reason is as Expected",
                        "Customer SIM Status Reason is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGSMStatusModifiedBy().trim().toLowerCase(),
                        pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getModifiedBy().trim().toLowerCase()), "Customer SIM Status Modified By is as Expected",
                        "Customer SIM Status Modified By is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getGSMStatusModifiedDate().trim(),
                        kycProfile.getResult().getModifiedDate(),
                        "Customer SIM Status, Modified Date is as Expected", "Customer SIM Status, Modified Date is not as Expected"));
            }
            final String simNumber = pages.getDemoGraphicPage().getSimNumber();
            assertCheck.append(actions.assertEqualStringType(simNumber.trim(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSim()),
                    "Customer's SIM Number is as Expected", "Customer's SIM Number is not as Expected"));
            if (!simNumber.equalsIgnoreCase("Unable to Fetch Data")) {
                pages.getDemoGraphicPage().hoverOnSIMNumberIcon();
                assertCheck.append(actions
                        .assertEqualStringType(pages.getDemoGraphicPage().getPIN1(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getPin1()), "PIN1 Matched Successfully",
                                "PIN1 NOT Matched"));
                assertCheck.append(actions
                        .assertEqualStringType(pages.getDemoGraphicPage().getPIN2(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getPin2()), "PIN2 Matched Successfully",
                                "PIN2 NOT Matched"));
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testSIMNumberAndGSMStatus " + e, true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testDeviceCompatible() {
        try {
            selUtils.addTestcaseDescription("Validate Device Compatible", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            Profile profileAPI = api.profileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            final int profileAPIStatusCode = profileAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(profileAPIStatusCode, 200, "Profile API Status Code Matched and is :" + profileAPIStatusCode, "Profile API Status Code NOT Matched and is :" + profileAPIStatusCode, false));
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            final String simType = pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSimType());
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getSimType().trim(), simType.toUpperCase(),
                    "Customer's SIM Type is as Expected and is -" + simType, "Customer's SIM Type is not as Expected"));
            final String deviceType = pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getDeviceType());
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDeviceCompatible().toLowerCase(), deviceType,
                    "Customer's Device Type is as Expected and is - " + deviceType, "Customer's Device Type is not as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testDeviceCompatible " + e, true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testDeviceInfo() {
        try {
            selUtils.addTestcaseDescription(
                    "Validate Device Info like IMEI Number,Validate data on hover like Device Type Brand Device Model and Device OS", "description");
            Profile profileAPI = api.profileAPITest(customerNumber);
            final int profileAPIStatusCode = profileAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(profileAPIStatusCode, 200, "Profile API Status Code Matched and is :" + profileAPIStatusCode, "Profile API Status Code NOT Matched and is :" + profileAPIStatusCode, false));
            pages.getDemoGraphicPage().hoverOnDeviceInfoIcon();
            assertCheck.append(actions
                    .assertEqualStringType(pages.getDemoGraphicPage().getIMEINumber().trim(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getImeiNumber()),
                            "Customer device IMEI number as expected", "Customer device IMEI number as not expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDeviceType().toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getDeviceType().toLowerCase().trim()), "Customer device type as expected",
                    "Customer device type as not expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getBrand().toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getBrand().toLowerCase().trim()), "Customer device Brand as expected",
                    "Customer device Brand as not expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getDeviceModel().toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getModel().toLowerCase().trim()), "Customer device model as expected",
                    "Customer device model as not expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemoGraphicPage().getDeviceOS(), pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getOs()),
                    "Customer device OS as expected", "Customer device OS as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testDeviceInfo " + e, true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testConnectionTypeServiceCategorySegment() {
        try {
            selUtils.addTestcaseDescription("Validate Connection Type, Validate Service Category,Validate Segment, Validate Service Class",
                    "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getLineType().toLowerCase().trim()), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            final String segment = pages.getDemoGraphicPage().getSegment();
            assertCheck.append(actions.assertEqualStringType(segment.toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSegment().toLowerCase().trim()), "Customer Segment as expected", "Customer Segment as not expected"));
            if (!segment.contains("Unable to fetch data")) {
                pages.getDemoGraphicPage().hoverOnSegmentInfoIcon();
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getSubSegment(), pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getSubSegment()), "Customer Sub Segment as expected",
                        "Customer Sub Segment as not expected"));
                final String serviceCategory = pages.getDemoGraphicPage().getServiceCategory();
                assertCheck.append(actions.assertEqualStringType(serviceCategory.toLowerCase().trim(),
                        pages.getDemoGraphicPage().getKeyValueAPI(kycProfile.getResult().getServiceCategory()), "Customer Service Category as expected",
                        "Customer Service Category as not expected and is: " + serviceCategory));
            }
            if (kycProfile.getResult().getVip()) {
                assertCheck.append(actions
                        .assertEqualBoolean(pages.getDemoGraphicPage().isVIP(), true, "Customer is VIP but Icon displayed as expected",
                                "Customer is VIP but Icon does not display as expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testConnectionTypeServiceCategorySegment " + e, true);
        }

    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testServiceClassAndAppStatus() {
        try {
            selUtils.addTestcaseDescription("Validate Service Class, Validate App Status", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            Profile profileAPI = api.profileAPITest(customerNumber);
            final String serviceClass = kycProfile.getResult().getServiceClass();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getServiceClass().toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(String.valueOf(serviceClass)), "Customer Service Class as expected",
                    "Customer Service Class as not expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getAppStatus().toLowerCase().trim(),
                    pages.getDemoGraphicPage().getKeyValueAPI(profileAPI.getResult().getAppStatus()), "App Status as expected",
                    "App Status as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testServiceClassAndAppStatus " + e, true);
        }
    }


}
