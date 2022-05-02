package com.airtel.cs.ui.frontendagent.am;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AmLinkedWalletsTest extends Driver {
    String customerNumber = null;
    AMProfile amProfile;
    Boolean isPermissionEnable = false;
    int size;
    public static final String RUN_AM_LINKED_WALLET = constants.getValue(ApplicationConstants.RUN_AM_LINKED_WALLET);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"ProdTest", "SmokeTest"})
    public void checkLinkedWalletFlag() {
        if (!StringUtils.equals(RUN_AM_LINKED_WALLET, "true")) {
            commonLib.skip("Skipping because Run Airtel Money Linked Wallets Test Case Flag Value is - " + RUN_AM_LINKED_WALLET);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
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
     * This method is used to Open Wallets tab
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction", "isUserHasPermission"})
    public void openWalletsTab() {
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
            commonLib.fail("Exception in Method - openWalletsTab" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to test Wallets tab Layout
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testWalletsLayout() {
        try {
            selUtils.addTestcaseDescription("Validate all the fields are visible or not in Wallets", "description");
            amProfile = api.amServiceProfileAPITest(customerNumber);
            assertCheck.append(actions.assertEqualIntType(amProfile.getStatusCode(), 200, "Am Profile API status code matched and is :" + amProfile.getStatusCode(), "Am Profile API status code NOT matched  and is :" + amProfile.getStatusCode(), false));
            if (amProfile.getStatusCode() == 200 && amProfile.getResult().getWallets().size() == 0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAmLinkedWallets().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                commonLib.warning("Linked Wallets data is not available for the punched msisdn");
            } else if (amProfile.getStatusCode() == 3007 && amProfile.getStatus().equalsIgnoreCase("Failure")) {
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
            commonLib.fail("Exception in Method - testWalletsLayout" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to check Wallets data
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testWalletsData() {
        try {
            selUtils.addTestcaseDescription("Validate data of all the fields of Wallets tab", "description");
            if (amProfile.getStatusCode() == 200 && amProfile.getResult().getWallets().size() == 0) {
                commonLib.warning("Linked Wallets data is not available for the punched msisdn");
            } else if (amProfile.getStatusCode() == 3007 && amProfile.getStatus().equalsIgnoreCase("status.failure")) {
                commonLib.fail("CS API is unable to give Linked Wallets data ", true);
            } else {
                size = pages.getAmLinkedWallets().getNoOfRows();
                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    String currency = amProfile.getResult().getWallets().get(i).getCurrency();
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 1), amProfile.getResult().getWallets().get(i).getWalletType(), "Wallet Type is same as expected ", "Wallet Type is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 2), amProfile.getResult().getWallets().get(i).getTcpId(), "Tcp Id is same as expected ", "Tcp Id is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 3), currency + " " + amProfile.getResult().getWallets().get(i).getBalance(), "Balance is same as expected ", "Balance is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 4), currency + " " + amProfile.getResult().getWallets().get(i).getFrozen(), "Frozen Amount is same as expected ", "Frozen Amount is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 5), currency + " " + amProfile.getResult().getWallets().get(i).getFundsInClearance(), "FIC is same as expected ", "FIC is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 6), amProfile.getResult().getWallets().get(i).getPrimary(), "Primary Value is same as expected ", "Primary Value is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 7), currency + " " + amProfile.getResult().getWallets().get(i).getTotalCredit(), "Total Credit is same as expected ", "Total Credit is NOT same as expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmLinkedWallets().getRowValue(row, 8), currency + " " + amProfile.getResult().getWallets().get(i).getTotalDebit(), "Total Debit is same as expected ", "Total Debit is NOT same as expected"));
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testWalletsData" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate widgets in profile management
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasPermission"})
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
