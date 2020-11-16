package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
    @Test(priority = 2,dependsOnMethods = "openUserManagementPage",description = "Validating User Management Edit Page", dataProviderClass = DataProviders.class, dataProvider = "loginData")
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


    @Test(priority = 3,dependsOnMethods = "openEditUserPage",description = "Validating User Management Edit User : Interaction Channel")
    public void getInteractionChannel() throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Interaction Channel", "Validating User Management Edit User : Interaction Channel");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.openListInteractionChannels();
        ArrayList<String> strings = userManagement.getInteractionChannels();
        try {
            userManagement.pressESC();
        }catch (NoSuchElementException | TimeoutException e){
            userManagement.clickOutside();
        }
        DataProviders data = new DataProviders();
        ArrayList<String> interactionChannel = data.getInteractionChannelData();
        for (String s : strings) {
            if (interactionChannel.contains(s)) {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " interaction channel is display correctly");
                interactionChannel.remove(s);
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " interaction channel must not display on frontend as tag not mention in config sheet.");
                softAssert.fail(s + " interaction channel should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (interactionChannel.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All interaction channel correctly configured and display on UI.");
        } else {
            for (String element : interactionChannel) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, element + " interaction channel does not display on UI but present in config sheet.");
                softAssert.fail(element + " interaction channel does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();

    }


    @Test(priority = 4,dependsOnMethods = "openEditUserPage",description = "Validating User Management Edit User : Work Flows")
    public void getWorkflows() throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Work Flows", "Validating User Management Edit User : Work Flows");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.openWorkgroupList();
        ArrayList<String> strings = userManagement.getWorkflows();
        try {
            userManagement.pressESC();
        }catch (NoSuchElementException | TimeoutException e){
            userManagement.clickOutside();
        }
        DataProviders data = new DataProviders();
        ArrayList<String> workFlow = data.getWorkFlowData();
        for (String s : strings) {
            if (workFlow.contains(s)) {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " workgroup is display correctly");
                workFlow.remove(s);
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " workgroup must not display on frontend as tag not mention in config sheet.");
                softAssert.fail(s + " workgroup should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (workFlow.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All workgroup correctly configured and display on UI.");
        } else {
            for (String element : workFlow) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, element + " workgroup does not display on UI but present in config sheet.");
                softAssert.fail(element + " workgroup does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();

    }

    @Test(priority = 5,dependsOnMethods = "openEditUserPage", description = "Validating User Management Edit User : Login Queue")
    public void getLoginQueue() throws InterruptedException {
        ExtentTestManager.startTest("Validating User Management Edit User : Login Queue", "Validating User Management Edit User :  Login Queue");
        userManagementPOM userManagement = new userManagementPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        userManagement.openLoginQueueList();
        ArrayList<String> strings = userManagement.getLoginQueues();
        try {
            userManagement.pressESC();
        }catch (NoSuchElementException | TimeoutException e){
            userManagement.clickOutside();
        }
        DataProviders data = new DataProviders();
        ArrayList<String> loginQueue = data.getLoginQueueData();
        for (String s : strings) {
            if (loginQueue.contains(s)) {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " ticketPool is display correctly");
                loginQueue.remove(s);
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " ticketPool must not display on frontend as tag not mention in config sheet.");
                softAssert.fail(s + " ticketPool should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (loginQueue.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All ticketPool correctly configured and display on UI.");
        } else {
            for (String element : loginQueue) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, element + " ticketPool does not display on UI but present in config sheet.");
                softAssert.fail(element + " ticketPool does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();

    }

    @DataProviders.User(UserType = "ALL")
    @Test(priority = 6,dependsOnMethods = "openEditUserPage", description = "Validating Bucket Size", dataProvider = "loginData", dataProviderClass = DataProviders.class)
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