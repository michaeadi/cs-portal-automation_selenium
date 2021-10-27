package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.hbb.HbbLinkedAccountsResponse;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants.HBB_INVALID_MSISDN;

public class HbbNonAirtelMsisdnTest extends Driver{


    private  String hbbNumber, hbbNonAirtelCustomerNumber , hbbInvalidNumber,hbbAlternateNumber, prepaidNumber,postpaidNumber= null;

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
            hbbNonAirtelCustomerNumber = constants.getValue(ApplicationConstants.HBB_NON_AIRTEL_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbNonAirtelCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getHbbProfilePage().ishbbPageForNonAirtelNo();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page for Non Airtel Msisdn Loaded Successfully", "HBB Page for Non Airtel Msisdn not Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void hbbCustomerInfoTest() {
        try {
            selUtils.addTestcaseDescription("Validating HBB Customer Information  ", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().firstName(), true, "First Name is displayed", "First Name is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().middleName(), true, "Middle Name is displayed", "Middle Name is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().lastName(), true, "Last Name is displayed", "Last Name is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().userName(), true, "User Name is displayed", "User Name is not displayed"));
        assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().dob(), true, "DOB is displayed", "DOB is not displayed"));
        assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().emailID(), true, "Email ID  is displayed", "Email ID  is not displayed"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - hbbCustomerInfoTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void nonAirtelMsisdnSearch() {
        try {
            selUtils.addTestcaseDescription("Validating Non Airtel Msisin Page by entering invalid number,hbb Number, Alternate Number ", "description");
            hbbInvalidNumber = constants.getValue(ApplicationConstants.HBB_INVALID_MSISDN);
            pages.getHbbProfilePage().searchNonAirtelMsisdnBox(hbbInvalidNumber);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isErrorDisplayed(), true,
                        "Error Message is visible", "Error Message is not  visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getHbbProfilePage().getInvalidText(), "Invalid Mobile Number",
                        "Error Message Correctly Displayed", "Error Message is not  Displayed Correctly"));
            commonLib.info("Validating for hbb Number");
            hbbNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
            pages.getHbbProfilePage().searchNonAirtelMsisdnBox(hbbNumber);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isHBBProfileVisible(), true, "HBB Profile is successfully displayed after entering number from Non Airtel Page", "HBB Profile is not displayed"));
            commonLib.info("Validating for alternate Number");
            hbbAlternateNumber = constants.getValue(ApplicationConstants.HBB_ALTERNATE_MSISDN);
            pages.getHbbProfilePage().openCustomerInteraction(hbbNonAirtelCustomerNumber);
            pages.getHbbProfilePage().searchNonAirtelMsisdnBox(hbbAlternateNumber);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isGSMProfileVisible(), true, "HBB Tab is displayed", "HBB Tab is not displayed"));
        }
            catch (Exception e) {
                commonLib.fail("Exception in Method - NonAirtelMsisdnSearch" + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);

        }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void nonAirtelMsisdnPrePostNoSearch() {
        try {
            selUtils.addTestcaseDescription("Validating Non Airtel Msisin Page by entering prepaid and post number ", "description");
            prepaidNumber= constants.getValue(ApplicationConstants.CUSTOMER_PREPAID_MSISDN);
            pages.getHbbProfilePage().openCustomerInteraction(hbbNonAirtelCustomerNumber);
            pages.getHbbProfilePage().searchNonAirtelMsisdnBox(prepaidNumber);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isGSMAMProfileVisible(), true, "HBB Tab is displayed", "HBB Tab is not displayed"));
            postpaidNumber= constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            pages.getHbbProfilePage().openCustomerInteraction(hbbNonAirtelCustomerNumber);
            pages.getHbbProfilePage().searchNonAirtelMsisdnBox(postpaidNumber);
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isGSMAMProfileVisible(), true, "HBB Tab is displayed", "HBB Tab is not displayed"));
        }
        catch (Exception e) {
            commonLib.fail("Exception in Method - NonAirtelMsisdnPrePostSearch" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);

    }




    }
