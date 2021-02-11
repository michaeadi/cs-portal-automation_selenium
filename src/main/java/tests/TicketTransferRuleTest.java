package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TicketTransferRuleDataBean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;

public class TicketTransferRuleTest extends BaseTest {

    @Test(priority = 1, description = "Ticket Transfer Rule Test", dataProvider = "ticketTransferRule", dataProviderClass = DataProviders.class)
    public void ticketTransferRuleCheck(Method method, TicketTransferRuleDataBean ruleData) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Ticket Transfer Rule Test " + ruleData.getIssueCode(), "Ticket Transfer Rule Test " + ruleData.getIssueCode() + " to ticket state " + ruleData.getToQueue());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        FilterTabPOM filterTab = null;
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            filterTab = ticketListPage.clickFilter();
            filterTab.waitTillLoaderGetsRemoved();
            filterTab.applyFilterByCategoryCode(ruleData.getIssueCode());
            filterTab.clickQueueFilter();
            filterTab.selectQueueByName(ruleData.getFromQueue());
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            filterTab.waitTillLoaderGetsRemoved();
        } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            filterTab.clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        try {
            ticketId = ticketListPage.getTicketIdvalue();
        } catch (NoSuchElementException | TimeoutException e) {
            ticketListPage.resetFilter();
            Assert.fail("No Ticket Found with Selected Filter ", e.getCause());
        }
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(), "Verify the searched Ticket fetched Successfully");
        String selectedState = viewTicket.selectState(ruleData.getTicketToState());
        if (!selectedState.equalsIgnoreCase("Required State not found")) {
            try {
                ticketListPage.waitTillLoaderGetsRemoved();
                ticketListPage.writeTicketId(ticketId);
                ticketListPage.clickSearchBtn();
                ticketListPage.waitTillLoaderGetsRemoved();
                softAssert.assertEquals(ticketListPage.getTicketIdvalue(), ticketId, "Search Ticket Does not Fetched Correctly");
                softAssert.assertEquals(ticketListPage.getStatevalue().toLowerCase().trim(), selectedState.toLowerCase().trim(), "Ticket Does not Updated to Selected State");
                softAssert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), ruleData.getToQueue().toLowerCase().trim(), "Ticket does not updated to correct ticket pool");
            } catch (TimeoutException | NoSuchElementException e) {
                softAssert.fail("Ticket has been transferred to Selected but not able search ticket."+e.fillInStackTrace());
            }
            ticketListPage.clearInputBox();
        } else {
            viewTicket.clickOutside();
            viewTicket.clickBackButton();
            softAssert.fail("Required State not found");
        }
        softAssert.assertAll();
    }
}
