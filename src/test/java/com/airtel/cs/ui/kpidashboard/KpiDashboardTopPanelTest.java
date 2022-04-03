package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.PassUtils;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.login.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class KpiDashboardTopPanelTest extends Driver {


    ObjectMapper mapper = new ObjectMapper();


    @Test(priority = 1, groups = {"RegressionTest", "ProdTest"})
    public void dashboardTopPanel() {
        try {
            selUtils.addTestcaseDescription("Verify Dashboard Top Panel", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.getKpiDashboardTopPanel().hoverOnKpiDashboardIcon();
            pages.getKpiDashboardTopPanel().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isRefreshIcondVisible(), true, "Refresh Icon is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isLastRefreshTimeVisible(), true, "Last Refresh Time is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketOverviewLableVisible(), true, "Open Ticket Overview is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketsBeyondSLALableVisible(), true, "Open Tickets Beyond SLA Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isOpenTicketsUnderSLALableVisible(), true, "Open Tickets Under SLA Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingWithin15MinsLableVisible(), true, "Breaching With in 15 Mins Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingWithin15To60MinsLableVisible(), true, "Breaching With in 15 To 60 Mins Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isBreachingGreaterThan60MinsLableVisible(), true, "Breaching With in 60 Mins Label is visible"));



        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 2, groups = {"RegressionTest", "ProdTest"})
    public void dashboardTopPanelDetailsPage() {
        try {
            selUtils.addTestcaseDescription(" Dashboard Top Panel Details Page", "description");

            pages.getKpiDashboardTopPanel().clickOnOpenTicketsBeyondSLADetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDeatilsOpenTicketsBeyondSLALableVisible(), true, "Details Open Tickets Beyond SLA Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLableVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
            @Test(priority = 3, groups = {"RegressionTest", "ProdTest"})
            public void dashboardOpenTicketsUnderSLADetails() {
                try {
                    selUtils.addTestcaseDescription(" Open Tickets Under SLA Details", "description");

            pages.getKpiDashboardTopPanel().clickOnOpenTicketsUnderSLALDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsOpenTicketsUnderSLALableVisible(), true, "Details Open Tickets Under SLA Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLableVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
                } catch (Exception e) {

                }
                actions.assertAllFoundFailedAssert(assertCheck);
            }

    @Test(priority = 4, groups = {"RegressionTest", "ProdTest"})
    public void dashboardBreachingWithin15MinsDetails() {
        try {
            selUtils.addTestcaseDescription(" Breaching Within 15 Mins Details", "description");
            pages.getKpiDashboardTopPanel().clickOnBreachingWithin15MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingWithin15MinsLableVisible(), true, "Details Breaching With in 15 Mins Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLableVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();
        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 5, groups = {"RegressionTest", "ProdTest"})
    public void dashboardBreachingWithin15To60MinsDetails() {
        try {
            selUtils.addTestcaseDescription(" Breaching Within 15 To 60 Mins Details", "description");

            pages.getKpiDashboardTopPanel().clickOnBreachingWithin15To60MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingWithin15To60MinsLableVisible(), true, "Details Breaching Within 15 To 60 Mins Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLableVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();

        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 6, groups = {"RegressionTest", "ProdTest"})
    public void dashboardBreachingGreaterThan60MinsDetails() {
        try {
            selUtils.addTestcaseDescription(" Breaching Greater Than 60 Mins Details", "description");
            pages.getKpiDashboardTopPanel().clickOnBreachingGreaterThan60MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isDetailsBreachingGreaterThan60MinsLableVisible(), true, "Details Breaching Greater Than 60 Mins Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isTicketIdLableVisible(), true, "Ticket ID Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isPriorityLableVisible(), true, "  Priority Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isStateLableVisible(), true, "State Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreationDateLableVisible(), true, "Creation date Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isCreatedByLableVisible(), true, "Created By Label is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isQueueLableVisible(), true, "Queue Label  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getKpiDashboardTopPanel().isSourceLableVisible(), true, "Source Label is visible"));
            pages.getKpiDashboardTopPanel().clickOnBackIcon();




        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}