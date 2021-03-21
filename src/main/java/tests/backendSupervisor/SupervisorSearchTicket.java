package tests.backendSupervisor;

import API.APIEndPoints;
import POJO.LoginPOJO;
import POJO.TicketList.IssueDetails;
import POJO.TicketList.TicketPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.nftrDataBeans;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;
import tests.frontendAgent.BaseTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Utils.ExtentReports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SupervisorSearchTicket extends BaseTest {


    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionBS | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

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

    @Test(priority = 1,description = "Open Supervisor Dashboard")
    public void openSupervisorDashboard(){
        ExtentTestManager.startTest("Open Supervisor Dashboard","Open Supervisor Dashboard");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void SearchTicket(Method method, nftrDataBeans Data) {
        ExtentTestManager.startTest("Search Ticket & Validate Ticket Meta Data: " + Data.getTicketNumber(), "Search Ticket & Validate Ticket Meta Data");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> workGroups = new HashMap<>();
        DataProviders dataProviders = new DataProviders();
        try {
            ticketListPage.writeTicketId(Data.getTicketNumber());
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber(), "Search Ticket does not found");

            try {
                TicketPOJO ticketPOJO = api.ticketMetaDataTest(Data.getTicketNumber());
                softAssert.assertTrue(ticketListPage.isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
                softAssert.assertTrue(ticketListPage.isWorkGroupName(), "Ticket Meta Data Does Not Have Workgroup");
                softAssert.assertTrue(ticketListPage.isPrioritylabel(), "Ticket Meta Data Does Not Have Priority");
                softAssert.assertTrue(ticketListPage.isStateLabel(), "Ticket Meta Data Does Not Have State");
                softAssert.assertTrue(ticketListPage.isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
                softAssert.assertTrue(ticketListPage.isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
                softAssert.assertTrue(ticketListPage.isQueueLabel(), "Ticket Meta Data Does Not Have Queue");
                softAssert.assertTrue(ticketListPage.isIssueLabel(), "Ticket Meta Data Does Not Have Issue");
                softAssert.assertTrue(ticketListPage.isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
                softAssert.assertTrue(ticketListPage.isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
                softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
                softAssert.assertTrue(ticketListPage.isCodeLabel(), "Ticket Meta Data Does Not Have Code");
                softAssert.assertEquals(ticketListPage.getIssueValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Validated");
                softAssert.assertEquals(ticketListPage.getIssueTypeValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Type Validated");
                softAssert.assertEquals(ticketListPage.getSubTypeValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Sub Type Validated");
                softAssert.assertEquals(ticketListPage.getsubSubTypeValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Sub Sub Type Validated");
                softAssert.assertEquals(ticketListPage.getCodeValue().toLowerCase().trim(), Data.getIssueCode().toLowerCase().trim(),
                        "Issue Does Not Code Validated");
                softAssert.assertEquals(ticketListPage.getWorkGroupName().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getWorkgroup1().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Ticket Does Not WorkGroup Validated");
                softAssert.assertEquals(ticketListPage.getqueueValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getAssignmentQueue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Ticket Does Not Queue Validated");
                softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                        "Ticket Does Not Priority Validated");
                softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                        "Issue Does Not Type Validated");
                softAssert.assertEquals(UtilsMethods.convertToHR(ticketPOJO.getResult().getCommittedSla()), Data.getCommittedSLA(), "Committed SLA does not configured Correctly");
                softAssert.assertEquals(ticketListPage.getMSISDN().trim(), ticketPOJO.getResult().getMsisdn().trim(), "User MSISDN is not same as expected");
                Map<String, Long> sla = ticketPOJO.getResult().getSla();
                if (Data.getWorkgroup1() != null)
                    workGroups.put(Data.getWorkgroup1(), Data.getSLA1());
                if (Data.getWorkgroup2() != null)
                    workGroups.put(Data.getWorkgroup2(), Data.getSLA2());
                if (Data.getWorkgroup3() != null)
                    workGroups.put(Data.getWorkgroup3(), Data.getSLA3());
                if (Data.getWorkgroup4() != null)
                    workGroups.put(Data.getWorkgroup4(), Data.getSLA4());
                for (Map.Entry mapElement : sla.entrySet()) {
                    String key = (String) mapElement.getKey();
                    String value = mapElement.getValue().toString();
                    System.out.println(key + " = " + value);
                    if (workGroups.containsKey(key)) {
                        workGroups.remove(key);
                        ExtentTestManager.getTest().log(LogStatus.INFO, key + " : workgroup is configured correctly in DB as mentioned in configuration");
                    } else {
                        ExtentTestManager.getTest().log(LogStatus.FAIL, key + " workgroup is not configured correctly in DB as not mentioned in configuration");
                    }
                    if (value.charAt(0) == '-') {
                        softAssert.assertTrue(ticketListPage.isNegativeSLA(), "For negative SLA red symbol does not display");
                    } else {
                        softAssert.assertTrue(ticketListPage.isPositiveSLA(), "For positive SLA green symbol does not display");
                    }
                }
                for (Map.Entry mapElement : workGroups.entrySet()) {
                    String key = (String) mapElement.getKey();
                    String value = mapElement.getValue().toString();
                    if (key != null)
                        if (!key.isEmpty())
                            ExtentTestManager.getTest().log(LogStatus.FAIL, key + " workgroup is not configured correctly in DB as mentioned in configuration");
                }
                ArrayList<IssueDetails> ticketLayout = ticketPOJO.getResult().getTicketDetails();
                List<String> configTicketLayout = dataProviders.getTicketLayout(Data.getIssueCode());
                try {
                    for (IssueDetails layout : ticketLayout) {
                        if (!configTicketLayout.contains(layout.getPlaceHolder().toLowerCase().trim())) {
                            ExtentTestManager.getTest().log(LogStatus.FAIL, layout.getPlaceHolder() + " : Ticket Layout must not configure in database as does not mention in Config sheet.");
                        }
                        configTicketLayout.remove(layout.getPlaceHolder().toLowerCase().trim());
                    }
                } catch (NullPointerException e) {
                    UtilsMethods.printInfoLog("No Ticket Layout Config in database :" + e.getMessage());
                }

                for (String name : configTicketLayout) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, name + " : Ticket Layout must be configure in database as mention in Config sheet.");
                }
            } catch (NullPointerException | TimeoutException | NoSuchElementException e) {
                e.printStackTrace();
                softAssert.fail("Ticket meta data Assertion failed: " + e.getMessage());
            }
        } catch (TimeoutException | NoSuchElementException | AssertionError e) {
            e.printStackTrace();
            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
            ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
            softAssert.fail("Ticket id search not done for following error: " + e.getMessage());
        }
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validate Assign to Agent and Transfer to Queue Option", dataProviderClass = DataProviders.class)
    public void validateCheckBox(Method method) {
        ExtentTestManager.startTest("Validate Check Box", "Validate Assign to Agent and Transfer to Queue Option om Open Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent(), "Check User does not have Option to Assign to Agent");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(), "Check User does not have Option to Transfer to Queue");
        softAssert.assertAll();
    }


}
