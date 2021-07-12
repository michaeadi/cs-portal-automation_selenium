package com.airtel.cs.ui.backendAgent.transfertoqueue;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.common.requisite.BAPreRequisites;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TransferQueueDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.agentpermission.AgentPermission;
import io.restassured.http.Headers;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TransferToQueueTest extends Driver {

    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private String ticketId = null;

    /**
     * Transfer to queue not working once need to verify one Env Up
     */

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void verifyPermission() {
        try {
            selUtils.addTestcaseDescription("Verify the permission for transfer to queue given to backend agent", "description");
            AgentPermission agentPermission = api.transferToQueuePermissionAPI();
            final int statusCode = agentPermission.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Agent Permission API success and status code is :" + statusCode, "Agent Permission API got failed and status code is :" + statusCode));
            String transfer_to_Queue_permission = constants.getValue(PermissionConstants.TRANSFER_QUEUE_PERMISSION);
            if (statusCode == 200) {
                assertCheck.append(actions.assertEqual_boolean(agentPermission.getResult().hasTransferToQueuePermission, UtilsMethods.isUserHasPermission(new Headers(map), transfer_to_Queue_permission), "Agent have permission to perform action transfer to queue", "Agent does not have permission to perform action transfer to queue"));
            } else {
                commonLib.fail("Agent Permission API failed.", false);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyPermissionViaAPI" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"verifyPermission"})
    public void agentQueueLogin() {
        try {
            selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isSideMenuVisible(), true, "Side menu visible as expected", "Side menu visible as not expected"));
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openBackendAgentDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().isQueueLoginPage(), true, "Backend Agent Queue login page display", "Backend Agent Queue login Page does not display"));
            assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().checkSubmitButton(), true, "Backend Agent Queue Submit button display", "Backend Agent Queue Submit button does not display"));
            pages.getAgentLoginPage().clickSelectQueue();
            pages.getAgentLoginPage().selectAllQueue();
            pages.getAgentLoginPage().clickOutside();
            pages.getAgentLoginPage().clickSubmitBtn();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.BACKEND_TICKET_LIST_PAGE_TITLE), "Backend Agent Redirect to Ticket List Page", "Backend Agent Does not Redirect to Ticket List Page"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - agentQueueLogin" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"verifyPermission", "agentQueueLogin"})
    public void checkTransferToQueueVisible() {
        try {
            selUtils.addTestcaseDescription("Backend Agent Select Ticket the from list , Validate the user able to view transfer to queue option", "description");
            pages.getSupervisorTicketList().clickCheckbox();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Transfer to Queue Option Visible to agent after selecting ticket as expected.", "Transfer to Queue Option is not Visible to agent after selecting ticket as expected."));
            pages.getSupervisorTicketList().clickCheckbox();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkTransferToQueueVisible" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"RegressionTest"}, enabled = false, dependsOnMethods = {"verifyPermission", "agentQueueLogin"})
    public void checkTransferToQueueConfig() {
        selUtils.addTestcaseDescription("Verify available queues while 'transfer to queue' over the agent screen ,Validate the queue name which config by admin", "description");
        pages.getSupervisorTicketList().clickCheckbox();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Agent have permission to transfer to queue and option visible after selecting ticket", "Agent have permission to transfer to queue but option does not visible after selecting ticket"));
        pages.getSupervisorTicketList().clickTransfertoQueue();
        /**
         *
         * Need config file format how to store data in excel
         * Implement read excel and assertion pending
         * */
        pages.getSupervisorTicketList().clickCloseTab();
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"verifyPermission", "agentQueueLogin"})
    public void performSingleTicketTransferToQueue() {
        try {
            selUtils.addTestcaseDescription("Verify that when only one ticket is selected for Transfer to Queue on the Ticket List Page, Validate that transfer to queue option displayed,Click on Transfer to queue option,Validate transfer to queue tab open, click on first queue available,Check error screen visible or not", "description");
            ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            pages.getSupervisorTicketList().clickCheckbox();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Agent have permission to transfer to queue and option visible after selecting ticket", "Agent have permission to transfer to queue but option does not visible after selecting ticket"));
            try {
                pages.getSupervisorTicketList().clickTransfertoQueue();
                assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer to Queue Pop up open as expected", "Transfer to Queue Page Title Does not Display"));
                String toQueueName = pages.getTransferToQueue().getFirstTransferQueue();
                pages.getTransferToQueue().clickTransferQueue(toQueueName);
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().getTransferSuccessMessage().contains("successfully"), true, "Successmessage displayed after transfer to queue action.", "Success message does not displayed after transfer to queue action."));
                if (pages.getSupervisorTicketList().isCancelBtn()) {
                    commonLib.warning("Not able to perform Transfer to Queue and message displayed: " + pages.getSupervisorTicketList().getTransferErrorMessage());
                    pages.getSupervisorTicketList().clickCancelBtn();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace(), true);
                pages.getSupervisorTicketList().clickCloseTab();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - performSingleTicketTransferToQueue" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"verifyPermission", "agentQueueLogin"})
    public void performMultipleTicketTransferToQueue() {
        try {
            selUtils.addTestcaseDescription("Verify that agent able to select multiple ticket for Transfer to Queue on the Ticket List Page, Validate that transfer to queue option displayed,Click on Transfer to queue option,Validate transfer to queue tab open, click on first queue available,Check error screen visible or not", "description");
            ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            pages.getSupervisorTicketList().clickSelectAll();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Agent have permission to transfer to queue and option visible after selecting ticket", "Agent have permission to transfer to queue but option does not visible after selecting ticket"));
            try {
                pages.getSupervisorTicketList().clickTransfertoQueue();
                assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer to Queue Pop up open as expected", "Transfer to Queue Page Title Does not Display"));
                String toQueueName = pages.getTransferToQueue().getFirstTransferQueue();
                pages.getTransferToQueue().clickTransferQueue(toQueueName);
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().getTransferSuccessMessage().contains("successfully"), true, "Ticket transfer to queue message successfully displayed", "Ticket does not transfer to queue message successfully displayed"));
                if (pages.getSupervisorTicketList().isCancelBtn()) {
                    commonLib.warning("Not able to perform Transfer to Queue and message displayed: " + pages.getSupervisorTicketList().getTransferErrorMessage());
                    pages.getSupervisorTicketList().clickCancelBtn();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace(), true);
                pages.getSupervisorTicketList().clickCloseTab();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - performMultipleTicketTransferToQueue" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"verifyPermission", "agentQueueLogin"})
    public void isUserHasWorkFlowOverRidePermission() {
        try {
            selUtils.addTestcaseDescription("Verify that Service Profile widget should be visible to the logged in agent if HLR permission is enabled in UM, Check User has permission to view HLR Widget Permission", "description");
            String workflow_override = constants.getValue(PermissionConstants.WORKFLOW_OVERRIDE_PERMISSION);
            assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isUserHasPermission(new Headers(map), workflow_override), true, "Agent has permission of ticket workflow override as expected", "Agent does not have permission of ticket workflow override as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasHLRPermission" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"isUserHasWorkFlowOverRidePermission", "verifyPermission", "agentQueueLogin"})
    public void validateTransferAnyWay() {
        try {
            selUtils.addTestcaseDescription("Verify that when only one ticket is selected for Transfer to Queue on the Ticket List Page, Validate that transfer to queue option displayed,Click on Transfer to queue option,Validate transfer to queue tab open, click on first queue available,Check error screen visible or not", "description");
            ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            pages.getSupervisorTicketList().clickCheckbox();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Agent have permission to transfer to queue and option visible after selecting ticket", "Agent have permission to transfer to queue but option does not visible after selecting ticket"));
            try {
                pages.getSupervisorTicketList().clickTransfertoQueue();
                assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer to Queue Pop up open as expected", "Transfer to Queue Page Title Does not Display"));
                String toQueueName = pages.getTransferToQueue().getFirstTransferQueue();
                pages.getTransferToQueue().clickTransferQueue(toQueueName);
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferAnyWayBtn(), true, "Transfer Anyway button display as expected", "Transfer Anyway button does not display as expected"));
                if (pages.getSupervisorTicketList().isTransferAnyWayBtn()) {
                    commonLib.info("Performing Transfer Anyway operation as ticket not transfer to given queue: " + pages.getSupervisorTicketList().getTransferErrorMessage());
                    pages.getSupervisorTicketList().clickTransferAnyWayBtn();
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().getTransferSuccessMessage().contains("successfully"), true, "Ticket transfer to queue message successfully displayed", "Ticket does not transfer to queue message successfully displayed"));
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace(), true);
                pages.getSupervisorTicketList().clickCloseTab();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - performSingleTicketTransferToQueue" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 9, dataProvider = "TransferQueue", dataProviderClass = DataProviders.class, enabled = false, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"verifyPermission", "agentQueueLogin"})
    public void transferToQueue(TransferQueueDataBean data) {
        selUtils.addTestcaseDescription("Backend Agent Transfer to queue", "description");
        String ticketId = null;
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getFilterTabPage().scrollToQueueFilter();
            pages.getFilterTabPage().clickQueueFilter();
            pages.getFilterTabPage().selectQueueByName(data.getFromQueue());
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().trim().toLowerCase(), data.getFromQueue().toLowerCase().trim(), "Ticket Does not found with Selected State");
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().validateQueueFilter(data.getFromQueue()), true, "Queue Filter Does Applied Correctly", "Queue Filter does not applied correctly"));
            try {
                ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                pages.getSupervisorTicketList().resetFilter();
                pages.getSupervisorTicketList().writeTicketId(ticketId);
                pages.getSupervisorTicketList().clickSearchBtn();
                pages.getSupervisorTicketList().clickCheckbox();
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isAssignToAgent(), true, "Assign to Agent Button Does Available after selecting ticket", "Assign to Agent Button Does Not Available after selecting ticket"));
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "Transfer to Queue Button Does Available after selecting ticket", "Transfer to Queue Button Does Not Available after selecting ticket"));
                    pages.getSupervisorTicketList().clickTransfertoQueue();
                    assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer to Queue Pop up open as expected", "Transfer to Queue Page Title Does not Display"));
                    pages.getTransferToQueue().clickTransferQueue(data.getToQueue());
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace(), true);
                    pages.getTransferToQueue().clickCloseTab();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("No Ticket Found in Selected Queue to perform transfer to queue action" + e.fillInStackTrace(), true);
            }
            try {
                Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                pages.getSupervisorTicketList().clearInputBox();
            } catch (AssertionError f) {
                f.printStackTrace();
                commonLib.info("Not able to perform transfer to Queue action: " + pages.getSupervisorTicketList().getTransferErrorMessage());
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCancelBtn(), true, "Cancel Button display as expected", "Cancel Button does not display."));
                if (data.getTransferAnyway().equalsIgnoreCase("true")) {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferAnyWayBtn(), true, "Transfer Anyway button does not display as expected", "Transfer Anyway button does not displayed."));
                    try {
                        pages.getSupervisorTicketList().clickTransferAnyWayBtn();
                        pages.getSupervisorTicketList().writeTicketId(ticketId);
                        pages.getSupervisorTicketList().clickSearchBtn();
                        Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                    } catch (NoSuchElementException | TimeoutException g) {
                        commonLib.fail("Transfer Anyway does not display in case of ticket does not transfer to selected queue.", true);
                        pages.getSupervisorTicketList().clickCancelBtn();
                    }
                } else {
                    pages.getSupervisorTicketList().clickCancelBtn();
                    commonLib.fail("Transfer to queue does not Perform as per config sheet both queue belong to same workgroup.", false);
                }
            }
        } catch (InterruptedException | NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.fail("Not able to apply filter " + e.getMessage(), true);
        }
        pages.getSupervisorTicketList().clearInputBox();
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
