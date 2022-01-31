package com.airtel.cs.ui.usermanagement;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class UserManagementTest extends Driver {

    private final BaseActions actions = new BaseActions();
    int currentBucketSize;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void openUserManagementPage() {
        try {
            selUtils.addTestcaseDescription("Validating User Management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openUserManagementPage();
            pages.getUserManagementPage().waitTillUMPageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pages.getUserManagementPage().isSearchVisible(), true, "User management module open as expected", "User management module does not open as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openUserManagementPage" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = {"openUserManagementPage"}, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void validateAddToUser() {
        try {
            selUtils.addTestcaseDescription("Validating Add to User Management page,Click on Add To User button,Validate Add new user page displayed,Navigate back to Single Screen and validate user management module open.", "description");
            pages.getUserManagementPage().clickAddUserBtn();
            pages.getUserManagementPage().switchFrameToAddUser();
            assertCheck.append(actions.assertEqualBoolean(pages.getUserManagementPage().checkingAddUser(), true, "Add user into UM Portal page displayed as expected", "Add to user page does not open"));
            pages.getUserManagementPage().switchToParentFrme();
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openUserManagementPage();
            pages.getUserManagementPage().waitTillUMPageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pages.getUserManagementPage().isSearchVisible(), true, "User management module open as expected", "User management module does not open as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAddToUser" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = "openUserManagementPage", groups = {"RegressionTest"})
    public void openEditUserPage() {
        try {
            selUtils.addTestcaseDescription("Validating User Management Edit Page", "description");
            pages.getUserManagementPage().waitTillLoaderGetsRemoved();
            pages.getUserManagementPage().searchAuuid(constants.getValue(CommonConstants.BETA_USER_ROLE_AUUID));
            pages.getUserManagementPage().clickSearchButton();
            pages.getUserManagementPage().waitUntilResultPageIsVisible();
            assertCheck.append(actions.assertEqualStringType(pages.getUserManagementPage().resultIsVisible(CommonConstants.BETA_USER_ROLE_AUUID), CommonConstants.BETA_USER_ROLE_AUUID, "Search using user auuid success and user detail fetched as expected", "Search using user auuid does not complete and user detail does not fetched as expected"));
            currentBucketSize = Integer.parseInt(pages.getUserManagementPage().getCurrentTicketBucketSize());
            pages.getUserManagementPage().clickViewEditButton();
            pages.getUserManagementPage().waitUntilEditPageIsOpen();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openEditUserPage" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 4, dependsOnMethods = {"openEditUserPage", "openUserManagementPage"}, groups = {"RegressionTest"})
    public void getInteractionChannel() {
        try {
            selUtils.addTestcaseDescription("Validating User Management Edit User : Interaction Channel,Validate all Interaction displayed as per config file.", "description");
            pages.getUserManagementPage().openListInteractionChannels();
            List<String> strings = pages.getUserManagementPage().getInteractionChannels();
            try {
                pages.getUserManagementPage().pressESC();
            } catch (NoSuchElementException | TimeoutException e) {
                pages.getUserManagementPage().clickOutside();
            }
            DataProviders data = new DataProviders();
            List<String> interactionChannel = data.getInteractionChannelData();
            for (String s : strings) {
                if (interactionChannel.contains(s)) {
                    commonLib.pass("Validate " + s + " interaction channel is display correctly");
                    interactionChannel.remove(s);
                } else {
                    commonLib.fail(s + " interaction channel must not display on frontend as tag not mention in config sheet.", false);
                }
            }
            if (interactionChannel.isEmpty()) {
                commonLib.pass("All interaction channel correctly configured and display on UI.");
            } else {
                for (String element : interactionChannel) {
                    commonLib.fail(element + " interaction channel does not display on UI but present in config sheet.", false);
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - getInteractionChannel" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 5, dependsOnMethods = {"openEditUserPage", "openUserManagementPage"}, groups = {"RegressionTest"})
    public void getWorkflows() {
        try {
            selUtils.addTestcaseDescription("Validating User Management Edit User : Work Flows,validate all the workgroup display as per config", "description");
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
                    commonLib.infoColored("Validate " + s + " workgroup is display correctly", JavaColors.GREEN, false);
                    workFlow.remove(s);
                } else {
                    commonLib.fail(s + " workgroup must not display on frontend as tag not mention in config sheet.", false);
                }
            }
            if (workFlow.isEmpty()) {
                commonLib.pass("All workgroup correctly configured and display on UI.");
            } else {
                for (String element : workFlow) {
                    commonLib.fail(element + " workgroup does not display on UI but present in config sheet.", false);
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - getWorkflows" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 6, dependsOnMethods = {"openEditUserPage", "openUserManagementPage"}, groups = {"RegressionTest"})
    public void getLoginQueue() {
        try {
            selUtils.addTestcaseDescription("Validating User Management Edit User : Login Queue,Validate all the queue must be display as per config file.", "description");
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
                    commonLib.pass("Validate " + s + " ticketPool is display correctly");
                    loginQueue.remove(s);
                } else {
                    commonLib.fail(s + " ticketPool must not display on frontend as tag not mention in config sheet.", true);
                }
            }
            if (loginQueue.isEmpty()) {
                commonLib.pass("All ticketPool correctly configured and display on UI.");
            } else {
                for (String element : loginQueue) {
                    commonLib.fail(element + " ticketPool does not display on UI but present in config sheet.", true);
                }
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - getLoginQueue" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);

    }

    @DataProviders.User()
    @Test(priority = 7, dependsOnMethods = {"openEditUserPage", "openUserManagementPage"}, groups = {"RegressionTest"}, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void changeBucketSize() {
        try {
            selUtils.addTestcaseDescription("Validating Bucket Size", "description");
            pages.getUserManagementPage().setTicketBucketSize(currentBucketSize + 1);
            pages.getUserManagementPage().clickUpdateButton();
            pages.getUserManagementPage().waitTillLoaderGetsRemoved();
            pages.getUserManagementPage().searchAuuid(CommonConstants.BETA_USER_ROLE_AUUID);
            pages.getUserManagementPage().clickSearchButton();
            pages.getUserManagementPage().waitUntilResultPageIsVisible();
            assertCheck.append(actions.assertEqualStringType(pages.getUserManagementPage().resultIsVisible(CommonConstants.BETA_USER_ROLE_AUUID), CommonConstants.BETA_USER_ROLE_AUUID, "Search using user auuid success and user detail fetched as expected", "Search using user auuid does not complete and user detail does not fetched as expected"));
            assertCheck.append(actions.assertEqualIntType((Integer.parseInt(pages.getUserManagementPage().getCurrentTicketBucketSize())), currentBucketSize + 1, "Agent bucket size update as expected", "Updated Bucket Size is not as Expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - changeBucketSize" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}