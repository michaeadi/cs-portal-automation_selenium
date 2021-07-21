package com.airtel.cs.ui.templateManagement;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.TemplateManagement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateTemplateTest extends Driver {

    static String templateCategory;
    static String templateName;
    private final BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    /**
     * This method is used to open template management
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openTemplateManagement() {
        try {
            selUtils.addTestcaseDescription("Open Template Management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openTemplateManagementPage();
            assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isPageLoaded(), true, "Template Management Module load", "Template Management Module does not load"));
            pages.getTemplateManagement();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openTemplateManagement" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate template management page
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void validateAddTemplateManagementPage() {
        try {
            selUtils.addTestcaseDescription("Validate Template Management Page", "description");
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isAddTemplateAvailable(), true, "Add Template button is available.", "Add Template button does not available."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Add Template button does not available." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isAddTemplateCategoryAvailable(), true, "Add template Category button is available.", "Add template Category button does not available."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Add template Category button does not available." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isCategoryAvailable(), true, "Category Label displayed on Template Management Page.", "Category Label does not display on Template Management Page."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Category Label does not display on Template Management Page." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isMessageChannelAvailable(), true, "Message Channel Label is displayed on Template Management Page.", "Message Channel Label does not display on Template Management Page."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Channel Label does not display on Template Management Page." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isTemplateNameAvailable(), true, "Template Name Label displayed on Template Management Page.", "Template Name Label does not display on Template Management Page."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Template Name Label does not display on Template Management Page" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isRoleAvailable(), true, "Role Label is displayed on Template Management Page.", "Role Label does not display on Template Management Page."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Role Label does not display on Template Management Page." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isAgentChannelAvailable(), true, "Agent channel Label is displayed on Template Management Page.", "Agent channel Label does not display on Template Management Page."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Agent channel Label does not display on Template Management Page." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isSMSLanguageAvailable(), true, "SMS Language Label displayed on Template Management Page.", "SMS Language Label does not display on Template Management Page."));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("SMS Language Label does not display on Template Management Page." + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAddTemplateManagementPage" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to add template category
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "openTemplateManagement")
    public void addTemplateCategory() {
        try {
            DateTimeFormatter formatting = DateTimeFormatter.ofPattern("hhmmddMMM");
            templateCategory = "Category" + LocalDateTime.now().format(formatting);
            selUtils.addTestcaseDescription("Create Template Category with name :" + templateCategory, "description");
            TemplateManagement templateManagement = new TemplateManagement(driver);
            templateManagement.switchTabToAddTemplateCategory();
            templateManagement.writeTemplateCategoryName(templateCategory);
            templateManagement.clickAddCategoryBtn();
            try {
                templateManagement.readResponseMessage();
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.info("Not able to read Message Pop up: " + e.fillInStackTrace());
            }
            assertCheck.append(actions.assertEqual_boolean(templateManagement.validateAddedCategoryDisplay(templateCategory), true, "Added Category is displayed in list", "Added Category does not display in list"));
            templateManagement.waitTillOverlayGetsRemoved();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - addTemplateCategory" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to create template
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"addTemplateCategory", "openTemplateManagement"})
    public void createTemplate() {
        try {
            DateTimeFormatter formating = DateTimeFormatter.ofPattern("MMMM hh0mm");
            templateName = "Template " + LocalDateTime.now().format(formating);
            selUtils.addTestcaseDescription("Create Template with name: " + templateName, "description");
            pages.getTemplateManagement().switchTabToAddTemplate();
            try {
                pages.getTemplateManagement().clickTemplateCategory();
                try {
                    pages.getTemplateManagement().selectOptionFromList(templateCategory);
                } catch (StaleElementReferenceException e) {
                    commonLib.info("Trying Again to select Agent Channels");
                    pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
                }
                pages.getTemplateManagement().writeTemplateName(templateName);
                pages.getTemplateManagement().clickAgentRole();
                try {
                    pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
                } catch (StaleElementReferenceException e) {
                    commonLib.info("Trying Again to select Agent Channels");
                    pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
                }
                pages.getTemplateManagement().clickOutside();
                pages.getTemplateManagement().clickAgentChannels();
                try {
                    pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
                } catch (StaleElementReferenceException e) {
                    commonLib.info("Trying Again to select Agent Channels");
                    pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
                }
                pages.getTemplateManagement().clickOutside();
                pages.getTemplateManagement().clickSMSLanguage();
                pages.getTemplateManagement().selectOptionFromList("English");
                pages.getTemplateManagement().clickOutside();
                pages.getTemplateManagement().writeSMSContent("Dear Customer Thank you for choosing Airtel.");
                assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().clickCreateTemplateBtn(), true, "Create Template button is enabled", "Create Template button does not enabled"));
                pages.getTemplateManagement().waitTillLoaderGetsRemoved();
                try {
                    pages.getTemplateManagement().readResponseMessage();
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.info("Not able to read Message Pop up: " + e.fillInStackTrace());
                }
                pages.getTemplateManagement().waitTillOverlayGetsRemoved();
            } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
                commonLib.fail("Create Template does not complete due to error :" + e.fillInStackTrace(), true);
            }
            pages.getTemplateManagement().clickOutside();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - addTemplateCategory" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate 'View Created Template' recent added template displaying with name
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"createTemplate", "openTemplateManagement"})
    public void validateAddedTemplate() {
        try {
            selUtils.addTestcaseDescription("Validate 'View Created Template' recent added template displaying with name: " + templateName, "description");
            pages.getTemplateManagement().clickViewCreatedTemplateTab();
            pages.getViewCreatedTemplate().writeSearchKeyword(templateName);
            pages.getViewCreatedTemplate().clickSearchIcon();
            pages.getViewCreatedTemplate().waitTillTimeLineGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isTemplatePresent(templateName), true, "Recent Created template with name '" + templateName + "' found in list.", "Recent Created template with name '" + templateName + "' not found in list."));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - addTemplateCategory" + e.fillInStackTrace(), true);
        }
    }
}
