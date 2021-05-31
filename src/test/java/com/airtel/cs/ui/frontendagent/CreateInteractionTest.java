package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.excelutils.WriteToExcel;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.CustomerProfile;
import com.airtel.cs.pojo.response.LoginPOJO;
import com.airtel.cs.pojo.response.smshistory.SMSHistoryList;
import com.airtel.cs.pojo.response.smshistory.SMSHistoryPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.airtel.cs.commonutils.dataproviders.DataProviders.User;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Log4j2
public class CreateInteractionTest extends Driver {

    String customerNumber = null;
    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();


    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", description = "Create FTR Interaction ", dataProvider = "getTestData1", dataProviderClass = DataProviders.class)
    public void createInteraction(FtrDataBeans data) throws InterruptedException {
        final String issueCode = data.getIssueCode();
        selUtils.addTestcaseDescription(" Validating FTR Ticket: " + issueCode, "description");
        pages.getCustomerProfilePage().clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        try {
            try {
                pages.getInteractionsPage().waitTillLoaderGetsRemoved();
                pages.getInteractionsPage().clickOnCode();
                pages.getInteractionsPage().searchCode(issueCode);
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                Thread.sleep(1000);
                pages.getInteractionsPage().clickOnCode();
                pages.getInteractionsPage().searchCode(issueCode);

            }
            pages.getInteractionsPage().selectCode(issueCode);
            pages.getInteractionsPage().waitTillLoaderGetsRemoved();
            commonLib.info("Creating ticket with issue code -" + issueCode);
            softAssert.assertEquals(pages.getInteractionsPage().getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is not as expected ");
            softAssert.assertEquals(pages.getInteractionsPage().getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is not as expected ");
            softAssert.assertEquals(pages.getInteractionsPage().getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is not as expected ");
            softAssert.assertEquals(pages.getInteractionsPage().getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is not as expected ");
            pages.getInteractionsPage().sendComment("Automation Suite");
            pages.getInteractionsPage().clickOnSave();
            softAssert.assertTrue(pages.getInteractionsPage().isResolvedFTRDisplayed(), "Resolved FTR does not display");
            softAssert.assertEquals(pages.getInteractionsPage().getResolvedFTRDisplayed(), "Resolved FTR", "Resolved FTR does not display");
            SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
            SMSHistoryList list = smsHistory.getResult().get(0);
            commonLib.info("Message Sent after Ticket Creation: " + list.getMessageText());
            softAssert.assertTrue(list.getMessageText().contains(issueCode), "Message does not sent to customer for same FTR Category for which Issue has been Create");
            softAssert.assertEquals(list.getSmsType().toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message type is not system");
            softAssert.assertFalse(list.isAction(), "Action button is not disabled");
            softAssert.assertEquals(list.getTemplateName().toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            log.info("in catch");
            pages.getInteractionsPage().clickOutside();
            pages.getInteractionsPage().resetInteractionIssue();
            pages.getInteractionsPage().waitTillLoaderGetsRemoved();
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.BASE64);
        pages.getInteractionsPage().closeInteractions();
        commonLib.fail("Test Failed", true);
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "com/airtel/cs/api")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 3)
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
        selUtils.addTestcaseDescription("LOGIN com.airtel.cs.API TEST,Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + data.getLoginAUUID(), "description");
        commonLib.info("Logging in Using Login com.airtel.cs.API for getting TOKEN with user : " + data.getLoginAUUID());
        baseURI = baseUrl;
        Headers headers = new Headers(map);
        RequestSpecification request = given()
                .headers(headers)
                .body(dtoAsString)
                .contentType("application/json");
        try {
            QueryableRequestSpecification queryable = SpecificationQuerier.query(request);
            commonLib.info("Request Headers are  : " + queryable.getHeaders());
            Response response = request.post("/auth/api/user-mngmnt/v2/login");
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
            commonLib.info("Request URL : " + queryable.getURI());
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
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


    @Test(priority = 4, dependsOnMethods = "openCustomerInteraction", description = "Create Interaction ", dataProvider = "getTestData2", dataProviderClass = DataProviders.class)
    public void CreateNFTRInteraction(NftrDataBeans data) throws InterruptedException, IOException {
        final String issueCode = data.getIssueCode();
        selUtils.addTestcaseDescription(" Validating NFTR Ticket: " + issueCode, "description");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        CustomerProfile customerInteractionPagePOM = new CustomerProfile(driver);
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        pages.getCustomerProfilePage().clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getInteractionsPage().clickOnCode();
            pages.getInteractionsPage().searchCode(issueCode);
        } catch (Exception e) {
            log.info("Try Again:");
            Thread.sleep(1000);
            pages.getInteractionsPage().clickOnCode();
            pages.getInteractionsPage().searchCode(issueCode);

        }
        pages.getInteractionsPage().selectCode(issueCode);
        pages.getInteractionsPage().waitTillLoaderGetsRemoved();
        commonLib.info("Creating ticket with issue code -" + issueCode);
        softAssert.assertEquals(pages.getInteractionsPage().getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is not as expected ");
        softAssert.assertEquals(pages.getInteractionsPage().getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is not as expected ");
        softAssert.assertEquals(pages.getInteractionsPage().getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is not as expected ");
        softAssert.assertEquals(pages.getInteractionsPage().getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is not as expected ");
        String ticketNumber = null;
        try {

            if (data.getIssueFieldLabel1() != null)
                if (data.getIssueFieldType1().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel1().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("1"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("1").replace("*", "").trim(), (data.getIssueFieldLabel1().replace("*", "").trim()), data.getIssueFieldLabel1() + " Label does not match");
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("1").contains("*"), data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("1", "012345");
                } else if (data.getIssueFieldType1().equalsIgnoreCase("Date") && !data.getIssueFieldLabel1().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel1()));
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                } else if (data.getIssueFieldType1().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel1().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("1"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").replace("*", "").trim(), (data.getIssueFieldLabel1().replace("*", "").trim()), data.getIssueFieldLabel1() + "Label does not match");
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").contains("*"), data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("1");
                }

            if (data.getIssueFieldLabel2() != null)
                if (data.getIssueFieldType2().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel2().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("2"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("2").replace("*", "").trim(), (data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("2").contains("*"), data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("2", "012345");
                } else if (data.getIssueFieldType2().equalsIgnoreCase("Date") && !data.getIssueFieldLabel2().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel2()));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType2().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel2().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("2"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").replace("*", "").trim(), (data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").contains("*"), data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("2");
                }

            if (data.getIssueFieldLabel3() != null)
                if (data.getIssueFieldType3().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel3().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("3"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("3").replace("*", "").trim(), (data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("3").contains("*"), data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("3", "012345");
                } else if (data.getIssueFieldType3().equalsIgnoreCase("Date") && !data.getIssueFieldLabel3().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel3()));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                } else if (data.getIssueFieldType3().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel3().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("3"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").replace("*", "").trim(), (data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").contains("*"), data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("3");
                }

            log.info("Field Type-4" + data.getIssueFieldType4());

            if (data.getIssueFieldLabel4() != null)
                if (data.getIssueFieldType4().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel4().isEmpty()) {
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("4").replace("*", "").trim(), (data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("4").contains("*"), data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("4", "012345");
                } else if (data.getIssueFieldType4().equalsIgnoreCase("Date") && !data.getIssueFieldLabel4().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel4()));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType4().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel4().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("4"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").replace("*", "").trim(), (data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").contains("*"), data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("4");
                }

            if (data.getIssueFieldLabel5() != null)
                if (data.getIssueFieldType5().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel5().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("5"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("5").replace("*", "").trim(), (data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("5").contains("*"), data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("5", "012345");
                } else if (data.getIssueFieldType5().equalsIgnoreCase("Date") && !data.getIssueFieldLabel5().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel5()));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType5().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel5().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("5"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").replace("*", "").trim(), (data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").contains("*"), data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("5");
                }

            if (data.getIssueFieldLabel6() != null)
                if (data.getIssueFieldType6().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel6().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("6"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("6").replace("*", "").trim(), (data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("6").contains("*"), data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("6", "012345");
                } else if (data.getIssueFieldType6().equalsIgnoreCase("Date") && !data.getIssueFieldLabel6().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel6()));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType6().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel6().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("6"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").replace("*", "").trim(), (data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").contains("*"), data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("6");
                }

            if (data.getIssueFieldLabel7() != null)
                if (data.getIssueFieldType7().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel7().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("7"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("7").replace("*", "").trim(), (data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("7").contains("*"), data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("7", "012345");
                } else if (data.getIssueFieldType7().equalsIgnoreCase("Date") && !data.getIssueFieldLabel7().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel7()));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType7().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel7().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("7"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").replace("*", "").trim(), (data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").contains("*"), data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("7");
                }

            pages.getInteractionsPage().sendComment("Automation Suite");
            Assert.assertTrue(pages.getInteractionsPage().isSaveEnable());
            pages.getInteractionsPage().clickOnSave();
            softAssert.assertTrue(pages.getInteractionsPage().isResolvedFTRDisplayed());
            log.info(pages.getInteractionsPage().getResolvedFTRDisplayed());
            String[] valueToWrite;
            if (!pages.getInteractionsPage().getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticketNumber = pages.getInteractionsPage().getResolvedFTRDisplayed();
                log.info("Ticket Number:ME " + ticketNumber);
                valueToWrite = new String[]{ticketNumber};
                WriteToExcel objExcelFile = new WriteToExcel();
                File excelDir = new File("Excels");
                File excel = new File(excelDir, excelPath);
                log.info("Ticket Number:You " + data.getRowNum());
                objExcelFile.writeTicketNumber(excel.getAbsolutePath(), config.getProperty(SUITE_TYPE + "-NftrSheet"), valueToWrite, data.getRowNum());
                log.info("Ticket Number Written to Excel " + valueToWrite[0]);
            } else {
                softAssert.fail("It's FTR not NFTR");
            }
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            log.info("in catch");
            e.printStackTrace();
            commonLib.error(e.getMessage());
            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
            commonLib.fail(e.getMessage(), true);
            try {
                pages.getInteractionsPage().closeInteractions();
                pages.getInteractionsPage().clickOnContinueButton();
            } catch (NoSuchElementException | TimeoutException ex) {
                softAssert.fail("Unable to close interaction", ex.getCause());
                pages.getInteractionsPage().clickOutside();
                pages.getInteractionsPage().resetInteractionIssue();
            }
            e.printStackTrace();
            Assert.fail(e.getCause().getMessage());
        }
        pages.getInteractionsPage().closeInteractions();
        SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
        SMSHistoryList list = smsHistory.getResult().get(0);
        commonLib.info("Message Sent after Ticket Creation: " + list.getMessageText());
        try {
            softAssert.assertTrue(list.getMessageText().contains(ticketNumber), "Message Sent does not send for same ticket id which has been Create");
            softAssert.assertEquals(list.getSmsType().toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message type is not system");
            softAssert.assertFalse(list.isAction(), "Action button is not disabled");
            softAssert.assertEquals(list.getTemplateName().toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
        } catch (NullPointerException e) {
            softAssert.fail("Not able to validate Message sent to customer or not. " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
