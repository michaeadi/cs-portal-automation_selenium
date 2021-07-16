package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PlanAndPackDetailedWidgetTest extends Driver {

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
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check whether user has permission for Plan and Pack Widget
     * isUserHasPlanAndPackWidgetPermission() - todo
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasPlanAndPackWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that plan and pack widget should be visible to the logged in agent if plan and pack permission is enabled in UM, Check User has permission to view plan and pack Widget Permission", "description");
            String planAndPackPermission = constants.getValue(PermissionConstants.CURRENT_PLAN_WIDGET_PERMISSION);
            pages.getPlanAndPackDetailedWidget().openCurrentPlanDetailPage();
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), planAndPackPermission), "Plan Widget displayed correctly as per user permission", "Plan Widget does not display correctly as per user permission"));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), planAndPackPermission), "Pack Widget displayed correctly as per user permission", "Pack Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPlanAndPackWidgetPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate watermarked in the left corner and middle
     * isAccountInformationWidgetDisplay() -- todo
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void planAndPackWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate is Plan and Pack Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay(), true, " Plan widget displayed", "Plan widget not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay(), true, "Pack widget displayed", "Pack widget not displayed"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPackFooterAuuidUHW(), loginAUUID, "Auuid shown at the footer of Your pack widget and is correct", "Auuid NOT shown at the footer of Your pack widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPackMiddleAuuidUHW(), loginAUUID, "Auuid shown at the middle of Your pack widget and is correct", "Auuid NOT shown at the middle of Your pack widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPlanFooterAuuidUHW(), loginAUUID, "Auuid shown at the middle of Your plan widget and is correct", "Auuid NOT shown at the middle of Your plan widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPlanMiddleAuuidUHW(), loginAUUID, "Auuid shown at the middle of Your plan widget and is correct", "Auuid NOT shown at the middle of Your plan widget"));
            // To be check again
            //assertCheck.append(actions.assertEqual_stringType(pages.getPlanAndPackDetailedWidget().getActivePackDetailsPlan(), "Plan Details", "Plan detail is visible under active pack", "Plan detail is noy visible under active pack"));
            //assertCheck.append(actions.assertEqual_stringType(pages.getPlanAndPackDetailedWidget().getActivePackAddOnBundle(), "Add-On Bundle Usage", "Add-On Bundle Usage is visible under active pack", "Add-On Bundle Usage is not visible under active pack"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - planAndPackWatermarkTest" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to show plan and pack widget on the basis of connection type and UM permission
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that plan and pack widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umPlanPermission = pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay();
            final boolean umPackPermission = pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umPlanPermission && umPackPermission) {
                assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay(), true, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
                assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay(), true, "User has permission for current plan widget", "User doesn't have permission for current plan widget"));
            } else {
                commonLib.fail(" Plan and Pack widget is not displayed", true);
            }

            // To do
            // assertCheck.append(actions.assertEqual_boolean(pages.getPlanAndPackDetailedWidget().isActionIconVisibleOnCurrentPlan(), true, "Action icon visible", "Action icon not visible"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate add on bundles column name
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void addOnBundleColumnNames() {
        try {
            selUtils.addTestcaseDescription("Validate add on bundles widget column name", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getProductName(), "Product Name", "Column name displayed and is correct", "Column name displayed and is not correct"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getBenefit(), "Benefit", "Column name displayed and is correct", "Column name displayed and is not correct"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getAvailable(), "Available", "Column name displayed and is correct", "Column name displayed and is not correct"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getUsed(), "Used", "Column name displayed and is correct", "Column name displayed and is not correct"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getCategory(), "Category", "Column name displayed and is correct", "Column name displayed and is not correct"));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - addOnBundleColumnNames" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void currentPlanProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();
            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isProfileManagementDisplay(), true, "Active packs displayed", "Active packs not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getActivePacks(), "ACTIVE PACKS", "Active pack tab visible", "Active pack tab not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isCheckBoxChecked(), true, "Checkbox under active pack is visible", "Checkbox under active pack is not visible"));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate MSISDN
     */

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void invalidMSISDNTest() {
        try {
            selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
            pages.getDemoGraphicPage().enterMSISDN("123456789");
            assertCheck.append(actions
                    .assertEqualStringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Entered customer number is Invalid",
                            "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
        }
    }

}