package com.airtel.cs.ui.frontendagent.autoadjustment;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.model.cs.response.adjustmenthistory.AdjustmentResult;
import com.airtel.cs.model.cs.response.adjustmentreason.AdjustmentReasonRequest;
import com.airtel.cs.model.cs.response.rechargehistory.RechargeHistory;
import com.airtel.cs.model.cs.response.usagehistory.UsageHistory;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.airtel.cs.commonutils.utils.UtilsMethods.stringNotNull;

public class AdjustmentTabTest extends Driver {

    public static final String RUN_ADJUSTMENT_TEST_CASE = constants.getValue(ApplicationConstants.RUN_ADJUSTMENT_TEST_CASE);
    private static String customerNumber;
    private static boolean hasPermission = false;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private String adjustmentId;
    private AdjustmentReasonRequest reasonAPI;
    private UsageHistory usageHistoryAPI;
    private RechargeHistory rechargeHistoryAPI;
    private static String adjustmentReason;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkAdjustmentFlag() {
        if (!StringUtils.equals(RUN_ADJUSTMENT_TEST_CASE, "true")) {
            commonLib.skip("Adjustment widget is NOT Enabled for this Opco " + OPCO);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
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
            customerNumber = constants.getValue(ApplicationConstants.ADJUSTMENT_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
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
            hasPermission = UtilsMethods.isUserHasPermission(adjustment_permission);
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().checkAdjustmentAction(), hasPermission, "Adjustment Action displayed correctly as per user permission", "Adjustment Action does not display correctly as per user permission"));
            pages.getCustomerProfilePage().clickOutside();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermissionOfAdjustment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 3, dependsOnMethods = {"openCustomerInteraction", "isUserHasPermissionOfAdjustment"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void validateReasonTypeFromActionMenu() {
        try {
            selUtils.addTestcaseDescription("Validate both adjustment reason type populated in dropdown after opening adjustment dropdown from action menu,validate recharge action displayed as per api response,validate usage action displayed as per api response,Validate on UI also displayed all the reasons as per api response", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickAdjustment();
            adjustmentId = pages.getAdjustmentTabPage().getWidgetId();
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected"));
            reasonAPI = api.getAdjustmentReason();
            assertCheck.append(actions.matchUiAndAPIResponse(reasonAPI.getResult().get(0).getAction(), constants.getValue(CommonConstants.ADJUSTMENT_REASON_RECHARGE), "Recharge Reason found as adjustment reason as expected per api response", "Recharge Reason does not found as adjustment reason as expected per api response"));
            assertCheck.append(actions.matchUiAndAPIResponse(reasonAPI.getResult().get(1).getAction(), constants.getValue(CommonConstants.ADJUSTMENT_REASON_USAGE), "Usage Reason found as adjustment reason as expected per api response", "Usage Reason does not found as adjustment reason as expected per api response"));
            List<String> reasons = pages.getAdjustmentTabPage().getAllAdjustmentReason();
            pages.getAdjustmentTabPage().validateReasonsDetail(reasons, reasonAPI);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateReasonTypeFromActionMenu" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = {"openCustomerInteraction", "validateReasonTypeFromActionMenu"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void ValidateAdjustmentWidgetOnLoad() {
        try {
            selUtils.addTestcaseDescription("Validate the adjustment widget on load of the page,Validate all the input fields displayed as expected,validate submit button is not enable without choosing mandatory fields.", "description");
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected", true));
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().isAdjustmentReasonVisible(), true, "Adjustment reason type fields display as expected", "Adjustment reason type field does not display as per expected."));
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().isAdjustmentTypeVisible(), true, "Adjustment Type type fields display as expected", "Adjustment type field does not display as per expected."));
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().isAdjustmentAccountTypeVisible(), true, "Adjustment account type fields display as expected", "Adjustment account type field does not display as per expected."));
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().isCommentBoxVisible(), true, "Comment box display as expected", "Comment box does not display as per expected."));
            assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentTabPage().getServiceNumber(), customerNumber, "Service number same as customer number as expected adjustment tab open for same msisdn", "Service number not same as customer number as expected adjustment tab  does not open for same msisdn"));
            assertCheck.append(actions.assertEqualStringType(pages.getAdjustmentTabPage().getAdjustmentCurrency(), constants.getValue(ApplicationConstants.ADJUSTMENT_CURRENCY_UNIT), "Adjustment currency same as expected", "Adjustment currency same as expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().isSubmitEnable(), false, "Submit button disabled as expected", "Submit button enable but it must be disable as mandatory fields does not filled as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateAdjustmentWidgetOnLoad" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, dependsOnMethods = {"openCustomerInteraction"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasPermissionAdjustUsage() {
        try {
            selUtils.addTestcaseDescription("validate that Adjustment Action is displayed for Usage History transactions when necessary permissions are given,Validate User has permission to see action along with transaction.", "description");
            pages.getCustomerProfilePage().goToHomeTab();
            widgetMethods.clickMenuButton(pages.getUsageHistoryWidget().getWidgetIdentifier());
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isWidgetDisplay(), true, "Detailed Usage History Widget visible", "Detailed Usage History Widget does not visible ", true));
            usageHistoryAPI = api.usageHistoryMenuTest(customerNumber);
            final int statusCode = usageHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Usage History Widget API success and status code is :" + statusCode, "Usage History Widget API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                commonLib.fail("API is Unable to Get usage history for Customer", false);
            } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Usage History Details from API");
                assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().getNoResultFound(), true, "No Result Message & Icon is Visible", "No Result Message is not Visible"));
            } else {
                int size = Math.min(usageHistoryAPI.getTotalCount(), 20);
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqualBoolean(widgetMethods.isQuickAction(pages.getDetailedUsageHistoryPage().getWidgetIdentifier(), i, 1), hasPermission, "Usage detailed history widget display adjust action icon as user has permission to permission adjustment", "Usage detailed history widget does not display adjust action icon as expected"));
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermissionAdjustUsage" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, dependsOnMethods = {"openCustomerInteraction", "isUserHasPermissionAdjustUsage"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void ValidateReasonFromUsageWidget() {
        try {
            selUtils.addTestcaseDescription("Validate usage adjustment reason type populated in dropdown after opening adjustment dropdown from usage detailed history widget,validate usage action displayed as per api response,Validate on UI also displayed all the reasons as per api response", "description");
            widgetMethods.clickQuickAction(pages.getDetailedUsageHistoryPage().getWidgetIdentifier(), 0, 1);
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected", true));
            List<String> reasons = pages.getAdjustmentTabPage().getAllAdjustmentReason();
            assertCheck.append(actions.assertEqualBoolean(reasons.remove(reasonAPI.getResult().get(1).getReason()), true, "Usage Reason found as adjustment reason as expected per api response", "Usage Reason does not found as adjustment reason as expected per api response"));
            assertCheck.append(actions.matchUiAndAPIResponse(reasonAPI.getResult().get(1).getAction(), constants.getValue(CommonConstants.ADJUSTMENT_REASON_USAGE), "Usage Reason type found as adjustment reason as expected per api response", "Usage Reason type does not found as adjustment reason as expected per api response"));
            assertCheck.append(actions.assertEqualStringType(usageHistoryAPI.getResult().get(0).getTxnNumber(), pages.getAdjustmentTabPage().getTransactionNumber(), "Transaction Number is same as expected", "Transaction Number is not same as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateAdjustmentWidgetOnLoad" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 6, dependsOnMethods = {"openCustomerInteraction"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasPermissionAdjustRecharge() {
        try {
            selUtils.addTestcaseDescription("validate that Adjustment Action is displayed for Usage History transactions when necessary permissions are given,Validate User has permission to see action along with transaction.", "description");
            pages.getCustomerProfilePage().goToHomeTab();
            widgetMethods.clickMenuButton(pages.getRechargeHistoryWidget().getUniqueIdentifier());
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getMoreRechargeHistoryPage().getUniqueIdentifier()), true, "Detailed Recharge History Widget visible", "Detailed Recharge History Widget does not visible ", true));
            rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
            final int statusCode = rechargeHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Recharge History Widget API success and status code is :" + statusCode, "Recharge History Widget API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                commonLib.fail("API is Unable to Get usage history for Customer", false);
            } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Recharge History Details from API");
                assertCheck.append(actions.assertEqualBoolean(widgetMethods.isNoResultFoundIconDisplay(pages.getMoreRechargeHistoryPage().getUniqueIdentifier()), true, "No Result Message & Icon is Visible", "No Result Message is not Visible"));
            } else {
                int size = Math.min(rechargeHistoryAPI.getTotalCount(), 20);
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqualBoolean(widgetMethods.isQuickAction(pages.getMoreRechargeHistoryPage().getUniqueIdentifier(), i, 1), hasPermission, "Recharge detailed history widget display adjust action icon as user has permission to permission adjustment", "Recharge detailed history widget does not display adjust action icon as expected"));
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermissionAdjustRecharge" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 7, dependsOnMethods = {"openCustomerInteraction", "isUserHasPermissionAdjustRecharge"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void ValidateReasonFromRechargeWidget() {
        try {
            selUtils.addTestcaseDescription("Validate usage adjustment reason type populated in dropdown after opening adjustment dropdown from usage detailed history widget,validate usage action displayed as per api response,Validate on UI also displayed all the reasons as per api response", "description");
            widgetMethods.clickQuickAction(pages.getMoreRechargeHistoryPage().getUniqueIdentifier(), 0, 1);
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected", true));
            List<String> reasons = pages.getAdjustmentTabPage().getAllAdjustmentReason();
            assertCheck.append(actions.assertEqualBoolean(reasons.remove(reasonAPI.getResult().get(0).getReason()), true, "Recharge Reason found as adjustment reason as expected per api response", "Recharge Reason does not found as adjustment reason as expected per api response"));
            assertCheck.append(actions.matchUiAndAPIResponse(reasonAPI.getResult().get(0).getAction(), constants.getValue(CommonConstants.ADJUSTMENT_REASON_RECHARGE), "Recharge Reason type found as adjustment reason as expected per api response", "Recharge Reason type does not found as adjustment reason as expected per api response"));
            assertCheck.append(actions.matchUiAndAPIResponse(stringNotNull(rechargeHistoryAPI.getResult().get(0).getTransactionId()), stringNotNull(pages.getAdjustmentTabPage().getTransactionNumber()), "Transaction Number is same as expected", "Transaction Number is not same as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - ValidateReasonFromRechargeWidget" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 8, dependsOnMethods = {"openCustomerInteraction"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasPermissionCreditTypeAdjustment() {
        try {
            selUtils.addTestcaseDescription("Verify that agent have permission to perform credit type Adjustment Action should be visible to the logged in agent if adjustment permission is enabled in UM, Check User has permission to perform credit type Adjustment.", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickAdjustment();
            adjustmentId = pages.getAdjustmentTabPage().getWidgetId();
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected", true));
            pages.getAdjustmentTabPage().OpenAdjustmentReason();
            adjustmentReason = pages.getAdjustmentTabPage().chooseOption(1);
            pages.getAdjustmentTabPage().OpenAdjustmentType();
            pages.getAdjustmentTabPage().chooseOption("Credit");
            String credit_adjustment_permission = constants.getValue(PermissionConstants.CREDIT_TYPE_ADJUSTMENT_PERMISSION_NAME);
            hasPermission = UtilsMethods.isUserHasPermission(credit_adjustment_permission);
            assertCheck.append(actions.assertEqualBoolean(!pages.getAdjustmentTabPage().isAccessDeniedMsg(), hasPermission, "Credit type adjustment permitted correctly as per user permission", "Credit type adjustment permitted correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermissionCreditTypeAdjustment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 9, dependsOnMethods = {"openCustomerInteraction", "isUserHasPermissionCreditTypeAdjustment"}, groups = {"RegressionTest"})
    public void performAdjustmentCreditType() {
        try {
            selUtils.addTestcaseDescription("Validate user able to open adjustment tab through home action,Validate agent able to choose fields reason and type and enter comment,Validate admin able to put comment,Validate agent able to click submit button and agent must receive proper success message", "description");
            assertCheck.append(actions.assertEqualBoolean(!pages.getAdjustmentTabPage().isAccessDeniedMsg(), true, "Credit type adjustment permitted by the agent as expected", "Credit type adjustment does not permitted as per user permission", true));
            pages.getAdjustmentTabPage().OpenAdjustmentPrepaidAccountType();
            pages.getAdjustmentTabPage().chooseOption("MAIN");
            pages.getAdjustmentTabPage().writeComment("Performing Credit Type Adjustment through automation");
            pages.getAdjustmentTabPage().enterMainAmount("0.00001");
            pages.getAdjustmentTabPage().clickSubmitButton();
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().getPopUpTitle().contains("CREDIT"), true, "Pop up title as per adjustment type", "Pop up title does not as per adjustment type"));
            pages.getAdjustmentTabPage().clickYesButton();
            AdjustmentHistory adjustmentHistoryAPI = api.getAdjustMentHistory(customerNumber);
            final int statusCode = adjustmentHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode, false));
            AdjustmentResult result = adjustmentHistoryAPI.getResult().get(0);
            assertCheck.append(actions.matchUiAndAPIResponse(result.getReason(), adjustmentReason, "Adjustment reason same as selected while performing adjustment", "Adjustment reason as not selected while performing adjustment"));
            assertCheck.append(actions.assertEqualBoolean(result.getAdjustmentType().contains("Credit"), true, "Adjustment type same as selected while performing adjustment", "Adjustment type not same as selected while performing adjustment"));
            assertCheck.append(actions.assertEqualBoolean(result.getAccountType().contains("Main"), true, "Adjustment account type same as selected while performing adjustment", "Adjustment account type not same as selected while performing adjustment"));
            assertCheck.append(actions.matchUiAndAPIResponse(result.getAmount(), "0.00001", "Adjustment Amount same as selected while performing adjustment", "Adjustment Amount as not selected while performing adjustment"));
            assertCheck.append(actions.matchUiAndAPIResponse(result.getComment(), "Performing Credit Type Adjustment through automation", "Adjustment Comment same as selected while performing adjustment", "Adjustment Comment as not selected while performing adjustment"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - performAdjustmentCreditType" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 10, dependsOnMethods = {"openCustomerInteraction"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasPermissionDebitTypeAdjustment() {
        try {
            selUtils.addTestcaseDescription("Verify that agent have permission to perform debit type Adjustment Action should be visible to the logged in agent if adjustment permission is enabled in UM, Check User has permission to perform credit type Adjustment.", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickAdjustment();
            adjustmentId = pages.getAdjustmentTabPage().getWidgetId();
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(adjustmentId), true, "Adjustment widget display as expected", "Adjustment widget does not display as expected", true));
            if (reasonAPI.getResult().size() > 1) {
                pages.getAdjustmentTabPage().OpenAdjustmentReason();
                pages.getAdjustmentTabPage().chooseOption(1);
            }
            pages.getAdjustmentTabPage().OpenAdjustmentType();
            pages.getAdjustmentTabPage().chooseOption("Debit");
            String debit_adjustment_permission = constants.getValue(PermissionConstants.DEBIT_TYPE_ADJUSTMENT_PERMISSION_NAME);
            hasPermission = UtilsMethods.isUserHasPermission(debit_adjustment_permission);
            assertCheck.append(actions.assertEqualBoolean(!pages.getAdjustmentTabPage().isAccessDeniedMsg(), hasPermission, "Debit type adjustment permitted correctly as per user permission", "Debit type adjustment permitted correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermissionDebitTypeAdjustment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 11, dependsOnMethods = {"openCustomerInteraction", "isUserHasPermissionDebitTypeAdjustment"}, groups = {"RegressionTest"})
    public void performAdjustmentDebitType() {
        try {
            assertCheck.append(actions.assertEqualBoolean(!pages.getAdjustmentTabPage().isAccessDeniedMsg(), true, "Debit type adjustment permitted by the agent as expected", "Debit type adjustment does not permitted as per user permission", true));
            selUtils.addTestcaseDescription("Validate user able to open adjustment tab through home action,Validate agent able to choose fields reason and type and enter comment,Validate admin able to put comment,Validate agent able to click submit button and agent must receive proper success message", "description");
            pages.getAdjustmentTabPage().OpenAdjustmentPrepaidAccountType();
            pages.getAdjustmentTabPage().chooseOption("MAIN");
            pages.getAdjustmentTabPage().writeComment("Performing debit Type Adjustment through automation");
            pages.getAdjustmentTabPage().enterMainAmount("0.00001");
            pages.getAdjustmentTabPage().clickSubmitButton();
            assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().getPopUpTitle().contains("DEBIT"), true, "Pop up title as per adjustment type", "Pop up title does not as per adjustment type"));
            pages.getAdjustmentTabPage().clickYesButton();
            AdjustmentHistory adjustmentHistoryAPI = api.getAdjustMentHistory(customerNumber);
            final int statusCode = adjustmentHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode, false));
            AdjustmentResult result = adjustmentHistoryAPI.getResult().get(0);
            assertCheck.append(actions.matchUiAndAPIResponse(result.getReason(), adjustmentReason, "Adjustment reason same as selected while performing adjustment", "Adjustment reason as not selected while performing adjustment"));
            assertCheck.append(actions.assertEqualBoolean(result.getAdjustmentType().contains("Debit"), true, "Adjustment type same as selected while performing adjustment", "Adjustment type not same as selected while performing adjustment"));
            assertCheck.append(actions.assertEqualBoolean(result.getAccountType().contains("Main"), true, "Adjustment account type same as selected while performing adjustment", "Adjustment account type not same as selected while performing adjustment"));
            assertCheck.append(actions.matchUiAndAPIResponse(result.getAmount(), "0.00001", "Adjustment Amount same as selected while performing adjustment", "Adjustment Amount as not selected while performing adjustment"));
            assertCheck.append(actions.matchUiAndAPIResponse(result.getComment(), "Performing debit Type Adjustment through automation", "Adjustment Comment same as selected while performing adjustment", "Adjustment Comment as not selected while performing adjustment"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - performAdjustmentCreditType" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}
