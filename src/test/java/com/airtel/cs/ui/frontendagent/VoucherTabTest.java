package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
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

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class VoucherTabTest extends Driver {

    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
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

    /**
     * This method is used to Validate Voucher Search Test
     * @throws InterruptedException
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void voucherSearchTest() throws InterruptedException {
        selUtils.addTestcaseDescription("Validate Voucher Search Test", "description");
        DataProviders data = new DataProviders();
        String voucherId = data.getVoucherId();
        if (voucherId != null && !voucherId.equals(" ")) {
            pages.getRechargeHistoryWidget().writeVoucherId(voucherId);
            pages.getRechargeHistoryWidget().clickSearchBtn();
            try {
                Assert.assertTrue(pages.getVoucherTab().isVoucherTabOpen(), "Voucher Id does not found");
                VoucherSearchPOJO voucher = api.voucherSearchTest(voucherId);
                VoucherDetail voucherDetail = voucher.getResult();
                if (voucher.getStatusCode() == 200) {
                    assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getSerialValue(), voucherDetail.getVoucherId(), "Voucher Serial number is same as search voucher id", "Voucher Serial number is not same as search voucher id"));
                    assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getStatusValue().toLowerCase().trim(), voucherDetail.getStatus().toLowerCase().trim(), "Voucher Status is same as voucher status received by api", "Voucher Status is not same as voucher status received by api"));
                    assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getSubStatus().toLowerCase().trim(), voucherDetail.getSubStatus().toLowerCase().trim(),"Voucher Sub Status is same as voucher Sub Status received by api", "Voucher Sub Status is not same as voucher Sub Status received by api" ));
                    assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getRechargeAmt().toLowerCase().trim(), voucherDetail.getRechargeAmount().toLowerCase().trim(), "Voucher Recharge amount is same as voucher Recharge amount received by api", "Voucher Recharge amount is not same as voucher Recharge amount received by api"));
                    assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getTimeStamp().toLowerCase().trim(), voucherDetail.getTimestamp().toLowerCase().trim(), "Voucher Time Stamp is same as voucher Time Stamp received by api", "Voucher Time Stamp is not same as voucher Time Stamp received by api"));
                    assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getExpiryDate().toLowerCase().trim(), voucherDetail.getExpiryDate().toLowerCase().trim(), "Voucher Expiry date is same as voucher Expiry date received by api", "Voucher Expiry date is not same as voucher Expiry date received by api"));
                    if (voucherDetail.getSubscriberId() != null)
                        assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getSubscriberId().toLowerCase().trim(), voucherDetail.getSubscriberId().toLowerCase().trim(), "Voucher Subscriber Id is same as voucher Subscriber Id received by api", "Voucher Subscriber Id is not same as voucher Subscriber Id received by api"));
                        assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getAgent().toLowerCase().trim(),voucherDetail.getAgent().toLowerCase().trim(), "Voucher Agent same as voucher Agent received by api", "Voucher Agent not same as voucher Agent received by api" ));
                        assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getBatchID().toLowerCase().trim(), voucherDetail.getBatchId().toLowerCase().trim(), "Voucher Batch Id same as voucher Batch Id received by api", "Voucher Batch Id not same as voucher Batch Id received by api"));
                        assertCheck.append(actions.assertNotEqual_stringType(pages.getVoucherTab().getVoucherGroup().toLowerCase().trim(), voucherDetail.getVoucherGroup().toLowerCase().trim(), "Voucher group same as voucher group received by api", "Voucher group not same as voucher group received by api"));
                    pages.getVoucherTab().clickDoneBtn();
                } else {
                    commonLib.fail("com.airtel.cs.API Response is not 200.", true);
                }
            } catch (TimeoutException | NoSuchElementException e) {
                pages.getVoucherTab().clickDoneBtn();
                commonLib.fail("Not able to validate Voucher tab: " + e.fillInStackTrace(), true);
            }
        } else {
            commonLib.fail("Voucher Id does not found in config sheet. Please add voucher in sheet.", true);
        }

        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
