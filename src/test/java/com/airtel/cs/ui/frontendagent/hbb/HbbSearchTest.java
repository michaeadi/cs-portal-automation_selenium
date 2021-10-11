package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class HbbSearchTest extends Driver{

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
    public void validateHbbProfile() {
        try {
            selUtils.addTestcaseDescription("Validating Hbb Profile", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHBBProfileVisible(), true, "HBB Profile is successfully displayed ", "HBB Profile is not displayed"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateHbbProfile " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateHBBTab() {
        try {
            selUtils.addTestcaseDescription("Validate Hbb Tab", "description");
            pages.getMsisdnSearchPage().clearDashboardCustomerNumber();
            pages.getMsisdnSearchPage().enterNumberOnDashboardSearch(constants.getValue("cs.hbb.alternate.msisdn"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHBBTabVisible(), true, "HBB Tab is displayed", "HBB Tab is not displayed"));
            pages.getHbbProfilePage().clickOnHbbTab();
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isPurpleLineVisible(), true, "Purple line is visible under HBB", "Purple line is not visible under HBB "));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHbbDisplayedNextToGSM(), true, "HBB tab is displayed next to GSM Profile","HBB tab is not displayed next to GSM Profile"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateHBBTab " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);

    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void hbbNonAirtelMsisdnSearch() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            hbbNonAirtelCustomerNumber = constants.getValue(ApplicationConstants.HBB_NON_AIRTEL_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbNonAirtelCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getHbbProfilePage().ishbbPageForNonAirtelNo();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            final boolean titleVisible = pages.getHbbProfilePage().isHBBCustomerInteractionTitleVisible();
            assertCheck.append(actions.assertEqualBoolean(titleVisible, true, "Customer Interaction Title visible", "Customer Interaction Title is not visible"));
            final String msisdnVisible = pages.getHbbProfilePage().getMsisdin();
            assertCheck.append(actions.assertEqualStringType(msisdnVisible,hbbNonAirtelCustomerNumber  , "Non Airtel msisdn is visible", "Non Airtel msisdn is not visible"));

        } catch (Exception e) {
            commonLib.fail("Exception in Method -hbbNonAirtelMsisdnSearch " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 4, groups = {"SanityTest","RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void invalidHbbMsisdn() {
        try {
            selUtils.addTestcaseDescription("Validating Search By HBB with invalid MSISDN : 754304rty", "description");
            pages.getMsisdnSearchPage().clearDashboardCustomerNumber();
            pages.getDemoGraphicPage().enterMSISDN(constants.getValue("cs.hbb.invalid.msisdn"));
            pages.getDemoGraphicPage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Invalid Mobile Number",
                    "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - invalidHbbMSISDNTest" + e.fillInStackTrace(), true);
        }
    }
}
