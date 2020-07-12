package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.UMDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.userManagementPOM;

import java.io.IOException;
import java.lang.reflect.Method;

public class userManagementTest extends BaseTest {


    @Test(priority = 1)
    public void openUserManagementPage(Method method) throws IOException {
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening User Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening User Management Page");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        userManagementPOM userManagement = SideMenuPOM.openUserManagementPage();
        userManagement.waitTillUMPageLoaded();
        softAssert.assertTrue(userManagement.isSearchVisible());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "SideMenu ")
    public void openEditUserPage(Method method) throws IOException {
        ExtentTestManager.startTest(method.getName(), "Opening User Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening User Management Page");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.waitTillLoaderGetsRemoved();
        userManagement.searchAuuid("2388006");
        userManagement.clickSearchButton();
        userManagement.waitUntilResultPageIsVisible();
        softAssert.assertEquals(userManagement.resultIsVisible("2388006"), "2388006");
        userManagement.clickViewEditButton();
        userManagement.waitUntilEditPageIsOpen();
        softAssert.assertAll();

    }

    @Test(priority = 3, description = "SideMenu ", dataProvider = "getInteractionChannelData", dataProviderClass = DataProvider.class)
    public void getInteractionChannel(Method method, UMDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Opening User Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening User Management Page");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        userManagement.openListInteractionChannels();
        String[] strings = userManagement.getInteractionChannels();
        for (String a : strings) {
            System.out.println(a);
        }
        softAssert.assertTrue(userManagement.isInteractionChannelPresent(strings, Data.getValue().trim()));
        userManagement.pressESC();
        softAssert.assertAll();

    }

    @Test(priority = 4, description = "SideMenu ", dataProvider = "getWorkFlowData", dataProviderClass = DataProvider.class)
    public void getWorkflows(Method method, UMDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Opening User Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening User Management Page");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        userManagement.openWorkgroupList();
        String[] strings = userManagement.getWorkflows();
        for (String a : strings) {
            System.out.println(a);
        }
        softAssert.assertTrue(userManagement.isWorkFlowPresent(strings, Data.getValue().trim()));
        softAssert.assertAll();
        userManagement.pressESC();
    }

    @Test(priority = 5, description = "SideMenu ", dataProvider = "getLoginQueueData", dataProviderClass = DataProvider.class)
    public void getLoginQueue(Method method, UMDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Opening User Management Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening User Management Page");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();

        userManagement.openLoginQueueList();
        String[] strings = userManagement.getLoginQueues();
        for (String a : strings) {
            System.out.println(a);
        }
        softAssert.assertTrue(userManagement.isLoginQueuePresent(strings, Data.getValue().trim()));
        softAssert.assertAll();
        userManagement.pressESC();


    }

}
