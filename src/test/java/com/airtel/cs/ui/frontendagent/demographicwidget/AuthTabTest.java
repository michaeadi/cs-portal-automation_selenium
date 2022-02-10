package com.airtel.cs.ui.frontendagent.demographicwidget;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.ActionTagDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.QuestionAnswerKeyDataBeans;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.authconfiguration.AuthDataConfigResult;
import com.airtel.cs.model.response.authconfiguration.Configuration;
import com.airtel.cs.model.response.authconfiguration.LockedSection;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class AuthTabTest extends Driver {

    RequestSource api = new RequestSource();
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
    public void validateAnswerQuestionConfig() {
        try {
            selUtils.addTestcaseDescription("Jira id - CSP-63443,Verify that the answers of the questions in pop up should either show data from configuration or show inline spinner", "description");
            Configuration config = api.getConfiguration("authorization_data",lineType);
            authTabConfig = config.getResult().getAuthDataConfig();
            final String statusCode = config.getStatusCode();
            assertCheck.append(actions.assertEqualStringType(statusCode, "200", "Config API Status Code is as Expected and is :" + statusCode, "Config API Status Code is NOT as Expected and is :" + statusCode));
            pages.getAuthTabPage().isAuthQuestionAsPerConfig(authTabConfig);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method :- validateAnswerQuestionConfig" + e.fillInStackTrace(), false);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateAnswerKey() {
        try {
            selUtils.addTestcaseDescription("Verify the question Answer as Per Config", "description");
            DataProviders dataProviders = new DataProviders();
            List<QuestionAnswerKeyDataBeans> config = dataProviders.getQuestionAnswerKey();
            pages.getAuthTabPage().isAuthQuestionAnswerKeyAsPerConfig(config,authTabConfig);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method :- validateAnswerKey" + e.fillInStackTrace(), false);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateLockedSectionStatus() {
        try {
            selUtils.addTestcaseDescription("Jira id - CSP-63442,Verify that there is a authorization pop for the actions like SIM Bar Unbar, PIN reset", "description");
            DataProviders dataProviders = new DataProviders();
            Configuration config = api.getConfiguration("locked_sections_keys",lineType);
            List<LockedSection> lockedSection = config.getResult().getLockedSectionsKeysConfig();
            final String statusCode = config.getStatusCode();
            assertCheck.append(actions.assertEqualStringType(statusCode, "200", "Config API Status Code is as Expected and is :" + statusCode, "Config API Status Code is NOT as Expected and is :" + statusCode));
            List<ActionTagDataBeans> actionTags = dataProviders.getActionTag();
            pages.getAuthTabPage().isLockedSectionCorrectlyDisplay(lockedSection,actionTags);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method :- validateLockedSectionStatus" + e.fillInStackTrace(), false);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateAuthTab() {
        boolean isTabOpened = false;
        try {
            selUtils.addTestcaseDescription("Verify the Authentication tab", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().openAuthTab();
            isTabOpened = true;
            DataProviders data = new DataProviders();
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
            Map<String, String> questionList = pages.getAuthTabPage().getQuestionAnswer();
            List<AuthTabDataBeans> list = data.getPolicy();
            List<String> questions = data.getPolicyQuestion();
            assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getAuthInstruction().toLowerCase().trim(), list.get(0).getPolicyMessage().toLowerCase().trim(), "Policy Message same as configured", "Policy Message not same as configured"));
            pages.getAuthTabPage().validateAuthQuestion(questionList,questions);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | AssertionError | NullPointerException e) {
            if (isTabOpened)
                pages.getAuthTabPage().clickCloseBtn();
            commonLib.fail("Exception in Method :- validateAuthTab" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "validateAuthTab"})
    public void validateAuthTabMinQuestion() {
        try {
            selUtils.addTestcaseDescription("Verify the Authentication tab Minimum question Configured correctly", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthTabLoad(), true, "Authentication tab loaded correctly", "Authentication tab does not load correctly"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthBtnEnable(), false, "Authenticate button in NOT enabled without choosing minimum number of question", "Authenticate button is enable without choosing minimum number of question."));
            pages.getDemoGraphicPage().selectPolicyQuestion();
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isAuthBtnEnable(), true, "Authenticate button is enabled", "Authenticate Button does not enable after choose minimum number of question"));
            pages.getAuthTabPage().clickAuthBtn();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method :- validateAuthTabMinQuestion" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "validateAuthTabMinQuestion"})
    public void authCustomer() {
        try {
            selUtils.addTestcaseDescription("Authenticate User", "description");
            DataProviders data = new DataProviders();
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isSIMBarPopup(), true, "SIM Bar/Unbar pop up opened", "SIM Bar/Unbar popup does not open"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isIssueDetailTitleVisible(), true, "Issue details configured correctly", "Issue Detail does not configured"));
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isSubmitBtnEnable(), false, "Submit button Not enabled without comment", "Submit button enable without adding comment"));
            pages.getAuthTabPage().fillAllInputField("Automation Testing");
            pages.getAuthTabPage().clickSelectReasonDropDown();
            List<String> reason = pages.getAuthTabPage().getReasonConfig();
            List<String> configReason = data.issueDetailReason("SIM Bar Unbar");
            for (String s : reason) {
                    assertCheck.append(actions.assertEqualBoolean(configReason.remove(s),true,s + ": Must not configured on UI as not mentioned in config.", s + ": Must configured on UI as mentioned in config."));
            }
            pages.getAuthTabPage().chooseReason();
            pages.getAuthTabPage().enterComment("Adding comment using Automation");
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isSubmitBtnEnable(), true, "Submit button does enabled after adding comment", "Submit button does NOT enabled after adding comment"));
            pages.getAuthTabPage().closeSIMBarPopup();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.fail("Exception in Method :- authCustomer" + e.fillInStackTrace(), true);
            pages.getAuthTabPage().closeSIMBarPopup();
        }
    }
}
