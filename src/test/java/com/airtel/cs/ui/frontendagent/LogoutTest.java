package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LogoutTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA | !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test
    public void logout() {
        ExtentTestManager.startTest("Logging Out Of Portal", "Logging Out Of Portal");
        if (pages.getSideMenu().isSideMenuVisible()) {
            pages.getSideMenu().clickOnSideMenu();
            pages.getSideMenu().logout();
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
