package com.airtel.cs.ui.frontendagent.actiondropdown;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.actionconfig.ActionConfigResult;
import com.airtel.cs.model.cs.response.actiontrail.EventLogsResponse;
import com.airtel.cs.model.cs.response.actiontrail.EventResult;
import com.airtel.cs.model.cs.response.parentcategory.Category;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.SortedMap;

public class SendInternetSettingsTest extends Driver {

    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    Boolean popup = true;
    ActionConfigResult actionConfigResult;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void isSendInternetSettingsEnabled() {
        if (StringUtils.equalsIgnoreCase(constants.getValue(ApplicationConstants.SEND_INTERNET_SETTINGS), "false")) {
            commonLib.skip("Send Internet Settings Feature is NOT Enabled for this Opco=" + OPCO);
            throw new SkipException("Send Internet Settings Feature is NOT Enabled for this Opco=" + OPCO);
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateCancelBtn() {
        boolean modalOpened = false;
        try {
            selUtils.addTestcaseDescription("Open send internet setting modal from actions drop down, Click on cancel button, Modal should be closed", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendInternetSetting();
            modalOpened = true;
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSendInternetSettingTitleVisible(), true, "Send Internet Setting Tab opened", "Send Internet Setting Tab does NOT opened"));
            popup = !pages.getCustomerProfilePage().isSendInternetSettingConfirmMessageVisible();
            if (popup) {
                pages.getCustomerProfilePage().clickCancelBtn();
            } else {
                pages.getCustomerProfilePage().clickCloseBtn();
            }
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            if (modalOpened)
                pages.getCustomerProfilePage().clickCloseBtn();
            commonLib.fail("Exception in Method :- validateCancelBtn" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateSendInternetSetting() {
        try {
            selUtils.addTestcaseDescription("Open send internet setting modal from actions drop down,Validate issue detail title visible,Select reason and enter comment and click on submit button, Validate success message", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().clickSendInternetSetting();
            popup = !pages.getCustomerProfilePage().isSendInternetSettingConfirmMessageVisible();
            if (popup) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isIssueDetailTitleVisible(), true, "Issue Detail Configured", "Issue Detail does not configured"));
                pages.getAuthTabPage().performSendInternetSettings();
            } else {
                pages.getAuthTabPage().clickYesBtn();
            }
            assertCheck.append(actions.assertEqualBoolean(pages.getAuthTabPage().isSuccessPopUpVisible(), true, "Success Popup visible after sending internet settings", "Success Popup NOT visible after sending internet settings\""));
            String successText = "Internet Settings has been sent on Customer`s Device.";
            assertCheck.append(actions.assertEqualStringType(pages.getAuthTabPage().getSuccessText(), successText, "Success text is displayed as expected", "Success text is not displayed as expected"));
            pages.getAuthTabPage().clickCrossIcon();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSendInternetSetting" + e.fillInStackTrace(), true);
            pages.getAuthTabPage().clickCrossIcon();
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "validateSendInternetSetting")
    public void checkActionTrail() {
        try {
            selUtils.addTestcaseDescription("Check Action trail", "description");
            EventLogsResponse eventLogs = api.getEventHistory(customerNumber, "ACTION");
            int statusCode = eventLogs.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Event Logs API success and status code is :" + statusCode, "Event Logs API got failed and status code is :" + statusCode, false, true));
            EventResult eventResult = eventLogs.getResult().get(0);
            if (statusCode == 200) {
                //pages.getActionTrailPage().assertMetaInfoAfterActionPerformed(constants.getValue(CommonConstants.SEND_INTERNET_SETTING_ACTION_KEY), eventResult);
                assertCheck.append(actions.assertEqualStringNotNull(eventResult.getActionType(), "Action Type is same as expected", "Action Type is not same as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(eventResult.getComments(), ApplicationConstants.COMMENT, "Comment same as expected.", "Comment same as not expected."));
                assertCheck.append(actions.matchUiAndAPIResponse(eventResult.getAgentId(), constants.getValue(CommonConstants.BETA_USER_ROLE_AUUID), "Agent id same as expected", "Agent id same as not expected"));
            } else
                commonLib.fail("Not able to fetch action trail event log using API", true);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkActionTrail" + e.fillInStackTrace(), true);
            pages.getCustomerProfilePage().clickOutside();
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", }, dependsOnMethods = "checkActionTrail")
    public void checkCategoryCode() {
        try {
            selUtils.addTestcaseDescription("Validate category code", "description");
            actionConfigResult = api.getActionConfig("sendInternetSettings");
            if (StringUtils.isNotEmpty(actionConfigResult.getCategoryId())) {
                SortedMap<String, List<Category>> categoryMap = api.getParentCategory(Long.parseLong(actionConfigResult.getCategoryId()));
                String categoryCode = categoryMap.get(categoryMap.lastKey()).get(0).getCategoryName();
                commonLib.info("Category code is : " + categoryCode);
                pages.getCustomerProfilePage().goToViewHistory();
                pages.getViewHistory().clickOnInteractionsTab();
                String code = pages.getViewHistory().getLastCreatedIssueCode();
                assertCheck.append(actions.assertEqualStringType(code.trim(), categoryCode, constants.getValue("category.interaction.found"), constants.getValue("category.interaction.not.found")));
            } else
                commonLib.warning("Category code is not available");
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkCategoryCode" + e.fillInStackTrace(), true);
            pages.getCustomerProfilePage().clickOutside();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
