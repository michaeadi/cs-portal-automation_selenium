package tests;

import Utils.DataProviders.DataProvider;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class FrontendAgentTicketTest extends BaseTest {

    @DataProvider.User(UserType = "ALL")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProvider.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Ticket Meta Data for Frontend Agent ", dataProvider = "singleTicketId", dataProviderClass = DataProvider.class)
    public void validateTicket(nftrDataBeans Data) throws InterruptedException, IOException {
        ExtentTestManager.startTest("Validate Ticket Meta Data for Frontend Agent :" + Data.getTicketNumber(), "Validate Ticket Meta Data for Frontend Agent : " + Data.getTicketNumber());
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        FrontendTicketHistory ticketHistory=viewHistory.clickOnTicketHistory();
        ticketHistory.waitTillLoaderGetsRemoved();
        ticketHistory.writeTicketId(Data.getTicketNumber());
        ticketHistory.clickSearchBtn();
        ticketHistory.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        softAssert.assertEquals(ticketHistory.getTicketId(1),Data.getTicketNumber(),"Ticket Id does not same as search ticket id.");
        softAssert.assertEquals(ticketHistory.getTicketPriority(1),Data.getPriority(),"Ticket priority not same as rule defined");
        softAssert.assertEquals(ticketHistory.getTicketQueue(1),Data.getAssignmentQueue(),"Ticket Queue not same as rule defined");
        softAssert.assertAll();
    }
}
