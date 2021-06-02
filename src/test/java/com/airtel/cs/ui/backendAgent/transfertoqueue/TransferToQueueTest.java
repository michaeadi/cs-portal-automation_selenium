package com.airtel.cs.ui.backendAgent.transfertoqueue;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TransferQueueDataBean;
import com.airtel.cs.pojo.response.agentpermission.AgentPermission;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TransferToQueueTest extends PreRequisites {

    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionBA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1,description = "Permission should be available and we should be able to toggle(Enable or Disable) between the permission")
    public void verifyPermission(){
        selUtils.addTestcaseDescription("Verify the permission for transfer to queue given to backend agent","description");
        AgentPermission agentPermission=api.transferToQueuePermissionAPI();
        if(agentPermission.getStatusCode()==200){
            assertCheck.append(actions.assertEqual_boolean(agentPermission.getResult().hasTransferToQueuePermission,true,"Agent have permission to perform action transfer to queue","Agent does not have permission to perform action transfer to queue"));
        }else{
            commonLib.fail("Agent Permission API failed.",false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2)
    public void testLoginIntoPortal() {
        try {
            selUtils.addTestcaseDescription("Logging Into Portal with valid Backend Agent credentials, Validating opened url,validating login button is getting enabled,Validating dashboard page opened successfully or not?", "description");
            loginAUUID = constants.getValue(CommonConstants.BA_USER_AUUID);
            final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
            pages.getLoginPage().openBaseURL(value);
            assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), value, "Correct URL Opened", "URl isn't as expected"));
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.BA_USER_PASSWORD)));
            assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is NOT enabled"));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionBA = false;
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getUserManagementPage().isUserManagementPageLoaded(), true, "Customer Dashboard Page Loaded Successfully", "Customer Dashboard page NOT Loaded"));
                actions.assertAllFoundFailedAssert(assertCheck);
            }
        } catch (Exception e) {
            continueExecutionBA = false;
            commonLib.fail("Exception in Method - testLoginIntoPortal" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, description = "Backend Agent Queue Login Page")
    public void agentQueueLogin() {
        selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openBackendAgentDashboard();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().isQueueLoginPage(),true,"Backend Agent Queue login page display","Backend Agent Queue login Page does not display"));
        assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().checkSubmitButton(),true,"Backend Agent Queue Submit button display","Backend Agent Queue Submit button does not display"));
        pages.getAgentLoginPage().clickSelectQueue();
        pages.getAgentLoginPage().selectAllQueue();
        pages.getAgentLoginPage().clickSubmitBtn();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"), "Backend Agent Does not Redirect to Ticket List Page");
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4,description = "Irrespective of supervisor having all the queue while transfer ticket to queue, Agent will be able to see only those queue which are configuraed (refer configuraiton)")
    public void checkTransferToQueueConfig(){
        selUtils.addTestcaseDescription("Verify available queues while 'transfer to queue' over the agent screen","description");
        pages.getSupervisorTicketList().clickCheckbox();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(),true, "Agent have permission to transfer to queue and option visible after selecting ticket","Agent have permission to transfer to queue but option does not visible after selecting ticket"));
        pages.getSupervisorTicketList().clickTransfertoQueue();
        //Check for Config
        pages.getSupervisorTicketList().clickCloseTab();
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, dataProvider = "TransferQueue", description = "Transfer to queue", enabled = true, dataProviderClass = DataProviders.class)
    public void transferToQueue(TransferQueueDataBean data) {
        selUtils.addTestcaseDescription("Backend Agent Transfer to queue", "description");
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().scrollToQueueFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().clickQueueFilter();
            pages.getFilterTabPage().selectQueueByName(data.getFromQueue());
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            Assert.assertEquals(pages.getSupervisorTicketList().getqueueValue().trim().toLowerCase(), data.getFromQueue().toLowerCase().trim(), "Ticket Does not found with Selected State");
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().validateQueueFilter(data.getFromQueue()),true, "Queue Filter Does Applied Correctly","Queue Filter does not applied correctly"));
            try {
                ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
                pages.getSupervisorTicketList().resetFilter();
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                pages.getSupervisorTicketList().writeTicketId(ticketId);
                pages.getSupervisorTicketList().clickSearchBtn();
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                pages.getSupervisorTicketList().clickCheckbox();
                try {
                    softAssert.assertTrue(pages.getSupervisorTicketList().isAssignToAgent(), "Assign to Agent Button Does Not Available");
                    softAssert.assertTrue(pages.getSupervisorTicketList().isTransferToQueue(), "Transfer to Queue Button Does Not Available");
                    pages.getSupervisorTicketList().clickTransfertoQueue();
                    softAssert.assertTrue(pages.getTransferToQueue().validatePageTitle(), "Page Title Does not Display");
                    pages.getTransferToQueue().clickTransferQueue(data.getToQueue());
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace());
                    pages.getTransferToQueue().clickCloseTab();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("No Ticket Found in Selected Queue to perform transfer to queue action" + e.fillInStackTrace());
            }
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            try {
                Assert.assertEquals(pages.getSupervisorTicketList().getqueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                pages.getSupervisorTicketList().clearInputBox();
            } catch (AssertionError f) {
                f.printStackTrace();
                commonLib.info("Not able to perform transfer to Queue action: " + pages.getSupervisorTicketList().getTransferErrorMessage());
                softAssert.assertTrue(pages.getSupervisorTicketList().isCancelBtn(), "Cancel Button does not display.");
                if (data.getTransferAnyway().equalsIgnoreCase("true")) {
                    softAssert.assertTrue(pages.getSupervisorTicketList().isTransferAnyWayBtn(), "Transfer Any button does not displayed.");
                    try {
                        pages.getSupervisorTicketList().clickTransferAnyWayBtn();
                        pages.getSupervisorTicketList().writeTicketId(ticketId);
                        pages.getSupervisorTicketList().clickSearchBtn();
                        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                        Assert.assertEquals(pages.getSupervisorTicketList().getqueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                    } catch (NoSuchElementException | TimeoutException g) {
                        softAssert.fail("Transfer Anyway does not display in case of ticket does not transfer to selected queue.");
                        pages.getSupervisorTicketList().clickCancelBtn();
                    }
                } else {
                    pages.getSupervisorTicketList().clickCancelBtn();
                    softAssert.fail("Transfer to queue does not Perform as per config sheet both queue belong to same workgroup.");
                }
            }
        } catch (InterruptedException | NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            e.printStackTrace();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        pages.getSupervisorTicketList().clearInputBox();
        softAssert.assertAll();
    }


}
