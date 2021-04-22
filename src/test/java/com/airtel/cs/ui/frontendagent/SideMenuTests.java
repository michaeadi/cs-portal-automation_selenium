package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SideMenuTests extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Validating Side Menu ")
    public void sideMenuAssert() {
        selUtils.addTestcaseDescription("Validating Side Menu and It's Options", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        try {
            softAssert.assertTrue(pages.getSideMenu().isAdminSettingVisible(), "Admin Setting Module does not displayed");
            softAssert.assertTrue(pages.getSideMenu().isCustomerServicesVisible(), "Customer Service Module does not displayed");
            softAssert.assertTrue(pages.getSideMenu().isUserManagementVisible(), "User Management Module does not displayed");
            softAssert.assertTrue(pages.getSideMenu().isProfileManagementVisible(), "Profile Management Module does not displayed");
            softAssert.assertTrue(pages.getSideMenu().isTemplateManagementVisible(), "Template Management Module does not displayed");
            pages.getSideMenu().clickOnName();
            softAssert.assertTrue(pages.getSideMenu().isCustomerInteractionVisible(), "Admin Setting Module does not displayed");
            softAssert.assertTrue(pages.getSideMenu().isSupervisorDashboardVisible(), "Supervisor Dashboard Module does not displayed");
            softAssert.assertTrue(pages.getSideMenu().isTicketBulkUpdateVisible(), "Ticket Bulk Update Module does not displayed");
        } catch (NotFoundException | TimeoutException e) {
            softAssert.fail("Side Menu Failed");
        } finally {
            pages.getSideMenu().clickOnSideMenu();
            softAssert.assertAll();
        }
    }
}