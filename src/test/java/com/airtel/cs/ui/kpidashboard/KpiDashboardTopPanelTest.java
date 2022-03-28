package com.airtel.cs.ui.kpidashboard;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.PassUtils;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.login.Login;
import com.airtel.cs.pagerepository.pagemethods.KpiDashboardTopPanel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class KpiDashboardTopPanelTest extends Driver {


    ObjectMapper mapper = new ObjectMapper();

    @Test(priority = 1, groups = {"RegressionTest", "ProdTest"})
    public void testLoginAPIWithBetaUser() {
        try {
            selUtils.addTestcaseDescription("Validate the Login API with Beta user,Hit the Login API -/auth/api/user-mngmnt/v2/login with valid headers and credentials,Validating Success Message from response", "description");
            final String loginAUUID = constants.getValue(CommonConstants.ADVISOR_USER_ROLE_AUUID);
            Login Req = Login.loginBody(loginAUUID, PassUtils.decodePassword(constants.getValue(CommonConstants.ADVISOR_USER_ROLE_PASSWORD)));
            map.clear();
            pages.getLoginPage().setCsAndDownstreamApiHeader();
            String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
            commonLib.info("Validating login api with user : " + loginAUUID);
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqualStringType(message, "User authenticated successfully", "User authenticated successfully", message, false));
        } catch (Exception e) {
            continueExecutionAPI = false;
            commonLib.fail("Exception in Method :- testLoginAPI " + e.fillInStackTrace(), false);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"RegressionTest", "ProdTest"})
    public void dashboardTopPanel() {
        try {
            selUtils.addTestcaseDescription("Verify Dashboard Top Panel", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDashboardIconVisible(), true, "Dashboard Icon is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isRefreshIcondVisible(), true, "Refresh Icon is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isLastRefreshTimeVisible(), true, "Last Refresh Time is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isOpenTicketOverviewLableVisible(), true, "Open Ticket Overview is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isOpenTicketsBeyondSLALableVisible(), true, "Open Tickets Beyond SLA Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isOpenTicketsUnderSLALableVisible(), true, "Open Tickets Under SLA Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isBreachingWithin15MinsLableVisible(), true, "Breaching With in 15 Mins Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isBreachingWithin15To60MinsLableVisible(), true, "Breaching With in 15 To 60 Mins Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDetailsBreachingGreaterThan60MinsLableVisible(), true, "Breaching With in 60 Mins Lable is visible"));



        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 3, groups = {"RegressionTest", "ProdTest"})
    public void dashboardTopPanelDetailsPage() {
        try {
            selUtils.addTestcaseDescription(" Dashboard Top Panel Details Page", "description");

            pages.KpiDashboardTopPanelPage().clickOnOpenTicketsBeyondSLADetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDeatilsOpenTicketsBeyondSLALableVisible(), true, "Deatils Open Tickets Beyond SLA Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isTicketIdLableVisible(), true, "Ticket ID Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isPriorityLableVisible(), true, "  Priority Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isStateLableVisible(), true, "State Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreationDateLableVisible(), true, "Creation date Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreatedByLableVisible(), true, "Created By Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isQueueLableVisible(), true, "Queue Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isSourceLableVisible(), true, "Source Lable is visible"));


            pages.KpiDashboardTopPanelPage().clickOnOpenTicketsUnderSLALDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDetailsOpenTicketsUnderSLALableVisible(), true, "Details Open Tickets Under SLA Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isTicketIdLableVisible(), true, "Ticket ID Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isPriorityLableVisible(), true, "  Priority Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isStateLableVisible(), true, "State Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreationDateLableVisible(), true, "Creation date Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreatedByLableVisible(), true, "Created By Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isQueueLableVisible(), true, "Queue Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isSourceLableVisible(), true, "Source Lable is visible"));


            pages.KpiDashboardTopPanelPage().clickOnBreachingWithin15MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDetailsBreachingWithin15MinsLableVisible(), true, "Details Breaching With in 15 Mins Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isTicketIdLableVisible(), true, "Ticket ID Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isPriorityLableVisible(), true, "  Priority Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isStateLableVisible(), true, "State Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreationDateLableVisible(), true, "Creation date Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreatedByLableVisible(), true, "Created By Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isQueueLableVisible(), true, "Queue Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isSourceLableVisible(), true, "Source Lable is visible"));


            pages.KpiDashboardTopPanelPage().clickOnBreachingWithin15To60MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDetailsBreachingWithin15To60MinsLableVisible(), true, "Details Breaching Within 15 To 60 Mins Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isTicketIdLableVisible(), true, "Ticket ID Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isPriorityLableVisible(), true, "  Priority Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isStateLableVisible(), true, "State Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreationDateLableVisible(), true, "Creation date Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreatedByLableVisible(), true, "Created By Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isQueueLableVisible(), true, "Queue Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isSourceLableVisible(), true, "Source Lable is visible"));


            pages.KpiDashboardTopPanelPage().clickOnBreachingGreaterThan60MinsDetailsIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isDetailsBreachingGreaterThan60MinsLableVisible(), true, "Details Breaching Greater Than 60 Mins Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isTicketIdLableVisible(), true, "Ticket ID Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isPriorityLableVisible(), true, "  Priority Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isStateLableVisible(), true, "State Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreationDateLableVisible(), true, "Creation date Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isCreatedByLableVisible(), true, "Created By Lable is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isQueueLableVisible(), true, "Queue Lable  is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isSourceLableVisible(), true, "Source Lable is visible"));




        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}