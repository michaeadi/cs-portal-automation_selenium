package com.airtel.cs.ui.profileManagement;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import lombok.extern.log4j.Log4j2;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Log4j2
public class ProfileManagementTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1,groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openProfileManagementPage() {
        try {
            selUtils.addTestcaseDescription("Validating Profile Management with Validating Filter and Columns Present", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();
            pages.getProfileManagement().waitTillPMPageLoads();
            assertCheck.append(actions.assertEqual_boolean(pages.getProfileManagement().isProfileConfigFilterPresent(), true, "Profile Management configuration filter present.", "Profile Management configuration filter does not present."));
            assertCheck.append(actions.assertEqual_boolean(pages.getProfileManagement().isRoleStatusFilterPresent(), true, "Profile Management Role status filter present.", "Profile Management Role status filter does not present."));
            assertCheck.append(actions.assertEqual_intType(pages.getProfileManagement().getNumberOfColumns(), 5, "Number of columns as expected", "Number of columns aren't as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getNameOfCol(0), "Role Name", "Column 1 Name as expected", "Column Name 1 isn't as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getNameOfCol(1), "Profile Configuration Status", "Column 2 Name as expected", "Column Name 2 isn't as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getNameOfCol(2), "Role Status", "Column 3 Name as expected", "Column Name 3 isn't as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getNameOfCol(3), "Role Description", "Column 4 Name as expected", "Column Name 4 isn't as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getNameOfCol(4), "Action", "Column 5 Name as expected", "Column Name 5 isn't as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openProfileManagementPage" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = "openProfileManagementPage",groups = {"RegressionTest"})
    public void profileManagementNotConfiguredFilterTest() {
        try {
            selUtils.addTestcaseDescription("Validating Profile Management's Configuration Filter with 'Not Configured' role status,Apply filter with role config status, Check all the role with searched role config status", "description");
            pages.getProfileManagement().getConfigFilterElement().click();
            pages.getProfileManagement().clickOnOption("Not Configured");
            pages.getProfileManagement().waitTillPMPageLoads();
            int size = pages.getProfileManagement().getNumberOfProfiles();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getConfigurationCol(i), "Not Configured", "Role validated with 'Not Configured' status", "Role does not validated with 'Not Configured' status"));
                }
            } else {
                commonLib.info("No Role found with search filter", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - profileManagementNotConfiguredFilterTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = {"openProfileManagementPage", "profileManagementNotConfiguredFilterTest"},groups = {"RegressionTest"})
    public void profileManagementConfiguredFilterTest() {
        try {
            selUtils.addTestcaseDescription("Validating Profile Management's Configuration Filter with 'Configured' role status,Apply filter with role config status, Check all the role with searched role config status", "description");
            pages.getProfileManagement().getConfigFilterElement().click();
            pages.getProfileManagement().clickOnOption("Configured");
            pages.getProfileManagement().waitTillPMPageLoads();
            int size = pages.getProfileManagement().getNumberOfProfiles();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getConfigurationCol(i), "Configured", "Role validated with 'Configured' status", "Role does not validated with 'Configured' status"));
                }
            } else {
                commonLib.info("No Role found with search filter", true);
            }
            pages.getProfileManagement().getConfigFilterElement().click();
            pages.getProfileManagement().clickOnOption("All");
        } catch (Exception e) {
            commonLib.fail("Exception in Method - profileManagementConfiguredFilterTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"RegressionTest"}, dependsOnMethods = "openProfileManagementPage")
    public void inactiveRoleStatusFilterTest() {
        try {
            selUtils.addTestcaseDescription("Validating Profile Management's Role Status Filter,Apply Filter with role status inactive,Validate all the role fetched as per filter applied criteria", "description");
            pages.getProfileManagement().getRoleStatusFilterElement().click();
            pages.getProfileManagement().clickOnOption("Inactive");
            pages.getProfileManagement().waitTillPMPageLoads();
            int size = pages.getProfileManagement().getNumberOfProfiles();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getRoleStatusCol(i), "Inactive", "Role status same as expected", "Role status not same as expected"));
                }
            } else {
                commonLib.info("No Role found with search filter", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - inactiveRoleStatusFilterTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5,groups = {"RegressionTest"}, dependsOnMethods = {"openProfileManagementPage", "inactiveRoleStatusFilterTest"})
    public void activeRoleStatusFilterTest() {
        try {
            selUtils.addTestcaseDescription("Validating Profile Management's Role Status Filter,Apply Filter with role status inactive,Validate all the role fetched as per filter applied criteria", "description");
            actions.assertAllFoundFailedAssert(assertCheck);
            pages.getProfileManagement().getRoleStatusFilterElement().click();
            pages.getProfileManagement().clickOnOption("Active");
            pages.getProfileManagement().waitTillPMPageLoads();
            int size = pages.getProfileManagement().getNumberOfProfiles();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(pages.getProfileManagement().getRoleStatusCol(i), "Active", "Role status same as expected", "Role status not same as expected"));
                }
            } else {
                commonLib.info("No Role found with search filter", true);
            }
            pages.getProfileManagement().getRoleStatusFilterElement().click();
            pages.getProfileManagement().clickOnOption("All");
        } catch (Exception e) {
            commonLib.fail("Exception in Method - activeRoleStatusFilterTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 6, groups = {"RegressionTest"}, dataProviderClass = DataProviders.class, dependsOnMethods = "openProfileManagementPage")
    public void widgetOrderTest() {
        try {
            selUtils.addTestcaseDescription("Validating Role's Widget Order Test,Get Role Order from Profile Management which is set by admin,Open Customer Dashboard,Validate all the widget must display in same order which is set by admin.", "description");
            String[] widgets;
            if (pages.getProfileManagement().isRoleConfigured(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME))) {
                pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
                assertCheck.append(actions.assertEqual_boolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
                int size = pages.getProfileManagement().getWidgetRowsSize() - pages.getProfileManagement().getDisableWidgetRows();
                widgets = new String[size];
                for (int i = 1; i <= size; i++) {
                    widgets[i - 1] = pages.getProfileManagement().getWidgetNameForOrder(i);
                }
            } else {
                pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
                assertCheck.append(actions.assertEqual_boolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
                pages.getProfileManagement().checkAllUnselectedWidgetsCheckboxes();
                int size = pages.getProfileManagement().getWidgetRowsSize();
                widgets = new String[size];
                for (int i = 1; i <= size; i++) {
                    widgets[i - 1] = pages.getProfileManagement().getWidgetNameForOrder(i);
                }
                assertCheck.append(actions.assertEqual_boolean(pages.getProfileManagement().isSubmitButtonEnable(), true, "Submit button enable as expected", "Submit button does not enable as expected"));
                pages.getProfileManagement().clickingSubmitButton();
                pages.getProfileManagement().waitTillPMPageLoads();
            }
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(constants.getValue(ApplicationConstants.CUSTOMER_MSISDN));
            pages.getMsisdnSearchPage().clickOnSearch();
            assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isCustomerProfilePageLoaded(), true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getCustomerProfilePage().getFirstWidgetHeader(), widgets[0],"Widget order number 1 name as expected","Widget order number 1 name as not expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getCustomerProfilePage().getSecondWidgetHeader(), widgets[1],"Widget order number 2 name as expected","Widget order number 2 name as not expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getCustomerProfilePage().getThirdWidgetHeader(), widgets[2],"Widget order number 3 name as expected","Widget order number 3 name as not expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getCustomerProfilePage().getFourthWidgetHeader(), widgets[3],"Widget order number 4 name as expected","Widget order number 4 name as not expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - widgetOrderTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
