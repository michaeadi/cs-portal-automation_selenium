package com.airtel.cs.ui.ticketsourceapp;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TransferQueueDataBean;
import com.airtel.cs.driver.Driver;
import com.sun.istack.NotNull;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TicketSourceAppTest extends Driver {

    private static String customerNumber = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testSourceTitleOpenTicketListing() {
        selUtils.addTestcaseDescription("Validation Source App is visible under Supervisor Ticket Listing for OPEN Ticket", "description");
        try {
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceTitleOpenTicketListing " + e.getMessage(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppInFilterOpenTicket() {
        boolean isFilterOpeneed = false;
        try {
            selUtils.addTestcaseDescription("Validate Source App from Filters under open type tickets", "description");
            pages.getSupervisorTicketList().clickFilter();
            isFilterOpeneed = true;
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), false, "Filter Button is NOT Enabled", "Filter Button is already Enabled"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSourceFilterPresent(), true, "Filter By Source is available", "Filter By Source is NOT available"));
            pages.getFilterTabPage().selectSourceFilterValue();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            if (isFilterOpeneed)
                pages.getFilterTabPage().clickCloseFilter();
            commonLib.fail("Caught exception in Testcase - testSourceAppInFilterOpenTicket " + e.getMessage(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppOpenTicketDetailsPage() {
        try {
            selUtils.addTestcaseDescription("Validate Source App is visible under Supervisor Open Ticket Details Page", "description");
            pages.getSupervisorTicketList().clickToOpenTicketFromDashboard();
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getSupervisorTicketList().checkSourceTitleDetailPage(), "Source Title Is visible under Ticket Details Page", "Source Title Is Blank under Ticket Details Page"));
            pages.getSupervisorTicketList().goBackToTicketListing();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppOpenTicketDetailsPage " + e.getMessage(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"}, dataProvider = "TransferQueue", dataProviderClass = DataProviders.class, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppTransferTicketToQueue(@NotNull TransferQueueDataBean data) {
        selUtils.addTestcaseDescription("Validate Source App while doing Transfer To Queue", "description");
        try {
            pages.getSupervisorTicketList().clickCheckbox();
            pages.getSupervisorTicketList().clickTransfertoQueue();
            assertCheck.append(actions.assertEqual_boolean(pages.getTransferToQueue().validatePageTitle(), true, "Transfer Ticket To Queue Page Title Matched", "Transfer Ticket To Queue Page Title NOT Matched"));
            try {
                pages.getTransferToQueue().clickTransferQueue(data.getToQueue());
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
                pages.getSupervisorTicketList().clickCancelBtn();
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to validate Source App while doing Transfer To Queue: " + e.getMessage(), true);
                pages.getTransferToQueue().clickCloseTab();

            }
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppTransferTicketToQueue " + e.getMessage(), true);
        }

    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceTitleClosedTicketListing() {
        try {
            selUtils.addTestcaseDescription("Validation Source App is visible under Supervisor Ticket Listing for CLOSED Ticket", "description");
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceTitleClosedTicketListing " + e.getMessage(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppInFilterCloseTicket() {
        try {
            selUtils.addTestcaseDescription("Validate Source App from Filters under Close Type Ticket", "description");
            pages.getSupervisorTicketList().clickFilter();
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isApplyFilterBtnEnabled(), false, "Filter Button is NOT Enabled", "Filter Button is already Enabled"));
            assertCheck.append(actions.assertEqual_boolean(pages.getFilterTabPage().isSourceFilterPresent(), true, "Filter By Source is available", "Filter By Source is NOT available"));
            pages.getFilterTabPage().selectSourceFilterValue();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getFilterTabPage().clickCloseFilter();
            commonLib.fail("Caught exception in Testcase - testSourceAppInFilterCloseTicket " + e.getMessage(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppClosedTicketDetailsPage() {
        try {
            selUtils.addTestcaseDescription("Validate Source App is visible under Supervisor Close Ticket Details Page", "description");
            pages.getSupervisorTicketList().clickToOpenTicketFromDashboard();
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getSupervisorTicketList().checkSourceTitleDetailPage(), "Source Title is visible under Ticket Details Page", "Source Title Is Blank under Ticket Details Page"));
            pages.getSupervisorTicketList().goBackToTicketListing();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppClosedTicketDetailsPage " + e.getMessage(), true);
        }
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppInteractionHistory() {
        selUtils.addTestcaseDescription("Validate Source App is visible under view history and then to Interaction tab", "description");
        try {
            assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isSideMenuVisible(), true, "Side Menu Visible", "Side Menu Not Visible"));
            pages.getSideMenuPage().clickOnSideMenu();
            assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isCustomerServicesVisible(), true, "Customer Service Visible", "Customer Service Not Visible"));
            pages.getSideMenuPage().openCustomerInteractionPage();
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            pages.getCustomerProfilePage().goToViewHistory();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testSourceAppInteractionHistory " + e.getMessage(), true);
        }
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppInteractionHistoryDetails() {
        try {
            selUtils.addTestcaseDescription("Validate Source App under NFTR detail page", "description");
            pages.getViewHistory().clickOnTicketIcon();
            assertCheck.append(actions.assertEqual_boolean(pages.getViewHistory().isSourceAppVisible(), true, "Source App is visible under NFTR Details Page", "Source App is visible under NFTR Details Page"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getViewHistory().getSourceText(), "Source App is visible under NFTR Details Page", "Source App is NOT visible under NFTR Details Page"));
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            commonLib.fail("Caught exception in Testcase - testSourceAppInteractionHistoryDetails " + e.getMessage(), true);
        }
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
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

    @Test(priority = 11, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppTicketHistoryDetail() {
        try {
            selUtils.addTestcaseDescription("Validate Source App under view history and then to ticket history detail page", "description");
            pages.getFrontendTicketHistoryPage().clickToOpenTicketTicketHistory();
            assertCheck.append(actions.assertEqual_boolean(pages.getViewHistory().isSourceAppVisible(), true, "Source App is visible under NFTR Details Page", "Source App is visible under NFTR Details Page"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getViewHistory().getSourceText(), "Source App is visible under NFTR Details Page", "Source App is NOT visible under NFTR Details Page"));
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getViewHistory().closeInteractionHistoryDetailPage();
            commonLib.fail("Caught exception in Testcase - testSourceAppTicketHistoryDetail " + e.getMessage(), true);
        }
    }

    @Test(priority = 12, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"testSourceTitleOpenTicketListing"})
    public void testSourceAppBulkUpdate() {
        boolean isFilterOpened = false;
        try {
            selUtils.addTestcaseDescription("Validate Source App under Bulk Update Tab", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openTicketBulkUpdateDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getTicketBulkUpdate().isTicketBulkUpdate(), true, "Ticket Bulk Update Page Opened Successfully", "Ticket Bulk Update page does not open."));
            pages.getTicketBulkUpdate().clickSelectFilter();
            pages.getFilterTabPage().clickLast30DaysFilter();
            pages.getFilterTabPage().clickApplyFilter();
            assertCheck.append(actions.assertEqual_boolean(pages.getTicketBulkUpdate().isSourceTitleVisible(), true, "Source Title is visible on Ticket Row Listing Page", "Source Title is NOT visible on Ticket Row Listing Page"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().checkSourceTitleListingPage(), true, "Source Title Text is not Blank and is - " + pages.getSupervisorTicketList().checkSourceTitleListingPage(), "Source Title Text is Blank"));
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openSupervisorDashboard();
            pages.getTicketBulkUpdate().clickPopUpContinueBtn();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException e) {
            if (isFilterOpened)
                pages.getTicketBulkUpdate().clickClearFilter();
            commonLib.fail("Caught exception in Testcase - testSourceAppBulkUpdate " + e.getMessage(), true);
        }
    }
}
