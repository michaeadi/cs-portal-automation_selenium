package com.airtel.cs.ui.ngpsb.demographic;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CustomerInteractionScreenTest extends Driver {
    private static String customerNumber=null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String space = " ";
    String className = this.getClass().getName();
    boolean isPermissionEnable = false;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }


    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            String text="Enter Mobile Number/Nuban ID/Customer ID";
            assertCheck.append(actions.assertEqualStringType(pages.getCustomerInteractionScreen().getSearchBoxText().toLowerCase().trim(),text.toLowerCase().trim() , " is as Expected", " is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isSuggestionsVisible()),true , "Suggestions is visible", "Suggestions is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isMsisdnRegexVisible()),true , "Msisdn Regex is visible", "Msisdn Regex  is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isCustomerIdRegexVisible()),true , "Customer ID Regex  is visible", "Customer ID is NOT visible"));
            assertCheck.append(actions.assertEqualBoolean((pages.getCustomerInteractionScreen().isNubanIdRegexVisible()),true , "Nuban ID Regex  is visible", "Nuban ID is NOT visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isUserHasPermission() {
        try {
            selUtils.addTestcaseDescription("Validate whether user has AM Customer Details Permission", "description");
            String permission = constants.getValue(PermissionConstants.AM_CUSTOMER_DETAILS_PERMISSION);
            isPermissionEnable = UtilsMethods.isUserHasPermission(permission);
            assertCheck.append(actions.assertEqualBoolean(isPermissionEnable, true, "Logged in user has Am Customer details permission to view the customer details", "Logged in user doesn't has Am Customer details permission to view the customer details"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermission " + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasPermission"})
    public void msisdnSearchTest() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            clmDetails = api.getCLMDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                if (isPermissionEnable) {
                    boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                    if (!pageLoaded) continueExecutionFA = false;
                } else commonLib.warning("Logged in user doesn't has permission to view customer details");

            } else commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


}
