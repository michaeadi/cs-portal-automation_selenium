package tests;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.sideMenuPOM;
import utils.ExtentReports.ExtentTestManager;
import utils.TestDatabean;

import java.lang.reflect.Method;

public class sideMenuTests extends BaseTest {

    @Test(priority = 1, description = "SideMenu ", dataProvider = "getTestData")
    public void LoggingIN(Method method, TestDatabean Data) throws InterruptedException {
        sideMenuPOM SideMenuPOM = new sideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Base URL");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        System.out.println(SideMenuPOM.getUserName());
        Thread.sleep(50000);
        SideMenuPOM.clickOnSideMenu();
        softAssert.assertTrue(SideMenuPOM.isAdminSettingVisible());
        softAssert.assertTrue(SideMenuPOM.isCustomerServicesVisible());
        softAssert.assertTrue(SideMenuPOM.isCaseManagementVisible());
        softAssert.assertTrue(SideMenuPOM.isUserManagementVisible());
        softAssert.assertTrue(SideMenuPOM.isProfileManagementVisible());
        softAssert.assertTrue(SideMenuPOM.isSupervisorDashboardVisible());
        softAssert.assertTrue(SideMenuPOM.isCustomerInteractionVisible());
        softAssert.assertTrue(SideMenuPOM.isUserManagementVisible());
        softAssert.assertAll();
    }

    @Test(priority = 1, dataProvider = "getTestData")
    public void CheckingSubmenu() {

    }

//    @Test(priority = 1, description = "Click on View Heatmap button and validate that a new window has been opened")
//    public void Clicking_On_HeatMap(Method method) {
//        ExtentTestManager.startTest(method.getName(), "Click on View Heatmap button and validate that a new window has been opened");
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on view heatmap");
//        loginPagePOM.clickOnHeatmap();
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating that new window opens");
//        loginpage.validateForNewWindow(2);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating  new window's URL");
//        loginpage.ValidateURL("https://vwo.com/");
//    }
//
//    @Test(priority = 2, description = "Click on the “Element list” tab. Verify that the element list panel is opened and displayed")
//    public void Opening_Element_List(Method method) {
//        ExtentTestManager.startTest(method.getName(), "Click on the “Element list” tab. Verify that the element list panel is opened and displayed");
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating a list of elements opens on clicking element list tab");
//        loginpage.clickOnElementList();
//    }
//
//    @Test(priority = 3, description = "Click on the “Start Free Trial” button on the element list and verify that it gets highlighted on the left as shown in the below image")
//    public void Asserting_Highlight_of_element(Method method) {
//        ExtentTestManager.startTest(method.getName(), "Click on the “Start Free Trial” button on the element list and verify that it gets highlighted on the left as shown in the below image");
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Start free trail element from list");
//
//        loginpage.clickonStartTrailElement();
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Validating that clicking on Start free trail element from list a box highlights start free trail");
//        loginpage.assertingStartTrailHighlight("block");
//
//
//    }
}