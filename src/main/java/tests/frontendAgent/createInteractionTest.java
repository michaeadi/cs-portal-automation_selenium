package tests.frontendAgent;

import API.APIEndPoints;
import POJO.LoginPOJO;
import POJO.SMSHistory.SMSHistoryList;
import POJO.SMSHistory.SMSHistoryPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
import Utils.DataProviders.ftrDataBeans;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExcelUtils.writeToExcel;
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
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static Utils.DataProviders.DataProviders.User;
import static Utils.ExtentReports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class createInteractionTest extends BaseTest {

    String customerNumber = null;
    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA | !continueExecutionAPI){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions: " + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerNumber=Data.getCustomerNumber();
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }


    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", description = "Create FTR Interaction ", dataProvider = "getTestData1", dataProviderClass = DataProviders.class, enabled = true)
    public void CreateInteraction(ftrDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(" Validating FTR Ticket: " + Data.getIssueCode(), "Creating FTR Tickets and Configurations of Issue Code " + Data.getIssueCode());
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        InteractionsPOM interactionsPOM = customerInteractionPagePOM.clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        try {
            try {
                interactionsPOM.waitTillLoaderGetsRemoved();
                interactionsPOM.clickOnCode();
                interactionsPOM.searchCode(Data.getIssueCode());
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                Thread.sleep(1000);
                interactionsPOM.clickOnCode();
                interactionsPOM.searchCode(Data.getIssueCode());

            }
            interactionsPOM.selectCode(Data.getIssueCode());
            interactionsPOM.waitTillLoaderGetsRemoved();
            ExtentTestManager.getTest().log(LogStatus.INFO, "Creating ticket with issue code -" + Data.getIssueCode());
            System.out.println(interactionsPOM.getIssue());
            softAssert.assertEquals(interactionsPOM.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is not as expected ");
            System.out.println(interactionsPOM.getIssueSubSubType());
            softAssert.assertEquals(interactionsPOM.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is not as expected ");
            System.out.println(interactionsPOM.getIssueType());
            softAssert.assertEquals(interactionsPOM.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is not as expected ");
            System.out.println(interactionsPOM.getIssueSubType());
            softAssert.assertEquals(interactionsPOM.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is not as expected ");
            interactionsPOM.sendComment("Automation Suite");
            interactionsPOM.clickOnSave();
            softAssert.assertTrue(interactionsPOM.isResolvedFTRDisplayed(), "Resolved FTR does not display");
            softAssert.assertEquals(interactionsPOM.getResolvedFTRDisplayed(), "Resolved FTR", "Resolved FTR does not display");
            SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
            SMSHistoryList list = smsHistory.getResult().get(0);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Message Sent after Ticket Creation: " + list.getMessageText());
            softAssert.assertTrue(list.getMessageText().contains(Data.getIssueCode()), "Message Sent does not send for same ticket id which has been Create");
            softAssert.assertEquals(list.getSmsType().toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message type is not system");
            softAssert.assertFalse(list.isAction(), "Action button is not disabled");
            softAssert.assertEquals(list.getTemplateName().toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            System.out.println("in catch");
            interactionsPOM.clickOutside();
            interactionsPOM.resetInteractionIssue();
            interactionsPOM.waitTillLoaderGetsRemoved();
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.BASE64);
        interactionsPOM.closeInteractions();
        ExtentTestManager.getTest().log(LogStatus.INFO, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 3)
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


    @Test(priority = 4, dependsOnMethods = "openCustomerInteraction", description = "Create Interaction ", dataProvider = "getTestData2", dataProviderClass = DataProviders.class)
    public void CreateNFTRInteraction(nftrDataBeans Data) throws InterruptedException, IOException {
        ExtentTestManager.startTest(" Validating NFTR Ticket: " + Data.getIssueCode(), "Creating NFTR Tickets and Configurations of Issue Code " + Data.getIssueCode());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        InteractionsPOM interactionsPOM = customerInteractionPagePOM.clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        try {
            interactionsPOM.clickOnCode();
            interactionsPOM.searchCode(Data.getIssueCode());
        } catch (Exception e) {
            System.out.println("Try Again:");
            Thread.sleep(1000);
            interactionsPOM.clickOnCode();
            interactionsPOM.searchCode(Data.getIssueCode());

        }
        interactionsPOM.selectCode(Data.getIssueCode());
        interactionsPOM.waitTillLoaderGetsRemoved();
        ExtentTestManager.getTest().log(LogStatus.INFO, "Creating ticket with issue code -" + Data.getIssueCode());
        softAssert.assertEquals(interactionsPOM.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is not as expected ");
        softAssert.assertEquals(interactionsPOM.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is not as expected ");
        softAssert.assertEquals(interactionsPOM.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is not as expected ");
        softAssert.assertEquals(interactionsPOM.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), Data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is not as expected ");
        String ticket_number = null;
        try {

            if (Data.getIssueFieldLabel1() != null)
                if (Data.getIssueFieldType1().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel1().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("1"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("1").replace("*", "").trim(), (Data.getIssueFieldLabel1().replace("*", "").trim()), Data.getIssueFieldLabel1() + " Label does not match");
                    if (Data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("1").contains("*"), Data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("1", "012345");
                } else if (Data.getIssueFieldType1().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel1().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel1()));
                    if (Data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));

                } else if (Data.getIssueFieldType1().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel1().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("1"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("1").replace("*", "").trim(), (Data.getIssueFieldLabel1().replace("*", "").trim()), Data.getIssueFieldLabel1() + "Label does not match");
                    if (Data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("1").contains("*"), Data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("1");
                }

            if (Data.getIssueFieldLabel2() != null)
                if (Data.getIssueFieldType2().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel2().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("2"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("2").replace("*", "").trim(), (Data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("2").contains("*"), Data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("2", "012345");
                } else if (Data.getIssueFieldType2().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel2().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel2()));
                    if (Data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType2().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel2().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("2"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("2").replace("*", "").trim(), (Data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("2").contains("*"), Data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("2");
                }

            if (Data.getIssueFieldLabel3() != null)
                if (Data.getIssueFieldType3().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel3().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("3"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("3").replace("*", "").trim(), (Data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("3").contains("*"), Data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("3", "012345");
                } else if (Data.getIssueFieldType3().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel3().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel3()));
                    if (Data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));

                } else if (Data.getIssueFieldType3().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel3().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("3"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("3").replace("*", "").trim(), (Data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("3").contains("*"), Data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("3");
                }

            System.out.println("Field Type-4" + Data.getIssueFieldType4());

            if (Data.getIssueFieldLabel4() != null)
                if (Data.getIssueFieldType4().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel4().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("4"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("4").replace("*", "").trim(), (Data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("4").contains("*"), Data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("4", "012345");
                } else if (Data.getIssueFieldType4().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel4().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel4()));
                    if (Data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType4().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel4().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("4"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("4").replace("*", "").trim(), (Data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("4").contains("*"), Data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("4");
                }

            if (Data.getIssueFieldLabel5() != null)
                if (Data.getIssueFieldType5().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel5().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("5"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("5").replace("*", "").trim(), (Data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("5").contains("*"), Data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("5", "012345");
                } else if (Data.getIssueFieldType5().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel5().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel5()));
                    if (Data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType5().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel5().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("5"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("5").replace("*", "").trim(), (Data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("5").contains("*"), Data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("5");
                }

            if (Data.getIssueFieldLabel6() != null)
                if (Data.getIssueFieldType6().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel6().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("6"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("6").replace("*", "").trim(), (Data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("6").contains("*"), Data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("6", "012345");
                } else if (Data.getIssueFieldType6().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel6().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel6()));
                    if (Data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType6().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel6().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("6"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("6").replace("*", "").trim(), (Data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("6").contains("*"), Data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("6");
                }

            if (Data.getIssueFieldLabel7() != null)
                if (Data.getIssueFieldType7().equalsIgnoreCase("Text Box") && !Data.getIssueFieldLabel7().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabel("7"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabel("7").replace("*", "").trim(), (Data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabel("7").contains("*"), Data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setIssueDetailInput("7", "012345");
                } else if (Data.getIssueFieldType7().equalsIgnoreCase("Date") && !Data.getIssueFieldLabel7().isEmpty()) {
                    System.out.println(interactionsPOM.isDateFieldAvailable());
                    softAssert.assertEquals(interactionsPOM.isDateFieldAvailable(), (Data.getIssueFieldLabel7()));
                    if (Data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.isDateFieldAvailableMandatory().contains("*"), Data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.setDateFieldAvailable(dtf.format(now));
                } else if (Data.getIssueFieldType7().equalsIgnoreCase("Drop Down") && !Data.getIssueFieldLabel7().isEmpty()) {
                    System.out.println(interactionsPOM.getIssueDetailLabelDropDown("7"));
                    softAssert.assertEquals(interactionsPOM.getIssueDetailLabelDropDown("7").replace("*", "").trim(), (Data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (Data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(interactionsPOM.getIssueDetailLabelDropDown("7").contains("*"), Data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    interactionsPOM.selectIssueDetailInput("7");
                }

            interactionsPOM.sendComment("Automation Suite");
            Assert.assertTrue(interactionsPOM.isSaveEnable());
            interactionsPOM.clickOnSave();
            softAssert.assertTrue(interactionsPOM.isResolvedFTRDisplayed());
            System.out.println(interactionsPOM.getResolvedFTRDisplayed());
            String[] valueToWrite;
            if (!interactionsPOM.getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticket_number = interactionsPOM.getResolvedFTRDisplayed();
                System.out.println("Ticket Number:ME " + ticket_number);
                valueToWrite = new String[]{ticket_number};
                writeToExcel objExcelFile = new writeToExcel();
                File Exceldir = new File("Excels");
                File Excel = new File(Exceldir, BaseTest.ExcelPath);
                System.out.println("Ticket Number:You " + Data.getRownum());
                objExcelFile.writeTicketNumber(Excel.getAbsolutePath(), config.getProperty(BaseTest.suiteType + "-NftrSheet"), valueToWrite, Data.getRownum());
                System.out.println("Ticket Number Written to Excel " + valueToWrite[0]);
            } else {
                softAssert.fail("It's FTR not NFTR");
            }
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            System.out.println("in catch");
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.ERROR, e.fillInStackTrace());
            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
            ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
            try {
                interactionsPOM.closeInteractions();
                interactionsPOM.clickOnContinueButton();
            } catch (NoSuchElementException | TimeoutException ex) {
                softAssert.fail("Unable to close interaction", ex.getCause());
                interactionsPOM.clickOutside();
                interactionsPOM.resetInteractionIssue();
            }
            e.printStackTrace();
            Assert.fail(e.getCause().getMessage());
        }
//        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
//                getScreenshotAs(OutputType.BASE64);
//        ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        interactionsPOM.closeInteractions();
        SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
        SMSHistoryList list = smsHistory.getResult().get(0);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Message Sent after Ticket Creation: " + list.getMessageText());
        softAssert.assertTrue(list.getMessageText().contains(ticket_number), "Message Sent does not send for same ticket id which has been Create");
        softAssert.assertEquals(list.getSmsType().toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message type is not system");
        softAssert.assertFalse(list.isAction(), "Action button is not disabled");
        softAssert.assertEquals(list.getTemplateName().toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
        softAssert.assertAll();
    }


}
