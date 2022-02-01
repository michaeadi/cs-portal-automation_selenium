package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.PaymentRequest;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AccountInformationWidgetTest extends Driver {

    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();
    public static Response response;
    private final BaseActions actions = new BaseActions();
    List<String> postpaidAccountInformation;
    String comments = "Adding comment using Automation";
    String customerAccountNumber = null;
    PaymentRequest paymentRequest = new PaymentRequest();

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
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to check whether user has permission for Account Information Widget
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasAccountInformationPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view account information Widget Permission", "description");
            String accountInfo_permission = constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), accountInfo_permission), "Account Information Widget displayed correctly as per user permission", "Account Information Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasAccountInformationPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to show account information widget on the basis of connection type and UM permission
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void connectionTypeAndUMPermissionTest() {
        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umPermission = pages.getAccountInformationWidget().isAccountInformationWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "User has permission for account information widget", "User doesn't have permission for account information widget"));
            } else if (connectionType.equalsIgnoreCase("POSTPAID") && !umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "POSTPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), false, "User has permission for account information widget", "User doesn't have permission for account information widget"));
            } else if (connectionType.equalsIgnoreCase("PREPAID") && umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "User has permission for account information widget", "User doesn't have permission for account information widget"));

            } else if (connectionType.equalsIgnoreCase("PREPAID") && !umPermission) {
                assertCheck.append(actions.assertEqualStringType(connectionType, "PREPAID", "Valid connection type found", "Invalid connection type found"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), false, "User has permission for account information widget", "User doesn't have permission for account information widget"));
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
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void accountInformationWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate is Account Information Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, " Account information widget displayed", "Account information widget not displayed"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the Your Account Information widget and is correct", "Auuid NOT shown at the footer of Your Account Information widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the Your Account Information widget and is correct", "Auuid NOT shown at the middle of Your Account Information widget"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - accountInformationWatermarkTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to call validate UI parameter values with ESB response
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void callingESBCustomerProfileAPI() {
        try {
            selUtils.addTestcaseDescription("Calling customer profile api to get customer account number", "description");

            /**
             * Calling customer profile api to get customer account number
             */
            CustomerProfileResponse customerProfileResponse = apiEsb.customerProfileResponse(customerNumber);
            final String customerProfileStatus = customerProfileResponse.getStatus();
            if (customerProfileStatus.trim().equalsIgnoreCase("ACTIVE")) {
                commonLib.info("Customer Account Number from esb: " + customerProfileResponse.getCustomerAccountNumber());
                customerAccountNumber = customerProfileResponse.getCustomerAccountNumber();

            } else if (customerProfileStatus.trim().equalsIgnoreCase("500")) {
                commonLib.info("Internal server error");
            } else {
                commonLib.info("Customer profile V2 downstream api not working");
            }

            /**
             * Setting payment request object
             */

            paymentRequest.setAccountNo(customerAccountNumber);
            paymentRequest.setLimit(constants.getValue(CommonConstants.PAYMENT_REQUEST_LIMIT));
            paymentRequest.setOffset(constants.getValue(CommonConstants.PAYMENT_REQUEST_OFFSET));


            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyESBParamWithUI" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to validate other tab and email id
     */
    @Test(priority = 6, groups = {"SanityTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"}, enabled = false)
    public void otherTabDisplay() {
        try {
            selUtils.addTestcaseDescription("Verify that Other tab and email id should be visible", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isOthersTabVisible(), true, "Others tab visible as expected", "Others tab not visible as expected"));
            pages.getAccountInformationWidget().openOthersTab();
            /**
             * Calling ESB api customer profile v2 to get email id
             */
            CustomerProfileResponse customerProfileResponse = apiEsb.customerProfileResponse(customerNumber);
            final String status = customerProfileResponse.getStatus();
            if (status.trim().equalsIgnoreCase("ACTIVE")) {
                if (Objects.nonNull(pages.getAccountInformationWidget().getEmailId()) && !pages.getAccountInformationWidget().getEmailId().isEmpty()) {
                    commonLib.info("Email from esb: " + customerProfileResponse.getEmail().trim());
                    commonLib.info("Email on portal : " + pages.getAccountInformationWidget().getEmailId());
                    assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getEmailId(), customerProfileResponse.getEmail().trim(), "Email ID display as per ESB response", "Email ID display is not as per ESB response"));
                }
            } else if (status.trim().equalsIgnoreCase("500")) {
                commonLib.info("Internal server error");
            } else {
                commonLib.info("Customer profile V2 downstream api not working");
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - otherTabDisplay" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifyAccountNumber() throws IOException, ParseException {
        try {
            selUtils.addTestcaseDescription("Validate if Account Number Visible?, Validate Account Number is Bolder", "description");
            postpaidAccountInformation = api.getPostpaidAccountInformation(customerNumber, customerAccountNumber, paymentRequest);
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "customerAccountNumber", "statusCode"), "200", "Status Code for Postpaid Account Information API to get AccountNumber Matched", "Status Code for Postpaid Account Information API to get AccountNumber NOT Matched", false));
            final String accountNumber = pages.getAccountInformationWidget().getAccountNumber();
            assertCheck.append(actions.assertEqualStringType(accountNumber, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "customerAccountNumber", "customerAccountNumber"), "Account Number displayed as expected and is :" + accountNumber, "Account Number not displayed as expected and is :" + accountNumber));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getAccountNumberStyle(), "Bold", "Account Number is in Bold State", "Account Number NOT in Bold state"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyAccountNumber()" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifySecurityDeposit() {
        try {
            selUtils.addTestcaseDescription("Validate Security Deposit is visible,Validate Security Deposit Currency Color,Validate upto 2 places after decimal", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "securityDeposit", "statusCode"), "200", "Status Code for Postpaid Account Information API to get Security Deposit Matched", "Status Code for Postpaid Account Information API to get Security Deposit NOT Matched", false));
            final String securityDeposit = pages.getAccountInformationWidget().getSecurityDeposit();
            assertCheck.append(actions.assertEqualStringType(securityDeposit, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "securityDeposit", "securityDeposit"), "Security deposit displays as expected and is :" + securityDeposit, "Security deposit NOT displays as expected and is :" + securityDeposit));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getSecurityDepositCurrencyStyle(), "#ed1c24", "Color for Security Deposit Currency Data Point Matched", "Color for Security Deposit Currency Data Point NOT Matched"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifySecurityDeposit()" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifyLastPaymentMode() throws IOException, ParseException {
        try {
            selUtils.addTestcaseDescription("Validate Last Payment Mode, Date and Amount are visible", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastPaymentAmount", "statusCode"), "200", "Status Code for Postpaid Account Information API to get Last Payment Mode Matched", "Status Code for Postpaid Account Information API to get Last Payment Mode NOT Matched", false));
            final String lastPaymentMode = pages.getAccountInformationWidget().getLastPaymentMode();
            assertCheck.append(actions.assertEqualStringType(lastPaymentMode, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastPaymentMode", "lastPaymentMode"), "Last payment mode displays as expected and is :" + lastPaymentMode, "Last payment mode not displays as expected and is :" + lastPaymentMode));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastPaymentModeStyle(), "Bold", "Last Payment Mode is in Bold State", "Last Payment Mode NOT in Bold state"));
            final String lastPaymentAmount=pages.getAccountInformationWidget().getLastPaymentAmount();
            assertCheck.append(actions.assertEqualStringType(lastPaymentAmount,pages.getAccountInformationWidget().getValue(postpaidAccountInformation,"lastPaymentAmount","lastPaymentAmount"),"Last Payment amount displayed as expected and is :" + lastPaymentAmount, "Last Payment amount not displayed as expected and is : " + lastPaymentAmount));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastPaymentAmountStyle(), "Bold", "Last Payment Amount is in Bold State", "Last Payment Amount is  NOT in Bold state"));
            final String lastPaymentDate=pages.getAccountInformationWidget().getLastPaymentDate();
            assertCheck.append(actions.assertEqualStringType(lastPaymentDate,pages.getAccountInformationWidget().getValue(postpaidAccountInformation,"lastPaymentDate","lastPaymentDate"),"Last Payment date displayed as expected and is :" + lastPaymentDate, "Last Payment date not displayed as expected and is : " + lastPaymentDate));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyLastPaymentMode()" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 10 , groups ={"SanityTest","RegressionTest","ProdTest"}, dependsOnMethods ={"isUserHasAccountInformationPermission"})
    public void verifyUnbilledAmount() {
        try {
            selUtils.addTestcaseDescription("Validate Unbilled Amount Should be Bifurcated in Data, Voice, SMS, Data ","description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmount", "statusCode"), "200", "Postpaid Account Information API 1 Status Code Matched", "Postpaid Account Information API 1 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmountForCalls(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmountForCalls", "currentMonthUnbilledAmountForCalls"), "Current month un-billed amount for calls displays as expected", "Current month un-billed amount for calls not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmountForSms(),  pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmountForSms", "currentMonthUnbilledAmountForSms"), "Current month un-billed amount for sms displays  as expected", "Current month un-billed amount for sms not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmountForData(),  pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmountForData", "currentMonthUnbilledAmountForData"), "Current month un-billed amount for data displays as expected", "Current month un-billed amount for data not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmountForOthers(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmountForOthers", "currentMonthUnbilledAmountForOthers"), "Current month un-billed amount for others displays as expected", "Current month un-billed amount for others not displays as expected"));

        }catch (Exception e) {
            commonLib.fail("Exception in Method - verifyUnbilledAmount()" + e.fillInStackTrace(), true);
        }
    }




    /**
     * This method is used to validate currency on account info widget
     */
    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifyCurrency() {
        try {
            selUtils.addTestcaseDescription("Validating currency on account information widget", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalCreditLmtCurrencyStyle(), "#ed1c24", "Color for Total Credit Limit Currency Data Point Matched", "Color for Total Credit Limit Currency Data Point NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getAvlCreditLmtCurrencyStyle(), "#ed1c24", "Color for Available Credit Limit Currency Data Point Matched", "Color for Available Credit Limit Currency Data Point NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnbillCurrencyStyle(), "#ed1c24", "Color for Current Month Un-billed Currency Data Point Matched", "Color for Current Month Un-billed Currency Data Point NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastMonthBillAmountCurrencyStyle(), "#ed1c24", "Color for Last Month Bill amount Currency Data Point Matched", "Color for Last Month Bill amount Currency Data Point NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastPaymentModeCurrencyStyle(), "#ed1c24", "Color for Last payment mode Currency Data Point Matched", "Color for Last payment mode Currency Data Point NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalOutstandingCurrencyStyle(), "#ed1c24", "Color for Total outstanding Currency Data Point Matched", "Color for Total outstanding Currency Data Point NOT Matched"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyCurrency()" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate bold text
     */
    @Test(priority = 11, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifyBoldText() {
        try {
            selUtils.addTestcaseDescription("Validating font family on account information widget", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTtlCreditLimitNumberStyle(), "Bold", "Total Credit Limit is in Bold State", "Total Credit Limit NOT in Bold state"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getAvlCreditLimitNumberStyle(), "Bold", "Available Credit Limit is in Bold State", "Available Credit Limit NOT in Bold state"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalOutstandingLimitNumberStyle(), "Bold", "Total outstanding is in Bold State", "Total outstanding NOT in Bold state"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getDueDateNumberStyle(), "Bold", "Due date is in Bold State", "Due date NOT in Bold state"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentCycleNumberStyle(), "Bold", "Current cycle is in Bold State", "Current cycle NOT in Bold state"));

        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyBoldText()" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate account information detail icon
     */
    @Test(priority = 12, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifyAccountInfoDetailedIcon() {

        try {
            selUtils.addTestcaseDescription("Validating all data points under Account Information widget like Due date And Total Outstanding And Current Cycle And Current Month Unbilled Amount And last Month Bill Amount And Last Payment Mode And Security Deposit And Account Number", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "availableCreditLimit", "statusCode"), "200", "Postpaid Account Information API 2 Status Code Matched", "Postpaid Account Information API 2 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastMonthBilledAmount", "statusCode"), "200", "Postpaid Account Information API 3 Status Code Matched", "Postpaid Account Information API 3 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "customerAccountNumber", "statusCode"), "200", "Postpaid Account Information API 4 Status Code Matched", "Postpaid Account Information API 4 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalOutstanding(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "totalOutstandingAmount", "totalOutstandingAmount"), "Total outstanding amount displays as expected", "Total outstanding amount not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getDueDate(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "dueDate", "dueDate"), "Due date displays as expected", "Due date not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentCycle(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentCycle", "currentCycle"), "Current cycle displays as expected", "Current cycle not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastMonthBillAmount(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastMonthBilledAmount", "lastMonthBilledAmount"), "Last month bill amount displays as expected", "Last month bill amount not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastMonthBillDate(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "dueDate", "dueDate"), "Last month bill date displays as expected", "Last month bill date not displays as expected"));

            String totalCreditLimit = pages.getAccountInformationWidget().getTotalCreditLimit();
            double ttlCreditLmt = Double.parseDouble(totalCreditLimit);
            String availCreditLimit = pages.getAccountInformationWidget().getAvailCreditLimit();
            double avlCreditLmt = 0.00d;
            avlCreditLmt = Double.parseDouble(availCreditLimit);
            double creditLimitUsed = 0.00d;
            creditLimitUsed = ttlCreditLmt - avlCreditLmt;

            String creditLmtUsedFromUI = pages.getAccountInformationWidget().getCreditLmtUsed();
            double creditLimitUsedUIValue = Double.parseDouble(creditLmtUsedFromUI);

            double creditLmtUsd = pages.getAccountInformationWidget().getCreditLimitUsed(creditLimitUsedUIValue, ttlCreditLmt);
            commonLib.info("creditLmtUsd : " + creditLmtUsd);
            double doubleMaxVal = 100;
            double availCreditLmt = pages.getAccountInformationWidget().getAvailLimit(doubleMaxVal, creditLimitUsedUIValue, ttlCreditLmt);

            String usedCreditLimitOnProgressBar = String.valueOf(creditLimitUsed);
            String usedCreditLimitCalculated = String.valueOf(creditLmtUsd);

            String availCreditLimitUI = String.valueOf(avlCreditLmt);
            String availCreditLimitCalculated = String.valueOf(availCreditLmt);
            commonLib.info("usedCreditLimitCalculated : " + usedCreditLimitCalculated);
            assertCheck.append(actions.assertEqualStringType(totalCreditLimit, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "totalCreditLimit", "totalCreditLimit"), "Progress bar that denotes Total Credit limit is displayed as expected", "Progress bar that denotes Total Credit limit not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(availCreditLimit, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "availableCreditLimit", "availableCreditLimit"), "Available Credit limit is displayed as expected", "Available Credit limit is not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTwoDecimalValue(usedCreditLimitOnProgressBar), pages.getAccountInformationWidget().getTwoDecimalValue(usedCreditLimitCalculated), "Red portion of the progress bar denotes the Used Credit Limit is displayed as expected", "Red portion of the progress bar denotes the Used Credit Limit is not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(availCreditLimitUI, availCreditLimitCalculated, "Non-Red portion of the progress bar denotes the Used Credit Limit is displayed as expected", "Non-Red portion of the progress bar denotes the Used Credit Limit is not displayed as expected"));

            String tempCreditLimitAPI = pages.getAccountInformationWidget()
                    .getValue(postpaidAccountInformation, "tempCreditLimit", "tempCreditLimit");
            if (StringUtils.isEmpty(tempCreditLimitAPI)) {
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTempCreditiLimit(), "-", "Temp credit limit displays as expected", "Temp credit limit not displays as expected"));
            } else {
                double tempCreditLimit = Double.parseDouble(pages.getAccountInformationWidget().getTempCreditiLimit());
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTempCreditCurrency().toLowerCase(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currency", "currency").toLowerCase(), "Temp credit currency displays as expected", "Temp credit currency not displays as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTempCreditiLimit(), tempCreditLimitAPI, "Temp credit limit displays as expected", "Temp credit limit not displays as expected"));
                if (tempCreditLimit > 0) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isTempCreditLimitInfoVisible(), true, "Temp credit info icon displays as expected", "Temp credit info icon not displays as expected"));
                    pages.getAccountInformationWidget().hoverOnTempCreditLimitInfoIcon();
                    String validTillDate = pages.getAccountInformationWidget().getValidTilldate();
                    assertCheck.append(actions.assertEqualStringType(validTillDate, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "tempCreditValidity", "tempCreditValidity"), "Validate till date is same as bill End date of downstream api", "Vallid till date is not same as bill End date of downstream api"));
                    assertCheck.append(actions.assertEqualStringType(validTillDate, pages.getAccountInformationWidget().getCurrentCycleEndDate(), "Validate till date is same as Current Cycle End date as expected", "Validate till date is not same as Current Cycle End date as expected"));
                } else {
                    assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isTempCreditLimitInfoVisible(), false, "Temp credit info icon not displays as expected", "Temp credit info icon displays as not expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyAccountInfoDetailedIcon()" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 13, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void decimalCountOnUI() {
        try {
            selUtils.addTestcaseDescription("Validating the decimal values up to 2", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().getDecimalValue(pages.getAccountInformationWidget().getTotalCreditLimit()), true, "Decimal value for credit limit on UI is 2", "Decimal value for credit limit on UI is not 2"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().getDecimalValue(pages.getAccountInformationWidget().getAvailCreditLimit()), true, "Decimal value for available credit limit on UI is 2", "Decimal value for available credit limit on UI is not 2"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().getDecimalValue(pages.getAccountInformationWidget().getSecurityDeposit()), true, "Decimal value for security deposit on UI is 2", "Decimal value for security deposit on UI is not 2"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().getDecimalValue(pages.getAccountInformationWidget().getLastMonthBillAmount()), true, "Decimal value for last month bill amount on UI is 2", "Decimal value for last month bill amount on UI is not 2"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().getDecimalValue(pages.getAccountInformationWidget().getTotalOutstanding()), true, "Decimal value for total outstanding on UI is 2", "Decimal value for total outstanding on UI is not 2"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 14, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void validateRaiseSRButton() {
        try {
            selUtils.addTestcaseDescription("Validating Total Credit limit have SR Icon must be visible,After clicking on SR Icon Validate Issue pop modal opened,Validate Account number field displayed,validate Comment box displayed,validate submit button disabled as mandatory field does not filled,Validate Cancel button displayed", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isSRIconDisplay(), true, constants.getValue("cs.account.information.sr.icon.found"), constants.getValue("cs.account.information.sr.icon.not.found"), true));
            String accountNumber = pages.getAccountInformationWidget().getAccountNumber();
            pages.getAccountInformationWidget().clickSRRaiseIcon();
            boolean modalOpen = pages.getAccountInformationWidget().isIssueDetailPopUpDisplay();
            if (modalOpen) {
                assertCheck.append(actions.assertEqualBoolean(modalOpen, true, constants.getValue("create.sr.popup.open"), constants.getValue("create.sr.popup.not.open")));
                assertCheck.append(actions.matchUiAndAPIResponse(accountNumber, pages.getAccountInformationWidget().getAccountNumberFromIssuePopup(), constants.getValue("account.number.equal"), constants.getValue("account.number.not.equal")));
                assertCheck.append(actions.assertEqualBoolean(pages.getTariffPlanPage().isCommentBoxVisible(), true, constants.getValue("comment.box.visible"), constants.getValue("comment.box.not.visible")));
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isEnterAmount(), true, constants.getValue("enter.amount.field.display"), constants.getValue("enter.amount.field.not.display")));
                assertCheck.append(actions.assertEqualBoolean(pages.getTariffPlanPage().isSubmitEnabled(), false, constants.getValue("submit.button.disabled"), constants.getValue("submit.button.enabled")));
                assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isCancelBtnEnable(), true, constants.getValue("cancel.button.display"), constants.getValue("cancel.button.not.display")));
                pages.getAccountInformationWidget().clickClosePopup();
            } else {
                commonLib.fail(constants.getValue("create.sr.popup.not.open"), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 15, groups = {"RegressionTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void createSRUsingRaiseSRButton() {
        try {
            selUtils.addTestcaseDescription("Validating Total Credit limit have SR Icon must be visible,After clicking on SR Icon Issue detail pop up open,Validate user able to enter amount,Validate user able to add comment, Validate Submit button enabled,Validate after clicking on submit button Success message displayed,Validate ticket id and expected closure date displayed.", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isSRIconDisplay(), true, constants.getValue("cs.account.information.sr.icon.found"), constants.getValue("cs.account.information.sr.icon.not.found"), true));
            String accountNumber = pages.getAccountInformationWidget().getAccountNumber();
            pages.getAccountInformationWidget().clickSRRaiseIcon();
            boolean modalOpen = pages.getAccountInformationWidget().isIssueDetailPopUpDisplay();
            if (modalOpen) {
                if (accountNumber == null) {
                    pages.getAccountInformationWidget().enterAccountNumberInPopUp("12345678");
                }
                pages.getAccountInformationWidget().writeAmount("10");
                pages.getAuthTabPage().clickSelectReasonDropDown();
                pages.getAuthTabPage().chooseReason();
                pages.getAuthTabPage().enterComment(comments);
                assertCheck.append(actions.assertEqualBoolean(pages.getTariffPlanPage().isSubmitEnabled(), true, constants.getValue("submit.button.enabled"), constants.getValue("submit.button.disabled")));
                pages.getTariffPlanPage().clickSubmitBtn();
                String successMessage = pages.getAccountInformationWidget().getSuccessMessage();
                commonLib.pass(successMessage);
                String ticketId = successMessage.split(" is ")[1].split(" and ")[0];
                String expectedClosureDate = successMessage.split(" is ")[2];
                assertCheck.append(actions.assertEqualStringNotNull(ticketId, "Ticket Number Displayed: " + ticketId, "Ticket Number does not displayed"));
                assertCheck.append(actions.assertEqualStringNotNull(expectedClosureDate, "Expected Closure Date Displayed: " + expectedClosureDate, "Expected Closure Date does not displayed"));
                pages.getAccountInformationWidget().clickClosePopup();
            } else {
                commonLib.fail(constants.getValue("create.sr.popup.not.open"), true);
            }
        } catch (Exception e) {
            if (pages.getAccountInformationWidget().isIssueDetailPopUpDisplay())
                pages.getAccountInformationWidget().clickClosePopup();
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 16, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void accountInfoProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            String[] widgets;
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();
            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));

            int size = pages.getProfileManagement().getWidgetRowsSize();
            widgets = new String[size];
            for (int i = 1; i <= size; i++) {
                widgets[i - 1] = pages.getProfileManagement().getWidgetNameForOrder(i);
                commonLib.info("Widget name : " + pages.getProfileManagement().getWidgetNameForOrder(i));
                if (pages.getProfileManagement().getWidgetNameForOrder(i).trim().equalsIgnoreCase("Postpaid Account Information")) {
                    assertCheck.append(actions.assertEqualStringType(pages.getProfileManagement().getWidgetNameForOrder(i).trim().toUpperCase(), "POSTPAID ACCOUNT INFORMATION", "Account Information visible in profile management", "Account Information is not visible in profile management"));

                    if (pages.getProfileManagement().isCheckboxEnable(i - 1).equalsIgnoreCase("true")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getProfileManagement().isCheckboxEnable(i - 1).toLowerCase().trim(), "true", "Postpaid Account Information widget enabled as expected", "Postpaid Account Information widget doesn't enabled as expected"));

                    } else {
                        pages.getProfileManagement().clickOnCheckbox(i - 1);
                        pages.getProfileManagement().clickingSubmitButton();
                        break;
                    }

                    assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isMovingUpButtonEnable(i), true, "Moving up button is enable", "Moving up button is not enable"));
                    pages.getProfileManagement().clickingUpButton(i);
                    assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isMovingDownButtonEnable(i), true, "Moving down button is enable", "Moving down button is not enable"));
                    pages.getProfileManagement().clickingDownButton(i);
                    assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isSubmitButtonEnable(), true, "Submit button enable as expected", "Submit button does not enable as expected"));
                    pages.getProfileManagement().clickingSubmitButton();
                    break;

                }
            }


        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentPlanProfileManagement" + e.fillInStackTrace(), true);
        }
    }

}