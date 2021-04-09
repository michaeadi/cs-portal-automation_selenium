package com.airtel.cs.ui.templateManagement;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.airtel.cs.driver.Driver;

import java.util.ArrayList;
import java.util.List;

public class ViewTemplateTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Open Template Management")
    public void openTemplateManagement() {
        ExtentTestManager.startTest("Open Template Management", "Open Template Management");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openTemplateManagementPage();
        softAssert.assertTrue(pages.getTemplateManagement().isPageLoaded());
        pages.getTemplateManagement().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "View Created Template Page Loaded", dependsOnMethods = "openTemplateManagement")
    public void openViewCreatedTemplate() {
        ExtentTestManager.startTest("View Created Template Page Loaded", "View Created Template Page Loaded");
        SoftAssert softAssert = new SoftAssert();
        pages.getTemplateManagement().waitTillLoaderGetsRemoved();
        pages.getTemplateManagement().clickViewCreatedTemplateTab();
        softAssert.assertTrue(pages.getViewCreatedTemplate().isViewCreatedTemplate(), "View Created Template Page does not load properly.");
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validating All Agent Channel displayed", dependsOnMethods = "openTemplateManagement")
    public void validateAgentChannel() {
        ExtentTestManager.startTest("Validating All Agent Channel displayed", "Validating All Agent Channel displayed");
        SoftAssert softAssert = new SoftAssert();
        pages.getViewCreatedTemplate().clickAgentChannel();
        ArrayList<String> strings = pages.getViewCreatedTemplate().getAllOptions();
        pages.getViewCreatedTemplate().clickOutside();
        DataProviders data = new DataProviders();
        List<String> interactionChannel = data.getInteractionChannelData();
        for (String s : strings) {
            if (interactionChannel.contains(s)) {
                ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " Agent channel is display correctly");
                interactionChannel.remove(s);
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " Agent channel must not display on frontend as tag not mention in config sheet.");
                softAssert.fail(s + " Agent channel should not display on UI as Agent channel not mention in config sheet.");
            }
        }
        if (interactionChannel.isEmpty()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "All Agent channel correctly configured and display on UI.");
        } else {
            for (String element : interactionChannel) {
                ExtentTestManager.getTest().log(LogStatus.FAIL, element + " Agent channel does not display on UI but present in config sheet.");
                softAssert.fail(element + " Agent channel does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validating All Agent Roles displayed", dependsOnMethods = "openTemplateManagement")
    public void validateRoles() {
        ExtentTestManager.startTest("Validating All Agent Roles displayed", "Validating All Agent Roles displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getViewCreatedTemplate().clickRoles();
            ArrayList<String> strings = pages.getViewCreatedTemplate().getAllOptions();
            pages.getViewCreatedTemplate().clickOutside();
            DataProviders data = new DataProviders();
            List<String> agentRoles = data.getRoles();
            for (String s : strings) {
                if (agentRoles.contains(s)) {
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " Agent Roles is display correctly");
                    agentRoles.remove(s);
                }
            }
            if (agentRoles.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All Agent Roles correctly configured and display on UI.");
            } else {
                for (String element : agentRoles) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, element + " Agent Roles does not display on UI but present in config sheet.");
                    softAssert.fail(element + " Agent Roles does not display on UI but present in config sheet.");
                }
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Validate Agent Role does not complete due to error :" + e.fillInStackTrace());
        }
        pages.getViewCreatedTemplate().clickOutside();
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Validating All Language displayed", dependsOnMethods = "openTemplateManagement")
    public void validateLanguage() {
        ExtentTestManager.startTest("Validating All Language displayed", "Validating All Language displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getViewCreatedTemplate().clickLanguage();
            ArrayList<String> strings = pages.getViewCreatedTemplate().getAllOptions();
            pages.getViewCreatedTemplate().clickOutside();
            DataProviders data = new DataProviders();
            List<String> language = data.getLanguage();
            for (String s : strings) {
                if (language.contains(s)) {
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Validate " + s + " Language is display correctly");
                    language.remove(s);
                } else {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, s + " Language must not display on frontend as tag not mention in config sheet.");
                    softAssert.fail(s + " Language should not display on UI as Agent channel not mention in config sheet.");
                }
            }
            if (language.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All Language correctly configured and display on UI.");
            } else {
                for (String element : language) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, element + " Language does not display on UI but present in config sheet.");
                    softAssert.fail(element + " Language does not display on UI but present in config sheet.");
                }
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            softAssert.fail("Validate Language does not able to complete due to following error: " + e.fillInStackTrace());
        }
        pages.getViewCreatedTemplate().clickOutside();
        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Validate template layout", dependsOnMethods = "openTemplateManagement")
    public void validateTemplateLayout() {
        ExtentTestManager.startTest("Validating template layout", "Validating All Language displayed");
        SoftAssert softAssert = new SoftAssert();
        int i = 1;
        pages.getViewCreatedTemplate().templateName(i);
        pages.getViewCreatedTemplate().templateCategory(i);
        softAssert.assertTrue(pages.getViewCreatedTemplate().isDeleteIcon(i), "Delete Icon Does not present");
        softAssert.assertTrue(pages.getViewCreatedTemplate().isCommentIcon(i), "Comment Icon does not present");
        softAssert.assertTrue(pages.getViewCreatedTemplate().isEditIcon(i), "Edit Icon does not present");
        pages.getViewCreatedTemplate().validateActiveStatus(i);
        pages.getViewCreatedTemplate().templateLanguage(i);
        pages.getViewCreatedTemplate().validateDeActiveStatus(i);
        softAssert.assertTrue(pages.getViewCreatedTemplate().checkPaginationDisplayed(), "Pagination does not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 7, description = "Validate admin able to deactivate/Activate Template", dependsOnMethods = "openTemplateManagement")
    public void deactivateTemplate() {
        ExtentTestManager.startTest("Validate admin able to deactivate/Activate Template", "Validate admin able to deactivate/Activate Template");
        SoftAssert softAssert = new SoftAssert();
        int i = 1;
        pages.getViewCreatedTemplate().clickDeleteIcon(i);
        pages.getViewCreatedTemplate().popUpTitleDeActive();
        pages.getViewCreatedTemplate().popUpMessage();
        softAssert.assertTrue(pages.getViewCreatedTemplate().isNoButtonAvailable(), "Admin does not have 'No' button available");
        softAssert.assertTrue(pages.getViewCreatedTemplate().isYesButtonAvailable(), "Admin does not have 'YES' button available");
        pages.getViewCreatedTemplate().clickNoBtn();
        softAssert.assertAll();
    }

}
