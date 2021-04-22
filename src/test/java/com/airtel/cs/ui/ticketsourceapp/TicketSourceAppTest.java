package com.airtel.cs.ui.ticketsourceapp;

import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.dataproviders.TransferQueueDataBean;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

public class TicketSourceAppTest extends PreRequisites {

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceTitleOpenTicketListing() {
        selUtils.addTestcaseDescription("Validation Source App is visible under Supervisor Ticket Listing for OPEN Ticket", "description");
        try {
            pages.getSideMenu().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceTitleOpenTicketListing " + e.getMessage(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppInFilterOpenTicket() {
        selUtils.addTestcaseDescription("Validate Source App from Filters under open type tickets", "description");
        pages.getSupervisorTicketList().clickFilter();
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), false, "Filter Button is NOT Enabled", "Filter Button is already Enabled"));
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSourceFilterPresent(), true, "Filter By Source is available", "Filter By Source is NOT available"));
        try {
            pages.getFilterTabPage().selectSourceFilterValue();
            actions.assertAllFoundFailedAssert(assertCheck);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.fail("Caught exception in Testcase - testSourceAppInFilterOpenTicket " + e.getMessage(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppOpenTicketDetailsPage() {
        selUtils.addTestcaseDescription("Validate Source App is visible under Supervisor Open Ticket Details Page", "description");
        try {
            pages.getSupervisorTicketList().clickToOpenTicketFromDashboard();
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getSupervisorTicketList().checkSourceTitleDetailPage(), "Source Title Is NOT Blank under Ticket Details Page", "Source Title Is Blank under Ticket Details Page"));
            pages.getSupervisorTicketList().goBackToTicketListing();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppOpenTicketDetailsPage " + e.getMessage(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "TransferQueue", dataProviderClass = DataProviders.class)
    public void testSourceAppTransferTicketToQueue(@NotNull TransferQueueDataBean data) {
        selUtils.addTestcaseDescription("Validate Source App while doing Transfer To Queue", "description");
        try {
            pages.getSupervisorTicketList().clickCheckbox();
            pages.getSupervisorTicketList().clickTransfertoQueue();
            assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer Ticket To Queue Page Title Matched", "Transfer Ticket To Queue Page Title NOT Matched"));
            pages.getTransferToQueue().clickTransferQueue(data.getToQueue());
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            pages.getSupervisorTicketList().clickCancelBtn();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppTransferTicketToQueue " + e.getMessage(), true);
        }

    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceTitleClosedTicketListing() {
        selUtils.addTestcaseDescription("Validation Source App is visible under Supervisor Ticket Listing for CLOSED Ticket", "description");
        try {
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceTitleClosedTicketListing " + e.getMessage(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppInFilterCloseTicket() {
        selUtils.addTestcaseDescription("Validate Source App from Filters under Close Type Ticket", "description");
        pages.getSupervisorTicketList().clickFilter();
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), false, "Filter Button is NOT Enabled", "Filter Button is already Enabled"));
        assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSourceFilterPresent(), true, "Filter By Source is available", "Filter By Source is NOT available"));
        try {
            pages.getFilterTabPage().selectSourceFilterValue();
            actions.assertAllFoundFailedAssert(assertCheck);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.fail("Caught exception in Testcase - testSourceAppInFilterCloseTicket " + e.getMessage(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppClosedTicketDetailsPage() {
        selUtils.addTestcaseDescription("Validate Source App is visible under Supervisor Close Ticket Details Page", "description");
        try {
            pages.getSupervisorTicketList().clickToOpenTicketFromDashboard();
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getSupervisorTicketList().checkSourceTitleDetailPage(), "Source Title Is NOT Blank under Ticket Details Page", "Source Title Is Blank under Ticket Details Page"));
            pages.getSupervisorTicketList().goBackToTicketListing();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppClosedTicketDetailsPage " + e.getMessage(), true);
        }
    }

    @DataProviders.User(userType = "Beta")
    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void testSourceAppInteractionHistory(TestDatabean data) {
        selUtils.addTestcaseDescription("Validate Source App is visible under view history and then to Interaction tab", "description");
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPOM().isSideMenuVisible(), true, "Side Menu Visible", "Side Menu Not Visible"));
            pages.getSideMenuPOM().clickOnSideMenu();
            assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPOM().isCustomerServicesVisible(), true, "Customer Service Visible", "Customer Service Not Visible"));
            pages.getSideMenuPOM().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
            pages.getMsisdnSearchPage().clickOnSearch();
            pages.getCustomerProfilePage().goToViewHistory();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppInteractionHistory " + e.getMessage(), true);
        }
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppInteractionHistoryDetails() {
        selUtils.addTestcaseDescription("Validate Source App under NFTR detail page", "description");
        pages.getViewHistory().clickOnTicketIcon();
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getViewHistory().isSourceAppVisible(), true, "Source App is visible under NFTR Details Page", "Source App is visible under NFTR Details Page"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getViewHistory().getSourceText(), "Source App is visible under NFTR Details Page", "Source App is NOT visible under NFTR Details Page"));
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            commonLib.fail("Caught exception in Testcase - testSourceAppInteractionHistoryDetails " + e.getMessage(), true);
        }
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppTicketHistory() {
        try {
            selUtils.addTestcaseDescription("Validate Source App under view history and then to Ticket tab", "description");
            pages.getViewHistory().goToTicketHistoryTab();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppTicketHistory " + e.getMessage(), true);
        }
    }

    @Test(priority = 11, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppTicketHistoryDetail() {
        selUtils.addTestcaseDescription("Validate Source App under view history and then to ticket history detail page", "description");
        pages.getFrontendTicketHistoryPage().clickToOpenTicketTicketHistory();
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getViewHistory().isSourceAppVisible(), true, "Source App is visible under NFTR Details Page", "Source App is visible under NFTR Details Page"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getViewHistory().getSourceText(), "Source App is visible under NFTR Details Page", "Source App is NOT visible under NFTR Details Page"));
            pages.getViewHistory().closeInteractionHistoryDetailPage();
        } catch (Exception e) {
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            commonLib.fail("Caught exception in Testcase - testSourceAppTicketHistoryDetail " + e.getMessage(), true);

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 12, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceAppBulkUpdate() {
        selUtils.addTestcaseDescription("Validate Source App under Bulk Update Tab", "description");
        try {
            pages.getSideMenu().clickOnSideMenu();
            pages.getSideMenu().openTicketBulkUpdateDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getTicketBulkUpdate().isTicketBulkUpdate(), true, "Ticket Bulk Update Page Opened Successfully", "Ticket Bulk Update page does not open."));
            pages.getTicketBulkUpdate().clickSelectFilter();
            pages.getFilterTabPage().clickLast30DaysFilter();
            pages.getFilterTabPage().clickApplyFilter();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppBulkUpdate " + e.getMessage(), true);
        }

    }
}
