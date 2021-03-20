package tests.frontendAgent;

import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import tests.frontendAgent.BaseTest;

public class sideMenuTests extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Validating Side Menu ")
    public void sideMenuAssert() throws InterruptedException {
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest("Validating Side Menu", "Validating Side Menu and It's Options");
        SoftAssert softAssert = new SoftAssert();
//        Thread.sleep(10000);
        SideMenuPOM.clickOnSideMenu();
        try {
            softAssert.assertTrue(SideMenuPOM.isAdminSettingVisible(),"Admin Setting Module does not displayed");
            softAssert.assertTrue(SideMenuPOM.isCustomerServicesVisible(),"Customer Service Module does not displayed");
//            softAssert.assertTrue(SideMenuPOM.isCaseManagementVisible());
            softAssert.assertTrue(SideMenuPOM.isUserManagementVisible(),"User Management Module does not displayed");
            softAssert.assertTrue(SideMenuPOM.isProfileManagementVisible(),"Profile Management Module does not displayed");
            softAssert.assertTrue(SideMenuPOM.isTemplateManagementVisible(),"Template Management Module does not displayed");
            SideMenuPOM.clickOnName();
//        SideMenuPOM.clickOnSideMenu();
            softAssert.assertTrue(SideMenuPOM.isCustomerInteractionVisible(),"Admin Setting Module does not displayed");
            softAssert.assertTrue(SideMenuPOM.isSupervisorDashboardVisible(),"Supervisor Dashboard Module does not displayed");
            softAssert.assertTrue(SideMenuPOM.isTicketBulkUpdateVisible(),"Ticket Bulk Update Module does not displayed");
        } catch (NotFoundException | TimeoutException e) {
            softAssert.fail("Side Menu Failed");
        } finally {
            SideMenuPOM.clickOnSideMenu();
            softAssert.assertAll();
        }
    }
}