package com.airtel.cs.ui.autounassignment;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.agents.AdditionalDetails;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLog;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLogList;
import io.restassured.http.Headers;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutoUnAssignmentTest extends Driver {

    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest","PositiveFlowUnAssignment"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest","PositiveFlowUnAssignment"})
    public void checkToRunUnAssignment() {
        if (!continueUnAssignment) {
            commonLib.skip("Skipping tests because ticket does not assigned to user");
            throw new SkipException("Skipping tests because ticket does not assigned to user");
        }
    }
    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkIfTicketIdPresent() {
        if (Boolean.valueOf(constants.getValue(String.valueOf(CommonConstants.AUTO_ASSIGNMENT_TICKET_ID==null)))) {
            commonLib.skip("Skipping tests because ticket does not assigned to user");
            throw new SkipException("Skipping tests because ticket does not assigned to user");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","PositiveFlowUnAssignment"})
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

    @Test(priority = 2,groups ={"SanityTest", "RegressionTest","SmokeTest","PositiveFlowUnAssignment"},dependsOnMethods = {"openSupervisorDashboard"})
    public void validateTicketAutoNotUnAssigned(){
        try {
            selUtils.addTestcaseDescription("Validate Agent have already assigned a ticket, Validate Agent login into portal before X hour then Ticket must not be unassigned from agent bucket. ", "description");
            String ticketId=constants.getValue(CommonConstants.AUTO_ASSIGNMENT_TICKET_ID);
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getSupervisorTicketList().getTicketIdValue(), ticketId, "Searched Ticket found on UI as expected","Searched Ticket does not found on UI as expected",true));
            assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getAssigneeAUUID(),loginAUUID,"Ticket auto assigned to Login Agent","Ticket does not auto assigned to Login Agent",true));
            TicketHistoryLog ticketHistoryLog=api.getTicketHistoryLog(ticketId);
            final Integer statusCode = ticketHistoryLog.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Ticket History log API Status Code Matched and is :" + statusCode, "Ticket History log Status Code NOT Matched and is :" + statusCode,false));
            TicketHistoryLogList ticketHistoryLogList=ticketHistoryLog.getResult().getTicketInteractionComments().get(ticketHistoryLog.getResult().getTicketInteractionComments().size()-1);
            assertCheck.append(actions.assertNotEqualStringType(ticketHistoryLogList.getEvent(),constants.getValue(CommonConstants.AUTO_UN_ASSIGNMENT_EVENT_NAME),"Ticket auto un-assigned from user by auto un-assignment event does not occurred as user login into portal before x hour.","Ticket auto un-assigned from user by auto un-assignment event regardless of agent login into portal before x hour."));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicketAutoUnAssigned" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3,groups ={"SanityTest", "RegressionTest", "ProdTest","SmokeTest"} )
    public void validateTicketAutoUnAssigned(){
        try {
            selUtils.addTestcaseDescription("Validate Agent Login into queue,Validate ticket assigned to login agent", "description");
            String ticketId=constants.getValue(CommonConstants.AUTO_ASSIGNMENT_TICKET_ID);
            TicketHistoryLog ticketHistoryLog=api.getTicketHistoryLog(ticketId);
            final Integer statusCode = ticketHistoryLog.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Ticket History log API Status Code Matched and is :" + statusCode, "Ticket History log Status Code NOT Matched and is :" + statusCode,false));
            TicketHistoryLogList ticketHistoryLogList=ticketHistoryLog.getResult().getTicketInteractionComments().get(ticketHistoryLog.getResult().getTicketInteractionComments().size()-1);
            AdditionalDetails agentDetails= UtilsMethods.getAgentDetail(new Headers(map)).getAdditionalDetails();
            assertCheck.append(actions.assertEqualIntType(ticketHistoryLogList.getAgentId(),agentDetails.getId(),"Agent id same as expected","Agent id does not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(ticketHistoryLogList.getAssignTo(),agentDetails.getName(),"Agent name same as expected","Agent name does not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(ticketHistoryLogList.getEvent(),constants.getValue(CommonConstants.AUTO_UN_ASSIGNMENT_EVENT_NAME),"Ticket auto un-assigned from user by auto un-assignment event","Ticket does not auto un-assigned from user by auto un-assignment event"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicketAutoUnAssigned" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
