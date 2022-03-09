package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.DetailAccountInfoViewBillWidget;
import com.airtel.cs.model.response.accountinfo.AccountDetails;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DetailAccountInfoViewBillTest extends Driver {
    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();
    private final BaseActions actions = new BaseActions();
    String accountNo = null;

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
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
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
     * This method is used to validate account information widget permission
     */

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void userWithAccountInfoWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view account information Widget Permission", "description");
            String accountInfo_permission = constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(accountInfo_permission), "Logged in user has Account Information Widget permission", "Logged in user doesn't have Account Information Widget permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - userWithAccountInfoWidgetPermission()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check whether user has permission for Account Information Widget
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasDetailedAccountInformationPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that detailed account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view detailed account information Widget Permission", "description");
            String detailedAccountInfo_permission = constants.getValue(PermissionConstants.VIEW_POSTPAID_BILL);
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoViewBillWidget().isActionIconVisibleOnAccountInfo(), true, "Detailed account information icon visible", "Detailed account information icon not visible"));
            pages.getDetailAccountInfoViewBillWidget().openAccountInformationDetailPage();
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoViewBillWidget().isDetailedAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(detailedAccountInfo_permission), "Detailed Account Information Widget displayed correctly as per user permission", "Detailed Account Information Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasDetailedAccountInformationPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to show view bill on the basis of connection type and UM permission
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that view bill should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umViewBillPermission = pages.getDetailAccountInfoViewBillWidget().isDetailedAccountInformationWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umViewBillPermission) {
                assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoViewBillWidget().isDetailedAccountInformationWidgetDisplay(), true, "View bill is visible", "View bill not visible"));
            } else {
                commonLib.fail(" Account information widget is not displayed", true);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to show account info detailed icon
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void accountInfoIcon() {
        try {
            selUtils.addTestcaseDescription("Verify that detailed account info icon should be visible to the logged in agent", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getAccountInfoTabName().toUpperCase(), "ACCOUNT INFO", "Account Info tab visible", "Account Info tab not visible"));

            /**
             * Calling v2/customer-profile API to fetch account number
             */
            CustomerProfileResponse customerProfileResponse = apiEsb.customerProfileResponse(constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN));
            accountNo = customerProfileResponse.getCustomerAccountNumber();
            commonLib.info("Account number : " + accountNo);

            final DetailAccountInfoViewBillWidget accountDetailsWidget = pages.getDetailAccountInfoViewBillWidget();
            AccountDetails accountDetails = api.getAccountInfoDetail(accountNo, 1);
            int size = accountDetails.getTotalCount() > 5 ? 5 : accountDetails.getTotalCount();
            int totalCount = accountDetails.getTotalCount();
            Integer pageNumber = 1;
            String ticketId = "";
            String billLastFour = null;
            for (int row = 1; row <= size; row++) {
                String transactionType = accountDetailsWidget.getTransactionType(row);
                if ("INVOICE".equalsIgnoreCase(transactionType)) {
                    assertCheck.append(actions.assertEqualBoolean(accountDetailsWidget.isViewBillIconDisplay(row), true, "View Bill icon visible", "View Bill icon not visible"));
                    String billNumber = accountDetailsWidget.getBillNumber(row).split(":")[1].trim();
                    commonLib.info("Bill Number is : " + billNumber);
                    billLastFour = pages.getDetailAccountInfoViewBillWidget().getLast4Char(billNumber);
                    pages.getDetailAccountInfoViewBillWidget().clickingOnViewBillIcon(row);
                    if (pages.getDetailAccountInfoViewBillWidget().isNextPagePDFVisible()) {
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoViewBillWidget().isBillVisible(), true, "Bill visible successfully and action pop is false", "Bill not visible"));
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getPdfSubWidget(billLastFour), "BILL: " + billLastFour, "Sub-widget is visible as expected", "Sub-widget is not visible as expected"));
                        pages.getDetailAccountInfoViewBillWidget().clickingOnNextPagePDF();
                        pages.getDetailAccountInfoViewBillWidget().clickingOnPrevPagePDF();
                        pages.getDetailAccountInfoViewBillWidget().goToHomeTab();
                        pages.getDetailAccountInfoViewBillWidget().goToViewHistoryTab();
                        pages.getDetailAccountInfoViewBillWidget().goActionTrailTab();
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getActionType(), "View Postpaid Bill [PDF]", "Action type for view bill is expected", "Action type for view bill is not expected"));
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getReason(), "Customer Request", "Reason for view bill is expected", "Reason for view bill is not expected"));
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getComment(), "Bill Viewed On Customer Request", "Comment for view bill is expected", "Comment for view bill is not expected"));
                        pages.getDetailAccountInfoViewBillWidget().clickingOnDropDown();
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getBillNo().trim(), billNumber, "View Bill entry rendered in action trail", "View Bill entry not rendered in action trail"));
                        pages.getDetailAccountInfoViewBillWidget().goToHomeTab();
                        pages.getDetailAccountInfoViewBillWidget().openAccountInformationDetailPage();
                        if (row >= 2) {
                            break;
                        }
                    } else {
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().viewBillError(), pages.getDetailAccountInfoViewBillWidget().viewBillError(), "Bill is not fetched UI showing proper message", "Bill is not fetched UI not showing proper message"));
                        pages.getDetailAccountInfoViewBillWidget().goToHomeTab();
                        pages.getDetailAccountInfoViewBillWidget().openAccountInformationDetailPage();
                    }

                }
                if (row == 5 && pageNumber * 5 != totalCount && accountDetailsWidget.nextPageVisible()) {
                    accountDetailsWidget.clickNextPage();
                    accountDetails = api.getAccountInfoDetail(accountNo, ++pageNumber);
                    assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoViewBillWidget().getPdfSubWidget(billLastFour), "BILL: " + billLastFour, "Sub-widget is visible as expected", "Sub-widget is not visible as expected"));
                    if (row >= 2) {
                        break;
                    }
                    row = 0;
                } else if (pageNumber * 5 == totalCount) {
                    break;
                }
            }


            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - accountInfoIcon()" + e.fillInStackTrace(), true);
        }
    }


}