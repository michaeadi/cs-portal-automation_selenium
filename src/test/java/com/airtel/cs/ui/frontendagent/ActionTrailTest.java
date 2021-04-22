package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ActionTrailTest extends Driver {

    String reason = null;
    String comments = "Adding comment using Automation";
    String agentAuuid = null;

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions :" + data.getCustomerNumber(), "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        agentAuuid = data.getLoginAUUID();
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Action Trail Tab")
    @Test(priority = 3, description = "Validating Action Trail Tab", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateActionTrailOpenCorrectly(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating the Action Trail Tab Under View History", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getMsisdnSearchPage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            softAssert.assertEquals(pages.getActionTrailPage().getHeaderValue(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Action Type Column does not display in header.");
            softAssert.assertEquals(pages.getActionTrailPage().getHeaderValue(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Date & Time Column does not display in header.");
            softAssert.assertEquals(pages.getActionTrailPage().getHeaderValue(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Reason Column does not display in header.");
            softAssert.assertEquals(pages.getActionTrailPage().getHeaderValue(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Agent Id Column does not display in header.");
            softAssert.assertEquals(pages.getActionTrailPage().getHeaderValue(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Agent name Column does not display in header.");
            softAssert.assertEquals(pages.getActionTrailPage().getHeaderValue(5).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Comments Column does not display in header.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to validate Action Trail Tab: " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify the Send Internet Setting tab", dependsOnMethods = "openCustomerInteraction")
    public void validateSendInternetSetting() {
        selUtils.addTestcaseDescription("Verify the Send Internet Setting tab", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        try {
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendSetting();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(pages.getAuthTabPage().isIssueDetailTitle(), "Issue Detail does not configured");
                pages.getAuthTabPage().openSelectPopup();
                reason = pages.getAuthTabPage().getReason();
                pages.getAuthTabPage().chooseReason();
                pages.getAuthTabPage().enterComment(comments);
                softAssert.assertTrue(pages.getCustomerProfilePage().isSendInternetSettingTitle(), "Send Internet Setting Tab Does not open after internet setting.");
                pages.getAuthTabPage().clickSubmitBtn();
                pages.getAuthTabPage().waitTillLoaderGetsRemoved();
                if (pages.getCustomerProfilePage().isSendInternetSettingTitle()) {
                    String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                            getScreenshotAs(OutputType.BASE64);
                    commonLib.fail("Test Failed", true);
                    softAssert.fail("Send Internet setting pop up does not close after submit button: ");
                    pages.getAuthTabPage().clickCloseBtn();
                }
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                softAssert.fail("Not able to close send Internet Setting Tab." + e.fillInStackTrace());
                pages.getAuthTabPage().clickCloseBtn();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Send Internet Setting Option does not configure correctly." + e.fillInStackTrace());
            pages.getCustomerProfilePage().clickOutside();
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validating Action Trail History", dependsOnMethods = "validateSendInternetSetting")
    public void validateActionTrailValue() {
        selUtils.addTestcaseDescription("Validating Action Trail History", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            softAssert.assertEquals(pages.getActionTrailPage().getValue(0, 0).toLowerCase().trim(), "send internet settings", "Action Type Column Value does not display in Correctly.");
            softAssert.assertNotNull(pages.getActionTrailPage().getValue(0, 1).toLowerCase().trim(), "Date & Time Column does not display in Correctly.");
            softAssert.assertEquals(pages.getActionTrailPage().getValue(0, 2).toLowerCase().trim(), reason.toLowerCase().trim(), "Reason Column does not display in Correctly.");
            softAssert.assertEquals(pages.getActionTrailPage().getValue(0, 3), agentAuuid, "Agent Id Column does not display in Correctly.");
            softAssert.assertNotNull(pages.getActionTrailPage().getValue(0, 4).toLowerCase().trim(), "Agent name Column does not display in Correctly.");
            softAssert.assertEquals(pages.getActionTrailPage().getValue(0, 5).toLowerCase().trim(), comments.toLowerCase().trim(), "Comments Column does not display in Correctly.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to validate Action Trail Tab: " + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }
}
