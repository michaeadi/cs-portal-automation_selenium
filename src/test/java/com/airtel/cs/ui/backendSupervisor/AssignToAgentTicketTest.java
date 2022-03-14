package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.ticketlist.Ticket;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AssignToAgentTicketTest extends Driver {

    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest"})
    public void checkExecution() {
        if (!(continueExecutionBS && continueExecutionFA)) {
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

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", groups = {"SanityTest", "RegressionTest"})
    public void assignTicketToAgent() {
        try {
            selUtils.addTestcaseDescription("Supervisor Perform Assign Ticket to Agent,Choose First Ticket from supervisor ticket list," +
                    "Click on Check box,Click on assign to agent button,Verify Assign to agent tab open,Click on Assign to button for user which have bucket size is greater than >0 and assignee not same as current assignee of ticket.", "description");
            String auuid = null;
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            String ticketQueue = pages.getSupervisorTicketList().getQueueValue();
            String assigneeAUUID = pages.getSupervisorTicketList().getAssigneeAUUID();
            String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            Ticket ticketAPI = api.ticketMetaDataTest(ticketId);
            if (ticketAPI.getResult().getAssignee() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().isNotAssigneeDisplay(), true, "In case of ticket Not Assigned to any agent assignee pan displayed correctly", "In case of ticket Not Assigned to any agent assignee pan does not display correctly"));
            } else {
                assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getAssigneeName().toLowerCase().trim(), ticketAPI.getResult().getAssignee().toLowerCase().trim(), "In case of ticket Assigned to any agent assignee pan displayed correctly", "In case of ticket Assigned to any agent assignee pan does not display on UI Correctly."));
            }
            pages.getSupervisorTicketList().clickCheckbox();
            pages.getSupervisorTicketList().clickAssigntoAgent();
            assertCheck.append(actions.assertEqualBoolean(pages.getAssignToAgentPage().validatePageTitle(), true, "Assign to Agent tab Open as expected", "Assign to Agent tab does not open as expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAssignToAgentPage().checkNegativeBucket(), false, "Agent bucket size in not in negative", "Agent bucket size in in Negative, Please check manually once"));
            assertCheck.append(actions.assertEqualStringType(pages.getAssignToAgentPage().getQueueName(), ticketQueue, "Assign to Agent tab Queue does not Open Correctly", "Assign to Agent tab Queue does not Open Correctly"));
            try {
                auuid = pages.getAssignToAgentPage().ticketAssignedToAgent(assigneeAUUID).trim();
            } catch (ElementClickInterceptedException | NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to assign ticket to agent " + e.fillInStackTrace(), true);
                pages.getAssignToAgentPage().closeAssignTab();
                pages.getSupervisorTicketList().clickCheckbox();
            }
            pages.getSupervisorTicketList().writeTicketIdSecond(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getAssigneeAUUID().trim(), auuid, "Ticket Assigned to new agent correctly", "Ticket does not Assigned to new agent correctly"));
            if (pages.getSupervisorTicketList().getAssigneeAUUID().trim().equalsIgnoreCase(auuid)) {
                commonLib.infoColored("Ticket unassigned from '" + assigneeAUUID + "' and Ticket Assigned to '" + auuid + "'", JavaColors.GREEN, false);
                commonLib.pass("Validated Ticket is Assigned to User Successfully");
            } else {
                commonLib.infoColored("Ticket unassigned from '" + assigneeAUUID + "' and Ticket Assigned to '" + auuid + "'", JavaColors.RED, false);
                commonLib.fail("Ticket does not Assigned to User Correctly", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - assignTicketToAgent" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
