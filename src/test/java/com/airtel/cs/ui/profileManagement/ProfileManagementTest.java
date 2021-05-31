package com.airtel.cs.ui.profileManagement;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import lombok.extern.log4j.Log4j2;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.airtel.cs.commonutils.dataproviders.DataProviders.User;

@Log4j2
public class ProfileManagementTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, description = "Validating Profile Management")
    public void openProfileManagementPage() {
        selUtils.addTestcaseDescription("Validating Profile Management with Validating Filter and Columns Present", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openProfileManagementPage();
        pages.getProfileManagement().waitTillPMPageLoads();
        softAssert.assertTrue(pages.getProfileManagement().isProfileConfigFilterPresent(),"Profile Management configuration filter does not present.");
        softAssert.assertTrue(pages.getProfileManagement().isRoleStatusFilterPresent(),"Profile Management Role status filter does not present.");
        softAssert.assertEquals(pages.getProfileManagement().getNumberOfColumns(), 5, "Number of columns aren't as expected");
        softAssert.assertEquals(pages.getProfileManagement().getNameOfCol(0), "Role Name", "Column 1 isn't as expected");
        softAssert.assertEquals(pages.getProfileManagement().getNameOfCol(1), "Profile Configuration Status", "Column 2 isn't as expected");
        softAssert.assertEquals(pages.getProfileManagement().getNameOfCol(2), "Role Status", "Column 3 isn't as expected");
        softAssert.assertEquals(pages.getProfileManagement().getNameOfCol(3), "Role Description", "Column 4 isn't as expected");
        softAssert.assertEquals(pages.getProfileManagement().getNameOfCol(4), "Action", "Column 5 isn't as expected");
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openProfileManagementPage")
    public void configurationFilterTest() {
        selUtils.addTestcaseDescription("Validating Profile Management's Configuration Filter", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getProfileManagement().getConfigFilterElement().click();
        pages.getProfileManagement().clickOnOption("Not Configured");
        pages.getProfileManagement().waitTillLoaderGetsRemoved();
        pages.getProfileManagement().waitTillPMPageLoads();
        int size = pages.getProfileManagement().getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(pages.getProfileManagement().getConfigurationCol(i), "Not Configured");
        }
        pages.getProfileManagement().getConfigFilterElement().click();
        pages.getProfileManagement().clickOnOption("Configured");
        pages.getProfileManagement().waitTillLoaderGetsRemoved();
        pages.getProfileManagement().waitTillPMPageLoads();
        size = pages.getProfileManagement().getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(pages.getProfileManagement().getConfigurationCol(i), "Configured");
        }

        pages.getProfileManagement().getConfigFilterElement().click();
        pages.getProfileManagement().clickOnOption("All");
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validating Profile Management", dependsOnMethods = "openProfileManagementPage")
    public void roleStatusFilterTest() {
        selUtils.addTestcaseDescription("Validating Profile Management's Role Status Filter", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getProfileManagement().waitTillLoaderGetsRemoved();
        pages.getProfileManagement().getRoleStatusFilterElement().click();
        pages.getProfileManagement().clickOnOption("Inactive");
        pages.getProfileManagement().waitTillLoaderGetsRemoved();
        pages.getProfileManagement().waitTillPMPageLoads();
        int size = pages.getProfileManagement().getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(pages.getProfileManagement().getRoleStatusCol(i), "Inactive");
        }
        pages.getProfileManagement().getRoleStatusFilterElement().click();
        pages.getProfileManagement().clickOnOption("Active");
        pages.getProfileManagement().waitTillLoaderGetsRemoved();
        pages.getProfileManagement().waitTillPMPageLoads();
        size = pages.getProfileManagement().getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(pages.getProfileManagement().getRoleStatusCol(i), "Active");
        }

        pages.getProfileManagement().getRoleStatusFilterElement().click();
        pages.getProfileManagement().clickOnOption("All");
        softAssert.assertAll();
    }

    @User()
    @Test(priority = 4, description = "Validating Role's Widget Order Test", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openProfileManagementPage")
    public void widgetOrderTest(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating Role's Widget Order Test", "description");
        SoftAssert softAssert = new SoftAssert();
        String[] widgets;
        if (pages.getProfileManagement().isRoleConfigured(data.getRoleType())) {
            pages.getProfileManagement().viewRoleWithName(data.getRoleType());
            pages.getProfileManagement().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getProfileManagement().isEditPageLoaded());
            int size = pages.getProfileManagement().getWidgetRowsSize() - pages.getProfileManagement().getDisableWidgetRows();
            widgets = new String[size];
            for (int i = 1; i <= size; i++) {
                widgets[i - 1] = pages.getProfileManagement().getWidgetNameForOrder(i);
            }
            log.info(widgets.length);
        } else {
            pages.getProfileManagement().viewRoleWithName(data.getRoleType());
            pages.getProfileManagement().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getProfileManagement().isEditPageLoaded());
            pages.getProfileManagement().checkAllUnselectedWidgetsCheckboxes();
            int size = pages.getProfileManagement().getWidgetRowsSize();
            widgets = new String[size];
            for (int i = 1; i <= size; i++) {
                widgets[i - 1] = pages.getProfileManagement().getWidgetNameForOrder(i);
            }
            log.info(widgets.length);
            softAssert.assertTrue(pages.getProfileManagement().isSubmitButtonEnable());
            pages.getProfileManagement().clickingSubmitButton();
            pages.getProfileManagement().waitTillLoaderGetsRemoved();
            pages.getProfileManagement().waitTillPMPageLoads();
        }
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isCustomerProfilePageLoaded());
        softAssert.assertEquals(pages.getCustomerProfilePage().getFirstWidgetHeader().trim().toLowerCase(), widgets[0].trim().toLowerCase());
        softAssert.assertEquals(pages.getCustomerProfilePage().getSecondWidgetHeader().trim().toLowerCase(), widgets[1].trim().toLowerCase());
        softAssert.assertEquals(pages.getCustomerProfilePage().getThirdWidgetHeader().trim().toLowerCase(), widgets[2].trim().toLowerCase());
        softAssert.assertEquals(pages.getCustomerProfilePage().getFourthWidgetHeader().trim().toLowerCase(), widgets[3].trim().toLowerCase());
        softAssert.assertAll();
    }
}
