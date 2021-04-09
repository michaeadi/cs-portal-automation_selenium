package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.ActionTagDataBeans;
import com.airtel.cs.commonutils.dataproviders.AuthTabDataBeans;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.QuestionAnswerKeyDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.configuration.ConfigurationPOJO;
import com.airtel.cs.pojo.configuration.LockedSection;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class AuthTabTest extends Driver {

    APIEndPoints api = new APIEndPoints();
    Map<String, String> authTabConfig;

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions :" + data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openCustomerInteractionPage();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate that the answers of the questions")
    public void validateAnswerQuestionConfig() {
        ExtentTestManager.startTest("Verify that the answers of the questions in pop up should either show data from configuration", "CSP-63443 For every question there should be an answer key, that should be created and stored in DB policy_questions table, from UI/API path the answers should be checked.Then the same should be configured in configurations table like - answerkey:\"API_URL.xpath.\" (AM_NAME: \"cs-am-service/v1/profile.result.firstName\")");
        SoftAssert softAssert = new SoftAssert();
        ConfigurationPOJO config = api.getConfiguration("authorization_data");
        authTabConfig = config.getResult().getAuthDataConfig();
        for (Map.Entry mapElement : authTabConfig.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = mapElement.getValue().toString();
            UtilsMethods.printInfoLog(key + " :" + value);
            if (value == null || value.isEmpty()) {
                softAssert.fail("For Question Key '" + key + "' value is missing. Please configure the same.");
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Verify the question Answer as Per Config")
    public void validateAnswerKey() {
        ExtentTestManager.startTest("Verify the question Answer as Per Config", "validate the question Answer as Per Config");
        SoftAssert softAssert = new SoftAssert();
        DataProviders dataProviders = new DataProviders();
        List<QuestionAnswerKeyDataBeans> config = dataProviders.getQuestionAnswerKey();
        for (QuestionAnswerKeyDataBeans questionAnswer : config) {
            UtilsMethods.printInfoLog("Question Key: '" + questionAnswer.getQuestionKey() + "' ; Answer Found in API: '" + authTabConfig.get(questionAnswer.getQuestionKey()));
            if (authTabConfig.get(questionAnswer.getQuestionKey()) != null) {
                softAssert.assertEquals(authTabConfig.get(questionAnswer.getQuestionKey()), questionAnswer.getAnswerKey(), "Answer key is not expected for Question: " + questionAnswer.getQuestionKey());
            } else {
                softAssert.fail("Question Key does not found in Database but present in config sheet.");
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Verify that there is a authorization pop for the actions")
    public void validateLockedSectionStatus() {
        ExtentTestManager.startTest("Verify that there is a authorization pop for the actions", "CSP-63442 Verify that there is a authorization pop for the actions like SIM Bar Unbar, PIN reset");
        SoftAssert softAssert = new SoftAssert();
        DataProviders dataProviders = new DataProviders();
        ConfigurationPOJO config = api.getConfiguration("locked_sections_keys");
        List<LockedSection> lockedSection = config.getResult().getLockedSectionsKeysConfig();
        List<ActionTagDataBeans> actionTags = dataProviders.getActionTag();
        for (LockedSection ls : lockedSection) {
            UtilsMethods.printInfoLog(ls.getKey() + " : " + ls.getIsAuthenticated());
            for (ActionTagDataBeans at : actionTags) {
                if (ls.getKey().equalsIgnoreCase(at.getActionTagName())) {
                    if (ls.getIsAuthenticated() != Boolean.parseBoolean(at.getIsAuth())) {
                        softAssert.fail("Action does not locked but as per config Action must be locked.");
                        break;
                    } else if (ls.getIsAuthenticated() == Boolean.parseBoolean(at.getIsAuth())) {
                        UtilsMethods.printPassLog("Action Verified " + at.getActionTagName());
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Verify the Authentication tab", dependsOnMethods = "openCustomerInteraction")
    public void validateAuthTab() throws InterruptedException {
        ExtentTestManager.startTest("Verify the Authentication tab", "Verify the Authentication tab");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        if (pages.getDemoGraphicPage().isPUKInfoLock()) {
            pages.getDemoGraphicPage().clickPUKToUnlock();
        }
        Thread.sleep(15000);
        DataProviders data = new DataProviders();
        try {
            Assert.assertTrue(pages.getAuthTabPage().isAuthTabLoad(), "Authentication tab does not load correctly");
            Map<String, String> questionList = pages.getAuthTabPage().getQuestionAnswer();
            List<AuthTabDataBeans> list = data.getPolicy();
            List<String> questions = data.getPolicyQuestion();
            softAssert.assertEquals(pages.getAuthTabPage().getAuthInstruction().toLowerCase().trim(), list.get(0).getPolicyMessage().toLowerCase().trim(), "Policy Message not same as configured");
            for (String s : questions) {
                String trim = s.replaceAll("[^a-zA-Z]+", "").toLowerCase().trim();
                if (!questionList.containsKey(trim)) {
                    softAssert.fail(s + " :Question must configured on UI as present in config sheet");
                }
                questionList.remove(trim);
            }
            if (questionList.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All Questions correctly configured and display on UI.");
            } else {
                for (Map.Entry<String, String> mapElement : questionList.entrySet()) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " Question Display on UI but does not present in config sheet.");
                    softAssert.fail(mapElement.getKey() + " :Question Display on UI but does not present in config sheet.");
                }
            }
        } catch (NoSuchElementException | TimeoutException | AssertionError e) {
            softAssert.fail("Not able to validate auth Tab");
            try {
                pages.getAuthTabPage().closeSIMBarPopup();
            } catch (NoSuchElementException | TimeoutException f) {
                pages.getAuthTabPage().clickCloseBtn();
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Verify the Authentication tab Minimum question Configured correctly")
    public void validateAuthTabMinQuestion() throws InterruptedException {
        ExtentTestManager.startTest("Verify the Authentication tab Minimum question Configured correctly", "Verify the Authentication tab Minimum question Configured correctly");
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        pages.getAuthTabPage().waitTillLoaderGetsRemoved();
        Assert.assertTrue(pages.getAuthTabPage().isAuthTabLoad(), "Authentication tab does not load correctly");
        List<AuthTabDataBeans> list = data.getPolicy();
        softAssert.assertFalse(pages.getAuthTabPage().isAuthBtnEnable(), "Authenticate button is enable without choosing minimum number of question.");
        for (int i = 1; i <= Integer.parseInt(list.get(0).getMinAnswer()); i++) {
            pages.getAuthTabPage().clickCheckBox(i);
            if (i < Integer.parseInt(list.get(0).getMinAnswer())) {
                softAssert.assertFalse(pages.getAuthTabPage().isAuthBtnEnable(), "Authenticate button is enable without choosing minimum number of question.");
            }
        }
        softAssert.assertTrue(pages.getAuthTabPage().isAuthBtnEnable(), "Authenticate Button does not enable after choose minimum number of question");
        pages.getAuthTabPage().clickCloseBtn();
        softAssert.assertAll();
    }

    @Test(priority = 7, description = "Authenticate User", dependsOnMethods = "validateAuthTabMinQuestion", enabled = false)
    public void authCustomer() {
        ExtentTestManager.startTest("Authenticate User", "Authenticate User");
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        pages.getAuthTabPage().waitTillLoaderGetsRemoved();
        if (pages.getAuthTabPage().isAuthTabLoad()) {
            pages.getAuthTabPage().clickAuthBtn();
            pages.getAuthTabPage().waitTillLoaderGetsRemoved();
        }
        try {
            Assert.assertTrue(pages.getAuthTabPage().isSIMBarPopup(), "SIM Bar/unbar popup does not open");
            softAssert.assertTrue(pages.getAuthTabPage().isIssueDetailTitle(), "Issue Detail does not configured");
            softAssert.assertFalse(pages.getAuthTabPage().isSubmitBtnEnable(), "Submit button enable without adding comment");
            pages.getAuthTabPage().openSelectPopup();
            List<String> reason = pages.getAuthTabPage().getReasonConfig();
            List<String> configReason = data.issueDetailReason("SIM Bar Unbar");
            for (String s : reason) {
                if (!configReason.contains(s)) {
                    softAssert.fail(s + ": Must not configured on UI as not mentioned in config.");
                }
                configReason.remove(s);
            }

            for (String s : configReason) {
                softAssert.fail(s + ": Must configured on UI as mentioned in config.");
            }
            pages.getAuthTabPage().chooseReason();
            pages.getAuthTabPage().writeComment("Adding comment using Automation");
            softAssert.assertTrue(pages.getAuthTabPage().isSubmitBtnEnable(), "Submit button does not enabled after adding comment");
            pages.getAuthTabPage().closeSIMBarPopup();
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to check SIM/Bar Issue detail option: " + e.fillInStackTrace());
            pages.getAuthTabPage().closeSIMBarPopup();
        }
        softAssert.assertAll();
    }

    @Test(priority = 8, description = "Verify the Send Internet Setting tab", dependsOnMethods = "openCustomerInteraction")
    public void validateSendInternetSetting() {
        ExtentTestManager.startTest("Verify the Send Internet Setting tab", "Verify the Send Internet Setting tab");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        try {
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendSetting();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            try {
                softAssert.assertTrue(pages.getCustomerProfilePage().isSendInternetSettingTitle(), "Send Internet Setting Tab Does not open after internet setting.");
                pages.getCustomerProfilePage().clickNoBtn();
                pages.getCustomerProfilePage().clickContinueButton();
            } catch (TimeoutException | NoSuchElementException e) {
                softAssert.fail("Not able to close send Internet Setting Tab." + e.fillInStackTrace());
                pages.getCustomerProfilePage().clickCloseBtn();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Send Internet Setting Option does not configure correctly." + e.fillInStackTrace());
            pages.getCustomerProfilePage().clickOutside();
        }
    }

    @Test(priority = 9, description = "Verify the Reset ME2U Password tab", dependsOnMethods = "openCustomerInteraction")
    public void validateResetME2UPassword() {
        ExtentTestManager.startTest("Verify the Reset ME2U Password tab", "Verify the Reset ME2U Password tab");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        try {
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickResetME2U();
            try {
                softAssert.assertTrue(pages.getCustomerProfilePage().isResetME2UPasswordTitle(), "Reset ME2U Password Tab Does not open.");
                pages.getCustomerProfilePage().clickNoBtn();
                pages.getCustomerProfilePage().clickContinueButton();
            } catch (TimeoutException | NoSuchElementException e) {
                softAssert.fail("Not able to Reset ME2U Password Tab." + e.fillInStackTrace());
                pages.getCustomerProfilePage().clickCloseBtn();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Reset ME2U Password Option does not configure correctly." + e.fillInStackTrace());
            pages.getCustomerProfilePage().clickOutside();
        }
    }
}
