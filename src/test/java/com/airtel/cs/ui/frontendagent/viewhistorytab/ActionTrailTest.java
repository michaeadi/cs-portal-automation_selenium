package com.airtel.cs.ui.frontendagent.viewhistorytab;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ActionTrailTest extends Driver {

    String comments = "Adding comment using Automation";
    private final BaseActions actions = new BaseActions();


    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "Action Trail Tab")
    @Test(priority = 2, description = "Validating Action Trail Tab", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void validateActionTrailOpenCorrectly(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible and then click on it,Validate column header name are visible and correct", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnActionTrailHistory();
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(0).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Action Type Column displayed in header", "Action Type Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(1).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Date & Time Column displayed in header", "Date & Time Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(2).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Reason Column displayed in header", "Reason Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(3).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Agent Id Column displayed in header", "Agent Id Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(4).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Agent name Column displayed in header.", "Agent name Column does not display in header"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getHeaderValue(5).toLowerCase().trim(), data.getRow6().toLowerCase().trim(), "Comments Column displayed in header", "Comments Column does not display in header"));
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Exception in Method - validateActionTrailOpenCorrectly" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, description = "Validating Action Trail History", dependsOnMethods = {"validateActionTrailOpenCorrectly"})
    public void validateActionTrailValue() {
        try {
            selUtils.addTestcaseDescription("Verify View History tab opened successfully,Verify Action Trail History tab is visible,Validate column's value are visible and correct", "description");
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(1, 1).toLowerCase().trim(), "send internet settings", "Action Type Column value displayed Correctly", "Action Type Column Value does not displayed Correctly"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getActionTrailPage().getValue(1, 2).toLowerCase().trim(), "Date & Time Column displayed Correctly", "Date & Time Column does not displayed Correctly"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(1, 3).toLowerCase().trim(), reason.toLowerCase().trim(), "Reason Column displayed Correctly", "Reason Column does not displayed Correctly"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(1, 4), loginAUUID, "Agent Id Column displayed Correctly", "Agent Id Column does not displayed Correctly"));
            assertCheck.append(actions.assertEqual_stringNotNull(pages.getActionTrailPage().getValue(1, 5).toLowerCase().trim(), "Agent name Column displayed Correctly", "Agent name Column does not displayed in Correctly"));
            assertCheck.append(actions.assertEqual_stringType(pages.getActionTrailPage().getValue(1, 6).toLowerCase().trim(), comments.toLowerCase().trim(), "Comments Column displayed Correctly", "Comments Column does not displayed in Correctly"));
        } catch (NoSuchElementException | TimeoutException | IndexOutOfBoundsException e) {
            commonLib.fail("Exception in Method - validateActionTrailValue" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
