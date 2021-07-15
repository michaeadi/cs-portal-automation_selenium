package com.airtel.cs.ui.frontendagent.loginandlogout;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends Driver {

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void logout() {
        selUtils.addTestcaseDescription("Logging Out Of Portal", "description");
        if (pages.getSideMenuPage().isSideMenuVisible()) {
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().logout();
            try {
                Assert.assertTrue(pages.getLoginPage().isEnterAUUIDFieldVisible());
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                pages.getLoginPage().selectByText("Continue");
            }
        }
        assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().isEnterAUUIDFieldVisible(), true, "User Logout Successfully", "User NOT Logout from the portal"));
        final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
        assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), value, "Correct URL Opened", "URl isn't as expected"));
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
