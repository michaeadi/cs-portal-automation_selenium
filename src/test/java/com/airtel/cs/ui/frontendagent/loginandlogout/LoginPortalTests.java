package com.airtel.cs.ui.frontendagent.loginandlogout;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

public class LoginPortalTests extends Driver {

    private final BaseActions actions = new BaseActions();

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
            assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), value, "Correct URL Opened", "URl isn't as expected"));
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.ALL_USER_ROLE_PASSWORD)));
            assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is NOT enabled"));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
           /* LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
            for (LogEntry entry : logEntries) {
                commonLib.info((String.format("%s %s %s\n", new Date(entry.getTimestamp()), entry.getLevel(),
                        entry.getMessage())));
            }
            String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return JSON.stringify(network)";
            commonLib.info(Network.responseReceived().toString());
            //Network.getResponseBody()
            String netData = ((JavascriptExecutor) driver).executeScript(scriptToExecute).toString();
            System.out.println
            (netData);
            if (netData.contains("auth/api/user-mngmnt/v2/login")) {
                //System.out.println(netData);
            }*/
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionFA = false;
                assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "User Login Successful Over Portal", "User Login Failed Over Portal"));
            } else {
                final Boolean userManagementPageLoaded = pages.getUserManagementPage().isUserManagementPageLoaded();
                assertCheck.append(actions.assertEqual_boolean(userManagementPageLoaded, true, "Customer Dashboard Page Loaded Successfully", "Customer Dashboard page NOT Loaded"));
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