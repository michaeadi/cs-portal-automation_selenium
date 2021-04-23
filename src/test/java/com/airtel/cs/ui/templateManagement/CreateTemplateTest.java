package com.airtel.cs.ui.templateManagement;

import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.TemplateManagementPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateTemplateTest extends Driver {


    static String templateCategory;
    static String templateName;

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
        selUtils.addTestcaseDescription("Open Template Management", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openTemplateManagementPage();
        softAssert.assertTrue(pages.getTemplateManagement().isPageLoaded(), "Template Management Module does not load");
        pages.getTemplateManagement().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Template Management Page", dependsOnMethods = "openTemplateManagement")
    public void validateAddTemplateManagementPage() {
        selUtils.addTestcaseDescription("Validate Template Management Page", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isAddTemplateAvailable(), "Add Template button does not available.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Add Template button does not available." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isAddTemplateCategoryAvailable(), "Add template Category button does not available.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Add template Category button does not available." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isCategoryAvailable(), "Category Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Category Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isMessageChannelAvailable(), "Message Channel Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Channel Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isTemplateNameAvailable(), "Template Name Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Template Name Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isRoleAvailable(), "Role Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Role Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isAgentChannelAvailable(), "Agent channel Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Agent channel Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(pages.getTemplateManagement().isSMSLanguageAvailable(), "SMS Language Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("SMS Language Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Create Template Category")
    public void addTemplateCategory() {
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("hhmmddMMM");
        templateCategory = "Category" + LocalDateTime.now().format(formatting);
        selUtils.addTestcaseDescription("Create Template Category with name :" + templateCategory, "description");
        SoftAssert softAssert = new SoftAssert();
        TemplateManagementPage templateManagement = new TemplateManagementPage(driver);
        templateManagement.switchTabToAddTemplateCategory();
        templateManagement.writeTemplateCategoryName(templateCategory);
        templateManagement.clickAddCategoryBtn();
        templateManagement.waitTillLoaderGetsRemoved();
        try {
            templateManagement.readResponseMessage();
        } catch (NoSuchElementException | TimeoutException e) {
            commonLib.info("Not able to read Message Pop up: " + e.fillInStackTrace());
        }
        softAssert.assertTrue(templateManagement.validateAddedCategoryDisplay(templateCategory), "Added Category does not display in list");
        templateManagement.waitTillOverlayGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Create Template", dependsOnMethods = "addTemplateCategory")
    public void createTemplate() {
        DateTimeFormatter formating = DateTimeFormatter.ofPattern("MMMM hh0mm");
        templateName = "Template " + LocalDateTime.now().format(formating);
        selUtils.addTestcaseDescription("Create Template with name: " + templateName, "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getTemplateManagement().switchTabToAddTemplate();
        try {
            pages.getTemplateManagement().clickTemplateCategory();
            try {
                pages.getTemplateManagement().selectOptionFromList(templateCategory);
            } catch (StaleElementReferenceException e) {
                commonLib.info("Trying Again to select Agent Channels");
                pages.getTemplateManagement().selectOptionFromList(config.getProperty("selectALL"));
            }
            pages.getTemplateManagement().writeTemplateName(templateName);
            pages.getTemplateManagement().clickAgentRole();
            try {
                pages.getTemplateManagement().selectOptionFromList(config.getProperty("selectALL"));
            } catch (StaleElementReferenceException e) {
                commonLib.info("Trying Again to select Agent Channels");
                pages.getTemplateManagement().selectOptionFromList(config.getProperty("selectALL"));
            }
            pages.getTemplateManagement().clickOutside();
            pages.getTemplateManagement().clickAgentChannels();
            try {
                pages.getTemplateManagement().selectOptionFromList(config.getProperty("selectALL"));
            } catch (StaleElementReferenceException e) {
                commonLib.info("Trying Again to select Agent Channels");
                pages.getTemplateManagement().selectOptionFromList(config.getProperty("selectALL"));
            }
            pages.getTemplateManagement().clickOutside();
            pages.getTemplateManagement().clickSMSLanguage();
            pages.getTemplateManagement().selectOptionFromList("English");
            pages.getTemplateManagement().clickOutside();
            pages.getTemplateManagement().writeSMSContent("Dear Customer Thank you for choosing Airtel.");
            softAssert.assertTrue(pages.getTemplateManagement().clickCreateTemplateBtn(), "Create Template button does not enabled");
            pages.getTemplateManagement().waitTillLoaderGetsRemoved();
            try {
                pages.getTemplateManagement().readResponseMessage();
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.info("Not able to read Message Pop up: " + e.fillInStackTrace());
            }
            pages.getTemplateManagement().waitTillOverlayGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            softAssert.fail(" Create Template does not complete due to error :" + e.fillInStackTrace());
        }
        pages.getTemplateManagement().clickOutside();
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = "createTemplate", description = "Validate 'View Created Template' recent added template displaying")
    public void validateAddedTemplate() {
        selUtils.addTestcaseDescription("Validate 'View Created Template' recent added template displaying with name: " + templateName, "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getTemplateManagement().clickViewCreatedTemplateTab();
        pages.getViewCreatedTemplate().writeSearchKeyword(templateName);
        pages.getViewCreatedTemplate().clickSearchIcon();
        pages.getViewCreatedTemplate().waitTillTimeLineGetsRemoved();
        softAssert.assertTrue(pages.getViewCreatedTemplate().isTemplatePresent(templateName), "Recent Created template with name '" + templateName + "' not found in list.");
        softAssert.assertAll();
    }
}
