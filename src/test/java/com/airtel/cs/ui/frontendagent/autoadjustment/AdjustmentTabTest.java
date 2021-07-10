package com.airtel.cs.ui.frontendagent.autoadjustment;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.adjustmentreason.AdjustmentReasonPOJO;
import com.airtel.cs.pojo.response.adjustmentreason.ReasonDetail;
import io.restassured.http.Headers;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AdjustmentTabTest extends Driver {

    public static final String RUN_ADJUSTMENT_TEST_CASE = constants.getValue(ApplicationConstants.RUN_ADJUSTMENT_TEST_CASE);
    private static String customerNumber;
    private final BaseActions actions = new BaseActions();
    private String adjustmentId;
    RequestSource api = new RequestSource();

    @BeforeMethod
    public void checkAdjustmentFlag() {
        if (!StringUtils.equals(RUN_ADJUSTMENT_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Friend & Family widget Test Case Flag Value is - " + RUN_ADJUSTMENT_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @BeforeMethod
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
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = {"openCustomerInteraction"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasPermissionOfAdjustment() {
        try {
            selUtils.addTestcaseDescription("Verify that Adjustment Action should be visible to the logged in agent if adjustment permission is enabled in UM, Check User has permission to view Adjustment sub tab Permission", "description");
            pages.getCustomerProfilePage().clickOnAction();
            String adjustment_permission = constants.getValue(PermissionConstants.ADJUSTMENT_WIDGET_PERMISSION_NAME);
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().checkAdjustmentAction(), UtilsMethods.isUserHasPermission(new Headers(map), adjustment_permission), "Adjustment Action displayed correctly as per user permission", "Adjustment Action does not display correctly as per user permission"));
            pages.getCustomerProfilePage().clickOnAction();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermissionOfAdjustment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 3, dependsOnMethods = {"openCustomerInteraction","isUserHasPermissionOfAdjustment"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void validateReasonTypeFromActionMenu() {
        try {
            selUtils.addTestcaseDescription("Validate both adjustment reason type populated in dropdown after opening adjustment dropdown from action menu,validate recharge action displayed as per api response,validate usage action displayed as per api response,Validate on UI also displayed all the reasons as per api response", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickAdjustment();
            adjustmentId = pages.getAdjustmentTabPage().getWidgetId();
            assertCheck.append(actions.assertEqual_boolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected"));
            AdjustmentReasonPOJO reasonAPI = api.getAdjustmentReason();
            assertCheck.append(actions.matchUiAndAPIResponse(reasonAPI.getResult().get(0).getAction(), constants.getValue(CommonConstants.ADJUSTMENT_REASON_RECHARGE), "Recharge Reason found as adjustment reason as expected per api response", "Recharge Reason does not found as adjustment reason as expected per api response"));
            assertCheck.append(actions.matchUiAndAPIResponse(reasonAPI.getResult().get(1).getAction(), constants.getValue(CommonConstants.ADJUSTMENT_REASON_USAGE), "Usage Reason found as adjustment reason as expected per api response", "Usage Reason does not found as adjustment reason as expected per api response"));
            List<String> reasons = pages.getAdjustmentTabPage().getAllAdjustmentReason();
            if (!reasons.isEmpty()) {
                for (ReasonDetail detail : reasonAPI.getResult()) {
                    if (reasons.remove(detail.getReason())) {
                        commonLib.pass(detail + " reason displayed on UI as per api response");
                    } else {
                        commonLib.fail(detail + " reason does not displayed on UI as per api response", false);
                    }
                }
            } else {
                commonLib.fail("No Adjustment reason displayed on UI", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateReasonTypeFromActionMenu" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = {"openCustomerInteraction","validateReasonTypeFromActionMenu"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void ValidateAdjustmentWidgetOnLoad() {
        try {
            selUtils.addTestcaseDescription("Validate the adjustment widget on load of the page,Validate all the input fields displayed as expected,validate submit button is not enable without choosing mandatory fields.", "description");
            assertCheck.append(actions.assertEqual_boolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected"));
            assertCheck.append(actions.assertEqual_boolean(pages.getAdjustmentTabPage().isAdjustmentReasonVisible(), true, "Adjustment reason type fields display as expected", "Adjustment reason type field does not display as per expected."));
            assertCheck.append(actions.assertEqual_boolean(pages.getAdjustmentTabPage().isAdjustmentTypeVisible(), true, "Adjustment Type type fields display as expected", "Adjustment type field does not display as per expected."));
            assertCheck.append(actions.assertEqual_boolean(pages.getAdjustmentTabPage().isAdjustmentAccountTypeVisible(), true, "Adjustment account type fields display as expected", "Adjustment account type field does not display as per expected."));
            assertCheck.append(actions.assertEqual_boolean(pages.getAdjustmentTabPage().isCommentBoxVisible(), true, "Comment box display as expected", "Comment box does not display as per expected."));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentTabPage().getServiceNumber(), customerNumber, "Service number same as customer number as expected adjustment tab open for same msisdn", "Service number not same as customer number as expected adjustment tab  does not open for same msisdn"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAdjustmentTabPage().getAdjustmentCurrency(), constants.getValue(ApplicationConstants.ADJUSTMENT_CURRENCY_UNIT), "Adjustment currency same as expected", "Adjustment currency same as expected"));
            assertCheck.append(actions.assertEqual_boolean(pages.getAdjustmentTabPage().isSubmitEnable(),false,"Submit button disabled as expected","Submit button enable but it must be disable as mandatory does not filled as expected"));
        }catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateAdjustmentWidgetOnLoad" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}
