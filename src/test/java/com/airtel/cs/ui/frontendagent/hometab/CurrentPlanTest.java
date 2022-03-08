package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CurrentPlanTest extends Driver {
    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();

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
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check whether user has permission for Current Plan Widget
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasCurrentPlanWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that current plan widget should be visible to the logged in agent if current plan permission is enabled in UM, Check User has permission to view current plan Widget Permission", "description");
            String currentPlanPermission = constants.getValue(PermissionConstants.CURRENT_PLAN_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), UtilsMethods.isUserHasPermission(currentPlanPermission), "Current plan Widget displayed correctly as per user permission", "Current plan Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPlanAndPackWidgetPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate watermarked in the left corner and middle
     */

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasCurrentPlanWidgetPermission"})
    public void currentPlanWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate is current plan widget Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), true, " Current Plan widget displayed", "Current Plan widget not displayed"));
            assertCheck.append(actions.assertEqualStringType(pages.getCurrentPlanWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the Your Current Plan widget and is correct", "Auuid NOT shown at the footer of Your Current Plan widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getCurrentPlanWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the Your Current Plan widget and is correct", "Auuid NOT shown at the middle of Your Current Plan widget"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanWatermarkTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to show current plan widget on the basis of connection type and UM permission
     */

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasCurrentPlanWidgetPermission"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that current plan widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN));
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umPermission = pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), true, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else if (connectionType.equalsIgnoreCase("POSTPAID") && !umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), false, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else if (connectionType.equalsIgnoreCase("PREPAID") && umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), true, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else if (connectionType.equalsIgnoreCase("PREPAID") && !umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), false, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
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

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasCurrentPlanWidgetPermission"})
    public void currentPlanWidgetDisplay() {
        try {
            selUtils.addTestcaseDescription("Validate current plan widget", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isPlanNameDisplayedOnCurrentPlanWidget(), true, "Name of the plan is displayed on Current plan widget", "Name of the plan is not displayed on Current plan widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isAdditionalBundleOnCurrentPlanWidget(), true, "Additional bundle count visible", "Additional bundle count not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isActionIconVisibleOnCurrentPlan(), true, "Action icon visible", "Action icon not visible"));

            /**
             * Calling CS API for current plan
             */
            final List<String> postpaidCurrentPlan = api.getPostpaidCurrentPlan(constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidCurrentPlan, "additionalBundles", "statusCode"), "200", "Postpaid Current Plan API 1 Status Code Matched", "Postpaid Current Plan API 1 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidCurrentPlan, "planName", "statusCode"), "200", "Postpaid Current Plan API 2 Status Code Matched", "Postpaid Current Plan API 2 Status Code NOT Matched"));
            String count = pages.getCurrentPlanWidget().isAdditionalBundleCountOnCurrentPlanWidget();
            assertCheck.append(actions.assertEqualStringType(count, pages.getCurrentPlanWidget().isAdditionalBundleCountOnCurrentPlanWidget(), "Count is showing as expected", "Count is not showing as expected"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanWidgetDisplay" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasCurrentPlanWidgetPermission"})
    public void currentPlanProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            String[] widgets;
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();
            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));

            int size = pages.getProfileManagement().getWidgetRowsSize();
            widgets = new String[size];
            for (int i = 1; i <= size; i++) {
                widgets[i - 1] = pages.getProfileManagement().getWidgetNameForOrder(i);
                commonLib.info("Widget name : " + pages.getProfileManagement().getWidgetNameForOrder(i));
                if (pages.getProfileManagement().getWidgetNameForOrder(i).trim().equalsIgnoreCase("Postpaid Current Plan")) {
                    assertCheck.append(actions.assertEqualStringType(pages.getProfileManagement().getWidgetNameForOrder(i).trim().toUpperCase(), "POSTPAID CURRENT PLAN", "Current plan visible in profile management", "Current plan is not visible in profile management"));

                    if (pages.getProfileManagement().isCheckboxEnable(i - 1).equalsIgnoreCase("true")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getProfileManagement().isCheckboxEnable(i - 1).toLowerCase().trim(), "true", "Postpaid current plan widget enabled as expected", "Postpaid current plan widget doesn't enabled as expected"));

                    } else {
                        pages.getProfileManagement().clickOnCheckbox(i - 1);
                        pages.getProfileManagement().clickingSubmitButton();
                        break;
                    }

                    assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isMovingUpButtonEnable(i), true, "Moving up button is enable", "Moving up button is not enable"));
                    pages.getProfileManagement().clickingUpButton(i);
                    assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isMovingDownButtonEnable(i), true, "Moving down button is enable", "Moving down button is not enable"));
                    pages.getProfileManagement().clickingDownButton(i);
                    assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isSubmitButtonEnable(), true, "Submit button enable as expected", "Submit button does not enable as expected"));
                    pages.getProfileManagement().clickingSubmitButton();
                    break;

                }
            }


        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
    }


}