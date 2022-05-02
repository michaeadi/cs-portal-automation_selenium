package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.request.VoucherRechargeRequest;
import com.airtel.cs.model.cs.response.voucher.VoucherDetail;
import com.airtel.cs.model.cs.response.voucher.VoucherRechargeResponse;
import com.airtel.cs.model.cs.response.voucher.VoucherSearch;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class OscRechargeTest extends Driver {

    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();
    String customerNumber, voucherId = null;
    boolean isRechargePermissionEnabled = false;
    VoucherSearch voucher;

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
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_PREPAID_MSISDN);
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
     * This method is used to check permission
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void permissionCheckTest() {
        try {
            selUtils.addTestcaseDescription("Validate whether user has OSC Recharge Permission ", "description");
            String rechargePermission = constants.getValue(PermissionConstants.OSC_RECHARGE_PERMISSION);
            isRechargePermissionEnabled = UtilsMethods.isUserHasPermission(rechargePermission);
            assertCheck.append(actions.assertEqualBoolean(isRechargePermissionEnabled, true, "Logged in user has OSC Recharge permission", "Logged in user doesn't has OSC Recharge permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - permissionCheckTest " + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Validate Voucher Search Test
     *
     * @throws InterruptedException
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void voucherSearchTest() throws InterruptedException {
        try {
            selUtils.addTestcaseDescription("Validate Voucher Search Test", "description");
            if (isRechargePermissionEnabled) {
                //DataProviders data = new DataProviders();
                voucherId = constants.getValue(ApplicationConstants.OSC_VOUCHER);
                if (voucherId != null && !voucherId.equals(" ")) {
                    pages.getOscRecharge().searchVoucher(voucherId);
                     voucher = api.voucherDetail(voucherId);
                    assertCheck.append(actions.assertEqualIntType(voucher.getStatusCode(), 200, "Voucher Detail API status code matched and is :" + voucher.getStatusCode(), "Voucher Detail API status code NOT matched  and is :" + voucher.getStatusCode(), false));
                    if (voucher.getStatusCode() == 200) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getSerialValue(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getVoucherId()), "Voucher Serial number is same as search voucher id", "Voucher Serial number is not same as search voucher id"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getStatusValue(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getStatus()), "Voucher Status is same as voucher status received by api", "Voucher Status is not same as voucher status received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getSubStatus(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getSubStatus()), "Voucher Sub Status is same as voucher Sub Status received by api", "Voucher Sub Status is not same as voucher Sub Status received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getRechargeAmt(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getRechargeAmount()), "Voucher Recharge amount is same as voucher Recharge amount received by api", "Voucher Recharge amount is not same as voucher Recharge amount received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getTimeStamp(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getTimestamp()), "Voucher Time Stamp is same as voucher Time Stamp received by api", "Voucher Time Stamp is not same as voucher Time Stamp received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getExpiryDate(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getExpiryDate()), "Voucher Expiry date is same as voucher Expiry date received by api", "Voucher Expiry date is not same as voucher Expiry date received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getAgent(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getAgent()), "Voucher Agent same as voucher Agent received by api", "Voucher Agent not same as voucher Agent received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getBatchID(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getBatchId()), "Voucher Batch Id same as voucher Batch Id received by api", "Voucher Batch Id not same as voucher Batch Id received by api"));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getOscRecharge().getVoucherGroup(), pages.getDemoGraphicPage().getKeyValueAPI(voucher.getResult().getVoucherGroup()), "Voucher group same as voucher group received by api", "Voucher group not same as voucher group received by api"));
                        pages.getOscRecharge().clickCrossIcon();
                    } else
                        commonLib.fail("Search Voucher API Response is not 200.", true);
                } else
                    commonLib.fail("Voucher Id does not found in config sheet. Please add voucher in sheet.", true);
            } else
                commonLib.fail("User doesn't has permission to perform OSC Recharge", true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            if (pages.getOscRecharge().isVoucherTabOpen())
                pages.getOscRecharge().clickCancelBtn();
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " voucherSearchTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * Osc voucher recharge API test.
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"})
    public void oscVoucherRechargeApiTest() {
        try {
            selUtils.addTestcaseDescription("Validate OSC Voucher Recharge API Test", "description");
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
                assertCheck.append(actions.assertEqualStringNotNull(message, "Voucher message null check pass", "Voucher message null check fail"));
                assertCheck.append(actions.assertEqualStringNotNull(status, "Voucher status null check pass", "Voucher status null check fail"));
            } else {
                commonLib.fail("Voucher Recharge API Response is not 200.", true);
            }
        } catch (Exception e) {
            commonLib.fail(constants.getValue("cs.portal.test.fail") + " oscVoucherRechargeApiTest " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * Osc voucher recharge test (from UI)
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "voucherSearchTest")
    public void performOscVoucherRecharge() {
        try {
            selUtils.addTestcaseDescription("Perform OSC Voucher Recharge ", "description");
            VoucherDetail response = apiEsb.callVoucherDetails(voucherId);
            String pin = pages.getOscRecharge().splitActivationCode(response.getActivationCode());
            pages.getOscRecharge().performOscRecharge(pin, voucherId);
            assertCheck.append(actions.assertEqualBoolean(pages.getOscRecharge().isSuccessPopUpVisible(), true, "Success Popup visible after performing voucher recharge", "Success Popup NOT visible after performing voucher recharge"));
            String successText = "Voucher has been  successfully recharged";
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getSuccessText(), successText, "Success text displayed as expected", "Success text not displayed as expected"));
            pages.getPinReset().clickCrossIcon();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - performOscVoucherRecharge" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 6, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction", "performOscVoucherRecharge"})
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Validating entry should be captured in Action Trail after performing OSC Recharge", "description");
            pages.getOscRecharge().goToActionTrail();
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getActionType(), "OSC Recharge", "Action type for OSC Recharge is expected", "Action type for OSC Recharge is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getReason(), "Customer Request", "Reason for OSC Recharge is expected", "Reason for OSC Recharge is not as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getComment(), constants.getValue(ApplicationConstants.COMMENT), "Comment for OSC Recharge is expected", "Comment for OSC Recharge is not as expected"));
            pages.getOscRecharge().clickingOnDropDown();
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getRechargeAmount().trim(), voucher.getResult().getRechargeAmount(), "Recharge Amount rendered as expected in action trail's meta info", "Recharge Amount NOT rendered as expected in action trail's meta info"));
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getRechargeMsisdn().trim(), customerNumber, "Recharged Msisdn rendered as expected in action trail's meta info", "Recharged Msisdn rendered as expected in action trail's meta info"));
            assertCheck.append(actions.assertEqualStringType(pages.getOscRecharge().getVoucherNumber().trim(), voucherId, "Voucher Number rendered as expected in action trail's meta info", "Voucher Number rendered as expected in action trail's meta info"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
        }
    }
}
