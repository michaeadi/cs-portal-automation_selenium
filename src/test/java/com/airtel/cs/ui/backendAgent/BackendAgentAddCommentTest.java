package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class BackendAgentAddCommentTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void agentQueueLogin() {
        try {
            selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openBackendAgentDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().isQueueLoginPage(), true, "Queue Login Page is Visible", "Queue Login Page is NOT Visible"));
            assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().checkSubmitButton(), true, "Submit button for login is Enabled", "Submit button for login is NOT Enabled"));
            pages.getAgentLoginPage().clickSelectQueue();
            pages.getAgentLoginPage().selectAllQueue();
            pages.getAgentLoginPage().clickSubmitBtn();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.BACKEND_AGENT_TICKET_LIST_PAGE), "Backend Agent Redirected to Ticket List Page Successfully", "Backend Agent Does not Redirect to Ticket List Page"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - agentQueueLogin", true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "agentQueueLogin")
    public void addNewComment() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Backend Agent add new comment on ticket", "description");
            String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
            String comment = "Backend Agent added comment on ticket using automation";
            pages.getSupervisorTicketList().viewTicket();
            assertCheck.append(actions.assertEqual_stringType(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully", "Verify the searched Ticket NOT fetched"));
            pages.getViewTicket().addComment(comment);
            pages.getViewTicket().clickAddButton();
            pages.getViewTicket().validateAddedComment(comment);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - addNewComment", true);
        }
    }

    @Test(priority = 3, dependsOnMethods = "agentQueueLogin", groups = {"SanityTest", "RegressionTest"})
    public void validateIssueCommentBS() {
        try {
            selUtils.addTestcaseDescription("Validate issue comment as Backend Agent", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getViewTicket().validateCommentType(constants.getValue(CommonConstants.ISSUE_COMMENT_TYPE)), true, "Issue Comment found on ticket", "Issue Comment does not found on ticket"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - validateIssueCommentBS", true);
        }
    }

    @Test(priority = 4, dependsOnMethods = "addNewComment", groups = {"SanityTest", "RegressionTest"})
    public void editComment() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Validate Edit comment as Backend Agent", "description");
            commonLib.info("Opening URL");
            String comment = "Adding updated comment using automation";
            pages.getViewTicket().openEditCommentBox();
            pages.getViewTicket().clearCommentBox();
            pages.getViewTicket().addComment(comment);
            pages.getViewTicket().clickAddButton();
            pages.getViewTicket().validateAddedComment(comment);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - editComment", true);
        }
    }

    @Test(priority = 5, dependsOnMethods = "agentQueueLogin", groups = {"SanityTest", "RegressionTest"})
    public void deleteLastAddedComment() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Validate Delete comment as Backend Agent", "description");
            commonLib.info("Opening URL");
            String comment = "Adding Comment to test Delete comment Flow " + LocalDateTime.now();
            pages.getViewTicket().addComment(comment);
            pages.getViewTicket().clickAddButton();
            pages.getViewTicket().validateAddedComment(comment);
            pages.getViewTicket().openDeleteComment();
            pages.getViewTicket().clickContinueButton();
            assertCheck.append(actions.assertEqual_boolean(pages.getViewTicket().isCommentDelete(comment), true, "Deleted comment NOT found on ticket", "Deleted comment found on ticket"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - deleteLastAddedComment", true);
        }
    }
}
