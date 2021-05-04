package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPortalTests extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over through API", "Skipping tests because user NOT able to login via API"));
        } else {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user NOT able to login via API"));
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1, description = "Test Login Into Portal with valid credentials")
    public void testLoginIntoPortal() {
        try {
            selUtils.addTestcaseDescription("Logging Into Portal with valid credentials, Validating opened url,validating login button is getting enabled,Validating dashboard page opened successfully or not?", "description");
            final String loginAUUID = constants.getValue(CommonConstants.ALL_USER_ROLE_AUUID);
            final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
            pages.getLoginPage().openBaseURL(value);
            assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), value, "Correct URL Opened", "URl isn't as expected"));
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.ALL_USER_ROLE_PASSWORD)));
            assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is NOT enabled"));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionFA = false;
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getUserManagementPage().isUserManagementPageLoaded(), true, "Customer Dashboard Page Loaded Successfully", "Customer Dashboard page NOT Loaded"));
                actions.assertAllFoundFailedAssert(assertCheck);
            }
        } catch (Exception e) {
            continueExecutionFA = false;
            commonLib.fail("Exception in Method - testLoginIntoPortal" + e.fillInStackTrace(), true);
        }
    }
}