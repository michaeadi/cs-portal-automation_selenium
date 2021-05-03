package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ActionTrailTest extends Driver {

    String reason = null;
    String comments = "Adding comment using Automation";
    String agentAuuid = null;
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

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions :" + data.getCustomerNumber(), "description");
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isCustomerProfilePageLoaded(), true, "Customer Profile Page Loaded", "Customer Profile Page NOT Loaded"));
        agentAuuid = data.getLoginAUUID();
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "Action Trail Tab")
    @Test(priority = 3, description = "Validating Action Trail Tab", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateActionTrailOpenCorrectly(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validating the Action Trail Tab Under View History", "description");
        try {
            pages.getMsisdnSearchPage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Action Type Column displayed in header", "Action Type Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Date & Time Column displayed in header", "Date & Time Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Reason Column displayed in header", "Reason Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Agent Id Column displayed in header", "Agent Id Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Agent name Column displayed in header.", "Agent name Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(5).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Comments Column displayed in header", "Comments Column does not display in header"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Not able to validate Action Trail Tab: " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, description = "Verify the Send Internet Setting tab", dependsOnMethods = "openCustomerInteraction")
    public void validateSendInternetSetting() {
        selUtils.addTestcaseDescription("Verify the Send Internet Setting tab", "description");
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        try {
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendSetting();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isIssueDetailTitle(), true, "Issue Detail Configured", "Issue Detail does not configured"));
                pages.getAuthTabPage().openSelectPopup();
                reason = pages.getAuthTabPage().getReason();
                pages.getAuthTabPage().chooseReason();
                pages.getAuthTabPage().enterComment(comments);
                assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isSendInternetSettingTitle(), true, "Send Internet Setting Title Displayed Correctly", "Send Internet Setting Title NOT Displayed Correctly"));
                pages.getAuthTabPage().clickSubmitBtn();
                pages.getAuthTabPage().waitTillLoaderGetsRemoved();
                if (pages.getCustomerProfilePage().isSendInternetSettingTitle()) {
                    String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                            getScreenshotAs(OutputType.BASE64);
                    commonLib.fail("Test Failed", true);
                    commonLib.fail("Send Internet setting pop up does not close after submit button: ", true);
                    pages.getAuthTabPage().clickCloseBtn();
                }
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                commonLib.fail("Not able to close send Internet Setting Tab." + e.fillInStackTrace(), true);
                pages.getAuthTabPage().clickCloseBtn();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Send Internet Setting Option does not configure correctly." + e.fillInStackTrace(), true);
            pages.getCustomerProfilePage().clickOutside();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, description = "Validating Action Trail History", dependsOnMethods = "validateSendInternetSetting")
    public void validateActionTrailValue() {
        selUtils.addTestcaseDescription("Validating Action Trail History", "description");
        try {
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(0).toLowerCase().trim(), "send internet settings", "Action Type Column value displayed Correctly", "Action Type Column Value does not display Correctly"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getActionTrailPage().getValue(1).toLowerCase().trim(), "Date & Time Column displayed Correctly", "Date & Time Column does not display Correctly"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(2).toLowerCase().trim(), reason.toLowerCase().trim(), "Reason Column displayed Correctly", "Reason Column does not display Correctly"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(3), agentAuuid, "Agent Id Column displayed Correctly", "Agent Id Column does not display Correctly"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getActionTrailPage().getValue(4).toLowerCase().trim(), "Agent name Column displayed Correctly", "Agent name Column does not display in Correctly"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(5).toLowerCase().trim(), comments.toLowerCase().trim(), "Comments Column displayed Correctly", "Comments Column does not display in Correctly"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Not able to validate Action Trail Tab: " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
