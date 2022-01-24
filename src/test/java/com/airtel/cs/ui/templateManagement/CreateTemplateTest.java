package com.airtel.cs.ui.templateManagement;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.TemplateManagement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateTemplateTest extends Driver {

    static String templateCategory;
    static String templateName;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    /**
     * This method is used to open template management
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void openTemplateManagement() {
        try {
            selUtils.addTestcaseDescription("Open Template Management", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openTemplateManagementPage();
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isPageLoaded(), true, "Template Management Module load", "Template Management Module does not load"));
            pages.getTemplateManagement();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openTemplateManagement" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate template management page
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}, dependsOnMethods = "openTemplateManagement")
    public void validateAddTemplateManagementPage() {
        try {
            selUtils.addTestcaseDescription("Validate Template Management Page", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isAddTemplateAvailable(), true, "Add Template button is available.", "Add Template button does not available."));
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isAddTemplateCategoryAvailable(), true, "Add template Category button is available.", "Add template Category button does not available."));
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isCategoryAvailable(), true, "Category Label displayed on Template Management Page.", "Category Label does not display on Template Management Page."));
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isMessageChannelAvailable(), true, "Message Channel Label is displayed on Template Management Page.", "Message Channel Label does not display on Template Management Page."));
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isTemplateNameAvailable(), true, "Template Name Label displayed on Template Management Page.", "Template Name Label does not display on Template Management Page."));
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isRoleAvailable(), true, "Role Label is displayed on Template Management Page.", "Role Label does not display on Template Management Page."));
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isAgentChannelAvailable(), true, "Agent channel Label is displayed on Template Management Page.", "Agent channel Label does not display on Template Management Page."));
            Integer number =pages.getTemplateManagement().checkNoOfChannels();
            if(number>1) {
                pages.getTemplateManagement().clickSMSCheckBox();
                assertCheck.append((actions.assertEqualBoolean(pages.getTemplateManagement().isSMSLanguageAvailable(),true,"SMS Language Label displayed on Template Management Page", "SMS Language Label isn't displayed  on Template Management Page")));
                pages.getTemplateManagement().clickEmailCheckBox();
                assertCheck.append((actions.assertEqualBoolean(pages.getTemplateManagement().isEmailLanguageVisible(),true,"Email Language Label displayed on Template Management Page", "Email Language Label isn't displayed  on Template Management Page")));
            }
            else
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().isSMSLanguageAvailable(), true, "SMS Language Label displayed on Template Management Page.", "SMS Language Label does not display on Template Management Page."));
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
            assertCheck.append(actions.assertEqualBoolean(templateManagement.validateAddedCategoryDisplay(templateCategory), true, "Added Category is displayed in list", "Added Category does not display in list"));
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
            DateTimeFormatter formating = DateTimeFormatter.ofPattern("mmmm hh0mm");
            templateName = "Template " + LocalDateTime.now().format(formating);
            selUtils.addTestcaseDescription("Create Template with name: " + templateName, "description");
            pages.getTemplateManagement().switchTabToAddTemplate();
            pages.getTemplateManagement().clickTemplateCategory();
            pages.getTemplateManagement().selectOptionFromList(templateCategory);
            pages.getTemplateManagement().writeTemplateName(templateName);
            pages.getTemplateManagement().clickAgentRole();
            pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
            pages.getTemplateManagement().clickOutside();
            pages.getTemplateManagement().clickAgentChannels();
            pages.getTemplateManagement().selectOptionFromList(constants.getValue(CommonConstants.SELECT_ALL_OPTION_NAME));
            pages.getTemplateManagement().clickOutside();
            pages.getTemplateManagement().clickSMSLanguage();
            pages.getTemplateManagement().selectOptionFromList("English");
            pages.getTemplateManagement().clickOutside();
            pages.getTemplateManagement().writeSMSContent("Dear Customer Thank you for choosing Airtel.");
            assertCheck.append(actions.assertEqualBoolean(pages.getTemplateManagement().clickCreateTemplateBtn(), true, "Create Template button is enabled", "Create Template button does not enabled"));
            try {
                pages.getTemplateManagement().readResponseMessage();
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.info("Not able to read Message Pop up: " + e.fillInStackTrace());
            }
            pages.getTemplateManagement().waitTillOverlayGetsRemoved();
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
            assertCheck.append(actions.assertEqualBoolean(pages.getViewCreatedTemplate().isTemplatePresent(templateName), true, "Recent Created template with name '" + templateName + "' found in list.", "Recent Created template with name '" + templateName + "' not found in list."));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - addTemplateCategory" + e.fillInStackTrace(), true);
        }
    }
}
