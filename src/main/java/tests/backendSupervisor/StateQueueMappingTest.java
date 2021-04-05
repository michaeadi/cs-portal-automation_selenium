package tests.backendSupervisor;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.pojo.LoginPOJO;
import com.airtel.cs.pojo.TicketList.QueueStates;
import com.airtel.cs.pojo.TicketList.TicketPOJO;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.QueueStateDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.pagerepository.pagemethods.FilterTabPOM;
import com.airtel.cs.pagerepository.pagemethods.supervisorTicketListPagePOM;
import tests.frontendagent.BaseTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.airtel.cs.commonutils.extentreports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class StateQueueMappingTest extends BaseTest {

    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "com/airtel/cs/api")
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
        UtilsMethods.addHeaders("Opco", OPCO);

        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        startTest("LOGIN com.airtel.cs.API TEST ", "Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + Data.getLoginAUUID());
        UtilsMethods.printInfoLog("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + Data.getLoginAUUID());
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

    @Test(priority = 1, dataProvider = "queueState", description = "State Queue Mapping Test", enabled = true, dataProviderClass = DataProviders.class)
    public void stateQueueTest(Method method, QueueStateDataBeans data) throws InterruptedException {
        ExtentTestManager.startTest("State Queue Mapping Test: " + data.getQueue(), "State Queue Mapping Test");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DataProviders dataProviders = new DataProviders();
        String ticketId = null;
        try {
            ticketListPage.clickFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.scrollToQueueFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            filterTab.clickQueueFilter();
            filterTab.selectQueueByName(data.getQueue());
            filterTab.clickOutsideFilter();
            filterTab.clickApplyFilter();
            ticketListPage.waitTillLoaderGetsRemoved();
            softAssert.assertTrue(ticketListPage.validateQueueFilter(data.getQueue()), "Queue Filter Does Applied Correctly or No Ticket Found");
        } catch (InterruptedException | NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            filterTab.clickOutsideFilter();
            filterTab.clickCloseFilter();
            Assert.fail("Not able to apply filter " + e.fillInStackTrace());
        }
        try {
            Assert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), data.getQueue().toLowerCase().trim(), "Ticket Does not found with Selected Queue");
            //Re-check
            ticketId = ticketListPage.getTicketIdvalue();
            TicketPOJO ticketPOJO = api.ticketMetaDataTest(ticketId);
            ArrayList<QueueStates> assignState = ticketPOJO.getResult().getQueueStates();
            List<String> state = new ArrayList<>();
            List<String> configState = dataProviders.getQueueState(data.getQueue());
            System.out.println(assignState.isEmpty() + " :Assign State Size:" + assignState.size());
            if (!assignState.isEmpty()) {
                for (QueueStates s : assignState) {
                    System.out.println("State Mapped: " + s.getExternalStateName());
                    state.add(s.getExternalStateName());
                }
            }

            for (String s : state) {
                System.out.println("State:" + s);
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
        ticketListPage.resetFilter();
        softAssert.assertAll();
    }
}
