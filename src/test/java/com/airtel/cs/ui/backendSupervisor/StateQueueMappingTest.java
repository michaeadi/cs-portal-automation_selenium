package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.QueueStateDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.LoginPOJO;
import com.airtel.cs.pojo.ticketlist.QueueStates;
import com.airtel.cs.pojo.ticketlist.TicketPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class StateQueueMappingTest extends Driver {

    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void loginAPI(TestDatabean data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SoftAssert softAssert = new SoftAssert();
        LoginPOJO req = LoginPOJO.loginBody(PassUtils.decodePassword(data.getPassword()), data.getLoginAUUID());

        map.clear();
        UtilsMethods.addHeaders("x-app-name", config.getProperty(evnName + "-x-app-name"));
        UtilsMethods.addHeaders("x-service-id", config.getProperty(evnName + "-x-service-id"));
        //map.add(new Header("x-bsy-bn", config.getProperty(Env + "-x-bsy-bn"))); //Comment this line this header removed from MG Opco.
        UtilsMethods.addHeaders("x-app-type", config.getProperty(evnName + "-x-app-type"));
        UtilsMethods.addHeaders("x-client-id", config.getProperty(evnName + "-x-client-id"));
        UtilsMethods.addHeaders("x-api-key", config.getProperty(evnName + "-x-api-key"));
        UtilsMethods.addHeaders("x-login-module", config.getProperty(evnName + "-x-login-module"));
        UtilsMethods.addHeaders("x-channel", config.getProperty(evnName + "-x-channel"));
        UtilsMethods.addHeaders("x-app-version", config.getProperty(evnName + "-x-app-version"));
        UtilsMethods.addHeaders("Opco", OPCO);

        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req);
        selUtils.addTestcaseDescription("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + data.getLoginAUUID(), "description");
        UtilsMethods.printInfoLog("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + data.getLoginAUUID());
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
            token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
            UtilsMethods.printInfoLog("Request URL : " + queryable.getURI());
            UtilsMethods.printInfoLog("Response Body : " + response.asString());
            UtilsMethods.printInfoLog("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            if (response.jsonPath().getString("message").equalsIgnoreCase("Failed to authenticate user.")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Please Update Password As soon as possible if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (response.jsonPath().getString("message").toLowerCase().contains("something went wrong")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Login com.airtel.cs.API Failed(Marked Build As Failed).\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            } else if (!response.jsonPath().getString("message").equalsIgnoreCase("User authenticated successfully")) {
                continueExecutionAPI = false;
                softAssert.fail("Not able to generate Token. Please Check the com.airtel.cs.API error Message and make changes if required.\ncom.airtel.cs.API Response Message: " + response.jsonPath().getString("message"));
            }
        } catch (Exception e) {
            continueExecutionAPI = false;
            softAssert.fail("Connectivity issue occurred, Not able to connect with server : " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, dataProvider = "queueState", description = "State Queue Mapping Test", dataProviderClass = DataProviders.class)
    public void stateQueueTest(QueueStateDataBeans data) {
        selUtils.addTestcaseDescription("State Queue Mapping Test: " + data.getQueue(), "description");
        SoftAssert softAssert = new SoftAssert();
        DataProviders dataProviders = new DataProviders();
        String ticketId;
        try {
            pages.getSupervisorTicketList().clickFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().scrollToQueueFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            pages.getFilterTabPage().clickQueueFilter();
            pages.getFilterTabPage().selectQueueByName(data.getQueue());
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickApplyFilter();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            softAssert.assertTrue(pages.getSupervisorTicketList().validateQueueFilter(data.getQueue()), "Queue Filter Does Applied Correctly or No Ticket Found");
        } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            pages.getFilterTabPage().clickOutsideFilter();
            pages.getFilterTabPage().clickCloseFilter();
            Assert.fail("Not able to apply filter " + e.fillInStackTrace());
        }
        try {
            Assert.assertEquals(pages.getSupervisorTicketList().getqueueValue().toLowerCase().trim(), data.getQueue().toLowerCase().trim(), "Ticket Does not found with Selected Queue");
            //Re-check
            ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
            TicketPOJO ticketPOJO = api.ticketMetaDataTest(ticketId);
            ArrayList<QueueStates> assignState = ticketPOJO.getResult().getQueueStates();
            List<String> state = new ArrayList<>();
            List<String> configState = dataProviders.getQueueState(data.getQueue());
            log.info(assignState.isEmpty() + " :Assign State Size:" + assignState.size());
            if (!assignState.isEmpty()) {
                for (QueueStates s : assignState) {
                    log.info("State Mapped: " + s.getExternalStateName());
                    state.add(s.getExternalStateName());
                }
            }

            for (String s : state) {
                log.info("State:" + s);
                if (!configState.contains(s.toLowerCase().trim())) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :State must not mapped to '" + data.getQueue() + "' as its not mention in config.");
                }
                configState.remove(s.toLowerCase().trim());
            }

            for (String s : configState) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :State must be mapped to '" + data.getQueue() + "' as its mention in config.");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            softAssert.fail("Not able to search Ticket due to following error: " + e.getMessage());
        }
        pages.getSupervisorTicketList().resetFilter();
        softAssert.assertAll();
    }
}
