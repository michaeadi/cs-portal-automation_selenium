package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.CurrentPlanWidget;
import com.airtel.cs.pojo.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CurrentPlanTest extends Driver {
    private static String customerNumber = null;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
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

    /**
     * This method is used to validate MSISDN
     */

    @Test(priority = 2, groups = {"RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void invalidMSISDNTest() {
        try {
            selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
            pages.getDemoGraphicPage().enterMSISDN("123456789");
            assertCheck.append(actions
                    .assertEqual_stringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Entered customer number is Invalid",
                            "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check whether user has permission for Current Plan Widget
     * isCurrentPlanWidgetDisplay() - todo
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasCurrentPlanWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that current plan widget should be visible to the logged in agent if current plan permission is enabled in UM, Check User has permission to view current plan Widget Permission", "description");
            String currentPlanPermission = constants.getValue(PermissionConstants.CURRENT_PLAN_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), currentPlanPermission), "Current plan Widget displayed correctly as per user permission", "Current plan Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPlanAndPackWidgetPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate watermarked in the left corner and middle
     * isCurrentPlanWidgetDisplay() -- todo
     */

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void currentPlanWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate is current plan widget Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), true, " Current Plan widget displayed", "Current Plan widget not displayed"));
            assertCheck.append(actions.assertEqual_stringType(pages.getCurrentPlanWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the Your Current Plan widget and is correct", "Auuid NOT shown at the footer of Your Current Plan widget"));
            assertCheck.append(actions.assertEqual_stringType(pages.getCurrentPlanWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the Your Current Plan widget and is correct", "Auuid NOT shown at the middle of Your Current Plan widget"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanWatermarkTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to show current plan widget on the basis of connection type and UM permission
     */

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that current plan widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umPermission = pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), true, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else if (connectionType.equalsIgnoreCase("POSTPAID") && !umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), false, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else if (connectionType.equalsIgnoreCase("PREPAID") && umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), true, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else if (connectionType.equalsIgnoreCase("PREPAID") && !umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), false, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else {
                commonLib.fail(" Current plan widget is not displayed", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate plan name and additional bundle count on current widgets
     */

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void currentPlanWidgetDisplay() {
        try {
            final CurrentPlanWidget currentPlanWidget = pages.getCurrentPlanWidget();

            /*final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            PostpaidPack postpaidPack = api.postpaidPackTest(customerNumber);
            // additional bundle should be displayed as per response of the ESB API my-packs
            int size = postpaidPack.getPayload().getPacks().size();
            String bundleCount = Integer.toString(size);
            commonLib.info("bundleCount" + bundleCount);*/
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isPlanNameDisplayedOnCurrentPlanWidget(), true, "Name of the plan is displayed on Current plan widget", "Name of the plan is not displayed on Current plan widget"));
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentPlanWidget().isAdditionalBundleOnCurrentPlanWidget(), true, "Additional bundle count visible", "Additional bundle count not visible"));

            // To be check again
            //assertCheck.append(actions.assertEqual_stringType(pages.getCurrentPlanWidget().isAdditionalBundleCountOnCurrentPlanWidget(), bundleCount, "Additional bundle count is correct", "Additional bundle count is incorrect"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - currentPlanWidgetDisplay" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate widgets in profile management
     * to-do
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void currentPlanProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            String[] widgets;
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();
            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));


            //actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
    }


}