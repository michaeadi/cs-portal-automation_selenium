package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.ticketlist.TicketPOJO;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssignToAgentTicketTest extends Driver {

    RequestSource api = new RequestSource();
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
        commonLib.info("Opening URL");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Assign Ticket to Agent")
    public void assignTicketToAgent() throws InterruptedException {
        selUtils.addTestcaseDescription("Supervisor Perform Assign Ticket to Agent", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String auuid = null;
        pages.getSupervisorTicketList().changeTicketTypeToOpen();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getSupervisorTicketList().isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
        softAssert.assertTrue(pages.getSupervisorTicketList().isWorkGroupName(), "Ticket Meta Data Does Not  Have Workgroup");
        softAssert.assertTrue(pages.getSupervisorTicketList().isPrioritylabel(), "Ticket Meta Data  Does Not  Have Priority");
        softAssert.assertTrue(pages.getSupervisorTicketList().isStateLabel(), "Ticket Meta Data Does Not  Have State");
        softAssert.assertTrue(pages.getSupervisorTicketList().isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
        softAssert.assertTrue(pages.getSupervisorTicketList().isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
        softAssert.assertTrue(pages.getSupervisorTicketList().isQueueLabel(), "Ticket Meta Data Have Does Not Queue");
        softAssert.assertTrue(pages.getSupervisorTicketList().isIssueLabel(), "Ticket Meta Data Have Does Not Issue");
        softAssert.assertTrue(pages.getSupervisorTicketList().isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
        softAssert.assertTrue(pages.getSupervisorTicketList().isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
        softAssert.assertTrue(pages.getSupervisorTicketList().isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
        softAssert.assertTrue(pages.getSupervisorTicketList().isCodeLabel(), "Ticket Meta Data Does Not Have Code");
        String ticketQueue = pages.getSupervisorTicketList().getqueueValue();
        String assigneeAUUID = pages.getSupervisorTicketList().getAssigneeAUUID();
        String ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
        TicketPOJO ticketAPI = api.ticketMetaDataTest(ticketId);
        if (ticketAPI.getResult().getAssignee() == null) {
            softAssert.assertTrue(pages.getSupervisorTicketList().isNotAssigneeDisplay(), "Not Assigned does not display correctly");
        } else {
            softAssert.assertEquals(pages.getSupervisorTicketList().getAssigneeName().toLowerCase().trim(), ticketAPI.getResult().getAssignee().toLowerCase().trim(), "Assignee pan does not display on UI Correctly.");
        }
        pages.getSupervisorTicketList().clickCheckbox();
        softAssert.assertTrue(pages.getSupervisorTicketList().isAssignToAgent(), "Assign to Agent Button  Does Not Available");
        softAssert.assertTrue(pages.getSupervisorTicketList().isTransferToQueue(), "Transfer to Queue Button  Does Not Available");
        pages.getSupervisorTicketList().clickAssigntoAgent();
        softAssert.assertTrue(pages.getAssignToAgentPage().validatePageTitle(), "Assign to Agent tab Does Not Open");
        softAssert.assertEquals(pages.getAssignToAgentPage().getQueueName(), ticketQueue, "Assign to Agent tab Queue does not Open Correctly");
        try {
            auuid = pages.getAssignToAgentPage().ticketAssignedToAgent(assigneeAUUID).trim();
        } catch (ElementClickInterceptedException | NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to assign ticket to agent " + e.fillInStackTrace());
            pages.getAssignToAgentPage().closeAssignTab();
            pages.getSupervisorTicketList().clickCheckbox();
        }
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        pages.getSupervisorTicketList().writeTicketIdSecond(ticketId);
        pages.getSupervisorTicketList().clickSearchBtn();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertEquals(pages.getSupervisorTicketList().getAssigneeAUUID().trim(), auuid, "Ticket does not assigned to agent");
        if (pages.getSupervisorTicketList().getAssigneeAUUID().trim().equalsIgnoreCase(auuid)) {
            commonLib.info("Ticket unassigned from '" + assigneeAUUID + "' and Ticket Assigned to '" + auuid + "'");
            commonLib.info("Validated Ticket is Assigned to User Successfully");
        } else {
            commonLib.info("Ticket unassigned from '" + assigneeAUUID + "' and Ticket Assigned to '" + auuid + "'");
            commonLib.fail("Ticket does not Assigned to User Correctly", true);
        }
        softAssert.assertAll();
    }
}
