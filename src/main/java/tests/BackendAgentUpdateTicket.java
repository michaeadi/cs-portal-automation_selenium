package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TicketStateDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BackendAgentTicketListPOM;
import pages.ViewTicketPagePOM;

import java.lang.reflect.Method;

public class BackendAgentUpdateTicket extends BaseTest {

    @Test(priority = 1, description = "Backend Agent Update Ticket", dataProvider = "ticketState", dataProviderClass = DataProviders.class)
    public void updateTicket(Method method, TicketStateDataBean ticketState) throws InterruptedException {
        BackendAgentTicketListPOM ticketListPage = new BackendAgentTicketListPOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Backend Agent Update Ticket", "Backend Agent Update Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
//        ticketListPage.writeTicketId(ticketId);
//        ticketListPage.clickedSearchBtn();
//        Thread.sleep(20000); // Update Particular Ticket
        ticketListPage.waitTillLoaderGetsRemoved();
        String ticketId = ticketListPage.getTicketIdvalue();
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(), "Verify the searched Ticket fetched Successfully");
        String selectedState = viewTicket.selectState(ticketState.getTicketStateName());
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.writeTicketId(ticketId);
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketListPage.noTicketFound(), "Backend agent able to see closed ticket");
        softAssert.assertAll();
    }
}
