package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.userManagementPOM;

import java.util.ArrayList;

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


    @Test(priority = 3, description = "Validating User Management Edit User : Interaction Channel")
    public void getInteractionChannel() throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Interaction Channel", "Validating User Management Edit User : Interaction Channel");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.openListInteractionChannels();
        ArrayList<String> strings = userManagement.getInteractionChannels();
        DataProviders data = new DataProviders();
        for (int i = 0; i < data.getInteractionChannelData().size(); i++) {
            if (!data.getInteractionChannelData().contains(strings.get(i).toLowerCase().trim())) {
                softAssert.fail(strings.get(i) + " Interaction Channels was expected ,but wasn't found on UI");
            }
        }
        strings.removeAll(data.getInteractionChannelData());
        if (strings.size() > 0) {
            for (String string : strings) {
                softAssert.fail(string + " is not expected in Interaction Channels ,as it is not in excel");
            }
        }
        userManagement.pressESC();
        softAssert.assertAll();

    }


    @Test(priority = 4, description = "Validating User Management Edit User : Work Flows")
    public void getWorkflows() throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Work Flows", "Validating User Management Edit User : Work Flows");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.openWorkgroupList();
        ArrayList<String> strings = userManagement.getWorkflows();
        DataProviders data = new DataProviders();
        for (int i = 0; i < data.getWorkFlowData().size(); i++) {
            if (!data.getWorkFlowData().contains(strings.get(i).toLowerCase().trim())) {
                softAssert.fail(strings.get(i) + " Work Flow was expected ,but wasn't found on UI");
            }
        }
        strings.removeAll(data.getWorkFlowData());
        if (strings.size() > 0) {
            for (String string : strings) {
                softAssert.fail(string + " is not expected in Work Flow ,as it is not in excel");
            }
        }
        userManagement.pressESC();
        softAssert.assertAll();

    }
    @Test(priority = 5, description = "Validating User Management Edit User : Login Queue")
    public void getLoginQueue() throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Login Queue", "Validating User Management Edit User :  Login Queue");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.openLoginQueueList();
        ArrayList<String> strings = userManagement.getLoginQueues();
        DataProviders data = new DataProviders();
        for (int i = 0; i < data.getLoginQueueData().size(); i++) {
            if (!data.getLoginQueueData().contains(strings.get(i).toLowerCase().trim())) {
                softAssert.fail(strings.get(i) + " Login Queue was expected ,but wasn't found on UI");
            }
        }
        strings.removeAll(data.getLoginQueueData());
        if (strings.size() > 0) {
            for (String string : strings) {
                softAssert.fail(string + " is not expected in Login Queue ,as it is not in excel");
            }
        }
        userManagement.pressESC();
        softAssert.assertAll();

    }

    @DataProviders.User(UserType = "ALL")
    @Test(priority = 6, description = "Validating Bucket Size", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void changeBucketSize(TestDatabean Data) {
        ExtentTestManager.startTest("Validating Bucket Size", "Validating Bucket Size");
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
