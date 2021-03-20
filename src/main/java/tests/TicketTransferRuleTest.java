package tests;

import POJO.LoginPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.TicketTransferRuleDataBean;
import Utils.ExtentReports.ExtentTestManager;
import Utils.PassUtils;
import Utils.UtilsMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static Utils.ExtentReports.ExtentTestManager.getTest;
import static Utils.ExtentReports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TicketTransferRuleTest extends BaseTest {

    @DataProviders.User(UserType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 0)
    public void loginAPI(TestDatabean Data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SoftAssert softAssert = new SoftAssert();
        LoginPOJO Req = LoginPOJO.loginBody(PassUtils.decodePassword(Data.getPassword()), Data.getLoginAUUID());

        map.clear();
        UtilsMethods.addHeaders("x-app-name", config.getProperty(Env + "-x-app-name"));
        UtilsMethods.addHeaders("x-service-id", config.getProperty(Env + "-x-service-id"));
        //map.add(new Header("x-bsy-bn", config.getProperty(Env + "-x-bsy-bn"))); //Comment this line this header removed from MG Opco.
        UtilsMethods.addHeaders("x-app-type", config.getProperty(Env + "-x-app-type"));
        UtilsMethods.addHeaders("x-client-id", config.getProperty(Env + "-x-client-id"));
        UtilsMethods.addHeaders("x-api-key", config.getProperty(Env + "-x-api-key"));
        UtilsMethods.addHeaders("x-login-module", config.getProperty(Env + "-x-login-module"));
        UtilsMethods.addHeaders("x-channel", config.getProperty(Env + "-x-channel"));
        UtilsMethods.addHeaders("x-app-version", config.getProperty(Env + "-x-app-version"));
        UtilsMethods.addHeaders("Opco", Opco);

        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        startTest("LOGIN API TEST ", "Logging in Using Login API for getting TOKEN with user : " + Data.getLoginAUUID());
        UtilsMethods.printInfoLog("Logging in Using Login API for getting TOKEN with user : " + Data.getLoginAUUID());
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        try {
            QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
            UtilsMethods.printInfoLog("Request Headers are  : " + queryable.getHeaders());
            Response response = request.post("/auth/api/user-mngmnt/v2/login");
            Token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", Token));
            UtilsMethods.printInfoLog("Request URL : " + queryable.getURI());
            UtilsMethods.printInfoLog("Response Body : " + response.asString());
            UtilsMethods.printInfoLog("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            if (response.jsonPath().getString("message").equalsIgnoreCase("Failed to authenticate user.")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Please Update Password As soon as possible if required.\nAPI Response Message: " + response.jsonPath().getString("message"));
            } else if (response.jsonPath().getString("message").toLowerCase().contains("something went wrong")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Login API Failed(Marked Build As Failed).\nAPI Response Message: " + response.jsonPath().getString("message"));
            } else if (!response.jsonPath().getString("message").equalsIgnoreCase("User authenticated successfully")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Please Check the API error Message and make changes if required.\nAPI Response Message: " + response.jsonPath().getString("message"));
            }
        } catch (Exception e) {
            continueExecutionAPI = false;
            softAssert.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Ticket Transfer Rule Test", dataProvider = "ticketTransferRule", dataProviderClass = DataProviders.class)
    public void ticketTransferRuleCheck(Method method, TicketTransferRuleDataBean ruleData) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Ticket Transfer Rule Test " + ruleData.getIssueCode(), "Ticket Transfer Rule Test " + ruleData.getIssueCode() + " to ticket state " + ruleData.getToQueue());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        String ticketId = null;
        FilterTabPOM filterTab = null;
        ticketListPage.waitTillLoaderGetsRemoved();
        try {
            filterTab = ticketListPage.clickFilter();
            filterTab.waitTillLoaderGetsRemoved();
            filterTab.applyFilterByCategoryCode(ruleData.getIssueCode());
            filterTab.clickQueueFilter();
            filterTab.selectQueueByName(ruleData.getFromQueue());
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            filterTab.waitTillLoaderGetsRemoved();
        } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            filterTab.clickCloseFilter();
            softAssert.fail("Not able to apply filter " + e.getMessage());
        }
        try {
            ticketId = ticketListPage.getTicketIdvalue();
        } catch (NoSuchElementException | TimeoutException e) {
            ticketListPage.resetFilter();
            Assert.fail("No Ticket Found with Selected Filter ", e.getCause());
        }
        ticketListPage.viewTicket();
        Assert.assertEquals(ticketId, viewTicket.getTicketId(), "Verify the searched Ticket fetched Successfully");
        String selectedState = viewTicket.selectState(ruleData.getTicketToState());
        if (!selectedState.equalsIgnoreCase("Required State not found")) {
            try {
                ticketListPage.waitTillLoaderGetsRemoved();
                ticketListPage.writeTicketId(ticketId);
                ticketListPage.clickSearchBtn();
                ticketListPage.waitTillLoaderGetsRemoved();
                softAssert.assertEquals(ticketListPage.getTicketIdvalue(), ticketId, "Search Ticket Does not Fetched Correctly");
                softAssert.assertEquals(ticketListPage.getStatevalue().toLowerCase().trim(), selectedState.toLowerCase().trim(), "Ticket Does not Updated to Selected State");
                softAssert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), ruleData.getToQueue().toLowerCase().trim(), "Ticket does not updated to correct ticket pool");
            } catch (TimeoutException | NoSuchElementException e) {
                softAssert.fail("Ticket has been transferred to Selected but not able search ticket."+e.fillInStackTrace());
            }
            ticketListPage.clearInputBox();
        } else {
            viewTicket.clickOutside();
            viewTicket.clickBackButton();
            softAssert.fail("Required State not found");
        }
        softAssert.assertAll();
    }
}
