package tests.backendAgent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TicketStateDataBean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPOM;
import com.airtel.cs.pagerepository.pagemethods.ViewTicketPagePOM;
import com.airtel.cs.pagerepository.pagemethods.agentLoginPagePOM;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class BackendAgentAddComment extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBA){
            softAssert.fail("Terminate Execution as Backend Agent not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Backend Agent Queue Login Page")
    public void agentQueueLogin(Method method) {
        ExtentTestManager.startTest("Backend Agent Login into Queue", "Backend Agent Login into Queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openBackendAgentDashboard();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage());
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton());
        AgentLoginPagePOM.clickSelectQueue();
        AgentLoginPagePOM.selectAllQueue();
        AgentLoginPagePOM.clickSubmitBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"), "Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Backend agent add new comment on Ticket", dataProvider = "ticketState", dataProviderClass = DataProviders.class,dependsOnMethods = "agentQueueLogin")
    public void addNewComment(Method method, TicketStateDataBean ticketState) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Backend Agent add new comment on ticket", "Backend Agent add new comment on ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
//        ticketListPage.writeTicketId(ticketId);
//        ticketListPage.clickedSearchBtn();
//        Thread.sleep(20000); // Add comment on Particular Ticket
        String ticketId = ticketListPage.getTicketIdvalue();
        String comment = "Backend Agent added comment on ticket using automation";
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(), "Verify the searched Ticket fetched Successfully");
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentQueueLogin", description = "Validate issue comment as Backend Agent")
    public void validateIssueCommentBS(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Validate issue comment as Backend Agent", "Validate issue comment [Backend Agent]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(viewTicket.validateCommentType(config.getProperty("issueComment")), "Issue Comment does not found on ticket");
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "addNewComment", description = "Validate Edit comment as Backend Agent")
    public void editComment() throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Validate Edit comment as Backend Agent", "Validate Edit comment [Backend Agent]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String comment = "Adding updated comment using automation";
        viewTicket.openEditCommentBox();
        viewTicket.clearCommentBox();
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        softAssert.assertAll();
    }

    @Test(priority = 5,dependsOnMethods = "agentQueueLogin", description = "Validate Delete comment as Backend Agent")
    public void deleteLastAddedComment() throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Validate Delete comment as Backend Agent", "Validate Delete comment [Backend Agent]");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String comment = "Adding Comment to test Delete comment Flow " + LocalDateTime.now();
        viewTicket.addComment(comment);
        viewTicket.clickAddButton();
        viewTicket.waitTillLoaderGetsRemoved();
        viewTicket.validateAddedComment(comment);
        viewTicket.openDeleteComment();
        viewTicket.clickContinueButton();
        viewTicket.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(viewTicket.isCommentDelete(comment), "Deleted comment found on ticket");
        softAssert.assertAll();
    }


}
