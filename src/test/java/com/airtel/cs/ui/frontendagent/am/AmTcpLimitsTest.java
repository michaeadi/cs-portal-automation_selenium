package com.airtel.cs.ui.frontendagent.am;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.am.TcpLimitsResponse;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AmTcpLimitsTest extends Driver {
    String customerNumber = null;
    AMProfile amProfile;
    String tcpId = null;
    TcpLimitsResponse tcpLimits;
    boolean isPermissionEnable = false;
    public static final String RUN_TCP_LIMIT_TESTCASE = constants.getValue(ApplicationConstants.RUN_TCP_LIMIT_TESTCASE);
    public static final String TCP_LIMIT_WEB_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_WEB_BEARER);
    public static final String TCP_LIMIT_IVR_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_IVR_BEARER);
    public static final String TCP_LIMIT_USSD_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_USSD_BEARER);
    public static final String TCP_LIMIT_BANK_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_BANK_BEARER);
    public static final String TCP_LIMIT_ALL_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_ALL_BEARER);
    public static final String TCP_LIMIT_WEBSERVICE_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_WEBSERVICE_BEARER);
    public static final String TCP_LIMIT_STK_BEARER = constants.getValue(ApplicationConstants.TCP_LIMIT_STK_BEARER);


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkTcpLimitWidgetFlag() {
        if (!StringUtils.equals(RUN_TCP_LIMIT_TESTCASE, "true")) {
            commonLib.skip("Skipping because Run Tcp Limit Test Case Flag Value is - " + RUN_TCP_LIMIT_TESTCASE);
            throw new SkipException("Skipping because this functionality is not applicable for current Opco");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasPermission() {
        try {
            selUtils.addTestcaseDescription("Validate whether user has AM Profile Details Permission ", "description");
            String permission = constants.getValue(PermissionConstants.AM_PROFILE_DETAILS_PERMISSION);
            isPermissionEnable = UtilsMethods.isUserHasPermission(permission);
            assertCheck.append(actions.assertEqualBoolean(isPermissionEnable, true, "Logged in user has Am Profile Details permission", "Logged in user doesn't has Am Profile Details permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermission " + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Open Tcp Limits
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void openTcpLimits() {
        try {
            selUtils.addTestcaseDescription("Validate all AM Transactions Widget visible ot not ,Open detailed page of Am Transactions widget , Open TCP Limits", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isAmTransactionsWidgetVisible(), true, "Am Transaction Widget is visible", "Am Transaction Widget is NOT visible"));
            if (isPermissionEnable) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMoreIconVisible(), true, "AM Transaction Widget detailed icon  is visible", "AM Transaction Widget detailed icon is NOT visible"));
                pages.getAmTcpLimits().clickMoreIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isAmProfileDetailsTabVisible(), true, "Am Profile Details tab is visible", "Am Profile Details tab is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isAmProfileDetailsDetailWidgetVisible(), true, "Am Profile Details widget is visible", "Am Profile Details widget is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isWalletsVisible(), true, "Wallets is visible", "Wallets is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isTcpLimitsIconVisible(), true, "Icon to open Tcp Limits is visible", "Icon to open Tcp Limits is NOT visible"));
                pages.getAmTcpLimits().clickTcpLLimitsIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isTcpLimitsVisible(), true, "TCP Limits is visible", "TCP Limit is NOT visible"));
            } else
                commonLib.fail("Am Profile Details widget is not visible as user has not permission to view it", true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openTcpLimits" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Open Tcp Limits Layout
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testTcpLimitsLayout() {
        try {
            selUtils.addTestcaseDescription("Validate all the fields are visible or not in TCP Limits ", "description");
            amProfile = api.amServiceProfileAPITest(customerNumber);
            tcpId = amProfile.getResult().getWallets().get(0).getTcpId();
            tcpLimits = api.getTcpLimits(customerNumber, tcpId);
            assertCheck.append(actions.assertEqualIntType(tcpLimits.getStatusCode(), 200, "TCP Limit API Status Code Matched and is :" + tcpLimits.getStatusCode(), "Tcp Limit Status Code NOT Matched and is :" + tcpLimits.getStatusCode(), false));
            if (tcpLimits.getStatusCode() == 200 && tcpLimits.getResult() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else if (tcpLimits.getStatusCode() == 500 && tcpLimits.getResult() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isWidgetErrorMessageVisible(), true, "CS API and widget both are giving error", "CS API is giving error but widget is not showing error message"));
                commonLib.fail("CS API is unable to give Tcp limits  data ", true);
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isTransferProfileDetailsVisible(), true, "Transfer Profile Details is visible", "Transfer Profile Details is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isPaymentMethodThresholdDetailsVisible(), true, "Payment Method Threshold Details is visible", "Payment Method Threshold Details is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isProfileIdVisible(), true, "Profile Id is visible", "Profile Id  is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isCurrencyVisible(), true, "Currency is visible", "Currency is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isProfileNameVisible(), true, "Profile Name is visible", "Profile Name is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMinimumResidualBalanceVisible(), true, "Minimum Residual Balance  is visible", "Minimum Residual Balance  is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMinimumTransferAllowedVisible(), true, "Minimum Transfer Allowed is visible", "Minimum Transfer Allowed is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMaximumTransactionAmountVisible(), true, "Maximum Transaction Amount is visible", "Maximum Transaction Amount is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMaximumBalanceVisible(), true, "Maximum Balance is visible", "Maximum Balance is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isPayeeVisible(), true, "Payee is visible", "Payee  is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isPayerVisible(), true, "Payer is visible", "Payer is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMinimumTransactionAmountVisible(), true, "Minimum Transaction Amount is visible", "Minimum Transaction Amount is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMinimumResidualBalanceVisible(), true, "Minimum Residual Balance  is visible", "Minimum Residual Balance  is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isMinimumTransferAllowedVisible(), true, "Minimum Transfer Allowed is visible", "Minimum Transfer Allowed is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isPayeeTcpLimitsVisible(), true, "Payee Tcp Limits is visible", "Payee Tcp Limits is NOT visible"));
                pages.getAmTcpLimits().clickPayer();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isPayerTcpLimitsVisible(), true, "Payer Tcp Limits is visible", "Payer Tcp Limits is NOT visible"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testTcpLimitsLayout" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check Tcp Limits Data
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testTcpLimitsData() {
        try {
            selUtils.addTestcaseDescription("Validate all the fields are visible or not in TCP Limits ", "description");
            if (tcpLimits.getStatusCode() == 200 && tcpLimits.getResult() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else if (tcpLimits.getStatusCode() == 500 && tcpLimits.getResult() == null) {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isWidgetErrorMessageVisible(), true, "CS API and widget both are giving error", "CS API is giving error but widget is not showing error message"));
                commonLib.fail("CS API is unable to give Tcp limits  data ", true);
            } else {
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getTransferDetailsValue(1), tcpLimits.getResult().getProfileId(), "Profile Id is same as expected ", "Profile Id is NOT same as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getTransferDetailsValue(2), tcpLimits.getResult().getProfileName(), "Profile Name is same as expected ", "Profile Name is NOT same as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getTransferDetailsValue(3), tcpLimits.getResult().getCurrency(), "Currency is same as expected ", "Currency is NOT same as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getThresholdDetailsValue(1), tcpLimits.getResult().getMinResidualBalance(), "Min Residual Balance is same as expected ", "Min Residual Balance is NOT same as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getThresholdDetailsValue(2), tcpLimits.getResult().getMinTxnAmount(), "Min txn amount  is same as expected ", "Min txn amount is NOT same as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getThresholdDetailsValue(3), tcpLimits.getResult().getMaxPctTransferAllowed() + "%", "Max Transfer Allowed is same as expected ", "Max Transfer Allowed is NOT same as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmTcpLimits().getThresholdDetailsValue(4), tcpLimits.getResult().getMaxBalance(), "Max Balance is same as expected ", "Max Balance is NOT same as expected"));
                if (!StringUtils.equals(TCP_LIMIT_WEB_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isWebVisible(), true, "WEB Type Bearer is visible on UI", "WEB Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickWeb();
                } else if (!StringUtils.equals(TCP_LIMIT_USSD_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isUssdVisible(), true, "USSD Type Bearer is visible on UI", "USD Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickUssd();
                } else if (!StringUtils.equals(TCP_LIMIT_WEBSERVICE_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isWebServiceVisible(), true, "WEBSERVICE Type Bearer is visible on UI", "WEBSERVUCE Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickWebService();
                } else if (!StringUtils.equals(TCP_LIMIT_BANK_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isBankVisible(), true, "BANK Type Bearer is visible on UI", "BANK Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickBank();
                } else if (!StringUtils.equals(TCP_LIMIT_ALL_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isAllVisible(), true, "ALL Type Bearer is visible on UI", "ALL Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickAll();
                } else if (!StringUtils.equals(TCP_LIMIT_IVR_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isIvrVisible(), true, "IVR Type Bearer is visible on UI", "IVR Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickIvr();
                } else if (!StringUtils.equals(TCP_LIMIT_STK_BEARER, "true")) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAmTcpLimits().isStkVisible(), true, "STK Type Bearer is visible on UI", "STK Type Bearer is NOT visible on UI"));
                    pages.getAmTcpLimits().clickStk();
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testTcpLimitsData" + e.fillInStackTrace(), true);
        }
    }


}
