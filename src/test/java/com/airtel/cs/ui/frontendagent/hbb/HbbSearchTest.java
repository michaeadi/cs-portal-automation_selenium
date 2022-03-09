package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.authconfiguration.Configuration;
import com.airtel.cs.model.response.hbb.HbbLinkedAccountsResponse;
import com.airtel.cs.model.response.kycprofile.GsmKyc;
import com.airtel.cs.model.response.kycprofile.KYCProfile;
import org.testng.IDynamicGraph;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.HBB_ALTERNATE_MSISDN;
import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.HBB_INVALID_MSISDN;


public class HbbSearchTest extends Driver{

    private  static String hbbCustomerNumber, hbbAlternateNumber,hbbNonAirtelCustomerNumber,invalidMsisdn = null;
    private String value;
    KYCProfile kycProfile;
    Configuration config;
    GsmKyc gsmKycAPI;


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


    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "SmokeTest","ProdTest"})
    public void hbbNonAirtelMsisdnSearch() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            hbbNonAirtelCustomerNumber = constants.getValue(ApplicationConstants.HBB_NON_AIRTEL_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbNonAirtelCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getHbbProfilePage().ishbbPageForNonAirtelNo();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page for Non Airtel Msisdn Loaded Successfully", "HBB Page for Non Airtel Msisdn not Loaded"));
            final boolean titleVisible = pages.getHbbProfilePage().isHBBCustomerInteractionTitleVisible();
            assertCheck.append(actions.assertEqualBoolean(titleVisible, true, "Customer Interaction Title visible", "Customer Interaction Title is not visible"));
            final String msisdnVisible = pages.getHbbProfilePage().getAttribute(pages.getHbbProfilePage().pageElements.searchNonAirtel, value,false);
            assertCheck.append(actions.assertEqualStringType(msisdnVisible,hbbNonAirtelCustomerNumber  , "Non Airtel msisdn is visible", "Non Airtel msisdn is not visible"));

        } catch (Exception e) {
            commonLib.fail("Exception in Method -hbbNonAirtelMsisdnSearch " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void validateHBBTab() {
        try {
            selUtils.addTestcaseDescription("Validate Hbb Tab", "description");
            hbbAlternateNumber = constants.getValue(ApplicationConstants.HBB_ALTERNATE_MSISDN);
            pages.getHbbProfilePage().searchNonAirtelMsisdnBox(hbbAlternateNumber);
            pages.getHbbProfilePage().hardWait(10);
            Boolean tabVisibility=pages.getHbbProfilePage().isHBBTabVisible();
            assertCheck.append(actions.assertEqualBoolean(tabVisibility, true, "HBB Tab is displayed", "HBB Tab is not displayed"));
            if(tabVisibility==true) {
                pages.getHbbProfilePage().clickOnHbbTab();
                assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isPurpleLineVisible(), true, "Purple line is visible under HBB", "Purple line is not visible under HBB "));
            }
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHbbDisplayedNextToGSM(), true, "HBB tab is displayed next to GSM Profile","HBB tab is not displayed next to GSM Profile"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateHBBTab " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);

    }

    @Test(priority = 5, groups = {"SanityTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void hbbLinkedNumbers() {
        try {
            selUtils.addTestcaseDescription("Validating list of hbb numbers in case they are linked with alternate number ", "description");
            pages.getHbbProfilePage().clickOnHbbTab();
            HbbLinkedAccountsResponse hbbLinkedNumbers = api.getLinkedHbbNumber(hbbCustomerNumber);
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
    @Test(priority = 6, groups = {"SanityTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void hbbTabVisibilityForNonHbbNumber() {
        try {
            selUtils.addTestcaseDescription("Validating hbb tab visible  in case of non hbb number ", "description");
            lineType = kycProfile.getResult().getLineType().toLowerCase().trim();
            config = api.getConfiguration("customerDemographicDetailsWidgets", lineType);
            int flag = pages.getDemoGraphicPage().checkConfigurationWithTab(config, "HBB");
            if (flag == 1)
                commonLib.info("Hbb Tab is visible ");
            else
                commonLib.info("Hbb Tab is not  visible ");


        } catch (Exception e) {
            commonLib.fail("Exception in Method - hbbLinkedNumbers" + e.fillInStackTrace(), true);
        }

    }
}

