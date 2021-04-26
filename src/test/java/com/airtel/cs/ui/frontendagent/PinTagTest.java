package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PinnedTagsDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PinTagTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }


    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        final String customerNumber = data.getCustomerNumber();
        selUtils.addTestcaseDescription("Validating the Search forCustomer Interactions :" + customerNumber, "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnName();
        pages.getSideMenuPage().openCustomerInteractionPage();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getMsisdnSearchPage().enterNumber(customerNumber);
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
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
                softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
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