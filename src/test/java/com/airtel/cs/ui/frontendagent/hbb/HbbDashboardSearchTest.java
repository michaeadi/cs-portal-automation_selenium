package com.airtel.cs.ui.frontendagent.hbb;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.*;

public class HbbDashboardSearchTest extends Driver {
    private String hbbCustomerNumber,hbbAlternateNumber = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            hbbCustomerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN );
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void hbbDashboardSearch() {
        try {
            selUtils.addTestcaseDescription("Searching with valid HBB Number on the search bar of the dashboard page", "description");
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(hbbCustomerNumber);
            pages.getDemoGraphicPage().clickOnDashboardSearch();
            final boolean pageLoaded = pages.getHbbProfilePage().isHBBProfileVisible();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - hbbDashboardSearch " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction","hbbDashboardSearch"})
    public void validateHBBTab() {
        try {
            selUtils.addTestcaseDescription("Validating Hbb Tab", "description");
            hbbAlternateNumber = constants.getValue(ApplicationConstants.HBB_ALTERNATE_MSISDN );
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(hbbAlternateNumber);
            pages.getDemoGraphicPage().clickOnDashboardSearch();
            Boolean tabVisiblity=pages.getHbbProfilePage().isHBBTabVisible();
            assertCheck.append(actions.assertEqualBoolean(tabVisiblity, true, "HBB Tab is displayed", "HBB Tab is not displayed"));
            if(tabVisiblity==true)
            {
                pages.getHbbProfilePage().clickOnHbbTab();
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isPurpleLineVisible(), true, "Purple line is visible under HBB", "Purple line is not visible under HBB "));
            }
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHbbDisplayedNextToGSM(), true, "HBB tab is displayed next to GSM Profile","HBB tab is not displayed next to GSM Profile"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateHBBTab " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction","hbbDashboardSearch"})
    public void gsmProfilePrepaidCheck() {
        try {
            selUtils.addTestcaseDescription("Validating GSM and AM Profile visibility for prepaid number", "description");
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(constants.getValue(CUSTOMER_PREPAID_MSISDN));
            pages.getDemoGraphicPage().clickOnDashboardSearch();
            final boolean profileVisibility=pages.getHbbProfilePage().isGSMAMProfileVisible();
            assertCheck.append(actions.assertEqualBoolean(profileVisibility, true, "GSM Profile is displayed along with AM Profile  ", "GSM Profile is not displayed along with AM Profile "));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - gsmProfilePrepaidCheck  " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = {"openCustomerInteraction","hbbDashboardSearch"})
    public void gsmProfilePostpaidCheck() {
        try {
            selUtils.addTestcaseDescription("Validating GSM and AM Profile visibility for postpaid number  ", "description");
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(constants.getValue(CUSTOMER_POSTPAID_MSISDN));
            pages.getDemoGraphicPage().clickOnDashboardSearch();
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isGSMAMProfileVisible(), true, "GSM Profile is displayed along with AM Profile  ", "GSM Profile is not displayed along with AM Profile "));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - gsmProfilePostpaidCheck " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}
