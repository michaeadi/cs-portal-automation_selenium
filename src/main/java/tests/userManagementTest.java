package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.UMDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.userManagementPOM;

public class userManagementTest extends BaseTest {

int currentBucketSize;
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

    @DataProviders.User(UserType = "ALL")
    @Test(priority = 2, description = "Validating User Management Edit Page", dataProviderClass = DataProviders.class, dataProvider = "loginData")
    public void openEditUserPage(TestDatabean Data) {
        ExtentTestManager.startTest("Validating User Management Edit Page", "Validating User Management Edit Page and Search Auuid Functionality  ");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.waitTillLoaderGetsRemoved();
        userManagement.searchAuuid(Data.getLoginAUUID());
        userManagement.clickSearchButton();
        userManagement.waitUntilResultPageIsVisible();
        softAssert.assertEquals(userManagement.resultIsVisible(Data.getLoginAUUID()), Data.getLoginAUUID());
        currentBucketSize = Integer.parseInt(userManagement.getCurrentTicketBucketSize());
        userManagement.clickViewEditButton();
        userManagement.waitUntilEditPageIsOpen();
        softAssert.assertAll();

    }

    @Test(priority = 3, description = "Validating User Management Edit User : Interaction Channel", dataProvider = "getInteractionChannelData", dataProviderClass = DataProviders.class)
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

    @Test(priority = 4, description = "Validating User Management Edit User : Work Flows", dataProvider = "getWorkFlowData", dataProviderClass = DataProviders.class)
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

    @Test(priority = 5, description = "Validating User Management Edit User : Login Queue", dataProvider = "getLoginQueueData", dataProviderClass = DataProviders.class)
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

    @DataProviders.User(UserType = "ALL")
    @Test(priority = 6, description = "Validating User Management Edit User : Login Queue", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void changeBucketSize(TestDatabean Data) {
        ExtentTestManager.startTest("Bucket Size", "Validating User Management Edit User :  Login Queue");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.setTicketBucketSize(currentBucketSize + 1);
        userManagement.clickUpdateButton();
        userManagement.waitTillLoaderGetsRemoved();
        userManagement.searchAuuid(Data.getLoginAUUID());
        userManagement.clickSearchButton();
        userManagement.waitUntilResultPageIsVisible();
        softAssert.assertEquals(userManagement.resultIsVisible(Data.getLoginAUUID()), Data.getLoginAUUID());
        softAssert.assertEquals(Integer.parseInt(userManagement.getCurrentTicketBucketSize()), currentBucketSize + 1, "Updated Bucket Size is not as Expected");
        softAssert.assertAll();

    }

}
