package com.airtel.cs.ui.ngpsb.demographic;
import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class NubanIdSearchTest extends Driver{
    private static String nubanID,invalidNubanId = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String className = this.getClass().getName();


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void searchNubanId() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid nuban id", "description");
            nubanID = constants.getValue(ApplicationConstants.NUBAN_ID);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(nubanID);
            pages.getMsisdnSearchPage().clickOnSearch();
            clmDetails = api.getCLMDetails(nubanID);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                if (pageLoaded)
                    assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "Nuban id is successfully searched", "Nuban id is NOT successfully searched",false));
                else
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - searchNubanId" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"RegressionTest"},dependsOnMethods = "searchNubanId")
    public void invalidNubanIdTest() {
        try {
            selUtils.addTestcaseDescription("Search invalid Nuban id , Validate error message", "description");
            invalidNubanId = constants.getValue(ApplicationConstants.INVALID_CUSTOMER_MSISDN);
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(invalidNubanId);
            pages.getDemoGraphicPage().clickOnDashboardSearch();
            String errorMessage = "Invalid Nuban ID/MSISDN. Please correct Nuban ID/MSISDN to proceed forward";
            assertCheck.append(actions.assertEqualStringType(pages.getPsbDemographicWidget().getErrorMessage(), errorMessage, "Error message is same as Expected when invalid nuban id is searched", "Error message is not same as Expected when invalid nuban id is searched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidNubanIdTest" + e.fillInStackTrace(), true);
        }
    }

}
