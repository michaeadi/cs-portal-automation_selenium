package tests.templateManagement;

import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.TemplateManagementPOM;
import pages.ViewCreatedTemplatePOM;
import tests.frontendAgent.BaseTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateTemplateTest extends BaseTest {


    static String templateCategory;
    static String templateName;

    @BeforeMethod
    public void checkExecution(){
        SoftAssert softAssert=new SoftAssert();
        if(!continueExecutionFA){
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Open Template Management")
    public void openTemplateManagement() {
        ExtentTestManager.startTest("Open Template Management", "Open Template Management");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        TemplateManagementPOM templateManagement = SideMenuPOM.openTemplateManagementPage();
        softAssert.assertTrue(templateManagement.isPageLoaded(), "Template Management Module does not load");
        templateManagement.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validate Template Management Page", dependsOnMethods = "openTemplateManagement")
    public void ValidateAddTemplateManagementPage() {
        ExtentTestManager.startTest("Validate Template Management Page", "Validate Template Management Page");
        SoftAssert softAssert = new SoftAssert();
        TemplateManagementPOM templateManagement = new TemplateManagementPOM(driver);
        try {
            softAssert.assertTrue(templateManagement.isAddTemplateAvailable(), "Add Template button does not available.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Add Template button does not available." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isAddTemplateCategoryAvailable(), "Add template Category button does not available.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Add template Category button does not available." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isCategoryAvailable(), "Category Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Category Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isMessageChannelAvailable(), "Message Channel Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Message Channel Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isTemplateNameAvailable(), "Template Name Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Template Name Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isRoleAvailable(), "Role Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Role Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isAgentChannelAvailable(), "Agent channel Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Agent channel Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        try {
            softAssert.assertTrue(templateManagement.isSMSLanguageAvailable(), "SMS Language Label does not display on Template Management Page.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("SMS Language Label does not display on Template Management Page." + e.fillInStackTrace());
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Create Template Category")
    public void addTemplateCategory() {
        DateTimeFormatter formating = DateTimeFormatter.ofPattern("hhmmddMMM");
        templateCategory = "Category" + LocalDateTime.now().format(formating);
        ExtentTestManager.startTest("Create Template Category with name :" + templateCategory, "Create Template Category with name :" + templateCategory);
        SoftAssert softAssert = new SoftAssert();
        TemplateManagementPOM templateManagement = new TemplateManagementPOM(driver);
        templateManagement.switchTabToAddTemplateCategory();
        templateManagement.writeTemplateCategoryName(templateCategory);
        templateManagement.clickAddCategoryBtn();
        templateManagement.waitTillLoaderGetsRemoved();
        try {
            templateManagement.readResponseMessage();
        } catch (NoSuchElementException | TimeoutException e) {
            templateManagement.printInfoLog("Not able to read Message Pop up: " + e.fillInStackTrace());
        }
        softAssert.assertTrue(templateManagement.validateAddedCategoryDisplay(templateCategory), "Added Category does not display in list");
        templateManagement.waitTillOverlayGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Create Template", dependsOnMethods = "addTemplateCategory")
    public void createTemplate() {
        DateTimeFormatter formating = DateTimeFormatter.ofPattern("MMMM hh0mm");
        templateName = "Template " + LocalDateTime.now().format(formating);
        ExtentTestManager.startTest("Create Template with name: " + templateName, "Create Template with name: " + templateName);
        SoftAssert softAssert = new SoftAssert();
        TemplateManagementPOM templateManagement = new TemplateManagementPOM(driver);
        templateManagement.switchTabToAddTemplate();
        try {
            templateManagement.clickTemplateCategory();
            try {
                templateManagement.selectOptionFromList(templateCategory);
            } catch (StaleElementReferenceException e) {
                templateManagement.printInfoLog("Trying Again to select Agent Channels");
                templateManagement.selectOptionFromList(config.getProperty("selectALL"));
            }
            templateManagement.writeTemplateName(templateName);
            templateManagement.clickAgentRole();
            try {
                templateManagement.selectOptionFromList(config.getProperty("selectALL"));
            } catch (StaleElementReferenceException e) {
                templateManagement.printInfoLog("Trying Again to select Agent Channels");
                templateManagement.selectOptionFromList(config.getProperty("selectALL"));
            }
            templateManagement.clickOutside();
            templateManagement.clickAgentChannels();
            try {
                templateManagement.selectOptionFromList(config.getProperty("selectALL"));
            } catch (StaleElementReferenceException e) {
                templateManagement.printInfoLog("Trying Again to select Agent Channels");
                templateManagement.selectOptionFromList(config.getProperty("selectALL"));
            }
            templateManagement.clickOutside();
            templateManagement.clickSMSLanguage();
            templateManagement.selectOptionFromList("English");
            templateManagement.clickOutside();
            templateManagement.writeSMSContent("Dear Customer Thank you for choosing Airtel.");
            softAssert.assertTrue(templateManagement.clickCreateTemplateBtn(), "Create Template button does not enabled");
            templateManagement.waitTillLoaderGetsRemoved();
            try {
                templateManagement.readResponseMessage();
            } catch (NoSuchElementException | TimeoutException e) {
                templateManagement.printInfoLog("Not able to read Message Pop up: " + e.fillInStackTrace());
            }
            templateManagement.waitTillOverlayGetsRemoved();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            softAssert.fail(" Create Template does not complete due to error :" + e.fillInStackTrace());
        }
        templateManagement.clickOutside();
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = "createTemplate", description = "Validate 'View Created Template' recent added template displaying")
    public void validateAddedTemplate() {
        ExtentTestManager.startTest("Validate 'View Created Template' recent added template displaying with name: " + templateName, "Validate 'View Created Template' recent added template displaying with name: " + templateName);
        SoftAssert softAssert = new SoftAssert();
        TemplateManagementPOM templateManagement = new TemplateManagementPOM(driver);
        ViewCreatedTemplatePOM createdTemplatePOM = templateManagement.clickViewCreatedTemplateTab();
        createdTemplatePOM.WriteSearchKeyword(templateName);
        createdTemplatePOM.clickSearchIcon();
        createdTemplatePOM.waitTillTimeLineGetsRemoved();
        softAssert.assertTrue(createdTemplatePOM.isTemplatePresent(templateName), "Recent Created template with name '" + templateName + "' not found in list.");
        softAssert.assertAll();
    }

}
