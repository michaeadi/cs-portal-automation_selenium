package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.customeprofile.CustomerProfileResponse;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import com.airtel.cs.model.response.postpaid.enterprise.AccountStatementCSResponse;
import com.airtel.cs.model.response.postpaid.enterprise.MsisdnDetail;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LinkedMsisdnToAccountNoTest extends Driver {

    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();
    ESBRequestSource apiEsb = new ESBRequestSource();
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
     * This method is used to validate account information widget permission
     */

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void userWithAccountInfoWidgetPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view account information Widget Permission", "description");
            String accountInfo_permission = constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().isAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), accountInfo_permission), "Logged in user has Account Information Widget permission", "Logged in user doesn't have Account Information Widget permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - userWithAccountInfoWidgetPermission()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to show account information on the basis of connection type and UM permission
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"userWithAccountInfoWidgetPermission"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean detailAccInfo = pages.getLinkedMsisdnToAccountNoWidget().isAccountInformationWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && detailAccInfo) {
                assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().isAccountInformationWidgetDisplay(), true, "Account information widget is visible", "Account information widget is not visible"));
            } else {
                commonLib.fail(" Account information widget is not displayed", true);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate watermarked in the left corner and middle
     */
    @DataProviders.Table(name = "LINKED MSISDN")
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"userWithAccountInfoWidgetPermission"})
    public void accountInformationWatermarkTest(HeaderDataBean data) {

        try {
            selUtils.addTestcaseDescription("Validate is Detail Account Information Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().isActionIconVisibleOnAccountInfo(), true, "Detailed account information icon visible", "Detailed account information icon not visible"));
            pages.getLinkedMsisdnToAccountNoWidget().openAccountInformationDetailPage();
            assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the linked msisdn widget and is correct", "Auuid NOT shown at the footer of Your linked msisdn widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the linked msisdn widget and is correct", "Auuid NOT shown at the middle of Your linked msisdn widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().getAccountInfoTab().toUpperCase(), "ACCOUNT INFO", "Account Info tab display as expected in detailed account info", "Account Info tab not display as expected in detailed account info"));
            assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().getAccountInfoDetailWidget().toUpperCase(), "LINKED MSISDN", "Linked MSISDN display as expected in detailed account info", "Linked MSISDN not display as expected in detailed account info"));
            for(int i=0;i<data.getHeaderName().size();i++){
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getPackHeaders(i+1), data.getHeaderName().get(i), "Header Name for Row "+(i+1)+" is as expected", "Header Name for Row "+(i+1)+" is not as expected"));
            }
            assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().getSearchMsisdnChkBox(), true, "Searched msisdn checkbox in Linked Msisdn Table is visible", "Searched msisdn checkbox in Linked Msisdn Table is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().getCheckboxAccountInfo(), true, "Checkbox for account information details visible", "Checkbox for account information details not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().getCheckboxLinkedMsisdn(), true, "Checkbox for linked msisdn is visible", "Checkbox for linked msisdn is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getLinkedMsisdnToAccountNoWidget().getPagination(), true, "Pagination in linked msisdn is as expected", "pagination in linked msisdn is not as expected"));
            int row = pages.getLinkedMsisdnToAccountNoWidget().getNumbersOfRows();
            assertCheck.append(actions.assertEqualIntType(row, 5, "No. of entries in linked msisdn is 5", "No. of entries in linked msisdn is not 5"));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - accountInformationWatermarkTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to call esb api's
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"userWithAccountInfoWidgetPermission"})
    public void esbAPICallToGetAccountNumber() {
        /**
         * Calling v2/customer-profile API to fetch account number
         */
        CustomerProfileResponse customerProfileResponse = apiEsb.customerProfileResponse(constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN));
        accountNo = customerProfileResponse.getCustomerAccountNumber();
        commonLib.info("Account number : " + accountNo);

    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"userWithAccountInfoWidgetPermission"})
    public void uiValueWithResponse() {
        /**
         * Calling CS api
         */
        AccountStatementCSResponse accountStatementCSResponse = api.accountStatementCSResponse(accountNo, 1);
        List<MsisdnDetail> msisdnDetailList = null;
        String msisdn = null;
        String acctNo = null;
        final String accountStatementStatus = accountStatementCSResponse.getStatus();
        if (accountStatementStatus.equalsIgnoreCase("SUCCESS")) {
            msisdnDetailList = accountStatementCSResponse.result.getMsisdnDetailList();
            Boolean msisdnFlag = pages.getLinkedMsisdnToAccountNoWidget().areAllDistinct(msisdnDetailList);
            assertCheck.append(actions.assertEqualBoolean(msisdnFlag, true, "All MSISDN'S are unique", "All MSISDN'S are not unique"));
            int row = pages.getLinkedMsisdnToAccountNoWidget().getNumbersOfRows();
            if (row > 5) {
                row = 5;
            }
            for (int i = 0; i < row; i++) {
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getMsisdn(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getMsisdn(), "Msisdn on UI is visible as per response", "Msisdn on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getSim(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getSimNumber(), "SIM No on UI is visible as per response", "SIM No on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getLineType(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getLineType(), "Line Type on UI is visible as per response", "Line Type on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getPaymentLvl(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getPaymentLevel(), "Payment Level on UI is visible as per response", "Payment Level on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getGsmStatus(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getGsmStatus(), "GSM Status on UI is visible as per response", "GSM Status on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getVip(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getVip(), "VIP on UI is visible as per response", "VIP on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getCug(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getCug(), "CUG on UI is visible as per response", "CUG on UI is not visible as per response"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getLinkedMsisdnToAccountNoWidget().getDnd(i + 1), accountStatementCSResponse.getResult().getMsisdnDetailList().get(i).getDnd(), "DND on UI is visible as per response", "DND on UI is not visible as per response"));

                if (!pages.getLinkedMsisdnToAccountNoWidget().firstWord(pages.getLinkedMsisdnToAccountNoWidget().getCurrentUsageLimit(i + 1)).equalsIgnoreCase("-") && !pages.getLinkedMsisdnToAccountNoWidget().firstWord(pages.getLinkedMsisdnToAccountNoWidget().getCurrentUsageLimit(i + 1)).isEmpty()) {
                    assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().firstWord(pages.getLinkedMsisdnToAccountNoWidget().getCurrentUsageLimit(i + 1)), constants.getValue("currency"), "Currency on current usage limit is as expected", "Currency on current usage limit is not expected"));
                }
                if (!pages.getLinkedMsisdnToAccountNoWidget().firstWord(pages.getLinkedMsisdnToAccountNoWidget().getSecurityDep(i + 1)).equalsIgnoreCase("-") && !pages.getLinkedMsisdnToAccountNoWidget().firstWord(pages.getLinkedMsisdnToAccountNoWidget().getSecurityDep(i + 1)).isEmpty()) {
                    assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().firstWord(pages.getLinkedMsisdnToAccountNoWidget().getSecurityDep(i + 1)), constants.getValue("currency"), "Currency on security deposit is as expected", "Currency on security deposit is not expected"));
                }
            }
        }
        pages.getLinkedMsisdnToAccountNoWidget().isPaginationRightMoveEnable();
        pages.getLinkedMsisdnToAccountNoWidget().clickingOnNextPage();
        pages.getLinkedMsisdnToAccountNoWidget().isPaginationLeftMoveEnable();
        pages.getLinkedMsisdnToAccountNoWidget().clickingOnPrevPage();
        pages.getLinkedMsisdnToAccountNoWidget().clickingMsisdnSearchBox();
        pages.getLinkedMsisdnToAccountNoWidget().enterNumber(customerNumber);
        pages.getLinkedMsisdnToAccountNoWidget().clickingOnSearch();

        if (pages.getLinkedMsisdnToAccountNoWidget().isMsisdnInvalid()) {
            assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().getInvalidNumberMessage().toUpperCase(), "INVALID MOBILE NUMBER", "Message display for invalid number as expected", "Message not display for invalid number as expected"));
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"userWithAccountInfoWidgetPermission"})
    public void linkedMSISDNProfileManagement() {
        try {
            selUtils.addTestcaseDescription("Validating widgets in profile management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();

            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
            int size = pages.getLinkedMsisdnToAccountNoWidget().getActivePackOnPM();
            if (size > 0) {
                for (int i = 1; i <= size; i++) {
                    if (pages.getLinkedMsisdnToAccountNoWidget().getHeaders(i).equalsIgnoreCase("ACCOUNT INFO")) {
                        assertCheck.append(actions.assertEqualStringType(pages.getLinkedMsisdnToAccountNoWidget().getHeaders(i), "ACCOUNT INFO", "ACCOUNT INFO is visible in profile management", "ACCOUNT INFO is not visible in profile management"));
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
            }


        } catch (Exception e) {
            commonLib.fail("Exception in Method - detailAccountInfoProfileManagement" + e.fillInStackTrace(), true);
        }
    }


}