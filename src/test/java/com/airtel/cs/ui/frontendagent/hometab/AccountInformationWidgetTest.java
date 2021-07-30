package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.PaymentRequest;
import com.airtel.cs.model.response.PaymentHistory;
import com.airtel.cs.model.response.CreditLimitResponse;
import com.airtel.cs.model.response.InvoiceHistoryResponse;
import com.airtel.cs.model.response.PaymentResponse;
import com.airtel.cs.model.response.Payment;
import com.airtel.cs.model.response.PostpaidBillDetailsResponse;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AccountInformationWidgetTest extends Driver {

    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();
    public static Response response;
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
    public void verifyESBParamWithUI() {
        try {
            selUtils.addTestcaseDescription("Validate UI parameter values with ESB response", "description");


            List<PaymentHistory> paymentHistory = null;
            String totalOutStanding = null;
            String totalUnbilled = null;
            String customerAccountNumber = null;

            /**
             * Calling credit limit api to get total and available credit limit
             */
            String totatCreditLimit = null;
            String availableCreditLimit = null;
            double ttlCrdtLmt = 0l;
            double avlCrdtLmt = 0l;
            double ttlCrdtLmtUI = 0l;
            double avlCrdtLmtUI = 0l;
            double usedCreditLimit = 0l;
            double usedCreditLimitUI = 0l;
            String usdCreditLmt = null;
            String usdCreditLmtUI = null;
            CreditLimitResponse creditLimitResponse = apiEsb.creditLimitResponse(customerNumber);
            final String creditLimitStatus = creditLimitResponse.getStatus();
            commonLib.info("creditLimitStatus" + creditLimitStatus);
            if (creditLimitStatus.trim().equalsIgnoreCase("200")) {
                totatCreditLimit = pages.getAccountInformationWidget().getTwoDecimalValue(creditLimitResponse.getTotalCreditLimit());
                availableCreditLimit = pages.getAccountInformationWidget().getTwoDecimalValue(creditLimitResponse.getAvailableCreditLimit());
                commonLib.info("totatCreditLimit : " + totatCreditLimit);
                commonLib.info("availableCreditLimit : " + availableCreditLimit);
                ttlCrdtLmt = Double.parseDouble(totatCreditLimit);
                avlCrdtLmt = Double.parseDouble(availableCreditLimit);
                ttlCrdtLmtUI = Double.parseDouble(pages.getAccountInformationWidget().getTotalCreditLimit());
                avlCrdtLmtUI = Double.parseDouble(pages.getAccountInformationWidget().getAvailCreditLimit());
                usedCreditLimit = ttlCrdtLmt - avlCrdtLmt;
                usedCreditLimitUI = ttlCrdtLmtUI - avlCrdtLmtUI;
                usdCreditLmt = String.valueOf(usedCreditLimit);
                usdCreditLmtUI = String.valueOf(usedCreditLimitUI);
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getAvailCreditLimit(), availableCreditLimit, "Available credit limit displayed as per ESB response", "Available credit limit not displayed as per ESB response"));
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalCreditLimit(), totatCreditLimit, "Total credit limit displayed as per ESB response", "Total credit limit displayed as per ESB response"));
                assertCheck.append(actions.assertEqualStringType(usdCreditLmt, usdCreditLmtUI, "Used credit limit is displayed as pe ESB response", "Used credit limit is not displayed as pe ESB response"));
            } else if (creditLimitStatus.trim().equalsIgnoreCase("500")) {
                commonLib.info("Internal server error");
            } else {
                commonLib.info("Credit Limit downstream api not working");
            }


            /**
             * Calling Invoice History api to get invoice due date and invoice date
             */
            String invoiceDueDate = null;
            String invoiceDate = null;
            String currentInvoice = null;
            String billStartDate = null;
            String billEndDate = null;
            String startDate = null;
            String endDate = null;
            String months = null;
            String firstDat = null;
            String endDat = null;
            InvoiceHistoryResponse invoiceHistoryResponse = apiEsb.invoiceHistoryResponse(customerNumber);
            final String invoiceHistoryStatus = invoiceHistoryResponse.getStatus();
            commonLib.info("invoiceHistoryStatus" + invoiceHistoryStatus);
            if (invoiceHistoryStatus.trim().equalsIgnoreCase("200")) {
                paymentHistory = invoiceHistoryResponse.getPaymentHistory();
                for (PaymentHistory ph : paymentHistory) {
                    invoiceDueDate = pages.getAccountInformationWidget().getDateFromEpoch(ph.getInvoiceDueDate(), "dd MMM yyyy");
                    invoiceDate = pages.getAccountInformationWidget().getDateFromEpoch(ph.getInvoiceDate(), "dd MMM yyyy");
                    currentInvoice = pages.getAccountInformationWidget().getTwoDecimalValue(ph.getCurrentInvoice());
                    billStartDate = pages.getAccountInformationWidget().getDateFromEpoch(ph.getBillStartDate(), "dd MMM yyyy");
                    billEndDate = pages.getAccountInformationWidget().getDateFromEpoch(ph.getBillEndDate(), "dd MMM yyyy");
                    startDate = pages.getAccountInformationWidget().getDateFromEpoch(ph.getBillStartDate(), "yyyy-MM-dd");
                    endDate = pages.getAccountInformationWidget().getDateFromEpoch(ph.getBillEndDate(), "yyyy-MM-dd");
                    months = pages.getAccountInformationWidget().getDateDiffInMonths(startDate, endDate);
                    firstDat = pages.getAccountInformationWidget().firstTwo(billStartDate);
                    endDat = pages.getAccountInformationWidget().firstTwo(billEndDate);
                    commonLib.info("invoiceDueDate : " + invoiceDueDate);
                    commonLib.info("invoiceDate : " + invoiceDate);
                    commonLib.info("currentInvoice : " + currentInvoice);
                    commonLib.info("billStartDate : " + billStartDate);
                    commonLib.info("billEndDate : " + billEndDate);
                    commonLib.info("firstDat : " + firstDat);
                    commonLib.info("endDat : " + endDat);

                }
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getDueDate(), invoiceDueDate, "Due date is displayed as per ESB response", "Due date is not displayed as per ESB response"));
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastMonthBillDate(), invoiceDate, "Last month bill date is displayed as per ESB response", "Last month bill date is not displayed as per ESB response"));
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentCycle(), billStartDate + " - " + billEndDate, "Bill cycle displayed as per ESB response", "Bill cycle not displayed as per ESB response"));
                assertCheck.append(actions.assertEqualStringType(months, "1", "Postpaid plan validity is 1 month", "Postpaid plan validity is 1 month"));
                assertCheck.append(actions.assertEqualStringType(firstDat, endDat, "Same cycle goes every month", "Same cycle doesn't goes every month"));
            } else if (invoiceHistoryStatus.trim().equalsIgnoreCase("500")) {
                commonLib.info("Internal server error");
            } else {
                commonLib.info("Invoice History downstream api not working");
            }

            /**
             * Calling Postpaid Bill Details api to get total outstanding
             */
            PostpaidBillDetailsResponse postpaidBillDetailsResponse = apiEsb.postpaidBillDetailsResponse(customerNumber);
            if (Objects.nonNull(postpaidBillDetailsResponse.getInvoiceDetails())) {
                totalOutStanding = pages.getAccountInformationWidget().getTwoDecimalValue(postpaidBillDetailsResponse.getInvoiceDetails().getTotalOutstanding());
                totalUnbilled = pages.getAccountInformationWidget().getTwoDecimalValue(postpaidBillDetailsResponse.getInvoiceDetails().getTotalUnbilled());
                commonLib.info("totalOutStanding : " + totalOutStanding);
                commonLib.info("totalUnbilled : " + totalUnbilled);
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalOutstanding(), totalOutStanding, "Total outstanding displayed as per ESB response", "Total outstanding not displayed as per ESB response"));
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmount(), totalUnbilled, "Total unbilled amount displayed as per ESB response", "Total unbilled amount not displayed as per ESB response"));
            } else if (Objects.isNull(postpaidBillDetailsResponse.getInvoiceDetails())) {
                commonLib.info("Internal server error");
            } else {
                commonLib.info("Postpaid Bill Details downstream api not working");
            }

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
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAccountNo(customerAccountNumber);
            paymentRequest.setLimit(constants.getValue(CommonConstants.PAYMENT_REQUEST_LIMIT));
            paymentRequest.setOffset(constants.getValue(CommonConstants.PAYMENT_REQUEST_OFFSET));

            /**
             * Calling enterprise-service/ esb account/payment api to get  payment amount, currency & payment date
             */
            List<Payment> payment = null;
            String paymentAmt = null;
            String currency = null;
            String paymentDate = null;
            PaymentResponse paymentResponse = apiEsb.paymentResponse(paymentRequest);
            final String accountPaymentStatus = paymentResponse.getStatus();
            if (accountPaymentStatus.trim().equalsIgnoreCase("200")) {
                payment = paymentResponse.getResponse().getPayments();
                for (Payment p : payment) {
                    paymentAmt = p.getPaymentAmount();
                    currency = p.getCurrency();
                    paymentDate = p.getPaymentDate();
                    commonLib.info("paymentAmt : " + paymentAmt);
                    commonLib.info("currency : " + currency);
                    commonLib.info("paymentDate : " + paymentDate);
                }
            } else if (accountPaymentStatus.trim().equalsIgnoreCase("400")) {
                commonLib.info("Bad request from the customer");
            } else if (accountPaymentStatus.trim().equalsIgnoreCase("401")) {
                commonLib.info("Unauthorised request");
            } else {
                commonLib.info("account/payment downstream api not working");
            }

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
                commonLib.info("Email from esb: " + customerProfileResponse.getEmail().trim());
                commonLib.info("Email on portal : " + pages.getAccountInformationWidget().getEmailId());
                commonLib.info("Unable : " + pages.getAccountInformationWidget().getUnableToFetch().toLowerCase());
                assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getEmailId(), customerProfileResponse.getEmail().trim(), "Email ID display as per ESB response", "Email ID display is not as per ESB response"));
                //assertCheck.append(actions.assertEqualStringType(pages.getPostpaidEmailIDWidget().getUnableToFetch().toLowerCase(), "unable to fetch", "Unable to fetch display correctly", "Unable to fetch not display correctly"));
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
            final List<String> postpaidAccountInformation = api.getPostpaidAccountInformation(customerNumber);
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "customerAccountNumber", "statusCode"), "200", "Postpaid Account Information API 4 Status Code Matched", "Postpaid Account Information API 4 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getAccountNumber(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "customerAccountNumber", "customerAccountNumber"), "Account Number displayed as expected", "Account Number not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getAccountNumberStyle(), "Bold", "Account Number is in Bold State", "Account Number NOT in Bold state"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyAccountNumber()" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to validate account information detail icon
     */
    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
    public void verifyAccountInfoDetailedIcon() {

        try {
            selUtils.addTestcaseDescription("Validating all data points under Account Information widget like Due date And Total Outstanding And Current Cycle And Current Month Unbilled Amount And last Month Bill Amount And Last Payment Mode And Security Deposit And Account Number", "description");
            final List<String> postpaidAccountInformation = api.getPostpaidAccountInformation(customerNumber);
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmount", "statusCode"), "200", "Postpaid Account Information API 1 Status Code Matched", "Postpaid Account Information API 1 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "availableCreditLimit", "statusCode"), "200", "Postpaid Account Information API 2 Status Code Matched", "Postpaid Account Information API 2 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastMonthBilledAmount", "statusCode"), "200", "Postpaid Account Information API 3 Status Code Matched", "Postpaid Account Information API 3 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastPaymentAmount", "statusCode"), "200", "Postpaid Account Information API 5 Status Code Matched", "Postpaid Account Information API 5 Status Code NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "securityDeposit", "statusCode"), "200", "Postpaid Account Information API 6 Status Code Matched", "Postpaid Account Information API 6 Status Code NOT Matched"));

            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getSecurityDeposit(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "securityDeposit", "securityDeposit"), "Security deposit displays as expected", "Security deposit not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTotalOutstanding(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "totalOutstandingAmount", "totalOutstandingAmount"), "Total outstanding amount displays as expected", "Total outstanding amount not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastPaymentMode(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastPaymentMode", "lastPaymentMode"), "Last payment mode displays as expected", "Last payment mode not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentMonthUnBillAmount(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentMonthUnbilledAmount", "currentMonthUnbilledAmount"), "Current month un-billed amount displays as expected", "Current month un-billed amount not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getDueDate(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "dueDate", "dueDate"), "Due date displays as expected", "Due date not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getCurrentCycle(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "currentCycle", "currentCycle"), "Current cycle displays as expected", "Current cycle not displays as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getLastMonthBillAmount(), pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "lastMonthBilledAmount", "lastMonthBilledAmount"), "Last month bill amount displays as expected", "Last month bill amount not displays as expected"));

            String totalCreditLimit = pages.getAccountInformationWidget().getTotalCreditLimit();
            double ttlCreditLmt = Double.parseDouble(totalCreditLimit);
            String availCreditLimit = pages.getAccountInformationWidget().getAvailCreditLimit();
            double avlCreditLmt = 0.00d;
            avlCreditLmt = Double.parseDouble(availCreditLimit);
            double creditLimitUsed = 0.00d;
            creditLimitUsed = ttlCreditLmt - avlCreditLmt;
            String crdtLmt = String.valueOf(creditLimitUsed);
            crdtLmt = pages.getAccountInformationWidget().getTwoDecimalValue(crdtLmt);
            commonLib.info("Credit limit used is : " + crdtLmt);

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

            assertCheck.append(actions.assertEqualStringType(totalCreditLimit, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "totalCreditLimit", "totalCreditLimit"), "Progress bar that denotes Total Credit limit is displayed as expected", "Progress bar that denotes Total Credit limit not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(availCreditLimit, pages.getAccountInformationWidget().getValue(postpaidAccountInformation, "availableCreditLimit", "availableCreditLimit"), "Available Credit limit is displayed as expected", "Available Credit limit is not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(pages.getAccountInformationWidget().getTwoDecimalValue(usedCreditLimitOnProgressBar), usedCreditLimitCalculated, "Red portion of the progress bar denotes the Used Credit Limit is displayed as expected", "Red portion of the progress bar denotes the Used Credit Limit is not displayed as expected"));
            assertCheck.append(actions.assertEqualStringType(availCreditLimitUI, availCreditLimitCalculated, "Non-Red portion of the progress bar denotes the Used Credit Limit is displayed as expected", "Non-Red portion of the progress bar denotes the Used Credit Limit is not displayed as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - verifyAccountInfoDetailedIcon()" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void decimalCountOnUI() {
        try {
            selUtils.addTestcaseDescription("Validating the decimal values upto 2", "description");
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

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasAccountInformationPermission"})
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