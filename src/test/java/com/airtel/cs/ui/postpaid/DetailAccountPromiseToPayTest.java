package com.airtel.cs.ui.postpaid;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.accountinfo.AccountDetails;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DetailAccountPromiseToPayTest extends Driver {

    private String customerNumber = null;
    private Boolean isPermissionEnable = false;
    ESBRequestSource apiEsb = new ESBRequestSource();
    AccountDetails accountDetails;
    String billNumber, EmailID = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "Smoke Test"})
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasAccountInfoWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Validate whether user has Account Information Widget Permission ", "description");
            String account_info_permission = constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION);
            Boolean isPermissionEnable = UtilsMethods.isUserHasPermission(new Headers(map), account_info_permission);
            assertCheck.append(actions.assertEqualBoolean(isPermissionEnable, true, "Logged in user has Account Information Widget permission", "Logged in user doesn't have Account Information Widget permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasAccountInfoWidgetPermission " + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void accountInfoWidgetVisible() {
        try {
            selUtils.addTestcaseDescription("Validate Account Information Widget , Validate Account Information Detailed Widget", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntergerStatusCode(statusCode, 200, "KYC Profile status code matched and is : " + statusCode, "KYC Profile status code not matched and is : " + statusCode));
            String connectionType = kycProfile.getResult().getLineType().toLowerCase().trim();
            if (connectionType.equalsIgnoreCase("POSTPAID") && isPermissionEnable) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "Account Information Widget is visible ", "Account Information Widget is visible"));
                pages.getDetailAccountInfoViewBillWidget().openAccountInformationDetailPage();
                assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoViewBillWidget().isDetailedAccountInformationWidgetDisplay(), true, "Account Info Detailed Page is visible ", "Account Info Detailed Page is visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "ACCOUNT INFORMATION DETAIL widget displayed as expected in Account Information Detail Page", "ACCOUNT INFORMATION DETAIL widget not displayed as expected in Account Information Detail Page"));
                assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getAccountInfoTabName().toUpperCase(), "ACCOUNT INFO", "Account Info tab is visible", "Account Info tab is not visible"));
            } else
                commonLib.fail(" Account information widget is not displayed", true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - accountInfoWidgetVisible" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateSendBill() {
        try {
            selUtils.addTestcaseDescription("Validating Promis To Pay permission , Performing Promis To Pay action", "description");
            String promise_to_pay_permission = constants.getValue(PermissionConstants.PROMISE_TO_PAY_POSTPAID_BILL);
            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isUserHasPermission(new Headers(map), promise_to_pay_permission), true, "Logged in user has Send Postpaid Bill permission", "Logged in user doesn't has  Send Postpaid Bill permission"));
            CustomerProfileResponse customerProfileResponse = apiEsb.customerProfileResponse(customerNumber);
            String accountNo = customerProfileResponse.getCustomerAccountNumber();
            accountDetails = api.getAccountInfoDetail(accountNo, 1);
            Integer statusCode = accountDetails.getStatusCode();
            if (statusCode == 500)
                assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isErrorMessageVisible(), true, "Unable to Fetch Data error message is visible for status code :" + statusCode, "Unable to Fetch Data error message is NOT visible for status code :" + statusCode));
            else {
                int totalCount = accountDetails.getTotalCount();
                int size = accountDetails.getTotalCount() > 5 ? 5 : totalCount;
                for (int row = 0; row < size; row++) {
                    String transactionType = accountDetails.getResult().get(row).getTransactionType();
                    String billNo = accountDetails.getResult().get(row).getReferenceNo();
                    if ("INVOICE".equalsIgnoreCase(transactionType)) {
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isPromiseToPayVisible(row), true, "Send Bill Icon is visible at row " + row, "Send Bill icon is not visible at row " + row));
                        if (StringUtils.equalsIgnoreCase(EmailID, "-")) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isPromiseToPayDisabled(row), true, "Send Bill Icon is disabled when there is no email id registered" + row, "Send Bill icon is not disabled when there is no email id registered" + row));
                            String hoverText = "Send To Email ID is not available".toLowerCase();
                            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountPromiseToPay().getPromiseToPayIconText(row), hoverText, "Message on hovering send bill icon is same as expected when no email id is registered  " + row, "Message on hovering send bill icon is NOT same as expected when no email id is registered " + row));
                        }
                        pages.getDetailAccountPromiseToPay().clickOnPromiseToPay(row);
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isIssuePopVisible(), true, "Issue Detail Pop up is visible", "Issue Detail Pop up is NOT visible"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isSelectReasonVisible(), true, "Select Reason Field is visible", "Select Reason Field is NOT visible"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isBillNumberVisible(), true, "Bill Number Field is visible", "Bill Number Field is NOT visible"));
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountPromiseToPay().getBillNumber(), billNumber, "Bill Number is same as expected", "Bill Number is NOT same as expected "));
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isCommentBoxVisible(), true, "Comment box is visible", "Comment box is NOT visible"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isSubmitBtnDisabled(), true, "Submit button is disabled", "Submit button is not disabled"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isCancelButtonVisible(), true, "Cancel Button is visible ", "Cancel Button is NOT visible"));
                        pages.getDetailAccountPromiseToPay().clickOnCancelButton();
                        /**
                         Performing operations after clicking Cancel button of Issue Detail Pop up
                         */
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isCancelConfirmMessageVisible(), true, "Cancel confirmation message is visible ", "Cancel confirm message  is NOT visible"));
                        pages.getDetailAccountPromiseToPay().clickOnCancel();
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isIssuePopVisible(), true, "Issue Detail Pop up is visible after clicking on Cancel", "Issue Detail Pop up is not visible after clicking on Cancel"));
                        pages.getDetailAccountPromiseToPay().clickOnContinue();
                        assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "Account Information Widget is visible after closing the Issue Detail Pop up ", "Account Information Widget is NOT visible after closing the Issue Detail Pop up"));
                        pages.getDetailAccountPromiseToPay().performPromiseToPay(row);
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountPromiseToPay().isSuccessPopUpVisible(), true, "Success Popup visible after PromiseToPay  ", "Success Popup NOT visible after sending visible"));
                        String successText = "Request for Send Bill has been successfully submitted";
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountPromiseToPay().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
                        pages.getDetailAccountPromiseToPay().clickCrossIcon();
                        break;
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSendBill" + e.fillInStackTrace(), true);
        }
    }

}
