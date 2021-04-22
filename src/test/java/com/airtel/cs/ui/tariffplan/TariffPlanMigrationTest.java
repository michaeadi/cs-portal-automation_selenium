package com.airtel.cs.ui.tariffplan;

import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.pojo.MainAccountBalance;
import com.airtel.cs.pojo.tariffplan.AvailablePlanPOJO;
import com.airtel.cs.pojo.tariffplan.CurrentPlanPOJO;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TariffPlanMigrationTest extends PreRequisites {
    String currentPlanFromUI;
    MainAccountBalance mainAccountBalanceBeforeMigration;
    String customerNewPlan;
    public static final String TARIFF_PLAN_ISSUE_CODE = constants.getValue(ApplicationConstants.TARIFF_PLAN_ISSUE_CODE);
    Integer interactionCountBeforeMigration;
    Integer interactionCountAfterMigration;
    Integer rowCountBeforeMigration;
    Integer rowCountAfterMigration;

    @BeforeClass
    public void checkTariffPlanFlag() {
        if (!StringUtils.equals(RUN_TARIFF_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Tariff Test Case Flag Value is - " + RUN_TARIFF_TEST_CASE);
            throw new SkipException("Skipping because this is for NG Only");
        }
    }

    @DataProviders.User(userType = "Tariff")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions :" + data.getCustomerNumber(), "description");
        try {
            pages.getSideMenuPOM().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
            pages.getMsisdnSearchPage().clickOnSearch();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isPageLoaded(), true, "Customer Page Loaded Sucessfully", "Customer Page NOT Loaded"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - openCustomerInteraction " + e.getMessage(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testChangeServiceClassVisibleAndClickable() {
        selUtils.addTestcaseDescription("Validating Change Service Class option is visible or not under Actions tab", "description");
        try {
            mainAccountBalanceBeforeMigration = pages.getCustomerProfilePage().getMainAccountBalance();
            pages.getCustomerProfilePage().goAndCheckFTRCreatedorNot();
            interactionCountBeforeMigration = pages.getViewHistoryPOM().getRowCount();
            pages.getViewHistoryPOM().goToActionTrailTab();
            rowCountBeforeMigration = pages.getViewHistoryPOM().getRowCount();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isChangeServiceClassOptionVisible(), true, "Change Service Class (Tariff Plan) Option is Visible under Actions Tab", "Change Service Class (Tariff Plan) Option is NOT Visible under Actions Tab"));
            pages.getCustomerProfilePage().openChangeServiceClassTab();
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isChangeServiceClassTabOpened(), true, "Service Class (Tariff Plan) Tab Opened Successfully", "Service Class (Tariff Plan) Tab Not Opened"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testChangeServiceClassVisibleAndClickable " + e.getMessage(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testChangeServiceClassVisibleAndClickable")
    public void testValidateCustomerCurrentPlanDetails() {
        selUtils.addTestcaseDescription("Validate Customer Current Plan Details under Service Class Tab", "description");
        try {
            currentPlanFromUI = pages.getTariffPlanPage().getCurrentPlan();
            final AvailablePlanPOJO availablePlanPOJO = api.availablePlanPOJO();
            final CurrentPlanPOJO currentPlanPOJO = api.currentPlanPOJO();
            final String currentPlanNameFromAPI = (String) currentPlanPOJO.getResult().get("planName");
            assertCheck.append(actions.assertEqual_stringType(currentPlanFromUI, currentPlanNameFromAPI, "Current Plan Value Matched with UI values", "API Current Plan Values not Matched with UI values and are UI Current Plan is -" + currentPlanFromUI + "and API Current Plan is -" + currentPlanNameFromAPI));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isCheckBoxChecked(), true, "Checkbox is Checked By default", "Checkbox is not Checked by Default"));
            //assertCheck.append(actions.assertEqual_stringType(currentPlanFromUI, pages.getTariffPlanPage().getCurrentPlanDetailsHeader(), "Current Plan Name Matched with Header name of the Plan Details", "Current Plan Name NOT Matched with Header name of the Plan Details"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getTariffPlanPage().getPlanDescription(), "Current Plan Details are Visible", "Current Plan Details are NOT Visible"));
            assertCheck.append(actions.assertEqual_stringType(pages.getTariffPlanPage().getDropDownName(), "Select Tariff Plan", "Drop Down Name Matched", "Drop Down Name Not Matched"));
            assertCheck.append(actions.assertEqual_intNotNull(pages.getTariffPlanPage().getDropDownListSize(), "List Fetched Successfully and Not Null", "List Not Fetched and is null"));
            final Boolean planNotInDropDownList = pages.getTariffPlanPage().checkCurrentPlanNotInDropDownList(currentPlanFromUI);
            assertCheck.append(actions.assertEqual_boolean(planNotInDropDownList, false, "Current Plan Not in Drop Down List"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testValidateCustomerCurrentPlanDetails " + e.getMessage(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testValidateCustomerCurrentPlanDetails")
    public void testSelectPlanOtherThanCurrentPlan() {
        selUtils.addTestcaseDescription("Validate new Plan Details from Drop Down List", "description");
        try {
            customerNewPlan = pages.getTariffPlanPage().selectPlanOtherThanCurrentPlan(currentPlanFromUI);
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isCheckBoxChecked(), false, "Checkbox is Unchecked After Selecting the Plan other than Current Plan", "Checkbox does not Unchecked After Plan Change"));
            //assertCheck.append(actions.assertEqual_stringType(newPlan, pages.getTariffPlanPage().getCurrentPlanDetailsHeader(), "New Plan Name Matched with Header name of the Plan Details", "New Plan Name NOT Matched with Header name of the Plan Details"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getTariffPlanPage().getPlanDescription(), "New Plan Details are Visible", "New Plan Details are NOT Visible"));
            assertCheck.append(actions.assertEqual_stringType(pages.getTariffPlanPage().getFootNote(), "Note: Changing tariff plan will also change the service class of customer", "Foot Note Message Matched", "Foot Note Message Not Matched"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isMigrateButtonPresent(), true, "Migrate Button Present on the UI"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isMigrateBtnEnabled(), true, "Migrate Button is Enabled"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSelectPlanOtherThanCurrentPlan " + e.getMessage(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testSelectPlanOtherThanCurrentPlan")
    public void testIssueDetailsPopUp() {
        selUtils.addTestcaseDescription("Validate new Plan Details from Drop Down List", "description");
        try {
            pages.getTariffPlanPage().openIssueDetailsModal();
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isIssueDetailModalOpened(), true, "Issue Modal Pop Up Opened After Click on Migrate Btn", "Issue Modal Popup NOT Opened After CLick on Migrate Btn"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isCommentBoxVisible(), true, "Comment Box is visible", "Comment Box is NOT Visible"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isSelectReasonVisible(), true, "Issue Details Reason is Visible", "Issue Detail Reason is NOT Visisble"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isCancelBtnVisisble(), true, "Cancel Btn Visible Over Issue Detail Popup", "Cancel Btn NOT Visible Over Issue Detail Popup"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isSubmitBtnVisible(), true, "Submit Btn Visible Over Issue Detail Popup", "Submit Btn NOT Visible Over Issue Detail PopUp"));
            pages.getTariffPlanPage().clickCancelBtn();
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isIssueDetailModalOpened(), false, "Modal got Closed Successfully", "Modal NOT Closed"));
            pages.getTariffPlanPage().enterDetailsIssuePopup();
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isSubmitEnabled(), true, "Submit Btn Enabled"));
            assertCheck.append(actions.assertEqual_stringType(pages.getTariffPlanPage().getNoteTextIssueDetailsPopUp(), "Changing your service plan will cost 100.0 NGN. Please inform to customer before proceeding.", "Note Message Matched", "Note Message NOT Matched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testIssueDetailsPopUp " + e.getMessage(), true);
        }
    }

    @Test(priority = 6, groups = {"RegressionTest"}, dependsOnMethods = "testIssueDetailsPopUp")
    public void testPlanMigration() {
        selUtils.addTestcaseDescription("Validate new Plan Details from Drop Down List", "description");
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().changePlan(), true, "Plan Changed Successfully", "Plan Not Changed"));
            assertCheck.append(actions.assertEqual_stringType(pages.getTariffPlanPage().getModalText(), "Plan Changed Successfully", "Success Message Matched", "Success Message NOT Found and is -" + pages.getTariffPlanPage().getModalText()));
            final CurrentPlanPOJO currentPlanPOJO = api.currentPlanPOJO();
            final String currentPlanNameFromAPI = (String) currentPlanPOJO.getResult().get("planName");
            assertCheck.append(actions.assertEqual_stringType(customerNewPlan, currentPlanNameFromAPI, "Plan Changed Successfully", "Plan Name Mismatched"));
            assertCheck.append(actions.assertEqual_stringType(pages.getCustomerProfilePage().goAndCheckFTRCreatedorNot(), TARIFF_PLAN_ISSUE_CODE, "FTR Ticket Created", "FTR Ticket NOT Created"));
            interactionCountAfterMigration = pages.getViewHistoryPOM().getRowCount();
            assertCheck.append(actions.assertEqual_intType(interactionCountAfterMigration, interactionCountBeforeMigration + 1, "Interaction Count Increased By 1", "Interaction Count NOT increased"));
            pages.getCustomerProfilePage().goToHomeTab();
            MainAccountBalance mainAccountBalanceAfterMigration = pages.getCustomerProfilePage().getMainAccountBalance();
            assertCheck.append(actions.assertEqual_intType(Integer.parseInt(String.valueOf(mainAccountBalanceAfterMigration)), Integer.parseInt(String.valueOf(mainAccountBalanceBeforeMigration)) - 100, "", ""));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testPlanMigration " + e.getMessage(), true);
        }
    }

    @Test(priority = 7, groups = {"RegressionTest"}, dependsOnMethods = "testPlanMigration")
    public void testActionTrailActivity() {
        selUtils.addTestcaseDescription("Validate that system should capture the Service class migration activity into Action Trail", "description");
        try {
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistoryPOM().goToActionTrailTab();
            rowCountAfterMigration = pages.getViewHistoryPOM().getRowCount();
            assertCheck.append(actions.assertEqual_intType(rowCountAfterMigration, rowCountBeforeMigration + 1, "Activity Row Count Increased By 1", "Activity Row Count NOT increased"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testActionTrailActivity " + e.getMessage(), true);
        }
    }

    @Test(priority = 8, groups = {"RegressionTest"})
    public void testIfMigrationPermissionRemoved() {
        selUtils.addTestcaseDescription("Validate that if the logged in user has the permission to view this feature but does not have permission for Migration, Then user will be able to see the Change Service Plan Option but won't be able to migrate Service Class", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.TARIFF_PLAN_MIGRATE_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            goAndCheckServiceClassOptionVisible();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isChangeServiceClassOptionVisible(), false, "Change Service Class Option Should not be visible, As permissions are removed", "Change Service Class Opton is visible and should not be"));
            pages.getCustomerProfilePage().openChangeServiceClassTab();
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isChangeServiceClassTabOpened(), true, "Service Class (Tariff Plan) Tab Opened Successfully", "Service Class (Tariff Plan) Tab Not Opened"));
            pages.getTariffPlanPage().selectPlanOtherThanCurrentPlan(currentPlanFromUI);
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isMigrateBtnEnabled(), true, "Migrate Button is Enabled"));
            pages.getTariffPlanPage().enterDetailsIssuePopup();
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().isSubmitEnabled(), true, "Submit Btn Enabled"));
            assertCheck.append(actions.assertEqual_boolean(pages.getTariffPlanPage().changePlan(), true, "Plan Changed Successfully", "Plan Not Changed"));
            assertCheck.append(actions.assertEqual_stringType(pages.getTariffPlanPage().getModalText(), "User is not authorized to perform this action.", "Failure Message Matched", "Failure Message NOT Found and is -" + pages.getTariffPlanPage().getModalText()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testIfMigrationPermissionRemoved " + e.getMessage(), true);
        }
    }

    @Test(priority = 9, groups = {"RegressionTest"})
    public void testIfViewPermissionRemoved() {
        selUtils.addTestcaseDescription("Validate that if the logged in user does not have permission for Service Plan Migration user won't be able to view Change Service Class option under the Action button on customer dashboard.", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.TARIFF_PLAN_VIEW_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            goAndCheckServiceClassOptionVisible();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isChangeServiceClassOptionVisible(), false, "Change Service Class Option Should not be visible, As permissions are removed", "Change Service Class Opton is visible and should not be"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testIfViewPermissionRemoved " + e.getMessage(), true);
        }
    }
}
