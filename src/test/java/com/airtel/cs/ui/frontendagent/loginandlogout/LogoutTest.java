package com.airtel.cs.ui.frontendagent.loginandlogout;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test
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
        Assert.assertTrue(pages.getLoginPage().isEnterAUUIDFieldVisible());
        pages.getLoginPage().waitTillLoaderGetsRemoved();
    }
}
