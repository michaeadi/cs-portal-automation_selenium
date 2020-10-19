package tests;

import Utils.DataProviders.DataProviders;
import Utils.ExtentReports.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.TicketBulkUpdatePOM;
import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class TicketBulkUpdateTest extends BaseTest {

    @Test(priority = 1, description = "Open Ticket Bulk Update Dashboard")
    public void openTicketBulkUpdate(Method method) {
        ExtentTestManager.startTest("Open Ticket Bulk Update Dashboard", "Open Ticket Bulk Update Dashboard");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        TicketBulkUpdatePOM ticketBulkUpdatePOM = sideMenu.openTicketBulkUpdateDashboard();
        SoftAssert softAssert = new SoftAssert();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.isTicketBulkUpdate();

        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Check user able to upload ticket id from excel")
    public void uploadTicketFromExcelTest(Method method) throws IOException, InterruptedException, AWTException {
        ExtentTestManager.startTest("Check user able to upload ticket id from excel", "Check user able to upload ticket id from excel");
        TicketBulkUpdatePOM ticketBulkUpdatePOM = new TicketBulkUpdatePOM(driver);
        DataProviders data=new DataProviders();
        SoftAssert softAssert=new SoftAssert();
        Assert.assertTrue(ticketBulkUpdatePOM.fileDownload(),"Ticket Upload Template does not download.Please check Excels/BulkUploadTemplate.xlsx downloaded");
        Assert.assertTrue(data.writeTicketNumberToExcel(),"No Ticket Found to write in Excel");
        ticketBulkUpdatePOM.addFile();
        List<String> uploadTickets=data.getTicketNumbers();
        List<String> addedTicket=ticketBulkUpdatePOM.getTicketList();

    }
}
