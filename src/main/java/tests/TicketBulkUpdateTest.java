package tests;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.TicketBulkUpdatePOM;
import pages.agentLoginPagePOM;

import java.awt.*;
import java.lang.reflect.Method;

public class TicketBulkUpdateTest extends BaseTest {

    @Test(priority = 1, description = "Open Ticket Bulk Update Dashboard")
    public void openTicketBulkUpdate(Method method) throws InterruptedException, AWTException {
        ExtentTestManager.startTest("Open Ticket Bulk Update Dashboard", "Open Ticket Bulk Update Dashboard");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.waitTillLoaderGetsRemoved();
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        TicketBulkUpdatePOM ticketBulkUpdatePOM = sideMenu.openTicketBulkUpdateDashboard();
        SoftAssert softAssert = new SoftAssert();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.fileDownload();
        Thread.sleep(2000);
        ticketBulkUpdatePOM.addFile();
        ticketBulkUpdatePOM.waitTillLoaderGetsRemoved();
        ticketBulkUpdatePOM.getErrorMessage();
        softAssert.assertAll();
    }
}
