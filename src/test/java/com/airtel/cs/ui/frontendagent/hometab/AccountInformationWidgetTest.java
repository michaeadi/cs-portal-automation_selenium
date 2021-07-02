package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AccountInformationWidget;
import com.airtel.cs.pojo.response.kycprofile.KYCProfile;
import com.airtel.cs.pojo.response.offerdetails.AccountInformation;
import com.airtel.cs.pojo.response.postpaid.PostpaidAccountInformation;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountInformationWidgetTest extends Driver {

    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();

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
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
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
     * This method is used to validate MSISDN
     */

    @Test(priority = 2, groups = {"RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void invalidMSISDNTest() {
        try {
            selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
            pages.getDemoGraphicPage().enterMSISDN("123456789");
            assertCheck.append(actions
                    .assertEqual_stringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Entered customer number is Invalid",
                            "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check whether user has permission for Account Information Widget
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasAccountInformationPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view account information Widget Permission", "description");
            String accountInfo_permission = constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), accountInfo_permission), "Account Information Widget displayed correctly as per user permission", "Account Information Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasAccountInformationPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to show account information widget on the basis of connection type and UM permission
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final String statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqual_stringType(statusCode, "200", "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umPermission = pages.getAccountInformationWidget().isAccountInformationWidgetDisplay();
            assertCheck.append(actions.assertEqual_stringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "User has permission for account information widget", "User doesn't have permission for account information widget"));
            } else if (connectionType.equalsIgnoreCase("POSTPAID") && !umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), false, "User has permission for account information widget", "User doesn't have permission for account information widget"));
            } else if (connectionType.equalsIgnoreCase("PREPAID") && umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "User has permission for account information widget", "User doesn't have permission for account information widget"));

            } else if (connectionType.equalsIgnoreCase("PREPAID") && !umPermission) {
                assertCheck.append(actions.assertEqual_stringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), false, "User has permission for account information widget", "User doesn't have permission for account information widget"));
            } else {
                commonLib.fail(" Account Information Widget is not displayed", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate watermarked in the left corner and middle
     * isAccountInformationWidgetDisplay() -- todo
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, enabled = false)
    public void accountInformationWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate is Account Information Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, " Account information widget displayed", "Account information widget not displayed"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the Your Account Information widget and is correct", "Auuid NOT shown at the footer of Your Account Information widget"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the Your Account Information widget and is correct", "Auuid NOT shown at the middle of Your Account Information widget"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - accountInformationWatermarkTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to verify bill cycle and due date in invoice history
     * To-do
     */

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, enabled = false)
    public void verifyBillCycleAndDueDateInvoiceHistory() {

        try {
            selUtils.addTestcaseDescription("Validating Account Information Widget :" + customerNumber, "description");
            final AccountInformationWidget accountInformationWidget = pages.getAccountInformationWidget();
            //assertCheck.append();


        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyBillCycleAndDueDateInvoiceHistory" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate account informationd detail icon
     * To-do
     */
    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void verifyAccountInfoDetailedIcon() {

        try {
            selUtils.addTestcaseDescription("Validating all data points under Account Information widget like Due date And Total Outstanding And Current Cycle And Current Month Unbilled Amount And last Month Bill Amount And Last Payment Mode And Security Deposit And Account Number", "description");
            PostpaidAccountInformation accountInformation = api.getPostpaidAccountInformation(customerNumber);
            final Integer statusCode = accountInformation.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            /*assertCheck.append(actions.assertEqual_boolean(pages.getAccountInformationWidget().isActionIconVisibleOnAccountInfo(), true, "Account information detailed icon is visible", "Account information detailed icon is not visible"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getDueDate(), "", "Due date displays as expected", "Due date not displays as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getTotalOutstanding(), "", "Total outstanding amount is displays as expected", "Total outstanding amount not  displays as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getCurrentCycle(), "", "Current Cycle displays as expected", "Current Cycle not displays as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmount(), "", "Current month un-bill amount displays as expected", "Current month un-bill amount not displays as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getLastMonthBillAmount(), "", "Last month bill amount displays as expected", "Last month bill amount not displays as expected"));;*/
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getLastPaymentMode(), "", "Last payment mode displays as expected", "Last payment mode not displays as expected"));
            /*assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getSecurityDeposit(), "", "Security deposit displays as expected", "Security deposit not displays as expected"));*/
            assertCheck.append(actions.assertEqual_stringType(pages.getAccountInformationWidget().getAccountNumber(), "", "Account Number displayed as expected", "Account Number not displayed as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);

        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyAccountInfoDetailedIcon()" + e.fillInStackTrace(), true);
        }
    }


}