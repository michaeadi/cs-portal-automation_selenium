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

public class KpiDashboard6MonthsOverviewPageTest  extends Driver {


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
    public void dashboardServiceLevelTrend() {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboardTopPanelPage().isKpiDashboardIconVisible(), true, "Dashboard Icon is visible"));
            pages.KpiDashboardTopPanelPage().hoverOnKpiDashboardIcon();
            pages.KpiDashboardTopPanelPage().clickOnCsDashboardIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboard6MonthsOverviewPage().isServiceLevelTrendVisible(), true, "Service Level Trend "));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboard6MonthsOverviewPage().isConnectionTypeDropDownVisible(), true, "Connection Type Drop Down "));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboard6MonthsOverviewPage().isQueueTypeDropDownVisible(), true, "Queue Type Drop Down "));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboard6MonthsOverviewPage().isIssueTypeDropDownVisible(), true, "Issue Type Drop Down "));
            assertCheck.append(actions.assertEqualBoolean(pages.KpiDashboard6MonthsOverviewPage().isIssueSubTypeDropDownVisible(), true, "Issue Sub Type Drop Down "));

        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"RegressionTest", "ProdTest"})
    public void dashboardSlaPerformanceDetailsQueueWise
            () {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");

            pages.KpiDashboard6MonthsOverviewPage().clickOnServiceLevelTrendDetailsIcon();



        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"RegressionTest", "ProdTest"})
    public void dashboardSLAPerformanceDetailsIssueTypeWise

            () {
        try {
            selUtils.addTestcaseDescription("Service Level Trend Graph Details Widget", "description");

            pages.KpiDashboard6MonthsOverviewPage().clickOnServiceLevelTrendDetailsIcon();



        } catch (Exception e) {

        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
        }

