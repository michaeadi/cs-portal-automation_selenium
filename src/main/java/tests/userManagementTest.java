package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.UMDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.userManagementPOM;

public class userManagementTest extends BaseTest {


    @Test(priority = 1)
    public void openUserManagementPage() {
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        ExtentTestManager.startTest("Validating User Management", "Validating User Management");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        userManagementPOM userManagement = SideMenuPOM.openUserManagementPage();
        userManagement.waitTillUMPageLoaded();
        softAssert.assertTrue(userManagement.isSearchVisible());
        softAssert.assertAll();
    }

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 2, dataProviderClass = DataProvider.class, dataProvider = "loginData")
    public void openEditUserPage(TestDatabean Data) {
        ExtentTestManager.startTest("Validating User Management Edit Page", "Validating User Management Edit Page and Search Auuid Functionality  ");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.waitTillLoaderGetsRemoved();
        userManagement.searchAuuid(Data.getLoginAUUID());
        userManagement.clickSearchButton();
        userManagement.waitUntilResultPageIsVisible();
        softAssert.assertEquals(userManagement.resultIsVisible(Data.getLoginAUUID()), Data.getLoginAUUID());
        userManagement.clickViewEditButton();
        userManagement.waitUntilEditPageIsOpen();
        softAssert.assertAll();

    }

    @Test(priority = 3, description = "SideMenu ", dataProvider = "getInteractionChannelData", dataProviderClass = DataProvider.class)
    public void getInteractionChannel(UMDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Interaction Channel", "Validating User Management Edit User : Interaction Channel");
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
    public void getWorkflows(UMDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Work Flows", "Validating User Management Edit User : Work Flows");
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
    public void getLoginQueue(UMDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Login Queue", "Validating User Management Edit User :  Login Queue");
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
