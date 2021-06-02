package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketTransferRuleDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TicketTransferRuleTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Ticket Transfer Rule Test", dataProvider = "ticketTransferRule", dataProviderClass = DataProviders.class)
    public void ticketTransferRuleCheck(TicketTransferRuleDataBean ruleData) throws InterruptedException {
        selUtils.addTestcaseDescription("Ticket Transfer Rule Test " + ruleData.getIssueCode() + " to ticket state " + ruleData.getToQueue(), "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getFilterTabPage().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().applyFilterByCategoryCode(ruleData.getIssueCode());
            pages.getFilterTabPage().clickQueueFilter();
            pages.getFilterTabPage().selectQueueByName(ruleData.getFromQueue());
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getFilterTabPage().waitTillLoaderGetsRemoved();
        } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            pages.getFilterTabPage().clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        try {
            ticketId = pages.getSupervisorTicketList().getTicketIdValue();
        } catch (NoSuchElementException | TimeoutException e) {
            pages.getSupervisorTicketList().resetFilter();
            Assert.fail("No Ticket Found with Selected Filter ", e.getCause());
        }
        pages.getSupervisorTicketList().viewTicket();
        Assert.assertEquals(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully");
        String selectedState = pages.getViewTicket().selectState(ruleData.getTicketToState());
        if (!selectedState.equalsIgnoreCase("Required State not found")) {
            try {
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                pages.getSupervisorTicketList().writeTicketId(ticketId);
                pages.getSupervisorTicketList().clickSearchBtn();
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                softAssert.assertEquals(pages.getSupervisorTicketList().getTicketIdValue(), ticketId, "Search Ticket Does not Fetched Correctly");
                softAssert.assertEquals(pages.getSupervisorTicketList().getStatevalue().toLowerCase().trim(), selectedState.toLowerCase().trim(), "Ticket Does not Updated to Selected State");
                softAssert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), ruleData.getToQueue().toLowerCase().trim(), "Ticket does not updated to correct ticket pool");
            } catch (TimeoutException | NoSuchElementException e) {
                softAssert.fail("Ticket has been transferred to Selected but not able search ticket." + e.fillInStackTrace());
            }
            pages.getSupervisorTicketList().clearInputBox();
        } else {
            pages.getViewTicket().clickOutside();
            pages.getViewTicket().clickBackButton();
            softAssert.fail("Required State not found");
        }
        softAssert.assertAll();
    }
}
