package com.airtel.cs.ui.ngpsb.demographic;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CustomerInteractionScreenTest extends Driver {
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String className = this.getClass().getName();
    String customerNumber;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void suggestionsCheckTest() {
        try {
            selUtils.addTestcaseDescription("Open Customer Interaction Page, Validate all the suggestions", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            String watermark = "Enter Mobile Number/Nuban ID/Customer ID";
            assertCheck.append(actions.assertEqualStringType(pages.getCustomerInteractionScreen().getSearchBoxWatermark().toLowerCase().trim(), watermark.toLowerCase().trim(), "Watermark text is as Expected", "Watermark text is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isSuggestionsVisible()), true, "Suggestions is visible", "Suggestions is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isMsisdnRegexVisible()), true, "Msisdn Regex is visible", "Msisdn Regex  is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isCustomerIdRegexVisible()), true, "Customer ID Regex  is visible", "Customer ID is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isNubanIdRegexVisible()), true, "Nuban ID Regex is visible", "Nuban ID Regex is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - suggestionsCheckTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void intermediateScreenHeaderTest() {
        try {
            selUtils.addTestcaseDescription("Validate all the headers of intermediate screen,", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER3_MSISDN);
            pages.getCustomerInteractionScreen().searchMsisdn(customerNumber);
            assertCheck.append(actions.assertEqualBoolean(pages.getPsbDemographicWidget().isActionVisible(), true, " Action is visible ", "Action is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getPsbDemographicWidget().isTypeVisible(), true, " Type is visible ", "Type is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getPsbDemographicWidget().isNubanIdVisible(), true, "Nuban Id is visible ", "Nuban Id is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getPsbDemographicWidget().isMsisdnVisible(), true, "Msisdn is visible ", "Msisdn is not visible "));
            assertCheck.append(actions.assertEqualBoolean(pages.getPsbDemographicWidget().isCreatedOnVisible(), true, "Created On is visible ", "Created on is not visible "));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - intermediateScreenHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"intermediateScreenHeaderTest"})
    public void intermediateScreenDataTest() {
        try {
            selUtils.addTestcaseDescription("Validate all the data of intermediate screen", "description");
            pages.getCustomerInteractionScreen().searchMsisdn(customerNumber);
            clmDetails = api.getCLMDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                int walletSize = 0, accountSize = 0;
                for (int i = 0; i < clmDetails.getResult().getDetails().size(); i++) {
                    walletSize = walletSize + clmDetails.getResult().getDetails().get(i).getWallets().size();
                    accountSize = accountSize + clmDetails.getResult().getDetails().get(i).getAccounts().size();
                }
                int totalSize = walletSize + accountSize;
                if (totalSize > 1) {
                    int row = 0, x = 1;
                    for (int i = 0; i < clmDetails.getResult().getDetails().size(); i++) {
                        for (int accounts = 0; accounts < clmDetails.getResult().getDetails().get(i).getAccounts().size(); accounts++) {
                            commonLib.info("Checking for row :" + (row + x));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 1), pages.getBasePage().getKeyValueAPI(clmDetails.getResult().getDetails().get(i).getMsisdn()), "Msisdn is same as Expected", "Msisdn is not same as Expected"));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 2), pages.getBasePage().getKeyValueAPI(clmDetails.getResult().getDetails().get(i).getAccounts().get(accounts).getId()), "Account Nuban id is same as Expected", "Account nuban id is not same as Expected"));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 3).trim(), "Account", "Data for Type is same as Expected", "Data for Type is not same as Expected"));
                            String createdOnDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(i).getAccounts().get(accounts).getCreatedOn()), constants.getValue(CommonConstants.NGPSB_ACCOUNT_CREATED_DATE_PATTERN));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 4).toLowerCase(), pages.getBasePage().getKeyValueAPI(createdOnDate).toLowerCase(), "Account Created On is same as Expected", "Account Created On is not same as Expected"));
                            row++;
                        }
                        for (int wallets = 0; wallets < clmDetails.getResult().getDetails().get(i).getWallets().size(); wallets++) {
                            commonLib.info("Checking for row :" + (row + x));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 1).trim(), pages.getBasePage().getKeyValueAPI(clmDetails.getResult().getDetails().get(i).getMsisdn()), "Msisdn is same as Expected", "Msisdn is not same as Expected"));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 2), pages.getBasePage().getKeyValueAPI(clmDetails.getResult().getDetails().get(i).getWallets().get(wallets).getId()), "Wallet Nuban id is same as Expected", "Wallet nuban id is not same as Expected"));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 3).trim(), "Wallet", "Data for Type same as Expected", "Data for Type is not same as Expected"));
                            String createdOnDate = UtilsMethods.getDateFromEpoch(Long.parseLong(clmDetails.getResult().getDetails().get(i).getWallets().get(wallets).getCreatedOn()), constants.getValue(CommonConstants.NGPSB_WALLET_CREATED_DATE_PATTERN));
                            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getHeaderValue(row + 1, 4).toLowerCase(), pages.getBasePage().getKeyValueAPI(createdOnDate).toLowerCase(), "Wallet Created On is same as Expected", "Wallet Created On is not same as Expected"));
                            row++;
                        }
                    }
                } else
                    commonLib.warning("There is only one wallet or account linked with the searched msisdn so intermediate screen is not visible");
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - intermediateScreenDataTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void msisdnSearchTest() {
        try {
            selUtils.addTestcaseDescription("Search msisdn from customer interaction page, Msisdn should get displayed on Customer Dashboard's search box", "description");
            String msisdn = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
            pages.getCustomerInteractionScreen().searchMsisdn(msisdn);
            clmDetails = api.getCLMDetails(msisdn);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded)
                    assertCheck.append(actions.assertEqualStringType(pages.getCustomerInteractionScreen().getCustomerDashboardSearchBoxText().trim(), customerNumber, "Msidin is displayed on Customer Dashboard's search box on searching the same from customer interaction page", " Msidin is NOT displayed on Customer Dashboard's search box on searching the same from customer interaction page"));
                else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - msisdnSearchTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void nubanIdSearchTest() {
        try {
            selUtils.addTestcaseDescription("Search Nuban ID from customer interaction page, Nuban ID should get displayed on Customer Dashboard's search box", "description");
            String nubanId = constants.getValue(ApplicationConstants.NUBAN_ID);
            pages.getCustomerInteractionScreen().searchMsisdn(nubanId);
            clmDetails = api.getCLMDetails(nubanId);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded)
                    assertCheck.append(actions.assertEqualStringType(pages.getCustomerInteractionScreen().getCustomerDashboardSearchBoxText().trim(), nubanId, "Nuban ID is displayed on Customer Dashboard's search box on searching the same from customer interaction page", " Nuban ID is NOT displayed on Customer Dashboard's search box on searching the same from customer interaction page"));
                else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - nubanIdSearchTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void customerIdSearchTest() {
        try {
            selUtils.addTestcaseDescription("Search Customer Id from customer interaction page, Customer Id should get displayed on Customer Dashboard's search box", "description");
            String customerId = constants.getValue(ApplicationConstants.CUSTOMER_ID);
            pages.getCustomerInteractionScreen().searchMsisdn(customerId);
            clmDetails = api.getCLMDetails(customerId);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded)
                    assertCheck.append(actions.assertEqualStringType(pages.getCustomerInteractionScreen().getCustomerDashboardSearchBoxText().trim(), customerId, "Customer Id is displayed on Customer Dashboard's search box on searching the same from customer interaction page", " Customer Id is NOT displayed on Customer Dashboard's search box on searching the same from customer interaction page"));
                else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - customerIdSearchTest" + e.fillInStackTrace(), true);
        }
    }


}
