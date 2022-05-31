package com.airtel.cs.ui.simbarunbar;

import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TestDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.hlrservice.HLRService;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SIMBarUnbarPermissionSeparationTest extends Driver {

    private static boolean SIM_BAR_UNBAR_CONTINUE_EXECUTION = true;
    private String customerNumber;
    private String simStatus;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecutionBarUnbar() {
        if (!SIM_BAR_UNBAR_CONTINUE_EXECUTION) {
            commonLib.skip("Skipping tests because " + SIM_BAR_UNBAR_CONTINUE_EXECUTION + "is set as false");
            throw new SkipException("Skipping tests because " + SIM_BAR_UNBAR_CONTINUE_EXECUTION + "is set as false");
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
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void viewSIMBarUnbarOption() {
        selUtils.addTestcaseDescription("Validate that SIM Bar Option is Available or not under Actions Tab", "description");
        try {
             simStatus = pages.getDemoGraphicPage().getGSMStatus();
            if (StringUtils.equalsIgnoreCase(simStatus, "Suspended")) {
                String simUnBar_permission = constants.getValue(PermissionConstants.SIM_UNBAR_PERMISSION);
                pages.getCustomerProfilePage().clickOnAction();
                assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), UtilsMethods.isUserHasPermission(simUnBar_permission), "Reactivation SIM Option is Visible under Actions Tab", "Reactivation SIM Option is NOT Visible under Actions Tab"));
                pages.getDemoGraphicPage().clickOutside();
            } else if (StringUtils.equalsIgnoreCase(simStatus, "Active")) {
                String simBar_permission = constants.getValue(PermissionConstants.SIM_BAR_PERMISSION);
                pages.getCustomerProfilePage().clickOnAction();
                assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), UtilsMethods.isUserHasPermission(simBar_permission), "Suspend SIM Option is Visible under Actions Tab as per user permission", "Suspend SIM Option is NOT Visible under Actions Tab as per user permission"));
                pages.getDemoGraphicPage().clickOutside();
            } else if (StringUtils.equalsIgnoreCase(simStatus, "Reactivation_required")) {
                commonLib.warning("Please check Manually as GSM Status is :" + simStatus);
                SIM_BAR_UNBAR_CONTINUE_EXECUTION = true;
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - viewSIMBarUnbarOption " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 3, dependsOnMethods = "viewSIMBarUnbarOption", groups = {"RegressionTest"})
    public void removeSuspendPermissionFromUM() {
        selUtils.addTestcaseDescription("Validate that if the logged in user do NOT have permission to do Suspend Action", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            pages.getLoginPage().doLoginInCSPortal();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), false, "Suspend SIM Option is NOT Visible under Actions Tab, As permission are removed", "Suspend SIM Option is Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - removeSuspendPermissionFromUM " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 4, dependsOnMethods = "removeSuspendPermissionFromUM", groups = {"RegressionTest"})
    public void giveSuspendPermissionFromUM() {
        selUtils.addTestcaseDescription("Validate that if the logged in user do NOT have permission to do Suspend Action", "description");
        /* LOGIN IN TEMPORARY BROWSER AS PER TESTCASE REQUIREMENT -
         *  - WITH UM CREDENTIALS */
        try {
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.SIM_BAR_PERMISSION));
            pages.getUserManagementPage().destroyTempBrowser();
            pages.getLoginPage().doLoginInCSPortal();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), true, "Suspend SIM Option is Visible under Actions Tab, As permission are there", "Suspend SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - giveSuspendPermissionFromUM " + e.getMessage(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest"})
    public void takeActionSIMSuspend() {
        selUtils.addTestcaseDescription("Validate SIM Suspend Action", "description");
        try {
            pages.getCustomerProfilePage().openSuspendSimTab();
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isAuthTabOpenedDoAction(), true, "Authentication Modal Opened", "Authentication Modal NOT Opened"));
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSuspendSIMModalOpened(), true, "Suspend SIM Modal Opened Successfully", "Suspend SIM Modal Not Opened"));
            pages.getCustomerProfilePage().performBarUnbarAction();
            final String modalText = pages.getCustomerProfilePage().getModalText();
            assertCheck.append(actions.assertEqualStringType(modalText, "Sim suspend is successful and issue logged", "Success Message Matched", "Success Message NOT Matched and is -" + modalText));
            pages.getAuthTabPage().closeSIMBarPopup();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - takeActionSIMSuspend " + e.getMessage(), true);
        }
    }

    @Test(priority = 6, dependsOnMethods = "takeActionSIMSuspend", groups = {"RegressionTest"})
    public void testReactivationSIMOptionIsVisible() {
        selUtils.addTestcaseDescription("Validate SIM Reactivation Option is visible or not under action tab", "description");
        try {
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), true, "Reactivation SIM Option is Visible under Actions Tab", "Reactivation SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - testReactivationSIMOptionIsVisible " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 7, dependsOnMethods = "testReactivationSIMOptionIsVisible", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeReactivationPermissionFromUM(TestDataBean data) throws InterruptedException {
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
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), false, "Reactivation SIM Option is NOT Visible under Actions Tab, As permission are removed", "Reactivation SIM Option is Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - removeReactivationPermissionFromUM " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 8, dependsOnMethods = "testReactivationSIMOptionIsVisible", dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void removeBothPermissionFromUM(TestDataBean data) {
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
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isReactivationSIMOptionVisible(), false, "Reactivation SIM Option is NOT Visible under Actions Tab, As permission are removed", "Reactivation SIM Option is Visible under Actions Tab"));
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSuspendSIMOptionVisible(), false, "Suspend SIM Option is Visible under Actions Tab, As permission are there", "Suspend SIM Option is NOT Visible under Actions Tab"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - removeBothPermissionFromUM " + e.getMessage(), true);
        }
    }

    @Test(priority = 9, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest"})
    public void suspendSIMPopupFalse() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate SIM Suspend Action", "description");
        try {
            if(StringUtils.equalsIgnoreCase(simStatus, "Active")){
                pages.getCustomerProfilePage().clickOnAction();
            if (pages.getCustomerProfilePage().isSuspendSIMOptionVisible()) {
                pages.getCustomerProfilePage().openSuspendSimTab();
                boolean popup = !pages.getCustomerProfilePage().isSuspendSIMConfirmMessageVisible();
                if (!popup) {
                    pages.getAuthTabPage().clickYesBtn();
                    final String toastText = pages.getAuthTabPage().getToastText();
                    assertCheck.append(actions.assertEqualStringType(toastText, "Sim suspend is successful", "Sim suspend is successful", "Sim suspend is unsuccessful :-" + toastText));
                    pages.getCustomerProfilePage().clickCrossIcon();
                    if(StringUtils.equalsIgnoreCase(toastText,"Sim suspend is successful"))
                    {
                        HLRService hlrService = api.getServiceProfileWidgetInfo(customerNumber);
                        Integer size=hlrService.getTotalCount();
                        for (int i = 0; i < size; i++) {
                        if(StringUtils.equalsIgnoreCase(hlrService.getResult().get(i).getServiceName(),"VOICE OUTGOING"))
                            assertCheck.append(actions.assertEqualStringType(hlrService.getResult().get(i).getServiceStatus(),"ENABLED", "VOICE OUTGOING  is barred as expected ", "VOICE OUTGOING is not barred as expected"));
                        if(StringUtils.equalsIgnoreCase(hlrService.getResult().get(i).getServiceName(),"VOICE INCOMING"))
                                assertCheck.append(actions.assertEqualStringType(hlrService.getResult().get(i).getServiceStatus(),"ENABLED", "VOICE OUTGOING  is barred as expected ", "VOICE OUTGOING is not barred as expected"));
                        if(StringUtils.equalsIgnoreCase(hlrService.getResult().get(i).getServiceName(),"SMS OUTGOING"))
                                assertCheck.append(actions.assertEqualStringType(hlrService.getResult().get(i).getServiceStatus(),"ENABLED", "VOICE OUTGOING  is barred as expected ", "VOICE OUTGOING is not barred as expected"));
                        }
                    }
                } else {
                    pages.getCustomerProfilePage().clickCancelBtn();
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } else
            commonLib.pass("SIM suspend action can't be performed as its status is " + simStatus );
        }
        catch (Exception e) {
            commonLib.fail("Exception in Testcase - suspendSIMPopupFalse " + e.getMessage(), true);
        }
    }

    @Test(priority = 10, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest"})
    public void reActivateSIMPopupFalse() {
        selUtils.addTestcaseDescription("Validate SIM Reactivate Action", "description");
        try {
            if(StringUtils.equalsIgnoreCase(simStatus, "Suspended")){
            pages.getCustomerProfilePage().clickOnAction();
            if (pages.getCustomerProfilePage().isReactivationSIMOptionVisible()) {
                pages.getCustomerProfilePage().openSuspendSimTab();
                boolean popup = !pages.getCustomerProfilePage().isReactivateSIMConfirmMessageVisible();
                if (!popup) {
                    pages.getAuthTabPage().clickYesBtn();
                    final String toastText = pages.getAuthTabPage().getToastText();
                    assertCheck.append(actions.assertEqualStringType(toastText, "Sim reactivate is successful", "Sim reactivate is successful", "Sim reactivate is unsuccessful :-" + toastText));
                    pages.getCustomerProfilePage().clickCrossIcon();
                } else {
                    pages.getCustomerProfilePage().clickCancelBtn();
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } else
                commonLib.pass("SIM Reactivate action can't be performed as its status is " + simStatus );
        } catch (Exception e) {
            commonLib.fail("Exception in Testcase - reActivateSIMPopupFalse " + e.getMessage(), true);
        }
    }
}