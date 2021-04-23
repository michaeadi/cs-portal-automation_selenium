package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TransferQueueDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TransferToQueueTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        selUtils.addTestcaseDescription("Open Supervisor Dashboard", "description");
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
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
            Assert.assertEquals(pages.getSupervisorTicketList().getqueueValue().trim().toLowerCase(), data.getFromQueue().toLowerCase().trim(), "Ticket Does not found with Selected State");
            softAssert.assertTrue(pages.getSupervisorTicketList().validateQueueFilter(data.getFromQueue()), "Queue Filter Does Applied Correctly");
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
