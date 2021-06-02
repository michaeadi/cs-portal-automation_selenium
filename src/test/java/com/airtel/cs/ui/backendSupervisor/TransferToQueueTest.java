package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TransferQueueDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TransferToQueueTest extends Driver {

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

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        selUtils.addTestcaseDescription("Open Supervisor Dashboard", "description");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dataProvider = "TransferQueue", description = "Transfer to queue", enabled = true, dataProviderClass = DataProviders.class)
    public void transferToQueue(TransferQueueDataBean data) {
        selUtils.addTestcaseDescription("Transfer to queue", "description");
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
            Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().trim().toLowerCase(), data.getFromQueue().toLowerCase().trim(), "Ticket Does not found with Selected State");
            softAssert.assertTrue(pages.getSupervisorTicketList().validateQueueFilter(data.getFromQueue()), "Queue Filter Does Applied Correctly");
            try {
                ticketId = pages.getSupervisorTicketList().getTicketIdValue();
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
                Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
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
                        Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getToQueue().toLowerCase().trim(), "Ticket Does not Transfer to Selected Queue");
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
