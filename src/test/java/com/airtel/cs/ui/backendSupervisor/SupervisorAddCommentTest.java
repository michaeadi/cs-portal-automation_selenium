package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class SupervisorAddCommentTest extends Driver {

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
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", description = "Supervisor Add Comment on Ticket")
    public void addCommentOnTicket() throws InterruptedException {
        selUtils.addTestcaseDescription("Supervisor Add Comment on Ticket,Add new Comment[Backend Supervisor]", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().changeTicketTypeToOpen();
        try {
            String ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
            String comment = "Supervisor added comment on ticket using automation";
            try {
                pages.getSupervisorTicketList().viewTicket();
                pages.getViewTicket().waitTillLoaderGetsRemoved();
                Assert.assertEquals(ticketId, pages.getViewTicket().getTicketId(), "Verify the searched Ticket fetched Successfully");
                try {
                    pages.getViewTicket().addComment(comment);
                    pages.getViewTicket().clickAddButton();
                    pages.getViewTicket().waitTillLoaderGetsRemoved();
                    pages.getViewTicket().validateAddedComment(comment);
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to add comment on ticket :" + e.fillInStackTrace());
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Not able to view ticket properly: " + e.fillInStackTrace());
                pages.getViewTicket().clickBackButton();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("No Ticket Found in open state " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "addCommentOnTicket", description = "Validate issue comment as supervisor")
    public void validateIssueComment() {
        selUtils.addTestcaseDescription("Validate issue comment [Backend Supervisor]", "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getViewTicket().validateCommentType(config.getProperty("issueComment")), "Issue Comment does not found on ticket");
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "addCommentOnTicket", description = "Validate Edit comment as Backend Supervisor")
    public void editComment() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Edit comment as Backend Supervisor", "description");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
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

    @Test(priority = 5, dependsOnMethods = "addCommentOnTicket", description = "Validate Delete comment as Backend Supervisor")
    public void deleteLastAddedComment() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Delete comment as Backend Supervisor", "description");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
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
