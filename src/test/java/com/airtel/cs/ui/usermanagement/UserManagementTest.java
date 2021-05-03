package com.airtel.cs.ui.usermanagement;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class UserManagementTest extends Driver {

    int currentBucketSize;
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1)
    public void openUserManagementPage() {
        selUtils.addTestcaseDescription("Validating User Management", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openUserManagementPage();
        pages.getUserManagementPage().waitTillUMPageLoaded();
        softAssert.assertTrue(pages.getUserManagementPage().isSearchVisible());
        softAssert.assertAll();
    }

    @Test(priority = 2,dependsOnMethods = "openUserManagementPage")
    public void validateAddToUser() throws InterruptedException {
        selUtils.addTestcaseDescription("Validating Add to User Management page", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUserManagementPage().clickAddUserBtn();
        Thread.sleep(60000);
        pages.getUserManagementPage().switchFrameToAddUser();
        softAssert.assertTrue(pages.getUserManagementPage().checkingAddUser(),"Add to user page does not open");
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 3, dependsOnMethods = "openUserManagementPage", description = "Validating User Management Edit Page", dataProviderClass = DataProviders.class, dataProvider = "loginData")
    public void openEditUserPage(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating User Management Edit Page", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUserManagementPage().waitTillLoaderGetsRemoved();
        pages.getUserManagementPage().searchAuuid(data.getLoginAUUID());
        pages.getUserManagementPage().clickSearchButton();
        pages.getUserManagementPage().waitUntilResultPageIsVisible();
        softAssert.assertEquals(pages.getUserManagementPage().resultIsVisible(data.getLoginAUUID()), data.getLoginAUUID());
        currentBucketSize = Integer.parseInt(pages.getUserManagementPage().getCurrentTicketBucketSize());
        pages.getUserManagementPage().clickViewEditButton();
        pages.getUserManagementPage().waitUntilEditPageIsOpen();
        softAssert.assertAll();

    }


    @Test(priority = 4, dependsOnMethods = "openEditUserPage", description = "Validating User Management Edit User : Interaction Channel")
    public void getInteractionChannel() throws InterruptedException {
        selUtils.addTestcaseDescription("Validating User Management Edit User : Interaction Channel", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUserManagementPage().openListInteractionChannels();
        ArrayList<String> strings = pages.getUserManagementPage().getInteractionChannels();
        try {
            pages.getUserManagementPage().pressESC();
        } catch (NoSuchElementException | TimeoutException e) {
            pages.getUserManagementPage().clickOutside();
        }
        DataProviders data = new DataProviders();
        List<String> interactionChannel = data.getInteractionChannelData();
        for (String s : strings) {
            if (interactionChannel.contains(s)) {
                commonLib.info("Validate " + s + " interaction channel is display correctly");
                interactionChannel.remove(s);
            } else {
                commonLib.fail(s + " interaction channel must not display on frontend as tag not mention in config sheet.",true);
                softAssert.fail(s + " interaction channel should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (interactionChannel.isEmpty()) {
            commonLib.pass("All interaction channel correctly configured and display on UI.");
        } else {
            for (String element : interactionChannel) {
                commonLib.fail(element + " interaction channel does not display on UI but present in config sheet.",true);
                softAssert.fail(element + " interaction channel does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();

    }


    @Test(priority = 5, dependsOnMethods = "openEditUserPage", description = "Validating User Management Edit User : Work Flows")
    public void getWorkflows() throws InterruptedException {
        selUtils.addTestcaseDescription("Validating User Management Edit User : Work Flows", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUserManagementPage().openWorkgroupList();
        ArrayList<String> strings = pages.getUserManagementPage().getWorkflows();
        try {
            pages.getUserManagementPage().pressESC();
        } catch (NoSuchElementException | TimeoutException e) {
            pages.getUserManagementPage().clickOutside();
        }
        DataProviders data = new DataProviders();
        List<String> workFlow = data.getWorkFlowData();
        for (String s : strings) {
            if (workFlow.contains(s)) {
                commonLib.info("Validate " + s + " workgroup is display correctly");
                workFlow.remove(s);
            } else {
                commonLib.fail(s + " workgroup must not display on frontend as tag not mention in config sheet.",true);
                softAssert.fail(s + " workgroup should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (workFlow.isEmpty()) {
            commonLib.pass("All workgroup correctly configured and display on UI.");
        } else {
            for (String element : workFlow) {
                commonLib.fail(element + " workgroup does not display on UI but present in config sheet.",true);
                softAssert.fail(element + " workgroup does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();

    }

    @Test(priority = 6, dependsOnMethods = "openEditUserPage", description = "Validating User Management Edit User : Login Queue")
    public void getLoginQueue() throws InterruptedException {
        selUtils.addTestcaseDescription("Validating User Management Edit User : Login Queue", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUserManagementPage().openLoginQueueList();
        ArrayList<String> strings = pages.getUserManagementPage().getLoginQueues();
        try {
            pages.getUserManagementPage().pressESC();
        } catch (NoSuchElementException | TimeoutException e) {
            pages.getUserManagementPage().clickOutside();
        }
        DataProviders data = new DataProviders();
        List<String> loginQueue = data.getLoginQueueData();
        for (String s : strings) {
            if (loginQueue.contains(s)) {
                commonLib.info("Validate " + s + " ticketPool is display correctly");
                loginQueue.remove(s);
            } else {
                commonLib.fail(s + " ticketPool must not display on frontend as tag not mention in config sheet.",true);
                softAssert.fail(s + " ticketPool should not display on UI as interaction channel not mention in config sheet.");
            }
        }
        if (loginQueue.isEmpty()) {
            commonLib.pass("All ticketPool correctly configured and display on UI.");
        } else {
            for (String element : loginQueue) {
                commonLib.fail(element + " ticketPool does not display on UI but present in config sheet.",true);
                softAssert.fail(element + " ticketPool does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();

    }

    @DataProviders.User()
    @Test(priority = 7, dependsOnMethods = "openEditUserPage", description = "Validating Bucket Size", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void changeBucketSize(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating Bucket Size", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getUserManagementPage().setTicketBucketSize(currentBucketSize + 1);
        pages.getUserManagementPage().clickUpdateButton();
        pages.getUserManagementPage().waitTillLoaderGetsRemoved();
        pages.getUserManagementPage().searchAuuid(data.getLoginAUUID());
        pages.getUserManagementPage().clickSearchButton();
        pages.getUserManagementPage().waitUntilResultPageIsVisible();
        softAssert.assertEquals(pages.getUserManagementPage().resultIsVisible(data.getLoginAUUID()), data.getLoginAUUID());
        softAssert.assertEquals(Integer.parseInt(pages.getUserManagementPage().getCurrentTicketBucketSize()), currentBucketSize + 1, "Updated Bucket Size is not as Expected");
        softAssert.assertAll();

    }

}