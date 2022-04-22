package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BackendAgentUpdateTicketTest extends Driver {


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest"})
    public void updateTicket() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Backend Agent Update Ticket", "description");
            commonLib.info("Opening URL");
            String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            pages.getSupervisorTicketList().viewTicket();
            assertCheck.append(actions.assertEqualStringType(ticketId, pages.getViewTicket().getTicketId(), "Searched ticket fetched successfully", "Searched ticket not fetched successfully"));
            DataProviders data = new DataProviders();
            String selectStateByConfig=data.getState(constants.getValue(CommonConstants.TICKET_CLOSE_STATE)).get(0).getTicketStateName();
            pages.getViewTicket().selectState(selectStateByConfig);
            pages.getSupervisorTicketList().enterCommentAndClickSubmit();
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().noTicketFound(), true, "Backend agent NOT able to see closed ticket", "Backend agent able to see closed ticket"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - updateTicket", true);
        }
    }
}
