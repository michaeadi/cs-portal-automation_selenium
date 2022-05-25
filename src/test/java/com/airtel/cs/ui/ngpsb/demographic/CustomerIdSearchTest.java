package com.airtel.cs.ui.ngpsb.demographic;
import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CustomerIdSearchTest extends Driver{
    private static String customerId, invalidCustomerId = null;
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
    public void searchCustomerId() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid customer id", "description");
            customerId = constants.getValue(ApplicationConstants.CUSTOMER_ID);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerId);
            pages.getMsisdnSearchPage().clickOnSearch();
            clmDetails = api.getCLMDetails(customerId);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded)
                    assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "Customer id is successfully searched", "Customer id is NOT successfully searched",false));
                else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - searchCustomerId" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"RegressionTest"},dependsOnMethods = "searchCustomerId")
    public void invalidCustomerIdTest() {
        try {
            selUtils.addTestcaseDescription("Search invalid Nuabn id , Validate error message", "description");
            invalidCustomerId = constants.getValue(ApplicationConstants.INVALID_CUSTOMER_ID);
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(invalidCustomerId);
            pages.getDemoGraphicPage().clickOnDashboardSearch();
            String errorMessage = "Invalid customer ID. Please enter correct customer ID to proceed forward";
            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getErrorMessage(), errorMessage, "Error message is same as Expected when invalid nuban id is searched", "Error message is not same as Expected when invalid nuban id is searched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidCustomerIdTest" + e.fillInStackTrace(), true);
        }
    }


}
