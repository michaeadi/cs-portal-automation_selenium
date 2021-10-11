package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HbbPrePostpaidTest extends Driver {

    private static String customerNumber = null;

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
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_PREPAID_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void gsmProfilePrepaidCheck() {
        try {
            selUtils.addTestcaseDescription("Validating GSM and AM Profile visibility for prepaid number  ", "description");
            final boolean profileVisibility=pages.getHbbProfilePage().isGSMAMProfileVisible();
            assertCheck.append(actions.assertEqualBoolean(profileVisibility, true, "GSM Profile is displayed along with AM Profile  ", "GSM Profile is not displayed along with AM Profile "));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - gsmProfilePrepaidCheck  " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void gsmProfilePostpaidCheck() {
        try {
            selUtils.addTestcaseDescription("Validating GSM and AM Profile visibility for postpaid number  ", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN );
            pages.getDemoGraphicPage().clearSearchBox(9);
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getHbbProfilePage().isGSMAMProfileVisible();
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isGSMAMProfileVisible(), true, "GSM Profile is displayed along with AM Profile  ", "GSM Profile is not displayed along with AM Profile "));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - gsmProfilePostpaidCheck " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


}

