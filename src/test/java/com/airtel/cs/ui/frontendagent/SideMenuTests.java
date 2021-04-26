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
        pages.getSideMenuPage().clickOnSideMenu();
        try {
            softAssert.assertTrue(pages.getSideMenuPage().isAdminSettingVisible(), "Admin Setting Module does not displayed");
            softAssert.assertTrue(pages.getSideMenuPage().isCustomerServicesVisible(), "Customer Service Module does not displayed");
            softAssert.assertTrue(pages.getSideMenuPage().isUserManagementVisible(), "User Management Module does not displayed");
            softAssert.assertTrue(pages.getSideMenuPage().isProfileManagementVisible(), "Profile Management Module does not displayed");
            softAssert.assertTrue(pages.getSideMenuPage().isTemplateManagementVisible(), "Template Management Module does not displayed");
            pages.getSideMenuPage().clickOnName();
            softAssert.assertTrue(pages.getSideMenuPage().isCustomerInteractionVisible(), "Admin Setting Module does not displayed");
            softAssert.assertTrue(pages.getSideMenuPage().isSupervisorDashboardVisible(), "Supervisor Dashboard Module does not displayed");
            softAssert.assertTrue(pages.getSideMenuPage().isTicketBulkUpdateVisible(), "Ticket Bulk Update Module does not displayed");
        } catch (NotFoundException | TimeoutException e) {
            softAssert.fail("Side Menu Failed");
        } finally {
            pages.getSideMenuPage().clickOnSideMenu();
            softAssert.assertAll();
        }
    }
}