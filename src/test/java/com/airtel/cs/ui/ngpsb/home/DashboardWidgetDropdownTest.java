package com.airtel.cs.ui.ngpsb.home;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class DashboardWidgetDropdownTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String className = this.getClass().getName();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER3_MSISDN);
            pages.getCustomerInteractionScreen().searchMsisdn(customerNumber);
            clmDetails = api.getCLMDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (!pageLoaded)
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void dropdownHeadersTest() {
        try {
            selUtils.addTestcaseDescription("Validate dropdown headers", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDashboardWidgetDropdown().isWidgetDropdownVisible(), true, "Dashboard widget dropdown is visible", "Dashboard widget dropdown is not visible", false));
            assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentMsisdnValue().trim(), "Current - " + customerNumber, "Current msisdn value is same as expected", "Current msisdn value is not same as expected", false));
            pages.getDashboardWidgetDropdown().clickDropdownArrow();
            assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentlySelectedMsisdnHeader(), "MSISDN", "Currently selected msisdn's  header is same as expected", "Current msisdn's header is not same as expected", false));
            assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentlySelectedMsisdnValue(), customerNumber, "Currently selected msisdn's value is same as expected", "Currently selected msisdn's value is not same as expected", false));
            int walletSize = clmDetails.getResult().getDetails().get(0).getWallets().size();
            int accountSize = clmDetails.getResult().getDetails().get(0).getAccounts().size();
            assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getLinkedWalletMessage().trim(), walletSize + " Wallet(s)", "Linked Wallet(s) message is same as expected", "Linked Wallet(s) message is not same as expected", false));
            assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getLinkedAccountMessage().trim(), accountSize + " Account(s)", "Linked Account(s) message is same as expected", "Linked Account(s) message is not same as expected", false));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - dropdownHeadersTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction", "dropdownHeadersTest"})
    public void dropdownDataTest() {
        try {
            selUtils.addTestcaseDescription("Validate dropdown data for linked Account and Wallet", "description");
            String nubanId = pages.getDashboardWidgetDropdown().getNubanId();
            for (int account = 0; account < pages.getDashboardWidgetDropdown().checkAccountRowSize(); account++) {
                int x = 1;
                String accountNubanId = clmDetails.getResult().getDetails().get(0).getAccounts().get(account).getId();
                assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getAccountNubanId(account + 1), accountNubanId, "Account Nuabn id is same as expected for row : " + (account + x), "Account Nuabn id is not same as expected for row :" + (account + x), false));
                if (nubanId.equalsIgnoreCase(accountNubanId)) {
                    String accountHoverMessage = "You are already viewing selected Account details";
                    pages.getDashboardWidgetDropdown().hoverCurrentlySelectedAccount(account + 1);
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getHoverMessage(), accountHoverMessage, "Hovering message for currently selected Account is same as expected", "Hovering message for currently selected Account is same as expected", false));
                }
                x++;
            }
            for (int wallet = 0; wallet < pages.getDashboardWidgetDropdown().checkWalletRowSize(); wallet++) {
                int x = 1;
                String walletNubanId = clmDetails.getResult().getDetails().get(0).getWallets().get(wallet).getId();
                assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getWalletNubanId(wallet + 1), clmDetails.getResult().getDetails().get(0).getWallets().get(wallet).getId(), "Wallet Nuabn id is same as expected for row : " + (wallet + x), "Wallet Nuabn id is same as expected for row : " + (wallet + x), false));
                if (nubanId.equalsIgnoreCase(walletNubanId)) {
                    String walletHoverMessage = "You are already viewing selected Wallet details";
                    pages.getDashboardWidgetDropdown().hoverCurrentlySelectedWallet(wallet + 1);
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getHoverMessage(), walletHoverMessage, "Hovering message for currently selected Wallet is same as expected", "Hovering message for currently selected Wallet is same as expected", false));
                }
                x++;
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - dropdownDataTest" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction","dropdownHeadersTest"})
    public void CTAClickTest() {
        try {
            selUtils.addTestcaseDescription("Validate that Wallet and Account Information widget should be visible after clicking their respective CTA's", "description");
            int flag = 0;
            for (int account = 0; account < clmDetails.getResult().getDetails().get(0).getAccounts().size(); account++) {
                int x = 1;
                pages.getDashboardWidgetDropdown().clickCTAOfAccount(account + 1);
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformation().isAccountInformationWidgetVisible(), true, "Account Information widget is visible after clicking CTA of Account for row : " + (account + x), "Account Information widget is NOT visible after clicking CTA of Account for row : " + (account + x)));
                flag++;
                break;
            }
            for (int wallet = 0; wallet < clmDetails.getResult().getDetails().get(0).getWallets().size(); wallet++) {
                int x = 1;
                if (flag > 0)
                    pages.getDashboardWidgetDropdown().clickDropdownArrow();
                pages.getDashboardWidgetDropdown().clickCTAOfWallet(wallet + 1);
                assertCheck.append(actions.assertEqualBoolean(pages.getWalletInformation().isWalletInformationWidgetVisible(), true, "Wallet Information widget is visible after clicking CTA of Wallet for row : " + (wallet + x), "Wallet Information widget is NOT visible after clicking CTA of Wallet for row : " + (wallet + x)));
                break;
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - CTAClickTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void searchWithCustomerId() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid Customer Id , Validate widget dropdown", "description");
            String customerId = constants.getValue(ApplicationConstants.CUSTOMER_ID);
            pages.getCustomerInteractionScreen().searchMsisdn(customerId);
            clmDetails = api.getCLMDetails(customerId);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getDashboardWidgetDropdown().isWidgetDropdownVisible(), true, "Dashboard widget dropdown is visible", "Dashboard widget dropdown is not visible", false));
                    String msisdn = clmDetails.getResult().getDetails().get(0).getMsisdn();
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentMsisdnValue().trim(), "Current - " + msisdn, "Current msisdn value is same as expected", "Current msisdn value is not same as expected", false));
                    pages.getDashboardWidgetDropdown().clickDropdownArrow();
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentlySelectedMsisdnHeader(), "MSISDN", "Currently selected msisdn's  header is same as expected", "Current msisdn's header is not same as expected", false));
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentlySelectedMsisdnValue(), msisdn, "Currently selected msisdn's value is same as expected", "Currently selected msisdn's value is not same as expected", false));
                } else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - searchWithCustomerId" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void searchWithNubanId() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid Nuban Id , Validate widget dropdown", "description");
            String nubanId = constants.getValue(ApplicationConstants.NUBAN_ID);
            pages.getCustomerInteractionScreen().searchMsisdn(nubanId);
            clmDetails = api.getCLMDetails(nubanId);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getDashboardWidgetDropdown().isWidgetDropdownVisible(), true, "Dashboard widget dropdown is visible", "Dashboard widget dropdown is not visible", false));
                    String msisdn = clmDetails.getResult().getDetails().get(0).getMsisdn();
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentMsisdnValue().trim(), "Current - " + msisdn, "Current msisdn value is same as expected", "Current msisdn value is not same as expected", false));
                    pages.getDashboardWidgetDropdown().clickDropdownArrow();
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentlySelectedMsisdnHeader(), "MSISDN", "Currently selected msisdn's  header is same as expected", "Current msisdn's header is not same as expected", false));
                    assertCheck.append(actions.assertEqualStringType(pages.getDashboardWidgetDropdown().getCurrentlySelectedMsisdnValue(), msisdn, "Currently selected msisdn's value is same as expected", "Currently selected msisdn's value is not same as expected", false));
                } else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - searchWithNubanId" + e.fillInStackTrace(), true);
        }
    }
}
