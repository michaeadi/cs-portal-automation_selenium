package tests;

import API.APIEndPoints;
import POJO.LoginPOJO;
import POJO.SMSHistory.SMSHistoryList;
import POJO.SMSHistory.SMSHistoryPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import Utils.PassUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static Utils.ExtentReports.ExtentTestManager.getTest;
import static Utils.ExtentReports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SupervisorUpdateTicket extends BaseTest {

    static String ticketId=null;
    String customerNumber = null;
    APIEndPoints api = new APIEndPoints();

    @DataProviders.User(UserType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 0)
    public void loginAPI(TestDatabean Data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LoginPOJO Req = LoginPOJO.loginBody(PassUtils.decodePassword(Data.getPassword()), Data.getLoginAUUID());
        map.clear();
        map.add(new Header("x-app-name", config.getProperty(Env + "-x-app-name")));
        map.add(new Header("x-service-id", config.getProperty(Env + "-x-service-id")));
        //map.add(new Header("x-bsy-bn", config.getProperty(Env + "-x-bsy-bn"))); //Comment this line this header removed from MG Opco.
        map.add(new Header("x-app-type", config.getProperty(Env + "-x-app-type")));
        map.add(new Header("x-client-id", config.getProperty(Env + "-x-client-id")));
        map.add(new Header("x-api-key", config.getProperty(Env + "-x-api-key")));
        map.add(new Header("x-login-module", config.getProperty(Env + "-x-login-module")));
        map.add(new Header("x-channel", config.getProperty(Env + "-x-channel")));
        map.add(new Header("x-app-version", config.getProperty(Env + "-x-app-version")));
        map.add(new Header("Opco", Opco));

        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        startTest("LOGIN API TEST ", "Logging in Using Login API for getting TOKEN with user : " + Data.getLoginAUUID());
        getTest().log(LogStatus.INFO, "Logging in Using Login API for getting TOKEN with user : " + Data.getLoginAUUID());
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
        getTest().log(LogStatus.INFO, "Request Headers are  : " + queryable.getHeaders());
        Response response = request.post("/auth/api/user-mngmnt/v2/login");
        Token = "Bearer " + response.jsonPath().getString("result.accessToken");
        map.add(new Header("Authorization", Token));
        getTest().log(LogStatus.INFO, "Response : " + response.asString());
        getTest().log(LogStatus.INFO, "Response Body is  : " + response.asString());
        getTest().log(LogStatus.INFO, "Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
    }

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void setCustomerNumber(Method method, TestDatabean Data) {
        customerNumber = Data.getCustomerNumber();
    }

    @Test(priority = 2, description = "Update Ticket", dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void updateTicket(Method method, nftrDataBeans Data) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        ViewTicketPagePOM viewTicket = new ViewTicketPagePOM(driver);
        ExtentTestManager.startTest("Update Ticket: " + Data.getIssueCode(), "Update Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        String selectedState = null;
        try {
            ticketListPage.writeTicketId(Data.getTicketNumber());
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber(), "Search Ticket does not found");
            try {
                ticketListPage.viewTicket();
                ticketListPage.waitTillLoaderGetsRemoved();
                try {
                    selectedState = viewTicket.selectState(data.ticketStateClosed());
                if (selectedState.equalsIgnoreCase(data.ticketStateClosed())) {
                    ticketListPage.waitTillLoaderGetsRemoved();
                    ticketListPage.changeTicketTypeToClosed();
                    ticketListPage.waitTillLoaderGetsRemoved();
                    ticketListPage.writeTicketId(Data.getTicketNumber());
                    ticketListPage.clickSearchBtn();
                    ticketListPage.waitTillLoaderGetsRemoved();
                    softAssert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber(), "Search Ticket Does not Fetched Correctly");
                    Assert.assertEquals(ticketListPage.getStatevalue(), selectedState, "Ticket Does not Updated to Selected State");
                    if(ticketId==null){
                        ticketId=Data.getTicketNumber();
                    }
                    SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
                    SMSHistoryList list = smsHistory.getResult().get(0);
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Message Sent after closure: " + list.getMessageText());
                    softAssert.assertTrue(list.getMessageText().contains(Data.getTicketNumber()), "Message Sent does not send for same ticket id which has been closed");
                    softAssert.assertEquals(list.getSmsType().toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message type is not system");
                    softAssert.assertFalse(list.isAction(), "Action button is not disabled");
                    softAssert.assertEquals(list.getTemplateName().toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
                } else {
                    viewTicket.clickBackButton();
                }
                }catch (TimeoutException | NoSuchElementException | ElementClickInterceptedException e){
                    softAssert.fail("Update Ticket does not complete due to error :" + e.fillInStackTrace());
                    viewTicket.clickBackButton();
                }
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                e.printStackTrace();
                softAssert.fail("Update Ticket does not complete due to error :" + e.fillInStackTrace());
            }
        } catch (TimeoutException | NoSuchElementException | AssertionError e) {
            e.printStackTrace();
            softAssert.fail("Ticket id search not done due to following error: " + e.getMessage());
        }
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 3, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(Method method, TestDatabean Data) throws IOException {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        if(ticketId!=null) {
            SideMenuPOM.clickOnSideMenu();
            SideMenuPOM.clickOnName();
            customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
            customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
            customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
            softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        }else{
            SideMenuPOM.printWarningLog("No Ticket Id Closed. SKIP Validate Re-open Icon on Closed Ticket");
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "openCustomerInteraction", description = "Validate Re-open Icon on Closed Ticket")
    public void validateReopenIcon() throws InterruptedException, IOException {
        SoftAssert softAssert = new SoftAssert();
        customerInteractionPagePOM customerInteractionPage = new customerInteractionPagePOM(driver);
        if(ticketId!=null) {
        ExtentTestManager.startTest("Validate Re-open Icon on Closed Ticket: " + ticketId, "Validate Re-open Icon on Closed Ticket: " + ticketId);
        viewHistoryPOM viewHistory = customerInteractionPage.clickOnViewHistory();
        FrontendTicketHistory ticketHistory = viewHistory.clickOnTicketHistory();
        ticketHistory.waitTillLoaderGetsRemoved();
        ticketHistory.writeTicketId(ticketId);
        ticketHistory.clickSearchBtn();
        ticketHistory.waitTillLoaderGetsRemoved();
        Thread.sleep(3000);
        softAssert.assertEquals(ticketHistory.getTicketId(1), ticketId, "Ticket Id does not same as search ticket id.");
        ticketHistory.getTicketState(1);
        softAssert.assertTrue(ticketHistory.checkReopen(1), "Reopen icon does not found on ticket");
        }else{
            customerInteractionPage.printWarningLog("No Ticket Id Closed. SKIP Validate Re-open Icon on Closed Ticket");
        }
        softAssert.assertAll();
    }
}
