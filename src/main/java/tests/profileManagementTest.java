package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;
import pages.profileManagementPOM;

import static Utils.DataProviders.DataProviders.User;

public class profileManagementTest extends BaseTest {

    @Test(priority = 1, description = "Validating Profile Management")
    public void openProfileManagementPage() {
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest("Validating Profile Management", "Validating Profile Management with Validating Filter and Columns Present ");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        profileManagementPOM profileManagement = SideMenuPOM.openProfileManagementPage();
        profileManagement.waitTillPMPageLoads();
        softAssert.assertTrue(profileManagement.isProfileConfigFilterPresent());
        softAssert.assertTrue(profileManagement.isRoleStatusFilterPresent());
        softAssert.assertEquals(profileManagement.getNumberOfColumns(), 5, "Number of columns aren't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(0), "Role Name", "Column 1 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(1), "Profile Configuration Status", "Column 2 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(2), "Role Status", "Column 3 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(3), "Role Description", "Column 4 isn't as expected");
        softAssert.assertEquals(profileManagement.getNameOfCol(4), "Action", "Column 5 isn't as expected");
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void configurationFilterTest() {
        ExtentTestManager.startTest("Validating Profile Management's Configuration Filter", "Validating Profile Management's Configuration Filter");
        SoftAssert softAssert = new SoftAssert();
        profileManagementPOM profileManagement = new profileManagementPOM(driver);
        profileManagement.getConfigFilterElement().click();
        profileManagement.clickOnOption("Not Configured");
        profileManagement.waitTillLoaderGetsRemoved();
        profileManagement.waitTillPMPageLoads();
        int size = profileManagement.getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(profileManagement.getConfigurationCol(i), "Not Configured");
        }
        profileManagement.getConfigFilterElement().click();
        profileManagement.clickOnOption("Configured");
        profileManagement.waitTillLoaderGetsRemoved();
        profileManagement.waitTillPMPageLoads();
        size = profileManagement.getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(profileManagement.getConfigurationCol(i), "Configured");
        }

        profileManagement.getConfigFilterElement().click();
        profileManagement.clickOnOption("All");
    }

    @Test(priority = 3, description = "Validating Profile Management")
    public void roleStatusFilterTest() {
        ExtentTestManager.startTest("Validating Profile Management's Role Status Filter", "Validating Profile Management's Role Status Filter");
        SoftAssert softAssert = new SoftAssert();
        profileManagementPOM profileManagement = new profileManagementPOM(driver);
        profileManagement.getRoleStatusFilterElement().click();
        profileManagement.clickOnOption("Inactive");
        profileManagement.waitTillLoaderGetsRemoved();
        profileManagement.waitTillPMPageLoads();
        int size = profileManagement.getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(profileManagement.getRoleStatusCol(i), "Inactive");
        }
        profileManagement.getRoleStatusFilterElement().click();
        profileManagement.clickOnOption("Active");
        profileManagement.waitTillLoaderGetsRemoved();
        profileManagement.waitTillPMPageLoads();
        size = profileManagement.getNumberOfProfiles();
        for (int i = 0; i < size; i++) {
            softAssert.assertEquals(profileManagement.getRoleStatusCol(i), "Active");
        }

        profileManagement.getRoleStatusFilterElement().click();
        profileManagement.clickOnOption("All");
    }

    @User(UserType = "ALL")
    @Test(priority = 3, description = "Validating Role's Widget Order Test", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void widgetOrderTest(TestDatabean Data) {
        ExtentTestManager.startTest("Validating Role's Widget Order Test", "Validating Profile Management with Validating Filter and Columns Present ");
        SoftAssert softAssert = new SoftAssert();
        String[] widgets;
        profileManagementPOM profileManagement = new profileManagementPOM(driver);
        if (profileManagement.isRoleConfigured(Data.getRoleType())) {
            profileManagement.viewRoleWithName(Data.getRoleType());
            profileManagement.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(profileManagement.isEditPageLoaded());
            int size = profileManagement.getWidgetRowsSize();
            widgets = new String[size];
            for (int i = 1; i <= size; i++) {
                widgets[i - 1] = profileManagement.getWidgetNameForOrder(i);
            }
            System.out.println(widgets.length);
        } else {
            profileManagement.viewRoleWithName(Data.getRoleType());
            profileManagement.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(profileManagement.isEditPageLoaded());
            profileManagement.checkAllUnselectedWidgetsCheckboxes();
            int size = profileManagement.getWidgetRowsSize();
            widgets = new String[size];
            for (int i = 1; i <= size; i++) {
                widgets[i - 1] = profileManagement.getWidgetNameForOrder(i);
            }
            System.out.println(widgets.length);
            softAssert.assertTrue(profileManagement.isSubmitButtonEnable());
            profileManagement.clickingSubmitButton();
            profileManagement.waitTillLoaderGetsRemoved();
            profileManagement.waitTillPMPageLoads();
        }
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertEquals(customerInteractionPagePOM.getFirstWidgetHeader().trim().toLowerCase(), widgets[0].trim().toLowerCase());
        softAssert.assertEquals(customerInteractionPagePOM.getSecondWidgetHeader().trim().toLowerCase(), widgets[1].trim().toLowerCase());
        softAssert.assertEquals(customerInteractionPagePOM.getThirdWidgetHeader().trim().toLowerCase(), widgets[2].trim().toLowerCase());
        softAssert.assertEquals(customerInteractionPagePOM.getFourthWidgetHeader().trim().toLowerCase(), widgets[3].trim().toLowerCase());
        softAssert.assertAll();
    }
}
