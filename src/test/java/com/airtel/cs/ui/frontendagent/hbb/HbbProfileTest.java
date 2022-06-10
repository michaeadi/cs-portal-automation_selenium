package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.model.cs.response.hbb.HbbUserDetailsResponse;
import org.testng.SkipException;
import com.airtel.cs.driver.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class HbbProfileTest extends Driver {
    private static String hbbCustomerNumber = null;

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
            hbbCustomerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testHbbProfileInfo() {
        try {
            selUtils.addTestcaseDescription(
                    "Validate User Name,Alternate Number,Email id and Device Type", "description");
            HbbUserDetailsResponse hbbUser = api.hbbUserDetailsTest(hbbCustomerNumber, "PRIMARY");
            List<String> customerDetails = api.searchAPITest(hbbCustomerNumber);
            final int statusCode = hbbUser.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Hbb User Details Status Code Matched and is :" + statusCode, "Hbb User Details Code NOT Matched and is :" + statusCode, false));
            final String userName = pages.getHbbProfilePage().getUserName();
            final String alternateNumber = pages.getHbbProfilePage().getAlternateNumber();
            final String emailId = pages.getHbbProfilePage().getEmailId();
            final String deviceType = pages.getHbbProfilePage().getDeviceType();
            final String deviceTypeFromAPI = pages.getAccountInformationWidget().getValue(customerDetails, "HBB", "hbbDeviceType");
            assertCheck.append(actions.matchUiAndAPIResponse(userName, hbbUser.getResult().getUserName(),
                    "User Name is as Expected", "User Name is not as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(alternateNumber, String.valueOf(hbbUser.getResult().getAlternateMsisdnList()),
                    "Alternate Msisdn is as Expected", "Alternate Msisdn  is not as Expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(emailId, hbbUser.getResult().getEmail(),
                    "Email id  is as Expected", "Email id is not as Expected"));
            assertCheck.append(actions.assertEqualStringType(deviceType, deviceTypeFromAPI, "Device Type is as Expected and is " + deviceType, "Device Type is NOT as Expected and is" + deviceType));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testHbbProfile " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testSimBarUnbar() {
        try {
            selUtils.addTestcaseDescription(
                    "Validating Sim Bar/Unbar status according to GSM status ", "description");
            String status = pages.getDemoGraphicPage().getGSMStatus();
            if (status.equals("Active"))
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSuspendSIMVisible(), true, "When GSM status is Active , Suspend SIM option is visible  ", "When GSM status is Active , Suspend SIM option is not visible"));
            if (status.equals("Suspended"))
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isReactivateSimVisible(), true, "When GSM status is Suspended , Reactivate SIM option is visible  ", "When GSM status is Active , Reactivate  SIM option is not visible"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testSimBarUnbar " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
