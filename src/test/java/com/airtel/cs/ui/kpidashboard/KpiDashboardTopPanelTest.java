package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;


public class KpiDashboardTopPanelTest extends Driver {


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void dashboardTopPanel() {
        try {
            selUtils.addTestcaseDescription("Verify Dashboard Top Panel", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is Visible", "Dashboard Icon is NOT Visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isRefreshIconVisible(), true, "Refresh Icon is Visible", "Refresh Icon is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isLastRefreshTimeVisible(), true, "Last Refresh Time is Visible", "Last Refresh Time is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketOverviewLabelVisible(), true, "Open Ticket Overview is Visible", "Open Ticket Overview is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketsBeyondSLALabelVisible(), true, "Open Tickets Beyond SLA Label is Visible", "Open Tickets Beyond SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketsUnderSLALabelVisible(), true, "Open Tickets Under SLA Label is Visible", "Open Tickets Under SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingWithin15MinsLabelVisible(), true, "Breaching With in 15 Mins Label is Visible", "Breaching With in 15 Mins Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingWithin15To60MinsLabelVisible(), true, "Breaching With in 15 To 60 Mins Label is Visible", "Breaching With in 15 To 60 Mins Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingGreaterThan60MinsLabelVisible(), true, "Breaching With in 60 Mins Label is Visible", "Breaching With in 60 Mins Label is NOT Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardTopPanel" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "dashboardTopPanel")
    public void openTicketBeyondSLADetailsPage() {
        try {
            selUtils.addTestcaseDescription(" Dashboard Top Panel Details Page", "description");
            pages.getKpiDashboardTopPanel().clickOnOpenTicketsBeyondSLADetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsOpenTicketsBeyondSLALabelVisible(), true, "Details Open Tickets Beyond SLA Label is Visible", "Details Open Tickets Beyond SLA Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLabelVisible(), true, "Ticket ID Label is Visible", "Ticket ID Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLabelVisible(), true, "Priority Label is Visible", "Priority Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLabelVisible(), true, "State Label is Visible", "State Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLabelVisible(), true, "Creation date Label is Visible", "Creation date Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLabelVisible(), true, "Created By Label is Visible", "Created By Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLabelVisible(), true, "Queue Label is Visible", "Queue Label is NOT Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is Visible", "Source Label is NOT Visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            commonLib.fail("Exception in method - openTicketBeyondSLADetailsPage" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "dashboardTopPanel")
    public void dashboardOpenTicketsUnderSLADetails() {
        try {
            selUtils.addTestcaseDescription(" Open Tickets Under SLA Details", "description");
            final boolean ticketFound = pages.getKpiDashboardTopPanel().clickOpenTicketsUnderSLAIcon();
            if (ticketFound) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsOpenTicketsUnderSLALabelVisible(), true, "Details Open Tickets Under SLA Label is Visible", "Details Open Tickets Under SLA Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLabelVisible(), true, "Ticket ID Label is Visible", "Ticket ID Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLabelVisible(), true, "Priority Label is Visible", "Priority Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLabelVisible(), true, "State Label is Visible", "State Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLabelVisible(), true, "Creation date Label is Visible", "Creation date Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLabelVisible(), true, "Created By Label is Visible", "Created By Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLabelVisible(), true, "Queue Label is Visible", "Queue Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is Visible", "Source Label is NOT Visible"));
                pages.getKpiDashboardTopPanel().clickOnBackIcon();
            } else {
                assertCheck.append(actions.assertEqualIntType(Integer.parseInt(pages.getKpiDashboardTopPanel().ticketCountUnderSLA()), 0, "Details Icon is not clickable as ticket count is 0 under SLA", "Detailed icon is not clickable and Ticket count is NOT 0 under SLA"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            commonLib.fail("Exception in method - dashboardOpenTicketsUnderSLADetails" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "dashboardTopPanel")
    public void breachingWithin15Min() {
        try {
            selUtils.addTestcaseDescription(" Breaching Within 15 Mins Details", "description");
            final boolean ticketFound = pages.getKpiDashboardTopPanel().clickBreachWithin15MinIcon();
            if (ticketFound) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingWithin15MinsLabelVisible(), true, "Details Breaching With in 15 Mins Label  is Visible", "Details Breaching With in 15 Mins Label  is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLabelVisible(), true, "Ticket ID Label is Visible", "Ticket ID Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLabelVisible(), true, "Priority Label is Visible", "Priority Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLabelVisible(), true, "State Label is Visible", "State Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLabelVisible(), true, "Creation date Label is Visible", "Creation date Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLabelVisible(), true, "Created By Label is Visible", "Created By Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLabelVisible(), true, "Queue Label is Visible", "Queue Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is Visible", "Source Label is NOT Visible"));
                pages.getKpiDashboardTopPanel().clickOnBackIcon();
            } else {
                assertCheck.append(actions.assertEqualIntType(Integer.parseInt(pages.getKpiDashboardTopPanel().ticketCountWithin15Min()), 0, "Details Icon is not clickable as ticket count is 0 within 15 min", "Detailed icon is not clickable and Ticket count is NOT 0 within 15 min"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            commonLib.fail("Exception in method - breachingWithin15Min" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "dashboardTopPanel")
    public void breachingWithin15To60Min() {
        try {
            selUtils.addTestcaseDescription(" Breaching Within 15 To 60 Mins Details", "description");
            final boolean ticketFound = pages.getKpiDashboardTopPanel().clickBreachWithin15To60MinIcon();
            if (ticketFound) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingWithin15To60MinsLabelVisible(), true, "Details Breaching Within 15 To 60 Mins Label  is Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLabelVisible(), true, "Ticket ID Label is Visible", "Ticket ID Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLabelVisible(), true, "Priority Label is Visible", "Priority Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLabelVisible(), true, "State Label is Visible", "State Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLabelVisible(), true, "Creation date Label is Visible", "Creation date Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLabelVisible(), true, "Created By Label is Visible", "Created By Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLabelVisible(), true, "Queue Label is Visible", "Queue Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is Visible", "Source Label is NOT Visible"));
                pages.getKpiDashboardTopPanel().clickOnBackIcon();
            } else {
                assertCheck.append(actions.assertEqualIntType(Integer.parseInt(pages.getKpiDashboardTopPanel().ticketCountWithin15and60Min()), 0, "Details Icon is not clickable as ticket count is 0 within 15 to 60 min", "Detailed icon is not clickable and Ticket count is NOT 0 within 15 to 60 min"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            commonLib.fail("Exception in method - breachingWithin15To60Min" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "dashboardTopPanel")
    public void breachingMoreThan60Min() {
        try {
            selUtils.addTestcaseDescription(" Breaching Greater Than 60 Mins Details", "description");
            final boolean ticketFound = pages.getKpiDashboardTopPanel().clickBreachingMoreThan60MinIcon();
            if (ticketFound) {
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingMoreThan60MinsLabelVisible(), true, "Details Breaching Greater Than 60 Mins Label  is Visible", "Details Breaching Greater Than 60 Mins Label  is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLabelVisible(), true, "Ticket ID Label  is Visible", "Ticket ID Label  is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLabelVisible(), true, "Priority Label is Visible", "Priority Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLabelVisible(), true, "State Label is Visible", "State Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLabelVisible(), true, "Creation date Label is Visible", "Creation date Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLabelVisible(), true, "Created By Label is Visible", "Created By Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLabelVisible(), true, "Queue Label is Visible", "Queue Label is NOT Visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is Visible", "Source Label is NOT Visible"));
                pages.getKpiDashboardTopPanel().clickOnBackIcon();
            } else {
                assertCheck.append(actions.assertEqualIntType(Integer.parseInt(pages.getKpiDashboardTopPanel().ticketCountMoreThan60Min()), 0, "Details Icon is not clickable as ticket count is 0 more than 60 min", "Detailed icon is not clickable and Ticket count is NOT 0 more than 60 min"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            commonLib.fail("Exception in method - breachingMoreThan60Min" + e.fillInStackTrace(), true);

        }
    }
}