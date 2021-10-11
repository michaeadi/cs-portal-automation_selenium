package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.hbb.HbbLinkedAccountsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class HbbAlternateNoTest extends Driver{


    private  static String hbbCustomerNumber, hbbNonAirtelCustomerNumber = null;

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
            hbbCustomerNumber = constants.getValue(ApplicationConstants.HBB_ALTERNATE_MSISDN );
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void hbbLinkedNumbers() {
        try {
            selUtils.addTestcaseDescription("Validating list of hbb numbers in case they are linked with alternate number ", "description");
            pages.getHbbProfilePage().clickOnHbbTab();
            HbbLinkedAccountsResponse hbbLinkedNumbers = api.getLinkedHbbNumber(hbbNonAirtelCustomerNumber);
            Integer statusCode = hbbLinkedNumbers.getStatusCode();
            List listOfLinkedNumbers = hbbLinkedNumbers.getResult();
            Integer count = listOfLinkedNumbers.size();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HBB Linked Numbers  API Status Code Matched and is :" + statusCode, "HBB Linked Numbers  Status Code NOT Matched and is :" + statusCode, false));
            assertCheck.append(actions.assertEqualIntType(pages.getHbbProfilePage().getHbbLinkedNumbers(), count, "Count of HBB Linked Numbers is correct:" + count, "Count of HBB Linked Numbers is incorrect:" + count, false));
        }
            catch (Exception e) {
                commonLib.fail("Exception in Method - hbbLinkedNumbers" + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);

        }
    }
