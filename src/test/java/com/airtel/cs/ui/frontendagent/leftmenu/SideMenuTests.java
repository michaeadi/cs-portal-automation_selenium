package com.airtel.cs.ui.frontendagent.leftmenu;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SideMenuTests extends Driver {
    private final BaseActions actions = new BaseActions();


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSideMenuOption() {
        try {
            selUtils.addTestcaseDescription("Validating Side Menu Options, Validating all options are visible as per permission given", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isSideMenuVisible(), true, "Side Menu is Visible", "Side Menu is NOT Visible"));
            pages.getSideMenuPage().clickOnSideMenu();
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isAdminSettingVisible(), true, "Admin Setting Module is Visible", "Admin Setting Module NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isCustomerServicesVisible(), true, "Customer Service Module is Visible", "Customer Service Module NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isUserManagementVisible(), true, "User Management Module is Visible", "User Management Module NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isProfileManagementVisible(), true, "Profile Management Module is Visible", "Profile Management Module NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isTemplateManagementVisible(), true, "Template Management Module is Visible", "Template Management Module NOT Visible"));
            pages.getSideMenuPage().clickOnUserName();
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isCustomerInteractionVisible(), true, "Admin Setting Module is Visible", "Admin Setting Module NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isSupervisorDashboardVisible(), true, "Supervisor Dashboard Module is Visible", "Supervisor Dashboard Module NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSideMenuPage().isTicketBulkUpdateVisible(), true, "Ticket Bulk Update Module is Visible", "Ticket Bulk Update Module NOT Visible"));
            pages.getSideMenuPage().clickOnSideMenu();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - testSideMenuOption", true);
        }
    }
}