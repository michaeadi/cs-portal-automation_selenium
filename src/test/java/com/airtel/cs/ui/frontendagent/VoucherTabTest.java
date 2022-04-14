package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.request.VoucherRechargeRequest;
import com.airtel.cs.model.cs.response.voucher.VoucherDetail;
import com.airtel.cs.model.cs.response.voucher.VoucherRechargeResponse;
import com.airtel.cs.model.cs.response.voucher.VoucherSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.airtel.cs.commonutils.utils.UtilsMethods.stringNotNull;

public class VoucherTabTest extends Driver {

    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    ObjectMapper mapper = new ObjectMapper();
     String customerNumber,voucherId=null;
    VoucherDetail voucherDetail;

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
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Validate Voucher Search Test
     *
     * @throws InterruptedException
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void voucherSearchTest() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Validate Voucher Search Test", "description");
            DataProviders data = new DataProviders();
            voucherId = data.getVoucherId();
            if (voucherId != null && !voucherId.equals(" ")) {
                pages.getRechargeHistoryWidget().writeVoucherId(voucherId);
                pages.getRechargeHistoryWidget().clickSearchBtn();
                VoucherSearch voucher = api.voucherSearchTest(voucherId);
                 voucherDetail = voucher.getResult();
                assertCheck.append(actions.assertEqualBoolean(pages.getVoucherTab().isVoucherTabOpen(), !stringNotNull(voucher.getApiErrors().getVoucherDetail()).equalsIgnoreCase(constants.getValue("cs.voucher.detail.api.error")), "Voucher Id does found", "Voucher Id does not found but pop up display", true));
                if (voucher.getStatusCode() == 200) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getSerialValue(), voucherDetail.getVoucherId(), "Voucher Serial number is same as search voucher id", "Voucher Serial number is not same as search voucher id"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getStatusValue(), voucherDetail.getStatus(), "Voucher Status is same as voucher status received by api", "Voucher Status is not same as voucher status received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getSubStatus(), voucherDetail.getSubStatus(), "Voucher Sub Status is same as voucher Sub Status received by api", "Voucher Sub Status is not same as voucher Sub Status received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getRechargeAmt(), voucherDetail.getRechargeAmount(), "Voucher Recharge amount is same as voucher Recharge amount received by api", "Voucher Recharge amount is not same as voucher Recharge amount received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getTimeStamp(), voucherDetail.getTimestamp(), "Voucher Time Stamp is same as voucher Time Stamp received by api", "Voucher Time Stamp is not same as voucher Time Stamp received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getExpiryDate(), voucherDetail.getExpiryDate(), "Voucher Expiry date is same as voucher Expiry date received by api", "Voucher Expiry date is not same as voucher Expiry date received by api"));
                    if (voucherDetail.getSubscriberId() != null)
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getSubscriberId(), voucherDetail.getSubscriberId(), "Voucher Subscriber Id is same as voucher Subscriber Id received by api", "Voucher Subscriber Id is not same as voucher Subscriber Id received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getAgent(), voucherDetail.getAgent(), "Voucher Agent same as voucher Agent received by api", "Voucher Agent not same as voucher Agent received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getBatchID(), voucherDetail.getBatchId(), "Voucher Batch Id same as voucher Batch Id received by api", "Voucher Batch Id not same as voucher Batch Id received by api"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getVoucherTab().getVoucherGroup(), voucherDetail.getVoucherGroup(), "Voucher group same as voucher group received by api", "Voucher group not same as voucher group received by api"));
                    pages.getVoucherTab().clickDoneBtn();
                } else {
                    commonLib.fail("Search Voucher API Response is not 200.", true);
                }
            } else {
                commonLib.fail("Voucher Id does not found in config sheet. Please add voucher in sheet.", true);
            }
        } catch (Exception e) {
            if (pages.getVoucherTab().isVoucherTabOpen())
                pages.getVoucherTab().clickDoneBtn();
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " voucherSearchTest" + e.fillInStackTrace(), true);
        }

        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * Osc voucher recharge test.
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "voucherSearchTest")
    public void oscVoucherRechargeTest() {
        try {
            selUtils.addTestcaseDescription("Validate OSC Voucher Recharge Test", "description");
            DataProviders data = new DataProviders();
            String voucherId = data.getVoucherId();
            VoucherRechargeRequest voucherRechargeRequest = new VoucherRechargeRequest();
            voucherRechargeRequest.setVoucherNumber(voucherId);
            voucherRechargeRequest.setKey(CommonConstants.RECHARGE);
            voucherRechargeRequest.setMsisdn(constants.getValue(ApplicationConstants.CUSTOMER_MSISDN));
            Response response = api.voucherRechargeTest(voucherRechargeRequest);
            final int statusCode = response.getStatusCode();
            VoucherRechargeResponse voucherRechargeResponse = response.as(VoucherRechargeResponse.class);
            final String message = voucherRechargeResponse.getMessage();
            final String status = voucherRechargeResponse.getStatus();
            if (statusCode == 200) {
                assertCheck.append(actions.assertEqualStringNotNull(message, "Voucher message null check pass" , "Voucher message null check fail"));
                assertCheck.append(actions.assertEqualStringNotNull(status, "Voucher status null check pass" , "Voucher status null check fail"));
            } else {
                commonLib.fail("Voucher Recharge API Response is not 200.", true);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " oscVoucherRechargeTest " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction","oscVoucherRechargeTest"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing OSC Recharge", "description");
            pages.getVoucherTab().goToActionTrail();
            assertCheck.append(actions.assertEqualStringType(pages.getVoucherTab().getActionType(), "OSC Recharge", "Action type for OSC Recharge is expected", "Action type for OSC Recharge is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getVoucherTab().getReason(), "Customer Request", "Reason for OSC Recharge is expected", "Reason for OSC Recharge is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getVoucherTab().getComment(), constants.getValue(ApplicationConstants.COMMENT), "Comment for OSC Recharge is expected", "Comment for OSC Recharge is not as expected"));
            pages.getVoucherTab().clickingOnDropDown();
            assertCheck.append(actions.assertEqualStringType(pages.getVoucherTab().getRechargeAmount().trim(), voucherDetail.getRechargeAmount(), "Recharge Amount rendered as expected in action trail's meta info", "Recharge Amount NOT rendered as expected in action trail's meta info"));
            assertCheck.append(actions.assertEqualStringType(pages.getVoucherTab().getRechargeMsisdn().trim(), customerNumber, "Recharged Msisdn rendered as expected in action trail's meta info", "Recharged Msisdn rendered as expected in action trail's meta info"));
            assertCheck.append(actions.assertEqualStringType(pages.getVoucherTab().getVoucherNumber().trim(), voucherId, "Voucher Number rendered as expected in action trail's meta info", "Voucher Number rendered as expected in action trail's meta info"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }
}
