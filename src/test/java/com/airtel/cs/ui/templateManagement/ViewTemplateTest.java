package com.airtel.cs.ui.templateManagement;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class ViewTemplateTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
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
            assertCheck.append(actions.assertEqual_boolean(pages.getTemplateManagement().isPageLoaded(), true, "Page loaded successfully", "Unable to load page"));
            pages.getTemplateManagement().waitTillLoaderGetsRemoved();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openTemplateManagement()" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to validate View Created Template Page Loaded
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void openViewCreatedTemplate() {
        try {
            selUtils.addTestcaseDescription("View Created Template Page Loaded", "description");
            pages.getTemplateManagement().waitTillLoaderGetsRemoved();
            pages.getTemplateManagement().clickViewCreatedTemplateTab();
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isViewCreatedTemplate(), true, "View Created Template Page load properly.", "View Created Template Page does not load properly."));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openTemplateManagement()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate all agent channel displayed
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void validateAgentChannel() {
        try {
            selUtils.addTestcaseDescription("Validating All Agent Channel displayed", "description");
            pages.getViewCreatedTemplate().clickAgentChannel();
            ArrayList<String> strings = pages.getViewCreatedTemplate().getAllOptions();
            pages.getViewCreatedTemplate().clickOutside();
            DataProviders data = new DataProviders();
            List<String> interactionChannel = data.getInteractionChannelData();
            for (String s : strings) {
                if (interactionChannel.contains(s)) {
                    commonLib.info("Validate " + s + " Agent channel is display correctly");
                    interactionChannel.remove(s);
                } else {
                    commonLib.fail(s + " Agent channel must not display on frontend as tag not mention in config sheet.", true);
                }
            }
            if (interactionChannel.isEmpty()) {
                commonLib.pass("All Agent channel correctly configured and display on UI.");
            } else {
                for (String element : interactionChannel) {
                    commonLib.fail(element + " Agent channel does not display on UI but present in config sheet.", true);
                }
            }
            // assertCheck is not used here for assertion, what to pass in assertAllFoundFailedAssert?
            //softAssert.assertAll();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAgentChannel()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate all agent roles displayed
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void validateRoles() {
        try {
            selUtils.addTestcaseDescription("Validating All Agent Roles displayed", "description");
            try {
                pages.getViewCreatedTemplate().clickRoles();
                ArrayList<String> strings = pages.getViewCreatedTemplate().getAllOptions();
                pages.getViewCreatedTemplate().clickOutside();
                DataProviders data = new DataProviders();
                List<String> agentRoles = data.getRoles();
                for (String s : strings) {
                    if (agentRoles.contains(s)) {
                        commonLib.info("Validate " + s + " Agent Roles is display correctly");
                        agentRoles.remove(s);
                    }
                }
                if (agentRoles.isEmpty()) {
                    commonLib.pass("All Agent Roles correctly configured and display on UI.");
                } else {
                    for (String element : agentRoles) {
                        commonLib.fail(element + " Agent Roles does not display on UI but present in config sheet.", true);
                    }
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            }
            pages.getViewCreatedTemplate().clickOutside();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateRoles()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate all language displayed
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void validateLanguage() {
        try {
            selUtils.addTestcaseDescription("Validating All Language displayed", "description");
            try {
                pages.getViewCreatedTemplate().clickLanguage();
                ArrayList<String> strings = pages.getViewCreatedTemplate().getAllOptions();
                pages.getViewCreatedTemplate().clickOutside();
                DataProviders data = new DataProviders();
                List<String> language = data.getLanguage();
                for (String s : strings) {
                    if (language.contains(s)) {
                        commonLib.info("Validate " + s + " Language is display correctly");
                        language.remove(s);
                    } else {
                        commonLib.fail(s + " Language must not display on frontend as tag not mention in config sheet.", true);
                    }
                }
                if (language.isEmpty()) {
                    commonLib.pass("All Language correctly configured and display on UI.");
                } else {
                    for (String element : language) {
                        commonLib.fail(element + " Language does not display on UI but present in config sheet.", true);
                    }
                }
            } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            }
            pages.getViewCreatedTemplate().clickOutside();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateLanguage()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate template layout
     */
    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void validateTemplateLayout() {
        try {
            selUtils.addTestcaseDescription("Validating template layout", "description");
            int i = 1;
            pages.getViewCreatedTemplate().templateName(i);
            pages.getViewCreatedTemplate().templateCategory(i);
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isDeleteIcon(i), true, "Delete Icon present", "Delete Icon Does not present"));
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isCommentIcon(i), true, "Comment Icon present", "Comment Icon does not present"));
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isEditIcon(i), true, "Edit Icon present", "Edit Icon does not present"));

            pages.getViewCreatedTemplate().validateActiveStatus(i);
            pages.getViewCreatedTemplate().templateLanguage(i);
            pages.getViewCreatedTemplate().validateDeActiveStatus(i);
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().checkPaginationDisplayed(), true, "Pagination displayed", "Pagination does not displayed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTemplateLayout()" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to Validate admin able to deactivate/Activate Template
     */
    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openTemplateManagement")
    public void deactivateTemplate() {
        try {
            selUtils.addTestcaseDescription("Validate admin able to deactivate/Activate Template", "description");
            int i = 1;
            pages.getViewCreatedTemplate().clickDeleteIcon(i);
            pages.getViewCreatedTemplate().popUpTitleDeActive();
            pages.getViewCreatedTemplate().popUpMessage();
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isNoButtonAvailable(), true, "Admin have 'No' button available", "Admin does not have 'No' button available"));
            assertCheck.append(actions.assertEqual_boolean(pages.getViewCreatedTemplate().isYesButtonAvailable(), true, "Admin have 'YES' button available", "Admin does not have 'YES' button available"));
            pages.getViewCreatedTemplate().clickNoBtn();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - deactivateTemplate()" + e.fillInStackTrace(), true);
        }
    }

}
