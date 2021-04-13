package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.LoginPOJO;
import com.airtel.cs.pojo.ticketlist.IssueDetails;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.airtel.cs.commonutils.extentreports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class SupervisorSearchTicket extends Driver {


    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 0)
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
        startTest("LOGIN com.airtel.cs.API TEST ", "Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + data.getLoginAUUID());
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
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
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

    @Test(priority = 1, description = "Open Supervisor Dashboard")
    public void openSupervisorDashboard() {
        ExtentTestManager.startTest("Open Supervisor Dashboard", "Open Supervisor Dashboard");
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void SearchTicket(NftrDataBeans data) {
        ExtentTestManager.startTest("Search Ticket & Validate Ticket Meta Data: " + data.getTicketNumber(), "Search Ticket & Validate Ticket Meta Data");
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> workGroups = new HashMap<>();
        DataProviders dataProviders = new DataProviders();
        try {
            pages.getSupervisorTicketList().writeTicketId(data.getTicketNumber());
            pages.getSupervisorTicketList().clickSearchBtn();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            Assert.assertEquals(pages.getSupervisorTicketList().getTicketIdvalue(), data.getTicketNumber(), "Search Ticket does not found");

            try {
                TicketPOJO ticketPOJO = api.ticketMetaDataTest(data.getTicketNumber());
                softAssert.assertTrue(pages.getSupervisorTicketList().isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
                softAssert.assertTrue(pages.getSupervisorTicketList().isWorkGroupName(), "Ticket Meta Data Does Not Have Workgroup");
                softAssert.assertTrue(pages.getSupervisorTicketList().isPrioritylabel(), "Ticket Meta Data Does Not Have Priority");
                softAssert.assertTrue(pages.getSupervisorTicketList().isStateLabel(), "Ticket Meta Data Does Not Have State");
                softAssert.assertTrue(pages.getSupervisorTicketList().isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
                softAssert.assertTrue(pages.getSupervisorTicketList().isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
                softAssert.assertTrue(pages.getSupervisorTicketList().isQueueLabel(), "Ticket Meta Data Does Not Have Queue");
                softAssert.assertTrue(pages.getSupervisorTicketList().isIssueLabel(), "Ticket Meta Data Does Not Have Issue");
                softAssert.assertTrue(pages.getSupervisorTicketList().isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
                softAssert.assertTrue(pages.getSupervisorTicketList().isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
                softAssert.assertTrue(pages.getSupervisorTicketList().isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
                softAssert.assertTrue(pages.getSupervisorTicketList().isCodeLabel(), "Ticket Meta Data Does Not Have Code");
                softAssert.assertEquals(pages.getSupervisorTicketList().getIssueValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getIssueTypeValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Type Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getSubTypeValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Sub Type Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getsubSubTypeValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Issue Does Not Sub Sub Type Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getCodeValue().toLowerCase().trim(), data.getIssueCode().toLowerCase().trim(),
                        "Issue Does Not Code Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getWorkGroupName().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getWorkgroup1().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Ticket Does Not WorkGroup Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getqueueValue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getAssignmentQueue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(),
                        "Ticket Does Not Queue Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getPriorityValue().toLowerCase().trim(), data.getPriority().toLowerCase().trim(),
                        "Ticket Does Not Priority Validated");
                softAssert.assertEquals(pages.getSupervisorTicketList().getPriorityValue().toLowerCase().trim(), data.getPriority().toLowerCase().trim(),
                        "Issue Does Not Type Validated");
                softAssert.assertEquals(UtilsMethods.convertToHR(ticketPOJO.getResult().getCommittedSla()), data.getCommittedSLA(), "Committed SLA does not configured Correctly");
                softAssert.assertEquals(pages.getSupervisorTicketList().getMSISDN().trim(), ticketPOJO.getResult().getMsisdn().trim(), "User MSISDN is not same as expected");
                Map<String, Long> sla = ticketPOJO.getResult().getSla();
                if (data.getWorkgroup1() != null)
                    workGroups.put(data.getWorkgroup1(), data.getSla1());
                if (data.getWorkgroup2() != null)
                    workGroups.put(data.getWorkgroup2(), data.getSla2());
                if (data.getWorkgroup3() != null)
                    workGroups.put(data.getWorkgroup3(), data.getSla3());
                if (data.getWorkgroup4() != null)
                    workGroups.put(data.getWorkgroup4(), data.getSla4());
                for (Map.Entry mapElement : sla.entrySet()) {
                    String key = (String) mapElement.getKey();
                    String value = mapElement.getValue().toString();
                    log.info(key + " = " + value);
                    if (workGroups.containsKey(key)) {
                        workGroups.remove(key);
                        ExtentTestManager.getTest().log(LogStatus.INFO, key + " : workgroup is configured correctly in DB as mentioned in configuration");
                    } else {
                        ExtentTestManager.getTest().log(LogStatus.FAIL, key + " workgroup is not configured correctly in DB as not mentioned in configuration");
                    }
                    if (value.charAt(0) == '-') {
                        softAssert.assertTrue(pages.getSupervisorTicketList().isNegativeSLA(), "For negative SLA red symbol does not display");
                    } else {
                        softAssert.assertTrue(pages.getSupervisorTicketList().isPositiveSLA(), "For positive SLA green symbol does not display");
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
                List<String> configTicketLayout = dataProviders.getTicketLayout(data.getIssueCode());
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
        pages.getSupervisorTicketList().clearInputBox();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validate Assign to Agent and Transfer to Queue Option")
    public void validateCheckBox() {
        ExtentTestManager.startTest("Validate Check Box", "Validate Assign to Agent and Transfer to Queue Option om Open Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().clickCheckbox();
        softAssert.assertTrue(pages.getSupervisorTicketList().isAssignToAgent(), "Check User does not have Option to Assign to Agent");
        softAssert.assertTrue(pages.getSupervisorTicketList().isTransferToQueue(), "Check User does not have Option to Transfer to Queue");
        softAssert.assertAll();
    }
}
