package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.driver.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;


public class KpiDashboardTopPanelTest extends Driver {


    ObjectMapper mapper = new ObjectMapper();


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void dashboardTopPanel() {
        try {
            selUtils.addTestcaseDescription("Verify Dashboard Top Panel", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isRefreshIconVisible(), true, "Refresh Icon is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isLastRefreshTimeVisible(), true, "Last Refresh Time is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketOverviewLabelVisible(), true, "Open Ticket Overview is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketsBeyondSLALabelVisible(), true, "Open Tickets Beyond SLA Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketsUnderSLALabelVisible(), true, "Open Tickets Under SLA Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingWithin15MinsLabelVisible(), true, "Breaching With in 15 Mins Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingWithin15To60MinsLabelVisible(), true, "Breaching With in 15 To 60 Mins Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingGreaterThan60MinsLabelVisible(), true, "Breaching With in 60 Mins Label is visible"));
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardTopPanel" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void dashboardTopPanelDetailsPage() {
        try {
            selUtils.addTestcaseDescription(" Dashboard Top Panel Details Page", "description");

            pages.getKpiDashboardTopPanel().clickOnOpenTicketsBeyondSLADetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsOpenTicketsBeyondSLALabelVisible(), true, "Details Open Tickets Beyond SLA Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardTopPanelDetailsPage" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void dashboardOpenTicketsUnderSLADetails() {
        try {
            selUtils.addTestcaseDescription(" Open Tickets Under SLA Details", "description");

            pages.getKpiDashboardTopPanel().clickOnOpenTicketsUnderSLALDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsOpenTicketsUnderSLALabelVisible(), true, "Details Open Tickets Under SLA Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardOpenTicketsUnderSLADetails" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void dashboardBreachingWithin15MinsDetails() {
        try {
            selUtils.addTestcaseDescription(" Breaching Within 15 Mins Details", "description");
            pages.getKpiDashboardTopPanel().clickOnBreachingWithin15MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingWithin15MinsLabelVisible(), true, "Details Breaching With in 15 Mins Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardBreachingWithin15MinsDetails" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void dashboardBreachingWithin15To60MinsDetails() {
        try {
            selUtils.addTestcaseDescription(" Breaching Within 15 To 60 Mins Details", "description");

            pages.getKpiDashboardTopPanel().clickOnBreachingWithin15To60MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingWithin15To60MinsLabelVisible(), true, "Details Breaching Within 15 To 60 Mins Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardBreachingWithin15To60MinsDetails" + e.fillInStackTrace(), true);

        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void dashboardBreachingGreaterThan60MinsDetails() {
        try {
            selUtils.addTestcaseDescription(" Breaching Greater Than 60 Mins Details", "description");
            pages.getKpiDashboardTopPanel().clickOnBreachingGreaterThan60MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingGreaterThan60MinsLabelVisible(), true, "Details Breaching Greater Than 60 Mins Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLabelVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
            actions.assertAllFoundFailedAssert(assertCheck);


        } catch (Exception e) {
            commonLib.fail("Exception in method - dashboardBreachingGreaterThan60MinsDetails" + e.fillInStackTrace(), true);

        }
    }
}