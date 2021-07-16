package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.PinnedTagsDataBeans;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PinTagTest extends Driver {

    private final BaseActions actions = new BaseActions();
    final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
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


    /**
     * This method is used to validate all pinned tag
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void checkALLPinnedTag() {
        try {
            selUtils.addTestcaseDescription("Validating Pinned Tag", "description");
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
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkALLPinnedTag()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate issue code for pin tag
     *
     * @param data
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "pinTag", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void checkIssueCodeForPinTag(PinnedTagsDataBeans data) {
        try {
            final String tagName = data.getTagName();
            selUtils.addTestcaseDescription("Validating Pinned Tag : " + tagName, "description");
            try {
                if (pages.getCustomerProfilePage().isPinTagVisible(tagName)) {
                    pages.getCustomerProfilePage().clickPinTag(tagName);
                    assertCheck.append(actions.assertEqual_boolean(pages.getMsisdnSearchPage().isCustomerSearchPageVisible(), true, "Msisden Search Page Loaded Successfully", "Msisden Search Page NOT Loaded"));
                    pages.getMsisdnSearchPage().enterNumber(customerNumber);
                    pages.getMsisdnSearchPage().clickOnSearch();
                    assertCheck.append(actions.assertEqual_boolean(pages.getCustomerProfilePage().isCustomerProfilePageLoaded(), true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
                    pages.getCustomerProfilePage().goToViewHistory();
                    pages.getViewHistory().clickOnInteractionsTab();
                    String issueCode = pages.getViewHistory().getLastCreatedIssueCode();
                    assertCheck.append(actions.assertEqual_stringType(issueCode.toLowerCase().trim(), data.getIssueCode().trim().toLowerCase(), "Issue code found in view history", "Issue code doesn't found in view history"));
                } else {
                    commonLib.fail(tagName + " Does not display on UI", true);
                }
            } catch (NoSuchElementException e) {
                commonLib.fail(tagName + " tag does not display on UI but present in config sheet.", true);
                e.printStackTrace();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkIssueCodeForPinTag()" + e.fillInStackTrace(), true);
        }
    }
}