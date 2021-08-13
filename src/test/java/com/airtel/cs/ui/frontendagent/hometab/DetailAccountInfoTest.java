package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.StatementRequest;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import com.airtel.cs.model.response.postpaid.AccountStatementResponse;
import com.airtel.cs.model.response.postpaid.PostpaidAccountDetailResponse;
import com.airtel.cs.model.response.postpaid.Statement;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DetailAccountInfoTest extends Driver {
    RequestSource api = new RequestSource();
    ESBRequestSource apiEsb = new ESBRequestSource();
    String customerNumber = null;
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
     * This method is used to show view bill on the basis of connection type and UM permission
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that view bill should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode, false));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umAccountInformationPermission = pages.getAccountInformationWidget().isAccountInformationWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umAccountInformationPermission) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "Detailed account information widget is visible", "Detailed account information widget not visible"));
            } else {
                commonLib.fail(" Detailed account information widget is not displayed", true);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
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
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isActionIconVisibleOnAccountInfo(), true, "Detailed account information icon visible", "Detailed account information icon not visible"));
            pages.getDetailAccountInfoWidget().openAccountInformationDetailPage();
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isDetailedAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), detailedAccountInfo_permission), "Detailed Account Information Widget displayed correctly as per user permission", "Detailed Account Information Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasDetailedAccountInformationPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    /**
     * This method is used to validate watermarked in the left corner and middle
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasDetailedAccountInformationPermission"})
    public void accountInformationWatermarkTest() {

        try {
            selUtils.addTestcaseDescription("Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the Your Account Information widget and is correct", "Auuid NOT shown at the footer of Your Account Information widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the Your Account Information widget and is correct", "Auuid NOT shown at the middle of Your Account Information widget"));
            String row = pages.getDetailAccountInfoWidget().getAccountInfoDetailRows();
            commonLib.info("row :" + row);
            row = row.trim().substring(row.indexOf('-') + 1, row.indexOf('o'));
            assertCheck.append(actions.assertEqualStringType(row.trim(), "5", "No. of entries in detail account info is 5", "No. of entries in detail account info is not 5"));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isDetailAccPagination(), true, "Pagination in detail account info is visible", "Pagination in detail account info is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isDetailAccountInfoDateRangeIconVisible(), true, "Date range icon visible", "Date range icon not visible"));
            pages.getDetailAccountInfoWidget().clickingOnDateRangeIcon();
            pages.getDetailAccountInfoWidget().clickingOnDateRangeDoneButton();
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isDetailAccPaginationRightMoveEnable(), true, "Right move button enable", "Right move button is not enable"));
            pages.getDetailAccountInfoWidget().clickingAccountInfoRightArrow();
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isDetailAccPaginationLeftMoveEnable(), true, "Left move button enable", "Left move button is not enable"));
            pages.getDetailAccountInfoWidget().clickingAccountInfoLeftArrow();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - accountInformationWatermarkTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to show account info detailed icon
     */
    @DataProviders.Table(name = "ACCOUNT INFORMATION DETAIL")
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void accountInfoIcon(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify that detailed account info icon should be visible to the logged in agent", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getAccountInfoTab().toUpperCase(), "ACCOUNT INFO", "Account Info tab display as expected in detailed account info", "Account Info tab not display as expected in detailed account info"));
            for (int i = 0; i < data.getHeaderName().size(); i++) {
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().getPackHeaders(i + 1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
            }
            int noOfCheckBox = pages.getDetailAccountInfoWidget().getWidgetRowsSize();
            if (noOfCheckBox > 0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isCheckboxDisplay(), true, "Checkbox visible in account info tab", "Checkbox is not visible in account info tab"));
            }

            /**
             * Calling v2/customer-profile API to fetch account number
             */
            CustomerProfileResponse customerProfileResponse = apiEsb.customerProfileResponse(constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN));
            accountNo = customerProfileResponse.getCustomerAccountNumber();
            commonLib.info("Account number : " + accountNo);


            /**
             * Setting statement request object
             */
            StatementRequest statementRequest = new StatementRequest();
            statementRequest.setAccountNo(accountNo);

            /**
             * Calling v1/postpaid/account/details API
             */
            PostpaidAccountDetailResponse accountDetailResponse = api.accountDetailResponse(accountNo);
            final String status = accountDetailResponse.getStatus();
            if (status.trim().equalsIgnoreCase("SUCCESS")) {
                int size = pages.getDetailAccountInfoWidget().getNumbersOfRows();
                if (size > 5) {
                    size = 5;
                }
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().dateFormat(pages.getDetailAccountInfoWidget().getDateTimeValue(i + 1)), accountDetailResponse.getResult().get(i).getDateTime(), "Date and time display as received in API on row", "Date and time is not display as received in API on row"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().getTypeValue(i + 1), accountDetailResponse.getResult().get(i).getTransactionType(), "Transaction type display as received in API on row", "Transaction type is not display as received in API on row"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().getStatusValue(i + 1), accountDetailResponse.getResult().get(i).getStatus(), "Status display as received in API on row", "Status is not display as received in API on row"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().getRefNoValue(i + 1), accountDetailResponse.getResult().get(i).getReferenceNo(), "Reference number display as received in API on row", "Reference number is not display as received in API on row"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().getBillAmountValue(i + 1).equals("-") ? "" : pages.getDetailAccountInfoWidget().getBillAmountValue(i + 1), accountDetailResponse.getResult().get(i).getBillAmount(), "Bill amount display as received in API on row", "Bill amount is not display as received in API on row"));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailAccountInfoWidget().getAmountRecValue(i + 1).equals("-") ? "" : pages.getDetailAccountInfoWidget().getAmountRecValue(i + 1), accountDetailResponse.getResult().get(i).getAmntReceived(), "Amount received display as received in API on row", "Amount received is not display as received in API on row"));

                    if (pages.getDetailAccountInfoWidget().getTypeValue(i + 1).equalsIgnoreCase("INVOICE")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getBillAmountValue(i + 1), accountDetailResponse.getResult().get(i).getBillAmount(), "Bill Amount (INVOICE) is displayed as per ESB TransactionAmountDebit", "Bill Amount (INVOICE) is not displayed as per ESB TransactionAmountDebit"));
                    }

                    if (pages.getDetailAccountInfoWidget().getTypeValue(i + 1).equalsIgnoreCase("ADJUSTMENT") && !pages.getDetailAccountInfoWidget().getBillAmountValue(i + 1).equalsIgnoreCase("-")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getBillAmountValue(i + 1), accountDetailResponse.getResult().get(i).getAmntReceived(), "Bill Amount (ADJUSTMENT) is displayed as per ESB TransactionAmountDebit", "Bill Amount (ADJUSTMENT) is not displayed as per ESB TransactionAmountDebit"));
                    }

                    if (pages.getDetailAccountInfoWidget().getTypeValue(i + 1).equalsIgnoreCase("ADJUSTMENT") && !pages.getDetailAccountInfoWidget().getBillAmountValue(i + 1).equalsIgnoreCase("-")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getAmountRecValue(i + 1), accountDetailResponse.getResult().get(i).getAmntReceived(), "Amount received (ADJUSTMENT) is displayed as per ESB TransactionAmountDebit", "Amount received (ADJUSTMENT) is not displayed as per ESB TransactionAmountDebit"));
                    }
                }

            } else {
                assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getUnableToFetch().toLowerCase(), "unable to fetch data", "Unable to fetch displays correctly", "Unable to fetch not displays correctly"));
                commonLib.fail("API does not able to fetch account statement", false);
            }


            if (status.trim().equalsIgnoreCase("SUCCESS")) {
                pages.getDetailAccountInfoWidget().txnTypeAssertion(accountDetailResponse);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - accountInfoIcon()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void detailAccountInfoProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();

            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
            int size = pages.getPlanAndPackDetailedWidget().getActivePackOnPM();
            if (size > 0)
                for (int i = 1; i <= size; i++) {
                    if (pages.getDetailAccountInfoWidget().getHeaders(i).equalsIgnoreCase("ACCOUNT INFO")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getHeaders(i), "ACCOUNT INFO", "ACCOUNT INFO is visible in profile management", "ACCOUNT INFO is not visible in profile management"));
                        assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isAccountInfoButtonEnable(), true, "ACCOUNT INFO tab button is enable", "ACCOUNT INFO tab is not enable"));
                        pages.getProfileManagement().openAccountInfoTab();
                        int chkBoxSize = pages.getProfileManagement().isChkBoxEnable();
                        if (chkBoxSize > 0) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isPostpaidAccountInfoChkBoxVisibile(), true, "Checkbox visible for detail account info", "Checkbox not visible for detail account info"));
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isPostpaidLinkedMsisdnChkBoxVisibile(), true, "Checkbox visible for linked msisdn", "Checkbox not visible for linked msisdn"));
                        }
                        if (pages.getProfileManagement().isAcctInfoMovingUpButtonEnable()) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isAcctInfoMovingUpButtonEnable(), true, "Account info moving up button working as expected", "Account info moving up button is not working as expected"));
                            pages.getProfileManagement().clickingAccountInfoUpButton();
                        }

                        if (pages.getProfileManagement().isAcctInfoMovingDownButtonEnable()) {
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isAcctInfoMovingDownButtonEnable(), true, "Account info moving down button working as expected", "Account info moving down button is not working as expected"));
                            pages.getProfileManagement().clickingAccountInfoDownButton();
                        }

                        pages.getProfileManagement().clickingSubmitButton();
                        break;

                    }
                }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - detailAccountInfoProfileManagement" + e.fillInStackTrace(), true);
        }
    }

}