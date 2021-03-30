package tests.backendAgent;

import com.airtel.cs.commonutils.DataProviders.DataProviders;
import com.airtel.cs.commonutils.DataProviders.TicketStateDataBean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.ViewTicketPagePOM;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;

public class BackendAgentUpdateTicket extends BaseTest {

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBA){
            softAssert.fail("Terminate Execution as Backend Agent not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Backend Agent Update Ticket", dataProvider = "ticketState", dataProviderClass = DataProviders.class)
    public void updateTicket(Method method, TicketStateDataBean ticketState) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
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
