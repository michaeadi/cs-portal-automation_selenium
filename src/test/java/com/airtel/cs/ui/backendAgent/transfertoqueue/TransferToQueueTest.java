package com.airtel.cs.ui.backendAgent.transfertoqueue;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.common.requisite.BAPreRequisites;
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

public class TransferToQueueTest extends BAPreRequisites {

    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1,groups = {"SanityTest", "RegressionTest", "ProdTest"})
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

    @Test(priority = 2,groups = {"SanityTest", "RegressionTest", "ProdTest"})
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

    @Test(priority = 3,groups = {"SanityTest", "RegressionTest", "ProdTest"},dependsOnMethods = {"verifyPermission","agentQueueLogin"})
    public void checkTransferToQueueVisible(){
        selUtils.addTestcaseDescription("Backend Agent Select Ticket the from list , Validate the user able to view transfer to queue option", "description");
        pages.getSupervisorTicketList().clickCheckbox();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(),true,"Transfer to Queue Option Visible to agent after selecting ticket as expected.","Transfer to Queue Option is not Visible to agent after selecting ticket as expected."));
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4,groups = {"RegressionTest"},enabled = false)
    public void checkTransferToQueueConfig(){
        selUtils.addTestcaseDescription("Verify available queues while 'transfer to queue' over the agent screen","description");
        pages.getSupervisorTicketList().clickCheckbox();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(),true, "Agent have permission to transfer to queue and option visible after selecting ticket","Agent have permission to transfer to queue but option does not visible after selecting ticket"));
        pages.getSupervisorTicketList().clickTransfertoQueue();
        /**
         *
         * Need config file format how to store data in excel
         * Implement read excel and assertion pending
         * */
        pages.getSupervisorTicketList().clickCloseTab();
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, dataProvider = "TransferQueue", dataProviderClass = DataProviders.class)
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
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().validateQueueFilter(data.getFromQueue()),true, "Queue Filter Does Applied Correctly","Queue Filter does not applied correctly"));
            try {
                ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                pages.getSupervisorTicketList().resetFilter();
                pages.getSupervisorTicketList().writeTicketId(ticketId);
                pages.getSupervisorTicketList().clickSearchBtn();
                pages.getSupervisorTicketList().clickCheckbox();
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isAssignToAgent(), true,"Assign to Agent Button Does Available after selecting ticket","Assign to Agent Button Does Not Available after selecting ticket"));
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(),true,"Transfer to Queue Button Does Available after selecting ticket", "Transfer to Queue Button Does Not Available after selecting ticket"));
                    pages.getSupervisorTicketList().clickTransfertoQueue();
                    assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(),true,"Transfer to Queue Pop up open as expected","Transfer to Queue Page Title Does not Display"));
                    pages.getTransferToQueue().clickTransferQueue(data.getToQueue());
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Not able to perform Transfer to Queue: " + e.fillInStackTrace(),true);
                    pages.getTransferToQueue().clickCloseTab();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("No Ticket Found in Selected Queue to perform transfer to queue action" + e.fillInStackTrace(),true);
            }
            try {
                Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                pages.getSupervisorTicketList().clearInputBox();
            } catch (AssertionError f) {
                f.printStackTrace();
                commonLib.info("Not able to perform transfer to Queue action: " + pages.getSupervisorTicketList().getTransferErrorMessage());
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCancelBtn(), true,"Cancel Button display as expected","Cancel Button does not display."));
                if (data.getTransferAnyway().equalsIgnoreCase("true")) {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferAnyWayBtn(),true,"Transfer Anyway button does not display as expected", "Transfer Anyway button does not displayed."));
                    try {
                        pages.getSupervisorTicketList().clickTransferAnyWayBtn();
                        pages.getSupervisorTicketList().writeTicketId(ticketId);
                        pages.getSupervisorTicketList().clickSearchBtn();
                        Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
                    } catch (NoSuchElementException | TimeoutException g) {
                        commonLib.fail("Transfer Anyway does not display in case of ticket does not transfer to selected queue.",true);
                        pages.getSupervisorTicketList().clickCancelBtn();
                    }
                } else {
                    pages.getSupervisorTicketList().clickCancelBtn();
                    commonLib.fail("Transfer to queue does not Perform as per config sheet both queue belong to same workgroup.",false);
                }
            }
        } catch (InterruptedException | NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.fail("Not able to apply filter " + e.getMessage(),true);
        }
        pages.getSupervisorTicketList().clearInputBox();
        actions.assertAllFoundFailedAssert(assertCheck);
    }


}
