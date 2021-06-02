package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;

public class BackendAgentAddCommentTest extends Driver {

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

    @Test(priority = 1, description = "Backend Agent Queue Login Page")
    public void agentQueueLogin() {
        selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openBackendAgentDashboard();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getAgentLoginPage().isQueueLoginPage());
        softAssert.assertTrue(pages.getAgentLoginPage().checkSubmitButton());
        pages.getAgentLoginPage().clickSelectQueue();
        pages.getAgentLoginPage().selectAllQueue();
        pages.getAgentLoginPage().clickSubmitBtn();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"), "Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Backend agent add new comment on Ticket", dependsOnMethods = "agentQueueLogin")
    public void addNewComment() throws InterruptedException {
        selUtils.addTestcaseDescription("Backend Agent add new comment on ticket", "description");
        SoftAssert softAssert = new SoftAssert();
        String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
        String comment = "Backend Agent added comment on ticket using automation";
        pages.getSupervisorTicketList().viewTicket();
        Assert.assertEquals(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully");
        pages.getViewTicket().addComment(comment);
        pages.getViewTicket().clickAddButton();
        pages.getViewTicket().waitTillLoaderGetsRemoved();
        pages.getViewTicket().validateAddedComment(comment);
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentQueueLogin", description = "Validate issue comment as Backend Agent")
    public void validateIssueCommentBS() {
        selUtils.addTestcaseDescription("Validate issue comment as Backend Agent", "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getViewTicket().validateCommentType(config.getProperty("issueComment")), "Issue Comment does not found on ticket");
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "addNewComment", description = "Validate Edit comment as Backend Agent")
    public void editComment() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Edit comment as Backend Agent", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String comment = "Adding updated comment using automation";
        pages.getViewTicket().openEditCommentBox();
        pages.getViewTicket().clearCommentBox();
        pages.getViewTicket().addComment(comment);
        pages.getViewTicket().clickAddButton();
        pages.getViewTicket().waitTillLoaderGetsRemoved();
        pages.getViewTicket().validateAddedComment(comment);
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = "agentQueueLogin", description = "Validate Delete comment as Backend Agent")
    public void deleteLastAddedComment() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Delete comment as Backend Agent", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String comment = "Adding Comment to test Delete comment Flow " + LocalDateTime.now();
        pages.getViewTicket().addComment(comment);
        pages.getViewTicket().clickAddButton();
        pages.getViewTicket().waitTillLoaderGetsRemoved();
        pages.getViewTicket().validateAddedComment(comment);
        pages.getViewTicket().openDeleteComment();
        pages.getViewTicket().clickContinueButton();
        pages.getViewTicket().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getViewTicket().isCommentDelete(comment), "Deleted comment found on ticket");
        softAssert.assertAll();
    }
}
