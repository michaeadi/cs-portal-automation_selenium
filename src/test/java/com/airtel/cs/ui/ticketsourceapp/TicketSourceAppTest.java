package com.airtel.cs.ui.ticketsourceapp;

import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.dataproviders.TransferQueueDataBean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import org.testng.annotations.Test;

public class TicketSourceAppTest extends PreRequisites {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceTitleOpenTicketListing() {
        ExtentTestManager.startTest("Validation Source App is visible under Supervisor Ticket Listing for OPEN Ticket", "Validation Source App is visible under Supervisor Ticket Listing for OPEN Ticket");
        pages.getSideMenu().openSupervisorDashboard();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppInFilterOpenTicket() {
        ExtentTestManager.startTest("Validate Source App from Filters under open type tickets", "Validate Source App from Filters under open type tickets");
        pages.getSupervisorTicketList().clickFilter();
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), false, "Filter Button is NOT Enabled", "Filter Button is already Enabled"));
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSourceFilterPresent(), true, "Filter By Source is available", "Filter By Source is NOT available"));
        try {
            pages.getFilterTabPage().selectSourceFilterValue();
            actions.assertAll_foundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.error("Not able to apply filter");
        }
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppOpenTicketDetailsPage() {
        ExtentTestManager.startTest("Validate Source App is visible under Supervisor Open Ticket Details Page", "Validate Source App is visible under Supervisor Open Ticket Details Page");
        pages.getSupervisorTicketList().clickToOpenTicketFromDashboard();
        assertCheck.append(actions.assertEqual_stringNotNull(pages.getSupervisorTicketList().checkSourceTitleDetailPage(), "Source Title Is NOT Blank under Ticket Details Page", "Source Title Is Blank under Ticket Details Page"));
        pages.getSupervisorTicketList().goBackToTicketListing();
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "TransferQueue", dataProviderClass = DataProviders.class)
    public void testSourceAppTransferTicketToQueue(TransferQueueDataBean data) {
        ExtentTestManager.startTest("Validate Source App while doing Transfer To Queue", "Validate Source App while doing Transfer To Queue");
        pages.getSupervisorTicketList().clickCheckbox();
        pages.getSupervisorTicketList().clickTransfertoQueue();
        assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer Ticket To Queue Page Title Matched", "Transfer Ticket To Queue Page Title NOT Matched"));
        pages.getTransferToQueue().clickTransferQueue(data.getToQueue());
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
        pages.getSupervisorTicketList().clickCancelBtn();
        actions.assertAll_foundFailedAssert(assertCheck);

    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceTitleClosedTicketListing() {
        ExtentTestManager.startTest("Validation Source App is visible under Supervisor Ticket Listing for CLOSED Ticket", "Validation Source App is visible under Supervisor Ticket Listing for CLOSED Ticket");
        pages.getSupervisorTicketList().changeTicketTypeToClosed();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppInFilterCloseTicket() {
        ExtentTestManager.startTest("Validate Source App from Filters under Close Type Ticket", "Validate Source App from Filters under Close Type Ticket");
        pages.getSupervisorTicketList().clickFilter();
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), false, "Filter Button is NOT Enabled", "Filter Button is already Enabled"));
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSourceFilterPresent(), true, "Filter By Source is available", "Filter By Source is NOT available"));
        try {
            pages.getFilterTabPage().selectSourceFilterValue();
            actions.assertAll_foundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.error("Not able to apply filter");
        }
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppClosedTicketDetailsPage() {
        ExtentTestManager.startTest("Validate Source App is visible under Supervisor Close Ticket Details Page", "Validate Source App is visible under Supervisor Close Ticket Details Page");
        pages.getSupervisorTicketList().clickToOpenTicketFromDashboard();
        assertCheck.append(actions.assertEqual_stringNotNull(pages.getSupervisorTicketList().checkSourceTitleDetailPage(), "Source Title Is NOT Blank under Ticket Details Page", "Source Title Is Blank under Ticket Details Page"));
        pages.getSupervisorTicketList().goBackToTicketListing();
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @DataProviders.User(UserType = "Beta")
    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void testSourceAppInteractionHistory(TestDatabean data) {
        ExtentTestManager.startTest("Validate Source App is visible under view history and then to Interaction tab", "");
        assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPOM().isSideMenuVisible(), true, "Side Menu Visible", "Side Menu Not Visible"));
        pages.getSideMenuPOM().clickOnSideMenu();
        assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPOM().isCustomerServicesVisible(), true, "Customer Service Visible", "Customer Service Not Visible"));
        pages.getSideMenuPOM().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        pages.getCustomerProfilePage().goToViewHistory();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppInteractionHistoryDetails() {
        ExtentTestManager.startTest("Validate Source App under NFTR detail page", "Validate Source App under NFTR detail page");
        pages.getViewHistory().clickOnTicketIcon();
        assertCheck.append(actions.assertEqual_boolean(pages.getViewHistory().isSourceAppVisible(), true, "Source App is visible under NFTR Details Page", "Source App is visible under NFTR Details Page"));
        assertCheck.append(actions.assertEqual_stringNotNull(pages.getViewHistory().getSourceText(), "Source App is visible under NFTR Details Page", "Source App is NOT visible under NFTR Details Page"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppTicketHistory() {
        ExtentTestManager.startTest("Validate Source App under view history and then to Ticket tab", "Validate Source App under view history and then to Ticket tab");
        pages.getViewHistory().goToTicketHistoryTab();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 11, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppTicketHistoryDetail() {
        ExtentTestManager.startTest("Validate Source App under view history and then to ticket history detail page", "Validate Source App under view history and then to ticket history detail page");
        pages.getFrontendTicketHistoryPage().clickToOpenTicketTicketHistory();
        assertCheck.append(actions.assertEqual_boolean(pages.getViewHistory().isSourceAppVisible(), true, "Source App is visible under NFTR Details Page", "Source App is visible under NFTR Details Page"));
        assertCheck.append(actions.assertEqual_stringNotNull(pages.getViewHistory().getSourceText(), "Source App is visible under NFTR Details Page", "Source App is NOT visible under NFTR Details Page"));
        actions.assertAll_foundFailedAssert(assertCheck);
    }

    @Test(priority = 12, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppBulkUpdate() {
        ExtentTestManager.startTest("Validate Source App under Bulk Update Tab", "Validate Source App under Bulk Update Tab");
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().openTicketBulkUpdateDashboard();
        assertCheck.append(actions.assertEqual_boolean(pages.getTicketBulkUpdate().isTicketBulkUpdate(), true, "Ticket Bulk Update Page Opened Successfully", "Ticket Bulk Update page does not open."));
        pages.getTicketBulkUpdate().clickSelectFilter();
        pages.getFilterTabPage().clickLast30DaysFilter();
        pages.getFilterTabPage().clickApplyFilter();
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
        assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
        actions.assertAll_foundFailedAssert(assertCheck);

    }
}
