package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.QueueStateDataBeans;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.ticketlist.QueueStates;
import com.airtel.cs.model.response.ticketlist.Ticket;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Log4j2
public class StateQueueMappingTest extends Driver {

    RequestSource api = new RequestSource();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionBS) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"RegressionTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dataProvider = "queueState", groups = {"RegressionTest"}, dataProviderClass = DataProviders.class)
    public void stateQueueTest(QueueStateDataBeans data) {
        try {
            selUtils.addTestcaseDescription("State Queue Mapping Test: " + data.getQueue() + ",Apply filter with queue name " + data.getQueue() + ",Validate filter applied correctly,Validate all state mapped to queue must be same as config file.", "description");
            DataProviders dataProviders = new DataProviders();
            String ticketId;
            try {
                pages.getSupervisorTicketList().clickFilter();
                pages.getFilterTabPage().scrollToQueueFilter();
                pages.getFilterTabPage().clickQueueFilter();
                pages.getFilterTabPage().selectQueueByName(data.getQueue());
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickApplyFilter();
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().validateQueueFilter(data.getQueue()), true, "Queue Filter applied correctly with queue name " + data.getQueue(), "Queue Filter Does Applied Correctly or No Ticket Found"));
            } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
                pages.getFilterTabPage().clickOutsideFilter();
                pages.getFilterTabPage().clickCloseFilter();
                Assert.fail("Not able to apply filter " + e.fillInStackTrace());
            }
            Assert.assertEquals(pages.getSupervisorTicketList().getQueueValue().toLowerCase().trim(), data.getQueue().toLowerCase().trim(), "Ticket Does not found with Selected Queue");
            ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            Ticket ticketPOJO = api.ticketMetaDataTest(ticketId);
            List<QueueStates> assignState = ticketPOJO.getResult().getQueueStates();
            List<String> state = pages.getSupervisorTicketList().getAssignedStateName(assignState);
            List<String> configState = dataProviders.getQueueState(data.getQueue());
            pages.getSupervisorTicketList().matchStateName(state, configState, data.getQueue());
            pages.getSupervisorTicketList().resetFilter();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - stateQueueTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
