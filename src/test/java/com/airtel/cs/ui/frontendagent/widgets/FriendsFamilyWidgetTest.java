package com.airtel.cs.ui.frontendagent.widgets;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.common.requisite.PreRequisites;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.pojo.response.friendsfamily.FriendsFamilyPOJO;
import org.testng.annotations.Test;

public class FriendsFamilyWidgetTest extends PreRequisites {
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private static String customerNumber;

    @Test(priority = 1, description = "Validate Customer Interaction Page")
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
    @Test(priority = 2, description = "CSP-63690 Verify that in the DA Details page there should be a table containing Customer's FnF List", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void  friendFamilyHeaderTest(HeaderDataBean headerValues) {
        selUtils.addTestcaseDescription("Validate Friend and Family widget header visible and display all the Column name as per config ", "description");
        try {
            pages.getCurrentBalanceWidgetPage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(),true, "Current Balance Widget MENU is visible","Current Balance Widget Menu is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            pages.getCustomerProfilePage().waitTillLoaderGetsRemoved();
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isOfferWidgetDisplay(),true,"Display offer Widget display","Display offer widget does not display"));
            FriendsFamilyPOJO friendsFamilyAPI=api.friendsFamilyAPITest(customerNumber);
            if (Integer.parseInt(friendsFamilyAPI.getStatus()) != 200) {
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
