package com.airtel.cs.ui.frontendagent.am;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.bankdetails.BankDetailsResponse;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AmLinkedBankAccountsTest extends Driver {
    String customerNumber = null;
    Boolean isPermissionEnable = false;
    BankDetailsResponse bankDetails;
    public static final String RUN_AM_LINKED_ACCOUNTS = constants.getValue(ApplicationConstants.RUN_AM_LINKED_ACCOUNTS);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"ProdTest", "SmokeTest", "SanityTest", "RegressionTest"})
    public void checkLinkedWalletFlag() {
        if (!StringUtils.equals(RUN_AM_LINKED_ACCOUNTS, "true")) {
            commonLib.skip("Skipping because Run Airtel Money Linked Wallets Test Case Flag Value is - " + RUN_AM_LINKED_ACCOUNTS);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
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
     * This method is used to Open Bank Accounts tab
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasPermission"})
    public void openBankAccountsTab() {
        try {
            selUtils.addTestcaseDescription("Validate AM Transactions Widget visible or not ,Open detailed page of Am Transactions widget , Validate auuid at the footer and middle of widget ", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isAmTransactionsWidgetVisible(), true, "Am Transaction Widget is visible", "Am Transaction Widget is NOT visible"));
            if (isPermissionEnable) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isMoreIconVisible(), true, "AM Transaction Widget detailed icon  is visible", "AM Transaction Widget detailed icon is NOT visible"));
                pages.getAmLinkedWallets().clickMoreIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isAmProfileDetailsDetailWidgetVisible(), true, "Am Profile Details widget is visible", "Am Profile Details widget is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isWalletsVisible(), true, "Wallets tab is visible", "Wallets tab is NOT visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of Wallets widget and is expected ", "Auuid NOT shown at the footer of Wallets widget"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of Wallets widget and is expected", "Auuid NOT shown at the middle of Wallets widget"));
            } else
                commonLib.fail("Am Profile Details widget is not visible as user has not permission to view it", true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openBankAccountsTab" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to test Wallets tab Layout
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openBankAccountsTab")
    public void testBankAccountsLayout() {
        try {
            selUtils.addTestcaseDescription("Validate all the fields are visible or not in Wallets", "description");
            bankDetails = api.getAmBankDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(bankDetails.getStatusCode(), 200, "Am Profile API status code matched and is :" + bankDetails.getStatusCode(), "Am Profile API status code NOT matched  and is :" + bankDetails.getStatusCode(), false));
            if (bankDetails.getStatusCode() == 200 && bankDetails.getResult().size() == 0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                commonLib.warning("No Account is linked to this MSISDN");
            } else if (bankDetails.getStatusCode() == 3007 && bankDetails.getStatus().equalsIgnoreCase("Failure")) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isWidgetErrorMessageVisible(), true, "CS API and widget both are giving error", "CS API is giving error but widget is not showing error message"));
                commonLib.fail("CS API is unable to give Linked Wallets data ", true);
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isWalletTypeVisible(), true, "Wallet Type is visible", "Wallet Type is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isBalanceVisible(), true, "Balance is visible", "Balance is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isProfileIdVisible(), true, "Profile Id is visible", "Profile Id  is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isFicVisible(), true, "FIC is visible", "FIC is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isFrozenAmountVisible(), true, "Frozen Amount is visible", "Frozen Amount is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isPrimaryVisible(), true, "Primary is visible", "Primary is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isTotalDebitVisible(), true, "Total Debit is visible", "Total Debit is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isTotalCreditVisible(), true, "Total Credit is visible", "Total Credit is NOT visible"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBankAccountsLayout" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 5, groups = {"SanityTest", "ProdTest", "RegressionTest"}, dependsOnMethods = {"openBankAccountsTab"})
    public void testBankAccounts() {
        try {
            selUtils.addTestcaseDescription("Validate Bank Accounts tab data", "description");
            bankDetails = api.getAmBankDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(bankDetails.getStatusCode(), 200, "Bank Details API Status Code Matched and is :" + bankDetails.getStatusCode(), "Bank Details API Status Code NOT Matched and is :" + bankDetails.getStatusCode(), false));
            if (bankDetails.getStatusCode() == 200 && bankDetails.getResult().size() == 0) {
                commonLib.warning("No Account is linked to this MSISDN");
            } else if (bankDetails.getStatusCode() == 2500 && bankDetails.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Bank Accounts", true);
            } else {
                int size = pages.getAmSmsTrails().checkRowSize();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 1), bankDetails.getResult().get(i).getAccountNumber(), "Account No. is same as expected ", "Account No.is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 2), bankDetails.getResult().get(i).getCustomerNo(), "Customer No. is same as expected ", "Account No.is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 3), bankDetails.getResult().get(i).getOpeningBalance(), "Opening Balance is same as expected ", "Opening Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 4), bankDetails.getResult().get(i).getAvailableBalance(), "Available Balance is same as expected ", "Available Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 5), bankDetails.getResult().get(i).getFrozen(), "Frozen Balance is same as expected ", "Frozen Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValue(row, 6), bankDetails.getResult().get(i).getCurrentBalance(), "Current Balance is same as expected ", "Current Balance is NOT same as expected"));
                    String accountStatus = pages.getBankAccount().getHeaderValue(row, 7);
                    assertCheck.append(actions.assertEqualStringType(accountStatus, bankDetails.getResult().get(i).getStatus(), "Account status is same as expected ", "Account status is NOT same as expected"));
                    if (accountStatus.equalsIgnoreCase("ACTIVE"))
                        assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValueColor(row, 7), "#33a833", "Colour of Account Status is same as expected", "Colour of Account Status is NOT same as expected"));
                    else if (accountStatus.equalsIgnoreCase("INACTIVE"))
                        assertCheck.append(actions.assertEqualStringType(pages.getBankAccount().getHeaderValueColor(row, 7), "#e4000e", "Colour of Account Status is same as expected", "Colour of Account Status is NOT same as expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testBankAccounts" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"isUserHasPermission"})
    public void profileManagementTest() {
        try {
            selUtils.addTestcaseDescription("Validating Am Profile Details in profile management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openProfileManagementPage();
            pages.getProfileManagement().viewRoleWithName(constants.getValue(CommonConstants.ALL_USER_ROLE_NAME));
            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isEditPageLoaded(), true, "Profile Management edit role config page open as expected", "Profile Management edit role config page open does not as expected"));
            int size = pages.getProfileManagement().getActiveTabsOnPM();
            if (size > 0)
                for (int i = 1; i <= size; i++) {
                    if (pages.getProfileManagement().getHeaders(i).equalsIgnoreCase("AM PROFILE DETAILS")) {
                        assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isAccountInfoButtonEnable(), true, "Am Profile Details tab button is enable", "Am Profile Details tab is not enable"));
                        pages.getProfileManagement().openAmProfileDetails();
                        int chkBoxSize = pages.getProfileManagement().isChkBoxEnable();
                        if (chkBoxSize > 0)
                            assertCheck.append(actions.assertEqualBoolean(pages.getProfileManagement().isPostpaidAccountInfoChkBoxVisibile(), true, "Checkbox is visible for AM Profile Details", "Checkbox is not visible for AM Profile Details"));
                    }
                }

        } catch (Exception e) {
            commonLib.fail("Exception in Method - profileManagementTest" + e.fillInStackTrace(), true);
        }
    }
}
