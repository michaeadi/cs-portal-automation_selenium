package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class SupervisorAddCommentTest extends Driver {

    private final BaseActions actions = new BaseActions();

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

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", groups = {"RegressionTest"})
    public void addCommentOnTicket() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Supervisor Add Comment on Ticket,Add new Comment[Backend Supervisor],Validate added display in comment section", "description");
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            try {
                String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                String comment = "Supervisor added comment on ticket using automation";
                try {
                    pages.getSupervisorTicketList().viewTicket();
                    Assert.assertEquals(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully");
                    try {
                        pages.getViewTicket().addComment(comment);
                        pages.getViewTicket().clickAddButton();
                        pages.getViewTicket().validateAddedComment(comment);
                    } catch (NoSuchElementException | TimeoutException e) {
                        commonLib.fail("Not able to add comment on ticket :" + e.fillInStackTrace(), true);
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Not able to view ticket properly: " + e.fillInStackTrace(), true);
                    pages.getViewTicket().clickBackButton();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("No Ticket Found in open state " + e.fillInStackTrace(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - addCommentOnTicket" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = "addCommentOnTicket", groups = {"RegressionTest"})
    public void validateIssueComment() {
        try {
            selUtils.addTestcaseDescription("Validate issue comment [Backend Supervisor] found on ticket or not", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getViewTicket().validateCommentType(constants.getValue(CommonConstants.ISSUE_COMMENT_TYPE)), true, "Issue Comment found on ticket", "Issue Comment does not found on ticket"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateIssueComment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, dependsOnMethods = "addCommentOnTicket", groups = {"RegressionTest"})
    public void editComment() {
        try {
            selUtils.addTestcaseDescription("Validate Edit comment as Backend Supervisor", "description");
            String comment = "Adding updated comment using automation";
            pages.getViewTicket().openEditCommentBox();
            pages.getViewTicket().clearCommentBox();
            pages.getViewTicket().addComment(comment);
            pages.getViewTicket().clickAddButton();
            pages.getViewTicket().validateAddedComment(comment);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - editComment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, dependsOnMethods = "addCommentOnTicket", groups = {"RegressionTest"})
    public void deleteLastAddedComment() {
        try{
        selUtils.addTestcaseDescription("Validate Delete comment as Backend Supervisor,Add new comment,Delete latest comment which added previously.", "description");
        String comment = "Adding Comment to test Delete comment Flow " + LocalDateTime.now();
        pages.getViewTicket().addComment(comment);
        pages.getViewTicket().clickAddButton();
        pages.getViewTicket().validateAddedComment(comment);
        pages.getViewTicket().openDeleteComment();
        pages.getViewTicket().clickContinueButton();
        assertCheck.append(actions.assertEqual_boolean(pages.getViewTicket().isCommentDelete(comment),true, "Deleted comment found does not found on ticket as expected","Deleted comment found on ticket as deleted comment must not found on ticket."));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - deleteLastAddedComment" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
