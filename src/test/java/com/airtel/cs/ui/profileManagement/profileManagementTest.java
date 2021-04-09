package com.airtel.cs.ui.profileManagement;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.airtel.cs.commonutils.dataproviders.DataProviders.User;

@Log4j2
public class profileManagementTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Validating Profile Management")
    public void openProfileManagementPage() {
        ExtentTestManager.startTest("Validating Profile Management", "Validating Profile Management with Validating Filter and Columns Present ");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openProfileManagementPage();
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
        ExtentTestManager.startTest("Validating Profile Management's Configuration Filter", "Validating Profile Management's Configuration Filter");
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
        ExtentTestManager.startTest("Validating Profile Management's Role Status Filter", "Validating Profile Management's Role Status Filter");
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
    @Test(priority = 3, description = "Validating Role's Widget Order Test", dataProvider = "loginData", dataProviderClass = DataProviders.class, dependsOnMethods = "openProfileManagementPage")
    public void widgetOrderTest(TestDatabean data) {
        ExtentTestManager.startTest("Validating Role's Widget Order Test", "Validating Profile Management with Validating Filter and Columns Present ");
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
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        softAssert.assertEquals(pages.getCustomerProfilePage().getFirstWidgetHeader().trim().toLowerCase(), widgets[0].trim().toLowerCase());
        softAssert.assertEquals(pages.getCustomerProfilePage().getSecondWidgetHeader().trim().toLowerCase(), widgets[1].trim().toLowerCase());
        softAssert.assertEquals(pages.getCustomerProfilePage().getThirdWidgetHeader().trim().toLowerCase(), widgets[2].trim().toLowerCase());
        softAssert.assertEquals(pages.getCustomerProfilePage().getFourthWidgetHeader().trim().toLowerCase(), widgets[3].trim().toLowerCase());
        softAssert.assertAll();
    }
}
