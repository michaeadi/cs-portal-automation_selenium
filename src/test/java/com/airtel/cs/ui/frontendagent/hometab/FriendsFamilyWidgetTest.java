package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.pojo.response.friendsfamily.FriendsFamilyPOJO;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FriendsFamilyWidgetTest extends PreRequisites {
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private static String customerNumber;
    private FriendsFamilyPOJO friendsFamilyAPI;
    public static final String RUN_FNF_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_FNF_WIDGET_TEST_CASE);

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA && !StringUtils.equalsIgnoreCase(RUN_FNF_WIDGET_TEST_CASE,"true")) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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

    @DataProviders.Table(name = "Friends and Family")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void  friendFamilyHeaderTest(HeaderDataBean headerValues) {
        selUtils.addTestcaseDescription("Validate Friend and Family widget header visible and display all the Column name as per config ", "description");
        try {
            pages.getCurrentBalanceWidgetPage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(),true, "Current Balance Widget MENU is visible","Current Balance Widget Menu is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isOfferWidgetDisplay(),true,"Display offer Widget display","Display offer widget does not display"));
            friendsFamilyAPI=api.friendsFamilyAPITest(customerNumber);
            final int statusCode = friendsFamilyAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Friends & Family Widget API success and status code is :" + statusCode, "Friends & Family Widget API got failed and status code is :" + statusCode));
            if (statusCode== 200) {
                assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFWidgetErrorDisplay(), true, "API and Widget both are showing error message", "API is Giving error But Widget is not showing error Message on API is " + friendsFamilyAPI.getMessage()));
                commonLib.fail("API is Unable to Get AM Transaction History for Customer", false);
            } else if(friendsFamilyAPI.getResponse().size()>0){
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(1).toLowerCase().trim(), headerValues.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(2).toLowerCase().trim(), headerValues.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(3).toLowerCase().trim(), headerValues.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(4).toLowerCase().trim(), headerValues.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getDisplayOfferHeader(5).toLowerCase().trim(), headerValues.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
            }else {
                assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFNoResultIconDisplay(), true, "API and Widget both are showing No Result found Icon", "API is Giving no result But Widget is not showing no result found icon"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - friendFamilyHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    /**
     * Test Case blocked as api not working
     * */
}
