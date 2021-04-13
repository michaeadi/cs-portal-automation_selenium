package com.airtel.cs.ui.frontendagent.simbarunbar;

import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import org.testng.annotations.Test;

public class SIMBarUnbarPermissionSeparationTest extends PreRequisites {

    @DataProviders.User(UserType = "Beta")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction(TestDatabean data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + data.getCustomerNumber());
        pages.getSideMenuPOM().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isPageLoaded(), true, "Customer Page Loaded Sucessfully", "Customer Page NOT Loaded"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void viewSIMBarOption() {
        ExtentTestManager.startTest("Validate that SIM Bar Option is Available or not under Actions Tab", "Validate that SIM Bar Option is Available or not under Actions Tab");
        pages.getCustomerProfilePage().clickOnAction();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), true, "Suspend SIM Option is Visible under Actions Tab", "Suspend SIM Option is NOT Visible under Actions Tab"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @DataProviders.User(UserType = "Beta")
    @Test(priority = 3, dependsOnMethods = "viewSIMBarOption", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeSuspendPermissionFromUM(TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Validate that if the logged in user do NOT have permission to do Suspend Action", "Validate that if the logged in user do NOT have permission to do Suspend Action");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        pages.getLoginPage().openNewTempBrowserAndLoginInUM();
        pages.getUserManagementPage().removeOrAddPermission(commonConstants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
        pages.getUserManagementPage().destroyTempBrowser();
        loginInCSPortal();
        pages.getSideMenuPOM().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        pages.getCustomerProfilePage().clickOnAction();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), false, "Suspend SIM Option is NOT Visible under Actions Tab, As permission are removed", "Suspend SIM Option is Visible under Actions Tab"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @DataProviders.User(UserType = "Beta")
    @Test(priority = 4, dependsOnMethods = "removeSuspendPermissionFromUM", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void giveSuspendPermissionFromUM(TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Validate that if the logged in user do NOT have permission to do Suspend Action", "Validate that if the logged in user do NOT have permission to do Suspend Action");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        pages.getLoginPage().openNewTempBrowserAndLoginInUM();
        pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
        pages.getUserManagementPage().destroyTempBrowser();
        loginInCSPortal();
        pages.getSideMenuPOM().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        pages.getCustomerProfilePage().clickOnAction();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), true, "Suspend SIM Option is Visible under Actions Tab, As permission are there", "Suspend SIM Option is NOT Visible under Actions Tab"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 5, dependsOnMethods = "giveSuspendPermissionFromUM", groups = {"RegressionTest"})
    public void takeActionSIMSuspend() {
        ExtentTestManager.startTest("Validate SIM Suspend Action", "Validate SIM Suspend Action");
        pages.getCustomerProfilePage().openSuspendSIMTab();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isAuthTabOpenedDoAction(), true, "Authentication Modal Opened", "Authentication Modal NOT Opened"));
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMModalOpened(), true, "Suspend SIM Modal Opened Successfully", "Suspend SIM Modal Not Opened"));
        pages.getCustomerProfilePage().doSIMBarAction();
        final String modalText = pages.getCustomerProfilePage().getModalText();
        assertCheck.append(actions.assertEqual_stringType(modalText, "Sim suspend is successful", "Success Message Matched", "Success Message NOT Matched and is -" + modalText));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 6, dependsOnMethods = "takeActionSIMSuspend", groups = {"RegressionTest"})
    public void testReactivationSIMOptionIsVisible() {
        ExtentTestManager.startTest("Validate SIM Reactivation Option is visible or not under action tab", "Validate SIM Reactivation Option is visible or not under action tab");
        pages.getCustomerProfilePage().clickOnAction();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), true, "Reactivation SIM Option is Visible under Actions Tab", "Reactivation SIM Option is NOT Visible under Actions Tab"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @DataProviders.User(UserType = "Beta")
    @Test(priority = 7, dependsOnMethods = "testReactivationSIMOptionIsVisible", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeReactivationPermissionFromUM(TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Validate that if the logged in user do NOT have permission to do Reactivation Action", "Validate that if the logged in user do NOT have permission to do Reactivation Action");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        pages.getLoginPage().openNewTempBrowserAndLoginInUM();
        pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_UNBAR_PERMISSION));
        pages.getUserManagementPage().destroyTempBrowser();
        loginInCSPortal();
        pages.getSideMenuPOM().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        pages.getCustomerProfilePage().clickOnAction();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), false, "Reactivation SIM Option is NOT Visible under Actions Tab, As permission are removed", "Reactivation SIM Option is Visible under Actions Tab"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @DataProviders.User(UserType = "Beta")
    @Test(priority = 8, dependsOnMethods = "testReactivationSIMOptionIsVisible", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeBothPermissionFromUM(TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Validate that if the logged in user do NOT have permission to do Reactivation Action", "Validate that if the logged in user do NOT have permission to do Reactivation Action");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        pages.getLoginPage().openNewTempBrowserAndLoginInUM();
        pages.getUserManagementPage().removeOrAddPermission(commonConstants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
        pages.getUserManagementPage().destroyTempBrowser();
        loginInCSPortal();
        pages.getSideMenuPOM().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        pages.getCustomerProfilePage().clickOnAction();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), false, "Reactivation SIM Option is NOT Visible under Actions Tab, As permission are removed", "Reactivation SIM Option is Visible under Actions Tab"));
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), false, "Suspend SIM Option is Visible under Actions Tab, As permission are there", "Suspend SIM Option is NOT Visible under Actions Tab"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }
}