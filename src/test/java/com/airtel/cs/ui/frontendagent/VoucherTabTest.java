package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.LoginPOJO;
import com.airtel.cs.pojo.response.voucher.VoucherDetail;
import com.airtel.cs.pojo.response.voucher.VoucherSearchPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class VoucherTabTest extends Driver {

    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @DataProviders.User(userType = "API")
    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, priority = 0)
    public void loginAPI(TestDatabean data) throws JsonProcessingException {
        selUtils.addTestcaseDescription("Validate the Login API with Beta user,Hit the Login API -/auth/api/user-mngmnt/v2/login with valid headers and credentials,Validating Success Message from response", "description");
        final String loginAUUID = constants.getValue(CommonConstants.BETA_USER_AUUID);
        LoginPOJO Req = LoginPOJO.loginBody(loginAUUID, PassUtils.decodePassword(constants.getValue(CommonConstants.BETA_USER_PASSWORD)));
        map.clear();
        pages.getLoginPage().setApiHeader();
        String dtoAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Req);
        commonLib.info("Validating login api with user : " + loginAUUID);
        try {
            final Response response = pages.getLoginPage().loginAPI(dtoAsString);
            String token = "Bearer " + response.jsonPath().getString("result.accessToken");
            map.add(new Header("Authorization", token));
            commonLib.info("Response Body : " + response.asString());
            commonLib.info("Response time : " + response.getTimeIn(TimeUnit.SECONDS) + " s");
            final String message = response.jsonPath().getString("message");
            assertCheck.append(actions.assertEqual_stringType(message, "User authenticated successfully", "User authenticated successfully", message));
        } catch (Exception e) {
            continueExecutionAPI = false;
            commonLib.fail("Exception in Method :- testLoginAPI " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User(userType = "NFTR")
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

    @Test(priority = 2, description = "Validate Voucher Search Test", dependsOnMethods = "openCustomerInteraction")
    public void voucherSearchTest() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Voucher Search Test", "description");
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        String voucherId = data.getVoucherId();
        if (voucherId != null && !voucherId.equals(" ")) {
            pages.getRechargeHistoryWidget().writeVoucherId(voucherId);
            pages.getRechargeHistoryWidget().clickSearchBtn();
            try {
                pages.getVoucherTab().waitTillTimeLineGetsRemoved();
                Assert.assertTrue(pages.getVoucherTab().isVoucherTabOpen(), "Voucher Id does not found");
                VoucherSearchPOJO voucher = api.voucherSearchTest(voucherId);
                VoucherDetail voucherDetail = voucher.getResult();
                if (voucher.getStatusCode() == 200) {
                    softAssert.assertEquals(pages.getVoucherTab().getSerialValue(), voucherDetail.getVoucherId(), "Voucher Serial number is not same as search voucher id");
                    softAssert.assertEquals(pages.getVoucherTab().getStatusValue().toLowerCase().trim(), voucherDetail.getStatus().toLowerCase().trim(), "Voucher Status is not same as voucher status received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getSubStatus().toLowerCase().trim(), voucherDetail.getSubStatus().toLowerCase().trim(), "Voucher Sub Status is not same as voucher Sub Status received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getRechargeAmt().toLowerCase().trim(), voucherDetail.getRechargeAmount().toLowerCase().trim(), "Voucher Recharge amount is not same as voucher Recharge amount received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getTimeStamp().toLowerCase().trim(), voucherDetail.getTimestamp().toLowerCase().trim(), "Voucher Time Stamp is not same as voucher Time Stamp received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getExpiryDate().toLowerCase().trim(), voucherDetail.getExpiryDate().toLowerCase().trim(), "Voucher Expiry date is not same as voucher Expiry date received by api");
                    if (voucherDetail.getSubscriberId() != null)
                        softAssert.assertEquals(pages.getVoucherTab().getSubscriberId().toLowerCase().trim(), voucherDetail.getSubscriberId().toLowerCase().trim(), "Voucher Subscriber Id is not same as voucher Subscriber Id received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getAgent().toLowerCase().trim(), voucherDetail.getAgent().toLowerCase().trim(), "Voucher Agent not same as voucher Agent received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getBatchID().toLowerCase().trim(), voucherDetail.getBatchId().toLowerCase().trim(), "Voucher Batch Id not same as voucher Batch Id received by api");
                    softAssert.assertEquals(pages.getVoucherTab().getVoucherGroup().toLowerCase().trim(), voucherDetail.getVoucherGroup().toLowerCase().trim(), "Voucher group not same as voucher group received by api");
                    pages.getVoucherTab().clickDoneBtn();
                } else {
                    softAssert.fail("com.airtel.cs.API Response is not 200.");
                }
            } catch (TimeoutException | NoSuchElementException e) {
                Thread.sleep(500);
                pages.getVoucherTab().clickDoneBtn();
                softAssert.fail("Not able to validate Voucher tab: " + e.getMessage());
            }
        } else {
            softAssert.fail("Voucher Id does not found in config sheet. Please add voucher in sheet.");
        }
        softAssert.assertAll();
    }
}
