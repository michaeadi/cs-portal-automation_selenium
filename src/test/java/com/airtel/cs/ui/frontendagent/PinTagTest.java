package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PinnedTagsDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PinTagTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }


    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
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


    @Test(priority = 2, description = "Validating Pinned Tags", dependsOnMethods = "openCustomerInteraction")
    public void checkALLPinnedTag() {
        selUtils.addTestcaseDescription("Validating Pinned Tag", "description");
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        Map<String, Boolean> tags = data.getALLPinnedTags();
        List<String> availableTags = pages.getCustomerProfilePage().getPinnedTagTexts();
        try {
            for (String s : availableTags) {
                if (tags.containsKey(s)) {
                    commonLib.info("Validate " + s + " pinned tag is display correctly");
                    tags.remove(s);
                } else {
                    commonLib.fail(s + " tag must not display on frontend as tag not mention in config sheet.", true);
                    softAssert.fail(s + " tag should not display on UI as tagged not mention in config sheet.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tags.isEmpty()) {
            commonLib.pass("All pinned tagged correctly configured and display on UI.");
        } else {
            for (Map.Entry mapElement : tags.entrySet()) {
                String key = (String) mapElement.getKey();
                commonLib.fail(key + " tag does not display on UI but present in config sheet.", true);
                softAssert.fail(key + " tag does not display on UI but present in config sheet.");
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "SideMenu ", dataProvider = "pinTag", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void checkIssueCodeForPinTag(PinnedTagsDataBeans data) {
        final String tagName = data.getTagName();
        selUtils.addTestcaseDescription("Validating Pinned Tag : " + tagName, "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            if (pages.getCustomerProfilePage().isPinTagVisible(tagName)) {
                pages.getCustomerProfilePage().clickPinTag(tagName);
                pages.getMsisdnSearchPage().waitUntilPageIsLoaded();
                pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
                pages.getMsisdnSearchPage().clickOnSearch();
                softAssert.assertTrue(pages.getCustomerProfilePage().isCustomerProfilePageLoaded());
                pages.getCustomerProfilePage().goToViewHistory();
                pages.getViewHistory().clickOnInteractionsTab();
                String issueCode = pages.getViewHistory().getLastCreatedIssueCode();
                softAssert.assertEquals(issueCode.toLowerCase().trim(), data.getIssueCode().trim().toLowerCase());
            } else {
                softAssert.fail(tagName + " Does not display on UI");
            }
        } catch (NoSuchElementException e) {
            commonLib.fail(tagName + " tag does not display on UI but present in config sheet.", true);
            softAssert.fail(tagName + " tag does not display on UI but present in config sheet.\n" + e.fillInStackTrace());
            e.printStackTrace();
        }
        softAssert.assertAll();
    }
}