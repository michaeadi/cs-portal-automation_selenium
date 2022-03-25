package com.airtel.cs.ui.frontendagent.demographicwidget;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.authconfiguration.AuthDataConfigResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class AirtelMoneyProfileBarTest<assertCheck> extends Driver {

    RequestSource api = new RequestSource();
    AMProfile amProfile;
    String customerNumber;
    Map<String, AuthDataConfigResult> authTabConfig;

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
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateBarUnbar() {
        try {
            pages.getDemoGraphicPage().clickAirtelStatusToUnlock();
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
            pages.getDemoGraphicPage().selectPolicyQuestion();
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthBtnEnable(), true, "Authenticate Button enabled after minimum number of question chosen", "Authenticate Button does not enable after choose minimum number of question"));
            pages.getAuthTabPage().clickAuthBtn();
            assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getWidgetUnlockMessage(), "Unlocking the widget", "Unlock Widget, Successfully", "Unlock Widget, Un-Successful"));
            assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getToastMessage(), "Customer response saved successfully", "Toast Message Shown Successfully", "Toast Message NOT Successful"));
            pages.getAuthTabPage().clickCloseBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getAirtelMoneyProfilePage().isBarredReasonLabelVisible(), true, "Barred reason label Visible", "Barred reason label Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAirtelMoneyProfilePage().isBarredOnLabelVisible(), true, "Barred On label Visible", "Barred On label Visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAirtelMoneyProfilePage().isBarIconVisible(), true, "Bar Icon Visible", "Bar Icon Visible"));
            pages.getAirtelMoneyProfilePage().performAirtelMoneyProfileBar();
            amProfile = api.amServiceProfileAPITest(customerNumber);
            assertCheck.append(actions.assertEqualIntType(amProfile.getStatusCode(), 200, "Am Profile API status code matched and is :" + amProfile.getStatusCode(), "Am Profile API status code NOT matched  and is :" + amProfile.getStatusCode(), false));
            assertCheck.append(actions.assertEqualStringType(pages.getAirtelMoneyProfilePage().isBarredReasonTextVisible(), amProfile.getResult().getBarDetails().getBarReason(), "Barred Reason text Visible", "Barred Reason tex Visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getAirtelMoneyProfilePage().isBarredOnTextVisible(), amProfile.getResult().getBarDetails().getBarredOn(), "Bar On  Text Visible", "Bar On  Text Visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {

            commonLib.fail("Exception in Method - validateBar/Unbar" + e.fillInStackTrace(), true);
        }
    }
}



