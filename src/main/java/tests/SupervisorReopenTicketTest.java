package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TicketStateDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;

public class SupervisorReopenTicketTest extends BaseTest {

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard(Method method) {
        ExtentTestManager.startTest("Open Supervisor Dashboard", "Open Supervisor Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "openSupervisorDashboard", dataProvider = "ReOpenState", description = "Supervisor Reopen Ticket", dataProviderClass = DataProviders.class)
    public void ReopenTicket(Method method, TicketStateDataBean reopen) throws InterruptedException {
        ExtentTestManager.startTest("Supervisor Reopen Ticket", "Reopen Ticket as Supervisor");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        ticketListPage.changeTicketTypeToClosed();
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            ticketId = ticketListPage.getTicketIdvalue();
            ticketListPage.clickCheckbox();
            try {
                softAssert.assertTrue(ticketListPage.isReopenBtn(), "Reopen Button Available");
                try {
                    ticketListPage.ClickReopenButton();
                    ticketListPage.addReopenComment("Reopen Comment Using Automation");
                    ticketListPage.submitReopenReq();
                    ticketListPage.waitTillLoaderGetsRemoved();
                    try {
                        ticketListPage.changeTicketTypeToOpen();
                        ticketListPage.waitTillLoaderGetsRemoved();
                        ticketListPage.writeTicketId(ticketId);
                        ticketListPage.clickSearchBtn();
                        ticketListPage.waitTillLoaderGetsRemoved();
                        Assert.assertEquals(ticketListPage.getStatevalue().toLowerCase().trim(), reopen.getTicketStateName().toLowerCase().trim(), "Ticket Does Not Reopen in Correct state");
                    } catch (NoSuchElementException | TimeoutException e) {
                        softAssert.fail("Ticket does not reopened successfully :"+e.fillInStackTrace());
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    softAssert.fail("Not able to add Re open comment " + e.fillInStackTrace());
                    ticketListPage.closedReopenBox();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                softAssert.fail("Reopen Ticket action can not complete due to following error " + e.fillInStackTrace());
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("No Ticket Found with closed State " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
