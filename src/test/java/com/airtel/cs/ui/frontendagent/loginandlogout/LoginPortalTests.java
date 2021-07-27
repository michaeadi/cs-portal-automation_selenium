package com.airtel.cs.ui.frontendagent.loginandlogout;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPortalTests extends Driver {

    String auth;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testLoginIntoPortal() {
        try {
            selUtils.addTestcaseDescription("Logging Into Portal with valid All user credentials, Validating opened url,validating login button is getting enabled,Validating dashboard page opened successfully or not?", "description");
            loginAUUID = constants.getValue(CommonConstants.ALL_USER_ROLE_AUUID);
            final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
            pages.getLoginPage().openBaseURL(value);
            assertCheck.append(actions.assertEqualStringType(driver.getCurrentUrl(), value, "Correct URL Opened", "URl isn't as expected"));
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.ALL_USER_ROLE_PASSWORD)));
            assertCheck.append(actions.assertEqualBoolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is NOT enabled"));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            UtilsMethods.getNewAddHeader();
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionFA = false;
                assertCheck.append(actions.assertEqualBoolean(continueExecutionFA, true, "User Login Successful Over Portal", "User Login Failed Over Portal"));
            } else {
                final Boolean userManagementPageLoaded = pages.getUserManagementPage().isUserManagementPageLoaded();
                assertCheck.append(actions.assertEqualBoolean(userManagementPageLoaded, true, "Customer Dashboard Page Loaded Successfully", "Customer Dashboard page NOT Loaded"));
                if (!userManagementPageLoaded) {
                    continueExecutionFA = false;
                    continueExecutionBU = false;
                }
            }
        } catch (Exception e) {
            continueExecutionFA = false;
            commonLib.fail("Exception in Method - testLoginIntoPortal" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}