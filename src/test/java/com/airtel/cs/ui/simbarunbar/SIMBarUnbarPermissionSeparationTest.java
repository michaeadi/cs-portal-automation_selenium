package com.airtel.cs.ui.simbarunbar;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SIMBarUnbarPermissionSeparationTest extends Driver {

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
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

    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void viewSIMBarOption() {
        selUtils.addTestcaseDescription("Validate that SIM Bar Option is Available or not under Actions Tab", "description");
        try {
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), true, "Suspend SIM Option is Visible under Actions Tab", "Suspend SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - viewSIMBarOption " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 3, dependsOnMethods = "viewSIMBarOption", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeSuspendPermissionFromUM(TestDatabean data) throws InterruptedException {
        selUtils.addTestcaseDescription("Validate that if the logged in user do NOT have permission to do Suspend Action", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            pages.getLoginPage().doLoginInCSPortal();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), false, "Suspend SIM Option is NOT Visible under Actions Tab, As permission are removed", "Suspend SIM Option is Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - removeSuspendPermissionFromUM " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 4, dependsOnMethods = "removeSuspendPermissionFromUM", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void giveSuspendPermissionFromUM(TestDatabean data) throws InterruptedException {
        selUtils.addTestcaseDescription("Validate that if the logged in user do NOT have permission to do Suspend Action", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            pages.getLoginPage().doLoginInCSPortal();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), true, "Suspend SIM Option is Visible under Actions Tab, As permission are there", "Suspend SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - giveSuspendPermissionFromUM " + e.getMessage(), true);
        }
    }

    @Test(priority = 5, dependsOnMethods = "giveSuspendPermissionFromUM", groups = {"RegressionTest"})
    public void takeActionSIMSuspend() {
        selUtils.addTestcaseDescription("Validate SIM Suspend Action", "description");
        try {
            pages.getCustomerProfilePage().openSuspendSIMTab();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isAuthTabOpenedDoAction(), true, "Authentication Modal Opened", "Authentication Modal NOT Opened"));
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMModalOpened(), true, "Suspend SIM Modal Opened Successfully", "Suspend SIM Modal Not Opened"));
            pages.getCustomerProfilePage().doSIMBarAction();
            final String modalText = pages.getCustomerProfilePage().getModalText();
            assertCheck.append(actions.assertEqual_stringType(modalText, "Sim suspend is successful", "Success Message Matched", "Success Message NOT Matched and is -" + modalText));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - takeActionSIMSuspend " + e.getMessage(), true);
        }
    }

    @Test(priority = 6, dependsOnMethods = "takeActionSIMSuspend", groups = {"RegressionTest"})
    public void testReactivationSIMOptionIsVisible() {
        selUtils.addTestcaseDescription("Validate SIM Reactivation Option is visible or not under action tab", "description");
        try {
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), true, "Reactivation SIM Option is Visible under Actions Tab", "Reactivation SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testReactivationSIMOptionIsVisible " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 7, dependsOnMethods = "testReactivationSIMOptionIsVisible", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeReactivationPermissionFromUM(TestDatabean data) throws InterruptedException {
        selUtils.addTestcaseDescription("Validate that if the logged in user do NOT have permission to do Reactivation Action", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_UNBAR_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            pages.getLoginPage().doLoginInCSPortal();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), false, "Reactivation SIM Option is NOT Visible under Actions Tab, As permission are removed", "Reactivation SIM Option is Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - removeReactivationPermissionFromUM " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 8, dependsOnMethods = "testReactivationSIMOptionIsVisible", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeBothPermissionFromUM(TestDatabean data) throws InterruptedException {
        selUtils.addTestcaseDescription("Validate that if the logged in user do NOT have permission to do Reactivation Action", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            pages.getLoginPage().doLoginInCSPortal();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), false, "Reactivation SIM Option is NOT Visible under Actions Tab, As permission are removed", "Reactivation SIM Option is Visible under Actions Tab"));
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), false, "Suspend SIM Option is Visible under Actions Tab, As permission are there", "Suspend SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - removeBothPermissionFromUM " + e.getMessage(), true);
        }
    }

    @Test(priority = 9, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void suspendSIMPopupFalse() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate SIM Suspend Action", "description");
        try {
            pages.getCustomerProfilePage().clickOnAction();
            if(pages.getCustomerProfilePage().isSuspendSIMOptionVisible()){
                pages.getCustomerProfilePage().openSuspendSIMTab();
                Boolean popup = !pages.getCustomerProfilePage().isSuspendSIMConfirmMessageVisible();
                if (!popup) {
                    pages.getAuthTabPage().clickYesBtn();
                    final String toastText = pages.getAuthTabPage().getToastText();
                    assertCheck.append(actions.assertEqual_stringType(toastText, "Sim suspend is successful", "Sim suspend is successful", "Sim suspend is unsuccessful :-" + toastText));
                    pages.getCustomerProfilePage().clickCancelBtn();
                    actions.assertAllFoundFailedAssert(assertCheck);
                } else {
                    pages.getCustomerProfilePage().clickCancelBtn();
                }
            }
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - suspendSIMPopupFalse " + e.getMessage(), true);
        }
    }

    @Test(priority = 10, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void reActivateSIMPopupFalse() {
        selUtils.addTestcaseDescription("Validate SIM Suspend Action", "description");
        try {
            pages.getCustomerProfilePage().clickOnAction();
            if(pages.getCustomerProfilePage().isReactivationSIMOptionVisible()){
                pages.getCustomerProfilePage().openSuspendSIMTab();
                Boolean popup = !pages.getCustomerProfilePage().isReactivateSIMConfirmMessageVisible();
                if (!popup) {
                    pages.getAuthTabPage().clickYesBtn();
                    final String toastText = pages.getAuthTabPage().getToastText();
                    assertCheck.append(actions.assertEqual_stringType(toastText, "Sim reactivate is successful", "Sim reactivate is successful", "Sim reactivate is unsuccessful :-" + toastText));
                    pages.getCustomerProfilePage().clickCancelBtn();
                    actions.assertAllFoundFailedAssert(assertCheck);
                } else {
                    pages.getCustomerProfilePage().clickCancelBtn();
                }
            }
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - reActivateSIMPopupFalse " + e.getMessage(), true);
        }
    }
}