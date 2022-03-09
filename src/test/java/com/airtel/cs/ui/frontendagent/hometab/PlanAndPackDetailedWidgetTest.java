package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.PlanPackRequest;
import com.airtel.cs.model.response.PlanPackResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PlanAndPackDetailedWidgetTest extends Driver {

    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();
    String customerNumber = null;

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
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
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
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasPlanAndPackWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that plan and pack widget should be visible to the logged in agent if plan and pack permission is enabled in UM, Check User has permission to view plan and pack Widget Permission", "description");
            String planAndPackPermission = constants.getValue(PermissionConstants.CURRENT_PLAN_WIDGET_PERMISSION);
            pages.getPlanAndPackDetailedWidget().openCurrentPlanDetailPage();
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay(), UtilsMethods.isUserHasPermission(planAndPackPermission), "Plan Widget displayed correctly as per user permission", "Plan Widget does not display correctly as per user permission"));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay(), UtilsMethods.isUserHasPermission(planAndPackPermission), "Pack Widget displayed correctly as per user permission", "Pack Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPlanAndPackWidgetPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate watermarked in the left corner and middle
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasPlanAndPackWidgetPermission"})
    public void planAndPackWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate is Plan and Pack Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay(), true, " Plan widget displayed", "Plan widget not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay(), true, "Pack widget displayed", "Pack widget not displayed"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPackFooterAuuidUHW(), loginAUUID, "Auuid shown at the footer of Your pack widget and is correct", "Auuid NOT shown at the footer of Your pack widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPackMiddleAuuidUHW(), loginAUUID, "Auuid shown at the middle of Your pack widget and is correct", "Auuid NOT shown at the middle of Your pack widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPlanFooterAuuidUHW(), loginAUUID, "Auuid shown at the middle of Your plan widget and is correct", "Auuid NOT shown at the middle of Your plan widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPlanMiddleAuuidUHW(), loginAUUID, "Auuid shown at the middle of Your plan widget and is correct", "Auuid NOT shown at the middle of Your plan widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getActivePackDetailsPlan(), "PLAN DETAILS", "Plan detail is visible under active pack", "Plan detail is noy visible under active pack"));
            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getActivePackAddOnBundle(), "ADD-ON BUNDLE USAGE", "Add-On Bundle Usage is visible under active pack", "Add-On Bundle Usage is not visible under active pack"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - planAndPackWatermarkTest" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to show plan and pack widget on the basis of connection type and UM permission
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasPlanAndPackWidgetPermission"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that plan and pack widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
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

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate add on bundles column name
     */
    @DataProviders.Table(name = "ADD ON BUNDLE USAGE")
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"isUserHasPlanAndPackWidgetPermission"})
    public void addOnBundleColumnNames(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validate add on bundles widget column name", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            PlanPackRequest planPackRequest = new PlanPackRequest();
            planPackRequest.setMsisdn(customerNumber);
            planPackRequest.setPageNumber(constants.getValue(CommonConstants.PAGE_NO));
            planPackRequest.setPageSize(constants.getValue(CommonConstants.PAGE_SIZE));

            assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getActivePacks(), "ACTIVE PACKS", "Active pack tab visible", "Active pack tab not visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getPackHeaders(1), data.getHeaderName().get(0), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getPackHeaders(2), data.getHeaderName().get(1), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getPackHeaders(3), data.getHeaderName().get(2), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getPackHeaders(4), data.getHeaderName().get(3), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getPackHeaders(5), data.getHeaderName().get(4), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));

            /**
             * Calling plan details api CS-PORTAL
             */
            PlanPackResponse planPackResponse = api.getPlanPack(planPackRequest, customerNumber);
            final String status = planPackResponse.getStatusCode().trim();
            assertCheck.append(actions.assertEqualStringType(status, "200", "Plan and pack details api called successfully", "Plan and pack details api not called successfully"));
            if (status.equalsIgnoreCase("200")) {
                int size = pages.getPlanAndPackDetailedWidget().getNumbersOfRows();
                if (size > 5) {
                    size = 5;
                }
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getProdName(i + 1), planPackResponse.getResult().getPackUsageDTO().get(i).getBundleName(), "Product Name display as received in API on row" + i, "Product Name not display as received in API on row" + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getCategory(i + 1), planPackResponse.getResult().getPackUsageDTO().get(i).getCategory(), "Category value display as received in API on row" + i, "Category not display as received in API on row" + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getBenefit(i + 1), planPackResponse.getResult().getPackUsageDTO().get(i).getBenefit(), "Benefit value display as received in API on row" + i, "Benefit not display as received in API on row" + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getUsed(i + 1), planPackResponse.getResult().getPackUsageDTO().get(i).getUsed(), "Used value display as received in API on row" + i, "Used not display as received in API on row" + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getPlanAndPackDetailedWidget().getAvailable(i + 1), planPackResponse.getResult().getPackUsageDTO().get(i).getAvailable(), "Available value display as received in API on row" + i, "Available value not display as received in API on row" + i));
                }
            }else {
                commonLib.fail("API does not able to fetch Plan Details", false);
            }

            if (planPackResponse.getStatusCode().trim().equalsIgnoreCase("500")) {
                assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPlanDetailUnableToFetch().trim(), "Unable to fetch data", "Unable to fetch data displayed correctly for plan details", "Unable to fetch data is not displayed correctly for plan details"));
                assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getPackDetailUnableToFetch().trim(), "Unable to fetch data", "Unable to fetch data displayed correctly for pack details", "Unable to fetch data is not displayed correctly for pack details"));
            }

            int size = pages.getPlanAndPackDetailedWidget().getWidgetRowsSize();
            if (size > 0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isCheckboxDisplay(), true, "Checkbox visible in active packs", "Checkbox is not visible in active packs"));
            }

            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - addOnBundleColumnNames" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasPlanAndPackWidgetPermission"})
    public void planPackProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();

            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
            int size = pages.getPlanAndPackDetailedWidget().getActivePackOnPM();
            if (size > 0)
                for (int i = 1; i <= size; i++) {
                    if (pages.getPlanAndPackDetailedWidget().getHeaders(i).equalsIgnoreCase("ACTIVE PACKS")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getPlanAndPackDetailedWidget().getHeaders(i), "ACTIVE PACKS", "Active Pack is visible in profile management", "Active Pack is not visible in profile management"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isActivePackTabButtonEnable(), true, "Active Pack tab button is enable", "Active Pack tab is not enable"));
                        pages.getProfileManagement().openActivePackTab();
                        int chkBoxSize = pages.getProfileManagement().getCheckboxActivePack();
                        if (chkBoxSize > 0) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isPlanDetailChkBoxVisible(), true, "Checkbox visible for plan details", "Checkbox not visible for plan details"));
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isPackDetailChkBoxVisible(), true, "Checkbox visible for pack details", "Checkbox not visible for pack details"));
                        }
                        pages.getProfileManagement().clickingSubmitButton();
                        break;

                    }
                }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
    }


}