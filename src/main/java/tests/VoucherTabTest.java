package tests;

import API.APIEndPoints;
import POJO.LoginPOJO;
import POJO.PlansPOJO;
import POJO.Voucher.VoucherDetail;
import POJO.Voucher.VoucherSearchPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.TestDatabean;
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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.util.concurrent.TimeUnit;

import static Utils.ExtentReports.ExtentTestManager.getTest;
import static Utils.ExtentReports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class VoucherTabTest extends BaseTest {

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
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        customerInteractionPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Voucher Search Test")
    public void voucherSearchTest() throws InterruptedException {
        ExtentTestManager.startTest("Validate Voucher Search Test", "Validate Voucher Search Test");
        SoftAssert softAssert = new SoftAssert();
        RechargeHistoryWidgetPOM rechargeHistory = new RechargeHistoryWidgetPOM(driver);
        rechargeHistory.writeVoucherId("000106222035384");
        VoucherTabPOM voucherTab = rechargeHistory.clickSearchBtn();
        try {
            voucherTab.waitTillTimeLineGetsRemoved();
            Assert.assertTrue(voucherTab.isVoucherTabOpen(), "Voucher Id not found");
            VoucherSearchPOJO voucher = api.voucherSearchTest("000106222035384");
            VoucherDetail voucherDetail = voucher.getResult();
            if(voucher.getStatus().equalsIgnoreCase("200")) {
                softAssert.assertEquals(voucherTab.getSerialValue(), voucherDetail.getVoucherId(), "Voucher Serial number is not same as search voucher id");
                softAssert.assertEquals(voucherTab.getStatusValue().toLowerCase().trim(), voucherDetail.getStatus().toLowerCase().trim(), "Voucher Status is not same as voucher status received by api");
                softAssert.assertEquals(voucherTab.getSubStatus().toLowerCase().trim(), voucherDetail.getSubStatus().toLowerCase().trim(), "Voucher Sub Status is not same as voucher Sub Status received by api");
                softAssert.assertEquals(voucherTab.getRechargeAmt().toLowerCase().trim(), voucherDetail.getRechargeAmount().toLowerCase().trim(), "Voucher Recharge amount is not same as voucher Recharge amount received by api");
                softAssert.assertEquals(voucherTab.getTimeStamp().toLowerCase().trim(), voucherDetail.getTimestamp().toLowerCase().trim(), "Voucher Time Stamp is not same as voucher Time Stamp received by api");
                softAssert.assertEquals(voucherTab.getExpiryDate().toLowerCase().trim(), voucherDetail.getExpiryDate().toLowerCase().trim(), "Voucher Expiry date is not same as voucher Expiry date received by api");
                if (voucherDetail.getSubscriberId() != null)
                    softAssert.assertEquals(voucherTab.getSubscriberId().toLowerCase().trim(), voucherDetail.getSubscriberId().toLowerCase().trim(), "Voucher Subscriber Id is not same as voucher Subscriber Id received by api");
                softAssert.assertEquals(voucherTab.getAgent().toLowerCase().trim(), voucherDetail.getAgent().toLowerCase().trim(), "Voucher Agent not same as voucher Agent received by api");
                softAssert.assertEquals(voucherTab.getBatchID().toLowerCase().trim(), voucherDetail.getBatchId().toLowerCase().trim(), "Voucher Batch Id not same as voucher Batch Id received by api");
                softAssert.assertEquals(voucherTab.getVoucherGroup().toLowerCase().trim(), voucherDetail.getVoucherGroup().toLowerCase().trim(), "Voucher group not same as voucher group received by api");
                voucherTab.clickDoneBtn();
            }else{
                softAssert.fail("API Response is not 200.");
            }
        }catch (TimeoutException | NoSuchElementException e){
            Thread.sleep(500);
            voucherTab.clickDoneBtn();
            softAssert.fail("Not able to validate Voucher tab: "+e.getMessage());
        }
        softAssert.assertAll();
    }

}
